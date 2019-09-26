// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/LookAHEADCache/com/sleepycat/je/cleaner/FileProcessor.java
package com.sleepycat.je.cleaner;
class FileProcessor {
// START_OF_INNER_ELEMENT 
// @MethodObject static class FileProcessor_processLN {
// 	protected LookAheadCache lookAheadCache;
// 	
//     protected void hook117() throws DatabaseException {
//     }
//     void execute() throws DatabaseException {
//       lookAheadCache=(LookAheadCache)lookAheadCachep;
//       original();
//     }
//     protected void hook132() throws DatabaseException {
//       offset=lookAheadCache.nextOffset();
//       info=lookAheadCache.remove(offset);
//       original();
//     }
//     protected void hook133() throws DatabaseException {
//       if (!isDupCountLN) {
//         for (int i=0; i < bin.getNEntries(); i+=1) {
//           lsn=bin.getLsn(i);
//           if (i != index && !bin.isEntryKnownDeleted(i) && !bin.isEntryPendingDeleted(i) && DbLsn.getFileNumber(lsn) == fileNum.longValue()) {
//             myOffset=new Long(DbLsn.getFileOffset(lsn));
//             myInfo=lookAheadCache.remove(myOffset);
//             if (myInfo != null) {
//               this.hook117();
//               _this.processFoundLN(myInfo,lsn,lsn,bin,i,null);
//             }
//           }
//         }
//       }
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// @MethodObject static class FileProcessor_processFile {
//     protected void hook116() throws DatabaseException, IOException {
//     }
//     protected void hook127() throws DatabaseException, IOException {
//       lookAheadCache=new LookAheadCache(lookAheadCacheSize);
//       original();
//     }
//     protected void hook128() throws DatabaseException, IOException {
//       lookAheadCacheSize=_this.cleaner.lookAheadCacheSize;
//       original();
//     }
//     protected void hook129() throws DatabaseException, IOException {
//       while (!lookAheadCache.isEmpty()) {
//         this.hook116();
//         _this.processLN(fileNum,location,null,null,lookAheadCache,dbCache);
//       }
//       original();
//     }
//     protected void hook130() throws DatabaseException, IOException {
//       lookAheadCache.add(aLsn,aLninfo);
//       if (lookAheadCache.isFull()) {
//         original();
//       }
//     }
//     protected void hook131() throws DatabaseException, IOException {
//       p=lookAheadCache;
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
