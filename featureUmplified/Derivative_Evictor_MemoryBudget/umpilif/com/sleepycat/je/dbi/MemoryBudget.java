/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../MemoryBudget.ump"
// line 3 "../../../../MemoryBudget_inner.ump"
public class MemoryBudget
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MemoryBudget()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../MemoryBudget.ump"
   public long getCriticalThreshold(){
    return criticalThreshold;
  }


  /**
   * 
   * Update the environment wide tree memory count, wake up the evictor if necessary.
   * @param incrementnote that increment may be negative.
   */
  // line 16 "../../../../MemoryBudget.ump"
   public void updateTreeMemoryUsage(long increment){
    original(increment);
	if (getCacheMemoryUsage() > cacheBudget) {
	    envImpl.alertEvictor();
	}
  }


  /**
   * 
   * Update the environment wide misc memory count, wake up the evictor if necessary.
   * @param incrementnote that increment may be negative.
   */
  // line 27 "../../../../MemoryBudget.ump"
   public void updateMiscMemoryUsage(long increment){
    original(increment);
	if (getCacheMemoryUsage() > cacheBudget) {
	    envImpl.alertEvictor();
	}
  }

  // line 34 "../../../../MemoryBudget.ump"
   public void updateLockMemoryUsage(long increment, int lockTableIndex){
    original(increment, lockTableIndex);
	if (getCacheMemoryUsage() > cacheBudget) {
	    envImpl.alertEvictor();
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../MemoryBudget_inner.ump"
  public static class MemoryBudget_reset
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public MemoryBudget_reset()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../MemoryBudget_inner.ump"
     protected void hook349() throws DatabaseException{
      _this.criticalThreshold=newCriticalThreshold;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../MemoryBudget.ump"
  private long criticalThreshold ;

  
}