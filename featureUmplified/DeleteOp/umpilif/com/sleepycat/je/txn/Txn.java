/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Txn.ump"
// line 3 "../../../../Txn_inner.ump"
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
   * @param dbImpl databaseImpl to remove
   * @param deleteAtCommit true if this databaseImpl should be cleaned on commit, false if it should be cleaned on abort.
   * @param mb environment memory budget.
   */
  // line 13 "../../../../Txn.ump"
   public void markDeleteAtTxnEnd(DatabaseImpl dbImpl, boolean deleteAtCommit) throws DatabaseException{
    new Txn_markDeleteAtTxnEnd(this, dbImpl, deleteAtCommit).execute();
  }

  // line 17 "../../../../Txn.ump"
   private void setDeletedDatabaseState(boolean isCommit) throws DatabaseException{
    if (deletedDatabases != null) {
	    Iterator iter = deletedDatabases.iterator();
	    while (iter.hasNext()) {
		DatabaseCleanupInfo info = (DatabaseCleanupInfo) iter.next();
		if (info.deleteAtCommit == isCommit) {
		    info.dbImpl.startDeleteProcessing();
		}
	    }
	}
  }


  /**
   * 
   * Cleanup leftover databaseImpls that are a by-product of database operations like removeDatabase(), truncateDatabase(). This method must be called outside the synchronization on this txn, because it calls deleteAndReleaseINs, which gets the TxnManager's allTxns latch. The checkpointer also gets the allTxns latch, and within that latch, needs to synchronize on individual txns, so we must avoid a latching hiearchy conflict.
   */
  // line 32 "../../../../Txn.ump"
   private void cleanupDatabaseImpls(boolean isCommit) throws DatabaseException{
    if (deletedDatabases != null) {
	    DatabaseCleanupInfo[] infoArray;
	    synchronized (this) {
		infoArray = new DatabaseCleanupInfo[deletedDatabases.size()];
		deletedDatabases.toArray(infoArray);
	    }
	    for (int i = 0; i < infoArray.length; i += 1) {
		DatabaseCleanupInfo info = infoArray[i];
		if (info.deleteAtCommit == isCommit) {
		    info.dbImpl.releaseDeletedINs();
		}
	    }
	    deletedDatabases = null;
	}
  }

  // line 49 "../../../../Txn.ump"
   protected void hook805() throws DatabaseException,RunRecoveryException,Throwable{
    cleanupDatabaseImpls(true);
	original();
  }

  // line 54 "../../../../Txn.ump"
   protected void hook806() throws DatabaseException,RunRecoveryException,Throwable{
    setDeletedDatabaseState(true);
	original();
  }

  // line 59 "../../../../Txn.ump"
   protected void hook807() throws DatabaseException{
    cleanupDatabaseImpls(false);
	original();
  }

  // line 64 "../../../../Txn.ump"
   protected void hook808() throws DatabaseException{
    setDeletedDatabaseState(false);
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.txn;
  
  @MethodObject
  // line 4 "../../../../Txn_inner.ump"
  public static class Txn_markDeleteAtTxnEnd
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Txn_markDeleteAtTxnEnd()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Txn_inner.ump"
    public  Txn_markDeleteAtTxnEnd(Txn _this, DatabaseImpl dbImpl, boolean deleteAtCommit){
      this._this=_this;
          this.dbImpl=dbImpl;
          this.deleteAtCommit=deleteAtCommit;
    }
  
    // line 11 "../../../../Txn_inner.ump"
    public void execute() throws DatabaseException{
      synchronized (_this) {
            this.hook797();
            if (_this.deletedDatabases == null) {
              _this.deletedDatabases=new HashSet();
              this.hook798();
            }
            _this.deletedDatabases.add(new DatabaseCleanupInfo(dbImpl,deleteAtCommit));
            this.hook796();
          }
    }
  
    // line 26 "../../../../Txn_inner.ump"
     protected void hook796() throws DatabaseException{
      
    }
  
    // line 28 "../../../../Txn_inner.ump"
     protected void hook797() throws DatabaseException{
      
    }
  
    // line 30 "../../../../Txn_inner.ump"
     protected void hook798() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 21 "../../../../Txn_inner.ump"
    protected Txn _this ;
  // line 22 "../../../../Txn_inner.ump"
    protected DatabaseImpl dbImpl ;
  // line 23 "../../../../Txn_inner.ump"
    protected boolean deleteAtCommit ;
  // line 24 "../../../../Txn_inner.ump"
    protected int delta ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Txn.ump"
  private Set deletedDatabases ;

  
}