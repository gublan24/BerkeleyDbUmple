/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../FSyncManager.ump"
public class FSyncManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FSyncManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../FSyncManager.ump"
   protected void hook434(EnvironmentImpl envImpl) throws DatabaseException{
    fsyncLatch = LatchSupport.makeLatch("fsyncLatch", envImpl);
	original(envImpl);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../FSyncManager.ump"
  private Latch fsyncLatch ;

  
}