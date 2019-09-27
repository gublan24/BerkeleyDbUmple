/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../EnvironmentImpl.ump"
// line 3 "../../../../EnvironmentImpl_inner.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../EnvironmentImpl_inner.ump"
  public static class EnvironmentImpl_checkLeaks
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImpl_checkLeaks()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../EnvironmentImpl_inner.ump"
     protected void hook313() throws DatabaseException{
      statsConfig=new StatsConfig();
          statsConfig.setFast(false);
          lockStat=_this.lockStat(statsConfig);
          if (lockStat.getNTotalLocks() != 0) {
            clean=false;
            System.out.println("Problem: " + lockStat.getNTotalLocks() + " locks left");
            _this.txnManager.getLockManager().dump();
          }
          txnStat=_this.txnStat(statsConfig);
          if (txnStat.getNActive() != 0) {
            clean=false;
            System.out.println("Problem: " + txnStat.getNActive() + " txns left");
            active=txnStat.getActiveTxns();
            if (active != null) {
              for (int i=0; i < active.length; i+=1) {
                System.out.println(active[i]);
              }
            }
          }
          original();
    }
  
  }
}