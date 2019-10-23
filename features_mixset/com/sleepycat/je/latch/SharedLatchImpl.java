/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

// line 3 "../../../../Latches_SharedLatchImpl.ump"
// line 3 "../../../../Latches_SharedLatchImpl_inner.ump"
public class SharedLatchImpl implements SharedLatch
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SharedLatchImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a shared latch.
   */
  // line 25 "../../../../Latches_SharedLatchImpl.ump"
   public  SharedLatchImpl(String name, EnvironmentImpl env){
    this.name = name;
	this.env = env;
  }

  // line 77 "../../../../Latches_SharedLatchImpl.ump"
   public boolean acquireExclusiveNoWait() throws DatabaseException{
    try {
	    Thread thread = Thread.currentThread();
	    int index = indexOf(thread);
	    if (index < 0) {
		if (waiters.size() == 0) {
		    Owner owner = new Owner(thread, Owner.EXCLUSIVE);
		    waiters.add(owner);
		    owner.nAcquires += 1;
		    Label431:           ;  //this.hook431();
		    assert (noteLatch ? noteLatch() : true);
		    return true;
		} else {
		    return false;
		}
	    } else {
		throw new LatchException(getNameString() + " reentrancy/upgrade not allowed");
	    }
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Returns the index of the first Owner for the given thread, or -1 if none.
   */
  // line 154 "../../../../Latches_SharedLatchImpl.ump"
   private int indexOf(Thread thread){
    Iterator i = waiters.iterator();
	for (int index = 0; i.hasNext(); index += 1) {
	    Owner owner = (Owner) i.next();
	    if (owner.thread == thread) {
		return index;
	    }
	}
	return -1;
  }


  /**
   * 
   * Returns the index of the first Owner waiting for a write lock, or Integer.MAX_VALUE if none.
   */
  // line 168 "../../../../Latches_SharedLatchImpl.ump"
   private int firstWriter(){
    Iterator i = waiters.iterator();
	for (int index = 0; i.hasNext(); index += 1) {
	    Owner owner = (Owner) i.next();
	    if (owner.type == Owner.EXCLUSIVE) {
		return index;
	    }
	}
	return Integer.MAX_VALUE;
  }


  /**
   * 
   * Holds the state of a single owner thread.
   */
  // line 182 "../../../../Latches_SharedLatchImpl.ump"
   private String getNameString(){
    return LatchSupport.latchTable.getNameString(name);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 189 "../../../../Latches_SharedLatchImpl.ump"
   private boolean noteLatch() throws LatchException{
    return LatchSupport.latchTable.noteLatch(this);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 196 "../../../../Latches_SharedLatchImpl.ump"
   private boolean unNoteLatch() throws LatchNotHeldException{
    return LatchSupport.latchTable.unNoteLatch(this, name);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Latches_SharedLatchImpl_inner.ump"
  public static class Owner
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //Owner Attributes
    private Thread thread;
    private int type;
    private int nAcquires;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Owner(Thread aThread, int aType, int aNAcquires)
    {
      thread = aThread;
      type = aType;
      nAcquires = aNAcquires;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setThread(Thread aThread)
    {
      boolean wasSet = false;
      thread = aThread;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setType(int aType)
    {
      boolean wasSet = false;
      type = aType;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setNAcquires(int aNAcquires)
    {
      boolean wasSet = false;
      nAcquires = aNAcquires;
      wasSet = true;
      return wasSet;
    }
  
    public Thread getThread()
    {
      return thread;
    }
  
    public int getType()
    {
      return type;
    }
  
    public int getNAcquires()
    {
      return nAcquires;
    }
  
    public void delete()
    {}
  
    // line 11 "../../../../Latches_SharedLatchImpl_inner.ump"
    public  Owner(Thread thread, int type){
      this.thread=thread;
          this.type=type;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+
              "type" + ":" + getType()+ "," +
              "nAcquires" + ":" + getNAcquires()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "thread" + "=" + (getThread() != null ? !getThread().equals(this)  ? getThread().toString().replaceAll("  ","    ") : "this" : "null");
    }  
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../Latches_SharedLatchImpl_inner.ump"
    static final int SHARED=0 ;
  // line 6 "../../../../Latches_SharedLatchImpl_inner.ump"
    static final int EXCLUSIVE=1 ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../Latches_SharedLatchImpl.ump"
  private String name = null ;
// line 15 "../../../../Latches_SharedLatchImpl.ump"
  private List waiters = new ArrayList() ;
// line 17 "../../../../Latches_SharedLatchImpl.ump"
  private EnvironmentImpl env = null ;
// line 19 "../../../../Latches_SharedLatchImpl.ump"
  private boolean noteLatch ;

// line 32 "../../../../Latches_SharedLatchImpl.ump"
  synchronized public void setName (String name) 
  {
    this.name = name;
  }

// line 39 "../../../../Latches_SharedLatchImpl.ump"
  synchronized public void setNoteLatch (boolean noteLatch) 
  {
    this.noteLatch = noteLatch;
  }

// line 48 "../../../../Latches_SharedLatchImpl.ump"
  public synchronized void acquireExclusive () throws DatabaseException 
  {
    try {
	    Thread thread = Thread.currentThread();
	    int index = indexOf(thread);
	    Owner owner;
	    if (index < 0) {
		owner = new Owner(thread, Owner.EXCLUSIVE);
		waiters.add(owner);
	    } else {
		throw new LatchException(getNameString() + " reentrancy/upgrade not allowed");
	    }
	    if (waiters.size() == 1) {
		Label429:           ;  //this.hook429();
	    } else {
		Label430:           ;  //this.hook430();
		while (waiters.get(0) != owner) {
		    wait();
		}
	    }
	    owner.nAcquires += 1;
	    assert (noteLatch ? noteLatch() : true);
	} catch (InterruptedException e) {
	    throw new RunRecoveryException(env, e);
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }

// line 103 "../../../../Latches_SharedLatchImpl.ump"
  public synchronized void acquireShared () throws DatabaseException 
  {
    try {
	    Thread thread = Thread.currentThread();
	    int index = indexOf(thread);
	    Owner owner;
	    if (index < 0) {
		owner = new Owner(thread, Owner.SHARED);
		waiters.add(owner);
	    } else {
		owner = (Owner) waiters.get(index);
	    }
	    while (indexOf(thread) > firstWriter()) {
		wait();
	    }
	    owner.nAcquires += 1;
	    Label432:           ;  //this.hook432();
	    assert (noteLatch ? noteLatch() : true);
	} catch (InterruptedException e) {
	    throw new RunRecoveryException(env, e);
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }

// line 130 "../../../../Latches_SharedLatchImpl.ump"
  public synchronized void release () throws LatchNotHeldException 
  {
    try {
	    Thread thread = Thread.currentThread();
	    int index = indexOf(thread);
	    if (index < 0 || index > firstWriter()) {
		return;
	    }
	    Owner owner = (Owner) waiters.get(index);
	    owner.nAcquires -= 1;
	    if (owner.nAcquires == 0) {
		waiters.remove(index);
		assert (noteLatch ? unNoteLatch() : true);
		notifyAll();
	    }
	    Label433:           ;  //this.hook433();
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }

// line 199 "../../../../Latches_SharedLatchImpl.ump"
  public synchronized boolean isWriteLockedByCurrentThread () 
  {
    if (waiters.size() > 0) {
	    Owner curOwner = (Owner) waiters.get(0);
	    return (curOwner.thread == Thread.currentThread() && curOwner.type == Owner.EXCLUSIVE);
	} else {
	    return false;
	}
  }

  
}