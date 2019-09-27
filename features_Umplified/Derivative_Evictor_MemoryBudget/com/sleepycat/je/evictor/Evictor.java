// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/Derivative_Evictor_MemoryBudget/com/sleepycat/je/evictor/Evictor.java
package com.sleepycat.je.evictor;
public class Evictor {
  protected long hook389(  long evictBytes,  IN renewedChild) throws DatabaseException {
    evictBytes=renewedChild.getInMemorySize();
    return original(evictBytes,renewedChild);
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class Evictor_isRunnable {
//     boolean execute() throws DatabaseException {
//       boolean result=original();
//       result=doRun;
//       return result;
//     }
//     protected void hook388() throws DatabaseException {
//       currentUsage=mb.getCacheMemoryUsage();
//       maxMem=mb.getCacheBudget();
//       doRun=((currentUsage - maxMem) > 0);
//       if (doRun) {
//         _this.currentRequiredEvictBytes=_this.evictBytesSetting;
//         _this.currentRequiredEvictBytes+=(currentUsage - maxMem);
//         if (_this.DEBUG) {
//           if (source == _this.SOURCE_CRITICAL) {
//             System.out.println("executed: critical runnable");
//           }
//         }
//       }
//       if (_this.runnableHook != null) {
//         doRun=((Boolean)_this.runnableHook.getHookValue()).booleanValue();
//         _this.currentRequiredEvictBytes=maxMem;
//       }
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
