/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import com.sleepycat.je.latch.LatchSupport;

// line 3 "../../../../RecoveryManager.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../RecoveryManager.ump"
   protected void hook585(IN in) throws DatabaseException{
    in.latch();
	original(in);
  }

  // line 14 "../../../../RecoveryManager.ump"
   protected void hook586(RecoveryInfo info, LNFileReader reader, TreeLocation location, LN ln, long logLsn, long abortLsn, boolean abortKnownDeleted, DatabaseImpl db) throws IOException,DatabaseException,Exception{
    try {
	    original(info, reader, location, ln, logLsn, abortLsn, abortKnownDeleted, db);
	} finally {
	    if (location.bin != null) {
		location.bin.releaseLatchIfOwner();
	    }
	}
  }

  // line 24 "../../../../RecoveryManager.ump"
   protected void hook587(IN inFromLog, long logLsn) throws DatabaseException{
    inFromLog.releaseLatchIfOwner();
	assert (LatchSupport.countLatchesHeld() == 0) : LatchSupport.latchesHeldToString() + "LSN = "
		+ DbLsn.toString(logLsn) + " inFromLog = " + inFromLog.getNodeId();
	original(inFromLog, logLsn);
  }

  // line 31 "../../../../RecoveryManager.ump"
   protected void hook588(SearchResult result) throws DatabaseException{
    if (result.parent != null) {
	    result.parent.releaseLatch();
	}
	original(result);
  }

  // line 38 "../../../../RecoveryManager.ump"
   protected void hook589(IN parent) throws DatabaseException{
    if (parent != null) {
	    parent.releaseLatch();
	}
	original(parent);
  }

  // line 45 "../../../../RecoveryManager.ump"
   protected void hook590(SearchResult result) throws DatabaseException{
    if (result.parent != null) {
	    result.parent.releaseLatch();
	}
	original(result);
  }

  // line 52 "../../../../RecoveryManager.ump"
   protected void hook591(TreeLocation location) throws DatabaseException{
    if (location.bin != null) {
	    location.bin.releaseLatchIfOwner();
	}
	original(location);
  }

  // line 60 "../../../../RecoveryManager.ump"
   protected static  boolean hook592(TreeLocation location, long logLsn, long abortLsn, boolean replaced, DIN duplicateRoot) throws DatabaseException{
    duplicateRoot.latch();
	try {
	    replaced = original(location, logLsn, abortLsn, replaced, duplicateRoot);
	} finally {
	    duplicateRoot.releaseLatch();
	}
	return replaced;
  }

}