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

  // line 6 "../../../../CursorImpl.ump"
   protected void hook203() throws DatabaseException{
    database.getDbEnvironment().getEvictor().doCriticalEviction();
	original();
  }


  /**
   * 
   * Reset a cursor to an uninitialized state, but unlike close(), allow it to be used further.
   */
  // line 14 "../../../../CursorImpl.ump"
   public void reset() throws DatabaseException{
    original();
	if (allowEviction) {
	    database.getDbEnvironment().getEvictor().doCriticalEviction();
	}
  }


  /**
   * 
   * Close a cursor.
   * @throws DatabaseExceptionif the cursor was previously closed.
   */
  // line 25 "../../../../CursorImpl.ump"
   public void close() throws DatabaseException{
    original();
	if (allowEviction) {
	    database.getDbEnvironment().getEvictor().doCriticalEviction();
	}
  }

}