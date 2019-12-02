/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: EntityModel.java,v 1.8 2006/05/02 23:14:14 mark Exp $
 */

package com.sleepycat.persist.model;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.impl.PersistCatalog;
import com.sleepycat.persist.model.AnnotationModel;
import com.sleepycat.persist.raw.RawType;

/**
 * The base class for classes that provide entity model metadata.  An {@link
 * EntityModel} defines entity classes, primary keys, secondary keys, and
 * relationships between entities.  For each entity class that is part of the
 * model, a single {@link PrimaryIndex} object and zero or more {@link
 * SecondaryIndex} objects may be accessed via an {@link EntityStore}.
 * 
 * <p>The built-in entity model, the {@link AnnotationModel}, is based on
 * annotations that are added to entity classes and their key fields.
 * Annotations are used in the examples in this package, and it is expected
 * that annotations will normally be used; most readers should therefore skip
 * to the {@link AnnotationModel} class.  However, a custom entity model class
 * may define its own metadata.  This can be used to define entity classes and
 * keys using mechanisms other than annotations.</p>
 *
 * <p>A concrete entity model class should extend this class and implement the
 * {@link #getClassMetadata}, {@link #getEntityMetadata} and {@link
 * #getKnownClasses} methods.</p>
 *
 * <p>This is an abstract class rather than an interface to allow adding
 * capabilities to the model at a future date without causing
 * incompatibilities.  For example, a method may be added in the future for
 * returning new information about the model and subclasses may override this
 * method to return the new information.  Any new methods will have default
 * implementations that return default values, and the use of the new
 * information will be optional.</p>
 *
 * @author Mark Hayes
 */
public abstract class EntityModel {

    private PersistCatalog catalog;

    /**
     * The default constructor for use by subclasses.
     */
    protected EntityModel() {
    }

    /**
     * Registers a persistent class, most importantly, a {@link
     * PersistentProxy} class.  Any persistent class may be registered in
     * advance of using it, to avoid the overhead of updating the catalog
     * database when an instance of the class is first stored.  This method
     * <em>must</em> be called to register {@link PersistentProxy} classes.
     * This method must be called before opening a store based on this model.
     *
     * @throws IllegalStateException if this method is called for a model that
     * is associated with an open store.
     *
     * @throws IllegalArgumentException if the given class is not persistent.
     */
    public final void registerClass(Class persistentClass) {
        if (catalog != null) {
            throw new IllegalStateException("Store is already open");
        } else {
            String className = persistentClass.getName();
            ClassMetadata meta = getClassMetadata(className);
            if (meta == null) {
                throw new IllegalArgumentException
                    ("Class is not persistent: " + className);
            }
        }
    }

    /**
     * Gives this model access to the catalog, which is used for returning
     * raw type information.
     */
    void setCatalog(PersistCatalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Returns the metadata for a given persistent class name, including proxy
     * classes and entity classes.
     *
     * @return the metadata or null if the class is not persistent or does not
     * exist.
     */
    public abstract ClassMetadata getClassMetadata(String className);

    /**
     * Returns the metadata for a given entity class name.
     *
     * @return the metadata or null if the class is not an entity class or does
     * not exist.
     */
    public abstract EntityMetadata getEntityMetadata(String className);

    /**
     * Returns the names of all known persistent classes.  A type becomes known
     * when an instance of the type is stored for the first time or metadata or
     * type information is queried for a specific class name.
     *
     * @return an unmodifiable set of class names.
     *
     * @throws IllegalStateException if this method is called for a model that
     * is not associated with an open store.
     */
    public abstract Set<String> getKnownClasses();

    /**
     * Returns the type information for the current version of a given class,
     * or null if the class is not persistent.
     *
     * @throws IllegalStateException if this method is called for a model that
     * is not associated with an open store.
     */
    public final RawType getRawType(String className) {
        if (catalog != null) {
            return catalog.getFormat(className);
        } else {
            throw new IllegalStateException("Store is not open");
        }
    }

    /**
     * Returns all known versions of type information for a given class name,
     * or null if the class is not persistent.
     *
     * @return an unmodifiable map of version number to <code>RawType</code>.
     *
     * @throws IllegalStateException if this method is called for a model that
     * is not associated with an open store.
     */
    public final SortedMap<Integer,RawType> getAllRawTypes(String className) {
        if (catalog != null) {
            RawType type = catalog.getFormat(className);
            if (type != null) {
                /* XXX type evolution */
                SortedMap<Integer,RawType> map =
                    new TreeMap<Integer,RawType>();
                map.put(Integer.valueOf(0), type);
                return Collections.unmodifiableSortedMap(map);
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("Store is not open");
        }
    }
}
