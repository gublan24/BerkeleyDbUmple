/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.cleaner.TrackedFileSummary;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.dbi.EnvironmentImpl;

/**
 * 
 * The SyncedLogManager uses the synchronized keyword to implement protected
 * regions.
 */
// line 16 "../../../../SyncedLogManager.ump"
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
  // line 36 "../../../../SyncedLogManager.ump"
   public  SyncedLogManager(EnvironmentImpl envImpl, boolean readOnly) throws DatabaseException{
    super(envImpl, readOnly);
  }

  // line 49 "../../../../SyncedLogManager.ump"
   protected LogResult logItem(LoggableObject item, boolean isProvisional, boolean flushRequired, boolean forceNewLogFile, long oldNodeLsn, boolean marshallOutsideLatch, ByteBuffer marshalledBuffer, UtilizationTracker tracker) throws IOException,DatabaseException{
    // line 21 "../../../../Latches_SyncedLogManager.ump"
    synchronized (logWriteLatch)
    // END OF UMPLE BEFORE INJECTION
    {
            return logInternal(item, isProvisional,
                               flushRequired, forceNewLogFile, oldNodeLsn, 
                               marshallOutsideLatch, marshalledBuffer,
                               tracker);
        }
  }

  // line 60 "../../../../SyncedLogManager.ump"
   protected void flushInternal() throws LogException,DatabaseException{
    try {
            Label512:
synchronized (logWriteLatch)
  {
                logBufferPool.writeBufferToFile(0);
            }
        } catch (IOException e) {
            throw new LogException(e.getMessage());
        }
  }


  /**
   * 
   * @see LogManager#getUnflushableTrackedSummary
   */
  // line 75 "../../../../SyncedLogManager.ump"
   public TrackedFileSummary getUnflushableTrackedSummary(long file) throws DatabaseException{
    // line 32 "../../../../Latches_SyncedLogManager.ump"
    synchronized (logWriteLatch)
    // END OF UMPLE BEFORE INJECTION
    {
            return getUnflushableTrackedSummaryInternal(file);
        }
  }


  /**
   * 
   * @see LogManager#countObsoleteLNs
   */
  // line 86 "../../../../SyncedLogManager.ump"
   public void countObsoleteNode(long lsn, LogEntryType type) throws DatabaseException{
    UtilizationTracker tracker = envImpl.getUtilizationTracker();
        Label514:
synchronized (logWriteLatch)
 {
            countObsoleteNodeInternal(tracker, lsn, type);
        }
  }


  /**
   * 
   * @see LogManager#countObsoleteNodes
   */
  // line 98 "../../../../SyncedLogManager.ump"
   public void countObsoleteNodes(TrackedFileSummary [] summaries) throws DatabaseException{
    UtilizationTracker tracker = envImpl.getUtilizationTracker();
        Label511:
synchronized (logWriteLatch)
 {
            countObsoleteNodesInternal(tracker, summaries);
        }
  }


  /**
   * 
   * @see LogManager#countObsoleteINs
   */
  // line 110 "../../../../SyncedLogManager.ump"
   public void countObsoleteINs(List lsnList) throws DatabaseException{
    Label515:
synchronized (logWriteLatch)
 {
            countObsoleteINsInternal(lsnList);
        }
  }

}