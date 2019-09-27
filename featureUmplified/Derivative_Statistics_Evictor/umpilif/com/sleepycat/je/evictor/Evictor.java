/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../Evictor.ump"
// line 3 "../../../../Evictor_inner.ump"
public class Evictor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor()
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
  // line 27 "../../../../Evictor.ump"
   public void loadStats(StatsConfig config, EnvironmentStats stat) throws DatabaseException{
    stat.setNEvictPasses(nEvictPasses);
	stat.setNNodesSelected(nNodesSelected);
	stat.setNNodesScanned(nNodesScanned);
	stat.setNNodesExplicitlyEvicted(nNodesEvicted);
	stat.setNBINsStripped(nBINsStripped);
	stat.setRequiredEvictBytes(currentRequiredEvictBytes);
	if (config.getClear()) {
	    nEvictPasses = 0;
	    nNodesSelected = 0;
	    nNodesScanned = 0;
	    nNodesEvicted = 0;
	    nBINsStripped = 0;
	}
  }

  // line 43 "../../../../Evictor.ump"
   protected void hook380(IN target) throws DatabaseException{
    if (target != null) {
	    nNodesSelectedThisRun++;
	    nNodesSelected++;
	}
	original(target);
  }

  // line 51 "../../../../Evictor.ump"
   protected void hook383(long evictedBytes) throws DatabaseException{
    if (evictedBytes > 0) {
	    nBINsStrippedThisRun++;
	    nBINsStripped++;
	}
	original(evictedBytes);
  }

  // line 59 "../../../../Evictor.ump"
   protected void hook384() throws DatabaseException{
    nNodesEvictedThisRun++;
	nNodesEvicted++;
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.evictor;
  
  @MethodObject
  // line 4 "../../../../Evictor_inner.ump"
  public static class Evictor_evictBatch
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_evictBatch()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
    public long execute() throws DatabaseException{
      _this.nNodesSelectedThisRun=0;
          _this.nNodesEvictedThisRun=0;
          return original();
    }
  
    // line 11 "../../../../Evictor_inner.ump"
     protected void hook381() throws DatabaseException{
      _this.nBINsStrippedThisRun=0;
          _this.nEvictPasses++;
          original();
    }
  
    // line 16 "../../../../Evictor_inner.ump"
     protected void hook382() throws DatabaseException{
      _this.nNodesScanned+=_this.nNodesScannedThisRun;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../Evictor.ump"
  private int nEvictPasses = 0 ;
// line 9 "../../../../Evictor.ump"
  private long nNodesSelected = 0 ;
// line 11 "../../../../Evictor.ump"
  private long nNodesSelectedThisRun ;
// line 13 "../../../../Evictor.ump"
  private int nNodesScanned = 0 ;
// line 15 "../../../../Evictor.ump"
  private long nNodesEvicted = 0 ;
// line 17 "../../../../Evictor.ump"
  private long nNodesEvictedThisRun ;
// line 19 "../../../../Evictor.ump"
  private long nBINsStripped = 0 ;
// line 21 "../../../../Evictor.ump"
  private long nBINsStrippedThisRun ;

  
}