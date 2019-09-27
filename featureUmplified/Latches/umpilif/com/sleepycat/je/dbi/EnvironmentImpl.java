/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.log.LatchedLogManager;
import com.sleepycat.je.latch.SharedLatch;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../EnvironmentImpl.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 16 "../../../../EnvironmentImpl.ump"
   public static  boolean getFairLatches(){
    return fairLatches;
  }


  /**
   * 
   * Returns the shared trigger latch.
   */
  // line 23 "../../../../EnvironmentImpl.ump"
   public SharedLatch getTriggerLatch(){
    return triggerLatch;
  }

  // line 27 "../../../../EnvironmentImpl.ump"
   protected void hook320() throws DatabaseException{
    triggerLatch = LatchSupport.makeSharedLatch("TriggerLatch", this);
	original();
  }

  // line 32 "../../../../EnvironmentImpl.ump"
   protected void hook321() throws DatabaseException{
    if (fairLatches) {
	    logManager = new LatchedLogManager(this, isReadOnly);
	} else {
	    original();
	}
  }

  // line 40 "../../../../EnvironmentImpl.ump"
   protected void hook322() throws DatabaseException{
    fairLatches = configManager.getBoolean(EnvironmentParams.ENV_FAIR_LATCHES);
	original();
  }

  // line 45 "../../../../EnvironmentImpl.ump"
   protected void hook323() throws DatabaseException{
    mapTreeRootLatch = LatchSupport.makeLatch("MapTreeRoot", this);
	original();
  }


  /**
   * 
   * Log the map tree root and save the LSN.
   */
  // line 53 "../../../../EnvironmentImpl.ump"
   public void logMapTreeRoot() throws DatabaseException{
    mapTreeRootLatch.acquire();
	try {
	    original();
	} finally {
	    mapTreeRootLatch.release();
	}
  }


  /**
   * 
   * Force a rewrite of the map tree root if required.
   */
  // line 65 "../../../../EnvironmentImpl.ump"
   public void rewriteMapTreeRoot(long cleanerTargetLsn) throws DatabaseException{
    mapTreeRootLatch.acquire();
	try {
	    original(cleanerTargetLsn);
	} finally {
	    mapTreeRootLatch.release();
	}
  }

  // line 74 "../../../../EnvironmentImpl.ump"
   protected void hook324(long rootLsn) throws DatabaseException{
    mapTreeRootLatch.acquire();
	try {
	    original(rootLsn);
	} finally {
	    mapTreeRootLatch.release();
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../EnvironmentImpl.ump"
  private static boolean fairLatches ;
// line 11 "../../../../EnvironmentImpl.ump"
  private Latch mapTreeRootLatch ;
// line 13 "../../../../EnvironmentImpl.ump"
  private SharedLatch triggerLatch ;

  
}