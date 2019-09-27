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
    public boolean execute() throws DatabaseException{
      boolean result=original();
          mb.updateTreeMemoryUsage(memoryChange);
          return result;
    }
  
    // line 11 "../../../../SortedLSNTreeWalker_inner.ump"
     protected void hook360() throws DatabaseException{
      memoryChange=0;
          mb=_this.envImpl.getMemoryBudget();
          original();
    }
  
    // line 16 "../../../../SortedLSNTreeWalker_inner.ump"
     protected void hook361() throws DatabaseException{
      memoryChange+=(thisIN.getAccumulatedDelta() - thisIN.getInMemorySize());
          thisIN.setInListResident(false);
          original();
    }
  
    // line 21 "../../../../SortedLSNTreeWalker_inner.ump"
     protected void hook362() throws DatabaseException{
      mb.updateTreeMemoryUsage(memoryChange);
          original();
    }
  
  }
}