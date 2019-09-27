/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Environment.ump"
public class Environment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Environment()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../Environment.ump"
   protected void hook58(String databaseName, DatabaseConfig dbConfig) throws DatabaseException{
    Tracer.trace(Level.FINEST, environmentImpl,
		"Environment.open: " + " name=" + databaseName + " dbConfig=" + dbConfig);
	original(databaseName, dbConfig);
  }

}