/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../FileProcessor.ump"
// line 3 "../../../../FileProcessor_inner.ump"
public class FileProcessor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileProcessor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Reset per-run counters.
   */
  // line 35 "../../../../FileProcessor.ump"
   private void resetPerRunCounters(){
    nINsObsoleteThisRun = 0;
	nINsCleanedThisRun = 0;
	nINsDeadThisRun = 0;
	nINsMigratedThisRun = 0;
	nLNsObsoleteThisRun = 0;
	nLNsCleanedThisRun = 0;
	nLNsDeadThisRun = 0;
	nLNsMigratedThisRun = 0;
	nLNsMarkedThisRun = 0;
	nLNQueueHitsThisRun = 0;
	nLNsLockedThisRun = 0;
	nEntriesReadThisRun = 0;
	nRepeatIteratorReadsThisRun = 0;
  }


  /**
   * 
   * Add per-run counters to total counters.
   */
  // line 54 "../../../../FileProcessor.ump"
   private void accumulatePerRunCounters(){
    cleaner.nINsObsolete += nINsObsoleteThisRun;
	cleaner.nINsCleaned += nINsCleanedThisRun;
	cleaner.nINsDead += nINsDeadThisRun;
	cleaner.nINsMigrated += nINsMigratedThisRun;
	cleaner.nLNsObsolete += nLNsObsoleteThisRun;
	cleaner.nLNsCleaned += nLNsCleanedThisRun;
	cleaner.nLNsDead += nLNsDeadThisRun;
	cleaner.nLNsMigrated += nLNsMigratedThisRun;
	cleaner.nLNsMarked += nLNsMarkedThisRun;
	cleaner.nLNQueueHits += nLNQueueHitsThisRun;
	cleaner.nLNsLocked += nLNsLockedThisRun;
	cleaner.nRepeatIteratorReads += nRepeatIteratorReadsThisRun;
  }

  // line 69 "../../../../FileProcessor.ump"
   protected void hook138() throws DatabaseException{
    resetPerRunCounters();
	original();
  }

  // line 74 "../../../../FileProcessor.ump"
   protected String hook139(String traceMsg) throws DatabaseException,IOException{
    traceMsg += " begins backlog=" + cleaner.nBacklogFiles;
	return original(traceMsg);
  }

  // line 79 "../../../../FileProcessor.ump"
   protected void hook140() throws DatabaseException,IOException{
    accumulatePerRunCounters();
	original();
  }

  // line 84 "../../../../FileProcessor.ump"
   protected String hook141(String traceMsg) throws DatabaseException{
    traceMsg += " nEntriesRead=" + nEntriesReadThisRun + " nINsObsolete=" + nINsObsoleteThisRun + " nINsCleaned="
		+ nINsCleanedThisRun + " nINsDead=" + nINsDeadThisRun + " nINsMigrated=" + nINsMigratedThisRun
		+ " nLNsObsolete=" + nLNsObsoleteThisRun + " nLNsCleaned=" + nLNsCleanedThisRun + " nLNsDead="
		+ nLNsDeadThisRun + " nLNsMigrated=" + nLNsMigratedThisRun + " nLNsMarked=" + nLNsMarkedThisRun
		+ " nLNQueueHits=" + nLNQueueHitsThisRun + " nLNsLocked=" + nLNsLockedThisRun;
	return original(traceMsg);
  }

  // line 93 "../../../../FileProcessor.ump"
   protected void hook142() throws DatabaseException{
    nLNsLockedThisRun++;
	original();
  }

  // line 98 "../../../../FileProcessor.ump"
   protected void hook143() throws DatabaseException{
    nLNsDeadThisRun++;
	original();
  }

  // line 103 "../../../../FileProcessor.ump"
   protected void hook144() throws DatabaseException{
    nLNsMarkedThisRun++;
	original();
  }

  // line 109 "../../../../FileProcessor.ump"
   protected void hook125(IN inClone, DatabaseImpl db, long lsn, boolean obsolete, boolean dirtied, boolean completed) throws DatabaseException{
    nINsCleanedThisRun++;
	original(inClone, db, lsn, obsolete, dirtied, completed);
  }

  // line 114 "../../../../FileProcessor.ump"
   protected void hook151() throws DatabaseException{
    nINsDeadThisRun++;
	original();
  }

  // line 119 "../../../../FileProcessor.ump"
   protected void hook152() throws DatabaseException{
    nINsDeadThisRun++;
	original();
  }

  // line 124 "../../../../FileProcessor.ump"
   protected void hook153() throws DatabaseException{
    nINsMigratedThisRun++;
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../FileProcessor_inner.ump"
  public static class FileProcessor_processLN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processLN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../FileProcessor_inner.ump"
    public void execute() throws DatabaseException{
      _this.nLNsCleanedThisRun++;
          original();
    }
  
    // line 10 "../../../../FileProcessor_inner.ump"
     protected void hook148() throws DatabaseException{
      _this.nLNsDeadThisRun++;
          original();
    }
  
    // line 14 "../../../../FileProcessor_inner.ump"
     protected void hook149() throws DatabaseException{
      _this.nLNsDeadThisRun++;
          original();
    }
  
    // line 18 "../../../../FileProcessor_inner.ump"
     protected void hook150() throws DatabaseException{
      _this.nLNsDeadThisRun++;
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 22 "../../../../FileProcessor_inner.ump"
  public static class FileProcessor_processFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 24 "../../../../FileProcessor_inner.ump"
     protected void hook145() throws DatabaseException,IOException{
      _this.nEntriesReadThisRun=reader.getNumRead();
          _this.nRepeatIteratorReadsThisRun=reader.getNRepeatIteratorReads();
          original();
    }
  
    // line 29 "../../../../FileProcessor_inner.ump"
     protected void hook146() throws DatabaseException,IOException{
      _this.cleaner.nEntriesRead+=1;
          original();
    }
  
    // line 33 "../../../../FileProcessor_inner.ump"
     protected void hook147() throws DatabaseException,IOException{
      if (isLN) {
            _this.nLNsObsoleteThisRun++;
          }
     else       if (isIN) {
            _this.nINsObsoleteThisRun++;
          }
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../FileProcessor.ump"
  private int nINsObsoleteThisRun = 0 ;
// line 7 "../../../../FileProcessor.ump"
  private int nINsCleanedThisRun = 0 ;
// line 9 "../../../../FileProcessor.ump"
  private int nINsDeadThisRun = 0 ;
// line 11 "../../../../FileProcessor.ump"
  private int nINsMigratedThisRun = 0 ;
// line 13 "../../../../FileProcessor.ump"
  private int nLNsObsoleteThisRun = 0 ;
// line 15 "../../../../FileProcessor.ump"
  private int nLNsCleanedThisRun = 0 ;
// line 17 "../../../../FileProcessor.ump"
  private int nLNsDeadThisRun = 0 ;
// line 19 "../../../../FileProcessor.ump"
  private int nLNsLockedThisRun = 0 ;
// line 21 "../../../../FileProcessor.ump"
  private int nLNsMigratedThisRun = 0 ;
// line 23 "../../../../FileProcessor.ump"
  private int nLNsMarkedThisRun = 0 ;
// line 25 "../../../../FileProcessor.ump"
  private int nLNQueueHitsThisRun = 0 ;
// line 27 "../../../../FileProcessor.ump"
  private int nEntriesReadThisRun ;
// line 29 "../../../../FileProcessor.ump"
  private long nRepeatIteratorReadsThisRun ;

  
}