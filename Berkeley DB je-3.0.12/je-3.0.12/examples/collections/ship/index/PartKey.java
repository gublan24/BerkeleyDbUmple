/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *	Sleepycat Software.  All rights reserved.
 *
 * $Id: PartKey.java,v 1.9 2006/01/03 21:55:17 bostic Exp $
 */

package collections.ship.index;

import java.io.Serializable;

/**
 * A PartKey serves as the key in the key/data pair for a part entity.
 *
 * <p> In this sample, PartKey is used both as the storage data for the key as
 * well as the object binding to the key.  Because it is used directly as
 * storage data using serial format, it must be Serializable. </p>
 *
 * @author Mark Hayes
 */
public class PartKey implements Serializable {

    private String number;

    public PartKey(String number) {

        this.number = number;
    }

    public final String getNumber() {

        return number;
    }

    public String toString() {

        return "[PartKey: number=" + number + ']';
    }
}
