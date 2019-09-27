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
   * Get lock count, for per transaction lock stats, for internal debugging.
   */
   public abstract LockStats collectStats(LockStats stats) throws DatabaseException;

}