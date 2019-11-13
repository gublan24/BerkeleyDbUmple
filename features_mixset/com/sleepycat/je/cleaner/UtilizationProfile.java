/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.txn.BasicLocker;
import com.sleepycat.je.txn.AutoTxn;
import com.sleepycat.je.tree.TreeLocation;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.FileSummaryLN;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.log.FileManager;
import com.sleepycat.je.dbi.CursorImpl.SearchMode;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.EnvConfigObserver;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.dbi.CursorImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseConfig;
import java.util.logging.Level;
import java.util.TreeMap;
import java.util.StringTokenizer;
import java.util.SortedSet;
import java.util.SortedMap;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import com.sleepycat.je.dbi.*;

// line 3 "../../../../UtilizationProfile.ump"
// line 3 "../../../../UtilizationProfile_static.ump"
// line 3 "../../../../MemoryBudget_UtilizationProfile.ump"
// line 3 "../../../../MemoryBudget_UtilizationProfile_inner.ump"
// line 3 "../../../../Evictor_UtilizationProfile.ump"
// line 3 "../../../../Evictor_UtilizationProfile_inner.ump"
// line 3 "../../../../DeleteOp_UtilizationProfile.ump"
// line 3 "../../../../Latches_UtilizationProfile.ump"
// line 3 "../../../../Latches_UtilizationProfile_inner.ump"
// line 3 "../../../../Derivative_LoggingSevere_LoggingBase_UtilizationProfile.ump"
public class UtilizationProfile implements EnvConfigObserver
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UtilizationProfile Attributes
  private int minUtilization;
  private int minFileUtilization;
  private int minAge;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UtilizationProfile(int aMinUtilization, int aMinFileUtilization, int aMinAge)
  {
    minUtilization = aMinUtilization;
    minFileUtilization = aMinFileUtilization;
    minAge = aMinAge;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMinUtilization(int aMinUtilization)
  {
    boolean wasSet = false;
    minUtilization = aMinUtilization;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinFileUtilization(int aMinFileUtilization)
  {
    boolean wasSet = false;
    minFileUtilization = aMinFileUtilization;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinAge(int aMinAge)
  {
    boolean wasSet = false;
    minAge = aMinAge;
    wasSet = true;
    return wasSet;
  }

  /**
   * 
   * Minimum overall utilization threshold that triggers cleaning.  Is non-private for unit tests.
   */
  public int getMinUtilization()
  {
    return minUtilization;
  }

  /**
   * 
   * Minimum utilization threshold for an individual log file that triggers cleaning.  Is non-private for unit tests.
   */
  public int getMinFileUtilization()
  {
    return minFileUtilization;
  }

  /**
   * 
   * Minumum age to qualify for cleaning.  If the first active LSN file is 5 and the mininum age is 2, file 4 won't qualify but file 3 will.  Must be greater than zero because we never clean the first active LSN file.  Is non-private for unit tests.
   */
  public int getMinAge()
  {
    return minAge;
  }

  public void delete()
  {}


  /**
   * 
   * Creates an empty UP.
   */
  // line 82 "../../../../UtilizationProfile.ump"
   public  UtilizationProfile(EnvironmentImpl env, UtilizationTracker tracker) throws DatabaseException{
    this.env = env;
	this.tracker = tracker;
	fileSummaryMap = new TreeMap();
	rmwFixEnabled = env.getConfigManager().getBoolean(EnvironmentParams.CLEANER_RMW_FIX);
	parseForceCleanFiles(env.getConfigManager().get(EnvironmentParams.CLEANER_FORCE_CLEAN_FILES));
	envConfigUpdate(env.getConfigManager());
	env.addConfigObserver(this);
  }


  /**
   * 
   * Process notifications of mutable property changes.
   */
  // line 95 "../../../../UtilizationProfile.ump"
   public void envConfigUpdate(DbConfigManager cm) throws DatabaseException{
    minAge = cm.getInt(EnvironmentParams.CLEANER_MIN_AGE);
	minUtilization = cm.getInt(EnvironmentParams.CLEANER_MIN_UTILIZATION);
	minFileUtilization = cm.getInt(EnvironmentParams.CLEANER_MIN_FILE_UTILIZATION);
  }


  /**
   * 
   * @see EnvironmentParams#CLEANER_RMW_FIX
   * @see FileSummaryLN#postFetchInit
   */
  // line 105 "../../../../UtilizationProfile.ump"
   public boolean isRMWFixEnabled(){
    return rmwFixEnabled;
  }


  /**
   * 
   * Returns the number of files in the profile.
   */
  // line 112 "../../../../UtilizationProfile.ump"
   synchronized  int getNumberOfFiles() throws DatabaseException{
    assert cachePopulated;
	return fileSummaryMap.size();
  }


  /**
   * 
   * Returns the cheapest file to clean from the given list of files.  This method is used to select the first file to be cleaned in the batch of to-be-cleaned files.
   */
  // line 120 "../../../../UtilizationProfile.ump"
   synchronized  Long getCheapestFileToClean(SortedSet files) throws DatabaseException{
    if (files.size() == 1) {
	    return (Long) files.first();
	}
	assert cachePopulated;
	Long bestFile = null;
	int bestCost = Integer.MAX_VALUE;
	for (Iterator iter = files.iterator(); iter.hasNext();) {
	    Long file = (Long) iter.next();
	    FileSummary summary = getFileSummary(file);
	    int thisCost = summary.getNonObsoleteCount();
	    if (bestFile == null || thisCost < bestCost) {
		bestFile = file;
		bestCost = thisCost;
	    }
	}
	return bestFile;
  }


  /**
   * 
   * Returns the best file that qualifies for cleaning, or null if no file qualifies.
   * @param fileSelector is used to determine valid cleaning candidates.
   * @param forceCleaning is true to always select a file, even if itsutilization is above the minimum utilization threshold.
   * @param lowUtilizationFiles is a returned set of files that are below theminimum utilization threshold.
   */
  // line 146 "../../../../UtilizationProfile.ump"
   synchronized  Long getBestFileForCleaning(FileSelector fileSelector, boolean forceCleaning, Set lowUtilizationFiles) throws DatabaseException{
    if (lowUtilizationFiles != null) {
	    lowUtilizationFiles.clear();
	}
	assert cachePopulated;
	if (fileSummaryMap.size() == 0) {
	    return null;
	}
	final int useMinUtilization = minUtilization;
	final int useMinFileUtilization = minFileUtilization;
	final int useMinAge = minAge;
	long firstActiveLsn = env.getCheckpointer().getFirstActiveLsn();
	if (firstActiveLsn == DbLsn.NULL_LSN) {
	    return null;
	}
	Iterator iter = fileSummaryMap.keySet().iterator();
	Long bestFile = null;
	int bestUtilization = 101;
	long totalSize = 0;
	long totalObsoleteSize = 0;
	while (iter.hasNext()) {
	    Long file = (Long) iter.next();
	    long fileNum = file.longValue();
	    FileSummary summary = getFileSummary(file);
	    int obsoleteSize = summary.getObsoleteSize();
	    if (fileSelector.isFileCleaningInProgress(file)) {
		totalSize += summary.totalSize - obsoleteSize;
		totalObsoleteSize += estimateUPObsoleteSize(summary);
		continue;
	    }
	    totalSize += summary.totalSize;
	    totalObsoleteSize += obsoleteSize;
	    if (DbLsn.getFileNumber(firstActiveLsn) - fileNum < useMinAge) {
		continue;
	    }
	    int thisUtilization = utilization(obsoleteSize, summary.totalSize);
	    if (bestFile == null || thisUtilization < bestUtilization) {
		bestFile = file;
		bestUtilization = thisUtilization;
	    }
	    if (lowUtilizationFiles != null && thisUtilization < useMinUtilization) {
		lowUtilizationFiles.add(file);
	    }
	}
	int totalUtilization = utilization(totalObsoleteSize, totalSize);
	if (forceCleaning || totalUtilization < useMinUtilization || bestUtilization < useMinFileUtilization) {
	    return bestFile;
	} else {
	    return null;
	}
  }


  /**
   * 
   * Calculate the utilization percentage.
   */
  // line 201 "../../../../UtilizationProfile.ump"
   public static  int utilization(long obsoleteSize, long totalSize){
    if (totalSize != 0) {
	    return (int) (((totalSize - obsoleteSize) * 100) / totalSize);
	} else {
	    return 0;
	}
  }


  /**
   * 
   * Estimate the log size that will be made obsolete when a log file is deleted and we delete its UP records. Note that we do not count the space taken by the deleted FileSummaryLN records written during log file deletion.  These add the same amount to the total log size and the obsolete log size, and therefore have a small impact on total utilization.
   */
  // line 212 "../../../../UtilizationProfile.ump"
   private int estimateUPObsoleteSize(FileSummary summary){
    if (true)
	    return 0;
	final int OVERHEAD = 75;
	int OFFSETS_PER_LN = 1000;
	int BYTES_PER_LN = OVERHEAD + (OFFSETS_PER_LN * 2);
	int totalNodes = summary.totalLNCount + summary.totalINCount;
	int logEntries = (totalNodes / OFFSETS_PER_LN) + 1;
	return logEntries * BYTES_PER_LN;
  }


  /**
   * 
   * Gets the base summary from the cached map.  Add the tracked summary, if one exists, to the base summary.  Sets all entries obsolete, if the file is in the forceCleanFiles set.
   */
  // line 226 "../../../../UtilizationProfile.ump"
   private synchronized  FileSummary getFileSummary(Long file){
    FileSummary summary = (FileSummary) fileSummaryMap.get(file);
	long fileNum = file.longValue();
	TrackedFileSummary trackedSummary = tracker.getTrackedFile(fileNum);
	if (trackedSummary != null) {
	    FileSummary totals = new FileSummary();
	    totals.add(summary);
	    totals.add(trackedSummary);
	    summary = totals;
	}
	if (isForceCleanFile(fileNum)) {
	    FileSummary allObsolete = new FileSummary();
	    allObsolete.add(summary);
	    allObsolete.obsoleteLNCount = allObsolete.totalLNCount;
	    allObsolete.obsoleteINCount = allObsolete.totalINCount;
	    summary = allObsolete;
	}
	return summary;
  }


  /**
   * 
   * Returns whether the given file is in the forceCleanFiles set.
   */
  // line 249 "../../../../UtilizationProfile.ump"
   private boolean isForceCleanFile(long file){
    if (forceCleanFiles != null) {
	    for (int i = 0; i < forceCleanFiles.length; i += 2) {
		long from = forceCleanFiles[i];
		long to = forceCleanFiles[i + 1];
		if (file >= from && file <= to) {
		    return true;
		}
	    }
	}
	return false;
  }


  /**
   * 
   * Parses the je.cleaner.forceCleanFiles property value.
   */
  // line 265 "../../../../UtilizationProfile.ump"
   private void parseForceCleanFiles(String propValue){
    if (propValue == null || propValue.length() == 0) {
	    forceCleanFiles = null;
	} else {
	    String errPrefix = "Error in " + EnvironmentParams.CLEANER_FORCE_CLEAN_FILES.getName() + "=" + propValue
		    + ": ";
	    StringTokenizer tokens = new StringTokenizer(propValue, ",-", true);
	    List list = new ArrayList();
	    while (tokens.hasMoreTokens()) {
		String fromStr = tokens.nextToken();
		long fromNum;
		try {
		    fromNum = Long.parseLong(fromStr, 16);
		} catch (NumberFormatException e) {
		    throw new IllegalArgumentException(errPrefix + "Invalid hex file number: " + fromStr);
		}
		long toNum = -1;
		if (tokens.hasMoreTokens()) {
		    String delim = tokens.nextToken();
		    if (",".equals(delim)) {
			toNum = fromNum;
		    } else if ("-".equals(delim)) {
			if (tokens.hasMoreTokens()) {
			    String toStr = tokens.nextToken();
			    try {
				toNum = Long.parseLong(toStr, 16);
			    } catch (NumberFormatException e) {
				throw new IllegalArgumentException(errPrefix + "Invalid hex file number: " + toStr);
			    }
			} else {
			    throw new IllegalArgumentException(errPrefix + "Expected file number: " + delim);
			}
		    } else {
			throw new IllegalArgumentException(errPrefix + "Expected '-' or ',': " + delim);
		    }
		} else {
		    toNum = fromNum;
		}
		assert toNum != -1;
		list.add(new Long(fromNum));
		list.add(new Long(toNum));
	    }
	    forceCleanFiles = new long[list.size()];
	    for (int i = 0; i < forceCleanFiles.length; i += 1) {
		forceCleanFiles[i] = ((Long) list.get(i)).longValue();
	    }
	}
  }


  /**
   * 
   * Count the given tracked info as obsolete and then log the summaries.
   */
  // line 317 "../../../../UtilizationProfile.ump"
   public void countAndLogSummaries(TrackedFileSummary [] summaries) throws DatabaseException{
    env.getLogManager().countObsoleteNodes(summaries);
	if (!DbInternal.getCheckpointUP(env.getConfigManager().getEnvironmentConfig())) {
	    return;
	}
	for (int i = 0; i < summaries.length; i += 1) {
	    long fileNum = summaries[i].getFileNumber();
	    TrackedFileSummary tfs = tracker.getTrackedFile(fileNum);
	    if (tfs != null) {
		flushFileSummary(tfs);
	    }
	}
  }


  /**
   * 
   * Returns a copy of the current file summary map, optionally including tracked summary information, for use by the DbSpace utility and by unit tests.  The returned map's key is a Long file number and its value is a FileSummary.
   */
  // line 334 "../../../../UtilizationProfile.ump"
   public synchronized  SortedMap getFileSummaryMap(boolean includeTrackedFiles) throws DatabaseException{
    assert cachePopulated;
	if (includeTrackedFiles) {
	    TreeMap map = new TreeMap();
	    Iterator iter = fileSummaryMap.keySet().iterator();
	    while (iter.hasNext()) {
		Long file = (Long) iter.next();
		FileSummary summary = getFileSummary(file);
		map.put(file, summary);
	    }
	    TrackedFileSummary[] trackedFiles = tracker.getTrackedFiles();
	    for (int i = 0; i < trackedFiles.length; i += 1) {
		TrackedFileSummary summary = trackedFiles[i];
		long fileNum = summary.getFileNumber();
		Long file = new Long(fileNum);
		if (!map.containsKey(file)) {
		    map.put(file, summary);
		}
	    }
	    return map;
	} else {
	    return new TreeMap(fileSummaryMap);
	}
  }


  /**
   * 
   * Clears the cache of file summary info.  The cache starts out unpopulated and is populated on the first call to getBestFileForCleaning.
   */
  // line 362 "../../../../UtilizationProfile.ump"
   public synchronized  void clearCache(){
    new UtilizationProfile_clearCache(this).execute();
  }


  /**
   * 
   * Removes a file from the utilization database and the profile, after it has been deleted by the cleaner.
   */
  // line 369 "../../../../UtilizationProfile.ump"
  public void removeFile(Long fileNum) throws DatabaseException{
    new UtilizationProfile_removeFile(this, fileNum).execute();
  }


  /**
   * 
   * For the LN at the cursor position deletes all LNs for the file.  This method performs eviction and is not synchronized.
   */
  // line 376 "../../../../UtilizationProfile.ump"
   private void deleteFileSummary(Long fileNum) throws DatabaseException{
    Locker locker = null;
	CursorImpl cursor = null;
	try {
	    locker = new BasicLocker(env);
	    cursor = new CursorImpl(fileSummaryDb, locker);
	    DatabaseEntry keyEntry = new DatabaseEntry();
	    DatabaseEntry dataEntry = new DatabaseEntry();
	    long fileNumVal = fileNum.longValue();
	    if (!getFirstFSLN(cursor, fileNumVal, keyEntry, dataEntry, LockType.WRITE)) {
		return;
	    }
	    OperationStatus status = OperationStatus.SUCCESS;
	    while (status == OperationStatus.SUCCESS) {
		Label173:           ;  //this.hook173();
		FileSummaryLN ln = (FileSummaryLN) cursor.getCurrentLN(LockType.NONE);
		if (ln != null) {
		    if (fileNumVal != ln.getFileNumber(keyEntry.getData())) {
			break;
		    }
		    TrackedFileSummary tfs = tracker.getTrackedFile(fileNumVal);
		    if (tfs != null) {
			ln.setTrackedSummary(tfs);
		    }
		    cursor.delete();
		}
		status = cursor.getNext(keyEntry, dataEntry, LockType.WRITE, true, false);
	    }
	} finally {
	    if (cursor != null) {
		Label178:
cursor.releaseBINs();
//	original(cursor);
           ;  //this.hook178(cursor);
		cursor.close();
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	}
  }


  /**
   * 
   * Updates and stores the FileSummary for a given tracked file, if flushing of the summary is allowed.
   */
  // line 418 "../../../../UtilizationProfile.ump"
   public void flushFileSummary(TrackedFileSummary tfs) throws DatabaseException{
    if (tfs.getAllowFlush()) {
	    putFileSummary(tfs);
	}
  }


  /**
   * 
   * Updates and stores the FileSummary for a given tracked file.  This method is synchronized and may not perform eviction.
   */
  // line 427 "../../../../UtilizationProfile.ump"
   private synchronized  PackedOffsets putFileSummary(TrackedFileSummary tfs) throws DatabaseException{
    return new UtilizationProfile_putFileSummary(this, tfs).execute();
  }


  /**
   * 
   * Returns the stored/packed obsolete offsets and the tracked obsolete offsets for the given file.  The tracked summary object returned can be used to test for obsolete offsets that are being added during cleaning by other threads participating in lazy migration.  The caller must call TrackedFileSummary.setAllowFlush(true) when cleaning is complete. This method performs eviction and is not synchronized.
   * @param logUpdate if true, log any updates to the utilization profile. Iffalse, only retrieve the new information.
   */
  // line 436 "../../../../UtilizationProfile.ump"
  public TrackedFileSummary getObsoleteDetail(Long fileNum, PackedOffsets packedOffsets, boolean logUpdate) throws DatabaseException{
    if (!env.getCleaner().trackDetail) {
	    return null;
	}
	assert cachePopulated;
	long fileNumVal = fileNum.longValue();
	List list = new ArrayList();
	TrackedFileSummary tfs = env.getLogManager().getUnflushableTrackedSummary(fileNumVal);
	Locker locker = null;
	CursorImpl cursor = null;
	try {
	    locker = new BasicLocker(env);
	    cursor = new CursorImpl(fileSummaryDb, locker);
	    DatabaseEntry keyEntry = new DatabaseEntry();
	    DatabaseEntry dataEntry = new DatabaseEntry();
	    OperationStatus status = OperationStatus.SUCCESS;
	    if (!getFirstFSLN(cursor, fileNumVal, keyEntry, dataEntry, LockType.NONE)) {
		status = OperationStatus.NOTFOUND;
	    }
	    while (status == OperationStatus.SUCCESS) {
		Label174:           ;  //this.hook174();
		FileSummaryLN ln = (FileSummaryLN) cursor.getCurrentLN(LockType.NONE);
		if (ln != null) {
		    if (fileNumVal != ln.getFileNumber(keyEntry.getData())) {
			break;
		    }
		    PackedOffsets offsets = ln.getObsoleteOffsets();
		    if (offsets != null) {
			list.add(offsets.toArray());
		    }
		    //Label:           ;  //this.hook187(cursor);
        Label187:
cursor.evict();
//	original(cursor);

		}
		status = cursor.getNext(keyEntry, dataEntry, LockType.NONE, true, false);
	    }
	} finally {
	    Label179:
if (cursor != null) {
	    cursor.releaseBINs();
	    cursor.close();
	}
	//original(cursor);
           ;  //this.hook179(cursor);
	    if (locker != null) {
		locker.operationEnd();
	    }
	}
	if (!tfs.isEmpty()) {
	    PackedOffsets offsets = null;
	    if (logUpdate) {
		offsets = putFileSummary(tfs);
		if (offsets != null) {
		    list.add(offsets.toArray());
		}
	    } else {
		long[] offsetList = tfs.getObsoleteOffsets();
		if (offsetList != null) {
		    list.add(offsetList);
		}
	    }
	}
	int size = 0;
	for (int i = 0; i < list.size(); i += 1) {
	    long[] a = (long[]) list.get(i);
	    size += a.length;
	}
	long[] offsets = new long[size];
	int index = 0;
	for (int i = 0; i < list.size(); i += 1) {
	    long[] a = (long[]) list.get(i);
	    System.arraycopy(a, 0, offsets, index, a.length);
	    index += a.length;
	}
	assert index == offsets.length;
	packedOffsets.pack(offsets);
	return tfs;
  }


  /**
   * 
   * Populate the profile for file selection.  This method performs eviction and is not synchronized.  It must be called before recovery is complete so that synchronization is unnecessary.  It must be called before the recovery checkpoint so that the checkpoint can flush file summary information.
   */
  // line 511 "../../../../UtilizationProfile.ump"
   public boolean populateCache() throws DatabaseException{
    return new UtilizationProfile_populateCache(this).execute();
  }


  /**
   * 
   * Positions at the most recent LN for the given file number.
   */
  // line 519 "../../../../UtilizationProfile.ump"
   private boolean getFirstFSLN(CursorImpl cursor, long fileNum, DatabaseEntry keyEntry, DatabaseEntry dataEntry, LockType lockType) throws DatabaseException{
    byte[] keyBytes = FileSummaryLN.makePartialKey(fileNum);
	keyEntry.setData(keyBytes);
	int result = cursor.searchAndPosition(keyEntry, dataEntry, SearchMode.SET_RANGE, lockType);
	if ((result & CursorImpl.FOUND) == 0) {
	    return false;
	}
	boolean exactKeyMatch = ((result & CursorImpl.EXACT_KEY) != 0);
	if (exactKeyMatch
		&& cursor.getCurrentAlreadyLatched(keyEntry, dataEntry, lockType, true) != OperationStatus.KEYEMPTY) {
	    return true;
	}
	OperationStatus status = cursor.getNext(keyEntry, dataEntry, lockType, true, !exactKeyMatch);
	return status == OperationStatus.SUCCESS;
  }


  /**
   * 
   * If the file summary db is already open, return, otherwise attempt to open it.  If the environment is read-only and the database doesn't exist, return false.  If the environment is read-write the database will be created if it doesn't exist.
   */
  // line 538 "../../../../UtilizationProfile.ump"
   private boolean openFileSummaryDatabase() throws DatabaseException{
    if (fileSummaryDb != null) {
	    return true;
	}
	DbTree dbTree = env.getDbMapTree();
	Locker autoTxn = null;
	boolean operationOk = false;
	try {
	    autoTxn = new AutoTxn(env, new TransactionConfig());
	    DatabaseImpl db = dbTree.getDb(autoTxn, DbTree.UTILIZATION_DB_NAME, null, true);
	    if (db == null) {
		if (env.isReadOnly()) {
		    return false;
		}
		db = dbTree.createDb(autoTxn, DbTree.UTILIZATION_DB_NAME, new DatabaseConfig(), null, true);
	    }
	    fileSummaryDb = db;
	    operationOk = true;
	    return true;
	} finally {
	    if (autoTxn != null) {
		autoTxn.operationEnd(operationOk);
	    }
	}
  }


  /**
   * 
   * Insert the given LN with the given key values.  This method is synchronized and may not perform eviction.
   */
  // line 567 "../../../../UtilizationProfile.ump"
   private synchronized  void insertFileSummary(FileSummaryLN ln, long fileNum, int sequence) throws DatabaseException{
    byte[] keyBytes = FileSummaryLN.makeFullKey(fileNum, sequence);
	Locker locker = null;
	CursorImpl cursor = null;
	try {
	    locker = new BasicLocker(env);
	    cursor = new CursorImpl(fileSummaryDb, locker);
	    //Label:           ;  //this.hook189(cursor);
      Label189:
// <<     private synchronized void insertFileSummary(...)
	    cursor.setAllowEviction(false);
//	original(cursor);

	    OperationStatus status = cursor.putLN(keyBytes, ln, false);
	    Label177:
//synchronized void insertFileSummary(FileSummaryLN ln, long fileNum, int sequence)
	    env.getLogger().log(Level.SEVERE, "Cleaner duplicate key sequence file=0x" + Long.toHexString(fileNum)
		    + " sequence=0x" + Long.toHexString(sequence));
           ;  //this.hook177(fileNum, sequence, status);
	    //Label:           ;  //this.hook188(cursor);
      Label188:
// <<     private synchronized void insertFileSummary(...)
	    cursor.evict();
//	original(cursor);

	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	}
  }


  /**
   * 
   * Checks that all FSLN offsets are indeed obsolete.  Assumes that the system is quiesent (does not lock LNs).  This method is not synchronized (because it doesn't access fileSummaryMap) and eviction is allowed.
   * @return true if no verification failures.
   */
  // line 594 "../../../../UtilizationProfile.ump"
   public boolean verifyFileSummaryDatabase() throws DatabaseException{
    DatabaseEntry key = new DatabaseEntry();
	DatabaseEntry data = new DatabaseEntry();
	openFileSummaryDatabase();
	Locker locker = null;
	CursorImpl cursor = null;
	boolean ok = true;
	try {
	    locker = new BasicLocker(env);
	    cursor = new CursorImpl(fileSummaryDb, locker);
	    if (cursor.positionFirstOrLast(true, null)) {
		OperationStatus status = cursor.getCurrentAlreadyLatched(key, data, LockType.NONE, true);
		while (status == OperationStatus.SUCCESS) {
		    Label175:           ;  //this.hook175();
		    FileSummaryLN ln = (FileSummaryLN) cursor.getCurrentLN(LockType.NONE);
		    if (ln != null) {
			long fileNumVal = ln.getFileNumber(key.getData());
			PackedOffsets offsets = ln.getObsoleteOffsets();
			if (offsets != null) {
			    long[] vals = offsets.toArray();
			    for (int i = 0; i < vals.length; i++) {
				long lsn = DbLsn.makeLsn(fileNumVal, vals[i]);
				if (!verifyLsnIsObsolete(lsn)) {
				    ok = false;
				}
			    }
			}
			//Label:           ;  //this.hook190(cursor);
      Label190:
cursor.evict();
//	original(cursor);

			status = cursor.getNext(key, data, LockType.NONE, true, false);
		    }
		}
	    }
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	}
	return ok;
  }

  // line 638 "../../../../UtilizationProfile.ump"
   private boolean verifyLsnIsObsolete(long lsn) throws DatabaseException{
    try {
	    Object o = env.getLogManager().getLogEntry(lsn);
	    if (!(o instanceof LNLogEntry)) {
		return true;
	    }
	    LNLogEntry entry = (LNLogEntry) o;
	    if (entry.getLN().isDeleted()) {
		return true;
	    }
	    DatabaseId dbId = entry.getDbId();
	    DatabaseImpl db = env.getDbMapTree().getDb(dbId);
	    boolean b = db == null;
	    Label186:
b |= db.isDeleted();
			//return original(db, b);
 //b = Label:           ;  //this.hook186(db, b);
	    if (b) {
		return true;
	    }
	    BIN bin = null;
	    Label180:           ;  //this.hook180(lsn, entry, db, bin);
			Tree tree = db.getTree();
			TreeLocation location = new TreeLocation();
			boolean parentFound = tree.getParentBINForChildLN(location, entry.getKey(), entry.getDupKey(), entry.getLN(), false, true, false, false);
			bin = location.bin;
			int index = location.index;
			if (!parentFound) {
					throw new ReturnBoolean(true);
			}
			if (bin.isEntryKnownDeleted(index)) {
					throw new ReturnBoolean(true);
			}
			if (bin.getLsn(index) != lsn) {
					throw new ReturnBoolean(true);
			}
			System.err.println("lsn " + DbLsn.getNoFormatString(lsn) + " was found in tree.");
			throw new ReturnBoolean(false);
//end hook180

	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    Label180_1:
//try {	    //original(lsn, entry, db, bin);} finally {
	    if (bin != null) {
		bin.releaseLatch();
	    }
	//}
           ;
	    return r.value;
	}
  }


  /**
   * protected void hook173() throws DatabaseException {
   * }
   * 
   * protected void hook174() throws DatabaseException {
   * }
   * 
   * protected void hook175() throws DatabaseException {
   * }
   * 
   * protected void hook177(long fileNum, int sequence, OperationStatus status) throws DatabaseException {
   * }
   * 
   * protected void hook178(CursorImpl cursor) throws DatabaseException {
   * }
   * 
   * protected void hook179(CursorImpl cursor) throws DatabaseException {
   * }
   * 
   * protected void hook180(long lsn, LNLogEntry entry, DatabaseImpl db, BIN bin) throws DatabaseException {
   * Tree tree = db.getTree();
   * TreeLocation location = new TreeLocation();
   * boolean parentFound = tree.getParentBINForChildLN(location, entry.getKey(), entry.getDupKey(), entry.getLN(),
   * false, true, false, false);
   * bin = location.bin;
   * int index = location.index;
   * if (!parentFound) {
   * throw new ReturnBoolean(true);
   * 
   * if (bin.isEntryKnownDeleted(index)) {
   * throw new ReturnBoolean(true);
   * 
   * if (bin.getLsn(index) != lsn) {
   * throw new ReturnBoolean(true);
   * 
   * System.err.println("lsn " + DbLsn.getNoFormatString(lsn) + " was found in tree.");
   * throw new ReturnBoolean(false);
   * }
   * protected boolean hook186(DatabaseImpl db, boolean b) throws DatabaseException {
   * return b;
   * }
   * protected void hook187(CursorImpl cursor) throws DatabaseException {
   * }
   * protected void hook188(CursorImpl cursor) throws DatabaseException {
   * }
   */
  // line 730 "../../../../UtilizationProfile.ump"
   protected void hook189(CursorImpl cursor) throws DatabaseException{
    
  }

  // line 733 "../../../../UtilizationProfile.ump"
   protected void hook190(CursorImpl cursor) throws DatabaseException{
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "minUtilization" + ":" + getMinUtilization()+ "," +
            "minFileUtilization" + ":" + getMinFileUtilization()+ "," +
            "minAge" + ":" + getMinAge()+ "]";
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 4 "../../../../UtilizationProfile_static.ump"
  // line 30 "../../../../MemoryBudget_UtilizationProfile_inner.ump"
  public static class UtilizationProfile_clearCache
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_clearCache()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../UtilizationProfile_static.ump"
    public  UtilizationProfile_clearCache(UtilizationProfile _this){
      this._this=_this;
    }
  
    // line 9 "../../../../UtilizationProfile_static.ump"
    public void execute(){
      // line 32 "../../../../MemoryBudget_UtilizationProfile_inner.ump"
      memorySize=_this.fileSummaryMap.size() * MemoryBudget.UTILIZATION_PROFILE_ENTRY;
              mb=_this.env.getMemoryBudget();
              mb.updateMiscMemoryUsage(0 - memorySize);
              //original();
      // END OF UMPLE BEFORE INJECTION
      _this.fileSummaryMap=new TreeMap();
          _this.cachePopulated=false;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 12 "../../../../UtilizationProfile_static.ump"
    protected UtilizationProfile _this ;
  // line 13 "../../../../UtilizationProfile_static.ump"
    protected int memorySize ;
  // line 14 "../../../../UtilizationProfile_static.ump"
    protected MemoryBudget mb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 16 "../../../../UtilizationProfile_static.ump"
  // line 16 "../../../../MemoryBudget_UtilizationProfile_inner.ump"
  public static class UtilizationProfile_removeFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_removeFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 18 "../../../../UtilizationProfile_static.ump"
    public  UtilizationProfile_removeFile(UtilizationProfile _this, Long fileNum){
      this._this=_this;
          this.fileNum=fileNum;
    }
  
    // line 22 "../../../../UtilizationProfile_static.ump"
    public void execute() throws DatabaseException{
      synchronized (_this) {
            assert _this.cachePopulated;
            if (_this.fileSummaryMap.remove(fileNum) != null) {
              //Label:           ;  //this.hook192();
              Label192:
  mb=_this.env.getMemoryBudget();
          mb.updateMiscMemoryUsage(0 - MemoryBudget.UTILIZATION_PROFILE_ENTRY);
          //original();
  
            }
          }
          _this.deleteFileSummary(fileNum);
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 31 "../../../../UtilizationProfile_static.ump"
    protected UtilizationProfile _this ;
  // line 32 "../../../../UtilizationProfile_static.ump"
    protected Long fileNum ;
  // line 33 "../../../../UtilizationProfile_static.ump"
    protected MemoryBudget mb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 37 "../../../../UtilizationProfile_static.ump"
  // line 23 "../../../../MemoryBudget_UtilizationProfile_inner.ump"
  public static class UtilizationProfile_putFileSummary
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_putFileSummary()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 39 "../../../../UtilizationProfile_static.ump"
    public  UtilizationProfile_putFileSummary(UtilizationProfile _this, TrackedFileSummary tfs){
      this._this=_this;
          this.tfs=tfs;
    }
  
    // line 43 "../../../../UtilizationProfile_static.ump"
    public PackedOffsets execute() throws DatabaseException{
      if (_this.env.isReadOnly()) {
            throw new DatabaseException("Cannot write file summary in a read-only environment");
          }
          if (tfs.isEmpty()) {
            return null;
          }
          if (!_this.cachePopulated) {
            return null;
          }
          fileNum=tfs.getFileNumber();
          fileNumLong=new Long(fileNum);
          summary=(FileSummary)_this.fileSummaryMap.get(fileNumLong);
          if (summary == null) {
            file=new File(_this.env.getFileManager().getFullFileName(fileNum,FileManager.JE_SUFFIX));
            if (!file.exists()) {
              return null;
            }
            summary=new FileSummary();
          }
          tmp=new FileSummary();
          tmp.add(summary);
          tmp.add(tfs);
          sequence=tmp.getEntriesCounted();
          ln=new FileSummaryLN(summary);
          ln.setTrackedSummary(tfs);
          _this.insertFileSummary(ln,fileNum,sequence);
          summary=ln.getBaseSummary();
          if (_this.fileSummaryMap.put(fileNumLong,summary) == null) {
            //Label:           ;  //this.hook193();
            Label193:
  mb=_this.env.getMemoryBudget();
          mb.updateMiscMemoryUsage(MemoryBudget.UTILIZATION_PROFILE_ENTRY);
          //original();
  
          }
          return ln.getObsoleteOffsets();
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 76 "../../../../UtilizationProfile_static.ump"
    protected UtilizationProfile _this ;
  // line 77 "../../../../UtilizationProfile_static.ump"
    protected TrackedFileSummary tfs ;
  // line 78 "../../../../UtilizationProfile_static.ump"
    protected long fileNum ;
  // line 79 "../../../../UtilizationProfile_static.ump"
    protected Long fileNumLong ;
  // line 80 "../../../../UtilizationProfile_static.ump"
    protected FileSummary summary ;
  // line 81 "../../../../UtilizationProfile_static.ump"
    protected File file ;
  // line 82 "../../../../UtilizationProfile_static.ump"
    protected FileSummary tmp ;
  // line 83 "../../../../UtilizationProfile_static.ump"
    protected int sequence ;
  // line 84 "../../../../UtilizationProfile_static.ump"
    protected FileSummaryLN ln ;
  // line 85 "../../../../UtilizationProfile_static.ump"
    protected MemoryBudget mb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 89 "../../../../UtilizationProfile_static.ump"
  // line 4 "../../../../MemoryBudget_UtilizationProfile_inner.ump"
  // line 4 "../../../../Evictor_UtilizationProfile_inner.ump"
  // line 4 "../../../../Latches_UtilizationProfile_inner.ump"
  public static class UtilizationProfile_populateCache
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_populateCache()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 91 "../../../../UtilizationProfile_static.ump"
    public  UtilizationProfile_populateCache(UtilizationProfile _this){
      this._this=_this;
    }
  
    // line 94 "../../../../UtilizationProfile_static.ump"
    public boolean execute() throws DatabaseException{
      assert !_this.cachePopulated;
          if (!_this.openFileSummaryDatabase()) {
            return false;
          }
          //Label:           ;  //this.hook194();
          Label194:
  oldMemorySize=_this.fileSummaryMap.size() * MemoryBudget.UTILIZATION_PROFILE_ENTRY;
          //original();
  
          existingFiles=_this.env.getFileManager().getAllFileNumbers();
          locker=null;
          cursor=null;
          try {
            locker=new BasicLocker(_this.env);
            cursor=new CursorImpl(_this.fileSummaryDb,locker);
            keyEntry=new DatabaseEntry();
            dataEntry=new DatabaseEntry();
            if (cursor.positionFirstOrLast(true,null)) {
              status=cursor.getCurrentAlreadyLatched(keyEntry,dataEntry,LockType.NONE,true);
              if (status != OperationStatus.SUCCESS) {
                status=cursor.getNext(keyEntry,dataEntry,LockType.NONE,true,false);
              }
              while (status == OperationStatus.SUCCESS) {
                Label176:           ;  //this.hook176();
                ln=(FileSummaryLN)cursor.getCurrentLN(LockType.NONE);
                if (ln == null) {
                  status=cursor.getNext(keyEntry,dataEntry,LockType.NONE,true,false);
                  continue;
                }
                keyBytes=keyEntry.getData();
                isOldVersion=ln.hasStringKey(keyBytes);
                fileNum=ln.getFileNumber(keyBytes);
                fileNumLong=new Long(fileNum);
                if (Arrays.binarySearch(existingFiles,fileNumLong) >= 0) {
                  _this.fileSummaryMap.put(fileNumLong,ln.getBaseSummary());
                  if (isOldVersion) {
                    _this.insertFileSummary(ln,fileNum,0);
                    Label182:
  cursor.latchBIN();
          //original();
             ;  //this.hook182();
                    cursor.delete();
                    Label181:
  cursor.releaseBIN();
          //original();
             ;  //this.hook181();
                  }
     else {
                   // Label:           ;  //this.hook191();
                      Label191:
  cursor.evict();
          //original();
  
                  }
                }
     else {
                  _this.fileSummaryMap.remove(fileNumLong);
                  if (isOldVersion) {
                    Label184:
  cursor.latchBIN();
          //original();
             ;  //this.hook184();
                    cursor.delete();
                    Label183:
  cursor.releaseBIN();
          //original();
             ;  //this.hook183();
                  }
     else {
                    _this.deleteFileSummary(fileNumLong);
                  }
                }
                if (isOldVersion) {
                  status=cursor.getNext(keyEntry,dataEntry,LockType.NONE,true,false);
                }
     else {
                  if (!_this.getFirstFSLN(cursor,fileNum + 1,keyEntry,dataEntry,LockType.NONE)) {
                    status=OperationStatus.NOTFOUND;
                  }
                }
              }
            }
          }
      finally {
            if (cursor != null) {
              Label185:
  cursor.releaseBINs();
          //original();
             ;  //this.hook185();
              cursor.close();
            }
            if (locker != null) {
              locker.operationEnd();
            }
            //Label:           ;  //this.hook195();
            Label195:
  newMemorySize=_this.fileSummaryMap.size() * MemoryBudget.UTILIZATION_PROFILE_ENTRY;
          mb=_this.env.getMemoryBudget();
          mb.updateMiscMemoryUsage(newMemorySize - oldMemorySize);
          //original();
   ;
          }
          _this.cachePopulated=true;
          return true;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 173 "../../../../UtilizationProfile_static.ump"
    protected UtilizationProfile _this ;
  // line 174 "../../../../UtilizationProfile_static.ump"
    protected int oldMemorySize ;
  // line 175 "../../../../UtilizationProfile_static.ump"
    protected Long[] existingFiles ;
  // line 176 "../../../../UtilizationProfile_static.ump"
    protected Locker locker ;
  // line 177 "../../../../UtilizationProfile_static.ump"
    protected CursorImpl cursor ;
  // line 178 "../../../../UtilizationProfile_static.ump"
    protected DatabaseEntry keyEntry ;
  // line 179 "../../../../UtilizationProfile_static.ump"
    protected DatabaseEntry dataEntry ;
  // line 180 "../../../../UtilizationProfile_static.ump"
    protected OperationStatus status ;
  // line 181 "../../../../UtilizationProfile_static.ump"
    protected FileSummaryLN ln ;
  // line 182 "../../../../UtilizationProfile_static.ump"
    protected byte[] keyBytes ;
  // line 183 "../../../../UtilizationProfile_static.ump"
    protected boolean isOldVersion ;
  // line 184 "../../../../UtilizationProfile_static.ump"
    protected long fileNum ;
  // line 185 "../../../../UtilizationProfile_static.ump"
    protected Long fileNumLong ;
  // line 186 "../../../../UtilizationProfile_static.ump"
    protected int newMemorySize ;
  // line 187 "../../../../UtilizationProfile_static.ump"
    protected MemoryBudget mb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 46 "../../../../UtilizationProfile.ump"
  private EnvironmentImpl env ;
// line 48 "../../../../UtilizationProfile.ump"
  private UtilizationTracker tracker ;
// line 50 "../../../../UtilizationProfile.ump"
  private DatabaseImpl fileSummaryDb ;
// line 52 "../../../../UtilizationProfile.ump"
  private SortedMap fileSummaryMap ;
// line 54 "../../../../UtilizationProfile.ump"
  private boolean cachePopulated ;
// line 56 "../../../../UtilizationProfile.ump"
  private boolean rmwFixEnabled ;
// line 76 "../../../../UtilizationProfile.ump"
  private long[] forceCleanFiles ;

  
}