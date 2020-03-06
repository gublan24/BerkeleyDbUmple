/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
import java.util.List;
import java.util.ArrayList;

// line 3 "../../../../Latches_LatchImpl.ump"
// line 3 "../../../../Derivative_Latches_Statistics_LatchImpl.ump"
public class LatchImpl implements Latch
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LatchImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a latch.
   */
  // line 27 "../../../../Latches_LatchImpl.ump"
   public  LatchImpl(String name, EnvironmentImpl env){
    this.name = name;
	this.env = env;
  }


  /**
   * 
   * Create a latch with no name, more optimal for shortlived latches.
   */
  // line 35 "../../../../Latches_LatchImpl.ump"
   public  LatchImpl(EnvironmentImpl env){
    this.env = env;
	this.name = DEFAULT_LATCH_NAME;
  }


  /**
   * 
   * Acquire a latch for exclusive/write access. <p>Wait for the latch if some other thread is holding it.  If there are threads waiting for access, they will be granted the latch on a FIFO basis.  When the method returns, the latch is held for exclusive access.</p>
   * @throws LatchException if the latch is already held by the callingthread.
   * @throws RunRecoveryException if an InterruptedException exceptionoccurs.
   */
  // line 52 "../../../../Latches_LatchImpl.ump"
   public void acquire() throws DatabaseException{
    try {
	    Thread thread = Thread.currentThread();
	    LatchWaiter waitTarget = null;
	    synchronized (this) {
		if (thread == owner) {
		    Label422:
stats.nAcquiresSelfOwned++;
	//original();
           ;  
		    throw new LatchException(getNameString() + " already held");
		}
		if (owner == null) {
		    Label423:
stats.nAcquiresNoWaiters++;
	//original();
           ;  
		    owner = thread;
		} else {
		    if (waiters == null) {
			waiters = new ArrayList();
		    }
		    waitTarget = new LatchWaiter(thread);
		    waiters.add(waitTarget);
		    Label424:
stats.nAcquiresWithContention++;
	//original();
           ;  
		}
	    }
	    if (waitTarget != null) {
		synchronized (waitTarget) {
		    while (true) {
			if (waitTarget.active) {
			    if (thread == owner) {
				break;
			    } else {
				throw new DatabaseException("waitTarget.active but not owner");
			    }
			} else {
			    waitTarget.wait();
			    if (thread == owner) {
				break;
			    } else {
				continue;
			    }
			}
		    }
		}
	    }
	    assert noteLatch();
	} catch (InterruptedException e) {
	    throw new RunRecoveryException(env, e);
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
  // line 106 "../../../../Latches_LatchImpl.ump"
   public synchronized  boolean acquireNoWait() throws LatchException{
    try {
	    Thread thread = Thread.currentThread();
	    if (thread == owner) {
		Label425:
//synchronized boolean acquireNoWait()
	stats.nAcquiresSelfOwned++;
	//original();
           ;  
		throw new LatchException(getNameString() + " already held");
	    }
	    if (owner == null) {
		owner = thread;
		Label426:
//synchronized boolean acquireNoWait()
	stats.nAcquireNoWaitSuccessful++;
	//original();
           ;  
		assert noteLatch();
		return true;
	    } else {
		Label427:
//synchronized boolean acquireNoWait()
	stats.nAcquireNoWaitUnsuccessful++;
	//original();
           ;  
		return false;
	    }
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Release the latch.  If there are other thread(s) waiting for the latch, one is woken up and granted the latch. If the latch was not owned by  the caller, just return;
   */
  // line 130 "../../../../Latches_LatchImpl.ump"
   public void releaseIfOwner(){
    doRelease(false);
  }


  /**
   * 
   * Release the latch.  If there are other thread(s) waiting for the latch, they are woken up and granted the latch.
   * @throws LatchNotHeldException if the latch is not currently held.
   */
  // line 138 "../../../../Latches_LatchImpl.ump"
   public void release() throws LatchNotHeldException{
    if (doRelease(true)) {
	    throw new LatchNotHeldException(getNameString() + " not held");
	}
  }


  /**
   * 
   * Do the work of releasing the latch. Wake up any waiters.
   * @returns true if this latch was not owned by the caller.
   */
  // line 148 "../../../../Latches_LatchImpl.ump"
   private boolean doRelease(boolean checkHeld){
    LatchWaiter newOwner = null;
	try {
	    synchronized (this) {
		Thread thread = Thread.currentThread();
		if (thread != owner) {
		    return true;
		}
		if (waiters != null && waiters.size() > 0) {
		    newOwner = (LatchWaiter) waiters.remove(0);
		    owner = (Thread) newOwner.thread;
		} else {
		    owner = null;
		}
		Label428:
stats.nReleases++;
	//original();
           ;  //this.hook428();
		assert unNoteLatch(checkHeld);
	    }
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
	if (newOwner != null) {
	    synchronized (newOwner) {
		newOwner.active = true;
		newOwner.notifyAll();
	    }
	}
	return false;
  }


  /**
   * 
   * Return true if the current thread holds this latch.
   * @return true if we hold this latch.  False otherwise.
   */
  // line 181 "../../../../Latches_LatchImpl.ump"
   public boolean isOwner(){
    return Thread.currentThread() == owner;
  }


  /**
   * 
   * Used only for unit tests.
   * @return the thread that currently holds the latch for exclusive access.
   */
  // line 189 "../../../../Latches_LatchImpl.ump"
   public Thread owner(){
    return owner;
  }


  /**
   * 
   * Return the number of threads waiting.
   * @return the number of threads waiting for the latch.
   */
  // line 197 "../../../../Latches_LatchImpl.ump"
   public synchronized  int nWaiters(){
    return (waiters != null) ? waiters.size() : 0;
  }


  /**
   * 
   * Formats a latch owner and waiters.
   */
  // line 206 "../../../../Latches_LatchImpl.ump"
   public synchronized  String toString(){
    return LatchSupport.latchTable.toString(name, owner, waiters, 0);
  }


  /**
   * 
   * For concocting exception messages
   */
  // line 213 "../../../../Latches_LatchImpl.ump"
   private String getNameString(){
    return LatchSupport.latchTable.getNameString(name);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 220 "../../../../Latches_LatchImpl.ump"
   private boolean noteLatch() throws LatchException{
    return LatchSupport.latchTable.noteLatch(this);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 227 "../../../../Latches_LatchImpl.ump"
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
  // line 11 "../../../../Derivative_Latches_Statistics_LatchImpl.ump"
   public LatchStats getLatchStats(){
    LatchStats s = null;
	try {
	    s = (LatchStats) stats.clone();
	} catch (CloneNotSupportedException e) {
	}
	return s;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../Latches_LatchImpl.ump"
  private static final String DEFAULT_LATCH_NAME = "LatchImpl" ;
// line 15 "../../../../Latches_LatchImpl.ump"
  private String name = null ;
// line 17 "../../../../Latches_LatchImpl.ump"
  private List waiters = null ;
// line 19 "../../../../Latches_LatchImpl.ump"
  private EnvironmentImpl env = null ;
// line 21 "../../../../Latches_LatchImpl.ump"
  private Thread owner = null ;

// line 42 "../../../../Latches_LatchImpl.ump"
  synchronized public void setName (String name) 
  {
    this.name = name;
  }

// line 242 "../../../../Latches_LatchImpl.ump"
  static private class LatchWaiter 
  {
    boolean active;
	Thread thread;

	LatchWaiter(Thread thread) {
	    this.thread = thread;
	    active = false;
	}

	public String toString() {
	    return "<LatchWaiter: " + thread + ">";
	}
  }
// line 5 "../../../../Derivative_Latches_Statistics_LatchImpl.ump"
  private LatchStats stats = new LatchStats() ;

  
}