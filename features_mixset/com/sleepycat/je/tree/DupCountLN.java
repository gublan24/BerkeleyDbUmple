/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.MemoryBudget;
import java.nio.ByteBuffer;

// line 3 "../../../../DupCountLN.ump"
// line 3 "../../../../MemoryBudget_DupCountLN.ump"
// line 3 "../../../../Statistics_DupCountLN.ump"
public class DupCountLN extends LN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DupCountLN Attributes
  private int dupCount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DupCountLN(long aNodeId)
  {
    super(aNodeId);
    dupCount = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDupCount(int aDupCount)
  {
    boolean wasSet = false;
    dupCount = aDupCount;
    wasSet = true;
    return wasSet;
  }

  public int getDupCount()
  {
    return dupCount;
  }

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * Create a new DupCountLn to hold a new DIN.
   */
  // line 22 "../../../../DupCountLN.ump"
   public  DupCountLN(int count){
    super(new byte[0]);
	this.dupCount = count;
  }

  // line 39 "../../../../DupCountLN.ump"
   public int incDupCount(){
    dupCount++;
	assert dupCount >= 0;
	return dupCount;
  }

  // line 45 "../../../../DupCountLN.ump"
   public int decDupCount(){
    dupCount--;
	assert dupCount >= 0;
	return dupCount;
  }


  /**
   * void setDupCount(int dupCount) {	this.dupCount = dupCount;}
   * 
   * @return true if this node is a duplicate-bearing node type, falseif otherwise.
   */
  // line 56 "../../../../DupCountLN.ump"
   public boolean containsDuplicates(){
    return true;
  }

  // line 60 "../../../../DupCountLN.ump"
   public boolean isDeleted(){
    return false;
  }

  // line 64 "../../../../DupCountLN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 68 "../../../../DupCountLN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 72 "../../../../DupCountLN.ump"
   public String endTag(){
    return END_TAG;
  }

  // line 76 "../../../../DupCountLN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	if (dumpTags) {
	    sb.append(TreeUtils.indent(nSpaces));
	    sb.append(beginTag());
	    sb.append('\n');
	}
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<count v=\"").append(dupCount).append("\"/>").append('\n');
	sb.append(super.dumpString(nSpaces, false));
	if (dumpTags) {
	    sb.append(TreeUtils.indent(nSpaces));
	    sb.append(endTag());
	}
	return sb.toString();
  }


  /**
   * 
   * Log type for transactional entries.
   */
  // line 96 "../../../../DupCountLN.ump"
   protected LogEntryType getTransactionalLogType(){
    return LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL;
  }


  /**
   * 
   * @see LN#getLogType
   */
  // line 103 "../../../../DupCountLN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_DUPCOUNTLN;
  }


  /**
   * 
   * @see LN#getLogSize
   */
  // line 110 "../../../../DupCountLN.ump"
   public int getLogSize(){
    return super.getLogSize() + LogUtils.INT_BYTES;
  }


  /**
   * 
   * @see LN#writeToLog
   */
  // line 117 "../../../../DupCountLN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	LogUtils.writeInt(logBuffer, dupCount);
  }


  /**
   * 
   * @see LN#readFromLog
   */
  // line 125 "../../../../DupCountLN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	dupCount = LogUtils.readInt(itemBuffer);
  }


  /**
   * 
   * Dump additional fields
   */
  // line 133 "../../../../DupCountLN.ump"
   protected void dumpLogAdditional(StringBuffer sb, boolean verbose){
    super.dumpLogAdditional(sb, verbose);
	sb.append("<count v=\"").append(dupCount).append("\"/>");
  }


  /**
   * 
   * Compute the approximate size of this node in memory for evictor invocation purposes.
   */
  // line 9 "../../../../MemoryBudget_DupCountLN.ump"
   public long getMemorySizeIncludedByParent(){
    return MemoryBudget.DUPCOUNTLN_OVERHEAD;
  }

  // line 6 "../../../../Statistics_DupCountLN.ump"
   public void accumulateStats(TreeWalkerStatsAccumulator acc){
    acc.processDupCountLN(this, new Long(getNodeId()));
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../DupCountLN.ump"
  private static final String BEGIN_TAG = "<dupCountLN>" ;
// line 14 "../../../../DupCountLN.ump"
  private static final String END_TAG = "</dupCountLN>" ;

  
}