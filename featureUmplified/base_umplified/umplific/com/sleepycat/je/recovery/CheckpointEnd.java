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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CheckpointEnd()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 38 "../../../../CheckpointEnd.ump"
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

  // line 64 "../../../../CheckpointEnd.ump"
   public  CheckpointEnd(){
    checkpointStartLsn = DbLsn.NULL_LSN;
	rootLsn = DbLsn.NULL_LSN;
	firstActiveLsn = DbLsn.NULL_LSN;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 73 "../../../../CheckpointEnd.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_CKPT_END;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 80 "../../../../CheckpointEnd.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 87 "../../../../CheckpointEnd.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 94 "../../../../CheckpointEnd.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 100 "../../../../CheckpointEnd.ump"
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
  // line 113 "../../../../CheckpointEnd.ump"
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
  // line 131 "../../../../CheckpointEnd.ump"
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
  // line 149 "../../../../CheckpointEnd.ump"
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
  // line 175 "../../../../CheckpointEnd.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 182 "../../../../CheckpointEnd.ump"
   public long getTransactionId(){
    return 0;
  }

  // line 186 "../../../../CheckpointEnd.ump"
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

  // line 202 "../../../../CheckpointEnd.ump"
  public long getCheckpointStartLsn(){
    return checkpointStartLsn;
  }

  // line 206 "../../../../CheckpointEnd.ump"
  public long getRootLsn(){
    return rootLsn;
  }

  // line 210 "../../../../CheckpointEnd.ump"
  public long getFirstActiveLsn(){
    return firstActiveLsn;
  }

  // line 214 "../../../../CheckpointEnd.ump"
  public long getLastNodeId(){
    return lastNodeId;
  }

  // line 218 "../../../../CheckpointEnd.ump"
  public int getLastDbId(){
    return lastDbId;
  }

  // line 222 "../../../../CheckpointEnd.ump"
  public long getLastTxnId(){
    return lastTxnId;
  }

  // line 226 "../../../../CheckpointEnd.ump"
  public long getId(){
    return id;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 16 "../../../../CheckpointEnd.ump"
  private String invoker ;
// line 18 "../../../../CheckpointEnd.ump"
  private Timestamp endTime ;
// line 20 "../../../../CheckpointEnd.ump"
  private long checkpointStartLsn ;
// line 22 "../../../../CheckpointEnd.ump"
  private boolean rootLsnExists ;
// line 24 "../../../../CheckpointEnd.ump"
  private long rootLsn ;
// line 26 "../../../../CheckpointEnd.ump"
  private long firstActiveLsn ;
// line 28 "../../../../CheckpointEnd.ump"
  private long lastNodeId ;
// line 30 "../../../../CheckpointEnd.ump"
  private int lastDbId ;
// line 32 "../../../../CheckpointEnd.ump"
  private long lastTxnId ;
// line 34 "../../../../CheckpointEnd.ump"
  private long id ;

  
}