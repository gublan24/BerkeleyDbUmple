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

  // line 8 "../../../../Evictor.ump"
   protected void hook373(EnvironmentImpl envImpl) throws DatabaseException{
    detailedTraceLevel = Tracer.parseLevel(envImpl, EnvironmentParams.JE_LOGGING_LEVEL_EVICTOR);
	original(envImpl);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Evictor.ump"
  private Level detailedTraceLevel ;

  
}