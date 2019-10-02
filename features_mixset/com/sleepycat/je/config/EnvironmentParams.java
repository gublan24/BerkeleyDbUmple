/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;

// line 3 "../../../../EnvironmentParams.ump"
// line 3 "../../../../loggingConsoleHandler_EnvironmentParams.ump"
// line 3 "../../../../LoggingDbLogHandler_EnvironmentParams.ump"
// line 3 "../../../../LoggingFileHandler_EnvironmentParams.ump"
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
// line 5 "../../../../LoggingDbLogHandler_EnvironmentParams.ump"
  public static final BooleanConfigParam JE_LOGGING_DBLOG = new BooleanConfigParam(
	    "java.util.logging.DbLogHandler.on", true, false, "# Use DbLogHandler in logging system.") ;
// line 5 "../../../../LoggingFileHandler_EnvironmentParams.ump"
  public static final BooleanConfigParam JE_LOGGING_FILE = new BooleanConfigParam("java.util.logging.FileHandler.on",
	    true, false, "# Use FileHandler in logging system.") ;
// line 8 "../../../../LoggingFileHandler_EnvironmentParams.ump"
  public static final IntConfigParam JE_LOGGING_FILE_LIMIT = new IntConfigParam("java.util.logging.FileHandler.limit",
	    new Integer(1000), new Integer(1000000000), new Integer(10000000), false,
	    "# Log file limit for FileHandler.") ;
// line 12 "../../../../LoggingFileHandler_EnvironmentParams.ump"
  public static final IntConfigParam JE_LOGGING_FILE_COUNT = new IntConfigParam("java.util.logging.FileHandler.count",
	    new Integer(1), null, new Integer(10), false, "# Log file count for FileHandler.") ;

  
}