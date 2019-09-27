/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../SyncedLockManager.ump"
public class SyncedLockManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SyncedLockManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../SyncedLockManager.ump"
   protected void hook782(Long nodeId, Locker locker, LockType type, boolean nonBlockingRequest, int lockTableIndex) throws DatabaseException{
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
	}
  }

  // line 14 "../../../../SyncedLockManager.ump"
   protected void hook783(String lockOrTxn, Locker locker, long nodeId, LockType type, LockGrantType grantType, Lock useLock, long timeout, long start, long now, DatabaseImpl database, int lockTableIndex){
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(lockOrTxn, locker, nodeId, type, grantType, useLock, timeout, start, now, database,
		    lockTableIndex);
	}
  }

  // line 22 "../../../../SyncedLockManager.ump"
   protected void hook784(long nodeId, Lock lock, Locker locker, boolean removeFromLocker, int lockTableIndex) throws DatabaseException{
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, lock, locker, removeFromLocker, lockTableIndex);
	}
  }

  // line 29 "../../../../SyncedLockManager.ump"
   protected void hook785(long nodeId, Locker owningLocker, Locker destLocker, boolean demoteToRead, int lockTableIndex) throws DatabaseException{
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, owningLocker, destLocker, demoteToRead, lockTableIndex);
	}
  }

  // line 36 "../../../../SyncedLockManager.ump"
   protected void hook786(long nodeId, Locker owningLocker, Locker [] destLockers, int lockTableIndex) throws DatabaseException{
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, owningLocker, destLockers, lockTableIndex);
	}
  }

  // line 42 "../../../../SyncedLockManager.ump"
   protected void hook787(long nodeId, Locker locker, int lockTableIndex) throws DatabaseException{
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, locker, lockTableIndex);
	}
  }

  // line 48 "../../../../SyncedLockManager.ump"
   protected void hook788(Long nodeId, int lockTableIndex){
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, lockTableIndex);
	}
  }

  // line 54 "../../../../SyncedLockManager.ump"
   protected void hook789(Long nodeId, Locker locker, LockType type, int lockTableIndex){
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, locker, type, lockTableIndex);
	}
  }

  // line 60 "../../../../SyncedLockManager.ump"
   protected void hook790(Long nodeId, Locker locker, int lockTableIndex){
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, locker, lockTableIndex);
	}
  }

  // line 66 "../../../../SyncedLockManager.ump"
   protected void hook791(Long nodeId, int lockTableIndex){
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, lockTableIndex);
	}
  }

  // line 72 "../../../../SyncedLockManager.ump"
   protected void hook792(Long nodeId, int lockTableIndex){
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, lockTableIndex);
	}
  }

  // line 78 "../../../../SyncedLockManager.ump"
   protected void hook793(Long nodeId, int lockTableIndex) throws DatabaseException{
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, lockTableIndex);
	}
  }

  // line 85 "../../../../SyncedLockManager.ump"
   protected void hook794(Long nodeId, Locker locker, LockType type, boolean flushFromWaiters, MemoryBudget mb, int lockTableIndex) throws DatabaseException{
    synchronized (lockTableLatches[lockTableIndex]) {
	    original(nodeId, locker, type, flushFromWaiters, mb, lockTableIndex);
	}
  }

  // line 91 "../../../../SyncedLockManager.ump"
   protected void hook795(LockStats stats, int i) throws DatabaseException{
    synchronized (lockTableLatches[i]) {
	    original(stats, i);
	}
  }

}