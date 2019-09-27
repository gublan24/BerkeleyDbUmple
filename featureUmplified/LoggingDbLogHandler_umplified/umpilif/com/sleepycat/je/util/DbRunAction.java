/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;

// line 3 "../../../../DbRunAction.ump"
// line 3 "../../../../DbRunAction_inner.ump"
public class DbRunAction
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbRunAction()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.util;
  
  @MethodObject
  // line 4 "../../../../DbRunAction_inner.ump"
  public static class DbRunAction_main
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbRunAction_main()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DbRunAction_inner.ump"
     protected void hook847() throws Exception{
      if (readOnly) {
            envConfig.setConfigParam(EnvironmentParams.JE_LOGGING_DBLOG.getName(),"false");
            envConfig.setReadOnly(true);
          }
          original();  //@Abdulaziz //toUpdate
    }
  
  }
}