/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ReadOnlyProcess.java,v 1.4 2006/01/03 21:56:12 bostic Exp $
 */

package com.sleepycat.je.cleaner;

import java.io.File;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.util.TestUtils;

/**
 * @see ReadOnlyLockerTest
 */
public class ReadOnlyProcess {

    public static void main(String[] args) {

        /*
         * Don't write to System.out in this process because the parent
         * process only reads System.err.
         */
        try {
            EnvironmentConfig envConfig = TestUtils.initEnvConfig();
            envConfig.setTransactional(true);
            envConfig.setReadOnly(true);

            File envHome = new File(System.getProperty(TestUtils.DEST_DIR));
            Environment env = new Environment(envHome, envConfig);

            //System.err.println("Opened read-only: " + envHome);
            //System.err.println(System.getProperty("java.class.path"));

            /* Sleep until the parent process kills me. */
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {

            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
