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

  // line 39 "../../../../TxnManager.ump"
   public  TxnManager(EnvironmentImpl env) throws DatabaseException{
    this.hook822(env);
	this.env = env;
	allTxns = new HashSet();
	this.hook821(env);
	allXATxns = Collections.synchronizedMap(new HashMap());
	thread2Txn = Collections.synchronizedMap(new HashMap());
	this.hook824();
	lastUsedTxnId = 0;
  }


  /**
   * 
   * Create a new transaction.
   * @param parentfor nested transactions, not yet supported
   * @param txnConfigspecifies txn attributes
   * @return the new txn
   */
  // line 77 "../../../../TxnManager.ump"
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
  // line 87 "../../../../TxnManager.ump"
   public LockManager getLockManager(){
    return lockManager;
  }


  /**
   * 
   * Called when txn is created.
   */
  // line 94 "../../../../TxnManager.ump"
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
  // line 104 "../../../../TxnManager.ump"
  public void unRegisterTxn(Txn txn, boolean isCommit) throws DatabaseException{
    allTxns.remove(txn);
	this.hook828(txn);
	this.hook825(isCommit);
	if (txn.isSerializableIsolation()) {
	    nActiveSerializable--;
	}
  }


  /**
   * 
   * Called when txn is created.
   */
  // line 116 "../../../../TxnManager.ump"
   public void registerXATxn(Xid xid, Txn txn, boolean isPrepare) throws DatabaseException{
    if (!allXATxns.containsKey(xid)) {
	    allXATxns.put(xid, txn);
	    this.hook829();
	}
	this.hook826(isPrepare);
  }


  /**
   * 
   * Called when txn ends.
   */
  // line 127 "../../../../TxnManager.ump"
  public void unRegisterXATxn(Xid xid, boolean isCommit) throws DatabaseException{
    if (allXATxns.remove(xid) == null) {
	    throw new DatabaseException("XA Transaction " + xid + " can not be unregistered.");
	}
	this.hook830();
	this.hook827(isCommit);
  }


  /**
   * 
   * Retrieve a Txn object from an Xid.
   */
  // line 138 "../../../../TxnManager.ump"
   public Txn getTxnFromXid(Xid xid) throws DatabaseException{
    return (Txn) allXATxns.get(xid);
  }


  /**
   * 
   * Called when txn is assoc'd with this thread.
   */
  // line 145 "../../../../TxnManager.ump"
   public void setTxnForThread(Transaction txn){
    Thread curThread = Thread.currentThread();
	thread2Txn.put(curThread, txn);
  }


  /**
   * 
   * Called when txn is assoc'd with this thread.
   */
  // line 153 "../../../../TxnManager.ump"
   public Transaction unsetTxnForThread() throws DatabaseException{
    Thread curThread = Thread.currentThread();
	return (Transaction) thread2Txn.remove(curThread);
  }


  /**
   * 
   * Retrieve a Txn object for this Thread.
   */
  // line 161 "../../../../TxnManager.ump"
   public Transaction getTxnForThread() throws DatabaseException{
    return (Transaction) thread2Txn.get(Thread.currentThread());
  }

  // line 165 "../../../../TxnManager.ump"
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
  // line 175 "../../../../TxnManager.ump"
   public boolean areOtherSerializableTransactionsActive(Locker excludeLocker){
    int exclude = (excludeLocker != null && excludeLocker.isSerializableIsolation()) ? 1 : 0;
	return (nActiveSerializable - exclude > 0);
  }


  /**
   * 
   * Get the earliest LSN of all the active transactions, for checkpoint.
   */
  // line 183 "../../../../TxnManager.ump"
   public long getFirstActiveLsn() throws DatabaseException{
    long firstActive = DbLsn.NULL_LSN;
	firstActive = this.hook823(firstActive);
	return firstActive;
  }

  // line 189 "../../../../TxnManager.ump"
   protected void hook821(EnvironmentImpl env) throws DatabaseException{
    
  }

  // line 192 "../../../../TxnManager.ump"
   protected void hook822(EnvironmentImpl env) throws DatabaseException{
    if (env.isNoLocking()) {
	    lockManager = new DummyLockManager(env);
	} else {
	    lockManager = new SyncedLockManager(env);
	}
  }

  // line 200 "../../../../TxnManager.ump"
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

  // line 215 "../../../../TxnManager.ump"
   protected void hook824() throws DatabaseException{
    
  }

  // line 218 "../../../../TxnManager.ump"
   protected void hook825(boolean isCommit) throws DatabaseException{
    
  }

  // line 221 "../../../../TxnManager.ump"
   protected void hook826(boolean isPrepare) throws DatabaseException{
    
  }

  // line 224 "../../../../TxnManager.ump"
   protected void hook827(boolean isCommit) throws DatabaseException{
    
  }

  // line 227 "../../../../TxnManager.ump"
   protected void hook828(Txn txn) throws DatabaseException{
    
  }

  // line 230 "../../../../TxnManager.ump"
   protected void hook829() throws DatabaseException{
    
  }

  // line 233 "../../../../TxnManager.ump"
   protected void hook830() throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 20 "../../../../TxnManager.ump"
  static final long NULL_TXN_ID = -1 ;
// line 22 "../../../../TxnManager.ump"
  private static final String DEBUG_NAME = TxnManager.class.getName() ;
// line 24 "../../../../TxnManager.ump"
  private LockManager lockManager ;
// line 26 "../../../../TxnManager.ump"
  private EnvironmentImpl env ;
// line 28 "../../../../TxnManager.ump"
  private Set allTxns ;
// line 30 "../../../../TxnManager.ump"
  private Map allXATxns ;
// line 32 "../../../../TxnManager.ump"
  private Map thread2Txn ;
// line 34 "../../../../TxnManager.ump"
  private long lastUsedTxnId ;
// line 36 "../../../../TxnManager.ump"
  private int nActiveSerializable ;

// line 52 "../../../../TxnManager.ump"
  synchronized public void setLastTxnId (long lastId) 
  {
    this.lastUsedTxnId = lastId;
  }

// line 59 "../../../../TxnManager.ump"
  public synchronized long getLastTxnId () 
  {
    return lastUsedTxnId;
  }

// line 66 "../../../../TxnManager.ump"
  synchronized long incTxnId () 
  {
    return ++lastUsedTxnId;
  }

  
}