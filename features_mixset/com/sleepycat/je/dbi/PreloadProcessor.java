/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.SortedLSNTreeWalker.TreeNodeProcessor;
import com.sleepycat.je.PreloadStats;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../PreloadProcessor.ump"
// line 3 "../../../../MemoryBudget_PreloadProcessor.ump"
// line 3 "../../../../Statistics_PreloadProcessor.ump"
public class PreloadProcessor implements TreeNodeProcessor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadProcessor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 18 "../../../../PreloadProcessor.ump"
  public  PreloadProcessor(EnvironmentImpl envImpl, long maxBytes, long targetTime, PreloadStats stats){
    this.envImpl = envImpl;
			this.maxBytes = maxBytes;
			this.targetTime = targetTime;
			Label535:
this.stats = stats;
			//original(stats);
 //this.hook353(stats);
  }


  /**
   * 
   * Called for each LSN that the SortedLSNTreeWalker encounters.
   */
  // line 28 "../../../../PreloadProcessor.ump"
   public void processLSN(long childLsn, LogEntryType childType){
    assert childLsn != DbLsn.NULL_LSN;
			if (System.currentTimeMillis() > targetTime) {
					throw DatabaseImpl.timeExceededPreloadException;
			}
			//this.hook355();
			Label355:
if (envImpl.getMemoryBudget().getCacheMemoryUsage() > maxBytes) {
	    throw DatabaseImpl.memoryExceededPreloadException;
	}
//	original();

			Label354:
if (childType.equals(LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL)
			|| childType.equals(LogEntryType.LOG_DUPCOUNTLN)) {
			  stats.nDupCountLNsLoaded++;
		} else if (childType.equals(LogEntryType.LOG_LN_TRANSACTIONAL) || childType.equals(LogEntryType.LOG_LN)) {
			  stats.nLNsLoaded++;
		} else if (childType.equals(LogEntryType.LOG_DBIN)) {
			  stats.nDBINsLoaded++;
		} else if (childType.equals(LogEntryType.LOG_BIN)) {
			  stats.nBINsLoaded++;
		} else if (childType.equals(LogEntryType.LOG_DIN)) {
			  stats.nDINsLoaded++;
		} else if (childType.equals(LogEntryType.LOG_IN)) {
			  stats.nINsLoaded++;
		}
		//original(childType);
 //this.hook354(childType);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../PreloadProcessor.ump"
  private EnvironmentImpl envImpl ;
// line 13 "../../../../PreloadProcessor.ump"
  private long maxBytes ;
// line 15 "../../../../PreloadProcessor.ump"
  private long targetTime ;
// line 5 "../../../../Statistics_PreloadProcessor.ump"
  private PreloadStats stats ;

  
}