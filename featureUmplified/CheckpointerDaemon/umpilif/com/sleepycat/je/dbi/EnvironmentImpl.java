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

  // line 6 "../../../../EnvironmentImpl.ump"
   protected void hook326(DbConfigManager mgr) throws DatabaseException{
    checkpointer.runOrPause(mgr.getBoolean(EnvironmentParams.ENV_RUN_CHECKPOINTER));
	original(mgr);
  }

  // line 11 "../../../../EnvironmentImpl.ump"
   protected void hook327(){
    if (checkpointer != null) {
	    checkpointer.requestShutdown();
	}
	original();
  }

  // line 18 "../../../../EnvironmentImpl.ump"
   protected void hook328() throws InterruptedException{
    checkpointer.shutdown();
	checkpointer.clearEnv();
	original();
  }

}