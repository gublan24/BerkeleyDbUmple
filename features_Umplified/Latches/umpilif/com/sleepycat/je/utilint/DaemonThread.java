/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../DaemonThread.ump"
public class DaemonThread
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DaemonThread()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../DaemonThread.ump"
   protected void hook856(String name, EnvironmentImpl env){
    workQueueLatch = LatchSupport.makeLatch(name + " work queue", env);
	original(name, env);
  }

  // line 15 "../../../../DaemonThread.ump"
   public void addToQueue(Object o) throws DatabaseException{
    workQueueLatch.acquire();
	original(o);
	workQueueLatch.release();
  }

  // line 21 "../../../../DaemonThread.ump"
   public int getQueueSize() throws DatabaseException{
    workQueueLatch.acquire();
	int result = original();
	workQueueLatch.release();
	return result;
  }

  // line 28 "../../../../DaemonThread.ump"
   protected void hook857() throws InterruptedException,Exception{
    workQueueLatch.release();
	original();
  }

  // line 33 "../../../../DaemonThread.ump"
   protected void hook858() throws InterruptedException,Exception{
    workQueueLatch.acquire();
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../DaemonThread.ump"
  protected Latch workQueueLatch ;

  
}