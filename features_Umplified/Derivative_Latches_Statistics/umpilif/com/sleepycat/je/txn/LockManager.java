/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import com.sleepycat.je.latch.LatchStats;

// line 3 "../../../../LockManager.ump"
// line 3 "../../../../LockManager_inner.ump"
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
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.txn;
  
  @MethodObject
  // line 4 "../../../../LockManager_inner.ump"
  public static class LockManager_lockStat
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public LockManager_lockStat()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../LockManager_inner.ump"
     protected void hook769() throws DatabaseException{
      for (int i=0; i < _this.nLockTables; i++) {
            latchStats=(LatchStats)_this.lockTableLatches[i].getLatchStats();
            stats.accumulateLockTableLatchStats(latchStats);
          }
          original();
    }
  
  }
}