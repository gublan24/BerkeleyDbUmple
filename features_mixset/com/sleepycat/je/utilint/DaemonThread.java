/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DeadlockException;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../DaemonThread.ump"
public class DaemonThread implements DaemonRunner,Runnable
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

  // line 36 "../../../../DaemonThread.ump"
   protected  DaemonThread(){
    
  }

  // line 39 "../../../../DaemonThread.ump"
   public  DaemonThread(long waitTime, String name, EnvironmentImpl env){
    init(waitTime, name, env);
  }

  // line 43 "../../../../DaemonThread.ump"
   protected void init(long waitTime, String name, EnvironmentImpl env){
    this.waitTime = waitTime;
			this.name = name;
			this.env = env;
			workQueue = new HashSet();
			Label856: //this.hook856(name, env);
  }


  /**
   * 
   * For testing.
   */
  // line 54 "../../../../DaemonThread.ump"
   public Thread getThread(){
    return thread;
  }


  /**
   * 
   * If run is true, starts the thread if not started or unpauses it if already started; if run is false, pauses the thread if started or does nothing if not started.
   */
  // line 61 "../../../../DaemonThread.ump"
   public void runOrPause(boolean run){
    if (run) {
	    paused = false;
	    if (thread != null) {
		wakeup();
	    } else {
		thread = new Thread(this, name);
		thread.setDaemon(true);
		thread.start();
	    }
	} else {
	    paused = true;
	}
  }

  // line 76 "../../../../DaemonThread.ump"
   public void requestShutdown(){
    shutdownRequest = true;
  }


  /**
   * 
   * Requests shutdown and calls join() to wait for the thread to stop.
   */
  // line 83 "../../../../DaemonThread.ump"
   public void shutdown(){
    if (thread != null) {
	    shutdownRequest = true;
	    while (thread.isAlive()) {
		synchronized (synchronizer) {
		    synchronizer.notifyAll();
		}
		try {
		    thread.join(JOIN_MILLIS);
		} catch (InterruptedException e) {
		}
	    }
	    thread = null;
	}
  }

  // line 99 "../../../../DaemonThread.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("<DaemonThread name=\"").append(name).append("\"/>");
	return sb.toString();
  }

  // line 105 "../../../../DaemonThread.ump"
   public void addToQueue(Object o) throws DatabaseException{
    workQueue.add(o);
	wakeup();
  }

  // line 110 "../../../../DaemonThread.ump"
   public int getQueueSize() throws DatabaseException{
    int count = workQueue.size();
	return count;
  }

  // line 115 "../../../../DaemonThread.ump"
   public void addToQueueAlreadyLatched(Collection c) throws DatabaseException{
    workQueue.addAll(c);
  }

  // line 119 "../../../../DaemonThread.ump"
   public void wakeup(){
    if (!paused) {
	    synchronized (synchronizer) {
		synchronizer.notifyAll();
	    }
	}
  }

  // line 127 "../../../../DaemonThread.ump"
   public void run(){
    while (true) {
	    if (shutdownRequest) {
		break;
	    }
	    try {
		Label858: //this.hook858();
		boolean nothingToDo = workQueue.size() == 0;
		Label857: //this.hook857();
		if (nothingToDo) {
		    synchronized (synchronizer) {
			if (waitTime == 0) {
			    synchronizer.wait();
			} else {
			    synchronizer.wait(waitTime);
			}
		    }
		}
		if (shutdownRequest) {
		    break;
		}
		if (paused) {
		    synchronized (synchronizer) {
			synchronizer.wait();
		    }
		    continue;
		}
		int numTries = 0;
		int maxRetries = nDeadlockRetries();
		do {
		    try {
			nWakeupRequests++;
			running = true;
			onWakeup();
			break;
		    } catch (DeadlockException e) {
		    } finally {
			running = false;
		    }
		    numTries++;
		    if (shutdownRequest) {
			break;
		    }
		} while (numTries <= maxRetries);
		if (shutdownRequest) {
		    break;
		}
	    } catch (InterruptedException IE) {
		System.err.println("Shutting down " + this + " due to exception: " + IE);
		shutdownRequest = true;
	    } catch (Exception E) {
		System.err.println(this + " caught exception: " + E);
		E.printStackTrace(System.err);
		if (env.mayNotWrite()) {
		    System.err.println("Exiting");
		    shutdownRequest = true;
		} else {
		    System.err.println("Continuing");
		}
	    }
	}
  }


  /**
   * 
   * Returns the number of retries to perform when Deadlock Exceptions occur.
   */
  // line 193 "../../../../DaemonThread.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return 0;
  }


  /**
   * 
   * Returns whether shutdown has been requested. This method should be used to to terminate daemon loops.
   */
  // line 205 "../../../../DaemonThread.ump"
   protected boolean isShutdownRequested(){
    return shutdownRequest;
  }


  /**
   * 
   * Returns whether the onWakeup method is currently executing. This is only an approximation and is used to avoid unnecessary wakeups.
   */
  // line 212 "../../../../DaemonThread.ump"
   public boolean isRunning(){
    return running;
  }


  /**
   * 
   * For unit testing.
   */
  // line 219 "../../../../DaemonThread.ump"
   public int getNWakeupRequests(){
    return nWakeupRequests;
  }

  // line 223 "../../../../DaemonThread.ump"
   protected void hook856(String name, EnvironmentImpl env){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../DaemonThread.ump"
  private static final int JOIN_MILLIS = 10 ;
// line 15 "../../../../DaemonThread.ump"
  private long waitTime ;
// line 17 "../../../../DaemonThread.ump"
  private Object synchronizer = new Object() ;
// line 19 "../../../../DaemonThread.ump"
  private Thread thread ;
// line 21 "../../../../DaemonThread.ump"
  private EnvironmentImpl env ;
// line 23 "../../../../DaemonThread.ump"
  protected String name ;
// line 25 "../../../../DaemonThread.ump"
  protected Set workQueue ;
// line 27 "../../../../DaemonThread.ump"
  protected int nWakeupRequests ;
// line 29 "../../../../DaemonThread.ump"
  private volatile boolean shutdownRequest = false ;
// line 31 "../../../../DaemonThread.ump"
  private volatile boolean paused = false ;
// line 33 "../../../../DaemonThread.ump"
  private boolean running = false ;
// line 199 "../../../../DaemonThread.ump"
  abstract protected void onWakeup() throws DatabaseException ;

  
}