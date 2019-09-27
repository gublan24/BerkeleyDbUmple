/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.latch.LatchSupport;

// line 3 "../../../../DatabaseImpl.ump"
// line 3 "../../../../DatabaseImpl_inner.ump"
public class DatabaseImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseImpl()
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
  // line 4 "../../../../DatabaseImpl_inner.ump"
  public static class DatabaseImpl_preload
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DatabaseImpl_preload()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DatabaseImpl_inner.ump"
    public PreloadStats execute() throws DatabaseException{
      PreloadStats result=original();
          assert LatchSupport.countLatchesHeld() == 0;
          return result;
    }
  
  }
}