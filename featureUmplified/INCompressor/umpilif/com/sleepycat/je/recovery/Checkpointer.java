/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../Checkpointer.ump"
public class Checkpointer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Checkpointer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../Checkpointer.ump"
   protected void hook527(IN target, IN parent, boolean allowDeltas, long checkpointStart, boolean logProvisionally, long newLsn, boolean mustLogParent) throws DatabaseException{
    envImpl.lazyCompress(target);
	original(target, parent, allowDeltas, checkpointStart, logProvisionally, newLsn, mustLogParent);
  }

}