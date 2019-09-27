// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/Evictor/com/sleepycat/je/cleaner/UtilizationProfile.java
package com.sleepycat.je.cleaner;
public class UtilizationProfile {
  protected void hook187(  CursorImpl cursor) throws DatabaseException {
    cursor.evict();
    original(cursor);
  }
  protected void hook188(  CursorImpl cursor) throws DatabaseException {
    cursor.evict();
    original(cursor);
  }
  protected void hook189(  CursorImpl cursor) throws DatabaseException {
    cursor.setAllowEviction(false);
    original(cursor);
  }
  protected void hook190(  CursorImpl cursor) throws DatabaseException {
    cursor.evict();
    original(cursor);
  }
// START_OF_INNER_ELEMENT 
// @MethodObject static class UtilizationProfile_populateCache {
//     protected void hook191() throws DatabaseException {
//       cursor.evict();
//       original();
//     }
//   }
// END_OF_INNER_ELEMENT 
}
