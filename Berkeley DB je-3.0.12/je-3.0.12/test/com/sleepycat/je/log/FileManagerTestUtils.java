/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: FileManagerTestUtils.java,v 1.4 2006/01/03 21:56:21 bostic Exp $
 */

package com.sleepycat.je.log;

import java.io.IOException;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.dbi.EnvironmentImpl;

public class FileManagerTestUtils {
    public static void createLogFile(FileManager fileManager,
    	                             EnvironmentImpl envImpl,
    	                             long logFileSize)
        throws DatabaseException, IOException {

        LogBuffer logBuffer = new LogBuffer(50, envImpl);
        logBuffer.getDataBuffer().flip();
        fileManager.bumpLsn(logFileSize - FileManager.firstLogEntryOffset());
        logBuffer.registerLsn(fileManager.getLastUsedLsn());
        fileManager.writeLogBuffer(logBuffer);
        fileManager.syncLogEndAndFinishFile();
    }
}

