/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.INList;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../Node.ump"
public abstract class Node implements LoggableObject,LogReadable,LogWritable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Node()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 27 "../../../../Node.ump"
   public static  synchronized  void setLastNodeId(long id){
    if (lastAllocatedId < id) {
	    lastAllocatedId = id;
	}
  }


  /**
   * 
   * Disallow use
   */
  // line 36 "../../../../Node.ump"
   private  Node(){
    
  }


  /**
   * 
   * Create a new node, assigning it the next available node id.
   */
  // line 42 "../../../../Node.ump"
   protected  Node(boolean init){
    if (init) {
	    nodeId = getNextNodeId();
	}
  }


  /**
   * 
   * Increment and return the next usable id. Must be synchronized.
   */
  // line 51 "../../../../Node.ump"
   public static  synchronized  long getNextNodeId(){
    return ++lastAllocatedId;
  }


  /**
   * 
   * Get the latest id, for checkpointing.
   */
  // line 58 "../../../../Node.ump"
   public static  synchronized  long getLastId(){
    return lastAllocatedId;
  }


  /**
   * 
   * Initialize a node that has been faulted in from the log
   */
  // line 65 "../../../../Node.ump"
   public void postFetchInit(DatabaseImpl db, long sourceLsn) throws DatabaseException{
    
  }

  // line 68 "../../../../Node.ump"
   public long getNodeId(){
    return nodeId;
  }

  // line 72 "../../../../Node.ump"
  public void setNodeId(long nid){
    nodeId = nid;
  }

  // line 76 "../../../../Node.ump"
   public void verify(byte [] maxKey) throws DatabaseException{
    
  }


  /**
   * 
   * @return true if this node is a duplicate-bearing node type, falseif otherwise.
   */
  // line 82 "../../../../Node.ump"
   public boolean containsDuplicates(){
    return false;
  }


  /**
   * 
   * Cover for LN's and just return 0 since they'll always be at the bottom of the tree.
   */
  // line 89 "../../../../Node.ump"
  public int getLevel(){
    return 0;
  }

  // line 93 "../../../../Node.ump"
  public boolean matchLNByNodeId(TreeLocation location, long nodeId) throws DatabaseException{
    throw new DatabaseException("matchLNByNodeId called on non DIN/DBIN");
  }


  /**
   * 
   * Add yourself to the in memory list if you're a type of node that  should belong.
   */
  public abstract void rebuildINList(INList inList) throws DatabaseException;


  /**
   * 
   * Remove yourself from the in memory list if you're a type of node that  is put there.
   */
  public abstract void accountForSubtreeRemoval(INList inList, UtilizationTracker tracker) throws DatabaseException;


  /**
   * 
   * @return true if you're part of a deletable subtree.
   */
  public abstract boolean isValidForDelete() throws DatabaseException;


  /**
   * 
   * Return the approximate size of this node in memory, if this size should be included in it's parents memory accounting.  For example, all INs return 0, because they are accounted for  individually. LNs must return a count, they're not counted on the INList.
   */
  // line 125 "../../../../Node.ump"
   protected long getMemorySizeIncludedByParent(){
    return 0;
  }


  /**
   * 
   * Default toString method at the root of the tree.
   */
  // line 132 "../../../../Node.ump"
   public String toString(){
    return this.dumpString(0, true);
  }

  // line 136 "../../../../Node.ump"
   private String beginTag(){
    return BEGIN_TAG;
  }

  // line 140 "../../../../Node.ump"
   private String endTag(){
    return END_TAG;
  }

  // line 144 "../../../../Node.ump"
   public void dump(int nSpaces){
    System.out.print(dumpString(nSpaces, true));
  }

  // line 148 "../../../../Node.ump"
  public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer self = new StringBuffer();
	self.append(TreeUtils.indent(nSpaces));
	if (dumpTags) {
	    self.append(beginTag());
	}
	self.append(nodeId);
	if (dumpTags) {
	    self.append(endTag());
	}
	return self.toString();
  }

  // line 161 "../../../../Node.ump"
   public String shortDescription(){
    return "<" + getType() + "/" + getNodeId();
  }

  // line 165 "../../../../Node.ump"
   public String getType(){
    return getClass().getName();
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
   public abstract LogEntryType getLogType();


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchBy default, nodes can be marshalled outside the log write latch.
   */
  // line 177 "../../../../Node.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 184 "../../../../Node.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 191 "../../../../Node.ump"
   public void postLogWork(long justLoggedLsn) throws DatabaseException{
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 197 "../../../../Node.ump"
   public int getLogSize(){
    return LogUtils.LONG_BYTES;
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 204 "../../../../Node.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeLong(logBuffer, nodeId);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 211 "../../../../Node.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    nodeId = LogUtils.readLong(itemBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 218 "../../../../Node.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append(BEGIN_TAG);
	sb.append(nodeId);
	sb.append(END_TAG);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 18 "../../../../Node.ump"
  private static long lastAllocatedId = 0 ;
// line 20 "../../../../Node.ump"
  private static final String BEGIN_TAG = "<node>" ;
// line 22 "../../../../Node.ump"
  private static final String END_TAG = "</node>" ;
// line 24 "../../../../Node.ump"
  private long nodeId ;
// line 114 "../../../../Node.ump"
  abstract protected boolean isSoughtNode(long nid, boolean updateGeneration) throws DatabaseException ;
// line 119 "../../../../Node.ump"
  abstract protected boolean canBeAncestor(boolean targetContainsDuplicates) ;

  
}