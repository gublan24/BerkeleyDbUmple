/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.MemoryBudget;

// line 3 "../../../../TrackedFileSummary.ump"
// line 3 "../../../../TrackedFileSummary_static.ump"
// line 3 "../../../../MemoryBudget_TrackedFileSummary.ump"
// line 3 "../../../../MemoryBudget_TrackedFileSummary_inner.ump"
public class TrackedFileSummary extends FileSummary
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TrackedFileSummary()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * Creates an empty tracked summary.
   */
  // line 22 "../../../../TrackedFileSummary.ump"
  public  TrackedFileSummary(UtilizationTracker tracker, long fileNum, boolean trackDetail){
    this.tracker = tracker;
	this.fileNum = fileNum;
	this.trackDetail = trackDetail;
  }


  /**
   * 
   * Returns whether this summary is allowed or prohibited from being flushed or evicted during cleaning.  By default, flushing is allowed.
   */
  // line 31 "../../../../TrackedFileSummary.ump"
   public boolean getAllowFlush(){
    return allowFlush;
  }


  /**
   * 
   * Allows or prohibits this summary from being flushed or evicted during cleaning.  By default, flushing is allowed.
   */
  // line 38 "../../../../TrackedFileSummary.ump"
  public void setAllowFlush(boolean allowFlush){
    this.allowFlush = allowFlush;
  }


  /**
   * 
   * Returns the file number being tracked.
   */
  // line 45 "../../../../TrackedFileSummary.ump"
   public long getFileNumber(){
    return fileNum;
  }


  /**
   * 
   * Overrides reset for a tracked file, and is called when a FileSummaryLN is written to the log. <p>Must be called under the log write latch.</p>
   */
  // line 52 "../../../../TrackedFileSummary.ump"
   public void reset(){
    obsoleteOffsets = null;
			tracker.resetFile(this);
			//this.hook168();
			Label168:
if (memSize > 0) {
					updateMemoryBudget(0 - memSize);
			}
			//original();
   ;
      super.reset();
  }


  /**
   * 
   * Tracks the given offset as obsolete or non-obsolete. <p>Must be called under the log write latch.</p>
   */
  // line 63 "../../../../TrackedFileSummary.ump"
  public void trackObsolete(long offset){
    new TrackedFileSummary_trackObsolete(this, offset).execute();
  }


  /**
   * 
   * Adds the obsolete offsets as well as the totals of the given object.
   */
  // line 70 "../../../../TrackedFileSummary.ump"
  public void addTrackedSummary(TrackedFileSummary other){
    add(other);
	if (other.obsoleteOffsets != null) {
	    if (obsoleteOffsets != null) {
		if (obsoleteOffsets.merge(other.obsoleteOffsets)) {
		    //this.hook169();
        Label169:
updateMemoryBudget(-MemoryBudget.TFS_LIST_SEGMENT_OVERHEAD);
	//	original();
   ;
		}
	    } else {
		obsoleteOffsets = other.obsoleteOffsets;
	    }
	}
  }


  /**
   * 
   * Returns obsolete offsets as an array of longs, or null if none.
   */
  // line 87 "../../../../TrackedFileSummary.ump"
   public long[] getObsoleteOffsets(){
    if (obsoleteOffsets != null) {
	    return obsoleteOffsets.toArray();
	} else {
	    return null;
	}
  }


  /**
   * 
   * Returns whether the given offset is present in the tracked offsets. This does not indicate whether the offset is obsolete in general, but only if it is known to be obsolete in this version of the tracked information.
   */
  // line 98 "../../../../TrackedFileSummary.ump"
  public boolean containsObsoleteOffset(long offset){
    if (obsoleteOffsets != null) {
	    return obsoleteOffsets.contains(offset);
	} else {
	    return false;
	}
  }


  /**
   * 
   * Return the total memory size for this object.  We only bother to budget obsolete detail, not the overhead for this object, for two reasons: 1) The number of these objects is very small, and 2) unit tests disable detail tracking as a way to prevent budget adjustments here.
   */
  // line 11 "../../../../MemoryBudget_TrackedFileSummary.ump"
  public int getMemorySize(){
    return memSize;
  }

  // line 15 "../../../../MemoryBudget_TrackedFileSummary.ump"
   private void updateMemoryBudget(int delta){
    memSize += delta;
			tracker.getEnvironment().getMemoryBudget().updateMiscMemoryUsage(delta);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 4 "../../../../TrackedFileSummary_static.ump"
  // line 4 "../../../../MemoryBudget_TrackedFileSummary_inner.ump"
  public static class TrackedFileSummary_trackObsolete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public TrackedFileSummary_trackObsolete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../TrackedFileSummary_static.ump"
    public  TrackedFileSummary_trackObsolete(TrackedFileSummary _this, long offset){
      this._this=_this;
          this.offset=offset;
    }
  
    // line 10 "../../../../TrackedFileSummary_static.ump"
    public void execute(){
      if (!_this.trackDetail) {          
            // line 6 "../../../../MemoryBudget_TrackedFileSummary_inner.ump"
            //original();
                    if (adjustMem != 0) {
                      _this.updateMemoryBudget(adjustMem);
                    }
            // END OF UMPLE AFTER INJECTION
            return;
          }
          //this.hook170();
          Label170:
  adjustMem=0;
          //original();
     ;
          if (_this.obsoleteOffsets == null) {
            _this.obsoleteOffsets=new OffsetList();
            //this.hook171();
            Label171:
  adjustMem+=MemoryBudget.TFS_LIST_INITIAL_OVERHEAD;
          //original();
     ;
          }
          if (_this.obsoleteOffsets.add(offset,_this.tracker.getEnvironment().isOpen())) {
            //this.hook172();
            Label172:
  adjustMem+=MemoryBudget.TFS_LIST_SEGMENT_OVERHEAD;
          //original();
     ;
          }
      
      // line 6 "../../../../MemoryBudget_TrackedFileSummary_inner.ump"
      //original();
              if (adjustMem != 0) {
                _this.updateMemoryBudget(adjustMem);
              }
      // END OF UMPLE AFTER INJECTION
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 25 "../../../../TrackedFileSummary_static.ump"
    protected TrackedFileSummary _this ;
  // line 26 "../../../../TrackedFileSummary_static.ump"
    protected long offset ;
  // line 27 "../../../../TrackedFileSummary_static.ump"
    protected int adjustMem ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../TrackedFileSummary.ump"
  private UtilizationTracker tracker ;
// line 10 "../../../../TrackedFileSummary.ump"
  private long fileNum ;
// line 12 "../../../../TrackedFileSummary.ump"
  private OffsetList obsoleteOffsets ;
// line 14 "../../../../TrackedFileSummary.ump"
  private boolean trackDetail ;
// line 16 "../../../../TrackedFileSummary.ump"
  private boolean allowFlush = true ;
// line 5 "../../../../MemoryBudget_TrackedFileSummary.ump"
  private int memSize ;

  
}