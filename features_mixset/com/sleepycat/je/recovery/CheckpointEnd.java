/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import java.util.Calendar;
import java.sql.Timestamp;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../CheckpointEnd.ump"
public class CheckpointEnd implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CheckpointEnd Attributes
  private long checkpointStartLsn;
  private long rootLsn;
  private long firstActiveLsn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CheckpointEnd()
  {
    checkpointStartLsn = DbLsn.NULL_LSN;
    rootLsn = DbLsn.NULL_LSN;
    firstActiveLsn = DbLsn.NULL_LSN;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCheckpointStartLsn(long aCheckpointStartLsn)
  {
    boolean wasSet = false;
    checkpointStartLsn = aCheckpointStartLsn;
    wasSet = true;
    return wasSet;
  }

  public boolean setRootLsn(long aRootLsn)
  {
    boolean wasSet = false;
    rootLsn = aRootLsn;
    wasSet = true;
    return wasSet;
  }

  public boolean setFirstActiveLsn(long aFirstActiveLsn)
  {
    boolean wasSet = false;
    firstActiveLsn = aFirstActiveLsn;
    wasSet = true;
    return wasSet;
  }

  public long getCheckpointStartLsn()
  {
    return checkpointStartLsn;
  }

  public long getRootLsn()
  {
    return rootLsn;
  }

  public long getFirstActiveLsn()
  {
    return firstActiveLsn;
  }

  public void delete()
  {}

  // line 41 "../../../../CheckpointEnd.ump"
   public  CheckpointEnd(String invoker, long checkpointStartLsn, long rootLsn, long firstActiveLsn, long lastNodeId, int lastDbId, long lastTxnId, long id){
    if (invoker == null) {
	    this.invoker = "";
	} else {
	    this.invoker = invoker;
	}
	Calendar cal = Calendar.getInstance();
	this.endTime = new Timestamp(cal.getTime().getTime());
	this.checkpointStartLsn = checkpointStartLsn;
	this.rootLsn = rootLsn;
	if (rootLsn == DbLsn.NULL_LSN) {
	    rootLsnExists = false;
	} else {
	    rootLsnExists = true;
	}
	if (firstActiveLsn == DbLsn.NULL_LSN) {
	    this.firstActiveLsn = checkpointStartLsn;
	} else {
	    this.firstActiveLsn = firstActiveLsn;
	}
	this.lastNodeId = lastNodeId;
	this.lastDbId = lastDbId;
	this.lastTxnId = lastTxnId;
	this.id = id;
  }


  /**
   * public CheckpointEnd() {
   * checkpointStartLsn = DbLsn.NULL_LSN;
   * rootLsn = DbLsn.NULL_LSN;
   * firstActiveLsn = DbLsn.NULL_LSN;
   * }
   * 
   * @see LoggableObject#getLogType
   */
  // line 76 "../../../../CheckpointEnd.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_CKPT_END;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 83 "../../../../CheckpointEnd.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 90 "../../../../CheckpointEnd.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 97 "../../../../CheckpointEnd.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 103 "../../../../CheckpointEnd.ump"
   public int getLogSize(){
    int size = LogUtils.getStringLogSize(invoker) + LogUtils.getTimestampLogSize() + LogUtils.getLongLogSize()
		+ LogUtils.getBooleanLogSize() + LogUtils.getLongLogSize() + LogUtils.getLongLogSize()
		+ LogUtils.getIntLogSize() + LogUtils.getLongLogSize() + LogUtils.getLongLogSize();
	if (rootLsnExists) {
	    size += LogUtils.getLongLogSize();
	}
	return size;
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 116 "../../../../CheckpointEnd.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeString(logBuffer, invoker);
	LogUtils.writeTimestamp(logBuffer, endTime);
	LogUtils.writeLong(logBuffer, checkpointStartLsn);
	LogUtils.writeBoolean(logBuffer, rootLsnExists);
	if (rootLsnExists) {
	    LogUtils.writeLong(logBuffer, rootLsn);
	}
	LogUtils.writeLong(logBuffer, firstActiveLsn);
	LogUtils.writeLong(logBuffer, lastNodeId);
	LogUtils.writeInt(logBuffer, lastDbId);
	LogUtils.writeLong(logBuffer, lastTxnId);
	LogUtils.writeLong(logBuffer, id);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 134 "../../../../CheckpointEnd.ump"
   public void readFromLog(ByteBuffer logBuffer, byte entryTypeVersion) throws LogException{
    invoker = LogUtils.readString(logBuffer);
	endTime = LogUtils.readTimestamp(logBuffer);
	checkpointStartLsn = LogUtils.readLong(logBuffer);
	rootLsnExists = LogUtils.readBoolean(logBuffer);
	if (rootLsnExists) {
	    rootLsn = LogUtils.readLong(logBuffer);
	}
	firstActiveLsn = LogUtils.readLong(logBuffer);
	lastNodeId = LogUtils.readLong(logBuffer);
	lastDbId = LogUtils.readInt(logBuffer);
	lastTxnId = LogUtils.readLong(logBuffer);
	id = LogUtils.readLong(logBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 152 "../../../../CheckpointEnd.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<CkptEnd invoker=\"").append(invoker);
	sb.append("\" time=\"").append(endTime);
	sb.append("\" lastNodeId=\"").append(lastNodeId);
	sb.append("\" lastDbId=\"").append(lastDbId);
	sb.append("\" lastTxnId=\"").append(lastTxnId);
	sb.append("\" id=\"").append(id);
	sb.append("\" rootExists=\"").append(rootLsnExists);
	sb.append("\">");
	sb.append("<ckptStart>");
	sb.append(DbLsn.toString(checkpointStartLsn));
	sb.append("</ckptStart>");
	if (rootLsnExists) {
	    sb.append("<root>");
	    sb.append(DbLsn.toString(rootLsn));
	    sb.append("</root>");
	}
	sb.append("<firstActive>");
	sb.append(DbLsn.toString(firstActiveLsn));
	sb.append("</firstActive>");
	sb.append("</CkptEnd>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 178 "../../../../CheckpointEnd.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 185 "../../../../CheckpointEnd.ump"
   public long getTransactionId(){
    return 0;
  }

  // line 189 "../../../../CheckpointEnd.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("time=").append(endTime);
	sb.append(" lastNodeId=").append(lastNodeId);
	sb.append(" lastDbId=").append(lastDbId);
	sb.append(" lastTxnId=").append(lastTxnId);
	sb.append(" id=").append(id);
	sb.append(" rootExists=").append(rootLsnExists);
	sb.append(" ckptStartLsn=").append(DbLsn.getNoFormatString(checkpointStartLsn));
	if (rootLsnExists) {
	    sb.append(" root=").append(DbLsn.getNoFormatString(rootLsn));
	}
	sb.append(" firstActive=").append(DbLsn.getNoFormatString(firstActiveLsn));
	return sb.toString();
  }

  // line 217 "../../../../CheckpointEnd.ump"
  public long getLastNodeId(){
    return lastNodeId;
  }

  // line 221 "../../../../CheckpointEnd.ump"
  public int getLastDbId(){
    return lastDbId;
  }

  // line 225 "../../../../CheckpointEnd.ump"
  public long getLastTxnId(){
    return lastTxnId;
  }

  // line 229 "../../../../CheckpointEnd.ump"
  public long getId(){
    return id;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../../../CheckpointEnd.ump"
  private String invoker ;
// line 19 "../../../../CheckpointEnd.ump"
  private Timestamp endTime ;
// line 23 "../../../../CheckpointEnd.ump"
  private boolean rootLsnExists ;
// line 29 "../../../../CheckpointEnd.ump"
  private long lastNodeId ;
// line 31 "../../../../CheckpointEnd.ump"
  private int lastDbId ;
// line 33 "../../../../CheckpointEnd.ump"
  private long lastTxnId ;
// line 35 "../../../../CheckpointEnd.ump"
  private long id ;

  
}