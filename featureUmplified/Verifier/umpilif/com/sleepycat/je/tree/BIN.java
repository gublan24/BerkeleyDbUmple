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


  /**
   * 
   * For each cursor in this BIN's cursor set, ensure that the cursor is actually referring to this BIN.
   */
  // line 9 "../../../../BIN.ump"
   public void verifyCursors(){
    if (cursorSet != null) {
	    Iterator iter = cursorSet.iterator();
	    while (iter.hasNext()) {
		CursorImpl cursor = (CursorImpl) iter.next();
		if (getCursorBINToBeRemoved(cursor) != this) {
		    BIN cBin = getCursorBIN(cursor);
		    assert cBin == this;
		}
	    }
	}
  }

}