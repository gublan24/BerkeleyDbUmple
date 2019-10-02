/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;

// line 3 "../../../../EnvironmentParams.ump"
// line 3 "../../../../loggingConsoleHandler_EnvironmentParams.ump"
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
  public static final ConfigParam JE_LOGGING_LEVEL = new ConfigParam("java.util.logging.level", "FINEST", false,
	    "# Trace messages equal and above this level will be logged.\n"
		    + "# Value should be one of the predefined java.util.logging.Level values");// line 5 "../../../../loggingConsoleHandler_EnvironmentParams.ump"
  public static final BooleanConfigParam JE_LOGGING_CONSOLE = new BooleanConfigParam(
	    "java.util.logging.ConsoleHandler.on", false, false, "# Use ConsoleHandler in logging system.") ;

  
}