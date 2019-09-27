/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../BINDelta.ump"
public class BINDelta
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BINDelta()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../BINDelta.ump"
   protected void hook611(BIN fullBIN) throws DatabaseException{
    fullBIN.releaseLatch();
	original(fullBIN);
  }

  // line 11 "../../../../BINDelta.ump"
   protected void hook612(BIN fullBIN) throws DatabaseException{
    fullBIN.latch();
	original(fullBIN);
  }

}