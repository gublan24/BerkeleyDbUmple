/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: EvolveConfig.java,v 1.2 2006/04/09 16:39:28 mark Exp $
 */

package com.sleepycat.persist.evolve;

import java.util.Set;

import com.sleepycat.persist.EntityStore;

/**
 * Configuration properties for eager conversion of unevolved objects.  This
 * configuration is used with {@link EntityStore#evolve EntityStore.evolve}.
 *
 * @author Mark Hayes
 */
public class EvolveConfig implements Cloneable {

    /**
     * Creates an evolve configuration with default properties.
     */
    public EvolveConfig() {
    }

    /**
     * Returns a shallow copy of the configuration.
     */
    public EvolveConfig cloneConfig() {
        try {
            return (EvolveConfig) clone();
        } catch (CloneNotSupportedException cannotHappen) {
            return null;
        }
    }

    /**
     * Adds an entity class for a primary index to be converted.  If no classes
     * are added, all indexes that require evolution will be converted.
     */
    public void addClassToEvolve(String entityClass) {
    }

    /**
     * Returns an unmodifiable set of the entity classes to be evolved.
     */
    public Set<String> getClassesToEvolve() {
        return null;
    }

    /**
     * Sets a progress listener that is notified each time an entity is read.
     */
    public void setEvolveListener(EvolveListener listener) {
    }

    /**
     * Returns the progress listener that is notified each time an entity is
     * read.
     */
    public EvolveListener getEvolveListener() {
        return null;
    }
}
