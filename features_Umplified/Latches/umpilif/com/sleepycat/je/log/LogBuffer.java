/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../LogBuffer.ump"
public class LogBuffer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogBuffer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * When modifying the buffer, acquire the readLatch. Call release() to release the latch. Note that containsLsn() acquires the latch for reading.
   */
  // line 13 "../../../../LogBuffer.ump"
   public void latchForWrite() throws DatabaseException{
    readLatch.acquire();
  }


  /**
   * 
   * @see LogSource#release
   */
  // line 20 "../../../../LogBuffer.ump"
   public void release() throws DatabaseException{
    readLatch.releaseIfOwner();
  }

  // line 24 "../../../../LogBuffer.ump"
   protected void hook479(EnvironmentImpl env) throws DatabaseException{
    readLatch = LatchSupport.makeLatch(DEBUG_NAME, env);
	original(env);
  }

  // line 29 "../../../../LogBuffer.ump"
  public void reinit() throws DatabaseException{
    readLatch.acquire();
	original();
	readLatch.release();
  }


  /**
   * 
   * This LSN has been written to the log.
   */
  // line 38 "../../../../LogBuffer.ump"
  public void registerLsn(long lsn) throws DatabaseException{
    readLatch.acquire();
	try {
	    original(lsn);
	} finally {
	    readLatch.release();
	}
  }


  /**
   * 
   * Support for reading a log entry out of a still-in-memory log
   * @return true if this buffer holds the entry at this LSN. The buffer willbe latched for read. Returns false if LSN is not here, and releases the read latch.
   */
  // line 51 "../../../../LogBuffer.ump"
  public boolean containsLsn(long lsn) throws DatabaseException{
    readLatch.acquire();
	return original(lsn);
  }

  // line 56 "../../../../LogBuffer.ump"
   protected void hook480() throws DatabaseException{
    readLatch.release();
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../LogBuffer.ump"
  private Latch readLatch ;

  
}