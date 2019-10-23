/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Level;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.File;

// line 3 "../../../../LastFileReader.ump"
// line 3 "../../../../Latches_LastFileReader.ump"
public class LastFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LastFileReader()
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
   * This file reader is always positioned at the last file.
   */
  // line 33 "../../../../LastFileReader.ump"
   public  LastFileReader(EnvironmentImpl env, int readBufferSize) throws IOException,DatabaseException{
    super(env, readBufferSize, true, DbLsn.NULL_LSN, new Long(-1), DbLsn.NULL_LSN, DbLsn.NULL_LSN);
	trackableEntries = new HashSet();
	lastOffsetSeen = new HashMap();
	lastValidOffset = 0;
	nextUnprovenOffset = 0;
	anticipateChecksumErrors = true;
  }


  /**
   * 
   * Ctor which allows passing in the file number we want to read to the end of.  This is used by the ScavengerFileReader when it encounters a bad log record in the middle of a file.
   */
  // line 46 "../../../../LastFileReader.ump"
   public  LastFileReader(EnvironmentImpl env, int readBufferSize, Long specificFileNumber) throws IOException,DatabaseException{
    super(env, readBufferSize, true, DbLsn.NULL_LSN, specificFileNumber, DbLsn.NULL_LSN, DbLsn.NULL_LSN);
	trackableEntries = new HashSet();
	lastOffsetSeen = new HashMap();
	lastValidOffset = 0;
	nextUnprovenOffset = 0;
	anticipateChecksumErrors = true;
  }


  /**
   * 
   * Override so that we always start at the last file.
   */
  // line 58 "../../../../LastFileReader.ump"
   protected void initStartingPosition(long endOfFileLsn, Long singleFileNum) throws IOException,DatabaseException{
    eof = false;
	Long lastNum = ((singleFileNum != null) && (singleFileNum.longValue() >= 0)) ? singleFileNum
		: fileManager.getLastFileNum();
	FileHandle fileHandle = null;
	readBufferFileEnd = 0;
	long fileLen = 0;
	while ((fileHandle == null) && !eof) {
	    if (lastNum == null) {
		eof = true;
	    } else {
		try {
		    readBufferFileNum = lastNum.longValue();
		    fileHandle = fileManager.getFileHandle(readBufferFileNum);
		    fileLen = fileHandle.getFile().length();
		    if (fileLen <= FileManager.firstLogEntryOffset()) {
			lastNum = fileManager.getFollowingFileNum(lastNum.longValue(), false);
			Label477:
fileHandle.release();
	//original(fileHandle);
 //this.hook477(fileHandle);
			fileHandle = null;
		    }
		} catch (DatabaseException e) {
		    lastNum = attemptToMoveBadFile(e);
		    fileHandle = null;
		} finally {
		    Label478:
if (fileHandle != null) {
	    fileHandle.release();
	}
	//original(fileHandle);
 //this.hook478(fileHandle);
		}
	    }
	}
	nextEntryOffset = 0;
  }


  /**
   * 
   * Something is wrong with this file. If there is no data in this file (the header is <= the file header size) then move this last file aside and search the next "last" file. If the last file does have data in it, throw an exception back to the application, since we're not sure what to do now.
   */
  // line 92 "../../../../LastFileReader.ump"
   private Long attemptToMoveBadFile(DatabaseException origException) throws DatabaseException,IOException{
    String fileName = fileManager.getFullFileNames(readBufferFileNum)[0];
	File problemFile = new File(fileName);
	Long lastNum = null;
	if (problemFile.length() <= FileManager.firstLogEntryOffset()) {
	    fileManager.clear();
	    lastNum = fileManager.getFollowingFileNum(readBufferFileNum, false);
	    fileManager.renameFile(readBufferFileNum, FileManager.BAD_SUFFIX);
	} else {
	    throw origException;
	}
	return lastNum;
  }

  // line 106 "../../../../LastFileReader.ump"
   public void setEndOfFile() throws IOException,DatabaseException{
    fileManager.truncateLog(readBufferFileNum, nextUnprovenOffset);
  }


  /**
   * 
   * @return The LSN to be used for the next log entry.
   */
  // line 113 "../../../../LastFileReader.ump"
   public long getEndOfLog(){
    return DbLsn.makeLsn(readBufferFileNum, nextUnprovenOffset);
  }

  // line 117 "../../../../LastFileReader.ump"
   public long getLastValidLsn(){
    return DbLsn.makeLsn(readBufferFileNum, lastValidOffset);
  }

  // line 121 "../../../../LastFileReader.ump"
   public long getPrevOffset(){
    return lastValidOffset;
  }

  // line 125 "../../../../LastFileReader.ump"
   public LogEntryType getEntryType(){
    return entryType;
  }


  /**
   * 
   * Tell the reader that we are interested in these kind of entries.
   */
  // line 132 "../../../../LastFileReader.ump"
   public void setTargetType(LogEntryType type){
    trackableEntries.add(type);
  }


  /**
   * 
   * @return The last LSN seen in the log for this kind of entry, or null.
   */
  // line 139 "../../../../LastFileReader.ump"
   public long getLastSeen(LogEntryType type){
    Long typeNumber = (Long) lastOffsetSeen.get(type);
	if (typeNumber != null) {
	    return DbLsn.makeLsn(readBufferFileNum, typeNumber.longValue());
	} else {
	    return DbLsn.NULL_LSN;
	}
  }


  /**
   * 
   * Validate the checksum on each entry, see if we should remember the LSN of this entry.
   */
  // line 151 "../../../../LastFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer){
    entryBuffer.position(entryBuffer.position() + currentEntrySize);
	entryType = new LogEntryType(currentEntryTypeNum, currentEntryTypeVersion);
	if (trackableEntries.contains(entryType)) {
	    lastOffsetSeen.put(entryType, new Long(currentEntryOffset));
	}
	return true;
  }


  /**
   * 
   * readNextEntry will stop at a bad entry.
   * @return true if an element has been read.
   */
  // line 164 "../../../../LastFileReader.ump"
   public boolean readNextEntry() throws DatabaseException,IOException{
    boolean foundEntry = false;
	nextUnprovenOffset = nextEntryOffset;
	try {
	    foundEntry = super.readNextEntry();
	    lastValidOffset = currentEntryOffset;
	} catch (DbChecksumException e) {
	    Tracer.trace(Level.INFO, env,
		    "Found checksum exception while searching " + " for end of log. Last valid entry is at "
			    + DbLsn.toString(DbLsn.makeLsn(readBufferFileNum, lastValidOffset)) + " Bad entry is at "
			    + DbLsn.makeLsn(readBufferFileNum, currentEntryOffset));
	}
	return foundEntry;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 19 "../../../../LastFileReader.ump"
  private Set trackableEntries ;
// line 21 "../../../../LastFileReader.ump"
  private long nextUnprovenOffset ;
// line 23 "../../../../LastFileReader.ump"
  private long lastValidOffset ;
// line 25 "../../../../LastFileReader.ump"
  private LogEntryType entryType ;
// line 27 "../../../../LastFileReader.ump"
  private Map lastOffsetSeen ;

  
}