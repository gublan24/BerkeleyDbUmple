// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Statistics/com/sleepycat/je/dbi/DatabaseImpl.java
// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Statistics/com/sleepycat/je/dbi/DatabaseImpl.java
package com.sleepycat.je.dbi;
import com.sleepycat.je.BtreeStats;
import com.sleepycat.je.DatabaseStats;
import com.sleepycat.je.StatsConfig;
public class DatabaseImpl {
  private BtreeStats stats;
  public DatabaseStats stat(  StatsConfig config) throws DatabaseException {
    if (stats == null) {
      stats=new BtreeStats();
    }
    if (!config.getFast()) {
      if (tree == null) {
        return new BtreeStats();
      }
      PrintStream out=config.getShowProgressStream();
      if (out == null) {
        out=System.err;
      }
      StatsAccumulator statsAcc=new StatsAccumulator(out,config.getShowProgressInterval(),getEmptyStats());
      walkDatabaseTree(statsAcc,out,true);
      statsAcc.copyToStats(stats);
    }
    return stats;
  }
  public DatabaseStats getEmptyStats(){
    return new BtreeStats();
  }
// START_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// // @MethodObject static class DatabaseImpl_preload {
// //     protected void hook287() throws DatabaseException {
// //       try {
// //         original();
// //       }
// //  catch (      HaltPreloadException HPE) {
// //         ret.status=HPE.getStatus();
// //       }
// //     }
// //   }
// END_OF_INNER_ELEMENT 
// END_OF_INNER_ELEMENT 
}
