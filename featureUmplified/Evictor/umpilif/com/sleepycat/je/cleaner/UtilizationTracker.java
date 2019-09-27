/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../UtilizationTracker.ump"
// line 3 "../../../../UtilizationTracker_inner.ump"
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
   * Evicts tracked detail if the budget for the tracker is exceeded. Evicts only one file summary LN at most to keep eviction batches small. Returns the number of bytes freed. <p> When flushFileSummary is called, the TrackedFileSummary is cleared via its reset method, which is called by FileSummaryLN.writeToLog. This is how memory is subtracted from the budget. </p>
   */
  // line 9 "../../../../UtilizationTracker.ump"
   public long evictMemory() throws DatabaseException{
    return new UtilizationTracker_evictMemory(this).execute();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../UtilizationTracker_inner.ump"
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
  
    // line 6 "../../../../UtilizationTracker_inner.ump"
    public  UtilizationTracker_evictMemory(UtilizationTracker _this){
      this._this=_this;
    }
  
    // line 9 "../../../../UtilizationTracker_inner.ump"
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
            this.hook198();
            b1=tfs.getAllowFlush();
            this.hook197();
            if (b1) {
              this.hook199();
              bestFile=tfs;
            }
          }
          b2=bestFile != null;
          this.hook196();
          if (b2) {
            _this.env.getUtilizationProfile().flushFileSummary(bestFile);
            totalEvicted+=largestBytes;
          }
          return totalEvicted;
    }
  
    // line 51 "../../../../UtilizationTracker_inner.ump"
     protected void hook196() throws DatabaseException{
      
    }
  
    // line 53 "../../../../UtilizationTracker_inner.ump"
     protected void hook197() throws DatabaseException{
      
    }
  
    // line 55 "../../../../UtilizationTracker_inner.ump"
     protected void hook198() throws DatabaseException{
      
    }
  
    // line 57 "../../../../UtilizationTracker_inner.ump"
     protected void hook199() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 39 "../../../../UtilizationTracker_inner.ump"
    protected UtilizationTracker _this ;
  // line 40 "../../../../UtilizationTracker_inner.ump"
    protected MemoryBudget mb ;
  // line 41 "../../../../UtilizationTracker_inner.ump"
    protected long totalEvicted ;
  // line 42 "../../../../UtilizationTracker_inner.ump"
    protected long totalBytes ;
  // line 43 "../../../../UtilizationTracker_inner.ump"
    protected int largestBytes ;
  // line 44 "../../../../UtilizationTracker_inner.ump"
    protected TrackedFileSummary bestFile ;
  // line 45 "../../../../UtilizationTracker_inner.ump"
    protected TrackedFileSummary[] a ;
  // line 46 "../../../../UtilizationTracker_inner.ump"
    protected TrackedFileSummary tfs ;
  // line 47 "../../../../UtilizationTracker_inner.ump"
    protected int mem ;
  // line 48 "../../../../UtilizationTracker_inner.ump"
    protected boolean b1 ;
  // line 49 "../../../../UtilizationTracker_inner.ump"
    protected boolean b2 ;
  
    
  }
}