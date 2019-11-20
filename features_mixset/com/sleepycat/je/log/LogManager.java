/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.TestHook;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.Adler32;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.cleaner.TrackedFileSummary;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
import java.util.zip.Checksum;
import java.util.List;
import java.nio.channels.ClosedChannelException;
import java.nio.ByteBuffer;
import java.nio.BufferOverflowException;
import java.io.RandomAccessFile;
import java.io.IOException;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../LogManager.ump"
// line 3 "../../../../LogManager_static.ump"
// line 3 "../../../../Statistics_LogManager.ump"
// line 3 "../../../../Statistics_LogManager_inner.ump"
// line 3 "../../../../Checksum_LogManager.ump"
// line 3 "../../../../Checksum_LogManager_inner.ump"
// line 3 "../../../../Latches_LogManager.ump"
// line 3 "../../../../FSync_LogManager.ump"
// line 3 "../../../../Derivative_CheckpointerDaemon_CPBytes_LogManager.ump"
// line 3 "../../../../Derivative_FSync_Statistics_LogManager.ump"
public class LogManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 56 "../../../../LogManager.ump"
   static  int HEADER_CONTENT_BYTES(){
    int r = HEADER_BYTES;
			Label504:
r -= CHECKSUM_BYTES;
        //return original(r);
 //r = hook504(r);
			return r;
  }


  /**
   * 
   * There is a single log manager per database environment.
   */
  // line 65 "../../../../LogManager.ump"
   public  LogManager(EnvironmentImpl envImpl, boolean readOnly) throws DatabaseException{
    this.envImpl = envImpl;
			this.fileManager = envImpl.getFileManager();
			DbConfigManager configManager = envImpl.getConfigManager();
			this.readOnly = readOnly;
			logBufferPool = new LogBufferPool(fileManager, envImpl);
			Label505:
doChecksumOnRead = configManager.getBoolean(EnvironmentParams.LOG_CHECKSUM_READ);
        //original(configManager);
 //this.hook505(configManager);
			Label502:
logWriteLatch = LatchSupport.makeLatch(DEBUG_NAME, envImpl);
	//original(envImpl);
 //this.hook502(envImpl);
			readBufferSize = configManager.getInt(EnvironmentParams.LOG_FAULT_READ_SIZE);
			Label498:
checkpointMonitor = new CheckpointMonitor(envImpl);
	//original(envImpl);
 //this.hook498(envImpl);
  }

  // line 77 "../../../../LogManager.ump"
   public long getLastLsnAtRecovery(){
    return lastLsnAtRecovery;
  }

  // line 81 "../../../../LogManager.ump"
   public void setLastLsnAtRecovery(long lastLsnAtRecovery){
    this.lastLsnAtRecovery = lastLsnAtRecovery;
  }


  /**
   * 
   * Reset the pool when the cache is resized. This method is called after the memory budget has been calculated.
   */
  // line 88 "../../../../LogManager.ump"
   public void resetPool(DbConfigManager configManager) throws DatabaseException{
    logBufferPool.reset(configManager);
  }


  /**
   * 
   * Log this single object and force a write of the log files.
   * @param itemobject to be logged
   * @param fsyncRequiredif true, log files should also be fsynced.
   * @return LSN of the new log entry
   */
  // line 98 "../../../../LogManager.ump"
   public long logForceFlush(LoggableObject item, boolean fsyncRequired) throws DatabaseException{
    return log(item, false, true, fsyncRequired, false, DbLsn.NULL_LSN);
  }


  /**
   * 
   * Log this single object and force a flip of the log files.
   * @param itemobject to be logged
   * @param fsyncRequiredif true, log files should also be fsynced.
   * @return LSN of the new log entry
   */
  // line 108 "../../../../LogManager.ump"
   public long logForceFlip(LoggableObject item) throws DatabaseException{
    return log(item, false, true, false, true, DbLsn.NULL_LSN);
  }


  /**
   * 
   * Write a log entry.
   * @return LSN of the new log entry
   */
  // line 116 "../../../../LogManager.ump"
   public long log(LoggableObject item) throws DatabaseException{
    return log(item, false, false, false, false, DbLsn.NULL_LSN);
  }


  /**
   * 
   * Write a log entry.
   * @return LSN of the new log entry
   */
  // line 124 "../../../../LogManager.ump"
   public long log(LoggableObject item, boolean isProvisional, long oldNodeLsn) throws DatabaseException{
    return log(item, isProvisional, false, false, false, oldNodeLsn);
  }


  /**
   * 
   * Write a log entry.
   * @param itemis the item to be logged.
   * @param isProvisionaltrue if this entry should not be read during recovery.
   * @param flushRequiredif true, write the log to the file after adding the item. i.e.call java.nio.channel.FileChannel.write().
   * @param fsyncRequiredif true, fsync the last file after adding the item.
   * @param oldNodeLsnis the previous version of the node to be counted as obsolete,or null if the item is not a node or has no old LSN.
   * @return LSN of the new log entry
   */
  // line 138 "../../../../LogManager.ump"
   private long log(LoggableObject item, boolean isProvisional, boolean flushRequired, boolean fsyncRequired, boolean forceNewLogFile, long oldNodeLsn) throws DatabaseException{
    if (readOnly) {
	    return DbLsn.NULL_LSN;
	}
	boolean marshallOutsideLatch = item.marshallOutsideWriteLatch();
	ByteBuffer marshalledBuffer = null;
	UtilizationTracker tracker = envImpl.getUtilizationTracker();
	LogResult logResult = null;
	try {
	    if (marshallOutsideLatch) {
		int itemSize = item.getLogSize();
		int entrySize = itemSize + HEADER_BYTES;
		marshalledBuffer = marshallIntoBuffer(item, itemSize, isProvisional, entrySize);
	    }
	    logResult = logItem(item, isProvisional, flushRequired, forceNewLogFile, oldNodeLsn, marshallOutsideLatch,
		    marshalledBuffer, tracker);
	} catch (BufferOverflowException e) {
	    throw new RunRecoveryException(envImpl, e);
	} catch (IOException e) {
	    throw new DatabaseException(Tracer.getStackTrace(e), e);
	}
	Label501:
if (fsyncRequired) {
	    fileManager.groupSync();
	}
	//original(fsyncRequired);
 //this.hook501(fsyncRequired);
	Label499:
if (logResult.wakeupCheckpointer) {
	    checkpointMonitor.activate();
	}
	//original(logResult);
 //this.hook499(logResult);
	if (logResult.wakeupCleaner) {
	    tracker.activateCleaner();
	}
	return logResult.currentLsn;
  }


  /**
   * 
   * Called within the log write critical section.
   */
  // line 176 "../../../../LogManager.ump"
   protected LogResult logInternal(LoggableObject item, boolean isProvisional, boolean flushRequired, boolean forceNewLogFile, long oldNodeLsn, boolean marshallOutsideLatch, ByteBuffer marshalledBuffer, UtilizationTracker tracker) throws IOException,DatabaseException{
    LogEntryType entryType = item.getLogType();
			if (oldNodeLsn != DbLsn.NULL_LSN) {
					tracker.countObsoleteNode(oldNodeLsn, entryType);
			}
			int entrySize;
			if (marshallOutsideLatch) {
					entrySize = marshalledBuffer.limit();
			} else {
					entrySize = item.getLogSize() + HEADER_BYTES;
			}
			if (forceNewLogFile) {
					fileManager.forceNewLogFile();
			}
			boolean flippedFile = fileManager.bumpLsn(entrySize);
			long currentLsn = DbLsn.NULL_LSN;
			boolean wakeupCleaner = false;
			boolean usedTemporaryBuffer = false;
			try {
					currentLsn = fileManager.getLastUsedLsn();
					wakeupCleaner = tracker.countNewLogEntry(currentLsn, entryType, entrySize);
					if (item.countAsObsoleteWhenLogged()) {
				tracker.countObsoleteNodeInexact(currentLsn, entryType);
					}
					if (!marshallOutsideLatch) {
				marshalledBuffer = marshallIntoBuffer(item, entrySize - HEADER_BYTES, isProvisional, entrySize);
					}
					if (entrySize != marshalledBuffer.limit()) {
				throw new DatabaseException(
					"Logged item entrySize= " + entrySize + " but marshalledSize=" + marshalledBuffer.limit()
						+ " type=" + entryType + " currentLsn=" + DbLsn.getNoFormatString(currentLsn));
					}
					LogBuffer useLogBuffer = logBufferPool.getWriteBuffer(entrySize, flippedFile);
					marshalledBuffer = addPrevOffsetAndChecksum(marshalledBuffer, fileManager.getPrevEntryOffset(), entrySize);
					Label503:
useLogBuffer.latchForWrite();
 //usedTemporaryBuffer = this.hook503(marshalledBuffer, entrySize, currentLsn, usedTemporaryBuffer,useLogBuffer);
					ByteBuffer useBuffer = useLogBuffer.getDataBuffer();
					if (useBuffer.capacity() - useBuffer.position() < entrySize) {
							fileManager.writeLogBuffer(new LogBuffer(marshalledBuffer, currentLsn));
							usedTemporaryBuffer = true;
							assert useBuffer.position() == 0;
							Label509:
nTempBufferWrites++;
		//	original();
 //this.hook509();
					} else {
							useBuffer.put(marshalledBuffer);
					}
					Label503_1:
//try {	    usedTemporaryBuffer = original(marshalledBuffer, entrySize, currentLsn, usedTemporaryBuffer, useLogBuffer);} finally {
	    useLogBuffer.release();
	//}
//	return usedTemporaryBuffer;

				 //End hook503
			} catch (Exception e) {
					fileManager.restoreLastPosition();
					if (e instanceof DatabaseException) {
				throw (DatabaseException) e;
					} else if (e instanceof IOException) {
				throw (IOException) e;
					} else {
				throw new DatabaseException(e);
					}
			}
			if (!usedTemporaryBuffer) {
					logBufferPool.writeCompleted(currentLsn, flushRequired);
			}
			item.postLogWork(currentLsn);
			boolean wakeupCheckpointer = false;
			Label500:
wakeupCheckpointer = checkpointMonitor.recordLogWrite(entrySize, item);
	//return //original(item, entrySize, wakeupCheckpointer);
 //wakeupCheckpointer = this.hook500(item, entrySize, wakeupCheckpointer);
			return new LogResult(currentLsn, wakeupCheckpointer, wakeupCleaner);
  }


  /**
   * 
   * Serialize a loggable object into this buffer.
   */
  // line 245 "../../../../LogManager.ump"
   private ByteBuffer marshallIntoBuffer(LoggableObject item, int itemSize, boolean isProvisional, int entrySize) throws DatabaseException{
    ByteBuffer destBuffer = ByteBuffer.allocate(entrySize);
	destBuffer.position(CHECKSUM_BYTES);
	writeHeader(destBuffer, item.getLogType(), itemSize, isProvisional);
	item.writeToLog(destBuffer);
	destBuffer.flip();
	return destBuffer;
  }

  // line 254 "../../../../LogManager.ump"
   private ByteBuffer addPrevOffsetAndChecksum(ByteBuffer destBuffer, long lastOffset, int entrySize){
    Checksum checksum = Adler32.makeChecksum();
	destBuffer.position(HEADER_PREV_OFFSET);
	LogUtils.writeUnsignedInt(destBuffer, lastOffset);
	checksum.update(destBuffer.array(), CHECKSUM_BYTES, (entrySize - CHECKSUM_BYTES));
	destBuffer.position(0);
	LogUtils.writeUnsignedInt(destBuffer, checksum.getValue());
	destBuffer.position(0);
	return destBuffer;
  }


  /**
   * 
   * Serialize a loggable object into this buffer. Return it ready for a copy.
   */
  // line 269 "../../../../LogManager.ump"
  public ByteBuffer putIntoBuffer(LoggableObject item, int itemSize, long prevLogEntryOffset, boolean isProvisional, int entrySize) throws DatabaseException{
    ByteBuffer destBuffer = marshallIntoBuffer(item, itemSize, isProvisional, entrySize);
	return addPrevOffsetAndChecksum(destBuffer, 0, entrySize);
  }


  /**
   * 
   * Helper to write the common entry header.
   * @param destBufferdestination
   * @param itemobject being logged
   * @param itemSizeWe could ask the item for this, but are passing it as aparameter for efficiency, because it's already available
   */
  // line 280 "../../../../LogManager.ump"
   private void writeHeader(ByteBuffer destBuffer, LogEntryType itemType, int itemSize, boolean isProvisional){
    byte typeNum = itemType.getTypeNum();
	destBuffer.put(typeNum);
	byte version = itemType.getVersion();
	if (isProvisional)
	    version = LogEntryType.setProvisional(version);
	destBuffer.put(version);
	destBuffer.position(HEADER_SIZE_OFFSET);
	LogUtils.writeInt(destBuffer, itemSize);
  }


  /**
   * 
   * Instantiate all the objects in the log entry at this LSN.
   * @param lsnlocation of entry in log.
   * @return log entry that embodies all the objects in the log entry.
   */
  // line 296 "../../../../LogManager.ump"
   public LogEntry getLogEntry(long lsn) throws DatabaseException{
    envImpl.checkIfInvalid();
	LogSource logSource = getLogSource(lsn);
	return getLogEntryFromLogSource(lsn, logSource);
  }

  // line 302 "../../../../LogManager.ump"
  public LogEntry getLogEntry(long lsn, RandomAccessFile file) throws DatabaseException{
    return getLogEntryFromLogSource(lsn, new FileSource(file, readBufferSize, fileManager));
  }


  /**
   * 
   * Instantiate all the objects in the log entry at this LSN. This will release the log source at the first opportunity.
   * @param lsnlocation of entry in log
   * @return log entry that embodies all the objects in the log entry
   */
  // line 311 "../../../../LogManager.ump"
   private LogEntry getLogEntryFromLogSource(long lsn, LogSource logSource) throws DatabaseException{
    return new LogManager_getLogEntryFromLogSource(this, lsn, logSource).execute();
  }


  /**
   * 
   * Fault in the first object in the log entry log entry at this LSN.
   * @param lsnlocation of object in log
   * @return the object in the log
   */
  // line 320 "../../../../LogManager.ump"
   public Object get(long lsn) throws DatabaseException{
    LogEntry entry = getLogEntry(lsn);
	return entry.getMainItem();
  }


  /**
   * 
   * Find the LSN, whether in a file or still in the log buffers.
   */
  // line 328 "../../../../LogManager.ump"
   private LogSource getLogSource(long lsn) throws DatabaseException{
    LogBuffer logBuffer = logBufferPool.getReadBuffer(lsn);
	if (logBuffer == null) {
	    try {
		return new FileHandleSource(fileManager.getFileHandle(DbLsn.getFileNumber(lsn)), readBufferSize,
			fileManager);
	    } catch (LogFileNotFoundException e) {
		throw new LogFileNotFoundException(DbLsn.getNoFormatString(lsn) + ' ' + e.getMessage());
	    }
	} else {
	    return logBuffer;
	}
  }


  /**
   * 
   * Flush all log entries, fsync the log file.
   */
  // line 345 "../../../../LogManager.ump"
   public void flush() throws DatabaseException{
    if (readOnly) {
	    return;
	}
	flushInternal();
	fileManager.syncLogEnd();
  }

  // line 360 "../../../../LogManager.ump"
   protected TrackedFileSummary getUnflushableTrackedSummaryInternal(long file) throws DatabaseException{
    return envImpl.getUtilizationTracker().getUnflushableTrackedSummary(file);
  }

  // line 370 "../../../../LogManager.ump"
   protected void countObsoleteNodeInternal(UtilizationTracker tracker, long lsn, LogEntryType type) throws DatabaseException{
    tracker.countObsoleteNode(lsn, type);
  }

  // line 380 "../../../../LogManager.ump"
   protected void countObsoleteNodesInternal(UtilizationTracker tracker, TrackedFileSummary [] summaries) throws DatabaseException{
    for (int i = 0; i < summaries.length; i += 1) {
	    TrackedFileSummary summary = summaries[i];
	    tracker.addSummary(summary.getFileNumber(), summary);
	}
  }

  // line 392 "../../../../LogManager.ump"
   protected void countObsoleteINsInternal(List lsnList) throws DatabaseException{
    UtilizationTracker tracker = envImpl.getUtilizationTracker();
	for (int i = 0; i < lsnList.size(); i += 1) {
	    Long offset = (Long) lsnList.get(i);
	    tracker.countObsoleteNode(offset.longValue(), LogEntryType.LOG_IN);
	}
  }

  // line 400 "../../../../LogManager.ump"
   public void setReadHook(TestHook hook){
    readHook = hook;
  }


  /**
   * protected void hook498(EnvironmentImpl envImpl) throws DatabaseException { }
   * protected void hook499(LogResult logResult) throws DatabaseException { }
   * protected boolean hook500(LoggableObject item, int entrySize, boolean wakeupCheckpointer)
   * throws IOException, DatabaseException {	return wakeupCheckpointer; }
   * protected void hook501(boolean fsyncRequired) throws DatabaseException { }
   */
  // line 413 "../../../../LogManager.ump"
   protected void hook502(EnvironmentImpl envImpl) throws DatabaseException{
    
  }


  /**
   * protected boolean hook503(ByteBuffer marshalledBuffer, int entrySize, long currentLsn, boolean usedTemporaryBuffer,
   * LogBuffer useLogBuffer) throws IOException, DatabaseException, Exception {
   * ByteBuffer useBuffer = useLogBuffer.getDataBuffer();
   * if (useBuffer.capacity() - useBuffer.position() < entrySize) {
   * fileManager.writeLogBuffer(new LogBuffer(marshalledBuffer, currentLsn));
   * usedTemporaryBuffer = true;
   * assert useBuffer.position() == 0;
   * Label509: //this.hook509();
   * } else {
   * useBuffer.put(marshalledBuffer);
   * }
   * return usedTemporaryBuffer;
   * }
   * protected static int hook504(int r) {
   * return r;
   * }
   */
  // line 434 "../../../../LogManager.ump"
   protected void hook505(DbConfigManager configManager) throws DatabaseException{
    
  }

  // line 12 "../../../../Statistics_LogManager.ump"
   public void loadStats(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    stats.setNRepeatFaultReads(nRepeatFaultReads);
			stats.setNTempBufferWrites(nTempBufferWrites);
			if (config.getClear()) {
					nRepeatFaultReads = 0;
					nTempBufferWrites = 0;
			}
			logBufferPool.loadStats(config, stats);
			Label497:
fileManager.loadStats(config, stats);
//	original(config, stats);
 //this.hook497(config, stats);
  }

  // line 12 "../../../../Checksum_LogManager.ump"
   public boolean getChecksumOnRead(){
    return doChecksumOnRead;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../LogManager_static.ump"
  // line 4 "../../../../Statistics_LogManager_inner.ump"
  // line 4 "../../../../Checksum_LogManager_inner.ump"
  public static class LogManager_getLogEntryFromLogSource
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public LogManager_getLogEntryFromLogSource()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../LogManager_static.ump"
    public  LogManager_getLogEntryFromLogSource(LogManager _this, long lsn, LogSource logSource){
      this._this=_this;
          this.lsn=lsn;
          this.logSource=logSource;
    }
  
    // line 11 "../../../../LogManager_static.ump"
    public LogEntry execute() throws DatabaseException{
      try {
            fileOffset=DbLsn.getFileOffset(lsn);
            entryBuffer=logSource.getBytes(fileOffset);
            Label507:
  validator=null;
          storedChecksum=LogUtils.getUnsignedInt(entryBuffer);
          if (_this.doChecksumOnRead) {
            validator=new ChecksumValidator();
            validator.update(_this.envImpl,entryBuffer,_this.HEADER_CONTENT_BYTES(),false);
          }
          //original();
   //this.hook507();
            loggableType=entryBuffer.get();
            version=entryBuffer.get();
            entryBuffer.position(entryBuffer.position() + _this.PREV_BYTES);
            itemSize=LogUtils.readInt(entryBuffer);
            if (entryBuffer.remaining() < itemSize) {
              entryBuffer=logSource.getBytes(fileOffset + _this.HEADER_BYTES,itemSize);
              Label508:
  _this.nRepeatFaultReads++;
          //original();
   //this.hook508();
            }
            Label506:
  if (_this.doChecksumOnRead) {
            validator.update(_this.envImpl,entryBuffer,itemSize,false);
            validator.validate(_this.envImpl,storedChecksum,lsn);
          }
          //original();
   //this.hook506();
            assert LogEntryType.isValidType(loggableType) : "Read non-valid log entry type: " + loggableType;
            logEntry=LogEntryType.findType(loggableType,version).getNewLogEntry();
            logEntry.readEntry(entryBuffer,itemSize,version,true);
            if (_this.readHook != null) {
              _this.readHook.doIOHook();
            }
            return logEntry;
          }
     catch (      DatabaseException e) {
            throw e;
          }
    catch (      ClosedChannelException e) {
            throw new RunRecoveryException(_this.envImpl,"Channel closed, may be " + "due to thread interrupt",e);
          }
    catch (      Exception e) {
            throw new DatabaseException(e);
          }
     finally {
            if (logSource != null) {
              logSource.release();
            }
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 47 "../../../../LogManager_static.ump"
    protected LogManager _this ;
  // line 48 "../../../../LogManager_static.ump"
    protected long lsn ;
  // line 49 "../../../../LogManager_static.ump"
    protected LogSource logSource ;
  // line 50 "../../../../LogManager_static.ump"
    protected long fileOffset ;
  // line 51 "../../../../LogManager_static.ump"
    protected ByteBuffer entryBuffer ;
  // line 52 "../../../../LogManager_static.ump"
    protected long storedChecksum ;
  // line 53 "../../../../LogManager_static.ump"
    protected byte loggableType ;
  // line 54 "../../../../LogManager_static.ump"
    protected byte version ;
  // line 55 "../../../../LogManager_static.ump"
    protected int itemSize ;
  // line 56 "../../../../LogManager_static.ump"
    protected LogEntry logEntry ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 25 "../../../../LogManager.ump"
  private static final String DEBUG_NAME = LogManager.class.getName() ;
// line 27 "../../../../LogManager.ump"
  static final int HEADER_BYTES = 14 ;
// line 29 "../../../../LogManager.ump"
  static final int CHECKSUM_BYTES = 4 ;
// line 31 "../../../../LogManager.ump"
  static final int PREV_BYTES = 4 ;
// line 33 "../../../../LogManager.ump"
  static final int HEADER_ENTRY_TYPE_OFFSET = 4 ;
// line 35 "../../../../LogManager.ump"
  static final int HEADER_VERSION_OFFSET = 5 ;
// line 37 "../../../../LogManager.ump"
  static final int HEADER_PREV_OFFSET = 6 ;
// line 39 "../../../../LogManager.ump"
  static final int HEADER_SIZE_OFFSET = 6 + 4 ;
// line 41 "../../../../LogManager.ump"
  protected LogBufferPool logBufferPool ;
// line 43 "../../../../LogManager.ump"
  private FileManager fileManager ;
// line 45 "../../../../LogManager.ump"
  protected EnvironmentImpl envImpl ;
// line 47 "../../../../LogManager.ump"
  private boolean readOnly ;
// line 49 "../../../../LogManager.ump"
  private int readBufferSize ;
// line 51 "../../../../LogManager.ump"
  private long lastLsnAtRecovery = DbLsn.NULL_LSN ;
// line 53 "../../../../LogManager.ump"
  private TestHook readHook ;
// line 166 "../../../../LogManager.ump"
  abstract protected LogResult logItem(LoggableObject item, boolean isProvisional, boolean flushRequired,
	    boolean forceNewLogFile, long oldNodeLsn, boolean marshallOutsideLatch, ByteBuffer marshalledBuffer,
	    UtilizationTracker tracker) throws IOException, DatabaseException ;
// line 352 "../../../../LogManager.ump"
  abstract protected void flushInternal() throws LogException, DatabaseException ;
// line 357 "../../../../LogManager.ump"
  abstract public TrackedFileSummary getUnflushableTrackedSummary(long file) throws DatabaseException ;
// line 366 "../../../../LogManager.ump"
  abstract public void countObsoleteNode(long lsn, LogEntryType type) throws DatabaseException ;
// line 376 "../../../../LogManager.ump"
  abstract public void countObsoleteNodes(TrackedFileSummary[] summaries) throws DatabaseException ;
// line 389 "../../../../LogManager.ump"
  abstract public void countObsoleteINs(List lsnList) throws DatabaseException ;
// line 7 "../../../../Statistics_LogManager.ump"
  private int nRepeatFaultReads ;
// line 9 "../../../../Statistics_LogManager.ump"
  private long nTempBufferWrites ;
// line 5 "../../../../Checksum_LogManager.ump"
  static final int HEADER_CHECKSUM_OFFSET = 0 ;
// line 7 "../../../../Checksum_LogManager.ump"
  private boolean doChecksumOnRead ;
// line 9 "../../../../Checksum_LogManager.ump"
  protected static ChecksumValidator validator ;
// line 7 "../../../../Latches_LogManager.ump"
  protected Latch logWriteLatch ;
// line 5 "../../../../Derivative_CheckpointerDaemon_CPBytes_LogManager.ump"
  private CheckpointMonitor checkpointMonitor ;

  
}