/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: HexFormatterTest.java,v 1.10 2006/01/03 21:56:36 bostic Exp $
 */

package com.sleepycat.je.util;

import junit.framework.TestCase;

import com.sleepycat.je.utilint.HexFormatter;

/**
 * Trivial formatting class that sticks leading 0's on the front of a hex
 * number.
 */
public class HexFormatterTest extends TestCase {
    public void testFormatLong() {
	assertTrue(HexFormatter.formatLong(0).equals("0x0000000000000000"));
	assertTrue(HexFormatter.formatLong(1).equals("0x0000000000000001"));
	assertTrue(HexFormatter.formatLong(0x1234567890ABCDEFL).equals("0x1234567890abcdef"));
	assertTrue(HexFormatter.formatLong(0x1234567890L).equals("0x0000001234567890"));
	assertTrue(HexFormatter.formatLong(0xffffffffffffffffL).equals("0xffffffffffffffff"));
    }
}
