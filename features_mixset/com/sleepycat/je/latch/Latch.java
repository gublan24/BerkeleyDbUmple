/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.latch;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;
// line 3 "../../../../Latches_Latch.ump"
public interface Latch
{
  
  public void setName(String name) ;

  public void acquire() throws DatabaseException ;

  public boolean acquireNoWait() throws LatchException ;

  public void releaseIfOwner() ;

  public void release() throws LatchNotHeldException ;

  public boolean isOwner() ;

  public Thread owner() ;

  public int nWaiters() ;

  public String toString() ;

}