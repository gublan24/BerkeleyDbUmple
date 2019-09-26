/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.entry.SingleItemLogEntry;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.log.entry.INLogEntry;
import com.sleepycat.je.log.entry.DeletedDupLNLogEntry;
import com.sleepycat.je.log.entry.BINDeltaLogEntry;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.HashSet;

// line 3 "../../../../LogEntryType.ump"
public class LogEntryType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogEntryType()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 124 "../../../../LogEntryType.ump"
   public static  boolean isNodeType(byte typeNum, byte version){
    return (typeNum <= MAX_NODE_TYPE_NUM);
  }


  /**
   * 
   * For base class support.
   */
  // line 131 "../../../../LogEntryType.ump"
  public  LogEntryType(byte typeNum, byte version){
    this.typeNum = typeNum;
	this.version = version;
  }


  /**
   * 
   * Create the static log types.
   */
  // line 139 "../../../../LogEntryType.ump"
   private  LogEntryType(byte typeNum, byte version, String displayName, LogEntry logEntry){
    this.typeNum = typeNum;
	this.version = version;
	this.logEntry = logEntry;
	this.displayName = displayName;
	LOG_TYPES[typeNum - 1] = this;
  }

  // line 147 "../../../../LogEntryType.ump"
   public boolean isNodeType(){
    return (typeNum <= MAX_NODE_TYPE_NUM);
  }


  /**
   * 
   * @return the static version of this type
   */
  // line 154 "../../../../LogEntryType.ump"
   public static  LogEntryType findType(byte typeNum, byte version){
    if (typeNum <= 0 || typeNum > MAX_TYPE_NUM) {
	    return null;
	}
	return (LogEntryType) LOG_TYPES[typeNum - 1];
  }


  /**
   * 
   * Get a copy of all types for unit testing.
   */
  // line 164 "../../../../LogEntryType.ump"
   public static  Set getAllTypes(){
    HashSet ret = new HashSet();
	for (int i = 0; i < MAX_TYPE_NUM; i++) {
	    ret.add(LOG_TYPES[i]);
	}
	return ret;
  }


  /**
   * 
   * @return the log entry type owned by the shared, static version
   */
  // line 175 "../../../../LogEntryType.ump"
   public LogEntry getSharedLogEntry(){
    return logEntry;
  }


  /**
   * 
   * @return a clone of the log entry type for a given log type.
   */
  // line 182 "../../../../LogEntryType.ump"
  public LogEntry getNewLogEntry() throws DatabaseException{
    try {
	    return (LogEntry) logEntry.clone();
	} catch (CloneNotSupportedException e) {
	    throw new DatabaseException(e);
	}
  }


  /**
   * 
   * Set the provisional bit.
   */
  // line 193 "../../../../LogEntryType.ump"
   static  byte setProvisional(byte version){
    return (byte) (version | PROVISIONAL_MASK);
  }


  /**
   * 
   * Clear the provisional bit.
   */
  // line 200 "../../../../LogEntryType.ump"
   public static  byte clearProvisional(byte version){
    return (byte) (version & IGNORE_PROVISIONAL);
  }


  /**
   * 
   * @return true if the provisional bit is set.
   */
  // line 207 "../../../../LogEntryType.ump"
   static  boolean isProvisional(byte version){
    return ((version & PROVISIONAL_MASK) != 0);
  }

  // line 211 "../../../../LogEntryType.ump"
  public byte getTypeNum(){
    return typeNum;
  }

  // line 215 "../../../../LogEntryType.ump"
  public byte getVersion(){
    return version;
  }


  /**
   * 
   * @return true if type number is valid.
   */
  // line 222 "../../../../LogEntryType.ump"
   static  boolean isValidType(byte typeNum){
    return typeNum > 0 && typeNum <= MAX_TYPE_NUM;
  }

  // line 226 "../../../../LogEntryType.ump"
   public String toString(){
    return displayName + "/" + version;
  }


  /**
   * 
   * Check for equality without making a new object.
   */
  // line 233 "../../../../LogEntryType.ump"
  public boolean equalsType(byte typeNum, byte version){
    return (this.typeNum == typeNum);
  }

  // line 237 "../../../../LogEntryType.ump"
   public boolean equalsType(byte typeNum){
    return (this.typeNum == typeNum);
  }

  // line 241 "../../../../LogEntryType.ump"
   public boolean equals(Object obj){
    if (this == obj) {
	    return true;
	}
	if (!(obj instanceof LogEntryType)) {
	    return false;
	}
	return typeNum == ((LogEntryType) obj).typeNum;
  }


  /**
   * 
   * This is used as a hash key.
   */
  // line 254 "../../../../LogEntryType.ump"
   public int hashCode(){
    return typeNum;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../LogEntryType.ump"
  private static final int MAX_TYPE_NUM = 27 ;
// line 17 "../../../../LogEntryType.ump"
  private static LogEntryType[] LOG_TYPES = new LogEntryType[MAX_TYPE_NUM] ;
// line 19 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_LN_TRANSACTIONAL = new LogEntryType((byte) 1, (byte) 0, "LN_TX",
	    new LNLogEntry(com.sleepycat.je.tree.LN.class, true)) ;
