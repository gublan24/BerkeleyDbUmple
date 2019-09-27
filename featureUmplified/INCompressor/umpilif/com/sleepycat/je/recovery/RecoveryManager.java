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
   protected void hook594(DatabaseImpl db, TreeLocation location, byte [] deletedKey) throws DatabaseException{
    if (deletedKey != null) {
	    db.getDbEnvironment().addToCompressorQueue(location.bin, new Key(deletedKey), false);
	}
	original(db, location, deletedKey);
  }

  // line 13 "../../../../RecoveryManager.ump"
   protected static  void hook595(DatabaseImpl db, TreeLocation location, byte [] deletedKey) throws DatabaseException{
    db.getDbEnvironment().addToCompressorQueue(location.bin, new Key(deletedKey), false);
	original(db, location, deletedKey);
  }

}