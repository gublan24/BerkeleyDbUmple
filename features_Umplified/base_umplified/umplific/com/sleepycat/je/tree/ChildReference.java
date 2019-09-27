/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogFileNotFoundException;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../ChildReference.ump"
public class ChildReference implements LogWritable,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ChildReference()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Construct an empty child reference, for reading from the log.
   */
  // line 40 "../../../../ChildReference.ump"
  public  ChildReference(){
    init(null, Key.EMPTY_KEY, DbLsn.NULL_LSN, 0);
  }


  /**
   * 
   * Construct a ChildReference for inserting a new entry.
   */
  // line 47 "../../../../ChildReference.ump"
   public  ChildReference(Node target, byte [] key, long lsn){
    init(target, key, lsn, DIRTY_BIT);
  }


  /**
   * 
   * Construct a ChildReference for inserting an existing entry.
   */
  // line 54 "../../../../ChildReference.ump"
   public  ChildReference(Node target, byte [] key, long lsn, byte existingState){
    init(target, key, lsn, existingState | DIRTY_BIT);
  }

  // line 58 "../../../../ChildReference.ump"
   private void init(Node target, byte [] key, long lsn, int state){
    this.target = target;
	this.key = key;
	this.lsn = lsn;
	this.state = (byte) state;
  }


  /**
   * 
   * Return the key for this ChildReference.
   */
  // line 68 "../../../../ChildReference.ump"
   public byte[] getKey(){
    return key;
  }


  /**
   * 
   * Set the key for this ChildReference.
   */
  // line 75 "../../../../ChildReference.ump"
   public void setKey(byte [] key){
    this.key = key;
	state |= DIRTY_BIT;
  }


  /**
   * 
   * Fetch the target object that this ChildReference refers to.  If the object is already in VM, then just return the reference to it.  If the object is not in VM, then read the object from the log.  If the object has been faulted in and the in arg is supplied, then the total memory size cache in the IN is invalidated.
   * @param database The database that this ChildReference resides in.
   * @param in The IN that this ChildReference lives in.  Ifthe target is fetched (i.e. it is null on entry), then the total in memory count is invalidated in the IN. May be null.  For example, the root is a ChildReference and there is no parent IN when the rootIN is fetched in.
   * @return the Node object representing the target node in the tree, ornull if there is no target of this ChildReference, or null if a pendingDelete or knownDeleted entry has been cleaned.
   */
  // line 86 "../../../../ChildReference.ump"
   public Node fetchTarget(DatabaseImpl database, IN in) throws DatabaseException{
    if (target == null) {
	    if (lsn == DbLsn.NULL_LSN) {
		if (!isKnownDeleted()) {
		    throw new DatabaseException(IN.makeFetchErrorMsg("NULL_LSN without KnownDeleted", in, lsn, state));
		}
	    } else {
		try {
		    EnvironmentImpl env = database.getDbEnvironment();
		    Node node = (Node) env.getLogManager().get(lsn);
		    node.postFetchInit(database, lsn);
		    target = node;
		    this.hook613(in);
		} catch (LogFileNotFoundException LNFE) {
		    if (!isKnownDeleted() && !isPendingDeleted()) {
			throw new DatabaseException(IN.makeFetchErrorMsg(LNFE.toString(), in, lsn, state), LNFE);
		    }
		} catch (Exception e) {
		    throw new DatabaseException(IN.makeFetchErrorMsg(e.toString(), in, lsn, state), e);
		}
	    }
	}
	return target;
  }

  // line 111 "../../../../ChildReference.ump"
  public byte getState(){
    return state;
  }


  /**
   * 
   * Return the target for this ChildReference.
   */
  // line 118 "../../../../ChildReference.ump"
   public Node getTarget(){
    return target;
  }


  /**
   * 
   * Sets the target for this ChildReference. No need to make dirty, that state only applies to key and LSN.
   */
  // line 125 "../../../../ChildReference.ump"
   public void setTarget(Node target){
    this.target = target;
  }


  /**
   * 
   * Clear the target for this ChildReference. No need to make dirty, that state only applies to key and LSN. This method is public because it's safe and used by RecoveryManager. This can't corrupt the tree.
   */
  // line 132 "../../../../ChildReference.ump"
   public void clearTarget(){
    this.target = null;
  }


  /**
   * 
   * Return the LSN for this ChildReference.
   * @return the LSN for this ChildReference.
   */
  // line 140 "../../../../ChildReference.ump"
   public long getLsn(){
    return lsn;
  }


  /**
   * 
   * Sets the target LSN for this ChildReference.
   * @param the target LSN.
   */
  // line 148 "../../../../ChildReference.ump"
   public void setLsn(long lsn){
    this.lsn = lsn;
	state |= DIRTY_BIT;
  }


  /**
   * 
   * @return true if the entry has been deleted, although the transaction theperformed the deletion may not be committed.
   */
  // line 156 "../../../../ChildReference.ump"
   private boolean isPendingDeleted(){
    return ((state & PENDING_DELETED_BIT) != 0);
  }


  /**
   * 
   * @return true if entry is deleted for sure.
   */
  // line 163 "../../../../ChildReference.ump"
   public boolean isKnownDeleted(){
    return ((state & KNOWN_DELETED_BIT) != 0);
  }


  /**
   * 
   * @return true if the object is dirty.
   */
  // line 170 "../../../../ChildReference.ump"
   private boolean isDirty(){
    return ((state & DIRTY_BIT) != 0);
  }


  /**
   * 
   * Get the entry migrate status.
   */
  // line 177 "../../../../ChildReference.ump"
   public boolean getMigrate(){
    return (state & MIGRATE_BIT) != 0;
  }


  /**
   * 
   * Set the entry migrate status.
   */
  // line 184 "../../../../ChildReference.ump"
   public void setMigrate(boolean migrate){
    if (migrate) {
	    state |= MIGRATE_BIT;
	} else {
	    state &= CLEAR_MIGRATE_BIT;
	}
  }


  /**
   * 
   * @see LogWritable#getLogSize
   */
  // line 195 "../../../../ChildReference.ump"
   public int getLogSize(){
    return LogUtils.getByteArrayLogSize(key) + LogUtils.getLongLogSize() + 1;
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 202 "../../../../ChildReference.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeByteArray(logBuffer, key);
	assert lsn != DbLsn.NULL_LSN;
	LogUtils.writeLong(logBuffer, lsn);
	logBuffer.put(state);
	state &= CLEAR_DIRTY_BIT;
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 213 "../../../../ChildReference.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion){
    key = LogUtils.readByteArray(itemBuffer);
	lsn = LogUtils.readLong(itemBuffer);
	state = itemBuffer.get();
	state &= CLEAR_DIRTY_BIT;
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 223 "../../../../ChildReference.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<ref knownDeleted=\"").append(isKnownDeleted());
	sb.append("\" pendingDeleted=\"").append(isPendingDeleted());
	sb.append("\">");
	sb.append(Key.dumpString(key, 0));
	sb.append(DbLsn.toString(lsn));
	sb.append("</ref>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 235 "../../../../ChildReference.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 242 "../../../../ChildReference.ump"
   public long getTransactionId(){
    return 0;
  }

  // line 246 "../../../../ChildReference.ump"
  public String dumpString(int nspaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	if (lsn == DbLsn.NULL_LSN) {
	    sb.append(TreeUtils.indent(nspaces));
	    sb.append("<lsn/>");
	} else {
	    sb.append(DbLsn.dumpString(lsn, nspaces));
	}
	sb.append('\n');
	if (key == null) {
	    sb.append(TreeUtils.indent(nspaces));
	    sb.append("<key/>");
	} else {
	    sb.append(Key.dumpString(key, nspaces));
	}
	sb.append('\n');
	if (target == null) {
	    sb.append(TreeUtils.indent(nspaces));
	    sb.append("<target/>");
	} else {
	    sb.append(target.dumpString(nspaces, true));
	}
	sb.append('\n');
	sb.append(TreeUtils.indent(nspaces));
	sb.append("<knownDeleted val=\"");
	sb.append(isKnownDeleted()).append("\"/>");
	sb.append("<pendingDeleted val=\"");
	sb.append(isPendingDeleted()).append("\"/>");
	sb.append("<dirty val=\"").append(isDirty()).append("\"/>");
	return sb.toString();
  }

  // line 278 "../../../../ChildReference.ump"
   public String toString(){
    return dumpString(0, false);
  }

  // line 282 "../../../../ChildReference.ump"
   protected void hook613(IN in) throws DatabaseException,LogFileNotFoundException,Exception{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 16 "../../../../ChildReference.ump"
  private Node target ;
// line 18 "../../../../ChildReference.ump"
  private long lsn ;
// line 20 "../../../../ChildReference.ump"
  private byte[] key ;
// line 22 "../../../../ChildReference.ump"
  private byte state ;
// line 24 "../../../../ChildReference.ump"
  private static final byte KNOWN_DELETED_BIT = 0x1 ;
// line 26 "../../../../ChildReference.ump"
  private static final byte DIRTY_BIT = 0x2 ;
// line 28 "../../../../ChildReference.ump"
  private static final byte CLEAR_DIRTY_BIT = ~0x2 ;
// line 30 "../../../../ChildReference.ump"
  private static final byte MIGRATE_BIT = 0x4 ;
// line 32 "../../../../ChildReference.ump"
  private static final byte CLEAR_MIGRATE_BIT = ~0x4 ;
// line 34 "../../../../ChildReference.ump"
  private static final byte PENDING_DELETED_BIT = 0x8 ;

  
}