// line 22 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_LN = new LogEntryType((byte) 2, (byte) 0, "LN",
	    new LNLogEntry(com.sleepycat.je.tree.LN.class, false)) ;
// line 25 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_MAPLN_TRANSACTIONAL = new LogEntryType((byte) 3, (byte) 1, "MapLN_TX",
	    new LNLogEntry(com.sleepycat.je.tree.MapLN.class, true)) ;
// line 28 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_MAPLN = new LogEntryType((byte) 4, (byte) 1, "MapLN",
	    new LNLogEntry(com.sleepycat.je.tree.MapLN.class, false)) ;
// line 31 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_NAMELN_TRANSACTIONAL = new LogEntryType((byte) 5, (byte) 0, "NameLN_TX",
	    new LNLogEntry(com.sleepycat.je.tree.NameLN.class, true)) ;
// line 34 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_NAMELN = new LogEntryType((byte) 6, (byte) 0, "NameLN",
	    new LNLogEntry(com.sleepycat.je.tree.NameLN.class, false)) ;
// line 37 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_DEL_DUPLN_TRANSACTIONAL = new LogEntryType((byte) 7, (byte) 0, "DelDupLN_TX",
	    new DeletedDupLNLogEntry(true)) ;
// line 40 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_DEL_DUPLN = new LogEntryType((byte) 8, (byte) 0, "DelDupLN",
	    new DeletedDupLNLogEntry(false)) ;
// line 43 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_DUPCOUNTLN_TRANSACTIONAL = new LogEntryType((byte) 9, (byte) 0,
	    "DupCountLN_TX", new LNLogEntry(com.sleepycat.je.tree.DupCountLN.class, true)) ;
// line 46 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_DUPCOUNTLN = new LogEntryType((byte) 10, (byte) 0, "DupCountLN",
	    new LNLogEntry(com.sleepycat.je.tree.DupCountLN.class, false)) ;
// line 49 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_FILESUMMARYLN = new LogEntryType((byte) 11, (byte) 2, "FileSummaryLN",
	    new LNLogEntry(com.sleepycat.je.tree.FileSummaryLN.class, false)) ;
// line 52 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_IN = new LogEntryType((byte) 12, (byte) 2, "IN",
	    new INLogEntry(com.sleepycat.je.tree.IN.class)) ;
// line 55 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_BIN = new LogEntryType((byte) 13, (byte) 2, "BIN",
	    new INLogEntry(com.sleepycat.je.tree.BIN.class)) ;
// line 58 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_DIN = new LogEntryType((byte) 14, (byte) 2, "DIN",
	    new INLogEntry(com.sleepycat.je.tree.DIN.class)) ;
