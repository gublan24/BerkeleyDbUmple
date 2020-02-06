/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.PropUtil;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.txn.TxnManager;
import com.sleepycat.je.txn.Txn;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.tree.Key;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.BINReference;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.recovery.RecoveryManager;
import com.sleepycat.je.recovery.RecoveryInfo;
import com.sleepycat.je.recovery.Checkpointer;
import com.sleepycat.je.log.SyncedLogManager;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.log.FileManager;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.cleaner.UtilizationProfile;
import com.sleepycat.je.cleaner.Cleaner;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.LockStats;
import com.sleepycat.je.EnvironmentMutableConfig;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.CheckpointConfig;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.io.PrintStream;
import java.io.IOException;
import java.io.File;

// line 3 "../../../../EnvironmentImpl.ump"
// line 3 "../../../../EnvironmentImpl_static.ump"
public class EnvironmentImpl implements EnvConfigObserver
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a database environment to represent the data in envHome. dbHome. Properties from the je.properties file in that directory are used to initialize the system wide property bag. Properties passed to this method are used to influence the open itself.
   * @param envHomeabsolute path of the database environment home directory
   * @param envConfig
   * @throws DatabaseExceptionon all other failures
   */
  // line 116 "../../../../EnvironmentImpl.ump"
   public  EnvironmentImpl(File envHome, EnvironmentConfig envConfig) throws DatabaseException{
    try {
	    this.envHome = envHome;
	    envState = DbEnvState.INIT;
	    Label323:   ; //this.hook323();
	    configManager = new DbConfigManager(envConfig);
	    configObservers = new ArrayList();
	    addConfigObserver(this);
	    memoryBudget = new MemoryBudget(this, configManager);
	    //this.hook336(envHome);
      Label336:   ;
	    forcedYield = configManager.getBoolean(EnvironmentParams.ENV_FORCED_YIELD);
	    isTransactional = configManager.getBoolean(EnvironmentParams.ENV_INIT_TXN);
	    isNoLocking = !(configManager.getBoolean(EnvironmentParams.ENV_INIT_LOCKING));
	    if (isTransactional && isNoLocking) {
		if (TEST_NO_LOCKING_MODE) {
		    isNoLocking = !isTransactional;
		} else {
		    throw new IllegalArgumentException(
			    "Can't set 'je.env.isNoLocking' and " + "'je.env.isTransactional';");
		}
	    }
	    Label322:   ; //this.hook322();
	    isReadOnly = configManager.getBoolean(EnvironmentParams.ENV_RDONLY);
	    fileManager = new FileManager(this, envHome, isReadOnly);
	    if (!envConfig.getAllowCreate() && !fileManager.filesExist()) {
		throw new DatabaseException("Enviroment creation isn't allowed, " + " but there is no pre-existing "
			+ " environment in " + envHome);
	    }
	    Label321:   ; 	
      logManager = new SyncedLogManager(this, isReadOnly); //this.hook321();
	    inMemoryINs = new INList(this);
	    txnManager = new TxnManager(this);
	    createDaemons();
	    dbMapTree = new DbTree(this);
	    referenceCount = 0;
	    Label320:   ; //this.hook320();
	    if (configManager.getBoolean(EnvironmentParams.ENV_RECOVERY)) {
		try {
		    RecoveryManager recoveryManager = new RecoveryManager(this);
		    lastRecoveryInfo = recoveryManager.recover(isReadOnly);
		} finally {
		    try {
			logManager.flush();
			fileManager.clear();
		    } catch (IOException e) {
			throw new DatabaseException(e.getMessage());
		    }
		}
	    } else {
		isReadOnly = true;
		noComparators = true;
	    }
	    runOrPauseDaemons(configManager);
	    lockTimeout = PropUtil.microsToMillis(configManager.getLong(EnvironmentParams.LOCK_TIMEOUT));
	    txnTimeout = PropUtil.microsToMillis(configManager.getLong(EnvironmentParams.TXN_TIMEOUT));
	  //  this.hook335();
      Label335:   ;
	    open();
	} catch (DatabaseException e) {
	    if (fileManager != null) {
		try {
		    fileManager.close();
		} catch (IOException IOE) {
		}
	    }
	    throw e;
	}
  }


  /**
   * 
   * Respond to config updates.
   */
  // line 189 "../../../../EnvironmentImpl.ump"
   public void envConfigUpdate(DbConfigManager mgr) throws DatabaseException{
    runOrPauseDaemons(mgr);
  }


  /**
   * 
   * Read configurations for daemons, instantiate.
   */
  // line 196 "../../../../EnvironmentImpl.ump"
   private void createDaemons() throws DatabaseException{
    new EnvironmentImpl_createDaemons(this).execute();
  }


  /**
   * 
   * Run or pause daemons, depending on config properties.
   */
  // line 203 "../../../../EnvironmentImpl.ump"
   private void runOrPauseDaemons(DbConfigManager mgr) throws DatabaseException{
    if (!isReadOnly) {
	    Label330:   ; //this.hook330(mgr);
	    Label333:   ; //this.hook333(mgr);
	    Label326:   ; //this.hook326(mgr);
	}
	Label317:   ; //this.hook317(mgr);
  }


  /**
   * 
   * Returns the UtilizationTracker.
   */
  // line 215 "../../../../EnvironmentImpl.ump"
   public UtilizationTracker getUtilizationTracker(){
    return cleaner.getUtilizationTracker();
  }


  /**
   * 
   * Returns the UtilizationProfile.
   */
  // line 222 "../../../../EnvironmentImpl.ump"
   public UtilizationProfile getUtilizationProfile(){
    return cleaner.getUtilizationProfile();
  }


  /**
   * 
   * Log the map tree root and save the LSN.
   */
  // line 229 "../../../../EnvironmentImpl.ump"
   public void logMapTreeRoot() throws DatabaseException{
    try {
				mapTreeRootLsn = logManager.log(dbMapTree);
				} finally {
						logMapTreeRoot_1:   ; //mapTreeRootLatch.release();
				}
  }


  /**
   * 
   * Force a rewrite of the map tree root if required.
   */
  // line 240 "../../../../EnvironmentImpl.ump"
   public void rewriteMapTreeRoot(long cleanerTargetLsn) throws DatabaseException{
    try{
			if (DbLsn.compareTo(cleanerTargetLsn, mapTreeRootLsn) == 0) {
					mapTreeRootLsn = logManager.log(dbMapTree);
			}
		  }
		  finally
			{
		   Label_rewriteMapTreeRoot_1: ;//
			}
  }


  /**
   * 
   * @return the mapping tree root LSN.
   */
  // line 255 "../../../../EnvironmentImpl.ump"
   public long getRootLsn(){
    return mapTreeRootLsn;
  }


  /**
   * 
   * Set the mapping tree from the log. Called during recovery.
   */
  // line 262 "../../../../EnvironmentImpl.ump"
   public void readMapTreeFromLog(long rootLsn) throws DatabaseException{
    dbMapTree = (DbTree) logManager.get(rootLsn);
			dbMapTree.setEnvironmentImpl(this);
			Label324:   ;	//this.hook324(rootLsn);
			try{
				mapTreeRootLsn = rootLsn;
			}
			finally
			{
			Label324_1: ;//
			}
  }


  /**
   * 
   * Not much to do, mark state.
   */
  // line 278 "../../../../EnvironmentImpl.ump"
   public void open(){
    envState = DbEnvState.OPEN;
  }


  /**
   * 
   * Invalidate the environment. Done when a fatal exception (RunRecoveryException) is thrown.
   */
  // line 285 "../../../../EnvironmentImpl.ump"
   public void invalidate(RunRecoveryException e){
    savedInvalidatingException = e;
	envState = DbEnvState.INVALID;
	requestShutdownDaemons();
  }


  /**
   * 
   * @return true if environment is open.
   */
  // line 294 "../../../../EnvironmentImpl.ump"
   public boolean isOpen(){
    return (envState == DbEnvState.OPEN);
  }


  /**
   * 
   * @return true if close has begun, although the state may still be open.
   */
  // line 301 "../../../../EnvironmentImpl.ump"
   public boolean isClosing(){
    return closing;
  }

  // line 305 "../../../../EnvironmentImpl.ump"
   public boolean isClosed(){
    return (envState == DbEnvState.CLOSED);
  }


  /**
   * 
   * When a RunRecoveryException occurs or the environment is closed, further writing can cause log corruption.
   */
  // line 312 "../../../../EnvironmentImpl.ump"
   public boolean mayNotWrite(){
    return (envState == DbEnvState.INVALID) || (envState == DbEnvState.CLOSED);
  }

  // line 316 "../../../../EnvironmentImpl.ump"
   public void checkIfInvalid() throws RunRecoveryException{
    if (envState == DbEnvState.INVALID) {
	    savedInvalidatingException.setAlreadyThrown();
	    throw savedInvalidatingException;
	}
  }

  // line 323 "../../../../EnvironmentImpl.ump"
   public void checkNotClosed() throws DatabaseException{
    if (envState == DbEnvState.CLOSED) {
	    throw new DatabaseException("Attempt to use a Environment that has been closed.");
	}
  }

  // line 329 "../../../../EnvironmentImpl.ump"
   public synchronized  void close() throws DatabaseException{
    if (--referenceCount <= 0) {
	    doClose(true);
	}
  }

  // line 335 "../../../../EnvironmentImpl.ump"
   public synchronized  void close(boolean doCheckpoint) throws DatabaseException{
    if (--referenceCount <= 0) {
	    doClose(doCheckpoint);
	}
  }

  // line 341 "../../../../EnvironmentImpl.ump"
   private void doClose(boolean doCheckpoint) throws DatabaseException{
    StringBuffer errors = new StringBuffer();
			try {
					Label319:   ; //this.hook319();
					try {
				envState.checkState(DbEnvState.VALID_FOR_CLOSE, DbEnvState.CLOSED);
					} catch (DatabaseException DBE) {
				throw DBE;
					}
					requestShutdownDaemons();
					if (doCheckpoint && !isReadOnly && (envState != DbEnvState.INVALID)
						&& logManager.getLastLsnAtRecovery() != fileManager.getLastUsedLsn()) {
				CheckpointConfig ckptConfig = new CheckpointConfig();
				ckptConfig.setForce(true);
				ckptConfig.setMinimizeRecoveryTime(true);
				try {
						invokeCheckpoint(ckptConfig, false, "close");
				} catch (DatabaseException IE) {
						errors.append("\nException performing checkpoint: ");
						errors.append(IE.toString()).append("\n");
				}
					}
					try {
				shutdownDaemons();
					} catch (InterruptedException IE) {
				errors.append("\nException shutting down daemon threads: ");
				errors.append(IE.toString()).append("\n");
					}
					Label318: //this.hook318();
					try {
				logManager.flush();
					} catch (DatabaseException DBE) {
				errors.append("\nException flushing log manager: ");
				errors.append(DBE.toString()).append("\n");
					}
					try {
				fileManager.clear();
					} catch (IOException IOE) {
				errors.append("\nException clearing file manager: ");
				errors.append(IOE.toString()).append("\n");
					} catch (DatabaseException DBE) {
				errors.append("\nException clearing file manager: ");
				errors.append(DBE.toString()).append("\n");
					}
					try {
				fileManager.close();
					} catch (IOException IOE) {
				errors.append("\nException clearing file manager: ");
				errors.append(IOE.toString()).append("\n");
					} catch (DatabaseException DBE) {
				errors.append("\nException clearing file manager: ");
				errors.append(DBE.toString()).append("\n");
					}
					try {
				inMemoryINs.clear();
					} catch (DatabaseException DBE) {
				errors.append("\nException closing file manager: ");
				errors.append(DBE.toString()).append("\n");
					}
					//this.hook337();
				  Label337:   ;
					DbEnvPool.getInstance().remove(envHome);
					Label325:   ; //this.hook325(errors);
			} finally {
					envState = DbEnvState.CLOSED;
			}
			if (errors.length() > 0 && savedInvalidatingException == null) {
					throw new RunRecoveryException(this, errors.toString());
			}
  }

  // line 412 "../../../../EnvironmentImpl.ump"
   public synchronized  void closeAfterRunRecovery() throws DatabaseException{
    try {
	    shutdownDaemons();
	} catch (InterruptedException IE) {
	}
	try {
	    fileManager.clear();
	} catch (Exception e) {
	}
	try {
	    fileManager.close();
	} catch (Exception e) {
	}
	DbEnvPool.getInstance().remove(envHome);
  }

  // line 428 "../../../../EnvironmentImpl.ump"
   public synchronized  void forceClose() throws DatabaseException{
    referenceCount = 1;
	close();
  }

  // line 433 "../../../../EnvironmentImpl.ump"
   public synchronized  void incReferenceCount(){
    referenceCount++;
  }

  // line 437 "../../../../EnvironmentImpl.ump"
   public static  int getThreadLocalReferenceCount(){
    return threadLocalReferenceCount;
  }

  // line 441 "../../../../EnvironmentImpl.ump"
   public static  synchronized  void incThreadLocalReferenceCount(){
    threadLocalReferenceCount++;
  }

  // line 445 "../../../../EnvironmentImpl.ump"
   public static  synchronized  void decThreadLocalReferenceCount(){
    threadLocalReferenceCount--;
  }

  // line 449 "../../../../EnvironmentImpl.ump"
   public static  boolean getNoComparators(){
    return noComparators;
  }


  /**
   * 
   * Invoke a checkpoint programatically. Note that only one checkpoint may run at a time.
   */
  // line 457 "../../../../EnvironmentImpl.ump"
   public boolean invokeCheckpoint(CheckpointConfig config, boolean flushAll, String invokingSource) throws DatabaseException{
    if (checkpointer != null) {
	    checkpointer.doCheckpoint(config, flushAll, invokingSource);
	    return true;
	} else {
	    return false;
	}
  }

  // line 466 "../../../../EnvironmentImpl.ump"
   public int invokeCleaner() throws DatabaseException{
    if (cleaner != null) {
	    return cleaner.doClean(true, false);
	} else {
	    return 0;
	}
  }

  // line 474 "../../../../EnvironmentImpl.ump"
   private void requestShutdownDaemons(){
    closing = true;
			Label331:   ; //this.hook331();
			//this.hook334();
			Label334:   ;
			Label327:   ; //this.hook327();
  }


  /**
   * 
   * Ask all daemon threads to shut down.
   */
  // line 485 "../../../../EnvironmentImpl.ump"
   private void shutdownDaemons() throws InterruptedException{
    shutdownCheckpointer();
  }

  // line 489 "../../../../EnvironmentImpl.ump"
  public void shutdownCheckpointer() throws InterruptedException{
    if (checkpointer != null) {
	    Label328:   ; //this.hook328();
	    checkpointer = null;
	}
	return;
  }

  // line 497 "../../../../EnvironmentImpl.ump"
   public boolean isNoLocking(){
    return isNoLocking;
  }

  // line 501 "../../../../EnvironmentImpl.ump"
   public boolean isTransactional(){
    return isTransactional;
  }

  // line 505 "../../../../EnvironmentImpl.ump"
   public boolean isReadOnly(){
    return isReadOnly;
  }

  // line 510 "../../../../EnvironmentImpl.ump"
   public DatabaseImpl createDb(Locker locker, String databaseName, DatabaseConfig dbConfig, Database databaseHandle) throws DatabaseException{
    return dbMapTree.createDb(locker, databaseName, dbConfig, databaseHandle);
  }


  /**
   * 
   * Get a database object given a database name.
   * @param databaseNametarget database.
   * @return null if database doesn't exist.
   */
  // line 519 "../../../../EnvironmentImpl.ump"
   public DatabaseImpl getDb(Locker locker, String databaseName, Database databaseHandle) throws DatabaseException{
    return dbMapTree.getDb(locker, databaseName, databaseHandle);
  }

  // line 523 "../../../../EnvironmentImpl.ump"
   public List getDbNames() throws DatabaseException{
    return dbMapTree.getDbNames();
  }


  /**
   * 
   * For debugging.
   */
  // line 530 "../../../../EnvironmentImpl.ump"
   public void dumpMapTree() throws DatabaseException{
    dbMapTree.dump();
  }


  /**
   * 
   * Transactional services.
   */
  // line 537 "../../../../EnvironmentImpl.ump"
   public Txn txnBegin(Transaction parent, TransactionConfig txnConfig) throws DatabaseException{
    if (!isTransactional) {
	    throw new DatabaseException("beginTransaction called, " + " but Environment was not opened "
		    + "with transactional cpabilities");
	}
	return txnManager.txnBegin(parent, txnConfig);
  }

  // line 545 "../../../../EnvironmentImpl.ump"
   public LogManager getLogManager(){
    return logManager;
  }

  // line 549 "../../../../EnvironmentImpl.ump"
   public FileManager getFileManager(){
    return fileManager;
  }

  // line 553 "../../../../EnvironmentImpl.ump"
   public DbTree getDbMapTree(){
    return dbMapTree;
  }


  /**
   * 
   * Returns the config manager for the current base configuration. <p> The configuration can change, but changes are made by replacing the config manager object with a enw one. To use a consistent set of properties, call this method once and query the returned manager repeatedly for each property, rather than getting the config manager via this method for each property individually. </p>
   */
  // line 560 "../../../../EnvironmentImpl.ump"
   public DbConfigManager getConfigManager(){
    return configManager;
  }


  /**
   * 
   * Clones the current configuration.
   */
  // line 567 "../../../../EnvironmentImpl.ump"
   public EnvironmentConfig cloneConfig(){
    return DbInternal.cloneConfig(configManager.getEnvironmentConfig());
  }


  /**
   * 
   * Clones the current mutable configuration.
   */
  // line 574 "../../../../EnvironmentImpl.ump"
   public EnvironmentMutableConfig cloneMutableConfig(){
    return DbInternal.cloneMutableConfig(configManager.getEnvironmentConfig());
  }


  /**
   * 
   * Throws an exception if an immutable property is changed.
   */
  // line 581 "../../../../EnvironmentImpl.ump"
   public void checkImmutablePropsForEquality(EnvironmentConfig config) throws IllegalArgumentException{
    DbInternal.checkImmutablePropsForEquality(configManager.getEnvironmentConfig(), config);
  }


  /**
   * 
   * Changes the mutable config properties that are present in the given config, and notifies all config observer.
   */
  // line 588 "../../../../EnvironmentImpl.ump"
   public synchronized  void setMutableConfig(EnvironmentMutableConfig config) throws DatabaseException{
    EnvironmentConfig newConfig = DbInternal.cloneConfig(configManager.getEnvironmentConfig());
	DbInternal.copyMutablePropsTo(config, newConfig);
	configManager = new DbConfigManager(newConfig);
	for (int i = configObservers.size() - 1; i >= 0; i -= 1) {
	    EnvConfigObserver o = (EnvConfigObserver) configObservers.get(i);
	    o.envConfigUpdate(configManager);
	}
  }


  /**
   * 
   * Adds an observer of mutable config changes.
   */
  // line 601 "../../../../EnvironmentImpl.ump"
   public synchronized  void addConfigObserver(EnvConfigObserver o){
    configObservers.add(o);
  }


  /**
   * 
   * Removes an observer of mutable config changes.
   */
  // line 608 "../../../../EnvironmentImpl.ump"
   public synchronized  void removeConfigObserver(EnvConfigObserver o){
    configObservers.remove(o);
  }

  // line 612 "../../../../EnvironmentImpl.ump"
   public INList getInMemoryINs(){
    return inMemoryINs;
  }

  // line 616 "../../../../EnvironmentImpl.ump"
   public TxnManager getTxnManager(){
    return txnManager;
  }

  // line 620 "../../../../EnvironmentImpl.ump"
   public Checkpointer getCheckpointer(){
    return checkpointer;
  }

  // line 624 "../../../../EnvironmentImpl.ump"
   public Cleaner getCleaner(){
    return cleaner;
  }

  // line 628 "../../../../EnvironmentImpl.ump"
   public MemoryBudget getMemoryBudget(){
    return memoryBudget;
  }


  /**
   * 
   * Info about the last recovery
   */
  // line 635 "../../../../EnvironmentImpl.ump"
   public RecoveryInfo getLastRecoveryInfo(){
    return lastRecoveryInfo;
  }


  /**
   * 
   * Get the environment home directory.
   */
  // line 642 "../../../../EnvironmentImpl.ump"
   public File getEnvironmentHome(){
    return envHome;
  }

  // line 646 "../../../../EnvironmentImpl.ump"
   public long getTxnTimeout(){
    return txnTimeout;
  }

  // line 650 "../../../../EnvironmentImpl.ump"
   public long getLockTimeout(){
    return lockTimeout;
  }


  /**
   * 
   * For stress testing. Should only ever be called from an assert.
   */
  // line 657 "../../../../EnvironmentImpl.ump"
   public static  boolean maybeForceYield(){
    if (forcedYield) {
	    Thread.yield();
	}
	return true;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../EnvironmentImpl_static.ump"
  public static class EnvironmentImpl_createDaemons
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImpl_createDaemons()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../EnvironmentImpl_static.ump"
    public  EnvironmentImpl_createDaemons(EnvironmentImpl _this){
      this._this=_this;
    }
  
    // line 9 "../../../../EnvironmentImpl_static.ump"
    public void execute() throws DatabaseException{
      checkpointerWakeupTime=0;
          Label329: //this.hook329();
          _this.checkpointer=new Checkpointer(_this,checkpointerWakeupTime,"Checkpointer");
          Label332: //this.hook332();
          _this.cleaner=new Cleaner(_this,"Cleaner");
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 15 "../../../../EnvironmentImpl_static.ump"
    protected EnvironmentImpl _this ;
  // line 16 "../../../../EnvironmentImpl_static.ump"
    protected long checkpointerWakeupTime ;
  // line 17 "../../../../EnvironmentImpl_static.ump"
    protected long compressorWakeupInterval ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 50 "../../../../EnvironmentImpl.ump"
  private static final boolean TEST_NO_LOCKING_MODE = false ;
// line 52 "../../../../EnvironmentImpl.ump"
  private DbEnvState envState ;
// line 54 "../../../../EnvironmentImpl.ump"
  private boolean closing ;
// line 56 "../../../../EnvironmentImpl.ump"
  private File envHome ;
// line 58 "../../../../EnvironmentImpl.ump"
  private int referenceCount ;
// line 60 "../../../../EnvironmentImpl.ump"
  private boolean isTransactional ;
// line 62 "../../../../EnvironmentImpl.ump"
  private boolean isNoLocking ;
// line 64 "../../../../EnvironmentImpl.ump"
  private boolean isReadOnly ;
// line 66 "../../../../EnvironmentImpl.ump"
  private MemoryBudget memoryBudget ;
// line 68 "../../../../EnvironmentImpl.ump"
  private long lockTimeout ;
// line 70 "../../../../EnvironmentImpl.ump"
  private long txnTimeout ;
// line 72 "../../../../EnvironmentImpl.ump"
  private DbTree dbMapTree ;
// line 74 "../../../../EnvironmentImpl.ump"
  private long mapTreeRootLsn = DbLsn.NULL_LSN ;
// line 76 "../../../../EnvironmentImpl.ump"
  private INList inMemoryINs ;
// line 78 "../../../../EnvironmentImpl.ump"
  private DbConfigManager configManager ;
// line 80 "../../../../EnvironmentImpl.ump"
  private List configObservers ;
// line 82 "../../../../EnvironmentImpl.ump"
  protected LogManager logManager ;
// line 84 "../../../../EnvironmentImpl.ump"
  private FileManager fileManager ;
// line 86 "../../../../EnvironmentImpl.ump"
  private TxnManager txnManager ;
// line 88 "../../../../EnvironmentImpl.ump"
  private Checkpointer checkpointer ;
// line 90 "../../../../EnvironmentImpl.ump"
  private Cleaner cleaner ;
// line 92 "../../../../EnvironmentImpl.ump"
  private RecoveryInfo lastRecoveryInfo ;
// line 94 "../../../../EnvironmentImpl.ump"
  private RunRecoveryException savedInvalidatingException ;
// line 96 "../../../../EnvironmentImpl.ump"
  private static boolean forcedYield = false ;
// line 98 "../../../../EnvironmentImpl.ump"
  private static int threadLocalReferenceCount = 0 ;
// line 103 "../../../../EnvironmentImpl.ump"
  private static boolean noComparators = false ;
// line 105 "../../../../EnvironmentImpl.ump"
  public static  boolean JAVA5_AVAILABLE ;
// line 107 "../../../../EnvironmentImpl.ump"
  private static final String DISABLE_JAVA_ADLER32 = "je.disable.java.adler32" ;

  
}