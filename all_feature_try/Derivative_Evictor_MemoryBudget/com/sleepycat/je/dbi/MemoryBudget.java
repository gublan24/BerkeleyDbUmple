// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Derivative_Evictor_MemoryBudget/com/sleepycat/je/dbi/MemoryBudget.java
// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Derivative_Evictor_MemoryBudget/com/sleepycat/je/dbi/MemoryBudget.java
package com.sleepycat.je.dbi;
public class MemoryBudget {
  private long criticalThreshold;
  public long getCriticalThreshold(){
    return criticalThreshold;
  }
  /** 
 * Update the environment wide tree memory count, wake up the evictor if
 * necessary.
 * @param incrementnote that increment may be negative.
 */
  public void updateTreeMemoryUsage(  long increment){
    original(increment);
    if (getCacheMemoryUsage() > cacheBudget) {
      envImpl.alertEvictor();
    }
  }
  /** 
 * Update the environment wide misc memory count, wake up the evictor if
 * necessary.
 * @param incrementnote that increment may be negative.
 */
  public void updateMiscMemoryUsage(  long increment){
    original(increment);
    if (getCacheMemoryUsage() > cacheBudget) {
      envImpl.alertEvictor();
    }
  }
  public void updateLockMemoryUsage(  long increment,  int lockTableIndex){
    original(increment,lockTableIndex);
    if (getCacheMemoryUsage() > cacheBudget) {
      envImpl.alertEvictor();
    }
  }
// START_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// // @MethodObject static class MemoryBudget_reset {
// //     protected void hook349() throws DatabaseException {
// //       _this.criticalThreshold=newCriticalThreshold;
// //       original();
// //     }
// //   }
// END_OF_INNER_ELEMENT 
// END_OF_INNER_ELEMENT 
}
