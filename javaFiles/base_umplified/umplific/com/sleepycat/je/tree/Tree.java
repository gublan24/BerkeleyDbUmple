/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.TestHookExecute;
import com.sleepycat.je.utilint.TestHook;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.txn.WriteLockInfo;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.txn.LockResult;
import com.sleepycat.je.txn.LockGrantType;
import com.sleepycat.je.txn.BasicLocker;
import com.sleepycat.je.recovery.RecoveryManager;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.dbi.INList;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.CursorImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../Tree.ump"
// line 3 "../../../../Tree_static.ump"
public class Tree implements LogWritable,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a new tree.
   */
  // line 76 "../../../../Tree.ump"
   public  Tree(DatabaseImpl database) throws DatabaseException{
    init(database);
	setDatabase(database);
  }


  /**
   * 
   * Create a tree that's being read in from the log.
   */
  // line 84 "../../../../Tree.ump"
   public  Tree() throws DatabaseException{
    init(null);
	maxMainTreeEntriesPerNode = 0;
	maxDupTreeEntriesPerNode = 0;
  }


  /**
   * 
   * constructor helper
   */
  // line 93 "../../../../Tree.ump"
   private void init(DatabaseImpl database){
    treeStats = new TreeStats();
	this.root = null;
	this.database = database;
  }


  /**
   * 
   * Set the database for this tree. Used by recovery when recreating an existing tree.
   */
  // line 102 "../../../../Tree.ump"
   public void setDatabase(DatabaseImpl database) throws DatabaseException{
    this.database = database;
	maxMainTreeEntriesPerNode = database.getNodeMaxEntries();
	maxDupTreeEntriesPerNode = database.getNodeMaxDupTreeEntries();
	DbConfigManager configManager = database.getDbEnvironment().getConfigManager();
	purgeRoot = configManager.getBoolean(EnvironmentParams.COMPRESSOR_PURGE_ROOT);
  }


  /**
   * 
   * @return the database for this Tree.
   */
  // line 113 "../../../../Tree.ump"
   public DatabaseImpl getDatabase(){
    return database;
  }


  /**
   * 
   * Set the root for the tree. Should only be called within the root latch.
   */
  // line 120 "../../../../Tree.ump"
   public void setRoot(ChildReference newRoot, boolean notLatched){
    root = newRoot;
  }

  // line 124 "../../../../Tree.ump"
   public ChildReference makeRootChildReference(Node target, byte [] key, long lsn){
    return new RootChildReference(target, key, lsn);
  }

  // line 128 "../../../../Tree.ump"
   private ChildReference makeRootChildReference(){
    return new RootChildReference();
  }


  /**
   * 
   * Get LSN of the rootIN. Obtained without latching, should only be accessed while quiescent.
   */
  // line 135 "../../../../Tree.ump"
   public long getRootLsn(){
    if (root == null) {
	    return DbLsn.NULL_LSN;
	} else {
	    return root.getLsn();
	}
  }


  /**
   * 
   * @return the TreeStats for this tree.
   */
  // line 146 "../../../../Tree.ump"
  public TreeStats getTreeStats(){
    return treeStats;
  }

  // line 150 "../../../../Tree.ump"
   private TreeWalkerStatsAccumulator getTreeStatsAccumulator(){
    if (EnvironmentImpl.getThreadLocalReferenceCount() > 0) {
	    return (TreeWalkerStatsAccumulator) treeStatsAccumulatorTL.get();
	} else {
	    return null;
	}
  }

  // line 158 "../../../../Tree.ump"
   public void setTreeStatsAccumulator(TreeWalkerStatsAccumulator tSA){
    treeStatsAccumulatorTL.set(tSA);
  }

  // line 162 "../../../../Tree.ump"
   public IN withRootLatchedExclusive(WithRootLatched wrl) throws DatabaseException{
    try {
	    this.hook670(wrl);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (IN) r.value;
	}
  }

  // line 171 "../../../../Tree.ump"
   public IN withRootLatchedShared(WithRootLatched wrl) throws DatabaseException{
    try {
	    this.hook671(wrl);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (IN) r.value;
	}
  }


  /**
   * 
   * Deletes a BIN specified by key from the tree. If the BIN resides in a subtree that can be pruned away, prune as much as possible, so we don't leave a branch that has no BINs. It's possible that the targeted BIN will now have entries, or will have resident cursors. Either will prevent deletion.
   * @param idKey -the identifier key of the node to delete.
   * @param trackeris used for tracking obsolete node info.
   */
  // line 186 "../../../../Tree.ump"
   public void delete(byte [] idKey, UtilizationTracker tracker) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    try {
	    IN subtreeRootIN = null;
	    ArrayList nodeLadder = new ArrayList();
	    IN rootIN = null;
	    boolean rootNeedsUpdating = false;
	    this.hook672(idKey, tracker, subtreeRootIN, nodeLadder, rootIN, rootNeedsUpdating);
	    if (subtreeRootIN != null) {
		EnvironmentImpl envImpl = database.getDbEnvironment();
		if (rootNeedsUpdating) {
		    DbTree dbTree = envImpl.getDbMapTree();
		    dbTree.modifyDbRoot(database);
		    this.hook661();
		}
		INList inList = envImpl.getInMemoryINs();
		accountForSubtreeRemoval(inList, subtreeRootIN, tracker);
	    }
	} catch (ReturnVoid r) {
	    return;
	}
  }


  /**
   * 
   * This entire tree is empty, clear the root and log a new MapLN
   * @return the rootIN that has been detached, or null if there hasn't beenany removal.
   */
  // line 212 "../../../../Tree.ump"
   private IN logTreeRemoval(IN rootIN, UtilizationTracker tracker) throws DatabaseException{
    IN detachedRootIN = null;
	if ((rootIN.getNEntries() <= 1) && (rootIN.validateSubtreeBeforeDelete(0))) {
	    root = null;
	    EnvironmentImpl envImpl = database.getDbEnvironment();
	    LogManager logManager = envImpl.getLogManager();
	    logManager.log(new INDeleteInfo(rootIN.getNodeId(), rootIN.getIdentifierKey(), database.getId()));
	    detachedRootIN = rootIN;
	}
	return detachedRootIN;
  }


  /**
   * 
   * Update nodes for a delete, going upwards. For example, suppose a node ladder holds: INa, INb, index for INb in INa INb, INc, index for INc in INb INc, BINd, index for BINd in INc When we enter this method, BINd has already been removed from INc. We need to - log INc - update INb, log INb - update INa, log INa
   * @param nodeLadderList of SplitInfos describing each node pair on the downwardpath
   * @param binRootparent of the dup tree, or null if this is not for dups.
   * @param indexslot occupied by this din tree.
   * @return whether the DB root needs updating.
   */
  // line 231 "../../../../Tree.ump"
   private boolean cascadeUpdates(ArrayList nodeLadder, BIN binRoot, int index) throws DatabaseException{
    ListIterator iter = nodeLadder.listIterator(nodeLadder.size());
	EnvironmentImpl envImpl = database.getDbEnvironment();
	LogManager logManager = envImpl.getLogManager();
	long newLsn = DbLsn.NULL_LSN;
	SplitInfo info4 = null;
	while (iter.hasPrevious()) {
	    info4 = (SplitInfo) iter.previous();
	    if (newLsn != DbLsn.NULL_LSN) {
		info4.parent.updateEntry(info4.index, newLsn);
	    }
	    newLsn = info4.parent.log(logManager);
	}
	boolean rootNeedsUpdating = false;
	if (info4 != null) {
	    if (info4.parent.isDbRoot()) {
		this.hook673();
		root.setLsn(newLsn);
		rootNeedsUpdating = true;
	    } else if ((binRoot != null) && info4.parent.isRoot()) {
		binRoot.updateEntry(index, newLsn);
	    } else {
		assert false;
	    }
	}
	return rootNeedsUpdating;
  }


  /**
   * 
   * Delete a subtree of a duplicate tree. Find the duplicate tree using mainKey in the top part of the tree and idKey in the duplicate tree.
   * @param idKeythe identifier key to be used in the duplicate subtree to findthe duplicate path.
   * @param mainKeythe key to be used in the main tree to find the duplicatesubtree.
   * @param trackeris used for tracking obsolete node info.
   * @return true if the delete succeeded, false if there were still cursorspresent on the leaf DBIN of the subtree that was located.
   */
  // line 267 "../../../../Tree.ump"
   public void deleteDup(byte [] idKey, byte [] mainKey, UtilizationTracker tracker) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    IN in = search(mainKey, SearchType.NORMAL, -1, null, false);
	IN deletedSubtreeRoot = null;
	deletedSubtreeRoot = this.hook674(idKey, mainKey, in, deletedSubtreeRoot);
	if (deletedSubtreeRoot != null) {
	    EnvironmentImpl envImpl = database.getDbEnvironment();
	    accountForSubtreeRemoval(envImpl.getInMemoryINs(), deletedSubtreeRoot, tracker);
	}
  }


  /**
   * 
   * We enter and leave this method with 'bin' latched.
   * @return the root of the subtree we have deleted, so it can be properlyaccounted for. May be null if nothing was deleted.
   */
  // line 282 "../../../../Tree.ump"
   private IN deleteDupSubtree(byte [] idKey, BIN bin, int index) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    EnvironmentImpl envImpl = database.getDbEnvironment();
	boolean dupCountLNLocked = false;
	DupCountLN dcl = null;
	BasicLocker locker = new BasicLocker(envImpl);
	DIN duplicateRoot = (DIN) bin.fetchTarget(index);
	duplicateRoot.latch(false);
	ArrayList nodeLadder = new ArrayList();
	IN subtreeRootIN = null;
	try {
	    ChildReference dclRef = duplicateRoot.getDupCountLNRef();
	    dcl = (DupCountLN) dclRef.fetchTarget(database, duplicateRoot);
	    LockResult lockResult = locker.nonBlockingLock(dcl.getNodeId(), LockType.READ, database);
	    if (lockResult.getLockGrant() == LockGrantType.DENIED) {
		throw CursorsExistException.CURSORS_EXIST;
	    } else {
		dupCountLNLocked = true;
	    }
	    searchDeletableSubTree(duplicateRoot, idKey, nodeLadder);
	    LogManager logManager = envImpl.getLogManager();
	    if (nodeLadder.size() == 0) {
		if (bin.nCursors() == 0) {
		    boolean deleteOk = bin.deleteEntry(index, true);
		    assert deleteOk;
		    logManager.log(new INDupDeleteInfo(duplicateRoot.getNodeId(), duplicateRoot.getMainTreeKey(),
			    duplicateRoot.getDupTreeKey(), database.getId()));
		    subtreeRootIN = duplicateRoot;
		    this.hook754(bin);
		} else {
		    throw CursorsExistException.CURSORS_EXIST;
		}
	    } else {
		SplitInfo detachPoint = (SplitInfo) nodeLadder.get(nodeLadder.size() - 1);
		boolean deleteOk = detachPoint.parent.deleteEntry(detachPoint.index, true);
		assert deleteOk;
		cascadeUpdates(nodeLadder, bin, index);
		subtreeRootIN = detachPoint.child;
	    }
	} finally {
	    this.hook676(nodeLadder);
	    if (dupCountLNLocked) {
		locker.releaseLock(dcl.getNodeId());
	    }
	    this.hook675(duplicateRoot);
	}
	return subtreeRootIN;
  }


  /**
   * 
   * Find the leftmost node (IN or BIN) in the tree. Do not descend into a duplicate tree if the leftmost entry of the first BIN refers to one.
   * @return the leftmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 334 "../../../../Tree.ump"
   public IN getFirstNode() throws DatabaseException{
    return search(null, SearchType.LEFT, -1, null, true);
  }


  /**
   * 
   * Find the rightmost node (IN or BIN) in the tree. Do not descend into a duplicate tree if the rightmost entry of the last BIN refers to one.
   * @return the rightmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 342 "../../../../Tree.ump"
   public IN getLastNode() throws DatabaseException{
    return search(null, SearchType.RIGHT, -1, null, true);
  }


  /**
   * 
   * Find the leftmost node (DBIN) in a duplicate tree.
   * @return the leftmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 350 "../../../../Tree.ump"
   public DBIN getFirstNode(DIN dupRoot) throws DatabaseException{
    if (dupRoot == null) {
	    throw new IllegalArgumentException("getFirstNode passed null root");
	}
	this.hook677(dupRoot);
	IN ret = searchSubTree(dupRoot, null, SearchType.LEFT, -1, null, true);
	return (DBIN) ret;
  }


  /**
   * 
   * Find the rightmost node (DBIN) in a duplicate tree.
   * @return the rightmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 363 "../../../../Tree.ump"
   public DBIN getLastNode(DIN dupRoot) throws DatabaseException{
    if (dupRoot == null) {
	    throw new IllegalArgumentException("getLastNode passed null root");
	}
	this.hook678(dupRoot);
	IN ret = searchSubTree(dupRoot, null, SearchType.RIGHT, -1, null, true);
	return (DBIN) ret;
  }


  /**
   * 
   * GetParentNode without optional tracking.
   */
  // line 376 "../../../../Tree.ump"
   public SearchResult getParentINForChildIN(IN child, boolean requireExactMatch, boolean updateGeneration) throws DatabaseException{
    return getParentINForChildIN(child, requireExactMatch, updateGeneration, -1, null);
  }


  /**
   * 
   * Return a reference to the parent or possible parent of the child. Used by objects that need to take a standalone node and find it in the tree, like the evictor, checkpointer, and recovery.
   * @param childThe child node for which to find the parent. This node islatched by the caller and is released by this function before returning to the caller.
   * @param requireExactMatchif true, we must find the exact parent, not a potentialparent.
   * @param updateGenerationif true, set the generation count during latching. Pass falsewhen the LRU should not be impacted, such as during eviction and checkpointing.
   * @param trackingListif not null, add the LSNs of the parents visited along theway, as a debug tracing mechanism. This is meant to stay in production, to add information to the log.
   * @return a SearchResult object. If the parent has been found,result.foundExactMatch is true. If any parent, exact or potential has been found, result.parent refers to that node.
   */
  // line 389 "../../../../Tree.ump"
   public SearchResult getParentINForChildIN(IN child, boolean requireExactMatch, boolean updateGeneration, int targetLevel, List trackingList) throws DatabaseException{
    if (child == null) {
	    throw new IllegalArgumentException("getParentNode passed null");
	}
	this.hook680(child);
	byte[] mainTreeKey = child.getMainTreeKey();
	byte[] dupTreeKey = child.getDupTreeKey();
	boolean isRoot = child.isRoot();
	this.hook679(child);
	return getParentINForChildIN(child.getNodeId(), child.containsDuplicates(), isRoot, mainTreeKey, dupTreeKey,
		requireExactMatch, updateGeneration, targetLevel, trackingList, true);
  }


  /**
   * 
   * Return a reference to the parent or possible parent of the child. Used by objects that need to take a node id and find it in the tree, like the evictor, checkpointer, and recovery.
   * @param requireExactMatchif true, we must find the exact parent, not a potentialparent.
   * @param updateGenerationif true, set the generation count during latching. Pass falsewhen the LRU should not be impacted, such as during eviction and checkpointing.
   * @param trackingListif not null, add the LSNs of the parents visited along theway, as a debug tracing mechanism. This is meant to stay in production, to add information to the log.
   * @param doFetchif false, stop the search if we run into a non-resident child.Used by the checkpointer to avoid conflicting with work done by the evictor.
   * @param childThe child node for which to find the parent. This node islatched by the caller and is released by this function before returning to the caller.
   * @return a SearchResult object. If the parent has been found,result.foundExactMatch is true. If any parent, exact or potential has been found, result.parent refers to that node.
   */
  // line 413 "../../../../Tree.ump"
   public SearchResult getParentINForChildIN(long targetNodeId, boolean targetContainsDuplicates, boolean targetIsRoot, byte [] targetMainTreeKey, byte [] targetDupTreeKey, boolean requireExactMatch, boolean updateGeneration, int targetLevel, List trackingList, boolean doFetch) throws DatabaseException{
    IN rootIN = getRootIN(updateGeneration);
	SearchResult result = new SearchResult();
	if (rootIN != null) {
	    if (trackingList != null) {
		trackingList.add(new TrackingInfo(root.getLsn(), rootIN.getNodeId()));
	    }
	    IN potentialParent = rootIN;
	    try {
		while (result.keepSearching) {
		    assert TestHookExecute.doHookIfSet(searchHook);
		    potentialParent.findParent(SearchType.NORMAL, targetNodeId, targetContainsDuplicates, targetIsRoot,
			    targetMainTreeKey, targetDupTreeKey, result, requireExactMatch, updateGeneration,
			    targetLevel, trackingList, doFetch);
		    potentialParent = result.parent;
		}
	    } catch (Exception e) {
		this.hook681(potentialParent);
		throw new DatabaseException(e);
	    }
	}
	return result;
  }


  /**
   * 
   * Return a reference to the parent of this LN. This searches through the main and duplicate tree and allows splits. Set the tree location to the proper BIN parent whether or not the LN child is found. That's because if the LN is not found, recovery or abort will need to place it within the tree, and so we must point at the appropriate position. <p> When this method returns with location.bin non-null, the BIN is latched and must be unlatched by the caller. Note that location.bin may be non-null even if this method returns false. </p>
   * @param locationa holder class to hold state about the location of our search.Sort of an internal cursor.
   * @param mainKeykey to navigate through main key
   * @param dupKeykey to navigate through duplicate tree. May be null, sincedeleted lns have no data.
   * @param lnthe node instantiated from the log
   * @param splitsAllowedtrue if this method is allowed to cause tree splits as a sideeffect. In practice, recovery can cause splits, but abort can't.
   * @param searchDupTreetrue if a search through the dup tree looking for a match onthe ln's node id should be made (only in the case where dupKey == null). See SR 8984.
   * @param updateGenerationif true, set the generation count during latching. Pass falsewhen the LRU should not be impacted, such as during eviction and checkpointing.
   * @return true if node found in tree. If false is returned and there is thepossibility that we can insert the record into a plausible parent we must also set - location.bin (may be null if no possible parent found) - location.lnKey (don't need to set if no possible parent).
   */
  // line 450 "../../../../Tree.ump"
   public boolean getParentBINForChildLN(TreeLocation location, byte [] mainKey, byte [] dupKey, LN ln, boolean splitsAllowed, boolean findDeletedEntries, boolean searchDupTree, boolean updateGeneration) throws DatabaseException{
    try {
	    IN searchResult = null;
	    try {
		if (splitsAllowed) {
		    searchResult = searchSplitsAllowed(mainKey, -1, updateGeneration);
		} else {
		    searchResult = search(mainKey, SearchType.NORMAL, -1, null, updateGeneration);
		}
		location.bin = (BIN) searchResult;
	    } catch (Exception e) {
		StringBuffer msg = new StringBuffer();
		if (searchResult != null) {
		    this.hook682(searchResult);
		    msg.append("searchResult=" + searchResult.getClass() + " nodeId=" + searchResult.getNodeId()
			    + " nEntries=" + searchResult.getNEntries());
		}
		throw new DatabaseException(msg.toString(), e);
	    }
	    if (location.bin == null) {
		return false;
	    }
	    boolean exactSearch = false;
	    boolean indicateIfExact = true;
	    if (!findDeletedEntries) {
		exactSearch = true;
		indicateIfExact = false;
	    }
	    location.index = location.bin.findEntry(mainKey, indicateIfExact, exactSearch);
	    boolean match = false;
	    if (findDeletedEntries) {
		match = (location.index >= 0 && (location.index & IN.EXACT_MATCH) != 0);
		location.index &= ~IN.EXACT_MATCH;
	    } else {
		match = (location.index >= 0);
	    }
	    if (match) {
		if (!location.bin.isEntryKnownDeleted(location.index)) {
		    if (database.getSortedDuplicates()) {
			Node childNode = location.bin.fetchTarget(location.index);
			this.hook683(location, mainKey, dupKey, ln, splitsAllowed, findDeletedEntries, searchDupTree,
				updateGeneration, exactSearch, indicateIfExact, childNode);
		    }
		}
		location.childLsn = location.bin.getLsn(location.index);
	    } else {
		location.lnKey = mainKey;
	    }
	    return match;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * For SR [#8984]: our prospective child is a deleted LN, and we're facing a dup tree. Alas, the deleted LN has no data, and therefore nothing to guide the search in the dup tree. Instead, we search by node id. This is very expensive, but this situation is a very rare case.
   */
  // line 508 "../../../../Tree.ump"
   private boolean searchDupTreeByNodeId(TreeLocation location, Node childNode, LN ln, boolean searchDupTree, boolean updateGeneration) throws DatabaseException{
    if (searchDupTree) {
	    BIN oldBIN = location.bin;
	    if (childNode.matchLNByNodeId(location, ln.getNodeId())) {
		location.index &= ~IN.EXACT_MATCH;
		this.hook684(oldBIN);
		location.bin.latch(updateGeneration);
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
  }


  /**
   * 
   * @return true if childNode is the DIN parent of this DupCountLN
   */
  // line 528 "../../../../Tree.ump"
   private boolean searchDupTreeForDupCountLNParent(TreeLocation location, byte [] mainKey, Node childNode) throws DatabaseException{
    location.lnKey = mainKey;
	if (childNode instanceof DIN) {
	    DIN dupRoot = (DIN) childNode;
	    location.childLsn = dupRoot.getDupCountLNRef().getLsn();
	    return true;
	} else {
	    return false;
	}
  }


  /**
   * 
   * Search the dup tree for the DBIN parent of this ln.
   */
  // line 544 "../../../../Tree.ump"
   private boolean searchDupTreeForDBIN(TreeLocation location, byte [] dupKey, DIN dupRoot, LN ln, boolean findDeletedEntries, boolean indicateIfExact, boolean exactSearch, boolean splitsAllowed, boolean updateGeneration) throws DatabaseException{
    try {
	    assert dupKey != null;
	    this.hook685(location, dupKey, dupRoot, ln, findDeletedEntries, indicateIfExact, exactSearch, splitsAllowed,
		    updateGeneration);
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * Return a reference to the adjacent BIN.
   * @param binThe BIN to find the next BIN for. This BIN is latched.
   * @param traverseWithinDupTreeif true, only search within the dup tree and return null whenthe traversal runs out of duplicates.
   * @return The next BIN, or null if there are no more. The returned node islatched and the caller must release it. If null is returned, the argument BIN remains latched.
   */
  // line 561 "../../../../Tree.ump"
   public BIN getNextBin(BIN bin, boolean traverseWithinDupTree) throws DatabaseException{
    return getNextBinInternal(traverseWithinDupTree, bin, true);
  }


  /**
   * 
   * Return a reference to the previous BIN.
   * @param binThe BIN to find the next BIN for. This BIN is latched.
   * @param traverseWithinDupTreeif true, only search within the dup tree and return null whenthe traversal runs out of duplicates.
   * @return The previous BIN, or null if there are no more. The returned nodeis latched and the caller must release it. If null is returned, the argument bin remains latched.
   */
  // line 571 "../../../../Tree.ump"
   public BIN getPrevBin(BIN bin, boolean traverseWithinDupTree) throws DatabaseException{
    return getNextBinInternal(traverseWithinDupTree, bin, false);
  }


  /**
   * 
   * Helper routine for above two routines to iterate through BIN's.
   */
  // line 578 "../../../../Tree.ump"
   private BIN getNextBinInternal(boolean traverseWithinDupTree, BIN bin, boolean forward) throws DatabaseException{
    try {
	    byte[] idKey = null;
	    if (bin.getNEntries() == 0) {
		idKey = bin.getIdentifierKey();
	    } else if (forward) {
		idKey = bin.getKey(bin.getNEntries() - 1);
	    } else {
		idKey = bin.getKey(0);
	    }
	    IN next = bin;
	    this.hook687();
	    IN parent = null;
	    IN nextIN = null;
	    this.hook686(traverseWithinDupTree, forward, idKey, next, parent, nextIN);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (BIN) r.value;
	}
  }


  /**
   * 
   * Split the root of the tree.
   */
  // line 602 "../../../../Tree.ump"
   private void splitRoot() throws DatabaseException{
    EnvironmentImpl env = database.getDbEnvironment();
	LogManager logManager = env.getLogManager();
	INList inMemoryINs = env.getInMemoryINs();
	IN curRoot = null;
	curRoot = (IN) root.fetchTarget(database, null);
	this.hook689(curRoot);
	long curRootLsn = 0;
	long logLsn = 0;
	IN newRoot = null;
	this.hook688(logManager, inMemoryINs, curRoot, curRootLsn, logLsn, newRoot);
	treeStats.nRootSplits++;
	this.hook662(curRoot, curRootLsn, logLsn, newRoot);
  }


  /**
   * 
   * Search the tree, starting at the root. Depending on search type either search using key, or search all the way down the right or left sides. Stop the search either when the bottom of the tree is reached, or a node matching nid is found (see below) in which case that node's parent is returned. Preemptive splitting is not done during the search.
   * @param key -the key to search for, or null if searchType is LEFT or RIGHT.
   * @param searchType -The type of tree search to perform. NORMAL means we're searching for key in the tree. LEFT/RIGHT means we're descending down the left or right side, resp. DELETE means we're descending the tree and will return the lowest node in the path that has > 1 entries.
   * @param nid -The nodeid to search for in the tree. If found, returns its parent. If the nodeid of the root is passed, null is returned.
   * @param binBoundary -If non-null, information is returned about whether the BIN found is the first or last BIN in the database.
   * @return - the Node that matches the criteria, if any. This is the nodethat is farthest down the tree with a match. Returns null if the root is null. Node is latched (unless it's null) and must be unlatched by the caller. Only IN's and BIN's are returned, not LN's. In a NORMAL search, It is the caller's responsibility to do the findEntry() call on the key and BIN to locate the entry that matches key. The return value node is latched upon return and it is the caller's responsibility to unlatch it.
   */
  // line 626 "../../../../Tree.ump"
   public IN search(byte [] key, SearchType searchType, long nid, BINBoundary binBoundary, boolean updateGeneration) throws DatabaseException{
    IN rootIN = getRootIN(true);
	if (rootIN != null) {
	    return searchSubTree(rootIN, key, searchType, nid, binBoundary, updateGeneration);
	} else {
	    return null;
	}
  }


  /**
   * 
   * Do a key based search, permitting pre-emptive splits. Returns the target node's parent.
   */
  // line 638 "../../../../Tree.ump"
   public IN searchSplitsAllowed(byte [] key, long nid, boolean updateGeneration) throws DatabaseException{
    return new Tree_searchSplitsAllowed(this, key, nid, updateGeneration).execute();
  }


  /**
   * 
   * Searches a portion of the tree starting at parent using key. If during the search a node matching a non-null nid argument is found, its parent is returned. If searchType is NORMAL, then key must be supplied to guide the search. If searchType is LEFT (or RIGHT), then the tree is searched down the left (or right) side to find the first (or last) leaf, respectively. <p> Enters with parent latched, assuming it's not null. Exits with the return value latched, assuming it's not null. <p>
   * @param parent -the root of the subtree to start the search at. This node should be latched by the caller and will be unlatched prior to return.
   * @param key -the key to search for, unless searchType is LEFT or RIGHT
   * @param searchType -NORMAL means search using key and, optionally, nid. LEFT means find the first (leftmost) leaf RIGHT means find the last (rightmost) leaf
   * @param nid -The nodeid to search for in the tree. If found, returns its parent. If the nodeid of the root is passed, null is returned. Pass -1 if no nodeid based search is desired.
   * @return - the node matching the argument criteria, or null. The node islatched and must be unlatched by the caller. The parent argument and any other nodes that are latched during the search are unlatched prior to return.
   */
  // line 651 "../../../../Tree.ump"
   public IN searchSubTree(IN parent, byte [] key, SearchType searchType, long nid, BINBoundary binBoundary, boolean updateGeneration) throws DatabaseException{
    if (parent == null) {
	    return null;
	}
	if ((searchType == SearchType.LEFT || searchType == SearchType.RIGHT) && key != null) {
	    throw new IllegalArgumentException("searchSubTree passed key and left/right search");
	}
	this.hook690(parent);
	if (parent.getNodeId() == nid) {
	    this.hook691(parent);
	    return null;
	}
	if (binBoundary != null) {
	    binBoundary.isLastBin = true;
	    binBoundary.isFirstBin = true;
	}
	int index;
	IN child = null;
	TreeWalkerStatsAccumulator treeStatsAccumulator = getTreeStatsAccumulator();
	try {
	    do {
		if (treeStatsAccumulator != null) {
		    parent.accumulateStats(treeStatsAccumulator);
		}
		if (parent.getNEntries() == 0) {
		    return parent;
		} else if (searchType == SearchType.NORMAL) {
		    index = parent.findEntry(key, false, false);
		} else if (searchType == SearchType.LEFT) {
		    index = 0;
		} else if (searchType == SearchType.RIGHT) {
		    index = parent.getNEntries() - 1;
		} else {
		    throw new IllegalArgumentException("Invalid value of searchType: " + searchType);
		}
		assert index >= 0;
		if (binBoundary != null) {
		    if (index != parent.getNEntries() - 1) {
			binBoundary.isLastBin = false;
		    }
		    if (index != 0) {
			binBoundary.isFirstBin = false;
		    }
		}
		child = (IN) parent.fetchTarget(index);
		child.latch(updateGeneration);
		if (treeStatsAccumulator != null) {
		    child.accumulateStats(treeStatsAccumulator);
		}
		if (child.getNodeId() == nid) {
		    this.hook693(child);
		    return parent;
		}
		this.hook692(parent);
		parent = child;
	    } while (!(parent instanceof BIN));
	    return child;
	} catch (Throwable t) {
	    this.hook694(parent, child);
	    if (t instanceof DatabaseException) {
		throw (DatabaseException) t;
	    } else {
		throw new DatabaseException(t);
	    }
	}
  }


  /**
   * 
   * Search down the tree using a key, but instead of returning the BIN that houses that key, find the point where we can detach a deletable subtree. A deletable subtree is a branch where each IN has one child, and the bottom BIN has no entries and no resident cursors. That point can be found by saving a pointer to the lowest node in the path with more than one entry. INa / \ INb INc | | INd .. / \ INe .. | BINx (suspected of being empty) In this case, we'd like to prune off the subtree headed by INe. INd is the parent of this deletable subtree. As we descend, we must keep latches for all the nodes that will be logged. In this case, we will need to keep INa, INb and INd latched when we return from this method. The method returns a list of parent/child/index structures. In this example, the list will hold: INa/INb/index INb/INd/index INd/INe/index Every node is latched, and every node except for the bottom most child (INe) must be logged.
   */
  // line 722 "../../../../Tree.ump"
   public void searchDeletableSubTree(IN parent, byte [] key, ArrayList nodeLadder) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    assert (parent != null);
	assert (key != null);
	this.hook695(parent);
	int index;
	IN child = null;
	IN lowestMultipleEntryIN = null;
	do {
	    if (parent.getNEntries() == 0) {
		break;
	    }
	    if (parent.getNEntries() > 1) {
		lowestMultipleEntryIN = parent;
	    }
	    index = parent.findEntry(key, false, false);
	    assert index >= 0;
	    child = (IN) parent.fetchTarget(index);
	    child.latch(false);
	    nodeLadder.add(new SplitInfo(parent, child, index));
	    parent = child;
	} while (!(parent instanceof BIN));
	if ((child != null) && (child instanceof BIN)) {
	    if (child.getNEntries() != 0) {
		throw NodeNotEmptyException.NODE_NOT_EMPTY;
	    }
	    if (((BIN) child).nCursors() > 0) {
		throw CursorsExistException.CURSORS_EXIST;
	    }
	}
	if (lowestMultipleEntryIN != null) {
	    ListIterator iter = nodeLadder.listIterator(nodeLadder.size());
	    while (iter.hasPrevious()) {
		SplitInfo info5 = (SplitInfo) iter.previous();
		if (info5.parent == lowestMultipleEntryIN) {
		    break;
		} else {
		    this.hook696(info5);
		    iter.remove();
		}
	    }
	} else {
	    this.hook697(nodeLadder);
	    nodeLadder.clear();
	}
  }


  /**
   * 
   * Search the portion of the tree starting at the parent, permitting preemptive splits.
   */
  // line 772 "../../../../Tree.ump"
   private IN searchSubTreeSplitsAllowed(IN parent, byte [] key, long nid, boolean updateGeneration) throws DatabaseException,SplitRequiredException{
    if (parent != null) {
	    while (true) {
		try {
		    return searchSubTreeUntilSplit(parent, key, nid, updateGeneration);
		} catch (SplitRequiredException e) {
		    if (waitHook != null) {
			waitHook.doHook();
		    }
		    forceSplit(parent, key);
		}
	    }
	} else {
	    return null;
	}
  }


  /**
   * 
   * Search the subtree, but throw an exception when we see a node that has to be split.
   */
  // line 793 "../../../../Tree.ump"
   private IN searchSubTreeUntilSplit(IN parent, byte [] key, long nid, boolean updateGeneration) throws DatabaseException,SplitRequiredException{
    try {
	    if (parent == null) {
		return null;
	    }
	    this.hook699(parent);
	    if (parent.getNodeId() == nid) {
		this.hook700(parent);
		return null;
	    }
	    int index = 0;
	    IN child = null;
	    this.hook698(parent, key, nid, updateGeneration, index, child);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (IN) r.value;
	}
  }


  /**
   * 
   * Do pre-emptive splitting in the subtree topped by the "parent" node. Search down the tree until we get to the BIN level, and split any nodes that fit the splittable requirement. Note that more than one node in the path may be splittable. For example, a tree might have a level2 IN and a BIN that are both splittable, and would be encountered by the same insert operation.
   */
  // line 815 "../../../../Tree.ump"
   private void forceSplit(IN parent, byte [] key) throws DatabaseException,SplitRequiredException{
    new Tree_forceSplit(this, parent, key).execute();
  }


  /**
   * 
   * Helper to obtain the root IN with proper root latching. Optionally updates the generation of the root when latching it.
   */
  // line 822 "../../../../Tree.ump"
   public IN getRootIN(boolean updateGeneration) throws DatabaseException{
    try {
	    this.hook702();
	    IN rootIN = null;
	    this.hook701(updateGeneration, rootIN);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (IN) r.value;
	}
  }


  /**
   * 
   * Inserts a new LN into the tree.
   * @param lnThe LN to insert into the tree.
   * @param keyKey value for the node
   * @param allowDuplicateswhether to allow duplicates to be inserted
   * @param cursorthe cursor to update to point to the newly inserted key/datapair, or null if no cursor should be updated.
   * @return true if LN was inserted, false if it was a duplicate duplicate orif an attempt was made to insert a duplicate when allowDuplicates was false.
   */
  // line 842 "../../../../Tree.ump"
   public boolean insert(LN ln, byte [] key, boolean allowDuplicates, CursorImpl cursor, LockResult lnLock) throws DatabaseException{
    try {
	    validateInsertArgs(allowDuplicates);
	    EnvironmentImpl env = database.getDbEnvironment();
	    LogManager logManager = env.getLogManager();
	    INList inMemoryINs = env.getInMemoryINs();
	    BIN bin = null;
	    this.hook703(ln, key, allowDuplicates, cursor, lnLock, env, logManager, inMemoryINs, bin);
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * Attempts to insert a duplicate at the current cursor BIN position. If an existing dup tree exists, insert into it; otherwise, create a new dup tree and place the new LN and the existing LN into it. If the current BIN entry contains an LN, the caller guarantees that it is not deleted.
   * @return true if duplicate inserted successfully, false if it was aduplicate duplicate, false if a there is an existing LN and allowDuplicates is false.
   */
  // line 861 "../../../../Tree.ump"
   private boolean insertDuplicate(byte [] key, BIN bin, LN newLN, LogManager logManager, INList inMemoryINs, CursorImpl cursor, LockResult lnLock, boolean allowDuplicates) throws DatabaseException{
    try {
	    EnvironmentImpl env = database.getDbEnvironment();
	    int index = cursor.getIndex();
	    boolean successfulInsert = false;
	    DIN dupRoot = null;
	    Node n = bin.fetchTarget(index);
	    long binNid = bin.getNodeId();
	    if (n instanceof DIN) {
		DBIN dupBin = null;
		this.hook704(key, bin, newLN, cursor, lnLock, allowDuplicates, env, index, successfulInsert, dupRoot, n,
			binNid, dupBin);
	    } else if (n instanceof LN) {
		if (!allowDuplicates) {
		    return false;
		}
		try {
		    lnLock.setAbortLsn(DbLsn.NULL_LSN, true, true);
		    dupRoot = createDuplicateTree(key, logManager, inMemoryINs, newLN, cursor);
		} finally {
		    if (dupRoot != null) {
			this.hook705(dupRoot);
			successfulInsert = true;
		    } else {
			successfulInsert = false;
		    }
		}
	    } else {
		throw new InconsistentNodeException("neither LN or DIN found in BIN");
	    }
	    return successfulInsert;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * Check if the duplicate root needs to be split. The current duplicate root is latched. Exit with the new root (even if it's unchanged) latched and the old root (unless the root is unchanged) unlatched.
   * @param binthe BIN containing the duplicate root.
   * @param indexthe index of the duplicate root in bin.
   * @return true if the duplicate root was split.
   */
  // line 903 "../../../../Tree.ump"
   private boolean maybeSplitDuplicateRoot(BIN bin, int index) throws DatabaseException{
    DIN curRoot = (DIN) bin.fetchTarget(index);
	if (curRoot.needsSplitting()) {
	    EnvironmentImpl env = database.getDbEnvironment();
	    LogManager logManager = env.getLogManager();
	    INList inMemoryINs = env.getInMemoryINs();
	    byte[] rootIdKey = curRoot.getKey(0);
	    DIN newRoot = new DIN(database, rootIdKey, maxDupTreeEntriesPerNode, curRoot.getDupKey(),
		    curRoot.getDupCountLNRef(), curRoot.getLevel() + 1);
	    this.hook707(newRoot);
	    long curRootLsn = 0;
	    long logLsn = 0;
	    this.hook706(bin, index, curRoot, logManager, inMemoryINs, rootIdKey, newRoot, curRootLsn, logLsn);
	    this.hook663(curRoot, newRoot, curRootLsn, logLsn);
	    return true;
	} else {
	    return false;
	}
  }


  /**
   * 
   * Convert an existing BIN entry from a single (non-duplicate) LN to a new DIN/DupCountLN->DBIN->LN subtree.
   * @param keythe key of the entry which will become the duplicate key forthe duplicate subtree.
   * @param logManagerthe logManager
   * @param inMemoryINsthe in memory IN list
   * @param newLNthe new record to be inserted
   * @param cursorpoints to the target position for this new dup tree.
   * @return the new duplicate subtree root (a DIN). It is latched when it isreturned and the caller should unlatch it. If new entry to be inserted is a duplicate of the existing LN, null is returned.
   */
  // line 933 "../../../../Tree.ump"
   private DIN createDuplicateTree(byte [] key, LogManager logManager, INList inMemoryINs, LN newLN, CursorImpl cursor) throws DatabaseException{
    EnvironmentImpl env = database.getDbEnvironment();
	DIN dupRoot = null;
	DBIN dupBin = null;
	BIN bin = cursor.getBIN();
	int index = cursor.getIndex();
	LN existingLN = (LN) bin.fetchTarget(index);
	boolean existingLNIsDeleted = bin.isEntryKnownDeleted(index) || existingLN.isDeleted();
	assert existingLN != null;
	byte[] existingKey = existingLN.getData();
	byte[] newLNKey = newLN.getData();
	boolean keysEqual = Key.compareKeys(newLNKey, existingKey, database.getDuplicateComparator()) == 0;
	if (keysEqual) {
	    return null;
	}
	Locker locker = cursor.getLocker();
	long nodeId = existingLN.getNodeId();
	int startingCount = (locker.createdNode(nodeId) || existingLNIsDeleted
		|| locker.getWriteLockInfo(nodeId).getAbortKnownDeleted()) ? 0 : 1;
	DupCountLN dupCountLN = new DupCountLN(startingCount);
	long firstDupCountLNLsn = dupCountLN.logProvisional(env, database.getId(), key, DbLsn.NULL_LSN);
	dupRoot = new DIN(database, existingKey, maxDupTreeEntriesPerNode, key,
		new ChildReference(dupCountLN, key, firstDupCountLNLsn), 2);
	this.hook710(dupRoot);
	dupRoot.setIsRoot(true);
	dupBin = new DBIN(database, existingKey, maxDupTreeEntriesPerNode, key, 1);
	this.hook709(dupBin);
	ChildReference newExistingLNRef = new ChildReference(existingLN, existingKey, bin.getLsn(index),
		bin.getState(index));
	boolean insertOk = dupBin.insertEntry(newExistingLNRef);
	assert insertOk;
	this.hook708(key, logManager, inMemoryINs, newLN, cursor, env, dupRoot, dupBin, bin, index, existingLN,
		newLNKey, locker, dupCountLN, firstDupCountLNLsn);
	return dupRoot;
  }


  /**
   * 
   * Validate args passed to insert. Presently this just means making sure that if they say duplicates are allowed that the database supports duplicates.
   */
  // line 972 "../../../../Tree.ump"
   private void validateInsertArgs(boolean allowDuplicates) throws DatabaseException{
    if (allowDuplicates && !database.getSortedDuplicates()) {
	    throw new DatabaseException(
		    "allowDuplicates passed to insert but database doesn't " + "have allow duplicates set.");
	}
  }


  /**
   * 
   * Find the BIN that is relevant to the insert. If the tree doesn't exist yet, then create the first IN and BIN.
   * @return the BIN that was found or created and return it latched.
   */
  // line 984 "../../../../Tree.ump"
   private BIN findBinForInsert(byte [] key, LogManager logManager, INList inMemoryINs, CursorImpl cursor) throws DatabaseException{
    BIN bin;
	bin = cursor.latchBIN();
	if (bin != null) {
	    if (!bin.needsSplitting() && bin.isKeyInBounds(key)) {
		return bin;
	    } else {
		this.hook712(bin);
	    }
	}
	boolean rootLatchIsHeld = false;
	bin = this.hook711(key, logManager, inMemoryINs, bin, rootLatchIsHeld);
	if (ckptHook != null) {
	    ckptHook.doHook();
	}
	return bin;
  }

  // line 1003 "../../../../Tree.ump"
   private void accountForSubtreeRemoval(INList inList, IN subtreeRoot, UtilizationTracker tracker) throws DatabaseException{
    this.hook713(inList, subtreeRoot, tracker);
	this.hook665(subtreeRoot);
  }


  /**
   * 
   * @see LogWritable#getLogSize
   */
  // line 1011 "../../../../Tree.ump"
   public int getLogSize(){
    int size = LogUtils.getBooleanLogSize();
	if (root != null) {
	    size += root.getLogSize();
	}
	return size;
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 1022 "../../../../Tree.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeBoolean(logBuffer, (root != null));
	if (root != null) {
	    root.writeToLog(logBuffer);
	}
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 1032 "../../../../Tree.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion){
    boolean rootExists = LogUtils.readBoolean(itemBuffer);
	if (rootExists) {
	    root = makeRootChildReference();
	    root.readFromLog(itemBuffer, entryTypeVersion);
	}
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 1043 "../../../../Tree.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<root>");
	if (root != null) {
	    root.dumpLog(sb, verbose);
	}
	sb.append("</root>");
  }


  /**
   * 
   * @see LogReadable#isTransactional
   */
  // line 1054 "../../../../Tree.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 1061 "../../../../Tree.ump"
   public long getTransactionId(){
    return 0;
  }


  /**
   * 
   * rebuildINList is used by recovery to add all the resident nodes to the IN list.
   */
  // line 1068 "../../../../Tree.ump"
   public void rebuildINList() throws DatabaseException{
    INList inMemoryList = database.getDbEnvironment().getInMemoryINs();
	if (root != null) {
	    this.hook714(inMemoryList);
	}
  }

  // line 1075 "../../../../Tree.ump"
   public void dump() throws DatabaseException{
    System.out.println(dumpString(0));
  }

  // line 1079 "../../../../Tree.ump"
   public String dumpString(int nSpaces) throws DatabaseException{
    StringBuffer sb = new StringBuffer();
	sb.append(TreeUtils.indent(nSpaces));
	sb.append("<tree>");
	sb.append('\n');
	if (root != null) {
	    sb.append(DbLsn.dumpString(root.getLsn(), nSpaces));
	    sb.append('\n');
	    IN rootIN = (IN) root.getTarget();
	    if (rootIN == null) {
		sb.append("<in/>");
	    } else {
		sb.append(rootIN.toString());
	    }
	    sb.append('\n');
	}
	sb.append(TreeUtils.indent(nSpaces));
	sb.append("</tree>");
	return sb.toString();
  }


  /**
   * 
   * Unit test support to validate subtree pruning. Didn't want to make root access public.
   */
  // line 1103 "../../../../Tree.ump"
  public boolean validateDelete(int index) throws DatabaseException{
    try {
	    this.hook715(index);
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }


  /**
   * 
   * Debugging check that all resident nodes are on the INList and no stray nodes are present in the unused portion of the IN arrays.
   */
  // line 1115 "../../../../Tree.ump"
   public void validateINList(IN parent) throws DatabaseException{
    if (parent == null) {
	    parent = (IN) root.getTarget();
	}
	if (parent != null) {
	    INList inList = database.getDbEnvironment().getInMemoryINs();
	    if (!inList.getINs().contains(parent)) {
		throw new DatabaseException("IN " + parent.getNodeId() + " missing from INList");
	    }
	    for (int i = 0;; i += 1) {
		try {
		    Node node = parent.getTarget(i);
		    if (i >= parent.getNEntries()) {
			if (node != null) {
			    throw new DatabaseException("IN " + parent.getNodeId() + " has stray node "
				    + node.getNodeId() + " at index " + i);
			}
			byte[] key = parent.getKey(i);
			if (key != null) {
			    throw new DatabaseException(
				    "IN " + parent.getNodeId() + " has stray key " + key + " at index " + i);
			}
		    }
		    if (node instanceof IN) {
			validateINList((IN) node);
		    }
		} catch (ArrayIndexOutOfBoundsException e) {
		    break;
		}
	    }
	}
  }

  // line 1148 "../../../../Tree.ump"
   public void setWaitHook(TestHook hook){
    waitHook = hook;
  }

  // line 1152 "../../../../Tree.ump"
   public void setSearchHook(TestHook hook){
    searchHook = hook;
  }

  // line 1156 "../../../../Tree.ump"
   public void setCkptHook(TestHook hook){
    ckptHook = hook;
  }

  // line 1160 "../../../../Tree.ump"
   protected void hook657(LN ln, EnvironmentImpl env, BIN bin, int index, long newLsn) throws DatabaseException{
    
  }

  // line 1163 "../../../../Tree.ump"
   protected void hook658(LN ln, EnvironmentImpl env, BIN bin, int index, long newLsn) throws DatabaseException{
    
  }

  // line 1166 "../../../../Tree.ump"
   protected void hook659(LN newLN, long binNid, DBIN dupBin, long newLsn) throws DatabaseException{
    
  }

  // line 1169 "../../../../Tree.ump"
   protected void hook660(LN newLN, long binNid, DBIN dupBin, long newLsn) throws DatabaseException{
    
  }

  // line 1172 "../../../../Tree.ump"
   protected void hook661() throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 1175 "../../../../Tree.ump"
   protected void hook662(IN curRoot, long curRootLsn, long logLsn, IN newRoot) throws DatabaseException{
    
  }

  // line 1178 "../../../../Tree.ump"
   protected void hook663(DIN curRoot, DIN newRoot, long curRootLsn, long logLsn) throws DatabaseException{
    
  }

  // line 1182 "../../../../Tree.ump"
   protected void hook664(LN newLN, DIN dupRoot, DBIN dupBin, BIN bin, LN existingLN, DupCountLN dupCountLN, long dbinLsn, long dinLsn, long dupCountLsn, long newLsn) throws DatabaseException{
    
  }

  // line 1185 "../../../../Tree.ump"
   protected void hook665(IN subtreeRoot) throws DatabaseException{
    
  }

  // line 1188 "../../../../Tree.ump"
   protected void hook670(WithRootLatched wrl) throws DatabaseException{
    this.hook728();
	throw new ReturnObject(wrl.doWork(root));
  }

  // line 1193 "../../../../Tree.ump"
   protected void hook671(WithRootLatched wrl) throws DatabaseException{
    this.hook729();
	throw new ReturnObject(wrl.doWork(root));
  }

  // line 1199 "../../../../Tree.ump"
   protected void hook672(byte [] idKey, UtilizationTracker tracker, IN subtreeRootIN, ArrayList nodeLadder, IN rootIN, boolean rootNeedsUpdating) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    if (root == null) {
	    throw new ReturnVoid();
	}
	rootIN = (IN) root.fetchTarget(database, null);
	rootIN.latch(false);
	searchDeletableSubTree(rootIN, idKey, nodeLadder);
	if (nodeLadder.size() == 0) {
	    if (purgeRoot) {
		subtreeRootIN = logTreeRemoval(rootIN, tracker);
		if (subtreeRootIN != null) {
		    rootNeedsUpdating = true;
		}
	    }
	} else {
	    SplitInfo detachPoint = (SplitInfo) nodeLadder.get(nodeLadder.size() - 1);
	    boolean deleteOk = detachPoint.parent.deleteEntry(detachPoint.index, true);
	    assert deleteOk;
	    rootNeedsUpdating = cascadeUpdates(nodeLadder, null, -1);
	    subtreeRootIN = detachPoint.child;
	}
  }

  // line 1222 "../../../../Tree.ump"
   protected void hook673() throws DatabaseException{
    
  }

  // line 1226 "../../../../Tree.ump"
   protected IN hook674(byte [] idKey, byte [] mainKey, IN in, IN deletedSubtreeRoot) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    this.hook730(in);
	assert in instanceof BIN;
	assert in.getNEntries() > 0;
	int index = in.findEntry(mainKey, false, true);
	if (index >= 0) {
	    deletedSubtreeRoot = deleteDupSubtree(idKey, (BIN) in, index);
	}
	return deletedSubtreeRoot;
  }

  // line 1237 "../../../../Tree.ump"
   protected void hook675(DIN duplicateRoot) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 1241 "../../../../Tree.ump"
   protected void hook676(ArrayList nodeLadder) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 1244 "../../../../Tree.ump"
   protected void hook677(DIN dupRoot) throws DatabaseException{
    
  }

  // line 1247 "../../../../Tree.ump"
   protected void hook678(DIN dupRoot) throws DatabaseException{
    
  }

  // line 1250 "../../../../Tree.ump"
   protected void hook679(IN child) throws DatabaseException{
    
  }

  // line 1253 "../../../../Tree.ump"
   protected void hook680(IN child) throws DatabaseException{
    
  }

  // line 1256 "../../../../Tree.ump"
   protected void hook681(IN potentialParent) throws DatabaseException{
    
  }

  // line 1259 "../../../../Tree.ump"
   protected void hook682(IN searchResult) throws DatabaseException{
    
  }

  // line 1264 "../../../../Tree.ump"
   protected void hook683(TreeLocation location, byte [] mainKey, byte [] dupKey, LN ln, boolean splitsAllowed, boolean findDeletedEntries, boolean searchDupTree, boolean updateGeneration, boolean exactSearch, boolean indicateIfExact, Node childNode) throws DatabaseException{
    if (childNode == null) {
	} else if (ln.containsDuplicates()) {
	    throw new ReturnBoolean(searchDupTreeForDupCountLNParent(location, mainKey, childNode));
	} else {
	    if (childNode.containsDuplicates()) {
		if (dupKey == null) {
		    throw new ReturnBoolean(
			    searchDupTreeByNodeId(location, childNode, ln, searchDupTree, updateGeneration));
		} else {
		    throw new ReturnBoolean(searchDupTreeForDBIN(location, dupKey, (DIN) childNode, ln,
			    findDeletedEntries, indicateIfExact, exactSearch, splitsAllowed, updateGeneration));
		}
	    }
	}
  }

  // line 1281 "../../../../Tree.ump"
   protected void hook684(BIN oldBIN) throws DatabaseException{
    
  }

  // line 1286 "../../../../Tree.ump"
   protected void hook685(TreeLocation location, byte [] dupKey, DIN dupRoot, LN ln, boolean findDeletedEntries, boolean indicateIfExact, boolean exactSearch, boolean splitsAllowed, boolean updateGeneration) throws DatabaseException{
    if (maybeSplitDuplicateRoot(location.bin, location.index)) {
	    dupRoot = (DIN) location.bin.fetchTarget(location.index);
	}
	this.hook731(location);
	location.lnKey = dupKey;
	if (splitsAllowed) {
	    try {
		location.bin = (BIN) searchSubTreeSplitsAllowed(dupRoot, location.lnKey, ln.getNodeId(),
			updateGeneration);
	    } catch (SplitRequiredException e) {
		throw new DatabaseException(e);
	    }
	} else {
	    location.bin = (BIN) searchSubTree(dupRoot, location.lnKey, SearchType.NORMAL, ln.getNodeId(), null,
		    updateGeneration);
	}
	location.index = location.bin.findEntry(location.lnKey, indicateIfExact, exactSearch);
	boolean match;
	if (findDeletedEntries) {
	    match = (location.index >= 0 && (location.index & IN.EXACT_MATCH) != 0);
	    location.index &= ~IN.EXACT_MATCH;
	} else {
	    match = (location.index >= 0);
	}
	if (match) {
	    location.childLsn = location.bin.getLsn(location.index);
	    throw new ReturnBoolean(true);
	} else {
	    throw new ReturnBoolean(false);
	}
  }

  // line 1320 "../../../../Tree.ump"
   protected void hook686(boolean traverseWithinDupTree, boolean forward, byte [] idKey, IN next, IN parent, IN nextIN) throws DatabaseException{
    while (true) {
	    SearchResult result = null;
	    if (!traverseWithinDupTree) {
		result = getParentINForChildIN(next, true, true);
		if (result.exactParentFound) {
		    parent = result.parent;
		} else {
		    this.hook733();
		    throw new ReturnObject(null);
		}
	    } else {
		if (next.isRoot()) {
		    this.hook734(next);
		    throw new ReturnObject(null);
		} else {
		    result = getParentINForChildIN(next, true, true);
		    if (result.exactParentFound) {
			parent = result.parent;
		    } else {
			throw new ReturnObject(null);
		    }
		}
	    }
	    this.hook732();
	    int index = parent.findEntry(idKey, false, false);
	    boolean moreEntriesThisBin = false;
	    if (forward) {
		index++;
		if (index < parent.getNEntries()) {
		    moreEntriesThisBin = true;
		}
	    } else {
		if (index > 0) {
		    moreEntriesThisBin = true;
		}
		index--;
	    }
	    if (moreEntriesThisBin) {
		nextIN = (IN) parent.fetchTarget(index);
		this.hook735(nextIN);
		if (nextIN instanceof BIN) {
		    this.hook736(parent);
		    TreeWalkerStatsAccumulator treeStatsAccumulator = getTreeStatsAccumulator();
		    if (treeStatsAccumulator != null) {
			nextIN.accumulateStats(treeStatsAccumulator);
		    }
		    throw new ReturnObject((BIN) nextIN);
		} else {
		    IN ret = searchSubTree(nextIN, null, (forward ? SearchType.LEFT : SearchType.RIGHT), -1, null,
			    true);
		    this.hook737(parent);
		    if (ret instanceof BIN) {
			throw new ReturnObject((BIN) ret);
		    } else {
			throw new InconsistentNodeException("subtree did not have a BIN for leaf");
		    }
		}
	    }
	    next = parent;
	}
  }

  // line 1383 "../../../../Tree.ump"
   protected void hook687() throws DatabaseException{
    
  }

  // line 1387 "../../../../Tree.ump"
   protected void hook688(LogManager logManager, INList inMemoryINs, IN curRoot, long curRootLsn, long logLsn, IN newRoot) throws DatabaseException{
    byte[] rootIdKey = curRoot.getKey(0);
	newRoot = new IN(database, rootIdKey, maxMainTreeEntriesPerNode, curRoot.getLevel() + 1);
	newRoot.setIsRoot(true);
	curRoot.setIsRoot(false);
	try {
	    curRootLsn = curRoot.logProvisional(logManager, newRoot);
	    boolean insertOk = newRoot.insertEntry(new ChildReference(curRoot, rootIdKey, curRootLsn));
	    assert insertOk;
	    logLsn = newRoot.log(logManager);
	} catch (DatabaseException e) {
	    curRoot.setIsRoot(true);
	    throw e;
	}
	inMemoryINs.add(newRoot);
	root.setTarget(newRoot);
	root.setLsn(logLsn);
	curRoot.split(newRoot, 0, maxMainTreeEntriesPerNode);
	root.setLsn(newRoot.getLastFullVersion());
  }

  // line 1408 "../../../../Tree.ump"
   protected void hook689(IN curRoot) throws DatabaseException{
    
  }

  // line 1411 "../../../../Tree.ump"
   protected void hook690(IN parent) throws DatabaseException{
    
  }

  // line 1414 "../../../../Tree.ump"
   protected void hook691(IN parent) throws DatabaseException{
    
  }

  // line 1417 "../../../../Tree.ump"
   protected void hook692(IN parent) throws DatabaseException,Throwable{
    
  }

  // line 1420 "../../../../Tree.ump"
   protected void hook693(IN child) throws DatabaseException,Throwable{
    
  }

  // line 1423 "../../../../Tree.ump"
   protected void hook694(IN parent, IN child) throws DatabaseException{
    
  }

  // line 1426 "../../../../Tree.ump"
   protected void hook695(IN parent) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 1429 "../../../../Tree.ump"
   protected void hook696(SplitInfo info5) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 1433 "../../../../Tree.ump"
   protected void hook697(ArrayList nodeLadder) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 1437 "../../../../Tree.ump"
   protected void hook698(IN parent, byte [] key, long nid, boolean updateGeneration, int index, IN child) throws DatabaseException,SplitRequiredException{
    do {
	    if (parent.getNEntries() == 0) {
		throw new ReturnObject(parent);
	    } else {
		index = parent.findEntry(key, false, false);
	    }
	    assert index >= 0;
	    child = (IN) parent.fetchTarget(index);
	    child.latch(updateGeneration);
	    if (child.needsSplitting()) {
		this.hook739(parent, child);
		throw splitRequiredException;
	    }
	    if (child.getNodeId() == nid) {
		this.hook740(child);
		throw new ReturnObject(parent);
	    }
	    this.hook738(parent);
	    parent = child;
	} while (!(parent instanceof BIN));
	throw new ReturnObject(parent);
  }

  // line 1461 "../../../../Tree.ump"
   protected void hook699(IN parent) throws DatabaseException,SplitRequiredException{
    
  }

  // line 1464 "../../../../Tree.ump"
   protected void hook700(IN parent) throws DatabaseException,SplitRequiredException{
    
  }

  // line 1467 "../../../../Tree.ump"
   protected void hook701(boolean updateGeneration, IN rootIN) throws DatabaseException{
    if (root != null) {
	    rootIN = (IN) root.fetchTarget(database, null);
	    rootIN.latch(updateGeneration);
	}
	throw new ReturnObject(rootIN);
  }

  // line 1475 "../../../../Tree.ump"
   protected void hook702() throws DatabaseException{
    
  }

  // line 1479 "../../../../Tree.ump"
   protected void hook703(LN ln, byte [] key, boolean allowDuplicates, CursorImpl cursor, LockResult lnLock, EnvironmentImpl env, LogManager logManager, INList inMemoryINs, BIN bin) throws DatabaseException{
    bin = findBinForInsert(key, logManager, inMemoryINs, cursor);
	this.hook741(bin);
	ChildReference newLNRef = new ChildReference(ln, key, DbLsn.NULL_LSN);
	cursor.setBIN(bin);
	int index = bin.insertEntry1(newLNRef);
	if ((index & IN.INSERT_SUCCESS) != 0) {
	    index &= ~IN.INSERT_SUCCESS;
	    cursor.updateBin(bin, index);
	    long newLsn = DbLsn.NULL_LSN;
	    try {
		newLsn = ln.log(env, database.getId(), key, DbLsn.NULL_LSN, cursor.getLocker());
	    } finally {
		if (newLsn == DbLsn.NULL_LSN) {
		    bin.setKnownDeleted(index);
		}
	    }
	    lnLock.setAbortLsn(DbLsn.NULL_LSN, true, true);
	    bin.updateEntry(index, newLsn);
	    this.hook657(ln, env, bin, index, newLsn);
	    throw new ReturnBoolean(true);
	} else {
	    index &= ~IN.EXACT_MATCH;
	    cursor.updateBin(bin, index);
	    LN currentLN = null;
	    boolean isDup = false;
	    Node n = bin.fetchTarget(index);
	    if (n == null || n instanceof LN) {
		currentLN = (LN) n;
	    } else {
		isDup = true;
	    }
	    boolean isDeleted = false;
	    LockResult currentLock = null;
	    if (!isDup) {
		if (currentLN == null) {
		    isDeleted = true;
		} else {
		    currentLock = cursor.lockLNDeletedAllowed(currentLN, LockType.WRITE);
		    currentLN = currentLock.getLN();
		    bin = cursor.getBIN();
		    index = cursor.getIndex();
		    if (cursor.getDupBIN() != null) {
			cursor.clearDupBIN(true);
			isDup = true;
		    } else if (bin.isEntryKnownDeleted(index) || currentLN == null || currentLN.isDeleted()) {
			isDeleted = true;
		    }
		}
	    }
	    if (isDeleted) {
		long abortLsn = bin.getLsn(index);
		boolean abortKnownDeleted = true;
		if (currentLN != null && currentLock.getLockGrant() == LockGrantType.EXISTING) {
		    long nodeId = currentLN.getNodeId();
		    Locker locker = cursor.getLocker();
		    WriteLockInfo info6 = locker.getWriteLockInfo(nodeId);
		    abortLsn = info6.getAbortLsn();
		    abortKnownDeleted = info6.getAbortKnownDeleted();
		}
		lnLock.setAbortLsn(abortLsn, abortKnownDeleted);
		long newLsn = ln.log(env, database.getId(), key, DbLsn.NULL_LSN, cursor.getLocker());
		bin.updateEntry(index, ln, newLsn, key);
		bin.clearKnownDeleted(index);
		bin.clearPendingDeleted(index);
		this.hook658(ln, env, bin, index, newLsn);
		throw new ReturnBoolean(true);
	    } else {
		throw new ReturnBoolean(
			insertDuplicate(key, bin, ln, logManager, inMemoryINs, cursor, lnLock, allowDuplicates));
	    }
	}
  }

  // line 1555 "../../../../Tree.ump"
   protected void hook704(byte [] key, BIN bin, LN newLN, CursorImpl cursor, LockResult lnLock, boolean allowDuplicates, EnvironmentImpl env, int index, boolean successfulInsert, DIN dupRoot, Node n, long binNid, DBIN dupBin) throws DatabaseException{
    dupRoot = (DIN) n;
	this.hook744(dupRoot);
	LockResult dclLockResult = cursor.lockDupCountLN(dupRoot, LockType.WRITE);
	bin = cursor.getBIN();
	index = cursor.getIndex();
	if (!allowDuplicates) {
	    DupCountLN dcl = (DupCountLN) dclLockResult.getLN();
	    if (dcl.getDupCount() > 0) {
		throw new ReturnBoolean(false);
	    }
	}
	maybeSplitDuplicateRoot(bin, index);
	dupRoot = (DIN) bin.fetchTarget(index);
	byte[] newLNKey = newLN.getData();
	long previousLsn = dupRoot.getLastFullVersion();
	try {
	    dupBin = (DBIN) searchSubTreeSplitsAllowed(dupRoot, newLNKey, -1, true);
	} catch (SplitRequiredException e) {
	    throw new DatabaseException(e);
	}
	long currentLsn = dupRoot.getLastFullVersion();
	if (currentLsn != previousLsn) {
	    bin.updateEntry(index, currentLsn);
	}
	this.hook743(cursor);
	bin = null;
	dupRoot = null;
	ChildReference newLNRef = new ChildReference(newLN, newLNKey, DbLsn.NULL_LSN);
	int dupIndex = dupBin.insertEntry1(newLNRef);
	if ((dupIndex & IN.INSERT_SUCCESS) != 0) {
	    dupIndex &= ~IN.INSERT_SUCCESS;
	    cursor.updateDBin(dupBin, dupIndex);
	    long newLsn = DbLsn.NULL_LSN;
	    try {
		newLsn = newLN.log(env, database.getId(), key, DbLsn.NULL_LSN, cursor.getLocker());
	    } finally {
		if (newLsn == DbLsn.NULL_LSN) {
		    dupBin.setKnownDeleted(dupIndex);
		}
	    }
	    lnLock.setAbortLsn(DbLsn.NULL_LSN, true, true);
	    dupBin.setLsn(dupIndex, newLsn);
	    this.hook659(newLN, binNid, dupBin, newLsn);
	    successfulInsert = true;
	} else {
	    dupIndex &= ~IN.EXACT_MATCH;
	    cursor.updateDBin(dupBin, dupIndex);
	    LN currentLN = (LN) dupBin.fetchTarget(dupIndex);
	    boolean isDeleted = false;
	    LockResult currentLock = null;
	    if (currentLN == null) {
		isDeleted = true;
	    } else {
		currentLock = cursor.lockLNDeletedAllowed(currentLN, LockType.WRITE);
		currentLN = currentLock.getLN();
		dupBin = cursor.getDupBIN();
		dupIndex = cursor.getDupIndex();
		if (dupBin.isEntryKnownDeleted(dupIndex) || currentLN == null || currentLN.isDeleted()) {
		    isDeleted = true;
		}
	    }
	    if (isDeleted) {
		long abortLsn = dupBin.getLsn(dupIndex);
		boolean abortKnownDeleted = true;
		if (currentLN != null && currentLock.getLockGrant() == LockGrantType.EXISTING) {
		    long nodeId = currentLN.getNodeId();
		    Locker locker = cursor.getLocker();
		    WriteLockInfo info7 = locker.getWriteLockInfo(nodeId);
		    abortLsn = info7.getAbortLsn();
		    abortKnownDeleted = info7.getAbortKnownDeleted();
		}
		lnLock.setAbortLsn(abortLsn, abortKnownDeleted);
		long newLsn = newLN.log(env, database.getId(), key, DbLsn.NULL_LSN, cursor.getLocker());
		dupBin.updateEntry(dupIndex, newLN, newLsn, newLNKey);
		dupBin.clearKnownDeleted(dupIndex);
		dupBin.clearPendingDeleted(dupIndex);
		this.hook660(newLN, binNid, dupBin, newLsn);
		successfulInsert = true;
	    } else {
		successfulInsert = false;
	    }
	}
	this.hook742(dupBin);
	dupBin = null;
	if (successfulInsert) {
	    this.hook746(cursor);
	    dupRoot = cursor.getLatchedDupRoot(false);
	    this.hook745(cursor);
	    dupRoot.incrementDuplicateCount(dclLockResult, key, cursor.getLocker(), true);
	}
  }

  // line 1648 "../../../../Tree.ump"
   protected void hook705(DIN dupRoot) throws DatabaseException{
    
  }

  // line 1652 "../../../../Tree.ump"
   protected void hook706(BIN bin, int index, DIN curRoot, LogManager logManager, INList inMemoryINs, byte [] rootIdKey, DIN newRoot, long curRootLsn, long logLsn) throws DatabaseException{
    newRoot.setIsRoot(true);
	curRoot.setDupCountLN(null);
	curRoot.setIsRoot(false);
	try {
	    curRootLsn = curRoot.logProvisional(logManager, newRoot);
	    boolean insertOk = newRoot.insertEntry(new ChildReference(curRoot, rootIdKey, bin.getLsn(index)));
	    assert insertOk;
	    logLsn = newRoot.log(logManager);
	} catch (DatabaseException e) {
	    curRoot.setIsRoot(true);
	    throw e;
	}
	inMemoryINs.add(newRoot);
	bin.updateEntry(index, newRoot, logLsn);
	curRoot.split(newRoot, 0, maxDupTreeEntriesPerNode);
  }

  // line 1670 "../../../../Tree.ump"
   protected void hook707(DIN newRoot) throws DatabaseException{
    
  }

  // line 1675 "../../../../Tree.ump"
   protected void hook708(byte [] key, LogManager logManager, INList inMemoryINs, LN newLN, CursorImpl cursor, EnvironmentImpl env, DIN dupRoot, DBIN dupBin, BIN bin, int index, LN existingLN, byte [] newLNKey, Locker locker, DupCountLN dupCountLN, long firstDupCountLNLsn) throws DatabaseException{
    long dbinLsn = dupBin.logProvisional(logManager, dupRoot);
	inMemoryINs.add(dupBin);
	dupRoot.setEntry(0, dupBin, dupBin.getKey(0), dbinLsn, dupBin.getState(0));
	long dinLsn = dupRoot.log(logManager);
	inMemoryINs.add(dupRoot);
	LockResult lockResult = locker.lock(dupCountLN.getNodeId(), LockType.WRITE, false, database);
	lockResult.setAbortLsn(firstDupCountLNLsn, false);
	dupCountLN.setDupCount(2);
	long dupCountLsn = dupCountLN.log(env, database.getId(), key, firstDupCountLNLsn, locker);
	dupRoot.updateDupCountLNRef(dupCountLsn);
	long newLsn = newLN.log(env, database.getId(), key, DbLsn.NULL_LSN, locker);
	int dupIndex = dupBin.insertEntry1(new ChildReference(newLN, newLNKey, newLsn));
	dupIndex &= ~IN.INSERT_SUCCESS;
	cursor.updateDBin(dupBin, dupIndex);
	bin.adjustCursorsForMutation(index, dupBin, dupIndex ^ 1, cursor);
	this.hook747(dupBin);
	bin.updateEntry(index, dupRoot, dinLsn);
	bin.setMigrate(index, false);
	this.hook664(newLN, dupRoot, dupBin, bin, existingLN, dupCountLN, dbinLsn, dinLsn, dupCountLsn, newLsn);
  }

  // line 1697 "../../../../Tree.ump"
   protected void hook709(DBIN dupBin) throws DatabaseException{
    
  }

  // line 1700 "../../../../Tree.ump"
   protected void hook710(DIN dupRoot) throws DatabaseException{
    
  }

  // line 1704 "../../../../Tree.ump"
   protected BIN hook711(byte [] key, LogManager logManager, INList inMemoryINs, BIN bin, boolean rootLatchIsHeld) throws DatabaseException{
    long logLsn;
	while (true) {
	    rootLatchIsHeld = true;
	    this.hook748();
	    if (root == null) {
		this.hook751();
		if (root != null) {
		    this.hook752();
		    rootLatchIsHeld = false;
		    continue;
		}
		bin = new BIN(database, key, maxMainTreeEntriesPerNode, 1);
		this.hook750(bin);
		logLsn = bin.logProvisional(logManager, null);
		IN rootIN = new IN(database, key, maxMainTreeEntriesPerNode, 2);
		rootIN.setIsRoot(true);
		boolean insertOk = rootIN.insertEntry(new ChildReference(bin, key, logLsn));
		assert insertOk;
		logLsn = rootIN.log(logManager);
		rootIN.setDirty(true);
		root = new ChildReference(rootIN, new byte[0], logLsn);
		inMemoryINs.add(bin);
		inMemoryINs.add(rootIN);
		this.hook749();
		rootLatchIsHeld = false;
		break;
	    } else {
		this.hook753();
		rootLatchIsHeld = false;
		IN in = searchSplitsAllowed(key, -1, true);
		if (in == null) {
		    continue;
		} else {
		    bin = (BIN) in;
		    break;
		}
	    }
	}
	return bin;
  }

  // line 1746 "../../../../Tree.ump"
   protected void hook712(BIN bin) throws DatabaseException{
    
  }

  // line 1749 "../../../../Tree.ump"
   protected void hook713(INList inList, IN subtreeRoot, UtilizationTracker tracker) throws DatabaseException{
    subtreeRoot.accountForSubtreeRemoval(inList, tracker);
  }

  // line 1753 "../../../../Tree.ump"
   protected void hook714(INList inMemoryList) throws DatabaseException{
    Node rootIN = root.getTarget();
	if (rootIN != null) {
	    rootIN.rebuildINList(inMemoryList);
	}
  }

  // line 1760 "../../../../Tree.ump"
   protected void hook715(int index) throws DatabaseException{
    IN rootIN = (IN) root.fetchTarget(database, null);
	throw new ReturnBoolean(rootIN.validateSubtreeBeforeDelete(index));
  }

  // line 1765 "../../../../Tree.ump"
   protected void hook728() throws DatabaseException{
    
  }

  // line 1768 "../../../../Tree.ump"
   protected void hook729() throws DatabaseException{
    
  }

  // line 1771 "../../../../Tree.ump"
   protected void hook730(IN in) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }

  // line 1774 "../../../../Tree.ump"
   protected void hook731(TreeLocation location) throws DatabaseException{
    
  }

  // line 1777 "../../../../Tree.ump"
   protected void hook732() throws DatabaseException{
    
  }

  // line 1780 "../../../../Tree.ump"
   protected void hook733() throws DatabaseException{
    
  }

  // line 1783 "../../../../Tree.ump"
   protected void hook734(IN next) throws DatabaseException{
    
  }

  // line 1786 "../../../../Tree.ump"
   protected void hook735(IN nextIN) throws DatabaseException{
    
  }

  // line 1789 "../../../../Tree.ump"
   protected void hook736(IN parent) throws DatabaseException{
    
  }

  // line 1792 "../../../../Tree.ump"
   protected void hook737(IN parent) throws DatabaseException{
    
  }

  // line 1795 "../../../../Tree.ump"
   protected void hook738(IN parent) throws DatabaseException,SplitRequiredException{
    
  }

  // line 1798 "../../../../Tree.ump"
   protected void hook739(IN parent, IN child) throws DatabaseException,SplitRequiredException{
    
  }

  // line 1801 "../../../../Tree.ump"
   protected void hook740(IN child) throws DatabaseException,SplitRequiredException{
    
  }

  // line 1804 "../../../../Tree.ump"
   protected void hook741(BIN bin) throws DatabaseException{
    
  }

  // line 1807 "../../../../Tree.ump"
   protected void hook742(DBIN dupBin) throws DatabaseException{
    
  }

  // line 1810 "../../../../Tree.ump"
   protected void hook743(CursorImpl cursor) throws DatabaseException{
    
  }

  // line 1813 "../../../../Tree.ump"
   protected void hook744(DIN dupRoot) throws DatabaseException{
    
  }

  // line 1816 "../../../../Tree.ump"
   protected void hook745(CursorImpl cursor) throws DatabaseException{
    
  }

  // line 1819 "../../../../Tree.ump"
   protected void hook746(CursorImpl cursor) throws DatabaseException{
    
  }

  // line 1822 "../../../../Tree.ump"
   protected void hook747(DBIN dupBin) throws DatabaseException{
    
  }

  // line 1825 "../../../../Tree.ump"
   protected void hook748() throws DatabaseException{
    
  }

  // line 1828 "../../../../Tree.ump"
   protected void hook749() throws DatabaseException{
    
  }

  // line 1831 "../../../../Tree.ump"
   protected void hook750(BIN bin) throws DatabaseException{
    
  }

  // line 1834 "../../../../Tree.ump"
   protected void hook751() throws DatabaseException{
    
  }

  // line 1837 "../../../../Tree.ump"
   protected void hook752() throws DatabaseException{
    
  }

  // line 1840 "../../../../Tree.ump"
   protected void hook753() throws DatabaseException{
    
  }

  // line 1843 "../../../../Tree.ump"
   protected void hook754(BIN bin) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  // line 4 "../../../../Tree_static.ump"
  public static class SearchType
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public SearchType()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 9 "../../../../Tree_static.ump"
     private  SearchType(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../Tree_static.ump"
    public static final SearchType NORMAL=new SearchType() ;
  // line 6 "../../../../Tree_static.ump"
    public static final SearchType LEFT=new SearchType() ;
  // line 7 "../../../../Tree_static.ump"
    public static final SearchType RIGHT=new SearchType() ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  // line 11 "../../../../Tree_static.ump"
  public class RootChildReference extends ChildReference
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RootChildReference()
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
  
    // line 14 "../../../../Tree_static.ump"
     private  RootChildReference(){
      super();
    }
  
    // line 17 "../../../../Tree_static.ump"
     private  RootChildReference(Node target, byte [] key, long lsn){
      super(target,key,lsn);
    }
  
    // line 20 "../../../../Tree_static.ump"
     private  RootChildReference(Node target, byte [] key, long lsn, byte existingState){
      super(target,key,lsn,existingState);
    }
  
    // line 23 "../../../../Tree_static.ump"
     public Node fetchTarget(DatabaseImpl database, IN in) throws DatabaseException{
      this.hook666();
          return super.fetchTarget(database,in);
    }
  
    // line 27 "../../../../Tree_static.ump"
     public void setTarget(Node target){
      this.hook667();
          super.setTarget(target);
    }
  
    // line 31 "../../../../Tree_static.ump"
     public void clearTarget(){
      this.hook668();
          super.clearTarget();
    }
  
    // line 35 "../../../../Tree_static.ump"
     public void setLsn(long lsn){
      this.hook669();
          super.setLsn(lsn);
    }
  
    // line 39 "../../../../Tree_static.ump"
     protected void hook666() throws DatabaseException{
      
    }
  
    // line 41 "../../../../Tree_static.ump"
     protected void hook667(){
      
    }
  
    // line 43 "../../../../Tree_static.ump"
     protected void hook668(){
      
    }
  
    // line 45 "../../../../Tree_static.ump"
     protected void hook669(){
      
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  // line 47 "../../../../Tree_static.ump"
  public static class SplitInfo
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //SplitInfo Attributes
    private IN parent;
    private IN child;
    private int index;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public SplitInfo(IN aParent, IN aChild, int aIndex)
    {
      parent = aParent;
      child = aChild;
      index = aIndex;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setParent(IN aParent)
    {
      boolean wasSet = false;
      parent = aParent;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setChild(IN aChild)
    {
      boolean wasSet = false;
      child = aChild;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setIndex(int aIndex)
    {
      boolean wasSet = false;
      index = aIndex;
      wasSet = true;
      return wasSet;
    }
  
    public IN getParent()
    {
      return parent;
    }
  
    public IN getChild()
    {
      return child;
    }
  
    public int getIndex()
    {
      return index;
    }
  
    public void delete()
    {}
  
    // line 52 "../../../../Tree_static.ump"
    public  SplitInfo(IN parent, IN child, int index){
      this.parent=parent;
          this.child=child;
          this.index=index;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+
              "index" + ":" + getIndex()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "parent" + "=" + (getParent() != null ? !getParent().equals(this)  ? getParent().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
              "  " + "child" + "=" + (getChild() != null ? !getChild().equals(this)  ? getChild().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 57 "../../../../Tree_static.ump"
  public static class Tree_searchSplitsAllowed
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_searchSplitsAllowed()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 59 "../../../../Tree_static.ump"
    public  Tree_searchSplitsAllowed(Tree _this, byte [] key, long nid, boolean updateGeneration){
      this._this=_this;
          this.key=key;
          this.nid=nid;
          this.updateGeneration=updateGeneration;
    }
  
    // line 65 "../../../../Tree_static.ump"
    public IN execute() throws DatabaseException{
      insertTarget=null;
          while (insertTarget == null) {
            this.hook717();
            rootIN=null;
            this.hook716();
            if (rootIN == null) {
              break;
            }
            try {
              insertTarget=_this.searchSubTreeSplitsAllowed(rootIN,key,nid,updateGeneration);
            }
     catch (        SplitRequiredException e) {
              continue;
            }
          }
          return insertTarget;
    }
  
    // line 93 "../../../../Tree_static.ump"
     protected void hook716() throws DatabaseException{
      while (true) {
            if (_this.root != null) {
              rootIN=(IN)_this.root.fetchTarget(_this.database,null);
              if (rootIN.needsSplitting()) {
                b=true;
                this.hook721();
                if (b)             continue;
                this.hook720();
                env=_this.database.getDbEnvironment();
                env.getDbMapTree().modifyDbRoot(_this.database);
                this.hook719();
                rootIN=(IN)_this.root.fetchTarget(_this.database,null);
              }
              this.hook718();
            }
            break;
          }
    }
  
    // line 112 "../../../../Tree_static.ump"
     protected void hook717() throws DatabaseException{
      
    }
  
    // line 114 "../../../../Tree_static.ump"
     protected void hook718() throws DatabaseException{
      
    }
  
    // line 116 "../../../../Tree_static.ump"
     protected void hook719() throws DatabaseException{
      
    }
  
    // line 118 "../../../../Tree_static.ump"
     protected void hook720() throws DatabaseException{
      
    }
  
    // line 120 "../../../../Tree_static.ump"
     protected void hook721() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 82 "../../../../Tree_static.ump"
    protected Tree _this ;
  // line 83 "../../../../Tree_static.ump"
    protected byte[] key ;
  // line 84 "../../../../Tree_static.ump"
    protected long nid ;
  // line 85 "../../../../Tree_static.ump"
    protected boolean updateGeneration ;
  // line 86 "../../../../Tree_static.ump"
    protected IN insertTarget ;
  // line 87 "../../../../Tree_static.ump"
    protected boolean rootLatched ;
  // line 88 "../../../../Tree_static.ump"
    protected boolean rootLatchedExclusive ;
  // line 89 "../../../../Tree_static.ump"
    protected IN rootIN ;
  // line 90 "../../../../Tree_static.ump"
    protected boolean b ;
  // line 91 "../../../../Tree_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 122 "../../../../Tree_static.ump"
  public static class Tree_forceSplit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_forceSplit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 124 "../../../../Tree_static.ump"
    public  Tree_forceSplit(Tree _this, IN parent, byte [] key){
      this._this=_this;
          this.parent=parent;
          this.key=key;
    }
  
    // line 129 "../../../../Tree_static.ump"
    public void execute() throws DatabaseException,SplitRequiredException{
      nodeLadder=new ArrayList();
          allLeftSideDescent=true;
          allRightSideDescent=true;
    {
          }
          child=null;
          origParent=parent;
          iter=null;
          this.hook722();
          success=false;
          try {
            this.hook723();
            if (origParent.needsSplitting() || !origParent.isRoot()) {
              throw _this.splitRequiredException;
            }
            do {
              if (parent.getNEntries() == 0) {
                break;
              }
     else {
                index=parent.findEntry(key,false,false);
                if (index != 0) {
                  allLeftSideDescent=false;
                }
                if (index != (parent.getNEntries() - 1)) {
                  allRightSideDescent=false;
                }
              }
              assert index >= 0;
              child=(IN)parent.getTarget(index);
              if (child == null) {
                break;
              }
     else {
                this.hook724();
                nodeLadder.add(new SplitInfo(parent,child,index));
              }
              parent=child;
            }
     while (!(parent instanceof BIN));
            startedSplits=false;
            logManager=_this.database.getDbEnvironment().getLogManager();
            iter=nodeLadder.listIterator(nodeLadder.size());
            lastParentForSplit=-1;
            while (iter.hasPrevious()) {
              info1=(SplitInfo)iter.previous();
              child=info1.child;
              parent=info1.parent;
              index=info1.index;
              if (child.needsSplitting()) {
                maxEntriesPerNode=(child.containsDuplicates() ? _this.maxDupTreeEntriesPerNode : _this.maxMainTreeEntriesPerNode);
                if (allLeftSideDescent || allRightSideDescent) {
                  child.splitSpecial(parent,index,maxEntriesPerNode,key,allLeftSideDescent);
                }
     else {
                  child.split(parent,index,maxEntriesPerNode);
                }
                lastParentForSplit=parent.getNodeId();
                startedSplits=true;
                if (parent.isDbRoot()) {
                  this.hook726();
                  _this.root.setLsn(parent.getLastFullVersion());
                  parent.setDirty(true);
                }
              }
     else {
                if (startedSplits) {
                  newLsn=0;
                  if (lastParentForSplit == child.getNodeId()) {
                    newLsn=child.getLastFullVersion();
                  }
     else {
                    newLsn=child.log(logManager);
                  }
                  parent.updateEntry(index,newLsn);
                }
              }
              this.hook725();
              child=null;
              iter.remove();
            }
            success=true;
          }
      finally {
            this.hook727();
          }
    }
  
    // line 236 "../../../../Tree_static.ump"
     protected void hook722() throws DatabaseException,SplitRequiredException{
      
    }
  
    // line 238 "../../../../Tree_static.ump"
     protected void hook723() throws DatabaseException,SplitRequiredException{
      
    }
  
    // line 240 "../../../../Tree_static.ump"
     protected void hook724() throws DatabaseException,SplitRequiredException{
      
    }
  
    // line 242 "../../../../Tree_static.ump"
     protected void hook725() throws DatabaseException,SplitRequiredException{
      
    }
  
    // line 244 "../../../../Tree_static.ump"
     protected void hook726() throws DatabaseException,SplitRequiredException{
      
    }
  
    // line 246 "../../../../Tree_static.ump"
     protected void hook727() throws DatabaseException,SplitRequiredException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 216 "../../../../Tree_static.ump"
    protected Tree _this ;
  // line 217 "../../../../Tree_static.ump"
    protected IN parent ;
  // line 218 "../../../../Tree_static.ump"
    protected byte[] key ;
  // line 219 "../../../../Tree_static.ump"
    protected ArrayList nodeLadder ;
  // line 220 "../../../../Tree_static.ump"
    protected boolean allLeftSideDescent ;
  // line 221 "../../../../Tree_static.ump"
    protected boolean allRightSideDescent ;
  // line 222 "../../../../Tree_static.ump"
    protected int index ;
  // line 223 "../../../../Tree_static.ump"
    protected IN child ;
  // line 224 "../../../../Tree_static.ump"
    protected IN origParent ;
  // line 225 "../../../../Tree_static.ump"
    protected ListIterator iter ;
  // line 226 "../../../../Tree_static.ump"
    protected boolean isRootLatched ;
  // line 227 "../../../../Tree_static.ump"
    protected boolean success ;
  // line 228 "../../../../Tree_static.ump"
    protected boolean startedSplits ;
  // line 229 "../../../../Tree_static.ump"
    protected LogManager logManager ;
  // line 230 "../../../../Tree_static.ump"
    protected long lastParentForSplit ;
  // line 231 "../../../../Tree_static.ump"
    protected SplitInfo info1 ;
  // line 232 "../../../../Tree_static.ump"
    protected int maxEntriesPerNode ;
  // line 233 "../../../../Tree_static.ump"
    protected long newLsn ;
  // line 234 "../../../../Tree_static.ump"
    protected SplitInfo info2 ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 37 "../../../../Tree.ump"
  private static final String TRACE_ROOT_SPLIT = "RootSplit:" ;
// line 39 "../../../../Tree.ump"
  private static final String TRACE_DUP_ROOT_SPLIT = "DupRootSplit:" ;
// line 41 "../../../../Tree.ump"
  private static final String TRACE_MUTATE = "Mut:" ;
// line 43 "../../../../Tree.ump"
  private static final String TRACE_INSERT = "Ins:" ;
// line 45 "../../../../Tree.ump"
  private static final String TRACE_INSERT_DUPLICATE = "InsD:" ;
// line 47 "../../../../Tree.ump"
  private DatabaseImpl database ;
// line 49 "../../../../Tree.ump"
  private ChildReference root ;
// line 51 "../../../../Tree.ump"
  private int maxMainTreeEntriesPerNode ;
// line 53 "../../../../Tree.ump"
  private int maxDupTreeEntriesPerNode ;
// line 55 "../../../../Tree.ump"
  private boolean purgeRoot ;
// line 57 "../../../../Tree.ump"
  private TreeStats treeStats ;
// line 59 "../../../../Tree.ump"
  private ThreadLocal treeStatsAccumulatorTL = new ThreadLocal() ;
// line 61 "../../../../Tree.ump"
  private static SplitRequiredException splitRequiredException = new SplitRequiredException() ;
// line 66 "../../../../Tree.ump"
  private TestHook waitHook ;
// line 68 "../../../../Tree.ump"
  private TestHook searchHook ;
// line 70 "../../../../Tree.ump"
  private TestHook ckptHook ;

  
}