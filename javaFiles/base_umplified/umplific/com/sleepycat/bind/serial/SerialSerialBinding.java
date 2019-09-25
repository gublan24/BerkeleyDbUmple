/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.bind.EntityBinding;
import com.sleepycat.bind.*;

// line 3 "../../../../SerialSerialBinding.ump"
public abstract class SerialSerialBinding implements EntityBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SerialSerialBinding()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates a serial-serial entity binding.
   * @param classCatalogis the catalog to hold shared class information and for adatabase should be a  {@link StoredClassCatalog}.
   * @param keyClassis the key base class.
   * @param dataClassis the data base class.
   */
  // line 20 "../../../../SerialSerialBinding.ump"
   public  SerialSerialBinding(ClassCatalog classCatalog, Class keyClass, Class dataClass){
    this(new SerialBinding(classCatalog, keyClass), new SerialBinding(classCatalog, dataClass));
  }


  /**
   * 
   * Creates a serial-serial entity binding.
   * @param keyBindingis the key binding.
   * @param dataBindingis the data binding.
   */
  // line 29 "../../../../SerialSerialBinding.ump"
   public  SerialSerialBinding(SerialBinding keyBinding, SerialBinding dataBinding){
    this.keyBinding = keyBinding;
	this.dataBinding = dataBinding;
  }

  // line 34 "../../../../SerialSerialBinding.ump"
   public Object entryToObject(DatabaseEntry key, DatabaseEntry data){
    return entryToObject(keyBinding.entryToObject(key), dataBinding.entryToObject(data));
  }

  // line 38 "../../../../SerialSerialBinding.ump"
   public void objectToKey(Object object, DatabaseEntry key){
    object = objectToKey(object);
	keyBinding.objectToEntry(object, key);
  }

  // line 43 "../../../../SerialSerialBinding.ump"
   public void objectToData(Object object, DatabaseEntry data){
    object = objectToData(object);
	dataBinding.objectToEntry(object, data);
  }


  /**
   * 
   * Constructs an entity object from deserialized key and data objects.
   * @param keyInputis the deserialized key object.
   * @param dataInputis the deserialized data object.
   * @return the entity object constructed from the key and data.
   */
   public abstract Object entryToObject(Object keyInput, Object dataInput);


  /**
   * 
   * Extracts a key object from an entity object.
   * @param objectis the entity object.
   * @return the deserialized key object.
   */
   public abstract Object objectToKey(Object object);


  /**
   * 
   * Extracts a data object from an entity object.
   * @param objectis the entity object.
   * @return the deserialized data object.
   */
   public abstract Object objectToData(Object object);
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../SerialSerialBinding.ump"
  private SerialBinding keyBinding ;
// line 11 "../../../../SerialSerialBinding.ump"
  private SerialBinding dataBinding ;

  
}