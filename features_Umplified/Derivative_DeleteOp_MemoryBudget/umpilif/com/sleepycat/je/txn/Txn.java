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
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.txn;
  
  @MethodObject
  // line 4 "../../../../Txn_inner.ump"
  public static class Txn_markDeleteAtTxnEnd
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Txn_markDeleteAtTxnEnd()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Txn_inner.ump"
     protected void hook796() throws DatabaseException{
      delta+=MemoryBudget.HASHSET_ENTRY_OVERHEAD + MemoryBudget.OBJECT_OVERHEAD;
          _this.updateMemoryUsage(delta);
          original();
    }
  
    // line 11 "../../../../Txn_inner.ump"
     protected void hook797() throws DatabaseException{
      delta=0;
          original();
    }
  
    // line 15 "../../../../Txn_inner.ump"
     protected void hook798() throws DatabaseException{
      delta+=MemoryBudget.HASHSET_OVERHEAD;
          original();
    }
  
  }
}