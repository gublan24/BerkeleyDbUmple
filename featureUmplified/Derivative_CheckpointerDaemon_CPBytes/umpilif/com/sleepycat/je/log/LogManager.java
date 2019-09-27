/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../LogManager.ump"
public class LogManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../LogManager.ump"
   protected void hook498(EnvironmentImpl envImpl) throws DatabaseException{
    checkpointMonitor = new CheckpointMonitor(envImpl);
	original(envImpl);
  }

  // line 13 "../../../../LogManager.ump"
   protected void hook499(LogResult logResult) throws DatabaseException{
    if (logResult.wakeupCheckpointer) {
	    checkpointMonitor.activate();
	}
	original(logResult);
  }

  // line 21 "../../../../LogManager.ump"
   protected boolean hook500(LoggableObject item, int entrySize, boolean wakeupCheckpointer) throws IOException,DatabaseException{
    wakeupCheckpointer = checkpointMonitor.recordLogWrite(entrySize, item);
	return original(item, entrySize, wakeupCheckpointer);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../LogManager.ump"
  private CheckpointMonitor checkpointMonitor ;

  
}