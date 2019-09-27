/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../SyncedLogManager.ump"
public class SyncedLogManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SyncedLogManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../SyncedLogManager.ump"
   protected void hook511(LoggableObject item, boolean isProvisional, boolean flushRequired, boolean forceNewLogFile, long oldNodeLsn, boolean marshallOutsideLatch, ByteBuffer marshalledBuffer, UtilizationTracker tracker) throws IOException,DatabaseException{
    synchronized (logWriteLatch) {
	    original(item, isProvisional, flushRequired, forceNewLogFile, oldNodeLsn, marshallOutsideLatch,
		    marshalledBuffer, tracker);
	}
  }

  // line 15 "../../../../SyncedLogManager.ump"
   protected void hook512() throws LogException,DatabaseException,IOException{
    synchronized (logWriteLatch) {
	    original();
	}
  }

  // line 21 "../../../../SyncedLogManager.ump"
   protected void hook513(long file) throws DatabaseException{
    synchronized (logWriteLatch) {
	    original(file);
	}
  }

  // line 27 "../../../../SyncedLogManager.ump"
   protected void hook514(long lsn, LogEntryType type, UtilizationTracker tracker) throws DatabaseException{
    synchronized (logWriteLatch) {
	    original(lsn, type, tracker);
	}
  }

  // line 33 "../../../../SyncedLogManager.ump"
   protected void hook515(TrackedFileSummary [] summaries, UtilizationTracker tracker) throws DatabaseException{
    synchronized (logWriteLatch) {
	    original(summaries, tracker);
	}
  }


  /**
   * 
   * @see LogManager#countObsoleteINs
   */
  // line 42 "../../../../SyncedLogManager.ump"
   public void countObsoleteINs(List lsnList) throws DatabaseException{
    synchronized (logWriteLatch) {
	    original(lsnList);
	}
  }

}