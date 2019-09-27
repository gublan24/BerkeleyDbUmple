/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../DbTree.ump"
public class DbTree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbTree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Remove the database by deleting the nameLN.
   */
  // line 9 "../../../../DbTree.ump"
  public void dbRemove(Locker locker, String databaseName) throws DatabaseException{
    CursorImpl nameCursor = null;
	try {
	    NameLockResult result = lockNameLN(locker, databaseName, "remove");
	    nameCursor = result.nameCursor;
	    if (nameCursor == null) {
		return;
	    } else {
		nameCursor.delete();
		locker.markDeleteAtTxnEnd(result.dbImpl, true);
	    }
	} finally {
	    if (nameCursor != null) {
		this.hook293(nameCursor);
		nameCursor.close();
	    }
	}
  }

  // line 28 "../../../../DbTree.ump"
   protected void hook293(CursorImpl nameCursor) throws DatabaseException{
    
  }

}