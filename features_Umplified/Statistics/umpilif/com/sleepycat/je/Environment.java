/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Environment.ump"
public class Environment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Environment()
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
  // line 9 "../../../Environment.ump"
   public EnvironmentStats getStats(StatsConfig config) throws DatabaseException{
    StatsConfig useConfig = (config == null) ? StatsConfig.DEFAULT : config;
	if (environmentImpl != null) {
	    return environmentImpl.loadStats(useConfig);
	} else {
	    return new EnvironmentStats();
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 21 "../../../Environment.ump"
   public LockStats getLockStats(StatsConfig config) throws DatabaseException{
    checkHandleIsValid();
	checkEnv();
	StatsConfig useConfig = (config == null) ? StatsConfig.DEFAULT : config;
	return environmentImpl.lockStat(useConfig);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 31 "../../../Environment.ump"
   public TransactionStats getTransactionStats(StatsConfig config) throws DatabaseException{
    checkHandleIsValid();
	checkEnv();
	StatsConfig useConfig = (config == null) ? StatsConfig.DEFAULT : config;
	return environmentImpl.txnStat(useConfig);
  }

}