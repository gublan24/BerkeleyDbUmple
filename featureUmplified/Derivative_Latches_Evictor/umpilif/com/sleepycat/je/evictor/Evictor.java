/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;
import com.sleepycat.je.latch.LatchSupport;

// line 3 "../../../../Evictor.ump"
// line 3 "../../../../Evictor_inner.ump"
public class Evictor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../Evictor.ump"
   protected long hook374(INList inList, IN target, ScanIterator scanIter, boolean envIsReadOnly, long evictedBytes) throws DatabaseException{
    try {
	    evictedBytes = original(inList, target, scanIter, envIsReadOnly, evictedBytes);
	} finally {
	    target.releaseLatchIfOwner();
	}
	return evictedBytes;
  }

  // line 18 "../../../../Evictor.ump"
   protected long hook375(IN child, IN parent, int index, INList inlist, ScanIterator scanIter, boolean envIsReadOnly, long evictBytes) throws DatabaseException{
    try {
	    evictBytes = original(child, parent, index, inlist, scanIter, envIsReadOnly, evictBytes);
	} finally {
	    parent.releaseLatch();
	}
	return evictBytes;
  }

  // line 27 "../../../../Evictor.ump"
   protected void hook378(IN parent) throws DatabaseException{
    assert parent.isLatchOwner();
	original(parent);
  }

  // line 33 "../../../../Evictor.ump"
   protected long hook379(IN parent, int index, INList inlist, ScanIterator scanIter, boolean envIsReadOnly, long evictBytes, IN renewedChild) throws DatabaseException{
    try {
	    evictBytes = original(parent, index, inlist, scanIter, envIsReadOnly, evictBytes, renewedChild);
	} finally {
	    renewedChild.releaseLatch();
	}
	return evictBytes;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.evictor;
  
  @MethodObject
  // line 4 "../../../../Evictor_inner.ump"
  public static class Evictor_evictBatch
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_evictBatch()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
    public long execute() throws DatabaseException{
      long result=original();
          assert LatchSupport.countLatchesHeld() == 0 : "latches held = " + LatchSupport.countLatchesHeld();
          return result;
    }
  
    // line 11 "../../../../Evictor_inner.ump"
     protected void hook376() throws DatabaseException{
      inList.latchMajor();
          original();
    }
  
    // line 15 "../../../../Evictor_inner.ump"
     protected void hook377() throws DatabaseException{
      inList.releaseMajorLatch();
          original();
    }
  
  }
}