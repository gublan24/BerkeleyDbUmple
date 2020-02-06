/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;

// line 3 "../../../../WriteLockInfo.ump"
public class WriteLockInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WriteLockInfo()
  {
    // line 28 "../../../../WriteLockInfo.ump"
    this.lock = null;
    	abortLsn = DbLsn.NULL_LSN;
    	abortKnownDeleted = true;
    	neverLocked = true;
    	createdThisTxn = false;
    // END OF UMPLE AFTER INJECTION
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 20 "../../../../WriteLockInfo.ump"
  public  WriteLockInfo(Lock lock){
    this.lock = lock;
	abortLsn = DbLsn.NULL_LSN;
	abortKnownDeleted = false;
	neverLocked = true;
	createdThisTxn = false;
  }

  // line 36 "../../../../WriteLockInfo.ump"
   public boolean getAbortKnownDeleted(){
    return abortKnownDeleted;
  }

  // line 40 "../../../../WriteLockInfo.ump"
   public long getAbortLsn(){
    return abortLsn;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../WriteLockInfo.ump"
  public Lock lock ;
// line 9 "../../../../WriteLockInfo.ump"
  protected long abortLsn = DbLsn.NULL_LSN ;
// line 11 "../../../../WriteLockInfo.ump"
  protected boolean abortKnownDeleted ;
// line 13 "../../../../WriteLockInfo.ump"
  protected boolean neverLocked ;
// line 15 "../../../../WriteLockInfo.ump"
  protected boolean createdThisTxn ;
// line 17 "../../../../WriteLockInfo.ump"
  static final WriteLockInfo basicWriteLockInfo = new WriteLockInfo() ;

  
}