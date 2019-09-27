/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../LockManager.ump"
public class LockManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 11 "../../../../LockManager.ump"
   protected void hook779(DbConfigManager configMgr) throws DatabaseException{
    nLockTables = configMgr.getInt(EnvironmentParams.N_LOCK_TABLES);
	original(configMgr);
  }

  // line 16 "../../../../LockManager.ump"
   protected void hook780(int lockTableIndex) throws DatabaseException{
    memoryBudget.updateLockMemoryUsage(TOTAL_LOCK_OVERHEAD, lockTableIndex);
	original(lockTableIndex);
  }

  // line 21 "../../../../LockManager.ump"
   protected void hook781(int lockTableIndex) throws DatabaseException{
    memoryBudget.updateLockMemoryUsage(REMOVE_TOTAL_LOCK_OVERHEAD, lockTableIndex);
	original(lockTableIndex);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../LockManager.ump"
  static final long TOTAL_LOCK_OVERHEAD = MemoryBudget.LOCK_OVERHEAD + MemoryBudget.HASHMAP_ENTRY_OVERHEAD
	    + MemoryBudget.LONG_OVERHEAD ;
// line 8 "../../../../LockManager.ump"
  private static final long REMOVE_TOTAL_LOCK_OVERHEAD = 0 - TOTAL_LOCK_OVERHEAD ;

  
}