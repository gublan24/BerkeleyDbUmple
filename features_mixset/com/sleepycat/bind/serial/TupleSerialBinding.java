/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleBase;
import com.sleepycat.bind.EntityBinding;
import com.sleepycat.bind.*;
import com.sleepycat.bind.tuple.*;

// line 3 "../../../../TupleSerialBinding.ump"
public abstract class TupleSerialBinding extends TupleBase implements EntityBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleSerialBinding()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * Creates a tuple-serial entity binding.
   * @param classCatalogis the catalog to hold shared class information and for adatabase should be a  {@link StoredClassCatalog}.
   * @param baseClassis the base class.
   */
  // line 21 "../../../../TupleSerialBinding.ump"
   public  TupleSerialBinding(ClassCatalog classCatalog, Class baseClass){
    this(new SerialBinding(classCatalog, baseClass));
  }


  /**
   * 
   * Creates a tuple-serial entity binding.
   * @param dataBindingis the data binding.
   */
  // line 29 "../../../../TupleSerialBinding.ump"
   public  TupleSerialBinding(SerialBinding dataBinding){
    this.dataBinding = dataBinding;
  }

  // line 33 "../../../../TupleSerialBinding.ump"
   public Object entryToObject(DatabaseEntry key, DatabaseEntry data){
    return entryToObject(entryToInput(key), dataBinding.entryToObject(data));
  }

  // line 37 "../../../../TupleSerialBinding.ump"
   public void objectToKey(Object object, DatabaseEntry key){
    TupleOutput output = getTupleOutput(object);
	objectToKey(object, output);
	outputToEntry(output, key);
  }

  // line 43 "../../../../TupleSerialBinding.ump"
   public void objectToData(Object object, DatabaseEntry data){
    object = objectToData(object);
	dataBinding.objectToEntry(object, data);
  }


  /**
   * 
   * Constructs an entity object from  {@link TupleInput} key entry anddeserialized data entry objects.
   * @param keyInputis the {@link TupleInput} key entry object.
   * @param dataInputis the deserialized data entry object.
   * @return the entity object constructed from the key and data.
   */
   public abstract Object entryToObject(TupleInput keyInput, Object dataInput);


  /**
   * 
   * Extracts a key tuple from an entity object.
   * @param objectis the entity object.
   * @param keyOutputis the {@link TupleOutput} to which the key should be written.
   */
   public abstract void objectToKey(Object object, TupleOutput keyOutput);


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
  
  // line 13 "../../../../TupleSerialBinding.ump"
  protected SerialBinding dataBinding ;

  
}