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
   protected void hook596() throws IOException,DatabaseException{
    env.invokeEvictor();
	original();
  }

  // line 11 "../../../../RecoveryManager.ump"
   protected void hook597() throws IOException,DatabaseException,Exception{
    env.invokeEvictor();
	original();
  }

  // line 16 "../../../../RecoveryManager.ump"
   protected void hook598() throws IOException,DatabaseException,Exception{
    env.invokeEvictor();
	original();
  }

}