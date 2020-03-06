/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: OperationStatus.java,v 1.8 2006/01/03 21:55:37 bostic Exp $
 */

package com.sleepycat.je;

/**
 * Javadoc for this public class is generated
 * via the doc templates in the doc_src directory.
 */
public class OperationStatus {
    /**
     * Javadoc for this public instance is generated via
     * the doc templates in the doc_src directory.
     */
    public static final OperationStatus SUCCESS =
	new OperationStatus("SUCCESS");

    /**
     * Javadoc for this public instance is generated via
     * the doc templates in the doc_src directory.
     */
    public static final OperationStatus KEYEXIST =
	new OperationStatus("KEYEXIST");

    /**
     * Javadoc for this public instance is generated via
     * the doc templates in the doc_src directory.
     */
    public static final OperationStatus KEYEMPTY =
	new OperationStatus("KEYEMPTY");

    /**
     * Javadoc for this public instance is generated via
     * the doc templates in the doc_src directory.
     */
    public static final OperationStatus NOTFOUND =
	new OperationStatus("NOTFOUND");

    /* For toString */
    private String statusName;

    private OperationStatus(String statusName) {
	this.statusName = statusName;
    }

    public String toString() {
	return "OperationStatus." + statusName;
    }
}
