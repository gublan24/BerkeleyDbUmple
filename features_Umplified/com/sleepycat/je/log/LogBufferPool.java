/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.DatabaseException;
import java.util.LinkedList;
import java.util.Iterator;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../LogBufferPool.ump"
public class LogBufferPool
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogBufferPool()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 29 "../../../../LogBufferPool.ump"
  public  LogBufferPool(FileManager fileManager, EnvironmentImpl envImpl) throws DatabaseException{
    this.fileManager = fileManager;
	this.envImpl = envImpl;
	this.hook485(envImpl);
	DbConfigManager configManager = envImpl.getConfigManager();
	runInMemory = configManager.getBoolean(EnvironmentParams.LOG_MEMORY_ONLY);
	reset(configManager);
	currentWriteBuffer = (LogBuffer) bufferPool.getFirst();
  }


  /**
   * 
   * Initialize the pool at construction time and when the cache is resized. This method is called after the memory budget has been calculated.
   */
  // line 42 "../../../../LogBufferPool.ump"
  public void reset(DbConfigManager configManager) throws DatabaseException{
    if (runInMemory && bufferPool != null) {
	    return;
	}
	int numBuffers = configManager.getInt(EnvironmentParams.NUM_LOG_BUFFERS);
	long logBufferBudget = envImpl.getMemoryBudget().getLogBufferBudget();
	int newBufferSize = (int) logBufferBudget / numBuffers;
	LinkedList newPool = new LinkedList();
	if (runInMemory) {
	    numBuffers = 1;
	}
	for (int i = 0; i < numBuffers; i++) {
	    newPool.add(new LogBuffer(newBufferSize, envImpl));
	}
	this.hook486();
	bufferPool = newPool;
	logBufferSize = newBufferSize;
  }


  /**
   * 
   * Get a log buffer for writing sizeNeeded bytes. If currentWriteBuffer is too small or too full, flush currentWriteBuffer and get a new one. Called within the log write latch.
   * @return a buffer that can hold sizeNeeded bytes.
   */
  // line 65 "../../../../LogBufferPool.ump"
  public LogBuffer getWriteBuffer(int sizeNeeded, boolean flippedFile) throws IOException,DatabaseException{
    if ((!currentWriteBuffer.hasRoom(sizeNeeded)) || flippedFile) {
	    writeBufferToFile(sizeNeeded);
	}
	if (flippedFile) {
	    if (!runInMemory) {
		fileManager.syncLogEndAndFinishFile();
	    }
	}
	return currentWriteBuffer;
  }


  /**
   * 
   * Write the contents of the currentWriteBuffer to disk.  Leave this buffer in memory to be available to would be readers.  Set up a new currentWriteBuffer. Assumes the log write latch is held.
   * @param sizeNeeded is the size of the next object we need to write tothe log. May be 0 if this is called on behalf of LogManager.flush().
   */
  // line 81 "../../../../LogBufferPool.ump"
  public void writeBufferToFile(int sizeNeeded) throws IOException,DatabaseException{
    int bufferSize = ((logBufferSize > sizeNeeded) ? logBufferSize : sizeNeeded);
	this.hook488();
	LogBuffer latchedBuffer = currentWriteBuffer;
	this.hook487(bufferSize, latchedBuffer);
  }


  /**
   * 
   * A loggable object has been freshly marshalled into the write log buffer. 1. Update buffer so it knows what LSNs it contains. 2. If this object requires a flush, write this buffer out to the  backing file. Assumes log write latch is held.
   */
  // line 91 "../../../../LogBufferPool.ump"
  public void writeCompleted(long lsn, boolean flushRequired) throws DatabaseException,IOException{
    currentWriteBuffer.registerLsn(lsn);
	if (flushRequired) {
	    writeBufferToFile(0);
	}
  }


  /**
   * 
   * Find a buffer that holds this LSN.
   * @return the buffer that contains this LSN, latched and ready toread, or return null.
   */
  // line 102 "../../../../LogBufferPool.ump"
  public LogBuffer getReadBuffer(long lsn) throws DatabaseException{
    LogBuffer foundBuffer = null;
	foundBuffer = this.hook489(lsn, foundBuffer);
	if (foundBuffer == null) {
	    return null;
	} else {
	    return foundBuffer;
	}
  }

  // line 112 "../../../../LogBufferPool.ump"
   protected void hook485(EnvironmentImpl envImpl) throws DatabaseException{
    
  }

  // line 115 "../../../../LogBufferPool.ump"
   protected void hook486() throws DatabaseException{
    
  }

  // line 118 "../../../../LogBufferPool.ump"
   protected void hook487(int bufferSize, LogBuffer latchedBuffer) throws IOException,DatabaseException{
    ByteBuffer currentByteBuffer = currentWriteBuffer.getDataBuffer();
	int savePosition = currentByteBuffer.position();
	int saveLimit = currentByteBuffer.limit();
	currentByteBuffer.flip();
	if (runInMemory) {
	    this.hook492(latchedBuffer);
	    latchedBuffer = null;
	    this.hook491();
	    currentWriteBuffer = new LogBuffer(bufferSize, envImpl);
	    bufferPool.add(currentWriteBuffer);
	    this.hook490();
	} else {
	    try {
		fileManager.writeLogBuffer(currentWriteBuffer);
		currentWriteBuffer.getDataBuffer().rewind();
		this.hook494(latchedBuffer);
		latchedBuffer = null;
		LogBuffer nextToUse = null;
		this.hook493(nextToUse);
	    } catch (DatabaseException DE) {
		currentByteBuffer.position(savePosition);
		currentByteBuffer.limit(saveLimit);
		throw DE;
	    }
	}
  }

  // line 146 "../../../../LogBufferPool.ump"
   protected void hook488() throws IOException,DatabaseException{
    
  }

  // line 149 "../../../../LogBufferPool.ump"
   protected LogBuffer hook489(long lsn, LogBuffer foundBuffer) throws DatabaseException{
    Iterator iter = bufferPool.iterator();
	while (iter.hasNext()) {
	    LogBuffer l = (LogBuffer) iter.next();
	    if (l.containsLsn(lsn)) {
		foundBuffer = l;
		break;
	    }
	}
	if (foundBuffer == null && currentWriteBuffer.containsLsn(lsn)) {
	    foundBuffer = currentWriteBuffer;
	}
	if (foundBuffer == null) {
	    this.hook496();
	}
	return foundBuffer;
  }

  // line 167 "../../../../LogBufferPool.ump"
   protected void hook490() throws IOException,DatabaseException{
    
  }

  // line 170 "../../../../LogBufferPool.ump"
   protected void hook491() throws IOException,DatabaseException{
    
  }

  // line 173 "../../../../LogBufferPool.ump"
   protected void hook492(LogBuffer latchedBuffer) throws IOException,DatabaseException{
    
  }

  // line 176 "../../../../LogBufferPool.ump"
   protected void hook493(LogBuffer nextToUse) throws IOException,DatabaseException{
    this.hook495();
	Iterator iter = bufferPool.iterator();
	nextToUse = (LogBuffer) iter.next();
	boolean done = bufferPool.remove(nextToUse);
	assert done;
	nextToUse.reinit();
	bufferPool.add(nextToUse);
	currentWriteBuffer = nextToUse;
  }

  // line 187 "../../../../LogBufferPool.ump"
   protected void hook494(LogBuffer latchedBuffer) throws IOException,DatabaseException{
    
  }

  // line 190 "../../../../LogBufferPool.ump"
   protected void hook495() throws IOException,DatabaseException{
    
  }

  // line 193 "../../../../LogBufferPool.ump"
   protected void hook496() throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../LogBufferPool.ump"
  private static final String DEBUG_NAME = LogBufferPool.class.getName() ;
// line 16 "../../../../LogBufferPool.ump"
  private EnvironmentImpl envImpl = null ;
// line 18 "../../../../LogBufferPool.ump"
  private int logBufferSize ;
// line 20 "../../../../LogBufferPool.ump"
  private LinkedList bufferPool ;
// line 22 "../../../../LogBufferPool.ump"
  private LogBuffer currentWriteBuffer ;
// line 24 "../../../../LogBufferPool.ump"
  private FileManager fileManager ;
// line 26 "../../../../LogBufferPool.ump"
  private boolean runInMemory ;

  
}