/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../CursorImpl.ump"
// line 3 "../../../../CursorImpl_inner.ump"
public class CursorImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CursorImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../CursorImpl.ump"
   public LockStats getLockStats() throws DatabaseException{
    return locker.collectStats(new LockStats());
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_getNextDuplicate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_getNextDuplicate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../CursorImpl_inner.ump"
     protected void hook200() throws DatabaseException{
      if (_this.index < 0) {
            throw new ReturnObject(OperationStatus.NOTFOUND);
          }
          duplicateRoot=(DIN)_this.bin.fetchTarget(_this.index);
          this.hook201();
    }
  
    // line 13 "../../../../CursorImpl_inner.ump"
     protected void hook201() throws DatabaseException{
      dcl=duplicateRoot.getDupCountLN();
          if (dcl != null) {
            dcl.accumulateStats(treeStatsAccumulator);
          }
    }
  
    // line 19 "../../../../CursorImpl_inner.ump"
     protected void hook275() throws DatabaseException{
      treeStatsAccumulator=_this.getTreeStatsAccumulator();
          if (treeStatsAccumulator != null) {
            this.hook200();
          }
          original();
    }
  
  }
}