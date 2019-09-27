/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;

// line 3 "../../../../DbVerify.ump"
public class DbVerify
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbVerify()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../DbVerify.ump"
   protected void hook851(EnvironmentImpl envImpl) throws DatabaseException{
    Tracer.trace(Level.INFO, envImpl, "DbVerify.verify of " + dbName + " starting");
	original(envImpl);
  }

  // line 11 "../../../../DbVerify.ump"
   protected void hook852(EnvironmentImpl envImpl) throws DatabaseException{
    Tracer.trace(Level.INFO, envImpl, "DbVerify.verify of " + dbName + " ending");
	original(envImpl);
  }

}