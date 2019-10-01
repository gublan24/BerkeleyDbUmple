/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import java.util.Calendar;
import java.sql.Timestamp;
import java.nio.ByteBuffer;

// line 3 "../../../../FileHeader.ump"
public class FileHeader implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileHeader()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 22 "../../../../FileHeader.ump"
  public  FileHeader(long fileNum, long lastEntryInPrevFileOffset){
    this.fileNum = fileNum;
	this.lastEntryInPrevFileOffset = lastEntryInPrevFileOffset;
	Calendar now = Calendar.getInstance();
	time = new Timestamp(now.getTimeInMillis());
	logVersion = LOG_VERSION;
  }


  /**
   * 
   * For logging only.
   */
  // line 33 "../../../../FileHeader.ump"
   public  FileHeader(){
    
  }


  /**
   * 
   * @return whether the file header has an old version number.
   * @throws DatabaseException if the header isn't valid.
   */
  // line 40 "../../../../FileHeader.ump"
  public boolean validate(String fileName, long expectedFileNum) throws DatabaseException{
    if (fileNum != expectedFileNum) {
	    throw new LogException("Wrong filenum in header for file " + fileName + " expected " + expectedFileNum
		    + " got " + fileNum);
	}
	return logVersion < LOG_VERSION;
  }


  /**
   * 
   * @return the offset of the last entry in the previous file.
   */
  // line 51 "../../../../FileHeader.ump"
  public long getLastEntryInPrevFileOffset(){
    return lastEntryInPrevFileOffset;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 58 "../../../../FileHeader.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_FILE_HEADER;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 65 "../../../../FileHeader.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 72 "../../../../FileHeader.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 79 "../../../../FileHeader.ump"
   public void postLogWork(long justLoggedLsn) throws DatabaseException{
    
  }


  /**
   * 
   * A header is always a known size.
   */
  // line 85 "../../../../FileHeader.ump"
   static  int entrySize(){
    return LogUtils.getTimestampLogSize() + LogUtils.UNSIGNED_INT_BYTES + LogUtils.LONG_BYTES + LogUtils.INT_BYTES;
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   * @return number of bytes used to store this object
   */
  // line 93 "../../../../FileHeader.ump"
   public int getLogSize(){
    return entrySize();
  }


  /**
   * 
   * @see LoggableObject#writeToLogSerialize this object into the buffer. Update cksum with allthe bytes used by this object
   * @param logBuffer is the destination buffer
   */
  // line 101 "../../../../FileHeader.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeTimestamp(logBuffer, time);
	LogUtils.writeUnsignedInt(logBuffer, fileNum);
	LogUtils.writeLong(logBuffer, lastEntryInPrevFileOffset);
	LogUtils.writeInt(logBuffer, logVersion);
  }


  /**
   * 
   * @see LogReadable#readFromLogInitialize this object from the data in itemBuf.
   * @param itemBuf the source buffer
   */
  // line 112 "../../../../FileHeader.ump"
   public void readFromLog(ByteBuffer logBuffer, byte entryTypeVersion) throws LogException{
    time = LogUtils.readTimestamp(logBuffer);
	fileNum = LogUtils.getUnsignedInt(logBuffer);
	lastEntryInPrevFileOffset = LogUtils.readLong(logBuffer);
	logVersion = LogUtils.readInt(logBuffer);
	if (logVersion > LOG_VERSION) {
	    throw new LogException("Expected log version " + LOG_VERSION + " or earlier but found " + logVersion
		    + " -- this version is not supported.");
	}
  }


  /**
   * 
   * @see LogReadable#dumpLog
   * @param sb destination string buffer
   * @param verbose if true, dump the full, verbose version
   */
  // line 128 "../../../../FileHeader.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<FileHeader num=\"0x");
	sb.append(Long.toHexString(fileNum));
	sb.append("\" lastEntryInPrevFileOffset=\"0x");
	sb.append(Long.toHexString(lastEntryInPrevFileOffset));
	sb.append("\" logVersion=\"0x");
	sb.append(Integer.toHexString(logVersion));
	sb.append("\" time=\"").append(time);
	sb.append("\"/>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 142 "../../../../FileHeader.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 149 "../../../../FileHeader.ump"
   public long getTransactionId(){
    return 0;
  }


  /**
   * 
   * Print in xml format
   */
  // line 156 "../../../../FileHeader.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	dumpLog(sb, true);
	return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../FileHeader.ump"
  private static final int LOG_VERSION = 3 ;
// line 13 "../../../../FileHeader.ump"
  private long fileNum ;
// line 15 "../../../../FileHeader.ump"
  private long lastEntryInPrevFileOffset ;
// line 17 "../../../../FileHeader.ump"
  private Timestamp time ;
// line 19 "../../../../FileHeader.ump"
  private int logVersion ;

  
}