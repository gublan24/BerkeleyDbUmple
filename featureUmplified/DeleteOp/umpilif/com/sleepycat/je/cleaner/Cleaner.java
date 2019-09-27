/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

/**
 * Original file:/home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/DeleteOp/com/sleepycat/je/cleaner/Cleaner.java
 * namespace com.sleepycat.je.cleaner;
 */
// line 3 "../../../../Cleaner.ump"
// line 3 "../../../../Cleaner_inner.ump"
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


  /**
   * 
   * Adds the DB ID to the pending DB set if it is being deleted but deletion is not yet complete.
   */
  // line 9 "../../../../Cleaner.ump"
  public void addPendingDB(DatabaseImpl db){
    if (db != null && db.isDeleted() && !db.isDeleteFinished()) {
	    DatabaseId id = db.getId();
	    if (fileSelector.addPendingDB(id)) {
		this.hook85(id);
	    }
	}
  }

  // line 18 "../../../../Cleaner.ump"
   protected void hook85(DatabaseId id){
    
  }

  // line 21 "../../../../Cleaner.ump"
   protected boolean hook112(DatabaseImpl db, boolean c) throws DatabaseException{
    c = c || db.isDeleted();
	return original(db, c);
  }

  // line 26 "../../../../Cleaner.ump"
   protected void hook113(DatabaseImpl db) throws DatabaseException{
    addPendingDB(db);
	original(db);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../Cleaner_inner.ump"
  public static class Cleaner_processPending
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Cleaner_processPending()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Cleaner_inner.ump"
    public void execute() throws DatabaseException{
      original();
          pendingDBs=_this.fileSelector.getPendingDBs();
          if (pendingDBs != null) {
            for (int i=0; i < pendingDBs.length; i+=1) {
              dbId2=pendingDBs[i];
              db2=dbMapTree.getDb(dbId2,_this.lockTimeout);
              if (db2 == null || db2.isDeleteFinished()) {
                _this.fileSelector.removePendingDB(dbId2);
              }
            }
          }
    }
  
  }
}