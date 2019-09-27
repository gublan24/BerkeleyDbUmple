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


  /**
   * 
   * Initialize the starting environment memory state
   */
  // line 9 "../../../../MemoryBudget.ump"
  public void initCacheMemoryUsage() throws DatabaseException{
    original();
	assert LatchSupport.countLatchesHeld() == 0;
  }

  // line 14 "../../../../MemoryBudget.ump"
   protected long hook347(long totalSize, INList inList) throws DatabaseException{
    inList.latchMajor();
	try {
	    totalSize = original(totalSize, inList);
	} finally {
	    inList.releaseMajorLatch();
	}
	return totalSize;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../MemoryBudget_inner.ump"
  public static class MemoryBudget_sinit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public MemoryBudget_sinit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../MemoryBudget_inner.ump"
     protected void hook348(){
      isJVM14=(LatchSupport.getJava5LatchClass() == null);
          original();
    }
  
  }
}