/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.util.concurrent.locks.ReentrantLock;

// line 3 "../../../../Latches_Java5LatchImpl.ump"
// line 3 "../../../../Latches_Java5LatchImpl_inner.ump"
// line 3 "../../../../Derivative_Latches_Statistics_Java5LatchImpl.ump"
public class Java5LatchImpl implements Latch
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  // Default constructor has been disabled.  


  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 17 "../../../../Latches_Java5LatchImpl.ump"
  public  Java5LatchImpl(){
    lock = new JEReentrantLock(EnvironmentImpl.getFairLatches());
  }


  /**
   * 
   * Set the latch name, used for latches in objects instantiated from the log.
   */
  // line 24 "../../../../Latches_Java5LatchImpl.ump"
   public void setName(String name){
    this.name = name;
  }


  /**
   * 
   * Acquire a latch for exclusive/write access. <p>Wait for the latch if some other thread is holding it.  If there are threads waiting for access, they will be granted the latch on a FIFO basis.  When the method returns, the latch is held for exclusive access.</p>
   * @throws LatchException if the latch is already held by the callingthread.
   */
  // line 32 "../../../../Latches_Java5LatchImpl.ump"
   public void acquire() throws DatabaseException{
    try {
	    if (lock.isHeldByCurrentThread()) {
		Label417:
stats.nAcquiresSelfOwned++;
	//original();
           ;  //this.hook417();
		throw new LatchException(name + " already held");
	    }
	    Label416:
if (lock.isLocked()) {
	    stats.nAcquiresWithContention++;
	} else {
	    stats.nAcquiresNoWaiters++;
	}
	//original();
           ;  //this.hook416();
	    lock.lock();
	    assert noteLatch();
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Acquire a latch for exclusive/write access, but do not block if it's not available.
   * @return true if the latch was acquired, false if it is not available.
   * @throws LatchException if the latch is already held by the callingthread.
   */
  // line 51 "../../../../Latches_Java5LatchImpl.ump"
   public boolean acquireNoWait() throws LatchException{
    try {
	    if (lock.isHeldByCurrentThread()) {
		Label418:
stats.nAcquiresSelfOwned++;
	//original();
           ;  //this.hook418();
		throw new LatchException(name + " already held");
	    }
	    boolean ret = lock.tryLock();
	    if (ret) {
		assert noteLatch();
		Label419:
stats.nAcquireNoWaitSuccessful++;
	//original();
           ;  //this.hook419();
	    } else {
		Label420:
stats.nAcquireNoWaitUnsuccessful++;
	//original();
           ;  //this.hook420();
	    }
	    return ret;
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Release the latch.  If there are other thread(s) waiting for the latch, one is woken up and granted the latch. If the latch was not owned by  the caller, just return;
   */
  // line 73 "../../../../Latches_Java5LatchImpl.ump"
   public void releaseIfOwner(){
    doRelease(false);
  }


  /**
   * 
   * Release the latch.  If there are other thread(s) waiting for the latch, they are woken up and granted the latch.
   * @throws LatchNotHeldException if the latch is not currently held.
   */
  // line 81 "../../../../Latches_Java5LatchImpl.ump"
   public void release() throws LatchNotHeldException{
    if (doRelease(true)) {
	    throw new LatchNotHeldException(name + " not held");
	}
  }


  /**
   * 
   * Do the work of releasing the latch. Wake up any waiters.
   * @returns true if this latch was not owned by the caller.
   */
  // line 91 "../../../../Latches_Java5LatchImpl.ump"
   private boolean doRelease(boolean checkHeld){
    try {
	    if (!lock.isHeldByCurrentThread()) {
		return true;
	    }
	    lock.unlock();
	    Label421:
stats.nReleases++;
	//original();
           ;  //this.hook421();
	    assert unNoteLatch(checkHeld);
	} catch (IllegalMonitorStateException IMSE) {
	    return true;
	}
	return false;
  }


  /**
   * 
   * Return true if the current thread holds this latch.
   * @return true if we hold this latch.  False otherwise.
   */
  // line 109 "../../../../Latches_Java5LatchImpl.ump"
   public boolean isOwner(){
    return lock.isHeldByCurrentThread();
  }


  /**
   * 
   * Used only for unit tests.
   * @return the thread that currently holds the latch for exclusive access.
   */
  // line 117 "../../../../Latches_Java5LatchImpl.ump"
   public Thread owner(){
    return lock.getOwner();
  }


  /**
   * 
   * Return the number of threads waiting.
   * @return the number of threads waiting for the latch.
   */
  // line 125 "../../../../Latches_Java5LatchImpl.ump"
   public int nWaiters(){
    return lock.getQueueLength();
  }


  /**
   * 
   * Formats a latch owner and waiters.
   */
  // line 132 "../../../../Latches_Java5LatchImpl.ump"
   public String toString(){
    return lock.toString();
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 139 "../../../../Latches_Java5LatchImpl.ump"
   private boolean noteLatch() throws LatchException{
    return LatchSupport.latchTable.noteLatch(this);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 146 "../../../../Latches_Java5LatchImpl.ump"
   private boolean unNoteLatch(boolean checkHeld){
    if (checkHeld) {
	    return LatchSupport.latchTable.unNoteLatch(this, name);
	} else {
	    LatchSupport.latchTable.unNoteLatch(this, name);
	    return true;
	}
  }


  /**
   * 
   * @return a LatchStats object with information about this latch.
   */
  // line 11 "../../../../Derivative_Latches_Statistics_Java5LatchImpl.ump"
   public LatchStats getLatchStats(){
    LatchStats s = null;
	try {
	    s = (LatchStats) stats.clone();
	} catch (CloneNotSupportedException e) {
	}
	return s;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Latches_Java5LatchImpl_inner.ump"
  public static class JEReentrantLock extends ReentrantLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public JEReentrantLock()
    {
      super();
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 9 "../../../../Latches_Java5LatchImpl_inner.ump"
    public  JEReentrantLock(boolean fair){
      super(fair);
    }
  
    // line 12 "../../../../Latches_Java5LatchImpl_inner.ump"
     protected Thread getOwner(){
      return super.getOwner();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../Latches_Java5LatchImpl.ump"
  private JEReentrantLock lock ;
// line 12 "../../../../Latches_Java5LatchImpl.ump"
  private String name ;
// line 5 "../../../../Derivative_Latches_Statistics_Java5LatchImpl.ump"
  private LatchStats stats = new LatchStats() ;

  
}