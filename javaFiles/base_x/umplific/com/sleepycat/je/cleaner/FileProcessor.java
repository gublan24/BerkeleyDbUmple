/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.DaemonThread;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.txn.LockResult;
import com.sleepycat.je.txn.LockGrantType;
import com.sleepycat.je.txn.BasicLocker;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.TreeLocation;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.SearchResult;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.log.CleanerFileReader;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Level;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.io.IOException;
import com.sleepycat.je.utilint.*;

// line 3 "../../../../FileProcessor.ump"
public class FileProcessor extends DaemonThread
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileProcessor()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 61 "../../../../FileProcessor.ump"
  public  FileProcessor(String name, EnvironmentImpl env, Cleaner cleaner, UtilizationProfile profile, FileSelector fileSelector){
    super(0, name, env);
	this.env = env;
	this.cleaner = cleaner;
	this.fileSelector = fileSelector;
	this.profile = profile;
  }

  // line 69 "../../../../FileProcessor.ump"
   public void clearEnv(){
    env = null;
	cleaner = null;
	fileSelector = null;
	profile = null;
  }


  /**
   * 
   * Return the number of retries when a deadlock exception occurs.
   */
  // line 79 "../../../../FileProcessor.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return cleaner.nDeadlockRetries;
  }


  /**
   * 
   * Cleaner doesn't have a work queue so just throw an exception if it's ever called.
   */
  // line 86 "../../../../FileProcessor.ump"
   public void addToQueue(Object o) throws DatabaseException{
    throw new DatabaseException("Cleaner.addToQueue should never be called.");
  }


  /**
   * 
   * Activates the cleaner. Is normally called when je.cleaner.byteInterval bytes are written to the log.
   */
  // line 93 "../../../../FileProcessor.ump"
   public void onWakeup() throws DatabaseException{
    doClean(true, true, false);
  }


  /**
   * 
   * Process all log entries in the given file. Note that we check for obsolete entries using the active TFS (TrackedFileSummary) for a file while it is being processed, and we prohibit flushing (eviction) of that offset information until file processing is complete. An entry could become obsolete because: 1- normal application activity deletes or updates the entry, 2- proactive migration migrates the entry before we process it, or 3- if trackDetail is false. However, checking the TFS is expensive if it has many entries, because we perform a linear search. There is a tradeoff between the cost of the TFS lookup and its benefit, which is to avoid a tree search if the entry is obsolete. Note that many more lookups for non-obsolete entries than obsolete entries will typically be done. In spite of that we check the tracked summary to avoid the situation where eviction does proactive migration, and evicts a BIN that is very soon afterward fetched during cleaning.
   * @return false if we aborted file processing because the environment isbeing closed.
   */
  // line 170 "../../../../FileProcessor.ump"
   private boolean processFile(Long fileNum) throws DatabaseException,IOException{
    return new FileProcessor_processFile(this, fileNum).execute();
  }


  /**
   * 
   * Processes the first LN in the look ahead cache and removes it from the cache. While the BIN is latched, look through the BIN for other LNs in the cache; if any match, process them to avoid a tree search later.
   * @param info
   * @param offset
   */
  // line 180 "../../../../FileProcessor.ump"
   private void processLN(Long fileNum, TreeLocation location, Long offset, LNInfo info, Object lookAheadCachep, Map dbCache) throws DatabaseException{
    new FileProcessor_processLN(this, fileNum, location, offset, info, lookAheadCachep, dbCache).execute();
  }


  /**
   * 
   * Processes an LN that was found in the tree. Lock the LN's node ID and then set the entry's MIGRATE flag if the LSN of the LN log entry is the active LSN in the tree.
   * @param infoidentifies the LN log entry.
   * @param logLsnis the LSN of the log entry.
   * @param treeLsnis the LSN found in the tree.
   * @param binis the BIN found in the tree; is latched on method entry andexit.
   * @param indexis the BIN index found in the tree.
   * @param parentDINis non-null for a DupCountLN only; if non-null, is latched onmethod entry and exit.
   */
  // line 194 "../../../../FileProcessor.ump"
   private void processFoundLN(LNInfo info, long logLsn, long treeLsn, BIN bin, int index, DIN parentDIN) throws DatabaseException{
    LN ln = info.getLN();
	byte[] key = info.getKey();
	byte[] dupKey = info.getDupKey();
	DatabaseImpl db = bin.getDatabase();
	boolean isDupCountLN = parentDIN != null;
	boolean obsolete = false;
	boolean migrated = false;
	boolean lockDenied = false;
	boolean completed = false;
	long nodeId = ln.getNodeId();
	BasicLocker locker = null;
	try {
	    Tree tree = db.getTree();
	    assert tree != null;
	    if (treeLsn != logLsn) {
		locker = new BasicLocker(env);
		LockResult lockRet = locker.nonBlockingLock(nodeId, LockType.READ, db);
		if (lockRet.getLockGrant() == LockGrantType.DENIED) {
		    this.hook142();
		    lockDenied = true;
		} else {
		    this.hook143();
		    obsolete = true;
		}
	    }
	    if (!obsolete && !lockDenied) {
		if (isDupCountLN) {
		    ChildReference dclRef = parentDIN.getDupCountLNRef();
		    dclRef.setMigrate(true);
		    parentDIN.setDirty(true);
		    if (treeLsn == logLsn && dclRef.getTarget() == null) {
			ln.postFetchInit(db, logLsn);
			parentDIN.updateDupCountLN(ln);
		    }
		} else {
		    bin.setMigrate(index, true);
		    bin.setDirty(true);
		    if (treeLsn == logLsn && bin.getTarget(index) == null) {
			ln.postFetchInit(db, logLsn);
			bin.updateEntry(index, ln);
		    }
		    if (PROHIBIT_DELTAS_WHEN_FETCHING && bin.getGeneration() == 0) {
			bin.setProhibitNextDelta();
		    }
		    bin.setGeneration();
		}
		this.hook144();
		migrated = true;
	    }
	    completed = true;
	} finally {
	    if (locker != null) {
		locker.operationEnd();
	    }
	    if (completed && lockDenied) {
		fileSelector.addPendingLN(ln, db.getId(), key, dupKey);
	    }
	    this.hook124(logLsn, ln, obsolete, migrated, completed);
	}
  }


  /**
   * 
   * If an IN is still in use in the in-memory tree, dirty it. The checkpoint invoked at the end of the cleaning run will end up rewriting it.
   */
  // line 259 "../../../../FileProcessor.ump"
   private void processIN(IN inClone, DatabaseImpl db, long lsn) throws DatabaseException{
    try {
	    boolean obsolete = false;
	    boolean dirtied = false;
	    boolean completed = false;
	    this.hook125(inClone, db, lsn, obsolete, dirtied, completed);
	} catch (ReturnVoid r) {
	    return;
	}
  }


  /**
   * 
   * Given a clone of an IN that has been taken out of the log, try to find it in the tree and verify that it is the current one in the log. Returns the node in the tree if it is found and it is current re: LSN's. Otherwise returns null if the clone is not found in the tree or it's not the latest version. Caller is responsible for unlatching the returned IN.
   */
  // line 273 "../../../../FileProcessor.ump"
   private IN findINInTree(Tree tree, DatabaseImpl db, IN inClone, long lsn) throws DatabaseException{
    try {
	    if (inClone.isDbRoot()) {
		IN rootIN = isRoot(tree, db, inClone, lsn);
		if (rootIN == null) {
		    return null;
		} else {
		    return rootIN;
		}
	    }
	    inClone.latch(Cleaner.UPDATE_GENERATION);
	    SearchResult result = null;
	    this.hook134(tree, db, inClone, lsn, result);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (IN) r.value;
	}
  }


  /**
   * 
   * Check if the cloned IN is the same node as the root in tree. Return the real root if it is, null otherwise. If non-null is returned, the returned IN (the root) is latched -- caller is responsible for unlatching it.
   */
  // line 295 "../../../../FileProcessor.ump"
   private IN isRoot(Tree tree, DatabaseImpl db, IN inClone, long lsn) throws DatabaseException{
    RootDoWork rdw = new RootDoWork(db, inClone, lsn);
	return tree.withRootLatchedShared(rdw);
  }


  /**
   * 
   * XXX: Was this intended to override Thread.toString()? If so it no longer does, because we separated Thread from DaemonThread.
   */
  // line 303 "../../../../FileProcessor.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("<Cleaner name=\"").append(name).append("\"/>");
	return sb.toString();
  }

  // line 309 "../../../../FileProcessor.ump"
   protected void hook121(String traceMsg) throws DatabaseException,IOException{
    
  }

  // line 312 "../../../../FileProcessor.ump"
   protected void hook122(IOException IOE) throws DatabaseException{
    
  }

  // line 315 "../../../../FileProcessor.ump"
   protected void hook123(String traceMsg) throws DatabaseException{
    
  }

  // line 319 "../../../../FileProcessor.ump"
   protected void hook124(long logLsn, LN ln, boolean obsolete, boolean migrated, boolean completed) throws DatabaseException{
    
  }

  // line 323 "../../../../FileProcessor.ump"
   protected void hook125(IN inClone, DatabaseImpl db, long lsn, boolean obsolete, boolean dirtied, boolean completed) throws DatabaseException{
    boolean b = db == null;
	b = this.hook159(db, b);
	if (b) {
	    this.hook160(db);
	    this.hook151();
	    obsolete = true;
	    completed = true;
	    throw new ReturnVoid();
	}
	Tree tree = db.getTree();
	assert tree != null;
	IN inInTree = findINInTree(tree, db, inClone, lsn);
	if (inInTree == null) {
	    this.hook152();
	    obsolete = true;
	} else {
	    this.hook153();
	    inInTree.setDirty(true);
	    inInTree.setProhibitNextDelta();
	    this.hook136(inInTree);
	    dirtied = true;
	}
	completed = true;
  }

  // line 350 "../../../../FileProcessor.ump"
   protected void hook134(Tree tree, DatabaseImpl db, IN inClone, long lsn, SearchResult result) throws DatabaseException{
    result = tree.getParentINForChildIN(inClone, true, Cleaner.UPDATE_GENERATION, inClone.getLevel(), null);
	if (!result.exactParentFound) {
	    throw new ReturnObject(null);
	}
	int compareVal = DbLsn.compareTo(result.parent.getLsn(result.index), lsn);
	if (compareVal > 0) {
	    throw new ReturnObject(null);
	} else {
	    IN in;
	    if (compareVal == 0) {
		in = (IN) result.parent.getTarget(result.index);
		if (in == null) {
		    in = inClone;
		    in.postFetchInit(db, lsn);
		    result.parent.updateEntry(result.index, in);
		}
	    } else {
		in = (IN) result.parent.fetchTarget(result.index);
	    }
	    in.latch(Cleaner.UPDATE_GENERATION);
	    throw new ReturnObject(in);
	}
  }

  // line 375 "../../../../FileProcessor.ump"
   protected void hook136(IN inInTree) throws DatabaseException{
    
  }

  // line 378 "../../../../FileProcessor.ump"
   protected void hook138() throws DatabaseException{
    
  }

  // line 381 "../../../../FileProcessor.ump"
   protected String hook139(String traceMsg) throws DatabaseException,IOException{
    return traceMsg;
  }

  // line 385 "../../../../FileProcessor.ump"
   protected void hook140() throws DatabaseException,IOException{
    
  }

  // line 388 "../../../../FileProcessor.ump"
   protected String hook141(String traceMsg) throws DatabaseException{
    return traceMsg;
  }

  // line 392 "../../../../FileProcessor.ump"
   protected void hook142() throws DatabaseException{
    
  }

  // line 395 "../../../../FileProcessor.ump"
   protected void hook143() throws DatabaseException{
    
  }

  // line 398 "../../../../FileProcessor.ump"
   protected void hook144() throws DatabaseException{
    
  }

  // line 401 "../../../../FileProcessor.ump"
   protected void hook151() throws DatabaseException{
    
  }

  // line 404 "../../../../FileProcessor.ump"
   protected void hook152() throws DatabaseException{
    
  }

  // line 407 "../../../../FileProcessor.ump"
   protected void hook153() throws DatabaseException{
    
  }

  // line 410 "../../../../FileProcessor.ump"
   protected boolean hook159(DatabaseImpl db, boolean b) throws DatabaseException{
    return b;
  }

  // line 414 "../../../../FileProcessor.ump"
   protected void hook160(DatabaseImpl db) throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 42 "../../../../FileProcessor.ump"
  private static final int PROCESS_PENDING_EVERY_N_LNS = 100 ;
