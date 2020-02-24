/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.MarshalledTupleKeyEntity;

// line 3 "../../../../TupleSerialMarshalledBinding.ump"
public class TupleSerialMarshalledBinding extends TupleSerialBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleSerialMarshalledBinding()
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
   * Creates a tuple-serial marshalled binding object.
   * @param classCatalogis the catalog to hold shared class information and for adatabase should be a  {@link StoredClassCatalog}.
   * @param baseClassis the base class for serialized objects stored using thisbinding -- all objects using this binding must be an instance of this class.
   */
  // line 18 "../../../../TupleSerialMarshalledBinding.ump"
   public  TupleSerialMarshalledBinding(ClassCatalog classCatalog, Class baseClass){
    this(new SerialBinding(classCatalog, baseClass));
  }


  /**
   * 
   * Creates a tuple-serial marshalled binding object.
   * @param dataBindingis the binding used for serializing and deserializing theentity object.
   */
  // line 26 "../../../../TupleSerialMarshalledBinding.ump"
   public  TupleSerialMarshalledBinding(SerialBinding dataBinding){
    super(dataBinding);
  }

  // line 30 "../../../../TupleSerialMarshalledBinding.ump"
   public Object entryToObject(TupleInput tupleInput, Object javaInput){
    MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) javaInput;
	if (tupleInput != null) {
	    entity.unmarshalPrimaryKey(tupleInput);
	}
	return entity;
  }

  // line 38 "../../../../TupleSerialMarshalledBinding.ump"
   public void objectToKey(Object object, TupleOutput output){
    MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) object;
	entity.marshalPrimaryKey(output);
  }

  // line 43 "../../../../TupleSerialMarshalledBinding.ump"
   public Object objectToData(Object object){
    return object;
  }

}