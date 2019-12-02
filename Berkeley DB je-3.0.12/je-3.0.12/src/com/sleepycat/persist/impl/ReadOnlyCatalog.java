/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ReadOnlyCatalog.java,v 1.4 2006/05/02 23:14:12 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Read-only catalog operations used when initializing new formats.  This
 * catalog is used temprarily when the main catalog has not been updated yet,
 * but the new formats need to do catalog lookups.
 *
 * @see PersistCatalog#addNewFormat
 *
 * @author Mark Hayes
 */
class ReadOnlyCatalog implements Catalog {

    private List<Format> formatList;
    private Map<String,Format> formatMap;

    ReadOnlyCatalog(List<Format> formatList, Map<String,Format> formatMap) {
        this.formatList = formatList;
        this.formatMap = formatMap;
    }

    public Format getFormat(int formatId) {
        try {
            Format format = formatList.get(formatId);
            if (format == null) {
                throw new IllegalStateException
                    ("Format does not exist: " + formatId);
            }
            return format;
        } catch (NoSuchElementException e) {
            throw new IllegalStateException
                ("Format does not exist: " + formatId);
        }
    }

    public Format getFormat(Class cls) {
        Format format = formatMap.get(cls.getName());
        if (format == null) {
            throw new IllegalArgumentException
                ("Class is not persistent: " + cls.getName());
        }
        return format;
    }

    public Format getFormat(String className) {
        return formatMap.get(className);
    }

    public Format createFormat(Class type, Map<String,Format> newFormats) {
        throw new IllegalStateException();
    }
}
