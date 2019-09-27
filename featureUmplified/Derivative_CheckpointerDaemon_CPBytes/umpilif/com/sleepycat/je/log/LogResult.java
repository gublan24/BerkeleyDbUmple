/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../LogResult.ump"
public class LogResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LogResult Attributes
  private boolean wakeupCheckpointer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogResult(boolean aWakeupCheckpointer)
  {
    wakeupCheckpointer = aWakeupCheckpointer;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWakeupCheckpointer(boolean aWakeupCheckpointer)
  {
    boolean wasSet = false;
    wakeupCheckpointer = aWakeupCheckpointer;
    wasSet = true;
    return wasSet;
  }

  public boolean getWakeupCheckpointer()
  {
    return wakeupCheckpointer;
  }

  public void delete()
  {}

  // line 8 "../../../../LogResult.ump"
   protected void hook510(boolean wakeupCheckpointer){
    this.wakeupCheckpointer = wakeupCheckpointer;
	original(wakeupCheckpointer);
  }


  public String toString()
  {
    return super.toString() + "["+
            "wakeupCheckpointer" + ":" + getWakeupCheckpointer()+ "]";
  }
}