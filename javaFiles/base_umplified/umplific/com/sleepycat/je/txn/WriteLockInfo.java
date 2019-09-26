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

  //WriteLockInfo Attributes
  private Lock lock;
  private long abortLsn;
  private boolean abortKnownDeleted;
  private boolean neverLocked;
  private boolean createdThisTxn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WriteLockInfo(Lock aLock, boolean aAbortKnownDeleted, boolean aNeverLocked, boolean aCreatedThisTxn)
  {
    lock = aLock;
    abortLsn = DbLsn.NULL_LSN;
    abortKnownDeleted = aAbortKnownDeleted;
    neverLocked = aNeverLocked;
    createdThisTxn = aCreatedThisTxn;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLock(Lock aLock)
  {
    boolean wasSet = false;
    lock = aLock;
    wasSet = true;
    return wasSet;
  }

  public boolean setAbortLsn(long aAbortLsn)
  {
    boolean wasSet = false;
    abortLsn = aAbortLsn;
    wasSet = true;
    return wasSet;
  }

  public boolean setAbortKnownDeleted(boolean aAbortKnownDeleted)
  {
    boolean wasSet = false;
    abortKnownDeleted = aAbortKnownDeleted;
    wasSet = true;
    return wasSet;
  }

  public boolean setNeverLocked(boolean aNeverLocked)
  {
    boolean wasSet = false;
    neverLocked = aNeverLocked;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreatedThisTxn(boolean aCreatedThisTxn)
  {
    boolean wasSet = false;
    createdThisTxn = aCreatedThisTxn;
    wasSet = true;
    return wasSet;
  }

  public Lock getLock()
  {
    return lock;
  }

  public long getAbortLsn()
  {
    return abortLsn;
  }

  public boolean getAbortKnownDeleted()
  {
    return abortKnownDeleted;
  }

  public boolean getNeverLocked()
  {
    return neverLocked;
  }

  public boolean getCreatedThisTxn()
  {
    return createdThisTxn;
  }

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

  // line 28 "../../../../WriteLockInfo.ump"
  public  WriteLockInfo(){
    this.lock = null;
	abortLsn = DbLsn.NULL_LSN;
	abortKnownDeleted = true;
	neverLocked = true;
	createdThisTxn = false;
  }


  public String toString()
  {
    return super.toString() + "["+
            "abortLsn" + ":" + getAbortLsn()+ "," +
            "abortKnownDeleted" + ":" + getAbortKnownDeleted()+ "," +
            "neverLocked" + ":" + getNeverLocked()+ "," +
            "createdThisTxn" + ":" + getCreatedThisTxn()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lock" + "=" + (getLock() != null ? !getLock().equals(this)  ? getLock().toString().replaceAll("  ","    ") : "this" : "null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../../../WriteLockInfo.ump"
  static final WriteLockInfo basicWriteLockInfo = new WriteLockInfo() ;

  
}