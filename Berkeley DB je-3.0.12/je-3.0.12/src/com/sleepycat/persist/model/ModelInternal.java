/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ModelInternal.java,v 1.2 2006/05/01 05:16:02 mark Exp $
 */

package com.sleepycat.persist.model;

import com.sleepycat.persist.impl.PersistCatalog;

/**
 * Internal access class that does not appear in the javadoc and should not be
 * used by applications.
 */
public class ModelInternal {

    public static void setCatalog(EntityModel model, PersistCatalog catalog) {
        model.setCatalog(catalog);
    }
}
