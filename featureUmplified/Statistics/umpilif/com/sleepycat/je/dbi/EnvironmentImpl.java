/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.TransactionStats;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../EnvironmentImpl.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 37 "../../../../EnvironmentImpl.ump"
   protected void hook314(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    
  }

  // line 40 "../../../../EnvironmentImpl.ump"
   protected void hook315(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    
  }

  // line 43 "../../../../EnvironmentImpl.ump"
   protected void hook316(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../EnvironmentImpl.ump"
  synchronized public EnvironmentStats loadStats (StatsConfig config) throws DatabaseException 
  {
    EnvironmentStats stats = new EnvironmentStats();
	this.hook314(config, stats);
	this.hook315(config, stats);
	checkpointer.loadStats(config, stats);
	cleaner.loadStats(config, stats);
	logManager.loadStats(config, stats);
	this.hook316(config, stats);
	return stats;
  }

// line 25 "../../../../EnvironmentImpl.ump"
  synchronized public LockStats lockStat (StatsConfig config) throws DatabaseException 
  {
    return txnManager.lockStat(config);
  }

// line 32 "../../../../EnvironmentImpl.ump"
  synchronized public TransactionStats txnStat (StatsConfig config) throws DatabaseException 
  {
    return txnManager.txnStat(config);
  }

  
}