// line 47 "../../../../FileProcessor.ump"
  private static final boolean PROHIBIT_DELTAS_WHEN_FETCHING = false ;
// line 49 "../../../../FileProcessor.ump"
  private static final boolean DEBUG_TRACING = false ;
// line 51 "../../../../FileProcessor.ump"
  private EnvironmentImpl env ;
// line 53 "../../../../FileProcessor.ump"
  private Cleaner cleaner ;
// line 55 "../../../../FileProcessor.ump"
  private FileSelector fileSelector ;
// line 57 "../../../../FileProcessor.ump"
  private UtilizationProfile profile ;

// line 103 "../../../../FileProcessor.ump"
  public synchronized int doClean (boolean invokedFromDaemon, boolean cleanMultipleFiles, boolean forceCleaning)
	    throws DatabaseException 
  {
    if (env.isClosed()) {
	    return 0;
	}
	int nOriginalLogFiles = profile.getNumberOfFiles();
	int nFilesCleaned = 0;
	while (true) {
	    if (nFilesCleaned >= nOriginalLogFiles) {
		break;
	    }
	    if (env.isClosing()) {
		break;
	    }
	    cleaner.processPending();
	    cleaner.deleteSafeToDeleteFiles();
	    boolean needLowUtilizationSet = cleaner.clusterResident || cleaner.clusterAll;
	    Long fileNum = fileSelector.selectFileForCleaning(profile, forceCleaning, needLowUtilizationSet,
		    cleaner.maxBatchFiles);
	    cleaner.updateReadOnlyFileCollections();
	    if (fileNum == null) {
		break;
	    }
	    this.hook138();
	    boolean finished = false;
	    long fileNumValue = fileNum.longValue();
	    int runId = ++cleaner.nCleanerRuns;
	    try {
		String traceMsg = "CleanerRun " + runId + " on file 0x" + Long.toHexString(fileNumValue);
		traceMsg = this.hook139(traceMsg);
		this.hook121(traceMsg);
		if (DEBUG_TRACING) {
		    System.out.println("\n" + traceMsg);
		}
		if (processFile(fileNum)) {
		    fileSelector.addCleanedFile(fileNum);
		    nFilesCleaned += 1;
		    this.hook140();
		    finished = true;
		}
	    } catch (IOException IOE) {
		this.hook122(IOE);
		throw new DatabaseException(IOE);
	    } finally {
		if (!finished) {
		    fileSelector.putBackFileForCleaning(fileNum);
		}
		String traceMsg = "CleanerRun " + runId + " on file 0x" + Long.toHexString(fileNumValue)
			+ " invokedFromDaemon=" + invokedFromDaemon + " finished=" + finished;
		traceMsg = this.hook141(traceMsg);
		this.hook123(traceMsg);
		if (DEBUG_TRACING) {
		    System.out.println("\n" + traceMsg);
		}
	    }
	    if (!cleanMultipleFiles) {
		break;
	    }
	}
	return nFilesCleaned;
  }

  
}