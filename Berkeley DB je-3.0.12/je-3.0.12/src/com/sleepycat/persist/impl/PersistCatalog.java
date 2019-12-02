/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: PersistCatalog.java,v 1.22 2006/05/24 17:13:32 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.evolve.Mutations;
import com.sleepycat.persist.model.AnnotationModel;
import com.sleepycat.persist.model.ClassMetadata;
import com.sleepycat.persist.model.EntityMetadata;
import com.sleepycat.persist.model.EntityModel;
import com.sleepycat.persist.model.FieldMetadata;
import com.sleepycat.persist.model.PrimaryKeyMetadata;
import com.sleepycat.persist.model.SecondaryKeyMetadata;
import com.sleepycat.util.RuntimeExceptionWrapper;

/**
 * The catalog of class formats for a store, along with its associated model
 * and mutations.
 *
 * @author Mark Hayes
 */
public class PersistCatalog implements Catalog {

    /** Key to formatList record in the catalog database. */
    private static final byte[] FORMATS_KEY = getIntBytes(-1);

    /** Key to mutations record in the catalog database. */
    private static final byte[] MUTATIONS_KEY = getIntBytes(-2);

    private static byte[] getIntBytes(int val) {
        DatabaseEntry entry = new DatabaseEntry();
        IntegerBinding.intToEntry(val, entry);
        assert entry.getSize() == 4 && entry.getData().length == 4;
        return entry.getData();
    }

    /**
     * A list of formats indexed by formatId.  Element zero is unused and null,
     * since IDs start at one; this avoids adjusting the ID to index the list.
     * Some elements are null to account for predefined IDs that are not used.
     *
     * <p>This field, like formatMap, is volatile because it is reassigned
     * when adding array and enum formats.  See {@link getFormat(Class)}.</p>
     */
    private volatile List<Format> formatList;

    /**
     * A map of the formats in formatList, indexed by class name.
     *
     * <p>This field, like formatList, is volatile because it is reassigned
     * when adding array and enum formats.  See {@link getFormat(Class)}.</p>
     */
    private volatile Map<String,Format> formatMap;

    /**
     * A temporary map of proxied class name to proxy class name.  Used during
     * catalog creation, and then set to null.  This map is used to force proxy
     * formats to be created prior to proxied formats. [#14665]
     */
    private Map<String,String> proxyClassMap;

    private EntityModel model;
    private Mutations mutations;
    private Database db;
    private int openCount;

