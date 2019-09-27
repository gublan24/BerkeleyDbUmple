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
  public static class DbRunAction_doEvict
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbRunAction_doEvict()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DbRunAction_inner.ump"
     protected void hook836() throws DatabaseException{
      c.setCacheSize(cacheUsage / 2);
          original();
    }
  
    // line 10 "../../../../DbRunAction_inner.ump"
     protected void hook837() throws DatabaseException{
      cacheUsage=envImpl.getMemoryBudget().getCacheMemoryUsage();
          original();
    }
  
  }
}