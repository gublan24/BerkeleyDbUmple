/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../Checkpointer.ump"
public class Checkpointer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Checkpointer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../Checkpointer.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("<Checkpointer name=\"").append(name).append("\"/>");
	return sb.toString();
  }


  /**
   * 
   * Return the number of retries when a deadlock exception occurs.
   */
  // line 20 "../../../../Checkpointer.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return envImpl.getConfigManager().getInt(EnvironmentParams.CHECKPOINTER_RETRY);
  }


  /**
   * 
   * Called whenever the DaemonThread wakes up from a sleep.
   */
  // line 27 "../../../../Checkpointer.ump"
   protected void onWakeup() throws DatabaseException{
    if (envImpl.isClosed()) {
	    return;
	}
	doCheckpoint(CheckpointConfig.DEFAULT, false, "daemon");
  }

  // line 34 "../../../../Checkpointer.ump"
   protected void hook538(EnvironmentImpl envImpl, long waitTime, String name) throws DatabaseException{
    super.init(0 + waitTime, name, envImpl);
	original(envImpl, waitTime, name);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../Checkpointer.ump"
  synchronized public void clearEnv () 
  {
    envImpl = null;
  }

  
}