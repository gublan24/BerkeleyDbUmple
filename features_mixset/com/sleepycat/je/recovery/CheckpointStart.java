/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import java.util.Calendar;
import java.sql.Timestamp;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../CheckpointStart.ump"
public class CheckpointStart implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CheckpointStart()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 22 "../../../../CheckpointStart.ump"
   public  CheckpointStart(long id, String invoker){
    Calendar cal = Calendar.getInstance();
	this.startTime = new Timestamp(cal.getTime().getTime());
	this.id = id;
	if (invoker == null) {
	    this.invoker = "";
	} else {
	    this.invoker = invoker;
	}
  }


  /**
   * public CheckpointStart() {
   * }
   * 
   * @see LoggableObject#getLogType
   */
  // line 39 "../../../../CheckpointStart.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_CKPT_START;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 46 "../../../../CheckpointStart.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 53 "../../../../CheckpointStart.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 60 "../../../../CheckpointStart.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 66 "../../../../CheckpointStart.ump"
   public int getLogSize(){
    return LogUtils.getTimestampLogSize() + LogUtils.getLongLogSize() + LogUtils.getStringLogSize(invoker);
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 73 "../../../../CheckpointStart.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeTimestamp(logBuffer, startTime);
	LogUtils.writeLong(logBuffer, id);
	LogUtils.writeString(logBuffer, invoker);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 82 "../../../../CheckpointStart.ump"
   public void readFromLog(ByteBuffer logBuffer, byte entryTypeVersion) throws LogException{
    startTime = LogUtils.readTimestamp(logBuffer);
	id = LogUtils.readLong(logBuffer);
	invoker = LogUtils.readString(logBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 91 "../../../../CheckpointStart.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<CkptStart invoker=\"").append(invoker);
	sb.append("\" time=\"").append(startTime);
	sb.append("\" id=\"").append(id);
	sb.append("\"/>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 101 "../../../../CheckpointStart.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 108 "../../../../CheckpointStart.ump"
   public long getTransactionId(){
    return 0;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../CheckpointStart.ump"
  private Timestamp startTime ;
// line 17 "../../../../CheckpointStart.ump"
  private long id ;
// line 19 "../../../../CheckpointStart.ump"
  private String invoker ;

  
}