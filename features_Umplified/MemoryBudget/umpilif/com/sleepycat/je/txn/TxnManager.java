/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../TxnManager.ump"
public class TxnManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TxnManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../TxnManager.ump"
   protected void hook828(Txn txn) throws DatabaseException{
    env.getMemoryBudget().updateMiscMemoryUsage(txn.getAccumulatedDelta() - txn.getInMemorySize());
	original(txn);
  }

  // line 11 "../../../../TxnManager.ump"
   protected void hook829() throws DatabaseException{
    env.getMemoryBudget().updateMiscMemoryUsage(MemoryBudget.HASHMAP_ENTRY_OVERHEAD);
	original();
  }

  // line 16 "../../../../TxnManager.ump"
   protected void hook830() throws DatabaseException{
    env.getMemoryBudget().updateMiscMemoryUsage(0 - MemoryBudget.HASHMAP_ENTRY_OVERHEAD);
	original();
  }

}