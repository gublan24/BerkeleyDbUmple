/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../Cleaner.ump"
public class Cleaner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cleaner Attributes
  private int nBacklogFiles;
  private int nCleanerDeletions;
  private int nINsObsolete;
  private int nINsCleaned;
  private int nINsDead;
  private int nINsMigrated;
  private int nLNsObsolete;
  private int nLNsCleaned;
  private int nLNsDead;
  private int nLNsLocked;
  private int nLNsMigrated;
  private int nLNsMarked;
  private int nLNQueueHits;
  private int nPendingLNsProcessed;
  private int nMarkedLNsProcessed;
  private int nToBeCleanedLNsProcessed;
  private int nClusterLNsProcessed;
  private int nPendingLNsLocked;
  private int nEntriesRead;
  private long nRepeatIteratorReads;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner()
  {
    nBacklogFiles = 0;
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
    nEntriesRead = 0;
    nRepeatIteratorReads = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNBacklogFiles(int aNBacklogFiles)
  {
    boolean wasSet = false;
    nBacklogFiles = aNBacklogFiles;
    wasSet = true;
    return wasSet;
  }

  public boolean setNCleanerDeletions(int aNCleanerDeletions)
  {
    boolean wasSet = false;
    nCleanerDeletions = aNCleanerDeletions;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsObsolete(int aNINsObsolete)
  {
    boolean wasSet = false;
    nINsObsolete = aNINsObsolete;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsCleaned(int aNINsCleaned)
  {
    boolean wasSet = false;
    nINsCleaned = aNINsCleaned;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsDead(int aNINsDead)
  {
    boolean wasSet = false;
    nINsDead = aNINsDead;
    wasSet = true;
    return wasSet;
  }

  public boolean setNINsMigrated(int aNINsMigrated)
  {
    boolean wasSet = false;
    nINsMigrated = aNINsMigrated;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsObsolete(int aNLNsObsolete)
  {
    boolean wasSet = false;
    nLNsObsolete = aNLNsObsolete;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsCleaned(int aNLNsCleaned)
  {
    boolean wasSet = false;
    nLNsCleaned = aNLNsCleaned;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsDead(int aNLNsDead)
  {
    boolean wasSet = false;
    nLNsDead = aNLNsDead;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsLocked(int aNLNsLocked)
  {
    boolean wasSet = false;
    nLNsLocked = aNLNsLocked;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsMigrated(int aNLNsMigrated)
  {
    boolean wasSet = false;
    nLNsMigrated = aNLNsMigrated;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNsMarked(int aNLNsMarked)
  {
    boolean wasSet = false;
    nLNsMarked = aNLNsMarked;
    wasSet = true;
    return wasSet;
  }

  public boolean setNLNQueueHits(int aNLNQueueHits)
  {
    boolean wasSet = false;
    nLNQueueHits = aNLNQueueHits;
    wasSet = true;
    return wasSet;
  }

  public boolean setNPendingLNsProcessed(int aNPendingLNsProcessed)
  {
    boolean wasSet = false;
    nPendingLNsProcessed = aNPendingLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNMarkedLNsProcessed(int aNMarkedLNsProcessed)
  {
    boolean wasSet = false;
    nMarkedLNsProcessed = aNMarkedLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNToBeCleanedLNsProcessed(int aNToBeCleanedLNsProcessed)
  {
    boolean wasSet = false;
    nToBeCleanedLNsProcessed = aNToBeCleanedLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNClusterLNsProcessed(int aNClusterLNsProcessed)
  {
    boolean wasSet = false;
    nClusterLNsProcessed = aNClusterLNsProcessed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNPendingLNsLocked(int aNPendingLNsLocked)
  {
    boolean wasSet = false;
    nPendingLNsLocked = aNPendingLNsLocked;
    wasSet = true;
    return wasSet;
  }

  public boolean setNEntriesRead(int aNEntriesRead)
  {
    boolean wasSet = false;
    nEntriesRead = aNEntriesRead;
    wasSet = true;
    return wasSet;
  }

  public boolean setNRepeatIteratorReads(long aNRepeatIteratorReads)
  {
    boolean wasSet = false;
    nRepeatIteratorReads = aNRepeatIteratorReads;
    wasSet = true;
    return wasSet;
  }

  public int getNBacklogFiles()
  {
    return nBacklogFiles;
  }

  public int getNCleanerDeletions()
  {
    return nCleanerDeletions;
  }

  public int getNINsObsolete()
  {
    return nINsObsolete;
  }

  public int getNINsCleaned()
  {
    return nINsCleaned;
  }

  public int getNINsDead()
  {
    return nINsDead;
  }

  public int getNINsMigrated()
  {
    return nINsMigrated;
  }

  public int getNLNsObsolete()
  {
    return nLNsObsolete;
  }

  public int getNLNsCleaned()
  {
    return nLNsCleaned;
  }

  public int getNLNsDead()
  {
    return nLNsDead;
  }

  public int getNLNsLocked()
  {
    return nLNsLocked;
  }

  public int getNLNsMigrated()
  {
    return nLNsMigrated;
  }

  public int getNLNsMarked()
  {
    return nLNsMarked;
  }

  public int getNLNQueueHits()
  {
    return nLNQueueHits;
  }

  public int getNPendingLNsProcessed()
  {
    return nPendingLNsProcessed;
  }

  public int getNMarkedLNsProcessed()
  {
    return nMarkedLNsProcessed;
  }

  public int getNToBeCleanedLNsProcessed()
  {
    return nToBeCleanedLNsProcessed;
  }

  public int getNClusterLNsProcessed()
  {
    return nClusterLNsProcessed;
  }

  public int getNPendingLNsLocked()
  {
    return nPendingLNsLocked;
  }

  public int getNEntriesRead()
  {
    return nEntriesRead;
  }

  public long getNRepeatIteratorReads()
  {
    return nRepeatIteratorReads;
  }

  public void delete()
  {}


  /**
   * 
   * Load stats.
   */
  // line 51 "../../../../Cleaner.ump"
   public void loadStats(StatsConfig config, EnvironmentStats stat) throws DatabaseException{
    stat.setCleanerBacklog(nBacklogFiles);
	stat.setNCleanerRuns(nCleanerRuns);
	stat.setNCleanerDeletions(nCleanerDeletions);
	stat.setNINsObsolete(nINsObsolete);
	stat.setNINsCleaned(nINsCleaned);
	stat.setNINsDead(nINsDead);
	stat.setNINsMigrated(nINsMigrated);
	stat.setNLNsObsolete(nLNsObsolete);
	stat.setNLNsCleaned(nLNsCleaned);
	stat.setNLNsDead(nLNsDead);
	stat.setNLNsLocked(nLNsLocked);
	stat.setNLNsMigrated(nLNsMigrated);
	stat.setNLNsMarked(nLNsMarked);
	stat.setNLNQueueHits(nLNQueueHits);
	stat.setNPendingLNsProcessed(nPendingLNsProcessed);
	stat.setNMarkedLNsProcessed(nMarkedLNsProcessed);
	stat.setNToBeCleanedLNsProcessed(nToBeCleanedLNsProcessed);
	stat.setNClusterLNsProcessed(nClusterLNsProcessed);
	stat.setNPendingLNsLocked(nPendingLNsLocked);
	stat.setNCleanerEntriesRead(nEntriesRead);
	stat.setNRepeatIteratorReads(nRepeatIteratorReads);
	if (config.getClear()) {
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
	    nEntriesRead = 0;
	    nRepeatIteratorReads = 0;
	}
  }

  // line 97 "../../../../Cleaner.ump"
   protected void hook96() throws DatabaseException{
    nCleanerDeletions++;
	original();
  }


  /**
   * 
   * Update the lowUtilizationFiles and mustBeCleanedFiles fields with new read-only collections, and update the backlog file count.
   */
  // line 105 "../../../../Cleaner.ump"
   public void updateReadOnlyFileCollections(){
    original();
	nBacklogFiles = fileSelector.getBacklog();
  }

  // line 110 "../../../../Cleaner.ump"
   protected void hook97() throws DatabaseException{
    nPendingLNsProcessed++;
	original();
  }

  // line 115 "../../../../Cleaner.ump"
   protected void hook98() throws DatabaseException{
    nLNsDead++;
	original();
  }

  // line 120 "../../../../Cleaner.ump"
   protected void hook99() throws DatabaseException{
    nPendingLNsLocked++;
	original();
  }

  // line 125 "../../../../Cleaner.ump"
   protected void hook100() throws DatabaseException{
    nLNsDead++;
	original();
  }

  // line 130 "../../../../Cleaner.ump"
   protected void hook101(){
    nMarkedLNsProcessed++;
	original();
  }

  // line 135 "../../../../Cleaner.ump"
   protected void hook102(){
    nToBeCleanedLNsProcessed++;
	original();
  }

  // line 140 "../../../../Cleaner.ump"
   protected void hook103(){
    nClusterLNsProcessed++;
	original();
  }

  // line 145 "../../../../Cleaner.ump"
   protected void hook104() throws DatabaseException{
    nLNsMigrated++;
	original();
  }

  // line 150 "../../../../Cleaner.ump"
   protected void hook105(boolean wasCleaned) throws DatabaseException{
    if (wasCleaned) {
	    nLNsDead++;
	}
	original(wasCleaned);
  }

  // line 157 "../../../../Cleaner.ump"
   protected void hook106(boolean wasCleaned) throws DatabaseException{
    if (wasCleaned) {
	    nLNsLocked++;
	}
	original(wasCleaned);
  }

  // line 164 "../../../../Cleaner.ump"
   protected void hook107(boolean wasCleaned) throws DatabaseException{
    if (wasCleaned) {
	    nLNsDead++;
	}
	original(wasCleaned);
  }

  // line 171 "../../../../Cleaner.ump"
   protected void hook108(boolean wasCleaned) throws DatabaseException{
    if (wasCleaned) {
	    nLNsDead++;
	}
	original(wasCleaned);
  }

  // line 178 "../../../../Cleaner.ump"
   protected void hook109() throws DatabaseException{
    nLNsMigrated++;
	original();
  }

  // line 183 "../../../../Cleaner.ump"
   protected void hook110(boolean wasCleaned) throws DatabaseException{
    if (wasCleaned) {
	    nLNsLocked++;
	}
	original(wasCleaned);
  }

  // line 190 "../../../../Cleaner.ump"
   protected void hook111(boolean wasCleaned) throws DatabaseException{
    if (wasCleaned) {
	    nLNsDead++;
	}
	original(wasCleaned);
  }


  public String toString()
  {
    return super.toString() + "["+
            "nBacklogFiles" + ":" + getNBacklogFiles()+ "," +
            "nCleanerDeletions" + ":" + getNCleanerDeletions()+ "," +
            "nINsObsolete" + ":" + getNINsObsolete()+ "," +
            "nINsCleaned" + ":" + getNINsCleaned()+ "," +
            "nINsDead" + ":" + getNINsDead()+ "," +
            "nINsMigrated" + ":" + getNINsMigrated()+ "," +
            "nLNsObsolete" + ":" + getNLNsObsolete()+ "," +
            "nLNsCleaned" + ":" + getNLNsCleaned()+ "," +
            "nLNsDead" + ":" + getNLNsDead()+ "," +
            "nLNsLocked" + ":" + getNLNsLocked()+ "," +
            "nLNsMigrated" + ":" + getNLNsMigrated()+ "," +
            "nLNsMarked" + ":" + getNLNsMarked()+ "," +
            "nLNQueueHits" + ":" + getNLNQueueHits()+ "," +
            "nPendingLNsProcessed" + ":" + getNPendingLNsProcessed()+ "," +
            "nMarkedLNsProcessed" + ":" + getNMarkedLNsProcessed()+ "," +
            "nToBeCleanedLNsProcessed" + ":" + getNToBeCleanedLNsProcessed()+ "," +
            "nClusterLNsProcessed" + ":" + getNClusterLNsProcessed()+ "," +
            "nPendingLNsLocked" + ":" + getNPendingLNsLocked()+ "," +
            "nEntriesRead" + ":" + getNEntriesRead()+ "," +
            "nRepeatIteratorReads" + ":" + getNRepeatIteratorReads()+ "]";
  }
}