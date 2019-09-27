/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;

// line 3 "../../../../SharedLatchImpl.ump"
public class SharedLatchImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SharedLatchImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../SharedLatchImpl.ump"
   protected void hook429() throws DatabaseException,InterruptedException{
    stats.nAcquiresNoWaiters++;
	original();
  }

  // line 13 "../../../../SharedLatchImpl.ump"
   protected void hook430() throws DatabaseException,InterruptedException{
    stats.nAcquiresWithContention++;
	original();
  }

  // line 18 "../../../../SharedLatchImpl.ump"
   protected void hook431() throws DatabaseException{
    stats.nAcquiresNoWaiters++;
	original();
  }

  // line 23 "../../../../SharedLatchImpl.ump"
   protected void hook432() throws DatabaseException,InterruptedException{
    stats.nAcquireSharedSuccessful++;
	original();
  }

  // line 28 "../../../../SharedLatchImpl.ump"
   protected void hook433() throws LatchNotHeldException{
    stats.nReleases++;
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../SharedLatchImpl.ump"
  private LatchStats stats = new LatchStats() ;

  
}