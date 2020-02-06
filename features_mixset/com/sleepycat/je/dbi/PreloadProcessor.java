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
			Label535:   ; //this.hook353(stats);
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
			Label355:   ;
			Label354:   ; //this.hook354(childType);
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

  
}