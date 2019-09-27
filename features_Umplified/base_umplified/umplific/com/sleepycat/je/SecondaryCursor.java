/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.dbi.CursorImpl.SearchMode;
import com.sleepycat.je.dbi.GetMode;
import java.util.logging.Level;
import java.util.Set;
import java.util.HashSet;

// line 3 "../../../SecondaryCursor.ump"
public class SecondaryCursor extends Cursor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SecondaryCursor(CursorImpl aCursorImpl, CursorConfig aConfig)
  {
    super(aCursorImpl, aConfig);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * Cursor constructor. Not public. To get a cursor, the user should call SecondaryDatabase.cursor();
   */
  // line 21 "../../../SecondaryCursor.ump"
  public  SecondaryCursor(SecondaryDatabase dbHandle, Transaction txn, CursorConfig cursorConfig) throws DatabaseException{
    super(dbHandle, txn, cursorConfig);
	secondaryDb = dbHandle;
	primaryDb = dbHandle.getPrimaryDatabase();
  }


  /**
   * 
   * Copy constructor.
   */
  // line 30 "../../../SecondaryCursor.ump"
   private  SecondaryCursor(SecondaryCursor cursor, boolean samePosition) throws DatabaseException{
    super(cursor, samePosition);
	secondaryDb = cursor.secondaryDb;
	primaryDb = cursor.primaryDb;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 39 "../../../SecondaryCursor.ump"
   public Database getPrimaryDatabase(){
    return primaryDb;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 46 "../../../SecondaryCursor.ump"
   public Cursor dup(boolean samePosition) throws DatabaseException{
    checkState(true);
	return new SecondaryCursor(this, samePosition);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 54 "../../../SecondaryCursor.ump"
   public SecondaryCursor dupSecondary(boolean samePosition) throws DatabaseException{
    return (SecondaryCursor) dup(samePosition);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 61 "../../../SecondaryCursor.ump"
   public OperationStatus delete() throws DatabaseException{
    checkState(true);
	checkUpdatesAllowed("delete");
	this.hook65();
	DatabaseEntry key = new DatabaseEntry();
	DatabaseEntry pKey = new DatabaseEntry();
	OperationStatus status = getCurrentInternal(key, pKey, LockMode.RMW);
	if (status == OperationStatus.SUCCESS) {
	    status = primaryDb.deleteInternal(cursorImpl.getLocker(), pKey);
	}
	return status;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 77 "../../../SecondaryCursor.ump"
   public OperationStatus put(DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    throw SecondaryDatabase.notAllowedException();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 84 "../../../SecondaryCursor.ump"
   public OperationStatus putNoOverwrite(DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    throw SecondaryDatabase.notAllowedException();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 91 "../../../SecondaryCursor.ump"
   public OperationStatus putNoDupData(DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    throw SecondaryDatabase.notAllowedException();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 98 "../../../SecondaryCursor.ump"
   public OperationStatus putCurrent(DatabaseEntry data) throws DatabaseException{
    throw SecondaryDatabase.notAllowedException();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 106 "../../../SecondaryCursor.ump"
   public OperationStatus getCurrent(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getCurrent(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 114 "../../../SecondaryCursor.ump"
   public OperationStatus getCurrent(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(true);
	checkArgsNoValRequired(key, pKey, data);
	this.hook66(lockMode);
	return getCurrentInternal(key, pKey, data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 124 "../../../SecondaryCursor.ump"
   public OperationStatus getFirst(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getFirst(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 132 "../../../SecondaryCursor.ump"
   public OperationStatus getFirst(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	checkArgsNoValRequired(key, pKey, data);
	this.hook67(lockMode);
	return position(key, pKey, data, lockMode, true);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 142 "../../../SecondaryCursor.ump"
   public OperationStatus getLast(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getLast(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 150 "../../../SecondaryCursor.ump"
   public OperationStatus getLast(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	checkArgsNoValRequired(key, pKey, data);
	this.hook68(lockMode);
	return position(key, pKey, data, lockMode, false);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 160 "../../../SecondaryCursor.ump"
   public OperationStatus getNext(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getNext(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 168 "../../../SecondaryCursor.ump"
   public OperationStatus getNext(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	checkArgsNoValRequired(key, pKey, data);
	this.hook69(lockMode);
	if (cursorImpl.isNotInitialized()) {
	    return position(key, pKey, data, lockMode, true);
	} else {
	    return retrieveNext(key, pKey, data, lockMode, GetMode.NEXT);
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 183 "../../../SecondaryCursor.ump"
   public OperationStatus getNextDup(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getNextDup(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 191 "../../../SecondaryCursor.ump"
   public OperationStatus getNextDup(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(true);
	checkArgsNoValRequired(key, pKey, data);
	this.hook70(lockMode);
	return retrieveNext(key, pKey, data, lockMode, GetMode.NEXT_DUP);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 202 "../../../SecondaryCursor.ump"
   public OperationStatus getNextNoDup(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getNextNoDup(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 210 "../../../SecondaryCursor.ump"
   public OperationStatus getNextNoDup(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	checkArgsNoValRequired(key, pKey, data);
	this.hook71(lockMode);
	if (cursorImpl.isNotInitialized()) {
	    return position(key, pKey, data, lockMode, true);
	} else {
	    return retrieveNext(key, pKey, data, lockMode, GetMode.NEXT_NODUP);
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 224 "../../../SecondaryCursor.ump"
   public OperationStatus getPrev(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getPrev(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 232 "../../../SecondaryCursor.ump"
   public OperationStatus getPrev(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	checkArgsNoValRequired(key, pKey, data);
	this.hook72(lockMode);
	if (cursorImpl.isNotInitialized()) {
	    return position(key, pKey, data, lockMode, false);
	} else {
	    return retrieveNext(key, pKey, data, lockMode, GetMode.PREV);
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 247 "../../../SecondaryCursor.ump"
   public OperationStatus getPrevDup(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getPrevDup(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 255 "../../../SecondaryCursor.ump"
   public OperationStatus getPrevDup(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(true);
	checkArgsNoValRequired(key, pKey, data);
	this.hook73(lockMode);
	return retrieveNext(key, pKey, data, lockMode, GetMode.PREV_DUP);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 266 "../../../SecondaryCursor.ump"
   public OperationStatus getPrevNoDup(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getPrevNoDup(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 274 "../../../SecondaryCursor.ump"
   public OperationStatus getPrevNoDup(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	checkArgsNoValRequired(key, pKey, data);
	this.hook74(lockMode);
	if (cursorImpl.isNotInitialized()) {
	    return position(key, pKey, data, lockMode, false);
	} else {
	    return retrieveNext(key, pKey, data, lockMode, GetMode.PREV_NODUP);
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 289 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchKey(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getSearchKey(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 297 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchKey(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(pKey, "pKey", false);
	DatabaseUtil.checkForNullDbt(data, "data", false);
	this.hook75(key, lockMode);
	return search(key, pKey, data, lockMode, SearchMode.SET);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 310 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchKeyRange(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    return getSearchKeyRange(key, new DatabaseEntry(), data, lockMode);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 318 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchKeyRange(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(pKey, "pKey", false);
	DatabaseUtil.checkForNullDbt(data, "data", false);
	this.hook76(key, data, lockMode);
	return search(key, pKey, data, lockMode, SearchMode.SET_RANGE);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 331 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchBoth(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    throw SecondaryDatabase.notAllowedException();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 339 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchBoth(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(pKey, "pKey", true);
	DatabaseUtil.checkForNullDbt(data, "data", false);
	this.hook77(key, data, lockMode);
	return search(key, pKey, data, lockMode, SearchMode.BOTH);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 352 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchBothRange(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    throw SecondaryDatabase.notAllowedException();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 360 "../../../SecondaryCursor.ump"
   public OperationStatus getSearchBothRange(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkState(false);
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(pKey, "pKey", true);
	DatabaseUtil.checkForNullDbt(data, "data", false);
	this.hook78(key, data, lockMode);
	return search(key, pKey, data, lockMode, SearchMode.BOTH_RANGE);
  }


  /**
   * 
   * Returns the current key and data.
   */
  // line 373 "../../../SecondaryCursor.ump"
   private OperationStatus getCurrentInternal(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    OperationStatus status = getCurrentInternal(key, pKey, lockMode);
	if (status == OperationStatus.SUCCESS) {
	    status = readPrimaryAfterGet(key, pKey, data, lockMode);
	}
	return status;
  }


  /**
   * 
   * Calls search() and retrieves primary data.
   */
  // line 385 "../../../SecondaryCursor.ump"
  public OperationStatus search(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode, SearchMode searchMode) throws DatabaseException{
    while (true) {
	    OperationStatus status = search(key, pKey, lockMode, searchMode);
	    if (status != OperationStatus.SUCCESS) {
		return status;
	    }
	    status = readPrimaryAfterGet(key, pKey, data, lockMode);
	    if (status == OperationStatus.SUCCESS) {
		return status;
	    }
	}
  }


  /**
   * 
   * Calls position() and retrieves primary data.
   */
  // line 402 "../../../SecondaryCursor.ump"
  public OperationStatus position(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode, boolean first) throws DatabaseException{
    while (true) {
	    OperationStatus status = position(key, pKey, lockMode, first);
	    if (status != OperationStatus.SUCCESS) {
		return status;
	    }
	    status = readPrimaryAfterGet(key, pKey, data, lockMode);
	    if (status == OperationStatus.SUCCESS) {
		return status;
	    }
	}
  }


  /**
   * 
   * Calls retrieveNext() and retrieves primary data.
   */
  // line 419 "../../../SecondaryCursor.ump"
  public OperationStatus retrieveNext(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode, GetMode getMode) throws DatabaseException{
    while (true) {
	    OperationStatus status = retrieveNext(key, pKey, lockMode, getMode);
	    if (status != OperationStatus.SUCCESS) {
		return status;
	    }
	    status = readPrimaryAfterGet(key, pKey, data, lockMode);
	    if (status == OperationStatus.SUCCESS) {
		return status;
	    }
	}
  }


  /**
   * 
   * Reads the primary data for a primary key that was read via a secondary. When SUCCESS is returned by this method, the caller should return SUCCESS.  When KEYEMPTY is returned, the caller should treat this as a deleted record and either retry the operation (in the case of position, search, and retrieveNext) or return KEYEMPTY (in the case of getCurrent).  KEYEMPTY is only returned when read-uncommitted is used.
   * @return SUCCESS if the primary was read succesfully, or KEYEMPTY ifusing read-uncommitted and the primary has been deleted, or KEYEMPTY if using read-uncommitted and the primary has been updated and no longer contains the secondary key.
   * @throws DatabaseException to indicate a corrupt secondary reference ifthe primary record is not found and read-uncommitted is not used (or read-uncommitted is used, but we cannot verify that a valid deletion has occured).
   */
  // line 438 "../../../SecondaryCursor.ump"
   private OperationStatus readPrimaryAfterGet(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    Locker locker = cursorImpl.getLocker();
	Cursor cursor = null;
	try {
	    cursor = new Cursor(primaryDb, locker, null);
	    OperationStatus status = cursor.search(pKey, data, lockMode, SearchMode.SET);
	    if (status != OperationStatus.SUCCESS) {
		if (isReadUncommittedMode(lockMode)) {
		    status = getCurrentInternal(key, pKey, lockMode);
		    if (status == OperationStatus.KEYEMPTY) {
			return status;
		    }
		}
		SecondaryDatabase secDb = (SecondaryDatabase) getDatabase();
		throw secDb.secondaryCorruptException();
	    }
	    if (isReadUncommittedMode(lockMode)) {
		SecondaryConfig config = secondaryDb.getPrivateSecondaryConfig();
		if (config.getImmutableSecondaryKey()) {
		} else if (config.getKeyCreator() != null) {
		    DatabaseEntry secKey = new DatabaseEntry();
		    if (!config.getKeyCreator().createSecondaryKey(secondaryDb, pKey, data, secKey)
			    || !secKey.equals(key)) {
			return OperationStatus.KEYEMPTY;
		    }
		} else if (config.getMultiKeyCreator() != null) {
		    Set results = new HashSet();
		    config.getMultiKeyCreator().createSecondaryKeys(secondaryDb, pKey, data, results);
		    if (!results.contains(key)) {
			return OperationStatus.KEYEMPTY;
		    }
		}
	    }
	    return OperationStatus.SUCCESS;
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	}
  }


  /**
   * 
   * Note that this flavor of checkArgs doesn't require that the dbt data is set.
   */
  // line 482 "../../../SecondaryCursor.ump"
   private void checkArgsNoValRequired(DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data){
    DatabaseUtil.checkForNullDbt(key, "key", false);
	DatabaseUtil.checkForNullDbt(pKey, "pKey", false);
	DatabaseUtil.checkForNullDbt(data, "data", false);
  }

  // line 488 "../../../SecondaryCursor.ump"
   protected void hook65() throws DatabaseException{
    
  }

  // line 491 "../../../SecondaryCursor.ump"
   protected void hook66(LockMode lockMode) throws DatabaseException{
    
  }

  // line 494 "../../../SecondaryCursor.ump"
   protected void hook67(LockMode lockMode) throws DatabaseException{
    
  }

  // line 497 "../../../SecondaryCursor.ump"
   protected void hook68(LockMode lockMode) throws DatabaseException{
    
  }

  // line 500 "../../../SecondaryCursor.ump"
   protected void hook69(LockMode lockMode) throws DatabaseException{
    
  }

  // line 503 "../../../SecondaryCursor.ump"
   protected void hook70(LockMode lockMode) throws DatabaseException{
    
  }

  // line 506 "../../../SecondaryCursor.ump"
   protected void hook71(LockMode lockMode) throws DatabaseException{
    
  }

  // line 509 "../../../SecondaryCursor.ump"
   protected void hook72(LockMode lockMode) throws DatabaseException{
    
  }

  // line 512 "../../../SecondaryCursor.ump"
   protected void hook73(LockMode lockMode) throws DatabaseException{
    
  }

  // line 515 "../../../SecondaryCursor.ump"
   protected void hook74(LockMode lockMode) throws DatabaseException{
    
  }

  // line 518 "../../../SecondaryCursor.ump"
   protected void hook75(DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    
  }

  // line 521 "../../../SecondaryCursor.ump"
   protected void hook76(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    
  }

  // line 524 "../../../SecondaryCursor.ump"
   protected void hook77(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    
  }

  // line 527 "../../../SecondaryCursor.ump"
   protected void hook78(DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../SecondaryCursor.ump"
  private SecondaryDatabase secondaryDb ;
// line 15 "../../../SecondaryCursor.ump"
  private Database primaryDb ;

  
}