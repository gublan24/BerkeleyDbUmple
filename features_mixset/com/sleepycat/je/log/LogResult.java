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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogResult()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../LogResult.ump"
  public  LogResult(long currentLsn, boolean wakeupCheckpointer, boolean wakeupCleaner){
    this.currentLsn = currentLsn;
	  Label510: //this.hook510(wakeupCheckpointer);
	  this.wakeupCleaner = wakeupCleaner;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../LogResult.ump"
  protected long currentLsn ;
// line 7 "../../../../LogResult.ump"
  protected boolean wakeupCleaner ;

  
}