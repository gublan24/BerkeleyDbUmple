/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: Mutations.java,v 1.4 2006/05/24 20:49:38 mark Exp $
 */

package com.sleepycat.persist.evolve;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PersistentProxy;
import com.sleepycat.persist.model.SecondaryKey;

/**
 * A collection of mutations for configuring class evolution.
 *
 * <p>For persistent data that is not short lived, changes to persistent
 * classes are almost inevitable.  Some changes are compatible with existing
 * types, and data conversion for these changes is performed automatically and
 * transparently.  Other changes are not compatible with existing types.
 * Mutations can be used to explicitly manage many types of incompatible
 * changes.</p>
 *
 * <p>Mutations are configured when a store is opened via {@link
 * StoreConfig#setMutations StoreConfig.setMutations}.  Mutations cause data
 * conversion to occur lazily as instances are read from the store.  The {@link
 * EntityStore#evolve EntityStore.evolve} method may also be used to perform
 * eager conversion.  For example:</p>
 *
 * <pre class="code">
 *  Mutations mutations = new Mutations();
 *  // Add mutations...
 *  StoreConfig config = new StoreConfig();
 *  config.setMutations(mutations);
 *  EntityStore store = new EntityStore(env, "myStore", config);</pre>
 *
 * <p>Not all incompatible class changes can be handled via mutations.  For
 * example, complex refactoring may require a transformation that manipulates
 * multiple entity instances at once.  Such changes are not possible with
 * mutations but can be performed using a {@link ConversionStore}.</p>
 *
 * <p>The different categories of type changes are described below.</p>
 *
 * <p><strong>Key Field Changes</strong></p>
 *
 * <p>Unlike entity data, key data is not versioned.  Therefore, the physical
 * key format for an index is fixed once the first key is written, and the
 * changes allowed for key fields are very limited.  The only changes allowed
 * for key fields are:</p>
 * <ul>
 * <li>The name of a key field may be changed, as long as this change is
 * accompanied by a {@link Renamer} mutation.</li>
 * <li>A primitive type may be changed to its corresponding primitive wrapper
 * type, and vice versa.  This is a compatible change.</li>
 * </ul>
 *
 * <p>Any other changes to a key field are not only incompatible, they are not
 * supported except by creating a new temporary index and copying the current
 * index to the temporary index.  See {@link ConversionStore#createTempIndex
 * ConversionStore.createTempIndex}.</p>
 *
 * <p>Key ordering, including the behavior of a custom {@link Comparator}, is
 * also fixed, since keys are stored in order in the index.  The specifications
 * for key ordering may not be changed, and the developer is responsible for
 * not changing the behavior of a key {@code Comparator}.
 * <strong>WARNING:</strong>: Changing the behavior of a key {@code Comparator}
 * is likely to make the index unusable.</p>
 *
 * <p><strong>Compatible Type Changes</strong></p>
 *
 * <p>Entity data, unlike key data, is versioned.  Therefore, some changes can
 * be made compatibly and other changes can be handled via mutations.
 * Compatible changes are defined below.</p>
 *
 * <p>Changes to field types in entity class definitions are compatible when
 * they conform to the Java Language Specification definitions for Widening
 * Primitive Conversions and Widening Reference Conversions.  For example, a
 * smaller integer type may be changed to a larger integer type, and a
 * reference type may be changed to one of its supertypes.  Automatic
 * widening conversions are performed as described in the Java Language
 * Specification.</p>
 *
 * <p>Primitive types may also be compatibly changed to their corresponding
 * primitive wrapper types, or to the wrapper type for a widened primitive
 * type.  However, changing from a primitive wrapper type to a primitive type
 * is not a compatible change since existing null values could not be
 * represented.</p>
 *
 * <p>In addition, adding fields to a class is a compatible change.  When a
 * persistent instance of a class is read that does not contain the new field,
 * the new field is initialized by the default constructor.</p>
 *
 * <p>All other changes to instance fields are considered incompatible.
 * Incompatible changes may be handled via mutations, as described next, or via
 * a {@link ConversionStore}.</p>
 *
 * <p><strong>Mutations</strong></p>
 *
 * <p>A class or field can be renamed using a {@link Renamer}.  Renaming is not
 * expensive, since it does not involve conversion of instance data.</p>
 *
 * <p>An entity class or field can be deleted using a {@link Deleter}.
 * Deleting an entity class involves removing the primary and secondary indices
 * for the store; removal is performed when the store is opened.  Deleting of a
 * field involves conversion of instances to discard the field data, and is
 * done lazily or by the {@link EntityStore#evolve EntityStore.evolve} method,
 * as already described.</p>
 *
 * <p>Other incompatible changes are made by creating a {@link Conversion}
 * mutation and implementing a {@link Converter#convert Converter.convert}
 * method that manipulates the raw objects and/or simple values directly.  The
 * {@code convert} method is passed an object of the old incompatible type and
 * it returns an object of a current type.</p>
 *
 * <p>Conversions can be specified in two ways: for specific fields or for all
 * instances of a class.  A different {@link Conversion} constructor is used in
 * each case.  Field-specific conversions are used instead of class conversions
 * when both are applicable.</p>
 *
 * <p>Note that all mutations are applied to a specific class version number.
 * Class versions are explicit for two reasons:</p>
 * <ol>
 * <li>This provides safety in the face of multiple unconverted versions of a
 * given type.  Without a version, a single conversion method would have to
 * handle multiple input types, and would have to distinguish between them by
 * examining the data or type information.</li>
 * <li>This allows arbitrary changes to be made.  For example, a series of name
 * changes may reuse a given name for more than one version.  To identify the
 * specific type being converted or renamed, a version number is needed.</li>
 * </ol>
 * <p>See {@link Entity#version} and {@link Persistent#version} for
 * information on assigning class version numbers.</p>
 *
 * <p>Mutations are responsible for converting an existing incompatible class
 * version to the current version as defined by a current class definition.
 * For example, consider that class-version A-1 is initially changed to A-2 and
 * a mutation is added for converting A-1 to A-2.  If later changes to version
 * A-3 occur before converting all A-1 instances to version A-2, the converter
 * for A-1 will have to be changed.  Instead of converting from A-1 to A-2 it
 * will need to convert from A-1 to A-3.  In addition, a mutation converting
 * A-2 to A-3 will be needed.</p>
 *
 * <p>When a conversion mutation applies to a given object, other mutations
 * that may apply to that object are not performed.  It is the responsibility
 * of the converter to return an object that conforms to the current class
 * definition, including renaming fields.  If the input object has nested
 * objects or superclasses that also need conversion, the converter for the
 * input object must also perform those conversions before returning the final
 * converted object.</p>
 *
 * <p>The above two rules are defined to avoid the complexity and potential
 * errors that could result if multiple mutations were performed on a single
 * object.</p>
 *
 * <p>The {@link EntityStore#evolve EntityStore.evolve} method is used to
 * ensure that all instances of an old class version are converted to the
 * current version.  When this takes place, mutations for the old version are
 * no longer needed and are discarded by the store.</p>
 *
 * <p>However, even when the application has called {@link EntityStore#evolve
 * EntityStore#evolve} it may be convenient to configure a set of mutations for
 * handling stores in various stages of evolution; in other words, to configure
 * more mutations than may actually be needed.  Therefore, a store will
 * silently ignore configured mutations for class versions that no longer
 * exist.  To find out what mutations are currently active, call {@link
 * EntityStore#getMutations EntityStore.getMutations}.</p>
 *
 * <p>Class changes that cannot be handled using a mutation may be handled
 * using a {@link ConversionStore}.</p>
 *
 * <p><strong>Other Metadata Changes</strong></p>
 *
 * <p>When a class is renamed that happens to be an entity class, it remains
 * an entity class.  When a field is renamed that happens to be a primary or
 * secondary key field, its metadata remains intact as well.</p>
 * 
 * <p>When {@link SecondaryKey} is added to a new or existing field, a new
 * index is created automatically.  The new index will be populated by reading
 * the primary index when the primary index is opened.</p>
 *
 * <p>When a field with {@link SecondaryKey} is deleted, or when {@link
 * SecondaryKey} is removed from a field without deleting it, the secondary
 * index is removed (dropped).  Removal occurs when the store is opened.</p>
 *
 * <p>Changing any other information about a primary or secondary key is
 * generally not allowed except using a {@link ConversionStore}, as described
 * above under Key Field Changes.</p>
 *
 * <p><strong><a name="genericTypes">Generic Types and Class
 * Evolution</a></strong></p>
 *
 * <p>Conversion mutations always target a specific class, for example {@code
 * Address}.  When class evolution is performed, all persistent entities are
 * processed that might contain instances of the targeted class.  To ensure
 * that only entities that actually contain instances of this class are
 * processed, and thereby avoid unnecessary processing, it is important to
 * supply specific type arguments when using generic classes.  Additionally,
 * you should avoid declaring type parameters for non-abstract entity classes
 * and subclasses.</p>
 *
 * <p>For example, consider an entity class that contains a field declared as
 * {@code Set<?> favoriteColors}.  The type argument is not specified and must
 * be assumed to be {@code Object}.  If any subclass of {@code Object} (for
 * example, {@code Address}) is being converted then class evolution must
 * process all entities containing the {@code favoriteColors} field.  If this
 * field were instead declared with a specific type argument (for example,
 * {@code Set<Color> favoriteColors}) then the entity will only be processed if
 * that specific class ({@code Color} or a subclass of it) is targeted by a
 * conversion.</p>
 *
 * <p>The same principle applies if a non-abstract entity class or subclass is
 * declared to have generic type parameters.  Since the entity class is the
 * type of top level persistence instances, there is no field declaration that
 * is known to the Direct Persistence Layer and therefore the actual type
 * arguments are not known to the Direct Persistence Layer.  The type arguments
 * will be assumed to be {@code Object} (or the upper bound of the type
 * parameter, if one is specified in the class declaration).  Therefore, it is
 * best to avoid the declaration of type parameters for entity classes and
 * subclasses.</p>
 *
 * <p>A variation on this theme is when a {@link PersistentProxy} is used to
 * proxy a class with generic type parameters.  In this case, when a field with
 * the proxied type is declared, the type parameters of the proxied class and
 * the proxy class are linked to determine the actual persistent types.  See
 * {@link PersistentProxy} for details.</p>
 *
 * @author Mark Hayes
 */
public class Mutations implements Serializable {

    /**
     * Creates an empty set of mutations.
     */
    public Mutations() {
    }

    /**
     * Adds a renamer mutation.
     */
    public void addRenamer(Renamer renamer) {
    }

    /**
     * Returns an unmodifiable set of all renamer mutations.
     */
    public Set<Renamer> getRenamers() {
        return null;
    }

    /**
     * Adds a deleter mutation.
     */
    public void addDeleter(Deleter deleter) {
    }

    /**
     * Returns an unmodifiable set of all deleter mutations.
     */
    public Set<Deleter> getDeleters() {
        return null;
    }

    /**
     * Adds a conversion mutation.
     */
    public void addConversion(Conversion conversion) {
    }

    /**
     * Returns an unmodifiable set of all conversion mutations.
     */
    public Set<Conversion> getConversions() {
        return null;
    }
}
