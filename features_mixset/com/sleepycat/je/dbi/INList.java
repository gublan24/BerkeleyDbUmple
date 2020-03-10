/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.DatabaseException;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../INList.ump"
// line 3 "../../../../INList_static.ump"
// line 3 "../../../../Latches_INList.ump"
// line 3 "../../../../MemoryBudget_INList.ump"
// line 3 "../../../../MemoryBudget_INList_inner.ump"
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

  // line 21 "../../../../INList.ump"
  public  INList(EnvironmentImpl envImpl){
    this.envImpl = envImpl;
			ins = new TreeSet();
			Label338:
addedINs = new HashSet();
	majorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Major Latch", envImpl);
	minorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Minor Latch", envImpl);
 ;//this.hook338(envImpl);
  }


  /**
   * 
   * Used only by tree verifier when validating INList. Must be called with orig.majorLatch acquired.
   */
  // line 30 "../../../../INList.ump"
   public  INList(INList orig, EnvironmentImpl envImpl) throws DatabaseException{
    ins = new TreeSet(orig.getINs());
			Lanbel340:   ; //this.hook340();
			this.envImpl = envImpl;
			Label339:
majorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Major Latch", envImpl);
	minorLatch = LatchSupport.makeLatch(DEBUG_NAME + " Minor Latch", envImpl);
   ; //this.hook339(envImpl);
  }

  // line 37 "../../../../INList.ump"
   public SortedSet getINs(){
    return ins;
  }

  // line 41 "../../../../INList.ump"
   public int getSize(){
    return ins.size();
  }


  /**
   * 
   * An IN has just come into memory, add it to the list.
   */
  // line 48 "../../../../INList.ump"
   public void add(IN in) throws DatabaseException{
    Label343:
;
			boolean enteredWithLatchHeld = majorLatch.isOwner();
				    boolean addToMajor = true;
				    try {
            if (enteredWithLatchHeld) {

                /* 
                 * Don't try to acquire the major latch twice, that's not
                 * supported. If we do hold the latch, don't modify the major
                 * list, we may be faulting in an IN while iterating over the
                 * list from the evictor.
                 */
                addToMajor = false;
            } else {
                if (!(majorLatch.acquireNoWait())) {
                    /* We couldn't acquire the latch. */
                    addToMajor = false;
                }
            }

            if (addToMajor) {
                
 ;
          addAndSetMemory(addedINs, in);
          

            } else {
                minorLatch.acquire();
                try {
                    addAndSetMemory(addedINs, in);
                } finally {
                    minorLatch.release();
                }

                /*
                 * The holder of the majorLatch may have released it.  If so,
                 * try to put the minor list into the major list so no INs are
                 * orphaned.
                 */
                if (!enteredWithLatchHeld) {
                    if (majorLatch.acquireNoWait()) {
                        try {
                            latchMinorAndDumpAddedINs();
                        } finally {
                            releaseMajorLatch();
                        }
                    }
                }
            }
        } finally {
            if (addToMajor) {
                releaseMajorLatchIfHeld();
            }
        }
Label344: ;
  }

  // line 55 "../../../../INList.ump"
   private void addAndSetMemory(Set set, IN in){
    new INList_addAndSetMemory(this, set, in).execute();
  }


  /**
   * 
   * An IN is getting evicted or is displaced by recovery.  Caller is responsible for acquiring the major latch before calling this and releasing it when they're done.
   */
  // line 62 "../../../../INList.ump"
   public void removeLatchAlreadyHeld(IN in) throws DatabaseException{
    // line 158 "../../../../Latches_INList.ump"
    assert majorLatch.isOwner();
    	//original(in);
    // END OF UMPLE BEFORE INJECTION
    boolean removeDone = ins.remove(in);
			Label341:
if (!removeDone) {
			  minorLatch.acquire();
			  try {
			removeDone = addedINs.remove(in);
			dumpAddedINsIntoMajorSet();
			  } finally {
			minorLatch.release();
			  }
		}
			//	return //original(in, removeDone);
   ;
			assert removeDone;

		  Label346:
if (updateMemoryUsage) {
			  envImpl.getMemoryBudget().updateTreeMemoryUsage(in.getAccumulatedDelta() - in.getInMemorySize());
			  in.setInListResident(false);
		}
//		original(in);
   ;
  }


  /**
   * 
   * An IN is getting swept or is displaced by recovery.
   */
  // line 73 "../../../../INList.ump"
   public void remove(IN in) throws DatabaseException{
    // line 179 "../../../../Latches_INList.ump"
    assert LatchSupport.countLatchesHeld() == 0;
    	majorLatch.acquire();
    // END OF UMPLE BEFORE INJECTION
    try {
						 	removeLatchAlreadyHeld(in); //original(in);
					} finally {
							LabelRemove_1:
releaseMajorLatch();
   ;//releaseMajorLatch();
					}
  }

  // line 81 "../../../../INList.ump"
   public SortedSet tailSet(IN in) throws DatabaseException{
    // line 189 "../../../../Latches_INList.ump"
    assert majorLatch.isOwner();
    	//return //original(in);
    // END OF UMPLE BEFORE INJECTION
    return ins.tailSet(in);
  }

  // line 85 "../../../../INList.ump"
   public IN first() throws DatabaseException{
    // line 194 "../../../../Latches_INList.ump"
    assert majorLatch.isOwner();
    	//return //original();
    // END OF UMPLE BEFORE INJECTION
    return (IN) ins.first();
  }


  /**
   * 
   * Return an iterator over the main 'ins' set.  Returned iterator will not show the elements in addedINs. The major latch should be held before entering.  The caller is responsible for releasing the major latch when they're finished with the iterator.
   * @return an iterator over the main 'ins' set.
   */
  // line 93 "../../../../INList.ump"
   public Iterator iterator(){
    // line 203 "../../../../Latches_INList.ump"
    assert majorLatch.isOwner();
    	//return //original();
    // END OF UMPLE BEFORE INJECTION
    return ins.iterator();
  }


  /**
   * 
   * Clear the entire list during recovery and at shutdown.
   */
  // line 100 "../../../../INList.ump"
   public void clear() throws DatabaseException{
    // line 211 "../../../../Latches_INList.ump"
    assert LatchSupport.countLatchesHeld() == 0;
    	majorLatch.acquire();
    	minorLatch.acquire();
    	//original();
    // END OF UMPLE BEFORE INJECTION
    ins.clear();
			clear_1:
addedINs.clear();
				minorLatch.release();
				releaseMajorLatch();
;
    // line 30 "../../../../MemoryBudget_INList.ump"
    //		original();
    		if (updateMemoryUsage) {
    			  envImpl.getMemoryBudget().refreshTreeMemoryUsage(0);
    			}
    // END OF UMPLE AFTER INJECTION
  }

  // line 105 "../../../../INList.ump"
   public void dump(){
    System.out.println("size=" + getSize());
			Iterator iter = ins.iterator();
			while (iter.hasNext()) {
					IN theIN = (IN) iter.next();
					System.out.println(
						"db=" + theIN.getDatabase().getId() + " nid=: " + theIN.getNodeId() + "/" + theIN.getLevel());
			}
  }


  /**
   * 
   * The locking hierarchy is:
   * 1. INList major latch.
   * 2. IN latch.
   * In other words, the INList major latch must be taken before any IN
   * latches to avoid deadlock.
   */
  // line 24 "../../../../Latches_INList.ump"
   public void latchMajor() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	majorLatch.acquire();
  }

  // line 31 "../../../../Latches_INList.ump"
   public void releaseMajorLatchIfHeld() throws DatabaseException{
    if (majorLatch.isOwner()) {
	    releaseMajorLatch();
	}
  }

  // line 39 "../../../../Latches_INList.ump"
   public void releaseMajorLatch() throws DatabaseException{
    /*
	 * Before we release the major latch, take a look at addedINs and see
	 * if anything has been added to it while we held the major latch.  If
	 * so, added it to ins.
	 */
        latchMinorAndDumpAddedINs();
	majorLatch.release();
  }

  // line 49 "../../../../Latches_INList.ump"
   private void dumpAddedINsIntoMajorSet(){
    if (addedINs.size() > 0) {
	    ins.addAll(addedINs);
	    addedINs.clear();
	}
  }

  // line 58 "../../../../Latches_INList.ump"
  public void latchMinorAndDumpAddedINs() throws DatabaseException{
    latchMinor();
        try {
            dumpAddedINsIntoMajorSet();
        } finally {
            releaseMinorLatch();
        }
  }

  // line 69 "../../../../Latches_INList.ump"
   private void latchMinor() throws DatabaseException{
    minorLatch.acquire();
  }

  // line 75 "../../../../Latches_INList.ump"
   private void releaseMinorLatch() throws DatabaseException{
    minorLatch.release();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../INList_static.ump"
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
  
    // line 6 "../../../../INList_static.ump"
    public  INList_add(INList _this, IN in){
      this._this=_this;
          this.in=in;
    }
  
    // line 10 "../../../../INList_static.ump"
    public void execute() throws DatabaseException{
      Label344:   ; //this.hook344();
          addToMajor=true;
  try{
          Label343:   ; //this.hook343();
    			Label345:    //this.hook345();
          _this.addAndSetMemory(_this.ins,in);
  Label345_1:   ;
  }
  finally{
           Label343_1:  ;//
  }
  				//End of hook345
  				//End of hook343
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 24 "../../../../INList_static.ump"
    protected INList _this ;
  // line 25 "../../../../INList_static.ump"
    protected IN in ;
  // line 26 "../../../../INList_static.ump"
    protected boolean enteredWithLatchHeld ;
  // line 27 "../../../../INList_static.ump"
    protected boolean addToMajor ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 36 "../../../../INList_static.ump"
  // line 4 "../../../../MemoryBudget_INList_inner.ump"
  public static class INList_addAndSetMemory
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public INList_addAndSetMemory()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 38 "../../../../INList_static.ump"
    public  INList_addAndSetMemory(INList _this, Set set, IN in){
      this._this=_this;
          this.set=set;
          this.in=in;
    }
  
    // line 43 "../../../../INList_static.ump"
    public void execute(){
      addOk=set.add(in);
          assert addOk : "failed adding in " + in.getNodeId();
      // line 6 "../../../../MemoryBudget_INList_inner.ump"
      //original();
              if (_this.updateMemoryUsage) {
                mb=_this.envImpl.getMemoryBudget();
                mb.updateTreeMemoryUsage(in.getInMemorySize());
                in.setInListResident(true);
              }
      // END OF UMPLE AFTER INJECTION
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 46 "../../../../INList_static.ump"
    protected INList _this ;
  // line 47 "../../../../INList_static.ump"
    protected Set set ;
  // line 48 "../../../../INList_static.ump"
    protected IN in ;
  // line 49 "../../../../INList_static.ump"
    protected boolean addOk ;
  // line 50 "../../../../INList_static.ump"
    protected MemoryBudget mb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../INList.ump"
  private static final String DEBUG_NAME = INList.class.getName() ;
// line 16 "../../../../INList.ump"
  private SortedSet ins = null ;
// line 18 "../../../../INList.ump"
  private EnvironmentImpl envImpl ;
// line 8 "../../../../Latches_INList.ump"
  private Set addedINs = null ;
// line 10 "../../../../Latches_INList.ump"
  private Latch majorLatch ;
// line 12 "../../../../Latches_INList.ump"
  private Latch minorLatch ;
// line 5 "../../../../MemoryBudget_INList.ump"
  private boolean updateMemoryUsage ;

  
}