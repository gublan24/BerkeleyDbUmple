/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;

// line 3 "../../../../Java5LatchImpl.ump"
public class Java5LatchImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Java5LatchImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * @return a LatchStats object with information about this latch.
   */
  // line 11 "../../../../Java5LatchImpl.ump"
   public LatchStats getLatchStats(){
    LatchStats s = null;
	try {
	    s = (LatchStats) stats.clone();
	} catch (CloneNotSupportedException e) {
	}
	return s;
  }

  // line 20 "../../../../Java5LatchImpl.ump"
   protected void hook416() throws DatabaseException{
    if (lock.isLocked()) {
	    stats.nAcquiresWithContention++;
	} else {
	    stats.nAcquiresNoWaiters++;
	}
	original();
  }

  // line 29 "../../../../Java5LatchImpl.ump"
   protected void hook417() throws DatabaseException{
    stats.nAcquiresSelfOwned++;
	original();
  }

  // line 34 "../../../../Java5LatchImpl.ump"
   protected void hook418() throws LatchException{
    stats.nAcquiresSelfOwned++;
	original();
  }

  // line 39 "../../../../Java5LatchImpl.ump"
   protected void hook419() throws LatchException{
    stats.nAcquireNoWaitSuccessful++;
	original();
  }

  // line 44 "../../../../Java5LatchImpl.ump"
   protected void hook420() throws LatchException{
    stats.nAcquireNoWaitUnsuccessful++;
	original();
  }

  // line 49 "../../../../Java5LatchImpl.ump"
   protected void hook421() throws IllegalMonitorStateException{
    stats.nReleases++;
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Java5LatchImpl.ump"
  private LatchStats stats = new LatchStats() ;

  
}