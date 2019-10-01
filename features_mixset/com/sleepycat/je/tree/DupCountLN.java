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
public class DupCountLN extends LN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DupCountLN()
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
   * Create a new DupCountLn to hold a new DIN.
   */
  // line 22 "../../../../DupCountLN.ump"
   public  DupCountLN(int count){
    super(new byte[0]);
	this.dupCount = count;
  }


  /**
   * 
   * Create an empty DupCountLN, to be filled in from the log.
   */
  // line 30 "../../../../DupCountLN.ump"
   public  DupCountLN(){
    super();
	dupCount = 0;
  }

  // line 35 "../../../../DupCountLN.ump"
   public int getDupCount(){
    return dupCount;
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

  // line 51 "../../../../DupCountLN.ump"
  public void setDupCount(int dupCount){
    this.dupCount = dupCount;
  }


  /**
   * 
   * @return true if this node is a duplicate-bearing node type, falseif otherwise.
   */
  // line 58 "../../../../DupCountLN.ump"
   public boolean containsDuplicates(){
    return true;
  }

  // line 62 "../../../../DupCountLN.ump"
   public boolean isDeleted(){
    return false;
  }

  // line 66 "../../../../DupCountLN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 70 "../../../../DupCountLN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 74 "../../../../DupCountLN.ump"
   public String endTag(){
    return END_TAG;
  }

  // line 78 "../../../../DupCountLN.ump"
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
  // line 98 "../../../../DupCountLN.ump"
   protected LogEntryType getTransactionalLogType(){
    return LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL;
  }


  /**
   * 
   * @see LN#getLogType
   */
  // line 105 "../../../../DupCountLN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_DUPCOUNTLN;
  }


  /**
   * 
   * @see LN#getLogSize
   */
  // line 112 "../../../../DupCountLN.ump"
   public int getLogSize(){
    return super.getLogSize() + LogUtils.INT_BYTES;
  }


  /**
   * 
   * @see LN#writeToLog
   */
  // line 119 "../../../../DupCountLN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	LogUtils.writeInt(logBuffer, dupCount);
  }


  /**
   * 
   * @see LN#readFromLog
   */
  // line 127 "../../../../DupCountLN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	dupCount = LogUtils.readInt(itemBuffer);
  }


  /**
   * 
   * Dump additional fields
   */
  // line 135 "../../../../DupCountLN.ump"
   protected void dumpLogAdditional(StringBuffer sb, boolean verbose){
    super.dumpLogAdditional(sb, verbose);
	sb.append("<count v=\"").append(dupCount).append("\"/>");
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../DupCountLN.ump"
  private static final String BEGIN_TAG = "<dupCountLN>" ;
// line 14 "../../../../DupCountLN.ump"
  private static final String END_TAG = "</dupCountLN>" ;
// line 16 "../../../../DupCountLN.ump"
  private int dupCount ;

  
}