/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../LogResult.ump"
public class LogResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LogResult Attributes
  private long currentLsn;
  private boolean wakeupCleaner;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogResult(long aCurrentLsn, boolean aWakeupCleaner)
  {
    currentLsn = aCurrentLsn;
    wakeupCleaner = aWakeupCleaner;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrentLsn(long aCurrentLsn)
  {
    boolean wasSet = false;
    currentLsn = aCurrentLsn;
    wasSet = true;
    return wasSet;
  }

  public boolean setWakeupCleaner(boolean aWakeupCleaner)
  {
    boolean wasSet = false;
    wakeupCleaner = aWakeupCleaner;
    wasSet = true;
    return wasSet;
  }

  public long getCurrentLsn()
  {
    return currentLsn;
  }

  public boolean getWakeupCleaner()
  {
    return wakeupCleaner;
  }

  public void delete()
  {}

  // line 11 "../../../../LogResult.ump"
  public  LogResult(long currentLsn, boolean wakeupCheckpointer, boolean wakeupCleaner){
    this.currentLsn = currentLsn;
	this.hook510(wakeupCheckpointer);
	this.wakeupCleaner = wakeupCleaner;
  }

  // line 17 "../../../../LogResult.ump"
   protected void hook510(boolean wakeupCheckpointer){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "currentLsn" + ":" + getCurrentLsn()+ "," +
            "wakeupCleaner" + ":" + getWakeupCleaner()+ "]";
  }
}