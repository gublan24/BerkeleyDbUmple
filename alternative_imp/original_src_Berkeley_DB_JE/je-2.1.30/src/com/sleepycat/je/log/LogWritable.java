/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: LogWritable.java,v 1.13 2006/01/03 21:55:49 bostic Exp $
 */

package com.sleepycat.je.log;

import java.nio.ByteBuffer;

/**
 * A class that implements LogWritable knows how to write itself into the JE
 * log.
 */
public interface LogWritable {

    /**
     * @return number of bytes used to store this object.
     */
    public int getLogSize();

    /**
     * Serialize this object into the buffer. 
     * @param logBuffer is the destination buffer
     */
    public void writeToLog(ByteBuffer logBuffer);
}
