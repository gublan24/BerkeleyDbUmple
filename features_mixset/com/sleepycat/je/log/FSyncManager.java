/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.PropUtil;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../FSync_FSyncManager.ump"
// line 3 "../../../../FSync_FSyncManager_inner.ump"
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

  // line 20 "../../../../FSync_FSyncManager.ump"
  public  FSyncManager(EnvironmentImpl envImpl) throws DatabaseException{
    timeout = PropUtil.microsToMillis(envImpl.getConfigManager().getLong(EnvironmentParams.LOG_FSYNC_TIMEOUT));
	this.envImpl = envImpl;
	Label434:           ;  //this.hook434(envImpl);
	fsyncInProgress = false;
	nextFSyncWaiters = new FSyncGroup(timeout, envImpl);
  }


  /**
   * 
   * Request that this file be fsynced to disk. This thread may or may not actually execute the fsync, but will not return until a fsync has been issued and executed on behalf of its write. There is a timeout period specified by EnvironmentParam.LOG_FSYNC_TIMEOUT that ensures that no thread gets stuck here indefinitely. When a thread comes in, it will find one of two things.  1. There is no fsync going on right now. This thread should go ahead and fsync. 2. There is an active fsync, wait until it's over before starting a new fsync. When a fsync is going on, all those threads that come along are grouped together as the nextFsyncWaiters. When the current fsync is finished, one of those nextFsyncWaiters will be selected as a leader to issue the next fsync. The other members of the group will merely wait until the fsync done on their behalf is finished. When a thread finishes a fsync, it has to: 1. wake up all the threads that were waiting for its fsync call. 2. wake up one member of the next group of waiting threads (the  nextFsyncWaiters) so that thread can become the new leader and issue the next fysnc call. If a non-leader member of the nextFsyncWaiters times out, it will issue its own fsync anyway, in case something happened to the leader.
   */
  // line 31 "../../../../FSync_FSyncManager.ump"
  public void fsync() throws DatabaseException{
    boolean doFsync = false;
	boolean isLeader = false;
	boolean needToWait = false;
	FSyncGroup inProgressGroup = null;
	FSyncGroup myGroup = null;
	synchronized (this) {
	    Label435:           ;  //this.hook435();
	    if (fsyncInProgress) {
		needToWait = true;
		myGroup = nextFSyncWaiters;
	    } else {
		isLeader = true;
		doFsync = true;
		fsyncInProgress = true;
		inProgressGroup = nextFSyncWaiters;
		nextFSyncWaiters = new FSyncGroup(timeout, envImpl);
	    }
	}
	if (needToWait) {
	    int waitStatus = myGroup.waitForFsync();
	    if (waitStatus == FSyncGroup.DO_LEADER_FSYNC) {
		synchronized (this) {
		    if (!fsyncInProgress) {
			isLeader = true;
			doFsync = true;
			fsyncInProgress = true;
			inProgressGroup = myGroup;
			nextFSyncWaiters = new FSyncGroup(timeout, envImpl);
		    }
		}
	    } else if (waitStatus == FSyncGroup.DO_TIMEOUT_FSYNC) {
		doFsync = true;
		Label436:           ;  //this.hook436();
	    }
	}
	if (doFsync) {
	    executeFSync();
	    synchronized (this) {
		Label437:           ;  //this.hook437();
		if (isLeader) {
		    inProgressGroup.wakeupAll();
		    nextFSyncWaiters.wakeupOne();
		    fsyncInProgress = false;
		}
	    }
	}
  }


  /**
   * 
   * Put the fsync execution into this method so it can be overridden for testing purposes.
   */
  // line 83 "../../../../FSync_FSyncManager.ump"
   protected void executeFSync() throws DatabaseException{
    envImpl.getFileManager().syncLogEnd();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../FSync_FSyncManager_inner.ump"
  public static class FSyncGroup
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FSyncGroup()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 13 "../../../../FSync_FSyncManager_inner.ump"
    public  FSyncGroup(long fsyncTimeout, EnvironmentImpl envImpl){
      this.fsyncTimeout=fsyncTimeout;
          fsyncDone=false;
          leaderExists=false;
          this.envImpl=envImpl;
    }
  
    // line 19 "../../../../FSync_FSyncManager_inner.ump"
     synchronized  boolean getLeader(){
      if (fsyncDone) {
            return false;
          }
     else {
            if (leaderExists) {
              return false;
            }
     else {
              leaderExists=true;
              return true;
            }
          }
    }
  
  
    /**
     * 
     * Wait for either a turn to execute a fsync, or to find out that a
     * fsync was done on your behalf.
     * @return true if the fsync wasn't done, and this thread needs to
     * execute a fsync when it wakes up. This may be true because it's the
     * leader of its group, or because the wait timed out.
     */
    // line 40 "../../../../FSync_FSyncManager_inner.ump"
     synchronized  int waitForFsync() throws RunRecoveryException{
      int status=0;
          if (!fsyncDone) {
            long startTime=System.currentTimeMillis();
            while (true) {
              try {
                wait(fsyncTimeout);
              }
     catch (          InterruptedException e) {
                throw new RunRecoveryException(envImpl,"Unexpected interrupt while waiting for fsync",e);
              }
              if (fsyncDone) {
                status=NO_FSYNC_NEEDED;
                break;
              }
     else {
                if (!leaderExists) {
                  leaderExists=true;
                  status=DO_LEADER_FSYNC;
                  break;
                }
     else {
                  long now=System.currentTimeMillis();
                  if ((now - startTime) > fsyncTimeout) {
                    status=DO_TIMEOUT_FSYNC;
                    break;
                  }
                }
              }
            }
          }
          return status;
    }
  
    // line 73 "../../../../FSync_FSyncManager_inner.ump"
     synchronized  void wakeupAll(){
      fsyncDone=true;
          notifyAll();
    }
  
    // line 77 "../../../../FSync_FSyncManager_inner.ump"
     synchronized  void wakeupOne(){
      notify();
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../FSync_FSyncManager_inner.ump"
    static int DO_TIMEOUT_FSYNC=0 ;
  // line 6 "../../../../FSync_FSyncManager_inner.ump"
    static int DO_LEADER_FSYNC=1 ;
  // line 7 "../../../../FSync_FSyncManager_inner.ump"
    static int NO_FSYNC_NEEDED=2 ;
  // line 8 "../../../../FSync_FSyncManager_inner.ump"
    private volatile boolean fsyncDone ;
  // line 9 "../../../../FSync_FSyncManager_inner.ump"
    private long fsyncTimeout ;
  // line 10 "../../../../FSync_FSyncManager_inner.ump"
    private boolean leaderExists ;
  // line 11 "../../../../FSync_FSyncManager_inner.ump"
    private EnvironmentImpl envImpl ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../FSync_FSyncManager.ump"
  private EnvironmentImpl envImpl ;
// line 13 "../../../../FSync_FSyncManager.ump"
  private long timeout ;
// line 15 "../../../../FSync_FSyncManager.ump"
  private volatile boolean fsyncInProgress ;
// line 17 "../../../../FSync_FSyncManager.ump"
  private FSyncGroup nextFSyncWaiters ;

  
}