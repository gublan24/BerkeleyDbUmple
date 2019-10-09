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

// line 3 "../../../Database.ump"
// line 3 "../../../Database_static.ump"
// line 3 "../../../loggingBase_Database.ump"
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
	this.hook45(txn, key);
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
	this.hook47(txn, key);
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
	this.hook48(txn, key, lockMode);
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
	this.hook49(txn, key, data, lockMode);
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
	this.hook50(txn, key, data);
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
	this.hook51(txn, key, data);
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
	this.hook52(txn, key, data);
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
	this.hook55();
	PreloadConfig config = new PreloadConfig();
	config.setMaxBytes(maxBytes);
	databaseImpl.preload(config);
  }

  // line 395 "../../../Database.ump"
   public void preload(long maxBytes, long maxMillisecs) throws DatabaseException{
    checkEnv();
	checkRequiredDbState(OPEN, "Can't call Database.preload");
	this.hook56();
	PreloadConfig config = new PreloadConfig();
	config.setMaxBytes(maxBytes);
	config.setMaxMillisecs(maxMillisecs);
	databaseImpl.preload(config);
  }

  // line 405 "../../../Database.ump"
   public PreloadStats preload(PreloadConfig config) throws DatabaseException{
    checkEnv();
	checkRequiredDbState(OPEN, "Can't call Database.preload");
	this.hook57();
	return databaseImpl.preload(config);
  }

  // line 412 "../../../Database.ump"
   public String getDatabaseName() throws DatabaseException{
    checkEnv();
	if (databaseImpl != null) {
	    return databaseImpl.getName();
	} else {
	    return null;
	}
  }

  // line 421 "../../../Database.ump"
  public String getDebugName(){
    if (databaseImpl != null) {
	    return databaseImpl.getDebugName();
	} else {
	    return null;
	}
  }

  // line 429 "../../../Database.ump"
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
  // line 441 "../../../Database.ump"
  public boolean isTransactional() throws DatabaseException{
    return databaseImpl.isTransactional();
  }

  // line 445 "../../../Database.ump"
   public Environment getEnvironment() throws DatabaseException{
    return envHandle;
  }

  // line 449 "../../../Database.ump"
   public List getSecondaryDatabases() throws DatabaseException{
    List list = new ArrayList();
	if (hasTriggers()) {
	    acquireTriggerListReadLock();
	    this.hook53(list);
	} else {
	}
	return list;
  }


  /**
   * 
   * @return true if the Database was opened read/write.
   */
  // line 462 "../../../Database.ump"
  public boolean isWritable(){
    return isWritable;
  }


  /**
   * 
   * Return the databaseImpl object instance.
   */
  // line 469 "../../../Database.ump"
  public DatabaseImpl getDatabaseImpl(){
    return databaseImpl;
  }


  /**
   * 
   * @throws DatabaseException if the Database state is not this value.
   */
  // line 491 "../../../Database.ump"
  public void checkRequiredDbState(DbState required, String msg) throws DatabaseException{
    if (state != required) {
	    throw new DatabaseException(msg + " Database state can't be " + state + " must be " + required);
	}
  }


  /**
   * 
   * @throws DatabaseException if the Database state is this value.
   */
  // line 500 "../../../Database.ump"
  public void checkProhibitedDbState(DbState prohibited, String msg) throws DatabaseException{
    if (state == prohibited) {
	    throw new DatabaseException(msg + " Database state must not be " + prohibited);
	}
  }


  /**
   * 
   * @throws RunRecoveryException if the underlying environment isinvalid
   */
  // line 509 "../../../Database.ump"
  public void checkEnv() throws RunRecoveryException{
    EnvironmentImpl env = envHandle.getEnvironmentImpl();
	if (env != null) {
	    env.checkIfInvalid();
	}
  }


  /**
   * 
   * Check that write operations aren't used on a readonly Database.
   */
  // line 530 "../../../Database.ump"
   private void checkWritable(String operation) throws DatabaseException{
    if (!isWritable) {
	    throw new DatabaseException("Database is Read Only: " + operation);
	}
  }


  /**
   * 
   * Returns whether any triggers are currently associated with this primary. Note that an update of the trigger list may be in progress and this method does not wait for that update to be completed.
   */
  // line 539 "../../../Database.ump"
  public boolean hasTriggers(){
    return triggerList != null;
  }


  /**
   * 
   * Gets a read-lock on the list of triggers.  releaseTriggerListReadLock() must be called to release the lock.  Called by all primary put and delete operations.
   */
  // line 546 "../../../Database.ump"
   private void acquireTriggerListReadLock() throws DatabaseException{
    new Database_acquireTriggerListReadLock(this).execute();
  }


  /**
   * 
   * Gets a write lock on the list of triggers.  An empty list is created if necessary, so null is never returned.  releaseTriggerListWriteLock() must always be called to release the lock.
   */
  // line 553 "../../../Database.ump"
   private void acquireTriggerListWriteLock() throws DatabaseException{
    new Database_acquireTriggerListWriteLock(this).execute();
  }


  /**
   * 
   * Releases a lock acquired by calling acquireTriggerListWriteLock().  If the list is now empty then it is set to null, that is, hasTriggers() will subsequently return false.
   */
  // line 560 "../../../Database.ump"
   private void releaseTriggerListWriteLock() throws DatabaseException{
    new Database_releaseTriggerListWriteLock(this).execute();
  }


  /**
   * 
   * Adds a given trigger to the list of triggers.  Called while opening a SecondaryDatabase.
   * @param insertAtFront true to insert at the front, or false to append.
   */
  // line 568 "../../../Database.ump"
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
  // line 585 "../../../Database.ump"
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
  // line 598 "../../../Database.ump"
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
  // line 619 "../../../Database.ump"
  public void notifyTriggers(Locker locker, DatabaseEntry priKey, DatabaseEntry oldData, DatabaseEntry newData) throws DatabaseException{
    acquireTriggerListReadLock();
	this.hook54(locker, priKey, oldData, newData);
  }

  // line 624 "../../../Database.ump"
   protected void hook44() throws DatabaseException{
    
  }

  // line 627 "../../../Database.ump"
   protected void hook45(Transaction txn, DatabaseEntry key) throws DatabaseException{
    
  }

  // line 630 "../../../Database.ump"
   protected void hook46(Transaction txn, CursorConfig cursorConfig) throws DatabaseException{
    
  }

  // line 633 "../../../Database.ump"
   protected void hook47(Transaction txn, DatabaseEntry key) throws DatabaseException{
    
  }

  // line 636 "../../../Database.ump"
   protected void hook48(Transaction txn, DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    
  }

  // line 640 "../../../Database.ump"
   protected void hook49(Transaction txn, DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    
  }

  // line 643 "../../../Database.ump"
   protected void hook50(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    
  }

  // line 646 "../../../Database.ump"
   protected void hook51(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    
  }

  // line 649 "../../../Database.ump"
   protected void hook52(Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    
  }

  // line 652 "../../../Database.ump"
   protected void hook53(List list) throws DatabaseException{
    for (int i = 0; i < triggerList.size(); i += 1) {
	    Object obj = triggerList.get(i);
	    if (obj instanceof SecondaryTrigger) {
		list.add(((SecondaryTrigger) obj).getDb());
	    }
	}
  }

  // line 662 "../../../Database.ump"
   protected void hook54(Locker locker, DatabaseEntry priKey, DatabaseEntry oldData, DatabaseEntry newData) throws DatabaseException{
    for (int i = 0; i < triggerList.size(); i += 1) {
	    DatabaseTrigger trigger = (DatabaseTrigger) triggerList.get(i);
	    trigger.databaseUpdated(this, locker, priKey, oldData, newData);
	}
  }

  // line 669 "../../../Database.ump"
   protected void hook55() throws DatabaseException{
    
  }

  // line 672 "../../../Database.ump"
   protected void hook56() throws DatabaseException{
    
  }

  // line 675 "../../../Database.ump"
   protected void hook57() throws DatabaseException{
    
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
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 46 "../../../Database_static.ump"
    protected Database _this ;
  // line 47 "../../../Database_static.ump"
    protected EnvironmentImpl env ;
  
    
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

// line 118 "../../../Database.ump"
  public synchronized void close () throws DatabaseException 
  {
    StringBuffer errors = null;
	checkEnv();
	checkProhibitedDbState(CLOSED, "Can't close Database:");
	this.hook44();
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

// line 170 "../../../Database.ump"
  public synchronized Cursor openCursor (Transaction txn, CursorConfig cursorConfig) throws DatabaseException 
  {
    checkEnv();
	checkRequiredDbState(OPEN, "Can't open a cursor");
	CursorConfig useConfig = (cursorConfig == null) ? CursorConfig.DEFAULT : cursorConfig;
	if (useConfig.getReadUncommitted() && useConfig.getReadCommitted()) {
	    throw new IllegalArgumentException("Only one may be specified: ReadCommitted or ReadUncommitted");
	}
	this.hook46(txn, cursorConfig);
	Cursor ret = newDbcInstance(txn, useConfig);
	return ret;
  }

// line 479 "../../../Database.ump"
  synchronized void removeCursor (Cursor dbc) 
  {
    cursors.remove(dbc);
  }

// line 483 "../../../Database.ump"
  synchronized void addCursor (Cursor dbc) 
  {
    cursors.add(dbc);
  }

// line 518 "../../../Database.ump"
  synchronized void invalidate () 
  {
    state = INVALID;
	envHandle.removeReferringHandle(this);
	if (databaseImpl != null) {
	    databaseImpl.removeReferringHandle(this);
	}
  }
// line 5 "../../../loggingBase_Database.ump"
  private Logger logger ;

  
}