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

  // line 7 "../../../../DbTree.ump"
   protected void hook296(Locker locker, NameLockResult result, DatabaseImpl newDb) throws DatabaseException,CloneNotSupportedException{
    locker.markDeleteAtTxnEnd(result.dbImpl, true);
	locker.markDeleteAtTxnEnd(newDb, false);
	original(locker, result, newDb);
  }

  // line 14 "../../../../DbTree.ump"
   protected void hook297(Locker locker, DatabaseImpl oldDatabase) throws DatabaseException,CloneNotSupportedException,UnsupportedEncodingException{
    locker.markDeleteAtTxnEnd(oldDatabase, true);
	original(locker, oldDatabase);
  }

}