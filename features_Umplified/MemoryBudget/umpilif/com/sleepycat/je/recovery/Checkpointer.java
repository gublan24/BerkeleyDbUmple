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
  public static class Checkpointer_selectDirtyINs
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_selectDirtyINs()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Checkpointer_inner.ump"
     protected void hook553() throws DatabaseException{
      totalSize=0;
          mb=_this.envImpl.getMemoryBudget();
          original();
    }
  
    // line 11 "../../../../Checkpointer_inner.ump"
     protected void hook554() throws DatabaseException{
      mb.refreshTreeMemoryUsage(totalSize);
          original();
    }
  
    // line 15 "../../../../Checkpointer_inner.ump"
     protected void hook530() throws DatabaseException{
      totalSize=mb.accumulateNewUsage(in,totalSize);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 19 "../../../../Checkpointer_inner.ump"
  public static class Checkpointer_doCheckpoint
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_doCheckpoint()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 21 "../../../../Checkpointer_inner.ump"
     protected void hook548() throws DatabaseException{
      dirtyMapMemSize=0;
          original();
    }
  
    // line 25 "../../../../Checkpointer_inner.ump"
     protected void hook549() throws DatabaseException{
      mb.updateMiscMemoryUsage(0 - dirtyMapMemSize);
          original();
    }
  
    // line 29 "../../../../Checkpointer_inner.ump"
     protected void hook550() throws DatabaseException{
      mb.updateMiscMemoryUsage(totalSize);
          original();
    }
  
    // line 33 "../../../../Checkpointer_inner.ump"
     protected void hook551() throws DatabaseException{
      totalSize=0;
          original();
    }
  
    // line 37 "../../../../Checkpointer_inner.ump"
     protected void hook552() throws DatabaseException{
      size=nodeSet.size() * MemoryBudget.CHECKPOINT_REFERENCE_SIZE;
          totalSize+=size;
          dirtyMapMemSize+=size;
          original();
    }
  
  }
}