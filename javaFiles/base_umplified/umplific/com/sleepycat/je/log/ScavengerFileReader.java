/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.HashSet;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../ScavengerFileReader.ump"
public class ScavengerFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ScavengerFileReader()
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
   */
  // line 26 "../../../../ScavengerFileReader.ump"
   public  ScavengerFileReader(EnvironmentImpl env, int readBufferSize, long startLsn, long finishLsn, long endOfFileLsn) throws IOException,DatabaseException{
    super(env, readBufferSize, false, startLsn, null, endOfFileLsn, finishLsn);
	this.readBufferSize = readBufferSize;
	anticipateChecksumErrors = true;
	targetEntryTypes = new HashSet();
	dumpCorruptedBounds = false;
  }


  /**
   * 
   * Set to true if corrupted boundaries should be dumped to stderr.
   */
  // line 37 "../../../../ScavengerFileReader.ump"
   public void setDumpCorruptedBounds(boolean dumpCorruptedBounds){
    this.dumpCorruptedBounds = dumpCorruptedBounds;
  }


  /**
   * 
   * Tell the reader that we are interested in these kind of entries.
   */
  // line 44 "../../../../ScavengerFileReader.ump"
   public void setTargetType(LogEntryType type){
    targetEntryTypes.add(new Byte(type.getTypeNum()));
  }

  // line 48 "../../../../ScavengerFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    LogEntryType lastEntryType = LogEntryType.findType(currentEntryTypeNum, currentEntryTypeVersion);
	LogEntry entry = lastEntryType.getSharedLogEntry();
	entry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
	processEntryCallback(entry, lastEntryType);
	return true;
  }

  // line 58 "../../../../ScavengerFileReader.ump"
   public boolean readNextEntry() throws DatabaseException,IOException{
    long saveCurrentEntryOffset = currentEntryOffset;
	try {
	    return super.readNextEntry();
	} catch (DbChecksumException DCE) {
	    resyncReader(DbLsn.makeLsn(readBufferFileNum, saveCurrentEntryOffset), dumpCorruptedBounds);
	    return super.readNextEntry();
	}
  }

  // line 69 "../../../../ScavengerFileReader.ump"
   protected boolean resyncReader(long nextGoodRecordPostCorruption, boolean showCorruptedBounds) throws DatabaseException,IOException{
    LastFileReader reader = null;
	long tryReadBufferFileNum = DbLsn.getFileNumber(nextGoodRecordPostCorruption);
	while (tryReadBufferFileNum >= 0) {
	    try {
		reader = new LastFileReader(env, readBufferSize, new Long(tryReadBufferFileNum));
		break;
	    } catch (DbChecksumException DCE) {
		tryReadBufferFileNum--;
		continue;
	    }
	}
	boolean switchedFiles = tryReadBufferFileNum != DbLsn.getFileNumber(nextGoodRecordPostCorruption);
	if (!switchedFiles) {
	    while (reader.readNextEntry()) {
	    }
	}
	long lastUsedLsn = reader.getLastValidLsn();
	long nextAvailableLsn = reader.getEndOfLog();
	if (showCorruptedBounds) {
	    System.err.println("A checksum error was found in the log.");
	    System.err.println("Corruption begins at LSN:\n   " + DbLsn.toString(nextAvailableLsn));
	    System.err
		    .println("Last known good record before corruption is at LSN:\n   " + DbLsn.toString(lastUsedLsn));
	    System.err.println("Next known good record after corruption is at LSN:\n   "
		    + DbLsn.toString(nextGoodRecordPostCorruption));
	}
	startLsn = lastUsedLsn;
	initStartingPosition(nextAvailableLsn, null);
	if (switchedFiles) {
	    currentEntryPrevOffset = 0;
	}
	return true;
  }


  /**
   * 
   * @return true if this reader should process this entry, or just skipover it.
   */
  // line 107 "../../../../ScavengerFileReader.ump"
   protected boolean isTargetEntry(byte logEntryTypeNumber, byte logEntryTypeVersion){
    if (targetEntryTypes.size() == 0) {
	    return true;
	} else {
	    return targetEntryTypes.contains(new Byte(logEntryTypeNumber));
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../ScavengerFileReader.ump"
  private Set targetEntryTypes ;
// line 17 "../../../../ScavengerFileReader.ump"
  private int readBufferSize ;
// line 19 "../../../../ScavengerFileReader.ump"
  private boolean dumpCorruptedBounds ;
// line 55 "../../../../ScavengerFileReader.ump"
  abstract protected void processEntryCallback(LogEntry entry, LogEntryType entryType) throws DatabaseException ;

  
}