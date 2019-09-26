/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../DeltaInfo.ump"
public class DeltaInfo implements LogWritable,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DeltaInfo()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 20 "../../../../DeltaInfo.ump"
  public  DeltaInfo(byte [] key, long lsn, byte state){
    this.key = key;
	this.lsn = lsn;
	this.state = state;
  }


  /**
   * 
   * For reading from the log only.
   */
  // line 29 "../../../../DeltaInfo.ump"
  public  DeltaInfo(){
    lsn = DbLsn.NULL_LSN;
  }

  // line 33 "../../../../DeltaInfo.ump"
   public int getLogSize(){
    return LogUtils.getByteArrayLogSize(key) + LogUtils.getLongLogSize() + 1;
  }

  // line 37 "../../../../DeltaInfo.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeByteArray(logBuffer, key);
	LogUtils.writeLong(logBuffer, lsn);
	logBuffer.put(state);
  }

  // line 43 "../../../../DeltaInfo.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    key = LogUtils.readByteArray(itemBuffer);
	lsn = LogUtils.readLong(itemBuffer);
	state = itemBuffer.get();
  }

  // line 49 "../../../../DeltaInfo.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append(Key.dumpString(key, 0));
	sb.append(DbLsn.toString(lsn));
	IN.dumpDeletedState(sb, state);
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 58 "../../../../DeltaInfo.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 65 "../../../../DeltaInfo.ump"
   public long getTransactionId(){
    return 0;
  }


  /**
   * 
   * @return the Key.
   */
  // line 72 "../../../../DeltaInfo.ump"
  public byte[] getKey(){
    return key;
  }


  /**
   * 
   * @return the state flags.
   */
  // line 79 "../../../../DeltaInfo.ump"
  public byte getState(){
    return state;
  }


  /**
   * 
   * @return true if this is known to be deleted.
   */
  // line 86 "../../../../DeltaInfo.ump"
  public boolean isKnownDeleted(){
    return IN.isStateKnownDeleted(state);
  }


  /**
   * 
   * @return the LSN.
   */
  // line 93 "../../../../DeltaInfo.ump"
  public long getLsn(){
    return lsn;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../DeltaInfo.ump"
  private byte[] key ;
// line 15 "../../../../DeltaInfo.ump"
  private long lsn ;
// line 17 "../../../../DeltaInfo.ump"
  private byte state ;

  
}