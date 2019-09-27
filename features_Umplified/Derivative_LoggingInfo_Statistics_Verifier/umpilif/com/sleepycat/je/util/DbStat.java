/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;

// line 3 "../../../../DbStat.ump"
public class DbStat
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbStat()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../DbStat.ump"
   protected void hook849() throws DatabaseException{
    Tracer.trace(Level.INFO, DbInternal.envGetEnvironmentImpl(env), "DbStat.stats of " + dbName + " ending");
	original();
  }

  // line 11 "../../../../DbStat.ump"
   protected void hook850() throws DatabaseException{
    Tracer.trace(Level.INFO, DbInternal.envGetEnvironmentImpl(env), "DbStat.stats of " + dbName + " starting");
	original();
  }

}