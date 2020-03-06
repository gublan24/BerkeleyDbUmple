/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

// line 3 "../../../../Latches_SharedLatchImpl.ump"
// line 3 "../../../../Derivative_Latches_Statistics_SharedLatchImpl.ump"
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
  // line 26 "../../../../Latches_SharedLatchImpl.ump"
   public  SharedLatchImpl(String name, EnvironmentImpl env){
    this.name = name;
	this.env = env;
  }


  /**
   * 
   * Acquire a latch for exclusive/write access.  Nesting is allowed, that is, the latch may be acquired more than once by the same thread for exclusive access.  However, if the thread already holds the latch for shared access, it cannot be upgraded and LatchException will be thrown. Wait for the latch if some other thread is holding it.  If there are threads waiting for access, they will be granted the latch on a FIFO basis.  When the method returns, the latch is held for exclusive access.
   * @throws LatchException if the latch is already held by the currentthread for shared access.
   * @throws RunRecoveryException if an InterruptedException exceptionoccurs.
   */
  // line 50 "../../../../Latches_SharedLatchImpl.ump"
   public synchronized  void acquireExclusive() throws DatabaseException{
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
		Label429:
//synchronized void acquireExclusive()

	stats.nAcquiresNoWaiters++;
	//original();
           ;  
	    } else {
		Label430:
//synchronized void acquireExclusive()
	stats.nAcquiresWithContention++;
	//original();
           ;  
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

  // line 78 "../../../../Latches_SharedLatchImpl.ump"
   public boolean acquireExclusiveNoWait() throws DatabaseException{
    try {
	    Thread thread = Thread.currentThread();
	    int index = indexOf(thread);
	    if (index < 0) {
		if (waiters.size() == 0) {
		    Owner owner = new Owner(thread, Owner.EXCLUSIVE);
		    waiters.add(owner);
		    owner.nAcquires += 1;
		    Label431:
stats.nAcquiresNoWaiters++;
	//original();
           ;  //this.hook431();
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
   * Acquire a latch for shared/read access.  Nesting is allowed, that is, the latch may be acquired more than once by the same thread.
   * @throws RunRecoveryException if an InterruptedException exceptionoccurs.
   */
  // line 105 "../../../../Latches_SharedLatchImpl.ump"
   public synchronized  void acquireShared() throws DatabaseException{
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
	    Label432:
stats.nAcquireSharedSuccessful++;
	//original();
           ;  
	    assert (noteLatch ? noteLatch() : true);
	} catch (InterruptedException e) {
	    throw new RunRecoveryException(env, e);
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Release an exclusive or shared latch.  If there are other thread(s) waiting for the latch, they are woken up and granted the latch.
   */
  // line 132 "../../../../Latches_SharedLatchImpl.ump"
   public synchronized  void release() throws LatchNotHeldException{
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
	    Label433:
// synchronized void release() 
	stats.nReleases++;
	//original();
           ;  
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Returns the index of the first Owner for the given thread, or -1 if none.
   */
  // line 155 "../../../../Latches_SharedLatchImpl.ump"
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
  // line 169 "../../../../Latches_SharedLatchImpl.ump"
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
  // line 183 "../../../../Latches_SharedLatchImpl.ump"
   private String getNameString(){
    return LatchSupport.latchTable.getNameString(name);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 208 "../../../../Latches_SharedLatchImpl.ump"
   private boolean noteLatch() throws LatchException{
    return LatchSupport.latchTable.noteLatch(this);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 215 "../../../../Latches_SharedLatchImpl.ump"
   private boolean unNoteLatch() throws LatchNotHeldException{
    return LatchSupport.latchTable.unNoteLatch(this, name);
  }

  // line 219 "../../../../Latches_SharedLatchImpl.ump"
   public synchronized  boolean isWriteLockedByCurrentThread(){
    if (waiters.size() > 0) {
	    Owner curOwner = (Owner) waiters.get(0);
	    return (curOwner.thread == Thread.currentThread() && curOwner.type == Owner.EXCLUSIVE);
	} else {
	    return false;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../Latches_SharedLatchImpl.ump"
  private String name = null ;
// line 16 "../../../../Latches_SharedLatchImpl.ump"
  private List waiters = new ArrayList() ;
// line 18 "../../../../Latches_SharedLatchImpl.ump"
  private EnvironmentImpl env = null ;
// line 20 "../../../../Latches_SharedLatchImpl.ump"
  private boolean noteLatch ;

// line 33 "../../../../Latches_SharedLatchImpl.ump"
  synchronized public void setName (String name) 
  {
    this.name = name;
  }

// line 40 "../../../../Latches_SharedLatchImpl.ump"
  synchronized public void setNoteLatch (boolean noteLatch) 
  {
    this.noteLatch = noteLatch;
  }

// line 188 "../../../../Latches_SharedLatchImpl.ump"
  private static class Owner 
  {
    static final int SHARED = 0;
        static final int EXCLUSIVE = 1;

        Thread thread;
        int type;
        int nAcquires;

        Owner(Thread thread, int type) {
            this.thread = thread;
            this.type = type;
        }
  }
// line 5 "../../../../Derivative_Latches_Statistics_SharedLatchImpl.ump"
  private LatchStats stats = new LatchStats() ;

  
}