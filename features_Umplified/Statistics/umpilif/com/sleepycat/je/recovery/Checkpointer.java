/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../Checkpointer.ump"
// line 3 "../../../../Checkpointer_inner.ump"
public class Checkpointer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Checkpointer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Load stats.
   */
  // line 25 "../../../../Checkpointer.ump"
   public void loadStats(StatsConfig config, EnvironmentStats stat) throws DatabaseException{
    stat.setNCheckpoints(nCheckpoints);
	stat.setLastCheckpointStart(lastCheckpointStart);
	stat.setLastCheckpointEnd(lastCheckpointEnd);
	stat.setLastCheckpointId(checkpointId);
	stat.setNFullINFlush(nFullINFlush);
	stat.setNFullBINFlush(nFullBINFlush);
	stat.setNDeltaINFlush(nDeltaINFlush);
	if (config.getClear()) {
	    nCheckpoints = 0;
	    nFullINFlush = 0;
	    nFullBINFlush = 0;
	    nDeltaINFlush = 0;
	}
  }


  /**
   * 
   * Reset per-run counters.
   */
  // line 44 "../../../../Checkpointer.ump"
   private void resetPerRunCounters(){
    nFullINFlushThisRun = 0;
	nDeltaINFlushThisRun = 0;
  }

  // line 49 "../../../../Checkpointer.ump"
   protected void hook531() throws DatabaseException{
    nCheckpoints = 0;
	original();
  }

  // line 54 "../../../../Checkpointer.ump"
   protected void hook532() throws DatabaseException{
    nFullINFlushThisRun++;
	nFullINFlush++;
	original();
  }

  // line 60 "../../../../Checkpointer.ump"
   protected void hook533(IN target) throws DatabaseException{
    nFullINFlushThisRun++;
	nFullINFlush++;
	if (target instanceof BIN) {
	    nFullBINFlush++;
	}
	original(target);
  }

  // line 69 "../../../../Checkpointer.ump"
   protected void hook537() throws DatabaseException{
    nDeltaINFlushThisRun++;
	nDeltaINFlush++;
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 4 "../../../../Checkpointer_inner.ump"
  public static class Checkpointer_doCheckpoint
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_doCheckpoint()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Checkpointer_inner.ump"
     protected void hook534() throws DatabaseException{
      _this.nCheckpoints++;
          original();
    }
  
    // line 10 "../../../../Checkpointer_inner.ump"
     protected void hook535() throws DatabaseException{
      _this.resetPerRunCounters();
          original();
    }
  
    // line 14 "../../../../Checkpointer_inner.ump"
     protected void hook536() throws DatabaseException{
      _this.lastCheckpointStart=checkpointStart;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../Checkpointer.ump"
  private int nCheckpoints ;
// line 9 "../../../../Checkpointer.ump"
  private long lastCheckpointStart ;
// line 11 "../../../../Checkpointer.ump"
  private int nFullINFlush ;
// line 13 "../../../../Checkpointer.ump"
  private int nFullBINFlush ;
// line 15 "../../../../Checkpointer.ump"
  private int nDeltaINFlush ;
// line 17 "../../../../Checkpointer.ump"
  private int nFullINFlushThisRun ;
// line 19 "../../../../Checkpointer.ump"
  private int nDeltaINFlushThisRun ;

  
}