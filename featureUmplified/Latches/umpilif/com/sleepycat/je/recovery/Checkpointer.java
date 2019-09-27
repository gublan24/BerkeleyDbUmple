/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../Checkpointer.ump"
// line 3 "../../../../Checkpointer_inner.ump"
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

  // line 8 "../../../../Checkpointer.ump"
   protected void hook526(CheckpointReference targetRef, Map dirtyMap, int currentLevel, boolean logProvisionally, boolean allowDeltas, long checkpointStart, Tree tree, SearchResult result, boolean mustLogParent) throws DatabaseException{
    try {
	    original(targetRef, dirtyMap, currentLevel, logProvisionally, allowDeltas, checkpointStart, tree, result,
		    mustLogParent);
	} finally {
	    result.parent.releaseLatch();
	}
  }

  // line 18 "../../../../Checkpointer.ump"
   protected void hook527(IN target, IN parent, boolean allowDeltas, long checkpointStart, boolean logProvisionally, long newLsn, boolean mustLogParent) throws DatabaseException{
    try {
	    original(target, parent, allowDeltas, checkpointStart, logProvisionally, newLsn, mustLogParent);
	} finally {
	    target.releaseLatch();
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 4 "../../../../Checkpointer_inner.ump"
  public static class Checkpointer_selectDirtyINs
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_selectDirtyINs()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Checkpointer_inner.ump"
     protected void hook528() throws DatabaseException{
      try {
            original();
          }
      finally {
            inMemINs.releaseMajorLatchIfHeld();
          }
    }
  
    // line 14 "../../../../Checkpointer_inner.ump"
     protected void hook529() throws DatabaseException{
      inMemINs.latchMajor();
          original();
    }
  
    // line 18 "../../../../Checkpointer_inner.ump"
     protected void hook530() throws DatabaseException{
      try {
            original();
          }
      finally {
            in.releaseLatch();
          }
    }
  
  }
}