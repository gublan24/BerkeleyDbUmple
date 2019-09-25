/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.MemoryBudget;

// line 3 "../../../../TrackedFileSummary.ump"
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
	this.hook168();
	super.reset();
  }


  /**
   * 
   * Tracks the given offset as obsolete or non-obsolete. <p>Must be called under the log write latch.</p>
   */
  // line 62 "../../../../TrackedFileSummary.ump"
  public void trackObsolete(long offset){
    new TrackedFileSummary_trackObsolete(this, offset).execute();
  }


  /**
   * 
   * Adds the obsolete offsets as well as the totals of the given object.
   */
  // line 69 "../../../../TrackedFileSummary.ump"
  public void addTrackedSummary(TrackedFileSummary other){
    add(other);
	if (other.obsoleteOffsets != null) {
	    if (obsoleteOffsets != null) {
		if (obsoleteOffsets.merge(other.obsoleteOffsets)) {
		    this.hook169();
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
  // line 85 "../../../../TrackedFileSummary.ump"
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
  // line 96 "../../../../TrackedFileSummary.ump"
  public boolean containsObsoleteOffset(long offset){
    if (obsoleteOffsets != null) {
	    return obsoleteOffsets.contains(offset);
	} else {
	    return false;
	}
  }

  // line 104 "../../../../TrackedFileSummary.ump"
   protected void hook168(){
    
  }

  // line 107 "../../../../TrackedFileSummary.ump"
   protected void hook169(){
    
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

  
}