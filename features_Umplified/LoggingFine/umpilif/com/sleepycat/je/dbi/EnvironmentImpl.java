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
   protected void hook318() throws DatabaseException{
    Tracer.trace(Level.FINE, this, "Env " + envHome + " daemons shutdown");
	original();
  }

  // line 11 "../../../../EnvironmentImpl.ump"
   protected void hook319() throws DatabaseException{
    Tracer.trace(Level.FINE, this, "Close of environment " + envHome + " started");
	original();
  }

}