/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.log.entry.INLogEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.util.Map;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../CleanerFileReader.ump"
// line 3 "../../../../CleanerFileReader_static.ump"
public class CleanerFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CleanerFileReader()
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


  /**
   * 
   * Create this reader to start at a given LSN.
   * @param env The relevant EnvironmentImpl.
   * @param readBufferSize buffer size in bytes for reading in log.
   * @param startLsn where to start in the log, or null for the beginning.
   * @param fileNum single file number.
   */
  // line 41 "../../../../CleanerFileReader.ump"
   public  CleanerFileReader(EnvironmentImpl env, int readBufferSize, long startLsn, Long fileNum) throws IOException,DatabaseException{
    super(env, readBufferSize, true, startLsn, fileNum, DbLsn.NULL_LSN, DbLsn.NULL_LSN);
	targetEntryMap = new HashMap();
	addTargetType(IS_LN, LogEntryType.LOG_LN_TRANSACTIONAL);
	addTargetType(IS_LN, LogEntryType.LOG_LN);
	addTargetType(IS_LN, LogEntryType.LOG_NAMELN_TRANSACTIONAL);
	addTargetType(IS_LN, LogEntryType.LOG_NAMELN);
	addTargetType(IS_LN, LogEntryType.LOG_MAPLN_TRANSACTIONAL);
	addTargetType(IS_LN, LogEntryType.LOG_MAPLN);
	addTargetType(IS_LN, LogEntryType.LOG_DEL_DUPLN_TRANSACTIONAL);
	addTargetType(IS_LN, LogEntryType.LOG_DEL_DUPLN);
	addTargetType(IS_LN, LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL);
	addTargetType(IS_LN, LogEntryType.LOG_DUPCOUNTLN);
	addTargetType(IS_LN, LogEntryType.LOG_FILESUMMARYLN);
	addTargetType(IS_IN, LogEntryType.LOG_IN);
	addTargetType(IS_IN, LogEntryType.LOG_BIN);
	addTargetType(IS_IN, LogEntryType.LOG_DIN);
	addTargetType(IS_IN, LogEntryType.LOG_DBIN);
	addTargetType(IS_ROOT, LogEntryType.LOG_ROOT);
  }

  // line 62 "../../../../CleanerFileReader.ump"
   private void addTargetType(byte category, LogEntryType entryType) throws DatabaseException{
    targetEntryMap.put(entryType, new EntryInfo(entryType.getNewLogEntry(), category));
  }


  /**
   * 
   * Helper for determining the starting position and opening up a file at the desired location.
   */
  // line 69 "../../../../CleanerFileReader.ump"
   protected void initStartingPosition(long endOfFileLsn, Long fileNum) throws IOException,DatabaseException{
    eof = false;
	readBufferFileNum = fileNum.longValue();
	readBufferFileEnd = 0;
	nextEntryOffset = readBufferFileEnd;
  }


  /**
   * 
   * @return true if this is a type we're interested in.
   */
  // line 79 "../../../../CleanerFileReader.ump"
   protected boolean isTargetEntry(byte entryTypeNum, byte entryTypeVersion){
    LogEntryType fromLogType = new LogEntryType(entryTypeNum, entryTypeVersion);
	EntryInfo info = (EntryInfo) targetEntryMap.get(fromLogType);
	if (info == null) {
	    return false;
	} else {
	    targetCategory = info.targetCategory;
	    targetLogEntry = info.targetLogEntry;
	    return true;
	}
  }


  /**
   * 
   * This reader instantiates an LN and key for every LN entry.
   */
  // line 94 "../../../../CleanerFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    targetLogEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
	return true;
  }


  /**
   * 
   * @return true if the last entry was an IN.
   */
  // line 102 "../../../../CleanerFileReader.ump"
   public boolean isIN(){
    return (targetCategory == IS_IN);
  }


  /**
   * 
   * @return true if the last entry was a LN.
   */
  // line 109 "../../../../CleanerFileReader.ump"
   public boolean isLN(){
    return (targetCategory == IS_LN);
  }


  /**
   * 
   * @return true if the last entry was a root
   */
  // line 116 "../../../../CleanerFileReader.ump"
   public boolean isRoot(){
    return (targetCategory == IS_ROOT);
  }


  /**
   * 
   * Get the last LN seen by the reader.
   */
  // line 123 "../../../../CleanerFileReader.ump"
   public LN getLN(){
    return ((LNLogEntry) targetLogEntry).getLN();
  }


  /**
   * 
   * Get the last entry seen by the reader as an IN.
   */
  // line 130 "../../../../CleanerFileReader.ump"
   public IN getIN() throws DatabaseException{
    return ((INLogEntry) targetLogEntry).getIN(env);
  }


  /**
   * 
   * Get the last databaseId seen by the reader.
   */
  // line 137 "../../../../CleanerFileReader.ump"
   public DatabaseId getDatabaseId(){
    if (targetCategory == IS_LN) {
	    return ((LNLogEntry) targetLogEntry).getDbId();
	} else if (targetCategory == IS_IN) {
	    return ((INLogEntry) targetLogEntry).getDbId();
	} else {
	    return null;
	}
  }


  /**
   * 
   * Get the last key seen by the reader.
   */
  // line 150 "../../../../CleanerFileReader.ump"
   public byte[] getKey(){
    return ((LNLogEntry) targetLogEntry).getKey();
  }


  /**
   * 
   * Get the last key seen by the reader.
   */
  // line 157 "../../../../CleanerFileReader.ump"
   public byte[] getDupTreeKey(){
    return ((LNLogEntry) targetLogEntry).getDupKey();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 20 "../../../../CleanerFileReader.ump"
  private static final byte IS_IN = 0 ;
// line 22 "../../../../CleanerFileReader.ump"
  private static final byte IS_LN = 1 ;
// line 24 "../../../../CleanerFileReader.ump"
  private static final byte IS_ROOT = 2 ;
// line 26 "../../../../CleanerFileReader.ump"
  private Map targetEntryMap ;
// line 28 "../../../../CleanerFileReader.ump"
  private LogEntry targetLogEntry ;
// line 30 "../../../../CleanerFileReader.ump"
  private byte targetCategory ;

  
}