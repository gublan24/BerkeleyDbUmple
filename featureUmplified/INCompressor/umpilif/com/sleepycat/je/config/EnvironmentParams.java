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
  public static final LongConfigParam COMPRESSOR_WAKEUP_INTERVAL = new LongConfigParam("je.compressor.wakeupInterval",
	    new Long(1000000), new Long(4294967296L), new Long(5000000), false,
	    "# The compressor wakeup interval in microseconds.") ;

  
}