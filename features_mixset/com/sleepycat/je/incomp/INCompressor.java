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
import com.sleepycat.je.utilint.*;

// line 3 "../../../../INCompressor_INCompressor.ump"
// line 3 "../../../../INCompressor_INCompressor_inner.ump"
public class INCompressor extends DaemonThread
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INCompressor()
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

  // line 48 "../../../../INCompressor_INCompressor.ump"
   public  INCompressor(EnvironmentImpl env, long waitTime, String name) throws DatabaseException{
    super(waitTime, name, env);
	this.env = env;
	lockTimeout = PropUtil
		.microsToMillis(env.getConfigManager().getLong(EnvironmentParams.COMPRESSOR_LOCK_TIMEOUT));
	binRefQueue = new HashMap();
	binRefQueueSync = new Object();
  }

  // line 57 "../../../../INCompressor_INCompressor.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("<INCompressor name=\"").append(name).append("\"/>");
	return sb.toString();
  }


  /**
   * 
   * The default daemon work queue is not used because we need a map, not a set.
   */
  // line 70 "../../../../INCompressor_INCompressor.ump"
   public void addToQueue(Object o) throws DatabaseException{
    throw new DatabaseException("INCompressor.addToQueue should never be called.");
  }

  // line 74 "../../../../INCompressor_INCompressor.ump"
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
  // line 85 "../../../../INCompressor_INCompressor.ump"
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
  // line 97 "../../../../INCompressor_INCompressor.ump"
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
  // line 109 "../../../../INCompressor_INCompressor.ump"
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
  // line 125 "../../../../INCompressor_INCompressor.ump"
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
  // line 138 "../../../../INCompressor_INCompressor.ump"
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

  // line 154 "../../../../INCompressor_INCompressor.ump"
   public boolean exists(long nodeId){
    Long node = new Long(nodeId);
	synchronized (binRefQueueSync) {
	    return (binRefQueue.get(node) != null);
	}
  }

  // line 161 "../../../../INCompressor_INCompressor.ump"
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
  // line 180 "../../../../INCompressor_INCompressor.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return env.getConfigManager().getInt(EnvironmentParams.COMPRESSOR_RETRY);
  }


  /**
   * 
   * Compresses a single BIN and then deletes the BIN if it is empty.
   * @param bin is latched when this method is called, and unlatched when itreturns.
   * @return true if the BINReference was requeued by this method.
   */
  // line 260 "../../../../INCompressor_INCompressor.ump"
   private boolean compressBin(DatabaseImpl db, BIN bin, BINReference binRef, UtilizationTracker tracker) throws DatabaseException{
    boolean empty = false;
			boolean requeued = false;
			byte[] idKey = bin.getIdentifierKey();
			byte[] dupKey = null;
			boolean isDBIN = bin.containsDuplicates();
			Label396: //this.hook396(bin, binRef, empty, requeued, dupKey, isDBIN);
			int nCursors = bin.nCursors();
			if (nCursors > 0) {
					addBinRefToQueue(binRef, false);
					requeued = true;
					Label414: //this.hook414();
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
			// End of 396
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
  // line 295 "../../../../INCompressor_INCompressor.ump"
   private boolean pruneBIN(DatabaseImpl dbImpl, BINReference binRef, byte [] idKey, boolean containsDups, byte [] dupKey, UtilizationTracker tracker) throws DatabaseException{
    boolean requeued = false;
			try {
					Tree tree = dbImpl.getTree();
					if (containsDups) {
				tree.deleteDup(idKey, dupKey, tracker);
					} else {
				tree.delete(idKey, tracker);
					}
					Label406: //this.hook406();
			} catch (NodeNotEmptyException NNEE) {
					Label407: //this.hook407();
			} catch (CursorsExistException e) {
					addBinRefToQueue(binRef, false);
					Label408: //this.hook408();
					requeued = true;
			}
			return requeued;
  }

  // line 316 "../../../../INCompressor_INCompressor.ump"
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

  // line 333 "../../../../INCompressor_INCompressor.ump"
   private boolean isRunnable() throws DatabaseException{
    return true;
  }


  /**
   * 
   * Search the tree for the BIN or DBIN that corresponds to this BINReference.
   * @param binRef the BINReference that indicates the bin we want.
   * @return the BIN or DBIN that corresponds to this BINReference. Thenode is latched upon return. Returns null if the BIN can't be found.
   */
  // line 342 "../../../../INCompressor_INCompressor.ump"
   public BIN searchForBIN(DatabaseImpl db, BINReference binRef) throws DatabaseException{
    return searchForBIN(db, binRef.getKey(), binRef.getData());
  }

  // line 346 "../../../../INCompressor_INCompressor.ump"
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
	    Label397: //this.hook397(mainKey, dupKey, tree, duplicateRoot, duplicateBin, bin);
      int index = bin.findEntry(mainKey, false, true);
			if (index >= 0) {
					Node node = null;
					if (!bin.isEntryKnownDeleted(index)) {
				node = bin.fetchTarget(index);
					}
					if (node == null) {
				Label400: //this.hook400(bin);
				throw new ReturnObject(null);
					}
					if (node.containsDuplicates()) {
				duplicateRoot = (DIN) node;
				Label401: //this.hook401(duplicateRoot, bin);
				duplicateBin = (DBIN) tree.searchSubTree(duplicateRoot, dupKey, SearchType.NORMAL, -1, null, false);
				throw new ReturnObject(duplicateBin);
					} else {
				throw new ReturnObject(bin);
					}
			} else {
					Label402: //this.hook402(bin);
					throw new ReturnObject(null);
			}
      //End of hook397
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (BIN) r.value;
	}
  }


  /**
   * 
   * Lazily compress a single BIN. Do not do any pruning. The target IN should be latched when we enter, and it will be remain latched.
   */
  // line 392 "../../../../INCompressor_INCompressor.ump"
   public void lazyCompress(IN in) throws DatabaseException{
    if (!in.isCompressible()) {
					return;
			}
			Label398: //this.hook398(in);
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
				Label409://this.hook409();
				if (!requeued && binRef.deletedKeysExist()) {
						addBinRefToQueue(binRef, false);
						Label410: //this.hook410();
				} else {
						if (bin.getNEntries() == 0) {
					addBinRefToQueue(binRef, false);
					Label411: //this.hook411();
						}
				}
					}
			}
  }

  // line 422 "../../../../INCompressor_INCompressor.ump"
   private boolean findDBAndBIN(BINSearch binSearch, BINReference binRef, DbTree dbTree, Map dbCache) throws DatabaseException{
    binSearch.db = dbTree.getDb(binRef.getDatabaseId(), lockTimeout, dbCache);
			boolean close = binSearch.db == null;
			Label415: //close = this.hook415(binSearch, close);
			if (close) {
					Label412: //this.hook412();
					return false;
			}
			Label391: //this.hook391();
			binSearch.bin = searchForBIN(binSearch.db, binRef);
			if ((binSearch.bin == null) || binSearch.bin.getNodeId() != binRef.getNodeId()) {
					Label399: //this.hook399(binSearch);
					Label413: //this.hook413();
					return false;
			}
			return true;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../INCompressor_INCompressor_inner.ump"
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
    
    // line 5 "../../../../INCompressor_INCompressor_inner.ump"
    public DatabaseImpl db ;
  // line 6 "../../../../INCompressor_INCompressor_inner.ump"
    public BIN bin ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 35 "../../../../INCompressor_INCompressor.ump"
  private static final String TRACE_COMPRESS = "INCompress:" ;
// line 37 "../../../../INCompressor_INCompressor.ump"
  private static final boolean DEBUG = false ;
// line 39 "../../../../INCompressor_INCompressor.ump"
  private EnvironmentImpl env ;
// line 41 "../../../../INCompressor_INCompressor.ump"
  private long lockTimeout ;
// line 43 "../../../../INCompressor_INCompressor.ump"
  private Map binRefQueue ;
// line 45 "../../../../INCompressor_INCompressor.ump"
  private Object binRefQueueSync ;

// line 62 "../../../../INCompressor_INCompressor.ump"
  synchronized public void clearEnv () 
  {
    env = null;
  }

// line 183 "../../../../INCompressor_INCompressor.ump"
  public synchronized void onWakeup () throws DatabaseException 
  {
    if (env.isClosed()) {
					return;
			}
			Label403: //this.hook403();
			doCompress();
  }

// line 194 "../../../../INCompressor_INCompressor.ump"
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
					Label404: //this.hook404();
					Label392: //this.hook392(binQueueSize);
					Label393: //this.hook393();
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
					Label394: //this.hook394(foundBin);
					pruneBIN(binSearch.db, binRef, idKey, isDBIN, dupKey, tracker);
						}
				}
				TrackedFileSummary[] summaries = tracker.getTrackedFiles();
				if (summaries.length > 0) {
						env.getUtilizationProfile().countAndLogSummaries(summaries);
				}
					} finally {
				Label395: //this.hook395();
				Label405: //this.hook405();
					}
			}
  }

  
}