/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2000-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: KeyRangeException.java,v 1.15 2006/01/03 21:55:29 bostic Exp $
 */

package com.sleepycat.collections;

/**
 * An exception thrown when a key is out of range.
 *
 * @author Mark Hayes
 */
class KeyRangeException extends IllegalArgumentException {

    /**
     * Creates a key range exception.
     */
    public KeyRangeException(String msg) {

        super(msg);
    }
}
