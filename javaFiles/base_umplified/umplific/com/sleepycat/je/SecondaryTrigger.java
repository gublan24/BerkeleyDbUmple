/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Locker;

// line 3 "../../../SecondaryTrigger.ump"
public class SecondaryTrigger implements DatabaseTrigger
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SecondaryTrigger()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 11 "../../../SecondaryTrigger.ump"
  public  SecondaryTrigger(SecondaryDatabase secDb){
    this.secDb = secDb;
  }

  // line 19 "../../../SecondaryTrigger.ump"
   public void triggerAdded(Database db){
    
  }

  // line 22 "../../../SecondaryTrigger.ump"
   public void triggerRemoved(Database db){
    secDb.clearPrimary();
  }

  // line 27 "../../../SecondaryTrigger.ump"
   public void databaseUpdated(Database db, Locker locker, DatabaseEntry priKey, DatabaseEntry oldData, DatabaseEntry newData) throws DatabaseException{
    secDb.updateSecondary(locker, null, priKey, oldData, newData);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../SecondaryTrigger.ump"
  private SecondaryDatabase secDb ;

// line 14 "../../../SecondaryTrigger.ump"
  final SecondaryDatabase getDb () 
  {
    return secDb;
  }

  
}