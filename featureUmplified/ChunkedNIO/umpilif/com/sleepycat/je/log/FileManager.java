/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileManager.ump"
public class FileManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../FileManager.ump"
   protected void hook456(DbConfigManager configManager) throws DatabaseException{
    chunkedNIOSize = configManager.getLong(EnvironmentParams.LOG_CHUNKED_NIO);
	original(configManager);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../FileManager.ump"
  private long chunkedNIOSize = 0 ;

  
}