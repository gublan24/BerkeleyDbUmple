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
// line 3 "../../../../Latches_SyncedLockManager.ump"
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
    LockAttemptResult temp =null;
    Label782:
synchronized (lockTableLatches[lockTableIndex]) { 
        
 ; // this.hook782(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
    temp= attemptLockInternal(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
    

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label782_1: ; // end of hook782
    return temp;
  }


  /**
   * 
   * @see LockManager#makeTimeoutMsg
   */
  // line 36 "../../../../SyncedLockManager.ump"
   protected String makeTimeoutMsg(String lockOrTxn, Locker locker, long nodeId, LockType type, LockGrantType grantType, Lock useLock, long timeout, long start, long now, DatabaseImpl database){
    int lockTableIndex = getLockTableIndex(nodeId);
    String temp; 
    Label783:
synchronized (lockTableLatches[lockTableIndex]) { 
        
 ; 
    temp = makeTimeoutMsgInternal(lockOrTxn, locker, nodeId, type, grantType, useLock, timeout, start, now,
        database);
    

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label783_1: ;// end of hook783
    return temp;
  }


  /**
   * 
   * @see LockManager#releaseAndNotifyTargets
   */
  // line 52 "../../../../SyncedLockManager.ump"
   protected Set releaseAndFindNotifyTargets(long nodeId, Lock lock, Locker locker, boolean removeFromLocker) throws DatabaseException{
    long nid = nodeId;
    if (nid == -1) {
      nid = lock.getNodeId().longValue();
    }
    int lockTableIndex = getLockTableIndex(nid);
    Set temp = null;
    Label784:
synchronized (lockTableLatches[lockTableIndex]) { 
        
 ; // this.hook784(nodeId, lock, locker, removeFromLocker, lockTableIndex);
    temp = releaseAndFindNotifyTargetsInternal(nodeId, lock, locker, removeFromLocker, lockTableIndex);
    

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label784_1: ;// end hook784
    return temp;
  }


  /**
   * 
   * @see LockManager#transfer
   */
  // line 69 "../../../../SyncedLockManager.ump"
  public void transfer(long nodeId, Locker owningLocker, Locker destLocker, boolean demoteToRead) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label785:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook785(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
	transferInternal(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
  

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label785_1: ;//end hook785
  }


  /**
   * 
   * @see LockManager#transferMultiple
   */
  // line 79 "../../../../SyncedLockManager.ump"
  public void transferMultiple(long nodeId, Locker owningLocker, Locker [] destLockers) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label786:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook786(nodeId, owningLocker, destLockers, lockTableIndex);
	transferMultipleInternal(nodeId, owningLocker, destLockers, lockTableIndex);
  

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label786_1: ;//end hook786
  }


  /**
   * 
   * @see LockManager#demote
   */
  // line 89 "../../../../SyncedLockManager.ump"
  public void demote(long nodeId, Locker locker) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
	Label787:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook787(nodeId, locker, lockTableIndex);
	demoteInternal(nodeId, locker, lockTableIndex);
	

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label787_1: ;// end hook787
  }


  /**
   * 
   * @see LockManager#isLocked
   */
  // line 99 "../../../../SyncedLockManager.ump"
  public boolean isLocked(Long nodeId){
    int lockTableIndex = getLockTableIndex(nodeId);
      boolean temp = true;
	    Label788:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook788(nodeId, lockTableIndex);
	    temp = isLockedInternal(nodeId, lockTableIndex);
      

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label788_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#isOwner
   */
  // line 111 "../../../../SyncedLockManager.ump"
  public boolean isOwner(Long nodeId, Locker locker, LockType type){
    int lockTableIndex = getLockTableIndex(nodeId);
      boolean temp  = false;
	    Label789:
//isOwner(Long nodeId, Locker locker, LockType type){
	   synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook789(nodeId, locker, type, lockTableIndex);
      temp = isOwnerInternal(nodeId, locker, type, lockTableIndex);
      

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label789_1: ;//end hook789
      return temp;
  }


  /**
   * 
   * @see LockManager#isWaiter
   */
  // line 123 "../../../../SyncedLockManager.ump"
  public boolean isWaiter(Long nodeId, Locker locker){
    int lockTableIndex = getLockTableIndex(nodeId);
      boolean temp  = false;
	    Label790:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook790(nodeId, locker, lockTableIndex);
	    temp = isWaiterInternal(nodeId, locker, lockTableIndex);
      

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label790_1: ;//end hook790
	    return temp;
  }


  /**
   * 
   * @see LockManager#nWaiters
   */
  // line 135 "../../../../SyncedLockManager.ump"
  public int nWaiters(Long nodeId){
    int lockTableIndex = getLockTableIndex(nodeId);
      int temp = 0;
	    Label791:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook791(nodeId, lockTableIndex);
	    temp= nWaitersInternal(nodeId, lockTableIndex);
      

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label791_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#nOwners
   */
  // line 147 "../../../../SyncedLockManager.ump"
  public int nOwners(Long nodeId){
    int lockTableIndex = getLockTableIndex(nodeId);
      int temp =0;
	    Label792:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook792(nodeId, lockTableIndex);
	    temp = nOwnersInternal(nodeId, lockTableIndex);
      

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label792_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#getWriterOwnerLocker
   */
  // line 159 "../../../../SyncedLockManager.ump"
  public Locker getWriteOwnerLocker(Long nodeId) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
      Locker temp = null;
	    Label793:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook793(nodeId, lockTableIndex);
	    temp = getWriteOwnerLockerInternal(nodeId, lockTableIndex);
	    

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label793_1: ;
      return temp;
  }


  /**
   * 
   * @see LockManager#validateOwnership
   */
  // line 172 "../../../../SyncedLockManager.ump"
   protected boolean validateOwnership(Long nodeId, Locker locker, LockType type, boolean flushFromWaiters, MemoryBudget mb) throws DatabaseException{
    int lockTableIndex = getLockTableIndex(nodeId);
      boolean temp = true;
	    Label794:
synchronized (lockTableLatches[lockTableIndex]) { 
        
           ;  //this.hook794(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex);
	    temp = validateOwnershipInternal(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex);
      

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label794_1: ;

	    return temp;
  }


  /**
   * 
   * @see LockManager#dumpLockTable
   */
  // line 185 "../../../../SyncedLockManager.ump"
   protected void dumpLockTable(LockStats stats) throws DatabaseException{
    for (int i = 0; i < nLockTables; i++) {
	      Label795:
synchronized (lockTableLatches[i]) { 
        
           ;  //this.hook795(stats, i);
	      dumpLockTableInternal(stats, i);
	      

	      // //original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	    }
Label795_1: ;//end hook795
	    }
  }

}