/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../EnvironmentImpl.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Add the database log as one of the debug logging destinations when the logging system is sufficiently initialized.
   */
  // line 9 "../../../../EnvironmentImpl.ump"
   public void enableDebugLoggingToDbLog() throws DatabaseException{
    if (configManager.getBoolean(EnvironmentParams.JE_LOGGING_DBLOG)) {
	    Handler dbLogHandler = new TraceLogHandler(this);
	    Level level = Level.parse(configManager.get(EnvironmentParams.JE_LOGGING_LEVEL));
	    dbLogHandler.setLevel(level);
	    envLogger.addHandler(dbLogHandler);
	}
  }

}