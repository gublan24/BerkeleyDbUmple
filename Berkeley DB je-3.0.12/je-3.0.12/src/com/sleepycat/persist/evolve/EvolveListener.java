/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: EvolveListener.java,v 1.2 2006/04/09 16:39:28 mark Exp $
 */

package com.sleepycat.persist.evolve;

/** 
 * The listener interface called during eager entity evolution.
 *
 * @author Mark Hayes
 */
public interface EvolveListener {

    /** 
     * The listener method called during eager entity evolution.
     *
     * @return true to continue evolution or false to stop.
     */
    boolean onEvolveProgress(EvolveStats stats);
}
