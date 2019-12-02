/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ApiTest.java,v 1.13 2006/01/03 21:56:10 bostic Exp $
 */

package com.sleepycat.je;

import junit.framework.TestCase;


/**
 * Test parameter handling for api methods.
 */
public class ApiTest extends TestCase {
    
    public void testBasic()
        throws Exception {

        try {
            new Environment(null, null);
            fail("Should get exception");
        } catch (NullPointerException e) {
            // expected exception
        } catch (Exception e) {
            fail("Shouldn't get other exception");
        }

    }
}
