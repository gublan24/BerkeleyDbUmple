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
  public static final IntConfigParam CLEANER_LOOK_AHEAD_CACHE_SIZE = new IntConfigParam(
	    "je.cleaner.lookAheadCacheSize", new Integer(0), null, new Integer(8192), true,
	    "# The look ahead cache size for cleaning in bytes.  Increasing this\n"
		    + "# value can reduce the number of Btree lookups.");
  
}