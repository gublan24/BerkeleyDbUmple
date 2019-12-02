/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: PersistentProxy.java,v 1.6 2006/05/24 20:49:40 mark Exp $
 */

package com.sleepycat.persist.model;

import com.sleepycat.persist.evolve.ConversionStore;
import com.sleepycat.persist.evolve.Converter;
import com.sleepycat.persist.evolve.Mutations;
import com.sleepycat.persist.raw.RawStore;

/**
 * Implemented by a proxy class to represent the persistent state of a
 * (non-persistent) proxied class.  Normally classes that are outside the scope
 * of the developer's control must be proxied since they cannot be annotated,
 * and because it is desirable to insulate the stored format from changes to
 * the instance fields of the proxied class.  This is useful for classes in the
 * standard Java libraries, for example.
 *
 * <p>{@code PersistentProxy} objects are not required to be thread-safe.  A
 * single thread will create and call the methods of a given {@code
 * PersistentProxy} object.</p>
 *
 * <p>There are three requirements for a proxy class:</p>
 * <ol>
 * <li>It must implement the <code>PersistentProxy</code> interface.</li>
 * <li>It must specified as a persistent proxy class in the entity model.
 * Using the {@link AnnotationModel}, a proxy class is indicated by the {@link
 * Persistent} annotation with the {@link Persistent#proxyFor} property.</li>
 * <li>It must be explicitly registered by calling {@link
 * EntityModel#registerClass} before opening the store.</li>
 * </ol>
 *
 * <p>In order to serialize an instance of the proxied class before it is
 * stored, an instance of the proxy class is created.  The proxied instance is
 * then passed to the proxy's {@link #initializeProxy initializeProxy} method.
 * When this method returns, the proxy instance contains the state of the
 * proxied instance.  The proxy instance is then serialized and stored in the
 * same way as for any persistent object.</p>
 *
 * <p>When an instance of the proxy object is deserialized after it is
 * retrieved from storage, its {@link #convertProxy} method is called.  The
 * instance of the proxied class returned by this method is then returned as a
 * field in the persistent instance.</p>
 *
 * <p>For example:</p>
 * <pre class="code">
 *  import java.util.Locale;
 *
 *  {@literal @Persistent(proxyFor=Locale.class)}
 *  class LocaleProxy implements {@literal PersistentProxy<Locale>} {
 *
 *      String language;
 *      String country;
 *      String variant;
 *
 *      private LocaleProxy() {}
 *
 *      public void initializeProxy(Locale object) {
 *          language = object.getLanguage();
 *          country = object.getCountry();
 *          variant = object.getVariant();
 *      }
 *
 *      public Locale convertProxy() {
 *          return new Locale(language, country, variant);
 *      }
 *  }</pre>
 *
 * <p>The above definition allows the {@code Locale} class to be used in any
 * persistent class, for example:</p>
 * <pre class="code">
 *  {@literal @Persistent}
 *  class LocalizedText {
 *      String text;
 *      Locale locale;
 *  }</pre>
 *
 * <p>A proxy for proxied class P does not handle instances of subclasses of P.
 * To proxy subclasses of P, a separate proxy class is needed.</p>
 *
 * <p>Several {@link <a href="Entity.html#proxyTypes">built in proxy types</a>}
 * are used implicitly.  An application defined proxy will be used instead of a
 * built-in proxy, if both exist for the same proxied class.</p>
 *
 * <p>With respect to class evolution, a proxy instance is no different than
 * any other persistent instance.  When using a {@link ConversionStore}, {@link
 * RawStore} or {@link Converter}, only the raw data of the proxy instance will
 * be visible.  Raw data for the proxied instance never exists.</p>
 *
 * <p><strong>Generic Types</strong></p>
 *
 * <p>Special consideration is needed when the proxied class has generic type
 * parameters.  The proxy class should have the same number of type parameters
 * as the proxied class.  For example, a proxy class for {@code HashMap<K,V>}
 * might declare arrays of type {@code K} and {@code V} as shown below:</p>
 *
 * <pre class="code">
 *  {@literal @Persistent(proxyFor=HashSet.class)}
 *  {@literal class HashMapProxy<K,V> implements PersistentProxy<HashSet<K,V>>} {
 *      private K[] keys;
 *      private V[] values;
 *      ...
 *  }</pre>
 *
 * <p>The type parameters of the proxy class and proxied class are linked by
 * the Direct Persistence Layer to determine actual types.  Knowing actual
 * types is important for efficient class evolution, as described under {@link
 * <a href="../evolve/Mutations.html#genericTypes">Generic Types and Class
 * Evolution</a>}.</p>
 *
 * <p>Using the example of the proxy class above, imagine that a field in a
 * persistent class is declared with type {@code HashMap<Integer,Color>}.  The
 * Direct Persistence Layer can determine that the actual type arguments in the
 * field declaration, {@code Integer} and {@code Color}, are the types of the
 * {@code keys} and {@code values} arrays in the proxy class.  The type
 * parameters {@code K} and {@code V} are the links between types in the proxy
 * class and types in the proxied class.</p>
 *
 * @author Mark Hayes
 */
public interface PersistentProxy<T> {

    /**
     * Copies the state of a given proxied class instance to this proxy
     * instance.
     */
    void initializeProxy(T object);

    /**
     * Returns a new proxied class instance to which the state of this proxy
     * instance has been copied.
     */
    T convertProxy();
}
