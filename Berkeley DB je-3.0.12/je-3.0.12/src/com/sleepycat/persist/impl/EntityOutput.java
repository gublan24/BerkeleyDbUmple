/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: EntityOutput.java,v 1.9 2006/05/01 19:00:35 mark Exp $
 */

package com.sleepycat.persist.impl;

import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.persist.raw.RawObject;

/**
 * Used for writing object fields.
 *
 * <p>Although this class extends TupleOutput, not all TupleOutput methods
 * should be called.  See Format for which methods should be called.  In
 * particular, Strings should be passed to {@link #writeObject} in this
 * class.</p>
 *
 * @author Mark Hayes
 */
public class EntityOutput extends TupleOutput {

    static final int PRI_KEY_VISITED_OFFSET = Integer.MAX_VALUE - 1;

    private Catalog catalog;
    private boolean rawAccess;
    private VisitedObjects visited;

    /**
     * Creates a new output with an empty/null VisitedObjects set.
     */
    EntityOutput(Catalog catalog, boolean rawAccess) {

        super();
        this.catalog = catalog;
        this.rawAccess = rawAccess;
    }

    /**
     * Called via Accessor to write all fields with reference types, except for
     * the primary key field and composite key fields (see writeKeyObject
     * below).
     */
    public void writeObject(Object o, Format fieldFormat) {

        /* For a null instance, write a zero format ID. */
        if (o == null) {
            writePackedInt(Format.ID_NULL);
            return;
        }

        /*
         * For an already visited instance, output a reference to it.  The
         * reference is the negation of the visited offset minus one.
         */
        if (visited != null) {
            int offset = visited.getOffset(o);
            if (offset > 0) {
                writePackedInt(-(offset + 1));
                return;
            }
        }

        /*
         * Get and validate the format.  Catalog.getFormat(Class) throws
         * IllegalArgumentException if the class is not persistent.  We don't
         * need to check the fieldFormat (and it will be null) for non-raw
         * access because field type checking is enforced by Java.
         */
        Format format;
        if (rawAccess) {
            assert fieldFormat != null;
            if (o instanceof RawObject) {
                format = (Format) ((RawObject) o).getType();
            } else {
                format = catalog.getFormat(o.getClass());
                if (!format.isSimple() || format.isEnum()) {
                    throw new IllegalArgumentException
                        ("Not a RawObject or a non-enum simple type: " +
                         format.getClassName());
                }
            }
            if (!format.isAssignableTo(fieldFormat)) {
                throw new IllegalArgumentException
                    ("Not a subtype of " + fieldFormat.getClassName() +
                     ": " + format.getClassName());
            }
            Format proxiedFormat = format.getProxiedFormat();
            if (proxiedFormat != null) {
                format = proxiedFormat;
            }
        } else {
            format = catalog.getFormat(o.getClass());
        }
        if (format.getProxiedFormat() != null) {
            throw new IllegalArgumentException
                ("May not store proxy classes directly: " +
                 format.getClassName());
        }
        if (format.isEntity()) {
            throw new IllegalArgumentException
                ("References to entities are not allowed: " +
                 o.getClass().getName());
        }

        /* Remember that we visited this instance. */
        if (visited == null) {
            visited = new VisitedObjects();
        }
        visited.add(o, size());

        /* Finally, write the formatId and object value. */
        writePackedInt(format.getId());
        format.writeObject(o, this, rawAccess);
    }

    /**
     * Called for a primary key field or composite key field with a reference
     * type.
     */
    public void writeKeyObject(Object o, Format fieldFormat) {

        /* Key objects must not be null and must be of the declared class. */
        if (o == null) {
            throw new IllegalArgumentException
                ("Key field object may not be null");
        }
        Format format;
        if (rawAccess) {
            if (o instanceof RawObject) {
                format = (Format) ((RawObject) o).getType();
            } else {
                format = catalog.getFormat(o.getClass());
                /* Expect primitive wrapper class in raw mode. */
                if (fieldFormat.isPrimitive()) {
                    fieldFormat = fieldFormat.getWrapperFormat();
                }
            }
        } else {
            format = catalog.getFormat(o.getClass());
        }
        if (fieldFormat != format) {
            throw new IllegalArgumentException
                ("The key field object class (" + o.getClass().getName() +
                 ") must be the field's declared class: " +
                 fieldFormat.getClassName());
        }

        /* Write the object value (no formatId is written for keys). */
        fieldFormat.writeObject(o, this, rawAccess);
    }

    /**
     * Called via Accessor.writeSecKeyFields for a primary key field with a
     * reference type.  This method must be called before writing any other
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
        visited.add(o, PRI_KEY_VISITED_OFFSET);
    }
}
