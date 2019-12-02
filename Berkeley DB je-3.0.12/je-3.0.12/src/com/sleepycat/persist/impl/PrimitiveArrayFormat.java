/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: PrimitiveArrayFormat.java,v 1.15 2006/05/09 05:46:58 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.persist.raw.RawObject;
import com.sleepycat.persist.raw.RawType;

/**
 * An array of primitives having one dimension.  Multidimensional arrays are
 * handled by {@link ObjectArrayFormat}.
 *
 * @author Mark Hayes
 */
public class PrimitiveArrayFormat extends Format {

    private static final long serialVersionUID = 8285299924106073591L;

    private SimpleFormat componentFormat;

    PrimitiveArrayFormat(Class type) {
        super(type);
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public int getDimensions() {
        return 1;
    }

    @Override
    public Format getComponentType() {
        return componentFormat;
    }

    @Override
    void collectRelatedFormats(Catalog catalog,
                               Map<String,Format> newFormats) {
        /* Simple type formats are predefined. */
        componentFormat = (SimpleFormat)
            catalog.getFormat(getType().getComponentType());
    }

    @Override
    void initialize(Catalog catalog) {
    }

    @Override
    Object newArray(int len) {
        return Array.newInstance(getType(), len);
    }

    @Override
    Object newInstance(EntityInput input, boolean rawAccess) {
        int len = input.readPackedInt();
        if (rawAccess) {
            return new RawObject(this, new Object[len]);
        } else {
            return componentFormat.newPrimitiveArray(len, input);
        }
    }

    @Override
    void writeObject(Object o, EntityOutput output, boolean rawAccess) {
        if (rawAccess) {
            Object[] a = ((RawObject) o).getElements();
            output.writePackedInt(a.length);
            for (int i = 0; i < a.length; i += 1) {
                componentFormat.writeObject(a[i], output, true);
            }
        } else {
            componentFormat.writePrimitiveArray(o, output);
        }
    }

    @Override
    void readObject(Object o, EntityInput input, boolean rawAccess) {
        if (rawAccess) {
            Object[] a = ((RawObject) o).getElements();
            for (int i = 0; i < a.length; i += 1) {
                a[i] = componentFormat.newInstance(input, true);
                componentFormat.readObject(a[i], input, true);
            }
        }
        /* Else, do nothing -- newInstance reads the value. */
    }

    @Override
    void skipContents(EntityInput input) {
        int len = input.readPackedInt();
        componentFormat.skipPrimitiveArray(len, input);
    }

    @Override
    void copySecMultiKey(EntityInput input, Format keyFormat, Set results) {
        int len = input.readPackedInt();
        componentFormat.copySecMultiKeyPrimitiveArray(len, input, results);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PrimitiveArrayFormat) {
            PrimitiveArrayFormat o = (PrimitiveArrayFormat) other;

            /*
             * The dimensions and component format are part of the class name,
             * which is compared by super.equals().
             */
            return super.equals(o);
        } else {
            return false;
        }
    }
}
