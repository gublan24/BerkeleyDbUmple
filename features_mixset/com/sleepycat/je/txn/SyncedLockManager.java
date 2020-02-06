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
    int lockTableIndex = getLockTableIndex(nodeId);
    Label782: ; // this.hook782(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
    LockAttemptResult temp = attemptLockInternal(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
    Label782_1: ; // end of hook782
    return temp;
  }


  /**
   * 
   * @see LockManager#makeTimeoutMsg
   */
  // line 35 "../../../../SyncedLockManager.ump"
   protected String makeTimeoutMsg(String lockOrTxn, Locker locker, long nodeId, LockType type, LockGrantType grantType, Lock useLock, long timeout, long start, long now, DatabaseImpl database){
    int lockTableIndex = getLockTableIndex(nodeId);
    Label783: ; 
    String temp = makeTimeoutMsgInternal(lockOrTxn, locker, nodeId, type, grantType, useLock, timeout, start, now,
        database);
    Label783_1: ;// end of hook783
    return temp;
  }


  /**
   * 
   * @see LockManager#releaseAndNotifyTargets
   */
  // line 50 "../../../../SyncedLockManager.ump"
   protected Set releaseAndFindNotifyTargets(long nodeId, Lock lock, Locker locker, boolean removeFromLocker) throws DatabaseException{
    long nid = nodeId;
    if (nid == -1) {
      nid = lock.getNodeId().longValue();
    }
    int lockTableIndex = getLockTableIndex(nid);
    Label784: ; // this.hook784(nodeId, lock, locker, removeFromLocker, lockTableIndex);
    Set temp = releaseAndFindNotifyTargetsInternal(nodeId, lock, locker, removeFromLocker, lockTableIndex);
    Label784_1: ;// end hook784
    return temp;
  }


  /**
   * 
   * @see LockManager#transfer
   */
  // line 66 "../../../../SyncedLockManager.ump"
  public void transfer(long nodeId, Locker owningLocker, Locker destLocker, boolean demoteToRead) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label785:           ;  //this.hook785(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
	transferInternal(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
  Label785_1: ;//end hook785
  }


  /**
   * 
   * @see LockManager#transferMultiple
   */
  // line 76 "../../../../SyncedLockManager.ump"
  public void transferMultiple(long nodeId, Locker owningLocker, Locker [] destLockers) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label786:           ;  //this.hook786(nodeId, owningLocker, destLockers, lockTableIndex);
	transferMultipleInternal(nodeId, owningLocker, destLockers, lockTableIndex);
  Label786_1: ;//end hook786
  }


  /**
   * 
   * @see LockManager#demote
   */
  // line 86 "../../../../SyncedLockManager.ump"
  public void demote(long nodeId, Locker locker) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label787:           ;  //this.hook787(nodeId, locker, lockTableIndex);
	demoteInternal(nodeId, locker, lockTableIndex);
	Label787_1: ;// end hook787
  }


  /**
   * 
   * @see LockManager#isLocked
   */
  // line 96 "../../../../SyncedLockManager.ump"
  public boolean isLocked(Long nodeId){
    int lockTableIndex = getLockTableIndex(nodeId);
	    Label788:           ;  //this.hook788(nodeId, lockTableIndex);
	    boolean temp = isLockedInternal(nodeId, lockTableIndex);
      Label788_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#isOwner
   */
  // line 107 "../../../../SyncedLockManager.ump"
  public boolean isOwner(Long nodeId, Locker locker, LockType type){
    int lockTableIndex = getLockTableIndex(nodeId);
	    Label789:           ;  //this.hook789(nodeId, locker, type, lockTableIndex);
	    boolean temp = isOwnerInternal(nodeId, locker, type, lockTableIndex);
      Label789_1: ;//end hook789
      return temp;
  }


  /**
   * 
   * @see LockManager#isWaiter
   */
  // line 118 "../../../../SyncedLockManager.ump"
  public boolean isWaiter(Long nodeId, Locker locker){
    int lockTableIndex = getLockTableIndex(nodeId);
	    Label790:           ;  //this.hook790(nodeId, locker, lockTableIndex);
	    boolean temp = isWaiterInternal(nodeId, locker, lockTableIndex);
      Label790_1: ;//end hook790
	    return temp;
  }


  /**
   * 
   * @see LockManager#nWaiters
   */
  // line 129 "../../../../SyncedLockManager.ump"
  public int nWaiters(Long nodeId){
    int lockTableIndex = getLockTableIndex(nodeId);
	    Label791:           ;  //this.hook791(nodeId, lockTableIndex);
	    int temp= nWaitersInternal(nodeId, lockTableIndex);
      Label791_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#nOwners
   */
  // line 140 "../../../../SyncedLockManager.ump"
  public int nOwners(Long nodeId){
    int lockTableIndex = getLockTableIndex(nodeId);
	    Label792:           ;  //this.hook792(nodeId, lockTableIndex);
	    int temp = nOwnersInternal(nodeId, lockTableIndex);
      Label792_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#getWriterOwnerLocker
   */
  // line 151 "../../../../SyncedLockManager.ump"
  public Locker getWriteOwnerLocker(Long nodeId) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	    Label793:           ;  //this.hook793(nodeId, lockTableIndex);
	    Locker temp = getWriteOwnerLockerInternal(nodeId, lockTableIndex);
	    Label793_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#validateOwnership
   */
  // line 163 "../../../../SyncedLockManager.ump"
   protected boolean validateOwnership(Long nodeId, Locker locker, LockType type, boolean flushFromWaiters, MemoryBudget mb) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	    Label794:           ;  //this.hook794(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex);
	    boolean temp = validateOwnershipInternal(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex);
      Label794_1: ;

	    return temp;
  }


  /**
   * 
   * @see LockManager#dumpLockTable
   */
  // line 175 "../../../../SyncedLockManager.ump"
   protected void dumpLockTable(LockStats stats) throws DatabaseException{
    for (int i = 0; i < nLockTables; i++) {
	      Label795:           ;  //this.hook795(stats, i);
	      dumpLockTableInternal(stats, i);
	      Label795_1: ;//end hook795
	    }
  }

}