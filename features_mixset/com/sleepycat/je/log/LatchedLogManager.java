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

// line 3 "../../../../Latches_LatchedLogManager.ump"
public class LatchedLogManager extends LogManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------



  /**
   * 
   * There is a single log manager per database environment.
   */
  // line 18 "../../../../Latches_LatchedLogManager.ump"
   public  LatchedLogManager(EnvironmentImpl envImpl, boolean readOnly) throws DatabaseException{
    super(envImpl, readOnly);
  }

  // line 24 "../../../../Latches_LatchedLogManager.ump"
   protected LogResult logItem(LoggableObject item, boolean isProvisional, boolean flushRequired, boolean forceNewLogFile, long oldNodeLsn, boolean marshallOutsideLatch, ByteBuffer marshalledBuffer, UtilizationTracker tracker) throws IOException,DatabaseException{
    logWriteLatch.acquire();
	try {
	    return logInternal(item, isProvisional, flushRequired, forceNewLogFile, oldNodeLsn, marshallOutsideLatch,
		    marshalledBuffer, tracker);
	} finally {
	    logWriteLatch.release();
	}
  }

  // line 34 "../../../../Latches_LatchedLogManager.ump"
   protected void flushInternal() throws LogException,DatabaseException{
    logWriteLatch.acquire();
	try {
	    logBufferPool.writeBufferToFile(0);
	} catch (IOException e) {
	    throw new LogException(e.getMessage());
	} finally {
	    logWriteLatch.release();
	}
  }


  /**
   * 
   * @see LogManager#getUnflusableTrackedSummary
   */
  // line 48 "../../../../Latches_LatchedLogManager.ump"
   public TrackedFileSummary getUnflushableTrackedSummary(long file) throws DatabaseException{
    logWriteLatch.acquire();
	try {
	    return getUnflushableTrackedSummaryInternal(file);
	} finally {
	    logWriteLatch.release();
	}
  }


  /**
   * 
   * @see LogManager#countObsoleteLNs
   */
  // line 60 "../../../../Latches_LatchedLogManager.ump"
   public void countObsoleteNode(long lsn, LogEntryType type) throws DatabaseException{
    UtilizationTracker tracker = envImpl.getUtilizationTracker();
	logWriteLatch.acquire();
	try {
	    countObsoleteNodeInternal(tracker, lsn, type);
	} finally {
	    logWriteLatch.release();
	}
  }


  /**
   * 
   * @see LogManager#countObsoleteNodes
   */
  // line 73 "../../../../Latches_LatchedLogManager.ump"
   public void countObsoleteNodes(TrackedFileSummary [] summaries) throws DatabaseException{
    UtilizationTracker tracker = envImpl.getUtilizationTracker();
	logWriteLatch.acquire();
	try {
	    countObsoleteNodesInternal(tracker, summaries);
	} finally {
	    logWriteLatch.release();
	}
  }


  /**
   * 
   * @see LogManager#countObsoleteINs
   */
  // line 86 "../../../../Latches_LatchedLogManager.ump"
   public void countObsoleteINs(List lsnList) throws DatabaseException{
    logWriteLatch.acquire();
	try {
	    countObsoleteINsInternal(lsnList);
	} finally {
	    logWriteLatch.release();
	}
  }

}