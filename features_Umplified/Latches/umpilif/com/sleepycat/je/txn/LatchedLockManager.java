/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.latch.Latch;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.LockStats;
import com.sleepycat.je.DatabaseException;
import java.util.Set;

// line 3 "../../../../LatchedLockManager.ump"
public class LatchedLockManager extends LockManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LatchedLockManager()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 15 "../../../../LatchedLockManager.ump"
   public  LatchedLockManager(EnvironmentImpl envImpl) throws DatabaseException{
    super(envImpl);
  }


  /**
   * 
   * @see LockManager#attemptLock
   */
  // line 23 "../../../../LatchedLockManager.ump"
   protected LockAttemptResult attemptLock(Long nodeId, Locker locker, LockType type, boolean nonBlockingRequest) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return attemptLockInternal(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#makeTimeoutMsg
   */
  // line 39 "../../../../LatchedLockManager.ump"
   protected String makeTimeoutMsg(String lockOrTxn, Locker locker, long nodeId, LockType type, LockGrantType grantType, Lock useLock, long timeout, long start, long now, DatabaseImpl database) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return makeTimeoutMsgInternal(lockOrTxn, locker, nodeId, type, grantType, useLock, timeout, start, now,
		    database);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#releaseAndNotifyTargets
   */
  // line 55 "../../../../LatchedLockManager.ump"
   protected Set releaseAndFindNotifyTargets(long nodeId, Lock lock, Locker locker, boolean removeFromLocker) throws DatabaseException{
    long nid = nodeId;
	if (nid == -1) {
	    nid = lock.getNodeId().longValue();
	}
	int lockTableIndex = getLockTableIndex(nid);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return releaseAndFindNotifyTargetsInternal(nodeId, lock, locker, removeFromLocker, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#transfer
   */
  // line 73 "../../../../LatchedLockManager.ump"
  public void transfer(long nodeId, Locker owningLocker, Locker destLocker, boolean demoteToRead) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    transferInternal(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#transferMultiple
   */
  // line 87 "../../../../LatchedLockManager.ump"
  public void transferMultiple(long nodeId, Locker owningLocker, Locker [] destLockers) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    transferMultipleInternal(nodeId, owningLocker, destLockers, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#demote
   */
  // line 101 "../../../../LatchedLockManager.ump"
  public void demote(long nodeId, Locker locker) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    demoteInternal(nodeId, locker, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#isLocked
   */
  // line 115 "../../../../LatchedLockManager.ump"
  public boolean isLocked(Long nodeId) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return isLockedInternal(nodeId, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#isOwner
   */
  // line 129 "../../../../LatchedLockManager.ump"
  public boolean isOwner(Long nodeId, Locker locker, LockType type) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return isOwnerInternal(nodeId, locker, type, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#isWaiter
   */
  // line 143 "../../../../LatchedLockManager.ump"
  public boolean isWaiter(Long nodeId, Locker locker) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return isWaiterInternal(nodeId, locker, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#nWaiters
   */
  // line 157 "../../../../LatchedLockManager.ump"
  public int nWaiters(Long nodeId) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return nWaitersInternal(nodeId, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#nOwners
   */
  // line 171 "../../../../LatchedLockManager.ump"
  public int nOwners(Long nodeId) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return nOwnersInternal(nodeId, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#getWriterOwnerLocker
   */
  // line 185 "../../../../LatchedLockManager.ump"
  public Locker getWriteOwnerLocker(Long nodeId) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return getWriteOwnerLockerInternal(nodeId, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#validateOwnership
   */
  // line 200 "../../../../LatchedLockManager.ump"
   protected boolean validateOwnership(Long nodeId, Locker locker, LockType type, boolean flushFromWaiters, MemoryBudget mb) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Latch latch = lockTableLatches[lockTableIndex];
	latch.acquire();
	try {
	    return validateOwnershipInternal(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex);
	} finally {
	    latch.release();
	}
  }


  /**
   * 
   * @see LockManager#dumpLockTable
   */
  // line 214 "../../../../LatchedLockManager.ump"
   protected void dumpLockTable(LockStats stats) throws DatabaseException{
    for (int i = 0; i < nLockTables; i++) {
	    lockTableLatches[i].acquire();
	    try {
		dumpLockTableInternal(stats, i);
	    } finally {
		lockTableLatches[i].release();
	    }
	}
  }

}