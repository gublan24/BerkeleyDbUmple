/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../PreloadProcessor.ump"
public class PreloadProcessor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadProcessor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../PreloadProcessor.ump"
   protected void hook353(PreloadStats stats){
    this.stats = stats;
	original(stats);
  }

  // line 13 "../../../../PreloadProcessor.ump"
   protected void hook354(LogEntryType childType){
    if (childType.equals(LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL)
		|| childType.equals(LogEntryType.LOG_DUPCOUNTLN)) {
	    stats.nDupCountLNsLoaded++;
	} else if (childType.equals(LogEntryType.LOG_LN_TRANSACTIONAL) || childType.equals(LogEntryType.LOG_LN)) {
	    stats.nLNsLoaded++;
	} else if (childType.equals(LogEntryType.LOG_DBIN)) {
	    stats.nDBINsLoaded++;
	} else if (childType.equals(LogEntryType.LOG_BIN)) {
	    stats.nBINsLoaded++;
	} else if (childType.equals(LogEntryType.LOG_DIN)) {
	    stats.nDINsLoaded++;
	} else if (childType.equals(LogEntryType.LOG_IN)) {
	    stats.nINsLoaded++;
	}
	original(childType);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../PreloadProcessor.ump"
  private PreloadStats stats ;

  
}