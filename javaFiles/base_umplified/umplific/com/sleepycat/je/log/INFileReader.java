/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.tree.MapLN;
import com.sleepycat.je.tree.INDupDeleteInfo;
import com.sleepycat.je.tree.INDeleteInfo;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.FileSummaryLN;
import com.sleepycat.je.log.entry.NodeLogEntry;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.log.entry.INLogEntry;
import com.sleepycat.je.log.entry.INContainingEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.cleaner.TrackedFileSummary;
import com.sleepycat.je.DatabaseException;
import java.util.Map;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../INFileReader.ump"
public class INFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INFileReader()
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
   * Create this reader to start at a given LSN.
   */
  // line 77 "../../../../INFileReader.ump"
   public  INFileReader(EnvironmentImpl env, int readBufferSize, long startLsn, long finishLsn, boolean trackIds, boolean mapDbOnly, long partialCkptStart, Map fileSummaryLsns) throws IOException,DatabaseException{
    super(env, readBufferSize, true, startLsn, null, DbLsn.NULL_LSN, finishLsn);
	this.trackIds = trackIds;
	this.mapDbOnly = mapDbOnly;
	targetEntryMap = new HashMap();
	if (trackIds) {
	    maxNodeId = 0;
	    maxDbId = 0;
	    tracker = env.getUtilizationTracker();
	    this.partialCkptStart = partialCkptStart;
	    this.fileSummaryLsns = fileSummaryLsns;
	    fsTrackingEntry = (LNLogEntry) LogEntryType.LOG_FILESUMMARYLN.getNewLogEntry();
	    dbIdTrackingMap = new HashMap();
	    txnIdTrackingMap = new HashMap();
	    otherNodeTrackingMap = new HashMap();
	    dbIdTrackingMap.put(LogEntryType.LOG_MAPLN_TRANSACTIONAL,
		    LogEntryType.LOG_MAPLN_TRANSACTIONAL.getNewLogEntry());
	    dbIdTrackingMap.put(LogEntryType.LOG_MAPLN, LogEntryType.LOG_MAPLN.getNewLogEntry());
	    txnIdTrackingMap.put(LogEntryType.LOG_LN_TRANSACTIONAL, LogEntryType.LOG_LN_TRANSACTIONAL.getNewLogEntry());
	    txnIdTrackingMap.put(LogEntryType.LOG_MAPLN_TRANSACTIONAL,
		    LogEntryType.LOG_MAPLN_TRANSACTIONAL.getNewLogEntry());
	    txnIdTrackingMap.put(LogEntryType.LOG_NAMELN_TRANSACTIONAL,
		    LogEntryType.LOG_NAMELN_TRANSACTIONAL.getNewLogEntry());
	    txnIdTrackingMap.put(LogEntryType.LOG_DEL_DUPLN_TRANSACTIONAL,
		    LogEntryType.LOG_DEL_DUPLN_TRANSACTIONAL.getNewLogEntry());
	    txnIdTrackingMap.put(LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL,
		    LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL.getNewLogEntry());
	}
  }


  /**
   * 
   * Configure this reader to target this kind of entry.
   */
  // line 110 "../../../../INFileReader.ump"
   public void addTargetType(LogEntryType entryType) throws DatabaseException{
    targetEntryMap.put(entryType, entryType.getNewLogEntry());
  }


  /**
   * 
   * If we're tracking node, database and txn ids, we want to see all node log entries. If not, we only want to see IN entries.
   * @return true if this is an IN entry.
   */
  // line 118 "../../../../INFileReader.ump"
   protected boolean isTargetEntry(byte entryTypeNum, byte entryTypeVersion) throws DatabaseException{
    lastEntryWasDelete = false;
	lastEntryWasDupDelete = false;
	targetLogEntry = null;
	dbIdTrackingEntry = null;
	txnIdTrackingEntry = null;
	nodeTrackingEntry = null;
	inTrackingEntry = null;
	fsTrackingEntry = null;
	isProvisional = LogEntryType.isProvisional(entryTypeVersion);
	fromLogType = LogEntryType.findType(entryTypeNum, entryTypeVersion);
	LogEntry possibleTarget = (LogEntry) targetEntryMap.get(fromLogType);
	if (!isProvisional) {
	    targetLogEntry = possibleTarget;
	}
	if (LogEntryType.LOG_IN_DELETE_INFO.equals(fromLogType)) {
	    lastEntryWasDelete = true;
	}
	if (LogEntryType.LOG_IN_DUPDELETE_INFO.equals(fromLogType)) {
	    lastEntryWasDupDelete = true;
	}
	if (trackIds) {
	    if (!isProvisional) {
		dbIdTrackingEntry = (LNLogEntry) dbIdTrackingMap.get(fromLogType);
		txnIdTrackingEntry = (LNLogEntry) txnIdTrackingMap.get(fromLogType);
	    }
	    if (fromLogType.isNodeType()) {
		if (possibleTarget != null) {
		    nodeTrackingEntry = (NodeLogEntry) possibleTarget;
		} else if (dbIdTrackingEntry != null) {
		    nodeTrackingEntry = dbIdTrackingEntry;
		} else if (txnIdTrackingEntry != null) {
		    nodeTrackingEntry = txnIdTrackingEntry;
		} else {
		    nodeTrackingEntry = (NodeLogEntry) otherNodeTrackingMap.get(fromLogType);
		    if (nodeTrackingEntry == null) {
			nodeTrackingEntry = (NodeLogEntry) fromLogType.getNewLogEntry();
			otherNodeTrackingMap.put(fromLogType, nodeTrackingEntry);
		    }
		}
		if (nodeTrackingEntry instanceof INLogEntry) {
		    inTrackingEntry = (INLogEntry) nodeTrackingEntry;
		}
		if (LogEntryType.LOG_FILESUMMARYLN.equals(fromLogType)) {
		    fsTrackingEntry = (LNLogEntry) nodeTrackingEntry;
		}
	    }
	    tracker.countNewLogEntry(getLastLsn(), fromLogType, LogManager.HEADER_BYTES + currentEntrySize);
	    return (targetLogEntry != null) || (dbIdTrackingEntry != null) || (txnIdTrackingEntry != null)
		    || (nodeTrackingEntry != null);
	} else {
	    return (targetLogEntry != null);
	}
  }


  /**
   * 
   * This reader looks at all nodes for the max node id and database id. It only returns non-provisional INs and IN delete entries.
   */
  // line 176 "../../../../INFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    boolean useEntry = false;
	boolean entryLoaded = false;
	if (targetLogEntry != null) {
	    targetLogEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
	    DatabaseId dbId = getDatabaseId();
	    boolean isMapDb = dbId.equals(DbTree.ID_DB_ID);
	    useEntry = (!mapDbOnly || isMapDb);
	    entryLoaded = true;
	}
	if (trackIds) {
	    LNLogEntry lnEntry = null;
	    if (dbIdTrackingEntry != null) {
		lnEntry = dbIdTrackingEntry;
		lnEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
		entryLoaded = true;
		MapLN mapLN = (MapLN) lnEntry.getMainItem();
		int dbId = mapLN.getDatabase().getId().getId();
		if (dbId > maxDbId) {
		    maxDbId = dbId;
		}
	    }
	    if (txnIdTrackingEntry != null) {
		if (lnEntry == null) {
		    lnEntry = txnIdTrackingEntry;
		    lnEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
		    entryLoaded = true;
		}
		long txnId = lnEntry.getTxnId().longValue();
		if (txnId > maxTxnId) {
		    maxTxnId = txnId;
		}
	    }
	    if (fsTrackingEntry != null) {
		if (!entryLoaded) {
		    nodeTrackingEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
		    entryLoaded = true;
		}
		byte[] keyBytes = fsTrackingEntry.getKey();
		FileSummaryLN fsln = (FileSummaryLN) fsTrackingEntry.getMainItem();
		long fileNum = fsln.getFileNumber(keyBytes);
		TrackedFileSummary trackedLN = tracker.getTrackedFile(fileNum);
		if (trackedLN != null) {
		    trackedLN.reset();
		}
		fileSummaryLsns.put(new Long(fileNum), new Long(getLastLsn()));
	    }
	    if (nodeTrackingEntry != null) {
		if (!entryLoaded) {
		    nodeTrackingEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, false);
		    entryLoaded = true;
		}
		long nodeId = nodeTrackingEntry.getNodeId();
		maxNodeId = (nodeId > maxNodeId) ? nodeId : maxNodeId;
	    }
	    if (inTrackingEntry != null) {
		assert entryLoaded : "All nodes should have been loaded";
		long oldLsn = inTrackingEntry.getObsoleteLsn();
		if (oldLsn != DbLsn.NULL_LSN) {
		    long newLsn = getLastLsn();
		    if (!isObsoleteLsnAlreadyCounted(oldLsn, newLsn)) {
			tracker.countObsoleteNodeInexact(oldLsn, fromLogType);
		    }
		}
		if (isProvisional && partialCkptStart != DbLsn.NULL_LSN) {
		    oldLsn = getLastLsn();
		    if (DbLsn.compareTo(partialCkptStart, oldLsn) < 0) {
			tracker.countObsoleteNodeInexact(oldLsn, fromLogType);
		    }
		}
	    }
	}
	return useEntry;
  }


  /**
   * 
   * Returns whether a given obsolete LSN has already been counted in the utilization profile.  If true is returned, it should not be counted again, to prevent double-counting.
   */
  // line 254 "../../../../INFileReader.ump"
   private boolean isObsoleteLsnAlreadyCounted(long oldLsn, long newLsn){
    Long fileNum = new Long(DbLsn.getFileNumber(oldLsn));
	long fileSummaryLsn = DbLsn.longToLsn((Long) fileSummaryLsns.get(fileNum));
	int cmpFsLsnToNewLsn = (fileSummaryLsn != DbLsn.NULL_LSN) ? DbLsn.compareTo(fileSummaryLsn, newLsn) : -1;
	return (cmpFsLsnToNewLsn >= 0);
  }


  /**
   * 
   * Get the last IN seen by the reader.
   */
  // line 264 "../../../../INFileReader.ump"
   public IN getIN() throws DatabaseException{
    return ((INContainingEntry) targetLogEntry).getIN(env);
  }


  /**
   * 
   * Get the last databaseId seen by the reader.
   */
  // line 271 "../../../../INFileReader.ump"
   public DatabaseId getDatabaseId(){
    if (lastEntryWasDelete) {
	    return ((INDeleteInfo) targetLogEntry.getMainItem()).getDatabaseId();
	} else if (lastEntryWasDupDelete) {
	    return ((INDupDeleteInfo) targetLogEntry.getMainItem()).getDatabaseId();
	} else {
	    return ((INContainingEntry) targetLogEntry).getDbId();
	}
  }


  /**
   * 
   * Get the maximum node id seen by the reader.
   */
  // line 284 "../../../../INFileReader.ump"
   public long getMaxNodeId(){
    return maxNodeId;
  }


  /**
   * 
   * Get the maximum db id seen by the reader.
   */
  // line 291 "../../../../INFileReader.ump"
   public int getMaxDbId(){
    return maxDbId;
  }


  /**
   * 
   * Get the maximum txn id seen by the reader.
   */
  // line 298 "../../../../INFileReader.ump"
   public long getMaxTxnId(){
    return maxTxnId;
  }


  /**
   * 
   * @return true if the last entry was a delete info entry.
   */
  // line 305 "../../../../INFileReader.ump"
   public boolean isDeleteInfo(){
    return lastEntryWasDelete;
  }


  /**
   * 
   * @return true if the last entry was a dup delete info entry.
   */
  // line 312 "../../../../INFileReader.ump"
   public boolean isDupDeleteInfo(){
    return lastEntryWasDupDelete;
  }


  /**
   * 
   * Get the deleted node id stored in the last delete info log entry.
   */
  // line 319 "../../../../INFileReader.ump"
   public long getDeletedNodeId(){
    return ((INDeleteInfo) targetLogEntry.getMainItem()).getDeletedNodeId();
  }


  /**
   * 
   * Get the deleted id key stored in the last delete info log entry.
   */
  // line 326 "../../../../INFileReader.ump"
   public byte[] getDeletedIdKey(){
    return ((INDeleteInfo) targetLogEntry.getMainItem()).getDeletedIdKey();
  }


  /**
   * 
   * Get the deleted node id stored in the last delete info log entry.
   */
  // line 333 "../../../../INFileReader.ump"
   public long getDupDeletedNodeId(){
    return ((INDupDeleteInfo) targetLogEntry.getMainItem()).getDeletedNodeId();
  }


  /**
   * 
   * Get the deleted main key stored in the last delete info log entry.
   */
  // line 340 "../../../../INFileReader.ump"
   public byte[] getDupDeletedMainKey(){
    return ((INDupDeleteInfo) targetLogEntry.getMainItem()).getDeletedMainKey();
  }


  /**
   * 
   * Get the deleted main key stored in the last delete info log entry.
   */
  // line 347 "../../../../INFileReader.ump"
   public byte[] getDupDeletedDupKey(){
    return ((INDupDeleteInfo) targetLogEntry.getMainItem()).getDeletedDupKey();
  }


  /**
   * 
   * Get the LSN that should represent this IN. For most INs, it's the LSN that was just read. For BINDelta entries, it's the LSN of the last full version.
   */
  // line 354 "../../../../INFileReader.ump"
   public long getLsnOfIN(){
    return ((INContainingEntry) targetLogEntry).getLsnOfIN(getLastLsn());
  }


  /**
   * 
   * Get the current log entry type.
   */
  // line 361 "../../../../INFileReader.ump"
   public LogEntryType getLogEntryType(){
    return LogEntryType.findType(currentEntryTypeNum, currentEntryTypeVersion);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 28 "../../../../INFileReader.ump"
  private boolean lastEntryWasDelete ;
// line 30 "../../../../INFileReader.ump"
  private boolean lastEntryWasDupDelete ;
// line 32 "../../../../INFileReader.ump"
  private LogEntryType fromLogType ;
// line 34 "../../../../INFileReader.ump"
  private boolean isProvisional ;
// line 36 "../../../../INFileReader.ump"
  private Map targetEntryMap ;
// line 38 "../../../../INFileReader.ump"
  private LogEntry targetLogEntry ;
// line 40 "../../../../INFileReader.ump"
  private Map dbIdTrackingMap ;
// line 42 "../../../../INFileReader.ump"
  private LNLogEntry dbIdTrackingEntry ;
// line 44 "../../../../INFileReader.ump"
  private Map txnIdTrackingMap ;
// line 46 "../../../../INFileReader.ump"
  private LNLogEntry txnIdTrackingEntry ;
// line 48 "../../../../INFileReader.ump"
  private Map otherNodeTrackingMap ;
// line 50 "../../../../INFileReader.ump"
  private NodeLogEntry nodeTrackingEntry ;
// line 52 "../../../../INFileReader.ump"
  private INLogEntry inTrackingEntry ;
// line 54 "../../../../INFileReader.ump"
  private LNLogEntry fsTrackingEntry ;
// line 56 "../../../../INFileReader.ump"
  private boolean trackIds ;
// line 58 "../../../../INFileReader.ump"
  private long maxNodeId ;
// line 60 "../../../../INFileReader.ump"
  private int maxDbId ;
// line 62 "../../../../INFileReader.ump"
  private long maxTxnId ;
// line 64 "../../../../INFileReader.ump"
  private boolean mapDbOnly ;
// line 66 "../../../../INFileReader.ump"
  private long partialCkptStart ;
// line 68 "../../../../INFileReader.ump"
  private UtilizationTracker tracker ;
// line 70 "../../../../INFileReader.ump"
  private Map fileSummaryLsns ;

  
}