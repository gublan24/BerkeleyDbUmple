/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../TupleTupleMarshalledKeyCreator.ump"
public class TupleTupleMarshalledKeyCreator extends TupleTupleKeyCreator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleTupleMarshalledKeyCreator()
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
   * Creates a tuple-tuple marshalled key creator.
   * @param bindingis the binding used for the tuple-tuple entity.
   * @param keyNameis the key name passed to the {@link MarshalledTupleKeyEntity#marshalSecondaryKey} method toidentify the index key.
   */
  // line 17 "../../../../TupleTupleMarshalledKeyCreator.ump"
   public  TupleTupleMarshalledKeyCreator(TupleTupleMarshalledBinding binding, String keyName){
    this.binding = binding;
	this.keyName = keyName;
  }

  // line 22 "../../../../TupleTupleMarshalledKeyCreator.ump"
   public boolean createSecondaryKey(TupleInput primaryKeyInput, TupleInput dataInput, TupleOutput indexKeyOutput){
    MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) binding.entryToObject(primaryKeyInput, dataInput);
	return entity.marshalSecondaryKey(keyName, indexKeyOutput);
  }

  // line 27 "../../../../TupleTupleMarshalledKeyCreator.ump"
   public boolean nullifyForeignKey(TupleInput dataInput, TupleOutput dataOutput){
    MarshalledTupleKeyEntity entity = (MarshalledTupleKeyEntity) binding.entryToObject(null, dataInput);
	if (entity.nullifyForeignKey(keyName)) {
	    binding.objectToData(entity, dataOutput);
	    return true;
	} else {
	    return false;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../TupleTupleMarshalledKeyCreator.ump"
  private String keyName ;
// line 9 "../../../../TupleTupleMarshalledKeyCreator.ump"
  private TupleTupleMarshalledBinding binding ;

  
}