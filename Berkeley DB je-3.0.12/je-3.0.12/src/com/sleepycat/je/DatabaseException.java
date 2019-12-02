/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: DatabaseException.java,v 1.19 2006/05/09 15:11:10 linda Exp $
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
        super(getVersionHeader() + message);
    }

    public DatabaseException(String message, Throwable t) {
        super((getVersionHeader() + message), t);
    }

    /* Public for unit tests. */
    public static String getVersionHeader() {
        return "(JE " + JEVersion.CURRENT_VERSION + ") ";
    }
}
