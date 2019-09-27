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
   * Returns the current memory usage in bytes for all btrees in the environmentImpl.
   */
  // line 9 "../../../Environment.ump"
  public long getMemoryUsage() throws DatabaseException{
    checkHandleIsValid();
	checkEnv();
	return environmentImpl.getMemoryBudget().getCacheMemoryUsage();
  }

}