/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *	Sleepycat Software.  All rights reserved.
 *
 * $Id: Enhanced0.java,v 1.1 2006/04/16 19:59:54 mark Exp $
 */

package com.sleepycat.persist.test;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;
import static com.sleepycat.persist.model.Relationship.*;

/**
 * For running ASMifier -- before any enhancements.
 */
@Entity
class Enhanced0 {

    @PrimaryKey
    private String f1;

    @SecondaryKey(relate=MANY_TO_ONE)
    private int f2;
    @SecondaryKey(relate=MANY_TO_ONE)
    private String f3;
    @SecondaryKey(relate=MANY_TO_ONE)
    private String f4;

    private int f5;
    private String f6;
    private String f7;
}
