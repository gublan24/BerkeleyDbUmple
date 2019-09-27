/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../SecondaryDatabase.ump"
// line 3 "../../../SecondaryDatabase_inner.ump"
public class SecondaryDatabase
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SecondaryDatabase()
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
  // line 9 "../../../SecondaryDatabase.ump"
  public void trace(Level level, String methodName) throws DatabaseException{
    new SecondaryDatabase_trace(this, level, methodName).execute();
  }


  /**
   * 
   * Adds secondary to primary's list, and populates the secondary if needed.
   */
  // line 16 "../../../SecondaryDatabase.ump"
   private void init(Locker locker) throws DatabaseException{
    trace(Level.FINEST, "SecondaryDatabase open");
	original(locker);
  }

  // line 21 "../../../SecondaryDatabase.ump"
   protected void hook79(Transaction txn, DatabaseEntry key) throws DatabaseException{
    trace(Level.FINEST, "SecondaryDatabase.delete", txn, key, null, null);
	original(txn, key);
  }

  // line 26 "../../../SecondaryDatabase.ump"
   protected void hook80(Transaction txn, DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryDatabase.get", txn, key, null, lockMode);
	original(txn, key, lockMode);
  }

  // line 32 "../../../SecondaryDatabase.ump"
   protected void hook81(Transaction txn, DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryDatabase.getSearchBoth", txn, key, data, lockMode);
	original(txn, key, data, lockMode);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 4 "../../../SecondaryDatabase_inner.ump"
  public static class SecondaryDatabase_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public SecondaryDatabase_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../SecondaryDatabase_inner.ump"
    public  SecondaryDatabase_trace(SecondaryDatabase _this, Level level, String methodName){
      this._this=_this;
          this.level=level;
          this.methodName=methodName;
    }
  
    // line 11 "../../../SecondaryDatabase_inner.ump"
    public void execute() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 12 "../../../SecondaryDatabase_inner.ump"
    protected SecondaryDatabase _this ;
  // line 13 "../../../SecondaryDatabase_inner.ump"
    protected Level level ;
  // line 14 "../../../SecondaryDatabase_inner.ump"
    protected String methodName ;
  // line 15 "../../../SecondaryDatabase_inner.ump"
    protected Logger logger ;
  // line 16 "../../../SecondaryDatabase_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}