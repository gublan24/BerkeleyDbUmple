/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;

// line 3 "../../../../DbDump.ump"
public class DbDump
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbDump()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../DbDump.ump"
   public void dump() throws IOException,DatabaseException{
    original();
	Tracer.trace(Level.INFO, DbInternal.envGetEnvironmentImpl(env), "DbDump.dump of " + dbName + " ending");
  }

  // line 11 "../../../../DbDump.ump"
   protected void hook834() throws IOException,DatabaseException{
    Tracer.trace(Level.INFO, DbInternal.envGetEnvironmentImpl(env), "DbDump.dump of " + dbName + " starting");
	original();
  }

}