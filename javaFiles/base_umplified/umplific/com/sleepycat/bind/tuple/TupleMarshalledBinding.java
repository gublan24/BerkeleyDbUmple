/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.RuntimeExceptionWrapper;

// line 3 "../../../../TupleMarshalledBinding.ump"
public class TupleMarshalledBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleMarshalledBinding()
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
   * Creates a tuple marshalled binding object. <p> The given class is used to instantiate key or data objects using {@link Class#forName}, and therefore must be a public class and have a public no-arguments constructor. It must also implement the  {@link MarshalledTupleEntry} interface.</p>
   * @param clsis the class of the key or data objects.
   */
  // line 15 "../../../../TupleMarshalledBinding.ump"
   public  TupleMarshalledBinding(Class cls){
    this.cls = cls;
	if (!MarshalledTupleEntry.class.isAssignableFrom(cls)) {
	    throw new IllegalArgumentException(cls.toString() + " does not implement MarshalledTupleEntry");
	}
  }

  // line 22 "../../../../TupleMarshalledBinding.ump"
   public Object entryToObject(TupleInput input){
    try {
	    MarshalledTupleEntry obj = (MarshalledTupleEntry) cls.newInstance();
	    obj.unmarshalEntry(input);
	    return obj;
	} catch (IllegalAccessException e) {
	    throw new RuntimeExceptionWrapper(e);
	} catch (InstantiationException e) {
	    throw new RuntimeExceptionWrapper(e);
	}
  }

  // line 34 "../../../../TupleMarshalledBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    MarshalledTupleEntry obj = (MarshalledTupleEntry) object;
	obj.marshalEntry(output);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../TupleMarshalledBinding.ump"
  private Class cls ;

  
}