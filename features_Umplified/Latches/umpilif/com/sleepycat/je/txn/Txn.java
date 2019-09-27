/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Txn.ump"
public class Txn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Txn()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../Txn.ump"
   protected void hook802(long undoLsn, TreeLocation location, LNLogEntry undoEntry, LN undoLN, DatabaseImpl db, long abortLsn, boolean abortKnownDeleted) throws DatabaseException,RuntimeException{
    try {
	    original(undoLsn, location, undoEntry, undoLN, db, abortLsn, abortKnownDeleted);
	} finally {
	    if (location.bin != null) {
		location.bin.releaseLatchIfOwner();
	    }
	}
  }

}