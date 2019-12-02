/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ConversionStore.java,v 1.5 2006/04/28 18:42:54 mark Exp $
 */

package com.sleepycat.persist.evolve;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.sleepycat.persist.model.EntityModel;
import com.sleepycat.persist.raw.RawObject;
import com.sleepycat.persist.raw.RawStore;

/**
 * Provides access to incompatible raw data in a store for performing manual
 * conversions.  When opening a conversion store, mutations need not be
 * configured for handling class incompatibilities and {@link
 * IncompatibleClassException} will not be thrown.
 *
 * <p>Manual conversions are needed when {@link Mutations} are insufficient for
 * making complex changes to a class hierarchy or entity model.  When using an
 * conversion store, an entity may be read even if its stored class definition
 * is incompatible with the current class definition or if the old class is no
 * longer present.  Entities may be arbitrarily manipulated, converted to be
 * compatible with current class definitions, and written back to the
 * store.</p>
 *
 * <p>Note that entities with incompatible classes may be read but not written.
 * Entities written must always conform to the current class definitions.</p>
 *
 * <p>After a conversion, in order to open the store as an {@link EntityStore}
 * or {@link RawStore} without causing an {@link IncompatibleClassException},
 * the {@link #deleteUnusedType deleteUnusedType} method should be called for
 * every incompatible class.  This method should be called after converting or
 * deleting all instances of the incompatible class.</p>
 *
 * <p>The {@link #deleteUnusedType deleteUnusedType} method does not verify
 * that no instances of the given type exist, since that would require
 * traversing one or more primary indices.  Instead, the caller is required to
 * have traversed all applicable indices and converted all instance of the
 * type.  If an instance of the type still exists and is later accessed via an
 * {@link EntityStore} or {@link RawStore}, a {@link DeletedClassException}
 * will be thrown.</p>
 *
 * <p>A entity store does not keep track of how many instances exist for a
 * given version of a class.  The only way to find old versions is to iterate
 * through all primary indices that may contain incompatible classes and check
 * the types and versions.  The type of a stored object is returned by {@link
 * RawObject#getType}; this will either be equal to the current type (returned
 * by {@link EntityModel#getRawType EntityModel.getRawType}) or the previously
 * stored type (returned by {@link EntityModel#getAllRawTypes
 * EntityModel.getAllRawTypes}).  Types may be compared using the
 * <code>equals</code> method, or the version number may be checked.</p>
 *
 * <p><strong>Using a Temporary Index</strong></p>
 *
 * <p>Conversion via a temporary index is required for performing a key
 * conversion, since key formats may not be changed.  It may also be used
 * to avoid making a backup of the environment before performing a
 * conversion, since the original index is not changed until the temporary
 * index is installed, and the temporary index may be installed safely using a
 * transaction.</p>
 *
 * <p>For example:</p>
 * <pre class="code">
 * // The class name and version of the entity instances to be converted.
 * //
 * String className = ...;
 * int classVersion = ...;
 *
 * // Open the conversion store, the current index and a temporary index.
 * //
 * Environment env = ...;
 * ConversionStore store = new ConversionStore(env, "myStore", null);
 * {@literal PrimaryIndex<Object,RawObject> oldIndex = store.getPrimaryIndex(className);}
 * {@literal PrimaryIndex<Object,RawObject> tempIndex = store.getTempIndex(className);}
 *
 * // Copy the instances from oldIndex to tempIndex, converting all instances
 * // to conform to current class definitions.  (See {@link Converter} for
 * // examples of conversion techniques.)
 * //
 * ...
 *
 * // Using a single transaction, atomically install the new index and delete
 * // the (now unused) type of the old instances.
 * //
 * Transaction txn = env.beginTransaction(null, null);
 * store.installTempIndex(txn, tempIndex);
 * store.deleteUnusedType(txn, className, classVersion);
 * txn.commit();</pre>
 *
 * <p>Using this approach, complex conversions can be performed using any
 * number of indices at once, and a single transaction can be used to
 * atomically commit the changes.</p>
 *
 * <p>Note that an entire index conversion could be performed in a single
 * transaction. However, for large indices this would use unacceptable amounts
 * of memory to hold transactional locks.  Therefore, the recommended approach
 * for converting a large index is to copy it to a temporary index, without
 * transactions, and then use a transaction to install the index and delete the
 * unused types.</p>
 *
 * @author Mark Hayes
 */
public class ConversionStore extends RawStore {

    /**
     * Opens an entity store for unchecked raw data access.
     *
     * @param env an open Berkeley DB environment.
     *
     * @param storeName the name of the entity store within the given
     * environment.
     *
     * @param config the store configuration, or null to use default
     * configuration properties.
     *
     * @throws IllegalArgumentException if the <code>Environment</code> is
     * read-only or the <code>config ReadOnly</code> property is true.
     */
    public ConversionStore(Environment env,
                           String storeName,
                           StoreConfig config)
        throws DatabaseException {
        super(env, storeName, config);
    }

    /**
     * Opens a new temporary primary index for copying new data to a new index
     * rather than updating an index in place.  The temporary index will be
     * initially empty.
     *
     * <p>Entities may be copied from the current primary index to the
     * temporary primary index, changing the key format in the process if
     * desired.  When conversion is completed successfully, {@link
     * #installTempIndex} should be called.  If this conversion store is closed
     * without calling {@link #installTempIndex}, the temporary index will be
     * deleted.  If a conversion store is opened and any residual temporary
     * indices exist, they will be deleted.</p>
     *
     * <p>Conversion via a temporary index is required for performing a key
     * conversion, since key formats may not be changed.  It may also be used
     * to avoid making a backup of the environment before performing a
     * conversion, since the original index is not changed until the temporary
     * index is installed, and the temporary index may be installed safely
     * using a transaction.</p>
     */
    public PrimaryIndex<Object,RawObject> createTempIndex(String entityClass)
        throws DatabaseException {
        return null;
    }

    /**
     * Installs a temporary primary index as the current index.  The current
     * index is removed and the temporary index is renamed to the current
     * index.
     */
    public void installTempIndex(Transaction txn,
                                 PrimaryIndex<Object,RawObject> tempIndex)
        throws DatabaseException {
    }

    /**
     * Removes a type for which all instances have been converted.
     *
     * <p>When performing a conversion, instances of an incompatible class
     * version may be converted to a different type or deleted entirely.  This
     * method is called to inform the store that such a type has no instances
     * and no longer requires conversion.  In addition to freeing the space
     * taken by the unused type definition, deleting an incompatible type
     * allows opening an {@link EntityStore} (or {@link RawStore}) without
     * causing an {@link IncompatibleClassException}.</p>
     *
     * <p>This method does not verify that no instances of the given type
     * exist, since that would require traversing one or more primary indices.
     * Instead, the caller is required to have traversed all applicable indices
     * and converted all instance of the type.  If an instance of the type
     * still exists and is later accessed via an {@link EntityStore} or {@link
     * RawStore}, a {@link DeletedClassException} will be thrown.</p>
     */
    public void deleteUnusedType(Transaction txn,
                                 String className,
                                 int classVersion) {
    }
}
