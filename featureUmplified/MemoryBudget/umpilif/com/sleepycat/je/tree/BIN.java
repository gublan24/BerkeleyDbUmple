/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../BIN.ump"
public class BIN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BIN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../BIN.ump"
   public static  long computeOverhead(DbConfigManager configManager) throws DatabaseException{
    return MemoryBudget.BIN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }

  // line 10 "../../../../BIN.ump"
   protected long getMemoryOverhead(MemoryBudget mb){
    return mb.getBINOverhead();
  }

  // line 14 "../../../../BIN.ump"
   protected void hook610(int index){
    updateMemorySize(getTarget(index), null);
	original(index);
  }

}