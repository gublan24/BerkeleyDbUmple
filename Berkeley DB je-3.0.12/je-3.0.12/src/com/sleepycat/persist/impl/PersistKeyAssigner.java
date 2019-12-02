/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: PersistKeyAssigner.java,v 1.8 2006/05/24 02:21:23 mark Exp $
 */

package com.sleepycat.persist.impl;

import com.sleepycat.bind.tuple.TupleBase;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Sequence;

/**
 * Assigns primary keys from a Sequence.
 *
 * This class is used directly by PrimaryIndex, not via an interface.  To avoid
 * making a public interface, the PersistEntityBinding contains a reference to
 * a PersistKeyAssigner, and the PrimaryIndex gets the key assigner from the
 * binding.  See the PrimaryIndex constructor for more information.
 *
 * @author Mark Hayes
 */
public class PersistKeyAssigner {

    private Catalog catalog;
    private Format keyFormat;
    private Format entityFormat;
    private boolean rawAccess;
    private Sequence sequence;

    PersistKeyAssigner(PersistKeyBinding keyBinding,
                       PersistEntityBinding entityBinding,
                       Sequence sequence) {
        catalog = keyBinding.catalog;
        keyFormat = keyBinding.keyFormat;
        entityFormat = entityBinding.entityFormat;
        rawAccess = entityBinding.rawAccess;
        this.sequence = sequence;
    }

    public boolean assignPrimaryKey(Object entity, DatabaseEntry key)
        throws DatabaseException {
            
        if (entityFormat.isPriKeyNullOrZero(entity, rawAccess)) {
            Long value = sequence.get(null, 1);
            EntityOutput output = new EntityOutput(catalog, rawAccess);
            keyFormat.writeObject(value, output, rawAccess);
            TupleBase.outputToEntry(output, key);
            EntityInput input = new EntityInput
                (catalog, rawAccess, null, 0,
                 key.getData(), key.getOffset(), key.getSize());
            entityFormat.readPriKey(entity, input, rawAccess);
            return true;
        } else {
            return false;
        }
    }
}
