/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../Checkpointer.ump"
// line 3 "../../../../Checkpointer_inner.ump"
public class Checkpointer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Checkpointer()
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
  // line 4 "../../../../Checkpointer_inner.ump"
  public static class Checkpointer_isRunnable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_isRunnable()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Checkpointer_inner.ump"
     protected void hook518() throws DatabaseException{
      sb.append(" time interval=").append(useTimeInterval);
          original();
    }
  
  }
}