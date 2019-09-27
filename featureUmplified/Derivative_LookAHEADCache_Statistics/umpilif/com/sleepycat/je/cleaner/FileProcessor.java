/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../FileProcessor.ump"
// line 3 "../../../../FileProcessor_inner.ump"
public class FileProcessor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileProcessor()
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
  // line 4 "../../../../FileProcessor_inner.ump"
  public static class FileProcessor_processLN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processLN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../FileProcessor_inner.ump"
     protected void hook117() throws DatabaseException{
      _this.nLNQueueHitsThisRun++;
          _this.nLNsCleanedThisRun++;
          original();
    }
  
  }
}