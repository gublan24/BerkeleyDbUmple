/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: BitMapTest.java,v 1.2 2006/01/03 21:56:36 bostic Exp $
 */

package com.sleepycat.je.utilint;

import junit.framework.TestCase;


public class BitMapTest extends TestCase {

    public void testSegments() {
        
        BitMap bmap = new BitMap();
        int startBit = 15;
        int endBit = 62;
        assertEquals(0, bmap.cardinality());
        assertEquals(0, bmap.getNumSegments());

        assertFalse(bmap.get(1001L));
        assertEquals(0, bmap.getNumSegments());

        /* set a bit in different segments. */
        for (int i = startBit; i <= endBit; i++) {
            long index = 1L << i;
            index += 17;
            bmap.set(index);
        }

        assertEquals((endBit - startBit +1), bmap.cardinality());
        assertEquals((endBit - startBit + 1), bmap.getNumSegments());
        
        /* should be set. */
        for (int i = startBit; i <= endBit; i++) {
            long index = 1L << i;
            index += 17;
            assertTrue(bmap.get(index));
        }

        /* should be clear. */
        for (int i = startBit; i <= endBit; i++) {
            long index = 7 + (1L << i);
            assertFalse(bmap.get(index));
        }

        /* checking for non-set bits should not create more segments. */
        assertEquals((endBit - startBit +1), bmap.cardinality());
        assertEquals((endBit - startBit + 1), bmap.getNumSegments());
    }

    public void testNegative() {
        BitMap bMap = new BitMap();
        
        try {
            bMap.set(-300);
            fail("should have thrown exception");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
