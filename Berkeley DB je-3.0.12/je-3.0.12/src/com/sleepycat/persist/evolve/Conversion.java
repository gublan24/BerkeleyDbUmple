/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: Conversion.java,v 1.2 2006/04/09 16:39:25 mark Exp $
 */

package com.sleepycat.persist.evolve;

/**
 * A mutation for converting an old version of an object value to conform to
 * the current class definition.  For example:
 *
 * <pre class="code">
 *  package my.package;
 *
 *  // The old class.  Version 0 is implied.
 *  //
 *  {@literal @Entity}
 *  class Person {
 *      // ...
 *  }
 *
 *  // The new class.  A new version number must be assigned.
 *  //
 *  {@literal @Entity(version=1)}
 *  class Person {
 *      // Incompatible changes were made here...
 *  }
 *
 *  // Add a conversion mutation.
 *  //
 *  Mutations mutations = new Mutations();
 *
 *  mutations.addConversion(new Conversion(Person.class.getName(), 0,
 *                                         new MyConverter()));
 *
 *  // Configure the mutations as described {@link Mutations here}.</pre>
 *
 * <p>See {@link Converter} for more information.</p>
 *
 * @author Mark Hayes
 */
public class Conversion extends Mutation {

    /**
     * Creates a mutation for converting all instances of the given class
     * version.
     */
    public Conversion(String className,
                      int classVersion,
                      Converter converter) {
        super(className, classVersion, null);
    }

    /**
     * Creates a mutation for converting all values of the given field in the
     * given class version.
     */
    public Conversion(String declaringClassName,
                      int declaringClassVersion,
                      String fieldName,
                      Converter converter) {
        super(declaringClassName, declaringClassVersion, fieldName);
    }

    /**
     * Returns the converter instance specified in the constructor.
     */
    public Converter getConverter() {
        return null;
    }
}
