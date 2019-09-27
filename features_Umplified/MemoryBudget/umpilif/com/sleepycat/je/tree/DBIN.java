/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../DBIN.ump"
public class DBIN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DBIN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Count up the memory usage attributable to this node alone.
   */
  // line 9 "../../../../DBIN.ump"
   protected long computeMemorySize(){
    long size = super.computeMemorySize();
	return size;
  }

  // line 14 "../../../../DBIN.ump"
   public static  long computeOverhead(DbConfigManager configManager) throws DatabaseException{
    return MemoryBudget.DBIN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }

  // line 18 "../../../../DBIN.ump"
   protected long getMemoryOverhead(MemoryBudget mb){
    return mb.getDBINOverhead();
  }

}