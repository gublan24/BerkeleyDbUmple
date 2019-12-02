/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: Mutation.java,v 1.2 2006/04/09 16:39:28 mark Exp $
 */

package com.sleepycat.persist.evolve;

import java.io.Serializable;

/**
 * The base class for all mutations.
 *
 * @author Mark Hayes
 */
public abstract class Mutation implements Serializable {

    Mutation(String className, int classVersion, String fieldName) {
    }

    /**
     * Returns the class to which this mutation applies.
     */
    public String getClassName() {
        return null;
    }

    /**
     * Returns the class version to which this mutation applies.
     */
    public int getClassVersion() {
        return 0;
    }

    /**
     * Returns the field name to which this mutation applies, or null if this
     * mutation applies to the class itself.
     */
    public String getFieldName() {
        return null;
    }
}
