/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RecoveryManager.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../RecoveryManager.ump"
   protected void hook575(IOException e) throws DatabaseException{
    Tracer.trace(env, "RecoveryManager", "recover", "Couldn't recover", e);
	original(e);
  }

  // line 11 "../../../../RecoveryManager.ump"
   protected void hook576(DatabaseImpl db, long logLsn, Exception e, String trace) throws DatabaseException{
    Tracer.trace(db.getDbEnvironment(), "RecoveryManager", "replaceOrInsert",
		" lsnFromLog:" + DbLsn.getNoFormatString(logLsn) + " " + trace, e);
	original(db, logLsn, e, trace);
  }

  // line 17 "../../../../RecoveryManager.ump"
   protected void hook577(String method, Exception origException, String badLsnString) throws DatabaseException{
    Tracer.trace(env, "RecoveryManager", method, "last LSN = " + badLsnString, origException);
	original(method, origException, badLsnString);
  }

}