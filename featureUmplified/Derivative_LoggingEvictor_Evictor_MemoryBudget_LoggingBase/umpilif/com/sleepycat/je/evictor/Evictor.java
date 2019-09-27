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
  public static class Evictor_isRunnable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_isRunnable()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
     protected void hook370() throws DatabaseException{
      sb.append(" doRun=").append(doRun);
          sb.append(" JEusedBytes=").append(_this.formatter.format(currentUsage));
          original();
    }
  
  }
}