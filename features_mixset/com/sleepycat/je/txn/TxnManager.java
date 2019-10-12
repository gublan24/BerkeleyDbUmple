/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.LockStats;
import com.sleepycat.je.DatabaseException;
import javax.transaction.xa.Xid;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Collections;
import com.sleepycat.je.TransactionStats;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../TxnManager.ump"
// line 3 "../../../../MemoryBudget_TxnManager.ump"
// line 3 "../../../../Statistics_TxnManager.ump"
// line 3 "../../../../Latches_TxnManager.ump"
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

  // line 40 "../../../../TxnManager.ump"
   public  TxnManager(EnvironmentImpl env) throws DatabaseException{
    this.hook822(env);
        this.env = env;
        allTxns = new HashSet();
        this.hook821(env);
        allXATxns = Collections.synchronizedMap(new HashMap());
        thread2Txn = Collections.synchronizedMap(new HashMap());
        Label824:
numCommits = 0;
        numAborts = 0;
        numXAPrepares = 0;
        numXACommits = 0;
        numXAAborts = 0;
        //original();
 //this.hook824();
        lastUsedTxnId = 0;
  }


  /**
   * 
   * Create a new transaction.
   * @param parentfor nested transactions, not yet supported
   * @param txnConfigspecifies txn attributes
   * @return the new txn
   */
  // line 78 "../../../../TxnManager.ump"
   public Txn txnBegin(Transaction parent, TransactionConfig txnConfig) throws DatabaseException{
    if (parent != null) {
            throw new DatabaseException("Nested transactions are not supported yet.");
        }
        return new Txn(env, txnConfig);
  }


  /**
   * 
   * Give transactions and environment access to lock manager.
   */
  // line 88 "../../../../TxnManager.ump"
   public LockManager getLockManager(){
    return lockManager;
  }


  /**
   * 
   * Called when txn is created.
   */
  // line 95 "../../../../TxnManager.ump"
  public void registerTxn(Txn txn) throws DatabaseException{
    allTxns.add(txn);
        if (txn.isSerializableIsolation()) {
            nActiveSerializable++;
        }
  }


  /**
   * 
   * Called when txn ends.
   */
  // line 105 "../../../../TxnManager.ump"
  public void unRegisterTxn(Txn txn, boolean isCommit) throws DatabaseException{
    allTxns.remove(txn);
        //	this.hook828(txn);
        Label828:
env.getMemoryBudget().updateMiscMemoryUsage(txn.getAccumulatedDelta() - txn.getInMemorySize());
//      original(txn);

            Label825:
if (isCommit) {
            numCommits++;
        } else {
            numAborts++;
        }
        //original(isCommit);
 //this.hook825(isCommit);
        if (txn.isSerializableIsolation()) {
            nActiveSerializable--;
        }
  }


  /**
   * 
   * Called when txn is created.
   */
  // line 118 "../../../../TxnManager.ump"
   public void registerXATxn(Xid xid, Txn txn, boolean isPrepare) throws DatabaseException{
    if (!allXATxns.containsKey(xid)) {
            allXATxns.put(xid, txn);
            //	    this.hook829();
            Label829:
env.getMemoryBudget().updateMiscMemoryUsage(MemoryBudget.HASHMAP_ENTRY_OVERHEAD);
      //original();

        }
        Label826:
if (isPrepare) {
            numXAPrepares++;
        }
        //original(isPrepare);
 //this.hook826(isPrepare);
  }


  /**
   * 
   * Called when txn ends.
   */
  // line 130 "../../../../TxnManager.ump"
  public void unRegisterXATxn(Xid xid, boolean isCommit) throws DatabaseException{
    if (allXATxns.remove(xid) == null) {
            throw new DatabaseException("XA Transaction " + xid + " can not be unregistered.");
        }
        //	this.hook830();
        Label830:
env.getMemoryBudget().updateMiscMemoryUsage(0 - MemoryBudget.HASHMAP_ENTRY_OVERHEAD);
      //original();

            Label827:
if (isCommit) {
            numXACommits++;
        } else {
            numXAAborts++;
        }
         //        original(isCommit);
 //this.hook827(isCommit);
  }


  /**
   * 
   * Retrieve a Txn object from an Xid.
   */
  // line 142 "../../../../TxnManager.ump"
   public Txn getTxnFromXid(Xid xid) throws DatabaseException{
    return (Txn) allXATxns.get(xid);
  }


  /**
   * 
   * Called when txn is assoc'd with this thread.
   */
  // line 149 "../../../../TxnManager.ump"
   public void setTxnForThread(Transaction txn){
    Thread curThread = Thread.currentThread();
        thread2Txn.put(curThread, txn);
  }


  /**
   * 
   * Called when txn is assoc'd with this thread.
   */
  // line 157 "../../../../TxnManager.ump"
   public Transaction unsetTxnForThread() throws DatabaseException{
    Thread curThread = Thread.currentThread();
        return (Transaction) thread2Txn.remove(curThread);
  }


  /**
   * 
   * Retrieve a Txn object for this Thread.
   */
  // line 165 "../../../../TxnManager.ump"
   public Transaction getTxnForThread() throws DatabaseException{
    return (Transaction) thread2Txn.get(Thread.currentThread());
  }

  // line 169 "../../../../TxnManager.ump"
   public Xid[] XARecover() throws DatabaseException{
    Set xidSet = allXATxns.keySet();
        Xid[] ret = new Xid[xidSet.size()];
        ret = (Xid[]) xidSet.toArray(ret);
        return ret;
  }


  /**
   * 
   * Returns whether there are any active serializable transactions, excluding the transaction given (if non-null). This is intentionally returned without latching, since latching would not make the act of reading an integer more atomic than it already is.
   */
  // line 179 "../../../../TxnManager.ump"
   public boolean areOtherSerializableTransactionsActive(Locker excludeLocker){
    int exclude = (excludeLocker != null && excludeLocker.isSerializableIsolation()) ? 1 : 0;
        return (nActiveSerializable - exclude > 0);
  }


  /**
   * 
   * Get the earliest LSN of all the active transactions, for checkpoint.
   */
  // line 187 "../../../../TxnManager.ump"
   public long getFirstActiveLsn() throws DatabaseException{
    long firstActive = DbLsn.NULL_LSN;
        firstActive = this.hook823(firstActive);
        return firstActive;
  }

  // line 192 "../../../../TxnManager.ump"
   protected void hook821(EnvironmentImpl env) throws DatabaseException{
    allTxnLatch = LatchSupport.makeLatch(DEBUG_NAME, env);
	original(env);
  }

  // line 195 "../../../../TxnManager.ump"
   protected void hook822(EnvironmentImpl env) throws DatabaseException{
    if (env.isNoLocking()) {
            lockManager = new DummyLockManager(env);
        } else {
            lockManager = new SyncedLockManager(env);
        }
  }

  // line 203 "../../../../TxnManager.ump"
   protected long hook823(long firstActive) throws DatabaseException{
    Iterator iter = allTxns.iterator();
        while (iter.hasNext()) {
            long txnFirstActive = ((Txn) iter.next()).getFirstActiveLsn();
            if (firstActive == DbLsn.NULL_LSN) {
                firstActive = txnFirstActive;
            } else if (txnFirstActive != DbLsn.NULL_LSN) {
                if (DbLsn.compareTo(txnFirstActive, firstActive) < 0) {
                    firstActive = txnFirstActive;
                }
            }
        }
        return firstActive;
  }


  /**
   * protected void hook824() throws DatabaseException {
   * }
   */
  // line 220 "../../../../TxnManager.ump"
   protected void hook825(boolean isCommit) throws DatabaseException{
    
  }

  // line 222 "../../../../TxnManager.ump"
   protected void hook826(boolean isPrepare) throws DatabaseException{
    
  }

  // line 224 "../../../../TxnManager.ump"
   protected void hook827(boolean isCommit) throws DatabaseException{
    
  }


  /**
   * protected void hook828(Txn txn) throws DatabaseException {
   * }
   */
  // line 229 "../../../../TxnManager.ump"
   protected void hook829() throws DatabaseException{
    
  }

  // line 231 "../../../../TxnManager.ump"
   protected void hook830() throws DatabaseException{
    
  }


  /**
   * 
   * Collect transaction related stats.
   */
  // line 22 "../../../../Statistics_TxnManager.ump"
   public TransactionStats txnStat(StatsConfig config) throws DatabaseException{
    TransactionStats stats = new TransactionStats();
        Label820://this.hook820(config, stats);
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
      //End hook820
        return stats;
  }


  /**
   * 
   * Collect lock related stats.
   */
  // line 53 "../../../../Statistics_TxnManager.ump"
   public LockStats lockStat(StatsConfig config) throws DatabaseException{
    return lockManager.lockStat(config);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 21 "../../../../TxnManager.ump"
  static final long NULL_TXN_ID = -1 ;
// line 23 "../../../../TxnManager.ump"
  private static final String DEBUG_NAME = TxnManager.class.getName() ;
// line 25 "../../../../TxnManager.ump"
  private LockManager lockManager ;
// line 27 "../../../../TxnManager.ump"
  private EnvironmentImpl env ;
// line 29 "../../../../TxnManager.ump"
  private Set allTxns ;
// line 31 "../../../../TxnManager.ump"
  private Map allXATxns ;
// line 33 "../../../../TxnManager.ump"
  private Map thread2Txn ;
// line 35 "../../../../TxnManager.ump"
  private long lastUsedTxnId ;
// line 37 "../../../../TxnManager.ump"
  private int nActiveSerializable ;

// line 53 "../../../../TxnManager.ump"
  synchronized public void setLastTxnId (long lastId) 
  {
    this.lastUsedTxnId = lastId;
  }

// line 60 "../../../../TxnManager.ump"
  public synchronized long getLastTxnId () 
  {
    return lastUsedTxnId;
  }

// line 67 "../../../../TxnManager.ump"
  synchronized long incTxnId () 
  {
    return ++lastUsedTxnId;
  }
// line 8 "../../../../Statistics_TxnManager.ump"
  private int numCommits ;
// line 10 "../../../../Statistics_TxnManager.ump"
  private int numAborts ;
// line 12 "../../../../Statistics_TxnManager.ump"
  private int numXAPrepares ;
// line 14 "../../../../Statistics_TxnManager.ump"
  private int numXACommits ;
// line 16 "../../../../Statistics_TxnManager.ump"
  private int numXAAborts ;
// line 7 "../../../../Latches_TxnManager.ump"
  private Latch allTxnLatch ;

  
}