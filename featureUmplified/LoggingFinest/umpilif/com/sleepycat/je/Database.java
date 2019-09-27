/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

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
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 10 "../../../Database.ump"
  public void trace(Level level, String methodName, Transaction txn, DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    new Database_trace(this, level, methodName, txn, key, data, lockMode).execute();
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 17 "../../../Database.ump"
  public void trace(Level level, String methodName, Transaction txn, CursorConfig config) throws DatabaseException{
    new Database_trace2(this, level, methodName, txn, config).execute();
  }

  // line 21 "../../../Database.ump"
   protected void hook44() throws DatabaseException{
    trace(Level.FINEST, "Database.close: ", null, null);
	original();
  }

  // line 26 "../../../Database.ump"
   protected void hook45(Transaction txn, DatabaseEntry key) throws DatabaseException{
    trace(Level.FINEST, "Database.openSequence", txn, key, null, null);
	original(txn, key);
  }

  // line 31 "../../../Database.ump"
   protected void hook46(Transaction txn, CursorConfig cursorConfig) throws DatabaseException{
    trace(Level.FINEST, "Database.openCursor", txn, cursorConfig);
	original(txn, cursorConfig);
  }

  // line 36 "../../../Database.ump"
   protected void hook47(Transaction txn, DatabaseEntry key) throws DatabaseException{
    trace(Level.FINEST, "Database.delete", txn, key, null, null);
	original(txn, key);
  }

  // line 41 "../../../Database.ump"
   protected void hook48(Transaction txn, DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Database.get", txn, key, null, lockMode);
	original(txn, key, lockMode);
  }

  // line 47 "../../../Database.ump"
   protected void hook49(Transaction txn, DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Database.getSearchBoth", txn, key, data, lockMode);
	original(txn, key, data, lockMode);
  }

  // line 52 "../../../Database.ump"
   protected void hook50(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    trace(Level.FINEST, "Database.put", txn, key, data, null);
	original(txn, key, data);
  }

  // line 57 "../../../Database.ump"
   protected void hook51(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    trace(Level.FINEST, "Database.putNoOverwrite", txn, key, data, null);
	original(txn, key, data);
  }

  // line 62 "../../../Database.ump"
   protected void hook52(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    trace(Level.FINEST, "Database.putNoDupData", txn, key, data, null);
	original(txn, key, data);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 4 "../../../Database_inner.ump"
  public static class Database_trace2
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_trace2()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../Database_inner.ump"
    public  Database_trace2(Database _this, Level level, String methodName, Transaction txn, CursorConfig config){
      this._this=_this;
          this.level=level;
          this.methodName=methodName;
          this.txn=txn;
          this.config=config;
    }
  
    // line 13 "../../../Database_inner.ump"
    public void execute() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 14 "../../../Database_inner.ump"
    protected Database _this ;
  // line 15 "../../../Database_inner.ump"
    protected Level level ;
  // line 16 "../../../Database_inner.ump"
    protected String methodName ;
  // line 17 "../../../Database_inner.ump"
    protected Transaction txn ;
  // line 18 "../../../Database_inner.ump"
    protected CursorConfig config ;
  // line 19 "../../../Database_inner.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 21 "../../../Database_inner.ump"
  public static class Database_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 23 "../../../Database_inner.ump"
    public  Database_trace(Database _this, Level level, String methodName, Transaction txn, DatabaseEntry key, DatabaseEntry data, LockMode lockMode){
      this._this=_this;
          this.level=level;
          this.methodName=methodName;
          this.txn=txn;
          this.key=key;
          this.data=data;
          this.lockMode=lockMode;
    }
  
    // line 32 "../../../Database_inner.ump"
    public void execute() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 33 "../../../Database_inner.ump"
    protected Database _this ;
  // line 34 "../../../Database_inner.ump"
    protected Level level ;
  // line 35 "../../../Database_inner.ump"
    protected String methodName ;
  // line 36 "../../../Database_inner.ump"
    protected Transaction txn ;
  // line 37 "../../../Database_inner.ump"
    protected DatabaseEntry key ;
  // line 38 "../../../Database_inner.ump"
    protected DatabaseEntry data ;
  // line 39 "../../../Database_inner.ump"
    protected LockMode lockMode ;
  // line 40 "../../../Database_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}