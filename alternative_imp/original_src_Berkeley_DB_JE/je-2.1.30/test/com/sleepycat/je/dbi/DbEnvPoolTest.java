/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2004-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: DbEnvPoolTest.java,v 1.3 2006/01/03 21:56:14 bostic Exp $
 */

package com.sleepycat.je.dbi;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.util.TestUtils;

public class DbEnvPoolTest extends TestCase {

    private static final boolean DEBUG = false;

    private File envHome = new File(System.getProperty(TestUtils.DEST_DIR));

    public DbEnvPoolTest() {
    }

    public void setUp()
        throws IOException {

        TestUtils.removeLogFiles("Setup", envHome, false);
    }
    
    public void tearDown()
        throws Exception {

	TestUtils.removeLogFiles("TearDown", envHome, false);
    }

    public void testCanonicalEnvironmentName ()
        throws Throwable {
        try {
            File file1 = new File(System.getProperty(TestUtils.DEST_DIR));
            File file2 = new File("build/test/classes");

            /* Create an environment. */
            EnvironmentConfig envConfig = TestUtils.initEnvConfig();
            envConfig.setAllowCreate(true);
            Environment envA = new Environment(envHome, envConfig);

            /* Look in the environment pool with the relative path name. */
            DbEnvPool.EnvironmentImplInfo info =
                DbEnvPool.getInstance().getEnvironment
                    (file2, TestUtils.initEnvConfig());
            /* We should find this file in the pool. */
            assertEquals(false, info.firstHandle);
            envA.close();

        } catch (Throwable t) {
            /* Dump stack trace before trying to tear down. */
            t.printStackTrace();
            throw t;
        }
    }
}
