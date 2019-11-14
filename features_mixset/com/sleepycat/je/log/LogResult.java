/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../LogResult.ump"
// line 3 "../../../../Derivative_CheckpointerDaemon_CPBytes_LogResult.ump"
public class LogResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LogResult Attributes
  private long currentLsn;
  private boolean wakeupCleaner;
  private boolean wakeupCheckpointer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogResult(long aCurrentLsn, boolean aWakeupCleaner, boolean aWakeupCheckpointer)
  {
    currentLsn = aCurrentLsn;
    wakeupCleaner = aWakeupCleaner;
    wakeupCheckpointer = aWakeupCheckpointer;
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

  public boolean setWakeupCheckpointer(boolean aWakeupCheckpointer)
  {
    boolean wasSet = false;
    wakeupCheckpointer = aWakeupCheckpointer;
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

  public boolean getWakeupCheckpointer()
  {
    return wakeupCheckpointer;
  }

  public void delete()
  {}

  // line 11 "../../../../LogResult.ump"
  public  LogResult(long currentLsn, boolean wakeupCheckpointer, boolean wakeupCleaner){
    this.currentLsn = currentLsn;
	Label510:
this.wakeupCheckpointer = wakeupCheckpointer;
	//original(wakeupCheckpointer);
 //this.hook510(wakeupCheckpointer);
	this.wakeupCleaner = wakeupCleaner;
  }


  public String toString()
  {
    return super.toString() + "["+
            "currentLsn" + ":" + getCurrentLsn()+ "," +
            "wakeupCleaner" + ":" + getWakeupCleaner()+ "," +
            "wakeupCheckpointer" + ":" + getWakeupCheckpointer()+ "]";
  }
}