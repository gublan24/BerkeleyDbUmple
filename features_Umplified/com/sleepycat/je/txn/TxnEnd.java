/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogEntryType;
import java.sql.Timestamp;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../TxnEnd.ump"
public abstract class TxnEnd implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TxnEnd()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 21 "../../../../TxnEnd.ump"
  public  TxnEnd(long id, long lastLsn){
    this.id = id;
	time = new Timestamp(System.currentTimeMillis());
	this.lastLsn = lastLsn;
  }


  /**
   * 
   * For constructing from the log
   */
  // line 30 "../../../../TxnEnd.ump"
   public  TxnEnd(){
    lastLsn = DbLsn.NULL_LSN;
  }

  // line 34 "../../../../TxnEnd.ump"
   public long getId(){
    return id;
  }

  // line 38 "../../../../TxnEnd.ump"
  public long getLastLsn(){
    return lastLsn;
  }

   protected abstract String getTagName();


  /**
   * 
   * @see LoggableObject#getLogType
   */
   public abstract LogEntryType getLogType();


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 52 "../../../../TxnEnd.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 59 "../../../../TxnEnd.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 66 "../../../../TxnEnd.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 72 "../../../../TxnEnd.ump"
   public int getLogSize(){
    return LogUtils.LONG_BYTES + LogUtils.getTimestampLogSize() + LogUtils.getLongLogSize();
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 79 "../../../../TxnEnd.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeLong(logBuffer, id);
	LogUtils.writeTimestamp(logBuffer, time);
	LogUtils.writeLong(logBuffer, lastLsn);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 88 "../../../../TxnEnd.ump"
   public void readFromLog(ByteBuffer logBuffer, byte entryTypeVersion){
    id = LogUtils.readLong(logBuffer);
	time = LogUtils.readTimestamp(logBuffer);
	lastLsn = LogUtils.readLong(logBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 97 "../../../../TxnEnd.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<").append(getTagName());
	sb.append(" id=\"").append(id);
	sb.append("\" time=\"").append(time);
	sb.append("\">");
	sb.append(DbLsn.toString(lastLsn));
	sb.append("</").append(getTagName()).append(">");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 109 "../../../../TxnEnd.ump"
   public boolean logEntryIsTransactional(){
    return true;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 116 "../../../../TxnEnd.ump"
   public long getTransactionId(){
    return id;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../TxnEnd.ump"
  protected long id ;
// line 16 "../../../../TxnEnd.ump"
  protected Timestamp time ;
// line 18 "../../../../TxnEnd.ump"
  private long lastLsn ;

  
}