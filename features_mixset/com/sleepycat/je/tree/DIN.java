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
public class DIN extends IN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DIN(long aNodeId)
  {
    super(aNodeId);
    // line 39 "../../../../DIN.ump"
    dupCountLNRef = new ChildReference();
    	init(null, Key.EMPTY_KEY, 0, 0);
    // END OF UMPLE AFTER INJECTION
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
   * Create a new DIN.
   */
  // line 48 "../../../../DIN.ump"
   public  DIN(DatabaseImpl db, byte [] identifierKey, int capacity, byte [] dupKey, ChildReference dupCountLNRef, int level){
    super(db, identifierKey, capacity, level);
	this.dupKey = dupKey;
	this.dupCountLNRef = dupCountLNRef;
  }

  // line 54 "../../../../DIN.ump"
   protected int generateLevel(DatabaseId dbId, int newLevel){
    return newLevel;
  }


  /**
   * 
   * Create a new DIN.  Need this because we can't call newInstance() without getting a 0 node.
   */
  // line 61 "../../../../DIN.ump"
   protected IN createNewInstance(byte [] identifierKey, int maxEntries, int level){
    return new DIN(getDatabase(), identifierKey, maxEntries, dupKey, dupCountLNRef, level);
  }


  /**
   * 
   * Return the key for this duplicate set.
   */
  // line 68 "../../../../DIN.ump"
   public byte[] getDupKey(){
    return dupKey;
  }


  /**
   * 
   * Get the key (dupe or identifier) in child that is used to locate it in 'this' node.
   */
  // line 75 "../../../../DIN.ump"
   public byte[] getChildKey(IN child) throws DatabaseException{
    return child.getIdentifierKey();
  }

  // line 79 "../../../../DIN.ump"
   public byte[] selectKey(byte [] mainTreeKey, byte [] dupTreeKey){
    return dupTreeKey;
  }


  /**
   * 
   * Return the key for navigating through the duplicate tree.
   */
  // line 86 "../../../../DIN.ump"
   public byte[] getDupTreeKey(){
    return getIdentifierKey();
  }


  /**
   * 
   * Return the key for navigating through the main tree.
   */
  // line 93 "../../../../DIN.ump"
   public byte[] getMainTreeKey(){
    return dupKey;
  }

  // line 97 "../../../../DIN.ump"
   public ChildReference getDupCountLNRef(){
    return dupCountLNRef;
  }

  // line 101 "../../../../DIN.ump"
   public DupCountLN getDupCountLN() throws DatabaseException{
    return (DupCountLN) dupCountLNRef.fetchTarget(getDatabase(), this);
  }


  /**
   * 
   * Assign the Dup Count LN.
   */
  // line 108 "../../../../DIN.ump"
  public void setDupCountLN(ChildReference dupCountLNRef){
    this.dupCountLNRef = dupCountLNRef;
  }


  /**
   * 
   * Assign the Dup Count LN node.  Does not dirty the DIN.
   */
  // line 115 "../../../../DIN.ump"
   public void updateDupCountLN(Node target){
    new DIN_updateDupCountLN(this, target).execute();
  }


  /**
   * 
   * Update Dup Count LN.
   */
  // line 122 "../../../../DIN.ump"
   public void updateDupCountLNRefAndNullTarget(long newLsn){
    new DIN_updateDupCountLNRefAndNullTarget(this, newLsn).execute();
  }


  /**
   * 
   * Update dup count LSN.
   */
  // line 129 "../../../../DIN.ump"
   public void updateDupCountLNRef(long newLsn){
    setDirty(true);
	dupCountLNRef.setLsn(newLsn);
  }


  /**
   * 
   * @return true if this node is a duplicate-bearing node type, falseif otherwise.
   */
  // line 137 "../../../../DIN.ump"
   public boolean containsDuplicates(){
    return true;
  }

  // line 141 "../../../../DIN.ump"
   public boolean isDbRoot(){
    return false;
  }


  /**
   * 
   * Increment or decrement the DupCountLN, log the updated LN, and update the lock result. Preconditions: This DIN is latched and the DupCountLN is write locked. Postconditions: Same as preconditions.
   */
  // line 156 "../../../../DIN.ump"
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

  // line 171 "../../../../DIN.ump"
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

  // line 184 "../../../../DIN.ump"
  public void accumulateStats(TreeWalkerStatsAccumulator acc){
    acc.processDIN(this, new Long(getNodeId()), getLevel());
  }


  /**
   * 
   * @see IN#getLogType
   */
  // line 191 "../../../../DIN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_DIN;
  }


  /**
   * 
   * Handles lazy migration of DupCountLNs prior to logging a DIN.  See BIN.logInternal for more information.
   */
  // line 199 "../../../../DIN.ump"
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
  // line 210 "../../../../DIN.ump"
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
  // line 223 "../../../../DIN.ump"
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
  // line 236 "../../../../DIN.ump"
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
  // line 250 "../../../../DIN.ump"
   protected void dumpLogAdditional(StringBuffer sb){
    super.dumpLogAdditional(sb);
	sb.append(Key.dumpString(dupKey, 0));
	if (dupCountLNRef != null) {
	    dupCountLNRef.dumpLog(sb, true);
	}
  }

  // line 258 "../../../../DIN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 262 "../../../../DIN.ump"
   public String endTag(){
    return END_TAG;
  }


  /**
   * 
   * For unit test support:
   * @return a string that dumps information about this DIN, without
   */
  // line 270 "../../../../DIN.ump"
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

  // line 297 "../../../../DIN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 301 "../../../../DIN.ump"
   public String shortClassName(){
    return "DIN";
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../DIN_static.ump"
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
      _this.dupCountLNRef.setTarget(target);
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
  
  
  
  // line 17 "../../../../DIN_static.ump"
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
          _this.dupCountLNRef.setTarget(null);
          _this.dupCountLNRef.setLsn(newLsn);
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

// line 147 "../../../../DIN.ump"
  public final Comparator getKeyComparator () 
  {
    return getDatabase().getDuplicateComparator();
  }

  
}