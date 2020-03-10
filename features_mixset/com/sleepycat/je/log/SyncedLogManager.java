/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.cleaner.TrackedFileSummary;
import com.sleepycat.je.DatabaseException;
import java.util.List;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../SyncedLogManager.ump"
// line 3 "../../../../Latches_SyncedLogManager.ump"
public class SyncedLogManager extends LogManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SyncedLogManager()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * There is a single log manager per database environment.
   */
  // line 18 "../../../../SyncedLogManager.ump"
   public  SyncedLogManager(EnvironmentImpl envImpl, boolean readOnly) throws DatabaseException{
    super(envImpl, readOnly);
  }

  // line 24 "../../../../SyncedLogManager.ump"
   protected LogResult logItem(LoggableObject item, boolean isProvisional, boolean flushRequired, boolean forceNewLogFile, long oldNodeLsn, boolean marshallOutsideLatch, ByteBuffer marshalledBuffer, UtilizationTracker tracker) throws IOException,DatabaseException{
     synchronized (logWriteLatch) {
       return logInternal(item, isProvisional,
                          flushRequired, forceNewLogFile, oldNodeLsn, 
                          marshallOutsideLatch, marshalledBuffer,
                          tracker);
   }
  }

  // line 34 "../../../../SyncedLogManager.ump"
   protected void flushInternal() throws LogException,DatabaseException{
    try {
	    this.hook512();
	} catch (IOException e) {
	    throw new LogException(e.getMessage());
	}
  }


  /**
   * 
   * @see LogManager#getUnflushableTrackedSummary
   */
  // line 45 "../../../../SyncedLogManager.ump"
   public TrackedFileSummary getUnflushableTrackedSummary(long file) throws DatabaseException{
    try {
	    this.hook513(file);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (TrackedFileSummary) r.value;
	}
  }


  /**
   * 
   * @see LogManager#countObsoleteLNs
   */
  // line 57 "../../../../SyncedLogManager.ump"
   public void countObsoleteNode(long lsn, LogEntryType type) throws DatabaseException{
    UtilizationTracker tracker = envImpl.getUtilizationTracker();
	this.hook514(lsn, type, tracker);
  }


  /**
   * 
   * @see LogManager#countObsoleteNodes
   */
  // line 65 "../../../../SyncedLogManager.ump"
   public void countObsoleteNodes(TrackedFileSummary [] summaries) throws DatabaseException{
    UtilizationTracker tracker = envImpl.getUtilizationTracker();
	this.hook515(summaries, tracker);
  }


  /**
   * 
   * @see LogManager#countObsoleteINs
   */
  // line 73 "../../../../SyncedLogManager.ump"
   public void countObsoleteINs(List lsnList) throws DatabaseException{
    countObsoleteINsInternal(lsnList);
  }

  // line 79 "../../../../SyncedLogManager.ump"
   protected void hook511(LoggableObject item, boolean isProvisional, boolean flushRequired, boolean forceNewLogFile, long oldNodeLsn, boolean marshallOutsideLatch, ByteBuffer marshalledBuffer, UtilizationTracker tracker) throws IOException,DatabaseException{
    throw new ReturnObject(logInternal(item, isProvisional, flushRequired, forceNewLogFile, oldNodeLsn,
		marshallOutsideLatch, marshalledBuffer, tracker));
  }

  // line 84 "../../../../SyncedLogManager.ump"
   protected void hook512() throws LogException,DatabaseException,IOException{
    logBufferPool.writeBufferToFile(0);
  }

  // line 88 "../../../../SyncedLogManager.ump"
   protected void hook513(long file) throws DatabaseException{
    throw new ReturnObject(getUnflushableTrackedSummaryInternal(file));
  }

  // line 92 "../../../../SyncedLogManager.ump"
   protected void hook514(long lsn, LogEntryType type, UtilizationTracker tracker) throws DatabaseException{
    countObsoleteNodeInternal(tracker, lsn, type);
  }

  // line 96 "../../../../SyncedLogManager.ump"
   protected void hook515(TrackedFileSummary [] summaries, UtilizationTracker tracker) throws DatabaseException{
    countObsoleteNodesInternal(tracker, summaries);
  }

}