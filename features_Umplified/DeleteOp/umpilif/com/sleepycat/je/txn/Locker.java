/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Locker.ump"
public abstract class Locker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Locker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Database operations like remove and truncate leave behind residual DatabaseImpls that must be purged at transaction commit or abort.
   */
   public abstract void markDeleteAtTxnEnd(DatabaseImpl db, boolean deleteAtCommit) throws DatabaseException;

}