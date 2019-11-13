/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.TinyHashSet;
import com.sleepycat.je.txn.LockerFactory;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.dbi.CursorImpl.SearchMode;
import com.sleepycat.je.dbi.PutMode;
import com.sleepycat.je.dbi.GetMode;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import java.util.Iterator;
import java.util.Comparator;
import java.util.ArrayList;
import com.sleepycat.je.dbi.TruncateResult;

/**
 * namespace com.sleepycat.je;
 */
// line 3 "../../../Database.ump"
// line 3 "../../../Database_static.ump"
// line 3 "../../../loggingBase_Database.ump"
// line 3 "../../../Truncate_Database.ump"
// line 3 "../../../Truncate_Database_inner.ump"
// line 3 "../../../DeleteOp_Database.ump"
// line 3 "../../../Statistics_Database.ump"
// line 3 "../../../Latches_Database.ump"
// line 3 "../../../Latches_Database_inner.ump"
public class Database
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Database Attributes
  private Environment envHandle;
  private DatabaseConfig configuration;
  private Locker handleLocker;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Database(Environment aEnvHandle, DatabaseConfig aConfiguration, Locker aHandleLocker)
  {
    envHandle = aEnvHandle;
    configuration = aConfiguration;
    handleLocker = aHandleLocker;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEnvHandle(Environment aEnvHandle)
  {
    boolean wasSet = false;
    envHandle = aEnvHandle;
    wasSet = true;
    return wasSet;
  }

  public boolean setConfiguration(DatabaseConfig aConfiguration)
  {
    boolean wasSet = false;
    configuration = aConfiguration;
    wasSet = true;
    return wasSet;
  }

  public boolean setHandleLocker(Locker aHandleLocker)
  {
    boolean wasSet = false;
    handleLocker = aHandleLocker;
    wasSet = true;
    return wasSet;
  }

  public Environment getEnvHandle()
  {
    return envHandle;
  }

  public DatabaseConfig getConfiguration()
  {
    return configuration;
  }

  public Locker getHandleLocker()
  {
    return handleLocker;
  }

  public void delete()
  {}


  /**
   * 
   * Creates a database but does not open or fully initialize it. Is protected for use in compat package.
   */
  // line 47 "../../../Database.ump"
   protected  Database(Environment env){
    this.envHandle = env;
	handleLocker = null;
  }


  /**
   * 
   * Create a database, called by Environment.
   */
  // line 56 "../../../Database.ump"
  public void initNew(Environment env, Locker locker, String databaseName, DatabaseConfig dbConfig) throws DatabaseException{
    if (dbConfig.getReadOnly()) {
	    throw new DatabaseException(
		    "DatabaseConfig.setReadOnly() must be set to false " + "when creating a Database");
	}
	init(env, dbConfig);
	EnvironmentImpl environmentImpl = DbInternal.envGetEnvironmentImpl(envHandle);
	databaseImpl = environmentImpl.createDb(locker, databaseName, dbConfig, this);
	databaseImpl.addReferringHandle(this);
  }


  /**
   * 
   * Open a database, called by Environment.
   */
  // line 71 "../../../Database.ump"
  public void initExisting(Environment env, Locker locker, DatabaseImpl databaseImpl, DatabaseConfig dbConfig) throws DatabaseException{
    validateConfigAgainstExistingDb(dbConfig, databaseImpl);
	init(env, dbConfig);
	this.databaseImpl = databaseImpl;
	databaseImpl.addReferringHandle(this);
	configuration.setSortedDuplicates(databaseImpl.getSortedDuplicates());
	configuration.setTransactional(databaseImpl.isTransactional());
  }

  // line 80 "../../../Database.ump"
   private void init(Environment env, DatabaseConfig config) throws DatabaseException{
    handleLocker = null;
	envHandle = env;
	configuration = config.cloneConfig();
	isWritable = !configuration.getReadOnly();
	state = OPEN;
  }


  /**
   * 
   * See if this new handle's configuration is compatible with the pre-existing database.
   */
  // line 92 "../../../Database.ump"
   private void validateConfigAgainstExistingDb(DatabaseConfig config, DatabaseImpl databaseImpl) throws DatabaseException{
    if (!config.getUseExistingConfig()) {
	    if (databaseImpl.getSortedDuplicates() != config.getSortedDuplicates()) {
		throw new DatabaseException("You can't open a Database with a duplicatesAllowed " + "configuration of "
			+ config.getSortedDuplicates() + " if the underlying database was created with a "
			+ "duplicatesAllowedSetting of " + databaseImpl.getSortedDuplicates() + ".");
	    }
	}
	if (databaseImpl.hasOpenHandles()) {
	    if (!config.getUseExistingConfig()) {
		if (config.getTransactional() != databaseImpl.isTransactional()) {
		    throw new DatabaseException("You can't open a Database with a transactional " + "configuration of "
			    + config.getTransactional() + " if the underlying database was created with a "
			    + "transactional configuration of " + databaseImpl.isTransactional() + ".");
		}
	    }
	} else {
	    databaseImpl.setTransactional(config.getTransactional());
	}
	if (config.getOverrideBtreeComparator()) {
	    databaseImpl.setBtreeComparator(config.getBtreeComparator());
	}
	if (config.getOverrideDuplicateComparator()) {
	    databaseImpl.setDuplicateComparator(config.getDuplicateComparator());
	}
  }

  // line 119 "../../../Database.ump"
   public synchronized  void close() throws DatabaseException{
    StringBuffer errors = null;
	checkEnv();
	checkProhibitedDbState(CLOSED, "Can't close Database:");
	Label44:           ;  //this.hook44();
	removeAllTriggers();
	envHandle.removeReferringHandle(this);
	if (cursors.size() > 0) {
	    errors = new StringBuffer("There are open cursors against the database.\n");
	    errors.append("They will be closed.\n");
	    Iterator iter = cursors.copy().iterator();
	    while (iter.hasNext()) {
		Cursor dbc = (Cursor) iter.next();
		try {
		    dbc.close();
		} catch (DatabaseException DBE) {
		    errors.append("Exception while closing cursors:\n");
		    errors.append(DBE.toString());
		}
	    }
	}
	if (databaseImpl != null) {
	    databaseImpl.removeReferringHandle(this);
	    databaseImpl = null;
	    handleLocker.setHandleLockOwner(true, this, true);
	    handleLocker.operationEnd(true);
	    state = CLOSED;
	}
	if (errors != null) {
	    throw new DatabaseException(errors.toString());
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 155 "../../../Database.ump"
   public Sequence openSequence(Transaction txn, DatabaseEntry key, SequenceConfig config) throws DatabaseException{
    checkEnv();
	DatabaseUtil.checkForNullDbt(key, "key", true);
	checkRequiredDbState(OPEN, "Can't call Database.openSequence:");
	checkWritable("openSequence");
	Label45:           ;  //this.hook45(txn, key);
	return new Sequence(this, txn, key, config);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 167 "../../../Database.ump"
   public void removeSequence(Transaction txn, DatabaseEntry key) throws DatabaseException{
    delete(txn, key);
  }

  // line 171 "../../../Database.ump"
   public synchronized  Cursor openCursor(Transaction txn, CursorConfig cursorConfig) throws DatabaseException{
    checkEnv();
	checkRequiredDbState(OPEN, "Can't open a cursor");
	CursorConfig useConfig = (cursorConfig == null) ? CursorConfig.DEFAULT : cursorConfig;
	if (useConfig.getReadUncommitted() && useConfig.getReadCommitted()) {
	    throw new IllegalArgumentException("Only one may be specified: ReadCommitted or ReadUncommitted");
	}
	Label46:           ;  //this.hook46(txn, cursorConfig);
	Cursor ret = newDbcInstance(txn, useConfig);
	return ret;
  }


  /**
   * 
   * Is overridden by SecondaryDatabase.
   */
  // line 186 "../../../Database.ump"
  public Cursor newDbcInstance(Transaction txn, CursorConfig cursorConfig) throws DatabaseException{
    return new Cursor(this, txn, cursorConfig);
  }

  // line 190 "../../../Database.ump"
   public OperationStatus delete(Transaction txn, DatabaseEntry key) throws DatabaseException{
    checkEnv();
	DatabaseUtil.checkForNullDbt(key, "key", true);
	checkRequiredDbState(OPEN, "Can't call Database.delete:");
	checkWritable("delete");
	Label47:           ;  //this.hook47(txn, key);
	OperationStatus commitStatus = OperationStatus.NOTFOUND;
	Locker locker = null;
	try {
	    locker = LockerFactory.getWritableLocker(envHandle, txn, isTransactional());
	    commitStatus = deleteInternal(locker, key);
	    return commitStatus;
	} finally {
	    if (locker != null) {
		locker.operationEnd(commitStatus);
	    }
	}
  }


  /**
   * 
   * Internal version of delete() that does no parameter checking.  Notify triggers.  Deletes all duplicates.
   */
  // line 212 "../../../Database.ump"
  public OperationStatus deleteInternal(Locker locker, DatabaseEntry key) throws DatabaseException{
    Cursor cursor = null;
	try {
	    cursor = new Cursor(this, locker, null);
	    cursor.setNonCloning(true);
	    OperationStatus commitStatus = OperationStatus.NOTFOUND;
	    DatabaseEntry oldData = new DatabaseEntry();
	    OperationStatus searchStatus = cursor.search(key, oldData, LockMode.RMW, SearchMode.SET);
	    if (searchStatus == OperationStatus.SUCCESS) {
		do {
		    if (hasTriggers()) {
			notifyTriggers(locker, key, oldData, null);
		    }
		    commitStatus = cursor.deleteNoNotify();
		    if (commitStatus != OperationStatus.SUCCESS) {
			return commitStatus;
		    }
		    if (databaseImpl.getSortedDuplicates()) {
			searchStatus = cursor.retrieveNext(key, oldData, LockMode.RMW, GetMode.NEXT_DUP);
		    } else {
			searchStatus = OperationStatus.NOTFOUND;
		    }
		} while (searchStatus == OperationStatus.SUCCESS);
		commitStatus = OperationStatus.SUCCESS;
	    }
	    return commitStatus;
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	}
  }

  // line 246 "../../../Database.ump"
   public OperationStatus get(Transaction txn, DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkEnv();
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(data, "data", false);
	checkRequiredDbState(OPEN, "Can't call Database.get:");
	Label48:           ;  //this.hook48(txn, key, lockMode);
	CursorConfig cursorConfig = CursorConfig.DEFAULT;
	if (lockMode == LockMode.READ_COMMITTED) {
	    cursorConfig = CursorConfig.READ_COMMITTED;
	    lockMode = null;
	}
	Cursor cursor = null;
	try {
	    cursor = new Cursor(this, txn, cursorConfig);
	    cursor.setNonCloning(true);
	    return cursor.search(key, data, lockMode, SearchMode.SET);
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	}
  }

  // line 270 "../../../Database.ump"
   public OperationStatus getSearchBoth(Transaction txn, DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    checkEnv();
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(data, "data", true);
	checkRequiredDbState(OPEN, "Can't call Database.getSearchBoth:");
	Label49:           ;  //this.hook49(txn, key, data, lockMode);
	CursorConfig cursorConfig = CursorConfig.DEFAULT;
	if (lockMode == LockMode.READ_COMMITTED) {
	    cursorConfig = CursorConfig.READ_COMMITTED;
	    lockMode = null;
	}
	Cursor cursor = null;
	try {
	    cursor = new Cursor(this, txn, cursorConfig);
	    cursor.setNonCloning(true);
	    return cursor.search(key, data, lockMode, SearchMode.BOTH);
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	}
  }

  // line 293 "../../../Database.ump"
   public OperationStatus put(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    checkEnv();
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(data, "data", true);
	DatabaseUtil.checkForPartialKey(key);
	checkRequiredDbState(OPEN, "Can't call Database.put");
	checkWritable("put");
	Label50:           ;  //this.hook50(txn, key, data);
	return putInternal(txn, key, data, PutMode.OVERWRITE);
  }

  // line 305 "../../../Database.ump"
   public OperationStatus putNoOverwrite(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    checkEnv();
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(data, "data", true);
	DatabaseUtil.checkForPartialKey(key);
	checkRequiredDbState(OPEN, "Can't call Database.putNoOverWrite");
	checkWritable("putNoOverwrite");
	Label51:           ;  //this.hook51(txn, key, data);
	return putInternal(txn, key, data, PutMode.NOOVERWRITE);
  }

  // line 317 "../../../Database.ump"
   public OperationStatus putNoDupData(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    checkEnv();
	DatabaseUtil.checkForNullDbt(key, "key", true);
	DatabaseUtil.checkForNullDbt(data, "data", true);
	DatabaseUtil.checkForPartialKey(key);
	checkRequiredDbState(OPEN, "Can't call Database.putNoDupData");
	checkWritable("putNoDupData");
	Label52:           ;  //this.hook52(txn, key, data);
	return putInternal(txn, key, data, PutMode.NODUP);
  }


  /**
   * 
   * Internal version of put() that does no parameter checking.
   */
  // line 332 "../../../Database.ump"
  public OperationStatus putInternal(Transaction txn, DatabaseEntry key, DatabaseEntry data, PutMode putMode) throws DatabaseException{
    Locker locker = null;
	Cursor cursor = null;
	OperationStatus commitStatus = OperationStatus.KEYEXIST;
	try {
	    locker = LockerFactory.getWritableLocker(envHandle, txn, isTransactional());
	    cursor = new Cursor(this, locker, null);
	    cursor.setNonCloning(true);
	    commitStatus = cursor.putInternal(key, data, putMode);
	    return commitStatus;
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	    if (locker != null) {
		locker.operationEnd(commitStatus);
	    }
	}
  }


  /**
   * 
   */
  // line 354 "../../../Database.ump"
   public JoinCursor join(Cursor [] cursors, JoinConfig config) throws DatabaseException{
    checkEnv();
	checkRequiredDbState(OPEN, "Can't call Database.join");
	DatabaseUtil.checkForNullParam(cursors, "cursors");
	if (cursors.length == 0) {
	    throw new IllegalArgumentException("At least one cursor is required.");
	}
	Locker locker = cursors[0].getCursorImpl().getLocker();
	if (!locker.isTransactional()) {
	    EnvironmentImpl env = envHandle.getEnvironmentImpl();
	    for (int i = 1; i < cursors.length; i += 1) {
		Locker locker2 = cursors[i].getCursorImpl().getLocker();
		if (locker2.isTransactional()) {
		    throw new IllegalArgumentException("All cursors must use the same transaction.");
		}
		EnvironmentImpl env2 = cursors[i].getDatabaseImpl().getDbEnvironment();
		if (env != env2) {
		    throw new IllegalArgumentException("All cursors must use the same environment.");
		}
	    }
	    locker = null;
	} else {
	    for (int i = 1; i < cursors.length; i += 1) {
		Locker locker2 = cursors[i].getCursorImpl().getLocker();
		if (locker.getTxnLocker() != locker2.getTxnLocker()) {
		    throw new IllegalArgumentException("All cursors must use the same transaction.");
		}
	    }
	}
	return new JoinCursor(locker, this, cursors, config);
  }

  // line 386 "../../../Database.ump"
   public void preload(long maxBytes) throws DatabaseException{
    checkEnv();
			checkRequiredDbState(OPEN, "Can't call Database.preload");
			//Label:           ;  //this.hook55();
			Label55:
databaseImpl.checkIsDeleted("preload");
			//original();

			PreloadConfig config = new PreloadConfig();
			config.setMaxBytes(maxBytes);
			databaseImpl.preload(config);
  }

  // line 396 "../../../Database.ump"
   public void preload(long maxBytes, long maxMillisecs) throws DatabaseException{
    checkEnv();
	checkRequiredDbState(OPEN, "Can't call Database.preload");
	//Label:           ;  //this.hook56();
  Label56:
databaseImpl.checkIsDeleted("preload");
			//original();

	PreloadConfig config = new PreloadConfig();
	config.setMaxBytes(maxBytes);
	config.setMaxMillisecs(maxMillisecs);
	databaseImpl.preload(config);
  }

  // line 407 "../../../Database.ump"
   public PreloadStats preload(PreloadConfig config) throws DatabaseException{
    checkEnv();
			checkRequiredDbState(OPEN, "Can't call Database.preload");
			//Label:           ;  //this.hook57();
      Label57:
databaseImpl.checkIsDeleted("preload");
			//original();

			return databaseImpl.preload(config);
  }

  // line 415 "../../../Database.ump"
   public String getDatabaseName() throws DatabaseException{
    checkEnv();
	if (databaseImpl != null) {
	    return databaseImpl.getName();
	} else {
	    return null;
	}
  }

  // line 424 "../../../Database.ump"
  public String getDebugName(){
    if (databaseImpl != null) {
	    return databaseImpl.getDebugName();
	} else {
	    return null;
	}
  }

  // line 432 "../../../Database.ump"
   public DatabaseConfig getConfig() throws DatabaseException{
    DatabaseConfig showConfig = configuration.cloneConfig();
	Comparator btComp = (databaseImpl == null ? null : databaseImpl.getBtreeComparator());
	Comparator dupComp = (databaseImpl == null ? null : databaseImpl.getDuplicateComparator());
	showConfig.setBtreeComparator(btComp == null ? null : btComp.getClass());
	showConfig.setDuplicateComparator(dupComp == null ? null : dupComp.getClass());
	return showConfig;
  }


  /**
   * 
   * Equivalent to getConfig().getTransactional() but cheaper.
   */
  // line 444 "../../../Database.ump"
  public boolean isTransactional() throws DatabaseException{
    return databaseImpl.isTransactional();
  }

  // line 448 "../../../Database.ump"
   public Environment getEnvironment() throws DatabaseException{
    return envHandle;
  }

  // line 452 "../../../Database.ump"
   public List getSecondaryDatabases() throws DatabaseException{
    List list = new ArrayList();
	if (hasTriggers()) {
	    acquireTriggerListReadLock();
	    Label53://Label:           ;  //this.hook53(list);
			try {
				for (int i = 0; i < triggerList.size(); i += 1) {
						Object obj = triggerList.get(i);
						if (obj instanceof SecondaryTrigger) {
					list.add(((SecondaryTrigger) obj).getDb());
						}
				}
			}
		  finally {
						Label53_1:
//		try {
					//original(list);
		//	} finally {
					releaseTriggerListReadLock();
			//}
   ;//
			}
	} else {
	}
	return list;
  }


  /**
   * 
   * @return true if the Database was opened read/write.
   */
  // line 476 "../../../Database.ump"
  public boolean isWritable(){
    return isWritable;
  }


  /**
   * 
   * Return the databaseImpl object instance.
   */
  // line 483 "../../../Database.ump"
  public DatabaseImpl getDatabaseImpl(){
    return databaseImpl;
  }

  // line 494 "../../../Database.ump"
   synchronized  void removeCursor(Cursor dbc){
    cursors.remove(dbc);
  }

  // line 498 "../../../Database.ump"
   synchronized  void addCursor(Cursor dbc){
    cursors.add(dbc);
  }


  /**
   * 
   * @throws DatabaseException if the Database state is not this value.
   */
  // line 505 "../../../Database.ump"
  public void checkRequiredDbState(DbState required, String msg) throws DatabaseException{
    if (state != required) {
	    throw new DatabaseException(msg + " Database state can't be " + state + " must be " + required);
	}
  }


  /**
   * 
   * @throws DatabaseException if the Database state is this value.
   */
  // line 514 "../../../Database.ump"
  public void checkProhibitedDbState(DbState prohibited, String msg) throws DatabaseException{
    if (state == prohibited) {
	    throw new DatabaseException(msg + " Database state must not be " + prohibited);
	}
  }


  /**
   * 
   * @throws RunRecoveryException if the underlying environment isinvalid
   */
  // line 523 "../../../Database.ump"
  public void checkEnv() throws RunRecoveryException{
    EnvironmentImpl env = envHandle.getEnvironmentImpl();
	if (env != null) {
	    env.checkIfInvalid();
	}
  }


  /**
   * 
   * Invalidate the handle, called by txn.abort by way of DbInternal.
   */
  // line 533 "../../../Database.ump"
   synchronized  void invalidate(){
    state = INVALID;
	envHandle.removeReferringHandle(this);
	if (databaseImpl != null) {
	    databaseImpl.removeReferringHandle(this);
	}
  }


  /**
   * 
   * Check that write operations aren't used on a readonly Database.
   */
  // line 544 "../../../Database.ump"
   private void checkWritable(String operation) throws DatabaseException{
    if (!isWritable) {
	    throw new DatabaseException("Database is Read Only: " + operation);
	}
  }


  /**
   * 
   * Returns whether any triggers are currently associated with this primary. Note that an update of the trigger list may be in progress and this method does not wait for that update to be completed.
   */
  // line 553 "../../../Database.ump"
  public boolean hasTriggers(){
    return triggerList != null;
  }


  /**
   * 
   * Gets a read-lock on the list of triggers.  releaseTriggerListReadLock() must be called to release the lock.  Called by all primary put and delete operations.
   */
  // line 560 "../../../Database.ump"
   private void acquireTriggerListReadLock() throws DatabaseException{
    new Database_acquireTriggerListReadLock(this).execute();
  }


  /**
   * 
   * Gets a write lock on the list of triggers.  An empty list is created if necessary, so null is never returned.  releaseTriggerListWriteLock() must always be called to release the lock.
   */
  // line 567 "../../../Database.ump"
   private void acquireTriggerListWriteLock() throws DatabaseException{
    new Database_acquireTriggerListWriteLock(this).execute();
  }


  /**
   * 
   * Releases a lock acquired by calling acquireTriggerListWriteLock().  If the list is now empty then it is set to null, that is, hasTriggers() will subsequently return false.
   */
  // line 574 "../../../Database.ump"
   private void releaseTriggerListWriteLock() throws DatabaseException{
    new Database_releaseTriggerListWriteLock(this).execute();
  }


  /**
   * 
   * Adds a given trigger to the list of triggers.  Called while opening a SecondaryDatabase.
   * @param insertAtFront true to insert at the front, or false to append.
   */
  // line 582 "../../../Database.ump"
  public void addTrigger(DatabaseTrigger trigger, boolean insertAtFront) throws DatabaseException{
    acquireTriggerListWriteLock();
	try {
	    if (insertAtFront) {
		triggerList.add(0, trigger);
	    } else {
		triggerList.add(trigger);
	    }
	    trigger.triggerAdded(this);
	} finally {
	    releaseTriggerListWriteLock();
	}
  }


  /**
   * 
   * Removes a given trigger from the list of triggers.  Called by SecondaryDatabase.close().
   */
  // line 599 "../../../Database.ump"
  public void removeTrigger(DatabaseTrigger trigger) throws DatabaseException{
    acquireTriggerListWriteLock();
	try {
	    triggerList.remove(trigger);
	    trigger.triggerRemoved(this);
	} finally {
	    releaseTriggerListWriteLock();
	}
  }


  /**
   * 
   * Clears the list of triggers.  Called by close(), this allows closing the primary before its secondaries, although we document that secondaries should be closed first.
   */
  // line 612 "../../../Database.ump"
   private void removeAllTriggers() throws DatabaseException{
    acquireTriggerListWriteLock();
	try {
	    for (int i = 0; i < triggerList.size(); i += 1) {
		DatabaseTrigger trigger = (DatabaseTrigger) triggerList.get(i);
		trigger.triggerRemoved(this);
	    }
	    triggerList.clear();
	} finally {
	    releaseTriggerListWriteLock();
	}
  }


  /**
   * 
   * Notifies associated triggers when a put() or delete() is performed on the primary.  This method is normally called only if hasTriggers() has returned true earlier.  This avoids acquiring a shared latch for primaries with no triggers.  If a trigger is added during the update process, there is no requirement to immediately start updating it.
   * @param locker the internal locker.
   * @param priKey the primary key.
   * @param oldData the primary data before the change, or null if the recorddid not previously exist.
   * @param newData the primary data after the change, or null if the recordhas been deleted.
   */
  // line 633 "../../../Database.ump"
  public void notifyTriggers(Locker locker, DatabaseEntry priKey, DatabaseEntry oldData, DatabaseEntry newData) throws DatabaseException{
    acquireTriggerListReadLock();
				try {	
						Label54: //Label:           ;  //this.hook54(locker, priKey, oldData, newData);
						for (int i = 0; i < triggerList.size(); i += 1) {
									DatabaseTrigger trigger = (DatabaseTrigger) triggerList.get(i);
									trigger.databaseUpdated(this, locker, priKey, oldData, newData);
							}
						}
				finally {
									hook54_1://releaseTriggerListReadLock();
							}
    //End of hook54
  }


  /**
   * 
   * @deprecated It has not been possible to implement this method withcorrect transactional semantics without incurring a performance penalty on all Database operations. Truncate functionality has been moved to Environment.truncateDatabase(), which requires that all Database handles on the database are closed before the truncate operation can execute.
   */
  // line 10 "../../../Truncate_Database.ump"
   public int truncate(Transaction txn, boolean countRecords) throws DatabaseException{
    return new Database_truncate(this, txn, countRecords).execute();
  }


  /**
   * 
   * Internal unchecked truncate that optionally counts records.
   * @deprecated
   */
  // line 18 "../../../Truncate_Database.ump"
  public int truncateInternal(Locker locker, boolean countRecords) throws DatabaseException{
    if (databaseImpl == null) {
					throw new DatabaseException("couldn't find database - truncate");
			}

			Label43:			//this.hook43();
			if (handleLocker.isHandleLockTransferrable()) {
					handleLocker.transferHandleLock(this, locker, false);
			}
			boolean operationOk = false;
			try {
					TruncateResult result = envHandle.getEnvironmentImpl().truncate(locker, databaseImpl);
					databaseImpl = result.getDatabase();
					operationOk = true;
					return countRecords ? result.getRecordCount() : -1;
			} finally {
					locker.setHandleLockOwner(operationOk, this, false);
			}
  }

  // line 6 "../../../Statistics_Database.ump"
   public DatabaseStats getStats(StatsConfig config) throws DatabaseException{
    checkEnv();
			checkRequiredDbState(OPEN, "Can't call Database.stat");
			StatsConfig useConfig = (config == null) ? StatsConfig.DEFAULT : config;
			if (databaseImpl != null) {
					Label38: //this.hook38();
					return databaseImpl.stat(useConfig);
			}
			return null;
  }


  /**
   * 
   * Releases a lock acquired by calling acquireTriggerListReadLock().
   */
  // line 9 "../../../Latches_Database.ump"
   private void releaseTriggerListReadLock() throws DatabaseException{
    EnvironmentImpl env = envHandle.getEnvironmentImpl();
	env.getTriggerLatch().release();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "envHandle" + "=" + (getEnvHandle() != null ? !getEnvHandle().equals(this)  ? getEnvHandle().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "configuration" + "=" + (getConfiguration() != null ? !getConfiguration().equals(this)  ? getConfiguration().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "handleLocker" + "=" + (getHandleLocker() != null ? !getHandleLocker().equals(this)  ? getHandleLocker().toString().replaceAll("  ","    ") : "this" : "null");
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../Database_static.ump"
  public static class DbState
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbState()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 7 "../../../Database_static.ump"
    public  DbState(String stateName){
      this.stateName=stateName;
    }
  
    // line 10 "../../../Database_static.ump"
     public String toString(){
      return "DbState." + stateName;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../Database_static.ump"
    private String stateName ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 13 "../../../Database_static.ump"
  // line 4 "../../../Latches_Database_inner.ump"
  public static class Database_acquireTriggerListReadLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_acquireTriggerListReadLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 15 "../../../Database_static.ump"
    public  Database_acquireTriggerListReadLock(Database _this){
      this._this=_this;
    }
  
    // line 18 "../../../Database_static.ump"
    public void execute() throws DatabaseException{
      // line 6 "../../../Latches_Database_inner.ump"
      env=_this.envHandle.getEnvironmentImpl();
              env.getTriggerLatch().acquireShared();
              //original();
      // END OF UMPLE BEFORE INJECTION
      if (_this.triggerList == null) {
            _this.triggerList=new ArrayList();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 22 "../../../Database_static.ump"
    protected Database _this ;
  // line 23 "../../../Database_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 25 "../../../Database_static.ump"
  // line 18 "../../../Latches_Database_inner.ump"
  public static class Database_acquireTriggerListWriteLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_acquireTriggerListWriteLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 27 "../../../Database_static.ump"
    public  Database_acquireTriggerListWriteLock(Database _this){
      this._this=_this;
    }
  
    // line 30 "../../../Database_static.ump"
    public void execute() throws DatabaseException{
      // line 20 "../../../Latches_Database_inner.ump"
      env=_this.envHandle.getEnvironmentImpl();
              env.getTriggerLatch().acquireExclusive();
              //original();
      // END OF UMPLE BEFORE INJECTION
      if (_this.triggerList == null) {
            _this.triggerList=new ArrayList();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 34 "../../../Database_static.ump"
    protected Database _this ;
  // line 35 "../../../Database_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 37 "../../../Database_static.ump"
  // line 11 "../../../Latches_Database_inner.ump"
  public static class Database_releaseTriggerListWriteLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_releaseTriggerListWriteLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 39 "../../../Database_static.ump"
    public  Database_releaseTriggerListWriteLock(Database _this){
      this._this=_this;
    }
  
    // line 42 "../../../Database_static.ump"
    public void execute() throws DatabaseException{
      if (_this.triggerList.size() == 0) {
            _this.triggerList=null;
          }
      // line 13 "../../../Latches_Database_inner.ump"
      //      original();
              env=_this.envHandle.getEnvironmentImpl();
              env.getTriggerLatch().release();
      // END OF UMPLE AFTER INJECTION
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 46 "../../../Database_static.ump"
    protected Database _this ;
  // line 47 "../../../Database_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../Truncate_Database_inner.ump"
  public static class Database_truncate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_truncate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../Truncate_Database_inner.ump"
    public  Database_truncate(Database _this, Transaction txn, boolean countRecords){
      this._this=_this;
          this.txn=txn;
          this.countRecords=countRecords;
    }
  
    // line 11 "../../../Truncate_Database_inner.ump"
    public int execute() throws DatabaseException{
      _this.checkEnv();
          _this.checkRequiredDbState(_this.OPEN,"Can't call Database.truncate");
          _this.checkWritable("truncate");
          //this.hook39();
          Label39:
          locker=null;
          //this.hook40();
          Label40:
          operationOk=false;
          try {
            locker=LockerFactory.getWritableLocker(_this.envHandle,txn,_this.isTransactional(),true,null);
            _this.acquireTriggerListReadLock();
            //this.hook41();
            Label41:
            count=_this.truncateInternal(locker,countRecords);
            for (int i=0; i < _this.triggerList.size(); i+=1) {
              obj=_this.triggerList.get(i);
              if (obj instanceof SecondaryTrigger) {
                secDb=((SecondaryTrigger)obj).getDb();
                secDb.truncateInternal(locker,false);
              }
            }
            operationOk=true;
            return count;
          }
      finally {
            if (locker != null) {
              locker.operationEnd(operationOk);
            }
            //this.hook42();
            Label42: ;
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 44 "../../../Truncate_Database_inner.ump"
    protected Database _this ;
  // line 45 "../../../Truncate_Database_inner.ump"
    protected Transaction txn ;
  // line 46 "../../../Truncate_Database_inner.ump"
    protected boolean countRecords ;
  // line 47 "../../../Truncate_Database_inner.ump"
    protected Locker locker ;
  // line 48 "../../../Truncate_Database_inner.ump"
    protected boolean triggerLock ;
  // line 49 "../../../Truncate_Database_inner.ump"
    protected boolean operationOk ;
  // line 50 "../../../Truncate_Database_inner.ump"
    protected int count ;
  // line 51 "../../../Truncate_Database_inner.ump"
    protected Object obj ;
  // line 52 "../../../Truncate_Database_inner.ump"
    protected SecondaryDatabase secDb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 21 "../../../Database.ump"
  static DbState OPEN = new DbState("OPEN") ;
// line 23 "../../../Database.ump"
  static DbState CLOSED = new DbState("CLOSED") ;
// line 25 "../../../Database.ump"
  static DbState INVALID = new DbState("INVALID") ;
// line 27 "../../../Database.ump"
  private DbState state ;
// line 31 "../../../Database.ump"
  private DatabaseImpl databaseImpl ;
// line 35 "../../../Database.ump"
  private boolean isWritable ;
// line 39 "../../../Database.ump"
  private TinyHashSet cursors = new TinyHashSet() ;
// line 41 "../../../Database.ump"
  private List triggerList ;
// line 5 "../../../loggingBase_Database.ump"
  private Logger logger ;

  
}