/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../LogBuffer.ump"
// line 3 "../../../../DiskFullErro_LogBuffer.ump"
// line 3 "../../../../IO_LogBuffer.ump"
// line 3 "../../../../NIO_LogBuffer.ump"
// line 3 "../../../../Latches_LogBuffer.ump"
public class LogBuffer implements LogSource
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

  // line 19 "../../../../LogBuffer.ump"
  public  LogBuffer(int capacity, EnvironmentImpl env) throws DatabaseException{
    Label481:
buffer = ByteBuffer.allocateDirect(capacity);
			//original(capacity);
 //this.hook481(capacity);
        Label482:
buffer = ByteBuffer.allocate(capacity);
			//original(capacity);
 //this.hook482(capacity);
        Label479://    this.hook479(env);
        reinit();
  }

  // line 26 "../../../../LogBuffer.ump"
  public  LogBuffer(ByteBuffer buffer, long firstLsn) throws DatabaseException{
    this.buffer = buffer;
        this.firstLsn = firstLsn;
        this.lastLsn = firstLsn;
  }

  // line 32 "../../../../LogBuffer.ump"
  public void reinit() throws DatabaseException{
    // line 30 "../../../../Latches_LogBuffer.ump"
    readLatch.acquire();
    	//original();
    	//readLatch.release();
    // END OF UMPLE BEFORE INJECTION
    buffer.clear();
        firstLsn = DbLsn.NULL_LSN;
        lastLsn = DbLsn.NULL_LSN;
    // line 20 "../../../../DiskFullErro_LogBuffer.ump"
    //original();
    	rewriteAllowed = false;
    // END OF UMPLE AFTER INJECTION
    // line 37 "../../../../Latches_LogBuffer.ump"
    readLatch.release();
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Return first LSN held in this buffer. Assumes the log write latch is held.
   */
  // line 41 "../../../../LogBuffer.ump"
  public long getFirstLsn(){
    return firstLsn;
  }


  /**
   * 
   * This LSN has been written to the log.
   */
  // line 49 "../../../../LogBuffer.ump"
  public void registerLsn(long lsn) throws DatabaseException{
    // line 44 "../../../../Latches_LogBuffer.ump"
    readLatch.acquire();
    // END OF UMPLE BEFORE INJECTION
    if (lastLsn != DbLsn.NULL_LSN) {
            assert(DbLsn.compareTo(lsn, lastLsn) > 0);
        }
        lastLsn = lsn;
        if (firstLsn == DbLsn.NULL_LSN) {
            firstLsn = lsn;
        }
    // line 49 "../../../../Latches_LogBuffer.ump"
    readLatch.release();
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Check capacity of buffer. Assumes that the log write latch is held.
   * @return true if this buffer can hold this many more bytes.
   */
  // line 63 "../../../../LogBuffer.ump"
  public boolean hasRoom(int numBytes){
    return (numBytes <= (buffer.capacity() - buffer.position()));
  }


  /**
   * 
   * @return the actual data buffer.
   */
  // line 70 "../../../../LogBuffer.ump"
  public ByteBuffer getDataBuffer(){
    return buffer;
  }


  /**
   * 
   * @return capacity in bytes
   */
  // line 77 "../../../../LogBuffer.ump"
  public int getCapacity(){
    return buffer.capacity();
  }


  /**
   * 
   * Support for reading a log entry out of a still-in-memory log
   * @return true if this buffer holds the entry at this LSN. The buffer willbe latched for read. Returns false if LSN is not here, and releases the read latch.
   */
  // line 85 "../../../../LogBuffer.ump"
  public boolean containsLsn(long lsn) throws DatabaseException{
    // line 59 "../../../../Latches_LogBuffer.ump"
    readLatch.acquire();
    	//return //original(lsn);
    // END OF UMPLE BEFORE INJECTION
    boolean found = false;
        if ((firstLsn != DbLsn.NULL_LSN) &&
            ((DbLsn.compareTo(firstLsn, lsn) <= 0) && (DbLsn.compareTo(lastLsn, lsn) >= 0))) {
            found = true;
        }
        if (found) {
            return true;
        } else {
            Label480:
readLatch.release();
	//original();
 //this.hook480();
            return false;
        }
  }


  /**
   * 
   * @see LogSource#getBytes
   */
  // line 102 "../../../../LogBuffer.ump"
   public ByteBuffer getBytes(long fileOffset){
    ByteBuffer copy = null;
        while (true) {
            try {
                copy = buffer.duplicate();
                copy.position((int)(fileOffset - DbLsn.getFileOffset(firstLsn)));
                break;
            } catch (IllegalArgumentException IAE) {
                continue;
            }
        }
        return copy;
  }


  /**
   * 
   * @see LogSource#getBytes
   */
  // line 119 "../../../../LogBuffer.ump"
   public ByteBuffer getBytes(long fileOffset, int numBytes){
    ByteBuffer copy = getBytes(fileOffset);
        assert(copy.remaining() >= numBytes): "copy.remaining=" + copy.remaining() + " numBytes=" + numBytes;
        return copy;
  }


  /**
   * protected void hook479(EnvironmentImpl env) throws DatabaseException {}
   * protected void hook480() throws DatabaseException {}
   * protected void hook481(int capacity) throws DatabaseException {}
   * protected void hook482(int capacity) throws DatabaseException {}
   */
  // line 132 "../../../../LogBuffer.ump"
   public void release() throws DatabaseException{
    readLatch.releaseIfOwner();
  }

  // line 8 "../../../../DiskFullErro_LogBuffer.ump"
  public boolean getRewriteAllowed(){
    return rewriteAllowed;
  }

  // line 12 "../../../../DiskFullErro_LogBuffer.ump"
  public void setRewriteAllowed(){
    rewriteAllowed = true;
  }


  /**
   * 
   * When modifying the buffer, acquire the readLatch. Call release() to release the latch. Note that containsLsn() acquires the latch for reading.
   */
  // line 13 "../../../../Latches_LogBuffer.ump"
   public void latchForWrite() throws DatabaseException{
    readLatch.acquire();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../LogBuffer.ump"
  private static final String DEBUG_NAME = LogBuffer.class.getName() ;
// line 12 "../../../../LogBuffer.ump"
  private ByteBuffer buffer ;
// line 14 "../../../../LogBuffer.ump"
  private long firstLsn ;
// line 16 "../../../../LogBuffer.ump"
  private long lastLsn ;
// line 5 "../../../../DiskFullErro_LogBuffer.ump"
  private boolean rewriteAllowed ;
// line 7 "../../../../Latches_LogBuffer.ump"
  private Latch readLatch ;

// line 23 "../../../../Latches_LogBuffer.ump"
  after Label479 :  LogBuffer (int , EnvironmentImpl ) 
  {
    readLatch = LatchSupport.makeLatch(DEBUG_NAME, env);
	////original(env);
  }

  
}