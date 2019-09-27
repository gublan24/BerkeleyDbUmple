/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RecoveryManager.ump"
// line 3 "../../../../RecoveryManager_inner.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Log trace information about root deletions, called by INCompressor and recovery.
   */
  // line 9 "../../../../RecoveryManager.ump"
   public static  void traceRootDeletion(Level level, DatabaseImpl database){
    new RecoveryManager_traceRootDeletion(level, database).execute();
  }

  // line 13 "../../../../RecoveryManager.ump"
   protected void hook557(DatabaseImpl db) throws DatabaseException{
    traceRootDeletion(Level.FINE, db);
	original(db);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 4 "../../../../RecoveryManager_inner.ump"
  public static class RecoveryManager_traceRootDeletion
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RecoveryManager_traceRootDeletion()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../RecoveryManager_inner.ump"
    public  RecoveryManager_traceRootDeletion(Level level, DatabaseImpl database){
      this.level=level;
          this.database=database;
    }
  
    // line 10 "../../../../RecoveryManager_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 11 "../../../../RecoveryManager_inner.ump"
    protected Level level ;
  // line 12 "../../../../RecoveryManager_inner.ump"
    protected DatabaseImpl database ;
  // line 13 "../../../../RecoveryManager_inner.ump"
    protected Logger logger ;
  // line 14 "../../../../RecoveryManager_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}