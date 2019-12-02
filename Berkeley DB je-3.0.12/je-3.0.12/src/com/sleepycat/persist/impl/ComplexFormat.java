/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ComplexFormat.java,v 1.19 2006/05/24 17:13:32 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sleepycat.persist.model.ClassMetadata;
import com.sleepycat.persist.model.EntityMetadata;
import com.sleepycat.persist.model.PrimaryKeyMetadata;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKeyMetadata;
import com.sleepycat.persist.raw.RawField;
import com.sleepycat.persist.raw.RawObject;

/**
 * Format for persistent complex classes that are not not composite key
 * classes.  This includes entity classes and subclasses.
 *
 * @author Mark Hayes
 */
public class ComplexFormat extends Format {

    private static final long serialVersionUID = -2847843033590454917L;

    private ClassMetadata clsMeta;
    private EntityMetadata entityMeta;
    private FieldInfo priKeyField;
    private List<FieldInfo> secKeyFields;
    private List<FieldInfo> nonKeyFields;
    private transient Accessor objAccessor;
    private transient Accessor rawAccessor;
    private transient Format entityFormat;
    private transient Map<String,RawField> rawFields;
    private transient Map<String,FieldAddress> secKeyAddresses;

    ComplexFormat(Class cls,
                  ClassMetadata clsMeta,
                  EntityMetadata entityMeta) {
        super(cls);
        this.clsMeta = clsMeta;
        this.entityMeta = entityMeta;
        secKeyFields = new ArrayList<FieldInfo>();
        nonKeyFields = FieldInfo.getInstanceFields(cls);

        /*
         * Validate primary key metadata and move primary key field from
         * nonKeyFields to priKeyField.
         */
        if (clsMeta.getPrimaryKey() != null) {
            String fieldName = clsMeta.getPrimaryKey().getName();
            FieldInfo field = FieldInfo.getField(nonKeyFields, fieldName);
            if (field == null) {
                throw new IllegalArgumentException
                    ("Primary key field does not exist: " +
                     getClassName() + '.' + fieldName);
            }
            nonKeyFields.remove(field);
            priKeyField = field;
        }

        /*
         * Validate secondary key metadata and move secondary key fields from
         * nonKeyFields to secKeyFields.
         */
        if (clsMeta.getSecondaryKeys() != null) {
            for (SecondaryKeyMetadata secKeyMeta :
                 clsMeta.getSecondaryKeys().values()) {
                String fieldName = secKeyMeta.getName();
                FieldInfo field = FieldInfo.getField(nonKeyFields, fieldName);
                if (field == null) {
                    throw new IllegalArgumentException
                        ("Secondary key field does not exist: " +
                         getClassName() + '.' + fieldName);
                }
                Class fieldCls = field.getFieldClass();
                Relationship rel = secKeyMeta.getRelationship();
                if (rel == Relationship.ONE_TO_MANY ||
                    rel == Relationship.MANY_TO_MANY) {
                    if (!PersistKeyCreator.isManyType(fieldCls)) {
                        throw new IllegalArgumentException
                            ("ONE_TO_MANY and MANY_TO_MANY keys must" +
                             " have an array or Collection type: " +
                             getClassName() + '.' + fieldName);
                    }
                } else {
                    if (PersistKeyCreator.isManyType(fieldCls)) {
                        throw new IllegalArgumentException
                            ("ONE_TO_ONE and MANY_TO_ONE keys must not" +
                             " have an array or Collection type: " +
                             getClassName() + '.' + fieldName);
                    }
                }
                nonKeyFields.remove(field);
                secKeyFields.add(field);
            }
        }

        /* Sort each group of fields by name. */
        Collections.sort(secKeyFields);
        Collections.sort(nonKeyFields);
    }

    String getPriKeyField() {
        if (clsMeta.getPrimaryKey() != null) {
            return clsMeta.getPrimaryKey().getName();
        } else {
            return null;
        }
    }

