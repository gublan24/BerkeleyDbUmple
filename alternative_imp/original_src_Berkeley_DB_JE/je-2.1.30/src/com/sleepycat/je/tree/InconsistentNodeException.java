/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: InconsistentNodeException.java,v 1.12 2006/01/03 21:55:56 bostic Exp $
 */

package com.sleepycat.je.tree;

import com.sleepycat.je.DatabaseException;

/**
 * Error to indicate that something is out of wack in the tree.
 */
public class InconsistentNodeException extends DatabaseException {
    public InconsistentNodeException() {
	super();
    }

    public InconsistentNodeException(String message) {
	super(message);
    }
}
