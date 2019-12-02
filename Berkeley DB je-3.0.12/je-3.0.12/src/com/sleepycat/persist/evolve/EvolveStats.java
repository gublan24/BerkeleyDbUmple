/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: EvolveStats.java,v 1.2 2006/04/09 16:39:28 mark Exp $
 */

package com.sleepycat.persist.evolve;

/**
 * Statistics accumulated during eager entity evolution.
 *
 * @author Mark Hayes
 */
public class EvolveStats {

    EvolveStats() {}

    /**
     * The total number of entities read during eager evolution.
     */
    public int getNRead() {
        return 0;
    }

    /**
     * The total number of entities converted during eager evolution.
     */
    public int getNConverted() {
        return 0;
    }
}
