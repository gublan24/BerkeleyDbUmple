/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../LogBufferPool.ump"
public class LogBufferPool
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogBufferPool()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../../LogBufferPool.ump"
  public void loadStats(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    stats.setNCacheMiss(nCacheMiss);
	stats.setNNotResident(nNotResident);
	if (config.getClear()) {
	    nCacheMiss = 0;
	    nNotResident = 0;
	}
	this.hook484();
	long bufferBytes = 0;
	int nLogBuffers = 0;
	this.hook483(bufferBytes, nLogBuffers);
	stats.setNLogBuffers(nLogBuffers);
	stats.setBufferBytes(bufferBytes);
  }

  // line 27 "../../../../LogBufferPool.ump"
   protected void hook483(long bufferBytes, int nLogBuffers) throws DatabaseException{
    Iterator iter = bufferPool.iterator();
	while (iter.hasNext()) {
	    LogBuffer l = (LogBuffer) iter.next();
	    nLogBuffers++;
	    bufferBytes += l.getCapacity();
	}
  }

  // line 36 "../../../../LogBufferPool.ump"
   protected void hook484() throws DatabaseException{
    
  }

  // line 39 "../../../../LogBufferPool.ump"
   protected LogBuffer hook489(long lsn, LogBuffer foundBuffer) throws DatabaseException{
    nNotResident++;
	return original(lsn, foundBuffer);
  }

  // line 44 "../../../../LogBufferPool.ump"
   protected void hook496() throws DatabaseException{
    nCacheMiss++;
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../LogBufferPool.ump"
  private long nNotResident = 0 ;
// line 9 "../../../../LogBufferPool.ump"
  private long nCacheMiss = 0 ;

  
}