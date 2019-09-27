/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../ReadCommittedLocker.ump"
public class ReadCommittedLocker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ReadCommittedLocker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Forwards this method to the transactional buddy.  The buddy handles write locks and therefore handles delete information.
   */
  // line 9 "../../../../ReadCommittedLocker.ump"
   public void addDeleteInfo(BIN bin, Key deletedKey) throws DatabaseException{
    getBuddy().addDeleteInfo(bin, deletedKey);
  }

}