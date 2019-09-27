/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../TrackedFileSummary.ump"
// line 3 "../../../../TrackedFileSummary_inner.ump"
public class TrackedFileSummary
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TrackedFileSummary()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Return the total memory size for this object.  We only bother to budget obsolete detail, not the overhead for this object, for two reasons: 1) The number of these objects is very small, and 2) unit tests disable detail tracking as a way to prevent budget adjustments here.
   */
  // line 11 "../../../../TrackedFileSummary.ump"
  public int getMemorySize(){
    return memSize;
  }

  // line 15 "../../../../TrackedFileSummary.ump"
   private void updateMemoryBudget(int delta){
    memSize += delta;
	tracker.getEnvironment().getMemoryBudget().updateMiscMemoryUsage(delta);
  }

  // line 20 "../../../../TrackedFileSummary.ump"
   protected void hook168(){
    if (memSize > 0) {
	    updateMemoryBudget(0 - memSize);
	}
	original();
  }

  // line 27 "../../../../TrackedFileSummary.ump"
   protected void hook169(){
    updateMemoryBudget(-MemoryBudget.TFS_LIST_SEGMENT_OVERHEAD);
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../TrackedFileSummary_inner.ump"
  public static class TrackedFileSummary_trackObsolete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public TrackedFileSummary_trackObsolete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../TrackedFileSummary_inner.ump"
    public void execute(){
      original();
          if (adjustMem != 0) {
            _this.updateMemoryBudget(adjustMem);
          }
    }
  
    // line 12 "../../../../TrackedFileSummary_inner.ump"
     protected void hook170(){
      adjustMem=0;
          original();
    }
  
    // line 16 "../../../../TrackedFileSummary_inner.ump"
     protected void hook171(){
      adjustMem+=MemoryBudget.TFS_LIST_INITIAL_OVERHEAD;
          original();
    }
  
    // line 20 "../../../../TrackedFileSummary_inner.ump"
     protected void hook172(){
      adjustMem+=MemoryBudget.TFS_LIST_SEGMENT_OVERHEAD;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../TrackedFileSummary.ump"
  private int memSize ;

  
}