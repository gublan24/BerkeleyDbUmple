/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import java.io.RandomAccessFile;
import java.io.IOException;

// line 3 "../../../../FileSource.ump"
public class FileSource implements LogSource
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileSource()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 19 "../../../../FileSource.ump"
  public  FileSource(RandomAccessFile file, int readBufferSize, FileManager fileManager){
    this.file = file;
	this.readBufferSize = readBufferSize;
	this.fileManager = fileManager;
  }


  /**
   * 
   * @see LogSource#release
   */
  // line 28 "../../../../FileSource.ump"
   public void release() throws DatabaseException{
    
  }


  /**
   * 
   * @see LogSource#getBytes
   */
  // line 34 "../../../../FileSource.ump"
   public ByteBuffer getBytes(long fileOffset) throws IOException{
    ByteBuffer destBuf = ByteBuffer.allocate(readBufferSize);
	fileManager.readFromFile(file, destBuf, fileOffset);
	assert EnvironmentImpl.maybeForceYield();
	destBuf.flip();
	return destBuf;
  }


  /**
   * 
   * @see LogSource#getBytes
   */
  // line 45 "../../../../FileSource.ump"
   public ByteBuffer getBytes(long fileOffset, int numBytes) throws IOException{
    ByteBuffer destBuf = ByteBuffer.allocate(numBytes);
	fileManager.readFromFile(file, destBuf, fileOffset);
	assert EnvironmentImpl.maybeForceYield();
	destBuf.flip();
	assert destBuf.remaining() >= numBytes : "remaining=" + destBuf.remaining() + " numBytes=" + numBytes;
	return destBuf;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../FileSource.ump"
  private RandomAccessFile file ;
// line 14 "../../../../FileSource.ump"
  private int readBufferSize ;
// line 16 "../../../../FileSource.ump"
  private FileManager fileManager ;

  
}