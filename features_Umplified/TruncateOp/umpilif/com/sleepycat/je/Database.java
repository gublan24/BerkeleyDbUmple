/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import com.sleepycat.je.dbi.TruncateResult;

/**
 * namespace com.sleepycat.je;
 */
// line 3 "../../../Database.ump"
// line 3 "../../../Database_inner.ump"
public class Database
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Database()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * @deprecated It has not been possible to implement this method withcorrect transactional semantics without incurring a performance penalty on all Database operations. Truncate functionality has been moved to Environment.truncateDatabase(), which requires that all Database handles on the database are closed before the truncate operation can execute.
   */
  // line 10 "../../../Database.ump"
   public int truncate(Transaction txn, boolean countRecords) throws DatabaseException{
    return new Database_truncate(this, txn, countRecords).execute();
  }


  /**
   * 
   * Internal unchecked truncate that optionally counts records.
   * @deprecated
   */
  // line 18 "../../../Database.ump"
  public int truncateInternal(Locker locker, boolean countRecords) throws DatabaseException{
    if (databaseImpl == null) {
	    throw new DatabaseException("couldn't find database - truncate");
	}
	this.hook43();
	if (handleLocker.isHandleLockTransferrable()) {
	    handleLocker.transferHandleLock(this, locker, false);
	}
	boolean operationOk = false;
	try {
	    TruncateResult result = envHandle.getEnvironmentImpl().truncate(locker, databaseImpl);
	    databaseImpl = result.getDatabase();
	    operationOk = true;
	    return countRecords ? result.getRecordCount() : -1;
	} finally {
	    locker.setHandleLockOwner(operationOk, this, false);
	}
  }

  // line 37 "../../../Database.ump"
   protected void hook43() throws DatabaseException{
    
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 4 "../../../Database_inner.ump"
  public static class Database_truncate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_truncate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../Database_inner.ump"
    public  Database_truncate(Database _this, Transaction txn, boolean countRecords){
      this._this=_this;
          this.txn=txn;
          this.countRecords=countRecords;
    }
  
    // line 11 "../../../Database_inner.ump"
    public int execute() throws DatabaseException{
      _this.checkEnv();
          _this.checkRequiredDbState(_this.OPEN,"Can't call Database.truncate");
          _this.checkWritable("truncate");
          this.hook39();
          locker=null;
          this.hook40();
          operationOk=false;
          try {
            locker=LockerFactory.getWritableLocker(_this.envHandle,txn,_this.isTransactional(),true,null);
            _this.acquireTriggerListReadLock();
            this.hook41();
            count=_this.truncateInternal(locker,countRecords);
            for (int i=0; i < _this.triggerList.size(); i+=1) {
              obj=_this.triggerList.get(i);
              if (obj instanceof SecondaryTrigger) {
                secDb=((SecondaryTrigger)obj).getDb();
                secDb.truncateInternal(locker,false);
              }
            }
            operationOk=true;
            return count;
          }
      finally {
            if (locker != null) {
              locker.operationEnd(operationOk);
            }
            this.hook42();
          }
    }
  
    // line 50 "../../../Database_inner.ump"
     protected void hook39() throws DatabaseException{
      
    }
  
    // line 52 "../../../Database_inner.ump"
     protected void hook40() throws DatabaseException{
      
    }
  
    // line 54 "../../../Database_inner.ump"
     protected void hook41() throws DatabaseException{
      
    }
  
    // line 56 "../../../Database_inner.ump"
     protected void hook42() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 40 "../../../Database_inner.ump"
    protected Database _this ;
  // line 41 "../../../Database_inner.ump"
    protected Transaction txn ;
  // line 42 "../../../Database_inner.ump"
    protected boolean countRecords ;
  // line 43 "../../../Database_inner.ump"
    protected Locker locker ;
  // line 44 "../../../Database_inner.ump"
    protected boolean triggerLock ;
  // line 45 "../../../Database_inner.ump"
    protected boolean operationOk ;
  // line 46 "../../../Database_inner.ump"
    protected int count ;
  // line 47 "../../../Database_inner.ump"
    protected Object obj ;
  // line 48 "../../../Database_inner.ump"
    protected SecondaryDatabase secDb ;
  
    
  }
}