/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: FieldInfo.java,v 1.10 2006/05/09 05:46:58 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sleepycat.persist.raw.RawField;
import com.sleepycat.persist.raw.RawType;

/**
 * A field definition used by ComplexFormat and CompositeKeyFormat.
 *
 * @author Mark Hayes
 */
class FieldInfo implements RawField, Serializable, Comparable<FieldInfo> {
    
    private static final long serialVersionUID = 2062721100372306296L;

    /**
     * Returns a list of all non-transient non-static fields that are declared
     * in the given class.
     */
    static List<FieldInfo> getInstanceFields(Class cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        List<FieldInfo> fields =
            new ArrayList<FieldInfo>(declaredFields.length);
        for (Field field : declaredFields) {
            int mods = field.getModifiers();
            if (!Modifier.isTransient(mods) && !Modifier.isStatic(mods)) {
                fields.add(new FieldInfo(field));
            }
        }
        return fields;
    }

    static FieldInfo getField(List<FieldInfo> fields, String fieldName) {
        for (FieldInfo field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    private String name;
    private String className;
    private transient Class cls;
    private transient Format format;

    FieldInfo(Field field) {
        name = field.getName();
        cls = field.getType();
        className = cls.getName();
    }

    void collectRelatedFormats(Catalog catalog,
                               Map<String,Format> newFormats) {
        Class cls = getFieldClass();
        catalog.createFormat(cls, newFormats);
    }

    void initialize(Catalog catalog) {
        format = catalog.getFormat(className);
        if (format == null) {
            throw new IllegalStateException(className);
        }
    }

    Class getFieldClass() {
        if (cls == null) {
            try {
                cls = SimpleCatalog.classForName(className);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return cls;
    }

    public String getName() {
        return name;
    }

    public Format getType() {
        return format;
    }

    public RawType getExpandedType() {
        throw new UnsupportedOperationException(); /* XXX */
    }

    public int compareTo(FieldInfo o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FieldInfo) {
            FieldInfo o = (FieldInfo) other;
            return name.equals(o.name) &&
                   className.equals(o.className);
        } else {
            return false;
        }
    }
}
