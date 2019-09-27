/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../MemoryBudget.ump"
// line 3 "../../../../MemoryBudget_inner.ump"
public class MemoryBudget
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MemoryBudget()
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
  // line 4 "../../../../MemoryBudget_inner.ump"
  public static class MemoryBudget_reset
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public MemoryBudget_reset()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../MemoryBudget_inner.ump"
     protected void hook350() throws DatabaseException{
      newCriticalThreshold=(newMaxMemory * _this.envImpl.getConfigManager().getInt(EnvironmentParams.EVICTOR_CRITICAL_PERCENTAGE)) / 100;
          original();
    }
  
  }
}