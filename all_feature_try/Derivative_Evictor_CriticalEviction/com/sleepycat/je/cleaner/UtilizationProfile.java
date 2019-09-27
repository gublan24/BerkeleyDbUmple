// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Derivative_Evictor_CriticalEviction/com/sleepycat/je/cleaner/UtilizationProfile.java
package com.sleepycat.je.cleaner;
public class UtilizationProfile {
  protected void hook173() throws DatabaseException {
    env.getEvictor().doCriticalEviction();
    original();
  }
  protected void hook174() throws DatabaseException {
    env.getEvictor().doCriticalEviction();
    original();
  }
  protected void hook175() throws DatabaseException {
    env.getEvictor().doCriticalEviction();
    original();
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class UtilizationProfile_populateCache {
//     protected void hook176() throws DatabaseException {
//       _this.env.getEvictor().doCriticalEviction();
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
