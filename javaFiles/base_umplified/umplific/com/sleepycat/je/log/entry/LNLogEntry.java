/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log.entry;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.txn.Txn;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.Key;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../../LNLogEntry.ump"
public class LNLogEntry implements LogEntry,LoggableObject,NodeLogEntry
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LNLogEntry()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 40 "../../../../../LNLogEntry.ump"
   public  LNLogEntry(Class logClass, boolean isTransactional){
    this.logClass = logClass;
	this.isTransactional = isTransactional;
  }

  // line 46 "../../../../../LNLogEntry.ump"
   public  LNLogEntry(LogEntryType entryType, LN ln, DatabaseId dbId, byte [] key, long abortLsn, boolean abortKnownDeleted, Txn txn){
    this.entryType = entryType;
	this.ln = ln;
	this.dbId = dbId;
	this.key = key;
	this.abortLsn = abortLsn;
	this.abortKnownDeleted = abortKnownDeleted;
	this.txn = txn;
	this.isTransactional = (txn != null);
	this.logClass = ln.getClass();
	this.nodeId = ln.getNodeId();
  }


  /**
   * 
   * @see LogEntry#readEntry
   */
  // line 63 "../../../../../LNLogEntry.ump"
   public void readEntry(ByteBuffer entryBuffer, int entrySize, byte entryTypeVersion, boolean readFullItem) throws DatabaseException{
    try {
	    if (readFullItem) {
		ln = (LN) logClass.newInstance();
		ln.readFromLog(entryBuffer, entryTypeVersion);
		nodeId = ln.getNodeId();
		dbId = new DatabaseId();
		dbId.readFromLog(entryBuffer, entryTypeVersion);
		key = LogUtils.readByteArray(entryBuffer);
		if (isTransactional) {
		    abortLsn = LogUtils.readLong(entryBuffer);
		    if (DbLsn.getFileNumber(abortLsn) == DbLsn.getFileNumber(DbLsn.NULL_LSN)) {
			abortLsn = DbLsn.NULL_LSN;
		    }
		    abortKnownDeleted = ((entryBuffer.get() & ABORT_KNOWN_DELETED_MASK) != 0) ? true : false;
		    txn = new Txn();
		    txn.readFromLog(entryBuffer, entryTypeVersion);
		}
	    } else {
		int endPosition = entryBuffer.position() + entrySize;
		nodeId = LogUtils.readLong(entryBuffer);
		entryBuffer.position(endPosition);
		ln = null;
	    }
	} catch (IllegalAccessException e) {
	    throw new DatabaseException(e);
	} catch (InstantiationException e) {
	    throw new DatabaseException(e);
	}
  }


  /**
   * 
   * @see LogEntry#dumpEntry
   */
  // line 97 "../../../../../LNLogEntry.ump"
   public StringBuffer dumpEntry(StringBuffer sb, boolean verbose){
    ln.dumpLog(sb, verbose);
	dbId.dumpLog(sb, verbose);
	sb.append(Key.dumpString(key, 0));
	if (isTransactional) {
	    if (abortLsn != DbLsn.NULL_LSN) {
		sb.append(DbLsn.toString(abortLsn));
	    }
	    sb.append("<knownDeleted val=\"");
	    sb.append(abortKnownDeleted ? "true" : "false");
	    sb.append("\"/>");
	    txn.dumpLog(sb, verbose);
	}
	return sb;
  }


  /**
   * 
   * @see LogEntry#getMainItem
   */
  // line 116 "../../../../../LNLogEntry.ump"
   public Object getMainItem(){
    return ln;
  }


  /**
   * 
   * @see LogEntry#clone
   */
  // line 123 "../../../../../LNLogEntry.ump"
   public Object clone() throws CloneNotSupportedException{
    return super.clone();
  }


  /**
   * 
   * @see LogEntry#isTransactional
   */
  // line 130 "../../../../../LNLogEntry.ump"
   public boolean isTransactional(){
    return isTransactional;
  }


  /**
   * 
   * @see LogEntry#getTransactionId
   */
  // line 137 "../../../../../LNLogEntry.ump"
   public long getTransactionId(){
    if (isTransactional) {
	    return txn.getId();
	} else {
	    return 0;
	}
  }


  /**
   * 
   * @see NodeLogEntry#getNodeId
   */
  // line 148 "../../../../../LNLogEntry.ump"
   public long getNodeId(){
    return nodeId;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 155 "../../../../../LNLogEntry.ump"
   public LogEntryType getLogType(){
    return entryType;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchAsk the ln if it can be marshalled outside the log write latch.
   */
  // line 162 "../../../../../LNLogEntry.ump"
   public boolean marshallOutsideWriteLatch(){
    return ln.marshallOutsideWriteLatch();
  }


  /**
   * 
   * Returns true for a deleted LN to count it immediately as obsolete.
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 170 "../../../../../LNLogEntry.ump"
   public boolean countAsObsoleteWhenLogged(){
    return ln.isDeleted();
  }


  /**
   * 
   * For LN entries, we need to record the latest LSN for that node with the owning transaction, within the protection of the log latch. This is a callback for the log manager to do that recording.
   * @see LoggableObject#postLogWork
   */
  // line 178 "../../../../../LNLogEntry.ump"
   public void postLogWork(long justLoggedLsn) throws DatabaseException{
    if (isTransactional) {
	    txn.addLogInfo(justLoggedLsn);
	}
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 187 "../../../../../LNLogEntry.ump"
   public int getLogSize(){
    int size = ln.getLogSize() + dbId.getLogSize() + LogUtils.getByteArrayLogSize(key);
	if (isTransactional) {
	    size += LogUtils.getLongLogSize();
	    size++;
	    size += txn.getLogSize();
	}
	return size;
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 200 "../../../../../LNLogEntry.ump"
   public void writeToLog(ByteBuffer destBuffer){
    ln.writeToLog(destBuffer);
	dbId.writeToLog(destBuffer);
	LogUtils.writeByteArray(destBuffer, key);
	if (isTransactional) {
	    LogUtils.writeLong(destBuffer, abortLsn);
	    byte aKD = 0;
	    if (abortKnownDeleted) {
		aKD |= ABORT_KNOWN_DELETED_MASK;
	    }
	    destBuffer.put(aKD);
	    txn.writeToLog(destBuffer);
	}
  }

  // line 215 "../../../../../LNLogEntry.ump"
   public LN getLN(){
    return ln;
  }

  // line 219 "../../../../../LNLogEntry.ump"
   public DatabaseId getDbId(){
    return dbId;
  }

  // line 223 "../../../../../LNLogEntry.ump"
   public byte[] getKey(){
    return key;
  }

  // line 227 "../../../../../LNLogEntry.ump"
   public byte[] getDupKey(){
    if (ln.isDeleted()) {
	    return null;
	} else {
	    return ln.getData();
	}
  }

  // line 235 "../../../../../LNLogEntry.ump"
   public long getAbortLsn(){
    return abortLsn;
  }

  // line 239 "../../../../../LNLogEntry.ump"
   public boolean getAbortKnownDeleted(){
    return abortKnownDeleted;
  }

  // line 243 "../../../../../LNLogEntry.ump"
   public Long getTxnId(){
    if (isTransactional) {
	    return new Long(txn.getId());
	} else {
	    return null;
	}
  }

  // line 251 "../../../../../LNLogEntry.ump"
   public Txn getUserTxn(){
    if (isTransactional) {
	    return txn;
	} else {
	    return null;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../../../../LNLogEntry.ump"
  private LN ln ;
// line 19 "../../../../../LNLogEntry.ump"
  private DatabaseId dbId ;
// line 21 "../../../../../LNLogEntry.ump"
  private byte[] key ;
// line 23 "../../../../../LNLogEntry.ump"
  private long abortLsn = DbLsn.NULL_LSN ;
// line 25 "../../../../../LNLogEntry.ump"
  private boolean abortKnownDeleted ;
// line 27 "../../../../../LNLogEntry.ump"
  private Txn txn ;
// line 29 "../../../../../LNLogEntry.ump"
  private static final byte ABORT_KNOWN_DELETED_MASK = (byte) 1 ;
// line 31 "../../../../../LNLogEntry.ump"
  private Class logClass ;
// line 33 "../../../../../LNLogEntry.ump"
  private LogEntryType entryType ;
// line 35 "../../../../../LNLogEntry.ump"
  private long nodeId ;
// line 37 "../../../../../LNLogEntry.ump"
  private boolean isTransactional ;

  
}