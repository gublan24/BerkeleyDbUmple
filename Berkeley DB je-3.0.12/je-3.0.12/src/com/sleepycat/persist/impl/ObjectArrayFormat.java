/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ObjectArrayFormat.java,v 1.17 2006/05/24 02:21:22 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.persist.raw.RawObject;
import com.sleepycat.persist.raw.RawType;

/**
 * An array of objects having a specified number of dimensions.  All
 * multidimensional arrays are handled by this class, since even a primitive
 * array of more than one dimension is an array of objects, where the component
 * objects may be primitive arrays.  The {@link PrimitiveArrayFormat} class
 * handles primitive arrays of one dimension only.
 *
 * In this class, and {@link PrimitiveArrayFormat}, we resort to using
 * reflection to allocate multidimensional arrays.  If there is a need for it,
 * reflection could be avoided in the future by generating code as new array
 * formats are encountered.
 *
 * @author Mark Hayes
 */
public class ObjectArrayFormat extends Format {

    private static final long serialVersionUID = 4317004346690441892L;

    private Format componentFormat;
    private int nDimensions;

    ObjectArrayFormat(Class type) {
        super(type);
        String name = getClassName();
        for (nDimensions = 0;
             name.charAt(nDimensions) == '[';
             nDimensions += 1) {
        }
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public int getDimensions() {
        return nDimensions;
    }

    @Override
    public Format getComponentType() {
        return componentFormat;
    }

    @Override
    void collectRelatedFormats(Catalog catalog,
                               Map<String,Format> newFormats) {
        Class cls = getType().getComponentType();
        componentFormat = catalog.createFormat(cls, newFormats);
    }

    @Override
    void initialize(Catalog catalog) {
    }

    @Override
    boolean isAssignableTo(Format format) {
        if (super.isAssignableTo(format)) {
            return true;
        }
        if (format instanceof ObjectArrayFormat) {
            ObjectArrayFormat other = (ObjectArrayFormat) format;
            if (componentFormat.isAssignableTo(other.componentFormat)) {
                return true;
            }
        }
        return false;
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
            return componentFormat.newArray(len);
        }
    }

    @Override
    void writeObject(Object o, EntityOutput output, boolean rawAccess) {
        Object[] a;
        if (rawAccess) {
            a = ((RawObject) o).getElements();
        } else {
            a = (Object[]) o;
        }
        output.writePackedInt(a.length);
        for (int i = 0; i < a.length; i += 1) {
            output.writeObject(a[i], componentFormat);
        }
    }

    @Override
    void readObject(Object o, EntityInput input, boolean rawAccess) {
        Object[] a;
        if (rawAccess) {
            a = ((RawObject) o).getElements();
        } else {
            a = (Object[]) o;
        }
        for (int i = 0; i < a.length; i += 1) {
            a[i] = input.readObject();
        }
    }

    @Override
    void skipContents(EntityInput input) {
        int len = input.readPackedInt();
        for (int i = 0; i < len; i += 1) {
            input.skipField(componentFormat);
        }
    }

    @Override
    void copySecMultiKey(EntityInput input, Format keyFormat, Set results) {
        int len = input.readPackedInt();
        for (int i = 0; i < len; i += 1) {
            KeyLocation loc = input.getKeyLocation(componentFormat);
            if (loc == null) {
                throw new IllegalArgumentException
                    ("Secondary key values in array may not be null");
            }
            if (loc.format != componentFormat) {
                throw new IllegalStateException
                    (componentFormat.getClassName());
            }
            int off1 = loc.input.getBufferOffset();
            componentFormat.skipContents(loc.input);
            int off2 = loc.input.getBufferOffset();
            DatabaseEntry entry = new DatabaseEntry
                (loc.input.getBufferBytes(), off1, off2 - off1);
            results.add(entry);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ObjectArrayFormat) {
            ObjectArrayFormat o = (ObjectArrayFormat) other;

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
