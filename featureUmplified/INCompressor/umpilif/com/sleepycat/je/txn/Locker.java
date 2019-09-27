/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Locker.ump"
public class Locker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Locker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Add delete information, to be added to the inCompressor queue when the transaction ends.
   */
  // line 11 "../../../../Locker.ump"
   public void addDeleteInfo(BIN bin, Key deletedKey) throws DatabaseException{
    synchronized (this) {
	    if (deleteInfo == null) {
		deleteInfo = new HashMap();
	    }
	    Long nodeId = new Long(bin.getNodeId());
	    BINReference binRef = (BINReference) deleteInfo.get(nodeId);
	    if (binRef == null) {
		binRef = bin.createReference();
		deleteInfo.put(nodeId, binRef);
	    }
	    binRef.addDeletedKey(deletedKey);
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Locker.ump"
  protected Map deleteInfo ;

  
}