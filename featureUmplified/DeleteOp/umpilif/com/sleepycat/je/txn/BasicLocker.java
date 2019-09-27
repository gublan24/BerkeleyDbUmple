/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../BasicLocker.ump"
public class BasicLocker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BasicLocker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../BasicLocker.ump"
   public void markDeleteAtTxnEnd(DatabaseImpl db, boolean deleteAtCommit) throws DatabaseException{
    if (deleteAtCommit) {
	    db.deleteAndReleaseINs();
	}
  }

}