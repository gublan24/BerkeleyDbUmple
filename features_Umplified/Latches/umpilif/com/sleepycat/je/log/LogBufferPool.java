/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

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

  // line 10 "../../../../LogBufferPool.ump"
   protected void hook485(EnvironmentImpl envImpl) throws DatabaseException{
    bufferPoolLatch = LatchSupport.makeLatch(DEBUG_NAME + "_FullLatch", envImpl);
	original(envImpl);
  }


  /**
   * 
   * Initialize the pool at construction time and when the cache is resized. This method is called after the memory budget has been calculated.
   */
  // line 18 "../../../../LogBufferPool.ump"
  public void reset(DbConfigManager configManager) throws DatabaseException{
    original(configManager);
	bufferPoolLatch.release();
  }

  // line 23 "../../../../LogBufferPool.ump"
   protected void hook486() throws DatabaseException{
    bufferPoolLatch.acquire();
	original();
  }

  // line 28 "../../../../LogBufferPool.ump"
   protected void hook487(int bufferSize, LogBuffer latchedBuffer) throws IOException,DatabaseException{
    try {
	    original(bufferSize, latchedBuffer);
	} finally {
	    if (latchedBuffer != null) {
		latchedBuffer.release();
	    }
	}
  }

  // line 38 "../../../../LogBufferPool.ump"
   protected void hook488() throws IOException,DatabaseException{
    currentWriteBuffer.latchForWrite();
	original();
  }

  // line 43 "../../../../LogBufferPool.ump"
   protected LogBuffer hook489(long lsn, LogBuffer foundBuffer) throws DatabaseException{
    bufferPoolLatch.acquire();
	try {
	    foundBuffer = original(lsn, foundBuffer);
	} finally {
	    bufferPoolLatch.releaseIfOwner();
	}
	return foundBuffer;
  }

  // line 53 "../../../../LogBufferPool.ump"
   protected void hook490() throws IOException,DatabaseException{
    bufferPoolLatch.release();
	original();
  }

  // line 58 "../../../../LogBufferPool.ump"
   protected void hook491() throws IOException,DatabaseException{
    bufferPoolLatch.acquire();
	original();
  }

  // line 63 "../../../../LogBufferPool.ump"
   protected void hook492(LogBuffer latchedBuffer) throws IOException,DatabaseException{
    latchedBuffer.release();
	original(latchedBuffer);
  }

  // line 68 "../../../../LogBufferPool.ump"
   protected void hook493(LogBuffer nextToUse) throws IOException,DatabaseException{
    try {
	    original(nextToUse);
	} finally {
	    bufferPoolLatch.releaseIfOwner();
	}
  }

  // line 76 "../../../../LogBufferPool.ump"
   protected void hook494(LogBuffer latchedBuffer) throws IOException,DatabaseException{
    latchedBuffer.release();
	original(latchedBuffer);
  }

  // line 81 "../../../../LogBufferPool.ump"
   protected void hook495() throws IOException,DatabaseException{
    bufferPoolLatch.acquire();
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../LogBufferPool.ump"
  private Latch bufferPoolLatch ;

  
}