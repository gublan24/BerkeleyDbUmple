/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: LockGrantType.java,v 1.16 2006/01/03 21:55:59 bostic Exp $
 */

package com.sleepycat.je.txn;

/**
 * LockGrantType is an enumeration of the possible results of a lock attempt.
 */
public class LockGrantType {
    private String name;

    /* Grant types */
    public static final LockGrantType NEW =
	new LockGrantType("NEW");
    public static final LockGrantType WAIT_NEW =
	new LockGrantType("WAIT_NEW");
    public static final LockGrantType PROMOTION =
	new LockGrantType("PROMOTION");
    public static final LockGrantType WAIT_PROMOTION =
	new LockGrantType("WAIT_PROMOTION");
    public static final LockGrantType EXISTING =
	new LockGrantType("EXISTING");
    public static final LockGrantType DENIED =
	new LockGrantType("DENIED");
    public static final LockGrantType WAIT_RESTART =
	new LockGrantType("WAIT_RESTART");
    public static final LockGrantType NONE_NEEDED =
	new LockGrantType("NONE_NEEDED");

    /* No lock types can be defined outside this class */
    private LockGrantType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
