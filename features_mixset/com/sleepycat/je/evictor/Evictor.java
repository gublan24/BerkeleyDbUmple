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

// line 3 "../../../../Evictor_Evictor.ump"
// line 3 "../../../../Evictor_Evictor_inner.ump"
public class Evictor
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
  {}

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
			//this.hook373(envImpl);
      Label737:
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
				//b = this.hook387(db, b);
        Label387:
				if (b) {
						String inInfo = " IN type=" + in.getLogType() + " id=" + in.getNodeId() + " not expected on INList";
						String errMsg = (db == null) ? inInfo
							: "Database " + db.getDebugName() + " id=" + db.getId() + inInfo;
						throw new DatabaseException(errMsg);
				}
				boolean b2 = false;
				//b2 = this.hook386(db, b2);
        Label386:
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
			//this.hook380(target);
      Label380:
			return target;
  }


  /**
   * 
   * Normalize the tree level of the given IN. Is public for unit testing. A BIN containing evictable LNs is given level 0, so it will be stripped first. For non-duplicate and DBMAP trees, the high order bits are cleared to make their levels correspond; that way, all bottom level nodes (BINs and DBINs) are given the same eviction priority. Note that BINs in a duplicate tree are assigned the same level as BINs in a non-duplicate tree. This isn't always optimimal, but is the best we can do considering that BINs in duplicate trees may contain a mix of LNs and DINs.
   */
  // line 227 "../../../../Evictor_Evictor.ump"
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
  // line 239 "../../../../Evictor_Evictor.ump"
   private long evict(INList inList, IN target, ScanIterator scanIter) throws DatabaseException{
    boolean envIsReadOnly = envImpl.isReadOnly();
			long evictedBytes = 0;
			if (target.latchNoWait(false)) {
					//evictedBytes = this.hook374(inList, target, scanIter, envIsReadOnly, evictedBytes);
         Label374:
				if (target instanceof BIN) {
							//this.hook385(target);
              Label385:
							evictedBytes = ((BIN) target).evictLNs();
							//this.hook383(evictedBytes);
              Label383:
					}
					if (evictedBytes == 0 && target.isEvictable()) {
							Tree tree = target.getDatabase().getTree();
							SearchResult result = tree.getParentINForChildIN(target, true, false);
							if (result.exactParentFound) {
						evictedBytes = evictIN(target, result.parent, result.index, inList, scanIter, envIsReadOnly);
							}
					}
		// end hook374
			}
			return evictedBytes;
  }


  /**
   * 
   * Evict an IN. Dirty nodes are logged before they're evicted. inlist is latched with the major latch by the caller.
   */
  // line 268 "../../../../Evictor_Evictor.ump"
   private long evictIN(IN child, IN parent, int index, INList inlist, ScanIterator scanIter, boolean envIsReadOnly) throws DatabaseException{
    long evictBytes = 0;
			//evictBytes = this.hook375(child, parent, index, inlist, scanIter, envIsReadOnly, evictBytes);
			Label375:
			//this.hook378(parent);
      Label378:
			long oldGenerationCount = child.getGeneration();
			IN renewedChild = (IN) parent.getTarget(index);
			if ((renewedChild != null) && (renewedChild.getGeneration() <= oldGenerationCount)
				&& renewedChild.latchNoWait(false)) {
					//evictBytes = this.hook379(parent, index, inlist, scanIter, envIsReadOnly, evictBytes, renewedChild);
          Label379:
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
						//evictBytes = this.hook389(evictBytes, renewedChild);
            Label389:
						if (newChildLsn) {
								parent.updateEntry(index, null, renewedChildLsn);
						} else {
								parent.updateEntry(index, (Node) null);
						}
						//this.hook384();
            Label384:
							}
					}
					// end of hook379
			}
			// end of hook375
			return evictBytes;
  }


  /**
   * 
   * Used by unit tests.
   */
  // line 317 "../../../../Evictor_Evictor.ump"
  public IN getNextNode(){
    return nextNode;
  }

  // line 321 "../../../../Evictor_Evictor.ump"
   public void setRunnableHook(TestHook hook){
    runnableHook = hook;
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
  
  
  
  @MethodObject
  // line 51 "../../../../Evictor_Evictor_inner.ump"
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
      _this.nNodesScannedThisRun=0;
          //this.hook381();
          Label381:
          assert _this.evictProfile.clear();
          nBatchSets=0;
          finished=false;
          evictBytes=0;
          evictBytes+=_this.envImpl.getUtilizationTracker().evictMemory();
          inList=_this.envImpl.getInMemoryINs();
          //this.hook376();
          Label376:
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
           // this.hook382();
           Label382:
           // this.hook377();
            Label377:
            //this.hook371();
            Label371:
          }
          return evictBytes;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 105 "../../../../Evictor_Evictor_inner.ump"
    protected Evictor _this ;
  // line 106 "../../../../Evictor_Evictor_inner.ump"
    protected String source ;
  // line 107 "../../../../Evictor_Evictor_inner.ump"
    protected long requiredEvictBytes ;
  // line 108 "../../../../Evictor_Evictor_inner.ump"
    protected int nBatchSets ;
  // line 109 "../../../../Evictor_Evictor_inner.ump"
    protected boolean finished ;
  // line 110 "../../../../Evictor_Evictor_inner.ump"
    protected long evictBytes ;
  // line 111 "../../../../Evictor_Evictor_inner.ump"
    protected INList inList ;
  // line 112 "../../../../Evictor_Evictor_inner.ump"
    protected int inListStartSize ;
  // line 113 "../../../../Evictor_Evictor_inner.ump"
    protected ScanIterator scanIter ;
  // line 114 "../../../../Evictor_Evictor_inner.ump"
    protected IN target ;
  // line 115 "../../../../Evictor_Evictor_inner.ump"
    protected Logger logger ;
  // line 116 "../../../../Evictor_Evictor_inner.ump"
    protected String msg ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 128 "../../../../Evictor_Evictor_inner.ump"
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
  
    // line 130 "../../../../Evictor_Evictor_inner.ump"
    public  Evictor_isRunnable(Evictor _this, String source){
      this._this=_this;
          this.source=source;
    }
  
    // line 134 "../../../../Evictor_Evictor_inner.ump"
    public boolean execute() throws DatabaseException{
      mb=_this.envImpl.getMemoryBudget();
          //this.hook388();
          Label388:
          //this.hook372();
          Label372:
          result=false;
          return result;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 142 "../../../../Evictor_Evictor_inner.ump"
    protected Evictor _this ;
  // line 143 "../../../../Evictor_Evictor_inner.ump"
    protected String source ;
  // line 144 "../../../../Evictor_Evictor_inner.ump"
    protected MemoryBudget mb ;
  // line 145 "../../../../Evictor_Evictor_inner.ump"
    protected long currentUsage ;
  // line 146 "../../../../Evictor_Evictor_inner.ump"
    protected long maxMem ;
  // line 147 "../../../../Evictor_Evictor_inner.ump"
    protected boolean doRun ;
  // line 148 "../../../../Evictor_Evictor_inner.ump"
    protected Logger logger ;
  // line 149 "../../../../Evictor_Evictor_inner.ump"
    protected Runtime r ;
  // line 150 "../../../../Evictor_Evictor_inner.ump"
    protected long totalBytes ;
  // line 151 "../../../../Evictor_Evictor_inner.ump"
    protected long freeBytes ;
  // line 152 "../../../../Evictor_Evictor_inner.ump"
    protected long usedBytes ;
  // line 153 "../../../../Evictor_Evictor_inner.ump"
    protected StringBuffer sb ;
  // line 154 "../../../../Evictor_Evictor_inner.ump"
    protected boolean result ;
  
    
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

// line 107 "../../../../Evictor_Evictor.ump"
  private synchronized void doEvict (String source, boolean evictDuringShutdown) throws DatabaseException 
  {
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

  
}