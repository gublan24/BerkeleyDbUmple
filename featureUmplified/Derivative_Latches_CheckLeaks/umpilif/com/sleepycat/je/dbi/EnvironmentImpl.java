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

  // line 6 "../../../../EnvironmentImpl.ump"
   protected void hook311() throws DatabaseException{
    LatchSupport.clearNotes();
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../EnvironmentImpl_inner.ump"
  public static class EnvironmentImpl_checkLeaks
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImpl_checkLeaks()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../EnvironmentImpl_inner.ump"
     protected void hook312() throws DatabaseException{
      if (LatchSupport.countLatchesHeld() > 0) {
            clean=false;
            System.out.println("Some latches held at env close.");
            LatchSupport.dumpLatchesHeld();
          }
          original();
    }
  
  }
}