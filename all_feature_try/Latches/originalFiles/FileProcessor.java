// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Latches/originalFiles/FileProcessor.java
package com.sleepycat.je.cleaner;
class FileProcessor {
  protected void hook134(  Tree tree,  DatabaseImpl db,  IN inClone,  long lsn,  SearchResult result) throws DatabaseException {
    try {
      original(tree,db,inClone,lsn,result);
    }
  finally {
      if ((result != null) && (result.exactParentFound)) {
        result.parent.releaseLatch();
      }
    }
  }
  protected void hook136(  IN inInTree) throws DatabaseException {
    inInTree.releaseLatch();
    original(inInTree);
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class FileProcessor_processLN {
//     protected void hook135() throws DatabaseException {
//       if (parentDIN != null) {
//         parentDIN.releaseLatchIfOwner();
//       }
//       if (bin != null) {
//         bin.releaseLatchIfOwner();
//       }
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
