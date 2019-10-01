/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Locker;

// line 3 "../../../ForeignKeyTrigger.ump"
public class ForeignKeyTrigger implements DatabaseTrigger
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ForeignKeyTrigger()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 11 "../../../ForeignKeyTrigger.ump"
  public  ForeignKeyTrigger(SecondaryDatabase secDb){
    this.secDb = secDb;
  }

  // line 15 "../../../ForeignKeyTrigger.ump"
   public void triggerAdded(Database db){
    
  }

  // line 18 "../../../ForeignKeyTrigger.ump"
   public void triggerRemoved(Database db){
    secDb.clearForeignKeyTrigger();
  }

  // line 23 "../../../ForeignKeyTrigger.ump"
   public void databaseUpdated(Database db, Locker locker, DatabaseEntry priKey, DatabaseEntry oldData, DatabaseEntry newData) throws DatabaseException{
    if (newData == null) {
	    secDb.onForeignKeyDelete(locker, priKey);
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../ForeignKeyTrigger.ump"
  private SecondaryDatabase secDb ;

  
}