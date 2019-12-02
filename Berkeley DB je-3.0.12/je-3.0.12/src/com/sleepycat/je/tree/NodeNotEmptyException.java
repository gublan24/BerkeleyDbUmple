/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: NodeNotEmptyException.java,v 1.12 2006/01/09 12:43:36 cwl Exp $
 */

package com.sleepycat.je.tree;

/**
 * Error to indicate that a bottom level BIN is not empty during a
 * delete subtree operation.
 */
public class NodeNotEmptyException extends Exception {

    /*
     * Throw this static instance, in order to reduce the cost of
     * fill in the stack trace. 
     */
    public static final NodeNotEmptyException NODE_NOT_EMPTY =
        new NodeNotEmptyException();

    private NodeNotEmptyException() {
    }
}
