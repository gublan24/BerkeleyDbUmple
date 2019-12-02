/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: Persistent.java,v 1.3 2006/04/30 04:57:09 mark Exp $
 */

package com.sleepycat.persist.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Identifies a persistent class that is not an {@link Entity} class or a
 * {@link <a href="Entity.html#simpleTypes">simple type</a>}.
 *
 * @author Mark Hayes
 */
@Documented @Retention(RUNTIME) @Target(TYPE)
public @interface Persistent {

    /**
     * Identifies a new version of a class when an incompatible class change
     * has been made.
     *
     * @see Entity#version
     */
    int version() default 0;

    /**
     * Specifies the class that is proxied by this {@link PersistentProxy}
     * instance.
     *
     * @see PersistentProxy
     */
    Class proxyFor() default void.class;
}
