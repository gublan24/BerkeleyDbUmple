/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.incomp;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.PropUtil;
import com.sleepycat.je.utilint.DaemonThread;
import com.sleepycat.je.tree.Tree.SearchType;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.NodeNotEmptyException;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.Key;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.DBIN;
import com.sleepycat.je.tree.CursorsExistException;
import com.sleepycat.je.tree.BINReference;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.cleaner.TrackedFileSummary;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Level;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

// line 3 "../../../../INCompressor.ump"
// line 3 "../../../../INCompressor_inner.ump"
public class INCompressor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INCompressor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 48 "../../../../INCompressor.ump"
   public  INCompressor(EnvironmentImpl env, long waitTime, String name) throws DatabaseException{
    super(waitTime, name, env);
	this.env = env;
	lockTimeout = PropUtil
		.microsToMillis(env.getConfigManager().getLong(EnvironmentParams.COMPRESSOR_LOCK_TIMEOUT));
	binRefQueue = new HashMap();
	binRefQueueSync = new Object();
  }

  // line 57 "../../../../INCompressor.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("<INCompressor name=\"").append(name).append("\"/>");
	return sb.toString();
  }


  /**
   * 
   * The default daemon work queue is not used because we need a map, not a set.
   */
  // line 70 "../../../../INCompressor.ump"
   public void addToQueue(Object o) throws DatabaseException{
    throw new DatabaseException("INCompressor.addToQueue should never be called.");
  }

  // line 74 "../../../../INCompressor.ump"
   public int getBinRefQueueSize() throws DatabaseException{
    int size = 0;
	synchronized (binRefQueueSync) {
	    size = binRefQueue.size();
	}
	return size;
  }


  /**
   * 
   * Adds the BIN and deleted Key to the queue if the BIN is not already in the queue, or adds the deleted key to an existing entry if one exists.
   */
  // line 85 "../../../../INCompressor.ump"
   public void addBinKeyToQueue(BIN bin, Key deletedKey, boolean doWakeup) throws DatabaseException{
    synchronized (binRefQueueSync) {
	    addBinKeyToQueueAlreadyLatched(bin, deletedKey);
	}
	if (doWakeup) {
	    wakeup();
	}
  }


  /**
   * 
   * Adds the BINReference to the queue if the BIN is not already in the queue, or adds the deleted keys to an existing entry if one exists.
   */
  // line 97 "../../../../INCompressor.ump"
   public void addBinRefToQueue(BINReference binRef, boolean doWakeup) throws DatabaseException{
    synchronized (binRefQueueSync) {
	    addBinRefToQueueAlreadyLatched(binRef);
	}
	if (doWakeup) {
	    wakeup();
	}
  }


  /**
   * 
   * Adds an entire collection of BINReferences to the queue at once.  Use this to avoid latching for each add.
   */
  // line 109 "../../../../INCompressor.ump"
   public void addMultipleBinRefsToQueue(Collection binRefs, boolean doWakeup) throws DatabaseException{
    synchronized (binRefQueueSync) {
	    Iterator it = binRefs.iterator();
	    while (it.hasNext()) {
		BINReference binRef = (BINReference) it.next();
		addBinRefToQueueAlreadyLatched(binRef);
	    }
	}
	if (doWakeup) {
	    wakeup();
	}
  }


  /**
   * 
   * Adds the BINReference with the latch held.
   */
  // line 125 "../../../../INCompressor.ump"
   private void addBinRefToQueueAlreadyLatched(BINReference binRef){
    Long node = new Long(binRef.getNodeId());
	BINReference existingRef = (BINReference) binRefQueue.get(node);
	if (existingRef != null) {
	    existingRef.addDeletedKeys(binRef);
	} else {
	    binRefQueue.put(node, binRef);
	}
  }


  /**
   * 
   * Adds the BIN and deleted Key with the latch held.
   */
  // line 138 "../../../../INCompressor.ump"
   private void addBinKeyToQueueAlreadyLatched(BIN bin, Key deletedKey){
    Long node = new Long(bin.getNodeId());
	BINReference existingRef = (BINReference) binRefQueue.get(node);
	if (existingRef != null) {
	    if (deletedKey != null) {
		existingRef.addDeletedKey(deletedKey);
	    }
	} else {
	    BINReference binRef = bin.createReference();
	    if (deletedKey != null) {
		binRef.addDeletedKey(deletedKey);
	    }
	    binRefQueue.put(node, binRef);
	}
  }

  // line 154 "../../../../INCompressor.ump"
   public boolean exists(long nodeId){
    Long node = new Long(nodeId);
	synchronized (binRefQueueSync) {
	    return (binRefQueue.get(node) != null);
	}
  }

  // line 161 "../../../../INCompressor.ump"
   private BINReference removeCompressibleBinReference(long nodeId){
    Long node = new Long(nodeId);
	BINReference foundRef = null;
	synchronized (binRefQueueSync) {
	    BINReference target = (BINReference) binRefQueue.remove(node);
	    if (target != null) {
		if (target.deletedKeysExist()) {
		    foundRef = target;
		} else {
		    binRefQueue.put(node, target);
		}
	    }
	}
	return foundRef;
  }


  /**
   * 
   * Return the number of retries when a deadlock exception occurs.
   */
  // line 180 "../../../../INCompressor.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return env.getConfigManager().getInt(EnvironmentParams.COMPRESSOR_RETRY);
  }


  /**
   * 
   * Compresses a single BIN and then deletes the BIN if it is empty.
   * @param bin is latched when this method is called, and unlatched when itreturns.
   * @return true if the BINReference was requeued by this method.
   */
  // line 260 "../../../../INCompressor.ump"
   private boolean compressBin(DatabaseImpl db, BIN bin, BINReference binRef, UtilizationTracker tracker) throws DatabaseException{
    boolean empty = false;
	boolean requeued = false;
	byte[] idKey = bin.getIdentifierKey();
	byte[] dupKey = null;
	boolean isDBIN = bin.containsDuplicates();
	this.hook396(bin, binRef, empty, requeued, dupKey, isDBIN);
	if (empty) {
	    requeued = pruneBIN(db, binRef, idKey, isDBIN, dupKey, tracker);
	}
	return requeued;
  }


  /**
   * 
   * If the target BIN is empty, attempt to remove the empty branch of the  tree.
   * @return true if the pruning was unable to proceed and the BINReferencewas requeued.
   */
  // line 278 "../../../../INCompressor.ump"
   private boolean pruneBIN(DatabaseImpl dbImpl, BINReference binRef, byte [] idKey, boolean containsDups, byte [] dupKey, UtilizationTracker tracker) throws DatabaseException{
    boolean requeued = false;
	try {
	    Tree tree = dbImpl.getTree();
	    if (containsDups) {
		tree.deleteDup(idKey, dupKey, tracker);
	    } else {
		tree.delete(idKey, tracker);
	    }
	    this.hook406();
	} catch (NodeNotEmptyException NNEE) {
	    this.hook407();
	} catch (CursorsExistException e) {
	    addBinRefToQueue(binRef, false);
	    this.hook408();
	    requeued = true;
	}
	return requeued;
  }

  // line 299 "../../../../INCompressor.ump"
   private void checkForRelocatedSlots(DatabaseImpl db, BINReference binRef, UtilizationTracker tracker) throws DatabaseException{
    Iterator iter = binRef.getDeletedKeyIterator();
	if (iter != null) {
	    byte[] mainKey = binRef.getKey();
	    boolean isDup = (binRef.getData() != null);
	    while (iter.hasNext()) {
		Key key = (Key) iter.next();
		BIN splitBin = isDup ? searchForBIN(db, mainKey, key.getKey()) : searchForBIN(db, key.getKey(), null);
		if (splitBin != null) {
		    BINReference splitBinRef = splitBin.createReference();
		    splitBinRef.addDeletedKey(key);
		    compressBin(db, splitBin, splitBinRef, tracker);
		}
	    }
	}
  }

  // line 316 "../../../../INCompressor.ump"
   private boolean isRunnable() throws DatabaseException{
    return true;
  }


  /**
   * 
   * Search the tree for the BIN or DBIN that corresponds to this BINReference.
   * @param binRef the BINReference that indicates the bin we want.
   * @return the BIN or DBIN that corresponds to this BINReference. Thenode is latched upon return. Returns null if the BIN can't be found.
   */
  // line 325 "../../../../INCompressor.ump"
   public BIN searchForBIN(DatabaseImpl db, BINReference binRef) throws DatabaseException{
    return searchForBIN(db, binRef.getKey(), binRef.getData());
  }

  // line 329 "../../../../INCompressor.ump"
   private BIN searchForBIN(DatabaseImpl db, byte [] mainKey, byte [] dupKey) throws DatabaseException{
    try {
	    Tree tree = db.getTree();
	    IN in = tree.search(mainKey, SearchType.NORMAL, -1, null, false);
	    if (in == null) {
		return null;
	    }
	    if (dupKey == null) {
		return (BIN) in;
	    }
	    DIN duplicateRoot = null;
	    DBIN duplicateBin = null;
	    BIN bin = (BIN) in;
	    this.hook397(mainKey, dupKey, tree, duplicateRoot, duplicateBin, bin);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (BIN) r.value;
	}
  }


  /**
   * 
   * Lazily compress a single BIN. Do not do any pruning. The target IN should be latched when we enter, and it will be remain latched.
   */
  // line 352 "../../../../INCompressor.ump"
   public void lazyCompress(IN in) throws DatabaseException{
    if (!in.isCompressible()) {
	    return;
	}
	this.hook398(in);
	BIN bin = (BIN) in;
	int nCursors = bin.nCursors();
	if (nCursors > 0) {
	    return;
	} else {
	    BINReference binRef = removeCompressibleBinReference(bin.getNodeId());
	    if ((binRef == null) || (!binRef.deletedKeysExist())) {
		return;
	    } else {
		boolean requeued = bin.compress(binRef, false);
		this.hook409();
		if (!requeued && binRef.deletedKeysExist()) {
		    addBinRefToQueue(binRef, false);
		    this.hook410();
		} else {
		    if (bin.getNEntries() == 0) {
			addBinRefToQueue(binRef, false);
			this.hook411();
		    }
		}
	    }
	}
  }

  // line 382 "../../../../INCompressor.ump"
   private boolean findDBAndBIN(BINSearch binSearch, BINReference binRef, DbTree dbTree, Map dbCache) throws DatabaseException{
    binSearch.db = dbTree.getDb(binRef.getDatabaseId(), lockTimeout, dbCache);
	boolean close = binSearch.db == null;
	close = this.hook415(binSearch, close);
	if (close) {
	    this.hook412();
	    return false;
	}
	this.hook391();
	binSearch.bin = searchForBIN(binSearch.db, binRef);
	if ((binSearch.bin == null) || binSearch.bin.getNodeId() != binRef.getNodeId()) {
	    this.hook399(binSearch);
	    this.hook413();
	    return false;
	}
	return true;
  }

  // line 400 "../../../../INCompressor.ump"
   protected void hook391() throws DatabaseException{
    
  }

  // line 403 "../../../../INCompressor.ump"
   protected void hook392(int binQueueSize) throws DatabaseException{
    
  }

  // line 406 "../../../../INCompressor.ump"
   protected void hook393() throws DatabaseException{
    
  }

  // line 409 "../../../../INCompressor.ump"
   protected void hook394(BIN foundBin) throws DatabaseException{
    
  }

  // line 412 "../../../../INCompressor.ump"
   protected void hook395() throws DatabaseException{
    
  }

  // line 416 "../../../../INCompressor.ump"
   protected void hook396(BIN bin, BINReference binRef, boolean empty, boolean requeued, byte [] dupKey, boolean isDBIN) throws DatabaseException{
    int nCursors = bin.nCursors();
	if (nCursors > 0) {
	    addBinRefToQueue(binRef, false);
	    requeued = true;
	    this.hook414();
	} else {
	    requeued = bin.compress(binRef, true);
	    if (!requeued) {
		empty = (bin.getNEntries() == 0);
		if (empty) {
		    if (isDBIN) {
			dupKey = ((DBIN) bin).getDupKey();
		    }
		}
	    }
	}
  }

  // line 436 "../../../../INCompressor.ump"
   protected void hook397(byte [] mainKey, byte [] dupKey, Tree tree, DIN duplicateRoot, DBIN duplicateBin, BIN bin) throws DatabaseException{
    int index = bin.findEntry(mainKey, false, true);
	if (index >= 0) {
	    Node node = null;
	    if (!bin.isEntryKnownDeleted(index)) {
		node = bin.fetchTarget(index);
	    }
	    if (node == null) {
		this.hook400(bin);
		throw new ReturnObject(null);
	    }
	    if (node.containsDuplicates()) {
		duplicateRoot = (DIN) node;
		this.hook401(duplicateRoot, bin);
		duplicateBin = (DBIN) tree.searchSubTree(duplicateRoot, dupKey, SearchType.NORMAL, -1, null, false);
		throw new ReturnObject(duplicateBin);
	    } else {
		throw new ReturnObject(bin);
	    }
	} else {
	    this.hook402(bin);
	    throw new ReturnObject(null);
	}
  }

  // line 461 "../../../../INCompressor.ump"
   protected void hook398(IN in) throws DatabaseException{
    
  }

  // line 464 "../../../../INCompressor.ump"
   protected void hook399(BINSearch binSearch) throws DatabaseException{
    
  }

  // line 467 "../../../../INCompressor.ump"
   protected void hook400(BIN bin) throws DatabaseException{
    
  }

  // line 470 "../../../../INCompressor.ump"
   protected void hook401(DIN duplicateRoot, BIN bin) throws DatabaseException{
    
  }

  // line 473 "../../../../INCompressor.ump"
   protected void hook402(BIN bin) throws DatabaseException{
    
  }

  // line 476 "../../../../INCompressor.ump"
   protected void hook403() throws DatabaseException{
    
  }

  // line 479 "../../../../INCompressor.ump"
   protected void hook404() throws DatabaseException{
    
  }

  // line 482 "../../../../INCompressor.ump"
   protected void hook405() throws DatabaseException{
    
  }

  // line 485 "../../../../INCompressor.ump"
   protected void hook406() throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 488 "../../../../INCompressor.ump"
   protected void hook407() throws DatabaseException{
    
  }

  // line 491 "../../../../INCompressor.ump"
   protected void hook408() throws DatabaseException{
    
  }

  // line 494 "../../../../INCompressor.ump"
   protected void hook409() throws DatabaseException{
    
  }

  // line 497 "../../../../INCompressor.ump"
   protected void hook410() throws DatabaseException{
    
  }

  // line 500 "../../../../INCompressor.ump"
   protected void hook411() throws DatabaseException{
    
  }

  // line 503 "../../../../INCompressor.ump"
   protected void hook412() throws DatabaseException{
    
  }

  // line 506 "../../../../INCompressor.ump"
   protected void hook413() throws DatabaseException{
    
  }

  // line 509 "../../../../INCompressor.ump"
   protected void hook414() throws DatabaseException{
    
  }

  // line 512 "../../../../INCompressor.ump"
   protected boolean hook415(BINSearch binSearch, boolean close) throws DatabaseException{
    return close;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.incomp;
  
  // line 4 "../../../../INCompressor_inner.ump"
  public static class BINSearch
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public BINSearch()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../INCompressor_inner.ump"
    public DatabaseImpl db ;
  // line 6 "../../../../INCompressor_inner.ump"
    public BIN bin ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 35 "../../../../INCompressor.ump"
  private static final String TRACE_COMPRESS = "INCompress:" ;
// line 37 "../../../../INCompressor.ump"
  private static final boolean DEBUG = false ;
// line 39 "../../../../INCompressor.ump"
  private EnvironmentImpl env ;
// line 41 "../../../../INCompressor.ump"
  private long lockTimeout ;
// line 43 "../../../../INCompressor.ump"
  private Map binRefQueue ;
// line 45 "../../../../INCompressor.ump"
  private Object binRefQueueSync ;

// line 62 "../../../../INCompressor.ump"
  synchronized public void clearEnv () 
  {
    env = null;
  }

// line 183 "../../../../INCompressor.ump"
  public synchronized void onWakeup () throws DatabaseException 
  {
    if (env.isClosed()) {
	    return;
	}
	this.hook403();
	doCompress();
  }

// line 194 "../../../../INCompressor.ump"
  public synchronized void doCompress () throws DatabaseException 
  {
    if (!isRunnable()) {
	    return;
	}
	Map queueSnapshot = null;
	int binQueueSize = 0;
	synchronized (binRefQueueSync) {
	    binQueueSize = binRefQueue.size();
	    if (binQueueSize > 0) {
		queueSnapshot = binRefQueue;
		binRefQueue = new HashMap();
	    }
	}
	if (binQueueSize > 0) {
	    this.hook404();
	    this.hook392(binQueueSize);
	    this.hook393();
	    UtilizationTracker tracker = new UtilizationTracker(env);
	    Map dbCache = new HashMap();
	    DbTree dbTree = env.getDbMapTree();
	    BINSearch binSearch = new BINSearch();
	    try {
		Iterator it = queueSnapshot.values().iterator();
		while (it.hasNext()) {
		    if (env.isClosed()) {
			return;
		    }
		    BINReference binRef = (BINReference) it.next();
		    if (!findDBAndBIN(binSearch, binRef, dbTree, dbCache)) {
			continue;
		    }
		    if (binRef.deletedKeysExist()) {
			boolean requeued = compressBin(binSearch.db, binSearch.bin, binRef, tracker);
			if (!requeued) {
			    checkForRelocatedSlots(binSearch.db, binRef, tracker);
			}
		    } else {
			BIN foundBin = binSearch.bin;
			byte[] idKey = foundBin.getIdentifierKey();
			boolean isDBIN = foundBin.containsDuplicates();
			byte[] dupKey = null;
			if (isDBIN) {
			    dupKey = ((DBIN) foundBin).getDupKey();
			}
			this.hook394(foundBin);
			pruneBIN(binSearch.db, binRef, idKey, isDBIN, dupKey, tracker);
		    }
		}
		TrackedFileSummary[] summaries = tracker.getTrackedFiles();
		if (summaries.length > 0) {
		    env.getUtilizationProfile().countAndLogSummaries(summaries);
		}
	    } finally {
		this.hook395();
		this.hook405();
	    }
	}
  }

  
}