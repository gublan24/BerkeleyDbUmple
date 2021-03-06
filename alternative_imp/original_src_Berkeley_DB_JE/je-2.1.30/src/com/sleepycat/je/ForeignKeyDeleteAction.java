/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ForeignKeyDeleteAction.java,v 1.4 2006/01/03 21:55:37 bostic Exp $
 */

package com.sleepycat.je;

/**
 * Javadoc for this public class is generated via
 * the doc templates in the doc_src directory.
 */
public class ForeignKeyDeleteAction {

    private String name;

    private ForeignKeyDeleteAction(String name) {
	this.name = name;
    }

    /**
     * Javadoc for this public class is generated via
     * the doc templates in the doc_src directory.
     */
    public final static ForeignKeyDeleteAction ABORT =
                    new ForeignKeyDeleteAction("ABORT");

    /**
     * Javadoc for this public class is generated via
     * the doc templates in the doc_src directory.
     */
    public final static ForeignKeyDeleteAction CASCADE =
                    new ForeignKeyDeleteAction("CASCADE");

    /**
     * Javadoc for this public class is generated via
     * the doc templates in the doc_src directory.
     */
    public final static ForeignKeyDeleteAction NULLIFY =
                    new ForeignKeyDeleteAction("NULLIFY");

    public String toString() {
	return "ForeignKeyDeleteAction." + name;
    }
}
