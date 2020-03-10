/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: DatabaseException.java,v 1.17 2006/01/03 21:55:36 bostic Exp $
 */

package com.sleepycat.je;

/**
 * Javadoc for this public class is generated
 * via the doc templates in the doc_src directory.
 */
public class DatabaseException extends Exception {
    public DatabaseException() {
        super();
    }

    public DatabaseException(Throwable t) {
        super(t);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable t) {
        super(message, t);
    }
}