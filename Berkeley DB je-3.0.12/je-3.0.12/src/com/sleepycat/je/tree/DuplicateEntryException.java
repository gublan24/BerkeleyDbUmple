/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: DuplicateEntryException.java,v 1.11 2006/01/03 21:55:56 bostic Exp $
 */

package com.sleepycat.je.tree;

import com.sleepycat.je.DatabaseException;

/**
 * Exception to indicate that an entry is already present in a node.
 */
public class DuplicateEntryException extends DatabaseException {
    public DuplicateEntryException() {
	super();
    }

    public DuplicateEntryException(String message) {
	super(message);
    }
}
