/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.tree.LN;

// line 3 "../../../../LockResult.ump"
public class LockResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockResult()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 15 "../../../../LockResult.ump"
   public  LockResult(LockGrantType grant, WriteLockInfo info){
    this.grant = grant;
	this.info = info;
  }

  // line 20 "../../../../LockResult.ump"
   public LN getLN(){
    return ln;
  }

  // line 24 "../../../../LockResult.ump"
   public void setLN(LN ln){
    this.ln = ln;
  }

  // line 28 "../../../../LockResult.ump"
   public LockGrantType getLockGrant(){
    return grant;
  }

  // line 32 "../../../../LockResult.ump"
   public void setAbortLsn(long abortLsn, boolean abortKnownDeleted){
    setAbortLsnInternal(abortLsn, abortKnownDeleted, false);
  }

  // line 36 "../../../../LockResult.ump"
   public void setAbortLsn(long abortLsn, boolean abortKnownDeleted, boolean createdThisTxn){
    setAbortLsnInternal(abortLsn, abortKnownDeleted, createdThisTxn);
  }

  // line 40 "../../../../LockResult.ump"
   private void setAbortLsnInternal(long abortLsn, boolean abortKnownDeleted, boolean createdThisTxn){
    if (info != null && info.neverLocked) {
	    if (abortLsn != DbLsn.NULL_LSN) {
		info.abortLsn = abortLsn;
		info.abortKnownDeleted = abortKnownDeleted;
	    }
	    info.createdThisTxn = createdThisTxn;
	    info.neverLocked = false;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../LockResult.ump"
  private LockGrantType grant ;
// line 10 "../../../../LockResult.ump"
  private WriteLockInfo info ;
// line 12 "../../../../LockResult.ump"
  private LN ln ;

  
}