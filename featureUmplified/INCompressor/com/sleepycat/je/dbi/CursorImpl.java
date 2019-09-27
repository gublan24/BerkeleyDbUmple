// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/INCompressor/com/sleepycat/je/dbi/CursorImpl.java
package com.sleepycat.je.dbi;
public class CursorImpl {
  protected void hook281(  byte[] lnKey) throws DatabaseException {
    locker.addDeleteInfo(dupBin,new Key(lnKey));
    original(lnKey);
  }
  protected void hook282(  byte[] lnKey) throws DatabaseException {
    locker.addDeleteInfo(bin,new Key(lnKey));
    original(lnKey);
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class CursorImpl_fetchCurrent {
//     protected void hook280() throws DatabaseException {
//       envImpl=_this.database.getDbEnvironment();
//       envImpl.addToCompressorQueue(_this.targetBin,new Key(_this.targetBin.getKey(_this.targetIndex)),false);
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
