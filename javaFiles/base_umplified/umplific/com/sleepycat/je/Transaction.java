/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.PropUtil;
import com.sleepycat.je.txn.Txn;
import com.sleepycat.je.txn.Locker;

// line 3 "../../../Transaction.ump"
public class Transaction
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Transaction()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates a transaction.
   */
  // line 21 "../../../Transaction.ump"
  public  Transaction(Environment env, Txn txn){
    this.env = env;
	this.txn = txn;
	this.id = txn.getId();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 30 "../../../Transaction.ump"
   public void abort() throws DatabaseException{
    checkEnv();
	env.removeReferringHandle(this);
	txn.abort(false);
	txn = null;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 40 "../../../Transaction.ump"
   public long getId() throws DatabaseException{
    return id;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 47 "../../../Transaction.ump"
   public void commit() throws DatabaseException{
    checkEnv();
	env.removeReferringHandle(this);
	txn.commit();
	txn = null;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 57 "../../../Transaction.ump"
   public void commitSync() throws DatabaseException{
    doCommit(Txn.TXN_SYNC);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 64 "../../../Transaction.ump"
   public void commitNoSync() throws DatabaseException{
    doCommit(Txn.TXN_NOSYNC);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 71 "../../../Transaction.ump"
   public void commitWriteNoSync() throws DatabaseException{
    doCommit(Txn.TXN_WRITE_NOSYNC);
  }

  // line 75 "../../../Transaction.ump"
   private void doCommit(byte commitType) throws DatabaseException{
    checkEnv();
	env.removeReferringHandle(this);
	txn.commit(commitType);
	txn = null;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 85 "../../../Transaction.ump"
   public void setTxnTimeout(long timeOut) throws DatabaseException{
    checkEnv();
	txn.setTxnTimeout(PropUtil.microsToMillis(timeOut));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 93 "../../../Transaction.ump"
   public void setLockTimeout(long timeOut) throws DatabaseException{
    checkEnv();
	txn.setLockTimeout(PropUtil.microsToMillis(timeOut));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 101 "../../../Transaction.ump"
   public void setName(String name){
    this.name = name;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 108 "../../../Transaction.ump"
   public String getName(){
    return name;
  }

  // line 112 "../../../Transaction.ump"
   public int hashCode(){
    return (int) id;
  }

  // line 116 "../../../Transaction.ump"
   public boolean equals(Object o){
    if (o == null) {
	    return false;
	}
	if (!(o instanceof Transaction)) {
	    return false;
	}
	if (((Transaction) o).id == id) {
	    return true;
	}
	return false;
  }

  // line 129 "../../../Transaction.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("<Transaction id=\"");
	sb.append(txn.getId()).append("\"");
	if (name != null) {
	    sb.append(" name=\"");
	    sb.append(name).append("\"");
	}
	sb.append(">");
	return sb.toString();
  }


  /**
   * 
   * This method should only be called by the LockerFactory.getReadableLocker and getWritableLocker methods.  The locker returned does not enforce the readCommitted isolation setting.
   */
  // line 144 "../../../Transaction.ump"
  public Locker getLocker() throws DatabaseException{
    if (txn == null) {
	    throw new DatabaseException("Transaction " + id + " has been closed and is no longer" + " usable.");
	} else {
	    return txn;
	}
  }

  // line 152 "../../../Transaction.ump"
  public Txn getTxn(){
    return txn;
  }


  /**
   * 
   * @throws RunRecoveryException if the underlying environment is invalid.
   */
  // line 159 "../../../Transaction.ump"
   private void checkEnv() throws RunRecoveryException{
    env.getEnvironmentImpl().checkIfInvalid();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../Transaction.ump"
  private Txn txn ;
// line 11 "../../../Transaction.ump"
  private Environment env ;
// line 13 "../../../Transaction.ump"
  private long id ;
// line 15 "../../../Transaction.ump"
  private String name ;

  
}