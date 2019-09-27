/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import com.sleepycat.je.latch.LatchStats;

// line 3 "../../../LockStats.ump"
public class LockStats
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockStats()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Internal use only.
   */
  // line 15 "../../../LockStats.ump"
   public void accumulateLockTableLatchStats(LatchStats latchStats){
    if (lockTableLatchStats == null) {
	    lockTableLatchStats = latchStats;
	    return;
	}
	lockTableLatchStats.nAcquiresNoWaiters += latchStats.nAcquiresNoWaiters;
	lockTableLatchStats.nAcquiresSelfOwned += latchStats.nAcquiresSelfOwned;
	lockTableLatchStats.nAcquiresUpgrade += latchStats.nAcquiresUpgrade;
	lockTableLatchStats.nAcquiresWithContention += latchStats.nAcquiresWithContention;
	lockTableLatchStats.nAcquireNoWaitSuccessful += latchStats.nAcquireNoWaitSuccessful;
	lockTableLatchStats.nAcquireNoWaitUnsuccessful += latchStats.nAcquireNoWaitUnsuccessful;
	lockTableLatchStats.nAcquireSharedSuccessful += latchStats.nAcquireSharedSuccessful;
  }

  // line 29 "../../../LockStats.ump"
   protected void hook64(StringBuffer sb){
    sb.append("lockTableLatch:\n").append(lockTableLatchStats);
	original(sb);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../LockStats.ump"
  private LatchStats lockTableLatchStats ;

  
}