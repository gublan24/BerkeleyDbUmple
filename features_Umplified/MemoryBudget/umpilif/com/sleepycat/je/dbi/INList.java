/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../INList.ump"
// line 3 "../../../../INList_inner.ump"
public class INList
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INList()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../INList.ump"
  public  INList(EnvironmentImpl envImpl){
    updateMemoryUsage = true;
  }


  /**
   * 
   * Used only by tree verifier when validating INList. Must be called with orig.majorLatch acquired.
   */
  // line 15 "../../../../INList.ump"
  public  INList(INList orig, EnvironmentImpl envImpl) throws DatabaseException{
    updateMemoryUsage = false;
  }

  // line 19 "../../../../INList.ump"
   protected void hook346(IN in) throws DatabaseException{
    if (updateMemoryUsage) {
	    envImpl.getMemoryBudget().updateTreeMemoryUsage(in.getAccumulatedDelta() - in.getInMemorySize());
	    in.setInListResident(false);
	}
	original(in);
  }


  /**
   * 
   * Clear the entire list during recovery and at shutdown.
   */
  // line 30 "../../../../INList.ump"
   public void clear() throws DatabaseException{
    original();
	if (updateMemoryUsage) {
	    envImpl.getMemoryBudget().refreshTreeMemoryUsage(0);
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../INList_inner.ump"
  public static class INList_addAndSetMemory
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public INList_addAndSetMemory()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../INList_inner.ump"
    public void execute(){
      original();
          if (_this.updateMemoryUsage) {
            mb=_this.envImpl.getMemoryBudget();
            mb.updateTreeMemoryUsage(in.getInMemorySize());
            in.setInListResident(true);
          }
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../INList.ump"
  private boolean updateMemoryUsage ;

  
}