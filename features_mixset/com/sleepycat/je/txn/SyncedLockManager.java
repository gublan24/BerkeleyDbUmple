/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.LockStats;
import com.sleepycat.je.DatabaseException;
import java.util.Set;

// line 3 "../../../../SyncedLockManager.ump"
public class SyncedLockManager extends LockManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SyncedLockManager()
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

  // line 14 "../../../../SyncedLockManager.ump"
   public  SyncedLockManager(EnvironmentImpl envImpl) throws DatabaseException{
    super(envImpl);
  }


  /**
   * 
   * @see LockManager#attemptLock
   */
  // line 22 "../../../../SyncedLockManager.ump"
   protected LockAttemptResult attemptLock(Long nodeId, Locker locker, LockType type, boolean nonBlockingRequest) throws DatabaseException{
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label782: ;  //this.hook782(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
			throw new ReturnObject(attemptLockInternal(nodeId, locker, type, nonBlockingRequest, lockTableIndex));
      Label782_1://end of hook782
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (LockAttemptResult) r.value;
	}
  }


  /**
   * 
   * @see LockManager#makeTimeoutMsg
   */
  // line 38 "../../../../SyncedLockManager.ump"
   protected String makeTimeoutMsg(String lockOrTxn, Locker locker, long nodeId, LockType type, LockGrantType grantType, Lock useLock, long timeout, long start, long now, DatabaseImpl database){
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label783:           ;  //this.hook783(lockOrTxn, locker, nodeId, type, grantType, useLock, timeout, start, now, database, lockTableIndex);
	throw new ReturnObject(makeTimeoutMsgInternal(lockOrTxn, locker, nodeId, type, grantType, useLock, timeout,	start, now, database));
		  Label783_1://end of hook783
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (String) r.value;
	}
  }


  /**
   * 
   * @see LockManager#releaseAndNotifyTargets
   */
  // line 54 "../../../../SyncedLockManager.ump"
   protected Set releaseAndFindNotifyTargets(long nodeId, Lock lock, Locker locker, boolean removeFromLocker) throws DatabaseException{
    try {
	    long nid = nodeId;
	    if (nid == -1) {
		nid = lock.getNodeId().longValue();
	    }
	    int lockTableIndex = getLockTableIndex(nid);
	    Label784:           ;  //this.hook784(nodeId, lock, locker, removeFromLocker, lockTableIndex);
	throw new ReturnObject(		releaseAndFindNotifyTargetsInternal(nodeId, lock, locker, removeFromLocker, lockTableIndex));
	    Label784_1://end hook784
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (Set) r.value;
	}
  }


  /**
   * 
   * @see LockManager#transfer
   */
  // line 73 "../../../../SyncedLockManager.ump"
  public void transfer(long nodeId, Locker owningLocker, Locker destLocker, boolean demoteToRead) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label785:           ;  //this.hook785(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
	transferInternal(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
  Label785_1://end hook785
  }


  /**
   * 
   * @see LockManager#transferMultiple
   */
  // line 83 "../../../../SyncedLockManager.ump"
  public void transferMultiple(long nodeId, Locker owningLocker, Locker [] destLockers) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label786:           ;  //this.hook786(nodeId, owningLocker, destLockers, lockTableIndex);
	transferMultipleInternal(nodeId, owningLocker, destLockers, lockTableIndex);
  Label786_1://end hook786
  }


  /**
   * 
   * @see LockManager#demote
   */
  // line 93 "../../../../SyncedLockManager.ump"
  public void demote(long nodeId, Locker locker) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label787:           ;  //this.hook787(nodeId, locker, lockTableIndex);
	demoteInternal(nodeId, locker, lockTableIndex);
	Label787_1:// end hook787
  }


  /**
   * 
   * @see LockManager#isLocked
   */
  // line 103 "../../../../SyncedLockManager.ump"
  public boolean isLocked(Long nodeId){
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label788:           ;  //this.hook788(nodeId, lockTableIndex);
	throw new ReturnBoolean(isLockedInternal(nodeId, lockTableIndex));
      Label788_1: 
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * @see LockManager#isOwner
   */
  // line 118 "../../../../SyncedLockManager.ump"
  public boolean isOwner(Long nodeId, Locker locker, LockType type){
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label789:           ;  //this.hook789(nodeId, locker, type, lockTableIndex);
	throw new ReturnBoolean(isOwnerInternal(nodeId, locker, type, lockTableIndex));
      Label789_1://end hook789
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * @see LockManager#isWaiter
   */
  // line 133 "../../../../SyncedLockManager.ump"
  public boolean isWaiter(Long nodeId, Locker locker){
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label790:           ;  //this.hook790(nodeId, locker, lockTableIndex);
	throw new ReturnBoolean(isWaiterInternal(nodeId, locker, lockTableIndex));
      Label790_1://end hook790
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * @see LockManager#nWaiters
   */
  // line 148 "../../../../SyncedLockManager.ump"
  public int nWaiters(Long nodeId){
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label791:           ;  //this.hook791(nodeId, lockTableIndex);
	throw new ReturnInt(nWaitersInternal(nodeId, lockTableIndex));
      Label791_1://end hook791
	    throw ReturnHack.returnInt;
	} catch (ReturnInt r) {
	    return r.value;
	}
  }


  /**
   * 
   * @see LockManager#nOwners
   */
  // line 163 "../../../../SyncedLockManager.ump"
  public int nOwners(Long nodeId){
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label792:           ;  //this.hook792(nodeId, lockTableIndex);
	throw new ReturnInt(nOwnersInternal(nodeId, lockTableIndex));
      Label792_1://end hook792
	    throw ReturnHack.returnInt;
	} catch (ReturnInt r) {
	    return r.value;
	}
  }


  /**
   * 
   * @see LockManager#getWriterOwnerLocker
   */
  // line 178 "../../../../SyncedLockManager.ump"
  public Locker getWriteOwnerLocker(Long nodeId) throws DatabaseException{
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label793:           ;  //this.hook793(nodeId, lockTableIndex);
	throw new ReturnObject(getWriteOwnerLockerInternal(nodeId, lockTableIndex));
	    Label793_1:
  throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (Locker) r.value;
	}
  }


  /**
   * 
   * @see LockManager#validateOwnership
   */
  // line 194 "../../../../SyncedLockManager.ump"
   protected boolean validateOwnership(Long nodeId, Locker locker, LockType type, boolean flushFromWaiters, MemoryBudget mb) throws DatabaseException{
    try {
	    int lockTableIndex = getLockTableIndex(nodeId);
	    Label794:           ;  //this.hook794(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex);
	throw new ReturnBoolean(validateOwnershipInternal(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex));
      Label794_1://end hook794
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * @see LockManager#dumpLockTable
   */
  // line 209 "../../../../SyncedLockManager.ump"
   protected void dumpLockTable(LockStats stats) throws DatabaseException{
    for (int i = 0; i < nLockTables; i++) {
	    Label795:           ;  //this.hook795(stats, i);
	dumpLockTableInternal(stats, i);
	    Label795_1://end hook795
	}
  }

}