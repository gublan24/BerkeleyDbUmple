/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: EntityInput.java,v 1.10 2006/05/24 02:21:22 mark Exp $
 */

package com.sleepycat.persist.impl;

import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.util.PackedInteger;

/**
 * Used for reading object fields.
 *
 * <p>Although this class extends TupleInput, not all TupleInput methods should
 * be called.  See Format for which methods should be called.  In
 * particular, Strings should be passed to {@link #readObject} in this
 * class.</p>
 *
 * @author Mark Hayes
 */
public class EntityInput extends TupleInput {

    private Catalog catalog;
    boolean rawAccess;
    private VisitedObjects visited;
    private DatabaseEntry priKeyEntry;
    private int priKeyFormatId;

    /**
     * Creates a new input with a empty/null VisitedObjects set.
     */
    EntityInput(Catalog catalog,
                boolean rawAccess,
                DatabaseEntry priKeyEntry,
                int priKeyFormatId,
                byte[] buffer,
                int offset,
                int length) {
        super(buffer, offset, length);
        this.catalog = catalog;
        this.rawAccess = rawAccess;
        this.priKeyEntry = priKeyEntry;
        this.priKeyFormatId = priKeyFormatId;
    }

    /**
     * Copy contructor where a new offset can be specified.
     */
    private EntityInput(EntityInput other, int offset) {
        this(other.catalog, other.rawAccess, other.priKeyEntry,
             other.priKeyFormatId, other.buf, offset, other.len);
        visited = other.visited;
    }

    /**
     * Copy contructor where a DatabaseEntry can be specified.
     */
    private EntityInput(EntityInput other, DatabaseEntry entry) {
        this(other.catalog, other.rawAccess, other.priKeyEntry,
             other.priKeyFormatId, entry.getData(), entry.getOffset(),
             entry.getSize());
        visited = other.visited;
    }

    /**
     * Get the catalog for this input.
     */
    Catalog getCatalog() {
        return catalog;
    }

    /**
     * Called via Accessor to read all fields with reference types, except for
     * the primary key field and composite key fields (see readKeyObject
     * below).
     */
    public Object readObject() {

        /* Save the current offset before reading the format ID. */
        int visitedOffset = off;
        EntityInput useInput = this;
        int formatId = readPackedInt();
        Object o = null;

        /* For a zero format ID, return a null instance. */
        if (formatId == Format.ID_NULL) {
            return null;
        }

        /* For a negative format ID, lookup an already visited instance. */
        if (formatId < 0) {
            int offset = (-(formatId + 1));
            if (visited != null) {
                o = visited.getObject(offset);
            }
            if (o != null) {
                /* Return a previously visited object. */
                return o;
            } else {

                /*
                 * When reading starts from a non-zero offset, we may have to
                 * go back in the stream and read the referenced object.  This
                 * happens when reading secondary key fields.
                 */
                visitedOffset = offset;
                if (offset == EntityOutput.PRI_KEY_VISITED_OFFSET) {
                    assert priKeyEntry != null && priKeyFormatId > 0;
                    useInput = new EntityInput(this, priKeyEntry);
                    formatId = priKeyFormatId;
                } else {
                    useInput = new EntityInput(this, offset);
                    formatId = useInput.readPackedInt();
                }
            }
        }

        /* Create the object using the format indicated. */
        Format format = catalog.getFormat(formatId);
        o = format.newInstance(useInput, rawAccess);

        /*
         * Add newly created object to the set of visited objects.  This must
         * be done before calling Format.readObject, in case the object
         * contains a reference to itself.
         */
        if (visited == null) {
            visited = new VisitedObjects();
        }
        visited.add(o, visitedOffset);

        /* Finish reading the object and return it. */
        format.readObject(o, useInput, rawAccess);
        return o;
    }

    /**
     * Called for a primary key field or a composite key field with a reference
     * type.
     *
     * <p>For such key fields, no formatId is present nor can the object
     * already be present in the visited object set.</p>
     */
    public Object readKeyObject(Format format) {

        /* Create and read the object using the given key format. */
        Object o = format.newInstance(this, rawAccess);
        format.readObject(o, this, rawAccess);
        return o;
    }

    /**
     * Called when copying secondary keys, for an input that is positioned on
     * the secondary key field.  Handles references to previously occurring
     * objects, returning a different EntityInput than this one if appropriate.
     */
    KeyLocation getKeyLocation(Format fieldFormat) {
        EntityInput input = this;
        if (!fieldFormat.isPrimitive()) {
            int formatId = input.readPackedInt();
            if (formatId == Format.ID_NULL) {
                /* Key field is null. */
                return null;
            }
            if (formatId < 0) {
                int offset = (-(formatId + 1));
                if (offset == EntityOutput.PRI_KEY_VISITED_OFFSET) {
                    assert priKeyEntry != null && priKeyFormatId > 0;
                    input = new EntityInput(this, priKeyEntry);
                    formatId = priKeyFormatId;
                } else {
                    input = new EntityInput(this, offset);
                    formatId = input.readPackedInt();
                }
            }
            fieldFormat = catalog.getFormat(formatId);
        }
        /* Key field is non-null. */
        return new KeyLocation(input, fieldFormat);
    }

    /**
     * Called via Accessor.readSecKeyFields for a primary key field with a
     * reference type.  This method must be called before reading any other
     * fields.
     */
    public void registerPriKeyObject(Object o) {

        /*
         * PRI_KEY_VISITED_OFFSET is used as the visited offset to indicate
         * that the visited object is stored in the primary key byte array.
         */
        if (visited == null) {
            visited = new VisitedObjects();
        }
        visited.add(o, EntityOutput.PRI_KEY_VISITED_OFFSET);
    }

    /**
     * Called via PersistKeyCreator to skip fields prior to the secondary key
     * field.
     */
    void skipField(Format declaredFormat) {
        if (declaredFormat != null && declaredFormat.isPrimitive()) {
            declaredFormat.skipContents(this);
        } else {
            int formatId = readPackedInt();
            if (formatId > 0) {
                Format format = catalog.getFormat(formatId);
                format.skipContents(this);
            }
        }
    }
}
