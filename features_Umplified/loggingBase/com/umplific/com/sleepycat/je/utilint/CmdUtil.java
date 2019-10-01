/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;

// line 3 "../../../../CmdUtil.ump"
public class CmdUtil
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CmdUtil()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../CmdUtil.ump"
   protected static  void hook855(EnvironmentConfig config) throws DatabaseException{
    config.setConfigParam(EnvironmentParams.JE_LOGGING_LEVEL.getName(), "SEVERE");
	original(config);
  }

}