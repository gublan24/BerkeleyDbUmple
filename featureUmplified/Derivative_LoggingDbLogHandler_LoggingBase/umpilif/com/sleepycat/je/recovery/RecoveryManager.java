/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RecoveryManager.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../RecoveryManager.ump"
   protected void hook556() throws DatabaseException,IOException{
    env.enableDebugLoggingToDbLog();
	original();
  }


  /**
   * 
   * Find the end of the log, initialize the FileManager. While we're perusing the log, return the last checkpoint LSN if we happen to see it.
   */
  // line 14 "../../../../RecoveryManager.ump"
   private void findEndOfLog(boolean readOnly) throws IOException,DatabaseException{
    original(readOnly);
	env.enableDebugLoggingToDbLog();
  }

}