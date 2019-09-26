/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.RuntimeExceptionWrapper;

// line 3 "../../../../TupleTupleMarshalledBinding.ump"
public class TupleTupleMarshalledBinding extends TupleTupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleTupleMarshalledBinding()
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
   * Creates a tuple-tuple marshalled binding object. <p> The given class is used to instantiate entity objects using {@link Class#forName}, and therefore must be a public class and have a public no-arguments constructor. It must also implement the  {@link MarshalledTupleEntry} and {@link MarshalledTupleKeyEntity} interfaces.</p>
   * @param clsis the class of the entity objects.
   */
  // line 15 "../../../../TupleTupleMarshalledBinding.ump"
   public  TupleTupleMarshalledBinding(Class cls){
    this.cls = cls;
	if (!MarshalledTupleKeyEntity.class.isAssignableFrom(cls)) {
	    throw new IllegalArgumentException(cls.toString() + " does not implement MarshalledTupleKeyEntity");
	}
	if (!MarshalledTupleEntry.class.isAssignableFrom(cls)) {
	    throw new IllegalArgumentException(cls.toString() + " does not implement MarshalledTupleEntry");
	}
  }

  // line 25 "../../../../TupleTupleMarshalledBinding.ump"
   public Object entryToObject(TupleInput keyInput, TupleInput dataInput){
    MarshalledTupleEntry obj;
	try {
	    obj = (MarshalledTupleEntry) cls.newInstance();
	} catch (IllegalAccessException e) {
	    throw new RuntimeExceptionWrapper(e);
	} catch (InstantiationException e) {
	    throw new RuntimeExceptionWrapper(e);
	}
	if (dataInput != null) {
	    obj.unmarshalEntry(dataInput);
	}
	MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) obj;
	if (keyInput != null) {
	    entity.unmarshalPrimaryKey(keyInput);
	}
	return entity;
  }

  // line 44 "../../../../TupleTupleMarshalledBinding.ump"
   public void objectToKey(Object object, TupleOutput output){
    MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) object;
	entity.marshalPrimaryKey(output);
  }

  // line 49 "../../../../TupleTupleMarshalledBinding.ump"
   public void objectToData(Object object, TupleOutput output){
    MarshalledTupleEntry entity = (MarshalledTupleEntry) object;
	entity.marshalEntry(output);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../TupleTupleMarshalledBinding.ump"
  private Class cls ;

  
}