    /**
     * Creates a new catalog, opening the database and reading it from a given
     * catalog database if it already exists.  All predefined formats and
     * formats for the given model are added.  For modified classes, old
     * formats are defined based on the rules for compatible class changes and
     * the given mutations.  If any format is changed or added, and the
     * database is not read-only, write the initialized catalog to the
     * database.
     */
    public PersistCatalog(Transaction txn,
                          Environment env,
                          String dbName,
                          DatabaseConfig dbConfig,
                          EntityModel modelParam,
                          Mutations mutationsParam,
                          boolean useCurrentModel)
        throws DatabaseException {

        db = env.openDatabase(txn, dbName, dbConfig);
        openCount = 1;
        boolean success = false;
        try {
            /* Merge the given mutations with the stored mutations. */
            mutations = (Mutations) readObject(txn, MUTATIONS_KEY);
            if (mutationsParam != null) {
                mutations = mergeMutations(mutations, mutationsParam);
                writeObject(txn, MUTATIONS_KEY, mutations);
            }

            /* Get the existing format list, or copy it from SimpleCatalog. */
            formatList = (List) readObject(txn, FORMATS_KEY);
            if (formatList == null) {
                formatList = SimpleCatalog.copyFormatList();
                /* Special case: Object is predefined but not a simple type. */
                Format format = new NonPersistentFormat(Object.class);
                format.setId(Format.ID_OBJECT);
                formatList.set(Format.ID_OBJECT, format);
            }

            /*
             * If we should not use the current model, initialize the stored
             * model and stop.
             */
            formatMap = new HashMap<String,Format>(formatList.size());
            if (!useCurrentModel) {
                for (Format format : formatList) {
                    if (format != null) {
                        formatMap.put(format.getClassName(), format);
                    }
                }
                for (Format format : formatList) {
                    if (format != null) {
                        format.initializeIfNeeded(this);
                    }
                }
                success = true;
                return;
            }

            /* Default to the AnnotationModel. */
            if (modelParam == null) {
                modelParam = new AnnotationModel();
            }

            /* Use the given model to update the stored catalog/model. */
            model = modelParam;

            /*
             * Add all predefined (simple) formats to the format map.  The
             * current version of other formats will be added below.
             */
            for (int i = 0; i <= Format.ID_PREDEFINED; i += 1) {
                Format simpleFormat = formatList.get(i);
                if (simpleFormat != null) {
                    formatMap.put(simpleFormat.getClassName(), simpleFormat);
                }
            }

            /*
             * Known classes are those explicitly registered by the user via
             * the model, plus the predefined proxy classes.
             */
            List<String> knownClasses =
                new ArrayList<String>(model.getKnownClasses());
            addPredefinedProxies(knownClasses);

            /*
             * Create a temporary map of proxied class name to proxy class
             * name, using all known formats and classes.  This map is used to
             * force proxy formats to be created prior to proxied formats.
             * [#14665]
             */
            proxyClassMap = new HashMap<String,String>();
            for (Format oldFormat : formatList) {
                if (oldFormat == null || Format.isPredefined(oldFormat)) {
                    continue;
                }
                addProxiedClass(oldFormat.getClassName());
            }
            for (String className : knownClasses) {
                addProxiedClass(className);
            }

            /*
             * Create a new format for each old format that is not predefined
             * (simple). Note that the process of querying metadata will build
             * up the EntityModel to its previous state.
             */
            Map<String,Format> newFormats = new HashMap<String,Format>();
            for (Format oldFormat : formatList) {
                if (oldFormat == null || Format.isPredefined(oldFormat)) {
                    continue;
                }
                String className = oldFormat.getClassName();
                if (!newFormats.containsKey(className)) {
                    Class type;
                    try {
                        type = SimpleCatalog.classForName(className);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException
                            ("Class no longer exists: " + className);
                    }
                    createFormat(type, newFormats);
                }
            }

            /*
             * Add known formats from the model and the predefined proxies.
             * In general, classes will not be present in an AnnotationModel
             * until an instance is stored, in which case an old format exists.
             * However, registered proxy classes are an exception and must be
             * added in advance.  And the user may choose to register new
             * classes in advance.  The more formats we define in advance, the
             * less times we have to write to the catalog database.
             */
            for (String className : knownClasses) {
                if (!newFormats.containsKey(className)) {
                    Class type;
                    try {
                        type = SimpleCatalog.classForName(className);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException
                            ("Class no longer exists: " + className);
                    }
                    if (!SimpleCatalog.isSimpleType(type)) {
                        createFormat(type, newFormats);
                    }
                }
            }

            /*
             * For each old format that is not predefined (simple), use it as
             * the current format if it equals the new format, or evolve it to
             * the current format if it differs.
             */
            for (Format oldFormat : formatList) {
                if (oldFormat == null || Format.isPredefined(oldFormat)) {
                    continue;
                }
                String className = oldFormat.getClassName();
                Format newFormat = newFormats.get(className);
                assert newFormat != null;
                if (newFormat.equals(oldFormat)) {
                    /* The old format is current; discard the new version. */
                    formatMap.put(className, oldFormat);
                    newFormats.remove(className);
                } else {
                    /* The old format must evolve to the current format. */
                    if (true) {
                        throw new UnsupportedOperationException
                            ("Class evolution is not supported in the beta " +
                             "release and the store must be deleted when " +
                             "a class is changed: " + className);
                    } else {
                        /* Enable code below for class evolution. */
                        Format updatedFormat = oldFormat.evolveTo(newFormat);
                        if (updatedFormat != oldFormat) {
                            replaceFormat(oldFormat, updatedFormat);
                        }
                    }
                }
            }

            /*
             * Add the new formats remaining.  New formats that are equal to
             * old formats were removed from the newFormats map above.
             */
            for (Format newFormat : newFormats.values()) {
                addFormat(newFormat);
            }

            /* Initialize all formats. */
            for (Format format : formatList) {
                if (format != null) {
                    format.initializeIfNeeded(this);
                }
            }

            /* Write the catalog if anything changed. */
            if (!db.getConfig().getReadOnly() && newFormats.size() > 0) {
                writeObject(txn, FORMATS_KEY, formatList);
            }

            /* The proxyClassMap is no longer needed. */
            proxyClassMap = null;

            success = true;
        } finally {
            if (!success) {
                close();
            }
        }
    }

    private void addProxiedClass(String className) {
        ClassMetadata metadata = model.getClassMetadata(className);
        if (metadata != null) {
            String proxiedClassName = metadata.getProxiedClassName();
            if (proxiedClassName != null) {
                proxyClassMap.put(proxiedClassName, className);
            }
        }
    }

