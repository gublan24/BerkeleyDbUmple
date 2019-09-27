// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Latches/originalFiles/Database.java
package com.sleepycat.je;
public class Database {
  /** 
 * Releases a lock acquired by calling acquireTriggerListReadLock().
 */
  private void releaseTriggerListReadLock() throws DatabaseException {
    EnvironmentImpl env=envHandle.getEnvironmentImpl();
    env.getTriggerLatch().release();
  }
  protected void hook53(  List list) throws DatabaseException {
    try {
      original(list);
    }
  finally {
      releaseTriggerListReadLock();
    }
  }
  protected void hook54(  Locker locker,  DatabaseEntry priKey,  DatabaseEntry oldData,  DatabaseEntry newData) throws DatabaseException {
    try {
      original(locker,priKey,oldData,newData);
    }
  finally {
      releaseTriggerListReadLock();
    }
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class Database_acquireTriggerListReadLock {
//     void execute() throws DatabaseException {
//       env=_this.envHandle.getEnvironmentImpl();
//       env.getTriggerLatch().acquireShared();
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// @MethodObject static class Database_releaseTriggerListWriteLock {
//     void execute() throws DatabaseException {
//       original();
//       env=_this.envHandle.getEnvironmentImpl();
//       env.getTriggerLatch().release();
//     }
//   }
// END_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// @MethodObject static class Database_acquireTriggerListWriteLock {
//     void execute() throws DatabaseException {
//       env=_this.envHandle.getEnvironmentImpl();
//       env.getTriggerLatch().acquireExclusive();
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}