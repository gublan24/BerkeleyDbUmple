/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../DupCountLN.ump"
public class DupCountLN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DupCountLN()
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
  // line 9 "../../../../DupCountLN.ump"
   public long getMemorySizeIncludedByParent(){
    return MemoryBudget.DUPCOUNTLN_OVERHEAD;
  }

}