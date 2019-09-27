/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;

// line 3 "../../../../LatchImpl.ump"
public class LatchImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LatchImpl()
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
  // line 11 "../../../../LatchImpl.ump"
   public LatchStats getLatchStats(){
    LatchStats s = null;
	try {
	    s = (LatchStats) stats.clone();
	} catch (CloneNotSupportedException e) {
	}
	return s;
  }

  // line 20 "../../../../LatchImpl.ump"
   protected void hook422() throws DatabaseException,InterruptedException{
    stats.nAcquiresSelfOwned++;
	original();
  }

  // line 25 "../../../../LatchImpl.ump"
   protected void hook423() throws DatabaseException,InterruptedException{
    stats.nAcquiresNoWaiters++;
	original();
  }

  // line 30 "../../../../LatchImpl.ump"
   protected void hook424() throws DatabaseException,InterruptedException{
    stats.nAcquiresWithContention++;
	original();
  }

  // line 35 "../../../../LatchImpl.ump"
   protected void hook425() throws LatchException{
    stats.nAcquiresSelfOwned++;
	original();
  }

  // line 40 "../../../../LatchImpl.ump"
   protected void hook426() throws LatchException{
    stats.nAcquireNoWaitSuccessful++;
	original();
  }

  // line 45 "../../../../LatchImpl.ump"
   protected void hook427() throws LatchException{
    stats.nAcquireNoWaitUnsuccessful++;
	original();
  }

  // line 50 "../../../../LatchImpl.ump"
   protected void hook428(){
    stats.nReleases++;
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../LatchImpl.ump"
  private LatchStats stats = new LatchStats() ;

  
}