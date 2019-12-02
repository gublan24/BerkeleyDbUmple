/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: ProxiedFormat.java,v 1.13 2006/05/09 05:46:58 mark Exp $
 */

package com.sleepycat.persist.impl;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.sleepycat.persist.model.PersistentProxy;
import com.sleepycat.persist.raw.RawObject;

/**
 * Format for types proxied by a PersistentProxy.
 *
 * @author Mark Hayes
 */
public class ProxiedFormat extends Format {

    private static final long serialVersionUID = -1000032651995478768L;

    private Format proxyFormat;

    ProxiedFormat(Class proxiedType, Format proxyFormat) {
        super(proxiedType);
        this.proxyFormat = proxyFormat;
    }

    @Override
    void collectRelatedFormats(Catalog catalog,
                               Map<String,Format> newFormats) {
        /* The proxy format is created explicitly by PersistCatalog. */
    }

    @Override
    void initialize(Catalog catalog) {
        proxyFormat.setProxiedFormat(this);
    }
    
    @Override
    Object newArray(int len) {
        return Array.newInstance(getType(), len);
    }

    @Override
    Object newInstance(EntityInput input, boolean rawAccess) {
        if (rawAccess) {
            return proxyFormat.newInstance(null, true);
        } else {
            PersistentProxy proxy =
                (PersistentProxy) proxyFormat.newInstance(null, false);
            proxyFormat.readObject(proxy, input, false);
            return proxy.convertProxy();
        }
    }

    @Override
    void writeObject(Object o, EntityOutput output, boolean rawAccess) {
        if (rawAccess) {
            proxyFormat.writeObject(o, output, true);
        } else {
            PersistentProxy proxy =
                (PersistentProxy) proxyFormat.newInstance(null, false);
            proxy.initializeProxy(o);
            proxyFormat.writeObject(proxy, output, false);
        }
    }

    @Override
    void readObject(Object o, EntityInput input, boolean rawAccess) {
        if (rawAccess) {
            proxyFormat.readObject(o, input, true);
        }
        /* Else, do nothing here -- newInstance reads the value. */
    }

    @Override
    void skipContents(EntityInput input) {
        proxyFormat.skipContents(input);
    }

    @Override
    void copySecMultiKey(EntityInput input, Format keyFormat, Set results) {
        CollectionProxy.copyElements(input, this, keyFormat, results);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ProxiedFormat) {
            ProxiedFormat o = (ProxiedFormat) other;
            return super.equals(o) &&
                   proxyFormat.getClassName().equals
                       (o.proxyFormat.getClassName());
        } else {
            return false;
        }
    }
}
