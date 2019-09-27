/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Lock.ump"
public class Lock
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Lock()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../Lock.ump"
   protected void hook760(MemoryBudget mb, int lockTableIndex){
    mb.updateLockMemoryUsage(MemoryBudget.LOCKINFO_OVERHEAD, lockTableIndex);
	original(mb, lockTableIndex);
  }

  // line 13 "../../../../Lock.ump"
   protected void hook761(MemoryBudget mb, int lockTableIndex){
    mb.updateLockMemoryUsage(MemoryBudget.LOCKINFO_OVERHEAD, lockTableIndex);
	original(mb, lockTableIndex);
  }

  // line 18 "../../../../Lock.ump"
   protected void hook762(MemoryBudget mb, int lockTableIndex){
    mb.updateLockMemoryUsage(REMOVE_LOCKINFO_OVERHEAD, lockTableIndex);
	original(mb, lockTableIndex);
  }

  // line 23 "../../../../Lock.ump"
   protected void hook763(MemoryBudget mb, int lockTableIndex){
    mb.updateLockMemoryUsage(REMOVE_LOCKINFO_OVERHEAD, lockTableIndex);
	original(mb, lockTableIndex);
  }

  // line 28 "../../../../Lock.ump"
   protected void hook764(MemoryBudget mb, int lockTableIndex){
    mb.updateLockMemoryUsage(MemoryBudget.LOCKINFO_OVERHEAD, lockTableIndex);
	original(mb, lockTableIndex);
  }

  // line 33 "../../../../Lock.ump"
   protected void hook765(MemoryBudget mb, int lockTableIndex, boolean removed){
    if (removed) {
	    mb.updateLockMemoryUsage(REMOVE_LOCKINFO_OVERHEAD, lockTableIndex);
	}
	original(mb, lockTableIndex, removed);
  }

  // line 40 "../../../../Lock.ump"
   protected void hook766(MemoryBudget mb, int lockTableIndex, LockInfo flushedInfo){
    if (flushedInfo != null) {
	    mb.updateLockMemoryUsage(REMOVE_LOCKINFO_OVERHEAD, lockTableIndex);
	}
	original(mb, lockTableIndex, flushedInfo);
  }

  // line 47 "../../../../Lock.ump"
   protected void hook767(MemoryBudget mb, int lockTableIndex){
    mb.updateLockMemoryUsage(REMOVE_LOCKINFO_OVERHEAD, lockTableIndex);
	original(mb, lockTableIndex);
  }

  // line 52 "../../../../Lock.ump"
   protected void hook768(MemoryBudget mb, int lockTableIndex, int numRemovedLockInfos) throws DatabaseException{
    mb.updateLockMemoryUsage(0 - (numRemovedLockInfos * MemoryBudget.LOCKINFO_OVERHEAD), lockTableIndex);
	original(mb, lockTableIndex, numRemovedLockInfos);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Lock.ump"
  private static final int REMOVE_LOCKINFO_OVERHEAD = 0 - MemoryBudget.LOCKINFO_OVERHEAD ;

  
}