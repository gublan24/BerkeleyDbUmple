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

// line 3 "../../../../DummyLockManager.ump"
public class DummyLockManager extends LockManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DummyLockManager()
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

  // line 14 "../../../../DummyLockManager.ump"
   public  DummyLockManager(EnvironmentImpl envImpl) throws DatabaseException{
    super(envImpl);
  }


  /**
   * 
   * @see LockManager#attemptLock
   */
  // line 22 "../../../../DummyLockManager.ump"
   protected LockAttemptResult attemptLock(Long nodeId, Locker locker, LockType type, boolean nonBlockingRequest) throws DatabaseException{
    return new LockAttemptResult(null, LockGrantType.NEW, true);
  }


  /**
   * 
   * @see LockManager#makeTimeoutMsg
   */
  // line 30 "../../../../DummyLockManager.ump"
   protected String makeTimeoutMsg(String lockOrTxn, Locker locker, long nodeId, LockType type, LockGrantType grantType, Lock useLock, long timeout, long start, long now, DatabaseImpl database){
    return null;
  }


  /**
   * 
   * @see LockManager#releaseAndNotifyTargets
   */
  // line 38 "../../../../DummyLockManager.ump"
   protected Set releaseAndFindNotifyTargets(long nodeId, Lock lock, Locker locker, boolean removeFromLocker) throws DatabaseException{
    return null;
  }


  /**
   * 
   * @see LockManager#transfer
   */
  // line 45 "../../../../DummyLockManager.ump"
  public void transfer(long nodeId, Locker owningLocker, Locker destLocker, boolean demoteToRead) throws DatabaseException{
    
  }


  /**
   * 
   * @see LockManager#transferMultiple
   */
  // line 51 "../../../../DummyLockManager.ump"
  public void transferMultiple(long nodeId, Locker owningLocker, Locker [] destLockers) throws DatabaseException{
    
  }


  /**
   * 
   * @see LockManager#demote
   */
  // line 57 "../../../../DummyLockManager.ump"
  public void demote(long nodeId, Locker locker) throws DatabaseException{
    
  }


  /**
   * 
   * @see LockManager#isLocked
   */
  // line 63 "../../../../DummyLockManager.ump"
  public boolean isLocked(Long nodeId){
    return false;
  }


  /**
   * 
   * @see LockManager#isOwner
   */
  // line 70 "../../../../DummyLockManager.ump"
  public boolean isOwner(Long nodeId, Locker locker, LockType type){
    return false;
  }


  /**
   * 
   * @see LockManager#isWaiter
   */
  // line 77 "../../../../DummyLockManager.ump"
  public boolean isWaiter(Long nodeId, Locker locker){
    return false;
  }


  /**
   * 
   * @see LockManager#nWaiters
   */
  // line 84 "../../../../DummyLockManager.ump"
  public int nWaiters(Long nodeId){
    return 0;
  }


  /**
   * 
   * @see LockManager#nOwners
   */
  // line 91 "../../../../DummyLockManager.ump"
  public int nOwners(Long nodeId){
    return 0;
  }


  /**
   * 
   * @see LockManager#getWriterOwnerLocker
   */
  // line 98 "../../../../DummyLockManager.ump"
  public Locker getWriteOwnerLocker(Long nodeId) throws DatabaseException{
    return null;
  }


  /**
   * 
   * @see LockManager#validateOwnership
   */
  // line 106 "../../../../DummyLockManager.ump"
   protected boolean validateOwnership(Long nodeId, Locker locker, LockType type, boolean flushFromWaiters, MemoryBudget mb) throws DatabaseException{
    return true;
  }


  /**
   * 
   * @see LockManager#dumpLockTable
   */
  // line 113 "../../../../DummyLockManager.ump"
   protected void dumpLockTable(LockStats stats) throws DatabaseException{
    
  }

}