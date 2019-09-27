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
  public static class FileProcessor_processFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../FileProcessor_inner.ump"
     protected void hook118() throws DatabaseException,IOException{
      
    }
  
    // line 8 "../../../../FileProcessor_inner.ump"
     protected void hook161() throws DatabaseException,IOException{
      adjustMem=(2 * readBufferSize) + obsoleteOffsets.getLogSize();
          budget=_this.env.getMemoryBudget();
    {
            this.hook118();
            budget.updateMiscMemoryUsage(adjustMem);
          }
          original();
    }
  
    // line 17 "../../../../FileProcessor_inner.ump"
     protected void hook162() throws DatabaseException,IOException{
      budget.updateMiscMemoryUsage(0 - adjustMem);
          original();
    }
  
  }
}