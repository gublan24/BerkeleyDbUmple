// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/LoggingConfig/com/sleepycat/je/recovery/Checkpointer.java
// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/LoggingConfig/com/sleepycat/je/recovery/Checkpointer.java
package com.sleepycat.je.recovery;
public class Checkpointer {
  private void trace(  EnvironmentImpl envImpl,  String invokingSource,  boolean success){
    StringBuffer sb=new StringBuffer();
    sb.append("Checkpoint ").append(checkpointId);
    sb.append(": source=").append(invokingSource);
    sb.append(" success=").append(success);
    this.hook516(sb);
    Tracer.trace(Level.CONFIG,envImpl,sb.toString());
  }
  protected void hook516(  StringBuffer sb){
  }
// START_OF_INNER_ELEMENT 
// START_OF_INNER_ELEMENT 
// // @MethodObject static class Checkpointer_doCheckpoint {
// //     protected void hook522() throws DatabaseException {
// //       traced=false;
// //       original();
// //     }
// //     protected void hook523() throws DatabaseException {
// //       _this.trace(_this.envImpl,invokingSource,true);
// //       traced=true;
// //       original();
// //     }
// //     protected void hook524() throws DatabaseException {
// //       if (!traced) {
// //         _this.trace(_this.envImpl,invokingSource,success);
// //       }
// //       original();
// //     }
// //   }
// END_OF_INNER_ELEMENT 
// END_OF_INNER_ELEMENT 
}
