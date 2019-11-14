/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.TestHook;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.DaemonThread;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.SearchResult;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.INList;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.text.NumberFormat;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.utilint.*;

// line 3 "../../../../Evictor_Evictor.ump"
// line 3 "../../../../Evictor_Evictor_inner.ump"
// line 3 "../../../../Derivative_Evictor_EvictorDaemon_Evictor.ump"
// line 3 "../../../../Derivative_Evictor_CriticalEviction_Evictor.ump"
// line 3 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
// line 3 "../../../../Derivative_Evictor_MemoryBudget_Evictor.ump"
// line 3 "../../../../Derivative_Evictor_MemoryBudget_Evictor_inner.ump"
// line 3 "../../../../Derivative_DeleteOp_Evictor_Evictor.ump"
// line 3 "../../../../Derivative_INCompressor_Evictor_Evictor.ump"
// line 3 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
// line 3 "../../../../Derivative_Statistics_Evictor_Evictor_inner.ump"
public class Evictor extends DaemonThread
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Evictor Attributes
  private EvictProfile evictProfile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor(EvictProfile aEvictProfile)
  {
    super();
    evictProfile = aEvictProfile;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEvictProfile(EvictProfile aEvictProfile)
  {
    boolean wasSet = false;
    evictProfile = aEvictProfile;
    wasSet = true;
    return wasSet;
  }

  public EvictProfile getEvictProfile()
  {
    return evictProfile;
  }

  public void delete()
  {
    super.delete();
  }

  // line 63 "../../../../Evictor_Evictor.ump"
   public  Evictor(EnvironmentImpl envImpl, String name) throws DatabaseException{
    super(0, name, envImpl);
			this.envImpl = envImpl;
			logManager = envImpl.getLogManager();
			nextNode = null;
			DbConfigManager configManager = envImpl.getConfigManager();
			nodesPerScan = configManager.getInt(EnvironmentParams.EVICTOR_NODES_PER_SCAN);
			evictBytesSetting = configManager.getLong(EnvironmentParams.EVICTOR_EVICT_BYTES);
			evictByLruOnly = configManager.getBoolean(EnvironmentParams.EVICTOR_LRU_ONLY);

      Label737:			//this.hook373(envImpl);
			evictProfile = new EvictProfile();
			formatter = NumberFormat.getNumberInstance();
			active = false;
  }

  // line 79 "../../../../Evictor_Evictor.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
			sb.append("<Evictor name=\"").append(name).append("\"/>");
			return sb.toString();
  }


  /**
   * 
   * Wakeup the evictor only if it's not already active.
   */
  // line 92 "../../../../Evictor_Evictor.ump"
   public void alert(){
    if (!active) {
					wakeup();
			}
  }


  /**
   * 
   * May be called by the evictor thread on wakeup or programatically.
   */
  // line 101 "../../../../Evictor_Evictor.ump"
   public void doEvict(String source) throws DatabaseException{
    doEvict(source, false);
  }


  /**
   * 
   * Allows performing eviction during shutdown, which is needed when during checkpointing and cleaner log file deletion.
   */
  // line 108 "../../../../Evictor_Evictor.ump"
   private synchronized  void doEvict(String source, boolean evictDuringShutdown) throws DatabaseException{
    if (active) {
					return;
			}
			active = true;
			try {
					boolean progress = true;
					while (progress && (evictDuringShutdown || !isShutdownRequested()) && isRunnable(source)) {
				if (evictBatch(source, currentRequiredEvictBytes) == 0) {
						progress = false;
				}
					}
			} finally {
					active = false;
			}
  }


  /**
   * 
   * Each iteration will latch and unlatch the major INList, and will attempt to evict requiredEvictBytes, but will give up after a complete pass over the major INList. Releasing the latch is important because it provides an opportunity for to add the minor INList to the major INList.
   * @return the number of bytes evicted, or zero if no progress was made.
   */
  // line 129 "../../../../Evictor_Evictor.ump"
  public long evictBatch(String source, long requiredEvictBytes) throws DatabaseException{
    return new Evictor_evictBatch(this, source, requiredEvictBytes).execute();
  }


  /**
   * 
   * Return true if eviction should happen.
   */
  // line 136 "../../../../Evictor_Evictor.ump"
  public boolean isRunnable(String source) throws DatabaseException{
    return new Evictor_isRunnable(this, source).execute();
  }


  /**
   * 
   * Select a single node to evict.
   */
  // line 143 "../../../../Evictor_Evictor.ump"
   private IN selectIN(INList inList, ScanIterator scanIter) throws DatabaseException{
    IN target = null;
			long targetGeneration = Long.MAX_VALUE;
			int targetLevel = Integer.MAX_VALUE;
			boolean targetDirty = true;
			boolean envIsReadOnly = envImpl.isReadOnly();
			int scanned = 0;
			boolean wrapped = false;
			while (scanned < nodesPerScan) {
					if (scanIter.hasNext()) {
				IN in = scanIter.next();
				nNodesScannedThisRun++;
				DatabaseImpl db = in.getDatabase();
				boolean b = db == null;
        Label387:				//b = this.hook387(db, b);
				if (b) {
						String inInfo = " IN type=" + in.getLogType() + " id=" + in.getNodeId() + " not expected on INList";
						String errMsg = (db == null) ? inInfo
							: "Database " + db.getDebugName() + " id=" + db.getId() + inInfo;
						throw new DatabaseException(errMsg);
				}
				boolean b2 = false;
        Label386:				//b2 = this.hook386(db, b2);
				if (b2) {
						continue;
				}
				if (db.getId().equals(DbTree.ID_DB_ID)) {
						continue;
				}
				if (envIsReadOnly && (target != null) && in.getDirty()) {
						continue;
				}
				int evictType = in.getEvictionType();
				if (evictType == IN.MAY_NOT_EVICT) {
						continue;
				}
				if (evictByLruOnly) {
						if (targetGeneration > in.getGeneration()) {
					targetGeneration = in.getGeneration();
					target = in;
						}
				} else {
						int level = normalizeLevel(in, evictType);
						if (targetLevel != level) {
					if (targetLevel > level) {
							targetLevel = level;
							targetDirty = in.getDirty();
							targetGeneration = in.getGeneration();
							target = in;
					}
						} else if (targetDirty != in.getDirty()) {
					if (targetDirty) {
							targetDirty = false;
							targetGeneration = in.getGeneration();
							target = in;
					}
						} else {
					if (targetGeneration > in.getGeneration()) {
							targetGeneration = in.getGeneration();
							target = in;
					}
						}
				}
				scanned++;
					} else {
				if (wrapped) {
						break;
				} else {
						nextNode = inList.first();
						scanIter.reset(nextNode);
						wrapped = true;
				}
					}
			}

      Label380:
if (target != null) {
	    nNodesSelectedThisRun++;
	    nNodesSelected++;
	}
	//original(target);
			//this.hook380(target);
			return target;
  }


  /**
   * 
   * Normalize the tree level of the given IN. Is public for unit testing. A BIN containing evictable LNs is given level 0, so it will be stripped first. For non-duplicate and DBMAP trees, the high order bits are cleared to make their levels correspond; that way, all bottom level nodes (BINs and DBINs) are given the same eviction priority. Note that BINs in a duplicate tree are assigned the same level as BINs in a non-duplicate tree. This isn't always optimimal, but is the best we can do considering that BINs in duplicate trees may contain a mix of LNs and DINs.
   */
  // line 225 "../../../../Evictor_Evictor.ump"
   public int normalizeLevel(IN in, int evictType){
    int level = in.getLevel() & IN.LEVEL_MASK;
	if (level == 1 && evictType == IN.MAY_EVICT_LNS) {
	    level = 0;
	}
	return level;
  }


  /**
   * 
   * Strip or evict this node.
   * @return number of bytes evicted.
   */
  // line 237 "../../../../Evictor_Evictor.ump"
   private long evict(INList inList, IN target, ScanIterator scanIter) throws DatabaseException{
    try {	
	boolean envIsReadOnly = envImpl.isReadOnly();
			long evictedBytes = 0;
			if (target.latchNoWait(false)) {
					//evictedBytes = this.hook374(inList, target, scanIter, envIsReadOnly, evictedBytes);
         Label374:
				if (target instanceof BIN) {

              Label385:
envImpl.lazyCompress(target);
	//original(target);
							//this.hook385(target);
							evictedBytes = ((BIN) target).evictLNs();

              Label383:
if (evictedBytes > 0) {
	    nBINsStrippedThisRun++;
	    nBINsStripped++;
	}
	//original(evictedBytes);
							//this.hook383(evictedBytes);
					}
					if (evictedBytes == 0 && target.isEvictable()) {
							Tree tree = target.getDatabase().getTree();
							SearchResult result = tree.getParentINForChildIN(target, true, false);
							if (result.exactParentFound) {
						evictedBytes = evictIN(target, result.parent, result.index, inList, scanIter, envIsReadOnly);
							}
					}
} finally {
Label374_1: ;//		// end hook374
}

			}
			return evictedBytes;
  }


  /**
   * 
   * Evict an IN. Dirty nodes are logged before they're evicted. inlist is latched with the major latch by the caller.
   */
  // line 270 "../../../../Evictor_Evictor.ump"
   private long evictIN(IN child, IN parent, int index, INList inlist, ScanIterator scanIter, boolean envIsReadOnly) throws DatabaseException{
    try{
			long evictBytes = 0;
			Label375:			//evictBytes = this.hook375(child, parent, index, inlist, scanIter, envIsReadOnly, evictBytes);
      Label378:			//this.hook378(parent);
			long oldGenerationCount = child.getGeneration();
			IN renewedChild = (IN) parent.getTarget(index);
			if ((renewedChild != null) && (renewedChild.getGeneration() <= oldGenerationCount)
				&& renewedChild.latchNoWait(false)) {
          Label379:					//evictBytes = this.hook379(parent, index, inlist, scanIter, envIsReadOnly, evictBytes, renewedChild);
					if (renewedChild.isEvictable()) {
							long renewedChildLsn = DbLsn.NULL_LSN;
							boolean newChildLsn = false;
							if (renewedChild.getDirty()) {
						if (!envIsReadOnly) {
								boolean logProvisional = (envImpl.getCheckpointer() != null
									&& (renewedChild.getLevel() < envImpl.getCheckpointer().getHighestFlushLevel()));
								renewedChildLsn = renewedChild.log(logManager, false, logProvisional, true, parent);
								newChildLsn = true;
						}
							} else {
						renewedChildLsn = parent.getLsn(index);
							}
							if (renewedChildLsn != DbLsn.NULL_LSN) {
						scanIter.mark();
						inlist.removeLatchAlreadyHeld(renewedChild);
						scanIter.resetToMark();
            Label389: 						//evictBytes = this.hook389(evictBytes, renewedChild);
						if (newChildLsn) {
								parent.updateEntry(index, null, renewedChildLsn);
						} else {
								parent.updateEntry(index, (Node) null);
						}

            Label384:
nNodesEvictedThisRun++;
	nNodesEvicted++;
	//original();
						//this.hook384();
							}
					}
					// end of hook379
			}
}finally {
Label375_1: ;//		// end hook375
}

			return evictBytes;
  }


  /**
   * 
   * Used by unit tests.
   */
  // line 319 "../../../../Evictor_Evictor.ump"
  public IN getNextNode(){
    return nextNode;
  }

  // line 323 "../../../../Evictor_Evictor.ump"
   public void setRunnableHook(TestHook hook){
    runnableHook = hook;
  }


  /**
   * 
   * Evictor doesn't have a work queue so just throw an exception if it's ever called.
   */
  // line 10 "../../../../Derivative_Evictor_EvictorDaemon_Evictor.ump"
   public void addToQueue(Object o) throws DatabaseException{
    throw new DatabaseException("Evictor.addToQueue should never be called.");
  }


  /**
   * 
   * Return the number of retries when a deadlock exception occurs.
   */
  // line 17 "../../../../Derivative_Evictor_EvictorDaemon_Evictor.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return envImpl.getConfigManager().getInt(EnvironmentParams.EVICTOR_RETRY);
  }


  /**
   * 
   * Called whenever the daemon thread wakes up from a sleep.
   */
  // line 24 "../../../../Derivative_Evictor_EvictorDaemon_Evictor.ump"
   public void onWakeup() throws DatabaseException{
    if (envImpl.isClosed()) {
	    return;
	}
	doEvict(SOURCE_DAEMON, false);
  }


  /**
   * 
   * Do a check on whether synchronous eviction is needed.
   */
  // line 9 "../../../../Derivative_Evictor_CriticalEviction_Evictor.ump"
   public void doCriticalEviction() throws DatabaseException{
    new Evictor_doCriticalEviction(this).execute();
  }


  /**
   * 
   * Load stats.
   */
  // line 27 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
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
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Evictor_Evictor_inner.ump"
  public static class EvictProfile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EvictProfile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 7 "../../../../Evictor_Evictor_inner.ump"
     public boolean count(IN target){
      candidates.add(new Long(target.getNodeId()));
          return true;
    }
  
    // line 11 "../../../../Evictor_Evictor_inner.ump"
     public List getCandidates(){
      return candidates;
    }
  
    // line 14 "../../../../Evictor_Evictor_inner.ump"
     public boolean clear(){
      candidates.clear();
          return true;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../Evictor_Evictor_inner.ump"
    private List candidates=new ArrayList() ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 18 "../../../../Evictor_Evictor_inner.ump"
  public static class ScanIterator
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public ScanIterator()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 23 "../../../../Evictor_Evictor_inner.ump"
    public  ScanIterator(IN startingIN, INList inList) throws DatabaseException{
      this.inList=inList;
          reset(startingIN);
    }
  
    // line 27 "../../../../Evictor_Evictor_inner.ump"
    public void reset(IN startingIN) throws DatabaseException{
      iter=inList.tailSet(startingIN).iterator();
    }
  
    // line 30 "../../../../Evictor_Evictor_inner.ump"
    public IN mark() throws DatabaseException{
      if (iter.hasNext()) {
            nextMark=(IN)iter.next();
          }
     else {
            nextMark=(IN)inList.first();
          }
          return (IN)nextMark;
    }
  
    // line 39 "../../../../Evictor_Evictor_inner.ump"
    public void resetToMark() throws DatabaseException{
      reset(nextMark);
    }
  
    // line 42 "../../../../Evictor_Evictor_inner.ump"
    public boolean hasNext(){
      return iter.hasNext();
    }
  
    // line 45 "../../../../Evictor_Evictor_inner.ump"
    public IN next(){
      return (IN)iter.next();
    }
  
    // line 48 "../../../../Evictor_Evictor_inner.ump"
    public void remove(){
      iter.remove();
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 19 "../../../../Evictor_Evictor_inner.ump"
    private INList inList ;
  // line 20 "../../../../Evictor_Evictor_inner.ump"
    private Iterator iter ;
  // line 21 "../../../../Evictor_Evictor_inner.ump"
    private IN nextMark ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 51 "../../../../Evictor_Evictor_inner.ump"
  // line 4 "../../../../Derivative_Statistics_Evictor_Evictor_inner.ump"
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
  
    // line 53 "../../../../Evictor_Evictor_inner.ump"
    public  Evictor_evictBatch(Evictor _this, String source, long requiredEvictBytes){
      this._this=_this;
          this.source=source;
          this.requiredEvictBytes=requiredEvictBytes;
    }
  
    // line 58 "../../../../Evictor_Evictor_inner.ump"
    public long execute() throws DatabaseException{
      // line 6 "../../../../Derivative_Statistics_Evictor_Evictor_inner.ump"
      _this.nNodesSelectedThisRun=0;
              _this.nNodesEvictedThisRun=0;
             // return original();
      // END OF UMPLE BEFORE INJECTION
      _this.nNodesScannedThisRun=0;
  
          Label381:
  _this.nBINsStrippedThisRun=0;
          _this.nEvictPasses++;
         // original();
          //this.hook381();
          assert _this.evictProfile.clear();
          nBatchSets=0;
          finished=false;
          evictBytes=0;
          evictBytes+=_this.envImpl.getUtilizationTracker().evictMemory();
          inList=_this.envImpl.getInMemoryINs();
  
          Label376:        //this.hook376();
          inListStartSize=inList.getSize();
          try {
            if (inListStartSize == 0) {
              _this.nextNode=null;
              return 0;
            }
     else {
              if (_this.nextNode == null) {
                _this.nextNode=inList.first();
              }
            }
            scanIter=new ScanIterator(_this.nextNode,inList);
            while ((evictBytes < requiredEvictBytes) && (_this.nNodesScannedThisRun <= inListStartSize)) {
              target=_this.selectIN(inList,scanIter);
              if (target == null) {
                break;
              }
     else {
                assert _this.evictProfile.count(target);
                evictBytes+=_this.evict(inList,target,scanIter);
              }
              nBatchSets++;
            }
            _this.nextNode=scanIter.mark();
            finished=true;
          }
      finally {
  
           	Label382:
  _this.nNodesScanned+=_this.nNodesScannedThisRun;
          //original();
           // this.hook382();
            Label377:         // this.hook377();
            Label371:          //this.hook371();
          }
          return evictBytes;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 103 "../../../../Evictor_Evictor_inner.ump"
    protected Evictor _this ;
  // line 104 "../../../../Evictor_Evictor_inner.ump"
    protected String source ;
  // line 105 "../../../../Evictor_Evictor_inner.ump"
    protected long requiredEvictBytes ;
  // line 106 "../../../../Evictor_Evictor_inner.ump"
    protected int nBatchSets ;
  // line 107 "../../../../Evictor_Evictor_inner.ump"
    protected boolean finished ;
  // line 108 "../../../../Evictor_Evictor_inner.ump"
    protected long evictBytes ;
  // line 109 "../../../../Evictor_Evictor_inner.ump"
    protected INList inList ;
  // line 110 "../../../../Evictor_Evictor_inner.ump"
    protected int inListStartSize ;
  // line 111 "../../../../Evictor_Evictor_inner.ump"
    protected ScanIterator scanIter ;
  // line 112 "../../../../Evictor_Evictor_inner.ump"
    protected IN target ;
  // line 113 "../../../../Evictor_Evictor_inner.ump"
    protected Logger logger ;
  // line 114 "../../../../Evictor_Evictor_inner.ump"
    protected String msg ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 126 "../../../../Evictor_Evictor_inner.ump"
  // line 4 "../../../../Derivative_Evictor_MemoryBudget_Evictor_inner.ump"
  public static class Evictor_isRunnable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_isRunnable()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 128 "../../../../Evictor_Evictor_inner.ump"
    public  Evictor_isRunnable(Evictor _this, String source){
      this._this=_this;
          this.source=source;
    }
  
    // line 132 "../../../../Evictor_Evictor_inner.ump"
    public boolean execute() throws DatabaseException{
      mb=_this.envImpl.getMemoryBudget();
          Label388:
  currentUsage=mb.getCacheMemoryUsage();
          maxMem=mb.getCacheBudget();
          doRun=((currentUsage - maxMem) > 0);
          if (doRun) {
            _this.currentRequiredEvictBytes=_this.evictBytesSetting;
            _this.currentRequiredEvictBytes+=(currentUsage - maxMem);
            if (_this.DEBUG) {
              if (source == _this.SOURCE_CRITICAL) {
                System.out.println("executed: critical runnable");
              }
            }
          }
          if (_this.runnableHook != null) {
            doRun=((Boolean)_this.runnableHook.getHookValue()).booleanValue();
            _this.currentRequiredEvictBytes=maxMem;
          }
          //original();
          //this.hook388();
  
          Label372:        //this.hook372();
          result=false;        
          // line 6 "../../../../Derivative_Evictor_MemoryBudget_Evictor_inner.ump"
          // boolean result=original();
                  result=doRun;
                  //return result;
          // END OF UMPLE AFTER INJECTION
          return result;
  
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 139 "../../../../Evictor_Evictor_inner.ump"
    protected Evictor _this ;
  // line 140 "../../../../Evictor_Evictor_inner.ump"
    protected String source ;
  // line 141 "../../../../Evictor_Evictor_inner.ump"
    protected MemoryBudget mb ;
  // line 142 "../../../../Evictor_Evictor_inner.ump"
    protected long currentUsage ;
  // line 143 "../../../../Evictor_Evictor_inner.ump"
    protected long maxMem ;
  // line 144 "../../../../Evictor_Evictor_inner.ump"
    protected boolean doRun ;
  // line 145 "../../../../Evictor_Evictor_inner.ump"
    protected Logger logger ;
  // line 146 "../../../../Evictor_Evictor_inner.ump"
    protected Runtime r ;
  // line 147 "../../../../Evictor_Evictor_inner.ump"
    protected long totalBytes ;
  // line 148 "../../../../Evictor_Evictor_inner.ump"
    protected long freeBytes ;
  // line 149 "../../../../Evictor_Evictor_inner.ump"
    protected long usedBytes ;
  // line 150 "../../../../Evictor_Evictor_inner.ump"
    protected StringBuffer sb ;
  // line 151 "../../../../Evictor_Evictor_inner.ump"
    protected boolean result ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
  public static class Evictor_doCriticalEviction
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_doCriticalEviction()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
    public  Evictor_doCriticalEviction(Evictor _this){
      this._this=_this;
    }
  
    // line 9 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
    public void execute() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 10 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
    protected Evictor _this ;
  // line 11 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
    protected MemoryBudget mb ;
  // line 12 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
    protected long currentUsage ;
  // line 13 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
    protected long maxMem ;
  // line 14 "../../../../Derivative_Evictor_CriticalEviction_Evictor_inner.ump"
    protected long over ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 30 "../../../../Evictor_Evictor.ump"
  public static final String SOURCE_DAEMON = "daemon" ;
// line 32 "../../../../Evictor_Evictor.ump"
  public static final String SOURCE_MANUAL = "manual" ;
// line 34 "../../../../Evictor_Evictor.ump"
  public static final String SOURCE_CRITICAL = "critical" ;
// line 36 "../../../../Evictor_Evictor.ump"
  private static final boolean DEBUG = false ;
// line 38 "../../../../Evictor_Evictor.ump"
  private EnvironmentImpl envImpl ;
// line 40 "../../../../Evictor_Evictor.ump"
  private LogManager logManager ;
// line 42 "../../../../Evictor_Evictor.ump"
  private volatile boolean active ;
// line 44 "../../../../Evictor_Evictor.ump"
  private IN nextNode ;
// line 46 "../../../../Evictor_Evictor.ump"
  private long currentRequiredEvictBytes ;
// line 48 "../../../../Evictor_Evictor.ump"
  private int nodesPerScan ;
// line 50 "../../../../Evictor_Evictor.ump"
  private long evictBytesSetting ;
// line 52 "../../../../Evictor_Evictor.ump"
  private boolean evictByLruOnly ;
// line 54 "../../../../Evictor_Evictor.ump"
  private NumberFormat formatter ;
// line 56 "../../../../Evictor_Evictor.ump"
  private int nNodesScannedThisRun ;
// line 60 "../../../../Evictor_Evictor.ump"
  private TestHook runnableHook ;

// line 84 "../../../../Evictor_Evictor.ump"
  synchronized public void clearEnv () 
  {
    envImpl = null;
  }

// line 5 "../../../../Derivative_Evictor_MemoryBudget_Evictor.ump"
  protected long hook389: evictIN (IN , IN , int , INList , ScanIterator , boolean ) 
  {
    evictBytes = renewedChild.getInMemorySize();
	//return original(evictBytes, renewedChild);
  }

// line 5 "../../../../Derivative_DeleteOp_Evictor_Evictor.ump"
  protected boolean hook386: selectIN (INList , ScanIterator ) 
  {
    b2 = db.isDeleted();
	//return original(db, b2);
  }

// line 10 "../../../../Derivative_DeleteOp_Evictor_Evictor.ump"
  protected boolean hook387: selectIN (INList , ScanIterator ) 
  {
    b |= db.isDeleteFinished();
	//return original(db, b);
  }
// line 7 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private int nEvictPasses = 0 ;
// line 9 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private long nNodesSelected = 0 ;
// line 11 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private long nNodesSelectedThisRun ;
// line 13 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private int nNodesScanned = 0 ;
// line 15 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private long nNodesEvicted = 0 ;
// line 17 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private long nNodesEvictedThisRun ;
// line 19 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private long nBINsStripped = 0 ;
// line 21 "../../../../Derivative_Statistics_Evictor_Evictor.ump"
  private long nBINsStrippedThisRun ;

  
}