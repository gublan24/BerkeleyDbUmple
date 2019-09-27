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
  
  // line 5 ../../../../EnvironmentParams.ump
  public static final BooleanConfigParam ENV_FAIR_LATCHES = new BooleanConfigParam("je.env.fairLatches", false, false,
	    "# If true, use latches instead of synchronized blocks to\n"
		    + "# implement the lock table and log write mutexes. Latches require\n"
		    + "# that threads queue to obtain the mutex in question and\n"
		    + "# therefore guarantee that there will be no mutex starvation, but \n"
		    + "# do incur a performance penalty. Latches should not be necessary in\n"
		    + "# most cases, so synchronized blocks are the default. An application\n"
		    + "# that puts heavy load on JE with threads with different thread\n"
		    + "# priorities might find it useful to use latches.  In a Java 5 JVM,\n"
		    + "# where java.util.concurrent.locks.ReentrantLock is used for the\n"
		    + "# latch implementation, this parameter will determine whether they\n"
		    + "# are 'fair' or not.  This parameter is 'static' across all\n" + "# environments.\n");
  
}