    @Override
    boolean isEntity() {
        return clsMeta.isEntityClass();
    }

    @Override
    boolean isModelClass() {
        return true;
    }

    @Override
    ClassMetadata getClassMetadata() {
        return clsMeta;
    }

    @Override
    EntityMetadata getEntityMetadata() {
        return entityMeta;
    }

    @Override
    Format getEntityFormat() {
        return entityFormat;
    }

    @Override
    public Map<String,RawField> getFields() {

        /*
         * Synchronization is not required since this object is immutable.  If
         * by chance we create two maps when two threads execute this block, no
         * harm is done.  But be sure to assign the rawFields field only after
         * the map is fully populated.
         */
        if (rawFields == null) {
            Map<String,RawField> map = new HashMap<String,RawField>();
            if (priKeyField != null) {
                map.put(priKeyField.getName(), priKeyField);
            }
            for (RawField field : secKeyFields) {
                map.put(field.getName(), field);
            }
            for (RawField field : nonKeyFields) {
                map.put(field.getName(), field);
            }
            rawFields = map;
        }
        return rawFields;
    }

    @Override
    void collectRelatedFormats(Catalog catalog,
                               Map<String,Format> newFormats) {
        Class cls = getType();
        /* Collect field formats. */
        if (priKeyField != null) {
            priKeyField.collectRelatedFormats(catalog, newFormats);
        }
        for (FieldInfo field : secKeyFields) {
            field.collectRelatedFormats(catalog, newFormats);
        }
        for (FieldInfo field : nonKeyFields) {
            field.collectRelatedFormats(catalog, newFormats);
        }
        /* Recursively collect superclass formats. */
        Class superCls = cls.getSuperclass();
        if (superCls != Object.class) {
            Format superFormat = catalog.createFormat(superCls, newFormats);
            if (!(superFormat instanceof ComplexFormat)) {
                throw new IllegalArgumentException
                    ("The superclass of a complex type must not be a" +
                     " composite key class or a simple type class: " +
                     superCls.getName());
            }
            setSuperFormat(superFormat);
        }
        /* Collect proxied format. */
        String proxiedClsName = clsMeta.getProxiedClassName();
        if (proxiedClsName != null) {
            Class proxiedCls;
            try {
                proxiedCls = SimpleCatalog.classForName(proxiedClsName);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
            catalog.createFormat(proxiedCls, newFormats);
        }
    }

    @Override
    void initialize(Catalog catalog) {
        Class type = getType();
        boolean useEnhanced = EnhancedAccessor.isEnhanced(type);
        /* Initialize all fields. */
        if (priKeyField != null) {
            priKeyField.initialize(catalog);
        }
        for (FieldInfo field : secKeyFields) {
            field.initialize(catalog);
        }
        for (FieldInfo field : nonKeyFields) {
            field.initialize(catalog);
        }
        /* Initialize the superclass format. */
        ComplexFormat superFormat = (ComplexFormat) getSuperFormat();
        if (superFormat != null) {
            superFormat.initializeIfNeeded(catalog);
            Accessor superAccessor = superFormat.objAccessor;
            if (useEnhanced) {
                if (!(superAccessor instanceof EnhancedAccessor)) {
                    throw new IllegalStateException
                        ("The superclass of an enhanced class must also " +
                         " be enhanced: " + superFormat.getClassName());
                }
            } else {
                if (!(superAccessor instanceof ReflectionAccessor)) {
                    throw new IllegalStateException
                        ("The superclass of an unenhanced class must " +
                         " not be enhanced: " + superFormat.getClassName());
                }
            }
        }
        /* Find entity class/metadata, if any. */
        EntityMetadata entityMeta = null;
        for (Format format = this;
             format != null;
             format = format.getSuperFormat()) {
            if (format.isEntity()) {
                entityFormat = format;
                entityMeta = format.getEntityMetadata();
                break;
            }
        }
        /* Create the accessors. */
        if (useEnhanced) {
            objAccessor = new EnhancedAccessor(catalog, type, this);
        } else {
            Accessor superObjAccessor =
                (superFormat != null) ?  superFormat.objAccessor : null;
            objAccessor = new ReflectionAccessor
                (catalog, type, superObjAccessor, priKeyField, secKeyFields,
                 nonKeyFields);
        }
        Accessor superRawAccessor =
            (superFormat != null) ? superFormat.rawAccessor : null;
        rawAccessor = new RawAccessor
            (this, superRawAccessor, priKeyField, secKeyFields, nonKeyFields);

        /* Initialize secondary key field addresses. */
        if (entityMeta != null) {
            secKeyAddresses = new HashMap<String,FieldAddress>();
            for (SecondaryKeyMetadata secKeyMeta :
                 entityMeta.getSecondaryKeys().values()) {
                String clsName = secKeyMeta.getDeclaringClassName();
                String fieldName = secKeyMeta.getName();
                int superLevel = 0;
                for (ComplexFormat format = this; format != null;
                     format = (ComplexFormat) format.getSuperFormat()) {
                    if (clsName.equals(format.getClassName())) {
                        boolean isSecField;
                        int fieldNum;
                        FieldInfo info = FieldInfo.getField
                            (format.secKeyFields, fieldName);
                        if (info != null) {
                            isSecField = true;
                            fieldNum = format.secKeyFields.indexOf(info);
                        } else {
                            isSecField = false;
                            info = FieldInfo.getField
                                (format.nonKeyFields, fieldName);
                            if (info == null) {
                                throw new IllegalStateException
                                    (secKeyMeta.toString());
                            }
                            fieldNum = format.nonKeyFields.indexOf(info);
                        }
                        FieldAddress addr = new FieldAddress
                            (isSecField, fieldNum, superLevel, format,
                             info.getType());
                        secKeyAddresses.put(secKeyMeta.getKeyName(), addr);
                    }
                    superLevel += 1;
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ComplexFormat) {
            ComplexFormat o = (ComplexFormat) other;
            return super.equals(o) &&
                   clsMeta.equals(o.clsMeta) &&
                   nullOrEqual(priKeyField, o.priKeyField) &&
                   secKeyFields.equals(o.secKeyFields) &&
                   nonKeyFields.equals(o.nonKeyFields);
        } else {
            return false;
        }
    }

    private boolean nullOrEqual(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }

    @Override
    Object newInstance(EntityInput input, boolean rawAccess) {
        Accessor accessor = rawAccess ? rawAccessor : objAccessor;
        return accessor.newInstance();
    }
    
    @Override
    Object newArray(int len) {
        return objAccessor.newArray(len);
    }

    @Override
    void writeObject(Object o, EntityOutput output, boolean rawAccess) {
        Accessor accessor = rawAccess ? rawAccessor : objAccessor;
        accessor.writeSecKeyFields(o, output);
        accessor.writeNonKeyFields(o, output);
    }

    @Override
    void readObject(Object o, EntityInput input, boolean rawAccess) {
        Accessor accessor = rawAccess ? rawAccessor : objAccessor;
        accessor.readSecKeyFields(o, input, 0, Integer.MAX_VALUE, -1);
        accessor.readNonKeyFields(o, input, 0, Integer.MAX_VALUE, -1);
    }

    @Override
    boolean isPriKeyNullOrZero(Object o, boolean rawAccess) {
        Accessor accessor = rawAccess ? rawAccessor : objAccessor;
        return accessor.isPriKeyFieldNullOrZero(o);
    }

    @Override
    void writePriKey(Object o, EntityOutput output, boolean rawAccess) {
        Accessor accessor = rawAccess ? rawAccessor : objAccessor;
        accessor.writePriKeyField(o, output);
    }

    @Override
    void readPriKey(Object o, EntityInput input, boolean rawAccess) {
        Accessor accessor = rawAccess ? rawAccessor : objAccessor;
        accessor.readPriKeyField(o, input);
    }

    @Override
    boolean nullifySecKey(Catalog catalog,
                          Object entity,
                          String keyName,
                          Object keyElement) {
        if (secKeyAddresses == null) {
            throw new IllegalStateException();
        }
        FieldAddress addr = secKeyAddresses.get(keyName);
        if (addr != null) {
            Object oldVal = rawAccessor.getField
                (entity, addr.fieldNum, addr.superLevel, addr.isSecField);
            if (oldVal != null) {
                if (keyElement != null) {
                    RawObject container = (RawObject) oldVal;
                    Object[] a1 = container.getElements();
                    boolean isArray = (a1 != null);
                    if (!isArray) {
                        a1 = CollectionProxy.getElements(container);
                    }
                    if (a1 != null) {
                        for (int i = 0; i < a1.length; i += 1) {
                            if (keyElement.equals(a1[i])) {
                                int len = a1.length - 1;
                                Object[] a2 = new Object[len];
                                System.arraycopy(a1, 0, a2, 0, i);
                                System.arraycopy(a1, i + 1, a2, i, len - i);
                                if (isArray) {
                                    rawAccessor.setField
                                        (entity, addr.fieldNum,
                                         addr.superLevel, addr.isSecField,
                                         new RawObject
                                            (container.getType(), a2));
                                } else {
                                    CollectionProxy.setElements(container, a2);
                                }
                                return true;
                            }
                        }
                    }
                    return false;
                } else {
                    rawAccessor.setField
                        (entity, addr.fieldNum, addr.superLevel,
                         addr.isSecField, null);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    void skipContents(EntityInput input) {
        skipToSecKeyField(input, Integer.MAX_VALUE);
        skipToNonKeyField(input, Integer.MAX_VALUE);
    }

    @Override
    void copySecMultiKey(EntityInput input, Format keyFormat, Set results) {
        CollectionProxy.copyElements(input, this, keyFormat, results);
    }

    @Override
    Format skipToSecKey(EntityInput input, String keyName) {
        if (secKeyAddresses == null) {
            throw new IllegalStateException();
        }
        FieldAddress addr = secKeyAddresses.get(keyName);
        if (addr != null) {
            if (addr.isSecField) {
                addr.clsFormat.skipToSecKeyField(input, addr.fieldNum);
            } else {
                skipToSecKeyField(input, Integer.MAX_VALUE);
                addr.clsFormat.skipToNonKeyField(input, addr.fieldNum);
            }
            return addr.keyFormat;
        } else {
            return null;
        }
    }
    
    private void skipToSecKeyField(EntityInput input, int toFieldNum) {
        ComplexFormat superFormat = (ComplexFormat) getSuperFormat();
        if (superFormat != null) {
            superFormat.skipToSecKeyField(input, Integer.MAX_VALUE);
        }
        int maxNum = Math.min(secKeyFields.size(), toFieldNum);
        for (int i = 0; i < maxNum; i += 1) {
            input.skipField(secKeyFields.get(i).getType());
        }
    }
    
    private void skipToNonKeyField(EntityInput input, int toFieldNum) {
        ComplexFormat superFormat = (ComplexFormat) getSuperFormat();
        if (superFormat != null) {
            superFormat.skipToNonKeyField(input, Integer.MAX_VALUE);
        }
        int maxNum = Math.min(nonKeyFields.size(), toFieldNum);
        for (int i = 0; i < maxNum; i += 1) {
            input.skipField(nonKeyFields.get(i).getType());
        }
    }

    private static class FieldAddress {

        boolean isSecField;
        int fieldNum;
        int superLevel;
        ComplexFormat clsFormat;
        Format keyFormat;

        FieldAddress(boolean isSecField,
                     int fieldNum,
                     int superLevel,
                     ComplexFormat clsFormat,
                     Format keyFormat) {
            this.isSecField = isSecField;
            this.fieldNum = fieldNum;
            this.superLevel = superLevel;
            this.clsFormat = clsFormat;
            this.keyFormat = keyFormat;
        }
    }
}
