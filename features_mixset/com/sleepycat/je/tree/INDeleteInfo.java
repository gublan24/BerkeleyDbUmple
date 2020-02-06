/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.DatabaseId;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../INDeleteInfo.ump"
public class INDeleteInfo implements LoggableObject,LogReadable,LogWritable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //INDeleteInfo Attributes
  private DatabaseId dbId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INDeleteInfo()
  {
    dbId = new DatabaseId();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDbId(DatabaseId aDbId)
  {
    boolean wasSet = false;
    dbId = aDbId;
    wasSet = true;
    return wasSet;
  }

  public DatabaseId getDbId()
  {
    return dbId;
  }

  public void delete()
  {}


  /**
   * 
   * Create a new delete info entry.
   */
  // line 25 "../../../../INDeleteInfo.ump"
   public  INDeleteInfo(long deletedNodeId, byte [] deletedIdKey, DatabaseId dbId){
    this.deletedNodeId = deletedNodeId;
	this.deletedIdKey = deletedIdKey;
	this.dbId = dbId;
  }

  // line 33 "../../../../INDeleteInfo.ump"
   public long getDeletedNodeId(){
    return deletedNodeId;
  }

  // line 37 "../../../../INDeleteInfo.ump"
   public byte[] getDeletedIdKey(){
    return deletedIdKey;
  }

  // line 41 "../../../../INDeleteInfo.ump"
   public DatabaseId getDatabaseId(){
    return dbId;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 48 "../../../../INDeleteInfo.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_IN_DELETE_INFO;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 55 "../../../../INDeleteInfo.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 62 "../../../../INDeleteInfo.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 69 "../../../../INDeleteInfo.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 75 "../../../../INDeleteInfo.ump"
   public int getLogSize(){
    return LogUtils.LONG_BYTES + LogUtils.getByteArrayLogSize(deletedIdKey) + dbId.getLogSize();
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 82 "../../../../INDeleteInfo.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeLong(logBuffer, deletedNodeId);
	LogUtils.writeByteArray(logBuffer, deletedIdKey);
	dbId.writeToLog(logBuffer);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 91 "../../../../INDeleteInfo.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    deletedNodeId = LogUtils.readLong(itemBuffer);
	deletedIdKey = LogUtils.readByteArray(itemBuffer);
	dbId.readFromLog(itemBuffer, entryTypeVersion);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 100 "../../../../INDeleteInfo.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<INDeleteEntry node=\"").append(deletedNodeId);
	sb.append("\">");
	sb.append(Key.dumpString(deletedIdKey, 0));
	dbId.dumpLog(sb, verbose);
	sb.append("</INDeleteEntry>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 111 "../../../../INDeleteInfo.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 118 "../../../../INDeleteInfo.ump"
   public long getTransactionId(){
    return 0;
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dbId" + "=" + (getDbId() != null ? !getDbId().equals(this)  ? getDbId().toString().replaceAll("  ","    ") : "this" : "null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../INDeleteInfo.ump"
  private long deletedNodeId ;
// line 17 "../../../../INDeleteInfo.ump"
  private byte[] deletedIdKey ;

  
}