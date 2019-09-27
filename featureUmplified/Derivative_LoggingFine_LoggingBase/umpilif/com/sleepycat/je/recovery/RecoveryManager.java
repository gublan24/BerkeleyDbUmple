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
    public void execute(){
      logger=database.getDbEnvironment().getLogger();
          if (logger.isLoggable(level)) {
            sb=new StringBuffer();
            sb.append(TRACE_ROOT_DELETE);
            sb.append(" Dbid=").append(database.getId());
            logger.log(level,sb.toString());
          }
          original();
    }
  
  }
}