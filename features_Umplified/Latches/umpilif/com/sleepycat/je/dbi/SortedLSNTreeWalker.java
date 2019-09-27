/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../SortedLSNTreeWalker.ump"
// line 3 "../../../../SortedLSNTreeWalker_inner.ump"
public class SortedLSNTreeWalker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SortedLSNTreeWalker()
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
  // line 4 "../../../../SortedLSNTreeWalker_inner.ump"
  public static class SortedLSNTreeWalker_extractINsForDb
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public SortedLSNTreeWalker_extractINsForDb()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../SortedLSNTreeWalker_inner.ump"
     protected void hook356() throws DatabaseException{
      inList.latchMajor();
          original();
    }
  
    // line 10 "../../../../SortedLSNTreeWalker_inner.ump"
     protected void hook357() throws DatabaseException{
      inList.latchMinorAndDumpAddedINs();
          original();
    }
  
    // line 14 "../../../../SortedLSNTreeWalker_inner.ump"
     protected void hook358() throws DatabaseException{
      inList.releaseMajorLatch();
          original();
    }
  
  }
}