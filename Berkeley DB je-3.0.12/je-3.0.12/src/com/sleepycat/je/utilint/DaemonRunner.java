/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: DaemonRunner.java,v 1.3 2006/01/03 21:56:02 bostic Exp $
 */

package com.sleepycat.je.utilint;


/**
 * An object capable of running (run/pause/shutdown/etc) a daemon thread.
 * See DaemonThread for details.
 */
public interface DaemonRunner {
    void runOrPause(boolean run);
    void requestShutdown();
    void shutdown();
    int getNWakeupRequests();
}
