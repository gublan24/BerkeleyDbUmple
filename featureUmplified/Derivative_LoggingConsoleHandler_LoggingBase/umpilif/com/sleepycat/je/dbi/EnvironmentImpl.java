/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../EnvironmentImpl.ump"
// line 3 "../../../../EnvironmentImpl_inner.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../EnvironmentImpl_inner.ump"
  public static class EnvironmentImpl_initLogger
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImpl_initLogger()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../EnvironmentImpl_inner.ump"
    public Logger execute() throws DatabaseException{
      Logger result=original();
          if (_this.configManager.getBoolean(EnvironmentParams.JE_LOGGING_CONSOLE)) {
            consoleHandler=new ConsoleHandler();
            consoleHandler.setLevel(level);
            logger.addHandler(consoleHandler);
          }
          return result;
    }
  
  }
}