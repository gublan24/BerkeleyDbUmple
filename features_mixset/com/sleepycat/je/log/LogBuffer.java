/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;

// line 3 "../../../../LogBuffer.ump"
// line 3 "../../../../DiskFullErro_LogBuffer.ump"
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

  // line 20 "../../../../LogBuffer.ump"
  public  LogBuffer(int capacity, EnvironmentImpl env) throws DatabaseException{
    this.hook481(capacity);
	this.hook482(capacity);
	this.hook479(env);
	reinit();
  }

  // line 27 "../../../../LogBuffer.ump"
  public  LogBuffer(ByteBuffer buffer, long firstLsn) throws DatabaseException{
    this.buffer = buffer;
	this.firstLsn = firstLsn;
	this.lastLsn = firstLsn;
  }

  // line 33 "../../../../LogBuffer.ump"
  public void reinit() throws DatabaseException{
    buffer.clear();
	firstLsn = DbLsn.NULL_LSN;
	lastLsn = DbLsn.NULL_LSN;
    // line 20 "../../../../DiskFullErro_LogBuffer.ump"
    //original();
    	rewriteAllowed = false;
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Return first LSN held in this buffer. Assumes the log write latch is held.
   */
  // line 42 "../../../../LogBuffer.ump"
  public long getFirstLsn(){
    return firstLsn;
  }


  /**
   * 
   * This LSN has been written to the log.
   */
  // line 49 "../../../../LogBuffer.ump"
  public void registerLsn(long lsn) throws DatabaseException{
    if (lastLsn != DbLsn.NULL_LSN) {
	    assert (DbLsn.compareTo(lsn, lastLsn) > 0);
	}
	lastLsn = lsn;
	if (firstLsn == DbLsn.NULL_LSN) {
	    firstLsn = lsn;
	}
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
    boolean found = false;
	if ((firstLsn != DbLsn.NULL_LSN)
		&& ((DbLsn.compareTo(firstLsn, lsn) <= 0) && (DbLsn.compareTo(lastLsn, lsn) >= 0))) {
	    found = true;
	}
	if (found) {
	    return true;
	} else {
	    this.hook480();
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
		copy.position((int) (fileOffset - DbLsn.getFileOffset(firstLsn)));
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
	assert (copy.remaining() >= numBytes) : "copy.remaining=" + copy.remaining() + " numBytes=" + numBytes;
	return copy;
  }

  // line 125 "../../../../LogBuffer.ump"
   protected void hook479(EnvironmentImpl env) throws DatabaseException{
    
  }

  // line 128 "../../../../LogBuffer.ump"
   protected void hook480() throws DatabaseException{
    
  }

  // line 131 "../../../../LogBuffer.ump"
   protected void hook481(int capacity) throws DatabaseException{
    
  }

  // line 134 "../../../../LogBuffer.ump"
   protected void hook482(int capacity) throws DatabaseException{
    
  }

  // line 137 "../../../../LogBuffer.ump"
   public void release() throws DatabaseException{
    
  }

  // line 8 "../../../../DiskFullErro_LogBuffer.ump"
  public boolean getRewriteAllowed(){
    return rewriteAllowed;
  }

  // line 12 "../../../../DiskFullErro_LogBuffer.ump"
  public void setRewriteAllowed(){
    rewriteAllowed = true;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../LogBuffer.ump"
  private static final String DEBUG_NAME = LogBuffer.class.getName() ;
// line 13 "../../../../LogBuffer.ump"
  private ByteBuffer buffer ;
// line 15 "../../../../LogBuffer.ump"
  private long firstLsn ;
// line 17 "../../../../LogBuffer.ump"
  private long lastLsn ;
// line 5 "../../../../DiskFullErro_LogBuffer.ump"
  private boolean rewriteAllowed ;

  
}