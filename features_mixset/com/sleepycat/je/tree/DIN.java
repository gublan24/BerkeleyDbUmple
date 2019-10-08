/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.txn.LockResult;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.cleaner.Cleaner;
import com.sleepycat.je.DatabaseException;
import java.util.Comparator;
import java.nio.ByteBuffer;

// line 3 "../../../../DIN.ump"
// line 3 "../../../../DIN_static.ump"
// line 3 "../../../../MemoryBudget_DIN.ump"
// line 3 "../../../../MemoryBudget_DIN_inner.ump"
public class DIN extends IN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DIN()
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
   * Create an empty DIN, with no node id, to be filled in from the log.
   */
  // line 39 "../../../../DIN.ump"
   public  DIN(){
    super();
	dupCountLNRef = new ChildReference();
	init(null, Key.EMPTY_KEY, 0, 0);
  }


  /**
   * 
   * Create a new DIN.
   */
  // line 49 "../../../../DIN.ump"
   public  DIN(DatabaseImpl db, byte [] identifierKey, int capacity, byte [] dupKey, ChildReference dupCountLNRef, int level){
    super(db, identifierKey, capacity, level);
	this.dupKey = dupKey;
	this.dupCountLNRef = dupCountLNRef;
  }

  // line 55 "../../../../DIN.ump"
   protected int generateLevel(DatabaseId dbId, int newLevel){
    return newLevel;
  }


  /**
   * 
   * Create a new DIN.  Need this because we can't call newInstance() without getting a 0 node.
   */
  // line 62 "../../../../DIN.ump"
   protected IN createNewInstance(byte [] identifierKey, int maxEntries, int level){
    return new DIN(getDatabase(), identifierKey, maxEntries, dupKey, dupCountLNRef, level);
  }


  /**
   * 
   * Return the key for this duplicate set.
   */
  // line 69 "../../../../DIN.ump"
   public byte[] getDupKey(){
    return dupKey;
  }


  /**
   * 
   * Get the key (dupe or identifier) in child that is used to locate it in 'this' node.
   */
  // line 76 "../../../../DIN.ump"
   public byte[] getChildKey(IN child) throws DatabaseException{
    return child.getIdentifierKey();
  }

  // line 80 "../../../../DIN.ump"
   public byte[] selectKey(byte [] mainTreeKey, byte [] dupTreeKey){
    return dupTreeKey;
  }


  /**
   * 
   * Return the key for navigating through the duplicate tree.
   */
  // line 87 "../../../../DIN.ump"
   public byte[] getDupTreeKey(){
    return getIdentifierKey();
  }


  /**
   * 
   * Return the key for navigating through the main tree.
   */
  // line 94 "../../../../DIN.ump"
   public byte[] getMainTreeKey(){
    return dupKey;
  }

  // line 98 "../../../../DIN.ump"
   public ChildReference getDupCountLNRef(){
    return dupCountLNRef;
  }

  // line 102 "../../../../DIN.ump"
   public DupCountLN getDupCountLN() throws DatabaseException{
    return (DupCountLN) dupCountLNRef.fetchTarget(getDatabase(), this);
  }


  /**
   * 
   * Assign the Dup Count LN.
   */
  // line 109 "../../../../DIN.ump"
  public void setDupCountLN(ChildReference dupCountLNRef){
    // line 35 "../../../../MemoryBudget_DIN.ump"
    updateMemorySize(this.dupCountLNRef, dupCountLNRef);
    	    //original(dupCountLNRef);
    // END OF UMPLE BEFORE INJECTION
    this.dupCountLNRef = dupCountLNRef;
  }


  /**
   * 
   * Assign the Dup Count LN node.  Does not dirty the DIN.
   */
  // line 116 "../../../../DIN.ump"
   public void updateDupCountLN(Node target){
    new DIN_updateDupCountLN(this, target).execute();
  }


  /**
   * 
   * Update Dup Count LN.
   */
  // line 123 "../../../../DIN.ump"
   public void updateDupCountLNRefAndNullTarget(long newLsn){
    new DIN_updateDupCountLNRefAndNullTarget(this, newLsn).execute();
  }


  /**
   * 
   * Update dup count LSN.
   */
  // line 130 "../../../../DIN.ump"
   public void updateDupCountLNRef(long newLsn){
    setDirty(true);
	dupCountLNRef.setLsn(newLsn);
  }


  /**
   * 
   * @return true if this node is a duplicate-bearing node type, falseif otherwise.
   */
  // line 138 "../../../../DIN.ump"
   public boolean containsDuplicates(){
    return true;
  }

  // line 142 "../../../../DIN.ump"
   public boolean isDbRoot(){
    return false;
  }


  /**
   * 
   * Increment or decrement the DupCountLN, log the updated LN, and update the lock result. Preconditions: This DIN is latched and the DupCountLN is write locked. Postconditions: Same as preconditions.
   */
  // line 157 "../../../../DIN.ump"
   public void incrementDuplicateCount(LockResult lockResult, byte [] key, Locker locker, boolean increment) throws DatabaseException{
    long oldLsn = dupCountLNRef.getLsn();
	lockResult.setAbortLsn(oldLsn, dupCountLNRef.isKnownDeleted());
	DupCountLN dupCountLN = getDupCountLN();
	if (increment) {
	    dupCountLN.incDupCount();
	} else {
	    dupCountLN.decDupCount();
	    assert dupCountLN.getDupCount() >= 0;
	}
	DatabaseImpl db = getDatabase();
	long newCountLSN = dupCountLN.log(db.getDbEnvironment(), db.getId(), key, oldLsn, locker);
	updateDupCountLNRef(newCountLSN);
  }

  // line 172 "../../../../DIN.ump"
  public boolean matchLNByNodeId(TreeLocation location, long nodeId) throws DatabaseException{
    for (int i = 0; i < getNEntries(); i++) {
	    Node n = fetchTarget(i);
	    if (n != null) {
		boolean ret = n.matchLNByNodeId(location, nodeId);
		if (ret) {
		    return true;
		}
	    }
	}
	return false;
  }

  // line 185 "../../../../DIN.ump"
  public void accumulateStats(TreeWalkerStatsAccumulator acc){
    acc.processDIN(this, new Long(getNodeId()), getLevel());
  }


  /**
   * 
   * @see IN#getLogType
   */
  // line 192 "../../../../DIN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_DIN;
  }


  /**
   * 
   * Handles lazy migration of DupCountLNs prior to logging a DIN.  See BIN.logInternal for more information.
   */
  // line 200 "../../../../DIN.ump"
   protected long logInternal(LogManager logManager, boolean allowDeltas, boolean isProvisional, boolean proactiveMigration, IN parent) throws DatabaseException{
    if (dupCountLNRef != null) {
	    Cleaner cleaner = getDatabase().getDbEnvironment().getCleaner();
	    cleaner.lazyMigrateDupCountLN(this, dupCountLNRef, proactiveMigration);
	}
	return super.logInternal(logManager, allowDeltas, isProvisional, proactiveMigration, parent);
  }


  /**
   * 
   * @see IN#getLogSize
   */
  // line 211 "../../../../DIN.ump"
   public int getLogSize(){
    int size = super.getLogSize();
	size += LogUtils.getByteArrayLogSize(dupKey);
	size += LogUtils.getBooleanLogSize();
	if (dupCountLNRef != null) {
	    size += dupCountLNRef.getLogSize();
	}
	return size;
  }


  /**
   * 
   * @see IN#writeToLog
   */
  // line 224 "../../../../DIN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	LogUtils.writeByteArray(logBuffer, dupKey);
	boolean dupCountLNRefExists = (dupCountLNRef != null);
	LogUtils.writeBoolean(logBuffer, dupCountLNRefExists);
	if (dupCountLNRefExists) {
	    dupCountLNRef.writeToLog(logBuffer);
	}
  }


  /**
   * 
   * @see IN#readFromLog
   */
  // line 237 "../../../../DIN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	dupKey = LogUtils.readByteArray(itemBuffer);
	boolean dupCountLNRefExists = LogUtils.readBoolean(itemBuffer);
	if (dupCountLNRefExists) {
	    dupCountLNRef.readFromLog(itemBuffer, entryTypeVersion);
	} else {
	    dupCountLNRef = null;
	}
  }


  /**
   * 
   * DINS need to dump their dup key
   */
  // line 251 "../../../../DIN.ump"
   protected void dumpLogAdditional(StringBuffer sb){
    super.dumpLogAdditional(sb);
	sb.append(Key.dumpString(dupKey, 0));
	if (dupCountLNRef != null) {
	    dupCountLNRef.dumpLog(sb, true);
	}
  }

  // line 259 "../../../../DIN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 263 "../../../../DIN.ump"
   public String endTag(){
    return END_TAG;
  }


  /**
   * 
   * For unit test support:
   * @return a string that dumps information about this DIN, without
   */
  // line 271 "../../../../DIN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	if (dumpTags) {
	    sb.append(TreeUtils.indent(nSpaces));
	    sb.append(beginTag());
	    sb.append('\n');
	}
	sb.append(TreeUtils.indent(nSpaces + 2));
	sb.append("<dupkey>");
	sb.append(dupKey == null ? "" : Key.dumpString(dupKey, 0));
	sb.append("</dupkey>");
	sb.append('\n');
	if (dupCountLNRef == null) {
	    sb.append(TreeUtils.indent(nSpaces + 2));
	    sb.append("<dupCountLN/>");
	} else {
	    sb.append(dupCountLNRef.dumpString(nSpaces + 4, true));
	}
	sb.append('\n');
	sb.append(super.dumpString(nSpaces, false));
	if (dumpTags) {
	    sb.append(TreeUtils.indent(nSpaces));
	    sb.append(endTag());
	}
	return sb.toString();
  }

  // line 298 "../../../../DIN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 302 "../../../../DIN.ump"
   public String shortClassName(){
    return "DIN";
  }


  /**
   * 
   * Count up the memory usage attributable to this node alone. LNs children are counted by their BIN/DIN parents, but INs are not counted by  their parents because they are resident on the IN list.
   */
  // line 9 "../../../../MemoryBudget_DIN.ump"
   protected long computeMemorySize(){
    long size = super.computeMemorySize();
    	if (dupCountLNRef != null) {
	      size += getEntryInMemorySize(dupCountLNRef.getKey(), dupCountLNRef.getTarget());
	    }
	    return size;
  }

  // line 17 "../../../../MemoryBudget_DIN.ump"
   public static  long computeOverhead(DbConfigManager configManager) throws DatabaseException{
    return MemoryBudget.DIN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }

  // line 21 "../../../../MemoryBudget_DIN.ump"
   protected long getMemoryOverhead(MemoryBudget mb){
    return mb.getDINOverhead();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 4 "../../../../DIN_static.ump"
  // line 15 "../../../../MemoryBudget_DIN_inner.ump"
  public static class DIN_updateDupCountLN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DIN_updateDupCountLN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DIN_static.ump"
    public  DIN_updateDupCountLN(DIN _this, Node target){
      this._this=_this;
          this.target=target;
    }
  
    // line 10 "../../../../DIN_static.ump"
    public void execute(){
      // line 17 "../../../../MemoryBudget_DIN_inner.ump"
      oldSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
      // END OF UMPLE BEFORE INJECTION
      _this.dupCountLNRef.setTarget(target);
      // line 21 "../../../../MemoryBudget_DIN_inner.ump"
      newSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
              _this.updateMemorySize(oldSize,newSize);
      // END OF UMPLE AFTER INJECTION
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 12 "../../../../DIN_static.ump"
    protected DIN _this ;
  // line 13 "../../../../DIN_static.ump"
    protected Node target ;
  // line 14 "../../../../DIN_static.ump"
    protected long oldSize ;
  // line 15 "../../../../DIN_static.ump"
    protected long newSize ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 17 "../../../../DIN_static.ump"
  // line 4 "../../../../MemoryBudget_DIN_inner.ump"
  public static class DIN_updateDupCountLNRefAndNullTarget
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DIN_updateDupCountLNRefAndNullTarget()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 19 "../../../../DIN_static.ump"
    public  DIN_updateDupCountLNRefAndNullTarget(DIN _this, long newLsn){
      this._this=_this;
          this.newLsn=newLsn;
    }
  
    // line 23 "../../../../DIN_static.ump"
    public void execute(){
      _this.setDirty(true);
          //this.hook614();
          Label614:
  oldSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
          //original();
  
          _this.dupCountLNRef.setTarget(null);
          _this.dupCountLNRef.setLsn(newLsn);
      // line 6 "../../../../MemoryBudget_DIN_inner.ump"
      //original();
              newSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
              _this.updateMemorySize(oldSize,newSize);
      // END OF UMPLE AFTER INJECTION
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 29 "../../../../DIN_static.ump"
    protected DIN _this ;
  // line 30 "../../../../DIN_static.ump"
    protected long newLsn ;
  // line 31 "../../../../DIN_static.ump"
    protected long oldSize ;
  // line 32 "../../../../DIN_static.ump"
    protected long newSize ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 21 "../../../../DIN.ump"
  private static final String BEGIN_TAG = "<din>" ;
// line 23 "../../../../DIN.ump"
  private static final String END_TAG = "</din>" ;
// line 28 "../../../../DIN.ump"
  private byte[] dupKey ;
// line 33 "../../../../DIN.ump"
  private ChildReference dupCountLNRef ;

// line 148 "../../../../DIN.ump"
  public final Comparator getKeyComparator () 
  {
    return getDatabase().getDuplicateComparator();
  }

  
}