/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import java.io.Serializable;

// line 3 "../../../EnvironmentStats.ump"
public class EnvironmentStats
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentStats()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Internal use only.
   */
  // line 224 "../../../EnvironmentStats.ump"
   public  EnvironmentStats(){
    reset();
  }


  /**
   * 
   * Resets all stats.
   */
  // line 231 "../../../EnvironmentStats.ump"
   private void reset(){
    splitBins = 0;
	dbClosedBins = 0;
	cursorsBins = 0;
	nonEmptyBins = 0;
	processedBins = 0;
	inCompQueueSize = 0;
	nEvictPasses = 0;
	nNodesSelected = 0;
	nNodesScanned = 0;
	nNodesExplicitlyEvicted = 0;
	nBINsStripped = 0;
	requiredEvictBytes = 0;
	nCheckpoints = 0;
	lastCheckpointId = 0;
	nFullINFlush = 0;
	nFullBINFlush = 0;
	nDeltaINFlush = 0;
	lastCheckpointStart = DbLsn.NULL_LSN;
	lastCheckpointEnd = DbLsn.NULL_LSN;
	cleanerBacklog = 0;
	nCleanerRuns = 0;
	nCleanerDeletions = 0;
	nINsObsolete = 0;
	nINsCleaned = 0;
	nINsDead = 0;
	nINsMigrated = 0;
	nLNsObsolete = 0;
	nLNsCleaned = 0;
	nLNsDead = 0;
	nLNsLocked = 0;
	nLNsMigrated = 0;
	nLNsMarked = 0;
	nLNQueueHits = 0;
	nPendingLNsProcessed = 0;
	nMarkedLNsProcessed = 0;
	nToBeCleanedLNsProcessed = 0;
	nClusterLNsProcessed = 0;
	nPendingLNsLocked = 0;
	nCleanerEntriesRead = 0;
	cacheDataBytes = 0;
	nNotResident = 0;
	nCacheMiss = 0;
	nLogBuffers = 0;
	bufferBytes = 0;
	this.hook60();
	nRepeatFaultReads = 0;
	nTempBufferWrites = 0;
	nRepeatIteratorReads = 0;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 285 "../../../EnvironmentStats.ump"
   public long getBufferBytes(){
    return bufferBytes;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 292 "../../../EnvironmentStats.ump"
   public int getCursorsBins(){
    return cursorsBins;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 299 "../../../EnvironmentStats.ump"
   public int getDbClosedBins(){
    return dbClosedBins;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 306 "../../../EnvironmentStats.ump"
   public int getInCompQueueSize(){
    return inCompQueueSize;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 313 "../../../EnvironmentStats.ump"
   public long getLastCheckpointId(){
    return lastCheckpointId;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 320 "../../../EnvironmentStats.ump"
   public long getNCacheMiss(){
    return nCacheMiss;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 327 "../../../EnvironmentStats.ump"
   public int getNCheckpoints(){
    return nCheckpoints;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 334 "../../../EnvironmentStats.ump"
   public int getCleanerBacklog(){
    return cleanerBacklog;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 341 "../../../EnvironmentStats.ump"
   public int getNCleanerRuns(){
    return nCleanerRuns;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 348 "../../../EnvironmentStats.ump"
   public int getNCleanerDeletions(){
    return nCleanerDeletions;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 355 "../../../EnvironmentStats.ump"
   public int getNDeltaINFlush(){
    return nDeltaINFlush;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 362 "../../../EnvironmentStats.ump"
   public long getLastCheckpointEnd(){
    return lastCheckpointEnd;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 369 "../../../EnvironmentStats.ump"
   public long getLastCheckpointStart(){
    return lastCheckpointStart;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 376 "../../../EnvironmentStats.ump"
   public int getNCleanerEntriesRead(){
    return nCleanerEntriesRead;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 383 "../../../EnvironmentStats.ump"
   public int getNEvictPasses(){
    return nEvictPasses;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 390 "../../../EnvironmentStats.ump"
   public int getNFullINFlush(){
    return nFullINFlush;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 397 "../../../EnvironmentStats.ump"
   public int getNFullBINFlush(){
    return nFullBINFlush;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 404 "../../../EnvironmentStats.ump"
   public int getNINsObsolete(){
    return nINsObsolete;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 411 "../../../EnvironmentStats.ump"
   public int getNINsCleaned(){
    return nINsCleaned;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 418 "../../../EnvironmentStats.ump"
   public int getNINsDead(){
    return nINsDead;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 425 "../../../EnvironmentStats.ump"
   public int getNINsMigrated(){
    return nINsMigrated;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 432 "../../../EnvironmentStats.ump"
   public int getNLNsObsolete(){
    return nLNsObsolete;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 439 "../../../EnvironmentStats.ump"
   public int getNLNsCleaned(){
    return nLNsCleaned;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 446 "../../../EnvironmentStats.ump"
   public int getNLNsDead(){
    return nLNsDead;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 453 "../../../EnvironmentStats.ump"
   public int getNLNsLocked(){
    return nLNsLocked;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 460 "../../../EnvironmentStats.ump"
   public int getNLNsMigrated(){
    return nLNsMigrated;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 467 "../../../EnvironmentStats.ump"
   public int getNLNsMarked(){
    return nLNsMarked;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 474 "../../../EnvironmentStats.ump"
   public int getNLNQueueHits(){
    return nLNQueueHits;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 481 "../../../EnvironmentStats.ump"
   public int getNPendingLNsProcessed(){
    return nPendingLNsProcessed;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 488 "../../../EnvironmentStats.ump"
   public int getNMarkedLNsProcessed(){
    return nMarkedLNsProcessed;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 495 "../../../EnvironmentStats.ump"
   public int getNToBeCleanedLNsProcessed(){
    return nToBeCleanedLNsProcessed;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 502 "../../../EnvironmentStats.ump"
   public int getNClusterLNsProcessed(){
    return nClusterLNsProcessed;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 509 "../../../EnvironmentStats.ump"
   public int getNPendingLNsLocked(){
    return nPendingLNsLocked;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 516 "../../../EnvironmentStats.ump"
   public int getNLogBuffers(){
    return nLogBuffers;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 523 "../../../EnvironmentStats.ump"
   public long getNNodesExplicitlyEvicted(){
    return nNodesExplicitlyEvicted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 530 "../../../EnvironmentStats.ump"
   public long getNBINsStripped(){
    return nBINsStripped;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 537 "../../../EnvironmentStats.ump"
   public long getRequiredEvictBytes(){
    return requiredEvictBytes;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 544 "../../../EnvironmentStats.ump"
   public long getNNodesScanned(){
    return nNodesScanned;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 551 "../../../EnvironmentStats.ump"
   public long getNNodesSelected(){
    return nNodesSelected;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 558 "../../../EnvironmentStats.ump"
   public long getCacheTotalBytes(){
    return cacheDataBytes + bufferBytes;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 565 "../../../EnvironmentStats.ump"
   public long getCacheDataBytes(){
    return cacheDataBytes;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 572 "../../../EnvironmentStats.ump"
   public long getNNotResident(){
    return nNotResident;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 579 "../../../EnvironmentStats.ump"
   public int getNonEmptyBins(){
    return nonEmptyBins;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 586 "../../../EnvironmentStats.ump"
   public int getProcessedBins(){
    return processedBins;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 593 "../../../EnvironmentStats.ump"
   public long getNRepeatFaultReads(){
    return nRepeatFaultReads;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 600 "../../../EnvironmentStats.ump"
   public long getNTempBufferWrites(){
    return nTempBufferWrites;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 607 "../../../EnvironmentStats.ump"
   public long getNRepeatIteratorReads(){
    return nRepeatIteratorReads;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 614 "../../../EnvironmentStats.ump"
   public int getSplitBins(){
    return splitBins;
  }


  /**
   * 
   * Internal use only.
   */
  // line 621 "../../../EnvironmentStats.ump"
   public void setCacheDataBytes(long cacheDataBytes){
    this.cacheDataBytes = cacheDataBytes;
  }


  /**
   * 
   * Internal use only.
   */
  // line 628 "../../../EnvironmentStats.ump"
   public void setNNotResident(long nNotResident){
    this.nNotResident = nNotResident;
  }


  /**
   * 
   * Internal use only.
   */
  // line 635 "../../../EnvironmentStats.ump"
   public void setNCacheMiss(long nCacheMiss){
    this.nCacheMiss = nCacheMiss;
  }


  /**
   * 
   * Internal use only.
   */
  // line 642 "../../../EnvironmentStats.ump"
   public void setNLogBuffers(int nLogBuffers){
    this.nLogBuffers = nLogBuffers;
  }


  /**
   * 
   * Internal use only.
   */
  // line 649 "../../../EnvironmentStats.ump"
   public void setBufferBytes(long bufferBytes){
    this.bufferBytes = bufferBytes;
  }


  /**
   * 
   * Internal use only.
   */
  // line 656 "../../../EnvironmentStats.ump"
   public void setCursorsBins(int val){
    cursorsBins = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 663 "../../../EnvironmentStats.ump"
   public void setDbClosedBins(int val){
    dbClosedBins = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 670 "../../../EnvironmentStats.ump"
   public void setInCompQueueSize(int val){
    inCompQueueSize = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 677 "../../../EnvironmentStats.ump"
   public void setLastCheckpointId(long l){
    lastCheckpointId = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 684 "../../../EnvironmentStats.ump"
   public void setNCheckpoints(int val){
    nCheckpoints = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 691 "../../../EnvironmentStats.ump"
   public void setCleanerBacklog(int val){
    cleanerBacklog = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 698 "../../../EnvironmentStats.ump"
   public void setNCleanerRuns(int val){
    nCleanerRuns = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 705 "../../../EnvironmentStats.ump"
   public void setNCleanerDeletions(int val){
    nCleanerDeletions = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 712 "../../../EnvironmentStats.ump"
   public void setNDeltaINFlush(int val){
    nDeltaINFlush = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 719 "../../../EnvironmentStats.ump"
   public void setLastCheckpointEnd(long lsn){
    lastCheckpointEnd = lsn;
  }


  /**
   * 
   * Internal use only.
   */
  // line 726 "../../../EnvironmentStats.ump"
   public void setLastCheckpointStart(long lsn){
    lastCheckpointStart = lsn;
  }


  /**
   * 
   * Internal use only.
   */
  // line 733 "../../../EnvironmentStats.ump"
   public void setNCleanerEntriesRead(int val){
    nCleanerEntriesRead = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 740 "../../../EnvironmentStats.ump"
   public void setNEvictPasses(int val){
    nEvictPasses = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 747 "../../../EnvironmentStats.ump"
   public void setNFullINFlush(int val){
    nFullINFlush = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 754 "../../../EnvironmentStats.ump"
   public void setNFullBINFlush(int val){
    nFullBINFlush = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 761 "../../../EnvironmentStats.ump"
   public void setNINsObsolete(int val){
    nINsObsolete = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 768 "../../../EnvironmentStats.ump"
   public void setNINsCleaned(int val){
    nINsCleaned = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 775 "../../../EnvironmentStats.ump"
   public void setNINsDead(int val){
    nINsDead = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 782 "../../../EnvironmentStats.ump"
   public void setNINsMigrated(int val){
    nINsMigrated = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 789 "../../../EnvironmentStats.ump"
   public void setNLNsObsolete(int val){
    nLNsObsolete = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 796 "../../../EnvironmentStats.ump"
   public void setNLNsCleaned(int val){
    nLNsCleaned = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 803 "../../../EnvironmentStats.ump"
   public void setNLNsDead(int val){
    nLNsDead = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 810 "../../../EnvironmentStats.ump"
   public void setNLNsLocked(int val){
    nLNsLocked = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 817 "../../../EnvironmentStats.ump"
   public void setNLNsMigrated(int val){
    nLNsMigrated = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 824 "../../../EnvironmentStats.ump"
   public void setNLNsMarked(int val){
    nLNsMarked = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 831 "../../../EnvironmentStats.ump"
   public void setNLNQueueHits(int val){
    nLNQueueHits = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 838 "../../../EnvironmentStats.ump"
   public void setNPendingLNsProcessed(int val){
    nPendingLNsProcessed = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 845 "../../../EnvironmentStats.ump"
   public void setNMarkedLNsProcessed(int val){
    nMarkedLNsProcessed = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 852 "../../../EnvironmentStats.ump"
   public void setNToBeCleanedLNsProcessed(int val){
    nToBeCleanedLNsProcessed = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 859 "../../../EnvironmentStats.ump"
   public void setNClusterLNsProcessed(int val){
    nClusterLNsProcessed = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 866 "../../../EnvironmentStats.ump"
   public void setNPendingLNsLocked(int val){
    nPendingLNsLocked = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 873 "../../../EnvironmentStats.ump"
   public void setNNodesExplicitlyEvicted(long l){
    nNodesExplicitlyEvicted = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 880 "../../../EnvironmentStats.ump"
   public void setRequiredEvictBytes(long l){
    requiredEvictBytes = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 887 "../../../EnvironmentStats.ump"
   public void setNBINsStripped(long l){
    nBINsStripped = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 894 "../../../EnvironmentStats.ump"
   public void setNNodesScanned(long l){
    nNodesScanned = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 901 "../../../EnvironmentStats.ump"
   public void setNNodesSelected(long l){
    nNodesSelected = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 908 "../../../EnvironmentStats.ump"
   public void setNonEmptyBins(int val){
    nonEmptyBins = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 915 "../../../EnvironmentStats.ump"
   public void setProcessedBins(int val){
    processedBins = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 922 "../../../EnvironmentStats.ump"
   public void setNRepeatFaultReads(long val){
    nRepeatFaultReads = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 929 "../../../EnvironmentStats.ump"
   public void setNTempBufferWrites(long val){
    nTempBufferWrites = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 936 "../../../EnvironmentStats.ump"
   public void setNRepeatIteratorReads(long val){
    nRepeatIteratorReads = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 943 "../../../EnvironmentStats.ump"
   public void setSplitBins(int val){
    splitBins = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 950 "../../../EnvironmentStats.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("splitBins=").append(splitBins).append('\n');
	sb.append("dbClosedBins=").append(dbClosedBins).append('\n');
	sb.append("cursorsBins=").append(cursorsBins).append('\n');
	sb.append("nonEmptyBins=").append(nonEmptyBins).append('\n');
	sb.append("processedBins=").append(processedBins).append('\n');
	sb.append("inCompQueueSize=").append(inCompQueueSize).append('\n');
	sb.append("nEvictPasses=").append(nEvictPasses).append('\n');
	sb.append("nNodesSelected=").append(nNodesSelected).append('\n');
	sb.append("nNodesScanned=").append(nNodesScanned).append('\n');
	sb.append("nNodesExplicitlyEvicted=").append(nNodesExplicitlyEvicted).append('\n');
	sb.append("nBINsStripped=").append(nBINsStripped).append('\n');
	sb.append("requiredEvictBytes=").append(requiredEvictBytes).append('\n');
	sb.append("nCheckpoints=").append(nCheckpoints).append('\n');
	sb.append("lastCheckpointId=").append(lastCheckpointId).append('\n');
	sb.append("nFullINFlush=").append(nFullINFlush).append('\n');
	sb.append("nFullBINFlush=").append(nFullBINFlush).append('\n');
	sb.append("nDeltaINFlush=").append(nDeltaINFlush).append('\n');
	sb.append("lastCheckpointStart=").append(DbLsn.getNoFormatString(lastCheckpointStart)).append('\n');
	sb.append("lastCheckpointEnd=").append(DbLsn.getNoFormatString(lastCheckpointEnd)).append('\n');
	sb.append("cleanerBacklog=").append(cleanerBacklog).append('\n');
	sb.append("nCleanerRuns=").append(nCleanerRuns).append('\n');
	sb.append("nCleanerDeletions=").append(nCleanerDeletions).append('\n');
	sb.append("nINsObsolete=").append(nINsObsolete).append('\n');
	sb.append("nINsCleaned=").append(nINsCleaned).append('\n');
	sb.append("nINsDead=").append(nINsDead).append('\n');
	sb.append("nINsMigrated=").append(nINsMigrated).append('\n');
	sb.append("nLNsObsolete=").append(nLNsObsolete).append('\n');
	sb.append("nLNsCleaned=").append(nLNsCleaned).append('\n');
	sb.append("nLNsDead=").append(nLNsDead).append('\n');
	sb.append("nLNsLocked=").append(nLNsLocked).append('\n');
	sb.append("nLNsMigrated=").append(nLNsMigrated).append('\n');
	sb.append("nLNsMarked=").append(nLNsMarked).append('\n');
	sb.append("nLNQueueHits=").append(nLNQueueHits).append('\n');
	sb.append("nPendingLNsProcessed=").append(nPendingLNsProcessed).append('\n');
	sb.append("nMarkedLNsProcessed=").append(nMarkedLNsProcessed).append('\n');
	sb.append("nToBeCleanedLNsProcessed=").append(nToBeCleanedLNsProcessed).append('\n');
	sb.append("nClusterLNsProcessed=").append(nClusterLNsProcessed).append('\n');
	sb.append("nPendingLNsLocked=").append(nPendingLNsLocked).append('\n');
	sb.append("nCleanerEntriesRead=").append(nCleanerEntriesRead).append('\n');
	sb.append("nNotResident=").append(nNotResident).append('\n');
	sb.append("nCacheMiss=").append(nCacheMiss).append('\n');
	sb.append("nLogBuffers=").append(nLogBuffers).append('\n');
	sb.append("bufferBytes=").append(bufferBytes).append('\n');
	sb.append("cacheDataBytes=").append(cacheDataBytes).append('\n');
	sb.append("cacheTotalBytes=").append(getCacheTotalBytes()).append('\n');
	this.hook61(sb);
	sb.append("nRepeatFaultReads=").append(nRepeatFaultReads).append('\n');
	sb.append("nTempBufferWrite=").append(nTempBufferWrites).append('\n');
	sb.append("nRepeatIteratorReads=").append(nRepeatIteratorReads).append('\n');
	return sb.toString();
  }

  // line 1004 "../../../EnvironmentStats.ump"
   protected void hook60(){
    
  }

  // line 1007 "../../../EnvironmentStats.ump"
   protected void hook61(StringBuffer sb){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../EnvironmentStats.ump"
  private int splitBins ;
// line 17 "../../../EnvironmentStats.ump"
  private int dbClosedBins ;
// line 22 "../../../EnvironmentStats.ump"
  private int cursorsBins ;
// line 27 "../../../EnvironmentStats.ump"
  private int nonEmptyBins ;
// line 32 "../../../EnvironmentStats.ump"
  private int processedBins ;
// line 37 "../../../EnvironmentStats.ump"
  private int inCompQueueSize ;
// line 42 "../../../EnvironmentStats.ump"
  private int nEvictPasses ;
// line 47 "../../../EnvironmentStats.ump"
  private long nNodesSelected ;
// line 52 "../../../EnvironmentStats.ump"
  private long nNodesScanned ;
// line 57 "../../../EnvironmentStats.ump"
  private long nNodesExplicitlyEvicted ;
// line 62 "../../../EnvironmentStats.ump"
  private long nBINsStripped ;
// line 67 "../../../EnvironmentStats.ump"
  private long requiredEvictBytes ;
// line 72 "../../../EnvironmentStats.ump"
  private int nCheckpoints ;
// line 77 "../../../EnvironmentStats.ump"
  private long lastCheckpointId ;
// line 82 "../../../EnvironmentStats.ump"
  private int nFullINFlush ;
// line 87 "../../../EnvironmentStats.ump"
  private int nFullBINFlush ;
// line 92 "../../../EnvironmentStats.ump"
  private int nDeltaINFlush ;
// line 97 "../../../EnvironmentStats.ump"
  private long lastCheckpointStart ;
// line 102 "../../../EnvironmentStats.ump"
  private long lastCheckpointEnd ;
// line 107 "../../../EnvironmentStats.ump"
  private int cleanerBacklog ;
// line 112 "../../../EnvironmentStats.ump"
  private int nCleanerRuns ;
// line 117 "../../../EnvironmentStats.ump"
  private int nCleanerDeletions ;
// line 122 "../../../EnvironmentStats.ump"
  private int nINsObsolete ;
// line 127 "../../../EnvironmentStats.ump"
  private int nINsCleaned ;
// line 132 "../../../EnvironmentStats.ump"
  private int nINsDead ;
// line 137 "../../../EnvironmentStats.ump"
  private int nINsMigrated ;
// line 142 "../../../EnvironmentStats.ump"
  private int nLNsObsolete ;
// line 147 "../../../EnvironmentStats.ump"
  private int nLNsCleaned ;
// line 152 "../../../EnvironmentStats.ump"
  private int nLNsDead ;
// line 157 "../../../EnvironmentStats.ump"
  private int nLNsLocked ;
// line 162 "../../../EnvironmentStats.ump"
  private int nLNsMigrated ;
// line 167 "../../../EnvironmentStats.ump"
  private int nLNsMarked ;
// line 172 "../../../EnvironmentStats.ump"
  private int nLNQueueHits ;
// line 177 "../../../EnvironmentStats.ump"
  private int nPendingLNsProcessed ;
// line 182 "../../../EnvironmentStats.ump"
  private int nMarkedLNsProcessed ;
// line 187 "../../../EnvironmentStats.ump"
  private int nToBeCleanedLNsProcessed ;
// line 192 "../../../EnvironmentStats.ump"
  private int nClusterLNsProcessed ;
// line 197 "../../../EnvironmentStats.ump"
  private int nPendingLNsLocked ;
// line 202 "../../../EnvironmentStats.ump"
  private int nCleanerEntriesRead ;
// line 204 "../../../EnvironmentStats.ump"
  private long cacheDataBytes ;
// line 206 "../../../EnvironmentStats.ump"
  private long nNotResident ;
// line 208 "../../../EnvironmentStats.ump"
  private long nCacheMiss ;
// line 210 "../../../EnvironmentStats.ump"
  private int nLogBuffers ;
// line 212 "../../../EnvironmentStats.ump"
  private long bufferBytes ;
// line 214 "../../../EnvironmentStats.ump"
  private long nRepeatFaultReads ;
// line 216 "../../../EnvironmentStats.ump"
  private long nTempBufferWrites ;
// line 218 "../../../EnvironmentStats.ump"
  private long nRepeatIteratorReads ;

  
}