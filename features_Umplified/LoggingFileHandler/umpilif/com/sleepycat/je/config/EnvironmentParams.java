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
  public static final BooleanConfigParam JE_LOGGING_FILE = new BooleanConfigParam("java.util.logging.FileHandler.on",
	    true, false, "# Use FileHandler in logging system.") ;
// line 8 "../../../../EnvironmentParams.ump"
  public static final IntConfigParam JE_LOGGING_FILE_LIMIT = new IntConfigParam("java.util.logging.FileHandler.limit",
	    new Integer(1000), new Integer(1000000000), new Integer(10000000), false,
	    "# Log file limit for FileHandler.") ;
// line 12 "../../../../EnvironmentParams.ump"
  public static final IntConfigParam JE_LOGGING_FILE_COUNT = new IntConfigParam("java.util.logging.FileHandler.count",
	    new Integer(1), null, new Integer(10), false, "# Log file count for FileHandler.") ;

  
}