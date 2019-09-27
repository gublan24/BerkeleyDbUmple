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
  public static class Evictor_evictBatch
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_evictBatch()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
     protected void hook368() throws DatabaseException{
      msg+=" nNodesSelected=" + _this.nNodesSelectedThisRun + " nEvicted="+ _this.nNodesEvictedThisRun+ " nBINsStripped="+ _this.nBINsStrippedThisRun;
          original();
    }
  
    // line 10 "../../../../Evictor_inner.ump"
     protected void hook369() throws DatabaseException{
      msg+="pass=" + _this.nEvictPasses;
          original();
    }
  
  }
}