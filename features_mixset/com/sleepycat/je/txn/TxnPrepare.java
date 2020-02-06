/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogEntryType;
import javax.transaction.xa.Xid;
import java.nio.ByteBuffer;

// line 3 "../../../../TxnPrepare.ump"
public class TxnPrepare extends TxnEnd
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TxnPrepare()
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

  // line 17 "../../../../TxnPrepare.ump"
   public  TxnPrepare(long id, Xid xid){
    super(id, DbLsn.NULL_LSN);
	this.xid = xid;
  }

  // line 24 "../../../../TxnPrepare.ump"
   public Xid getXid(){
    return xid;
  }


  /**
   * 
   * @see TxnEnd#getLogType
   */
  // line 31 "../../../../TxnPrepare.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_TXN_PREPARE;
  }

  // line 35 "../../../../TxnPrepare.ump"
   protected String getTagName(){
    return "TxnPrepare";
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 42 "../../../../TxnPrepare.ump"
   public int getLogSize(){
    return LogUtils.LONG_BYTES + LogUtils.getTimestampLogSize() + LogUtils.getXidSize(xid);
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 49 "../../../../TxnPrepare.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeLong(logBuffer, id);
	LogUtils.writeTimestamp(logBuffer, time);
	LogUtils.writeXid(logBuffer, xid);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 58 "../../../../TxnPrepare.ump"
   public void readFromLog(ByteBuffer logBuffer, byte entryTypeVersion){
    id = LogUtils.readLong(logBuffer);
	time = LogUtils.readTimestamp(logBuffer);
	xid = LogUtils.readXid(logBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 67 "../../../../TxnPrepare.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<").append(getTagName());
	sb.append(" id=\"").append(id);
	sb.append("\" xid=\"").append(xid);
	sb.append("\" time=\"").append(time);
	sb.append("\">");
	sb.append("</").append(getTagName()).append(">");
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../TxnPrepare.ump"
  private Xid xid ;

  
}