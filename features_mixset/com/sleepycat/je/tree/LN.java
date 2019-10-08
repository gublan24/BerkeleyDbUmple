/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.txn.WriteLockInfo;
import com.sleepycat.je.txn.Txn;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.log.entry.DeletedDupLNLogEntry;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.INList;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import java.util.Map;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../LN.ump"
// line 3 "../../../../MemoryBudget_LN.ump"
public class LN extends Node implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LN()
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
   * Create an empty LN, to be filled in from the log.
   */
  // line 40 "../../../../LN.ump"
   public  LN(){
    super(false);
	this.data = null;
  }


  /**
   * 
   * Create a new LN from a byte array.
   */
  // line 48 "../../../../LN.ump"
   public  LN(byte [] data){
    super(true);
	if (data == null) {
	    this.data = null;
	} else {
	    init(data, 0, data.length);
	}
  }


  /**
   * 
   * Create a new LN from a DatabaseEntry.
   */
  // line 60 "../../../../LN.ump"
   public  LN(DatabaseEntry dbt){
    super(true);
	byte[] data = dbt.getData();
	if (data == null) {
	    this.data = null;
	} else if (dbt.getPartial()) {
	    init(data, dbt.getOffset(), dbt.getPartialOffset() + dbt.getSize(), dbt.getPartialOffset(), dbt.getSize());
	} else {
	    init(data, dbt.getOffset(), dbt.getSize());
	}
  }

  // line 72 "../../../../LN.ump"
   private void init(byte [] data, int off, int len, int doff, int dlen){
    if (len == 0) {
	    this.data = LogUtils.ZERO_LENGTH_BYTE_ARRAY;
	} else {
	    this.data = new byte[len];
	    System.arraycopy(data, off, this.data, doff, dlen);
	}
  }

  // line 81 "../../../../LN.ump"
   private void init(byte [] data, int off, int len){
    init(data, off, len, 0, len);
  }

  // line 85 "../../../../LN.ump"
   public byte[] getData(){
    return data;
  }

  // line 89 "../../../../LN.ump"
   public byte[] copyData(){
    int len = data.length;
	byte[] ret = new byte[len];
	System.arraycopy(data, 0, ret, 0, len);
	return ret;
  }

  // line 96 "../../../../LN.ump"
   public boolean isDeleted(){
    return (data == null);
  }

  // line 100 "../../../../LN.ump"
  public void makeDeleted(){
    data = null;
  }

  // line 104 "../../../../LN.ump"
  public boolean isValidForDelete(){
    return false;
  }


  /**
   * 
   * A LN can never be a child in the search chain.
   */
  // line 111 "../../../../LN.ump"
   protected boolean isSoughtNode(long nid, boolean updateGeneration){
    return false;
  }


  /**
   * 
   * A LN can never be the ancestor of another node.
   */
  // line 118 "../../../../LN.ump"
   protected boolean canBeAncestor(boolean targetContainsDuplicates){
    return false;
  }


  /**
   * 
   * Delete this LN's data and log the new version.
   */
  // line 126 "../../../../LN.ump"
   public long delete(DatabaseImpl database, byte [] lnKey, byte [] dupKey, long oldLsn, Locker locker) throws DatabaseException{
    makeDeleted();
	EnvironmentImpl env = database.getDbEnvironment();
	long newLsn = DbLsn.NULL_LSN;
	if (dupKey != null) {
	    LogEntryType entryType;
	    long logAbortLsn;
	    boolean logAbortKnownDeleted;
	    Txn logTxn;
	    if (locker.isTransactional()) {
		entryType = LogEntryType.LOG_DEL_DUPLN_TRANSACTIONAL;
		WriteLockInfo info = locker.getWriteLockInfo(getNodeId());
		logAbortLsn = info.getAbortLsn();
		logAbortKnownDeleted = info.getAbortKnownDeleted();
		logTxn = locker.getTxnLocker();
	    } else {
		entryType = LogEntryType.LOG_DEL_DUPLN;
		logAbortLsn = DbLsn.NULL_LSN;
		logAbortKnownDeleted = true;
		logTxn = null;
	    }
	    if (oldLsn == logAbortLsn) {
		oldLsn = DbLsn.NULL_LSN;
	    }
	    DeletedDupLNLogEntry logEntry = new DeletedDupLNLogEntry(entryType, this, database.getId(), dupKey, lnKey,
		    logAbortLsn, logAbortKnownDeleted, logTxn);
	    LogManager logManager = env.getLogManager();
	    newLsn = logManager.log(logEntry, false, oldLsn);
	} else {
	    newLsn = log(env, database.getId(), lnKey, oldLsn, locker);
	}
	return newLsn;
  }


  /**
   * 
   * Modify the LN's data and log the new version.
   */
  // line 164 "../../../../LN.ump"
   public long modify(byte [] newData, DatabaseImpl database, byte [] lnKey, long oldLsn, Locker locker) throws DatabaseException{
    data = newData;
	EnvironmentImpl env = database.getDbEnvironment();
	long newLsn = log(env, database.getId(), lnKey, oldLsn, locker);
	return newLsn;
  }


  /**
   * 
   * Add yourself to the dirty list if you're dirty. LNs are never dirty.
   */
  // line 174 "../../../../LN.ump"
  public void addToDirtyMap(Map dirtyMap){
    
  }


  /**
   * 
   * Add yourself to the in memory list if you're a type of node that should belong.
   */
  // line 180 "../../../../LN.ump"
  public void rebuildINList(INList inList){
    
  }


  /**
   * 
   * No need to do anything, stop the search.
   */
  // line 186 "../../../../LN.ump"
  public void accountForSubtreeRemoval(INList inList, UtilizationTracker tracker){
    
  }

  // line 189 "../../../../LN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 193 "../../../../LN.ump"
   public String endTag(){
    return END_TAG;
  }

  // line 197 "../../../../LN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer self = new StringBuffer();
	if (dumpTags) {
	    self.append(TreeUtils.indent(nSpaces));
	    self.append(beginTag());
	    self.append('\n');
	}
	self.append(super.dumpString(nSpaces + 2, true));
	self.append('\n');
	if (data != null) {
	    self.append(TreeUtils.indent(nSpaces + 2));
	    self.append("<data>");
	    self.append(TreeUtils.dumpByteArray(data));
	    self.append("</data>");
	    self.append('\n');
	}
	if (dumpTags) {
	    self.append(TreeUtils.indent(nSpaces));
	    self.append(endTag());
	}
	return self.toString();
  }


  /**
   * 
   * Log a provisional, non-txnal version of a ln.
   * @param env the environment.
   * @param dbId database id of this node. (Not stored in LN)
   * @param key key of this node. (Not stored in LN)
   * @param oldLsn is the LSN of the previous version or null.
   */
  // line 227 "../../../../LN.ump"
   public long logProvisional(EnvironmentImpl env, DatabaseId dbId, byte [] key, long oldLsn) throws DatabaseException{
    return log(env, dbId, key, oldLsn, null, true);
  }


  /**
   * 
   * Log this LN. Whether its logged as a transactional entry or not depends on the type of locker.
   * @param env the environment.
   * @param dbId database id of this node. (Not stored in LN)
   * @param key key of this node. (Not stored in LN)
   * @param oldLsn is the LSN of the previous version or null.
   * @param locker owning locker.
   */
  // line 240 "../../../../LN.ump"
   public long log(EnvironmentImpl env, DatabaseId dbId, byte [] key, long oldLsn, Locker locker) throws DatabaseException{
    return log(env, dbId, key, oldLsn, locker, false);
  }


  /**
   * 
   * Log this LN. Whether its logged as a transactional entry or not depends on the type of locker.
   * @param env the environment.
   * @param dbId database id of this node. (Not stored in LN)
   * @param key key of this node. (Not stored in LN)
   * @param oldLsn is the LSN of the previous version or null.
   * @param locker owning locker.
   */
  // line 253 "../../../../LN.ump"
   private long log(EnvironmentImpl env, DatabaseId dbId, byte [] key, long oldLsn, Locker locker, boolean isProvisional) throws DatabaseException{
    LogEntryType entryType;
	long logAbortLsn;
	boolean logAbortKnownDeleted;
	Txn logTxn;
	if (locker != null && locker.isTransactional()) {
	    entryType = getTransactionalLogType();
	    WriteLockInfo info = locker.getWriteLockInfo(getNodeId());
	    logAbortLsn = info.getAbortLsn();
	    logAbortKnownDeleted = info.getAbortKnownDeleted();
	    logTxn = locker.getTxnLocker();
	    assert logTxn != null;
	} else {
	    entryType = getLogType();
	    logAbortLsn = DbLsn.NULL_LSN;
	    logAbortKnownDeleted = false;
	    logTxn = null;
	}
	if (oldLsn == logAbortLsn) {
	    oldLsn = DbLsn.NULL_LSN;
	}
	LNLogEntry logEntry = new LNLogEntry(entryType, this, dbId, key, logAbortLsn, logAbortKnownDeleted, logTxn);
	LogManager logManager = env.getLogManager();
	return logManager.log(logEntry, isProvisional, oldLsn);
  }


  /**
   * 
   * Log type for transactional entries
   */
  // line 282 "../../../../LN.ump"
   protected LogEntryType getTransactionalLogType(){
    return LogEntryType.LOG_LN_TRANSACTIONAL;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 289 "../../../../LN.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 296 "../../../../LN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_LN;
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 303 "../../../../LN.ump"
   public int getLogSize(){
    int size = super.getLogSize();
	size += LogUtils.getBooleanLogSize();
	if (!isDeleted()) {
	    size += LogUtils.getByteArrayLogSize(data);
	}
	return size;
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 315 "../../../../LN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	boolean dataExists = !isDeleted();
	LogUtils.writeBoolean(logBuffer, dataExists);
	if (dataExists) {
	    LogUtils.writeByteArray(logBuffer, data);
	}
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 327 "../../../../LN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	boolean dataExists = LogUtils.readBoolean(itemBuffer);
	if (dataExists) {
	    data = LogUtils.readByteArray(itemBuffer);
	}
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 338 "../../../../LN.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append(beginTag());
	super.dumpLog(sb, verbose);
	if (data != null) {
	    sb.append("<data>");
	    sb.append(TreeUtils.dumpByteArray(data));
	    sb.append("</data>");
	}
	dumpLogAdditional(sb, verbose);
	sb.append(endTag());
  }


  /**
   * 
   * Never called.
   * @see LogReadable#logEntryIsTransactional.
   */
  // line 354 "../../../../LN.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * Never called.
   * @see LogReadable#getTransactionId
   */
  // line 362 "../../../../LN.ump"
   public long getTransactionId(){
    return 0;
  }

  // line 366 "../../../../LN.ump"
   protected void dumpLogAdditional(StringBuffer sb, boolean verbose){
    
  }


  /**
   * 
   * Compute the approximate size of this node in memory for evictor invocation purposes.
   */
  // line 9 "../../../../MemoryBudget_LN.ump"
   public long getMemorySizeIncludedByParent(){
    int size = MemoryBudget.LN_OVERHEAD;
		if (data != null) {
			  size += MemoryBudget.byteArraySize(data.length);
		}
		return size;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 30 "../../../../LN.ump"
  private static final String BEGIN_TAG = "<ln>" ;
// line 32 "../../../../LN.ump"
  private static final String END_TAG = "</ln>" ;
// line 34 "../../../../LN.ump"
  private byte[] data ;

  
}