/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: LatchException.java,v 1.15 2006/01/03 21:55:47 bostic Exp $
 */

package com.sleepycat.je.latch;

import com.sleepycat.je.DatabaseException;

/**
 * The root of latch related exceptions.
 */

public class LatchException extends DatabaseException {

    public LatchException() {
	super();
    }

    public LatchException(String message) {
	super(message);
    }
}
