/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Sequence.ump"
public class Sequence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Sequence()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 13 "../../../Sequence.ump"
   public SequenceStats getStats(StatsConfig config) throws DatabaseException{
    if (config == null) {
	    config = StatsConfig.DEFAULT;
	}
	if (!config.getFast()) {
	    Cursor cursor = db.openCursor(null, null);
	    try {
		readDataRequired(cursor, LockMode.READ_UNCOMMITTED);
	    } finally {
		cursor.close();
	    }
	}
	SequenceStats stats = new SequenceStats(nGets, nCachedGets, storedValue, cacheValue, cacheLast, rangeMin,
		rangeMax, cacheSize);
	if (config.getClear()) {
	    nGets = 0;
	    nCachedGets = 0;
	}
	return stats;
  }

  // line 34 "../../../Sequence.ump"
   protected void hook83(boolean cached) throws DatabaseException{
    nGets += 1;
	if (cached) {
	    nCachedGets += 1;
	}
	original(cached);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../Sequence.ump"
  private int nGets ;
// line 7 "../../../Sequence.ump"
  private int nCachedGets ;

  
}