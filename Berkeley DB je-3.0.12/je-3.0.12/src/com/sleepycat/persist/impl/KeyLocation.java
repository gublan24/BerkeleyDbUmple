/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: KeyLocation.java,v 1.1 2006/05/24 02:21:22 mark Exp $
 */

package com.sleepycat.persist.impl;

/**
 * Holder for the input and format of a key.  Used when copying secondary keys.
 * Returned by EntityInput.getKeyLocation().
 *
 * @author Mark Hayes
 */
class KeyLocation {

    EntityInput input;
    Format format;

    KeyLocation(EntityInput input, Format format) {
        this.input = input;
        this.format = format;
    }
}
