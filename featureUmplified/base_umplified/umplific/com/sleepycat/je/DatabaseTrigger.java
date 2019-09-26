/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Locker;
// line 3 "../../../DatabaseTrigger.ump"
public interface DatabaseTrigger
{
  
  void databaseUpdated(Database db, Locker locker, DatabaseEntry priKey, DatabaseEntry oldData, DatabaseEntry newData)
	    throws DatabaseException ;

  // ABSTRACT METHODS 

 public void triggerAdded(Database db);
 public void triggerRemoved(Database db);
}