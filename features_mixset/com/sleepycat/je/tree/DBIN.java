/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.dbi.CursorImpl;
import com.sleepycat.je.DatabaseException;
import java.util.Comparator;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../DBIN.ump"
public class DBIN extends BIN implements LoggableObject
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DBIN(long aNodeId)
  {
    super(aNodeId);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }


  /**
   * public DBIN() {super();}
   */
  // line 32 "../../../../DBIN.ump"
   public  DBIN(DatabaseImpl db, byte [] identifierKey, int maxEntriesPerNode, byte [] dupKey, int level){
    super(db, identifierKey, maxEntriesPerNode, level);
	this.dupKey = dupKey;
  }


  /**
   * 
   * Create a new DBIN. Need this because we can't call newInstance() without getting a 0 node.
   */
  // line 40 "../../../../DBIN.ump"
   protected IN createNewInstance(byte [] identifierKey, int maxEntries, int level){
    return new DBIN(getDatabase(), identifierKey, maxEntries, dupKey, level);
  }

  // line 44 "../../../../DBIN.ump"
   protected int generateLevel(DatabaseId dbId, int newLevel){
    return newLevel;
  }


  /**
   * 
   * Return the key for this duplicate set.
   */
  // line 58 "../../../../DBIN.ump"
   public byte[] getDupKey(){
    return dupKey;
  }


  /**
   * 
   * Get the key (dupe or identifier) in child that is used to locate it in 'this' node.
   */
  // line 65 "../../../../DBIN.ump"
   public byte[] getChildKey(IN child) throws DatabaseException{
    return child.getIdentifierKey();
  }

  // line 69 "../../../../DBIN.ump"
   public byte[] selectKey(byte [] mainTreeKey, byte [] dupTreeKey){
    return dupTreeKey;
  }


  /**
   * 
   * Return the key for navigating through the duplicate tree.
   */
  // line 76 "../../../../DBIN.ump"
   public byte[] getDupTreeKey(){
    return getIdentifierKey();
  }


  /**
   * 
   * Return the key for navigating through the main tree.
   */
  // line 83 "../../../../DBIN.ump"
   public byte[] getMainTreeKey(){
    return dupKey;
  }


  /**
   * 
   * @return true if this node is a duplicate-bearing node type, false ifotherwise.
   */
  // line 90 "../../../../DBIN.ump"
   public boolean containsDuplicates(){
    return true;
  }


  /**
   * 
   * @return the log entry type to use for bin delta log entries.
   */
  // line 97 "../../../../DBIN.ump"
  public LogEntryType getBINDeltaType(){
    return LogEntryType.LOG_DUP_BIN_DELTA;
  }

  // line 101 "../../../../DBIN.ump"
   public BINReference createReference(){
    return new DBINReference(getNodeId(), getDatabase().getId(), getIdentifierKey(), dupKey);
  }

  // line 105 "../../../../DBIN.ump"
   protected boolean canBeAncestor(boolean targetContainsDuplicates){
    return false;
  }


  /**
   * 
   * @Override
   */
  // line 112 "../../../../DBIN.ump"
  public boolean hasNonLNChildren(){
    return false;
  }


  /**
   * 
   * The following four methods access the correct fields in a cursor depending on whether "this" is a BIN or DBIN. For BIN's, the CursorImpl.index and CursorImpl.bin fields should be used. For DBIN's, the CursorImpl.dupIndex and CursorImpl.dupBin fields should be used.
   */
  // line 119 "../../../../DBIN.ump"
  public BIN getCursorBIN(CursorImpl cursor){
    return cursor.getDupBIN();
  }

  // line 123 "../../../../DBIN.ump"
  public BIN getCursorBINToBeRemoved(CursorImpl cursor){
    return cursor.getDupBINToBeRemoved();
  }

  // line 127 "../../../../DBIN.ump"
  public int getCursorIndex(CursorImpl cursor){
    return cursor.getDupIndex();
  }

  // line 131 "../../../../DBIN.ump"
  public void setCursorBIN(CursorImpl cursor, BIN bin){
    cursor.setDupBIN((DBIN) bin);
  }

  // line 135 "../../../../DBIN.ump"
  public void setCursorIndex(CursorImpl cursor, int index){
    cursor.setDupIndex(index);
  }

  // line 139 "../../../../DBIN.ump"
  public boolean matchLNByNodeId(TreeLocation location, long nodeId) throws DatabaseException{
    for (int i = 0; i < getNEntries(); i++) {
	    LN ln = (LN) fetchTarget(i);
	    if (ln != null) {
		if (ln.getNodeId() == nodeId) {
		    location.bin = this;
		    location.index = i;
		    location.lnKey = getKey(i);
		    location.childLsn = getLsn(i);
		    return true;
		}
	    }
	}
	return false;
  }

  // line 155 "../../../../DBIN.ump"
  public void accumulateStats(TreeWalkerStatsAccumulator acc){
    acc.processDBIN(this, new Long(getNodeId()), getLevel());
  }

  // line 159 "../../../../DBIN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 163 "../../../../DBIN.ump"
   public String endTag(){
    return END_TAG;
  }


  /**
   * 
   * For unit test support:
   * @return a string that dumps information about this IN, without
   */
  // line 171 "../../../../DBIN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	sb.append(TreeUtils.indent(nSpaces));
	sb.append(beginTag());
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<dupkey>");
	sb.append(dupKey == null ? "" : Key.dumpString(dupKey, 0));
	sb.append("</dupkey>");
	sb.append('\n');
	sb.append(super.dumpString(nSpaces, false));
	sb.append(TreeUtils.indent(nSpaces));
	sb.append(endTag());
	return sb.toString();
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 190 "../../../../DBIN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_DBIN;
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 197 "../../../../DBIN.ump"
   public int getLogSize(){
    int size = super.getLogSize();
	size += LogUtils.getByteArrayLogSize(dupKey);
	return size;
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 206 "../../../../DBIN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	LogUtils.writeByteArray(logBuffer, dupKey);
  }


  /**
   * 
   * @see BIN#readFromLog
   */
  // line 214 "../../../../DBIN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	dupKey = LogUtils.readByteArray(itemBuffer);
  }


  /**
   * 
   * DBINS need to dump their dup key
   */
  // line 222 "../../../../DBIN.ump"
   protected void dumpLogAdditional(StringBuffer sb){
    super.dumpLogAdditional(sb);
	sb.append(Key.dumpString(dupKey, 0));
  }

  // line 227 "../../../../DBIN.ump"
   public String shortClassName(){
    return "DBIN";
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 20 "../../../../DBIN.ump"
  private static final String BEGIN_TAG = "<dbin>" ;
// line 22 "../../../../DBIN.ump"
  private static final String END_TAG = "</dbin>" ;
// line 27 "../../../../DBIN.ump"
  private byte[] dupKey ;

// line 50 "../../../../DBIN.ump"
  public final Comparator getKeyComparator () 
  {
    return getDatabase().getDuplicateComparator();
  }

  
}