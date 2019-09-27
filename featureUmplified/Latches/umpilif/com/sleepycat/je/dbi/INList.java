/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../INList.ump"
// line 3 "../../../../INList_inner.ump"
public class INList
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INList()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * The locking hierarchy is: 1. INList major latch. 2. IN latch. In other words, the INList major latch must be taken before any IN latches to avoid deadlock.
   */
  // line 17 "../../../../INList.ump"
   public void latchMajor() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	majorLatch.acquire();
  }

  // line 22 "../../../../INList.ump"
   public void releaseMajorLatchIfHeld() throws DatabaseException{
    if (majorLatch.isOwner()) {
	    releaseMajorLatch();
	}
  }

  // line 28 "../../../../INList.ump"
   public void releaseMajorLatch() throws DatabaseException{
    latchMinorAndDumpAddedINs();
	majorLatch.release();
  }

  // line 33 "../../../../INList.ump"
   private void dumpAddedINsIntoMajorSet(){
    if (addedINs.size() > 0) {
	    ins.addAll(addedINs);
	    addedINs.clear();
	}
  }

  // line 40 "../../../../INList.ump"
  public void latchMinorAndDumpAddedINs() throws DatabaseException{
    latchMinor();
	try {
	    dumpAddedINsIntoMajorSet();
	} finally {
	    releaseMinorLatch();
	}
  }

  // line 49 "../../../../INList.ump"
   private void latchMinor() throws DatabaseException{
    minorLatch.acquire();
  }

  // line 53 "../../../../INList.ump"
   private void releaseMinorLatch() throws DatabaseException{
    minorLatch.release();
  }

  // line 57 "../../../../INList.ump"
   protected void hook338(EnvironmentImpl envImpl){
    addedINs = new HashSet();
	majorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Major Latch", envImpl);
	minorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Minor Latch", envImpl);
	original(envImpl);
  }

  // line 64 "../../../../INList.ump"
   protected void hook339(EnvironmentImpl envImpl) throws DatabaseException{
    majorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Major Latch", envImpl);
	minorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Minor Latch", envImpl);
	original(envImpl);
  }

  // line 70 "../../../../INList.ump"
   protected void hook340() throws DatabaseException{
    addedINs = new HashSet();
	original();
  }


  /**
   * 
   * An IN is getting evicted or is displaced by recovery.  Caller is responsible for acquiring the major latch before calling this and releasing it when they're done.
   */
  // line 78 "../../../../INList.ump"
   public void removeLatchAlreadyHeld(IN in) throws DatabaseException{
    assert majorLatch.isOwner();
	original(in);
  }

  // line 83 "../../../../INList.ump"
   protected boolean hook341(IN in, boolean removeDone) throws DatabaseException{
    if (!removeDone) {
	    minorLatch.acquire();
	    try {
		removeDone = addedINs.remove(in);
		dumpAddedINsIntoMajorSet();
	    } finally {
		minorLatch.release();
	    }
	}
	return original(in, removeDone);
  }


  /**
   * 
   * An IN is getting swept or is displaced by recovery.
   */
  // line 99 "../../../../INList.ump"
   public void remove(IN in) throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	majorLatch.acquire();
	try {
	    original(in);
	} finally {
	    releaseMajorLatch();
	}
  }

  // line 109 "../../../../INList.ump"
   public SortedSet tailSet(IN in) throws DatabaseException{
    assert majorLatch.isOwner();
	return original(in);
  }

  // line 114 "../../../../INList.ump"
   public IN first() throws DatabaseException{
    assert majorLatch.isOwner();
	return original();
  }


  /**
   * 
   * Return an iterator over the main 'ins' set.  Returned iterator will not show the elements in addedINs. The major latch should be held before entering.  The caller is responsible for releasing the major latch when they're finished with the iterator.
   * @return an iterator over the main 'ins' set.
   */
  // line 123 "../../../../INList.ump"
   public Iterator iterator(){
    assert majorLatch.isOwner();
	return original();
  }


  /**
   * 
   * Clear the entire list during recovery and at shutdown.
   */
  // line 131 "../../../../INList.ump"
   public void clear() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	majorLatch.acquire();
	minorLatch.acquire();
	original();
  }

  // line 138 "../../../../INList.ump"
   protected void hook342() throws DatabaseException{
    addedINs.clear();
	minorLatch.release();
	releaseMajorLatch();
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../INList_inner.ump"
  public static class INList_add
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public INList_add()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../INList_inner.ump"
     protected void hook343() throws DatabaseException{
      try {
            original();
          }
      finally {
            if (addToMajor) {
              _this.releaseMajorLatchIfHeld();
            }
          }
    }
  
    // line 16 "../../../../INList_inner.ump"
     protected void hook344() throws DatabaseException{
      enteredWithLatchHeld=_this.majorLatch.isOwner();
          original();
    }
  
    // line 20 "../../../../INList_inner.ump"
     protected void hook345() throws DatabaseException{
      if (enteredWithLatchHeld) {
            addToMajor=false;
          }
     else {
            if (!(_this.majorLatch.acquireNoWait())) {
              addToMajor=false;
            }
          }
          if (addToMajor) {
            original();
          }
     else {
            _this.minorLatch.acquire();
            try {
              _this.addAndSetMemory(_this.addedINs,in);
            }
      finally {
              _this.minorLatch.release();
            }
            if (!enteredWithLatchHeld) {
              if (_this.majorLatch.acquireNoWait()) {
                try {
                  _this.latchMinorAndDumpAddedINs();
                }
      finally {
                  _this.releaseMajorLatch();
                }
              }
            }
          }
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../INList.ump"
  private Set addedINs = null ;
// line 9 "../../../../INList.ump"
  private Latch majorLatch ;
// line 11 "../../../../INList.ump"
  private Latch minorLatch ;

  
}