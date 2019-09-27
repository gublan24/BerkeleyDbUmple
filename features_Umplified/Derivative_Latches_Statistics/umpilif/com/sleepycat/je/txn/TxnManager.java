/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

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

  // line 6 "../../../../TxnManager.ump"
   protected void hook820(StatsConfig config, TransactionStats stats) throws DatabaseException{
    allTxnLatch.acquire();
	try {
	    original(config, stats);
	} finally {
	    allTxnLatch.release();
	}
  }

}