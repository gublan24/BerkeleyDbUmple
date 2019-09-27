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
   protected void hook800(Throwable t) throws DatabaseException,Throwable{
    Tracer.trace(envImpl, "Txn", "commit", "Commit of transaction " + id + " failed", t);
	original(t);
  }

  // line 11 "../../../../Txn.ump"
   protected void hook801(Long nodeId, long undoLsn, DatabaseException e) throws DatabaseException{
    Tracer.trace(envImpl, "Txn", "undo", "for node=" + nodeId + " LSN=" + DbLsn.getNoFormatString(undoLsn), e);
	original(nodeId, undoLsn, e);
  }

}