/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: LatchNotHeldException.java,v 1.13 2006/01/03 21:55:47 bostic Exp $
 */

package com.sleepycat.je.latch;

/**
 * An exception that is thrown when a latch is not held but a method is invoked
 * on it that assumes it is held.
 */
public class LatchNotHeldException extends LatchException {
    public LatchNotHeldException() {
	super();
    }

    public LatchNotHeldException(String message) {
	super(message);
    }
}
