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
   protected void hook803() throws DatabaseException,RunRecoveryException,Throwable{
    if ((deleteInfo != null) && deleteInfo.size() > 0) {
	    envImpl.addToCompressorQueue(deleteInfo.values(), false);
	    deleteInfo.clear();
	}
	original();
  }

  // line 14 "../../../../Txn.ump"
   protected void hook804() throws DatabaseException{
    deleteInfo = null;
	original();
  }

}