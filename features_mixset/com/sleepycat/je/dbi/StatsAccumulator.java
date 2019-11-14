/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.TreeWalkerStatsAccumulator;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.DupCountLN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.DBIN;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.DatabaseStats;
import com.sleepycat.je.BtreeStats;
import java.util.Set;
import java.util.HashSet;
import java.io.PrintStream;
import com.sleepycat.je.tree.*;

// line 3 "../../../../Statistics_StatsAccumulator.ump"
// line 3 "../../../../Derivative_Statistics_Verifier_StatsAccumulator.ump"
public class StatsAccumulator implements TreeWalkerStatsAccumulator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StatsAccumulator Attributes
  private PrintStream progressStream;
  private int progressInterval;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StatsAccumulator(PrintStream aProgressStream, int aProgressInterval)
  {
    progressStream = aProgressStream;
    progressInterval = aProgressInterval;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setProgressStream(PrintStream aProgressStream)
  {
    boolean wasSet = false;
    progressStream = aProgressStream;
    wasSet = true;
    return wasSet;
  }

  public boolean setProgressInterval(int aProgressInterval)
  {
    boolean wasSet = false;
    progressInterval = aProgressInterval;
    wasSet = true;
    return wasSet;
  }

  public PrintStream getProgressStream()
  {
    return progressStream;
  }

  public int getProgressInterval()
  {
    return progressInterval;
  }

  public void delete()
  {}

  // line 54 "../../../../Statistics_StatsAccumulator.ump"
  public  StatsAccumulator(PrintStream progressStream, int progressInterval, DatabaseStats useStats){
    this.progressStream = progressStream;
	this.progressInterval = progressInterval;
	insSeenByLevel = new long[MAX_LEVELS];
	binsSeenByLevel = new long[MAX_LEVELS];
	dinsSeenByLevel = new long[MAX_LEVELS];
	dbinsSeenByLevel = new long[MAX_LEVELS];
	this.useStats = useStats;
  }

  // line 64 "../../../../Statistics_StatsAccumulator.ump"
   public void processIN(IN node, Long nid, int level){
    if (inNodeIdsSeen.add(nid)) {
					tallyLevel(level, insSeenByLevel);
					Label363:
verifyNode(node);
	//original(node);
 //this.hook363(node);
	}
  }

  // line 71 "../../../../Statistics_StatsAccumulator.ump"
   public void processBIN(BIN node, Long nid, int level){
    if (binNodeIdsSeen.add(nid)) {
					tallyLevel(level, binsSeenByLevel);
					Label364:
verifyNode(node);
	//original(node);
 //this.hook364(node);
			}
  }

  // line 78 "../../../../Statistics_StatsAccumulator.ump"
   public void processDIN(DIN node, Long nid, int level){
    if (dinNodeIdsSeen.add(nid)) {
					tallyLevel(level, dinsSeenByLevel);
					Label365:
verifyNode(node);
	//original(node);
 //this.hook365(node);
			}
  }

  // line 85 "../../../../Statistics_StatsAccumulator.ump"
   public void processDBIN(DBIN node, Long nid, int level){
    if (dbinNodeIdsSeen.add(nid)) {
					tallyLevel(level, dbinsSeenByLevel);
					Label366:
verifyNode(node);
	//original(node);
 //this.hook366(node);
			}
  }

  // line 92 "../../../../Statistics_StatsAccumulator.ump"
   public void processDupCountLN(DupCountLN node, Long nid){
    dupCountLNsSeen.add(nid);
			Label376: //this.hook367(node);
  }

  // line 97 "../../../../Statistics_StatsAccumulator.ump"
   private void tallyLevel(int levelArg, long [] nodesSeenByLevel){
    int level = levelArg;
	if (level >= IN.DBMAP_LEVEL) {
	    return;
	}
	if (level >= IN.MAIN_LEVEL) {
	    level &= ~IN.MAIN_LEVEL;
	    if (level > mainTreeMaxDepth) {
		mainTreeMaxDepth = level;
	    }
	} else {
	    if (level > duplicateTreeMaxDepth) {
		duplicateTreeMaxDepth = level;
	    }
	}
	nodesSeenByLevel[level]++;
  }

  // line 115 "../../../../Statistics_StatsAccumulator.ump"
   public void incrementLNCount(){
    lnCount++;
	if (progressInterval != 0) {
	    if ((lnCount % progressInterval) == 0) {
		copyToStats(useStats);
		progressStream.println(useStats);
	    }
	}
  }

  // line 125 "../../../../Statistics_StatsAccumulator.ump"
   public void incrementDeletedLNCount(){
    deletedLNCount++;
  }

  // line 129 "../../../../Statistics_StatsAccumulator.ump"
  public Set getINNodeIdsSeen(){
    return inNodeIdsSeen;
  }

  // line 133 "../../../../Statistics_StatsAccumulator.ump"
  public Set getBINNodeIdsSeen(){
    return binNodeIdsSeen;
  }

  // line 137 "../../../../Statistics_StatsAccumulator.ump"
  public Set getDINNodeIdsSeen(){
    return dinNodeIdsSeen;
  }

  // line 141 "../../../../Statistics_StatsAccumulator.ump"
  public Set getDBINNodeIdsSeen(){
    return dbinNodeIdsSeen;
  }

  // line 145 "../../../../Statistics_StatsAccumulator.ump"
  public long[] getINsByLevel(){
    return insSeenByLevel;
  }

  // line 149 "../../../../Statistics_StatsAccumulator.ump"
  public long[] getBINsByLevel(){
    return binsSeenByLevel;
  }

  // line 153 "../../../../Statistics_StatsAccumulator.ump"
  public long[] getDINsByLevel(){
    return dinsSeenByLevel;
  }

  // line 157 "../../../../Statistics_StatsAccumulator.ump"
  public long[] getDBINsByLevel(){
    return dbinsSeenByLevel;
  }

  // line 161 "../../../../Statistics_StatsAccumulator.ump"
  public long getLNCount(){
    return lnCount;
  }

  // line 165 "../../../../Statistics_StatsAccumulator.ump"
  public Set getDupCountLNCount(){
    return dupCountLNsSeen;
  }

  // line 169 "../../../../Statistics_StatsAccumulator.ump"
  public long getDeletedLNCount(){
    return deletedLNCount;
  }

  // line 173 "../../../../Statistics_StatsAccumulator.ump"
  public int getMainTreeMaxDepth(){
    return mainTreeMaxDepth;
  }

  // line 177 "../../../../Statistics_StatsAccumulator.ump"
  public int getDuplicateTreeMaxDepth(){
    return duplicateTreeMaxDepth;
  }

  // line 181 "../../../../Statistics_StatsAccumulator.ump"
  public void copyToStats(DatabaseStats stats){
    BtreeStats bStats = (BtreeStats) stats;
			bStats.setInternalNodeCount(getINNodeIdsSeen().size());
			bStats.setBottomInternalNodeCount(getBINNodeIdsSeen().size());
			bStats.setDuplicateInternalNodeCount(getDINNodeIdsSeen().size());
			bStats.setDuplicateBottomInternalNodeCount(getDBINNodeIdsSeen().size());
			bStats.setLeafNodeCount(getLNCount());
			bStats.setDeletedLeafNodeCount(getDeletedLNCount());
			bStats.setDupCountLeafNodeCount(getDupCountLNCount().size());
			bStats.setMainTreeMaxDepth(getMainTreeMaxDepth());
			bStats.setDuplicateTreeMaxDepth(getDuplicateTreeMaxDepth());
			bStats.setINsByLevel(getINsByLevel());
			bStats.setBINsByLevel(getBINsByLevel());
			bStats.setDINsByLevel(getDINsByLevel());
			bStats.setDBINsByLevel(getDBINsByLevel());
  }

  // line 6 "../../../../Derivative_Statistics_Verifier_StatsAccumulator.ump"
  public void verifyNode(Node node){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "progressInterval" + ":" + getProgressInterval()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "progressStream" + "=" + (getProgressStream() != null ? !getProgressStream().equals(this)  ? getProgressStream().toString().replaceAll("  ","    ") : "this" : "null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 19 "../../../../Statistics_StatsAccumulator.ump"
  private Set inNodeIdsSeen = new HashSet() ;
// line 21 "../../../../Statistics_StatsAccumulator.ump"
  private Set binNodeIdsSeen = new HashSet() ;
// line 23 "../../../../Statistics_StatsAccumulator.ump"
  private Set dinNodeIdsSeen = new HashSet() ;
// line 25 "../../../../Statistics_StatsAccumulator.ump"
  private Set dbinNodeIdsSeen = new HashSet() ;
// line 27 "../../../../Statistics_StatsAccumulator.ump"
  private Set dupCountLNsSeen = new HashSet() ;
// line 29 "../../../../Statistics_StatsAccumulator.ump"
  private long[] insSeenByLevel = null ;
// line 31 "../../../../Statistics_StatsAccumulator.ump"
  private long[] binsSeenByLevel = null ;
// line 33 "../../../../Statistics_StatsAccumulator.ump"
  private long[] dinsSeenByLevel = null ;
// line 35 "../../../../Statistics_StatsAccumulator.ump"
  private long[] dbinsSeenByLevel = null ;
// line 37 "../../../../Statistics_StatsAccumulator.ump"
  private long lnCount = 0 ;
// line 39 "../../../../Statistics_StatsAccumulator.ump"
  private long deletedLNCount = 0 ;
// line 41 "../../../../Statistics_StatsAccumulator.ump"
  private int mainTreeMaxDepth = 0 ;
// line 43 "../../../../Statistics_StatsAccumulator.ump"
  private int duplicateTreeMaxDepth = 0 ;
// line 45 "../../../../Statistics_StatsAccumulator.ump"
  private DatabaseStats useStats ;
// line 51 "../../../../Statistics_StatsAccumulator.ump"
  private static final int MAX_LEVELS = 100 ;

  
}