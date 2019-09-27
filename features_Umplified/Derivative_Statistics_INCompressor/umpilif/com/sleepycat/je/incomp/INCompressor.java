/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.incomp;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../INCompressor.ump"
public class INCompressor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INCompressor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Return stats
   */
  // line 39 "../../../../INCompressor.ump"
   public void loadStats(StatsConfig config, EnvironmentStats stat) throws DatabaseException{
    stat.setSplitBins(splitBins);
	stat.setDbClosedBins(dbClosedBins);
	stat.setCursorsBins(cursorsBins);
	stat.setNonEmptyBins(nonEmptyBins);
	stat.setProcessedBins(processedBins);
	stat.setInCompQueueSize(getBinRefQueueSize());
	if (DEBUG) {
	    System.out.println("lazyProcessed = " + lazyProcessed);
	    System.out.println("lazyEmpty = " + lazyEmpty);
	    System.out.println("lazySplit = " + lazySplit);
	    System.out.println("wokenUp=" + wokenUp);
	}
	if (config.getClear()) {
	    splitBins = 0;
	    dbClosedBins = 0;
	    cursorsBins = 0;
	    nonEmptyBins = 0;
	    processedBins = 0;
	    lazyProcessed = 0;
	    lazyEmpty = 0;
	    lazySplit = 0;
	    wokenUp = 0;
	}
  }


  /**
   * 
   * Reset per-run counters.
   */
  // line 68 "../../../../INCompressor.ump"
   private void resetPerRunCounters(){
    splitBinsThisRun = 0;
	dbClosedBinsThisRun = 0;
	cursorsBinsThisRun = 0;
	nonEmptyBinsThisRun = 0;
	processedBinsThisRun = 0;
  }

  // line 76 "../../../../INCompressor.ump"
   private void accumulatePerRunCounters(){
    splitBins += splitBinsThisRun;
	dbClosedBins += dbClosedBinsThisRun;
	cursorsBins += cursorsBinsThisRun;
	nonEmptyBins += nonEmptyBinsThisRun;
	processedBins += processedBinsThisRun;
  }

  // line 84 "../../../../INCompressor.ump"
   protected void hook403() throws DatabaseException{
    wokenUp++;
	original();
  }

  // line 89 "../../../../INCompressor.ump"
   protected void hook404() throws DatabaseException{
    resetPerRunCounters();
	original();
  }

  // line 94 "../../../../INCompressor.ump"
   protected void hook405() throws DatabaseException{
    accumulatePerRunCounters();
	original();
  }

  // line 99 "../../../../INCompressor.ump"
   protected void hook406() throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    processedBinsThisRun++;
	original();
  }

  // line 104 "../../../../INCompressor.ump"
   protected void hook407() throws DatabaseException{
    nonEmptyBinsThisRun++;
	original();
  }

  // line 109 "../../../../INCompressor.ump"
   protected void hook408() throws DatabaseException{
    cursorsBinsThisRun++;
	original();
  }

  // line 114 "../../../../INCompressor.ump"
   protected void hook409() throws DatabaseException{
    lazyProcessed++;
	original();
  }

  // line 119 "../../../../INCompressor.ump"
   protected void hook410() throws DatabaseException{
    lazySplit++;
	original();
  }

  // line 124 "../../../../INCompressor.ump"
   protected void hook411() throws DatabaseException{
    lazyEmpty++;
	original();
  }

  // line 129 "../../../../INCompressor.ump"
   protected void hook412() throws DatabaseException{
    dbClosedBinsThisRun++;
	original();
  }

  // line 134 "../../../../INCompressor.ump"
   protected void hook413() throws DatabaseException{
    splitBinsThisRun++;
	original();
  }

  // line 139 "../../../../INCompressor.ump"
   protected void hook414() throws DatabaseException{
    cursorsBinsThisRun++;
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../INCompressor.ump"
  private int splitBins = 0 ;
// line 9 "../../../../INCompressor.ump"
  private int dbClosedBins = 0 ;
// line 11 "../../../../INCompressor.ump"
  private int cursorsBins = 0 ;
// line 13 "../../../../INCompressor.ump"
  private int nonEmptyBins = 0 ;
// line 15 "../../../../INCompressor.ump"
  private int processedBins = 0 ;
// line 17 "../../../../INCompressor.ump"
  private int splitBinsThisRun = 0 ;
// line 19 "../../../../INCompressor.ump"
  private int dbClosedBinsThisRun = 0 ;
// line 21 "../../../../INCompressor.ump"
  private int cursorsBinsThisRun = 0 ;
// line 23 "../../../../INCompressor.ump"
  private int nonEmptyBinsThisRun = 0 ;
// line 25 "../../../../INCompressor.ump"
  private int processedBinsThisRun = 0 ;
// line 27 "../../../../INCompressor.ump"
  private int lazyProcessed = 0 ;
// line 29 "../../../../INCompressor.ump"
  private int lazyEmpty = 0 ;
// line 31 "../../../../INCompressor.ump"
  private int lazySplit = 0 ;
// line 33 "../../../../INCompressor.ump"
  private int wokenUp = 0 ;

  
}