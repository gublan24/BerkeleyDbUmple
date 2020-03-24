/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: TreeStats.java,v 1.10 2006/01/03 21:55:58 bostic Exp $
 */

package com.sleepycat.je.tree;

/**
 * A class that provides interesting stats about a particular tree.
 */
public final class TreeStats {

    /**
     * Number of times the root was split.
     */
    public int nRootSplits = 0;
}
