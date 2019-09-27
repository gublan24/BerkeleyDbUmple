/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import com.sleepycat.je.TransactionStats;
import com.sleepycat.je.StatsConfig;

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


  /**
   * 
   * Collect transaction related stats.
   */
  // line 21 "../../../../TxnManager.ump"
   public TransactionStats txnStat(StatsConfig config) throws DatabaseException{
    TransactionStats stats = new TransactionStats();
	this.hook820(config, stats);
	return stats;
  }


  /**
   * 
   * Collect lock related stats.
   */
  // line 30 "../../../../TxnManager.ump"
   public LockStats lockStat(StatsConfig config) throws DatabaseException{
    return lockManager.lockStat(config);
  }

  // line 34 "../../../../TxnManager.ump"
   protected void hook820(StatsConfig config, TransactionStats stats) throws DatabaseException{
    stats.setNCommits(numCommits);
	stats.setNAborts(numAborts);
	stats.setNXAPrepares(numXAPrepares);
	stats.setNXACommits(numXACommits);
	stats.setNXAAborts(numXAAborts);
	stats.setNActive(allTxns.size());
	TransactionStats.Active[] activeSet = new TransactionStats.Active[stats.getNActive()];
	stats.setActiveTxns(activeSet);
	Iterator iter = allTxns.iterator();
	int i = 0;
	while (iter.hasNext()) {
	    Locker txn = (Locker) iter.next();
	    activeSet[i] = new TransactionStats.Active(txn.toString(), txn.getId(), 0);
	    i++;
	}
	if (config.getClear()) {
	    numCommits = 0;
	    numAborts = 0;
	    numXACommits = 0;
	    numXAAborts = 0;
	}
  }

  // line 58 "../../../../TxnManager.ump"
   protected void hook824() throws DatabaseException{
    numCommits = 0;
	numAborts = 0;
	numXAPrepares = 0;
	numXACommits = 0;
	numXAAborts = 0;
	original();
  }

  // line 67 "../../../../TxnManager.ump"
   protected void hook825(boolean isCommit) throws DatabaseException{
    if (isCommit) {
	    numCommits++;
	} else {
	    numAborts++;
	}
	original(isCommit);
  }

  // line 76 "../../../../TxnManager.ump"
   protected void hook826(boolean isPrepare) throws DatabaseException{
    if (isPrepare) {
	    numXAPrepares++;
	}
	original(isPrepare);
  }

  // line 83 "../../../../TxnManager.ump"
   protected void hook827(boolean isCommit) throws DatabaseException{
    if (isCommit) {
	    numXACommits++;
	} else {
	    numXAAborts++;
	}
	original(isCommit);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../TxnManager.ump"
  private int numCommits ;
// line 9 "../../../../TxnManager.ump"
  private int numAborts ;
// line 11 "../../../../TxnManager.ump"
  private int numXAPrepares ;
// line 13 "../../../../TxnManager.ump"
  private int numXACommits ;
// line 15 "../../../../TxnManager.ump"
  private int numXAAborts ;

  
}