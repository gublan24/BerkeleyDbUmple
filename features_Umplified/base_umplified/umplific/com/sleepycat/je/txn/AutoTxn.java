/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Database;

// line 3 "../../../../AutoTxn.ump"
public class AutoTxn extends Txn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AutoTxn()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 12 "../../../../AutoTxn.ump"
   public  AutoTxn(EnvironmentImpl env, TransactionConfig config) throws DatabaseException{
    super(env, config);
  }


  /**
   * 
   * AutoTxns abort or commit at the end of the operation
   */
  // line 19 "../../../../AutoTxn.ump"
   public void operationEnd(boolean operationOK) throws DatabaseException{
    if (operationOK) {
	    commit();
	} else {
	    abort(false);
	}
  }


  /**
   * 
   * AutoTxns abort or commit at the end of the operation
   */
  // line 30 "../../../../AutoTxn.ump"
   public void operationEnd() throws DatabaseException{
    operationEnd(true);
  }


  /**
   * 
   * Transfer any handle locks to the db handle on success. On failure, leave it with this txn, the handle lock will be released at abort and the handle marked invalid.
   */
  // line 38 "../../../../AutoTxn.ump"
   public void setHandleLockOwner(boolean operationOK, Database dbHandle, boolean dbIsClosing) throws DatabaseException{
    if (operationOK) {
	    if (!dbIsClosing) {
		transferHandleLockToHandle(dbHandle);
	    }
	    unregisterHandle(dbHandle);
	}
  }

}