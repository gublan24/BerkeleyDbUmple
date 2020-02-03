/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.entry.INLogEntry;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.log.LogFileNotFoundException;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.INList;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.LatchNotHeldException;
import com.sleepycat.je.latch.Latch;
import com.sleepycat.bind.serial.*;
import com.sleepycat.je.log.*;

// line 3 "../../../../IN.ump"
// line 3 "../../../../IN_static.ump"
// line 3 "../../../../MemoryBudget_IN.ump"
// line 3 "../../../../MemoryBudget_IN_inner.ump"
// line 3 "../../../../Evictor_IN.ump"
// line 3 "../../../../INCompressor_IN.ump"
// line 3 "../../../../INCompressor_IN_inner.ump"
// line 3 "../../../../Latches_IN.ump"
// line 3 "../../../../Latches_IN_inner.ump"
// line 3 "../../../../LoggingFine_IN.ump"
// line 3 "../../../../LoggingFine_IN_inner.ump"
// line 3 "../../../../LoggingFinest_IN.ump"
// line 3 "../../../../LoggingFinest_IN_inner.ump"
// line 3 "../../../../Derivative_LoggingFine_LoggingBase_IN.ump"
// line 3 "../../../../Derivative_LoggingFine_LoggingBase_IN_inner.ump"
// line 3 "../../../../Derivative_LoggingFinest_LoggingBase_IN.ump"
// line 3 "../../../../Derivative_LoggingFinest_LoggingBase_IN_inner.ump"
// line 3 "../../../../Derivative_LoggingInfo_MemoryBudget_IN.ump"
public class IN extends Node implements Comparable,LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public IN()
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


  /**
   * 
   * Create an empty IN, with no node id, to be filled in from the log.
   */
  // line 120 "../../../../IN.ump"
   public  IN(){
    super(false);
	init(null, Key.EMPTY_KEY, 0, 0);
  }


  /**
   * 
   * Create a new IN.
   */
  // line 128 "../../../../IN.ump"
   public  IN(DatabaseImpl db, byte [] identifierKey, int capacity, int level){
    super(true);
	init(db, identifierKey, capacity, generateLevel(db.getId(), level));
  }


  /**
   * 
   * Initialize IN object.
   */
  // line 136 "../../../../IN.ump"
   protected void init(DatabaseImpl db, byte [] identifierKey, int initialCapacity, int level){
    setDatabase(db);
	EnvironmentImpl env = (databaseImpl == null) ? null : databaseImpl.getDbEnvironment();
	Label618: //this.hook618(env);
	generation = 0;
	dirty = false;
	nEntries = 0;
	this.identifierKey = identifierKey;
	entryTargets = new Node[initialCapacity];
	entryKeyVals = new byte[initialCapacity][];
	baseFileNumber = -1;
	entryLsnByteArray = new byte[initialCapacity << 2];
	entryLsnLongArray = null;
	entryStates = new byte[initialCapacity];
	isRoot = false;
	this.level = level;
    // line 151 "../../../../MemoryBudget_IN.ump"
    // original(db, identifierKey, initialCapacity, level);
          inListResident = false;
    // END OF UMPLE AFTER INJECTION
  }

  // line 154 "../../../../IN.ump"
   private long getEqualityKey(){
    int hash = System.identityHashCode(this);
	long hash2 = (((long) hash) << 32) | hash;
	return hash2 ^ getNodeId();
  }

  // line 160 "../../../../IN.ump"
   public boolean equals(Object obj){
    if (!(obj instanceof IN)) {
	    return false;
	}
	IN in = (IN) obj;
	return (this.getEqualityKey() == in.getEqualityKey());
  }

  // line 168 "../../../../IN.ump"
   public int hashCode(){
    return (int) getEqualityKey();
  }


  /**
   * 
   * Sort based on node id.
   */
  // line 175 "../../../../IN.ump"
   public int compareTo(Object o){
    if (o == null) {
	    throw new NullPointerException();
	}
	IN argIN = (IN) o;
	long argEqualityKey = argIN.getEqualityKey();
	long myEqualityKey = getEqualityKey();
	if (myEqualityKey < argEqualityKey) {
	    return -1;
	} else if (myEqualityKey > argEqualityKey) {
	    return 1;
	} else {
	    return 0;
	}
  }


  /**
   * 
   * Create a new IN.  Need this because we can't call newInstance() without getting a 0 for nodeid.
   */
  // line 194 "../../../../IN.ump"
   protected IN createNewInstance(byte [] identifierKey, int maxEntries, int level){
    return new IN(databaseImpl, identifierKey, maxEntries, level);
  }


  /**
   * 
   * Initialize a node that has been read in from the log.
   */
  // line 201 "../../../../IN.ump"
   public void postFetchInit(DatabaseImpl db, long sourceLsn) throws DatabaseException{
    setDatabase(db);
	setLastFullLsn(sourceLsn);
	EnvironmentImpl env = db.getDbEnvironment();
	//this.hook637();
  Label637:
initMemorySize();
//	original();

	env.getInMemoryINs().add(this);
  }


  /**
   * 
   * Initialize a node read in during recovery.
   */
  // line 213 "../../../../IN.ump"
   public void postRecoveryInit(DatabaseImpl db, long sourceLsn){
    setDatabase(db);
	setLastFullLsn(sourceLsn);
    // line 164 "../../../../MemoryBudget_IN.ump"
    //original(db, sourceLsn);
    	  initMemorySize();
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Sets the last logged LSN.
   */
  // line 221 "../../../../IN.ump"
  public void setLastFullLsn(long lsn){
    lastFullVersion = lsn;
  }


  /**
   * 
   * Returns the last logged LSN, or null if never logged.  Is public for unit testing.
   */
  // line 228 "../../../../IN.ump"
   public long getLastFullVersion(){
    return lastFullVersion;
  }


  /**
   * 
   * Latch this node, optionally setting the generation.
   */
  // line 235 "../../../../IN.ump"
   public void latch(boolean updateGeneration) throws DatabaseException{
    if (updateGeneration) {
	    setGeneration();
	}
    // line 55 "../../../../Latches_IN.ump"
    //original(updateGeneration);
    	latch.acquire();
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Latch this node if it is not latched by another thread, optionally setting the generation if the latch succeeds.
   */
  // line 244 "../../../../IN.ump"
   public boolean latchNoWait(boolean updateGeneration) throws DatabaseException{
    try {
	    Label619: //this.hook619(updateGeneration);
			if (updateGeneration) {
						setGeneration();
				}
				throw new ReturnBoolean(true);
	    //throw ReturnHack.returnBoolean;
	} catch (ReturnBoolean r) {
	    return r.value;
	}
  }

  // line 257 "../../../../IN.ump"
   public long getGeneration(){
    return generation;
  }

  // line 261 "../../../../IN.ump"
   public void setGeneration(){
    generation = Generation.getNextGeneration();
  }

  // line 265 "../../../../IN.ump"
   public void setGeneration(long newGeneration){
    generation = newGeneration;
  }

  // line 269 "../../../../IN.ump"
   public int getLevel(){
    return level;
  }

  // line 273 "../../../../IN.ump"
   protected int generateLevel(DatabaseId dbId, int newLevel){
    if (dbId.equals(DbTree.ID_DB_ID)) {
	    return newLevel | DBMAP_LEVEL;
	} else {
	    return newLevel | MAIN_LEVEL;
	}
  }

  // line 281 "../../../../IN.ump"
   public boolean getDirty(){
    return dirty;
  }

  // line 285 "../../../../IN.ump"
   public void setDirty(boolean dirty){
    this.dirty = dirty;
  }

  // line 289 "../../../../IN.ump"
   public boolean isRoot(){
    return isRoot;
  }

  // line 293 "../../../../IN.ump"
   public boolean isDbRoot(){
    return isRoot;
  }

  // line 297 "../../../../IN.ump"
  public void setIsRoot(boolean isRoot){
    this.isRoot = isRoot;
	setDirty(true);
  }


  /**
   * 
   * @return the identifier key for this node.
   */
  // line 305 "../../../../IN.ump"
   public byte[] getIdentifierKey(){
    return identifierKey;
  }


  /**
   * 
   * Set the identifier key for this node.
   * @param key - the new identifier key for this node.
   */
  // line 313 "../../../../IN.ump"
  public void setIdentifierKey(byte [] key){
    identifierKey = key;
	setDirty(true);
  }


  /**
   * 
   * Get the key (dupe or identifier) in child that is used to locate it in 'this' node.
   */
  // line 321 "../../../../IN.ump"
   public byte[] getChildKey(IN child) throws DatabaseException{
    return child.getIdentifierKey();
  }

  // line 325 "../../../../IN.ump"
   public byte[] selectKey(byte [] mainTreeKey, byte [] dupTreeKey){
    return mainTreeKey;
  }


  /**
   * 
   * Return the key for this duplicate set.
   */
  // line 332 "../../../../IN.ump"
   public byte[] getDupKey() throws DatabaseException{
    throw new DatabaseException(shortClassName() + ".getDupKey() called");
  }


  /**
   * 
   * Return the key for navigating through the duplicate tree.
   */
  // line 339 "../../../../IN.ump"
   public byte[] getDupTreeKey(){
    return null;
  }


  /**
   * 
   * Return the key for navigating through the main tree.
   */
  // line 346 "../../../../IN.ump"
   public byte[] getMainTreeKey(){
    return getIdentifierKey();
  }


  /**
   * 
   * Get the database for this IN.
   */
  // line 353 "../../../../IN.ump"
   public DatabaseImpl getDatabase(){
    return databaseImpl;
  }


  /**
   * 
   * Set the database reference for this node.
   */
  // line 360 "../../../../IN.ump"
   public void setDatabase(DatabaseImpl db){
    databaseImpl = db;
  }

  // line 364 "../../../../IN.ump"
   public DatabaseId getDatabaseId(){
    return databaseImpl.getId();
  }

  // line 368 "../../../../IN.ump"
   private void setEntryInternal(int from, int to){
    entryTargets[to] = entryTargets[from];
	entryKeyVals[to] = entryKeyVals[from];
	entryStates[to] = entryStates[from];
	if (entryLsnLongArray == null) {
	    int fromOff = from << 2;
	    int toOff = to << 2;
	    entryLsnByteArray[toOff++] = entryLsnByteArray[fromOff++];
	    entryLsnByteArray[toOff++] = entryLsnByteArray[fromOff++];
	    entryLsnByteArray[toOff++] = entryLsnByteArray[fromOff++];
	    entryLsnByteArray[toOff] = entryLsnByteArray[fromOff];
	} else {
	    entryLsnLongArray[to] = entryLsnLongArray[from];
	}
  }

  // line 384 "../../../../IN.ump"
   private void clearEntry(int idx){
    entryTargets[idx] = null;
	entryKeyVals[idx] = null;
	setLsnElement(idx, DbLsn.NULL_LSN);
	entryStates[idx] = 0;
  }


  /**
   * 
   * Return the idx'th key.
   */
  // line 394 "../../../../IN.ump"
   public byte[] getKey(int idx){
    return entryKeyVals[idx];
  }


  /**
   * 
   * Set the idx'th key.
   */
  // line 401 "../../../../IN.ump"
   private void setKey(int idx, byte [] keyVal){
    entryKeyVals[idx] = keyVal;
	entryStates[idx] |= DIRTY_BIT;
  }


  /**
   * 
   * Get the idx'th migrate status.
   */
  // line 409 "../../../../IN.ump"
   public boolean getMigrate(int idx){
    return (entryStates[idx] & MIGRATE_BIT) != 0;
  }


  /**
   * 
   * Set the idx'th migrate status.
   */
  // line 416 "../../../../IN.ump"
   public void setMigrate(int idx, boolean migrate){
    if (migrate) {
	    entryStates[idx] |= MIGRATE_BIT;
	} else {
	    entryStates[idx] &= CLEAR_MIGRATE_BIT;
	}
  }

  // line 424 "../../../../IN.ump"
   public byte getState(int idx){
    return entryStates[idx];
  }


  /**
   * 
   * Return the idx'th target.
   */
  // line 431 "../../../../IN.ump"
   public Node getTarget(int idx){
    return entryTargets[idx];
  }


  /**
   * 
   * Sets the idx'th target. No need to make dirty, that state only applies to key and LSN. <p>WARNING: This method does not update the memory budget.  The caller must update the budget.</p>
   */
  // line 438 "../../../../IN.ump"
  public void setTarget(int idx, Node target){
    entryTargets[idx] = target;
  }


  /**
   * 
   * Return the idx'th LSN for this entry.
   * @return the idx'th LSN for this entry.
   */
  // line 446 "../../../../IN.ump"
   public long getLsn(int idx){
    if (entryLsnLongArray == null) {
	    int offset = idx << 2;
	    int fileOffset = getFileOffset(offset);
	    if (fileOffset == -1) {
		return DbLsn.NULL_LSN;
	    } else {
		return DbLsn.makeLsn((long) (baseFileNumber + getFileNumberOffset(offset)), fileOffset);
	    }
	} else {
	    return entryLsnLongArray[idx];
	}
  }


  /**
   * 
   * Sets the idx'th target LSN.
   */
  // line 463 "../../../../IN.ump"
   public void setLsn(int idx, long lsn){
    new IN_setLsn(this, idx, lsn).execute();
  }

  // line 467 "../../../../IN.ump"
  public long[] getEntryLsnLongArray(){
    return entryLsnLongArray;
  }

  // line 471 "../../../../IN.ump"
  public byte[] getEntryLsnByteArray(){
    return entryLsnByteArray;
  }

  // line 475 "../../../../IN.ump"
  public void initEntryLsn(int capacity){
    entryLsnLongArray = null;
	entryLsnByteArray = new byte[capacity << 2];
	baseFileNumber = -1;
  }

  // line 481 "../../../../IN.ump"
  public void setLsnElement(int idx, long value){
    int offset = idx << 2;
	if (entryLsnLongArray != null) {
	    entryLsnLongArray[idx] = value;
	    return;
	}
	if (value == DbLsn.NULL_LSN) {
	    setFileNumberOffset(offset, (byte) 0);
	    setFileOffset(offset, -1);
	    return;
	}
	long thisFileNumber = DbLsn.getFileNumber(value);
	if (baseFileNumber == -1) {
	    baseFileNumber = thisFileNumber;
	    setFileNumberOffset(offset, (byte) 0);
	} else {
	    if (thisFileNumber < baseFileNumber) {
		if (!adjustFileNumbers(thisFileNumber)) {
		    mutateToLongArray(idx, value);
		    return;
		}
		baseFileNumber = thisFileNumber;
	    }
	    long fileNumberDifference = thisFileNumber - baseFileNumber;
	    if (fileNumberDifference > Byte.MAX_VALUE) {
		mutateToLongArray(idx, value);
		return;
	    }
	    setFileNumberOffset(offset, (byte) (thisFileNumber - baseFileNumber));
	}
	int fileOffset = (int) DbLsn.getFileOffset(value);
	if (fileOffset > MAX_FILE_OFFSET) {
	    mutateToLongArray(idx, value);
	    return;
	}
	setFileOffset(offset, fileOffset);
  }

  // line 519 "../../../../IN.ump"
   private void mutateToLongArray(int idx, long value){
    int nElts = entryLsnByteArray.length >> 2;
	long[] newArr = new long[nElts];
	for (int i = 0; i < nElts; i++) {
	    newArr[i] = getLsn(i);
	}
	newArr[idx] = value;
	entryLsnLongArray = newArr;
	entryLsnByteArray = null;
  }


  /**
   * 
   * private void maybeAdjustCapacity(int offset) { if (entryLsnLongArray == null) { int bytesNeeded = offset + BYTES_PER_LSN_ENTRY; int currentBytes = entryLsnByteArray.length; if (currentBytes < bytesNeeded) { int newBytes = bytesNeeded + (GROWTH_INCREMENT * BYTES_PER_LSN_ENTRY); byte[] newArr = new byte[newBytes]; System.arraycopy(entryLsnByteArray, 0, newArr, 0, currentBytes); entryLsnByteArray = newArr; for (int i = currentBytes; i < newBytes; i += BYTES_PER_LSN_ENTRY) { setFileNumberOffset(i, (byte) 0); setFileOffset(i, -1); } } } else { int currentEntries = entryLsnLongArray.length; int idx = offset >> 2; if (currentEntries < idx + 1) { int newEntries = idx + GROWTH_INCREMENT; long[] newArr = new long[newEntries]; System.arraycopy(entryLsnLongArray, 0, newArr, 0, currentEntries); entryLsnLongArray = newArr; for (int i = currentEntries; i < newEntries; i++) { entryLsnLongArray[i] = DbLsn.NULL_LSN; } } } }
   */
  // line 533 "../../../../IN.ump"
   private boolean adjustFileNumbers(long newBaseFileNumber){
    long oldBaseFileNumber = baseFileNumber;
	for (int i = 0; i < entryLsnByteArray.length; i += BYTES_PER_LSN_ENTRY) {
	    if (getFileOffset(i) == -1) {
		continue;
	    }
	    long curEntryFileNumber = oldBaseFileNumber + getFileNumberOffset(i);
	    long newCurEntryFileNumberOffset = (curEntryFileNumber - newBaseFileNumber);
	    if (newCurEntryFileNumberOffset > Byte.MAX_VALUE) {
		long undoOffset = oldBaseFileNumber - newBaseFileNumber;
		for (int j = i - BYTES_PER_LSN_ENTRY; j >= 0; j -= BYTES_PER_LSN_ENTRY) {
		    if (getFileOffset(j) == -1) {
			continue;
		    }
		    setFileNumberOffset(j, (byte) (getFileNumberOffset(j) - undoOffset));
		}
		return false;
	    }
	    setFileNumberOffset(i, (byte) newCurEntryFileNumberOffset);
	}
	return true;
  }

  // line 556 "../../../../IN.ump"
   private void setFileNumberOffset(int offset, byte fileNumberOffset){
    entryLsnByteArray[offset] = fileNumberOffset;
  }

  // line 560 "../../../../IN.ump"
   private byte getFileNumberOffset(int offset){
    return entryLsnByteArray[offset];
  }

  // line 564 "../../../../IN.ump"
   private void setFileOffset(int offset, int fileOffset){
    put3ByteInt(offset + 1, fileOffset);
  }

  // line 568 "../../../../IN.ump"
   private int getFileOffset(int offset){
    return get3ByteInt(offset + 1);
  }

  // line 572 "../../../../IN.ump"
   private void put3ByteInt(int offset, int value){
    entryLsnByteArray[offset++] = (byte) (value >>> 0);
	entryLsnByteArray[offset++] = (byte) (value >>> 8);
	entryLsnByteArray[offset] = (byte) (value >>> 16);
  }

  // line 578 "../../../../IN.ump"
   private int get3ByteInt(int offset){
    int ret = (entryLsnByteArray[offset++] & 0xFF) << 0;
	ret += (entryLsnByteArray[offset++] & 0xFF) << 8;
	ret += (entryLsnByteArray[offset] & 0xFF) << 16;
	if (ret == THREE_BYTE_NEGATIVE_ONE) {
	    ret = -1;
	}
	return ret;
  }


  /**
   * 
   * @return true if the idx'th entry has been deleted, although thetransaction that performed the deletion may not be committed.
   */
  // line 591 "../../../../IN.ump"
   public boolean isEntryPendingDeleted(int idx){
    return ((entryStates[idx] & PENDING_DELETED_BIT) != 0);
  }


  /**
   * 
   * Set pendingDeleted to true.
   */
  // line 598 "../../../../IN.ump"
   public void setPendingDeleted(int idx){
    entryStates[idx] |= PENDING_DELETED_BIT;
	entryStates[idx] |= DIRTY_BIT;
  }


  /**
   * 
   * Set pendingDeleted to false.
   */
  // line 606 "../../../../IN.ump"
   public void clearPendingDeleted(int idx){
    entryStates[idx] &= CLEAR_PENDING_DELETED_BIT;
	entryStates[idx] |= DIRTY_BIT;
  }


  /**
   * 
   * @return true if the idx'th entry is deleted for sure.  If a transactionperformed the deletion, it has been committed.
   */
  // line 614 "../../../../IN.ump"
   public boolean isEntryKnownDeleted(int idx){
    return ((entryStates[idx] & KNOWN_DELETED_BIT) != 0);
  }


  /**
   * 
   * Set knownDeleted to true.
   */
  // line 621 "../../../../IN.ump"
  public void setKnownDeleted(int idx){
    entryStates[idx] |= KNOWN_DELETED_BIT;
	entryStates[idx] |= DIRTY_BIT;
  }


  /**
   * 
   * Set knownDeleted to false.
   */
  // line 629 "../../../../IN.ump"
  public void clearKnownDeleted(int idx){
    entryStates[idx] &= CLEAR_KNOWN_DELETED_BIT;
	entryStates[idx] |= DIRTY_BIT;
  }


  /**
   * 
   * @return true if the object is dirty.
   */
  // line 637 "../../../../IN.ump"
  public boolean isDirty(int idx){
    return ((entryStates[idx] & DIRTY_BIT) != 0);
  }


  /**
   * 
   * @return the number of entries in this node.
   */
  // line 644 "../../../../IN.ump"
   public int getNEntries(){
    return nEntries;
  }


  /**
   * 
   * Returns true if the given state is known deleted.
   */
  // line 651 "../../../../IN.ump"
   static  boolean isStateKnownDeleted(byte state){
    return ((state & KNOWN_DELETED_BIT) != 0);
  }


  /**
   * 
   * Returns true if the given state is known deleted.
   */
  // line 658 "../../../../IN.ump"
   static  boolean isStatePendingDeleted(byte state){
    return ((state & KNOWN_DELETED_BIT) != 0);
  }


  /**
   * 
   * @return the maximum number of entries in this node.
   */
  // line 665 "../../../../IN.ump"
  public int getMaxEntries(){
    return entryTargets.length;
  }


  /**
   * 
   * Returns the target of the idx'th entry or null if a pendingDeleted or knownDeleted entry has been cleaned.  Note that null can only be returned for a slot that could contain a deleted LN, not other node types and not a DupCountLN since DupCountLNs are never deleted.  Null is also returned for a KnownDeleted slot with a NULL_LSN.
   * @return the target node or null.
   */
  // line 673 "../../../../IN.ump"
   public Node fetchTarget(int idx) throws DatabaseException{
    if (entryTargets[idx] == null) {
	    long lsn = getLsn(idx);
	    if (lsn == DbLsn.NULL_LSN) {
		if (!isEntryKnownDeleted(idx)) {
		    throw new DatabaseException(
			    makeFetchErrorMsg("NULL_LSN without KnownDeleted", this, lsn, entryStates[idx]));
		}
	    } else {
		try {
		    EnvironmentImpl env = databaseImpl.getDbEnvironment();
		    Node node = (Node) env.getLogManager().get(lsn);
		    node.postFetchInit(databaseImpl, lsn);
		    entryTargets[idx] = node;
		   // this.hook638(node);
        Label638:
updateMemorySize(null, node);
//	original(node);


		} catch (LogFileNotFoundException LNFE) {
		    if (!isEntryKnownDeleted(idx) && !isEntryPendingDeleted(idx)) {
			throw new DatabaseException(makeFetchErrorMsg(LNFE.toString(), this, lsn, entryStates[idx]));
		    }
		} catch (Exception e) {
		    throw new DatabaseException(makeFetchErrorMsg(e.toString(), this, lsn, entryStates[idx]), e);
		}
	    }
	}
	return entryTargets[idx];
  }

  // line 702 "../../../../IN.ump"
   static  String makeFetchErrorMsg(String msg, IN in, long lsn, byte state){
    StringBuffer sb = new StringBuffer();
	sb.append("fetchTarget of ");
	if (lsn == DbLsn.NULL_LSN) {
	    sb.append("null lsn");
	} else {
	    sb.append(DbLsn.getNoFormatString(lsn));
	}
	if (in != null) {
	    sb.append(" IN=").append(in.getNodeId());
	}
	sb.append(" state=").append(state);
	sb.append(" ").append(msg);
	return sb.toString();
  }


  /**
   * 
   * Set the idx'th entry of this node.
   */
  // line 721 "../../../../IN.ump"
   public void setEntry(int idx, Node target, byte [] keyVal, long lsn, byte state){
    new IN_setEntry(this, idx, target, keyVal, lsn, state).execute();
  }


  /**
   * 
   * Update the idx'th entry of this node. Note: does not dirty the node.
   */
  // line 728 "../../../../IN.ump"
   public void updateEntry(int idx, Node node){
    new IN_updateEntry(this, idx, node).execute();
  }


  /**
   * 
   * Update the idx'th entry of this node.
   */
  // line 735 "../../../../IN.ump"
   public void updateEntry(int idx, Node node, long lsn){
    new IN_updateEntry2(this, idx, node, lsn).execute();
  }


  /**
   * 
   * Update the idx'th entry of this node.
   */
  // line 742 "../../../../IN.ump"
   public void updateEntry(int idx, Node node, long lsn, byte [] key){
    new IN_updateEntry3(this, idx, node, lsn, key).execute();
  }


  /**
   * 
   * Update the idx'th entry of this node.
   */
  // line 749 "../../../../IN.ump"
   public void updateEntry(int idx, long lsn){
    setLsn(idx, lsn);
	setDirty(true);
  }


  /**
   * 
   * Update the idx'th entry of this node.
   */
  // line 757 "../../../../IN.ump"
   public void updateEntry(int idx, long lsn, byte state){
    setLsn(idx, lsn);
	entryStates[idx] = state;
	setDirty(true);
  }


  /**
   * 
   * Update the idx'th entry of this node. This flavor is used when the target LN is being modified, by an operation like a delete or update. We don't have to check whether the LSN has been nulled or not, because we know an LSN existed before. Also, the modification of the target is done in the caller, so instead of passing in the old and new nodes, we pass in the old and new node sizes.
   */
  // line 766 "../../../../IN.ump"
   public void updateEntry(int idx, long lsn, long oldLNSize, long newLNSize){
    // line 177 "../../../../MemoryBudget_IN.ump"
    updateMemorySize(oldLNSize, newLNSize);
    	//original(idx, lsn, oldLNSize, newLNSize);
    // END OF UMPLE BEFORE INJECTION
    setLsn(idx, lsn);
	setDirty(true);
  }


  /**
   * 
   * Update the idx'th entry of this node.  Only update the key if the new key is less than the existing key.
   */
  // line 774 "../../../../IN.ump"
   private void updateEntryCompareKey(int idx, Node node, long lsn, byte [] key){
    new IN_updateEntryCompareKey(this, idx, node, lsn, key).execute();
  }


  /**
   * 
   * Returns whether the given key is greater than or equal to the first key in the IN and less than or equal to the last key in the IN.  This method is used to determine whether a key to be inserted belongs in this IN, without doing a tree search.  If false is returned it is still possible that the key belongs in this IN, but a tree search must be performed to find out.
   */
  // line 781 "../../../../IN.ump"
   public boolean isKeyInBounds(byte [] keyVal){
    if (nEntries < 2) {
	    return false;
	}
	Comparator userCompareToFcn = getKeyComparator();
	int cmp;
	byte[] myKey;
	myKey = entryKeyVals[0];
	cmp = Key.compareKeys(keyVal, myKey, userCompareToFcn);
	if (cmp < 0) {
	    return false;
	}
	myKey = entryKeyVals[nEntries - 1];
	cmp = Key.compareKeys(keyVal, myKey, userCompareToFcn);
	if (cmp > 0) {
	    return false;
	}
	return true;
  }


  /**
   * 
   * Find the entry in this IN for which key arg is >= the key. Currently uses a binary search, but eventually, this may use binary or linear search depending on key size, number of entries, etc. Note that the 0'th entry's key is treated specially in an IN.  It always compares lower than any other key. This is public so that DbCursorTest can access it.
   * @param key - the key to search for.
   * @param indicateIfDuplicate - true if EXACT_MATCH shouldbe or'd onto the return value if key is already present in this node.
   * @param exact - true if an exact match must be found.
   * @return offset for the entry that has a key >= the arg.  0 if keyis less than the 1st entry.  -1 if exact is true and no exact match is found.  If indicateIfDuplicate is true and an exact match was found then EXACT_MATCH is or'd onto the return value.
   */
  // line 808 "../../../../IN.ump"
   public int findEntry(byte [] key, boolean indicateIfDuplicate, boolean exact){
    int high = nEntries - 1;
	int low = 0;
	int middle = 0;
	Comparator userCompareToFcn = getKeyComparator();
	boolean entryZeroSpecialCompare = entryZeroKeyComparesLow() && !exact && !indicateIfDuplicate;
	assert nEntries >= 0;
	while (low <= high) {
	    middle = (high + low) / 2;
	    int s;
	    byte[] middleKey = null;
	    if (middle == 0 && entryZeroSpecialCompare) {
		s = 1;
	    } else {
		middleKey = entryKeyVals[middle];
		s = Key.compareKeys(key, middleKey, userCompareToFcn);
	    }
	    if (s < 0) {
		high = middle - 1;
	    } else if (s > 0) {
		low = middle + 1;
	    } else {
		int ret;
		if (indicateIfDuplicate) {
		    ret = middle | EXACT_MATCH;
		} else {
		    ret = middle;
		}
		if ((ret >= 0) && exact && isEntryKnownDeleted(ret & 0xffff)) {
		    return -1;
		} else {
		    return ret;
		}
	    }
	}
	if (exact) {
	    return -1;
	} else {
	    return high;
	}
  }


  /**
   * 
   * Inserts the argument ChildReference into this IN.  Assumes this node is already latched by the caller.
   * @param entry The ChildReference to insert into the IN.
   * @return true if the entry was successfully inserted, falseif it was a duplicate.
   * @throws InconsistentNodeException if the node is full(it should have been split earlier).
   */
  // line 856 "../../../../IN.ump"
   public boolean insertEntry(ChildReference entry) throws DatabaseException{
    return (insertEntry1(entry) & INSERT_SUCCESS) != 0;
  }


  /**
   * 
   * Same as insertEntry except that it returns the index where the dup was found instead of false.  The return value is |'d with either INSERT_SUCCESS or EXACT_MATCH depending on whether the entry was inserted or it was a duplicate, resp. This returns a failure if there's a duplicate match. The caller must do the processing to check if the entry is actually deleted and can be overwritten. This is foisted upon the caller rather than handled in this object because there may be some latch releasing/retaking in order to check a child LN. Inserts the argument ChildReference into this IN.  Assumes this node is already latched by the caller.
   * @param entry The ChildReference to insert into the IN.
   * @return either (1) the index of location in the IN where the entry wasinserted |'d with INSERT_SUCCESS, or (2) the index of the duplicate in the IN |'d with EXACT_MATCH if the entry was found to be a duplicate.
   * @throws InconsistentNodeException if the node is full (it should havebeen split earlier).
   */
  // line 866 "../../../../IN.ump"
   public int insertEntry1(ChildReference entry) throws DatabaseException{
    return new IN_insertEntry1(this, entry).execute();
  }


  /**
   * 
   * Deletes the ChildReference with the key arg from this IN.  Assumes this node is already latched by the caller. This seems to only be used by INTest.
   * @param key The key of the reference to delete from the IN.
   * @param maybeValidate true if assert validation should occur prior todelete.  Set this to false during recovery.
   * @return true if the entry was successfully deleted, false if it was notfound.
   */
  // line 876 "../../../../IN.ump"
  public boolean deleteEntry(byte [] key, boolean maybeValidate) throws DatabaseException{
    if (nEntries == 0) {
	    return false;
	}
	int index = findEntry(key, false, true);
	if (index < 0) {
	    return false;
	}
	return deleteEntry(index, maybeValidate);
  }


  /**
   * 
   * Deletes the ChildReference at index from this IN.  Assumes this node is already latched by the caller.
   * @param index The index of the entry to delete from the IN.
   * @param maybeValidate true if asserts are enabled.
   * @return true if the entry was successfully deleted, false if it was notfound.
   */
  // line 893 "../../../../IN.ump"
   public boolean deleteEntry(int index, boolean maybeValidate) throws DatabaseException{
    return new IN_deleteEntry(this, index, maybeValidate).execute();
  }


  /**
   * 
   * Do nothing since INs don't support deltas.
   */
  // line 900 "../../../../IN.ump"
   public void setProhibitNextDelta(){
    
  }

  // line 903 "../../../../IN.ump"
   public boolean compress(BINReference binRef, boolean canFetch) throws DatabaseException{
    return false;
  }

  // line 907 "../../../../IN.ump"
   public boolean isCompressible(){
    return false;
  }

  // line 911 "../../../../IN.ump"
  public boolean validateSubtreeBeforeDelete(int index) throws DatabaseException{
    return new IN_validateSubtreeBeforeDelete(this, index).execute();
  }


  /**
   * 
   * Return true if this node needs splitting.  For the moment, needing to be split is defined by there being no free entries available.
   */
  // line 918 "../../../../IN.ump"
   public boolean needsSplitting(){
    if ((entryTargets.length - nEntries) < 1) {
	    return true;
	} else {
	    return false;
	}
  }


  /**
   * 
   * Indicates whether whether entry 0's key is "special" in that it always compares less than any other key.  BIN's don't have the special key, but IN's do.
   */
  // line 929 "../../../../IN.ump"
  public boolean entryZeroKeyComparesLow(){
    return true;
  }


  /**
   * 
   * Split this into two nodes.  Parent IN is passed in parent and should be latched by the caller. childIndex is the index in parent of where "this" can be found.
   * @return lsn of the newly logged parent
   */
  // line 937 "../../../../IN.ump"
  public void split(IN parent, int childIndex, int maxEntries) throws DatabaseException{
    splitInternal(parent, childIndex, maxEntries, -1);
  }

  // line 941 "../../../../IN.ump"
   protected void splitInternal(IN parent, int childIndex, int maxEntries, int splitIndex) throws DatabaseException{
    new IN_splitInternal(this, parent, childIndex, maxEntries, splitIndex).execute();
  }


  /**
   * 
   * Called when we know we are about to split on behalf of a key that is the minimum (leftSide) or maximum (!leftSide) of this node.  This is achieved by just forcing the split to occur either one element in from the left or the right (i.e. splitIndex is 1 or nEntries - 1).
   */
  // line 949 "../../../../IN.ump"
  public void splitSpecial(IN parent, int parentIndex, int maxEntriesPerNode, byte [] key, boolean leftSide) throws DatabaseException{
    int index = findEntry(key, false, false);
	if (leftSide && index == 0) {
	    splitInternal(parent, parentIndex, maxEntriesPerNode, 1);
	} else if (!leftSide && index == (nEntries - 1)) {
	    splitInternal(parent, parentIndex, maxEntriesPerNode, nEntries - 1);
	} else {
	    split(parent, parentIndex, maxEntriesPerNode);
	}
  }

  // line 960 "../../../../IN.ump"
  public void adjustCursors(IN newSibling, int newSiblingLow, int newSiblingHigh){
    
  }

  // line 963 "../../../../IN.ump"
  public void adjustCursorsForInsert(int insertIndex){
    
  }


  /**
   * 
   * Return the relevant user defined comparison function for this type of node.  For IN's and BIN's, this is the BTree Comparison function.
   */
  // line 969 "../../../../IN.ump"
   public Comparator getKeyComparator(){
    return databaseImpl.getBtreeComparator();
  }


  /**
   * 
   * Shift entries to the right starting with (and including) the entry at index. Caller is responsible for incrementing nEntries.
   * @param index - The position to start shifting from.
   */
  // line 977 "../../../../IN.ump"
   private void shiftEntriesRight(int index){
    for (int i = nEntries; i > index; i--) {
	    setEntryInternal(i - 1, i);
	}
	clearEntry(index);
	setDirty(true);
  }


  /**
   * 
   * Shift entries starting at the byHowMuch'th element to the left, thus removing the first byHowMuch'th elements of the entries array.  This always starts at the 0th entry.  Caller is responsible for decrementing nEntries.
   * @param byHowMuch - The number of entries to remove from the left sideof the entries array.
   */
  // line 989 "../../../../IN.ump"
   private void shiftEntriesLeft(int byHowMuch){
    for (int i = 0; i < nEntries - byHowMuch; i++) {
	    setEntryInternal(i + byHowMuch, i);
	}
	for (int i = nEntries - byHowMuch; i < nEntries; i++) {
	    clearEntry(i);
	}
	setDirty(true);
  }


  /**
   * 
   * Check that the IN is in a valid state.  For now, validity means that the keys are in sorted order and that there are more than 0 entries. maxKey, if non-null specifies that all keys in this node must be less than maxKey.
   */
  // line 1002 "../../../../IN.ump"
   public void verify(byte [] maxKey) throws DatabaseException{
    new IN_verify(this, maxKey).execute();
  }


  /**
   * 
   * Add self and children to this in-memory IN list. Called by recovery, can run with no latching.
   */
  // line 1009 "../../../../IN.ump"
  public void rebuildINList(INList inList) throws DatabaseException{
    // line 185 "../../../../MemoryBudget_IN.ump"
    initMemorySize();
    	//original(inList);
    // END OF UMPLE BEFORE INJECTION
    inList.add(this);
	for (int i = 0; i < nEntries; i++) {
	    Node n = getTarget(i);
	    if (n != null) {
		n.rebuildINList(inList);
	    }
	}
  }


  /**
   * 
   * Remove self and children from the in-memory IN list. The INList latch is already held before this is called.  Also count removed nodes as obsolete.
   */
  // line 1022 "../../../../IN.ump"
  public void accountForSubtreeRemoval(INList inList, UtilizationTracker tracker) throws DatabaseException{
    if (nEntries > 1) {
	    throw new DatabaseException(
		    "Found non-deletable IN " + getNodeId() + " while flushing INList. nEntries = " + nEntries);
	}
	inList.removeLatchAlreadyHeld(this);
	if (lastFullVersion != DbLsn.NULL_LSN) {
	    tracker.countObsoleteNode(lastFullVersion, getLogType());
	}
	for (int i = 0; i < nEntries; i++) {
	    Node n = fetchTarget(i);
	    if (n != null) {
		n.accountForSubtreeRemoval(inList, tracker);
	    }
	}
  }


  /**
   * 
   * Check if this node fits the qualifications for being part of a deletable subtree. It can only have one IN child and no LN children.
   */
  // line 1042 "../../../../IN.ump"
  public boolean isValidForDelete() throws DatabaseException{
    return new IN_isValidForDelete(this).execute();
  }

  // line 1150 "../../../../IN.ump"
   protected void descendOnParentSearch(SearchResult result, boolean targetContainsDuplicates, boolean targetIsRoot, long targetNodeId, Node child, boolean requireExactMatch) throws DatabaseException{
    if (child.canBeAncestor(targetContainsDuplicates)) {
	    Label624: //this.hook624();
	    result.parent = (IN) child;
	} else {
	    Label625: //this.hook625(child);
	    result.exactParentFound = false;
	    result.keepSearching = false;
	    if (requireExactMatch) {
		Label626: //this.hook626();
		result.parent = null;
	    } else {
		result.parent = this;
	    }
	}
  }

  // line 1167 "../../../../IN.ump"
   protected boolean isSoughtNode(long nid, boolean updateGeneration) throws DatabaseException{
    latch(updateGeneration);
	if (getNodeId() == nid) {
	    Label627: //this.hook627();
	    return true;
	} else {
	    return false;
	}
  }

  // line 1177 "../../../../IN.ump"
   protected boolean canBeAncestor(boolean targetContainsDuplicates){
    return true;
  }


  /**
   * 
   * Returns whether any resident children are not LNs (are INs).
   */
  // line 1184 "../../../../IN.ump"
  public boolean hasNonLNChildren(){
    return hasResidentChildren();
  }


  /**
   * 
   * Returns whether any child is non-null.
   */
  // line 1191 "../../../../IN.ump"
   private boolean hasResidentChildren(){
    for (int i = 0; i < getNEntries(); i++) {
	    if (getTarget(i) != null) {
		return true;
	    }
	}
	return false;
  }

  // line 1200 "../../../../IN.ump"
  public void accumulateStats(TreeWalkerStatsAccumulator acc){
    acc.processIN(this, new Long(getNodeId()), getLevel());
  }


  /**
   * 
   * Log this IN and clear the dirty flag.
   */
  // line 1207 "../../../../IN.ump"
   public long log(LogManager logManager) throws DatabaseException{
    return logInternal(logManager, false, false, false, null);
  }


  /**
   * 
   * Log this IN and clear the dirty flag.
   */
  // line 1214 "../../../../IN.ump"
   public long log(LogManager logManager, boolean allowDeltas, boolean proactiveMigration) throws DatabaseException{
    return logInternal(logManager, allowDeltas, false, proactiveMigration, null);
  }


  /**
   * 
   * Log this node provisionally and clear the dirty flag.
   * @param item object to be logged
   * @return LSN of the new log entry
   */
  // line 1223 "../../../../IN.ump"
   public long logProvisional(LogManager logManager, IN parent) throws DatabaseException{
    return logInternal(logManager, false, true, false, parent);
  }


  /**
   * 
   * Log this node with all available options.
   */
  // line 1231 "../../../../IN.ump"
   public long log(LogManager logManager, boolean allowDeltas, boolean isProvisional, boolean proactiveMigration, IN parent) throws DatabaseException{
    return logInternal(logManager, allowDeltas, isProvisional, proactiveMigration, parent);
  }


  /**
   * 
   * Decide how to log this node. INs are always logged in full.  Migration never performed since it only applies to BINs.
   */
  // line 1239 "../../../../IN.ump"
   protected long logInternal(LogManager logManager, boolean allowDeltas, boolean isProvisional, boolean proactiveMigration, IN parent) throws DatabaseException{
    long lsn = logManager.log(new INLogEntry(this), isProvisional,
		isProvisional ? DbLsn.NULL_LSN : lastFullVersion);
	if (isProvisional) {
	    if (parent != null) {
		parent.trackProvisionalObsolete(this, lastFullVersion, DbLsn.NULL_LSN);
	    }
	} else {
	    flushProvisionalObsolete(logManager);
	}
	setLastFullLsn(lsn);
	setDirty(false);
	return lsn;
  }


  /**
   * 
   * Adds the given obsolete LSNs and any tracked obsolete LSNs for the given child IN to this IN's tracking list.  This method is called to track obsolete LSNs when a child IN is logged provisionally.  Such LSNs cannot be considered obsolete until an ancestor IN is logged non-provisionally.
   */
  // line 1257 "../../../../IN.ump"
  public void trackProvisionalObsolete(IN child, long obsoleteLsn1, long obsoleteLsn2){
    new IN_trackProvisionalObsolete(this, child, obsoleteLsn1, obsoleteLsn2).execute();
  }


  /**
   * 
   * Adds the provisional obsolete tracking information in this node to the live tracker.  This method is called when this node is logged non-provisionally.
   */
  // line 1264 "../../../../IN.ump"
  public void flushProvisionalObsolete(LogManager logManager) throws DatabaseException{
    new IN_flushProvisionalObsolete(this, logManager).execute();
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 1271 "../../../../IN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_IN;
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 1278 "../../../../IN.ump"
   public int getLogSize(){
    int size = super.getLogSize();
	size += LogUtils.getByteArrayLogSize(identifierKey);
	size += LogUtils.getBooleanLogSize();
	size += LogUtils.INT_BYTES;
	size += LogUtils.INT_BYTES;
	size += LogUtils.INT_BYTES;
	size += LogUtils.getBooleanLogSize();
	boolean compactLsnsRep = (entryLsnLongArray == null);
	if (compactLsnsRep) {
	    size += LogUtils.INT_BYTES;
	}
	for (int i = 0; i < nEntries; i++) {
	    size += LogUtils.getByteArrayLogSize(entryKeyVals[i])
		    + (compactLsnsRep ? LogUtils.INT_BYTES : LogUtils.getLongLogSize()) + 1;
	}
	return size;
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 1300 "../../../../IN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	LogUtils.writeByteArray(logBuffer, identifierKey);
	LogUtils.writeBoolean(logBuffer, isRoot);
	LogUtils.writeInt(logBuffer, nEntries);
	LogUtils.writeInt(logBuffer, level);
	LogUtils.writeInt(logBuffer, entryTargets.length);
	boolean compactLsnsRep = (entryLsnLongArray == null);
	LogUtils.writeBoolean(logBuffer, compactLsnsRep);
	if (compactLsnsRep) {
	    LogUtils.writeInt(logBuffer, (int) baseFileNumber);
	}
	for (int i = 0; i < nEntries; i++) {
	    LogUtils.writeByteArray(logBuffer, entryKeyVals[i]);
	    assert !(getLsn(i) == DbLsn.NULL_LSN && (entryStates[i] & KNOWN_DELETED_BIT) == 0);
	    if (compactLsnsRep) {
		int offset = i << 2;
		int fileOffset = getFileOffset(offset);
		logBuffer.put(getFileNumberOffset(offset));
		logBuffer.put((byte) ((fileOffset >>> 0) & 0xff));
		logBuffer.put((byte) ((fileOffset >>> 8) & 0xff));
		logBuffer.put((byte) ((fileOffset >>> 16) & 0xff));
	    } else {
		LogUtils.writeLong(logBuffer, entryLsnLongArray[i]);
	    }
	    logBuffer.put(entryStates[i]);
	    entryStates[i] &= CLEAR_DIRTY_BIT;
	}
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 1333 "../../../../IN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	identifierKey = LogUtils.readByteArray(itemBuffer);
	isRoot = LogUtils.readBoolean(itemBuffer);
	nEntries = LogUtils.readInt(itemBuffer);
	level = LogUtils.readInt(itemBuffer);
	int length = LogUtils.readInt(itemBuffer);
	entryTargets = new Node[length];
	entryKeyVals = new byte[length][];
	baseFileNumber = -1;
	long storedBaseFileNumber = -1;
	entryLsnByteArray = new byte[length << 2];
	entryLsnLongArray = null;
	entryStates = new byte[length];
	boolean compactLsnsRep = false;
	if (entryTypeVersion > 1) {
	    compactLsnsRep = LogUtils.readBoolean(itemBuffer);
	    if (compactLsnsRep) {
		baseFileNumber = LogUtils.readInt(itemBuffer) & 0xffffffff;
		storedBaseFileNumber = baseFileNumber;
	    }
	}
	for (int i = 0; i < nEntries; i++) {
	    entryKeyVals[i] = LogUtils.readByteArray(itemBuffer);
	    long lsn;
	    if (compactLsnsRep) {
		byte fileNumberOffset = itemBuffer.get();
		int fileOffset = (itemBuffer.get() & 0xff);
		fileOffset |= ((itemBuffer.get() & 0xff) << 8);
		fileOffset |= ((itemBuffer.get() & 0xff) << 16);
		if (fileOffset == THREE_BYTE_NEGATIVE_ONE) {
		    lsn = DbLsn.NULL_LSN;
		} else {
		    lsn = DbLsn.makeLsn(storedBaseFileNumber + fileNumberOffset, fileOffset);
		}
	    } else {
		lsn = LogUtils.readLong(itemBuffer);
	    }
	    setLsnElement(i, lsn);
	    byte entryState = itemBuffer.get();
	    entryState &= CLEAR_DIRTY_BIT;
	    entryState &= CLEAR_MIGRATE_BIT;
	    if (lsn == DbLsn.NULL_LSN) {
		entryState |= KNOWN_DELETED_BIT;
	    }
	    entryStates[i] = entryState;
	}
    // line 126 "../../../../Latches_IN.ump"
    //original(itemBuffer, entryTypeVersion);
    	latch.setName(shortClassName() + getNodeId());
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 1385 "../../../../IN.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append(beginTag());
	super.dumpLog(sb, verbose);
	sb.append(Key.dumpString(identifierKey, 0));
	sb.append("<isRoot val=\"");
	sb.append(isRoot);
	sb.append("\"/>");
	sb.append("<level val=\"");
	sb.append(Integer.toHexString(level));
	sb.append("\"/>");
	sb.append("<entries numEntries=\"");
	sb.append(nEntries);
	sb.append("\" length=\"");
	sb.append(entryTargets.length);
	boolean compactLsnsRep = (entryLsnLongArray == null);
	if (compactLsnsRep) {
	    sb.append("\" baseFileNumber=\"");
	    sb.append(baseFileNumber);
	}
	sb.append("\">");
	if (verbose) {
	    for (int i = 0; i < nEntries; i++) {
		sb.append("<ref knownDeleted=\"").append(isEntryKnownDeleted(i));
		sb.append("\" pendingDeleted=\"").append(isEntryPendingDeleted(i));
		sb.append("\">");
		sb.append(Key.dumpString(entryKeyVals[i], 0));
		sb.append(DbLsn.toString(getLsn(i)));
		sb.append("</ref>");
	    }
	}
	sb.append("</entries>");
	dumpLogAdditional(sb);
	sb.append(endTag());
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional.
   */
  // line 1423 "../../../../IN.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 1430 "../../../../IN.ump"
   public long getTransactionId(){
    return 0;
  }


  /**
   * 
   * Allows subclasses to add additional fields before the end tag. If they just overload dumpLog, the xml isn't nested.
   */
  // line 1437 "../../../../IN.ump"
   protected void dumpLogAdditional(StringBuffer sb){
    
  }

  // line 1440 "../../../../IN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 1444 "../../../../IN.ump"
   public String endTag(){
    return END_TAG;
  }

  // line 1448 "../../../../IN.ump"
  public void dumpKeys() throws DatabaseException{
    for (int i = 0; i < nEntries; i++) {
	    System.out.println(Key.dumpString(entryKeyVals[i], 0));
	}
  }


  /**
   * 
   * For unit test support:
   * @return a string that dumps information about this IN, without
   */
  // line 1458 "../../../../IN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	if (dumpTags) {
	    sb.append(TreeUtils.indent(nSpaces));
	    sb.append(beginTag());
	    sb.append('\n');
	}
	sb.append(super.dumpString(nSpaces + 2, true));
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<idkey>");
	sb.append(identifierKey == null ? "" : Key.dumpString(identifierKey, 0));
	sb.append("</idkey>");
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<dirty val=\"").append(dirty).append("\"/>");
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<generation val=\"").append(generation).append("\"/>");
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<level val=\"");
	sb.append(Integer.toHexString(level)).append("\"/>");
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<isRoot val=\"").append(isRoot).append("\"/>");
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<entries nEntries=\"");
	sb.append(nEntries);
	sb.append("\">");
	sb.append('\n');
	for (int i = 0; i < nEntries; i++) {
	    sb.append(TreeUtils.indent(nSpaces + 4));
	    sb.append("<entry id=\"" + i + "\">");
	    sb.append('\n');
	    if (getLsn(i) == DbLsn.NULL_LSN) {
		sb.append(TreeUtils.indent(nSpaces + 6));
		sb.append("<lsn/>");
	    } else {
		sb.append(DbLsn.dumpString(getLsn(i), nSpaces + 6));
	    }
	    sb.append('\n');
	    if (entryKeyVals[i] == null) {
		sb.append(TreeUtils.indent(nSpaces + 6));
		sb.append("<key/>");
	    } else {
		sb.append(Key.dumpString(entryKeyVals[i], (nSpaces + 6)));
	    }
	    sb.append('\n');
	    if (entryTargets[i] == null) {
		sb.append(TreeUtils.indent(nSpaces + 6));
		sb.append("<target/>");
	    } else {
		sb.append(entryTargets[i].dumpString(nSpaces + 6, true));
	    }
	    sb.append('\n');
	    sb.append(TreeUtils.indent(nSpaces + 6));
	    dumpDeletedState(sb, getState(i));
	    sb.append("<dirty val=\"").append(isDirty(i)).append("\"/>");
	    sb.append('\n');
	    sb.append(TreeUtils.indent(nSpaces + 4));
	    sb.append("</entry>");
	    sb.append('\n');
	}
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("</entries>");
	sb.append('\n');
	if (dumpTags) {
	    sb.append(TreeUtils.indent(nSpaces));
	    sb.append(endTag());
	}
	return sb.toString();
  }


  /**
   * 
   * Utility method for output of knownDeleted and pendingDelete.
   */
  // line 1536 "../../../../IN.ump"
   static  void dumpDeletedState(StringBuffer sb, byte state){
    sb.append("<knownDeleted val=\"");
	sb.append(isStateKnownDeleted(state)).append("\"/>");
	sb.append("<pendingDeleted val=\"");
	sb.append(isStatePendingDeleted(state)).append("\"/>");
  }

  // line 1543 "../../../../IN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 1547 "../../../../IN.ump"
   public String shortClassName(){
    return "IN";
  }


  /**
   * protected void hook618(EnvironmentImpl env) {
   * }
   * protected void hook619(boolean updateGeneration) throws DatabaseException {
   * if (updateGeneration) {
   * setGeneration();
   * }
   * throw new ReturnBoolean(true);
   * }
   */
  // line 1561 "../../../../IN.ump"
   protected void hook620() throws DatabaseException{
    
  }

  // line 1564 "../../../../IN.ump"
   protected void hook621() throws DatabaseException{
    
  }

  // line 1567 "../../../../IN.ump"
   protected void hook622() throws DatabaseException{
    
  }

  // line 1570 "../../../../IN.ump"
   protected void hook623() throws DatabaseException{
    
  }

  // line 1573 "../../../../IN.ump"
   protected void hook624() throws DatabaseException{
    
  }

  // line 1576 "../../../../IN.ump"
   protected void hook625(Node child) throws DatabaseException{
    
  }

  // line 1579 "../../../../IN.ump"
   protected void hook626() throws DatabaseException{
    
  }

  // line 1582 "../../../../IN.ump"
   protected void hook627() throws DatabaseException{
    
  }


  /**
   * 
   * Initialize the per-node memory count by computing its memory usage.
   */
  // line 13 "../../../../MemoryBudget_IN.ump"
   protected void initMemorySize(){
    inMemorySize = computeMemorySize();
  }

  // line 17 "../../../../MemoryBudget_IN.ump"
   public boolean verifyMemorySize(){
    long calcMemorySize = computeMemorySize();
			if (calcMemorySize != inMemorySize) {
					String msg = "-Warning: Out of sync. " + "Should be " + calcMemorySize + " / actual: " + inMemorySize
						+ " node: " + getNodeId();

				  Label615:
Tracer.trace(Level.INFO, databaseImpl.getDbEnvironment(), msg);
	//original(msg);
				 // this.hook615(msg);
					System.out.println(msg);
					return false;
			} 
      else {
					return true;
			}
  }


  /**
   * 
   * Return the number of bytes used by this IN.  Latching is up to the caller.
   */
  // line 35 "../../../../MemoryBudget_IN.ump"
   public long getInMemorySize(){
    return inMemorySize;
  }

  // line 39 "../../../../MemoryBudget_IN.ump"
   private long getEntryInMemorySize(int idx){
    return getEntryInMemorySize(entryKeyVals[idx], entryTargets[idx]);
  }

  // line 43 "../../../../MemoryBudget_IN.ump"
   protected long getEntryInMemorySize(byte [] key, Node target){
    long ret = 0;
			if (key != null) {
					ret += MemoryBudget.byteArraySize(key.length);
			}
			if (target != null) {
					ret += target.getMemorySizeIncludedByParent();
			}
		return ret;
  }


  /**
   * 
   * Count up the memory usage attributable to this node alone. LNs children are counted by their BIN/DIN parents, but INs are not counted by their parents because they are resident on the IN list.
   */
  // line 57 "../../../../MemoryBudget_IN.ump"
   protected long computeMemorySize(){
    MemoryBudget mb = databaseImpl.getDbEnvironment().getMemoryBudget();
		long calcMemorySize = getMemoryOverhead(mb);
		calcMemorySize += computeLsnOverhead();
		for (int i = 0; i < nEntries; i++) {
			  calcMemorySize += getEntryInMemorySize(i);
		}
		if (provisionalObsolete != null) {
			  calcMemorySize += provisionalObsolete.size() * MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
		}
		return calcMemorySize;
  }

  // line 70 "../../../../MemoryBudget_IN.ump"
   public static  long computeOverhead(DbConfigManager configManager) throws DatabaseException{
    return MemoryBudget.IN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }

  // line 74 "../../../../MemoryBudget_IN.ump"
   private int computeLsnOverhead(){
    if (entryLsnLongArray == null) {
			  return MemoryBudget.byteArraySize(entryLsnByteArray.length);
		} else {
			  return MemoryBudget.BYTE_ARRAY_OVERHEAD + entryLsnLongArray.length * MemoryBudget.LONG_OVERHEAD;
		}
  }

  // line 82 "../../../../MemoryBudget_IN.ump"
   protected static  long computeArraysOverhead(DbConfigManager configManager) throws DatabaseException{
    int capacity = configManager.getInt(EnvironmentParams.NODE_MAX);
		return MemoryBudget.byteArraySize(capacity) + (capacity * (2 * MemoryBudget.ARRAY_ITEM_OVERHEAD));
  }

  // line 87 "../../../../MemoryBudget_IN.ump"
   protected long getMemoryOverhead(MemoryBudget mb){
    return mb.getINOverhead();
  }

  // line 91 "../../../../MemoryBudget_IN.ump"
   protected void updateMemorySize(ChildReference oldRef, ChildReference newRef){
    long delta = 0;
		if (newRef != null) {
			  delta = getEntryInMemorySize(newRef.getKey(), newRef.getTarget());
		}
		if (oldRef != null) {
			  delta -= getEntryInMemorySize(oldRef.getKey(), oldRef.getTarget());
		}
		changeMemorySize(delta);
  }

  // line 102 "../../../../MemoryBudget_IN.ump"
   protected void updateMemorySize(long oldSize, long newSize){
    long delta = newSize - oldSize;
		changeMemorySize(delta);
  }

  // line 107 "../../../../MemoryBudget_IN.ump"
  public void updateMemorySize(Node oldNode, Node newNode){
    long delta = 0;
		if (newNode != null) {
			  delta = newNode.getMemorySizeIncludedByParent();
		}
		if (oldNode != null) {
			  delta -= oldNode.getMemorySizeIncludedByParent();
		}
		changeMemorySize(delta);
  }

  // line 118 "../../../../MemoryBudget_IN.ump"
   private void changeMemorySize(long delta){
    inMemorySize += delta;
			if (inListResident) {
					MemoryBudget mb = databaseImpl.getDbEnvironment().getMemoryBudget();
					accumulatedDelta += delta;
					if (accumulatedDelta > ACCUMULATED_LIMIT || accumulatedDelta < -ACCUMULATED_LIMIT) {
				mb.updateTreeMemoryUsage(accumulatedDelta);
				accumulatedDelta = 0;
					}
			}
  }

  // line 130 "../../../../MemoryBudget_IN.ump"
   public int getAccumulatedDelta(){
    return accumulatedDelta;
  }

  // line 134 "../../../../MemoryBudget_IN.ump"
   public void setInListResident(boolean resident){
    inListResident = resident;
  }

  // line 138 "../../../../MemoryBudget_IN.ump"
   protected void hook615(String msg){
    
  }


  /**
   * 
   * Returns whether this node can itself be evicted.  This is faster than (getEvictionType() == MAY_EVICT_NODE) and is used by the evictor after a node has been selected, to check that it is still evictable.
   */
  // line 15 "../../../../Evictor_IN.ump"
   public boolean isEvictable(){
    if (isEvictionProhibited()) {
					return false;
			}
			if (hasNonLNChildren()) {
					return false;
			}
			return true;
  }


  /**
   * 
   * Returns the eviction type for this IN, for use by the evictor.  Uses the internal isEvictionProhibited and getChildEvictionType methods that may be overridden by subclasses.
   * @return MAY_EVICT_LNS if evictable LNs may be stripped; otherwise,MAY_EVICT_NODE if the node itself may be evicted; otherwise, MAY_NOT_EVICT.
   */
  // line 29 "../../../../Evictor_IN.ump"
   public int getEvictionType(){
    if (isEvictionProhibited()) {
					return MAY_NOT_EVICT;
			} else {
					return getChildEvictionType();
			}
  }


  /**
   * 
   * Returns whether the node is not evictable, irrespective of the status of the children nodes.
   */
  // line 40 "../../../../Evictor_IN.ump"
  public boolean isEvictionProhibited(){
    return isDbRoot();
  }


  /**
   * 
   * Returns the eviction type based on the status of child nodes, irrespective of isEvictionProhibited.
   */
  // line 47 "../../../../Evictor_IN.ump"
  public int getChildEvictionType(){
    return hasResidentChildren() ? MAY_NOT_EVICT : MAY_EVICT_NODE;
  }


  /**
   * 
   * Latch this node and set the generation.
   */
  // line 14 "../../../../Latches_IN.ump"
   public void latch() throws DatabaseException{
    latch(true);
  }


  /**
   * 
   * Latch this node if it is not latched by another thread, and set the generation if the latch succeeds.
   */
  // line 21 "../../../../Latches_IN.ump"
   public boolean latchNoWait() throws DatabaseException{
    return latchNoWait(true);
  }


  /**
   * 
   * Release the latch on this node.
   */
  // line 28 "../../../../Latches_IN.ump"
   public void releaseLatch() throws LatchNotHeldException{
    latch.release();
  }


  /**
   * 
   * Release the latch on this node.
   */
  // line 35 "../../../../Latches_IN.ump"
   public void releaseLatchIfOwner() throws LatchNotHeldException{
    latch.releaseIfOwner();
  }


  /**
   * 
   * @return true if this thread holds the IN's latch
   */
  // line 42 "../../../../Latches_IN.ump"
   public boolean isLatchOwner(){
    return latch.isOwner();
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 10 "../../../../LoggingFine_IN.ump"
   private void traceSplit(Level level, IN parent, IN newSibling, long parentLsn, long myNewLsn, long newSiblingLsn, int splitIndex, int idKeyIndex, int childIndex){
    new IN_traceSplit(this, level, parent, newSibling, parentLsn, myNewLsn, newSiblingLsn, splitIndex, idKeyIndex,
		childIndex).execute();
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 9 "../../../../LoggingFinest_IN.ump"
   private void traceDelete(Level level, int index){
    new IN_traceDelete(this, level, index).execute();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 4 "../../../../IN_static.ump"
  // line 83 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_setLsn
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_setLsn()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../IN_static.ump"
    public  IN_setLsn(IN _this, int idx, long lsn){
      this._this=_this;
          this.idx=idx;
          this.lsn=lsn;
    }
  
    // line 11 "../../../../IN_static.ump"
    public void execute(){
      // line 85 "../../../../MemoryBudget_IN_inner.ump"
      oldSize=_this.computeLsnOverhead();
              //original();
      // END OF UMPLE BEFORE INJECTION
      _this.setLsnElement(idx,lsn);
          //this.hook639();
          Label639:
  _this.changeMemorySize(_this.computeLsnOverhead() - oldSize);
          //original();
  
          _this.entryStates[idx]|=_this.DIRTY_BIT;
    }
  
    // line 21 "../../../../IN_static.ump"
     protected void hook639(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 16 "../../../../IN_static.ump"
    protected IN _this ;
  // line 17 "../../../../IN_static.ump"
    protected int idx ;
  // line 18 "../../../../IN_static.ump"
    protected long lsn ;
  // line 19 "../../../../IN_static.ump"
    protected int oldSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 23 "../../../../IN_static.ump"
  // line 125 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_setEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_setEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 25 "../../../../IN_static.ump"
    public  IN_setEntry(IN _this, int idx, Node target, byte [] keyVal, long lsn, byte state){
      this._this=_this;
          this.idx=idx;
          this.target=target;
          this.keyVal=keyVal;
          this.lsn=lsn;
          this.state=state;
    }
  
    // line 33 "../../../../IN_static.ump"
    public void execute(){
      // line 127 "../../../../MemoryBudget_IN_inner.ump"
      oldSize=_this.getEntryInMemorySize(idx);
              //original();
      // END OF UMPLE BEFORE INJECTION
      newNEntries=idx + 1;
          if (newNEntries > _this.nEntries) {
            _this.nEntries=newNEntries;
            //this.hook641();
            Label641:
  oldSize=0;
          //original();
  
          }
          _this.entryTargets[idx]=target;
          _this.entryKeyVals[idx]=keyVal;
          _this.setLsnElement(idx,lsn);
          _this.entryStates[idx]=state;
          //this.hook640();
          Label640:
  newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
          //original();
  
          _this.setDirty(true);
    }
  
    // line 57 "../../../../IN_static.ump"
     protected void hook640(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 47 "../../../../IN_static.ump"
    protected IN _this ;
  // line 48 "../../../../IN_static.ump"
    protected int idx ;
  // line 49 "../../../../IN_static.ump"
    protected Node target ;
  // line 50 "../../../../IN_static.ump"
    protected byte[] keyVal ;
  // line 51 "../../../../IN_static.ump"
    protected long lsn ;
  // line 52 "../../../../IN_static.ump"
    protected byte state ;
  // line 53 "../../../../IN_static.ump"
    protected long oldSize ;
  // line 54 "../../../../IN_static.ump"
    protected int newNEntries ;
  // line 55 "../../../../IN_static.ump"
    protected long newSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 61 "../../../../IN_static.ump"
  // line 72 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_updateEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 63 "../../../../IN_static.ump"
    public  IN_updateEntry(IN _this, int idx, Node node){
      this._this=_this;
          this.idx=idx;
          this.node=node;
    }
  
    // line 68 "../../../../IN_static.ump"
    public void execute(){
      // line 74 "../../../../MemoryBudget_IN_inner.ump"
      oldSize=_this.getEntryInMemorySize(idx);
              //original();
      // END OF UMPLE BEFORE INJECTION
      _this.setTarget(idx,node);
      // line 79 "../../../../MemoryBudget_IN_inner.ump"
      newSize=_this.getEntryInMemorySize(idx);
              _this.updateMemorySize(oldSize,newSize);
      // END OF UMPLE AFTER INJECTION
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 70 "../../../../IN_static.ump"
    protected IN _this ;
  // line 71 "../../../../IN_static.ump"
    protected int idx ;
  // line 72 "../../../../IN_static.ump"
    protected Node node ;
  // line 73 "../../../../IN_static.ump"
    protected long oldSize ;
  // line 74 "../../../../IN_static.ump"
    protected long newSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 76 "../../../../IN_static.ump"
  // line 93 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_updateEntry2
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntry2()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 78 "../../../../IN_static.ump"
    public  IN_updateEntry2(IN _this, int idx, Node node, long lsn){
      this._this=_this;
          this.idx=idx;
          this.node=node;
          this.lsn=lsn;
    }
  
    // line 84 "../../../../IN_static.ump"
    public void execute(){
      // line 95 "../../../../MemoryBudget_IN_inner.ump"
      oldSize=_this.getEntryInMemorySize(idx);
              //original();
      // END OF UMPLE BEFORE INJECTION
      _this.setLsn(idx,lsn);
          _this.setTarget(idx,node);
          //this.hook642();
          Label642:
  newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
         // original();
  
          _this.setDirty(true);
    }
  
    // line 97 "../../../../IN_static.ump"
     protected void hook642(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 90 "../../../../IN_static.ump"
    protected IN _this ;
  // line 91 "../../../../IN_static.ump"
    protected int idx ;
  // line 92 "../../../../IN_static.ump"
    protected Node node ;
  // line 93 "../../../../IN_static.ump"
    protected long lsn ;
  // line 94 "../../../../IN_static.ump"
    protected long oldSize ;
  // line 95 "../../../../IN_static.ump"
    protected long newSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 99 "../../../../IN_static.ump"
  // line 114 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_updateEntry3
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntry3()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 101 "../../../../IN_static.ump"
    public  IN_updateEntry3(IN _this, int idx, Node node, long lsn, byte [] key){
      this._this=_this;
          this.idx=idx;
          this.node=node;
          this.lsn=lsn;
          this.key=key;
    }
  
    // line 108 "../../../../IN_static.ump"
    public void execute(){
      // line 116 "../../../../MemoryBudget_IN_inner.ump"
      oldSize=_this.getEntryInMemorySize(idx);
              //original();
      // END OF UMPLE BEFORE INJECTION
      _this.setLsn(idx,lsn);
          _this.setTarget(idx,node);
          _this.setKey(idx,key);
          //this.hook643();
          Label643:
  newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
          //original();
  
          _this.setDirty(true);
    }
  
    // line 123 "../../../../IN_static.ump"
     protected void hook643(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 115 "../../../../IN_static.ump"
    protected IN _this ;
  // line 116 "../../../../IN_static.ump"
    protected int idx ;
  // line 117 "../../../../IN_static.ump"
    protected Node node ;
  // line 118 "../../../../IN_static.ump"
    protected long lsn ;
  // line 119 "../../../../IN_static.ump"
    protected byte[] key ;
  // line 120 "../../../../IN_static.ump"
    protected long oldSize ;
  // line 121 "../../../../IN_static.ump"
    protected long newSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 125 "../../../../IN_static.ump"
  // line 61 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_updateEntryCompareKey
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntryCompareKey()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 127 "../../../../IN_static.ump"
    public  IN_updateEntryCompareKey(IN _this, int idx, Node node, long lsn, byte [] key){
      this._this=_this;
          this.idx=idx;
          this.node=node;
          this.lsn=lsn;
          this.key=key;
    }
  
    // line 134 "../../../../IN_static.ump"
    public void execute(){
      // line 63 "../../../../MemoryBudget_IN_inner.ump"
      oldSize=_this.getEntryInMemorySize(idx);
              //original();
      // END OF UMPLE BEFORE INJECTION
      _this.setLsn(idx,lsn);
          _this.setTarget(idx,node);
          existingKey=_this.getKey(idx);
          s=Key.compareKeys(key,existingKey,_this.getKeyComparator());
          if (s < 0) {
            _this.setKey(idx,key);
          }
          //this.hook644();
         Label644:
  newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
          //original();
  
          _this.setDirty(true);
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 145 "../../../../IN_static.ump"
    protected IN _this ;
  // line 146 "../../../../IN_static.ump"
    protected int idx ;
  // line 147 "../../../../IN_static.ump"
    protected Node node ;
  // line 148 "../../../../IN_static.ump"
    protected long lsn ;
  // line 149 "../../../../IN_static.ump"
    protected byte[] key ;
  // line 150 "../../../../IN_static.ump"
    protected long oldSize ;
  // line 151 "../../../../IN_static.ump"
    protected byte[] existingKey ;
  // line 152 "../../../../IN_static.ump"
    protected int s ;
  // line 153 "../../../../IN_static.ump"
    protected long newSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 157 "../../../../IN_static.ump"
  // line 47 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_insertEntry1
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_insertEntry1()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 159 "../../../../IN_static.ump"
    public  IN_insertEntry1(IN _this, ChildReference entry){
      this._this=_this;
          this.entry=entry;
    }
  
    // line 163 "../../../../IN_static.ump"
    public int execute() throws DatabaseException{
      if (_this.nEntries >= _this.entryTargets.length) {
            _this.compress(null,true);
          }
          if (_this.nEntries < _this.entryTargets.length) {
            key=entry.getKey();
            index=_this.findEntry(key,true,false);
            if (index >= 0 && (index & _this.EXACT_MATCH) != 0) {
              return index;
            }
     else {
              index++;
            }
            if (index < _this.nEntries) {
              //this.hook647();
              Label647:
  oldSize=_this.computeLsnOverhead();
          //original();
  
              _this.shiftEntriesRight(index);
              //this.hook646();
              Label646:
  _this.changeMemorySize(_this.computeLsnOverhead() - oldSize);
          //original();
  
            }
            _this.entryTargets[index]=entry.getTarget();
            _this.entryKeyVals[index]=entry.getKey();
            _this.setLsnElement(index,entry.getLsn());
            _this.entryStates[index]=entry.getState();
            _this.nEntries++;
            _this.adjustCursorsForInsert(index);
           // this.hook645();
           Label645:
  _this.updateMemorySize(0,_this.getEntryInMemorySize(index));
          //original();
  
            _this.setDirty(true);
            return (index | _this.INSERT_SUCCESS);
          }
     else {
            throw new InconsistentNodeException("Node " + _this.getNodeId() + " should have been split before calling insertEntry");
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 197 "../../../../IN_static.ump"
    protected IN _this ;
  // line 198 "../../../../IN_static.ump"
    protected ChildReference entry ;
  // line 199 "../../../../IN_static.ump"
    protected byte[] key ;
  // line 200 "../../../../IN_static.ump"
    protected int index ;
  // line 201 "../../../../IN_static.ump"
    protected int oldSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 207 "../../../../IN_static.ump"
  // line 11 "../../../../MemoryBudget_IN_inner.ump"
  // line 18 "../../../../LoggingFinest_IN_inner.ump"
  public static class IN_deleteEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_deleteEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 209 "../../../../IN_static.ump"
    public  IN_deleteEntry(IN _this, int index, boolean maybeValidate){
      this._this=_this;
          this.index=index;
          this.maybeValidate=maybeValidate;
    }
  
    // line 214 "../../../../IN_static.ump"
    public boolean execute() throws DatabaseException{
      if (_this.nEntries == 0) {
            return false;
          }
          assert maybeValidate ? _this.validateSubtreeBeforeDelete(index) : true;
          if (index < _this.nEntries) {
            //this.hook649();
            Label649:
  _this.updateMemorySize(_this.getEntryInMemorySize(index),0);
          oldLSNArraySize=_this.computeLsnOverhead();
          //original();
  
            for (int i=index; i < _this.nEntries - 1; i++) {
              _this.setEntryInternal(i + 1,i);
            }
            _this.clearEntry(_this.nEntries - 1);
            //this.hook648();
            Label648:
  _this.updateMemorySize(oldLSNArraySize,_this.computeLsnOverhead());
          //original();
  
            _this.nEntries--;
            _this.setDirty(true);
            _this.setProhibitNextDelta();
            Label616: //this.hook616();
            return true;
          }
     else {
            return false;
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 237 "../../../../IN_static.ump"
    protected IN _this ;
  // line 238 "../../../../IN_static.ump"
    protected int index ;
  // line 239 "../../../../IN_static.ump"
    protected boolean maybeValidate ;
  // line 240 "../../../../IN_static.ump"
    protected int oldLSNArraySize ;
  
  // line 20 "../../../../LoggingFinest_IN_inner.ump"
    protected void hook616: execute () 
    {
      _this.traceDelete(Level.FINEST,index);
          //original();
    }
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 247 "../../../../IN_static.ump"
  // line 40 "../../../../Latches_IN_inner.ump"
  public static class IN_validateSubtreeBeforeDelete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_validateSubtreeBeforeDelete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 249 "../../../../IN_static.ump"
    public  IN_validateSubtreeBeforeDelete(IN _this, int index){
      this._this=_this;
          this.index=index;
    }
  
    // line 253 "../../../../IN_static.ump"
    public boolean execute() throws DatabaseException{
      try {
            Label628:
  needToLatch=!_this.isLatchOwner();    
  				//try {          //original();//      }
   //this.hook628();
  	 				this.hook629();
  		      if (index >= _this.nEntries) {
  		        throw new ReturnBoolean(true);
  		      }
  					 else {
  		        child=_this.fetchTarget(index);
  		        throw new ReturnBoolean(child != null && child.isValidForDelete());
  		      }
  // end hook628
            throw ReturnHack.returnBoolean;
          }
     catch (      ReturnBoolean r) {
  Label628_1:
  if (needToLatch) {
              _this.releaseLatchIfOwner();
            }
  
            return r.value;
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 271 "../../../../IN_static.ump"
    protected IN _this ;
  // line 272 "../../../../IN_static.ump"
    protected int index ;
  // line 273 "../../../../IN_static.ump"
    protected boolean needToLatch ;
  // line 274 "../../../../IN_static.ump"
    protected Node child ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 289 "../../../../IN_static.ump"
  // line 4 "../../../../MemoryBudget_IN_inner.ump"
  // line 4 "../../../../INCompressor_IN_inner.ump"
  // line 5 "../../../../Latches_IN_inner.ump"
  // line 32 "../../../../LoggingFine_IN_inner.ump"
  public static class IN_splitInternal
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_splitInternal()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 291 "../../../../IN_static.ump"
    public  IN_splitInternal(IN _this, IN parent, int childIndex, int maxEntries, int splitIndex){
      this._this=_this;
          this.parent=parent;
          this.childIndex=childIndex;
          this.maxEntries=maxEntries;
          this.splitIndex=splitIndex;
    }
  
    // line 298 "../../../../IN_static.ump"
    public void execute() throws DatabaseException{
      if (_this.identifierKey == null) {
            throw new InconsistentNodeException("idkey is null");
          }
          idKeyIndex=_this.findEntry(_this.identifierKey,false,false);
          if (splitIndex < 0) {
            splitIndex=_this.nEntries / 2;
          }
    {
          }
          newSibling=null;
          if (idKeyIndearentLsn=DbLsn.NULL_LSN;
          newSibling=_this.createNewInstance(newIdKey,maxEntries,_this.level);
          Label631:
  newSibling.latch();
          //original();
   //this.hook631();
          oldMemorySize=_this.inMemorySize;
          //this.hook630();
          Label630:
  //     try {          //original();}//  finally {
            newSibling.releaseLatch();
      //    }
  x < splitIndex) {
            low=splitIndex;
            high=_this.nEntries;
          }
     else {
            low=0;
            high=splitIndex;
          }
          newIdKey=_this.entryKeyVals[low];
          p
          toIdx=0;
          deletedEntrySeen=false;
          binRef=null;
          for (int i=low; i < high; i++) {
            thisKey=_this.entryKeyVals[i];
            if (_this.isEntryPendingDeleted(i)) {
              if (!deletedEntrySeen) {
                deletedEntrySeen=true;
                binRef=new BINReference(newSibling.getNodeId(),_this.databaseImpl.getId(),newIdKey);
              }
              binRef.addDeletedKey(new Key(thisKey));
            }
            newSibling.setEntry(toIdx++,_this.entryTargets[i],thisKey,_this.getLsn(i),_this.entryStates[i]);
            _this.clearEntry(i);
          }
          Label636:
  if (deletedEntrySeen) {
            _this.databaseImpl.getDbEnvironment().addToCompressorQueue(binRef,false);
          }
  //        original();
   //this.hook636();
          newSiblingNEntries=(high - low);
          if (low == 0) {
            _this.shiftEntriesLeft(newSiblingNEntries);
          }
          newSibling.nEntries=toIdx;
          _this.nEntries-=newSiblingNEntries;
          _this.setDirty(true);
          _this.adjustCursors(newSibling,low,high);
          env=_this.databaseImpl.getDbEnvironment();
          logManager=env.getLogManager();
          inMemoryINs=env.getInMemoryINs();
          newSiblingLsn=newSibling.logProvisional(logManager,parent);
          myNewLsn=_this.logProvisional(logManager,parent);
          if (low == 0) {
            if (childIndex == 0) {
              parent.updateEntryCompareKey(childIndex,newSibling,newSiblingLsn,newIdKey);
            }
     else {
              parent.updateEntry(childIndex,newSibling,newSiblingLsn);
            }
            insertOk1=parent.insertEntry(new ChildReference(_this,_this.entryKeyVals[0],myNewLsn));
            assert insertOk1;
          }
     else {
            if (childIndex == 0) {
              parent.updateEntryCompareKey(childIndex,_this,myNewLsn,_this.entryKeyVals[0]);
            }
     else {
              parent.updateEntry(childIndex,_this,myNewLsn);
            }
            insertOk2=parent.insertEntry(new ChildReference(newSibling,newIdKey,newSiblingLsn));
            assert insertOk2;
          }
          parentLsn=parent.log(logManager);
          if (parent.isRoot()) {
            parent.setDirty(true);
          }
          //this.hook650();
          Label650:
  newSize=_this.computeMemorySize();
          _this.updateMemorySize(oldMemorySize,newSize);
          //original();
  
          inMemoryINs.add(newSibling);
          Label617:
  _this.traceSplit(Level.FINE,parent,newSibling,parentLsn,myNewLsn,newSiblingLsn,splitIndex,idKeyIndex,childIndex);
         // original();
   ;//this.hook617();
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 384 "../../../../IN_static.ump"
    protected IN _this ;
  // line 385 "../../../../IN_static.ump"
    protected IN parent ;
  // line 386 "../../../../IN_static.ump"
    protected int childIndex ;
  // line 387 "../../../../IN_static.ump"
    protected int maxEntries ;
  // line 388 "../../../../IN_static.ump"
    protected int splitIndex ;
  // line 389 "../../../../IN_static.ump"
    protected int idKeyIndex ;
  // line 390 "../../../../IN_static.ump"
    protected int low ;
  // line 391 "../../../../IN_static.ump"
    protected int high ;
  // line 392 "../../../../IN_static.ump"
    protected IN newSibling ;
  // line 393 "../../../../IN_static.ump"
    protected byte[] newIdKey ;
  // line 394 "../../../../IN_static.ump"
    protected long parentLsn ;
  // line 395 "../../../../IN_static.ump"
    protected long oldMemorySize ;
  // line 396 "../../../../IN_static.ump"
    protected int toIdx ;
  // line 397 "../../../../IN_static.ump"
    protected boolean deletedEntrySeen ;
  // line 398 "../../../../IN_static.ump"
    protected BINReference binRef ;
  // line 399 "../../../../IN_static.ump"
    protected byte[] thisKey ;
  // line 400 "../../../../IN_static.ump"
    protected int newSiblingNEntries ;
  // line 401 "../../../../IN_static.ump"
    protected EnvironmentImpl env ;
  // line 402 "../../../../IN_static.ump"
    protected LogManager logManager ;
  // line 403 "../../../../IN_static.ump"
    protected INList inMemoryINs ;
  // line 404 "../../../../IN_static.ump"
    protected long newSiblingLsn ;
  // line 405 "../../../../IN_static.ump"
    protected long myNewLsn ;
  // line 406 "../../../../IN_static.ump"
    protected boolean insertOk1 ;
  // line 407 "../../../../IN_static.ump"
    protected boolean insertOk2 ;
  // line 408 "../../../../IN_static.ump"
    protected long newSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 416 "../../../../IN_static.ump"
  // line 61 "../../../../Latches_IN_inner.ump"
  public static class IN_verify
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_verify()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 418 "../../../../IN_static.ump"
    public  IN_verify(IN _this, byte [] maxKey){
      this._this=_this;
          this.maxKey=maxKey;
    }
  
    // line 422 "../../../../IN_static.ump"
    public void execute() throws DatabaseException{
      // line 63 "../../../../Latches_IN_inner.ump"
      unlatchThis=false;
              //original();
      // END OF UMPLE BEFORE INJECTION
      try {
            Label632:
  if (!_this.isLatchOwner()) {
            _this.latch();
            unlatchThis=true;
          }
          //original();
   //this.hook632();
            userCompareToFcn=(_this.databaseImpl == null ? null : _this.getKeyComparator());
            key1=null;
            for (int i=1; i < _this.nEntries; i++) {
              key1=_this.entryKeyVals[i];
              key2=_this.entryKeyVals[i - 1];
              s=Key.compareKeys(key1,key2,userCompareToFcn);
              if (s <= 0) {
                throw new InconsistentNodeException("IN " + _this.getNodeId() + " key "+ (i - 1)+ " ("+ Key.dumpString(key2,0)+ ") and "+ i+ " ("+ Key.dumpString(key1,0)+ ") are out of order");
              }
            }
            inconsistent=false;
            if (maxKey != null && key1 != null) {
              if (Key.compareKeys(key1,maxKey,userCompareToFcn) >= 0) {
                inconsistent=true;
              }
            }
            if (inconsistent) {
              throw new InconsistentNodeException("IN " + _this.getNodeId() + " has entry larger than next entry in parent.");
            }
          }
     catch (      DatabaseException DE) {
            DE.printStackTrace(System.out);
          }
     finally {
            Label633:
  if (unlatchThis) {
            _this.releaseLatch();
          }
          //original();
   //this.hook633();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 451 "../../../../IN_static.ump"
    protected IN _this ;
  // line 452 "../../../../IN_static.ump"
    protected byte[] maxKey ;
  // line 453 "../../../../IN_static.ump"
    protected boolean unlatchThis ;
  // line 454 "../../../../IN_static.ump"
    protected Comparator userCompareToFcn ;
  // line 455 "../../../../IN_static.ump"
    protected byte[] key1 ;
  // line 456 "../../../../IN_static.ump"
    protected byte[] key2 ;
  // line 457 "../../../../IN_static.ump"
    protected int s ;
  // line 458 "../../../../IN_static.ump"
    protected boolean inconsistent ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 462 "../../../../IN_static.ump"
  // line 19 "../../../../Latches_IN_inner.ump"
  public static class IN_isValidForDelete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_isValidForDelete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 464 "../../../../IN_static.ump"
    public  IN_isValidForDelete(IN _this){
      this._this=_this;
    }
  
    // line 467 "../../../../IN_static.ump"
    public boolean execute() throws DatabaseException{
      try {
  				      Label634:
  needToLatch=!_this.isLatchOwner();
   //this.hook634();
  							Label635:
  if (needToLatch) {
            _this.latch();
          }
          //original();
   //this.hook635();
  						  if (_this.nEntries > 1) {
  						    throw new ReturnBoolean(false);
  						  }
  				 else       if (_this.nEntries == 1) {
  						    child=_this.fetchTarget(0);
  						    throw new ReturnBoolean(child != null && child.isValidForDelete());
  						  }
  				 else {
  						    throw new ReturnBoolean(true);
  						  }
  
  //end hook634
  
            throw ReturnHack.returnBoolean;
          }
     catch (      ReturnBoolean r) {
  Label634_1:
  //        try {          //original();} finally {
            if (needToLatch) {
              _this.releaseLatchIfOwner();
            }
    //      }
   
            return r.value;
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 490 "../../../../IN_static.ump"
    protected IN _this ;
  // line 491 "../../../../IN_static.ump"
    protected boolean needToLatch ;
  // line 492 "../../../../IN_static.ump"
    protected Node child ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 512 "../../../../IN_static.ump"
  // line 22 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_trackProvisionalObsolete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_trackProvisionalObsolete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 514 "../../../../IN_static.ump"
    public  IN_trackProvisionalObsolete(IN _this, IN child, long obsoleteLsn1, long obsoleteLsn2){
      this._this=_this;
          this.child=child;
          this.obsoleteLsn1=obsoleteLsn1;
          this.obsoleteLsn2=obsoleteLsn2;
    }
  
    // line 520 "../../../../IN_static.ump"
    public void execute(){
      memDelta=0;
          if (child.provisionalObsolete != null) {
            //this.hook652();
            Label652:
  childMemDelta=child.provisionalObsolete.size() * MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
          //original();
  
            if (_this.provisionalObsolete != null) {
              _this.provisionalObsolete.addAll(child.provisionalObsolete);
            }
     else {
              _this.provisionalObsolete=child.provisionalObsolete;
            }
            child.provisionalObsolete=null;
            //this.hook651();
            Label651:
  child.changeMemorySize(0 - childMemDelta);
          memDelta+=childMemDelta;
          //original();
  
          }
          if (obsoleteLsn1 != DbLsn.NULL_LSN || obsoleteLsn2 != DbLsn.NULL_LSN) {
            if (_this.provisionalObsolete == null) {
              _this.provisionalObsolete=new ArrayList();
            }
            if (obsoleteLsn1 != DbLsn.NULL_LSN) {
              _this.provisionalObsolete.add(new Long(obsoleteLsn1));
              //this.hook653();
              Label653:
  memDelta+=MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
          //original();
  
            }
            if (obsoleteLsn2 != DbLsn.NULL_LSN) {
              _this.provisionalObsolete.add(new Long(obsoleteLsn2));
              //this.hook654();
              Label654:
  memDelta+=MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
         // original();
  
            }
          }
      // line 24 "../../../../MemoryBudget_IN_inner.ump"
      //original();
              if (memDelta != 0) {
                _this.changeMemorySize(memDelta);
              }
      // END OF UMPLE AFTER INJECTION
    }
  
    // line 557 "../../../../IN_static.ump"
     protected void hook651(){
      
    }
  
  
    /**
     * protected void hook652(){
     * }
     */
    // line 561 "../../../../IN_static.ump"
     protected void hook653(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 550 "../../../../IN_static.ump"
    protected IN _this ;
  // line 551 "../../../../IN_static.ump"
    protected IN child ;
  // line 552 "../../../../IN_static.ump"
    protected long obsoleteLsn1 ;
  // line 553 "../../../../IN_static.ump"
    protected long obsoleteLsn2 ;
  // line 554 "../../../../IN_static.ump"
    protected int memDelta ;
  // line 555 "../../../../IN_static.ump"
    protected int childMemDelta ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 565 "../../../../IN_static.ump"
  // line 104 "../../../../MemoryBudget_IN_inner.ump"
  public static class IN_flushProvisionalObsolete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_flushProvisionalObsolete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 567 "../../../../IN_static.ump"
    public  IN_flushProvisionalObsolete(IN _this, LogManager logManager){
      this._this=_this;
          this.logManager=logManager;
    }
  
    // line 571 "../../../../IN_static.ump"
    public void execute() throws DatabaseException{
      if (_this.provisionalObsolete != null) {
            //this.hook656();
            Label656:
  memDelta=_this.provisionalObsolete.size() * MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
          //original();
  
            logManager.countObsoleteINs(_this.provisionalObsolete);
            _this.provisionalObsolete=null;
            //this.hook655();
            Label655:
  _this.changeMemorySize(0 - memDelta);
          //original();
  
          }
    }
  
    // line 584 "../../../../IN_static.ump"
     protected void hook655() throws DatabaseException{
      
    }
  
    // line 586 "../../../../IN_static.ump"
     protected void hook656() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 580 "../../../../IN_static.ump"
    protected IN _this ;
  // line 581 "../../../../IN_static.ump"
    protected LogManager logManager ;
  // line 582 "../../../../IN_static.ump"
    protected int memDelta ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../LoggingFine_IN_inner.ump"
  // line 4 "../../../../Derivative_LoggingFine_LoggingBase_IN_inner.ump"
  public static class IN_traceSplit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_traceSplit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../LoggingFine_IN_inner.ump"
    public  IN_traceSplit(IN _this, Level level, IN parent, IN newSibling, long parentLsn, long myNewLsn, long newSiblingLsn, int splitIndex, int idKeyIndex, int childIndex){
      this._this=_this;
          this.level=level;
          this.parent=parent;
          this.newSibling=newSibling;
          this.parentLsn=parentLsn;
          this.myNewLsn=myNewLsn;
          this.newSiblingLsn=newSiblingLsn;
          this.splitIndex=splitIndex;
          this.idKeyIndex=idKeyIndex;
          this.childIndex=childIndex;
    }
  
    // line 18 "../../../../LoggingFine_IN_inner.ump"
    public void execute(){
      // line 6 "../../../../Derivative_LoggingFine_LoggingBase_IN_inner.ump"
      logger=_this.databaseImpl.getDbEnvironment().getLogger();
              if (logger.isLoggable(level)) {
                sb=new StringBuffer();
                sb.append(_this.TRACE_SPLIT);
                sb.append(" parent=");
                sb.append(parent.getNodeId());
                sb.append(" child=");
                sb.append(_this.getNodeId());
                sb.append(" newSibling=");
                sb.append(newSibling.getNodeId());
                sb.append(" parentLsn = ");
                sb.append(DbLsn.getNoFormatString(parentLsn));
                sb.append(" childLsn = ");
                sb.append(DbLsn.getNoFormatString(myNewLsn));
                sb.append(" newSiblingLsn = ");
                sb.append(DbLsn.getNoFormatString(newSiblingLsn));
                sb.append(" splitIdx=");
                sb.append(splitIndex);
                sb.append(" idKeyIdx=");
                sb.append(idKeyIndex);
                sb.append(" childIdx=");
                sb.append(childIndex);
                logger.log(level,sb.toString());
              }
              //original();
      // END OF UMPLE BEFORE INJECTION
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 19 "../../../../LoggingFine_IN_inner.ump"
    protected IN _this ;
  // line 20 "../../../../LoggingFine_IN_inner.ump"
    protected Level level ;
  // line 21 "../../../../LoggingFine_IN_inner.ump"
    protected IN parent ;
  // line 22 "../../../../LoggingFine_IN_inner.ump"
    protected IN newSibling ;
  // line 23 "../../../../LoggingFine_IN_inner.ump"
    protected long parentLsn ;
  // line 24 "../../../../LoggingFine_IN_inner.ump"
    protected long myNewLsn ;
  // line 25 "../../../../LoggingFine_IN_inner.ump"
    protected long newSiblingLsn ;
  // line 26 "../../../../LoggingFine_IN_inner.ump"
    protected int splitIndex ;
  // line 27 "../../../../LoggingFine_IN_inner.ump"
    protected int idKeyIndex ;
  // line 28 "../../../../LoggingFine_IN_inner.ump"
    protected int childIndex ;
  // line 29 "../../../../LoggingFine_IN_inner.ump"
    protected Logger logger ;
  // line 30 "../../../../LoggingFine_IN_inner.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../LoggingFinest_IN_inner.ump"
  // line 4 "../../../../Derivative_LoggingFinest_LoggingBase_IN_inner.ump"
  public static class IN_traceDelete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_traceDelete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../LoggingFinest_IN_inner.ump"
    public  IN_traceDelete(IN _this, Level level, int index){
      this._this=_this;
          this.level=level;
          this.index=index;
    }
  
    // line 11 "../../../../LoggingFinest_IN_inner.ump"
    public void execute(){
      // line 6 "../../../../Derivative_LoggingFinest_LoggingBase_IN_inner.ump"
      logger=_this.databaseImpl.getDbEnvironment().getLogger();
              if (logger.isLoggable(level)) {
                sb=new StringBuffer();
                sb.append(_this.TRACE_DELETE);
                sb.append(" in=").append(_this.getNodeId());
                sb.append(" index=");
                sb.append(index);
                logger.log(level,sb.toString());
              }
              //original();
      // END OF UMPLE BEFORE INJECTION
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 12 "../../../../LoggingFinest_IN_inner.ump"
    protected IN _this ;
  // line 13 "../../../../LoggingFinest_IN_inner.ump"
    protected Level level ;
  // line 14 "../../../../LoggingFinest_IN_inner.ump"
    protected int index ;
  // line 15 "../../../../LoggingFinest_IN_inner.ump"
    protected Logger logger ;
  // line 16 "../../../../LoggingFinest_IN_inner.ump"
    protected StringBuffer sb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 34 "../../../../IN.ump"
  private static final String BEGIN_TAG = "<in>" ;
// line 36 "../../../../IN.ump"
  private static final String END_TAG = "</in>" ;
// line 38 "../../../../IN.ump"
  private static final String TRACE_SPLIT = "Split:" ;
// line 40 "../../../../IN.ump"
  private static final String TRACE_DELETE = "Delete:" ;
// line 42 "../../../../IN.ump"
  private static final byte KNOWN_DELETED_BIT = 0x1 ;
// line 44 "../../../../IN.ump"
  private static final byte CLEAR_KNOWN_DELETED_BIT = ~0x1 ;
// line 46 "../../../../IN.ump"
  private static final byte DIRTY_BIT = 0x2 ;
// line 48 "../../../../IN.ump"
  private static final byte CLEAR_DIRTY_BIT = ~0x2 ;
// line 50 "../../../../IN.ump"
  private static final byte MIGRATE_BIT = 0x4 ;
// line 52 "../../../../IN.ump"
  private static final byte CLEAR_MIGRATE_BIT = ~0x4 ;
// line 54 "../../../../IN.ump"
  private static final byte PENDING_DELETED_BIT = 0x8 ;
// line 56 "../../../../IN.ump"
  private static final byte CLEAR_PENDING_DELETED_BIT = ~0x8 ;
// line 58 "../../../../IN.ump"
  private static final int BYTES_PER_LSN_ENTRY = 4 ;
// line 60 "../../../../IN.ump"
  private static final int MAX_FILE_OFFSET = 0xfffffe ;
// line 62 "../../../../IN.ump"
  private static final int THREE_BYTE_NEGATIVE_ONE = 0xffffff ;
// line 64 "../../../../IN.ump"
  private static final int GROWTH_INCREMENT = 5 ;
// line 66 "../../../../IN.ump"
  public static final int DBMAP_LEVEL = 0x20000 ;
// line 68 "../../../../IN.ump"
  public static final int MAIN_LEVEL = 0x10000 ;
// line 70 "../../../../IN.ump"
  public static final int LEVEL_MASK = 0x0ffff ;
// line 72 "../../../../IN.ump"
  public static final int MIN_LEVEL = -1 ;
// line 74 "../../../../IN.ump"
  public static final int MAX_LEVEL = Integer.MAX_VALUE ;
// line 76 "../../../../IN.ump"
  public static final int BIN_LEVEL = MAIN_LEVEL | 1 ;
// line 78 "../../../../IN.ump"
  private long generation ;
// line 80 "../../../../IN.ump"
  private boolean dirty ;
// line 82 "../../../../IN.ump"
  private int nEntries ;
// line 84 "../../../../IN.ump"
  private byte[] identifierKey ;
// line 86 "../../../../IN.ump"
  private Node[] entryTargets ;
// line 88 "../../../../IN.ump"
  private byte[][] entryKeyVals ;
// line 90 "../../../../IN.ump"
  private long baseFileNumber ;
// line 92 "../../../../IN.ump"
  private byte[] entryLsnByteArray ;
// line 94 "../../../../IN.ump"
  private long[] entryLsnLongArray ;
// line 96 "../../../../IN.ump"
  private byte[] entryStates ;
// line 98 "../../../../IN.ump"
  private DatabaseImpl databaseImpl ;
// line 100 "../../../../IN.ump"
  private boolean isRoot ;
// line 102 "../../../../IN.ump"
  private int level ;
// line 104 "../../../../IN.ump"
  private long inMemorySize ;
// line 106 "../../../../IN.ump"
  private long lastFullVersion = DbLsn.NULL_LSN ;
// line 108 "../../../../IN.ump"
  private List provisionalObsolete ;
// line 110 "../../../../IN.ump"
  public static final int EXACT_MATCH = (1 << 16) ;
// line 112 "../../../../IN.ump"
  public static final int INSERT_SUCCESS = (1 << 17) ;
// line 114 "../../../../IN.ump"
  public static int ACCUMULATED_LIMIT = 1000 ;

// line 1049 "../../../../IN.ump"
  void findParent (Tree.SearchType searchType, long targetNodeId, boolean targetContainsDuplicates,
	    boolean targetIsRoot, byte[] targetMainTreeKey, byte[] targetDupTreeKey, SearchResult result,
	    boolean requireExactMatch, boolean updateGeneration, int targetLevel, List trackingList, boolean doFetch)
	    throws DatabaseException 
  {
    if (getNodeId() == targetNodeId) {
	    Label620://this.hook620();
	    result.exactParentFound = false;
	    result.keepSearching = false;
	    result.parent = null;
	    return;
	}
	if (getNEntries() == 0) {
	    result.keepSearching = false;
	    result.exactParentFound = false;
	    if (requireExactMatch) {
		Label621://this.hook621();
		result.parent = null;
	    } else {
		result.parent = this;
		result.index = -1;
	    }
	    return;
	} else {
	    if (searchType == Tree.SearchType.NORMAL) {
		result.index = findEntry(selectKey(targetMainTreeKey, targetDupTreeKey), false, false);
	    } else if (searchType == Tree.SearchType.LEFT) {
		result.index = 0;
	    } else if (searchType == Tree.SearchType.RIGHT) {
		result.index = nEntries - 1;
	    } else {
		throw new IllegalArgumentException("Invalid value of searchType: " + searchType);
	    }
	    if (result.index < 0) {
		result.keepSearching = false;
		result.exactParentFound = false;
		if (requireExactMatch) {
		    Label622://this.hook622();
		    result.parent = null;
		} else {
		    result.parent = this;
		}
		return;
	    }
	    Node child = null;
	    boolean isDeleted = false;
	    if (isEntryKnownDeleted(result.index)) {
		isDeleted = true;
	    } else if (doFetch) {
		child = fetchTarget(result.index);
		if (child == null) {
		    isDeleted = true;
		}
	    } else {
		child = getTarget(result.index);
	    }
	    if (isDeleted) {
		result.exactParentFound = false;
		result.keepSearching = false;
		if (requireExactMatch) {
		    result.parent = null;
		    Label623://this.hook623();
		} else {
		    result.parent = this;
		}
		return;
	    }
	    if (targetLevel >= 0 && level == targetLevel + 1) {
		result.exactParentFound = true;
		result.parent = this;
		result.keepSearching = false;
		return;
	    }
	    if (child == null) {
		assert !doFetch;
		result.keepSearching = false;
		result.exactParentFound = false;
		result.parent = this;
		result.childNotResident = true;
		return;
	    }
	    long childLsn = getLsn(result.index);
	    if (child.isSoughtNode(targetNodeId, updateGeneration)) {
		result.exactParentFound = true;
		result.parent = this;
		result.keepSearching = false;
		return;
	    } else {
		descendOnParentSearch(result, targetContainsDuplicates, targetIsRoot, targetNodeId, child,
			requireExactMatch);
		if (trackingList != null) {
		    if ((result.parent != this) && (result.parent != null)) {
			trackingList.add(new TrackingInfo(childLsn, child.getNodeId()));
		    }
		}
		return;
	    }
	}
  }
// line 5 "../../../../MemoryBudget_IN.ump"
  private boolean inListResident ;
// line 7 "../../../../MemoryBudget_IN.ump"
  private int accumulatedDelta = 0 ;
// line 5 "../../../../Evictor_IN.ump"
  public static final int MAY_NOT_EVICT = 0 ;
// line 7 "../../../../Evictor_IN.ump"
  public static final int MAY_EVICT_LNS = 1 ;
// line 9 "../../../../Evictor_IN.ump"
  public static final int MAY_EVICT_NODE = 2 ;
// line 8 "../../../../Latches_IN.ump"
  private Latch latch ;

// line 45 "../../../../Latches_IN.ump"
  protected void hook618: init (DatabaseImpl , byte , int , int ) 
  {
    latch = LatchSupport.makeLatch(shortClassName() + getNodeId(), env);
	//original(env);
  }

// line 59 "../../../../Latches_IN.ump"
  protected void hook619: latchNoWait (boolean ) 
  {
    //if (latch.acquireNoWait()) {
	    //original(updateGeneration);
	//} else {
	    if(! latch.acquireNoWait()) 
        throw new ReturnBoolean(false);
	//}
  }

// line 79 "../../../../Latches_IN.ump"
  protected void hook620: findParent (Tree.SearchType , long , boolean , boolean , byte , byte , SearchResult , boolean , boolean , int , List , boolean ) 
  {
    releaseLatch();
	//original();
  }

// line 84 "../../../../Latches_IN.ump"
  protected void hook621: findParent (Tree.SearchType , long , boolean , boolean , byte , byte , SearchResult , boolean , boolean , int , List , boolean ) 
  {
    releaseLatch();
	//original();
  }

// line 89 "../../../../Latches_IN.ump"
  protected void hook622: findParent (Tree.SearchType , long , boolean , boolean , byte , byte , SearchResult , boolean , boolean , int , List , boolean ) 
  {
    releaseLatch();
	//original();
  }

// line 94 "../../../../Latches_IN.ump"
  protected void hook623: findParent (Tree.SearchType , long , boolean , boolean , byte , byte , SearchResult , boolean , boolean , int , List , boolean ) 
  {
    releaseLatch();
	//original();
  }

// line 99 "../../../../Latches_IN.ump"
  protected void hook624:  descendOnParentSearch (SearchResult , boolean , boolean , long , Node , boolean ) 
  {
    releaseLatch();
	//original();
  }

// line 105 "../../../../Latches_IN.ump"
  protected void hook625:  descendOnParentSearch (SearchResult , boolean , boolean , long , Node , boolean ) 
  {
    ((IN) child).releaseLatch();
	//original(child);
  }

// line 111 "../../../../Latches_IN.ump"
  protected void hook626:  descendOnParentSearch (SearchResult , boolean , boolean , long , Node , boolean ) 
  {
    releaseLatch();
	//original();
  }

// line 116 "../../../../Latches_IN.ump"
  protected void hook627: isSoughtNode (long , boolean ) 
  {
    releaseLatch();
	//original();
  }

  
}