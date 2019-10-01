/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Sequence.ump"
public class Sequence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Sequence()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../Sequence.ump"
   protected void hook84(Database db) throws DatabaseException{
    logger = db.getEnvironment().getEnvironmentImpl().getLogger();
	original(db);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../Sequence.ump"
  private Logger logger ;

  
}