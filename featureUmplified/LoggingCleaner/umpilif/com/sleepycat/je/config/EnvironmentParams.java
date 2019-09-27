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
  public static final ConfigParam JE_LOGGING_LEVEL_CLEANER = new ConfigParam("java.util.logging.level.cleaner",
	    "FINE", true, "# Cleaner specific detailed trace messages will be issued at this\n"
		    + "# level. The Value should be one of the predefined \n" + "# java.util.logging.Level values");
  
}