/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
// line 3 "../../../../MarshalledTupleKeyEntity.ump"
public interface MarshalledTupleKeyEntity
{
  
  // ABSTRACT METHODS 

 public void marshalPrimaryKey(TupleOutput keyOutput);
 public void unmarshalPrimaryKey(TupleInput keyInput);
 public boolean marshalSecondaryKey(String keyName, TupleOutput keyOutput);
 public boolean nullifyForeignKey(String keyName);
}