    private void addPredefinedProxies(List<String> knownClasses) {
        knownClasses.add(CollectionProxy.ArrayListProxy.class.getName());
        knownClasses.add(CollectionProxy.LinkedListProxy.class.getName());
        knownClasses.add(CollectionProxy.HashSetProxy.class.getName());
        knownClasses.add(CollectionProxy.TreeSetProxy.class.getName());
        knownClasses.add(MapProxy.HashMapProxy.class.getName());
        knownClasses.add(MapProxy.TreeMapProxy.class.getName());
    }

    /**
     * Gets the model parameter, default model or stored model.
     */
    public EntityModel getResolvedModel() {
        if (model != null) {
            return model;
        } else {
            return new StoredModel(this);
        }
    }

    /**
     * Increments the reference count for a catalog that is already open.
     */
    public void openExisting() {
        openCount += 1;
    }

    /**
     * Decrements the reference count and closes the catalog DB when it reaches
     * zero.  Returns true if the database was closed or false if the reference
     * count is still non-zero and the database was left open.
     */
    public boolean close()
        throws DatabaseException {

        if (openCount == 0) {
            throw new IllegalStateException("Catalog is not open");
        } else {
            openCount -= 1;
            if (openCount == 0) {
                Database dbToClose = db;
                db = null;
                dbToClose.close();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Returns the current merged mutations.
     */
    public Mutations getMutations() {
        return mutations;
    }

    /**
     * If the given class format is not already present in the given map,
     * creates an uninitialized format, adds it to the map, and also collects
     * related formats in the map.
     */
    public Format createFormat(Class type, Map<String,Format> newFormats) {
        /* Return an existing or new format for this class. */
        String className = type.getName();
        Format format = newFormats.get(className);
        if (format != null) {
            return format;
        }
        format = formatMap.get(className);
        if (format != null) {
            return format;
        }
        /* Simple types are predefined. */
        assert !SimpleCatalog.isSimpleType(type) : className;
        /* Create format of the appropriate type. */
        String proxyClassName = null;
        if (proxyClassMap != null) {
            proxyClassName = proxyClassMap.get(className);
        }
        if (proxyClassName != null) {
            /* Must create proxy class format before proxied class format. */
            Class proxyClass;
            try {
                proxyClass = SimpleCatalog.classForName(proxyClassName);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
            Format proxyFormat = createFormat(proxyClass, newFormats);
            format = new ProxiedFormat(type, proxyFormat);
        } else if (type.isArray()) {
            format = type.getComponentType().isPrimitive() ?
                (new PrimitiveArrayFormat(type)) :
                (new ObjectArrayFormat(type));
        } else if (type.isEnum()) {
            format = new EnumFormat(type);
        } else if (type == Object.class || type.isInterface()) {
            format = new NonPersistentFormat(type);
        } else {
            ClassMetadata metadata = model.getClassMetadata(className);
            if (metadata == null) {
                throw new IllegalArgumentException
                    ("Class is not persistent: " + className);
            }
            if (metadata.getCompositeKeyFields() != null &&
                (metadata.getPrimaryKey() != null ||
                 metadata.getSecondaryKeys() != null)) {
                throw new IllegalArgumentException
                    ("A composite key class may not have primary or" +
                     " secondary key fields: " + type.getName());
            }
            try {
                type.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException
                    ("No default constructor: " + type.getName(), e);
            }
            if (metadata.getCompositeKeyFields() != null) {
                format = new CompositeKeyFormat
                    (type, metadata, metadata.getCompositeKeyFields());
            } else {
                EntityMetadata entityMetadata =
                    model.getEntityMetadata(className);
                format = new ComplexFormat(type, metadata, entityMetadata);
            }
        }
        /* Collect new format along with any related new formats. */
        newFormats.put(className, format);
        format.collectRelatedFormats(this, newFormats);

        return format;
    }

    /**
     * Adds a format and makes it the current format for the class.
     */
    private void addFormat(Format format) {
        addFormat(format, formatList, formatMap);
    }

    /**
     * Adds a format given the format collections, for use when dynamically
     * adding enum and array formats.
     */
    private void addFormat(Format format,
                           List<Format> list,
                           Map<String,Format> map) {
        format.setId(list.size());
        list.add(format);
        map.put(format.getClassName(), format);
    }
    
    /**
     * Replaces oldFormat with newFormat, giving the new format the same ID as
     * the old format.
     */
    private void replaceFormat(Format oldFormat, Format newFormat) {
        assert oldFormat.getClassName().equals(newFormat.getClassName());
        int id = oldFormat.getId();
        newFormat.setId(id);
        formatList.set(id, newFormat);
    }

    /**
     * Returns a set of all persistent (non-simple type) class names.
     */
    Set<String> getModelClasses() {
        Set<String> classes = new HashSet<String>();
        for (Format format : formatMap.values()) {
            if (format.isModelClass()) {
                classes.add(format.getClassName());
            }
        }
        return classes;
    }

    public Format getFormat(int formatId) {
        try {
            Format format = formatList.get(formatId);
            if (format == null) {
                throw new IllegalStateException
                    ("Format does not exist: " + formatId);
            }
            return format;
        } catch (NoSuchElementException e) {
            throw new IllegalStateException
                ("Format does not exist: " + formatId);
        }
    }

    public Format getFormat(Class cls) {
        Format format = formatMap.get(cls.getName());
        if (format == null) {
            if (model != null) {
                format = addNewFormat(cls);
            }
            if (format == null) {
                throw new IllegalArgumentException
                    ("Class is not persistent: " + cls.getName());
            }
        }
        return format;
    }

    public Format getFormat(String className) {
        return formatMap.get(className);
    }

    /**
     * Adds a format for a new class.  Returns the format added for the given
     * class, or throws an exception if the given class is not persistent.
     *
     * <p>This method uses a copy-on-write technique to add new formats without
     * impacting other threads.</p>
     */
    private synchronized Format addNewFormat(Class cls) {

        /*
         * After synchronizing, check whether another thread has added the
         * format needed.  Note that this is not the double-check technique
         * because the formatMap field is volatile and is not itself checked
         * for null.  (The double-check technique is known to be flawed in
         * Java.)
         */
        Format format = formatMap.get(cls.getName());
        if (format != null) {
            return format;
        }

        /* Copy the read-only format collections. */
        List<Format> newList = new ArrayList<Format>(formatList);
        Map<String,Format> newMap = new HashMap<String,Format>(formatMap);

        /* Add the new format and all related new formats. */
        Map<String,Format> newFormats = new HashMap<String,Format>();
        format = createFormat(cls, newFormats);
        for (Format newFormat : newFormats.values()) {
            addFormat(newFormat, newList, newMap);
        }

        /*
         * Initialize new formats using a read-only catalog because we can't
         * update this catalog until after we store it (below).
         */
        Catalog newFormatCatalog = new ReadOnlyCatalog(newList, newMap);
        for (Format newFormat : newFormats.values()) {
            newFormat.initializeIfNeeded(newFormatCatalog);
        }

        /*
         * Write the updated catalog using auto-commit, then assign the new
         * collections.  The database write must occur before the collections
         * are used, since a format must be persistent before it can be
         * referenced by a data record.
         */
        try {
            writeObject(null, FORMATS_KEY, newList);
        } catch (DatabaseException e) {
            throw new RuntimeExceptionWrapper(e);
        }
        formatList = newList;
        formatMap = newMap;

        return format;
    }

    /**
     * Reads an object with a given key.
     */
    private Object readObject(Transaction txn, byte[] recordKey)
        throws DatabaseException {
        
        Object object = null;
        DatabaseEntry key = new DatabaseEntry(recordKey);
        DatabaseEntry data = new DatabaseEntry();
        OperationStatus status = db.get(txn, key, data, null);
        if (status == OperationStatus.SUCCESS) {
            ByteArrayInputStream bais = new ByteArrayInputStream
                (data.getData(), data.getOffset(), data.getSize());
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                object = ois.readObject();
                assert ois.available() == 0;
            } catch (ClassNotFoundException e) {
                throw new DatabaseException(e);
            } catch (IOException e) {
                throw new DatabaseException(e);
            }
        }
        return object;
    }

    /**
     * Writes an object with a given key.
     */
    private void writeObject(Transaction txn, byte[] recordKey, Object object)
        throws DatabaseException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        DatabaseEntry key = new DatabaseEntry(recordKey);
        DatabaseEntry data = new DatabaseEntry(baos.toByteArray());
        db.put(txn, key, data);
    }

    /**
     * Placeholder for type evolution support.
     */
    private Mutations mergeMutations(Mutations storedMutations,
                                     Mutations newMutations) {
        throw new UnsupportedOperationException
            ("Class evolution is not supported in this beta release.");
    }
}
