// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/CheckLeaks/com/sleepycat/je/dbi/EnvironmentImpl.java
// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/CheckLeaks/com/sleepycat/je/dbi/EnvironmentImpl.java
package com.sleepycat.je.dbi;
public class EnvironmentImpl {
  /** 
 * Debugging support. Check for leaked locks and transactions.
 */
  private void checkLeaks() throws DatabaseException {
    new EnvironmentImpl_checkLeaks(this).execute();
  }
  protected void hook311() throws DatabaseException {
  }
  protected void hook325(  StringBuffer errors) throws DatabaseException {
    try {
      checkLeaks();
      this.hook311();
    }
 catch (    DatabaseException DBE) {
      errors.append("\nException performing validity checks: ");
      errors.append(DBE.toString()).append("\n");
    }
    original(errors);
  }
// START_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// // @MethodObject static class EnvironmentImpl_checkLeaks {
// //     EnvironmentImpl_checkLeaks(    EnvironmentImpl _this){
// //       this._this=_this;
// //     }
// //     void execute() throws DatabaseException {
// //       if (!_this.configManager.getBoolean(EnvironmentParams.ENV_CHECK_LEAKS)) {
// //         return;
// //       }
// //       clean=true;
// //       this.hook313();
// //       this.hook312();
// //       assert clean : "Lock, transaction, or latch left behind at environment close";
// //     }
// //     protected EnvironmentImpl _this;
// //     protected boolean clean;
// //     protected StatsConfig statsConfig;
// //     protected LockStats lockStat;
// //     protected TransactionStats txnStat;
// //     protected TransactionStats.Active[] active;
// //     protected void hook312() throws DatabaseException {
// //     }
// //     protected void hook313() throws DatabaseException {
// //     }
// //   }
// END_OF_INNER_ELEMENT 
// END_OF_INNER_ELEMENT 
}
