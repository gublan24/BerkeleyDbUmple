/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
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
import com.sleepycat.je.latch.SharedLatch;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.LatchNotHeldException;
import com.sleepycat.je.log.*;

// line 3 "../../../../Tree.ump"
// line 4 "../../../../Tree_static.ump"
// line 3 "../../../../INCompressor_Tree.ump"
// line 3 "../../../../Latches_Tree.ump"
// line 4 "../../../../Latches_Tree_inner.ump"
public class Tree implements LogWritable,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private int maxMainTreeEntriesPerNode;
  private int maxDupTreeEntriesPerNode;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree()
  {
    maxMainTreeEntriesPerNode = 0;
    maxDupTreeEntriesPerNode = 0;
    // line 89 "../../../../Tree.ump"
    init(null);
            //maxMainTreeEntriesPerNode = 0;
            //maxDupTreeEntriesPerNode = 0;
    // END OF UMPLE AFTER INJECTION
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMaxMainTreeEntriesPerNode(int aMaxMainTreeEntriesPerNode)
  {
    boolean wasSet = false;
    maxMainTreeEntriesPerNode = aMaxMainTreeEntriesPerNode;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxDupTreeEntriesPerNode(int aMaxDupTreeEntriesPerNode)
  {
    boolean wasSet = false;
    maxDupTreeEntriesPerNode = aMaxDupTreeEntriesPerNode;
    wasSet = true;
    return wasSet;
  }

  public int getMaxMainTreeEntriesPerNode()
  {
    return maxMainTreeEntriesPerNode;
  }

  public int getMaxDupTreeEntriesPerNode()
  {
    return maxDupTreeEntriesPerNode;
  }

  public void delete()
  {}


  /**
   * 
   * Create a new tree.
   */
  // line 81 "../../../../Tree.ump"
   public  Tree(DatabaseImpl database) throws DatabaseException{
    init(database);
        setDatabase(database);
  }


  /**
   * 
   * constructor helper
   */
  // line 98 "../../../../Tree.ump"
   private void init(DatabaseImpl database){
    // line 25 "../../../../Latches_Tree.ump"
    rootLatch =	    LatchSupport.makeSharedLatch	    ("RootLatch",	     (database != null) ? database.getDbEnvironment() : null);
    // END OF UMPLE BEFORE INJECTION
    treeStats = new TreeStats();
        this.root = null;
        this.database = database;
  }


  /**
   * 
   * Set the database for this tree. Used by recovery when recreating an existing tree.
   */
  // line 107 "../../../../Tree.ump"
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
  // line 118 "../../../../Tree.ump"
   public DatabaseImpl getDatabase(){
    return database;
  }


  /**
   * 
   * Set the root for the tree. Should only be called within the root latch.
   */
  // line 125 "../../../../Tree.ump"
   public void setRoot(ChildReference newRoot, boolean notLatched){
    // line 32 "../../../../Latches_Tree.ump"
    assert(notLatched || rootLatch.isWriteLockedByCurrentThread());
    // END OF UMPLE BEFORE INJECTION
    root = newRoot;
  }

  // line 129 "../../../../Tree.ump"
   public ChildReference makeRootChildReference(Node target, byte [] key, long lsn){
    return new RootChildReference(target, key, lsn);
  }

  // line 133 "../../../../Tree.ump"
   private ChildReference makeRootChildReference(){
    return new RootChildReference();
  }


  /**
   * 
   * Get LSN of the rootIN. Obtained without latching, should only be accessed while quiescent.
   */
  // line 140 "../../../../Tree.ump"
   public long getRootLsn(){
    if (root == null) {
            return DbLsn.NULL_LSN;
        } else {
            return root.getLsn();
        }
  }


  /**
   * --- ADDED
   */
  // line 151 "../../../../Tree.ump"
   public boolean rootExists(){
    if (root == null) {
            return false;
        } 
        
        if ((root.getTarget() == null) && 
            (root.getLsn() == DbLsn.NULL_LSN)) {
            return false;
        }

        return true;
  }


  /**
   * ---
   * 
   * @return the TreeStats for this tree.
   */
  // line 169 "../../../../Tree.ump"
  public TreeStats getTreeStats(){
    return treeStats;
  }

  // line 173 "../../../../Tree.ump"
   private TreeWalkerStatsAccumulator getTreeStatsAccumulator(){
    if (EnvironmentImpl.getThreadLocalReferenceCount() > 0) {
            return (TreeWalkerStatsAccumulator) treeStatsAccumulatorTL.get();
        } else {
            return null;
        }
  }

  // line 181 "../../../../Tree.ump"
   public void setTreeStatsAccumulator(TreeWalkerStatsAccumulator tSA){
    treeStatsAccumulatorTL.set(tSA);
  }

  // line 185 "../../../../Tree.ump"
   public IN withRootLatchedExclusive(WithRootLatched wrl) throws DatabaseException{
    try {

            Label728:
rootLatch.acquireExclusive();
 ; 
            return wrl.doWork(root); 

        }
        finally  {
            Label670:
rootLatch.release();
;
        }
  }

  // line 197 "../../../../Tree.ump"
   public IN withRootLatchedShared(WithRootLatched wrl) throws DatabaseException{
    try {
            Label671:
rootLatch.acquireShared();
 ; 
            return wrl.doWork(root); 
        }
       finally  {
            Label729:
rootLatch.release();
 ; 
        }
  }


  /**
   * 
   * Deletes a BIN specified by key from the tree. If the BIN resides in a subtree that can be pruned away, prune as much as possible, so we don't leave a branch that has no BINs. It's possible that the targeted BIN will now have entries, or will have resident cursors. Either will prevent deletion.
   * @param idKey -the identifier key of the node to delete.
   * @param trackeris used for tracking obsolete node info.
   */
  // line 213 "../../../../Tree.ump"
   public void delete(byte [] idKey, UtilizationTracker tracker) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    IN subtreeRootIN = null;
      ArrayList nodeLadder = new ArrayList();
      IN rootIN = null;  
      boolean rootNeedsUpdating = false;      
      try {

            Label672:
rootLatch.acquireExclusive();
 ;
            if (root == null) {
                return; //throw new ReturnVoid();
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
            } 
            else {
                SplitInfo detachPoint = (SplitInfo) nodeLadder.get(nodeLadder.size() - 1);
                boolean deleteOk = detachPoint.parent.deleteEntry(detachPoint.index, true);
                assert deleteOk;
                rootNeedsUpdating = cascadeUpdates(nodeLadder, null, -1);
                subtreeRootIN = detachPoint.child;
            }

           if (subtreeRootIN != null) {
                    EnvironmentImpl envImpl = database.getDbEnvironment();
                    if (rootNeedsUpdating) {
                        DbTree dbTree = envImpl.getDbMapTree();
                        dbTree.modifyDbRoot(database);
                        Label661: ; //this.hook661();
                    }
                    INList inList = envImpl.getInMemoryINs();
                    accountForSubtreeRemoval(inList, subtreeRootIN, tracker);
                }
            } 


			finally {
            Label672_1:
releaseNodeLadderLatches(nodeLadder);

            if (rootIN != null) {
                rootIN.releaseLatch();
            }

            rootLatch.release();
  ;
        }

     if (subtreeRootIN != null) {

            EnvironmentImpl envImpl = database.getDbEnvironment();
            if (rootNeedsUpdating) {
                /*
                 * modifyDbRoot will grab locks and we can't have the INList
                 * latches or root latch held while it tries to acquire locks.
                 */
                DbTree dbTree = envImpl.getDbMapTree();
                dbTree.modifyDbRoot(database);
                Label661: ;
            } 

            /* 
             * Count obsolete nodes after logging the delete. We can do
             * this without having the nodes of the subtree latched because the
             * subtree has been detached from the tree.
             */
            INList inList = envImpl.getInMemoryINs();
            accountForSubtreeRemoval(inList, subtreeRootIN, tracker);
        }
  }


  /**
   * 
   * This entire tree is empty, clear the root and log a new MapLN
   * @return the rootIN that has been detached, or null if there hasn't beenany removal.
   */
  // line 288 "../../../../Tree.ump"
   private IN logTreeRemoval(IN rootIN, UtilizationTracker tracker) throws DatabaseException{
    // line 71 "../../../../Latches_Tree.ump"
    assert rootLatch.isWriteLockedByCurrentThread();
    // END OF UMPLE BEFORE INJECTION
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
  // line 307 "../../../../Tree.ump"
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
                Label673:
assert rootLatch.isWriteLockedByCurrentThread();
  ; 
                root.setLsn(newLsn);
                rootNeedsUpdating = true;
            }
            else if ((binRoot != null) && info4.parent.isRoot()) {
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
  // line 344 "../../../../Tree.ump"
   public void deleteDup(byte [] idKey, byte [] mainKey, UtilizationTracker tracker) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    IN in = search(mainKey, SearchType.NORMAL, -1, null, false);
        IN deletedSubtreeRoot = null;
				try {

        Label730:
assert in.isLatchOwner();
 ; 
        assert in instanceof BIN;
        assert in .getNEntries() > 0;
        int index = in .findEntry(mainKey, false, true);
        if (index >= 0) {
            deletedSubtreeRoot = deleteDupSubtree(idKey, (BIN) in , index);
        }
				}
				finally {
        Label674:
in.releaseLatch();
   ;
				}

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
  // line 373 "../../../../Tree.ump"
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
                    Label754:
if (bin.getNEntries() == 0) {
					database.getDbEnvironment().addToCompressorQueue(bin, null, false);
			}
			//original(bin);
 ; //this.hook754(bin);
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
            Label676:
releaseNodeLadderLatches(nodeLadder);
        //original(nodeLadder);
; 
            if (dupCountLNLocked) {
                locker.releaseLock(dcl.getNodeId());
            }
            Label675:
if (duplicateRoot != null) {
            duplicateRoot.releaseLatch();
        }
        //original(duplicateRoot);
; 
        }
        return subtreeRootIN;
  }


  /**
   * 
   * Find the leftmost node (IN or BIN) in the tree. Do not descend into a duplicate tree if the leftmost entry of the first BIN refers to one.
   * @return the leftmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 425 "../../../../Tree.ump"
   public IN getFirstNode() throws DatabaseException{
    return search(null, SearchType.LEFT, -1, null, true);
  }


  /**
   * 
   * Find the rightmost node (IN or BIN) in the tree. Do not descend into a duplicate tree if the rightmost entry of the last BIN refers to one.
   * @return the rightmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 433 "../../../../Tree.ump"
   public IN getLastNode() throws DatabaseException{
    return search(null, SearchType.RIGHT, -1, null, true);
  }


  /**
   * 
   * Find the leftmost node (DBIN) in a duplicate tree.
   * @return the leftmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 441 "../../../../Tree.ump"
   public DBIN getFirstNode(DIN dupRoot) throws DatabaseException{
    if (dupRoot == null) {
            throw new IllegalArgumentException("getFirstNode passed null root");
        }
        Label677:
assert dupRoot.isLatchOwner();
        //original(dupRoot);
 ; //this.hook677(dupRoot);
        IN ret = searchSubTree(dupRoot, null, SearchType.LEFT, -1, null, true);
        return (DBIN) ret;
  }


  /**
   * 
   * Find the rightmost node (DBIN) in a duplicate tree.
   * @return the rightmost node in the tree, null if the tree is empty. Thereturned node is latched and the caller must release it.
   */
  // line 454 "../../../../Tree.ump"
   public DBIN getLastNode(DIN dupRoot) throws DatabaseException{
    if (dupRoot == null) {
            throw new IllegalArgumentException("getLastNode passed null root");
        }
        Label678:
assert dupRoot.isLatchOwner();
        //original(dupRoot);
 ; //this.hook678(dupRoot);
        IN ret = searchSubTree(dupRoot, null, SearchType.RIGHT, -1, null, true);
        return (DBIN) ret;
  }


  /**
   * 
   * GetParentNode without optional tracking.
   */
  // line 467 "../../../../Tree.ump"
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
  // line 480 "../../../../Tree.ump"
   public SearchResult getParentINForChildIN(IN child, boolean requireExactMatch, boolean updateGeneration, int targetLevel, List trackingList) throws DatabaseException{
    if (child == null) {
            throw new IllegalArgumentException("getParentNode passed null");
        }
        Label680:
assert child.isLatchOwner();
        //original(child);
 ; //this.hook680(child);
        byte[] mainTreeKey = child.getMainTreeKey();
        byte[] dupTreeKey = child.getDupTreeKey();
        boolean isRoot = child.isRoot();
        Label679:
child.releaseLatch();
        //original(child);
 ; //this.hook679(child);
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
  // line 504 "../../../../Tree.ump"
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
                Label681:
potentialParent.releaseLatchIfOwner();
        //original(potentialParent);
; //this.hook681(potentialParent);
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
  // line 542 "../../../../Tree.ump"
   public boolean getParentBINForChildLN(TreeLocation location, byte [] mainKey, byte [] dupKey, LN ln, boolean splitsAllowed, boolean findDeletedEntries, boolean searchDupTree, boolean updateGeneration) throws DatabaseException{
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
                    Label682:
searchResult.releaseLatchIfOwner();
        //original(searchResult);
; //this.hook682(searchResult);
                    msg.append("searchResult=" + searchResult.getClass() + " nodeId=" + searchResult.getNodeId() +
                        " nEntries=" + searchResult.getNEntries());
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
try {
                        Label683: ; 
                            if (childNode == null) {} else if (ln.containsDuplicates()) {
                                return searchDupTreeForDupCountLNParent(location, mainKey, childNode);
                            } else {
                                if (childNode.containsDuplicates()) {
                                    if (dupKey == null) {
                                        return    searchDupTreeByNodeId(location, childNode, ln, searchDupTree, updateGeneration);
                                    } else {
                                        return searchDupTreeForDBIN(location, dupKey, (DIN) childNode, ln, findDeletedEntries, indicateIfExact, exactSearch, splitsAllowed, updateGeneration);
                                    }
                                }
                            }
                        }
   catch (DatabaseException e) {
                        Label683_1:
location.bin.releaseLatchIfOwner();
   ;
                        throw e;
                    }

    }                       
   }
           
                location.childLsn = location.bin.getLsn(location.index);
            } else {
                location.lnKey = mainKey;
            }
            return match;
  }


  /**
   * 
   * For SR [#8984]: our prospective child is a deleted LN, and we're facing a dup tree. Alas, the deleted LN has no data, and therefore nothing to guide the search in the dup tree. Instead, we search by node id. This is very expensive, but this situation is a very rare case.
   */
  // line 615 "../../../../Tree.ump"
   private boolean searchDupTreeByNodeId(TreeLocation location, Node childNode, LN ln, boolean searchDupTree, boolean updateGeneration) throws DatabaseException{
    if (searchDupTree) {
            BIN oldBIN = location.bin;
            if (childNode.matchLNByNodeId(location, ln.getNodeId())) {
                location.index &= ~IN.EXACT_MATCH;
                Label684:
if (oldBIN != null) {
            oldBIN.releaseLatch();
        }
        //original(oldBIN);
 ; //this.hook684(oldBIN);
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
  // line 635 "../../../../Tree.ump"
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
  // line 651 "../../../../Tree.ump"
   private boolean searchDupTreeForDBIN(TreeLocation location, byte [] dupKey, DIN dupRoot, LN ln, boolean findDeletedEntries, boolean indicateIfExact, boolean exactSearch, boolean splitsAllowed, boolean updateGeneration) throws DatabaseException{
    try {
      assert dupKey != null;
      Label685:
dupRoot.latch();
 ; 
               
      if (maybeSplitDuplicateRoot(location.bin, location.index)) {
        dupRoot = (DIN) location.bin.fetchTarget(location.index);
      }
      Label731:
location.bin.releaseLatch();
            //original(location);
 ; 
      location.lnKey = dupKey;
      if (splitsAllowed) {
        try {
          location.bin = (BIN) searchSubTreeSplitsAllowed(dupRoot, location.lnKey, ln.getNodeId(), updateGeneration);
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
        return true;
      } else {
        return false;
      }
    } 

    catch (DatabaseException e) {
      Label685_1:
//	try {	    //original(location, dupKey, dupRoot, ln, findDeletedEntries, indicateIfExact, exactSearch, splitsAllowed, updateGeneration);} 

            dupRoot.releaseLatchIfOwner();
 ;
            throw e;
     }
  }


  /**
   * 
   * Return a reference to the adjacent BIN.
   * @param binThe BIN to find the next BIN for. This BIN is latched.
   * @param traverseWithinDupTreeif true, only search within the dup tree and return null whenthe traversal runs out of duplicates.
   * @return The next BIN, or null if there are no more. The returned node islatched and the caller must release it. If null is returned, the argument BIN remains latched.
   */
  // line 699 "../../../../Tree.ump"
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
  // line 709 "../../../../Tree.ump"
   public BIN getPrevBin(BIN bin, boolean traverseWithinDupTree) throws DatabaseException{
    return getNextBinInternal(traverseWithinDupTree, bin, false);
  }


  /**
   * 
   * Helper routine for above two routines to iterate through BIN's.
   */
  // line 717 "../../../../Tree.ump"
   private BIN getNextBinInternal(boolean traverseWithinDupTree, BIN bin, boolean forward) throws DatabaseException{
    byte[] idKey = null;
      IN parent = null;
      IN nextIN = null;
      if (bin.getNEntries() == 0) {
        idKey = bin.getIdentifierKey();
      } else if (forward) {
        idKey = bin.getKey(bin.getNEntries() - 1);
      } else {
        idKey = bin.getKey(0);
      }
      IN next = bin;
    try {
      Label687:
assert LatchSupport.countLatchesHeld() == 1: LatchSupport.latchesHeldToString();
        //original();
 ; // this.hook687();

      Label686: ; // this.hook686(traverseWithinDupTree, forward, idKey, next, parent, nextIN);
      while (true) {
        SearchResult result = null;
        if (!traverseWithinDupTree) {
          result = getParentINForChildIN(next, true, true);
          if (result.exactParentFound) {
            parent = result.parent;
          } else {
            Label733:
assert(LatchSupport.countLatchesHeld() == 0): LatchSupport.latchesHeldToString();
            //original();
 ; // this.hook733();
            return null; // throw new ReturnObject(null);
          }
        } else {
          if (next.isRoot()) {
            Label734:
next.releaseLatch();
            //original(next);
 ; // this.hook734(next);
            return null; // throw new ReturnObject(null);
          } else {
            result = getParentINForChildIN(next, true, true);
            if (result.exactParentFound) {
              parent = result.parent;
            } else {
              return null; // throw new ReturnObject(null);
            }
          }
        }
        Label732:
assert(LatchSupport.countLatchesHeld() == 1): LatchSupport.latchesHeldToString();
            //original();
 ; // this.hook732();
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
          Label735:
nextIN.latch();
            assert(LatchSupport.countLatchesHeld() == 2): LatchSupport.latchesHeldToString();
 ; // this.hook735(nextIN);
          if (nextIN instanceof BIN) {
            Label736:
parent.releaseLatch();
            //original(parent);
 ; // this.hook736(parent);
            TreeWalkerStatsAccumulator treeStatsAccumulator = getTreeStatsAccumulator();
            if (treeStatsAccumulator != null) {
              nextIN.accumulateStats(treeStatsAccumulator);
            }
            return (BIN) nextIN; // throw new ReturnObject((BIN) nextIN);
          } else {
            IN ret = searchSubTree(nextIN, null, (forward ? SearchType.LEFT : SearchType.RIGHT), -1, null, true);
            Label737:
parent.releaseLatch();
            assert LatchSupport.countLatchesHeld() == 1: LatchSupport.latchesHeldToString();
            //original(parent);
 ; // this.hook737(parent);
            if (ret instanceof BIN) {
              return (BIN) ret; // throw new ReturnObject((BIN) ret);
            } else {
              throw new InconsistentNodeException("subtree did not have a BIN for leaf");
            }
          }
        }
        next = parent;
      }
      // end hook686
    } catch (DatabaseException e) {
      Label686_1:
//					try {							//original(traverseWithinDupTree, forward, idKey, next, parent, nextIN);} 

     //   catch (DatabaseException e) {
            next.releaseLatchIfOwner();
            if (parent != null) {
                parent.releaseLatchIfOwner();
            }
            if (nextIN != null) {
                nextIN.releaseLatchIfOwner();
            }

      //  }
 ;
                  throw e;

    }
  }


  /**
   * 
   * Split the root of the tree.
   */
  // line 802 "../../../../Tree.ump"
   private void splitRoot() throws DatabaseException{
    EnvironmentImpl env = database.getDbEnvironment();
        LogManager logManager = env.getLogManager();
        INList inMemoryINs = env.getInMemoryINs();
        IN curRoot = null;
        curRoot = (IN) root.fetchTarget(database, null);
        Label689:
curRoot.latch();
        //original(curRoot);
 ; //this.hook689(curRoot);
        long curRootLsn = 0;
        long logLsn = 0;
        IN newRoot = null;
        Label688: ; 
				try{
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
        finally { Label688_1:
curRoot.releaseLatch();
 ;
        }    
            treeStats.nRootSplits++;
        Label662: ;
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
  // line 848 "../../../../Tree.ump"
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
  // line 861 "../../../../Tree.ump"
   public IN searchSplitsAllowed(byte [] key, long nid, boolean updateGeneration) throws DatabaseException{
    IN insertTarget = null;
        while (insertTarget == null) {
    				Label717:
;
        rootLatch.acquireShared();
        boolean rootLatched = true;
	      boolean rootLatchedExclusive = false;
 ;
            IN rootIN = null;
	    try {
		while (true) {
		    if (root != null) {
			rootIN = (IN) root.fetchTarget(database, null);

			/* Check if root needs splitting. */
			if (rootIN.needsSplitting()) {
			    if (!rootLatchedExclusive) {
						Label718:
rootIN = null;
				rootLatch.release();
				rootLatch.acquireExclusive();
				rootLatchedExclusive = true;
				continue;
 
			    }
			    splitRoot();

			    /*
			     * We can't hold any latches while we lock.  If the
			     * root splits again between latch release and
			     * DbTree.db lock, no problem.  The latest root
			     * will still get written out.
			     */
					Label719:
rootLatch.release();
			    rootLatched = false;
 ;
			    EnvironmentImpl env = database.getDbEnvironment();
			    env.getDbMapTree().modifyDbRoot(database);
					Label720:
rootLatched = true;
			    rootLatch.acquireExclusive();
 ;
			    rootIN = (IN) root.fetchTarget(database, null);
			}
				Label721:
rootIN.latch();
 ;
		    }
		    break;
		}
	    } finally {
Label716_1:
if (rootLatched) {
						rootLatch.release();	
				}
 ;


	    }

            /* Don't loop forever if the root is null. [#13897] */
            if (rootIN == null) {
                break;
            } 

            try {
                insertTarget = searchSubTreeSplitsAllowed(rootIN, key, nid,
                                                          updateGeneration);
            } catch (SplitRequiredException e) {

                /* 
                 * The last slot in the root was used at the point when this
                 * thread released the rootIN latch in order to force splits.
                 * Retry. SR [#11147].
                 */
                continue;
            }
        }

        return insertTarget;        



//return new Tree_searchSplitsAllowed(this, key, nid, updateGeneration).execute();
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
  // line 935 "../../../../Tree.ump"
   public IN searchSubTree(IN parent, byte [] key, SearchType searchType, long nid, BINBoundary binBoundary, boolean updateGeneration) throws DatabaseException{
    if (parent == null) {
            return null;
        }
        if ((searchType == SearchType.LEFT || searchType == SearchType.RIGHT) && key != null) {
            throw new IllegalArgumentException("searchSubTree passed key and left/right search");
        }
        Label690:
assert parent.isLatchOwner();
        //original(parent);
 ; //this.hook690(parent);
        if (parent.getNodeId() == nid) {
            Label691:
parent.releaseLatch();
        //original(parent);
; //this.hook691(parent);
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
                    Label693:
child.releaseLatch();
        //original(child);
; //this.hook693(child);
                    return parent;
                }
                Label692:
parent.releaseLatch();
        //original(parent);
 ; //this.hook692(parent);
                parent = child;
            } while (!(parent instanceof BIN));
            return child;
        } catch (Throwable t) {
            Label694:
if (child != null) {
            child.releaseLatchIfOwner();
        }
        parent.releaseLatchIfOwner();
        //original(parent, child);
; //this.hook694(parent, child);
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
  // line 1006 "../../../../Tree.ump"
   public void searchDeletableSubTree(IN parent, byte [] key, ArrayList nodeLadder) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    assert(parent != null);
        assert(key != null);
        Label695:
assert parent.isLatchOwner();
        //original(parent);
 ; //this.hook695(parent);
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
                    Label696:
info5.child.releaseLatch();
        //original(info5);
; //this.hook696(info5);
                    iter.remove();
                }
            }
        } else {
            Label697:
releaseNodeLadderLatches(nodeLadder);
        //original(nodeLadder);
; //this.hook697(nodeLadder);
            nodeLadder.clear();
        }
  }


  /**
   * 
   * Search the portion of the tree starting at the parent, permitting preemptive splits.
   */
  // line 1056 "../../../../Tree.ump"
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
  // line 1077 "../../../../Tree.ump"
   private IN searchSubTreeUntilSplit(IN parent, byte [] key, long nid, boolean updateGeneration) throws DatabaseException,SplitRequiredException{
    //  
            if (parent == null) {
                return null;
            }
            Label699:
assert parent.isLatchOwner();
            //original(parent);
 ; //this.hook699(parent);
            if (parent.getNodeId() == nid) {
                Label700:
parent.releaseLatch();
            //original(parent);
; //this.hook700(parent);
                return null;
            }
            int index = 0;
            IN child = null;
            try {
            Label698: ; //this.hook698(parent, key, nid, updateGeneration, index, child);
            do {
                if (parent.getNEntries() == 0) {
                    return parent; //throw new ReturnObject(parent);
                } else {
                    index = parent.findEntry(key, false, false);
                }
                assert index >= 0;
                child = (IN) parent.fetchTarget(index);
                child.latch(updateGeneration);
                if (child.needsSplitting()) {
                    Label739:
child.releaseLatch();
            parent.releaseLatch();
            //original(parent, child);
; //this.hook739(parent, child);
                    throw splitRequiredException;
                }
                if (child.getNodeId() == nid) {
                    Label740:
child.releaseLatch();
            //original(child);
; //this.hook740(child);
                    return parent; //throw new ReturnObject(parent);
                }
                Label738:
parent.releaseLatch();
            //original(parent);
 ; //this.hook738(parent);
                parent = child;
            } while (!(parent instanceof BIN));

            //hook698
            } catch (DatabaseException e) {
                       Label698_1:
if (child != null) {
                child.releaseLatchIfOwner();
            }
            parent.releaseLatchIfOwner();
            
            throw e;
        }

            return parent; //throw new ReturnObject(parent);
               
// throw ReturnHack.returnObject;
//        } catch (ReturnObject r) {
  //          return (IN) r.value;
  //      }
  }


  /**
   * 
   * Do pre-emptive splitting in the subtree topped by the "parent" node. Search down the tree until we get to the BIN level, and split any nodes that fit the splittable requirement. Note that more than one node in the path may be splittable. For example, a tree might have a level2 IN and a BIN that are both splittable, and would be encountered by the same insert operation.
   */
  // line 1130 "../../../../Tree.ump"
   private void forceSplit(IN parent, byte [] key) throws DatabaseException,SplitRequiredException{
    ArrayList nodeLadder = new ArrayList();
				boolean allLeftSideDescent = true;
				boolean allRightSideDescent = true;
        int index;
        IN child = null;
        IN originalParent = parent;
        ListIterator iter = null;

				Label722:
;
				boolean isRootLatched = false;
 ;	
        boolean success = false;
        try {

            /*
             * Latch the root in order to update the root LSN when we're done.
             * Latch order must be: root, root IN.  We'll leave this method
             * with the original parent latched.
             */
            Label723:
if (originalParent.isDbRoot()) {
                rootLatch.acquireExclusive();
                isRootLatched = true;
            }
            originalParent.latch();
 ;

            /* 
             * Another thread may have crept in and 
             *  - used the last free slot in the parent, making it impossible 
             *    to correctly progagate the split. 
             *  - actually split the root, in which case we may be looking at 
             *    the wrong subtree for this search. 
             * If so, throw and retry from above. SR [#11144]
             */
            if (originalParent.needsSplitting() || !originalParent.isRoot()) {
                throw splitRequiredException;
            }

            /* 
             * Search downward to the BIN level, saving the information
             * needed to do a split if necessary.
             */
            do {
                if (parent.getNEntries() == 0) {
                    /* No more children, can't descend anymore. */
                    break;
                } else {
                    /* Look for the entry matching key in the current node. */
                    index = parent.findEntry(key, false, false);
                    if (index != 0) {
                        allLeftSideDescent = false;
                    }
                    if (index != (parent.getNEntries() - 1)) {
                        allRightSideDescent = false;
                    }
                }

                assert index >= 0;

                /* 
                 * Get the child node that matches. We only need to work on
                 * nodes in residence.
                 */
                child = (IN) parent.getTarget(index);
                if (child == null) {
                    break;
                } else {
                   Label724:
child.latch();
 ;
                    nodeLadder.add(new SplitInfo(parent, child, index));
                } 

                /* Continue down a level */
                parent = child;
            } while (!(parent instanceof BIN));

            boolean startedSplits = false;
            LogManager logManager =
                database.getDbEnvironment().getLogManager();

            /* 
             * Process the accumulated nodes from the bottom up. Split each
             * node if required. If the node should not split, we check if
             * there have been any splits on the ladder yet. If there are none,
             * we merely release the node, since there is no update.  If splits
             * have started, we need to propagate new LSNs upward, so we log
             * the node and update its parent.
             *
             * Start this iterator at the end of the list.
             */
            iter = nodeLadder.listIterator(nodeLadder.size());
            long lastParentForSplit = -1;
            while (iter.hasPrevious()) {
                SplitInfo info = (SplitInfo) iter.previous();
                child = info.child;
                parent = info.parent;
                index = info.index;

                /* Opportunistically split the node if it is full. */
                if (child.needsSplitting()) {
		    int maxEntriesPerNode = (child.containsDuplicates() ?
					     maxDupTreeEntriesPerNode :
					     maxMainTreeEntriesPerNode);
                    if (allLeftSideDescent || allRightSideDescent) {
                        child.splitSpecial(parent,
                                           index,
                                           maxEntriesPerNode,
                                           key,
                                           allLeftSideDescent);
                    } else {
                        child.split(parent, index, maxEntriesPerNode);
                    }
                    lastParentForSplit = parent.getNodeId();
                    startedSplits = true;

                    /*
                     * If the DB root IN was logged, update the DB tree's child
                     * reference.  Now the MapLN is logically dirty, but the
                     * change hasn't been logged. Set the rootIN to be dirty
                     * again, to force flushing the rootIN and mapLN in the 
                     * next checkpoint. Be sure to flush the MapLN
                     * if we ever evict the root.
                     */
                    if (parent.isDbRoot()) {
                        Label725:
assert isRootLatched;
 ;
                        root.setLsn(parent.getLastFullVersion());
                        parent.setDirty(true);
                    }
                } else {
                    if (startedSplits) {
                        long newLsn = 0;

                        /* 
                         * If this child was the parent of a split, it's
                         * already logged by the split call. We just need to
                         * propagate the logging upwards. If this child is just
                         * a link in the chain upwards, log it.
                         */
                        if (lastParentForSplit == child.getNodeId()) {
                            newLsn = child.getLastFullVersion();
                        } else {
                            newLsn = child.log(logManager);
                        }
                        parent.updateEntry(index, newLsn);
                    } 
                }
								Label726:
child.releaseLatch();
 ;
                child = null;
                iter.remove();
            }

            success = true;
        } finally {
            Label727:
if (!success) {
                if (child != null) {
                    child.releaseLatchIfOwner();
                }
                originalParent.releaseLatchIfOwner();
            }

            /*
             * Unlatch any remaining children. There should only be remainders
             * in the event of an exception.
             */
            if (nodeLadder.size() > 0) {
                iter = nodeLadder.listIterator(nodeLadder.size());
                while (iter.hasPrevious()) {
                    SplitInfo info = (SplitInfo) iter.previous();
                    info.child.releaseLatchIfOwner();
                }
            }

            if (isRootLatched) {
                rootLatch.release();
            }
 ;
        }
  }


  /**
   * 
   * Helper to obtain the root IN with proper root latching. Optionally updates the generation of the root when latching it.
   */
  // line 1284 "../../../../Tree.ump"
   public IN getRootIN(boolean updateGeneration) throws DatabaseException{
    IN rootIN = null;
    Label702:
rootLatch.acquireShared();
            //original();
 ; 
    Label701: ; 
    try{
    if (root != null) {  
      rootIN = (IN) root.fetchTarget(database, null);
      rootIN.latch(updateGeneration);
    }
    return rootIN;		
	}
	finally{
    Label701_1:
rootLatch.release();
;
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
  // line 1310 "../../../../Tree.ump"
   public boolean insert(LN ln, byte [] key, boolean allowDuplicates, CursorImpl cursor, LockResult lnLock) throws DatabaseException{
    try {
      validateInsertArgs(allowDuplicates);
      EnvironmentImpl env = database.getDbEnvironment();
      LogManager logManager = env.getLogManager();
      INList inMemoryINs = env.getInMemoryINs();
      BIN bin = null;
      Label703: ;
      bin = findBinForInsert(key, logManager, inMemoryINs, cursor);
      Label741:
assert bin.isLatchOwner();
            //original(bin);
 ;
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
        Label657: ;
        return true;
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
          Label658: ; // this.hook658(ln, env, bin, index, newLsn);
          return true;
        } else {
          return insertDuplicate(key, bin, ln, logManager, inMemoryINs, cursor, lnLock, allowDuplicates);
        }
      }
    } finally {
      Label703_1:
cursor.releaseBIN();
 ;
    }
  }


  /**
   * 
   * Attempts to insert a duplicate at the current cursor BIN position. If an existing dup tree exists, insert into it; otherwise, create a new dup tree and place the new LN and the existing LN into it. If the current BIN entry contains an LN, the caller guarantees that it is not deleted.
   * @return true if duplicate inserted successfully, false if it was aduplicate duplicate, false if a there is an existing LN and allowDuplicates is false.
   */
  // line 1398 "../../../../Tree.ump"
   private boolean insertDuplicate(byte [] key, BIN bin, LN newLN, LogManager logManager, INList inMemoryINs, CursorImpl cursor, LockResult lnLock, boolean allowDuplicates) throws DatabaseException{
    EnvironmentImpl env = database.getDbEnvironment();
    int index = cursor.getIndex();
    boolean successfulInsert = false;
    DIN dupRoot = null;
    Node n = bin.fetchTarget(index);
    long binNid = bin.getNodeId();
    if (n instanceof DIN) {
      DBIN dupBin = null;
      Label704: ; // this.hook704(key, bin, newLN, cursor, lnLock, allowDuplicates, env, index,
                  // successfulInsert, dupRoot, n, binNid, dupBin);
      dupRoot = (DIN) n;
      Label744:
dupRoot.latch();
            //original(dupRoot);
 ; // this.hook744(dupRoot);
      LockResult dclLockResult = cursor.lockDupCountLN(dupRoot, LockType.WRITE);
      bin = cursor.getBIN();
      index = cursor.getIndex();
      if (!allowDuplicates) {
        DupCountLN dcl = (DupCountLN) dclLockResult.getLN();
        if (dcl.getDupCount() > 0) {
          return false;
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
      Label743:
cursor.releaseBIN();
            //original(cursor);
 ; // this.hook743(cursor);
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
        Label659: ;
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
          Label660: ; // this.hook660(newLN, binNid, dupBin, newLsn);
          successfulInsert = true;
        } else {
          successfulInsert = false;
        }
      }
      Label742:
dupBin.releaseLatch();
            //original(dupBin);
 ; // this.hook742(dupBin);
      dupBin = null;
      if (successfulInsert) {
        Label746:
cursor.latchBIN();
            //original(cursor);
 ; // this.hook746(cursor);
        dupRoot = cursor.getLatchedDupRoot(false);
        Label745:
cursor.releaseBIN();
            //original(cursor);
 ; // this.hook745(cursor);
        dupRoot.incrementDuplicateCount(dclLockResult, key, cursor.getLocker(), true);
      }
      Label704_1:
if (dupBin != null) {
                dupBin.releaseLatchIfOwner();
            }
            if (dupRoot != null) {
                dupRoot.releaseLatchIfOwner();
            }
 ;
    } else if (n instanceof LN) {
      if (!allowDuplicates) {
        return false;
      }
      try {
        lnLock.setAbortLsn(DbLsn.NULL_LSN, true, true);
        dupRoot = createDuplicateTree(key, logManager, inMemoryINs, newLN, cursor);
      } finally {
        if (dupRoot != null) {
          Label705:
dupRoot.releaseLatch();
            //original(dupRoot);
 ; // this.hook705(dupRoot);
          successfulInsert = true;
        } else {
          successfulInsert = false;
        }
      }
    } else {
      throw new InconsistentNodeException("neither LN or DIN found in BIN");
    }
    return successfulInsert;
  }


  /**
   * 
   * Check if the duplicate root needs to be split. The current duplicate root is latched. Exit with the new root (even if it's unchanged) latched and the old root (unless the root is unchanged) unlatched.
   * @param binthe BIN containing the duplicate root.
   * @param indexthe index of the duplicate root in bin.
   * @return true if the duplicate root was split.
   */
  // line 1528 "../../../../Tree.ump"
   private boolean maybeSplitDuplicateRoot(BIN bin, int index) throws DatabaseException{
    DIN curRoot = (DIN) bin.fetchTarget(index);

        if (curRoot.needsSplitting()) {

            EnvironmentImpl env = database.getDbEnvironment();
            LogManager logManager = env.getLogManager();
            INList inMemoryINs = env.getInMemoryINs();

            /* 
             * Make a new root DIN, giving it an id key from the previous root.
             */
            byte[] rootIdKey = curRoot.getKey(0);
            DIN newRoot = new DIN(database,
                                  rootIdKey,
                                  maxDupTreeEntriesPerNode,
                                  curRoot.getDupKey(),
                                  curRoot.getDupCountLNRef(),
                                  curRoot.getLevel() + 1);

            Label707:
newRoot.latch();
            //original(newRoot);
 ; 
            long curRootLsn = 0;
            long logLsn = 0;
            try {
                newRoot.setIsRoot(true);
                curRoot.setDupCountLN(null);
                curRoot.setIsRoot(false);

                /* 
                 * Make the new root DIN point to the old root DIN, and then
                 * log. We should be able to insert into the root because the
                 * root is newly created.
                 */
                try {
                    curRootLsn = curRoot.logProvisional(logManager, newRoot);
                    boolean insertOk = newRoot.insertEntry
                        (new ChildReference(curRoot, rootIdKey,
                                            bin.getLsn(index)));
                    assert insertOk;

                    logLsn = newRoot.log(logManager);
                } catch (DatabaseException e) {

                    /* Something went wrong when we tried to log. */
                    curRoot.setIsRoot(true);
                    throw e;
                }

                inMemoryINs.add(newRoot);
                bin.updateEntry(index, newRoot, logLsn);
                curRoot.split(newRoot, 0, maxDupTreeEntriesPerNode);
            } finally {
            	Label706_1:
curRoot.releaseLatch();
 ;
            }
             Label663: ;
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
  // line 1601 "../../../../Tree.ump"
   private DIN createDuplicateTree(byte [] key, LogManager logManager, INList inMemoryINs, LN newLN, CursorImpl cursor) throws DatabaseException{
    EnvironmentImpl env = database.getDbEnvironment();
        DIN dupRoot = null;
        DBIN dupBin = null;
        BIN bin = cursor.getBIN();
        int index = cursor.getIndex();

        /*
         * fetchTarget returned an LN before this method was called, and we're
         * still latched, so the target should never be null here.
         */
        LN existingLN = (LN) bin.fetchTarget(index);
 	boolean existingLNIsDeleted = bin.isEntryKnownDeleted(index) ||
 	    existingLN.isDeleted();
        assert existingLN != null;

        byte[] existingKey = existingLN.getData();
        byte[] newLNKey = newLN.getData();

        /* Check for duplicate duplicates. */
        boolean keysEqual = Key.compareKeys
            (newLNKey, existingKey, database.getDuplicateComparator()) == 0;
        if (keysEqual) {
            return null;
        }

        /* 
         * Replace the existing LN with a duplicate tree. 
         * 
         * Once we create a dup tree, we don't revert back to the LN.  Create
         * a DupCountLN to hold the count for this dup tree. Since we don't
         * roll back the internal nodes of a duplicate tree, we need to create
         * a pre-transaction version of the DupCountLN. This version must hold
         * a count of either 0 or 1, depending on whether the current
         * transaction created the exising lN or not. If the former, the count
         * must roll back to 0, if the latter, the count must roll back to 1.
         *
         * Note that we are logging a sequence of nodes and must make sure the
         * log can be correctly recovered even if the entire sequence doesn't
         * make it to the log. We need to make all children provisional to the
         * DIN. This works:
         *
         * Entry 1: (provisional) DupCountLN (first version)
         * Entry 2: (provisional) DupBIN 
         * Entry 3: DIN
         * Entry 4: DupCountLN (second version, incorporating the new count.
         *           This can't be provisional because we need to possibly
         *            roll it back.)
         * Entry 5: new LN.
         * See [SR #10203] for a description of the bug that existed before
         * this change.
         */

        /* Create the first version of DupCountLN and log it. (Entry 1). */
        Locker locker = cursor.getLocker();
 	long nodeId = existingLN.getNodeId();
 
 	/*
 	 * If the existing entry is known to be deleted or was created by this
 	 * transaction, then the DCL should get rolled back to 0, not 1.
 	 * [13726].
 	 */
 	int startingCount =
 	    (locker.createdNode(nodeId) ||
 	     existingLNIsDeleted ||
 	     locker.getWriteLockInfo(nodeId).getAbortKnownDeleted()) ?
 	    0 : 1;

        DupCountLN dupCountLN = new DupCountLN(startingCount);
        long firstDupCountLNLsn =
            dupCountLN.logProvisional(env, database.getId(),
				      key, DbLsn.NULL_LSN);

        /* Make the duplicate root and DBIN. */
        dupRoot = new DIN(database,
                          existingKey,                   // idkey
                          maxDupTreeEntriesPerNode,
                          key,                           // dup key
                          new ChildReference
                          (dupCountLN, key, firstDupCountLNLsn),
                          2);                            // level
				Label710:
dupRoot.latch();
 ;
        dupRoot.setIsRoot(true);

        dupBin = new DBIN(database,
                          existingKey,                   // idkey
                          maxDupTreeEntriesPerNode,
                          key,                           // dup key
                          1);                            // level
        Label709:
dupBin.latch();
 ;

        /* 
         * Attach the existing LN child to the duplicate BIN. Since this is a
         * newly created BIN, insertEntry will be successful.
         */
        ChildReference newExistingLNRef = new ChildReference
            (existingLN, existingKey, bin.getLsn(index), bin.getState(index));

        boolean insertOk = dupBin.insertEntry(newExistingLNRef);
        assert insertOk;

        try {

            /* Entry 2: DBIN. */
            long dbinLsn = dupBin.logProvisional(logManager, dupRoot);
            inMemoryINs.add(dupBin);
        
            /* Attach the duplicate BIN to the duplicate IN root. */
            dupRoot.setEntry(0, dupBin, dupBin.getKey(0),
                             dbinLsn, dupBin.getState(0));

            /* Entry 3:  DIN */
            long dinLsn = dupRoot.log(logManager);
            inMemoryINs.add(dupRoot);

            /*
             * Now that the DIN is logged, we've created a duplicate tree that
             * holds the single, preexisting LN. We can safely create the non
             * provisional LNs that pertain to this insert -- the new LN and
             * the new DupCountLN.
             *
             * We request a lock while holding latches which is usually
             * forbidden, but safe in this case since we know it will be
             * immediately granted (we just created dupCountLN above).
             */
            LockResult lockResult = locker.lock
                (dupCountLN.getNodeId(), LockType.WRITE, false /*noWait*/,
                 database);
            lockResult.setAbortLsn(firstDupCountLNLsn, false);

            dupCountLN.setDupCount(2);
            long dupCountLsn = dupCountLN.log(env, database.getId(), key,
                                              firstDupCountLNLsn, locker);
            dupRoot.updateDupCountLNRef(dupCountLsn);
        
            /* Add the newly created LN. */
            long newLsn =
                newLN.log(env, database.getId(), key, DbLsn.NULL_LSN, locker);
            int dupIndex = dupBin.insertEntry1
                (new ChildReference(newLN, newLNKey, newLsn));
            dupIndex &= ~IN.INSERT_SUCCESS;
            cursor.updateDBin(dupBin, dupIndex);

            /*
             * Adjust any cursors positioned on the mutated BIN entry to point
             * to the DBIN at the location of the entry we moved there.  The
             * index of the moved entry is 1 or 0, the XOR of the index of the
             * new entry.
             */
            bin.adjustCursorsForMutation(index, dupBin, dupIndex ^ 1, cursor);
            Label747:
dupBin.releaseLatch();
 ;


            /* 
             * Update the "regular" BIN to point to the new duplicate tree
             * instead of the existing LN.  Clear the MIGRATE flag since it
             * applies only to the original LN.
             */
            bin.updateEntry(index, dupRoot, dinLsn);
            bin.setMigrate(index, false);

            Label664: ;

        } catch (DatabaseException e) {

            /* 
             * Strictly speaking, not necessary to release latches, because if
             * we fail to log the entries, we just throw them away, but our
             * unit tests check for 0 latches held in the event of a logging
             * error.
             */
            Label708_1:
dupBin.releaseLatchIfOwner();
                dupRoot.releaseLatchIfOwner();
 ;
            throw e;
        }
        return dupRoot;
  }


  /**
   * 
   * Validate args passed to insert. Presently this just means making sure that if they say duplicates are allowed that the database supports duplicates.
   */
  // line 1782 "../../../../Tree.ump"
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
  // line 1794 "../../../../Tree.ump"
   private BIN findBinForInsert(byte [] key, LogManager logManager, INList inMemoryINs, CursorImpl cursor) throws DatabaseException{
    BIN bin;
        bin = cursor.latchBIN();
        if (bin != null) {
            if (!bin.needsSplitting() && bin.isKeyInBounds(key)) {
                return bin;
            } else {
                Label712:
bin.releaseLatch();
            //original(bin);
   ;; //this.hook712(bin);
            }
        }
        boolean rootLatchIsHeld = false;
        Label711:   ; 

            long logLsn;
try{
        while (true) {
            rootLatchIsHeld = true;
            Label748:
rootLatch.acquireShared();
            //original();
 ; //this.hook748();
            if (root == null) {
                Label751:
rootLatch.release();
            rootLatch.acquireExclusive();
            //original();
; //this.hook751();
                if (root != null) {
                    Label752:
rootLatch.release();
            //original();
; //this.hook752();
                    rootLatchIsHeld = false;
                    continue;
                }
                bin = new BIN(database, key, maxMainTreeEntriesPerNode, 1);
                Label750:
bin.latch();
            //original(bin);
; //this.hook750(bin);
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
                Label749:
rootLatch.release();
            //original();
; //this.hook749();
                rootLatchIsHeld = false;
                break;
            }
            else {
                Label753:
rootLatch.release();
            //original();
; //this.hook753();
                rootLatchIsHeld = false;
                IN in = searchSplitsAllowed(key, -1, true);
                if ( in == null) {
                    continue;
                } else {
                    bin = (BIN) in ;
                    break;
                }
            }
        }
}        finally
{ Label711_1:
if (rootLatchIsHeld) {
                rootLatch.release();
            }
   ;
            //end hook711
            if (ckptHook != null) {
                ckptHook.doHook();
            }
}
        return bin;
  }

  // line 1859 "../../../../Tree.ump"
   private void accountForSubtreeRemoval(INList inList, IN subtreeRoot, UtilizationTracker tracker) throws DatabaseException{
    try {
            Label713:
inList.latchMajor();
; //this.hook713(inList, subtreeRoot, tracker);
            //original(inList, subtreeRoot, tracker);
            subtreeRoot.accountForSubtreeRemoval(inList, tracker);
        }
        finally {
            Label713_1:
inList.releaseMajorLatch();
;
        }

        //end

        Label665: ; //this.hook665(subtreeRoot);
  }


  /**
   * 
   * @see LogWritable#getLogSize
   */
  // line 1877 "../../../../Tree.ump"
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
  // line 1888 "../../../../Tree.ump"
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
  // line 1898 "../../../../Tree.ump"
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
  // line 1909 "../../../../Tree.ump"
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
  // line 1920 "../../../../Tree.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 1927 "../../../../Tree.ump"
   public long getTransactionId(){
    return 0;
  }


  /**
   * 
   * rebuildINList is used by recovery to add all the resident nodes to the IN list.
   */
  // line 1934 "../../../../Tree.ump"
   public void rebuildINList() throws DatabaseException{
    INList inMemoryList = database.getDbEnvironment().getInMemoryINs();
        if (root != null) {
            rebuildINList_1:
rootLatch.acquireShared();
 ; 
				try {
            Node rootIN = root.getTarget();
            if (rootIN != null) {
                rootIN.rebuildINList(inMemoryList);
            }
				}
				finally{
            rebuildINList_2:
rootLatch.release();
 ; 
				}
        }
  }

  // line 1950 "../../../../Tree.ump"
   public void dump() throws DatabaseException{
    System.out.println(dumpString(0));
  }

  // line 1954 "../../../../Tree.ump"
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
  // line 1978 "../../../../Tree.ump"
  public boolean validateDelete(int index) throws DatabaseException{
    Label714:
rootLatch.acquireShared();
 ; 
        try {
            IN rootIN = (IN) root.fetchTarget(database, null);
            return rootIN.validateSubtreeBeforeDelete(index);
        } finally {
            Label715:
rootLatch.release();
 ;

        }
  }


  /**
   * 
   * Debugging check that all resident nodes are on the INList and no stray nodes are present in the unused portion of the IN arrays.
   */
  // line 1992 "../../../../Tree.ump"
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
                            throw new DatabaseException("IN " + parent.getNodeId() + " has stray node " +
                                node.getNodeId() + " at index " + i);
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

  // line 2025 "../../../../Tree.ump"
   public void setWaitHook(TestHook hook){
    waitHook = hook;
  }

  // line 2029 "../../../../Tree.ump"
   public void setSearchHook(TestHook hook){
    searchHook = hook;
  }

  // line 2033 "../../../../Tree.ump"
   public void setCkptHook(TestHook hook){
    ckptHook = hook;
  }

  // line 13 "../../../../Latches_Tree.ump"
   private void releaseNodeLadderLatches(ArrayList nodeLadder) throws LatchNotHeldException{
    ListIterator iter = nodeLadder.listIterator(nodeLadder.size());
        while (iter.hasPrevious()) {
            SplitInfo info3 = (SplitInfo) iter.previous();
            info3.child.releaseLatch();
        }
  }


  public String toString()
  {
    return super.toString() + "["+
            "maxMainTreeEntriesPerNode" + ":" + getMaxMainTreeEntriesPerNode()+ "," +
            "maxDupTreeEntriesPerNode" + ":" + getMaxDupTreeEntriesPerNode()+ "]";
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 6 "../../../../Tree_static.ump"
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
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 7 "../../../../Tree_static.ump"
    public static final SearchType NORMAL=new SearchType() ;
  // line 8 "../../../../Tree_static.ump"
    public static final SearchType LEFT=new SearchType() ;
  // line 9 "../../../../Tree_static.ump"
    public static final SearchType RIGHT=new SearchType() ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 13 "../../../../Tree_static.ump"
  // line 100 "../../../../Latches_Tree_inner.ump"
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
      Label666:
  if (getTarget() == null && !rootLatch.isWriteLockedByCurrentThread()) {
            rootLatch.release();
            rootLatch.acquireExclusive();
          }
             ;  
          return super.fetchTarget(database,in);
    }
  
    // line 27 "../../../../Tree_static.ump"
     public void setTarget(Node target){
      Label667:
  assert rootLatch.isWriteLockedByCurrentThread();
             ;  
          super.setTarget(target);
    }
  
    // line 31 "../../../../Tree_static.ump"
     public void clearTarget(){
      Label668:
  assert rootLatch.isWriteLockedByCurrentThread();
             ;  
          super.clearTarget();
    }
  
    // line 35 "../../../../Tree_static.ump"
     public void setLsn(long lsn){
      Label669:
  assert rootLatch.isWriteLockedByCurrentThread();
             ;  
          super.setLsn(lsn);
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 40 "../../../../Tree_static.ump"
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
  
  
    public String toString()
    {
      return super.toString() + "["+
              "index" + ":" + getIndex()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "parent" + "=" + (getParent() != null ? !getParent().equals(this)  ? getParent().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
              "  " + "child" + "=" + (getChild() != null ? !getChild().equals(this)  ? getChild().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 46 "../../../../Tree_static.ump"
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
  
    // line 48 "../../../../Tree_static.ump"
    public  Tree_searchSplitsAllowed(Tree _this, byte [] key, long nid, boolean updateGeneration){
      this._this=_this;
          this.key=key;
          this.nid=nid;
          this.updateGeneration=updateGeneration;
    }
  
    // line 55 "../../../../Tree_static.ump"
     public IN execute() throws DatabaseException{
      insertTarget = null;
        while (insertTarget == null) {
          Label717: ; // this.hook717();
          rootIN = null;
          Label716: ; // this.hook716();
  
          while (true) {
            if (_this.root != null) {
              rootIN = (IN) _this.root.fetchTarget(_this.database, null);
              if (rootIN.needsSplitting()) {
                b = true;
                Label721: ; // this.hook721();
                if (b)
                  continue;
                abel720: ;// this.hook720();
                env = _this.database.getDbEnvironment();
                env.getDbMapTree().modifyDbRoot(_this.database);
                abel719: ;// this.hook719();
                rootIN = (IN) _this.root.fetchTarget(_this.database, null);
              }
              abel718: ;// this.hook718();
            }
            break;
          }
          Label716_1: ;
          if (rootIN == null) {
            break;
          }
          try {
            insertTarget = _this.searchSubTreeSplitsAllowed(rootIN, key, nid, updateGeneration);
          } catch (SplitRequiredException e) {
            continue;
          }
        }
        return insertTarget;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 91 "../../../../Tree_static.ump"
    protected Tree _this ;
  // line 92 "../../../../Tree_static.ump"
    protected byte[] key ;
  // line 93 "../../../../Tree_static.ump"
    protected long nid ;
  // line 94 "../../../../Tree_static.ump"
    protected boolean updateGeneration ;
  // line 95 "../../../../Tree_static.ump"
    protected IN insertTarget ;
  // line 96 "../../../../Tree_static.ump"
    protected boolean rootLatched ;
  // line 97 "../../../../Tree_static.ump"
    protected boolean rootLatchedExclusive ;
  // line 98 "../../../../Tree_static.ump"
    protected IN rootIN ;
  // line 99 "../../../../Tree_static.ump"
    protected boolean b ;
  // line 100 "../../../../Tree_static.ump"
    protected EnvironmentImpl env ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 103 "../../../../Tree_static.ump"
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
  
    // line 105 "../../../../Tree_static.ump"
    public  Tree_forceSplit(Tree _this, IN parent, byte [] key){
      this._this=_this;
          this.parent=parent;
          this.key=key;
    }
  
    // line 110 "../../../../Tree_static.ump"
    public void execute() throws DatabaseException,SplitRequiredException{
      nodeLadder=new ArrayList();
          allLeftSideDescent=true;
          allRightSideDescent=true;
    {
          }
          child=null;
          origParent=parent;
          iter=null;
          Label722:           ;  //this.hook722();
          success=false;
          try {
            Label723:           ;  //this.hook723();
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
                Label724:           ;  //this.hook724();
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
                  Label726:           ;  //this.hook726();
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
              Label725:           ;  //this.hook725();
              child=null;
              iter.remove();
            }
            success=true;
          }
      finally {
            Label727:           ;  //this.hook727();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 197 "../../../../Tree_static.ump"
    protected Tree _this ;
  // line 198 "../../../../Tree_static.ump"
    protected IN parent ;
  // line 199 "../../../../Tree_static.ump"
    protected byte[] key ;
  // line 200 "../../../../Tree_static.ump"
    protected ArrayList nodeLadder ;
  // line 201 "../../../../Tree_static.ump"
    protected boolean allLeftSideDescent ;
  // line 202 "../../../../Tree_static.ump"
    protected boolean allRightSideDescent ;
  // line 203 "../../../../Tree_static.ump"
    protected int index ;
  // line 204 "../../../../Tree_static.ump"
    protected IN child ;
  // line 205 "../../../../Tree_static.ump"
    protected IN origParent ;
  // line 206 "../../../../Tree_static.ump"
    protected ListIterator iter ;
  // line 207 "../../../../Tree_static.ump"
    protected boolean isRootLatched ;
  // line 208 "../../../../Tree_static.ump"
    protected boolean success ;
  // line 209 "../../../../Tree_static.ump"
    protected boolean startedSplits ;
  // line 210 "../../../../Tree_static.ump"
    protected LogManager logManager ;
  // line 211 "../../../../Tree_static.ump"
    protected long lastParentForSplit ;
  // line 212 "../../../../Tree_static.ump"
    protected SplitInfo info1 ;
  // line 213 "../../../../Tree_static.ump"
    protected int maxEntriesPerNode ;
  // line 214 "../../../../Tree_static.ump"
    protected long newLsn ;
  // line 215 "../../../../Tree_static.ump"
    protected SplitInfo info2 ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 38 "../../../../Tree.ump"
  private static final String TRACE_ROOT_SPLIT = "RootSplit:" ;
// line 40 "../../../../Tree.ump"
  private static final String TRACE_DUP_ROOT_SPLIT = "DupRootSplit:" ;
// line 42 "../../../../Tree.ump"
  private static final String TRACE_MUTATE = "Mut:" ;
// line 44 "../../../../Tree.ump"
  private static final String TRACE_INSERT = "Ins:" ;
// line 46 "../../../../Tree.ump"
  private static final String TRACE_INSERT_DUPLICATE = "InsD:" ;
// line 48 "../../../../Tree.ump"
  private DatabaseImpl database ;
// line 50 "../../../../Tree.ump"
  private ChildReference root ;
// line 56 "../../../../Tree.ump"
  private boolean purgeRoot ;
// line 58 "../../../../Tree.ump"
  private TreeStats treeStats ;
// line 60 "../../../../Tree.ump"
  private ThreadLocal treeStatsAccumulatorTL = new ThreadLocal() ;
// line 62 "../../../../Tree.ump"
  private static SplitRequiredException splitRequiredException = new SplitRequiredException() ;
// line 71 "../../../../Tree.ump"
  private TestHook waitHook ;
// line 73 "../../../../Tree.ump"
  private TestHook searchHook ;
// line 75 "../../../../Tree.ump"
  private TestHook ckptHook ;
// line 9 "../../../../Latches_Tree.ump"
  private SharedLatch rootLatch ;

  
}