/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Collections;

// line 3 "../../../../FileSelector.ump"
public class FileSelector
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FileSelector Attributes
  private int dummyVar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileSelector(int aDummyVar)
  {
    dummyVar = aDummyVar;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDummyVar(int aDummyVar)
  {
    boolean wasSet = false;
    dummyVar = aDummyVar;
    wasSet = true;
    return wasSet;
  }

  public int getDummyVar()
  {
    return dummyVar;
  }

  public void delete()
  {}

  // line 37 "../../../../FileSelector.ump"
   public  FileSelector(){
    toBeCleanedFiles = new TreeSet();
			cleanedFiles = new HashSet();
			checkpointedFiles = new HashSet();
			fullyProcessedFiles = new HashSet();
			safeToDeleteFiles = new HashSet();
			pendingLNs = new HashMap();
			//this.hook163();
			Label163:   ;
			lowUtilizationFiles = Collections.EMPTY_SET;
			beingCleanedFiles = new HashSet();
  }


  /**
   * 
   * Returns the best file that qualifies for cleaning, or null if no file qualifies.  This method is not thread safe and should only be called from the cleaner thread.
   * @param forceCleaning is true to always select a file, even if itsutilization is above the minimum utilization threshold.
   * @param calcLowUtilizationFiles whether to recalculate the set of filesthat are below the minimum utilization threshold.
   * @param maxBatchFiles is the maximum number of files to be selected atone time, or zero if there is no limit.
   * @return the next file to be cleaned, or null if no file needs cleaning.
   */
  // line 58 "../../../../FileSelector.ump"
  public Long selectFileForCleaning(UtilizationProfile profile, boolean forceCleaning, boolean calcLowUtilizationFiles, int maxBatchFiles) throws DatabaseException{
    Set newLowUtilizationFiles = calcLowUtilizationFiles ? (new HashSet()) : null;
	while (true) {
	    if (maxBatchFiles > 0) {
		synchronized (this) {
		    if (toBeCleanedFiles.size() >= maxBatchFiles) {
			break;
		    }
		}
	    }
	    Long fileNum = profile.getBestFileForCleaning(this, forceCleaning, newLowUtilizationFiles);
	    if (fileNum == null) {
		break;
	    }
	    synchronized (this) {
		toBeCleanedFiles.add(fileNum);
	    }
	}
	if (newLowUtilizationFiles != null) {
	    lowUtilizationFiles = newLowUtilizationFiles;
	}
	SortedSet availableFiles;
	synchronized (this) {
	    availableFiles = new TreeSet(toBeCleanedFiles);
	}
	Long file = profile.getCheapestFileToClean(availableFiles);
	synchronized (this) {
	    toBeCleanedFiles.remove(file);
	    beingCleanedFiles.add(file);
	}
	return file;
  }


  /**
   * 
   * Returns whether the file is in any stage of the cleaning process.
   */
  // line 94 "../../../../FileSelector.ump"
   synchronized  boolean isFileCleaningInProgress(Long file){
    return toBeCleanedFiles.contains(file) || beingCleanedFiles.contains(file) || cleanedFiles.contains(file)
		|| checkpointedFiles.contains(file) || fullyProcessedFiles.contains(file)
		|| safeToDeleteFiles.contains(file);
  }


  /**
   * 
   * When file cleaning is aborted, move the file back from the being-cleaned set to the to-be-cleaned set.
   */
  // line 103 "../../../../FileSelector.ump"
   synchronized  void putBackFileForCleaning(Long fileNum){
    toBeCleanedFiles.add(fileNum);
	beingCleanedFiles.remove(fileNum);
  }


  /**
   * 
   * When cleaning is complete, move the file from the being-cleaned set to the cleaned set.
   */
  // line 111 "../../../../FileSelector.ump"
   synchronized  void addCleanedFile(Long fileNum){
    cleanedFiles.add(fileNum);
	beingCleanedFiles.remove(fileNum);
  }


  /**
   * 
   * Returns a read-only set of low utilization files that can be accessed without synchronization.
   */
  // line 119 "../../../../FileSelector.ump"
  public Set getLowUtilizationFiles(){
    return lowUtilizationFiles;
  }


  /**
   * 
   * Returns a read-only copy of to-be-cleaned and being-cleaned files that can be accessed without synchronization.
   */
  // line 126 "../../../../FileSelector.ump"
   synchronized  Set getMustBeCleanedFiles(){
    Set set = new HashSet(toBeCleanedFiles);
	set.addAll(beingCleanedFiles);
	return set;
  }


  /**
   * 
   * Returns the number of files waiting to-be-cleaned.
   */
  // line 135 "../../../../FileSelector.ump"
   synchronized  int getBacklog(){
    return toBeCleanedFiles.size();
  }


  /**
   * 
   * Returns a copy of the cleaned and fully-processed files at the time a checkpoint starts.
   */
  // line 142 "../../../../FileSelector.ump"
   synchronized  Set[] getFilesAtCheckpointStart(){
    anyPendingDuringCheckpoint = !pendingLNs.isEmpty();
			Label164:   ; //this.hook164();
			Set[] files = new Set[2];
			files[0] = (cleanedFiles.size() > 0) ? (new HashSet(cleanedFiles)) : null;
			files[1] = (fullyProcessedFiles.size() > 0) ? (new HashSet(fullyProcessedFiles)) : null;
			return (files[0] != null || files[1] != null) ? files : null;
  }


  /**
   * 
   * When a checkpoint is complete, moves the previously cleaned and fully-processed files to the checkpointed and safe-to-delete sets.
   */
  // line 154 "../../../../FileSelector.ump"
   synchronized  void updateFilesAtCheckpointEnd(Set [] files){
    if (files != null) {
	    Set previouslyCleanedFiles = files[0];
	    if (previouslyCleanedFiles != null) {
		if (anyPendingDuringCheckpoint) {
		    checkpointedFiles.addAll(previouslyCleanedFiles);
		} else {
		    safeToDeleteFiles.addAll(previouslyCleanedFiles);
		}
		cleanedFiles.removeAll(previouslyCleanedFiles);
	    }
	    Set previouslyProcessedFiles = files[1];
	    if (previouslyProcessedFiles != null) {
		safeToDeleteFiles.addAll(previouslyProcessedFiles);
		fullyProcessedFiles.removeAll(previouslyProcessedFiles);
	    }
	    updateProcessedFiles();
	}
  }


  /**
   * 
   * Adds the given LN info to the pending LN set.
   */
  // line 177 "../../../../FileSelector.ump"
   synchronized  boolean addPendingLN(LN ln, DatabaseId dbId, byte [] key, byte [] dupKey){
    assert ln != null;
	boolean added = pendingLNs.put(new Long(ln.getNodeId()), new LNInfo(ln, dbId, key, dupKey)) != null;
	anyPendingDuringCheckpoint = true;
	return added;
  }


  /**
   * 
   * Returns an array of LNInfo for LNs that could not be migrated in a prior cleaning attempt, or null if no LNs are pending.
   */
  // line 187 "../../../../FileSelector.ump"
   synchronized  LNInfo[] getPendingLNs(){
    if (pendingLNs.size() > 0) {
	    LNInfo[] lns = new LNInfo[pendingLNs.size()];
	    pendingLNs.values().toArray(lns);
	    return lns;
	} else {
	    return null;
	}
  }


  /**
   * 
   * Removes the LN for the given node ID from the pending LN set.
   */
  // line 200 "../../../../FileSelector.ump"
   synchronized  void removePendingLN(long nodeId){
    pendingLNs.remove(new Long(nodeId));
	updateProcessedFiles();
  }


  /**
   * 
   * Returns a copy of the safe-to-delete files.
   */
  // line 208 "../../../../FileSelector.ump"
   synchronized  Set copySafeToDeleteFiles(){
    if (safeToDeleteFiles.size() == 0) {
	    return null;
	} else {
	    return new HashSet(safeToDeleteFiles);
	}
  }


  /**
   * 
   * Removes file from the safe-to-delete set after the file itself has finally been deleted.
   */
  // line 219 "../../../../FileSelector.ump"
   synchronized  void removeDeletedFile(Long fileNum){
    safeToDeleteFiles.remove(fileNum);
  }


  /**
   * 
   * If there are no pending LNs or DBs outstanding, move the checkpointed files to the fully-processed set.  The check for pending LNs/DBs and the copying of the checkpointed files must be done atomically in a synchronized block.  All methods that call this method are synchronized.
   */
  // line 226 "../../../../FileSelector.ump"
   private void updateProcessedFiles(){
    boolean b = pendingLNs.isEmpty();
			Label165:   ; //b = this.hook165(b);
			if (b) {
					fullyProcessedFiles.addAll(checkpointedFiles);
					checkpointedFiles.clear();
			}
  }


  public String toString()
  {
    return super.toString() + "["+
            "dummyVar" + ":" + getDummyVar()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 16 "../../../../FileSelector.ump"
  private SortedSet toBeCleanedFiles ;
// line 18 "../../../../FileSelector.ump"
  private Set beingCleanedFiles ;
// line 20 "../../../../FileSelector.ump"
  private Set cleanedFiles ;
// line 22 "../../../../FileSelector.ump"
  private Set checkpointedFiles ;
// line 24 "../../../../FileSelector.ump"
  private Set fullyProcessedFiles ;
// line 26 "../../../../FileSelector.ump"
  private Set safeToDeleteFiles ;
// line 28 "../../../../FileSelector.ump"
  private Map pendingLNs ;
// line 30 "../../../../FileSelector.ump"
  private boolean anyPendingDuringCheckpoint ;
// line 32 "../../../../FileSelector.ump"
  private Set lowUtilizationFiles ;

  
}