/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.latch;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
// line 3 "../../../../Latches_SharedLatch.ump"
public interface SharedLatch
{
  
  public void setName(String name) ;

  public void setNoteLatch(boolean noteLatch) ;

  public void acquireExclusive()
	throws DatabaseException ;

  public boolean acquireExclusiveNoWait()
	throws DatabaseException ;

  public void acquireShared()
        throws DatabaseException ;

  public void release()
	throws LatchNotHeldException ;

  public boolean isWriteLockedByCurrentThread() ;

}