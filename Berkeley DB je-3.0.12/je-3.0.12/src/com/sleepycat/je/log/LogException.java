/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: LogException.java,v 1.12 2006/01/03 21:55:49 bostic Exp $
 */

package com.sleepycat.je.log;

import com.sleepycat.je.DatabaseException;

/**
 * Configuration related exceptions.
 */
public class LogException extends DatabaseException {
    public LogException(String message) {
	super(message);
    }

    public LogException(String message, Exception e) {
	super(message, e);
    }
}

