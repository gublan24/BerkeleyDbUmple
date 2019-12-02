/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: DeletedClassException.java,v 1.2 2006/04/09 16:39:25 mark Exp $
 */

package com.sleepycat.persist.evolve;

import com.sleepycat.je.DatabaseException;

/**
 * While reading from an index, an instance of a deleted class version was
 * encountered.
 *
 * @see ConversionStore#deleteUnusedType ConversionStore.deleteUnusedType
 *
 * @author Mark Hayes
 */
public class DeletedClassException extends DatabaseException {
}
