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

  // line 53 "../../../../Checkpointer.ump"
   public  Checkpointer(EnvironmentImpl envImpl, long waitTime, String name) throws DatabaseException{
    this.hook538(envImpl, waitTime, name);
	this.envImpl = envImpl;
	this.hook539(envImpl);
	logFileMax = envImpl.getConfigManager().getLong(EnvironmentParams.LOG_FILE_MAX);
	this.hook531();
	this.hook545(waitTime);
	lastCheckpointMillis = 0;
	highestFlushLevel = IN.MIN_LEVEL;
	logManager = envImpl.getLogManager();
  }

  // line 65 "../../../../Checkpointer.ump"
   public int getHighestFlushLevel(){
    return highestFlushLevel;
  }


  /**
   * 
   * Figure out the wakeup period. Supplied through this static method because we need to pass wakeup period to the superclass and need to do the calcuation outside this constructor.
   */
  // line 73 "../../../../Checkpointer.ump"
   public static  long getWakeupPeriod(DbConfigManager configManager) throws IllegalArgumentException,DatabaseException{
    return new Checkpointer_getWakeupPeriod(configManager).execute();
  }


  /**
   * 
   * @return the first active LSN point of the last completed checkpoint. Ifno checkpoint has run, return null.
   */
  // line 87 "../../../../Checkpointer.ump"
   public long getFirstActiveLsn(){
    return lastFirstActiveLsn;
  }


  /**
   * 
   * Initialize the FirstActiveLsn during recovery. The cleaner needs this.
   */
  // line 94 "../../../../Checkpointer.ump"
   public void setFirstActiveLsn(long lastFirstActiveLsn){
    this.lastFirstActiveLsn = lastFirstActiveLsn;
  }


  /**
   * 
   * Determine whether a checkpoint should be run. 1. If the force parameter is specified, always checkpoint. 2. If the config object specifies time or log size, use that. 3. If the environment is configured to use log size based checkpointing, check the log. 4. Lastly, use time based checking.
   */
  // line 101 "../../../../Checkpointer.ump"
   private boolean isRunnable(CheckpointConfig config) throws DatabaseException{
    return new Checkpointer_isRunnable(this, config).execute();
  }


  /**
   * 
   * Flush a FileSummaryLN node for each TrackedFileSummary that is currently active. Tell the UtilizationProfile about the updated file summary.
   */
  // line 119 "../../../../Checkpointer.ump"
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
  // line 134 "../../../../Checkpointer.ump"
   private void flushDirtyNodes(SortedMap dirtyMap, boolean flushAll, boolean allowDeltas, boolean flushExtraLevel, long checkpointStart) throws DatabaseException{
    while (dirtyMap.size() > 0) {
	    Integer currentLevel = (Integer) dirtyMap.firstKey();
	    boolean logProvisionally = (currentLevel.intValue() != highestFlushLevel);
	    Set nodeSet = (Set) dirtyMap.get(currentLevel);
	    Iterator iter = nodeSet.iterator();
	    while (iter.hasNext()) {
		CheckpointReference targetRef = (CheckpointReference) iter.next();
		this.hook520();
		this.hook546(dirtyMap, allowDeltas, checkpointStart, currentLevel, logProvisionally, targetRef);
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
		this.hook532();
	    }
	}
	if (!targetWasRoot) {
	    SearchResult result = tree.getParentINForChildIN(targetRef.nodeId, targetRef.containsDuplicates, false,
		    targetRef.mainTreeKey, targetRef.dupTreeKey, false, false, -1, null, false);
	    if (result.parent != null) {
		boolean mustLogParent = false;
		this.hook526(targetRef, dirtyMap, currentLevel, logProvisionally, allowDeltas, checkpointStart, tree,
			result, mustLogParent);
	    }
	}
  }


  /**
   * 
   * @return true if this parent is appropriately 1 level above the child.
   */
  // line 192 "../../../../Checkpointer.ump"
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

  // line 219 "../../../../Checkpointer.ump"
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

  // line 231 "../../../../Checkpointer.ump"
   private boolean logTargetAndUpdateParent(IN target, IN parent, int index, boolean allowDeltas, long checkpointStart, boolean logProvisionally) throws DatabaseException{
    target.latch(false);
	long newLsn = DbLsn.NULL_LSN;
	boolean mustLogParent = true;
	this.hook527(target, parent, allowDeltas, checkpointStart, logProvisionally, newLsn, mustLogParent);
	if (newLsn != DbLsn.NULL_LSN) {
	    this.hook533(target);
	    parent.updateEntry(index, newLsn);
	}
	return mustLogParent;
  }


  /**
   * 
   * Add a node to the dirty map. The dirty map is keyed by level (Integers) and holds sets of IN references.
   */
  // line 246 "../../../../Checkpointer.ump"
   private void addToDirtyMap(Map dirtyMap, IN in){
    Integer inLevel = new Integer(in.getLevel());
	Set inSet = (Set) dirtyMap.get(inLevel);
	if (inSet == null) {
	    inSet = new HashSet();
	    dirtyMap.put(inLevel, inSet);
	}
	inSet.add(new CheckpointReference(in.getDatabase(), in.getNodeId(), in.containsDuplicates(), in.isDbRoot(),
		in.getMainTreeKey(), in.getDupTreeKey()));
  }

  // line 257 "../../../../Checkpointer.ump"
   protected void hook520() throws DatabaseException{
    
  }

  // line 262 "../../../../Checkpointer.ump"
   protected void hook526(CheckpointReference targetRef, Map dirtyMap, int currentLevel, boolean logProvisionally, boolean allowDeltas, long checkpointStart, Tree tree, SearchResult result, boolean mustLogParent) throws DatabaseException{
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
	    assert checkParentChildRelationship(result, currentLevel) : dumpParentChildInfo(result, result.parent,
		    targetRef.nodeId, currentLevel, tree);
	    addToDirtyMap(dirtyMap, result.parent);
	}
  }

  // line 286 "../../../../Checkpointer.ump"
   protected void hook527(IN target, IN parent, boolean allowDeltas, long checkpointStart, boolean logProvisionally, long newLsn, boolean mustLogParent) throws DatabaseException{
    if (target.getDirty()) {
	    newLsn = target.log(logManager, allowDeltas, logProvisionally, true, parent);
	    if (allowDeltas && newLsn == DbLsn.NULL_LSN) {
		this.hook537();
		long lastFullLsn = target.getLastFullVersion();
		if (DbLsn.compareTo(lastFullLsn, checkpointStart) < 0) {
		    mustLogParent = false;
		}
	    }
	}
  }

  // line 299 "../../../../Checkpointer.ump"
   protected void hook531() throws DatabaseException{
    
  }

  // line 302 "../../../../Checkpointer.ump"
   protected void hook532() throws DatabaseException{
    
  }

  // line 305 "../../../../Checkpointer.ump"
   protected void hook533(IN target) throws DatabaseException{
    
  }

  // line 308 "../../../../Checkpointer.ump"
   protected void hook537() throws DatabaseException{
    
  }

  // line 311 "../../../../Checkpointer.ump"
   protected void hook538(EnvironmentImpl envImpl, long waitTime, String name) throws DatabaseException{
    
  }

  // line 314 "../../../../Checkpointer.ump"
   protected void hook539(EnvironmentImpl envImpl) throws DatabaseException{
    
  }

  // line 317 "../../../../Checkpointer.ump"
   protected void hook545(long waitTime) throws DatabaseException{
    
  }

  // line 321 "../../../../Checkpointer.ump"
   protected void hook546(SortedMap dirtyMap, boolean allowDeltas, long checkpointStart, Integer currentLevel, boolean logProvisionally, CheckpointReference targetRef) throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 36 "../../../../Checkpointer.ump"
  private EnvironmentImpl envImpl ;
// line 38 "../../../../Checkpointer.ump"
  private LogManager logManager ;
// line 40 "../../../../Checkpointer.ump"
  private long checkpointId ;
// line 42 "../../../../Checkpointer.ump"
  private long logFileMax ;
// line 44 "../../../../Checkpointer.ump"
  private long lastCheckpointMillis ;
// line 46 "../../../../Checkpointer.ump"
  private long lastFirstActiveLsn ;
// line 48 "../../../../Checkpointer.ump"
  private long lastCheckpointEnd ;
// line 50 "../../../../Checkpointer.ump"
  private volatile int highestFlushLevel ;

// line 79 "../../../../Checkpointer.ump"
  synchronized public void setCheckpointId (long lastCheckpointId) 
  {
    checkpointId = lastCheckpointId;
  }

// line 110 "../../../../Checkpointer.ump"
  public synchronized void doCheckpoint (CheckpointConfig config, boolean flushAll, String invokingSource)
	    throws DatabaseException 
  {
    new Checkpointer_doCheckpoint(this, config, flushAll, invokingSource).execute();
  }

  
}