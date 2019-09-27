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
  public static final LongConfigParam CHECKPOINTER_BYTES_INTERVAL = new LongConfigParam(
	    "je.checkpointer.bytesInterval", new Long(0), new Long(Long.MAX_VALUE), new Long(20000000), false,
	    "# Ask the checkpointer to run every time we write this many bytes\n"
		    + "# to the log. If set, supercedes je.checkpointer.wakeupInterval. To\n"
		    + "# use time based checkpointing, set this to 0.");
  
}