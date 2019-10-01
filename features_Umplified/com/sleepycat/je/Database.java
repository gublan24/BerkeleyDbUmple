/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Database_static.ump"
// line 3 "../../../Database.ump"
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
   * Creates a database but does not open or fully initialize it. Is protected for use in compat package.
   */
  // line 11 "../../../Database.ump"
  public  Database(Environment env){
    logger = envHandle.getEnvironmentImpl().getLogger();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../Database_static.ump"
  public static class DbState
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbState()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 7 "../../../Database_static.ump"
    public  DbState(String stateName){
      this.stateName=stateName;
    }
  
    // line 10 "../../../Database_static.ump"
     public String toString(){
      return "DbState." + stateName;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../Database_static.ump"
    private String stateName ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 13 "../../../Database_static.ump"
  public static class Database_acquireTriggerListReadLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_acquireTriggerListReadLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 15 "../../../Database_static.ump"
    public  Database_acquireTriggerListReadLock(Database _this){
      this._this=_this;
    }
  
    // line 18 "../../../Database_static.ump"
    public void execute() throws DatabaseException{
      if (_this.triggerList == null) {
            _this.triggerList=new ArrayList();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 22 "../../../Database_static.ump"
    protected Database _this ;
  // line 23 "../../../Database_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 25 "../../../Database_static.ump"
  public static class Database_acquireTriggerListWriteLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_acquireTriggerListWriteLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 27 "../../../Database_static.ump"
    public  Database_acquireTriggerListWriteLock(Database _this){
      this._this=_this;
    }
  
    // line 30 "../../../Database_static.ump"
    public void execute() throws DatabaseException{
      if (_this.triggerList == null) {
            _this.triggerList=new ArrayList();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 34 "../../../Database_static.ump"
    protected Database _this ;
  // line 35 "../../../Database_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 37 "../../../Database_static.ump"
  public static class Database_releaseTriggerListWriteLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_releaseTriggerListWriteLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 39 "../../../Database_static.ump"
    public  Database_releaseTriggerListWriteLock(Database _this){
      this._this=_this;
    }
  
    // line 42 "../../../Database_static.ump"
    public void execute() throws DatabaseException{
      if (_this.triggerList.size() == 0) {
            _this.triggerList=null;
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 46 "../../../Database_static.ump"
    protected Database _this ;
  // line 47 "../../../Database_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../Database.ump"
  private Logger logger ;

  
}