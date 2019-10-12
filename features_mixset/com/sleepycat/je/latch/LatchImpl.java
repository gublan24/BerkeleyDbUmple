/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
import java.util.List;
import java.util.ArrayList;

// line 3 "../../../../Latches_LatchImpl.ump"
// line 3 "../../../../Latches_LatchImpl_inner.ump"
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
  // line 26 "../../../../Latches_LatchImpl.ump"
   public  LatchImpl(String name, EnvironmentImpl env){
    this.name = name;
	this.env = env;
  }


  /**
   * 
   * Create a latch with no name, more optimal for shortlived latches.
   */
  // line 34 "../../../../Latches_LatchImpl.ump"
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
  // line 51 "../../../../Latches_LatchImpl.ump"
   public void acquire() throws DatabaseException{
    try {
	    Thread thread = Thread.currentThread();
	    LatchWaiter waitTarget = null;
	    synchronized (this) {
		if (thread == owner) {
		    this.hook422();
		    throw new LatchException(getNameString() + " already held");
		}
		if (owner == null) {
		    this.hook423();
		    owner = thread;
		} else {
		    if (waiters == null) {
			waiters = new ArrayList();
		    }
		    waitTarget = new LatchWaiter(thread);
		    waiters.add(waitTarget);
		    this.hook424();
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
   * Release the latch.  If there are other thread(s) waiting for the latch, one is woken up and granted the latch. If the latch was not owned by  the caller, just return;
   */
  // line 129 "../../../../Latches_LatchImpl.ump"
   public void releaseIfOwner(){
    doRelease(false);
  }


  /**
   * 
   * Release the latch.  If there are other thread(s) waiting for the latch, they are woken up and granted the latch.
   * @throws LatchNotHeldException if the latch is not currently held.
   */
  // line 137 "../../../../Latches_LatchImpl.ump"
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
  // line 147 "../../../../Latches_LatchImpl.ump"
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
		this.hook428();
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
  // line 180 "../../../../Latches_LatchImpl.ump"
   public boolean isOwner(){
    return Thread.currentThread() == owner;
  }


  /**
   * 
   * Used only for unit tests.
   * @return the thread that currently holds the latch for exclusive access.
   */
  // line 188 "../../../../Latches_LatchImpl.ump"
   public Thread owner(){
    return owner;
  }


  /**
   * 
   * For concocting exception messages
   */
  // line 210 "../../../../Latches_LatchImpl.ump"
   private String getNameString(){
    return LatchSupport.latchTable.getNameString(name);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 217 "../../../../Latches_LatchImpl.ump"
   private boolean noteLatch() throws LatchException{
    return LatchSupport.latchTable.noteLatch(this);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 224 "../../../../Latches_LatchImpl.ump"
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
   * Simple class that encapsulates a Thread to be 'notify()ed'.
   */
  // line 236 "../../../../Latches_LatchImpl.ump"
   protected void hook422() throws DatabaseException,InterruptedException{
    
  }

  // line 239 "../../../../Latches_LatchImpl.ump"
   protected void hook423() throws DatabaseException,InterruptedException{
    
  }

  // line 242 "../../../../Latches_LatchImpl.ump"
   protected void hook424() throws DatabaseException,InterruptedException{
    
  }

  // line 245 "../../../../Latches_LatchImpl.ump"
   protected void hook425() throws LatchException{
    
  }

  // line 248 "../../../../Latches_LatchImpl.ump"
   protected void hook426() throws LatchException{
    
  }

  // line 251 "../../../../Latches_LatchImpl.ump"
   protected void hook427() throws LatchException{
    
  }

  // line 254 "../../../../Latches_LatchImpl.ump"
   protected void hook428(){
    
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Latches_LatchImpl_inner.ump"
  public static class LatchWaiter
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //LatchWaiter Attributes
    private boolean active;
    private Thread thread;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public LatchWaiter(boolean aActive, Thread aThread)
    {
      active = aActive;
      thread = aThread;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setActive(boolean aActive)
    {
      boolean wasSet = false;
      active = aActive;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setThread(Thread aThread)
    {
      boolean wasSet = false;
      thread = aThread;
      wasSet = true;
      return wasSet;
    }
  
    public boolean getActive()
    {
      return active;
    }
  
    public Thread getThread()
    {
      return thread;
    }
  
    public void delete()
    {}
  
    // line 8 "../../../../Latches_LatchImpl_inner.ump"
    public  LatchWaiter(Thread thread){
      this.thread=thread;
          active=false;
    }
  
    // line 12 "../../../../Latches_LatchImpl_inner.ump"
     public String toString(){
      return "<LatchWaiter: " + thread + ">";
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../Latches_LatchImpl.ump"
  private static final String DEFAULT_LATCH_NAME = "LatchImpl" ;
// line 14 "../../../../Latches_LatchImpl.ump"
  private String name = null ;
// line 16 "../../../../Latches_LatchImpl.ump"
  private List waiters = null ;
// line 18 "../../../../Latches_LatchImpl.ump"
  private EnvironmentImpl env = null ;
// line 20 "../../../../Latches_LatchImpl.ump"
  private Thread owner = null ;

// line 41 "../../../../Latches_LatchImpl.ump"
  synchronized public void setName (String name) 
  {
    this.name = name;
  }

// line 104 "../../../../Latches_LatchImpl.ump"
  public synchronized boolean acquireNoWait () throws LatchException 
  {
    try {
	    Thread thread = Thread.currentThread();
	    if (thread == owner) {
		this.hook425();
		throw new LatchException(getNameString() + " already held");
	    }
	    if (owner == null) {
		owner = thread;
		this.hook426();
		assert noteLatch();
		return true;
	    } else {
		this.hook427();
		return false;
	    }
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }

// line 195 "../../../../Latches_LatchImpl.ump"
  public synchronized int nWaiters () 
  {
    return (waiters != null) ? waiters.size() : 0;
  }

// line 202 "../../../../Latches_LatchImpl.ump"
  public synchronized String toString () 
  {
    return LatchSupport.latchTable.toString(name, owner, waiters, 0);
  }

  
}