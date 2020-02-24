/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.PropUtil;
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
import com.sleepycat.je.cleaner.UtilizationProfile;
import com.sleepycat.je.cleaner.TrackedFileSummary;
import com.sleepycat.je.cleaner.Cleaner;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.CheckpointConfig;
import java.util.logging.Level;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.HashSet;

// line 3 "../../../../Checkpointer.ump"
// line 3 "../../../../Checkpointer_static.ump"
// line 3 "../../../../MemoryBudget_Checkpointer.ump"
// line 3 "../../../../MemoryBudget_Checkpointer_inner.ump"
// line 3 "../../../../DeleteOp_Checkpointer.ump"
// line 3 "../../../../Evictor_Checkpointer.ump"
// line 3 "../../../../Evictor_Checkpointer_inner.ump"
// line 3 "../../../../INCompressor_Checkpointer.ump"
// line 3 "../../../../Latches_Checkpointer.ump"
// line 3 "../../../../Latches_Checkpointer_inner.ump"
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

  // line 52 "../../../../Checkpointer.ump"
   public  Checkpointer(EnvironmentImpl envImpl, long waitTime, String name) throws DatabaseException{
    Label538:   ; //this.hook538(envImpl, waitTime, name);
            this.envImpl = envImpl;
        Label539:   ; //this.hook539(envImpl);
            logFileMax = envImpl.getConfigManager().getLong(EnvironmentParams.LOG_FILE_MAX);
        Label531:   ; //this.hook531();
            Label545:   ; //this.hook545(waitTime);
            lastCheckpointMillis = 0;
        highestFlushLevel = IN.MIN_LEVEL;
        logManager = envImpl.getLogManager();
  }

  // line 64 "../../../../Checkpointer.ump"
   public int getHighestFlushLevel(){
    return highestFlushLevel;
  }


  /**
   * 
   * Figure out the wakeup period. Supplied through this static method because we need to pass wakeup period to the superclass and need to do the calcuation outside this constructor.
   */
  // line 72 "../../../../Checkpointer.ump"
   public static  long getWakeupPeriod(DbConfigManager configManager) throws IllegalArgumentException,DatabaseException{
    return new Checkpointer_getWakeupPeriod(configManager).execute();
  }


  /**
   * 
   * @return the first active LSN point of the last completed checkpoint. Ifno checkpoint has run, return null.
   */
  // line 86 "../../../../Checkpointer.ump"
   public long getFirstActiveLsn(){
    return lastFirstActiveLsn;
  }


  /**
   * 
   * Initialize the FirstActiveLsn during recovery. The cleaner needs this.
   */
  // line 93 "../../../../Checkpointer.ump"
   public void setFirstActiveLsn(long lastFirstActiveLsn){
    this.lastFirstActiveLsn = lastFirstActiveLsn;
  }


  /**
   * 
   * Determine whether a checkpoint should be run. 1. If the force parameter is specified, always checkpoint. 2. If the config object specifies time or log size, use that. 3. If the environment is configured to use log size based checkpointing, check the log. 4. Lastly, use time based checking.
   */
  // line 100 "../../../../Checkpointer.ump"
   private boolean isRunnable(CheckpointConfig config) throws DatabaseException{
    return new Checkpointer_isRunnable(this, config).execute();
  }


  /**
   * 
   * The real work to do a checkpoint. This may be called by the checkpoint thread when waking up, or it may be invoked programatically through the api.
   * @param allowDeltasif true, this checkpoint may opt to log BIN deltas instead ofthe full node.
   * @param flushAllif true, this checkpoint must flush all the way to the top ofthe dbtree, instead of stopping at the highest level last modified.
   * @param invokingSourcea debug aid, to indicate who invoked this checkpoint. (i.e.recovery, the checkpointer daemon, the cleaner, programatically)
   */
  // line 111 "../../../../Checkpointer.ump"
   public synchronized  void doCheckpoint(CheckpointConfig config, boolean flushAll, String invokingSource) throws DatabaseException{
    new Checkpointer_doCheckpoint(this, config, flushAll, invokingSource).execute();
  }


  /**
   * 
   * Flush a FileSummaryLN node for each TrackedFileSummary that is currently active. Tell the UtilizationProfile about the updated file summary.
   */
  // line 118 "../../../../Checkpointer.ump"
   private void flushUtilizationInfo() throws DatabaseException{
    if (!DbInternal.getCheckpointUP(envImpl.getConfigManager().getEnvironmentConfig())) {
            return;
        }
        UtilizationProfile profile = envImpl.getUtilizationProfile();
        TrackedFileSummary[] activeFiles = envImpl.getUtilizationTracker().getTrackedFiles();
        for (int i = 0; i < activeFiles.length; i += 1) {
            profile.flushFileSummary(activeFiles[i]);
        }
  }


  /**
   * 
   * Flush the nodes in order, from the lowest level to highest level. As a flush dirties its parent, add it to the dirty map, thereby cascading the writes up the tree. If flushAll wasn't specified, we need only cascade up to the highest level set at the start of checkpointing. Note that all but the top level INs and the BINDeltas are logged provisionally. That's because we don't need to process lower INs because the higher INs will end up pointing at them.
   */
  // line 133 "../../../../Checkpointer.ump"
   private void flushDirtyNodes(SortedMap dirtyMap, boolean flushAll, boolean allowDeltas, boolean flushExtraLevel, long checkpointStart) throws DatabaseException{
    while (dirtyMap.size() > 0) {
            Integer currentLevel = (Integer) dirtyMap.firstKey();
            boolean logProvisionally = (currentLevel.intValue() != highestFlushLevel);
            Set nodeSet = (Set) dirtyMap.get(currentLevel);
            Iterator iter = nodeSet.iterator();
            while (iter.hasNext()) {
                CheckpointReference targetRef = (CheckpointReference) iter.next();
                Label520:   ; //this.hook520();
                //this.hook546(dirtyMap, allowDeltas, checkpointStart, currentLevel, logProvisionally, targetRef);
                Label546:
if (!(targetRef.db.isDeleted())) {
					flushIN(targetRef, dirtyMap, currentLevel.intValue(), logProvisionally, allowDeltas, checkpointStart);
			}
			//original(dirtyMap, allowDeltas, checkpointStart, currentLevel, logProvisionally, targetRef);
   ;
                    iter.remove();
            }
            dirtyMap.remove(currentLevel);
            if (currentLevel.intValue() == highestFlushLevel) {
                break;
            }
        }
  }


  /**
   * 
   * Scan the INList for all dirty INs. Arrange them in level sorted map for level ordered flushing.
   */
  // line 156 "../../../../Checkpointer.ump"
   private SortedMap selectDirtyINs(boolean flushAll, boolean flushExtraLevel) throws DatabaseException{
    return new Checkpointer_selectDirtyINs(this, flushAll, flushExtraLevel).execute();
  }


  /**
   * 
   * Flush the target IN.
   */
  // line 164 "../../../../Checkpointer.ump"
   private void flushIN(CheckpointReference targetRef, Map dirtyMap, int currentLevel, boolean logProvisionally, boolean allowDeltas, long checkpointStart) throws DatabaseException{
    Tree tree = targetRef.db.getTree();
        boolean targetWasRoot = false;
        if (targetRef.isDbRoot) {
            RootFlusher flusher = new RootFlusher(targetRef.db, logManager, targetRef.nodeId);
            tree.withRootLatchedExclusive(flusher);
            boolean flushed = flusher.getFlushed();
            targetWasRoot = flusher.stillRoot();
            if (flushed) {
                DbTree dbTree = targetRef.db.getDbEnvironment().getDbMapTree();
                dbTree.modifyDbRoot(targetRef.db);
                Label532:   ; //this.hook532();
            }
        }
        if (!targetWasRoot) {
            SearchResult result = tree.getParentINForChildIN(targetRef.nodeId, targetRef.containsDuplicates, false,
                targetRef.mainTreeKey, targetRef.dupTreeKey, false, false, -1, null, false);
            if (result.parent != null) {
                boolean mustLogParent = false;
                //this.hook526(targetRef, dirtyMap, currentLevel, logProvisionally, allowDeltas, checkpointStart, tree, result, mustLogParent);
                try {
                    if (result.exactParentFound) {
                        IN renewedTarget = (IN) result.parent.getTarget(result.index);
                        if (renewedTarget == null) {
                            mustLogParent = true;
                        } else {
                            mustLogParent = logTargetAndUpdateParent(renewedTarget, result.parent, result.index, allowDeltas,
                                checkpointStart, logProvisionally);
                        }
                    } else {
                        if (result.childNotResident) {
                            if (result.parent.getLevel() > currentLevel) {
                                mustLogParent = true;
                            }
                        }
                    }
                    if (mustLogParent) {
                        assert checkParentChildRelationship(result, currentLevel): dumpParentChildInfo(result, result.parent,
                            targetRef.nodeId, currentLevel, tree);
                        addToDirtyMap(dirtyMap, result.parent);
                    }
                } finally {
                    Label526:
result.parent.releaseLatch()
   ; //;
                }
            }
        }
  }


  /**
   * 
   * @return true if this parent is appropriately 1 level above the child.
   */
  // line 215 "../../../../Checkpointer.ump"
   private boolean checkParentChildRelationship(SearchResult result, int childLevel){
    if (result.childNotResident && !result.exactParentFound) {
            return true;
        }
        int parentLevel = result.parent.getLevel();
        boolean isMapTree = (childLevel & IN.DBMAP_LEVEL) != 0;
        boolean isMainTree = (childLevel & IN.MAIN_LEVEL) != 0;
        boolean checkOk = false;
        if (isMapTree || isMainTree) {
            if (parentLevel == (childLevel + 1)) {
                checkOk = true;
            }
        } else {
            if (childLevel == 1) {
                if (parentLevel == 2) {
                    checkOk = true;
                }
            } else {
                if ((parentLevel == IN.BIN_LEVEL) || (parentLevel == childLevel + 1)) {
                    checkOk = true;
                }
            }
        }
        return checkOk;
  }

  // line 242 "../../../../Checkpointer.ump"
   private String dumpParentChildInfo(SearchResult result, IN parent, long childNodeId, int currentLevel, Tree tree) throws DatabaseException{
    StringBuffer sb = new StringBuffer();
        sb.append("ckptId=").append(checkpointId);
        sb.append(" result=").append(result);
        sb.append(" parent node=").append(parent.getNodeId());
        sb.append(" level=").append(parent.getLevel());
        sb.append(" child node=").append(childNodeId);
        sb.append(" level=").append(currentLevel);
        return sb.toString();
  }

  // line 254 "../../../../Checkpointer.ump"
   private boolean logTargetAndUpdateParent(IN target, IN parent, int index, boolean allowDeltas, long checkpointStart, boolean logProvisionally) throws DatabaseException{
    target.latch(false);
        long newLsn = DbLsn.NULL_LSN;
        boolean mustLogParent = true;
        Label527:
envImpl.lazyCompress(target);
			//original(target, parent, allowDeltas, checkpointStart, logProvisionally, newLsn, mustLogParent);
   ; //this.hook527(target, parent, allowDeltas, checkpointStart, logProvisionally, newLsn, mustLogParent);
            if (target.getDirty()) {
                newLsn = target.log(logManager, allowDeltas, logProvisionally, true, parent);
                if (allowDeltas && newLsn == DbLsn.NULL_LSN) {
                    Label537:   ; //this.hook537();
                        long lastFullLsn = target.getLastFullVersion();
                    if (DbLsn.compareTo(lastFullLsn, checkpointStart) < 0) {
                        mustLogParent = false;
                    }
                }
            }
        //End hook527
        Label527_1:
target.releaseLatch();
   ; //;
        if (newLsn != DbLsn.NULL_LSN) {
            Label533:   ; //this.hook533(target);
                parent.updateEntry(index, newLsn);
        }
        return mustLogParent;
  }


  /**
   * 
   * Add a node to the dirty map. The dirty map is keyed by level (Integers) and holds sets of IN references.
   */
  // line 281 "../../../../Checkpointer.ump"
   private void addToDirtyMap(Map dirtyMap, IN in){
    Integer inLevel = new Integer( in .getLevel());
        Set inSet = (Set) dirtyMap.get(inLevel);
        if (inSet == null) {
            inSet = new HashSet();
            dirtyMap.put(inLevel, inSet);
        }
        inSet.add(new CheckpointReference( in .getDatabase(), in .getNodeId(), in .containsDuplicates(), in .isDbRoot(), in .getMainTreeKey(), in .getDupTreeKey()));
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Checkpointer_static.ump"
  public static class CheckpointReference
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //CheckpointReference Attributes
    private DatabaseImpl db;
    private long nodeId;
    private boolean containsDuplicates;
    private boolean isDbRoot;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CheckpointReference(DatabaseImpl aDb, long aNodeId, boolean aContainsDuplicates, boolean aIsDbRoot)
    {
      db = aDb;
      nodeId = aNodeId;
      containsDuplicates = aContainsDuplicates;
      isDbRoot = aIsDbRoot;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setDb(DatabaseImpl aDb)
    {
      boolean wasSet = false;
      db = aDb;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setNodeId(long aNodeId)
    {
      boolean wasSet = false;
      nodeId = aNodeId;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setContainsDuplicates(boolean aContainsDuplicates)
    {
      boolean wasSet = false;
      containsDuplicates = aContainsDuplicates;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setIsDbRoot(boolean aIsDbRoot)
    {
      boolean wasSet = false;
      isDbRoot = aIsDbRoot;
      wasSet = true;
      return wasSet;
    }
  
    public DatabaseImpl getDb()
    {
      return db;
    }
  
    public long getNodeId()
    {
      return nodeId;
    }
  
    public boolean getContainsDuplicates()
    {
      return containsDuplicates;
    }
  
    public boolean getIsDbRoot()
    {
      return isDbRoot;
    }
  
    public void delete()
    {}
  
    // line 12 "../../../../Checkpointer_static.ump"
     public  CheckpointReference(DatabaseImpl db, long nodeId, boolean containsDuplicates, boolean isDbRoot, byte [] mainTreeKey, byte [] dupTreeKey){
      this.db = db;
     this.nodeId = nodeId;
     this.containsDuplicates = containsDuplicates;
     this.isDbRoot = isDbRoot;
     this.mainTreeKey = mainTreeKey;
     this.dupTreeKey = dupTreeKey;
    }
  
    // line 20 "../../../../Checkpointer_static.ump"
     public boolean equals(Object o){
      if (!(o instanceof CheckpointReference)) {
      return false;
     }
     CheckpointReference other = (CheckpointReference) o;
     return nodeId == other.nodeId;
    }
  
    // line 27 "../../../../Checkpointer_static.ump"
     public int hashCode(){
      return (int) nodeId;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+
              "nodeId" + ":" + getNodeId()+ "," +
              "containsDuplicates" + ":" + getContainsDuplicates()+ "," +
              "isDbRoot" + ":" + getIsDbRoot()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "db" + "=" + (getDb() != null ? !getDb().equals(this)  ? getDb().toString().replaceAll("  ","    ") : "this" : "null");
    }  
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 9 "../../../../Checkpointer_static.ump"
    protected byte[] mainTreeKey ;
  // line 10 "../../../../Checkpointer_static.ump"
    protected byte[] dupTreeKey ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 30 "../../../../Checkpointer_static.ump"
  public static class Checkpointer_getWakeupPeriod
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_getWakeupPeriod()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 32 "../../../../Checkpointer_static.ump"
    public  Checkpointer_getWakeupPeriod(DbConfigManager configManager){
      this.configManager = configManager;
    }
  
    // line 35 "../../../../Checkpointer_static.ump"
    public long execute() throws IllegalArgumentException,DatabaseException{
      Label541:   ; //this.hook541();
      Label519:   ; //this.hook519();
      result = 0;
      Label540:   ; //this.hook540();
      return result;
    }
  
    // line 45 "../../../../Checkpointer_static.ump"
     protected void hook519() throws IllegalArgumentException,DatabaseException{
      
    }
  
  
    /**
     * protected void hook540() throws IllegalArgumentException, DatabaseException {
     * }
     */
    // line 48 "../../../../Checkpointer_static.ump"
     protected void hook541() throws IllegalArgumentException,DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 41 "../../../../Checkpointer_static.ump"
    protected DbConfigManager configManager ;
  // line 42 "../../../../Checkpointer_static.ump"
    protected long wakeupPeriod ;
  // line 43 "../../../../Checkpointer_static.ump"
    protected long bytePeriod ;
  // line 44 "../../../../Checkpointer_static.ump"
    protected int result ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 50 "../../../../Checkpointer_static.ump"
  public static class Checkpointer_isRunnable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_isRunnable()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 52 "../../../../Checkpointer_static.ump"
    public  Checkpointer_isRunnable(Checkpointer _this, CheckpointConfig config){
      this._this = _this;
     this.config = config;
    }
  
    // line 57 "../../../../Checkpointer_static.ump"
    public boolean execute() throws DatabaseException{
      useBytesInterval = 0;
     useTimeInterval = 0;
     nextLsn = DbLsn.NULL_LSN;
     try {
      if (config.getForce()) {
       return true;
      } else {
       Label543:   ; //this.hook543();
       Label544:   ; //added from this.hook543();
      }
      Label542:   ; //this.hook542();
      return false;
     } 
     finally {
      Label521:   ; //this.hook521();
     }
     //throw ReturnHack.returnBoolean;
  
     //catch (ReturnBoolean r) {    return r.value;}
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 77 "../../../../Checkpointer_static.ump"
    protected Checkpointer _this ;
  // line 78 "../../../../Checkpointer_static.ump"
    protected CheckpointConfig config ;
  // line 79 "../../../../Checkpointer_static.ump"
    protected long useBytesInterval ;
  // line 80 "../../../../Checkpointer_static.ump"
    protected long useTimeInterval ;
  // line 81 "../../../../Checkpointer_static.ump"
    protected long nextLsn ;
  // line 82 "../../../../Checkpointer_static.ump"
    protected long lastUsedLsn ;
  // line 83 "../../../../Checkpointer_static.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 94 "../../../../Checkpointer_static.ump"
  // line 21 "../../../../MemoryBudget_Checkpointer_inner.ump"
  // line 4 "../../../../Evictor_Checkpointer_inner.ump"
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
  
    // line 96 "../../../../Checkpointer_static.ump"
    public  Checkpointer_doCheckpoint(Checkpointer _this, CheckpointConfig config, boolean flushAll, String invokingSource){
      this._this = _this;
     this.config = config;
     this.flushAll = flushAll;
     this.invokingSource = invokingSource;
    }
  
    // line 102 "../../../../Checkpointer_static.ump"
    public void execute() throws DatabaseException{
      if (_this.envImpl.isReadOnly()) {
      return;
     }
     if (!_this.isRunnable(config)) {
      return;
     }
     flushExtraLevel = false;
     cleaner = _this.envImpl.getCleaner();
     cleanerFiles = cleaner.getFilesAtCheckpointStart();
     if (cleanerFiles != null) {
      flushExtraLevel = true;
     }
     _this.lastCheckpointMillis = System.currentTimeMillis();
     Label535:   ; //this.hook535();
      _this.checkpointId++;
     Label534:   ; //this.hook534();
      success = false;
     Label522:   ; //this.hook522();
      //this.hook548();
      Label548:
  dirtyMapMemSize=0;
          //original();
     ;
      mb = _this.envImpl.getMemoryBudget();
     try {
      //this.hook525();
      checkpointStart = DbLsn.NULL_LSN;
      firstActiveLsn = DbLsn.NULL_LSN;
      dirtyMap = null;
      //this.hook547();
      Label547:
  synchronized (_this.envImpl.getEvictor()) {
            
     ; startEntry = new CheckpointStart(_this.checkpointId, invokingSource);
      checkpointStart = _this.logManager.log(startEntry);
      firstActiveLsn = _this.envImpl.getTxnManager().getFirstActiveLsn();
      if (firstActiveLsn == DbLsn.NULL_LSN) {
       firstActiveLsn = checkpointStart;
      } else {
       if (DbLsn.compareTo(checkpointStart, firstActiveLsn) < 0) {
        firstActiveLsn = checkpointStart;
       }
      }
      dirtyMap = _this.selectDirtyINs(flushAll, flushExtraLevel);
      //end of hook547
      //this.hook551();
      
  
          }
  Label551:
  totalSize=0;
          //original();
     ; for (Iterator i = dirtyMap.values().iterator(); i.hasNext();) {
       nodeSet = (Set) i.next();
       //this.hook552();
       Label552:
  size=nodeSet.size() * MemoryBudget.CHECKPOINT_REFERENCE_SIZE;
          totalSize+=size;
          dirtyMapMemSize+=size;
          //original();
     ;
      }
      //this.hook550();
      Label550:
  mb.updateMiscMemoryUsage(totalSize);
          //original();
     ; allowDeltas = !config.getMinimizeRecoveryTime();
      _this.flushDirtyNodes(dirtyMap, flushAll, allowDeltas, flushExtraLevel, checkpointStart);
      _this.flushUtilizationInfo();
      endEntry = new CheckpointEnd(invokingSource, checkpointStart, _this.envImpl.getRootLsn(), firstActiveLsn, Node.getLastId(), _this.envImpl.getDbMapTree().getLastDbId(), _this.envImpl.getTxnManager().getLastTxnId(), _this.checkpointId);
      Label523:   ; //this.hook523();
       _this.lastCheckpointEnd = _this.logManager.logForceFlush(endEntry, true);
      _this.lastFirstActiveLsn = firstActiveLsn;
      Label536:   ; //this.hook536();
       _this.highestFlushLevel = IN.MIN_LEVEL;
      success = true;
      if (cleanerFiles != null) {
       cleaner.updateFilesAtCheckpointEnd(cleanerFiles);
      }
  
     }
     catch (      DatabaseException e) {
       Label525:   ;
            throw e;
          }
     finally {
      //this.hook549();
      Label549:
  mb.updateMiscMemoryUsage(0 - dirtyMapMemSize);
          //original();
     ; Label524:   ; //this.hook524();
     }
    }
  
  
    /**
     * protected void hook522() throws DatabaseException {}
     * protected void hook523() throws DatabaseException {}
     * protected void hook524() throws DatabaseException {}
     * protected void hook525() throws DatabaseException {
     * }
     */
    // line 198 "../../../../Checkpointer_static.ump"
     protected void hook534() throws DatabaseException{
      
    }
  
    // line 199 "../../../../Checkpointer_static.ump"
     protected void hook535() throws DatabaseException{
      
    }
  
    // line 200 "../../../../Checkpointer_static.ump"
     protected void hook536() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 172 "../../../../Checkpointer_static.ump"
    protected Checkpointer _this ;
  // line 173 "../../../../Checkpointer_static.ump"
    protected CheckpointConfig config ;
  // line 174 "../../../../Checkpointer_static.ump"
    protected boolean flushAll ;
  // line 175 "../../../../Checkpointer_static.ump"
    protected String invokingSource ;
  // line 176 "../../../../Checkpointer_static.ump"
    protected boolean flushExtraLevel ;
  // line 177 "../../../../Checkpointer_static.ump"
    protected Cleaner cleaner ;
  // line 178 "../../../../Checkpointer_static.ump"
    protected Set[] cleanerFiles ;
  // line 179 "../../../../Checkpointer_static.ump"
    protected boolean success ;
  // line 180 "../../../../Checkpointer_static.ump"
    protected boolean traced ;
  // line 181 "../../../../Checkpointer_static.ump"
    protected int dirtyMapMemSize ;
  // line 182 "../../../../Checkpointer_static.ump"
    protected MemoryBudget mb ;
  // line 183 "../../../../Checkpointer_static.ump"
    protected long checkpointStart ;
  // line 184 "../../../../Checkpointer_static.ump"
    protected long firstActiveLsn ;
  // line 185 "../../../../Checkpointer_static.ump"
    protected SortedMap dirtyMap ;
  // line 186 "../../../../Checkpointer_static.ump"
    protected CheckpointStart startEntry ;
  // line 187 "../../../../Checkpointer_static.ump"
    protected int totalSize ;
  // line 188 "../../../../Checkpointer_static.ump"
    protected Set nodeSet ;
  // line 189 "../../../../Checkpointer_static.ump"
    protected int size ;
  // line 190 "../../../../Checkpointer_static.ump"
    protected boolean allowDeltas ;
  // line 191 "../../../../Checkpointer_static.ump"
    protected CheckpointEnd endEntry ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 215 "../../../../Checkpointer_static.ump"
  // line 4 "../../../../MemoryBudget_Checkpointer_inner.ump"
  // line 4 "../../../../Latches_Checkpointer_inner.ump"
  public static class Checkpointer_selectDirtyINs
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_selectDirtyINs()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 217 "../../../../Checkpointer_static.ump"
    public  Checkpointer_selectDirtyINs(Checkpointer _this, boolean flushAll, boolean flushExtraLevel){
      this._this = _this;
     this.flushAll = flushAll;
     this.flushExtraLevel = flushExtraLevel;
    }
  
    // line 222 "../../../../Checkpointer_static.ump"
    public SortedMap execute() throws DatabaseException{
      newDirtyMap = new TreeMap();
     inMemINs = _this.envImpl.getInMemoryINs();
     Label529:
  inMemINs.latchMajor();
          //original();
     ; //this.hook529();
      //this.hook553();
      Label553:
  totalSize=0;
          mb=_this.envImpl.getMemoryBudget();
          //original();
     ;
      //this.hook528();
      Label528:   ;
      try {
       iter = inMemINs.iterator();
       while (iter.hasNext()) {
        in = (IN) iter.next(); in .latch(false);
        //this.hook530();
        Label530:
  totalSize=mb.accumulateNewUsage(in,totalSize);
          //original();
     ;
         if ( in .getDirty()) {
          level = new Integer( in .getLevel());
          if (newDirtyMap.containsKey(level)) {
           dirtySet = (Set) newDirtyMap.get(level);
          } else {
           dirtySet = new HashSet();
           newDirtyMap.put(level, dirtySet);
          }
          dirtySet.add(new CheckpointReference( in .getDatabase(), in .getNodeId(), in .containsDuplicates(), in .isDbRoot(), in .getMainTreeKey(), in .getDupTreeKey()));
         }
       }
       Label530_1:   ;
        //this.hook554(); //excute()
        Label554:
  mb.refreshTreeMemoryUsage(totalSize);
          //   original();
     ;
        if (newDirtyMap.size() > 0) {
         if (flushAll) {
          _this.highestFlushLevel = _this.envImpl.getDbMapTree().getHighestLevel();
         } else {
          _this.highestFlushLevel = ((Integer) newDirtyMap.lastKey()).intValue();
          if (flushExtraLevel) {
           _this.highestFlushLevel += 1;
          }
         }
        }
       else {
        _this.highestFlushLevel = IN.MAX_LEVEL;
       }
      }
     finally {
      Label528_1:
  inMemINs.releaseMajorLatchIfHeld();
     ; //;
  
     }
     //end hook528
     return newDirtyMap;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 270 "../../../../Checkpointer_static.ump"
    protected Checkpointer _this ;
  // line 271 "../../../../Checkpointer_static.ump"
    protected boolean flushAll ;
  // line 272 "../../../../Checkpointer_static.ump"
    protected boolean flushExtraLevel ;
  // line 273 "../../../../Checkpointer_static.ump"
    protected SortedMap newDirtyMap ;
  // line 274 "../../../../Checkpointer_static.ump"
    protected INList inMemINs ;
  // line 275 "../../../../Checkpointer_static.ump"
    protected long totalSize ;
  // line 276 "../../../../Checkpointer_static.ump"
    protected MemoryBudget mb ;
  // line 277 "../../../../Checkpointer_static.ump"
    protected Iterator iter ;
  // line 278 "../../../../Checkpointer_static.ump"
    protected IN in ;
  // line 279 "../../../../Checkpointer_static.ump"
    protected Integer level ;
  // line 280 "../../../../Checkpointer_static.ump"
    protected Set dirtySet ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 35 "../../../../Checkpointer.ump"
  private EnvironmentImpl envImpl ;
// line 37 "../../../../Checkpointer.ump"
  private LogManager logManager ;
// line 39 "../../../../Checkpointer.ump"
  private long checkpointId ;
// line 41 "../../../../Checkpointer.ump"
  private long logFileMax ;
// line 43 "../../../../Checkpointer.ump"
  private long lastCheckpointMillis ;
// line 45 "../../../../Checkpointer.ump"
  private long lastFirstActiveLsn ;
// line 47 "../../../../Checkpointer.ump"
  private long lastCheckpointEnd ;
// line 49 "../../../../Checkpointer.ump"
  private volatile int highestFlushLevel ;

// line 78 "../../../../Checkpointer.ump"
  synchronized public void setCheckpointId (long lastCheckpointId) 
  {
    checkpointId = lastCheckpointId;
  }

  
}