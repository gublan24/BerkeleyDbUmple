/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;

// line 3 "../../../../DbLoad.ump"
public class DbLoad
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbLoad()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../DbLoad.ump"
   public boolean load() throws IOException,DatabaseException{
    Tracer.trace(Level.INFO, DbInternal.envGetEnvironmentImpl(env), "DbLoad.load of " + dbName + " starting");
	return original();
  }

  // line 11 "../../../../DbLoad.ump"
   protected void hook835() throws IOException,DatabaseException{
    Tracer.trace(Level.INFO, DbInternal.envGetEnvironmentImpl(env), "DbLoad.load of " + dbName + " ending.");
	original();
  }

}