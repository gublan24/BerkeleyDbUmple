/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import com.sleepycat.je.StatsConfig;

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


  /**
   * 
   * Statistics
   */
  // line 14 "../../../../LockManager.ump"
   public LockStats lockStat(StatsConfig config) throws DatabaseException{
    return new LockManager_lockStat(this, config).execute();
  }

  // line 18 "../../../../LockManager.ump"
   protected void hook774() throws DatabaseException{
    nRequests = 0;
	nWaits = 0;
	original();
  }

  // line 25 "../../../../LockManager.ump"
   protected LockAttemptResult attemptLockInternal(Long nodeId, Locker locker, LockType type, boolean nonBlockingRequest, int lockTableIndex) throws DatabaseException{
    nRequests++;
	return original(nodeId, locker, type, nonBlockingRequest, lockTableIndex);
  }

  // line 30 "../../../../LockManager.ump"
   protected void hook775() throws DatabaseException{
    nWaits++;
	original();
  }

  // line 35 "../../../../LockManager.ump"
   protected void hook776(LockStats stats, Map lockTable){
    stats.accumulateNTotalLocks(lockTable.size());
	original(stats, lockTable);
  }

  // line 40 "../../../../LockManager.ump"
   protected void hook777(LockStats stats, Lock lock){
    stats.setNWaiters(stats.getNWaiters() + lock.nWaiters());
	stats.setNOwners(stats.getNOwners() + lock.nOwners());
	original(stats, lock);
  }

  // line 46 "../../../../LockManager.ump"
   protected void hook778(LockStats stats, LockInfo info){
    if (info.getLockType().isWriteLock()) {
	    stats.setNWriteLocks(stats.getNWriteLocks() + 1);
	} else {
	    stats.setNReadLocks(stats.getNReadLocks() + 1);
	}
	original(stats, info);
  }
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
    public  LockManager_lockStat(LockManager _this, StatsConfig config){
      this._this=_this;
          this.config=config;
    }
  
    // line 10 "../../../../LockManager_inner.ump"
    public LockStats execute() throws DatabaseException{
      stats=new LockStats();
          stats.setNRequests(_this.nRequests);
          stats.setNWaits(_this.nWaits);
          if (config.getClear()) {
            _this.nWaits=0;
            _this.nRequests=0;
          }
          this.hook769();
          if (!config.getFast()) {
            _this.dumpLockTable(stats);
          }
          return stats;
    }
  
    // line 28 "../../../../LockManager_inner.ump"
     protected void hook769() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 23 "../../../../LockManager_inner.ump"
    protected LockManager _this ;
  // line 24 "../../../../LockManager_inner.ump"
    protected StatsConfig config ;
  // line 25 "../../../../LockManager_inner.ump"
    protected LockStats stats ;
  // line 26 "../../../../LockManager_inner.ump"
    protected LatchStats latchStats ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../LockManager.ump"
  private long nRequests ;
// line 8 "../../../../LockManager.ump"
  private long nWaits ;

  
}