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

// line 3 "../../../../INDupDeleteInfo.ump"
public class INDupDeleteInfo implements LoggableObject,LogReadable,LogWritable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INDupDeleteInfo()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a new delete info entry.
   */
  // line 27 "../../../../INDupDeleteInfo.ump"
   public  INDupDeleteInfo(long deletedNodeId, byte [] deletedMainKey, byte [] deletedDupKey, DatabaseId dbId){
    this.deletedNodeId = deletedNodeId;
	this.deletedMainKey = deletedMainKey;
	this.deletedDupKey = deletedDupKey;
	this.dbId = dbId;
  }


  /**
   * 
   * Used by logging system only.
   */
  // line 37 "../../../../INDupDeleteInfo.ump"
   public  INDupDeleteInfo(){
    dbId = new DatabaseId();
  }

  // line 41 "../../../../INDupDeleteInfo.ump"
   public long getDeletedNodeId(){
    return deletedNodeId;
  }

  // line 45 "../../../../INDupDeleteInfo.ump"
   public byte[] getDeletedMainKey(){
    return deletedMainKey;
  }

  // line 49 "../../../../INDupDeleteInfo.ump"
   public byte[] getDeletedDupKey(){
    return deletedDupKey;
  }

  // line 53 "../../../../INDupDeleteInfo.ump"
   public DatabaseId getDatabaseId(){
    return dbId;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 60 "../../../../INDupDeleteInfo.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_IN_DUPDELETE_INFO;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 67 "../../../../INDupDeleteInfo.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 74 "../../../../INDupDeleteInfo.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 81 "../../../../INDupDeleteInfo.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 87 "../../../../INDupDeleteInfo.ump"
   public int getLogSize(){
    return LogUtils.LONG_BYTES + LogUtils.getByteArrayLogSize(deletedMainKey)
		+ LogUtils.getByteArrayLogSize(deletedDupKey) + dbId.getLogSize();
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 95 "../../../../INDupDeleteInfo.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeLong(logBuffer, deletedNodeId);
	LogUtils.writeByteArray(logBuffer, deletedMainKey);
	LogUtils.writeByteArray(logBuffer, deletedDupKey);
	dbId.writeToLog(logBuffer);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 105 "../../../../INDupDeleteInfo.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    deletedNodeId = LogUtils.readLong(itemBuffer);
	deletedMainKey = LogUtils.readByteArray(itemBuffer);
	deletedDupKey = LogUtils.readByteArray(itemBuffer);
	dbId.readFromLog(itemBuffer, entryTypeVersion);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 115 "../../../../INDupDeleteInfo.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<INDupDeleteEntry node=\"").append(deletedNodeId);
	sb.append("\">");
	sb.append(Key.dumpString(deletedMainKey, 0));
	sb.append(Key.dumpString(deletedDupKey, 0));
	dbId.dumpLog(sb, verbose);
	sb.append("</INDupDeleteEntry>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 127 "../../../../INDupDeleteInfo.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 134 "../../../../INDupDeleteInfo.ump"
   public long getTransactionId(){
    return 0;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../INDupDeleteInfo.ump"
  private long deletedNodeId ;
// line 17 "../../../../INDupDeleteInfo.ump"
  private byte[] deletedMainKey ;
// line 19 "../../../../INDupDeleteInfo.ump"
  private byte[] deletedDupKey ;
// line 21 "../../../../INDupDeleteInfo.ump"
  private DatabaseId dbId ;

  
}