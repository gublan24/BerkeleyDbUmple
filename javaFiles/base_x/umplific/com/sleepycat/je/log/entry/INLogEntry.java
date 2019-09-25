/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log.entry;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../../INLogEntry.ump"
public class INLogEntry implements LogEntry,LoggableObject,NodeLogEntry,INContainingEntry
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INLogEntry()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Construct a log entry for reading.
   */
  // line 30 "../../../../../INLogEntry.ump"
   public  INLogEntry(Class logClass){
    this.logClass = logClass;
  }


  /**
   * 
   * Construct a log entry for writing to the log.
   */
  // line 37 "../../../../../INLogEntry.ump"
   public  INLogEntry(IN in){
    this.in = in;
	this.dbId = in.getDatabase().getId();
	this.logClass = in.getClass();
	this.nodeId = in.getNodeId();
	this.obsoleteLsn = in.getLastFullVersion();
  }


  /**
   * 
   * Read in an IN entry.
   */
  // line 49 "../../../../../INLogEntry.ump"
   public void readEntry(ByteBuffer entryBuffer, int entrySize, byte entryTypeVersion, boolean readFullItem) throws DatabaseException{
    entryTypeVersion &= LogEntryType.clearProvisional(entryTypeVersion);
	try {
	    if (readFullItem) {
		in = (IN) logClass.newInstance();
		in.readFromLog(entryBuffer, entryTypeVersion);
		nodeId = in.getNodeId();
	    } else {
		int position = entryBuffer.position() + entrySize;
		if (entryTypeVersion == 1) {
		    position -= LogUtils.UNSIGNED_INT_BYTES;
		} else if (entryTypeVersion >= 2) {
		    position -= LogUtils.LONG_BYTES;
		}
		position -= LogUtils.INT_BYTES;
		nodeId = LogUtils.readLong(entryBuffer);
		entryBuffer.position(position);
		in = null;
	    }
	    dbId = new DatabaseId();
	    dbId.readFromLog(entryBuffer, entryTypeVersion);
	    if (entryTypeVersion < 1) {
		obsoleteLsn = DbLsn.NULL_LSN;
	    } else if (entryTypeVersion == 1) {
		long fileNum = LogUtils.getUnsignedInt(entryBuffer);
		if (fileNum == 0xffffffffL) {
		    obsoleteLsn = DbLsn.NULL_LSN;
		} else {
		    obsoleteLsn = DbLsn.makeLsn(fileNum, 0);
		}
	    } else {
		obsoleteLsn = LogUtils.readLong(entryBuffer);
	    }
	} catch (IllegalAccessException e) {
	    throw new DatabaseException(e);
	} catch (InstantiationException e) {
	    throw new DatabaseException(e);
	}
  }


  /**
   * 
   * Returns the LSN of the prior version of this node.  Used for counting the prior version as obsolete.  If the offset of the LSN is zero, only the file number is known because we read a version 1 log entry.
   */
  // line 92 "../../../../../INLogEntry.ump"
   public long getObsoleteLsn(){
    return obsoleteLsn;
  }


  /**
   * 
   * Print out the contents of an entry.
   */
  // line 99 "../../../../../INLogEntry.ump"
   public StringBuffer dumpEntry(StringBuffer sb, boolean verbose){
    in.dumpLog(sb, verbose);
	dbId.dumpLog(sb, verbose);
	return sb;
  }


  /**
   * 
   * @return the item in the log entry
   */
  // line 108 "../../../../../INLogEntry.ump"
   public Object getMainItem(){
    return in;
  }

  // line 112 "../../../../../INLogEntry.ump"
   public Object clone() throws CloneNotSupportedException{
    return super.clone();
  }


  /**
   * 
   * @see LogEntry#isTransactional
   */
  // line 119 "../../../../../INLogEntry.ump"
   public boolean isTransactional(){
    return false;
  }


  /**
   * 
   * @see LogEntry#getTransactionId
   */
  // line 126 "../../../../../INLogEntry.ump"
   public long getTransactionId(){
    return 0;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 133 "../../../../../INLogEntry.ump"
   public LogEntryType getLogType(){
    return in.getLogType();
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchAsk the in if it can be marshalled outside the log write latch.
   */
  // line 140 "../../../../../INLogEntry.ump"
   public boolean marshallOutsideWriteLatch(){
    return in.marshallOutsideWriteLatch();
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 147 "../../../../../INLogEntry.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 154 "../../../../../INLogEntry.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 160 "../../../../../INLogEntry.ump"
   public int getLogSize(){
    return (in.getLogSize() + dbId.getLogSize() + LogUtils.LONG_BYTES);
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 167 "../../../../../INLogEntry.ump"
   public void writeToLog(ByteBuffer destBuffer){
    in.writeToLog(destBuffer);
	dbId.writeToLog(destBuffer);
	LogUtils.writeLong(destBuffer, obsoleteLsn);
  }

  // line 173 "../../../../../INLogEntry.ump"
   public IN getIN(EnvironmentImpl env) throws DatabaseException{
    return in;
  }


  /**
   * 
   * @see NodeLogEntry#getNodeId
   */
  // line 180 "../../../../../INLogEntry.ump"
   public long getNodeId(){
    return nodeId;
  }


  /**
   * 
   * @see INContainingEntry#getDbId()
   */
  // line 187 "../../../../../INLogEntry.ump"
   public DatabaseId getDbId(){
    return (DatabaseId) dbId;
  }


  /**
   * 
   * @return the LSN that represents this IN. For a vanilla IN entry, it's the last lsn read by the log reader.
   */
  // line 194 "../../../../../INLogEntry.ump"
   public long getLsnOfIN(long lastReadLsn){
    return lastReadLsn;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 16 "../../../../../INLogEntry.ump"
  private IN in ;
// line 18 "../../../../../INLogEntry.ump"
  private DatabaseId dbId ;
// line 20 "../../../../../INLogEntry.ump"
  private long obsoleteLsn ;
// line 22 "../../../../../INLogEntry.ump"
  private long nodeId ;
// line 24 "../../../../../INLogEntry.ump"
  private Class logClass ;

  
}