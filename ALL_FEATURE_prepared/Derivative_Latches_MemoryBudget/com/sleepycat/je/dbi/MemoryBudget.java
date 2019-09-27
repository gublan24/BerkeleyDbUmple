// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/Derivative_Latches_MemoryBudget/com/sleepycat/je/dbi/MemoryBudget.java
package com.sleepycat.je.dbi;
public class MemoryBudget {
  /** 
 * Initialize the starting environment memory state
 */
  void initCacheMemoryUsage() throws DatabaseException {
    original();
    assert LatchSupport.countLatchesHeld() == 0;
  }
  protected long hook347(  long totalSize,  INList inList) throws DatabaseException {
    inList.latchMajor();
    try {
      totalSize=original(totalSize,inList);
    }
  finally {
      inList.releaseMajorLatch();
    }
    return totalSize;
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class MemoryBudget_sinit {
//     protected void hook348(){
//       isJVM14=(LatchSupport.getJava5LatchClass() == null);
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
