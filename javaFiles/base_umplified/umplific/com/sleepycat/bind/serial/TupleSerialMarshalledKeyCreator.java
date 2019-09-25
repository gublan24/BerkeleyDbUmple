/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.MarshalledTupleKeyEntity;

// line 3 "../../../../TupleSerialMarshalledKeyCreator.ump"
public class TupleSerialMarshalledKeyCreator extends TupleSerialKeyCreator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleSerialMarshalledKeyCreator()
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
   * Creates a tuple-serial marshalled key creator.
   * @param bindingis the binding used for the tuple-serial entity.
   * @param keyNameis the key name passed to the {@link MarshalledTupleKeyEntity#marshalSecondaryKey} method toidentify the index key.
   */
  // line 20 "../../../../TupleSerialMarshalledKeyCreator.ump"
   public  TupleSerialMarshalledKeyCreator(TupleSerialMarshalledBinding binding, String keyName){
    super(binding.dataBinding);
	this.binding = binding;
	this.keyName = keyName;
	if (dataBinding == null) {
	    throw new NullPointerException("dataBinding may not be null");
	}
  }

  // line 29 "../../../../TupleSerialMarshalledKeyCreator.ump"
   public boolean createSecondaryKey(TupleInput primaryKeyInput, Object dataInput, TupleOutput indexKeyOutput){
    MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) binding.entryToObject(primaryKeyInput, dataInput);
	return entity.marshalSecondaryKey(keyName, indexKeyOutput);
  }

  // line 34 "../../../../TupleSerialMarshalledKeyCreator.ump"
   public Object nullifyForeignKey(Object dataInput){
    MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) binding.entryToObject(null, dataInput);
	return entity.nullifyForeignKey(keyName) ? dataInput : null;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../TupleSerialMarshalledKeyCreator.ump"
  private TupleSerialMarshalledBinding binding ;
// line 12 "../../../../TupleSerialMarshalledKeyCreator.ump"
  private String keyName ;

  
}