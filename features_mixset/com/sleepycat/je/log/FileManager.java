/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.Checksum;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.latch.Latch;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.utilint.Adler32;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.HexFormatter;

/**
 * 
 * The FileManager presents the abstraction of one contiguous file.  It doles
 * out LSNs.
 */
// line 18 "../../../../FileManager.ump"
// line 4 "../../../../FileManager_static.ump"
// line 3 "../../../../Latches_FileManager.ump"
// line 3 "../../../../EnvironmentLocking_FileManager.ump"
// line 3 "../../../../FileHandleCache_FileManager.ump"
// line 3 "../../../../Statistics_FileManager.ump"
// line 3 "../../../../IO_FileManager.ump"
// line 3 "../../../../IO_FileManager_inner.ump"
// line 3 "../../../../ChunckedNIO_FileManager.ump"
// line 3 "../../../../NIO_FileManager.ump"
// line 3 "../../../../NIO_FileManager_inner.ump"
// line 3 "../../../../FSync_FileManager.ump"
// line 5 "../../../../Derivative_Latches_FileHandleCache_FileManager.ump"
// line 3 "../../../../Derivative_FSync_Statistics_FileManager.ump"
// line 2 "../../../../Derivative_IO_SynchronizedIO_FileManager_inner.ump"
// line 3 "../../../../Derivative_NIO_ChunkedNIO_FileManager_inner.ump"
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
   * If non-0, do NIO in chunks of this size.
   * 
   * Set up the file cache and initialize the file manager to point to the
   * beginning of the log.
   * 
   * @param configManager
   * @param dbEnvHome environment home directory
   */
  // line 155 "../../../../FileManager.ump"
   public  FileManager(EnvironmentImpl envImpl, File dbEnvHome, boolean readOnly) throws DatabaseException{
    this.envImpl = envImpl;
        this.dbEnvHome = dbEnvHome;
        this.readOnly = readOnly;

        /* Read configurations. */
        DbConfigManager configManager = envImpl.getConfigManager();
        maxFileSize = configManager.getLong(EnvironmentParams.LOG_FILE_MAX);

      //  useNIO =  configManager.getBoolean(EnvironmentParams.LOG_USE_NIO);
        Label456:
chunkedNIOSize =    configManager.getLong(EnvironmentParams.LOG_CHUNKED_NIO);
 ;

      //  boolean directNIO =     configManager.getBoolean(EnvironmentParams.LOG_DIRECT_NIO);
/*
        if (!useNIO && (chunkedNIOSize > 0 || directNIO)) {
            throw new IllegalArgumentException
                (EnvironmentParams.LOG_USE_NIO.getName() +
                 " is false and therefore " +
                 EnvironmentParams.LOG_DIRECT_NIO.getName() +
                 " or " +
                 EnvironmentParams.LOG_CHUNKED_NIO.getName() +
                 " may not be used.");
        }
        */

        Label467:
lockEnvironment(readOnly, false);

//
 ;

        /* Cache of files. */
        Label457:
fileCache = new FileCache(configManager);
 ; 
        Label449:
fileCacheLatch =LatchSupport.makeLatch(DEBUG_NAME + "_fileCache", envImpl);
 ;



        if (!dbEnvHome.exists()) {
            throw new LogException("Environment home " + dbEnvHome +
                                     " doesn't exist");
        }

        /* Start out as if no log existed. */
        currentFileNum = 0L;
        nextAvailableLsn = DbLsn.makeLsn(currentFileNum,
					 firstLogEntryOffset());
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

        Label452:
syncManager = new FSyncManager(envImpl);
 ;
  }


  /**
   * 
   * Set the file manager's "end of log".
   * 
   * @param nextAvailableLsn LSN to be used for the next log entry
   * @param lastUsedLsn last LSN to have a valid entry, may be null
   * @param prevOffset value to use for the prevOffset of the next entry.
   * If the beginning of the file, this is 0.
   */
  // line 223 "../../../../FileManager.ump"
   public void setLastPosition(long nextAvailableLsn, long lastUsedLsn, long prevOffset){
    this.lastUsedLsn = lastUsedLsn;
        perFileLastUsedLsn.put(new Long(DbLsn.getFileNumber(lastUsedLsn)),
                               new Long(lastUsedLsn));
        this.nextAvailableLsn = nextAvailableLsn;
        currentFileNum = DbLsn.getFileNumber(this.nextAvailableLsn);
        this.prevOffset = prevOffset;
	saveLastPosition();
  }


  /**
   * Cause the current LSN state to be saved in case we fail after we have
   * bumped the lsn pointer but before we've successfully marshalled into the
   * log buffer.
   */
  // line 238 "../../../../FileManager.ump"
  public void saveLastPosition(){
    savedNextAvailableLsn = nextAvailableLsn;
	savedLastUsedLsn = lastUsedLsn;
	savedPrevOffset = prevOffset;
        savedForceNewFile = forceNewFile;
        savedCurrentFileNum = currentFileNum;
  }

  // line 246 "../../../../FileManager.ump"
  public void restoreLastPosition(){
    nextAvailableLsn = savedNextAvailableLsn;
	lastUsedLsn = savedLastUsedLsn;
	prevOffset = savedPrevOffset;
        forceNewFile = savedForceNewFile;
        currentFileNum = savedCurrentFileNum;
  }


  /**
   * 
   * May be used to disable sync at file end to speed unit tests.
   * Must only be used for unit testing, since log corruption may result.
   */
  // line 258 "../../../../FileManager.ump"
   public void setSyncAtFileEnd(boolean sync){
    syncAtFileEnd = sync;
  }


  /**
   * File management
   * 
   * public for cleaner.
   * 
   * @return the number of the first file in this environment.
   */
  // line 271 "../../../../FileManager.ump"
   public Long getFirstFileNum(){
    return getFileNum(true);
  }

  // line 275 "../../../../FileManager.ump"
   public boolean getReadOnly(){
    return readOnly;
  }


  /**
   * 
   * @return the number of the last file in this environment.
   */
  // line 282 "../../../../FileManager.ump"
   public Long getLastFileNum(){
    return getFileNum(false);
  }


  /**
   * For unit tests.
   */
  // line 289 "../../../../FileManager.ump"
   public long getCurrentFileNum(){
    return currentFileNum;
  }

  // line 293 "../../../../FileManager.ump"
   public void setIncludeDeletedFiles(boolean includeDeletedFiles){
    this.includeDeletedFiles = includeDeletedFiles;
  }


  /**
   * 
   * Get all JE file numbers.
   * @return an array of all JE file numbers.
   */
  // line 301 "../../../../FileManager.ump"
   public Long[] getAllFileNumbers(){
    /* Get all the names in sorted order. */
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
   * @param currentFileNum the file we're at right now. Note that
   * it may not exist, if it's been cleaned and renamed.
   * @param forward if true, we want the next larger file, if false
   * we want the previous file
   * @return null if there is no following file, or if filenum doesn't exist
   */
  // line 319 "../../../../FileManager.ump"
   public Long getFollowingFileNum(long currentFileNum, boolean forward){
    /* Get all the names in sorted order. */
        String[] names = listFiles(JE_SUFFIXES);

        /* Search for the current file. */
        String searchName = getFileName(currentFileNum, JE_SUFFIX);
        int foundIdx = Arrays.binarySearch(names, searchName);

        boolean foundTarget = false;
        if (foundIdx >= 0) {
            if (forward) {
                foundIdx++;
            } else {
                foundIdx --;
            }
        } else {

            /* 
             * currentFileNum not found (might have been cleaned). FoundIdx
             * will be (-insertionPoint - 1).
             */
            foundIdx = Math.abs(foundIdx + 1);
            if (!forward) {
                foundIdx--;
            }
        }

        /* The current fileNum is found, return the next or prev file. */
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
  // line 363 "../../../../FileManager.ump"
   public boolean filesExist(){
    String[] names = listFiles(JE_SUFFIXES);
        return (names.length != 0);
  }


  /**
   * 
   * Get the first or last file number in the set of je files.
   * 
   * @param first if true, get the first file, else get the last file
   * @return the file number or null if no files exist
   */
  // line 374 "../../../../FileManager.ump"
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
   * 
   * @param the file name
   * @return the file number
   */
  // line 393 "../../../../FileManager.ump"
   private Long getNumFromName(String fileName){
    String fileNumber = fileName.substring(0, fileName.indexOf("."));
        return new Long(Long.parseLong(fileNumber, 16));
  }


  /**
   * 
   * Find je files. Return names sorted in ascending fashion.
   * 
   * @param suffix which type of file we're looking for
   * @return array of file names
   */
  // line 404 "../../../../FileManager.ump"
  public String[] listFiles(String [] suffixes){
    String[] fileNames = dbEnvHome.list(new JEFileFilter(suffixes));
        Arrays.sort(fileNames);
        return fileNames;
  }


  /**
   * 
   * Find je files, flavor for unit test support.
   * 
   * @param suffix which type of file we're looking for
   * @return array of file names
   */
  // line 416 "../../../../FileManager.ump"
   public static  String[] listFiles(File envDirFile, String [] suffixes){
    String[] fileNames = envDirFile.list(new JEFileFilter(suffixes));
        Arrays.sort(fileNames);
        return fileNames;
  }


  /**
   * 
   * @return the full file name and path for the nth je file.
   */
  // line 425 "../../../../FileManager.ump"
  public String[] getFullFileNames(long fileNum){
    if (includeDeletedFiles) {
	    int nSuffixes = JE_AND_DEL_SUFFIXES.length;
	    String[] ret = new String[nSuffixes];
	    for (int i = 0; i < nSuffixes; i++) {
		ret[i] = getFullFileName(getFileName(fileNum,
                                                     JE_AND_DEL_SUFFIXES[i]));
	    }
	    return ret;
	} else {
	    return new String[]
                { getFullFileName(getFileName(fileNum, JE_SUFFIX)) };
	}
  }


  /**
   * 
   * @return the full file name and path for the given file number and
   * 
   */
  // line 444 "../../../../FileManager.ump"
   public String getFullFileName(long fileNum, String suffix){
    return getFullFileName(getFileName(fileNum, suffix));
  }


  /**
   * 
   * @return the full file name and path for this file name.
   */
  // line 451 "../../../../FileManager.ump"
   private String getFullFileName(String fileName){
    return dbEnvHome + File.separator + fileName;
  }


  /**
   * 
   * @return the file name for the nth file.
   */
  // line 459 "../../../../FileManager.ump"
   public static  String getFileName(long fileNum, String suffix){
    /* 
         * HexFormatter generates a 0 padded string starting with 0x.  We want
         * the right most 8 digits, so start at 10.
         */
        return (HexFormatter.formatLong(fileNum).substring(10) + suffix);
  }


  /**
   * 
   * Rename this file to NNNNNNNN.suffix. If that file already exists, try
   * NNNNNNNN.suffix.1, etc. Used for deleting files or moving corrupt files
   * aside.
   * 
   * @param fileNum the file we want to move
   * @param newSuffix the new file suffix
   */
  // line 477 "../../../../FileManager.ump"
   public void renameFile(long fileNum, String newSuffix) throws DatabaseException,IOException{
    int repeatNum = 0;
        boolean renamed = false;
        while (!renamed) {
            String generation = "";
            if (repeatNum > 0) {
                generation = "." + repeatNum;
            }
            String newName =
		getFullFileName(getFileName(fileNum, newSuffix) + generation);
            File targetFile = new File(newName);
            if (targetFile.exists()) {
                repeatNum++;
            } else {
                String oldFileName = getFullFileNames(fileNum)[0];
                Label458:
clearFileCache(fileNum);
   ;

                File oldFile = new File(oldFileName);
                if (oldFile.renameTo(targetFile)) {
                    renamed = true;
                } else {
                    throw new LogException("Couldn't rename " + oldFileName +
                                             " to " + newName);
                }
            }
        }
  }


  /**
   * 
   * Delete log file NNNNNNNN.
   * 
   * @param fileNum the file we want to move
   */
  // line 512 "../../../../FileManager.ump"
   public void deleteFile(long fileNum) throws DatabaseException,IOException{
    String fileName = getFullFileNames(fileNum)[0];
									Label459:
clearFileCache(fileNum);
   ;

					File file = new File(fileName);
					boolean done = file.delete();
					if (!done) {
							throw new LogException
						("Couldn't delete " + file);
					}
  }


  /**
   * 
   * Return a read only file handle that corresponds the this file number.
   * Retrieve it from the cache or open it anew and validate the file header.
   * This method takes a latch on this file, so that the file descriptor will
   * be held in the cache as long as it's in use.  When the user is done with
   * the file, the latch must be released.
   * 
   * @param fileNum which file
   * @return the file handle for the existing or newly created file
   */
  // line 536 "../../../../FileManager.ump"
  public FileHandle getFileHandle(long fileNum) throws LogException,DatabaseException{
    /* Check the file cache for this file. */
        Long fileId = new Long(fileNum);
        FileHandle fileHandle = null;

        /**
         * Loop until we get an open FileHandle.
         */
        Label460:
while (true)
 {

            /*
             * The file cache is intentionally not latched here so that it's
             * not a bottleneck in the fast path.  We check that the file
             * handle that we get back is really still open after we latch it
             * down below.
             */
            Label462:
fileHandle = fileCache.get(fileId);
 ;

            /* The file wasn't in the cache. */
            if (fileHandle == null) {

LabelGetFileHandle:
fileCacheLatch.acquire();
 ;

                try {
                    /* Check the file cache again under the latch. */
                    fileHandle = fileCache.get(fileId);
                    if (fileHandle == null) {

                        fileHandle =		    makeFileHandle(fileNum, FileMode.READ_MODE);

                        /* Put it into the cache. */
                        Label464:
fileCache.add(fileId, fileHandle);
 ;
                    }
                } finally {
                    Label454:
fileCacheLatch.release();
 ;
                }
            }

            /* Get latch before returning */
            
fileHandle.latch();
Label453: ;

            /*
             * We may have obtained this file handle outside the file cache
             * latch, so we have to test that the handle is still valid.  If
             * it's not, then loop back and try again.
             */
            if (fileHandle.getFile() == null) {
               LabelGetFileHandle_2:
fileHandle.release();
 ;
            } else {
                Label460_1:
break;

            }
        }

        return fileHandle;
  }

  // line 594 "../../../../FileManager.ump"
   private FileHandle makeFileHandle(long fileNum, FileMode mode) throws DatabaseException{
    String[] fileNames = getFullFileNames(fileNum);
        RandomAccessFile newFile = null;
        String fileName = null;
        try {

            /* 
	     * Open the file. Note that we are going to try a few names to open
	     * this file -- we'll try for N.jdb, and if that doesn't exist and
	     * we're configured to look for all types, we'll look for N.del.
             */
            FileNotFoundException FNFE = null;
            for (int i = 0; i < fileNames.length; i++) {
                fileName = fileNames[i];
                try {
                    newFile =
			new RandomAccessFile(fileName, mode.getModeValue());
                    break;
                } catch (FileNotFoundException e) {
                    /* Save the first exception thrown. */
                    if (FNFE == null) {
                        FNFE = e;
                    }
                }
            }

            /* 
	     * If we didn't find the file or couldn't create it, rethrow the
	     * exception.
	     */
            if (newFile == null) {
                throw FNFE;
            }

            boolean oldHeaderVersion = false;

            if (newFile.length() == 0) {
                /* 
                 * If the file is empty, reinitialize it if we can. If
                 * not, send the file handle back up; the calling code will
                 * deal with the fact that there's nothing there.
                 */
                if (mode == FileMode.READWRITE_MODE) {
                    /* An empty file, write a header. */
                    long lastLsn = DbLsn.longToLsn((Long)
                                                   perFileLastUsedLsn.remove
                                                   (new Long(fileNum - 1)));
                    long headerPrevOffset = 0;
                    if (lastLsn != DbLsn.NULL_LSN) {
                        headerPrevOffset = DbLsn.getFileOffset(lastLsn);
                    }
                    FileHeader fileHeader =
                        new FileHeader(fileNum,
                                       headerPrevOffset);
                    writeFileHeader(newFile,
                                    fileName,
                                    fileHeader);
                }
            } else {
                /* A non-empty file, check the header */
                oldHeaderVersion =
                    readAndValidateFileHeader(newFile, fileName, fileNum);
            }
            return new FileHandle
                (newFile, fileName, envImpl, oldHeaderVersion);
        } catch (FileNotFoundException e) {
            throw new LogFileNotFoundException
                ("Couldn't open file " + fileName + ": " +
                 e.getMessage());
        } catch (DbChecksumException e) {

            /* 
             * Let this exception go as a checksum exception, so it sets the
             * run recovery state correctly.
             */
            closeFileInErrorCase(newFile);
            throw new DbChecksumException
                (envImpl, "Couldn't open file " + fileName, e);
        } catch (Throwable t) {

            /*
             * Catch Throwable here (rather than exception) because in unit
             * test mode, we run assertions and they throw errors. We want to
             * clean up the file object in all cases.
             */
            closeFileInErrorCase(newFile);
            throw new DatabaseException
		("Couldn't open file " + fileName + ": " + t, t);
        }
  }


  /**
   * 
   * Close this file and eat any exceptions. Used in catch clauses.
   */
  // line 688 "../../../../FileManager.ump"
   private void closeFileInErrorCase(RandomAccessFile file){
    try {
            if (file != null) {
                file.close();
            }
        } catch (IOException e) {

	    /* 
	     * Klockwork - ok
             * Couldn't close file, oh well.
	     */
        }
  }


  /**
   * 
   * Read the given je log file and validate the header. 
   * 
   * @throws DatabaseException if the file header isn't valid
   * 
   * @return whether the file header has an old version number.
   */
  // line 713 "../../../../FileManager.ump"
   private boolean readAndValidateFileHeader(RandomAccessFile file, String fileName, long fileNum) throws DatabaseException,IOException{
    /* 
         * Read the file header from this file. It's always the first log
         * entry.
         */
        LogManager logManager = envImpl.getLogManager();
        LogEntry headerEntry = 
            logManager.getLogEntry(DbLsn.makeLsn(fileNum, 0), file);
        FileHeader header = (FileHeader) headerEntry.getMainItem();
        return header.validate(fileName, fileNum);
  }


  /**
   * 
   * Write a proper file header to the given file.
   */
  // line 732 "../../../../FileManager.ump"
   private void writeFileHeader(RandomAccessFile file, String fileName, FileHeader header) throws DatabaseException,IOException{
    /*
	 * Fail loudly if the environment is invalid.  A RunRecoveryException
	 * must have occurred.
	 */
	envImpl.checkIfInvalid();

        /*
         * Fail silent if the environment is not open.
         */
        if (envImpl.mayNotWrite()) {
            return;
        }

        /* Serialize the header into this buffer. */
        int headerSize = header.getLogSize();
        int entrySize = headerSize + LogManager.HEADER_BYTES;
        ByteBuffer headerBuf = envImpl.getLogManager().
	    putIntoBuffer(header, headerSize, 0, false, entrySize);
        
	if (++writeCount >= stopOnWriteCount) {
	    Runtime.getRuntime().halt(0xff);
	}

        /* Write the buffer into the channel. */
        int bytesWritten;
        try {
            if (RUNRECOVERY_EXCEPTION_TESTING) {
                generateRunRecoveryException(file, headerBuf, 0);
            }
            bytesWritten = writeToFile(file, headerBuf, 0);
        } catch (ClosedChannelException e) {

            /* 
             * The channel should never be closed. It may be closed because
             * of an interrupt received by another thread. See SR [#10463]
             */
            throw new RunRecoveryException
		(envImpl, "Channel closed, may be due to thread interrupt", e);
        } catch (IOException e) {
            /* Possibly an out of disk exception. */
            throw new RunRecoveryException
		(envImpl, "IOException caught: " + e);
        }

        if (bytesWritten != entrySize) {
            throw new LogException
		("File " + fileName +
		 " was created with an incomplete header. Only " +
		 bytesWritten + " bytes were written.");
        }
  }


  /**
   * 
   * @return the prevOffset field stored in the file header.
   */
  // line 790 "../../../../FileManager.ump"
  public long getFileHeaderPrevOffset(long fileNum) throws IOException,DatabaseException{
    LogEntry headerEntry = 
            envImpl.getLogManager().getLogEntry(DbLsn.makeLsn(fileNum, 0));
        FileHeader header = (FileHeader) headerEntry.getMainItem();
        return header.getLastEntryInPrevFileOffset();
  }


  /**
   * Support for writing new log entries
   * 
   * @return the file offset of the last LSN that was used. For constructing
   * the headers of log entries. If the last LSN that was used was in a
   * previous file, or this is the very first LSN of the whole system, return
   * 0.
   */
  // line 807 "../../../../FileManager.ump"
  public long getPrevEntryOffset(){
    return prevOffset;
  }


  /**
   * 
   * Increase the current log position by "size" bytes. Move the prevOffset
   * pointer along.
   * 
   * @param size is an unsigned int
   * @return true if we flipped to the next log file.
   */
  // line 819 "../../../../FileManager.ump"
  public boolean bumpLsn(long size){
    /* Save copy of initial lsn state. */
        saveLastPosition();

        boolean flippedFiles = false;

        if (forceNewFile ||
            (DbLsn.getFileOffset(nextAvailableLsn) + size) > maxFileSize) {

            forceNewFile = false;

            /* Move to another file. */
            currentFileNum++;

            /* Remember the last used LSN of the previous file. */
            if (lastUsedLsn != DbLsn.NULL_LSN) {
                perFileLastUsedLsn.put
		    (new Long(DbLsn.getFileNumber(lastUsedLsn)),
		     new Long(lastUsedLsn));
            }
            prevOffset = 0;
            lastUsedLsn = DbLsn.makeLsn(currentFileNum, 
					firstLogEntryOffset());
            flippedFiles = true;
        } else {
            if (lastUsedLsn == DbLsn.NULL_LSN) {
                prevOffset = 0; 
            } else {
                prevOffset = DbLsn.getFileOffset(lastUsedLsn);
            }
            lastUsedLsn = nextAvailableLsn;
        }
        nextAvailableLsn =
	    DbLsn.makeLsn(DbLsn.getFileNumber(lastUsedLsn),
			  (DbLsn.getFileOffset(lastUsedLsn) + size));

        return flippedFiles;
  }


  /**
   * 
   * Write out a log buffer to the file.
   * @param fullBuffer buffer to write
   */
  // line 864 "../../../../FileManager.ump"
  public void writeLogBuffer(LogBuffer fullBuffer) throws DatabaseException{
    /*
	 * Fail loudly if the environment is invalid.  A RunRecoveryException
	 * must have occurred.
	 */
	envImpl.checkIfInvalid();

        /*
         * Fail silent if the environment is not open.
         */
        if (envImpl.mayNotWrite()) {
            return;
        }

        /* Use the LSN to figure out what file to write this buffer to. */
        long firstLsn = fullBuffer.getFirstLsn();

        /*
         * Is there anything in this write buffer? We could have been called by
         * the environment shutdown, and nothing is actually in the buffer.
         */
        if (firstLsn != DbLsn.NULL_LSN) {
            
            RandomAccessFile file =
                endOfLog.getWritableFile(DbLsn.getFileNumber(firstLsn));
            ByteBuffer data = fullBuffer.getDataBuffer();
 
            if (++writeCount >= stopOnWriteCount) {
                Runtime.getRuntime().halt(0xff);
            }

            try {

                /*
                 * Check that we do not overwrite unless the file only contains
                 * a header [#11915] [#12616].
                 */
  							 Label465:   ;

                if (IO_EXCEPTION_TESTING) {
		    throw new IOException("generated for testing");
                }
                if (RUNRECOVERY_EXCEPTION_TESTING) {
                    generateRunRecoveryException
                        (file, data, DbLsn.getFileOffset(firstLsn));
                }
 		writeToFile(file, data, DbLsn.getFileOffset(firstLsn));
            } catch (ClosedChannelException e) {

                /*
                 * The file should never be closed. It may be closed because
                 * of an interrupt received by another thread. See SR [#10463].
                 */
                throw new RunRecoveryException
		    (envImpl, "File closed, may be due to thread interrupt",
		     e);
            } catch (IOException IOE) {

		/* 
		 * Possibly an out of disk exception, but java.io will only
		 * tell us IOException with no indication of whether it's out
		 * of disk or something else.
		 *
		 * Since we can't tell what sectors were actually written to
		 * disk, we need to change any commit records that might have
		 * made it out to disk to abort records.  If they made it to
		 * disk on the write, then rewriting should allow them to be
		 * rewritten.  See [11271].
		 */
		abortCommittedTxns(data);
		Label466:   ;



	    assert EnvironmentImpl.maybeForceYield();
        }
    }
  }


  /**
   * 
   * Write a buffer to a file at a given offset, using NIO if so configured.
   */
  // line 949 "../../../../FileManager.ump"
   private int writeToFile(RandomAccessFile file, ByteBuffer data, long destOffset) throws IOException,DatabaseException{
    int totalBytesWritten = 0;
        if (useNIO) {
            FileChannel channel = file.getChannel();

        Label446:
if (chunkedNIOSize > 0) {

                /*
                 * We can't change the limit without impacting readers that
                 * might find this buffer in the buffer pool.  Duplicate the
                 * buffer so we can set the limit independently.
                 */
                ByteBuffer useData = data.duplicate();

                /*
                 * Write small chunks of data by manipulating the position and
                 * limit properties of the buffer, and submitting it for
                 * writing repeatedly.
                 *
                 * For each chunk, the limit is set to the position +
                 * chunkedNIOSize, capped by the original limit of the buffer.
                 *
                 * Preconditions: data to be written is betweek data.position()
                 * and data.limit()

                 * Postconditions: data.limit() has not changed,
                 * data.position() == data.limit(), offset of the channel has
                 * not been modified.
                 */
                int originalLimit = useData.limit();
                useData.limit(useData.position());
                while (useData.limit() < originalLimit) {
                    useData.limit((int)
                                  (Math.min(useData.limit() + chunkedNIOSize,
                                            originalLimit)));
                    int bytesWritten = channel.write(useData, destOffset);
                    destOffset += bytesWritten;
                    totalBytesWritten += bytesWritten;
                }
            } else {

                /*
                 * Perform a single write using NIO.
                 */
                totalBytesWritten = channel.write(data, destOffset);
            }
 ;

        } else {

            /*
             * Perform a RandomAccessFile write and update the buffer position.
             * ByteBuffer.array() is safe to use since all non-direct
             * ByteBuffers have a backing array.  Synchronization on the file
             * object is needed because two threads may call seek() on the same
             * file object.
             */

                Label445_1:
Label447:
synchronized (file) {
				

            assert data.hasArray();
                assert data.arrayOffset() == 0;

                int pos = data.position();
                int size = data.limit() - pos;
                file.seek(destOffset);
                file.write(data.array(), pos, size);
                data.position(pos + size);
                totalBytesWritten = size;
            

        }
Label447_1: ;//
 ;
								
            
        }
        return totalBytesWritten;
  }


  /**
   * 
   * Read a buffer from a file at a given offset, using NIO if so configured.
   */
  // line 980 "../../../../FileManager.ump"
  public void readFromFile(RandomAccessFile file, ByteBuffer readBuffer, long offset) throws IOException{
    if (useNIO) {
            FileChannel channel = file.getChannel();

      Label445:
if (chunkedNIOSize > 0) {

                /*
                 * Read a chunk at a time to prevent large direct memory
                 * allocations by NIO.
                 */
                int readLength = readBuffer.limit();
                long currentPosition = offset;
                while (readBuffer.position() < readLength) {
                    readBuffer.limit((int)
                                     (Math.min(readBuffer.limit() +
                                               chunkedNIOSize,
                                               readLength)));
                    int bytesRead = channel.read(readBuffer, currentPosition);
      
                    if (bytesRead < 1)
                        break;
      
                    currentPosition += bytesRead;
                }
            } else {

                /*
                 * Perform a single read using NIO.
                 */
                channel.read(readBuffer, offset);
            }
 ;
        } else {

            /*
             * Perform a RandomAccessFile read and update the buffer position.
             * ByteBuffer.array() is safe to use since all non-direct
             * ByteBuffers have a backing array.  Synchronization on the file
             * object is needed because two threads may call seek() on the same
             * file object.
             */

                Label446_1:
Label448:
synchronized (file) {
					

       					assert readBuffer.hasArray();
                assert readBuffer.arrayOffset() == 0;

                int pos = readBuffer.position();
                int size = readBuffer.limit() - pos;
                file.seek(offset);
                int bytesRead = file.read(readBuffer.array(), pos, size);
                if (bytesRead > 0) {
                    readBuffer.position(pos + bytesRead);
                }
           

        }
Label448_1: ;
 ;


            
        }
  }


  /**
   * Iterate through a buffer looking for commit records.  Change all commit
   * records to abort records.
   */
  // line 1006 "../../../../FileManager.ump"
   private void abortCommittedTxns(ByteBuffer data){
    final byte commitType = LogEntryType.LOG_TXN_COMMIT.getTypeNum();
	final byte abortType = LogEntryType.LOG_TXN_ABORT.getTypeNum();
	Label461:
data.position(0);
 ;

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
	    /* Move byte buffer past version. */
	    byte version = data.get();
  	    /* Read the size, skipping over the prev offset. */
  	    data.position(data.position() + LogManager.PREV_BYTES);
  	    int itemSize = LogUtils.readInt(data);
	    int itemDataStartPos = data.position();
	    if (recomputeChecksum) {
		Checksum checksum = Adler32.makeChecksum();
		data.position(recStartPos);
		/* Calculate the checksum and write it into the buffer. */
		int nChecksumBytes = itemSize +
		    (LogManager.HEADER_BYTES - LogManager.CHECKSUM_BYTES);
		byte[] checksumBytes = new byte[nChecksumBytes];
		System.arraycopy(data.array(),
				 recStartPos + LogManager.CHECKSUM_BYTES,
				 checksumBytes, 0, nChecksumBytes);
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
  // line 1051 "../../../../FileManager.ump"
  public void syncLogEnd() throws DatabaseException{
    try {
            endOfLog.force();
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
  }


  /**
   * 
   * Sync the end of the log, close off this log file. Should only be called
   * under the log write latch.
   */
  // line 1065 "../../../../FileManager.ump"
  public void syncLogEndAndFinishFile() throws DatabaseException,IOException{
    if (syncAtFileEnd) {
            syncLogEnd();
        }
        endOfLog.close();
  }


  /**
   * 
   * Flush a file using the group sync mechanism, trying to amortize off
   * other syncs.
   */
  // line 1078 "../../../../FileManager.ump"
  public void groupSync() throws DatabaseException{
    syncManager.fsync();
  }


  /**
   * 
   * Close all file handles and empty the cache.
   */
  // line 1087 "../../../../FileManager.ump"
   public void clear() throws IOException,DatabaseException{
    // line 23 "../../../../Derivative_Latches_FileHandleCache_FileManager.ump"
    fileCacheLatch.acquire();
    // END OF UMPLE BEFORE INJECTION
    try {
    				Label451:
fileCache.clear();
 ;
        } finally {
                Label451_1:
fileCacheLatch.release();
 ;
        }
        endOfLog.close();
  }


  /**
   * 
   * Clear the file lock.
   */
  // line 1102 "../../../../FileManager.ump"
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
   * Lock the environment.  Return true if the lock was acquired.  If
   * exclusive is false, then this implements a single writer, multiple
   * reader lock.  If exclusive is true, then implement an exclusive lock.
   * 
   * There is a lock file and there are two regions of the lock file: byte 0,
   * and byte 1.  Byte 0 is the exclusive writer process area of the lock
   * file.  If an environment is opened for write, then it attempts to take
   * an exclusive write lock on byte 0.  Byte 1 is the shared reader process
   * area of the lock file.  If an environment is opened for read-only, then
   * it attempts to take a shared lock on byte 1.  This is how we implement
   * single writer, multi reader semantics.
   * 
   * The cleaner, each time it is invoked, attempts to take an exclusive lock
   * on byte 1.  The owning process already either has an exclusive lock on
   * byte 0, or a shared lock on byte 1.  This will necessarily conflict with
   * any shared locks on byte 1, even if it's in the same process and there
   * are no other holders of that shared lock.  So if there is only one
   * read-only process, it will have byte 1 for shared access, and the
   * cleaner can not run in it because it will attempt to get an exclusive
   * lock on byte 1 (which is already locked for shared access by itself).
   * If a write process comes along and tries to run the cleaner, it will
   * attempt to get an exclusive lock on byte 1.  If there are no other
   * reader processes (with shared locks on byte 1), and no other writers
   * (which are running cleaners on with exclusive locks on byte 1), then the
   * cleaner will run.
   */
  // line 1134 "../../../../FileManager.ump"
   public boolean lockEnvironment(boolean readOnly, boolean exclusive) throws DatabaseException{
    try {
	    if (checkEnvHomePermissions(readOnly)) {
		return true;
	    }
            if (lockFile == null) {
                lockFile = new RandomAccessFile(new File(dbEnvHome,
                                                         "je" + LOCK_SUFFIX),
                                                "rw"); 
            }
            channel = lockFile.getChannel();

            boolean throwIt = false;
            try {
                if (exclusive) {

                    /*
                     * To lock exclusive, must have exclusive on 
                     * shared reader area (byte 1).
                     */
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
                throw new LogException
                    ("A je" + LOCK_SUFFIX + "file exists in " + dbEnvHome +
                     " The environment can not be locked for " +
                     (readOnly ? "shared" : "single writer") + " access.");
            }
        } catch (IOException IOE) {
            throw new LogException(IOE.toString());
        }
        return true;
  }

  // line 1186 "../../../../FileManager.ump"
   public void releaseExclusiveLock() throws DatabaseException{
    try {
            if (exclLock != null) {
                exclLock.release();
            }
        } catch (IOException IOE) {
            throw new DatabaseException(IOE);
        }
  }


  /**
   * 
   * Ensure that if the environment home dir is on readonly media or in a
   * readonly directory that the environment has been opened for readonly
   * 
   * 
   * @return true if the environment home dir is readonly.
   */
  // line 1205 "../../../../FileManager.ump"
   private boolean checkEnvHomePermissions(boolean readOnly) throws DatabaseException{
    boolean envDirIsReadOnly = !dbEnvHome.canWrite();
	if (envDirIsReadOnly &&
	    !readOnly) {
	    throw new DatabaseException
		("The Environment directory " +
                 dbEnvHome +
                 " is not writable, but the " +
		 "Environment was opened for read-write access.");
	}

	return envDirIsReadOnly;
  }


  /**
   * 
   * Truncate a log at this position. Used by recovery to a timestamp
   * utilities and by recovery to set the end-of-log position.
   * 
   * <p>This method forces a new log file to be written next, if the last
   * file (the file truncated to) has an old version in its header.  This
   * ensures that when the log is opened by an old version of JE, a version
   * incompatibility will be detected.  [#11243]</p>
   */
  // line 1230 "../../../../FileManager.ump"
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
  // line 1248 "../../../../FileManager.ump"
  public void forceNewLogFile(){
    forceNewFile = true;
  }


  /**
   * 
   * Return the offset of the first log entry after the file header.
   * 
   * @return the size in bytes of the file header log entry.
   */
  // line 1259 "../../../../FileManager.ump"
   public static  int firstLogEntryOffset(){
    return FileHeader.entrySize() + LogManager.HEADER_BYTES;
  }


  /**
   * 
   * Return the next available LSN in the log. Note that this is
   * unsynchronized, so is only valid as an approximation of log size.
   */
  // line 1267 "../../../../FileManager.ump"
   public long getNextLsn(){
    return nextAvailableLsn;
  }


  /**
   * 
   * Return the last allocated LSN in the log. Note that this is
   * unsynchronized, so if it is called outside the log write latch it is
   * only valid as an approximation of log size.
   */
  // line 1276 "../../../../FileManager.ump"
   public long getLastUsedLsn(){
    return lastUsedLsn;
  }


  /**
   * fsync stats.
   */
  // line 1283 "../../../../FileManager.ump"
   public long getNFSyncs(){
    return syncManager.getNFSyncs();
  }

  // line 1287 "../../../../FileManager.ump"
   public long getNFSyncRequests(){
    return syncManager.getNFSyncRequests();
  }

  // line 1291 "../../../../FileManager.ump"
   public long getNFSyncTimeouts(){
    return syncManager.getNTimeouts();
  }

  // line 1297 "../../../../FileManager.ump"
  public void loadStats(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    syncManager.loadStats(config, stats);
  }


  /**
   * Unit test support
   * @return ids of files in cache
   */
  // line 1308 "../../../../FileManager.ump"
  public Set getCacheKeys(){
    return fileCache.getCacheKeys();
  }

  // line 1620 "../../../../FileManager.ump"
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
        if (runRecoveryExceptionCounter ==
            runRecoveryExceptionRandom.nextInt(RUNRECOVERY_EXCEPTION_MAX)) {
            int len = runRecoveryExceptionRandom.nextInt(data.remaining());
            if (len > 0) {
                byte[] a = new byte[len];
                data.get(a, 0, len);
                ByteBuffer buf = ByteBuffer.wrap(a);
                writeToFile(file, buf, destOffset);
            }
            runRecoveryExceptionThrown = true;
		throw new RunRecoveryException
		    (envImpl, "Randomly generated for testing");
        }
  }


  /**
   * 
   * Clear a file out of the file cache regardless of mode type.
   */
  // line 15 "../../../../FileHandleCache_FileManager.ump"
   private void clearFileCache(long fileNum) throws IOException,DatabaseException{
    // line 42 "../../../../Derivative_Latches_FileHandleCache_FileManager.ump"
    fileCacheLatch.acquire();
    // END OF UMPLE BEFORE INJECTION
    fileCache.remove(fileNum);
    // line 59 "../../../../Derivative_Latches_FileHandleCache_FileManager.ump"
    fileCacheLatch.release();
    // END OF UMPLE AFTER INJECTION
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  /**
   * 
   * The LogEndFileDescriptor is used to write and fsync the end of the log.
   * Because the JE log is append only, there is only one logical R/W file
   * descriptor for the whole environment. This class actually implements two
   * RandomAccessFile instances, one for writing and one for fsyncing, so the
   * two types of operations don't block each other.
   * 
   * The write file descriptor is considered the master.  Manipulation of
   * this class is done under the log write latch. Here's an explanation of
   * why the log write latch is sufficient to safeguard all operations.
   * 
   * There are two types of callers who may use this file descriptor: the
   * thread that is currently writing to the end of the log and any threads
   * that are fsyncing on behalf of the FSyncManager.
   * 
   * The writing thread appends data to the file and fsyncs the file when we
   * flip over to a new log file.  The file is only instantiated at the point
   * that it must do so -- which is either when the first fsync is required
   * by JE or when the log file is full and we flip files.  Therefore, the
   * writing thread has two actions that change this descriptor -- we
   * initialize the file descriptor for the given log file at the first write
   * to the file, and we close the file descriptor when the log file is full.
   * Therefore is a period when there is no log descriptor -- when we have
   * not yet written a log buffer into a given log file.
   * 
   * The fsyncing threads ask for the log end file descriptor asynchronously,
   * but will never modify it.  These threads may arrive at the point when
   * the file descriptor is null, and therefore skip their fysnc, but that is
   * fine because it means a writing thread already flipped that target file
   * and has moved on to the next file.
   * 
   * Time     Activity
   * 10       thread 1 writes log entry A into file 0x0, issues fsync 
   * outside of log write latch, yields the processor
   * 20       thread 2 writes log entry B, piggybacks off thread 1
   * 30       thread 3 writes log entry C, but no room left in that file,
   * so it flips the log, and fsyncs file 0x0, all under the log
   * write latch. It nulls out endOfLogRWFile, moves onto file 
   * 0x1, but doesn't create the file yet.
   * 40       thread 1 finally comes along, but endOfLogRWFile is null--
   * no need to fsync in that case, 0x0 got fsynced.
   */
  // line 1484 "../../../../FileManager.ump"
  // line 6 "../../../../FileManager_static.ump"
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
    // line 1494 "../../../../FileManager.ump"
    public RandomAccessFile getWritableFile(long fileNumber) throws RunRecoveryException{
      try {
  
                  if (endOfLogRWFile == null) {
  
                      /* 
                       * We need to make a file descriptor for the end of the
                       * log.  This is guaranteed to be called under the log
                       * write latch.
                       */
                      endOfLogRWFile =
                          makeFileHandle(fileNumber,
                                         FileMode.READWRITE_MODE).getFile();
                      endOfLogSyncFile =
                          makeFileHandle(fileNumber,
                                         FileMode.READWRITE_MODE).getFile();
                  }
              
                  return endOfLogRWFile;
              } catch (Exception e) {
  
                  /* 
                   * If we can't get a write channel, we need to go into
                   * RunRecovery state.
                   */
                  throw new RunRecoveryException(envImpl, e);
              }
    }
  
  
    /**
     * 
     * FSync the log file that makes up the end of the log.
     */
    // line 1528 "../../../../FileManager.ump"
    public void force() throws DatabaseException,IOException{
      /* 
               * Get a local copy of the end of the log file descriptor, it could
               * change. No need to latch, no harm done if we get an old file
               * descriptor, because we forcibly fsync under the log write latch
               * when we switch files.
               * 
               * If there is no current end file descriptor, we know that the log
               * file has flipped to a new file since the fsync was issued.
               */
              RandomAccessFile file = endOfLogSyncFile;
              if (file != null) {
              
                  FileChannel channel = file.getChannel();
                  try {
                      channel.force(false);
                  } catch (ClosedChannelException e) {
  
                      /* 
                       * The channel should never be closed. It may be closed
                       * because of an interrupt received by another thread. See
                       * SR [#10463]
                       */
                      throw new RunRecoveryException
  			(envImpl,
  			 "Channel closed, may be due to thread interrupt",
  			 e);
                  } 
              
  		assert EnvironmentImpl.maybeForceYield();
              }
    }
  
  
    /**
     * 
     * Close the end of the log file descriptor. Use atomic assignment to
     * ensure that we won't force and close on the same descriptor.
     */
    // line 1567 "../../../../FileManager.ump"
    public void close() throws IOException{
      IOException firstException = null;
              if (endOfLogRWFile != null) {
                  RandomAccessFile file = endOfLogRWFile;
  
                  /* 
                   * Null out so that other threads know endOfLogRWFile is no
                   * longer available.
                   */
                  endOfLogRWFile = null;
                  try {
                      file.close();
                  } catch (IOException e) {
                      /* Save this exception, so we can try the second close. */
                      firstException = e;
                  }
              }
              if (endOfLogSyncFile != null) {
                  RandomAccessFile file = endOfLogSyncFile;
  
                  /* 
                   * Null out so that other threads know endOfLogSyncFile is no
                   * longer available.
                   */
                  endOfLogSyncFile = null;
                  file.close();
              }
  
              if (firstException != null) {
                  throw firstException;
              }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 7 "../../../../FileManager_static.ump"
    private RandomAccessFile endOfLogRWFile=null ;
  // line 8 "../../../../FileManager_static.ump"
    private RandomAccessFile endOfLogSyncFile=null ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 66 "../../../../FileManager_static.ump"
  // line 4 "../../../../NIO_FileManager_inner.ump"
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
  
    // line 68 "../../../../FileManager_static.ump"
    public  FileManager_writeToFile(FileManager _this, RandomAccessFile file, ByteBuffer data, long destOffset){
      this._this=_this;
          this.file=file;
          this.data=data;
          this.destOffset=destOffset;
    }
  
    // line 74 "../../../../FileManager_static.ump"
    public int execute() throws IOException,DatabaseException{
      totalBytesWritten=0;
          Label455:
  channel=file.getChannel();
          //original();
   ;//this.hook455();
          Label445:
  totalBytesWritten=channel.write(data,destOffset);
          //original();
   ;//this.hook445();
          Label445_1: ;//
          return totalBytesWritten;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 80 "../../../../FileManager_static.ump"
    protected FileManager _this ;
  // line 81 "../../../../FileManager_static.ump"
    protected RandomAccessFile file ;
  // line 82 "../../../../FileManager_static.ump"
    protected ByteBuffer data ;
  // line 83 "../../../../FileManager_static.ump"
    protected long destOffset ;
  // line 84 "../../../../FileManager_static.ump"
    protected int totalBytesWritten ;
  // line 85 "../../../../FileManager_static.ump"
    protected FileChannel channel ;
  // line 86 "../../../../FileManager_static.ump"
    protected ByteBuffer useData ;
  // line 87 "../../../../FileManager_static.ump"
    protected int origLimit ;
  // line 88 "../../../../FileManager_static.ump"
    protected int bytesWritten ;
  // line 89 "../../../../FileManager_static.ump"
    protected int pos ;
  // line 90 "../../../../FileManager_static.ump"
    protected int size ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 96 "../../../../FileManager_static.ump"
  // line 15 "../../../../NIO_FileManager_inner.ump"
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
  
    // line 98 "../../../../FileManager_static.ump"
    public  FileManager_readFromFile(FileManager _this, RandomAccessFile file, ByteBuffer readBuffer, long offset){
      this._this=_this;
          this.file=file;
          this.readBuffer=readBuffer;
          this.offset=offset;
    }
  
    // line 104 "../../../../FileManager_static.ump"
    public void execute() throws IOException{
      // line 17 "../../../../NIO_FileManager_inner.ump"
      channel=file.getChannel();
              //original();
      // END OF UMPLE BEFORE INJECTION
      Label446:
  channel.read(readBuffer,offset);
          //original();
   ;//this.hook446();
          Label446_1: ; //
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 107 "../../../../FileManager_static.ump"
    protected FileManager _this ;
  // line 108 "../../../../FileManager_static.ump"
    protected RandomAccessFile file ;
  // line 109 "../../../../FileManager_static.ump"
    protected ByteBuffer readBuffer ;
  // line 110 "../../../../FileManager_static.ump"
    protected long offset ;
  // line 111 "../../../../FileManager_static.ump"
    protected FileChannel channel ;
  // line 112 "../../../../FileManager_static.ump"
    protected int readLength ;
  // line 113 "../../../../FileManager_static.ump"
    protected long currentPosition ;
  // line 114 "../../../../FileManager_static.ump"
    protected int bytesRead1 ;
  // line 115 "../../../../FileManager_static.ump"
    protected int pos ;
  // line 116 "../../../../FileManager_static.ump"
    protected int size ;
  // line 117 "../../../../FileManager_static.ump"
    protected int bytesRead2 ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 53 "../../../../FileManager.ump"
  public static class FileMode 
  {
    public static final FileMode READ_MODE = new FileMode("r");
        public static final FileMode READWRITE_MODE = new FileMode("rw");

        private String fileModeValue;

        private FileMode(String fileModeValue) {
            this.fileModeValue = fileModeValue;
        }

	public String getModeValue() {
	    return fileModeValue;
	}
  }
// line 68 "../../../../FileManager.ump"
  static boolean IO_EXCEPTION_TESTING = false ;
// line 69 "../../../../FileManager.ump"
  private static final String DEBUG_NAME = FileManager.class.getName() ;
// line 71 "../../../../FileManager.ump"
  private static long writeCount = 0 ;
// line 72 "../../../../FileManager.ump"
  private static long stopOnWriteCount = Long.MAX_VALUE ;
// line 74 "../../../../FileManager.ump"
  public static final String JE_SUFFIX = ".jdb" ;
// line 75 "../../../../FileManager.ump"
  public static final String CIF_SUFFIX = ".cif" ;
// line 76 "../../../../FileManager.ump"
  public static final String DEL_SUFFIX = ".del" ;
// line 77 "../../../../FileManager.ump"
  public static final String BAD_SUFFIX = ".bad" ;
// line 78 "../../../../FileManager.ump"
  public static final String LOCK_SUFFIX = ".lck" ;
// line 79 "../../../../FileManager.ump"
  static final String[] DEL_SUFFIXES = {DEL_SUFFIX};
// line 80 "../../../../FileManager.ump"
  static final String[] JE_SUFFIXES = {JE_SUFFIX};
// line 81 "../../../../FileManager.ump"
  private static final String[] JE_AND_DEL_SUFFIXES = {JE_SUFFIX, DEL_SUFFIX};
// line 85 "../../../../FileManager.ump"
  private boolean syncAtFileEnd = true ;
// line 87 "../../../../FileManager.ump"
  private EnvironmentImpl envImpl ;
// line 88 "../../../../FileManager.ump"
  private long maxFileSize ;
// line 89 "../../../../FileManager.ump"
  private File dbEnvHome ;
// line 92 "../../../../FileManager.ump"
  private boolean includeDeletedFiles = false ;
// line 100 "../../../../FileManager.ump"
  private boolean readOnly ;
// line 103 "../../../../FileManager.ump"
  private long currentFileNum ;
// line 104 "../../../../FileManager.ump"
  private long nextAvailableLsn ;
// line 105 "../../../../FileManager.ump"
  private long lastUsedLsn ;
// line 106 "../../../../FileManager.ump"
  private long prevOffset ;
// line 107 "../../../../FileManager.ump"
  private boolean forceNewFile ;
// line 113 "../../../../FileManager.ump"
  private long savedCurrentFileNum ;
// line 114 "../../../../FileManager.ump"
  private long savedNextAvailableLsn ;
// line 115 "../../../../FileManager.ump"
  private long savedLastUsedLsn ;
// line 116 "../../../../FileManager.ump"
  private long savedPrevOffset ;
// line 117 "../../../../FileManager.ump"
  private boolean savedForceNewFile ;
// line 120 "../../../../FileManager.ump"
  private LogEndFileDescriptor endOfLog ;
// line 133 "../../../../FileManager.ump"
  private Map perFileLastUsedLsn ;
// line 136 "../../../../FileManager.ump"
  private boolean useNIO ;

// line 1324 "../../../../FileManager.ump"
  private static class FileCache 
  {
    private Map fileMap;            // Long->file
        private LinkedList fileList;    // list of file numbers
        private int fileCacheSize;

        FileCache(DbConfigManager configManager)
            throws DatabaseException {

            /* 
             * A fileMap maps the file number to FileHandles (RandomAccessFile,
             * latch). The fileList is a list of Longs to determine which files
             * to eject out of the file cache if it's too small.
             */
            fileMap = new Hashtable();
            fileList = new LinkedList();
            fileCacheSize =
		configManager.getInt(EnvironmentParams.LOG_FILE_CACHE_SIZE);
        }

        private FileHandle get(Long fileId) {
            return (FileHandle) fileMap.get(fileId);
        }

        private void add(Long fileId, FileHandle fileHandle)
            throws DatabaseException {

            /* 
             * Does the cache have any room or do we have to evict?  Hunt down
             * the file list for an unused file. Note that the file cache might
             * actually grow past the prescribed size if there is nothing
             * evictable. Should we try to shrink the file cache? Presently if
             * it grows, it doesn't shrink.
             */
            if (fileList.size() >= fileCacheSize) {
                Iterator iter = fileList.iterator();
                while (iter.hasNext()) {
                    Long evictId = (Long) iter.next();
                    FileHandle evictTarget = (FileHandle) fileMap.get(evictId);

                    /* 
                     * Try to latch. If latchNoWait returns false, then another
                     * thread owns this latch. Note that a thread that's trying
                     * to get a new file handle should never already own the
                     * latch on another file handle, because these latches are
                     * meant to be short lived and only held over the i/o out
                     * of the file.
                     */
                    if (evictTarget.latchNoWait()) {
                        try {
                            fileMap.remove(evictId);
                            iter.remove();
                            evictTarget.close();
                        } catch (IOException e) {
                            throw new DatabaseException (e);
                        } finally {
                            evictTarget.release();
                        }
                        break;
                    } 
                }
            }

            /* 
             * We've done our best to evict. Add the file the the cache now
             * whether or not we did evict.
             */
            fileList.add(fileId);
            fileMap.put(fileId, fileHandle);
        }

        /**
         * Take any file handles corresponding to this file name out of the
         * cache. A file handle could be there twice, in rd only and in r/w
         * mode.
         */
        private void remove(long fileNum) 
            throws IOException, DatabaseException {

            Iterator iter = fileList.iterator();
            while (iter.hasNext()) {
                Long evictId = (Long) iter.next();
                if (evictId.longValue() == fileNum) {
                    FileHandle evictTarget = (FileHandle) fileMap.get(evictId);
                    try {
                        evictTarget.latch();
                        fileMap.remove(evictId);
                        iter.remove();
                        evictTarget.close();
                    } finally {
                        evictTarget.release();
                    }
                }
            }
        }

        private void clear()
            throws IOException, DatabaseException {

            Iterator iter = fileMap.values().iterator();
            while (iter.hasNext()) {
                FileHandle fileHandle = (FileHandle) iter.next();
                try {
                    fileHandle.latch();
                    fileHandle.close();
                    iter.remove();
                } finally {
                    fileHandle.release();
                }
            }
            fileMap.clear();
            fileList.clear();
        }

        private Set getCacheKeys() {
            return fileMap.keySet();
        }
  }
// line 1605 "../../../../FileManager.ump"
  static boolean RUNRECOVERY_EXCEPTION_TESTING = false ;
// line 1607 "../../../../FileManager.ump"
  private static final int RUNRECOVERY_EXCEPTION_MAX = 100 ;
// line 1609 "../../../../FileManager.ump"
  private int runRecoveryExceptionCounter = 0 ;
// line 1611 "../../../../FileManager.ump"
  private boolean runRecoveryExceptionThrown = false ;
// line 1613 "../../../../FileManager.ump"
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
// line 5 "../../../../ChunckedNIO_FileManager.ump"
  private long chunkedNIOSize = 0 ;
// line 5 "../../../../FSync_FileManager.ump"
  private FSyncManager syncManager ;
// line 7 "../../../../Derivative_Latches_FileHandleCache_FileManager.ump"
  private Latch fileCacheLatch ;

  
}