/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Cursor.ump"
// line 3 "../../../Cursor_inner.ump"
public class Cursor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cursor()
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
  // line 9 "../../../Cursor.ump"
  public void trace(Level level, String methodName, DatabaseEntry key, DatabaseEntry data, LockMode lockMode){
    new Cursor_trace(this, level, methodName, key, data, lockMode).execute();
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 16 "../../../Cursor.ump"
  public void trace(Level level, String methodName, LockMode lockMode){
    new Cursor_trace2(this, level, methodName, lockMode).execute();
  }

  // line 20 "../../../Cursor.ump"
   protected void hook0() throws DatabaseException{
    trace(Level.FINEST, "Cursor.count: ", null);
	original();
  }

  // line 25 "../../../Cursor.ump"
   protected void hook1() throws DatabaseException{
    trace(Level.FINEST, "Cursor.delete: ", null);
	original();
  }

  // line 30 "../../../Cursor.ump"
   protected void hook2(DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    trace(Level.FINEST, "Cursor.put: ", key, data, null);
	original(key, data);
  }

  // line 35 "../../../Cursor.ump"
   protected void hook3(DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    trace(Level.FINEST, "Cursor.putNoOverwrite: ", key, data, null);
	original(key, data);
  }

  // line 40 "../../../Cursor.ump"
   protected void hook4(DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    trace(Level.FINEST, "Cursor.putNoDupData: ", key, data, null);
	original(key, data);
  }

  // line 45 "../../../Cursor.ump"
   protected void hook5(DatabaseEntry data) throws DatabaseException{
    trace(Level.FINEST, "Cursor.putCurrent: ", null, data, null);
	original(data);
  }

  // line 50 "../../../Cursor.ump"
   protected void hook6(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getCurrent: ", lockMode);
	original(lockMode);
  }

  // line 55 "../../../Cursor.ump"
   protected void hook7(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getFirst: ", lockMode);
	original(lockMode);
  }

  // line 60 "../../../Cursor.ump"
   protected void hook8(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getLast: ", lockMode);
	original(lockMode);
  }

  // line 65 "../../../Cursor.ump"
   protected void hook9(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getNext: ", lockMode);
	original(lockMode);
  }

  // line 70 "../../../Cursor.ump"
   protected void hook10(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getNextDup: ", lockMode);
	original(lockMode);
  }

  // line 75 "../../../Cursor.ump"
   protected void hook11(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getNextNoDup: ", lockMode);
	original(lockMode);
  }

  // line 80 "../../../Cursor.ump"
   protected void hook12(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getPrev: ", lockMode);
	original(lockMode);
  }

  // line 85 "../../../Cursor.ump"
   protected void hook13(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getPrevDup: ", lockMode);
	original(lockMode);
  }

  // line 90 "../../../Cursor.ump"
   protected void hook14(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getPrevNoDup: ", lockMode);
	original(lockMode);
  }

  // line 95 "../../../Cursor.ump"
   protected void hook15(DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getSearchKey: ", key, null, lockMode);
	original(key, lockMode);
  }

  // line 100 "../../../Cursor.ump"
   protected void hook16(DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getSearchKeyRange: ", key, null, lockMode);
	original(key, lockMode);
  }

  // line 105 "../../../Cursor.ump"
   protected void hook17(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getSearchBoth: ", key, data, lockMode);
	original(key, data, lockMode);
  }

  // line 110 "../../../Cursor.ump"
   protected void hook18(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "Cursor.getSearchBothRange: ", key, data, lockMode);
	original(key, data, lockMode);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 4 "../../../Cursor_inner.ump"
  public static class Cursor_trace2
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Cursor_trace2()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../Cursor_inner.ump"
    public  Cursor_trace2(Cursor _this, Level level, String methodName, LockMode lockMode){
      this._this=_this;
          this.level=level;
          this.methodName=methodName;
          this.lockMode=lockMode;
    }
  
    // line 12 "../../../Cursor_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 13 "../../../Cursor_inner.ump"
    protected Cursor _this ;
  // line 14 "../../../Cursor_inner.ump"
    protected Level level ;
  // line 15 "../../../Cursor_inner.ump"
    protected String methodName ;
  // line 16 "../../../Cursor_inner.ump"
    protected LockMode lockMode ;
  // line 17 "../../../Cursor_inner.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 19 "../../../Cursor_inner.ump"
  public static class Cursor_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Cursor_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 21 "../../../Cursor_inner.ump"
    public  Cursor_trace(Cursor _this, Level level, String methodName, DatabaseEntry key, DatabaseEntry data, LockMode lockMode){
      this._this=_this;
          this.level=level;
          this.methodName=methodName;
          this.key=key;
          this.data=data;
          this.lockMode=lockMode;
    }
  
    // line 29 "../../../Cursor_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 30 "../../../Cursor_inner.ump"
    protected Cursor _this ;
  // line 31 "../../../Cursor_inner.ump"
    protected Level level ;
  // line 32 "../../../Cursor_inner.ump"
    protected String methodName ;
  // line 33 "../../../Cursor_inner.ump"
    protected DatabaseEntry key ;
  // line 34 "../../../Cursor_inner.ump"
    protected DatabaseEntry data ;
  // line 35 "../../../Cursor_inner.ump"
    protected LockMode lockMode ;
  // line 36 "../../../Cursor_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}