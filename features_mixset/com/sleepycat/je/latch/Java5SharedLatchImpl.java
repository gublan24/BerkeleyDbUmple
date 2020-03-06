/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../Latches_Java5SharedLatchImpl.ump"
public class Java5SharedLatchImpl extends ReentrantReadWriteLock implements SharedLatch
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

  // line 22 "../../../../Latches_Java5SharedLatchImpl.ump"
  public  Java5SharedLatchImpl(){
    super(EnvironmentImpl.getFairLatches());
  }


  /**
   * 
   * Set the latch name, used for latches in objects instantiated from the
   * log.
   */
  // line 30 "../../../../Latches_Java5SharedLatchImpl.ump"
   public void setName(String name){
    this.name = name;
  }


  /**
   * 
   * If noteLatch is true, then track latch usage in the latchTable.
   */
  // line 37 "../../../../Latches_Java5SharedLatchImpl.ump"
   public void setNoteLatch(boolean noteLatch){
    this.noteLatch = noteLatch;
  }


  /**
   * 
   * Acquire a latch for exclusive/write access.
   * 
   * Wait for the latch if some other thread is holding it.  If there are
   * threads waiting for access, they will be granted the latch on a FIFO
   * basis if fair latches are set. When the method returns, the latch is
   * held for exclusive access.
   * 
   * @throws LatchException if the latch is already held by the current
   * thread for exclusive access.
   */
  // line 54 "../../../../Latches_Java5SharedLatchImpl.ump"
   public void acquireExclusive() throws DatabaseException{
    try {
	    if (isWriteLockedByCurrentThread()) {
		throw new LatchException(name + " already held");
	    }

	    writeLock().lock();

            assert (noteLatch ? noteLatch() : true);// intentional side effect;
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }

  // line 70 "../../../../Latches_Java5SharedLatchImpl.ump"
   public boolean acquireExclusiveNoWait() throws DatabaseException{
    try {
	    if (isWriteLockedByCurrentThread()) {
		throw new LatchException(name + " already held");
	    }

	    boolean ret = writeLock().tryLock();

            assert (noteLatch ? noteLatch() : true);// intentional side effect;
	    return ret;
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Acquire a latch for shared/read access.
   */
  // line 90 "../../../../Latches_Java5SharedLatchImpl.ump"
   public void acquireShared() throws DatabaseException{
    try {
	    readLock().lock();

            assert (noteLatch ?  noteLatch() : true);// intentional side effect
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Release an exclusive or shared latch.  If there are other thread(s)
   * waiting for the latch, they are woken up and granted the latch.
   */
  // line 106 "../../../../Latches_Java5SharedLatchImpl.ump"
   public void release() throws LatchNotHeldException{
    try {
	    if (isWriteLockedByCurrentThread()) {
		writeLock().unlock();
		return;
	    }

	    /*
	     * There's no way to tell if a readlock is held by the current
	     * thread so just try unlocking it.
	     */
	    readLock().unlock();
	} catch (IllegalMonitorStateException IMSE) {
	    return;
	} finally {
	    /* Intentional side effect. */
	    assert (noteLatch ? unNoteLatch() : true);
	}
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 131 "../../../../Latches_Java5SharedLatchImpl.ump"
   private boolean noteLatch() throws LatchException{
    return LatchSupport.latchTable.noteLatch(this);
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 139 "../../../../Latches_Java5SharedLatchImpl.ump"
   private boolean unNoteLatch(){
    return LatchSupport.latchTable.unNoteLatch(this, name);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 18 "../../../../Latches_Java5SharedLatchImpl.ump"
  private String name ;
// line 19 "../../../../Latches_Java5SharedLatchImpl.ump"
  private boolean noteLatch ;

  
}