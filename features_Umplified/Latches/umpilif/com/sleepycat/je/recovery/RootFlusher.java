/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RootFlusher.ump"
public class RootFlusher
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RootFlusher()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../RootFlusher.ump"
   protected void hook599(ChildReference root, IN rootIN) throws DatabaseException{
    rootIN.latch(false);
	try {
	    original(root, rootIN);
	} finally {
	    rootIN.releaseLatch();
	}
  }

}