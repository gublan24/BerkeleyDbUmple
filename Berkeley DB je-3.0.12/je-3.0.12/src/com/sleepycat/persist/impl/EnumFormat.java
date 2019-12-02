/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: EnumFormat.java,v 1.13 2006/05/09 05:46:58 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sleepycat.persist.raw.RawObject;

/**
 * Format for all enum types.
 *
 * In this class we resort to using reflection to allocate arrays of enums.
 * If there is a need for it, reflection could be avoided in the future by
 * generating code as new array formats are encountered.
 *
 * @author Mark Hayes
 */
public class EnumFormat extends Format {

    private static final long serialVersionUID = 1069833955604373538L;

    private String[] names;
    private transient Object[] values;

    EnumFormat(Class type) {
        super(type);
        values = type.getEnumConstants();
        names = new String[values.length];
        for (int i = 0; i < names.length; i += 1) {
            names[i] = ((Enum) values[i]).name();
        }
    }

    @Override
    public boolean isEnum() {
        return true;
    }

    @Override
    public List<String> getEnumConstants() {
        return Arrays.asList(names);
    }

    @Override
    void collectRelatedFormats(Catalog catalog,
                               Map<String,Format> newFormats) {
    }

    @Override
    void initialize(Catalog catalog) {
        if (values == null) {
            Class cls = getType();
            values = new Object[names.length];
            for (int i = 0; i < names.length; i += 1) {
                values[i] = Enum.valueOf(cls, names[i]);
            }
        }
    }
    
    @Override
    Object newArray(int len) {
        return Array.newInstance(getType(), len);
    }

    @Override
    Object newInstance(EntityInput input, boolean rawAccess) {
        int index = input.readPackedInt();
        if (rawAccess) {
            return new RawObject(this, names[index]);
        } else {
            return Enum.valueOf(getType(), names[index]);
        }
    }

    @Override
    void writeObject(Object o, EntityOutput output, boolean rawAccess) {
        if (rawAccess) {
            String name = ((RawObject) o).getEnum();
            for (int i = 0; i < names.length; i += 1) {
                if (names[i].equals(name)) {
                    output.writePackedInt(i);
                    return;
                }
            }
        } else {
            for (int i = 0; i < values.length; i += 1) {
                if (o == values[i]) {
                    output.writePackedInt(i);
                    return;
                }
            }
        }
        throw new IllegalStateException("Bad enum: " + o);
    }

    @Override
    void readObject(Object o, EntityInput input, boolean rawAccess) {
        /* newInstance reads the value -- do nothing here. */
    }

    @Override
    void skipContents(EntityInput input) {
        input.skipFast(input.getPackedIntByteLength());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof EnumFormat) {
            EnumFormat o = (EnumFormat) other;
            return super.equals(o) &&
                   Arrays.equals(names, o.names);
        } else {
            return false;
        }
    }
}
