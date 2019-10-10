/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.PropUtil;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.DaemonRunner;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.txn.LockResult;
import com.sleepycat.je.txn.LockGrantType;
import com.sleepycat.je.txn.BasicLocker;
import com.sleepycat.je.tree.TreeLocation;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.log.FileManager;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.EnvConfigObserver;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Set;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import java.util.Arrays;
import java.io.IOException;
import com.sleepycat.je.dbi.*;
import com.sleepycat.je.utilint.*;

/**
 * Original file:/home/abdulaziz/Desktop/BerkeleyDb/ALL_FEATURE/features/DeleteOp/com/sleepycat/je/cleaner/Cleaner.java
 * namespace com.sleepycat.je.cleaner;
 */
// line 3 "../../../../Cleaner.ump"
// line 3 "../../../../Cleaner_static.ump"
// line 3 "../../../../EnvironmentLocking_Cleaner.ump"
// line 3 "../../../../CriticalEviction_Cleaner.ump"
// line 3 "../../../../CriticalEviction_Cleaner_inner.ump"
// line 3 "../../../../Evictor_Cleaner.ump"
// line 3 "../../../../DeleteOp_Cleaner.ump"
// line 3 "../../../../DeleteOp_Cleaner_inner.ump"
// line 3 "../../../../CleanerDaemon_Cleaner.ump"
public class Cleaner implements EnvConfigObserver,DaemonRunner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cleaner Attributes
  private int nCleanerRuns;
  private long lockTimeout;
  private int readBufferSize;
  private int nDeadlockRetries;
  private boolean expunge;
  private boolean clusterResident;
  private boolean clusterAll;
  private int maxBatchFiles;
  private long cleanerBytesInterval;
  private boolean trackDetail;
  private Set mustBeCleanedFiles;
  private Set lowUtilizationFiles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner(long aLockTimeout, int aReadBufferSize, int aNDeadlockRetries, boolean aExpunge, boolean aClusterResident, boolean aClusterAll, int aMaxBatchFiles, long aCleanerBytesInterval, boolean aTrackDetail)
  {
    nCleanerRuns = 0;
    lockTimeout = aLockTimeout;
    readBufferSize = aReadBufferSize;
    nDeadlockRetries = aNDeadlockRetries;
    expunge = aExpunge;
    clusterResident = aClusterResident;
    clusterAll = aClusterAll;
    maxBatchFiles = aMaxBatchFiles;
    cleanerBytesInterval = aCleanerBytesInterval;
    trackDetail = aTrackDetail;
    mustBeCleanedFiles = Collections.EMPTY_SET;
    lowUtilizationFiles = Collections.EMPTY_SET;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNCleanerRuns(int aNCleanerRuns)
  {
    boolean wasSet = false;
    nCleanerRuns = aNCleanerRuns;
    wasSet = true;
    return wasSet;
  }

  public boolean setLockTimeout(long aLockTimeout)
  {
    boolean wasSet = false;
    lockTimeout = aLockTimeout;
    wasSet = true;
    return wasSet;
  }

  public boolean setReadBufferSize(int aReadBufferSize)
  {
    boolean wasSet = false;
    readBufferSize = aReadBufferSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setNDeadlockRetries(int aNDeadlockRetries)
  {
    boolean wasSet = false;
    nDeadlockRetries = aNDeadlockRetries;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpunge(boolean aExpunge)
  {
    boolean wasSet = false;
    expunge = aExpunge;
    wasSet = true;
    return wasSet;
  }

  public boolean setClusterResident(boolean aClusterResident)
  {
    boolean wasSet = false;
    clusterResident = aClusterResident;
    wasSet = true;
    return wasSet;
  }

  public boolean setClusterAll(boolean aClusterAll)
  {
    boolean wasSet = false;
    clusterAll = aClusterAll;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxBatchFiles(int aMaxBatchFiles)
  {
    boolean wasSet = false;
    maxBatchFiles = aMaxBatchFiles;
    wasSet = true;
    return wasSet;
  }

  public boolean setCleanerBytesInterval(long aCleanerBytesInterval)
  {
    boolean wasSet = false;
    cleanerBytesInterval = aCleanerBytesInterval;
    wasSet = true;
    return wasSet;
  }

  public boolean setTrackDetail(boolean aTrackDetail)
  {
    boolean wasSet = false;
    trackDetail = aTrackDetail;
    wasSet = true;
    return wasSet;
  }

  public boolean setMustBeCleanedFiles(Set aMustBeCleanedFiles)
  {
    boolean wasSet = false;
    mustBeCleanedFiles = aMustBeCleanedFiles;
    wasSet = true;
    return wasSet;
  }

  public boolean setLowUtilizationFiles(Set aLowUtilizationFiles)
  {
    boolean wasSet = false;
    lowUtilizationFiles = aLowUtilizationFiles;
    wasSet = true;
    return wasSet;
  }

  public int getNCleanerRuns()
  {
    return nCleanerRuns;
  }

  public long getLockTimeout()
  {
    return lockTimeout;
  }

  public int getReadBufferSize()
  {
    return readBufferSize;
  }

  public int getNDeadlockRetries()
  {
    return nDeadlockRetries;
  }

  public boolean getExpunge()
  {
    return expunge;
  }

  public boolean getClusterResident()
  {
    return clusterResident;
  }

  public boolean getClusterAll()
  {
    return clusterAll;
  }

  public int getMaxBatchFiles()
  {
    return maxBatchFiles;
  }

  public long getCleanerBytesInterval()
  {
    return cleanerBytesInterval;
  }

  public boolean getTrackDetail()
  {
    return trackDetail;
  }

  /**
   * 
   * All files that are to-be-cleaning or being-cleaned. Used to perform proactive migration. Is read-only after assignment, so no synchronization is needed.
   */
  public Set getMustBeCleanedFiles()
  {
    return mustBeCleanedFiles;
  }

  /**
   * 
   * All files that are below the minUtilization threshold. Used to perform clustering migration. Is read-only after assignment, so no synchronization is needed.
   */
  public Set getLowUtilizationFiles()
  {
    return lowUtilizationFiles;
  }

  public void delete()
  {}

  // line 102 "../../../../Cleaner.ump"
   public  Cleaner(EnvironmentImpl env, String name) throws DatabaseException{
    this.env = env;
	this.name = name;
	tracker = new UtilizationTracker(env, this);
	profile = new UtilizationProfile(env, tracker);
	fileSelector = new FileSelector();
	threads = new FileProcessor[0];
	deleteFileLock = new Object();
	trackDetail = env.getConfigManager().getBoolean(EnvironmentParams.CLEANER_TRACK_DETAIL);
	envConfigUpdate(env.getConfigManager());
	env.addConfigObserver(this);
  }


  /**
   * 
   * Process notifications of mutable property changes.
   */
  // line 118 "../../../../Cleaner.ump"
   public void envConfigUpdate(DbConfigManager cm) throws DatabaseException{
    lockTimeout = PropUtil.microsToMillis(cm.getLong(EnvironmentParams.CLEANER_LOCK_TIMEOUT));
	readBufferSize = cm.getInt(EnvironmentParams.CLEANER_READ_SIZE);
	if (readBufferSize <= 0) {
	    readBufferSize = cm.getInt(EnvironmentParams.LOG_ITERATOR_READ_SIZE);
	}
	this.hook94(cm);
	nDeadlockRetries = cm.getInt(EnvironmentParams.CLEANER_DEADLOCK_RETRY);
	expunge = cm.getBoolean(EnvironmentParams.CLEANER_REMOVE);
	clusterResident = cm.getBoolean(EnvironmentParams.CLEANER_CLUSTER);
	clusterAll = cm.getBoolean(EnvironmentParams.CLEANER_CLUSTER_ALL);
	maxBatchFiles = cm.getInt(EnvironmentParams.CLEANER_MAX_BATCH_FILES);
	this.hook90();
	if (clusterResident && clusterAll) {
	    throw new IllegalArgumentException("Both " + EnvironmentParams.CLEANER_CLUSTER + " and "
		    + EnvironmentParams.CLEANER_CLUSTER_ALL + " may not be set to true.");
	}
	int nThreads = cm.getInt(EnvironmentParams.CLEANER_THREADS);
	assert nThreads > 0;
	if (nThreads != threads.length) {
	    for (int i = nThreads; i < threads.length; i += 1) {
		if (threads[i] != null) {
		    threads[i].shutdown();
		    threads[i] = null;
		}
	    }
	    FileProcessor[] newThreads = new FileProcessor[nThreads];
	    for (int i = 0; i < nThreads && i < threads.length; i += 1) {
		newThreads[i] = threads[i];
	    }
	    threads = newThreads;
	    for (int i = 0; i < nThreads; i += 1) {
		if (threads[i] == null) {
		    threads[i] = new FileProcessor(name + '-' + (i + 1), env, this, profile, fileSelector);
		}
	    }
	}
	cleanerBytesInterval = cm.getLong(EnvironmentParams.CLEANER_BYTES_INTERVAL);
	if (cleanerBytesInterval == 0) {
	    cleanerBytesInterval = cm.getLong(EnvironmentParams.LOG_FILE_MAX) / 4;
	}
  }

  // line 161 "../../../../Cleaner.ump"
   public UtilizationTracker getUtilizationTracker(){
    return tracker;
  }

  // line 165 "../../../../Cleaner.ump"
   public UtilizationProfile getUtilizationProfile(){
    return profile;
  }

  // line 169 "../../../../Cleaner.ump"
   public void wakeup(){
    for (int i = 0; i < threads.length; i += 1) {
	    if (threads[i] != null) {
		threads[i].wakeup();
	    }
	}
  }

  // line 177 "../../../../Cleaner.ump"
   private boolean areThreadsRunning(){
    for (int i = 0; i < threads.length; i += 1) {
	    if (threads[i] != null) {
		return threads[i].isRunning();
	    }
	}
	return false;
  }


  /**
   * 
   * Cleans selected files and returns the number of files cleaned. This method is not invoked by a deamon thread, it is programatically.
   * @param cleanMultipleFilesis true to clean until we're under budget, or false to cleanat most one file.
   * @param forceCleaningis true to clean even if we're not under the utilizationthreshold.
   * @return the number of files cleaned, not including files cleanedunsuccessfully.
   */
  // line 192 "../../../../Cleaner.ump"
   public int doClean(boolean cleanMultipleFiles, boolean forceCleaning) throws DatabaseException{
    FileProcessor processor = new FileProcessor("", env, this, profile, fileSelector);
	return processor.doClean(false, cleanMultipleFiles, forceCleaning);
  }


  /**
   * 
   * Deletes all files that are safe-to-delete, if an exclusive lock on the environment can be obtained.
   */
  // line 200 "../../../../Cleaner.ump"
  public void deleteSafeToDeleteFiles() throws DatabaseException{
    try {
	    synchronized (deleteFileLock) {
		Set safeFiles = fileSelector.copySafeToDeleteFiles();
		if (safeFiles == null) {
		    return;
		}
		env.checkIfInvalid();
		if (env.mayNotWrite()) {
		    return;
		}
		//this.hook115(safeFiles);
Label115:
if (!env.getFileManager().lockEnvironment(false, true)) {
	    //bellow label introduced in EnvironmentLocking_Cleaner.ump
      Label87:
	    throw new ReturnVoid();
	    }

for (Iterator i = safeFiles.iterator(); i.hasNext();) {
	    Long fileNum = (Long) i.next();
	    long fileNumValue = fileNum.longValue();
	    boolean deleted = false;
	    try {
		if (expunge) {
		    env.getFileManager().deleteFile(fileNumValue);
		} else {
		    env.getFileManager().renameFile(fileNumValue, FileManager.DEL_SUFFIX);
		}
		deleted = true;
	    } catch (DatabaseException e) {
		traceFileNotDeleted(e, fileNumValue);
	    } catch (IOException e) {
		traceFileNotDeleted(e, fileNumValue);
	    }
	    if (deleted) {
		this.hook88(fileNumValue);
		try {
		    profile.removeFile(fileNum);
		} finally {
		    fileSelector.removeDeletedFile(fileNum);
		}
	    }
	    Label96:
	}
//
Label_115_1:
env.getFileManager().releaseExclusiveLock();

	    }
	} catch (ReturnVoid r) {
	    return;
	}
  }

  // line 247 "../../../../Cleaner.ump"
   private void traceFileNotDeleted(Exception e, long fileNum){
    
  }


  /**
   * 
   * Returns a copy of the cleaned and processed files at the time a checkpoint starts. <p> If non-null is returned, the checkpoint should flush an extra level, and addCheckpointedFiles() should be called when the checkpoint is complete. </p>
   */
  // line 253 "../../../../Cleaner.ump"
   public Set[] getFilesAtCheckpointStart() throws DatabaseException{
    processPending();
	return fileSelector.getFilesAtCheckpointStart();
  }


  /**
   * 
   * When a checkpoint is complete, update the files that were returned at the beginning of the checkpoint.
   */
  // line 261 "../../../../Cleaner.ump"
   public void updateFilesAtCheckpointEnd(Set [] files) throws DatabaseException{
    fileSelector.updateFilesAtCheckpointEnd(files);
	deleteSafeToDeleteFiles();
  }


  /**
   * 
   * Update the lowUtilizationFiles and mustBeCleanedFiles fields with new read-only collections, and update the backlog file count.
   */
  // line 269 "../../../../Cleaner.ump"
   public void updateReadOnlyFileCollections(){
    mustBeCleanedFiles = fileSelector.getMustBeCleanedFiles();
	lowUtilizationFiles = fileSelector.getLowUtilizationFiles();
  }


  /**
   * 
   * If any LNs are pending, process them. This method should be called often enough to prevent the pending LN set from growing too large.
   */
  // line 277 "../../../../Cleaner.ump"
  public void processPending() throws DatabaseException{
    new Cleaner_processPending(this).execute();
  }


  /**
   * 
   * Processes a pending LN, getting the lock first to ensure that the overhead of retries is mimimal.
   */
  // line 285 "../../../../Cleaner.ump"
   private void processPendingLN(LN ln, DatabaseImpl db, byte [] key, byte [] dupKey, TreeLocation location) throws DatabaseException{
    boolean parentFound = false;
	boolean processedHere = true;
	boolean lockDenied = false;
	boolean obsolete = false;
	boolean completed = false;
	BasicLocker locker = null;
	BIN bin = null;
	DIN parentDIN = null;
	try {
	    this.hook97();
	    boolean c = db == null;
	    //c = this.hook112(db, c);
      Label112:
c = c || db.isDeleted();
			//return original(db, c);

	    if (c) {
		//this.hook113(db);
    Label113:
addPendingDB(db);
			//original(db);

		this.hook98();
		obsolete = true;
		completed = true;
		return;
	    }
	    Tree tree = db.getTree();
	    assert tree != null;
	    locker = new BasicLocker(env);
	    LockResult lockRet = locker.nonBlockingLock(ln.getNodeId(), LockType.READ, db);
	    if (lockRet.getLockGrant() == LockGrantType.DENIED) {
		this.hook99();
		lockDenied = true;
		completed = true;
		return;
	    }
	    parentFound = tree.getParentBINForChildLN(location, key, dupKey, ln, false, true, true, UPDATE_GENERATION);
	    bin = location.bin;
	    int index = location.index;
	    if (!parentFound) {
		this.hook100();
		obsolete = true;
		completed = true;
		return;
	    }
	    if (ln.containsDuplicates()) {
		parentDIN = (DIN) bin.fetchTarget(index);
		parentDIN.latch(UPDATE_GENERATION);
		ChildReference dclRef = parentDIN.getDupCountLNRef();
		processedHere = false;
		migrateDupCountLN(db, dclRef.getLsn(), parentDIN, dclRef, true, true, ln.getNodeId(), CLEAN_PENDING_LN);
	    } else {
		processedHere = false;
		migrateLN(db, bin.getLsn(index), bin, index, true, true, ln.getNodeId(), CLEAN_PENDING_LN);
	    }
	    completed = true;
	} catch (DatabaseException DBE) {
	    DBE.printStackTrace();
	    this.hook89(DBE);
	    throw DBE;
	} finally {
	    this.hook95(bin, parentDIN);
	    if (locker != null) {
		locker.operationEnd();
	    }
	    if (processedHere) {
		if (completed && !lockDenied) {
		    fileSelector.removePendingLN(ln.getNodeId());
		}
		this.hook91(ln, obsolete, completed);
	    }
	}
  }


  /**
   * 
   * This method should be called just before logging a root DIN. The DupCountLN will be migrated if the MIGRATE flag is set, or if it is in a file to be cleaned, or if the LN qualifies according to the rules for cluster and clusterAll. <p> On return this method guarantees that the MIGRATE flag will not be set on the child entry. If this method is *not* called before logging a root DIN, then the addPendingDupCountLN method must be called. </p>
   * @param dinis the latched DIN. The latch will not be released by thismethod.
   * @param dclRefis the reference to the DupCountLN.
   * @param proactiveMigrationperform proactive migration if needed; this is false during asplit, to reduce the delay in the user operation.
   */
  // line 405 "../../../../Cleaner.ump"
   public void lazyMigrateDupCountLN(DIN din, ChildReference dclRef, boolean proactiveMigration) throws DatabaseException{
    DatabaseImpl db = din.getDatabase();
	boolean migrateFlag = dclRef.getMigrate();
	boolean isResident = (dclRef.getTarget() != null);
	boolean isBinInDupDb = false;
	long childLsn = dclRef.getLsn();
	if (shouldMigrateLN(migrateFlag, isResident, proactiveMigration, isBinInDupDb, childLsn)) {
	    migrateDupCountLN(db, childLsn, din, dclRef, migrateFlag, false, 0, CLEAN_MIGRATE_LN);
	}
  }


  /**
   * 
   * Returns whether an LN entry should be migrated. Updates stats.
   * @param migrateFlagis whether the MIGRATE flag is set on the entry.
   * @param isResidentis whether the LN is currently resident.
   * @param proactiveMigrationperform proactive migration if needed; this is false during asplit, to reduce the delay in the user operation.
   * @param isBinInDupDbis whether this is a BIN entry in a database with duplicatesenabled.
   * @param childLsnis the LSN of the LN.
   * @return whether to migrate the LN.
   */
  // line 426 "../../../../Cleaner.ump"
   private boolean shouldMigrateLN(boolean migrateFlag, boolean isResident, boolean proactiveMigration, boolean isBinInDupDb, long childLsn){
    boolean doMigration = false;
	if (migrateFlag) {
	    doMigration = true;
	    this.hook101();
	} else if (!proactiveMigration || isBinInDupDb || env.isClosing()) {
	} else {
	    Long fileNum = new Long(DbLsn.getFileNumber(childLsn));
	    if ((PROACTIVE_MIGRATION || isResident) && mustBeCleanedFiles.contains(fileNum)) {
		doMigration = true;
		this.hook102();
	    } else if ((clusterAll || (clusterResident && isResident)) && lowUtilizationFiles.contains(fileNum)) {
		doMigration = true;
		this.hook103();
	    }
	}
	return doMigration;
  }


  /**
   * 
   * Migrate an LN in the given BIN entry, if it is not obsolete. The BIN is latched on entry to this method and is left latched when it returns.
   */
  // line 449 "../../../../Cleaner.ump"
   private void migrateLN(DatabaseImpl db, long lsn, BIN bin, int index, boolean wasCleaned, boolean isPending, long lockedPendingNodeId, String cleanAction) throws DatabaseException{
    boolean obsolete = false;
	boolean migrated = false;
	boolean lockDenied = false;
	boolean completed = false;
	boolean clearTarget = false;
	BasicLocker locker = null;
	LN ln = null;
	try {
	    if (!bin.isEntryKnownDeleted(index)) {
		ln = (LN) bin.getTarget(index);
		if (ln == null) {
		    ln = (LN) bin.fetchTarget(index);
		    clearTarget = !db.getId().equals(DbTree.ID_DB_ID);
		}
	    }
	    if (ln == null) {
		this.hook105(wasCleaned);
		obsolete = true;
		completed = true;
		return;
	    }
	    if (lockedPendingNodeId != ln.getNodeId()) {
		locker = new BasicLocker(env);
		LockResult lockRet = locker.nonBlockingLock(ln.getNodeId(), LockType.READ, db);
		if (lockRet.getLockGrant() == LockGrantType.DENIED) {
		    this.hook106(wasCleaned);
		    lockDenied = true;
		    completed = true;
		    return;
		}
	    }
	    if (ln.isDeleted()) {
		bin.setKnownDeletedLeaveTarget(index);
		this.hook107(wasCleaned);
		obsolete = true;
		completed = true;
		return;
	    }
	    if (bin.getMigrate(index)) {
		Long fileNum = new Long(DbLsn.getFileNumber(lsn));
		if (!fileSelector.isFileCleaningInProgress(fileNum)) {
		    obsolete = true;
		    completed = true;
		    this.hook108(wasCleaned);
		    return;
		}
	    }
	    byte[] key = getLNMainKey(bin, index);
	    long newLNLsn = ln.log(env, db.getId(), key, lsn, locker);
	    bin.updateEntry(index, newLNLsn);
	    this.hook104();
	    migrated = true;
	    completed = true;
	    return;
	} finally {
	    if (isPending) {
		if (completed && !lockDenied) {
		    fileSelector.removePendingLN(lockedPendingNodeId);
		}
	    } else {
		if (bin.getMigrate(index) && (!completed || lockDenied)) {
		    byte[] key = getLNMainKey(bin, index);
		    byte[] dupKey = getLNDupKey(bin, index, ln);
		    fileSelector.addPendingLN(ln, db.getId(), key, dupKey);
		    if (!areThreadsRunning()) {
			env.getUtilizationTracker().activateCleaner();
		    }
		    clearTarget = false;
		}
	    }
	    bin.setMigrate(index, false);
	    if (clearTarget) {
		bin.updateEntry(index, null);
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	    this.hook92(lsn, cleanAction, obsolete, migrated, completed, ln);
	}
  }


  /**
   * 
   * Migrate the DupCountLN for the given DIN. The DIN is latched on entry to this method and is left latched when it returns.
   */
  // line 535 "../../../../Cleaner.ump"
   private void migrateDupCountLN(DatabaseImpl db, long lsn, DIN parentDIN, ChildReference dclRef, boolean wasCleaned, boolean isPending, long lockedPendingNodeId, String cleanAction) throws DatabaseException{
    boolean obsolete = false;
	boolean migrated = false;
	boolean lockDenied = false;
	boolean completed = false;
	boolean clearTarget = false;
	BasicLocker locker = null;
	LN ln = null;
	try {
	    ln = (LN) dclRef.getTarget();
	    if (ln == null) {
		ln = (LN) dclRef.fetchTarget(db, parentDIN);
		assert ln != null;
		clearTarget = !db.getId().equals(DbTree.ID_DB_ID);
	    }
	    if (lockedPendingNodeId != ln.getNodeId()) {
		locker = new BasicLocker(env);
		LockResult lockRet = locker.nonBlockingLock(ln.getNodeId(), LockType.READ, db);
		if (lockRet.getLockGrant() == LockGrantType.DENIED) {
		    this.hook110(wasCleaned);
		    lockDenied = true;
		    completed = true;
		    return;
		}
	    }
	    Long fileNum = new Long(DbLsn.getFileNumber(lsn));
	    if (!fileSelector.isFileCleaningInProgress(fileNum)) {
		obsolete = true;
		completed = true;
		this.hook111(wasCleaned);
		return;
	    }
	    byte[] key = parentDIN.getDupKey();
	    long newLNLsn = ln.log(env, db.getId(), key, lsn, locker);
	    parentDIN.updateDupCountLNRef(newLNLsn);
	    this.hook109();
	    migrated = true;
	    completed = true;
	    return;
	} finally {
	    if (isPending) {
		if (completed && !lockDenied) {
		    fileSelector.removePendingLN(lockedPendingNodeId);
		}
	    } else {
		if (dclRef.getMigrate() && (!completed || lockDenied)) {
		    byte[] key = parentDIN.getDupKey();
		    byte[] dupKey = null;
		    fileSelector.addPendingLN(ln, db.getId(), key, dupKey);
		    if (!areThreadsRunning()) {
			env.getUtilizationTracker().activateCleaner();
		    }
		    clearTarget = false;
		}
	    }
	    dclRef.setMigrate(false);
	    if (clearTarget) {
		parentDIN.updateDupCountLN(null);
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	    this.hook93(lsn, cleanAction, obsolete, migrated, completed, ln);
	}
  }


  /**
   * 
   * Returns the main key for a given BIN entry.
   */
  // line 604 "../../../../Cleaner.ump"
   private byte[] getLNMainKey(BIN bin, int index) throws DatabaseException{
    if (bin.containsDuplicates()) {
	    return bin.getDupKey();
	} else {
	    return bin.getKey(index);
	}
  }


  /**
   * 
   * Returns the duplicate key for a given BIN entry.
   */
  // line 615 "../../../../Cleaner.ump"
   private byte[] getLNDupKey(BIN bin, int index, LN ln) throws DatabaseException{
    DatabaseImpl db = bin.getDatabase();
	if (!db.getSortedDuplicates() || ln.containsDuplicates()) {
	    return null;
	} else if (bin.containsDuplicates()) {
	    return bin.getKey(index);
	} else {
	    return ln.getData();
	}
  }

  // line 626 "../../../../Cleaner.ump"
   protected void hook88(long fileNumValue) throws DatabaseException{
    
  }

  // line 629 "../../../../Cleaner.ump"
   protected void hook89(DatabaseException DBE) throws DatabaseException{
    
  }

  // line 632 "../../../../Cleaner.ump"
   protected void hook90() throws DatabaseException{
    
  }

  // line 635 "../../../../Cleaner.ump"
   protected void hook91(LN ln, boolean obsolete, boolean completed) throws DatabaseException{
    
  }

  // line 639 "../../../../Cleaner.ump"
   protected void hook92(long lsn, String cleanAction, boolean obsolete, boolean migrated, boolean completed, LN ln) throws DatabaseException{
    
  }

  // line 643 "../../../../Cleaner.ump"
   protected void hook93(long lsn, String cleanAction, boolean obsolete, boolean migrated, boolean completed, LN ln) throws DatabaseException{
    
  }

  // line 646 "../../../../Cleaner.ump"
   protected void hook94(DbConfigManager cm) throws DatabaseException{
    
  }

  // line 649 "../../../../Cleaner.ump"
   protected void hook95(BIN bin, DIN parentDIN) throws DatabaseException{
    
  }

  // line 652 "../../../../Cleaner.ump"
   protected void hook96() throws DatabaseException{
    
  }

  // line 655 "../../../../Cleaner.ump"
   protected void hook97() throws DatabaseException{
    
  }

  // line 658 "../../../../Cleaner.ump"
   protected void hook98() throws DatabaseException{
    
  }

  // line 661 "../../../../Cleaner.ump"
   protected void hook99() throws DatabaseException{
    
  }

  // line 664 "../../../../Cleaner.ump"
   protected void hook100() throws DatabaseException{
    
  }

  // line 667 "../../../../Cleaner.ump"
   protected void hook101(){
    
  }

  // line 670 "../../../../Cleaner.ump"
   protected void hook102(){
    
  }

  // line 673 "../../../../Cleaner.ump"
   protected void hook103(){
    
  }

  // line 676 "../../../../Cleaner.ump"
   protected void hook104() throws DatabaseException{
    
  }

  // line 679 "../../../../Cleaner.ump"
   protected void hook105(boolean wasCleaned) throws DatabaseException{
    
  }

  // line 682 "../../../../Cleaner.ump"
   protected void hook106(boolean wasCleaned) throws DatabaseException{
    
  }

  // line 685 "../../../../Cleaner.ump"
   protected void hook107(boolean wasCleaned) throws DatabaseException{
    
  }

  // line 688 "../../../../Cleaner.ump"
   protected void hook108(boolean wasCleaned) throws DatabaseException{
    
  }

  // line 691 "../../../../Cleaner.ump"
   protected void hook109() throws DatabaseException{
    
  }

  // line 694 "../../../../Cleaner.ump"
   protected void hook110(boolean wasCleaned) throws DatabaseException{
    
  }

  // line 697 "../../../../Cleaner.ump"
   protected void hook111(boolean wasCleaned) throws DatabaseException{
    
  }


  /**
   * protected boolean hook112(DatabaseImpl db, boolean c) throws DatabaseException {
   * return c;
   * }
   * protected void hook113(DatabaseImpl db) throws DatabaseException {
   * }
   */
  // line 708 "../../../../Cleaner.ump"
   protected void hook115(Set safeFiles) throws DatabaseException{
    for (Iterator i = safeFiles.iterator(); i.hasNext();) {
	    Long fileNum = (Long) i.next();
	    long fileNumValue = fileNum.longValue();
	    boolean deleted = false;
	    try {
		if (expunge) {
		    env.getFileManager().deleteFile(fileNumValue);
		} else {
		    env.getFileManager().renameFile(fileNumValue, FileManager.DEL_SUFFIX);
		}
		deleted = true;
	    } catch (DatabaseException e) {
		traceFileNotDeleted(e, fileNumValue);
	    } catch (IOException e) {
		traceFileNotDeleted(e, fileNumValue);
	    }
	    if (deleted) {
		this.hook88(fileNumValue);
		try {
		    profile.removeFile(fileNum);
		} finally {
		    fileSelector.removeDeletedFile(fileNum);
		}
	    }
	    this.hook96();
	}
  }


  /**
   * 
   * Returns whether the given BIN entry may be stripped by the evictor. True is always returned if the BIN is not dirty. False is returned if the BIN is dirty and the entry will be migrated soon.
   */
  // line 9 "../../../../Evictor_Cleaner.ump"
   public boolean isEvictable(BIN bin, int index){
    if (bin.getDirty()) {
					if (bin.getMigrate(index)) {
				return false;
					}
					boolean isResident = (bin.getTarget(index) != null);
					Long fileNum = new Long(DbLsn.getFileNumber(bin.getLsn(index)));
					if ((PROACTIVE_MIGRATION || isResident) && mustBeCleanedFiles.contains(fileNum)) {
				return false;
					}
					if ((clusterAll || (clusterResident && isResident)) && lowUtilizationFiles.contains(fileNum)) {
				return false;
					}
			}
			return true;
  }


  /**
   * 
   * Adds the DB ID to the pending DB set if it is being deleted but deletion is not yet complete.
   */
  // line 9 "../../../../DeleteOp_Cleaner.ump"
  public void addPendingDB(DatabaseImpl db){
    if (db != null && db.isDeleted() && !db.isDeleteFinished()) {
					DatabaseId id = db.getId();
					if (fileSelector.addPendingDB(id)) {
							//this.hook85(id);
							Label85: ;
					}
			}
  }

  // line 7 "../../../../CleanerDaemon_Cleaner.ump"
   public void runOrPause(boolean run){
    if (!env.isNoLocking()) {
					for (int i = 0; i < threads.length; i += 1) {
				if (threads[i] != null) {
						threads[i].runOrPause(run);
				}
					}
			}
  }

  // line 17 "../../../../CleanerDaemon_Cleaner.ump"
   public void requestShutdown(){
    for (int i = 0; i < threads.length; i += 1) {
					if (threads[i] != null) {
				threads[i].requestShutdown();
					}
			}
  }

  // line 25 "../../../../CleanerDaemon_Cleaner.ump"
   public void shutdown(){
    for (int i = 0; i < threads.length; i += 1) {
					if (threads[i] != null) {
				threads[i].shutdown();
				threads[i].clearEnv();
				threads[i] = null;
					}
			}
  }

  // line 35 "../../../../CleanerDaemon_Cleaner.ump"
   public int getNWakeupRequests(){
    int count = 0;
			for (int i = 0; i < threads.length; i += 1) {
					if (threads[i] != null) {
				count += threads[i].getNWakeupRequests();
					}
			}
			return count;
  }


  public String toString()
  {
    return super.toString() + "["+
            "nCleanerRuns" + ":" + getNCleanerRuns()+ "," +
            "lockTimeout" + ":" + getLockTimeout()+ "," +
            "readBufferSize" + ":" + getReadBufferSize()+ "," +
            "nDeadlockRetries" + ":" + getNDeadlockRetries()+ "," +
            "expunge" + ":" + getExpunge()+ "," +
            "clusterResident" + ":" + getClusterResident()+ "," +
            "clusterAll" + ":" + getClusterAll()+ "," +
            "maxBatchFiles" + ":" + getMaxBatchFiles()+ "," +
            "cleanerBytesInterval" + ":" + getCleanerBytesInterval()+ "," +
            "trackDetail" + ":" + getTrackDetail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mustBeCleanedFiles" + "=" + (getMustBeCleanedFiles() != null ? !getMustBeCleanedFiles().equals(this)  ? getMustBeCleanedFiles().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lowUtilizationFiles" + "=" + (getLowUtilizationFiles() != null ? !getLowUtilizationFiles().equals(this)  ? getLowUtilizationFiles().toString().replaceAll("  ","    ") : "this" : "null");
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
    @MethodObject
  // line 4 "../../../../Cleaner_static.ump"
  // line 4 "../../../../CriticalEviction_Cleaner_inner.ump"
  // line 4 "../../../../DeleteOp_Cleaner_inner.ump"
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
  
    // line 6 "../../../../Cleaner_static.ump"
    public  Cleaner_processPending(Cleaner _this){
      this._this=_this;
    }
  
    // line 9 "../../../../Cleaner_static.ump"
    public void execute() throws DatabaseException{
      dbMapTree=_this.env.getDbMapTree();
          pendingLNs=_this.fileSelector.getPendingLNs();
          if (pendingLNs != null) {
            location=new TreeLocation();
            for (int i=0; i < pendingLNs.length; i+=1) {
              info=pendingLNs[i];
              dbId1=info.getDbId();
              db1=dbMapTree.getDb(dbId1,_this.lockTimeout);
              key=info.getKey();
              dupKey=info.getDupKey();
              ln=info.getLN();
              Label114:
  if (_this.DO_CRITICAL_EVICTION) {
            Label86:        
            //this.hook86();
          }
  
  //            this.hook114();
              _this.processPendingLN(ln,db1,key,dupKey,location);
            }
          }
      // line 6 "../../../../DeleteOp_Cleaner_inner.ump"
      //original();
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
      // END OF UMPLE AFTER INJECTION
    }
  
    // line 40 "../../../../Cleaner_static.ump"
     protected void hook114() throws DatabaseException{
      
    }
  
    // line 6 "../../../../CriticalEviction_Cleaner_inner.ump"
     protected void hook86() throws DatabaseException{
      // Label86 is introduced by CriticalEviction_Cleaner_inner.ump
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 26 "../../../../Cleaner_static.ump"
    protected Cleaner _this ;
  // line 27 "../../../../Cleaner_static.ump"
    protected DbTree dbMapTree ;
  // line 28 "../../../../Cleaner_static.ump"
    protected LNInfo[] pendingLNs ;
  // line 29 "../../../../Cleaner_static.ump"
    protected TreeLocation location ;
  // line 30 "../../../../Cleaner_static.ump"
    protected LNInfo info ;
  // line 31 "../../../../Cleaner_static.ump"
    protected DatabaseId dbId1 ;
  // line 32 "../../../../Cleaner_static.ump"
    protected DatabaseImpl db1 ;
  // line 33 "../../../../Cleaner_static.ump"
    protected byte[] key ;
  // line 34 "../../../../Cleaner_static.ump"
    protected byte[] dupKey ;
  // line 35 "../../../../Cleaner_static.ump"
    protected LN ln ;
  // line 36 "../../../../Cleaner_static.ump"
    protected DatabaseId[] pendingDBs ;
  // line 37 "../../../../Cleaner_static.ump"
    protected DatabaseId dbId2 ;
  // line 38 "../../../../Cleaner_static.ump"
    protected DatabaseImpl db2 ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 39 "../../../../Cleaner.ump"
  static final String CLEAN_IN = "CleanIN:" ;
// line 41 "../../../../Cleaner.ump"
  static final String CLEAN_LN = "CleanLN:" ;
// line 43 "../../../../Cleaner.ump"
  static final String CLEAN_MIGRATE_LN = "CleanMigrateLN:" ;
// line 45 "../../../../Cleaner.ump"
  static final String CLEAN_PENDING_LN = "CleanPendingLN:" ;
// line 50 "../../../../Cleaner.ump"
  static final boolean PROACTIVE_MIGRATION = true ;
// line 55 "../../../../Cleaner.ump"
  static final boolean UPDATE_GENERATION = false ;
// line 87 "../../../../Cleaner.ump"
  private String name ;
// line 89 "../../../../Cleaner.ump"
  private EnvironmentImpl env ;
// line 91 "../../../../Cleaner.ump"
  private UtilizationProfile profile ;
// line 93 "../../../../Cleaner.ump"
  private UtilizationTracker tracker ;
// line 95 "../../../../Cleaner.ump"
  private FileSelector fileSelector ;
// line 97 "../../../../Cleaner.ump"
  private FileProcessor[] threads ;
// line 99 "../../../../Cleaner.ump"
  private Object deleteFileLock ;

// line 359 "../../../../Cleaner.ump"
  public void lazyMigrateLNs (final BIN bin, boolean proactiveMigration) throws DatabaseException 
  {
    DatabaseImpl db = bin.getDatabase();
	boolean isBinInDupDb = db.getSortedDuplicates() && !bin.containsDuplicates();
	Integer[] sortedIndices = null;
	int nSortedIndices = 0;
	int nEntries = bin.getNEntries();
	for (int index = 0; index < nEntries; index += 1) {
	    boolean migrateFlag = bin.getMigrate(index);
	    boolean isResident = (bin.getTarget(index) != null);
	    long childLsn = bin.getLsn(index);
	    if (shouldMigrateLN(migrateFlag, isResident, proactiveMigration, isBinInDupDb, childLsn)) {
		if (isResident) {
		    migrateLN(db, childLsn, bin, index, migrateFlag, false, 0, CLEAN_MIGRATE_LN);
		} else {
		    if (sortedIndices == null) {
			sortedIndices = new Integer[nEntries];
		    }
		    sortedIndices[nSortedIndices++] = new Integer(index);
		}
	    }
	}
	if (sortedIndices != null) {
	    Arrays.sort(sortedIndices, 0, nSortedIndices, new Comparator() {
		public int compare(Object o1, Object o2) {
		    int i1 = ((Integer) o1).intValue();
		    int i2 = ((Integer) o2).intValue();
		    return DbLsn.compareTo(bin.getLsn(i1), bin.getLsn(i2));
		}
	    });
	    for (int i = 0; i < nSortedIndices; i += 1) {
		int index = sortedIndices[i].intValue();
		long childLsn = bin.getLsn(index);
		boolean migrateFlag = bin.getMigrate(index);
		migrateLN(db, childLsn, bin, index, migrateFlag, false, 0, CLEAN_MIGRATE_LN);
	    }
	}
  }
// line 8 "../../../../CriticalEviction_Cleaner.ump"
  static final boolean DO_CRITICAL_EVICTION = true ;

  
}