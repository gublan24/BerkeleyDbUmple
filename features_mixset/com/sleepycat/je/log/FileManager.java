/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.HexFormatter;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.Adler32;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
import java.util.zip.Checksum;
import java.util.Set;
import java.util.Random;
import java.util.Map;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Arrays;
import java.nio.channels.OverlappingFileLockException;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.ByteBuffer;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../FileManager.ump"
// line 3 "../../../../FileManager_static.ump"
// line 3 "../../../../EnvironmentLocking_FileManager.ump"
// line 3 "../../../../DiskFullErro_FileManager.ump"
// line 3 "../../../../FileHandleCache_FileManager.ump"
// line 3 "../../../../Statistics_FileManager.ump"
public class FileManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Set up the file cache and initialize the file manager to point to the beginning of the log.
   * @param configManager
   * @param dbEnvHomeenvironment home directory
   */
  // line 112 "../../../../FileManager.ump"
   public  FileManager(EnvironmentImpl envImpl, File dbEnvHome, boolean readOnly) throws DatabaseException{
    this.envImpl = envImpl;
	this.dbEnvHome = dbEnvHome;
	this.readOnly = readOnly;
	DbConfigManager configManager = envImpl.getConfigManager();
	maxFileSize = configManager.getLong(EnvironmentParams.LOG_FILE_MAX);
	this.hook456(configManager);
	Label467:
lockEnvironment(readOnly, false);
//	original(readOnly);
//

  Label457:
fileCache = new FileCache(configManager);
 
	//this.hook457(configManager);
	this.hook449(envImpl);
	if (!dbEnvHome.exists()) {
	    throw new LogException("Environment home " + dbEnvHome + " doesn't exist");
	}
	currentFileNum = 0L;
	nextAvailableLsn = DbLsn.makeLsn(currentFileNum, firstLogEntryOffset());
	lastUsedLsn = DbLsn.NULL_LSN;
	perFileLastUsedLsn = new HashMap();
	prevOffset = 0L;
	endOfLog = new LogEndFileDescriptor();
	forceNewFile = false;
	saveLastPosition();
	String stopOnWriteProp = System.getProperty("je.debug.stopOnWrite");
	if (stopOnWriteProp != null) {
	    stopOnWriteCount = Long.parseLong(stopOnWriteProp);
	}
	this.hook452(envImpl);
  }


  /**
   * 
   * Set the file manager's "end of log".
   * @param nextAvailableLsnLSN to be used for the next log entry
   * @param lastUsedLsnlast LSN to have a valid entry, may be null
   * @param prevOffsetvalue to use for the prevOffset of the next entry. If thebeginning of the file, this is 0.
   */
  // line 147 "../../../../FileManager.ump"
   public void setLastPosition(long nextAvailableLsn, long lastUsedLsn, long prevOffset){
    this.lastUsedLsn = lastUsedLsn;
	perFileLastUsedLsn.put(new Long(DbLsn.getFileNumber(lastUsedLsn)), new Long(lastUsedLsn));
	this.nextAvailableLsn = nextAvailableLsn;
	currentFileNum = DbLsn.getFileNumber(this.nextAvailableLsn);
	this.prevOffset = prevOffset;
	saveLastPosition();
  }

  // line 156 "../../../../FileManager.ump"
  public void saveLastPosition(){
    savedNextAvailableLsn = nextAvailableLsn;
	savedLastUsedLsn = lastUsedLsn;
	savedPrevOffset = prevOffset;
	savedForceNewFile = forceNewFile;
	savedCurrentFileNum = currentFileNum;
  }

  // line 164 "../../../../FileManager.ump"
  public void restoreLastPosition(){
    nextAvailableLsn = savedNextAvailableLsn;
	lastUsedLsn = savedLastUsedLsn;
	prevOffset = savedPrevOffset;
	forceNewFile = savedForceNewFile;
	currentFileNum = savedCurrentFileNum;
  }


  /**
   * 
   * May be used to disable sync at file end to speed unit tests. Must only be used for unit testing, since log corruption may result.
   */
  // line 175 "../../../../FileManager.ump"
   public void setSyncAtFileEnd(boolean sync){
    syncAtFileEnd = sync;
  }


  /**
   * 
   * public for cleaner.
   * @return the number of the first file in this environment.
   */
  // line 183 "../../../../FileManager.ump"
   public Long getFirstFileNum(){
    return getFileNum(true);
  }

  // line 187 "../../../../FileManager.ump"
   public boolean getReadOnly(){
    return readOnly;
  }


  /**
   * 
   * @return the number of the last file in this environment.
   */
  // line 194 "../../../../FileManager.ump"
   public Long getLastFileNum(){
    return getFileNum(false);
  }

  // line 198 "../../../../FileManager.ump"
   public long getCurrentFileNum(){
    return currentFileNum;
  }

  // line 202 "../../../../FileManager.ump"
   public void setIncludeDeletedFiles(boolean includeDeletedFiles){
    this.includeDeletedFiles = includeDeletedFiles;
  }


  /**
   * 
   * Get all JE file numbers.
   * @return an array of all JE file numbers.
   */
  // line 210 "../../../../FileManager.ump"
   public Long[] getAllFileNumbers(){
    String[] names = listFiles(JE_SUFFIXES);
	Long[] nums = new Long[names.length];
	for (int i = 0; i < nums.length; i += 1) {
	    nums[i] = getNumFromName(names[i]);
	}
	return nums;
  }


  /**
   * 
   * Get the next file number before/after currentFileNum.
   * @param currentFileNumthe file we're at right now. Note that it may not exist, ifit's been cleaned and renamed.
   * @param forwardif true, we want the next larger file, if false we want theprevious file
   * @return null if there is no following file, or if filenum doesn't exist
   */
  // line 225 "../../../../FileManager.ump"
   public Long getFollowingFileNum(long currentFileNum, boolean forward){
    String[] names = listFiles(JE_SUFFIXES);
	String searchName = getFileName(currentFileNum, JE_SUFFIX);
	int foundIdx = Arrays.binarySearch(names, searchName);
	boolean foundTarget = false;
	if (foundIdx >= 0) {
	    if (forward) {
		foundIdx++;
	    } else {
		foundIdx--;
	    }
	} else {
	    foundIdx = Math.abs(foundIdx + 1);
	    if (!forward) {
		foundIdx--;
	    }
	}
	if (forward && (foundIdx < names.length)) {
	    foundTarget = true;
	} else if (!forward && (foundIdx > -1)) {
	    foundTarget = true;
	}
	if (foundTarget) {
	    return getNumFromName(names[foundIdx]);
	} else {
	    return null;
	}
  }


  /**
   * 
   * @return true if there are any files at all.
   */
  // line 257 "../../../../FileManager.ump"
   public boolean filesExist(){
    String[] names = listFiles(JE_SUFFIXES);
	return (names.length != 0);
  }


  /**
   * 
   * Get the first or last file number in the set of je files.
   * @param firstif true, get the first file, else get the last file
   * @return the file number or null if no files exist
   */
  // line 267 "../../../../FileManager.ump"
   private Long getFileNum(boolean first){
    String[] names = listFiles(JE_SUFFIXES);
	if (names.length == 0) {
	    return null;
	} else {
	    int index = 0;
	    if (!first) {
		index = names.length - 1;
	    }
	    return getNumFromName(names[index]);
	}
  }


  /**
   * 
   * Get the file number from a file name.
   * @param thefile name
   * @return the file number
   */
  // line 285 "../../../../FileManager.ump"
   private Long getNumFromName(String fileName){
    String fileNumber = fileName.substring(0, fileName.indexOf("."));
	return new Long(Long.parseLong(fileNumber, 16));
  }


  /**
   * 
   * Find je files. Return names sorted in ascending fashion.
   * @param suffixwhich type of file we're looking for
   * @return array of file names
   */
  // line 295 "../../../../FileManager.ump"
  public String[] listFiles(String [] suffixes){
    String[] fileNames = dbEnvHome.list(new JEFileFilter(suffixes));
	Arrays.sort(fileNames);
	return fileNames;
  }


  /**
   * 
   * Find je files, flavor for unit test support.
   * @param suffixwhich type of file we're looking for
   * @return array of file names
   */
  // line 306 "../../../../FileManager.ump"
   public static  String[] listFiles(File envDirFile, String [] suffixes){
    String[] fileNames = envDirFile.list(new JEFileFilter(suffixes));
	Arrays.sort(fileNames);
	return fileNames;
  }


  /**
   * 
   * @return the full file name and path for the nth je file.
   */
  // line 315 "../../../../FileManager.ump"
  public String[] getFullFileNames(long fileNum){
    if (includeDeletedFiles) {
	    int nSuffixes = JE_AND_DEL_SUFFIXES.length;
	    String[] ret = new String[nSuffixes];
	    for (int i = 0; i < nSuffixes; i++) {
		ret[i] = getFullFileName(getFileName(fileNum, JE_AND_DEL_SUFFIXES[i]));
	    }
	    return ret;
	} else {
	    return new String[] { getFullFileName(getFileName(fileNum, JE_SUFFIX)) };
	}
  }


  /**
   * 
   * @return the full file name and path for the given file number and suffix.
   */
  // line 331 "../../../../FileManager.ump"
   public String getFullFileName(long fileNum, String suffix){
    return getFullFileName(getFileName(fileNum, suffix));
  }


  /**
   * 
   * @return the full file name and path for this file name.
   */
  // line 338 "../../../../FileManager.ump"
   private String getFullFileName(String fileName){
    return dbEnvHome + File.separator + fileName;
  }


  /**
   * 
   * @return the file name for the nth file.
   */
  // line 345 "../../../../FileManager.ump"
   public static  String getFileName(long fileNum, String suffix){
    return (HexFormatter.formatLong(fileNum).substring(10) + suffix);
  }


  /**
   * 
   * Rename this file to NNNNNNNN.suffix. If that file already exists, try NNNNNNNN.suffix.1, etc. Used for deleting files or moving corrupt files aside.
   * @param fileNumthe file we want to move
   * @param newSuffixthe new file suffix
   */
  // line 354 "../../../../FileManager.ump"
   public void renameFile(long fileNum, String newSuffix) throws DatabaseException,IOException{
    int repeatNum = 0;
	boolean renamed = false;
	while (!renamed) {
	    String generation = "";
	    if (repeatNum > 0) {
		generation = "." + repeatNum;
	    }
	    String newName = getFullFileName(getFileName(fileNum, newSuffix) + generation);
	    File targetFile = new File(newName);
	    if (targetFile.exists()) {
		repeatNum++;
	    } else {
		String oldFileName = getFullFileNames(fileNum)[0];
    Label458:
clearFileCache(fileNum);
		
//this.hook458(fileNum);
		File oldFile = new File(oldFileName);
		if (oldFile.renameTo(targetFile)) {
		    renamed = true;
		} else {
		    throw new LogException("Couldn't rename " + oldFileName + " to " + newName);
		}
	    }
	}
  }


  /**
   * 
   * Delete log file NNNNNNNN.
   * @param fileNumthe file we want to move
   */
  // line 384 "../../../../FileManager.ump"
   public void deleteFile(long fileNum) throws DatabaseException,IOException{
    String fileName = getFullFileNames(fileNum)[0];
  Label459:
clearFileCache(fileNum);

	//this.hook459(fileNum);
	File file = new File(fileName);
	boolean done = file.delete();
	if (!done) {
	    throw new LogException("Couldn't delete " + file);
	}
  }


  /**
   * 
   * Return a read only file handle that corresponds the this file number. Retrieve it from the cache or open it anew and validate the file header. This method takes a latch on this file, so that the file descriptor will be held in the cache as long as it's in use. When the user is done with the file, the latch must be released.
   * @param fileNumwhich file
   * @return the file handle for the existing or newly created file
   */
  // line 400 "../../../../FileManager.ump"
  public FileHandle getFileHandle(long fileNum) throws LogException,DatabaseException{
    try {
	    Long fileId = new Long(fileNum);
	    FileHandle fileHandle = null;
// Start of hook460
      Label460:
		  Label463:
      Label450:
      Label462:
fileHandle = fileCache.get(fileId);
	  if (fileHandle == null) // continue

      fileHandle = makeFileHandle(fileNum, FileMode.READ_MODE);
		  Label464:
fileCache.add(fileId, fileHandle);

      Label453:
			if (fileHandle.getFile() == null) {
					Label454:
			} 
			else {
					throw new ReturnObject(fileHandle);
			}
// End of hook460
					throw ReturnHack.returnObject;
			} catch (ReturnObject r) {
					return (FileHandle) r.value;
			}
  }

  // line 425 "../../../../FileManager.ump"
   private FileHandle makeFileHandle(long fileNum, FileMode mode) throws DatabaseException{
    String[] fileNames = getFullFileNames(fileNum);
	RandomAccessFile newFile = null;
	String fileName = null;
	try {
	    FileNotFoundException FNFE = null;
	    for (int i = 0; i < fileNames.length; i++) {
		fileName = fileNames[i];
		try {
		    newFile = new RandomAccessFile(fileName, mode.getModeValue());
		    break;
		} catch (FileNotFoundException e) {
		    if (FNFE == null) {
			FNFE = e;
		    }
		}
	    }
	    if (newFile == null) {
		throw FNFE;
	    }
	    boolean oldHeaderVersion = false;
	    if (newFile.length() == 0) {
		if (mode == FileMode.READWRITE_MODE) {
		    long lastLsn = DbLsn.longToLsn((Long) perFileLastUsedLsn.remove(new Long(fileNum - 1)));
		    long headerPrevOffset = 0;
		    if (lastLsn != DbLsn.NULL_LSN) {
			headerPrevOffset = DbLsn.getFileOffset(lastLsn);
		    }
		    FileHeader fileHeader = new FileHeader(fileNum, headerPrevOffset);
		    writeFileHeader(newFile, fileName, fileHeader);
		}
	    } else {
		oldHeaderVersion = readAndValidateFileHeader(newFile, fileName, fileNum);
	    }
	    return new FileHandle(newFile, fileName, envImpl, oldHeaderVersion);
	} catch (FileNotFoundException e) {
	    throw new LogFileNotFoundException("Couldn't open file " + fileName + ": " + e.getMessage());
	} catch (DbChecksumException e) {
	    closeFileInErrorCase(newFile);
	    throw new DbChecksumException(envImpl, "Couldn't open file " + fileName, e);
	} catch (Throwable t) {
	    closeFileInErrorCase(newFile);
	    throw new DatabaseException("Couldn't open file " + fileName + ": " + t, t);
	}
  }


  /**
   * 
   * Close this file and eat any exceptions. Used in catch clauses.
   */
  // line 474 "../../../../FileManager.ump"
   private void closeFileInErrorCase(RandomAccessFile file){
    try {
	    if (file != null) {
		file.close();
	    }
	} catch (IOException e) {
	}
  }


  /**
   * 
   * Read the given je log file and validate the header.
   * @throws DatabaseExceptionif the file header isn't valid
   * @return whether the file header has an old version number.
   */
  // line 489 "../../../../FileManager.ump"
   private boolean readAndValidateFileHeader(RandomAccessFile file, String fileName, long fileNum) throws DatabaseException,IOException{
    LogManager logManager = envImpl.getLogManager();
	LogEntry headerEntry = logManager.getLogEntry(DbLsn.makeLsn(fileNum, 0), file);
	FileHeader header = (FileHeader) headerEntry.getMainItem();
	return header.validate(fileName, fileNum);
  }


  /**
   * 
   * Write a proper file header to the given file.
   */
  // line 500 "../../../../FileManager.ump"
   private void writeFileHeader(RandomAccessFile file, String fileName, FileHeader header) throws DatabaseException,IOException{
    envImpl.checkIfInvalid();
	if (envImpl.mayNotWrite()) {
	    return;
	}
	int headerSize = header.getLogSize();
	int entrySize = headerSize + LogManager.HEADER_BYTES;
	ByteBuffer headerBuf = envImpl.getLogManager().putIntoBuffer(header, headerSize, 0, false, entrySize);
	if (++writeCount >= stopOnWriteCount) {
	    Runtime.getRuntime().halt(0xff);
	}
	int bytesWritten;
	try {
	    if (RUNRECOVERY_EXCEPTION_TESTING) {
		generateRunRecoveryException(file, headerBuf, 0);
	    }
	    bytesWritten = writeToFile(file, headerBuf, 0);
	} catch (ClosedChannelException e) {
	    throw new RunRecoveryException(envImpl, "Channel closed, may be due to thread interrupt", e);
	} catch (IOException e) {
	    throw new RunRecoveryException(envImpl, "IOException caught: " + e);
	}
	if (bytesWritten != entrySize) {
	    throw new LogException("File " + fileName + " was created with an incomplete header. Only " + bytesWritten
		    + " bytes were written.");
	}
  }


  /**
   * 
   * @return the prevOffset field stored in the file header.
   */
  // line 531 "../../../../FileManager.ump"
  public long getFileHeaderPrevOffset(long fileNum) throws IOException,DatabaseException{
    LogEntry headerEntry = envImpl.getLogManager().getLogEntry(DbLsn.makeLsn(fileNum, 0));
	FileHeader header = (FileHeader) headerEntry.getMainItem();
	return header.getLastEntryInPrevFileOffset();
  }


  /**
   * 
   * @return the file offset of the last LSN that was used. For constructingthe headers of log entries. If the last LSN that was used was in a previous file, or this is the very first LSN of the whole system, return 0.
   */
  // line 540 "../../../../FileManager.ump"
  public long getPrevEntryOffset(){
    return prevOffset;
  }


  /**
   * 
   * Increase the current log position by "size" bytes. Move the prevOffset pointer along.
   * @param sizeis an unsigned int
   * @return true if we flipped to the next log file.
   */
  // line 549 "../../../../FileManager.ump"
  public boolean bumpLsn(long size){
    saveLastPosition();
	boolean flippedFiles = false;
	if (forceNewFile || (DbLsn.getFileOffset(nextAvailableLsn) + size) > maxFileSize) {
	    forceNewFile = false;
	    currentFileNum++;
	    if (lastUsedLsn != DbLsn.NULL_LSN) {
		perFileLastUsedLsn.put(new Long(DbLsn.getFileNumber(lastUsedLsn)), new Long(lastUsedLsn));
	    }
	    prevOffset = 0;
	    lastUsedLsn = DbLsn.makeLsn(currentFileNum, firstLogEntryOffset());
	    flippedFiles = true;
	} else {
	    if (lastUsedLsn == DbLsn.NULL_LSN) {
		prevOffset = 0;
	    } else {
		prevOffset = DbLsn.getFileOffset(lastUsedLsn);
	    }
	    lastUsedLsn = nextAvailableLsn;
	}
	nextAvailableLsn = DbLsn.makeLsn(DbLsn.getFileNumber(lastUsedLsn), (DbLsn.getFileOffset(lastUsedLsn) + size));
	return flippedFiles;
  }


  /**
   * 
   * Write out a log buffer to the file.
   * @param fullBufferbuffer to write
   */
  // line 577 "../../../../FileManager.ump"
  public void writeLogBuffer(LogBuffer fullBuffer) throws DatabaseException{
    envImpl.checkIfInvalid();
	if (envImpl.mayNotWrite()) {
	    return;
	}
	long firstLsn = fullBuffer.getFirstLsn();
	if (firstLsn != DbLsn.NULL_LSN) {
	    RandomAccessFile file = endOfLog.getWritableFile(DbLsn.getFileNumber(firstLsn));
	    ByteBuffer data = fullBuffer.getDataBuffer();
	    if (++writeCount >= stopOnWriteCount) {
		Runtime.getRuntime().halt(0xff);
	    }
	    try {
	//	this.hook465(fullBuffer, firstLsn, file);
Label465:
assert fullBuffer.getRewriteAllowed() || (DbLsn.getFileOffset(firstLsn) >= file.length()
		|| file.length() == firstLogEntryOffset()) : "FileManager would overwrite non-empty file 0x"
			+ Long.toHexString(DbLsn.getFileNumber(firstLsn)) + " lsnOffset=0x"
			+ Long.toHexString(DbLsn.getFileOffset(firstLsn)) + " fileLength=0x"
			+ Long.toHexString(file.length());
//	original(fullBuffer, firstLsn, file);

		if (IO_EXCEPTION_TESTING) {
		    throw new IOException("generated for testing");
		}
		if (RUNRECOVERY_EXCEPTION_TESTING) {
		    generateRunRecoveryException(file, data, DbLsn.getFileOffset(firstLsn));
		}
		writeToFile(file, data, DbLsn.getFileOffset(firstLsn));
	    } catch (ClosedChannelException e) {
		throw new RunRecoveryException(envImpl, "File closed, may be due to thread interrupt", e);
	    } catch (IOException IOE) {
		abortCommittedTxns(data);
    Label466:
try {
	      if (IO_EXCEPTION_TESTING) {
          throw new IOException("generated for testing");
	      }
	    writeToFile(file, data, DbLsn.getFileOffset(firstLsn));
	    } 
catch (IOException IOE2) {
	    fullBuffer.setRewriteAllowed();
	    throw new DatabaseException(IOE2);
	}
	if (false) //THIS IS DEAD CODE
//	    original(fullBuffer, firstLsn, file, data, IOE);
	throw new DatabaseException(IOE);

	//	this.hook466(fullBuffer, firstLsn, file, data, IOE); 
	    }
	    assert EnvironmentImpl.maybeForceYield();
	}
  }


  /**
   * 
   * Write a buffer to a file at a given offset, using NIO if so configured.
   */
  // line 614 "../../../../FileManager.ump"
   private int writeToFile(RandomAccessFile file, ByteBuffer data, long destOffset) throws IOException,DatabaseException{
    return new FileManager_writeToFile(this, file, data, destOffset).execute();
  }


  /**
   * 
   * Read a buffer from a file at a given offset, using NIO if so configured.
   */
  // line 621 "../../../../FileManager.ump"
  public void readFromFile(RandomAccessFile file, ByteBuffer readBuffer, long offset) throws IOException{
    new FileManager_readFromFile(this, file, readBuffer, offset).execute();
  }

  // line 625 "../../../../FileManager.ump"
   private void abortCommittedTxns(ByteBuffer data){
    final byte commitType = LogEntryType.LOG_TXN_COMMIT.getTypeNum();
	final byte abortType = LogEntryType.LOG_TXN_ABORT.getTypeNum();
//	this.hook461(data);
  Label461:
data.position(0);

	while (data.remaining() > 0) {
	    int recStartPos = data.position();
	    data.position(recStartPos + LogManager.HEADER_ENTRY_TYPE_OFFSET);
	    int typePos = data.position();
	    byte entryType = data.get();
	    boolean recomputeChecksum = false;
	    if (entryType == commitType) {
		data.position(typePos);
		data.put(abortType);
		recomputeChecksum = true;
	    }
	    byte version = data.get();
	    data.position(data.position() + LogManager.PREV_BYTES);
	    int itemSize = LogUtils.readInt(data);
	    int itemDataStartPos = data.position();
	    if (recomputeChecksum) {
		Checksum checksum = Adler32.makeChecksum();
		data.position(recStartPos);
		int nChecksumBytes = itemSize + (LogManager.HEADER_BYTES - LogManager.CHECKSUM_BYTES);
		byte[] checksumBytes = new byte[nChecksumBytes];
		System.arraycopy(data.array(), recStartPos + LogManager.CHECKSUM_BYTES, checksumBytes, 0,
			nChecksumBytes);
		checksum.update(checksumBytes, 0, nChecksumBytes);
		LogUtils.writeUnsignedInt(data, checksum.getValue());
	    }
	    data.position(itemDataStartPos + itemSize);
	}
	data.position(0);
  }


  /**
   * 
   * FSync the end of the log.
   */
  // line 663 "../../../../FileManager.ump"
  public void syncLogEnd() throws DatabaseException{
    try {
	    endOfLog.force();
	} catch (IOException e) {
	    throw new DatabaseException(e);
	}
  }


  /**
   * 
   * Sync the end of the log, close off this log file. Should only be called under the log write latch.
   */
  // line 674 "../../../../FileManager.ump"
  public void syncLogEndAndFinishFile() throws DatabaseException,IOException{
    if (syncAtFileEnd) {
	    syncLogEnd();
	}
	endOfLog.close();
  }


  /**
   * 
   * Close all file handles and empty the cache.
   */
  // line 684 "../../../../FileManager.ump"
   public void clear() throws IOException,DatabaseException{
    // line 76 "../../../../FileHandleCache_FileManager.ump"
    fileCache.clear();
    // END OF UMPLE BEFORE INJECTION
    endOfLog.close();
  }


  /**
   * 
   * Clear the file lock.
   */
  // line 691 "../../../../FileManager.ump"
   public void close() throws IOException,DatabaseException{
    // line 77 "../../../../EnvironmentLocking_FileManager.ump"
    if (envLock != null) {
    	    envLock.release();
    	}
    	if (exclLock != null) {
    	    exclLock.release();
    	}
    	if (channel != null) {
    	    channel.close();
    	}
    	if (lockFile != null) {
    	    lockFile.close();
    	}
    // END OF UMPLE BEFORE INJECTION
    
  }


  /**
   * 
   * Ensure that if the environment home dir is on readonly media or in a readonly directory that the environment has been opened for readonly access.
   * @return true if the environment home dir is readonly.
   */
  // line 698 "../../../../FileManager.ump"
   private boolean checkEnvHomePermissions(boolean readOnly) throws DatabaseException{
    boolean envDirIsReadOnly = !dbEnvHome.canWrite();
	if (envDirIsReadOnly && !readOnly) {
	    throw new DatabaseException("The Environment directory " + dbEnvHome + " is not writable, but the "
		    + "Environment was opened for read-write access.");
	}
	return envDirIsReadOnly;
  }


  /**
   * 
   * Truncate a log at this position. Used by recovery to a timestamp utilities and by recovery to set the end-of-log position. <p> This method forces a new log file to be written next, if the last file (the file truncated to) has an old version in its header. This ensures that when the log is opened by an old version of JE, a version incompatibility will be detected. [#11243] </p>
   */
  // line 710 "../../../../FileManager.ump"
   public void truncateLog(long fileNum, long offset) throws IOException,DatabaseException{
    FileHandle handle = makeFileHandle(fileNum, FileMode.READWRITE_MODE);
	RandomAccessFile file = handle.getFile();
	try {
	    file.getChannel().truncate(offset);
	} finally {
	    file.close();
	}
	if (handle.isOldHeaderVersion()) {
	    forceNewFile = true;
	}
  }


  /**
   * 
   * Set the flag that causes a new file to be written before the next write.
   */
  // line 726 "../../../../FileManager.ump"
  public void forceNewLogFile(){
    forceNewFile = true;
  }


  /**
   * 
   * @return the size in bytes of the file header log entry.
   */
  // line 733 "../../../../FileManager.ump"
   public static  int firstLogEntryOffset(){
    return FileHeader.entrySize() + LogManager.HEADER_BYTES;
  }


  /**
   * 
   * Return the next available LSN in the log. Note that this is unsynchronized, so is only valid as an approximation of log size.
   */
  // line 740 "../../../../FileManager.ump"
   public long getNextLsn(){
    return nextAvailableLsn;
  }


  /**
   * 
   * Return the last allocated LSN in the log. Note that this is unsynchronized, so if it is called outside the log write latch it is only valid as an approximation of log size.
   */
  // line 747 "../../../../FileManager.ump"
   public long getLastUsedLsn(){
    return lastUsedLsn;
  }

  // line 752 "../../../../FileManager.ump"
   private void generateRunRecoveryException(RandomAccessFile file, ByteBuffer data, long destOffset) throws DatabaseException,IOException{
    if (runRecoveryExceptionThrown) {
	    try {
		throw new Exception("Write after RunRecoveryException");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	runRecoveryExceptionCounter += 1;
	if (runRecoveryExceptionCounter >= RUNRECOVERY_EXCEPTION_MAX) {
	    runRecoveryExceptionCounter = 0;
	}
	if (runRecoveryExceptionRandom == null) {
	    runRecoveryExceptionRandom = new Random(System.currentTimeMillis());
	}
	if (runRecoveryExceptionCounter == runRecoveryExceptionRandom.nextInt(RUNRECOVERY_EXCEPTION_MAX)) {
	    int len = runRecoveryExceptionRandom.nextInt(data.remaining());
	    if (len > 0) {
		byte[] a = new byte[len];
		data.get(a, 0, len);
		ByteBuffer buf = ByteBuffer.wrap(a);
		writeToFile(file, buf, destOffset);
	    }
	    runRecoveryExceptionThrown = true;
	    throw new RunRecoveryException(envImpl, "Randomly generated for testing");
	}
  }

  // line 780 "../../../../FileManager.ump"
   protected void hook449(EnvironmentImpl envImpl) throws DatabaseException{
    
  }

  // line 784 "../../../../FileManager.ump"
   protected FileHandle hook450(long fileNum, Long fileId, FileHandle fileHandle) throws LogException,DatabaseException{
    fileHandle = this.hook462(fileNum, fileId, fileHandle);
	return fileHandle;
  }

  // line 789 "../../../../FileManager.ump"
   protected void hook452(EnvironmentImpl envImpl) throws DatabaseException{
    
  }


  /**
   * protected void hook453(FileHandle fileHandle) throws LogException, DatabaseException {
   * }
   */
  // line 795 "../../../../FileManager.ump"
   protected void hook454(FileHandle fileHandle) throws LogException,DatabaseException{
    
  }

  // line 798 "../../../../FileManager.ump"
   protected void hook456(DbConfigManager configManager) throws DatabaseException{
    
  }

  // line 801 "../../../../FileManager.ump"
   protected void hook457(DbConfigManager configManager) throws DatabaseException{
    
  }

  // line 804 "../../../../FileManager.ump"
   protected void hook458(long fileNum) throws DatabaseException,IOException{
    
  }

  // line 807 "../../../../FileManager.ump"
   protected void hook459(long fileNum) throws DatabaseException,IOException{
    
  }


  /**
   * protected void hook460(long fileNum, Long fileId, FileHandle fileHandle) throws LogException, DatabaseException {
   * fileHandle = this.hook463(fileNum, fileId, fileHandle);
   * this.hook453(fileHandle);
   * if (fileHandle.getFile() == null) {
   * this.hook454(fileHandle);
   * } else {
   * throw new ReturnObject(fileHandle);
   * }
   * }
   * protected void hook461(ByteBuffer data) {
   * }
   * protected FileHandle hook462(long fileNum, Long fileId, FileHandle fileHandle)
   * throws LogException, DatabaseException {
   * fileHandle = makeFileHandle(fileNum, FileMode.READ_MODE);
   * this.hook464(fileId, fileHandle);
   * return fileHandle;
   * }
   * protected FileHandle hook463(long fileNum, Long fileId, FileHandle fileHandle)
   * throws LogException, DatabaseException {
   * fileHandle = this.hook450(fileNum, fileId, fileHandle);
   * return fileHandle;
   * }
   * protected void hook464(Long fileId, FileHandle fileHandle) throws LogException, DatabaseException {
   * }
   */
  // line 840 "../../../../FileManager.ump"
   protected void hook465(LogBuffer fullBuffer, long firstLsn, RandomAccessFile file) throws DatabaseException,ClosedChannelException,IOException{
    
  }


  /**
   * protected void hook466(LogBuffer fullBuffer, long firstLsn, RandomAccessFile file, ByteBuffer data, IOException IOE)
   * throws DatabaseException {
   * throw new DatabaseException(IOE);
   * }
   */
  // line 848 "../../../../FileManager.ump"
   protected void hook467(boolean readOnly) throws DatabaseException{
    
  }


  /**
   * 
   * Lock the environment. Return true if the lock was acquired. If exclusive is false, then this implements a single writer, multiple reader lock. If exclusive is true, then implement an exclusive lock. There is a lock file and there are two regions of the lock file: byte 0, and byte 1. Byte 0 is the exclusive writer process area of the lock file. If an environment is opened for write, then it attempts to take an exclusive write lock on byte 0. Byte 1 is the shared reader process area of the lock file. If an environment is opened for read-only, then it attempts to take a shared lock on byte 1. This is how we implement single writer, multi reader semantics. The cleaner, each time it is invoked, attempts to take an exclusive lock on byte 1. The owning process already either has an exclusive lock on byte 0, or a shared lock on byte 1. This will necessarily conflict with any shared locks on byte 1, even if it's in the same process and there are no other holders of that shared lock. So if there is only one read-only process, it will have byte 1 for shared access, and the cleaner can not run in it because it will attempt to get an exclusive lock on byte 1 (which is already locked for shared access by itself). If a write process comes along and tries to run the cleaner, it will attempt to get an exclusive lock on byte 1. If there are no other reader processes (with shared locks on byte 1), and no other writers (which are running cleaners on with exclusive locks on byte 1), then the cleaner will run.
   */
  // line 17 "../../../../EnvironmentLocking_FileManager.ump"
   public boolean lockEnvironment(boolean readOnly, boolean exclusive) throws DatabaseException{
    try {
	    if (checkEnvHomePermissions(readOnly)) {
		return true;
	    }
	    if (lockFile == null) {
		lockFile = new RandomAccessFile(new File(dbEnvHome, "je" + LOCK_SUFFIX), "rw");
	    }
	    channel = lockFile.getChannel();
	    boolean throwIt = false;
	    try {
		if (exclusive) {
		    exclLock = channel.tryLock(1, 2, false);
		    if (exclLock == null) {
			return false;
		    }
		    return true;
		} else {
		    if (readOnly) {
			envLock = channel.tryLock(1, 2, true);
		    } else {
			envLock = channel.tryLock(0, 1, false);
		    }
		    if (envLock == null) {
			throwIt = true;
		    }
		}
	    } catch (OverlappingFileLockException e) {
		throwIt = true;
	    }
	    if (throwIt) {
		throw new LogException(
			"A je" + LOCK_SUFFIX + "file exists in " + dbEnvHome + " The environment can not be locked for "
				+ (readOnly ? "shared" : "single writer") + " access.");
	    }
	} catch (IOException IOE) {
	    throw new LogException(IOE.toString());
	}
	return true;
  }

  // line 58 "../../../../EnvironmentLocking_FileManager.ump"
   public void releaseExclusiveLock() throws DatabaseException{
    try {
	    if (exclLock != null) {
		exclLock.release();
	    }
	} catch (IOException IOE) {
	    throw new DatabaseException(IOE);
	}
  }

  // line 8 "../../../../FileHandleCache_FileManager.ump"
  public Set getCacheKeys(){
    return fileCache.getCacheKeys();
  }


  /**
   * 
   * Clear a file out of the file cache regardless of mode type.
   */
  // line 15 "../../../../FileHandleCache_FileManager.ump"
   private void clearFileCache(long fileNum) throws IOException,DatabaseException{
    fileCache.remove(fileNum);
  }

  // line 50 "../../../../FileHandleCache_FileManager.ump"
   protected void hook460(long fileNum, Long fileId, FileHandle fileHandle) throws LogException,DatabaseException{
    while (true) {
			  original(fileNum, fileId, fileHandle);
			}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../FileManager_static.ump"
  public static class FileMode
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileMode()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 9 "../../../../FileManager_static.ump"
     private  FileMode(String fileModeValue){
      this.fileModeValue=fileModeValue;
    }
  
    // line 12 "../../../../FileManager_static.ump"
     public String getModeValue(){
      return fileModeValue;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../FileManager_static.ump"
    public static final FileMode READ_MODE=new FileMode("r") ;
  // line 6 "../../../../FileManager_static.ump"
    public static final FileMode READWRITE_MODE=new FileMode("rw") ;
  // line 7 "../../../../FileManager_static.ump"
    private String fileModeValue ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 15 "../../../../FileManager_static.ump"
  public class LogEndFileDescriptor
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public LogEndFileDescriptor()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
  
    /**
     * 
     * getWritableFile must be called under the log write latch.
     */
    // line 22 "../../../../FileManager_static.ump"
    public RandomAccessFile getWritableFile(long fileNumber) throws RunRecoveryException{
      try {
            if (endOfLogRWFile == null) {
              endOfLogRWFile=makeFileHandle(fileNumber,FileMode.READWRITE_MODE).getFile();
              endOfLogSyncFile=makeFileHandle(fileNumber,FileMode.READWRITE_MODE).getFile();
            }
            return endOfLogRWFile;
          }
     catch (      Exception e) {
            throw new RunRecoveryException(envImpl,e);
          }
    }
  
  
    /**
     * 
     * FSync the log file that makes up the end of the log.
     */
    // line 37 "../../../../FileManager_static.ump"
    public void force() throws DatabaseException,IOException{
      RandomAccessFile file=endOfLogSyncFile;
          if (file != null) {
            FileChannel channel=file.getChannel();
            try {
              channel.force(false);
            }
     catch (        ClosedChannelException e) {
              throw new RunRecoveryException(envImpl,"Channel closed, may be due to thread interrupt",e);
            }
            assert EnvironmentImpl.maybeForceYield();
          }
    }
  
  
    /**
     * 
     * Close the end of the log file descriptor. Use atomic assignment to
     * ensure that we won't force and close on the same descriptor.
     */
    // line 54 "../../../../FileManager_static.ump"
    public void close() throws IOException{
      IOException firstException=null;
          if (endOfLogRWFile != null) {
            RandomAccessFile file=endOfLogRWFile;
            endOfLogRWFile=null;
            try {
              file.close();
            }
     catch (        IOException e) {
              firstException=e;
            }
          }
          if (endOfLogSyncFile != null) {
            RandomAccessFile file=endOfLogSyncFile;
            endOfLogSyncFile=null;
            file.close();
          }
          if (firstException != null) {
            throw firstException;
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 16 "../../../../FileManager_static.ump"
    private RandomAccessFile endOfLogRWFile=null ;
  // line 17 "../../../../FileManager_static.ump"
    private RandomAccessFile endOfLogSyncFile=null ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 75 "../../../../FileManager_static.ump"
  public static class FileManager_writeToFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileManager_writeToFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 77 "../../../../FileManager_static.ump"
    public  FileManager_writeToFile(FileManager _this, RandomAccessFile file, ByteBuffer data, long destOffset){
      this._this=_this;
          this.file=file;
          this.data=data;
          this.destOffset=destOffset;
    }
  
    // line 83 "../../../../FileManager_static.ump"
    public int execute() throws IOException,DatabaseException{
      totalBytesWritten=0;
          this.hook455();
          this.hook445();
          return totalBytesWritten;
    }
  
    // line 100 "../../../../FileManager_static.ump"
     protected void hook445() throws IOException,DatabaseException{
      
    }
  
    // line 102 "../../../../FileManager_static.ump"
     protected void hook455() throws IOException,DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 88 "../../../../FileManager_static.ump"
    protected FileManager _this ;
  // line 89 "../../../../FileManager_static.ump"
    protected RandomAccessFile file ;
  // line 90 "../../../../FileManager_static.ump"
    protected ByteBuffer data ;
  // line 91 "../../../../FileManager_static.ump"
    protected long destOffset ;
  // line 92 "../../../../FileManager_static.ump"
    protected int totalBytesWritten ;
  // line 93 "../../../../FileManager_static.ump"
    protected FileChannel channel ;
  // line 94 "../../../../FileManager_static.ump"
    protected ByteBuffer useData ;
  // line 95 "../../../../FileManager_static.ump"
    protected int origLimit ;
  // line 96 "../../../../FileManager_static.ump"
    protected int bytesWritten ;
  // line 97 "../../../../FileManager_static.ump"
    protected int pos ;
  // line 98 "../../../../FileManager_static.ump"
    protected int size ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 104 "../../../../FileManager_static.ump"
  public static class FileManager_readFromFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileManager_readFromFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 106 "../../../../FileManager_static.ump"
    public  FileManager_readFromFile(FileManager _this, RandomAccessFile file, ByteBuffer readBuffer, long offset){
      this._this=_this;
          this.file=file;
          this.readBuffer=readBuffer;
          this.offset=offset;
    }
  
    // line 112 "../../../../FileManager_static.ump"
    public void execute() throws IOException{
      this.hook446();
    }
  
    // line 126 "../../../../FileManager_static.ump"
     protected void hook446() throws IOException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 114 "../../../../FileManager_static.ump"
    protected FileManager _this ;
  // line 115 "../../../../FileManager_static.ump"
    protected RandomAccessFile file ;
  // line 116 "../../../../FileManager_static.ump"
    protected ByteBuffer readBuffer ;
  // line 117 "../../../../FileManager_static.ump"
    protected long offset ;
  // line 118 "../../../../FileManager_static.ump"
    protected FileChannel channel ;
  // line 119 "../../../../FileManager_static.ump"
    protected int readLength ;
  // line 120 "../../../../FileManager_static.ump"
    protected long currentPosition ;
  // line 121 "../../../../FileManager_static.ump"
    protected int bytesRead1 ;
  // line 122 "../../../../FileManager_static.ump"
    protected int pos ;
  // line 123 "../../../../FileManager_static.ump"
    protected int size ;
  // line 124 "../../../../FileManager_static.ump"
    protected int bytesRead2 ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 33 "../../../../FileManager.ump"
  static boolean IO_EXCEPTION_TESTING = false ;
// line 35 "../../../../FileManager.ump"
  private static final String DEBUG_NAME = FileManager.class.getName() ;
// line 37 "../../../../FileManager.ump"
  private static long writeCount = 0 ;
// line 39 "../../../../FileManager.ump"
  private static long stopOnWriteCount = Long.MAX_VALUE ;
// line 41 "../../../../FileManager.ump"
  public static final String JE_SUFFIX = ".jdb" ;
// line 43 "../../../../FileManager.ump"
  public static final String CIF_SUFFIX = ".cif" ;
// line 45 "../../../../FileManager.ump"
  public static final String DEL_SUFFIX = ".del" ;
// line 47 "../../../../FileManager.ump"
  public static final String BAD_SUFFIX = ".bad" ;
// line 49 "../../../../FileManager.ump"
  public static final String LOCK_SUFFIX = ".lck" ;
// line 51 "../../../../FileManager.ump"
  static final String[] DEL_SUFFIXES = {DEL_SUFFIX};
// line 53 "../../../../FileManager.ump"
  static final String[] JE_SUFFIXES = {JE_SUFFIX};
// line 55 "../../../../FileManager.ump"
  private static final String[] JE_AND_DEL_SUFFIXES = {JE_SUFFIX, DEL_SUFFIX};
// line 57 "../../../../FileManager.ump"
  private boolean syncAtFileEnd = true ;
// line 59 "../../../../FileManager.ump"
  private EnvironmentImpl envImpl ;
// line 61 "../../../../FileManager.ump"
  private long maxFileSize ;
// line 63 "../../../../FileManager.ump"
  private File dbEnvHome ;
// line 65 "../../../../FileManager.ump"
  private boolean includeDeletedFiles = false ;
// line 67 "../../../../FileManager.ump"
  private boolean readOnly ;
// line 69 "../../../../FileManager.ump"
  private long currentFileNum ;
// line 71 "../../../../FileManager.ump"
  private long nextAvailableLsn ;
// line 73 "../../../../FileManager.ump"
  private long lastUsedLsn ;
// line 75 "../../../../FileManager.ump"
  private long prevOffset ;
// line 77 "../../../../FileManager.ump"
  private boolean forceNewFile ;
// line 79 "../../../../FileManager.ump"
  private long savedCurrentFileNum ;
// line 81 "../../../../FileManager.ump"
  private long savedNextAvailableLsn ;
// line 83 "../../../../FileManager.ump"
  private long savedLastUsedLsn ;
// line 85 "../../../../FileManager.ump"
  private long savedPrevOffset ;
// line 87 "../../../../FileManager.ump"
  private boolean savedForceNewFile ;
// line 89 "../../../../FileManager.ump"
  private LogEndFileDescriptor endOfLog ;
// line 91 "../../../../FileManager.ump"
  private Map perFileLastUsedLsn ;
// line 96 "../../../../FileManager.ump"
  static boolean RUNRECOVERY_EXCEPTION_TESTING = false ;
// line 98 "../../../../FileManager.ump"
  private static final int RUNRECOVERY_EXCEPTION_MAX = 100 ;
// line 100 "../../../../FileManager.ump"
  private int runRecoveryExceptionCounter = 0 ;
// line 102 "../../../../FileManager.ump"
  private boolean runRecoveryExceptionThrown = false ;
// line 104 "../../../../FileManager.ump"
  private Random runRecoveryExceptionRandom = null ;
// line 5 "../../../../EnvironmentLocking_FileManager.ump"
  private RandomAccessFile lockFile ;
// line 7 "../../../../EnvironmentLocking_FileManager.ump"
  private FileChannel channel ;
// line 9 "../../../../EnvironmentLocking_FileManager.ump"
  private FileLock envLock ;
// line 11 "../../../../EnvironmentLocking_FileManager.ump"
  private FileLock exclLock ;
// line 5 "../../../../FileHandleCache_FileManager.ump"
  private FileCache fileCache ;

  
}