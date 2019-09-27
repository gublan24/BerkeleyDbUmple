/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;

// line 3 "../../../../EnvironmentParams.ump"
public class EnvironmentParams
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentParams()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../EnvironmentParams.ump"
  public static final IntConfigParam CHECKPOINTER_RETRY = new IntConfigParam("je.checkpointer.deadlockRetry",
	    new Integer(0), new Integer(Integer.MAX_VALUE), new Integer(3), false,
	    "# The number of times to retry a checkpoint if it runs into a deadlock.") ;

  
}