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

  // line 6 "../../../../Txn.ump"
   protected void hook799(int numReadLocks, int numWriteLocks, boolean openCursors) throws DatabaseException{
    Tracer.trace(Level.FINE, envImpl, "Abort:id = " + id + " numWriteLocks= " + numWriteLocks + " numReadLocks= "
		+ numReadLocks + " openCursors= " + openCursors);
	original(numReadLocks, numWriteLocks, openCursors);
  }

}