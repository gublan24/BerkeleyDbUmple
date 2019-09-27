/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../BasicLocker.ump"
public class BasicLocker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BasicLocker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Release locks at the end of the transaction.
   */
  // line 9 "../../../../BasicLocker.ump"
   public void operationEnd(boolean operationOK) throws DatabaseException{
    original(operationOK);
	synchronized (this) {
	    if ((deleteInfo != null) && (deleteInfo.size() > 0)) {
		envImpl.addToCompressorQueue(deleteInfo.values(), false);
		deleteInfo.clear();
	    }
	}
  }

}