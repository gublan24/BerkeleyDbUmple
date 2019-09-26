/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
// line 3 "../../../../DaemonRunner.ump"
public interface DaemonRunner
{
  
  // ABSTRACT METHODS 

 public void runOrPause(boolean run);
 public void requestShutdown();
 public void shutdown();
 public int getNWakeupRequests();
}