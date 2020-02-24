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
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../TxnManager.ump"
// line 3 "../../../../MemoryBudget_TxnManager.ump"
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
    Label822:
if (EnvironmentImpl.getFairLatches()) {
			  lockManager = new LatchedLockManager(env);
		} else
  ;           ;  //this.hook822(env);
   			if (env.isNoLocking()) {
            lockManager = new DummyLockManager(env);
        } else {
            lockManager = new SyncedLockManager(env);
        }
        this.env = env;
        allTxns = new HashSet();
        
allTxnLatch = LatchSupport.makeLatch(DEBUG_NAME, env);
	//original(env);
Label821:           ;  //this.hook821(env);
        allXATxns = Collections.synchronizedMap(new HashMap());
        thread2Txn = Collections.synchronizedMap(new HashMap());
        Label824:            ;  //this.hook824();
        lastUsedTxnId = 0;
  }


  /**
   * 
   * Get the last used id, for checkpoint info.
   */
  // line 66 "../../../../TxnManager.ump"
   public synchronized  long getLastTxnId(){
    return lastUsedTxnId;
  }


  /**
   * 
   * Get the next transaction id to use.
   */
  // line 73 "../../../../TxnManager.ump"
   synchronized  long incTxnId(){
    return ++lastUsedTxnId;
  }


  /**
   * 
   * Create a new transaction.
   * @param parentfor nested transactions, not yet supported
   * @param txnConfigspecifies txn attributes
   * @return the new txn
   */
  // line 83 "../../../../TxnManager.ump"
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
  // line 93 "../../../../TxnManager.ump"
   public LockManager getLockManager(){
    return lockManager;
  }


  /**
   * 
   * Called when txn is created.
   */
  // line 100 "../../../../TxnManager.ump"
  public void registerTxn(Txn txn) throws DatabaseException{
    // line 27 "../../../../Latches_TxnManager.ump"
    allTxnLatch.acquire();
    	//original(txn);
    // END OF UMPLE BEFORE INJECTION
    allTxns.add(txn);
        if (txn.isSerializableIsolation()) {
            nActiveSerializable++;
        }
    // line 33 "../../../../Latches_TxnManager.ump"
    //original(txn);
    	allTxnLatch.release();
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Called when txn ends.
   */
  // line 110 "../../../../TxnManager.ump"
  public void unRegisterTxn(Txn txn, boolean isCommit) throws DatabaseException{
    // line 42 "../../../../Latches_TxnManager.ump"
    allTxnLatch.acquire();
    // END OF UMPLE BEFORE INJECTION
    allTxns.remove(txn);
        //	           ;  //this.hook828(txn);
        Label828:
env.getMemoryBudget().updateMiscMemoryUsage(txn.getAccumulatedDelta() - txn.getInMemorySize());
//      original(txn);
  ;
            Label825:  ; //           ;  //this.hook825(isCommit);
        if (txn.isSerializableIsolation()) {
            nActiveSerializable--;
        }
    // line 46 "../../../../Latches_TxnManager.ump"
    //try {	    //original(txn, isCommit);	} finally {
    	    allTxnLatch.release();
    	//}
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Called when txn is created.
   */
  // line 123 "../../../../TxnManager.ump"
   public void registerXATxn(Xid xid, Txn txn, boolean isPrepare) throws DatabaseException{
    if (!allXATxns.containsKey(xid)) {
            allXATxns.put(xid, txn);
            //	               ;  //this.hook829();
            Label829:
env.getMemoryBudget().updateMiscMemoryUsage(MemoryBudget.HASHMAP_ENTRY_OVERHEAD);
      //original();
  ;
        }
        Label826:  ; //           ;  //this.hook826(isPrepare);
  }


  /**
   * 
   * Called when txn ends.
   */
  // line 135 "../../../../TxnManager.ump"
  public void unRegisterXATxn(Xid xid, boolean isCommit) throws DatabaseException{
    if (allXATxns.remove(xid) == null) {
            throw new DatabaseException("XA Transaction " + xid + " can not be unregistered.");
        }
        //	           ;  //this.hook830();
        Label830:
env.getMemoryBudget().updateMiscMemoryUsage(0 - MemoryBudget.HASHMAP_ENTRY_OVERHEAD);
      //original();
  ;
            Label827:  ; //           ;  //this.hook827(isCommit);
  }


  /**
   * 
   * Retrieve a Txn object from an Xid.
   */
  // line 147 "../../../../TxnManager.ump"
   public Txn getTxnFromXid(Xid xid) throws DatabaseException{
    return (Txn) allXATxns.get(xid);
  }


  /**
   * 
   * Called when txn is assoc'd with this thread.
   */
  // line 154 "../../../../TxnManager.ump"
   public void setTxnForThread(Transaction txn){
    Thread curThread = Thread.currentThread();
        thread2Txn.put(curThread, txn);
  }


  /**
   * 
   * Called when txn is assoc'd with this thread.
   */
  // line 162 "../../../../TxnManager.ump"
   public Transaction unsetTxnForThread() throws DatabaseException{
    Thread curThread = Thread.currentThread();
        return (Transaction) thread2Txn.remove(curThread);
  }


  /**
   * 
   * Retrieve a Txn object for this Thread.
   */
  // line 170 "../../../../TxnManager.ump"
   public Transaction getTxnForThread() throws DatabaseException{
    return (Transaction) thread2Txn.get(Thread.currentThread());
  }

  // line 174 "../../../../TxnManager.ump"
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
  // line 184 "../../../../TxnManager.ump"
   public boolean areOtherSerializableTransactionsActive(Locker excludeLocker){
    int exclude = (excludeLocker != null && excludeLocker.isSerializableIsolation()) ? 1 : 0;
        return (nActiveSerializable - exclude > 0);
  }


  /**
   * 
   * Get the earliest LSN of all the active transactions, for checkpoint.
   */
  // line 192 "../../../../TxnManager.ump"
   public long getFirstActiveLsn() throws DatabaseException{
    try{
						  long firstActive = DbLsn.NULL_LSN;
						  Label823:
allTxnLatch.acquire();
  ; //firstActive = this.hook823(firstActive);
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
			finally{
			Label823_1:
allTxnLatch.release();
  ;
			}
//end of hook823
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

// line 58 "../../../../TxnManager.ump"
  synchronized public void setLastTxnId (long lastId) 
  {
    this.lastUsedTxnId = lastId;
  }
// line 7 "../../../../Latches_TxnManager.ump"
  private Latch allTxnLatch ;

  
}