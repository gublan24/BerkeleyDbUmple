// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/Statistics/com/sleepycat/je/dbi/CursorImpl.java
package com.sleepycat.je.dbi;
public class CursorImpl {
  public LockStats getLockStats() throws DatabaseException {
    return locker.collectStats(new LockStats());
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class CursorImpl_getNextDuplicate {
//     protected void hook200() throws DatabaseException {
//       if (_this.index < 0) {
//         throw new ReturnObject(OperationStatus.NOTFOUND);
//       }
//       duplicateRoot=(DIN)_this.bin.fetchTarget(_this.index);
//       this.hook201();
//     }
//     protected void hook201() throws DatabaseException {
//       dcl=duplicateRoot.getDupCountLN();
//       if (dcl != null) {
//         dcl.accumulateStats(treeStatsAccumulator);
//       }
//     }
//     protected void hook275() throws DatabaseException {
//       treeStatsAccumulator=_this.getTreeStatsAccumulator();
//       if (treeStatsAccumulator != null) {
//         this.hook200();
//       }
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
