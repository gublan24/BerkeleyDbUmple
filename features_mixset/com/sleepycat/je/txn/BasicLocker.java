/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.CursorImpl;
import com.sleepycat.je.LockStats;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Database;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

// line 3 "../../../../BasicLocker.ump"
// line 3 "../../../../DeleteOp_BasicLocker.ump"
// line 3 "../../../../INCompressor_BasicLocker.ump"
// line 3 "../../../../Statistics_BasicLocker.ump"
public class BasicLocker extends Locker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BasicLocker()
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


  /**
   * 
   * Creates a BasicLocker.
   */
  // line 25 "../../../../BasicLocker.ump"
   public  BasicLocker(EnvironmentImpl env) throws DatabaseException{
    super(env, false, false);
  }


  /**
   * 
   * BasicLockers always have a fixed id, because they are never used for recovery.
   */
  // line 32 "../../../../BasicLocker.ump"
   protected long generateId(TxnManager txnManager){
    return TxnManager.NULL_TXN_ID;
  }

  // line 36 "../../../../BasicLocker.ump"
   protected void checkState(boolean ignoreCalledByAbort) throws DatabaseException{
    
  }


  /**
   * 
   * @see Locker#lockInternal
   * @Override
   */
  // line 44 "../../../../BasicLocker.ump"
  public LockResult lockInternal(long nodeId, LockType lockType, boolean noWait, DatabaseImpl database) throws DatabaseException{
    synchronized (this) {
	    checkState(false);
	}
	long timeout = 0;
	boolean useNoWait = noWait || defaultNoWait;
	if (!useNoWait) {
	    synchronized (this) {
		timeout = lockTimeOutMillis;
	    }
	}
	LockGrantType grant = lockManager.lock(nodeId, this, lockType, timeout, useNoWait, database);
	return new LockResult(grant, null);
  }


  /**
   * 
   * Get the txn that owns the lock on this node. Return null if there's no owning txn found.
   */
  // line 62 "../../../../BasicLocker.ump"
   public Locker getWriteOwnerLocker(long nodeId) throws DatabaseException{
    return lockManager.getWriteOwnerLocker(new Long(nodeId));
  }


  /**
   * 
   * Get the abort LSN for this node in the txn that owns the lock on this node. Return null if there's no owning txn found.
   */
  // line 69 "../../../../BasicLocker.ump"
   public long getOwnerAbortLsn(long nodeId) throws DatabaseException{
    Locker ownerTxn = lockManager.getWriteOwnerLocker(new Long(nodeId));
	if (ownerTxn != null) {
	    return ownerTxn.getAbortLsn(nodeId);
	}
	return DbLsn.NULL_LSN;
  }


  /**
   * 
   * Is never transactional.
   */
  // line 80 "../../../../BasicLocker.ump"
   public boolean isTransactional(){
    return false;
  }


  /**
   * 
   * Is never serializable isolation.
   */
  // line 87 "../../../../BasicLocker.ump"
   public boolean isSerializableIsolation(){
    return false;
  }


  /**
   * 
   * Is never read-committed isolation.
   */
  // line 94 "../../../../BasicLocker.ump"
   public boolean isReadCommittedIsolation(){
    return false;
  }


  /**
   * 
   * No transactional locker is available.
   */
  // line 101 "../../../../BasicLocker.ump"
   public Txn getTxnLocker(){
    return null;
  }


  /**
   * 
   * Creates a new instance of this txn for the same environment.  No transactional locks are held by this object, so no locks are retained.
   */
  // line 108 "../../../../BasicLocker.ump"
   public Locker newNonTxnLocker() throws DatabaseException{
    return new BasicLocker(envImpl);
  }


  /**
   * 
   * Releases all locks, since all locks held by this locker are non-transactional.
   */
  // line 115 "../../../../BasicLocker.ump"
   public void releaseNonTxnLocks() throws DatabaseException{
    operationEnd(true);
  }


  /**
   * 
   * Release locks at the end of the transaction.
   */
  // line 122 "../../../../BasicLocker.ump"
   public void operationEnd() throws DatabaseException{
    operationEnd(true);
  }


  /**
   * 
   * Release locks at the end of the transaction.
   */
  // line 129 "../../../../BasicLocker.ump"
   public void operationEnd(boolean operationOK) throws DatabaseException{
    if (ownedLock != null) {
	    lockManager.release(ownedLock, this);
	    ownedLock = null;
	}
	if (ownedLockSet != null) {
	    Iterator iter = ownedLockSet.iterator();
	    while (iter.hasNext()) {
		Lock l = (Lock) iter.next();
		lockManager.release(l, this);
	    }
	    ownedLockSet.clear();
	}
    // line 9 "../../../../INCompressor_BasicLocker.ump"
    //	original(operationOK);
    			synchronized (this) {
    					if ((deleteInfo != null) && (deleteInfo.size() > 0)) {
    				envImpl.addToCompressorQueue(deleteInfo.values(), false);
    				deleteInfo.clear();
    	    }
    	}
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Transfer any MapLN locks to the db handle.
   */
  // line 147 "../../../../BasicLocker.ump"
   public void setHandleLockOwner(boolean ignore, Database dbHandle, boolean dbIsClosing) throws DatabaseException{
    if (dbHandle != null) {
	    if (!dbIsClosing) {
		transferHandleLockToHandle(dbHandle);
	    }
	    unregisterHandle(dbHandle);
	}
  }


  /**
   * 
   * This txn doesn't store cursors.
   */
  // line 159 "../../../../BasicLocker.ump"
   public void registerCursor(CursorImpl cursor) throws DatabaseException{
    
  }


  /**
   * 
   * This txn doesn't store cursors.
   */
  // line 165 "../../../../BasicLocker.ump"
   public void unRegisterCursor(CursorImpl cursor) throws DatabaseException{
    
  }


  /**
   * 
   * @return the abort LSN for this node.
   */
  // line 171 "../../../../BasicLocker.ump"
   public long getAbortLsn(long nodeId) throws DatabaseException{
    return DbLsn.NULL_LSN;
  }


  /**
   * 
   * @return a dummy WriteLockInfo for this node.
   */
  // line 178 "../../../../BasicLocker.ump"
   public WriteLockInfo getWriteLockInfo(long nodeId) throws DatabaseException{
    return WriteLockInfo.basicWriteLockInfo;
  }


  /**
   * 
   * Add a lock to set owned by this transaction.
   */
  // line 185 "../../../../BasicLocker.ump"
  public void addLock(Long nodeId, Lock lock, LockType type, LockGrantType grantStatus) throws DatabaseException{
    if (ownedLock == lock || (ownedLockSet != null && ownedLockSet.contains(lock))) {
	    return;
	}
	if (ownedLock == null) {
	    ownedLock = lock;
	} else {
	    if (ownedLockSet == null) {
		ownedLockSet = new HashSet();
	    }
	    ownedLockSet.add(lock);
	}
  }


  /**
   * 
   * Remove a lock from the set owned by this txn.
   */
  // line 202 "../../../../BasicLocker.ump"
  public void removeLock(long nodeId, Lock lock) throws DatabaseException{
    if (lock == ownedLock) {
	    ownedLock = null;
	} else if (ownedLockSet != null) {
	    ownedLockSet.remove(lock);
	}
  }


  /**
   * 
   * Always false for this txn.
   */
  // line 213 "../../../../BasicLocker.ump"
   public boolean createdNode(long nodeId) throws DatabaseException{
    return false;
  }


  /**
   * 
   * A lock is being demoted. Move it from the write collection into the read collection.
   */
  // line 220 "../../../../BasicLocker.ump"
  public void moveWriteToReadLock(long nodeId, Lock lock){
    
  }

  // line 6 "../../../../DeleteOp_BasicLocker.ump"
   public void markDeleteAtTxnEnd(DatabaseImpl db, boolean deleteAtCommit) throws DatabaseException{
    if (deleteAtCommit) {
					db.deleteAndReleaseINs();
			}
  }


  /**
   * 
   * stats
   */
  // line 9 "../../../../Statistics_BasicLocker.ump"
   public LockStats collectStats(LockStats stats) throws DatabaseException{
    if (ownedLock != null) {
					if (ownedLock.isOwnedWriteLock(this)) {
				stats.setNWriteLocks(stats.getNWriteLocks() + 1);
					} else {
				stats.setNReadLocks(stats.getNReadLocks() + 1);
					}
			}
			if (ownedLockSet != null) {
					Iterator iter = ownedLockSet.iterator();
					while (iter.hasNext()) {
				Lock l = (Lock) iter.next();
				if (l.isOwnedWriteLock(this)) {
						stats.setNWriteLocks(stats.getNWriteLocks() + 1);
				} else {
						stats.setNReadLocks(stats.getNReadLocks() + 1);
				}
					}
			}
			return stats;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../../../BasicLocker.ump"
  private Lock ownedLock ;
// line 19 "../../../../BasicLocker.ump"
  private Set ownedLockSet ;

  
}