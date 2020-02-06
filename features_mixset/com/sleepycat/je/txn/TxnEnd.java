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

  //TxnEnd Attributes
  private long lastLsn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TxnEnd()
  {
    lastLsn = DbLsn.NULL_LSN;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLastLsn(long aLastLsn)
  {
    boolean wasSet = false;
    lastLsn = aLastLsn;
    wasSet = true;
    return wasSet;
  }

  public long getLastLsn()
  {
    return lastLsn;
  }

  public void delete()
  {}

  // line 21 "../../../../TxnEnd.ump"
  public  TxnEnd(long id, long lastLsn){
    this.id = id;
	time = new Timestamp(System.currentTimeMillis());
	this.lastLsn = lastLsn;
  }

  // line 29 "../../../../TxnEnd.ump"
   public long getId(){
    return id;
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
  // line 47 "../../../../TxnEnd.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 54 "../../../../TxnEnd.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 61 "../../../../TxnEnd.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 67 "../../../../TxnEnd.ump"
   public int getLogSize(){
    return LogUtils.LONG_BYTES + LogUtils.getTimestampLogSize() + LogUtils.getLongLogSize();
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 74 "../../../../TxnEnd.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeLong(logBuffer, id);
	LogUtils.writeTimestamp(logBuffer, time);
	LogUtils.writeLong(logBuffer, lastLsn);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 83 "../../../../TxnEnd.ump"
   public void readFromLog(ByteBuffer logBuffer, byte entryTypeVersion){
    id = LogUtils.readLong(logBuffer);
	time = LogUtils.readTimestamp(logBuffer);
	lastLsn = LogUtils.readLong(logBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 92 "../../../../TxnEnd.ump"
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
  // line 104 "../../../../TxnEnd.ump"
   public boolean logEntryIsTransactional(){
    return true;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 111 "../../../../TxnEnd.ump"
   public long getTransactionId(){
    return id;
  }


  public String toString()
  {
    return super.toString() + "["+
            "lastLsn" + ":" + getLastLsn()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../TxnEnd.ump"
  protected long id ;
// line 16 "../../../../TxnEnd.ump"
  protected Timestamp time ;

  
}