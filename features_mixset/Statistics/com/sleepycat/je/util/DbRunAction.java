// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/Statistics/com/sleepycat/je/util/DbRunAction.java
package com.sleepycat.je.util;
import com.sleepycat.je.StatsConfig;
public class DbRunAction {
  private static final int DBSTATS=6;
// START_OF_INNER_ELEMENT 
// @MethodObject static class DbRunAction_main {
//     protected void hook838() throws Exception {
//       if (doAction == DBSTATS) {
//         dbConfig=new DatabaseConfig();
//         dbConfig.setReadOnly(true);
//         DbInternal.setUseExistingConfig(dbConfig,true);
//         db=env.openDatabase(null,dbName,dbConfig);
//         try {
//           System.out.println(db.getStats(new StatsConfig()));
//         }
//   finally {
//           db.close();
//         }
//       }
//       original();
//     }
//     protected void hook839() throws Exception {
//       if (action.equalsIgnoreCase("dbstats")) {
//         doAction=DBSTATS;
//       }
//  else {
//         original();
//       }
//     }
//   }
// END_OF_INNER_ELEMENT 
}