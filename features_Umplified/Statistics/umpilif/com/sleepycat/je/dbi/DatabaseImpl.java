/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.DatabaseStats;
import com.sleepycat.je.BtreeStats;

// line 3 "../../../../DatabaseImpl.ump"
// line 3 "../../../../DatabaseImpl_inner.ump"
public class DatabaseImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 11 "../../../../DatabaseImpl.ump"
   public DatabaseStats stat(StatsConfig config) throws DatabaseException{
    if (stats == null) {
	    stats = new BtreeStats();
	}
	if (!config.getFast()) {
	    if (tree == null) {
		return new BtreeStats();
	    }
	    PrintStream out = config.getShowProgressStream();
	    if (out == null) {
		out = System.err;
	    }
	    StatsAccumulator statsAcc = new StatsAccumulator(out, config.getShowProgressInterval(), getEmptyStats());
	    walkDatabaseTree(statsAcc, out, true);
	    statsAcc.copyToStats(stats);
	}
	return stats;
  }

  // line 30 "../../../../DatabaseImpl.ump"
   public DatabaseStats getEmptyStats(){
    return new BtreeStats();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../DatabaseImpl_inner.ump"
  public static class DatabaseImpl_preload
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DatabaseImpl_preload()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DatabaseImpl_inner.ump"
     protected void hook287() throws DatabaseException{
      try {
            original();
          }
     catch (      HaltPreloadException HPE) {
            ret.status=HPE.getStatus();
          }
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../DatabaseImpl.ump"
  private BtreeStats stats ;

  
}