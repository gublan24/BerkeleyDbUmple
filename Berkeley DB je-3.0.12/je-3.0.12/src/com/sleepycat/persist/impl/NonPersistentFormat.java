/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: NonPersistentFormat.java,v 1.7 2006/05/09 05:46:58 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * Format for a non-persistent class that is only used for declared field
 * types and arrays.  Currently used only for Object and interface types.
 *
 * @author Mark Hayes
 */
class NonPersistentFormat extends Format {

    private static final long serialVersionUID = -7488355830875148784L;

    NonPersistentFormat(Class type) {
        super(type);
    }

    @Override
    void initialize(Catalog catalog) {
    }

    @Override
    void collectRelatedFormats(Catalog catalog,
                               Map<String,Format> newFormats) {
    }

    @Override
    Object newArray(int len) {
        return Array.newInstance(getType(), len);
    }

    @Override
    Object newInstance(EntityInput input, boolean rawAccess) {
        throw new UnsupportedOperationException
            ("Cannot instantiate non-persistent class: " + getClassName());
    }

    @Override
    void writeObject(Object o, EntityOutput output, boolean rawAccess) {
        throw new UnsupportedOperationException();
    }

    @Override
    void readObject(Object o, EntityInput input, boolean rawAccess) {
        throw new UnsupportedOperationException();
    }

    @Override
    void skipContents(EntityInput input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NonPersistentFormat) {
            NonPersistentFormat o = (NonPersistentFormat) other;
            return super.equals(o);
        } else {
            return false;
        }
    }
}
