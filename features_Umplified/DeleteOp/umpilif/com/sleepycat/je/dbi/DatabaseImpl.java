/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../DatabaseImpl.ump"
public class DatabaseImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 16 "../../../../DatabaseImpl.ump"
   public boolean isDeleted(){
    return !(deleteState == NOT_DELETED);
  }

  // line 20 "../../../../DatabaseImpl.ump"
   public boolean isDeleteFinished(){
    return (deleteState == DELETED);
  }

  // line 24 "../../../../DatabaseImpl.ump"
   public void startDeleteProcessing(){
    assert (deleteState == NOT_DELETED);
	deleteState = DELETED_CLEANUP_INLIST_HARVEST;
  }

  // line 29 "../../../../DatabaseImpl.ump"
  public void finishedINListHarvest(){
    assert (deleteState == DELETED_CLEANUP_INLIST_HARVEST);
	deleteState = DELETED_CLEANUP_LOG_HARVEST;
  }


  /**
   * 
   * Purge a DatabaseImpl and corresponding MapLN in the db mapping tree. Purging consists of removing all related INs from the db mapping tree and deleting the related MapLN. Used at the a transaction end in these cases: - purge the deleted database after a commit of Environment.removeDatabase - purge the deleted database after a commit of Environment.truncateDatabase - purge the newly created database after an abort of Environment.truncateDatabase
   */
  // line 37 "../../../../DatabaseImpl.ump"
   public void deleteAndReleaseINs() throws DatabaseException{
    startDeleteProcessing();
	releaseDeletedINs();
  }

  // line 42 "../../../../DatabaseImpl.ump"
   public void releaseDeletedINs() throws DatabaseException{
    if (pendingDeletedHook != null) {
	    pendingDeletedHook.doHook();
	}
	try {
	    long rootLsn = tree.getRootLsn();
	    if (rootLsn == DbLsn.NULL_LSN) {
		envImpl.getDbMapTree().deleteMapLN(id);
	    } else {
		UtilizationTracker snapshot = new UtilizationTracker(envImpl);
		snapshot.countObsoleteNodeInexact(rootLsn, LogEntryType.LOG_IN);
		ObsoleteProcessor obsoleteProcessor = new ObsoleteProcessor(snapshot);
		SortedLSNTreeWalker walker = new SortedLSNTreeWalker(this, true, true, rootLsn, obsoleteProcessor);
		envImpl.getDbMapTree().deleteMapLN(id);
		walker.walk();
		envImpl.getUtilizationProfile().countAndLogSummaries(snapshot.getTrackedFiles());
	    }
	} finally {
	    deleteState = DELETED;
	}
  }

  // line 64 "../../../../DatabaseImpl.ump"
   public void checkIsDeleted(String operation) throws DatabaseException{
    if (isDeleted()) {
	    throw new DatabaseException("Attempt to " + operation + " a deleted database");
	}
  }

  // line 70 "../../../../DatabaseImpl.ump"
   protected void hook288() throws DatabaseException{
    deleteState = NOT_DELETED;
	original();
  }

  // line 75 "../../../../DatabaseImpl.ump"
   protected void hook289() throws DatabaseException{
    deleteState = NOT_DELETED;
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../DatabaseImpl.ump"
  private static final short NOT_DELETED = 1 ;
// line 7 "../../../../DatabaseImpl.ump"
  private static final short DELETED_CLEANUP_INLIST_HARVEST = 2 ;
// line 9 "../../../../DatabaseImpl.ump"
  private static final short DELETED_CLEANUP_LOG_HARVEST = 3 ;
// line 11 "../../../../DatabaseImpl.ump"
  private static final short DELETED = 4 ;
// line 13 "../../../../DatabaseImpl.ump"
  private short deleteState ;

  
}