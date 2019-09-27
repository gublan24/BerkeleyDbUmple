/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../TxnManager.ump"
public class TxnManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TxnManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../TxnManager.ump"
   protected void hook821(EnvironmentImpl env) throws DatabaseException{
    allTxnLatch = LatchSupport.makeLatch(DEBUG_NAME, env);
	original(env);
  }

  // line 15 "../../../../TxnManager.ump"
   protected void hook822(EnvironmentImpl env) throws DatabaseException{
    if (EnvironmentImpl.getFairLatches()) {
	    lockManager = new LatchedLockManager(env);
	} else {
	    original(env);
	}
  }


  /**
   * 
   * Called when txn is created.
   */
  // line 26 "../../../../TxnManager.ump"
  public void registerTxn(Txn txn) throws DatabaseException{
    allTxnLatch.acquire();
	original(txn);
	allTxnLatch.release();
  }


  /**
   * 
   * Called when txn ends.
   */
  // line 35 "../../../../TxnManager.ump"
  public void unRegisterTxn(Txn txn, boolean isCommit) throws DatabaseException{
    allTxnLatch.acquire();
	try {
	    original(txn, isCommit);
	} finally {
	    allTxnLatch.release();
	}
  }

  // line 44 "../../../../TxnManager.ump"
   protected long hook823(long firstActive) throws DatabaseException{
    allTxnLatch.acquire();
	try {
	    firstActive = original(firstActive);
	} finally {
	    allTxnLatch.release();
	}
	return firstActive;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../TxnManager.ump"
  private Latch allTxnLatch ;

  
}