// line 61 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_DBIN = new LogEntryType((byte) 15, (byte) 2, "DBIN",
	    new INLogEntry(com.sleepycat.je.tree.DBIN.class)) ;
// line 64 "../../../../LogEntryType.ump"
  public static final LogEntryType[] IN_TYPES = {LogEntryType.LOG_IN, LogEntryType.LOG_BIN, LogEntryType.LOG_DIN,
	    LogEntryType.LOG_DBIN};
// line 70 "../../../../LogEntryType.ump"
  private static final int MAX_NODE_TYPE_NUM = 15 ;
// line 72 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_ROOT = new LogEntryType((byte) 16, (byte) 1, "Root",
	    new SingleItemLogEntry(com.sleepycat.je.dbi.DbTree.class)) ;
// line 75 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_TXN_COMMIT = new LogEntryType((byte) 17, (byte) 0, "Commit",
	    new SingleItemLogEntry(com.sleepycat.je.txn.TxnCommit.class)) ;
// line 78 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_TXN_ABORT = new LogEntryType((byte) 18, (byte) 0, "Abort",
	    new SingleItemLogEntry(com.sleepycat.je.txn.TxnAbort.class)) ;
// line 81 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_CKPT_START = new LogEntryType((byte) 19, (byte) 0, "CkptStart",
	    new SingleItemLogEntry(com.sleepycat.je.recovery.CheckpointStart.class)) ;
// line 84 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_CKPT_END = new LogEntryType((byte) 20, (byte) 0, "CkptEnd",
	    new SingleItemLogEntry(com.sleepycat.je.recovery.CheckpointEnd.class)) ;
// line 87 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_IN_DELETE_INFO = new LogEntryType((byte) 21, (byte) 0, "INDelete",
	    new SingleItemLogEntry(com.sleepycat.je.tree.INDeleteInfo.class)) ;
// line 90 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_BIN_DELTA = new LogEntryType((byte) 22, (byte) 0, "BINDelta",
	    new BINDeltaLogEntry(com.sleepycat.je.tree.BINDelta.class)) ;
// line 93 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_DUP_BIN_DELTA = new LogEntryType((byte) 23, (byte) 0, "DupBINDelta",
	    new BINDeltaLogEntry(com.sleepycat.je.tree.BINDelta.class)) ;
// line 96 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_TRACE = new LogEntryType((byte) 24, (byte) 0, "Trace",
	    new SingleItemLogEntry(com.sleepycat.je.utilint.Tracer.class)) ;
// line 99 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_FILE_HEADER = new LogEntryType((byte) 25, (byte) 0, "FileHeader",
	    new SingleItemLogEntry(com.sleepycat.je.log.FileHeader.class)) ;
// line 102 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_IN_DUPDELETE_INFO = new LogEntryType((byte) 26, (byte) 0, "INDupDelete",
	    new SingleItemLogEntry(com.sleepycat.je.tree.INDupDeleteInfo.class)) ;
// line 105 "../../../../LogEntryType.ump"
  public static final LogEntryType LOG_TXN_PREPARE = new LogEntryType((byte) 27, (byte) 0, "Prepare",
	    new SingleItemLogEntry(com.sleepycat.je.txn.TxnPrepare.class)) ;
// line 111 "../../../../LogEntryType.ump"
  private static final byte PROVISIONAL_MASK = (byte) 0x80 ;
// line 113 "../../../../LogEntryType.ump"
  private static final byte IGNORE_PROVISIONAL = ~PROVISIONAL_MASK ;
// line 115 "../../../../LogEntryType.ump"
  private byte typeNum ;
// line 117 "../../../../LogEntryType.ump"
  private byte version ;
// line 119 "../../../../LogEntryType.ump"
  private String displayName ;
// line 121 "../../../../LogEntryType.ump"
  private LogEntry logEntry ;

  
}