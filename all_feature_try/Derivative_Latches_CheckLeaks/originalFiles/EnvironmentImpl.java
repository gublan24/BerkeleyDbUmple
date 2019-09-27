// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/all_feature_try/Derivative_Latches_CheckLeaks/originalFiles/EnvironmentImpl.java
package com.sleepycat.je.dbi;
public class EnvironmentImpl {
  protected void hook311() throws DatabaseException {
    LatchSupport.clearNotes();
    original();
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class EnvironmentImpl_checkLeaks {
//     protected void hook312() throws DatabaseException {
//       if (LatchSupport.countLatchesHeld() > 0) {
//         clean=false;
//         System.out.println("Some latches held at env close.");
//         LatchSupport.dumpLatchesHeld();
//       }
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
