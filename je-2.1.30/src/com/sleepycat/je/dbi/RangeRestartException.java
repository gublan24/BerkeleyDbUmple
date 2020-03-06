/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: RangeRestartException.java,v 1.3 2006/01/03 21:55:41 bostic Exp $
 */

package com.sleepycat.je.dbi;

import com.sleepycat.je.DatabaseException;

/**
 * Thrown by the LockManager when requesting a RANGE_READ or RANGE_WRITE
 * lock, and a RANGE_INSERT lock is held or is waiting.  This exception is
 * caught by read operations and causes a restart of the operation.  It should
 * never be seen by the user.
 */
public class RangeRestartException extends DatabaseException {

    public RangeRestartException() {
        super();
    }
}
