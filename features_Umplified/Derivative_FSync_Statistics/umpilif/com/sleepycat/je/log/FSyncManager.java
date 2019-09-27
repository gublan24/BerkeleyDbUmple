/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../FSyncManager.ump"
public class FSyncManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FSyncManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 14 "../../../../FSyncManager.ump"
  public long getNFSyncRequests(){
    return nFSyncRequests;
  }

  // line 18 "../../../../FSyncManager.ump"
  public long getNFSyncs(){
    return nFSyncs;
  }

  // line 22 "../../../../FSyncManager.ump"
  public long getNTimeouts(){
    return nTimeouts;
  }

  // line 26 "../../../../FSyncManager.ump"
  public void loadStats(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    stats.setNFSyncs(nFSyncs);
	stats.setNFSyncRequests(nFSyncRequests);
	stats.setNFSyncTimeouts(nTimeouts);
	if (config.getClear()) {
	    nFSyncs = 0;
	    nFSyncRequests = 0;
	    nTimeouts = 0;
	}
  }

  // line 37 "../../../../FSyncManager.ump"
   protected void hook435() throws DatabaseException{
    nFSyncRequests++;
	original();
  }

  // line 42 "../../../../FSyncManager.ump"
   protected void hook436() throws DatabaseException{
    synchronized (this) {
	    nTimeouts++;
	}
	original();
  }

  // line 49 "../../../../FSyncManager.ump"
   protected void hook437() throws DatabaseException{
    nFSyncs++;
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../FSyncManager.ump"
  private long nFSyncRequests = 0 ;
// line 9 "../../../../FSyncManager.ump"
  private long nFSyncs = 0 ;
// line 11 "../../../../FSyncManager.ump"
  private long nTimeouts = 0 ;

  
}