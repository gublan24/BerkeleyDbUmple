/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../LogManager.ump"
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

  // line 10 "../../../../LogManager.ump"
   protected void hook502(EnvironmentImpl envImpl) throws DatabaseException{
    logWriteLatch = LatchSupport.makeLatch(DEBUG_NAME, envImpl);
	original(envImpl);
  }

  // line 16 "../../../../LogManager.ump"
   protected boolean hook503(ByteBuffer marshalledBuffer, int entrySize, long currentLsn, boolean usedTemporaryBuffer, LogBuffer useLogBuffer) throws IOException,DatabaseException,Exception{
    useLogBuffer.latchForWrite();
	try {
	    usedTemporaryBuffer = original(marshalledBuffer, entrySize, currentLsn, usedTemporaryBuffer, useLogBuffer);
	} finally {
	    useLogBuffer.release();
	}
	return usedTemporaryBuffer;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../LogManager.ump"
  protected Latch logWriteLatch ;

  
}