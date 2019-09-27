/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

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

  // line 6 "../../../../LogBufferPool.ump"
   protected void hook483(long bufferBytes, int nLogBuffers) throws DatabaseException{
    try {
	    original(bufferBytes, nLogBuffers);
	} finally {
	    bufferPoolLatch.release();
	}
  }

  // line 14 "../../../../LogBufferPool.ump"
   protected void hook484() throws DatabaseException{
    bufferPoolLatch.acquire();
	original();
  }

}