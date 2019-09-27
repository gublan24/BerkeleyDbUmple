/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../Cleaner.ump"
public class Cleaner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../Cleaner.ump"
   protected void hook88(long fileNumValue) throws DatabaseException{
    Tracer.trace(Level.SEVERE, env, "Cleaner deleted file 0x" + Long.toHexString(fileNumValue));
	original(fileNumValue);
  }

  // line 11 "../../../../Cleaner.ump"
   private void traceFileNotDeleted(Exception e, long fileNum){
    Tracer.trace(env, "Cleaner", "deleteSafeToDeleteFiles",
		"Log file 0x" + Long.toHexString(fileNum) + " could not be " + (expunge ? "deleted" : "renamed")
			+ ".  This operation will be retried at the next checkpoint.",
		e);
	original(e, fileNum);
  }

  // line 19 "../../../../Cleaner.ump"
   protected void hook89(DatabaseException DBE) throws DatabaseException{
    Tracer.trace(env, "com.sleepycat.je.cleaner.Cleaner", "processLN", "Exception thrown: ", DBE);
	original(DBE);
  }

}