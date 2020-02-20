/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.util.List;
import java.util.ArrayList;

// line 3 "../../../../UtilizationTracker.ump"
// line 3 "../../../../Evictor_UtilizationTracker.ump"
// line 3 "../../../../Evictor_UtilizationTracker_inner.ump"
public class UtilizationTracker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UtilizationTracker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates an empty tracker. The cleaner field of the environment object must be initialized before using this constructor.
   */
  // line 29 "../../../../UtilizationTracker.ump"
   public  UtilizationTracker(EnvironmentImpl env) throws DatabaseException{
    this(env, env.getCleaner());
  }


  /**
   * 
   * Constructor used by the cleaner constructor, prior to setting the cleaner field of the environment.
   */
  // line 36 "../../../../UtilizationTracker.ump"
  public  UtilizationTracker(EnvironmentImpl env, Cleaner cleaner) throws DatabaseException{
    assert cleaner != null;
	this.env = env;
	this.cleaner = cleaner;
	files = new ArrayList();
	snapshot = new TrackedFileSummary[0];
	activeFile = -1;
  }

  // line 45 "../../../../UtilizationTracker.ump"
   public EnvironmentImpl getEnvironment(){
    return env;
  }


  /**
   * 
   * Wakeup the cleaner thread and reset the log byte counter.
   */
  // line 52 "../../../../UtilizationTracker.ump"
   public void activateCleaner(){
    env.getCleaner().wakeup();
	bytesSinceActivate = 0;
  }


  /**
   * 
   * Returns a snapshot of the files being tracked as of the last time a log entry was added. The summary info returned is the delta since the last checkpoint, not the grand totals, and is approximate since it is changing in real time. This method may be called without holding the log write latch. <p> If files are added or removed from the list of tracked files in real time, the returned array will not be changed since it is a snapshot. But the objects contained in the array are live and will be updated in real time under the log write latch. The array and the objects in the array should not be modified by the caller. </p>
   */
  // line 60 "../../../../UtilizationTracker.ump"
   public TrackedFileSummary[] getTrackedFiles(){
    return snapshot;
  }


  /**
   * 
   * Returns one file from the snapshot of tracked files, or null if the given file number is not in the snapshot array.
   * @see #getTrackedFiles
   */
  // line 68 "../../../../UtilizationTracker.ump"
   public TrackedFileSummary getTrackedFile(long fileNum){
    TrackedFileSummary[] a = snapshot;
	for (int i = 0; i < a.length; i += 1) {
	    if (a[i].getFileNumber() == fileNum) {
		return a[i];
	    }
	}
	return null;
  }


  /**
   * 
   * Counts the addition of all new log entries including LNs, and returns whether the cleaner should be woken. <p> Must be called under the log write latch. </p>
   */
  // line 81 "../../../../UtilizationTracker.ump"
   public boolean countNewLogEntry(long lsn, LogEntryType type, int size){
    TrackedFileSummary file = getFile(DbLsn.getFileNumber(lsn));
	file.totalCount += 1;
	file.totalSize += size;
	if (type.isNodeType()) {
	    if (inArray(type, LogEntryType.IN_TYPES)) {
		file.totalINCount += 1;
		file.totalINSize += size;
	    } else {
		file.totalLNCount += 1;
		file.totalLNSize += size;
	    }
	}
	bytesSinceActivate += size;
	return (bytesSinceActivate >= env.getCleaner().cleanerBytesInterval);
  }


  /**
   * 
   * Counts a node that has become obsolete and tracks the LSN offset to avoid a lookup during cleaning. <p> This method should only be called for LNs and INs (i.e, only for nodes). If type is null we assume it is an LN. </p> <p> Must be called under the log write latch. </p>
   */
  // line 101 "../../../../UtilizationTracker.ump"
   public void countObsoleteNode(long lsn, LogEntryType type){
    TrackedFileSummary file = getFile(DbLsn.getFileNumber(lsn));
	countOneNode(file, type);
	file.trackObsolete(DbLsn.getFileOffset(lsn));
  }


  /**
   * 
   * Counts as countObsoleteNode does, but since the LSN may be inexact, does not track the obsolete LSN offset. <p> This method should only be called for LNs and INs (i.e, only for nodes). If type is null we assume it is an LN. </p> <p> Must be called under the log write latch. </p>
   */
  // line 110 "../../../../UtilizationTracker.ump"
   public void countObsoleteNodeInexact(long lsn, LogEntryType type){
    TrackedFileSummary file = getFile(DbLsn.getFileNumber(lsn));
	countOneNode(file, type);
  }


  /**
   * 
   * Counts a change in the obsolete status of an node, incrementing the obsolete count if obsolete is true and decrementing it if obsolete is false.
   */
  // line 118 "../../../../UtilizationTracker.ump"
   private void countOneNode(TrackedFileSummary file, LogEntryType type){
    if (type == null || type.isNodeType()) {
	    if (type == null || !inArray(type, LogEntryType.IN_TYPES)) {
		file.obsoleteLNCount += 1;
	    } else {
		file.obsoleteINCount += 1;
	    }
	}
  }


  /**
   * 
   * Adds changes from a given TrackedFileSummary. <p> Must be called under the log write latch. </p>
   */
  // line 131 "../../../../UtilizationTracker.ump"
   public void addSummary(long fileNumber, TrackedFileSummary other){
    TrackedFileSummary file = getFile(fileNumber);
	file.addTrackedSummary(other);
  }


  /**
   * 
   * Returns a tracked summary for the given file which will not be flushed. Used for watching changes that occur while a file is being cleaned.
   */
  // line 139 "../../../../UtilizationTracker.ump"
   public TrackedFileSummary getUnflushableTrackedSummary(long fileNum) throws DatabaseException{
    TrackedFileSummary file = getFile(fileNum);
	file.setAllowFlush(false);
	return file;
  }


  /**
   * 
   * Returns a tracked file for the given file number, adding an empty one if the file is not already being tracked. <p> Must be called under the log write latch. </p>
   */
  // line 148 "../../../../UtilizationTracker.ump"
   private TrackedFileSummary getFile(long fileNum){
    if (activeFile < fileNum) {
	    activeFile = fileNum;
	}
	int size = files.size();
	for (int i = 0; i < size; i += 1) {
	    TrackedFileSummary file = (TrackedFileSummary) files.get(i);
	    if (file.getFileNumber() == fileNum) {
		return file;
	    }
	}
	TrackedFileSummary file = new TrackedFileSummary(this, fileNum, cleaner.trackDetail);
	files.add(file);
	takeSnapshot();
	return file;
  }


  /**
   * 
   * Called after the FileSummaryLN is written to the log during checkpoint. <p> We keep the active file summary in the tracked file list, but we remove older files to prevent unbounded growth of the list. </p> <p> Must be called under the log write latch. </p>
   */
  // line 168 "../../../../UtilizationTracker.ump"
  public void resetFile(TrackedFileSummary file){
    if (file.getFileNumber() < activeFile && file.getAllowFlush()) {
	    files.remove(file);
	    takeSnapshot();
	}
  }


  /**
   * 
   * Takes a snapshot of the tracked file list. <p> Must be called under the log write latch. </p>
   */
  // line 178 "../../../../UtilizationTracker.ump"
   private void takeSnapshot(){
    TrackedFileSummary[] a = new TrackedFileSummary[files.size()];
	files.toArray(a);
	snapshot = a;
  }


  /**
   * 
   * Returns whether an object reference is in an array.
   */
  // line 187 "../../../../UtilizationTracker.ump"
   private boolean inArray(Object o, Object [] a){
    for (int i = 0; i < a.length; i += 1) {
	    if (a[i] == o) {
		return true;
	    }
	}
	return false;
  }


  /**
   * 
   * Evicts tracked detail if the budget for the tracker is exceeded. Evicts only one file summary LN at most to keep eviction batches small. Returns the number of bytes freed. <p> When flushFileSummary is called, the TrackedFileSummary is cleared via its reset method, which is called by FileSummaryLN.writeToLog. This is how memory is subtracted from the budget. </p>
   */
  // line 9 "../../../../Evictor_UtilizationTracker.ump"
   public long evictMemory() throws DatabaseException{
    return new UtilizationTracker_evictMemory(this).execute();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 4 "../../../../Evictor_UtilizationTracker_inner.ump"
  public static class UtilizationTracker_evictMemory
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationTracker_evictMemory()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_UtilizationTracker_inner.ump"
    public  UtilizationTracker_evictMemory(UtilizationTracker _this){
      this._this=_this;
    }
  
    // line 9 "../../../../Evictor_UtilizationTracker_inner.ump"
    public long execute() throws DatabaseException{
      if (!_this.cleaner.trackDetail) {
            return 0;
          }
          if (!_this.env.isOpen()) {
            return 0;
          }
          mb=_this.env.getMemoryBudget();
          totalEvicted=0;
          totalBytes=0;
          largestBytes=0;
          bestFile=null;
          a=_this.snapshot;
          for (int i=0; i < a.length; i+=1) {
            tfs=a[i];
  
            Label198:          //this.hook198();
            b1=tfs.getAllowFlush();
  
            Label197:          //this.hook197();
            if (b1) {
  
               Label199:           // this.hook199();
              bestFile=tfs;
            }
          }
          b2=bestFile != null;
  
            Label196:        //this.hook196();
          if (b2) {
            _this.env.getUtilizationProfile().flushFileSummary(bestFile);
            totalEvicted+=largestBytes;
          }
          return totalEvicted;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 43 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected UtilizationTracker _this ;
  // line 44 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected MemoryBudget mb ;
  // line 45 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected long totalEvicted ;
  // line 46 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected long totalBytes ;
  // line 47 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected int largestBytes ;
  // line 48 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected TrackedFileSummary bestFile ;
  // line 49 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected TrackedFileSummary[] a ;
  // line 50 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected TrackedFileSummary tfs ;
  // line 51 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected int mem ;
  // line 52 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected boolean b1 ;
  // line 53 "../../../../Evictor_UtilizationTracker_inner.ump"
    protected boolean b2 ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../UtilizationTracker.ump"
  private EnvironmentImpl env ;
// line 15 "../../../../UtilizationTracker.ump"
  private Cleaner cleaner ;
// line 17 "../../../../UtilizationTracker.ump"
  private List files ;
// line 19 "../../../../UtilizationTracker.ump"
  private long activeFile ;
// line 21 "../../../../UtilizationTracker.ump"
  private TrackedFileSummary[] snapshot ;
// line 23 "../../../../UtilizationTracker.ump"
  private long bytesSinceActivate ;

  
}