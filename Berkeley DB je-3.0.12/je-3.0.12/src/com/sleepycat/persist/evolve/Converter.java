/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: Converter.java,v 1.2 2006/04/09 16:39:25 mark Exp $
 */

package com.sleepycat.persist.evolve;

import java.io.Serializable;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.raw.RawObject;
import com.sleepycat.persist.raw.RawType;
import com.sleepycat.persist.model.EntityModel;

/**
 * Converts an old version of an object value to conform to the current class
 * definition.
 *
 * <p>Conversions of simple types are generally simple.  For example, a {@code
 * String} field that contains only integer values can be easily converted to
 * an {@code int} field:</p>
 * <pre class="code">
 *  // The old class.  Version 0 is implied.
 *  //
 *  {@literal @Persistent}
 *  class Address {
 *      String zipCode;
 *      ...
 *  }
 *
 *  // The new class.  A new version number must be assigned.
 *  //
 *  {@literal @Persistent(version=1)}
 *  class Address {
 *      int zipCode;
 *      ...
 *  }
 *
 *  // The converter class.
 *  //
 *  class MyConverter1 implements Converter {
 *
 *      public void initialize(EntityModel model) {
 *          // No initialization needed.
 *      }
 *
 *      public Object convert(Object fromValue) {
 *          return Integer.valueOf((String) fromValue);
 *      }
 *  }
 *
 *  // Create a field conversion mutation.
 *  //
 *  Conversion conversion = new Conversion(Address.class.getName(), 0,
 *                                         "zipCode", new MyConverter1());
 *
 *  // Configure the conversion as described {@link Mutations here}.</pre>
 *
 * <p>A converter may perform arbitrary transformations on an object.  For
 * example, a converter may transform a single String address field into four
 * fields for street, city, state and zip code, or vice versa.</p>
 * <pre class="code">
 *  // The old class.  Version 0 is implied.
 *  //
 *  {@literal @Entity}
 *  class Person {
 *      String address;
 *      ...
 *  }
 *
 *  // The new class.  A new version number must be assigned.
 *  //
 *  {@literal @Entity(version=1)}
 *  class Person {
 *      Address address;
 *      ...
 *  }
 *
 *  // The new address class.
 *  //
 *  {@literal @Persistent}
 *  class Address {
 *      String street;
 *      String city;
 *      String state;
 *      int zipCode;
 *      ...
 *  }
 *
 *  class MyConverter2 implements Converter {
 *      private transient RawType newPersonType;
 *      private transient RawType addressType;
 *
 *      public void initialize(EntityModel model) {
 *          newPersonType = model.getRawType(Person.class.getName());
 *          addressType = model.getRawType(Address.class.getName());
 *      }
 *
 *      public Object convert(Object fromValue) {
 *
 *          // Get field value maps for old and new objects.
 *          //
 *          RawObject person = (RawObject) fromValue;
 *          RawObject address = new RawObject(addressType, null, null);
 *          {@literal Map<String,?> personValues = person.getValues();}
 *          {@literal Map<String,?> addressValues = address.getValues();}
 *
 *          // Remove the old address and replace it with a new one.
 *          //
 *          String oldAddress = (String) personValues.remove("address");
 *          addressValues.put("street", parseStreet(oldAddress));
 *          addressValues.put("city", parseCity(oldAddress));
 *          addressValues.put("state", parseState(oldAddress));
 *          addressValues.put("zipCode", parseZipCode(oldAddress));
 *          personValues.put("address", address);
 *
 *          return new RawObject(newPersonType, personValues, person.getSuper());
 *      }
 *
 *      private String parseStreet(String oldAddress) { ... }
 *      private String parseCity(String oldAddress) { ... }
 *      private String parseState(String oldAddress) { ... }
 *      private Integer parseZipCode(String oldAddress) { ... }
 *  }
 *
 *  // Create a field conversion mutation.
 *  //
 *  Conversion conversion = new Conversion(Person.class.getName(), 0,
 *                                         "address", new MyConverter2());
 *
 *  // Configure the conversion as described {@link Mutations here}.</pre>
 *
 * <p>Note that when a converter returns a {@link RawObject}, it must return
 * it with a {@link RawType} that is current as defined by the current class
 * definitions.  The proper types can be obtained from the {@link EntityModel}
 * in the converter's {@link #initialize initialize} method.</p>
 *
 * <p>A converter can handle changes to class hierarchies.  For example, if a
 * "name" field originally declared in class A is moved to its superclass B, a
 * converter can move the field value accordingly:</p>
 * <pre class="code">
 *  // The old classes.  Version 0 is implied.
 *  //
 *  {@literal @Persistent}
 *  class A extends B {
 *      String name;
 *      ...
 *  }
 *  {@literal @Persistent}
 *  class B {
 *      ...
 *  }
 *
 *  // The new classes.  A new version number must be assigned.
 *  //
 *  {@literal @Persistent(version=1)}
 *  class A extends B {
 *      ...
 *  }
 *  {@literal @Persistent(version=1)}
 *  class B {
 *      String name;
 *      ...
 *  }
 *
 *  class MyConverter3 implements Converter {
 *      private transient RawType newAType;
 *      private transient RawType newBType;
 *
 *      public void initialize(EntityModel model) {
 *          newAType = model.getRawType(A.class.getName());
 *          newBType = model.getRawType(B.class.getName());
 *      }
 *
 *      public Object convert(Object fromValue) {
 *          RawObject oldA = (RawObject) fromValue;
 *          RawObject oldB = oldA.getSuper();
 *          {@literal Map<String,?> aValues = oldA.getValues();}
 *          {@literal Map<String,?> bValues = oldB.getValues();}
 *          bValues.put("name", aValues.remove("name"));
 *          RawObject newB = new RawObject(newBType, bValues, oldB.getSuper());
 *          RawObject newA = new RawObject(newAType, aValues, newB);
 *          return newA;
 *      }
 *  }
 *
 *  // Create a class conversion mutation.
 *  //
 *  Conversion conversion = new Conversion(B.class.getName(), 0,
 *                                         new MyConverter3());
 *
 *  // Configure the conversion as described {@link Mutations here}.</pre>
 *
 * <p>A converter may return an instance of a different class entirely, as long
 * as it conforms to current class definitions.  For example, a field that is
 * used to discriminate between two types of objects could be removed and
 * replaced by two new subclasses:</p>
 * <pre class="code">
 *  // The old class.  Version 0 is implied.
 *  //
 *  {@literal @Persistent}
 *  class Pet {
 *      boolean isCatNotDog;
 *      ...
 *  }
 *
 *  // The new classes.  A new version number must be assigned to the Pet class.
 *  //
 *  {@literal @Persistent(version=1)}
 *  class Pet {
 *      ...
 *  }
 *  {@literal @Persistent}
 *  class Cat extends Pet {
 *      ...
 *  }
 *  {@literal @Persistent}
 *  class Dog extends Pet {
 *      ...
 *  }
 *
 *  class MyConverter4 implements Converter {
 *      private transient RawType newPetType;
 *      private transient RawType dogType;
 *      private transient RawType catType;
 *
 *      public void initialize(EntityModel model) {
 *          newPetType = model.getRawType(Pet.class.getName());
 *          dogType = model.getRawType(Dog.class.getName());
 *          catType = model.getRawType(Cat.class.getName());
 *      }
 *
 *      public Object convert(Object fromValue) {
 *          RawObject pet = (RawObject) fromValue;
 *          {@literal Map<String,?> petValues = pet.getValues();}
 *          Boolean isCat = (Boolean) petValues.remove("isCatNotDog");
 *          RawObject newPet = new RawObject(newPetType, petValues,
 *                                           pet.getSuper());
 *          RawType newType = isCat.booleanValue() ? catType : dogType;
 *          return new RawObject(newType, null, newPet);
 *      }
 *  }
 *
 *  // Create a class conversion mutation.
 *  //
 *  Conversion conversion = new Conversion(Pet.class.getName(), 0,
 *                                         new MyConverter4());
 *
 *  // Configure the conversion as described {@link Mutations here}.</pre>
 *
 * <p>The primary limitation is that a converter may access at most a single
 * entity instance at one time.  Conversions involving multiple entities at
 * once may be performed using a {@link ConversionStore}.</p>
 *
 * @author Mark Hayes
 */
public interface Converter extends Serializable {

    /**
     * Initializes the converter, allowing it to obtain raw type information
     * from the entity model.
     */
    void initialize(EntityModel model);

    /**
     * Converts an old version of an object value to conform to the current
     * class definition.
     *
     * <p>If a {@link RuntimeException} is thrown by this method, it will be
     * wrapped in a {@link DatabaseException} and thrown to the original
     * caller.  Similarly, a {@link DatabaseException} will be thrown if the
     * object returned by this method does not conform to current class
     * definitions.</p>
     *
     * <p>The class of the input and output object may be one of the simple
     * types or {@link RawObject}, or an array of one of these types.</p>
     *
     * @param fromValue the object value being converted.  The type of this
     * value is defined by the old class version that is being converted.
     *
     * @return the converted object.  The type of this value must conform to
     * the current class definition.
     */
    Object convert(Object fromValue);
}
