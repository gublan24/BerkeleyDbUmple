/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Environment.ump"
public class Environment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Environment()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 9 "../../../Environment.ump"
   public long truncateDatabase(Transaction txn, String databaseName, boolean returnCount) throws DatabaseException{
    checkHandleIsValid();
	checkEnv();
	DatabaseUtil.checkForNullParam(databaseName, "databaseName");
	Locker locker = null;
	boolean operationOk = false;
	long count = 0;
	try {
	    locker = LockerFactory.getWritableLocker(this, txn, environmentImpl.isTransactional(), true, null);
	    count = environmentImpl.truncate(locker, databaseName, returnCount);
	    operationOk = true;
	} finally {
	    if (locker != null) {
		locker.operationEnd(operationOk);
	    }
	}
	return count;
  }

}