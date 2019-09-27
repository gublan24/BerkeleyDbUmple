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
   protected void hook546(SortedMap dirtyMap, boolean allowDeltas, long checkpointStart, Integer currentLevel, boolean logProvisionally, CheckpointReference targetRef) throws DatabaseException{
    if (!(targetRef.db.isDeleted())) {
	    flushIN(targetRef, dirtyMap, currentLevel.intValue(), logProvisionally, allowDeltas, checkpointStart);
	}
	original(dirtyMap, allowDeltas, checkpointStart, currentLevel, logProvisionally, targetRef);
  }

}