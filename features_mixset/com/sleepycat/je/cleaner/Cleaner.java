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
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;
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
// line 3 "../../../../Evictor_Cleaner.ump"
// line 3 "../../../../DeleteOp_Cleaner.ump"
// line 3 "../../../../DeleteOp_Cleaner_inner.ump"
// line 3 "../../../../CleanerDaemon_Cleaner.ump"
// line 3 "../../../../Statistics_Cleaner.ump"
// line 3 "../../../../Latches_Cleaner.ump"
// line 3 "../../../../LookAHEADCache_Cleaner.ump"
// line 3 "../../../../LoggingCleaner_Cleaner.ump"
// line 3 "../../../../LoggingCleaner_Cleaner_inner.ump"
// line 3 "../../../../LoggingSevere_Cleaner.ump"
// line 3 "../../../../Derivative_LoggingSevere_EnvironmentLocking_Cleaner.ump"
// line 3 "../../../../Derivative_Evictor_CriticalEviction_Cleaner.ump"
// line 3 "../../../../Derivative_Evictor_CriticalEviction_Cleaner_inner.ump"
// line 3 "../../../../Derivative_LoggingCleaner_DeleteOp_Cleaner.ump"
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
  private int nBacklogFiles;
  private int nCleanerDeletions;
  private int nINsObsolete;
  private int nINsCleaned;
  private int nINsDead;
  private int nINsMigrated;
  private int nLNsObsolete;
  private int nLNsCleaned;
  private int nLNsDead;
  private int nLNsLocked;
  private int nLNsMigrated;
  private int nLNsMarked;
  private int nLNQueueHits;
  private int nPendingLNsProcessed;
  private int nMarkedLNsProcessed;
  private int nToBeCleanedLNsProcessed;
  private int nClusterLNsProcessed;
  private int nPendingLNsLocked;
  private int nEntriesRead;
  private long nRepeatIteratorReads;
  private int lookAheadCacheSize;
  private Level detailedTraceLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner(long aLockTimeout, int aReadBufferSize, int aNDeadlockRetries, boolean aExpunge, boolean aClusterResident, boolean aClusterAll, int aMaxBatchFiles, long aCleanerBytesInterval, boolean aTrackDetail, int aLookAheadCacheSize, Level aDetailedTraceLevel)
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
    nBacklogFiles = 0;
    nCleanerDeletions = 0;
    nINsObsolete = 0;
    nINsCleaned = 0;
    nINsDead = 0;
    nINsMigrated = 0;
    nLNsObsolete = 0;
    nLNsCleaned = 0;
    nLNsDead = 0;
    nLNsLocked = 0;
    nLNsMigrated = 0;
    nLNsMarked = 0;
    nLNQueueHits = 0;
    nPendingLNsProcessed = 0;
    nMarkedLNsProcessed = 0;
    nToBeCleanedLNsProcessed = 0;
    nClusterLNsProcessed = 0;
    nPendingLNsLocked = 0;
    nEntriesRead = 0;
    nRepeatIteratorReads = 0;
    lookAheadCacheSize = aLookAheadCacheSize;
    detailedTraceLevel = aDetailedTraceLevel;
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

  public boolean setNBacklogFiles(int aNBacklogFiles)
  {
    boolean wasSet = false;
    nBacklogFiles = aNBacklogFiles;
    wasSet = true;
    return wasSet;
  }

  public boolean setNCleanerDeletions(int aNCleanerDeletions)
  {
    boolean wasSet = false;
    nCleanerDeletions = aNCleanerDeletions;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsObsolete(int aNINsObsolete)
  {
    boolean wasSet = false;
    nINsObsolete = aNINsObsolete;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsCleaned(int aNINsCleaned)
  {
    boolean wasSet = false;
    nINsCleaned = aNINsCleaned;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsDead(int aNINsDead)
  {
    boolean wasSet = false;
    nINsDead = aNINsDead;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsMigrated(int aNINsMigrated)
  {
    boolean wasSet = false;
    nINsMigrated = aNINsMigrated;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsObsolete(int aNLNsObsolete)
  {
    boolean wasSet = false;
    nLNsObsolete = aNLNsObsolete;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsCleaned(int aNLNsCleaned)
  {
    boolean wasSet = false;
    nLNsCleaned = aNLNsCleaned;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsDead(int aNLNsDead)
  {
    boolean wasSet = false;
    nLNsDead = aNLNsDead;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsLocked(int aNLNsLocked)
  {
    boolean wasSet = false;
    nLNsLocked = aNLNsLocked;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsMigrated(int aNLNsMigrated)
  {
    boolean wasSet = false;
    nLNsMigrated = aNLNsMigrated;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsMarked(int aNLNsMarked)
  {
    boolean wasSet = false;
    nLNsMarked = aNLNsMarked;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNQueueHits(int aNLNQueueHits)
  {
    boolean wasSet = false;
    nLNQueueHits = aNLNQueueHits;
    wasSet = true;
    return wasSet;
  }

  public boolean setNPendingLNsProcessed(int aNPendingLNsProcessed)
  {
    boolean wasSet = false;
    nPendingLNsProcessed = aNPendingLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNMarkedLNsProcessed(int aNMarkedLNsProcessed)
  {
    boolean wasSet = false;
    nMarkedLNsProcessed = aNMarkedLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNToBeCleanedLNsProcessed(int aNToBeCleanedLNsProcessed)
  {
    boolean wasSet = false;
    nToBeCleanedLNsProcessed = aNToBeCleanedLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNClusterLNsProcessed(int aNClusterLNsProcessed)
  {
    boolean wasSet = false;
    nClusterLNsProcessed = aNClusterLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNPendingLNsLocked(int aNPendingLNsLocked)
  {
    boolean wasSet = false;
    nPendingLNsLocked = aNPendingLNsLocked;
    wasSet = true;
    return wasSet;
  }

  public boolean setNEntriesRead(int aNEntriesRead)
  {
    boolean wasSet = false;
    nEntriesRead = aNEntriesRead;
    wasSet = true;
    return wasSet;
  }

  public boolean setNRepeatIteratorReads(long aNRepeatIteratorReads)
  {
    boolean wasSet = false;
    nRepeatIteratorReads = aNRepeatIteratorReads;
    wasSet = true;
    return wasSet;
  }

  public boolean setLookAheadCacheSize(int aLookAheadCacheSize)
  {
    boolean wasSet = false;
    lookAheadCacheSize = aLookAheadCacheSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setDetailedTraceLevel(Level aDetailedTraceLevel)
  {
    boolean wasSet = false;
    detailedTraceLevel = aDetailedTraceLevel;
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

  public int getNBacklogFiles()
  {
    return nBacklogFiles;
  }

  public int getNCleanerDeletions()
  {
    return nCleanerDeletions;
  }

  public int getNINsObsolete()
  {
    return nINsObsolete;
  }

  public int getNINsCleaned()
  {
    return nINsCleaned;
  }

  public int getNINsDead()
  {
    return nINsDead;
  }

  public int getNINsMigrated()
  {
    return nINsMigrated;
  }

  public int getNLNsObsolete()
  {
    return nLNsObsolete;
  }

  public int getNLNsCleaned()
  {
    return nLNsCleaned;
  }

  public int getNLNsDead()
  {
    return nLNsDead;
  }

  public int getNLNsLocked()
  {
    return nLNsLocked;
  }

  public int getNLNsMigrated()
  {
    return nLNsMigrated;
  }

  public int getNLNsMarked()
  {
    return nLNsMarked;
  }

  public int getNLNQueueHits()
  {
    return nLNQueueHits;
  }

  public int getNPendingLNsProcessed()
  {
    return nPendingLNsProcessed;
  }

  public int getNMarkedLNsProcessed()
  {
    return nMarkedLNsProcessed;
  }

  public int getNToBeCleanedLNsProcessed()
  {
    return nToBeCleanedLNsProcessed;
  }

  public int getNClusterLNsProcessed()
  {
    return nClusterLNsProcessed;
  }

  public int getNPendingLNsLocked()
  {
    return nPendingLNsLocked;
  }

  public int getNEntriesRead()
  {
    return nEntriesRead;
  }

  public long getNRepeatIteratorReads()
  {
    return nRepeatIteratorReads;
  }

  public int getLookAheadCacheSize()
  {
    return lookAheadCacheSize;
  }

  /**
   * adding inner elements ;
   */
  public Level getDetailedTraceLevel()
  {
    return detailedTraceLevel;
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
	Label94:
lookAheadCacheSize = cm.getInt(EnvironmentParams.CLEANER_LOOK_AHEAD_CACHE_SIZE);
//	original(cm);
 //this.hook94(cm);
	nDeadlockRetries = cm.getInt(EnvironmentParams.CLEANER_DEADLOCK_RETRY);
	expunge = cm.getBoolean(EnvironmentParams.CLEANER_REMOVE);
	clusterResident = cm.getBoolean(EnvironmentParams.CLEANER_CLUSTER);
	clusterAll = cm.getBoolean(EnvironmentParams.CLEANER_CLUSTER_ALL);
	maxBatchFiles = cm.getInt(EnvironmentParams.CLEANER_MAX_BATCH_FILES);
	Label90:
detailedTraceLevel = Tracer.parseLevel(env, EnvironmentParams.JE_LOGGING_LEVEL_CLEANER);
	//original();
 //this.hook90();
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
//>> Label87 introduced by EnviromentLocking_Cleaner.ump
	Tracer.trace(Level.SEVERE, env,	"Cleaner has " + safeFiles.size() + " files not deleted because of read-only processes.");
	//original(safeFiles);

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
nCleanerDeletions++;
			//original();

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
    Tracer.trace(env, "Cleaner", "deleteSafeToDeleteFiles",
		"Log file 0x" + Long.toHexString(fileNum) + " could not be " + (expunge ? "deleted" : "renamed")
			+ ".  This operation will be retried at the next checkpoint.",
		e);
	original(e, fileNum);
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
    // line 106 "../../../../Statistics_Cleaner.ump"
    //original();
    			nBacklogFiles = fileSelector.getBacklog();
    // END OF UMPLE AFTER INJECTION
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
	    Label97:
nPendingLNsProcessed++;
				//original();
 //this.hook97();
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

		Label98:
nLNsDead++;
			//original();
 //this.hook98();
		obsolete = true;
		completed = true;
		return;
	    }
	    Tree tree = db.getTree();
	    assert tree != null;
	    locker = new BasicLocker(env);
	    LockResult lockRet = locker.nonBlockingLock(ln.getNodeId(), LockType.READ, db);
	    if (lockRet.getLockGrant() == LockGrantType.DENIED) {
		Label99:
nPendingLNsLocked++;
			//original();
 //this.hook99();
		lockDenied = true;
		completed = true;
		return;
	    }
	    parentFound = tree.getParentBINForChildLN(location, key, dupKey, ln, false, true, true, UPDATE_GENERATION);
	    bin = location.bin;
	    int index = location.index;
	    if (!parentFound) {
		Label100:
nLNsDead++;
			//original();
 //this.hook100();
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
	    Label95:
if (parentDIN != null) {
					parentDIN.releaseLatchIfOwner();
			}
			if (bin != null) {
					bin.releaseLatchIfOwner();
			}
			//original(bin, parentDIN);
 //this.hook95(bin, parentDIN);
	    if (locker != null) {
		locker.operationEnd();
	    }
	    if (processedHere) {
		if (completed && !lockDenied) {
		    fileSelector.removePendingLN(ln.getNodeId());
		}
		Label91:
trace(detailedTraceLevel, CLEAN_PENDING_LN, ln, DbLsn.NULL_LSN, completed, obsolete, false);
	//original(ln, obsolete, completed);
 //this.hook91(ln, obsolete, completed);
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
	    Label101:
nMarkedLNsProcessed++;
			//original();
 //this.hook101();
	} else if (!proactiveMigration || isBinInDupDb || env.isClosing()) {
	} else {
	    Long fileNum = new Long(DbLsn.getFileNumber(childLsn));
	    if ((PROACTIVE_MIGRATION || isResident) && mustBeCleanedFiles.contains(fileNum)) {
		doMigration = true;
		Label102:
nToBeCleanedLNsProcessed++;
			//original();
 //this.hook102();
	    } else if ((clusterAll || (clusterResident && isResident)) && lowUtilizationFiles.contains(fileNum)) {
		doMigration = true;
		Label103:
nClusterLNsProcessed++;
			//original();
 //this.hook103();
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
				Label105:
if (wasCleaned) {
					nLNsDead++;
			}
			//original(wasCleaned);
 //this.hook105(wasCleaned);
				obsolete = true;
				completed = true;
				return;
					}
					if (lockedPendingNodeId != ln.getNodeId()) {
				locker = new BasicLocker(env);
				LockResult lockRet = locker.nonBlockingLock(ln.getNodeId(), LockType.READ, db);
				if (lockRet.getLockGrant() == LockGrantType.DENIED) {
						Label106:
if (wasCleaned) {
					nLNsLocked++;
			}
			//original(wasCleaned);
 //this.hook106(wasCleaned);
						lockDenied = true;
						completed = true;
						return;
				}
					}
					if (ln.isDeleted()) {
				bin.setKnownDeletedLeaveTarget(index);
				Label107:
if (wasCleaned) {
					nLNsDead++;
			}
			//original(wasCleaned);
 //this.hook107(wasCleaned);
				obsolete = true;
				completed = true;
				return;
					}
					if (bin.getMigrate(index)) {
				Long fileNum = new Long(DbLsn.getFileNumber(lsn));
				if (!fileSelector.isFileCleaningInProgress(fileNum)) {
						obsolete = true;
						completed = true;
						Label108:
if (wasCleaned) {
					nLNsDead++;
			}
			//original(wasCleaned);
 //this.hook108(wasCleaned);
						return;
				}
					}
					byte[] key = getLNMainKey(bin, index);
					long newLNLsn = ln.log(env, db.getId(), key, lsn, locker);
					bin.updateEntry(index, newLNLsn);
					Label104:
nLNsMigrated++;
			//original();
 //this.hook104();
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
					Label92:
trace(detailedTraceLevel, cleanAction, ln, lsn, completed, obsolete, migrated);
	//original(lsn, cleanAction, obsolete, migrated, completed, ln);
 //this.hook92(lsn, cleanAction, obsolete, migrated, completed, ln);
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
						Label110:
if (wasCleaned) {
					nLNsLocked++;
			}
			//original(wasCleaned);
 //this.hook110(wasCleaned);
						lockDenied = true;
						completed = true;
						return;
				}
					}
					Long fileNum = new Long(DbLsn.getFileNumber(lsn));
					if (!fileSelector.isFileCleaningInProgress(fileNum)) {
				obsolete = true;
				completed = true;
				Label111:
if (wasCleaned) {
					nLNsDead++;
			}
			//original(wasCleaned);
 //this.hook111(wasCleaned);
				return;
					}
					byte[] key = parentDIN.getDupKey();
					long newLNLsn = ln.log(env, db.getId(), key, lsn, locker);
					parentDIN.updateDupCountLNRef(newLNLsn);
					Label109:
nLNsMigrated++;
	//original();
 //this.hook109();
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
					Label93: //this.hook93(lsn, cleanAction, obsolete, migrated, completed, ln);
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
    Tracer.trace(Level.SEVERE, env, "Cleaner deleted file 0x" + Long.toHexString(fileNumValue));
	original(fileNumValue);
  }

  // line 629 "../../../../Cleaner.ump"
   protected void hook89(DatabaseException DBE) throws DatabaseException{
    Tracer.trace(env, "com.sleepycat.je.cleaner.Cleaner", "processLN", "Exception thrown: ", DBE);
	original(DBE);
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

							Label85:
Tracer.trace(detailedTraceLevel, env, "CleanAddPendingDB " + id);
	//original(id);
 ;							//this.hook85(id);
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


  /**
   * 
   * Load stats.
   */
  // line 51 "../../../../Statistics_Cleaner.ump"
   public void loadStats(StatsConfig config, EnvironmentStats stat) throws DatabaseException{
    stat.setCleanerBacklog(nBacklogFiles);
			stat.setNCleanerRuns(nCleanerRuns);
			stat.setNCleanerDeletions(nCleanerDeletions);
			stat.setNINsObsolete(nINsObsolete);
			stat.setNINsCleaned(nINsCleaned);
			stat.setNINsDead(nINsDead);
			stat.setNINsMigrated(nINsMigrated);
			stat.setNLNsObsolete(nLNsObsolete);
			stat.setNLNsCleaned(nLNsCleaned);
			stat.setNLNsDead(nLNsDead);
			stat.setNLNsLocked(nLNsLocked);
			stat.setNLNsMigrated(nLNsMigrated);
			stat.setNLNsMarked(nLNsMarked);
			stat.setNLNQueueHits(nLNQueueHits);
			stat.setNPendingLNsProcessed(nPendingLNsProcessed);
			stat.setNMarkedLNsProcessed(nMarkedLNsProcessed);
			stat.setNToBeCleanedLNsProcessed(nToBeCleanedLNsProcessed);
			stat.setNClusterLNsProcessed(nClusterLNsProcessed);
			stat.setNPendingLNsLocked(nPendingLNsLocked);
			stat.setNCleanerEntriesRead(nEntriesRead);
			stat.setNRepeatIteratorReads(nRepeatIteratorReads);
			if (config.getClear()) {
					nCleanerRuns = 0;
					nCleanerDeletions = 0;
					nINsObsolete = 0;
					nINsCleaned = 0;
					nINsDead = 0;
					nINsMigrated = 0;
					nLNsObsolete = 0;
					nLNsCleaned = 0;
					nLNsDead = 0;
					nLNsLocked = 0;
					nLNsMigrated = 0;
					nLNsMarked = 0;
					nLNQueueHits = 0;
					nPendingLNsProcessed = 0;
					nMarkedLNsProcessed = 0;
					nToBeCleanedLNsProcessed = 0;
					nClusterLNsProcessed = 0;
					nPendingLNsLocked = 0;
					nEntriesRead = 0;
					nRepeatIteratorReads = 0;
			}
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 12 "../../../../LoggingCleaner_Cleaner.ump"
  public void trace(Level level, String action, Node node, long logLsn, boolean completed, boolean obsolete, boolean dirtiedMigrated){
    new Cleaner_trace(this, level, action, node, logLsn, completed, obsolete, dirtiedMigrated).execute();
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
            "trackDetail" + ":" + getTrackDetail()+ "," +
            "nBacklogFiles" + ":" + getNBacklogFiles()+ "," +
            "nCleanerDeletions" + ":" + getNCleanerDeletions()+ "," +
            "nINsObsolete" + ":" + getNINsObsolete()+ "," +
            "nINsCleaned" + ":" + getNINsCleaned()+ "," +
            "nINsDead" + ":" + getNINsDead()+ "," +
            "nINsMigrated" + ":" + getNINsMigrated()+ "," +
            "nLNsObsolete" + ":" + getNLNsObsolete()+ "," +
            "nLNsCleaned" + ":" + getNLNsCleaned()+ "," +
            "nLNsDead" + ":" + getNLNsDead()+ "," +
            "nLNsLocked" + ":" + getNLNsLocked()+ "," +
            "nLNsMigrated" + ":" + getNLNsMigrated()+ "," +
            "nLNsMarked" + ":" + getNLNsMarked()+ "," +
            "nLNQueueHits" + ":" + getNLNQueueHits()+ "," +
            "nPendingLNsProcessed" + ":" + getNPendingLNsProcessed()+ "," +
            "nMarkedLNsProcessed" + ":" + getNMarkedLNsProcessed()+ "," +
            "nToBeCleanedLNsProcessed" + ":" + getNToBeCleanedLNsProcessed()+ "," +
            "nClusterLNsProcessed" + ":" + getNClusterLNsProcessed()+ "," +
            "nPendingLNsLocked" + ":" + getNPendingLNsLocked()+ "," +
            "nEntriesRead" + ":" + getNEntriesRead()+ "," +
            "nRepeatIteratorReads" + ":" + getNRepeatIteratorReads()+ "," +
            "lookAheadCacheSize" + ":" + getLookAheadCacheSize()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mustBeCleanedFiles" + "=" + (getMustBeCleanedFiles() != null ? !getMustBeCleanedFiles().equals(this)  ? getMustBeCleanedFiles().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lowUtilizationFiles" + "=" + (getLowUtilizationFiles() != null ? !getLowUtilizationFiles().equals(this)  ? getLowUtilizationFiles().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "detailedTraceLevel" + "=" + (getDetailedTraceLevel() != null ? !getDetailedTraceLevel().equals(this)  ? getDetailedTraceLevel().toString().replaceAll("  ","    ") : "this" : "null");
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 4 "../../../../Cleaner_static.ump"
  // line 4 "../../../../DeleteOp_Cleaner_inner.ump"
  // line 4 "../../../../Derivative_Evictor_CriticalEviction_Cleaner_inner.ump"
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
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../LoggingCleaner_Cleaner_inner.ump"
  public static class Cleaner_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Cleaner_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../LoggingCleaner_Cleaner_inner.ump"
    public  Cleaner_trace(Cleaner _this, Level level, String action, Node node, long logLsn, boolean completed, boolean obsolete, boolean dirtiedMigrated){
      this._this=_this;
          this.level=level;
          this.action=action;
          this.node=node;
          this.logLsn=logLsn;
          this.completed=completed;
          this.obsolete=obsolete;
          this.dirtiedMigrated=dirtiedMigrated;
    }
  
    // line 16 "../../../../LoggingCleaner_Cleaner_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 17 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected Cleaner _this ;
  // line 18 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected Level level ;
  // line 19 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected String action ;
  // line 20 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected Node node ;
  // line 21 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected long logLsn ;
  // line 22 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected boolean completed ;
  // line 23 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected boolean obsolete ;
  // line 24 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected boolean dirtiedMigrated ;
  // line 25 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected Logger logger ;
  // line 26 "../../../../LoggingCleaner_Cleaner_inner.ump"
    protected StringBuffer sb ;
  
    
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