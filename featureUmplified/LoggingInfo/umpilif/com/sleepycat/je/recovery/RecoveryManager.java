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
   protected void hook573() throws DatabaseException,IOException{
    Tracer.trace(Level.INFO, env, "There are " + preparedTxns.size() + " prepared but unfinished txns.");
	original();
  }

  // line 11 "../../../../RecoveryManager.ump"
   protected void hook574(LNFileReader reader) throws IOException,DatabaseException,Exception{
    Tracer.trace(Level.INFO, env, "Found unfinished prepare record: id: " + reader.getTxnPrepareId() + " Xid: "
		+ reader.getTxnPrepareXid());
	original(reader);
  }

}