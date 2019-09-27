/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;

// line 3 "../../../../Evictor.ump"
public class Evictor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Evictor doesn't have a work queue so just throw an exception if it's ever called.
   */
  // line 10 "../../../../Evictor.ump"
   public void addToQueue(Object o) throws DatabaseException{
    throw new DatabaseException("Evictor.addToQueue should never be called.");
  }


  /**
   * 
   * Return the number of retries when a deadlock exception occurs.
   */
  // line 17 "../../../../Evictor.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return envImpl.getConfigManager().getInt(EnvironmentParams.EVICTOR_RETRY);
  }


  /**
   * 
   * Called whenever the daemon thread wakes up from a sleep.
   */
  // line 24 "../../../../Evictor.ump"
   public void onWakeup() throws DatabaseException{
    if (envImpl.isClosed()) {
	    return;
	}
	doEvict(SOURCE_DAEMON, false);
  }

}