/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Txn.ump"
// line 3 "../../../../Txn_inner.ump"
public class Txn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Txn()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../../Txn.ump"
   private void updateMemoryUsage(int delta){
    inMemorySize += delta;
	accumulatedDelta += delta;
	if (accumulatedDelta > ACCUMULATED_LIMIT || accumulatedDelta < -ACCUMULATED_LIMIT) {
	    envImpl.getMemoryBudget().updateMiscMemoryUsage(accumulatedDelta);
	    accumulatedDelta = 0;
	}
  }

  // line 21 "../../../../Txn.ump"
  public int getAccumulatedDelta(){
    return accumulatedDelta;
  }

  // line 25 "../../../../Txn.ump"
   protected void hook809() throws DatabaseException{
    updateMemoryUsage(MemoryBudget.TXN_OVERHEAD);
	original();
  }

  // line 30 "../../../../Txn.ump"
   protected void hook810(int delta){
    delta += READ_LOCK_OVERHEAD;
	updateMemoryUsage(delta);
	original(delta);
  }

  // line 36 "../../../../Txn.ump"
   protected int hook811(int delta){
    delta = MemoryBudget.HASHSET_OVERHEAD;
	return original(delta);
  }

  // line 41 "../../../../Txn.ump"
   protected void hook812() throws DatabaseException{
    updateMemoryUsage(0 - READ_LOCK_OVERHEAD);
	original();
  }

  // line 46 "../../../../Txn.ump"
   protected void hook813() throws DatabaseException{
    updateMemoryUsage(0 - WRITE_LOCK_OVERHEAD);
	original();
  }

  // line 51 "../../../../Txn.ump"
   protected void hook814(){
    updateMemoryUsage(0 - WRITE_LOCK_OVERHEAD);
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.txn;
  
  @MethodObject
  // line 4 "../../../../Txn_inner.ump"
  public static class Txn_addLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Txn_addLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Txn_inner.ump"
     protected void hook815() throws DatabaseException{
      delta=0;
          original();
    }
  
    // line 10 "../../../../Txn_inner.ump"
     protected void hook816() throws DatabaseException{
      _this.updateMemoryUsage(delta);
          original();
    }
  
    // line 14 "../../../../Txn_inner.ump"
     protected void hook817() throws DatabaseException{
      delta+=_this.WRITE_LOCK_OVERHEAD;
          original();
    }
  
    // line 18 "../../../../Txn_inner.ump"
     protected void hook818() throws DatabaseException{
      delta+=MemoryBudget.TWOHASHMAPS_OVERHEAD;
          original();
    }
  
    // line 22 "../../../../Txn_inner.ump"
     protected void hook819() throws DatabaseException{
      delta-=_this.READ_LOCK_OVERHEAD;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Txn.ump"
  private final int READ_LOCK_OVERHEAD = MemoryBudget.HASHSET_ENTRY_OVERHEAD ;
// line 7 "../../../../Txn.ump"
  private final int WRITE_LOCK_OVERHEAD = MemoryBudget.HASHMAP_ENTRY_OVERHEAD + MemoryBudget.LONG_OVERHEAD ;
// line 9 "../../../../Txn.ump"
  private int accumulatedDelta = 0 ;

  
}