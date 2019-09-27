/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../CursorImpl.ump"
public class CursorImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CursorImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Evict the LN node at the cursor position. This is used for internal databases only.
   */
  // line 9 "../../../../CursorImpl.ump"
   public void evict() throws DatabaseException{
    try {
	    original();
	} finally {
	    releaseBINs();
	}
  }

  // line 17 "../../../../CursorImpl.ump"
   protected void hook202() throws DatabaseException{
    latchBINs();
	original();
  }

}