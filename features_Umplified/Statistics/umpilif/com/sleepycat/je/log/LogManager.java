/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../LogManager.ump"
// line 3 "../../../../LogManager_inner.ump"
public class LogManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../../LogManager.ump"
   public void loadStats(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    stats.setNRepeatFaultReads(nRepeatFaultReads);
	stats.setNTempBufferWrites(nTempBufferWrites);
	if (config.getClear()) {
	    nRepeatFaultReads = 0;
	    nTempBufferWrites = 0;
	}
	logBufferPool.loadStats(config, stats);
	this.hook497(config, stats);
  }

  // line 23 "../../../../LogManager.ump"
   protected void hook497(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    
  }

  // line 26 "../../../../LogManager.ump"
   protected void hook509() throws IOException,DatabaseException,Exception{
    nTempBufferWrites++;
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.log;
  
  @MethodObject
  // line 4 "../../../../LogManager_inner.ump"
  public static class LogManager_getLogEntryFromLogSource
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public LogManager_getLogEntryFromLogSource()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../LogManager_inner.ump"
     protected void hook508() throws DatabaseException,ClosedChannelException,Exception{
      _this.nRepeatFaultReads++;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../LogManager.ump"
  private int nRepeatFaultReads ;
// line 9 "../../../../LogManager.ump"
  private long nTempBufferWrites ;

  
}