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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileSelector()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 35 "../../../../FileSelector.ump"
  public  FileSelector(){
    toBeCleanedFiles = new TreeSet();
	cleanedFiles = new HashSet();
	checkpointedFiles = new HashSet();
	fullyProcessedFiles = new HashSet();
	safeToDeleteFiles = new HashSet();
	pendingLNs = new HashMap();
	this.hook163();
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
  // line 55 "../../../../FileSelector.ump"
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
   * Returns a read-only set of low utilization files that can be accessed without synchronization.
   */
  // line 116 "../../../../FileSelector.ump"
  public Set getLowUtilizationFiles(){
    return lowUtilizationFiles;
  }


  /**
   * 
   * If there are no pending LNs or DBs outstanding, move the checkpointed files to the fully-processed set.  The check for pending LNs/DBs and the copying of the checkpointed files must be done atomically in a synchronized block.  All methods that call this method are synchronized.
   */
  // line 223 "../../../../FileSelector.ump"
   private void updateProcessedFiles(){
    boolean b = pendingLNs.isEmpty();
	b = this.hook165(b);
	if (b) {
	    fullyProcessedFiles.addAll(checkpointedFiles);
	    checkpointedFiles.clear();
	}
  }

  // line 232 "../../../../FileSelector.ump"
   protected void hook163(){
    
  }

  // line 235 "../../../../FileSelector.ump"
   protected void hook164(){
    
  }

  // line 238 "../../../../FileSelector.ump"
   protected boolean hook165(boolean b){
    return b;
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

// line 90 "../../../../FileSelector.ump"
  synchronized boolean isFileCleaningInProgress (Long file) 
  {
    return toBeCleanedFiles.contains(file) || beingCleanedFiles.contains(file) || cleanedFiles.contains(file)
		|| checkpointedFiles.contains(file) || fullyProcessedFiles.contains(file)
		|| safeToDeleteFiles.contains(file);
  }

// line 99 "../../../../FileSelector.ump"
  synchronized void putBackFileForCleaning (Long fileNum) 
  {
    toBeCleanedFiles.add(fileNum);
	beingCleanedFiles.remove(fileNum);
  }

// line 107 "../../../../FileSelector.ump"
  synchronized void addCleanedFile (Long fileNum) 
  {
    cleanedFiles.add(fileNum);
	beingCleanedFiles.remove(fileNum);
  }

// line 122 "../../../../FileSelector.ump"
  synchronized Set getMustBeCleanedFiles () 
  {
    Set set = new HashSet(toBeCleanedFiles);
	set.addAll(beingCleanedFiles);
	return set;
  }

// line 131 "../../../../FileSelector.ump"
  synchronized int getBacklog () 
  {
    return toBeCleanedFiles.size();
  }

// line 138 "../../../../FileSelector.ump"
  synchronized Set[] getFilesAtCheckpointStart () 
  {
    anyPendingDuringCheckpoint = !pendingLNs.isEmpty();
	this.hook164();
	Set[] files = new Set[2];
	files[0] = (cleanedFiles.size() > 0) ? (new HashSet(cleanedFiles)) : null;
	files[1] = (fullyProcessedFiles.size() > 0) ? (new HashSet(fullyProcessedFiles)) : null;
	return (files[0] != null || files[1] != null) ? files : null;
  }

// line 150 "../../../../FileSelector.ump"
  synchronized void updateFilesAtCheckpointEnd (Set[] files) 
  {
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

// line 173 "../../../../FileSelector.ump"
  synchronized boolean addPendingLN (LN ln, DatabaseId dbId, byte[] key, byte[] dupKey) 
  {
    assert ln != null;
	boolean added = pendingLNs.put(new Long(ln.getNodeId()), new LNInfo(ln, dbId, key, dupKey)) != null;
	anyPendingDuringCheckpoint = true;
	return added;
  }

// line 183 "../../../../FileSelector.ump"
  synchronized LNInfo[] getPendingLNs () 
  {
    if (pendingLNs.size() > 0) {
	    LNInfo[] lns = new LNInfo[pendingLNs.size()];
	    pendingLNs.values().toArray(lns);
	    return lns;
	} else {
	    return null;
	}
  }

// line 196 "../../../../FileSelector.ump"
  synchronized void removePendingLN (long nodeId) 
  {
    pendingLNs.remove(new Long(nodeId));
	updateProcessedFiles();
  }

// line 204 "../../../../FileSelector.ump"
  synchronized Set copySafeToDeleteFiles () 
  {
    if (safeToDeleteFiles.size() == 0) {
	    return null;
	} else {
	    return new HashSet(safeToDeleteFiles);
	}
  }

// line 215 "../../../../FileSelector.ump"
  synchronized void removeDeletedFile (Long fileNum) 
  {
    safeToDeleteFiles.remove(fileNum);
  }

  
}