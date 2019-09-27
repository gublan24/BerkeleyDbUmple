/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../SecondaryCursor.ump"
public class SecondaryCursor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SecondaryCursor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../SecondaryCursor.ump"
   protected void hook65() throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.delete: ", null);
	original();
  }

  // line 11 "../../../SecondaryCursor.ump"
   protected void hook66(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getCurrent: ", lockMode);
	original(lockMode);
  }

  // line 16 "../../../SecondaryCursor.ump"
   protected void hook67(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getFirst: ", lockMode);
	original(lockMode);
  }

  // line 21 "../../../SecondaryCursor.ump"
   protected void hook68(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getLast: ", lockMode);
	original(lockMode);
  }

  // line 26 "../../../SecondaryCursor.ump"
   protected void hook69(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getNext: ", lockMode);
	original(lockMode);
  }

  // line 31 "../../../SecondaryCursor.ump"
   protected void hook70(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getNextDup: ", lockMode);
	original(lockMode);
  }

  // line 36 "../../../SecondaryCursor.ump"
   protected void hook71(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getNextNoDup: ", null, null, lockMode);
	original(lockMode);
  }

  // line 41 "../../../SecondaryCursor.ump"
   protected void hook72(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getPrev: ", lockMode);
	original(lockMode);
  }

  // line 46 "../../../SecondaryCursor.ump"
   protected void hook73(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getPrevDup: ", lockMode);
	original(lockMode);
  }

  // line 51 "../../../SecondaryCursor.ump"
   protected void hook74(LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getPrevNoDup: ", lockMode);
	original(lockMode);
  }

  // line 56 "../../../SecondaryCursor.ump"
   protected void hook75(DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getSearchKey: ", key, null, lockMode);
	original(key, lockMode);
  }

  // line 61 "../../../SecondaryCursor.ump"
   protected void hook76(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getSearchKeyRange: ", key, data, lockMode);
	original(key, data, lockMode);
  }

  // line 66 "../../../SecondaryCursor.ump"
   protected void hook77(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getSearchBoth: ", key, data, lockMode);
	original(key, data, lockMode);
  }

  // line 71 "../../../SecondaryCursor.ump"
   protected void hook78(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    trace(Level.FINEST, "SecondaryCursor.getSearchBothRange: ", key, data, lockMode);
	original(key, data, lockMode);
  }

}