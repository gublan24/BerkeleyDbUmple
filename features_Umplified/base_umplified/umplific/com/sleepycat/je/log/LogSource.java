/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import java.io.IOException;
// line 3 "../../../../LogSource.ump"
public interface LogSource
{
  
  void release() throws DatabaseException ;

  ByteBuffer getBytes(long fileOffset) throws IOException ;

  ByteBuffer getBytes(long fileOffset, int numBytes) throws IOException ;

}