/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../JoinCursor.ump"
public class JoinCursor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JoinCursor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../JoinCursor.ump"
   protected void hook62(LockMode lockMode) throws DatabaseException{
    priCursor.trace(Level.FINEST, "JoinCursor.getNext(key): ", lockMode);
	original(lockMode);
  }

  // line 11 "../../../JoinCursor.ump"
   protected void hook63(LockMode lockMode) throws DatabaseException{
    priCursor.trace(Level.FINEST, "JoinCursor.getNext(key,data): ", lockMode);
	original(lockMode);
  }

}