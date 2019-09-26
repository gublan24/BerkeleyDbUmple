// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/LoggingCleaner/com/sleepycat/je/cleaner/FileProcessor.java
package com.sleepycat.je.cleaner;
class FileProcessor {
  protected void hook124(  long logLsn,  LN ln,  boolean obsolete,  boolean migrated,  boolean completed) throws DatabaseException {
    cleaner.trace(cleaner.detailedTraceLevel,Cleaner.CLEAN_LN,ln,logLsn,completed,obsolete,migrated);
    original(logLsn,ln,obsolete,migrated,completed);
  }
  protected void hook125(  IN inClone,  DatabaseImpl db,  long lsn,  boolean obsolete,  boolean dirtied,  boolean completed) throws DatabaseException {
    try {
      original(inClone,db,lsn,obsolete,dirtied,completed);
    }
  finally {
      cleaner.trace(cleaner.detailedTraceLevel,Cleaner.CLEAN_IN,inClone,lsn,completed,obsolete,dirtied);
    }
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class FileProcessor_processLN {
//     protected void hook126() throws DatabaseException {
//       if (processedHere) {
//         _this.cleaner.trace(_this.cleaner.detailedTraceLevel,Cleaner.CLEAN_LN,ln,logLsn,completed,obsolete,false);
//       }
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
