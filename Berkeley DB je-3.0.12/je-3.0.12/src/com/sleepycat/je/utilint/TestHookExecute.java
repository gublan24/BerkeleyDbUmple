/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: TestHookExecute.java,v 1.2 2006/01/03 21:56:03 bostic Exp $
 */

package com.sleepycat.je.utilint;

/**
 */
public class TestHookExecute {
    public static boolean doHookIfSet(TestHook testHook) {
        if (testHook != null) {
            testHook.doHook();
        }
        return true;
    }
}
