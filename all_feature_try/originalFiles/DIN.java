// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/MemoryBudget/com/sleepycat/je/tree/DIN.java
package com.sleepycat.je.tree;
public final class DIN {
  /** 
 * Count up the memory usage attributable to this node alone. LNs children
 * are counted by their BIN/DIN parents, but INs are not counted by 
 * their parents because they are resident on the IN list.
 */
  protected long computeMemorySize(){
    long size=super.computeMemorySize();
    if (dupCountLNRef != null) {
      size+=getEntryInMemorySize(dupCountLNRef.getKey(),dupCountLNRef.getTarget());
    }
    return size;
  }
  public static long computeOverhead(  DbConfigManager configManager) throws DatabaseException {
    return MemoryBudget.DIN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }
  protected long getMemoryOverhead(  MemoryBudget mb){
    return mb.getDINOverhead();
  }
  /** 
 * Create a new DIN.
 */
  DIN(  DatabaseImpl db,  byte[] identifierKey,  int capacity,  byte[] dupKey,  ChildReference dupCountLNRef,  int level){
    initMemorySize();
  }
  /** 
 * Assign the Dup Count LN.
 */
  void setDupCountLN(  ChildReference dupCountLNRef){
    updateMemorySize(this.dupCountLNRef,dupCountLNRef);
    original(dupCountLNRef);
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class DIN_updateDupCountLNRefAndNullTarget {
//     void execute(){
//       original();
//       newSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
//       _this.updateMemorySize(oldSize,newSize);
//     }
//     protected void hook614(){
//       oldSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// @MethodObject static class DIN_updateDupCountLN {
//     void execute(){
//       oldSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
//       original();
//       newSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
//       _this.updateMemorySize(oldSize,newSize);
//     }
//   }
// END_OF_INNER_ELEMENT 
}
