/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;

// line 3 "../../../../Evictor.ump"
// line 3 "../../../../Evictor_inner.ump"
public class Evictor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.evictor;
  
  @MethodObject
  // line 4 "../../../../Evictor_inner.ump"
  public static class Evictor_doCriticalEviction
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_doCriticalEviction()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
    public void execute() throws DatabaseException{
      mb=_this.envImpl.getMemoryBudget();
          currentUsage=mb.getCacheMemoryUsage();
          maxMem=mb.getCacheBudget();
          over=currentUsage - maxMem;
          if (over > mb.getCriticalThreshold()) {
            if (_this.DEBUG) {
              System.out.println("***critical detected:" + over);
            }
            _this.doEvict(_this.SOURCE_CRITICAL,true);
          }
          original();
    }
  
  }
}