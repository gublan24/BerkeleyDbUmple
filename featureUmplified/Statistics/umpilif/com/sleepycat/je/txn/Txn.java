/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Txn.ump"
public class Txn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Txn()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * stats
   */
  // line 9 "../../../../Txn.ump"
   public LockStats collectStats(LockStats stats) throws DatabaseException{
    synchronized (this) {
	    int nReadLocks = (readLocks == null) ? 0 : readLocks.size();
	    stats.setNReadLocks(stats.getNReadLocks() + nReadLocks);
	    int nWriteLocks = (writeInfo == null) ? 0 : writeInfo.size();
	    stats.setNWriteLocks(stats.getNWriteLocks() + nWriteLocks);
	}
	return stats;
  }

}