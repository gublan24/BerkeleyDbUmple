/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: JEConnectionFactory.java,v 1.5 2006/01/03 21:55:47 bostic Exp $
 */

package com.sleepycat.je.jca.ra;

import java.io.Serializable;

import javax.resource.Referenceable;

import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.TransactionConfig;

public interface JEConnectionFactory
    extends Referenceable, Serializable {

    public JEConnection getConnection(String jeRootDir,
				      EnvironmentConfig envConfig)
	throws JEException;

    public JEConnection getConnection(String jeRootDir,
				      EnvironmentConfig envConfig,
				      TransactionConfig transConfig)
	throws JEException;
}
