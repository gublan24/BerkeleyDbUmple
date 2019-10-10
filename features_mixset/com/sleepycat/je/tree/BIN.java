/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.TinyHashSet;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.txn.LockResult;
import com.sleepycat.je.txn.LockGrantType;
import com.sleepycat.je.txn.BasicLocker;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.CursorImpl;
import com.sleepycat.je.cleaner.Cleaner;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.Iterator;
import java.util.Comparator;
import com.sleepycat.je.log.*;

// line 3 "../../../../BIN.ump"
// line 3 "../../../../MemoryBudget_BIN.ump"
// line 3 "../../../../Evictor_BIN.ump"
// line 3 "../../../../INCompressor_BIN.ump"
public class BIN extends IN implements LoggableObject
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BIN()
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

  // line 39 "../../../../BIN.ump"
   public  BIN(){
    cursorSet = new TinyHashSet();
	numDeltasSinceLastFull = 0;
	prohibitNextDelta = false;
  }

  // line 45 "../../../../BIN.ump"
   public  BIN(DatabaseImpl db, byte [] identifierKey, int maxEntriesPerNode, int level){
    super(db, identifierKey, maxEntriesPerNode, level);
	cursorSet = new TinyHashSet();
	numDeltasSinceLastFull = 0;
	prohibitNextDelta = false;
  }


  /**
   * 
   * Create a holder object that encapsulates information about this BIN for the INCompressor.
   */
  // line 55 "../../../../BIN.ump"
   public BINReference createReference(){
    return new BINReference(getNodeId(), getDatabase().getId(), getIdentifierKey());
  }


  /**
   * 
   * Create a new BIN. Need this because we can't call newInstance() without getting a 0 for nodeid.
   */
  // line 62 "../../../../BIN.ump"
   protected IN createNewInstance(byte [] identifierKey, int maxEntries, int level){
    return new BIN(getDatabase(), identifierKey, maxEntries, level);
  }


  /**
   * 
   * Get the key (dupe or identifier) in child that is used to locate it in 'this' node. For BIN's, the child node has to be a DIN so we use the Dup Key to cross the main-tree/dupe-tree boundary.
   */
  // line 69 "../../../../BIN.ump"
   public byte[] getChildKey(IN child) throws DatabaseException{
    return child.getDupKey();
  }


  /**
   * 
   * @return the log entry type to use for bin delta log entries.
   */
  // line 76 "../../../../BIN.ump"
  public LogEntryType getBINDeltaType(){
    return LogEntryType.LOG_BIN_DELTA;
  }


  /**
   * 
   * @return location of last logged delta version. If never set, return null.
   */
  // line 83 "../../../../BIN.ump"
   public long getLastDeltaVersion(){
    return lastDeltaVersion;
  }


  /**
   * 
   * If cleaned or compressed, must log full version.
   * @Override
   */
  // line 91 "../../../../BIN.ump"
   public void setProhibitNextDelta(){
    prohibitNextDelta = true;
  }

  // line 96 "../../../../BIN.ump"
   protected void descendOnParentSearch(SearchResult result, boolean targetContainsDuplicates, boolean targetIsRoot, long targetNodeId, Node child, boolean requireExactMatch) throws DatabaseException{
    if (child.canBeAncestor(targetContainsDuplicates)) {
	    if (targetContainsDuplicates && targetIsRoot) {
		long childNid = child.getNodeId();
		this.hook603(child);
		result.keepSearching = false;
		if (childNid == targetNodeId) {
		    result.exactParentFound = true;
		} else {
		    result.exactParentFound = false;
		}
		if (requireExactMatch && !result.exactParentFound) {
		    result.parent = null;
		    this.hook604();
		} else {
		    result.parent = this;
		}
	    } else {
		this.hook605();
		result.parent = (IN) child;
	    }
	} else {
	    result.exactParentFound = false;
	    result.keepSearching = false;
	    if (!requireExactMatch && targetContainsDuplicates) {
		result.parent = this;
	    } else {
		this.hook606();
		result.parent = null;
	    }
	}
  }

  // line 129 "../../../../BIN.ump"
   protected boolean canBeAncestor(boolean targetContainsDuplicates){
    return targetContainsDuplicates;
  }


  /**
   * 
   * @Override
   */
  // line 136 "../../../../BIN.ump"
  public boolean isEvictionProhibited(){
    return (nCursors() > 0);
  }


  /**
   * 
   * @Override
   */
  // line 143 "../../../../BIN.ump"
  public boolean hasNonLNChildren(){
    for (int i = 0; i < getNEntries(); i++) {
	    Node node = getTarget(i);
	    if (node != null) {
		if (!(node instanceof LN)) {
		    return true;
		}
	    }
	}
	return false;
  }


  /**
   * 
   * Indicates whether entry 0's key is "special" in that it always compares less than any other key. BIN's don't have the special key, but IN's do.
   */
  // line 158 "../../../../BIN.ump"
  public boolean entryZeroKeyComparesLow(){
    return false;
  }


  /**
   * 
   * Mark this entry as deleted, using the delete flag. Only BINS may do this.
   * @param indexindicates target entry
   */
  // line 166 "../../../../BIN.ump"
   public void setKnownDeleted(int index){
    super.setKnownDeleted(index);
	//this.hook610(index);
Label610:
updateMemorySize(getTarget(index), null);
	//original(index);

	setMigrate(index, false);
	super.setTarget(index, null);
	setDirty(true);
  }


  /**
   * 
   * Mark this entry as deleted, using the delete flag. Only BINS may do this. Don't null the target field. This is used so that an LN can still be locked by the compressor even if the entry is knownDeleted. See BIN.compress.
   * @param indexindicates target entry
   */
  // line 179 "../../../../BIN.ump"
   public void setKnownDeletedLeaveTarget(int index){
    setMigrate(index, false);
	super.setKnownDeleted(index);
	setDirty(true);
  }


  /**
   * 
   * Clear the known deleted flag. Only BINS may do this.
   * @param indexindicates target entry
   */
  // line 189 "../../../../BIN.ump"
   public void clearKnownDeleted(int index){
    super.clearKnownDeleted(index);
	setDirty(true);
  }

  // line 194 "../../../../BIN.ump"
   public Set getCursorSet(){
    return cursorSet.copy();
  }


  /**
   * 
   * Register a cursor with this bin. Caller has this bin already latched.
   * @param cursorCursor to register.
   */
  // line 202 "../../../../BIN.ump"
   public void addCursor(CursorImpl cursor){
    cursorSet.add(cursor);
  }


  /**
   * 
   * Unregister a cursor with this bin. Caller has this bin already latched.
   * @param cursorCursor to unregister.
   */
  // line 210 "../../../../BIN.ump"
   public void removeCursor(CursorImpl cursor){
    cursorSet.remove(cursor);
  }


  /**
   * 
   * @return the number of cursors currently referring to this BIN.
   */
  // line 217 "../../../../BIN.ump"
   public int nCursors(){
    return cursorSet.size();
  }


  /**
   * 
   * The following four methods access the correct fields in a cursor depending on whether "this" is a BIN or DBIN. For BIN's, the CursorImpl.index and CursorImpl.bin fields should be used. For DBIN's, the CursorImpl.dupIndex and CursorImpl.dupBin fields should be used.
   */
  // line 224 "../../../../BIN.ump"
  public BIN getCursorBIN(CursorImpl cursor){
    return cursor.getBIN();
  }

  // line 228 "../../../../BIN.ump"
  public BIN getCursorBINToBeRemoved(CursorImpl cursor){
    return cursor.getBINToBeRemoved();
  }

  // line 232 "../../../../BIN.ump"
  public int getCursorIndex(CursorImpl cursor){
    return cursor.getIndex();
  }

  // line 236 "../../../../BIN.ump"
  public void setCursorBIN(CursorImpl cursor, BIN bin){
    cursor.setBIN(bin);
  }

  // line 240 "../../../../BIN.ump"
  public void setCursorIndex(CursorImpl cursor, int index){
    cursor.setIndex(index);
  }


  /**
   * 
   * Called when we know we are about to split on behalf of a key that is the minimum (leftSide) or maximum (!leftSide) of this node. This is achieved by just forcing the split to occur either one element in from the left or the right (i.e. splitIndex is 1 or nEntries - 1).
   */
  // line 248 "../../../../BIN.ump"
  public void splitSpecial(IN parent, int parentIndex, int maxEntriesPerNode, byte [] key, boolean leftSide) throws DatabaseException{
    int index = findEntry(key, true, false);
	int nEntries = getNEntries();
	boolean exact = (index & IN.EXACT_MATCH) != 0;
	index &= ~IN.EXACT_MATCH;
	if (leftSide && index < 0) {
	    splitInternal(parent, parentIndex, maxEntriesPerNode, 1);
	} else if (!leftSide && !exact && index == (nEntries - 1)) {
	    splitInternal(parent, parentIndex, maxEntriesPerNode, nEntries - 1);
	} else {
	    split(parent, parentIndex, maxEntriesPerNode);
	}
  }


  /**
   * 
   * Adjust any cursors that are referring to this BIN. This method is called during a split operation. "this" is the BIN being split. newSibling is the new BIN into which the entries from "this" between newSiblingLow and newSiblingHigh have been copied.
   * @param newSibling -the newSibling into which "this" has been split.
   * @param newSiblingLow,newSiblingHigh - the low and high entry of "this" that were moved into newSibling.
   */
  // line 267 "../../../../BIN.ump"
  public void adjustCursors(IN newSibling, int newSiblingLow, int newSiblingHigh){
    int adjustmentDelta = (newSiblingHigh - newSiblingLow);
	Iterator iter = cursorSet.iterator();
	while (iter.hasNext()) {
	    CursorImpl cursor = (CursorImpl) iter.next();
	    if (getCursorBINToBeRemoved(cursor) == this) {
		continue;
	    }
	    int cIdx = getCursorIndex(cursor);
	    BIN cBin = getCursorBIN(cursor);
	    assert cBin == this : "nodeId=" + getNodeId() + " cursor=" + cursor.dumpToString(true);
	    assert newSibling instanceof BIN;
	    BIN ns = (BIN) newSibling;
	    if (newSiblingLow == 0) {
		if (cIdx < newSiblingHigh) {
		    setCursorBIN(cursor, ns);
		    iter.remove();
		    ns.addCursor(cursor);
		} else {
		    setCursorIndex(cursor, cIdx - adjustmentDelta);
		}
	    } else {
		if (cIdx >= newSiblingLow) {
		    setCursorIndex(cursor, cIdx - newSiblingLow);
		    setCursorBIN(cursor, ns);
		    iter.remove();
		    ns.addCursor(cursor);
		}
	    }
	}
  }


  /**
   * 
   * Adjust cursors referring to this BIN following an insert.
   * @param insertIndex -The index of the new entry.
   */
  // line 303 "../../../../BIN.ump"
  public void adjustCursorsForInsert(int insertIndex){
    if (cursorSet != null) {
	    Iterator iter = cursorSet.iterator();
	    while (iter.hasNext()) {
		CursorImpl cursor = (CursorImpl) iter.next();
		if (getCursorBINToBeRemoved(cursor) != this) {
		    int cIdx = getCursorIndex(cursor);
		    if (insertIndex <= cIdx) {
			setCursorIndex(cursor, cIdx + 1);
		    }
		}
	    }
	}
  }


  /**
   * 
   * Adjust cursors referring to the given binIndex in this BIN following a mutation of the entry from an LN to a DIN. The entry was moved from a BIN to a newly created DBIN so each cursor must be added to the new DBIN.
   * @param binIndex -The index of the DIN (previously LN) entry in the BIN.
   * @param dupBin -The DBIN into which the LN entry was moved.
   * @param dupBinIndex -The index of the moved LN entry in the DBIN.
   * @param excludeCursor -The cursor being used for insertion and that should not be updated.
   */
  // line 325 "../../../../BIN.ump"
  public void adjustCursorsForMutation(int binIndex, DBIN dupBin, int dupBinIndex, CursorImpl excludeCursor){
    if (cursorSet != null) {
	    Iterator iter = cursorSet.iterator();
	    while (iter.hasNext()) {
		CursorImpl cursor = (CursorImpl) iter.next();
		if (getCursorBINToBeRemoved(cursor) != this && cursor != excludeCursor
			&& cursor.getIndex() == binIndex) {
		    assert cursor.getDupBIN() == null;
		    cursor.addCursor(dupBin);
		    cursor.updateDBin(dupBin, dupBinIndex);
		}
	    }
	}
  }


  /**
   * 
   * Compress this BIN by removing any entries that are deleted. Deleted entries are those that have LN's marked deleted or if the knownDeleted flag is set. Caller is responsible for latching and unlatching this node.
   * @param binRefis used to determine the set of keys to be checked fordeletedness, or is null to check all keys.
   * @param canFetchif false, don't fetch any non-resident children. We don't wantsome callers of compress, such as the evictor, to fault in other nodes.
   * @return true if we had to requeue the entry because we were unable to getlocks, false if all entries were processed and therefore any remaining deleted keys in the BINReference must now be in some other BIN because of a split.
   */
  // line 346 "../../../../BIN.ump"
   public boolean compress(BINReference binRef, boolean canFetch) throws DatabaseException{
    boolean ret = false;
			boolean setNewIdKey = false;
			boolean anyLocksDenied = false;
			DatabaseImpl db = getDatabase();
			BasicLocker lockingTxn = new BasicLocker(db.getDbEnvironment());
			try {
					for (int i = 0; i < getNEntries(); i++) {
				boolean deleteEntry = false;
				if (binRef == null || isEntryPendingDeleted(i) || isEntryKnownDeleted(i)
					|| binRef.hasDeletedKey(new Key(getKey(i)))) {
						Node n = null;
						if (canFetch) {
					n = fetchTarget(i);
						} else {
					n = getTarget(i);
					if (n == null) {
							continue;
					}
						}
						if (n == null) {
					deleteEntry = true;
						} else if (isEntryKnownDeleted(i)) {
					LockResult lockRet = lockingTxn.nonBlockingLock(n.getNodeId(), LockType.READ, db);
					if (lockRet.getLockGrant() == LockGrantType.DENIED) {
							anyLocksDenied = true;
							continue;
					}
					deleteEntry = true;
						} else {
					if (!n.containsDuplicates()) {
							LN ln = (LN) n;
							LockResult lockRet = lockingTxn.nonBlockingLock(ln.getNodeId(), LockType.READ, db);
							if (lockRet.getLockGrant() == LockGrantType.DENIED) {
						anyLocksDenied = true;
						continue;
							}
							if (ln.isDeleted()) {
						deleteEntry = true;
							}
					}
						}
						if (binRef != null) {
					binRef.removeDeletedKey(new Key(getKey(i)));
						}
				}
				if (deleteEntry) {
						boolean entryIsIdentifierKey = Key.compareKeys(getKey(i), getIdentifierKey(),
							getKeyComparator()) == 0;
						if (entryIsIdentifierKey) {
					setNewIdKey = true;
						}
						boolean deleteSuccess = deleteEntry(i, true);
						assert deleteSuccess;
						i--;
				}
					}
			} finally {
					if (lockingTxn != null) {
				lockingTxn.operationEnd();
					}
			}
			if (anyLocksDenied && binRef != null) {
					Label609:
db.getDbEnvironment().addToCompressorQueue(binRef, false);
			//original(binRef, db);
 //this.hook609(binRef, db);
					ret = true;
			}
			if (getNEntries() != 0 && setNewIdKey) {
					setIdentifierKey(getKey(0));
			}
			if (getNEntries() == 0) {
					setGeneration(0);
			}
			return ret;
  }

  // line 421 "../../../../BIN.ump"
   public boolean isCompressible(){
    return true;
  }

  // line 425 "../../../../BIN.ump"
  public boolean validateSubtreeBeforeDelete(int index) throws DatabaseException{
    return true;
  }


  /**
   * 
   * Check if this node fits the qualifications for being part of a deletable subtree. It can only have one IN child and no LN children.
   */
  // line 432 "../../../../BIN.ump"
  public boolean isValidForDelete() throws DatabaseException{
    try {
	    int validIndex = 0;
	    int numValidEntries = 0;
	    boolean needToLatch = false;
	    this.hook607(validIndex, numValidEntries, needToLatch);
	    throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }

  // line 444 "../../../../BIN.ump"
  public void accumulateStats(TreeWalkerStatsAccumulator acc){
    acc.processBIN(this, new Long(getNodeId()), getLevel());
  }


  /**
   * 
   * Return the relevant user defined comparison function for this type of node. For IN's and BIN's, this is the BTree Comparison function. Overriden by DBIN.
   */
  // line 451 "../../../../BIN.ump"
   public Comparator getKeyComparator(){
    return getDatabase().getBtreeComparator();
  }

  // line 455 "../../../../BIN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 459 "../../../../BIN.ump"
   public String endTag(){
    return END_TAG;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 466 "../../../../BIN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_BIN;
  }

  // line 470 "../../../../BIN.ump"
   public String shortClassName(){
    return "BIN";
  }


  /**
   * 
   * Decide how to log this node. BINs may be logged provisionally. If logging a delta, return an null for the LSN.
   */
  // line 478 "../../../../BIN.ump"
   protected long logInternal(LogManager logManager, boolean allowDeltas, boolean isProvisional, boolean proactiveMigration, IN parent) throws DatabaseException{
    boolean doDeltaLog = false;
	long lastFullVersion = getLastFullVersion();
	Cleaner cleaner = getDatabase().getDbEnvironment().getCleaner();
	cleaner.lazyMigrateLNs(this, proactiveMigration);
	BINDelta deltaInfo = null;
	if ((allowDeltas) && (lastFullVersion != DbLsn.NULL_LSN) && !prohibitNextDelta) {
	    deltaInfo = new BINDelta(this);
	    doDeltaLog = doDeltaLog(deltaInfo);
	}
	long returnLsn = DbLsn.NULL_LSN;
	if (doDeltaLog) {
	    lastDeltaVersion = logManager.log(deltaInfo);
	    returnLsn = DbLsn.NULL_LSN;
	    numDeltasSinceLastFull++;
	} else {
	    returnLsn = super.logInternal(logManager, allowDeltas, isProvisional, proactiveMigration, parent);
	    lastDeltaVersion = DbLsn.NULL_LSN;
	    numDeltasSinceLastFull = 0;
	}
	prohibitNextDelta = false;
	return returnLsn;
  }


  /**
   * 
   * Decide whether to log a full or partial BIN, depending on the ratio of the delta size to full BIN size, and the number of deltas that have been logged since the last full.
   * @return true if we should log the deltas of this BIN
   */
  // line 506 "../../../../BIN.ump"
   private boolean doDeltaLog(BINDelta deltaInfo) throws DatabaseException{
    int maxDiffs = (getNEntries() * getDatabase().getBinDeltaPercent()) / 100;
	if ((deltaInfo.getNumDeltas() <= maxDiffs) && (numDeltasSinceLastFull < getDatabase().getBinMaxDeltas())) {
	    return true;
	} else {
	    return false;
	}
  }

  // line 515 "../../../../BIN.ump"
   protected void hook603(Node child) throws DatabaseException{
    
  }

  // line 518 "../../../../BIN.ump"
   protected void hook604() throws DatabaseException{
    
  }

  // line 521 "../../../../BIN.ump"
   protected void hook605() throws DatabaseException{
    
  }

  // line 524 "../../../../BIN.ump"
   protected void hook606() throws DatabaseException{
    
  }

  // line 527 "../../../../BIN.ump"
   protected void hook607(int validIndex, int numValidEntries, boolean needToLatch) throws DatabaseException{
    this.hook608(needToLatch);
	for (int i = 0; i < getNEntries(); i++) {
	    if (!isEntryKnownDeleted(i)) {
		numValidEntries++;
		validIndex = i;
	    }
	}
	if (numValidEntries > 1) {
	    throw new ReturnBoolean(false);
	} else {
	    if (nCursors() > 0) {
		throw new ReturnBoolean(false);
	    }
	    if (numValidEntries == 1) {
		Node child = fetchTarget(validIndex);
		throw new ReturnBoolean(child != null && child.isValidForDelete());
	    } else {
		throw new ReturnBoolean(true);
	    }
	}
  }

  // line 550 "../../../../BIN.ump"
   protected void hook608(boolean needToLatch) throws DatabaseException{
    
  }


  /**
   * protected void hook609(BINReference binRef, DatabaseImpl db) throws DatabaseException {
   * }
   */
  // line 556 "../../../../BIN.ump"
   protected void hook610(int index){
    
  }

  // line 6 "../../../../MemoryBudget_BIN.ump"
   public static  long computeOverhead(DbConfigManager configManager) throws DatabaseException{
    return MemoryBudget.BIN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }

  // line 10 "../../../../MemoryBudget_BIN.ump"
   protected long getMemoryOverhead(MemoryBudget mb){
    return mb.getBINOverhead();
  }


  /**
   * 
   * @Override
   */
  // line 9 "../../../../Evictor_BIN.ump"
  public int getChildEvictionType(){
    Cleaner cleaner = getDatabase().getDbEnvironment().getCleaner();
			for (int i = 0; i < getNEntries(); i++) {
					Node node = getTarget(i);
					if (node != null) {
				if (node instanceof LN) {
						if (cleaner.isEvictable(this, i)) {
					return MAY_EVICT_LNS;
						}
				} else {
						return MAY_NOT_EVICT;
				}
					}
			}
			return MAY_EVICT_NODE;
  }


  /**
   * 
   * Reduce memory consumption by evicting all LN targets. Note that the targets are not persistent, so this doesn't affect node dirtiness. The BIN should be latched by the caller.
   * @return number of evicted bytes
   */
  // line 30 "../../../../Evictor_BIN.ump"
   public long evictLNs() throws DatabaseException{
    Cleaner cleaner = getDatabase().getDbEnvironment().getCleaner();
			long removed = 0;
			if (nCursors() == 0) {
					for (int i = 0; i < getNEntries(); i++) {
				removed += evictInternal(i, cleaner);
					}
					//this.hook601(removed);
					Label601:  ;

			}
			return removed;
  }


  /**
   * 
   * Evict a single LN if allowed and adjust the memory budget.
   */
  // line 47 "../../../../Evictor_BIN.ump"
   public void evictLN(int index) throws DatabaseException{
    Cleaner cleaner = getDatabase().getDbEnvironment().getCleaner();
			long removed = evictInternal(index, cleaner);
			//this.hook602(removed);
      Label602: ;
  }


  /**
   * 
   * Evict a single LN if allowed. The amount of memory freed is returned and must be subtracted from the memory budget by the caller.
   */
  // line 57 "../../../../Evictor_BIN.ump"
   private long evictInternal(int index, Cleaner cleaner) throws DatabaseException{
    Node n = getTarget(index);
			if (n instanceof LN && cleaner.isEvictable(this, index)) {
					setTarget(index, null);
					return n.getMemorySizeIncludedByParent();
			} else {
					return 0;
			}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 26 "../../../../BIN.ump"
  private static final String BEGIN_TAG = "<bin>" ;
// line 28 "../../../../BIN.ump"
  private static final String END_TAG = "</bin>" ;
// line 30 "../../../../BIN.ump"
  private TinyHashSet cursorSet ;
// line 32 "../../../../BIN.ump"
  private long lastDeltaVersion = DbLsn.NULL_LSN ;
// line 34 "../../../../BIN.ump"
  private int numDeltasSinceLastFull ;
// line 36 "../../../../BIN.ump"
  private boolean prohibitNextDelta ;

  
}