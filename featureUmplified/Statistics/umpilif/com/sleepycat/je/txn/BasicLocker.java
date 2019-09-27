/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../BasicLocker.ump"
public class BasicLocker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BasicLocker()
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
  // line 9 "../../../../BasicLocker.ump"
   public LockStats collectStats(LockStats stats) throws DatabaseException{
    if (ownedLock != null) {
	    if (ownedLock.isOwnedWriteLock(this)) {
		stats.setNWriteLocks(stats.getNWriteLocks() + 1);
	    } else {
		stats.setNReadLocks(stats.getNReadLocks() + 1);
	    }
	}
	if (ownedLockSet != null) {
	    Iterator iter = ownedLockSet.iterator();
	    while (iter.hasNext()) {
		Lock l = (Lock) iter.next();
		if (l.isOwnedWriteLock(this)) {
		    stats.setNWriteLocks(stats.getNWriteLocks() + 1);
		} else {
		    stats.setNReadLocks(stats.getNReadLocks() + 1);
		}
	    }
	}
	return stats;
  }

}