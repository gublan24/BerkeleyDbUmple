/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../BIN.ump"
public class BIN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BIN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../BIN.ump"
   protected void hook601(long removed) throws DatabaseException{
    updateMemorySize(removed, 0);
	original(removed);
  }

  // line 11 "../../../../BIN.ump"
   protected void hook602(long removed) throws DatabaseException{
    updateMemorySize(removed, 0);
	original(removed);
  }

}