/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../UtilizationTracker.ump"
// line 3 "../../../../UtilizationTracker_inner.ump"
public class UtilizationTracker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UtilizationTracker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../UtilizationTracker_inner.ump"
  public static class UtilizationTracker_evictMemory
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationTracker_evictMemory()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../UtilizationTracker_inner.ump"
     protected void hook196() throws DatabaseException{
      b2&=totalBytes > mb.getTrackerBudget();
          original();
    }
  
    // line 10 "../../../../UtilizationTracker_inner.ump"
     protected void hook197() throws DatabaseException{
      b1&=mem > largestBytes;
          original();
    }
  
    // line 14 "../../../../UtilizationTracker_inner.ump"
     protected void hook198() throws DatabaseException{
      mem=tfs.getMemorySize();
          totalBytes+=mem;
          original();
    }
  
    // line 19 "../../../../UtilizationTracker_inner.ump"
     protected void hook199() throws DatabaseException{
      largestBytes=mem;
          original();
    }
  
  }
}