/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../LN.ump"
public class LN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Compute the approximate size of this node in memory for evictor invocation purposes.
   */
  // line 9 "../../../../LN.ump"
   public long getMemorySizeIncludedByParent(){
    int size = MemoryBudget.LN_OVERHEAD;
	if (data != null) {
	    size += MemoryBudget.byteArraySize(data.length);
	}
	return size;
  }

}