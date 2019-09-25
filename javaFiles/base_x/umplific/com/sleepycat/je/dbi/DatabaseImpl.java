/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.TestHook;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.CmdUtil;
import com.sleepycat.je.txn.ThreadLocker;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.TreeWalkerStatsAccumulator;
import com.sleepycat.je.tree.TreeUtils;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.SortedLSNTreeWalker.TreeNodeProcessor;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.PreloadStatus;
import com.sleepycat.je.PreloadStats;
import com.sleepycat.je.PreloadConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.Cursor;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;
import java.nio.ByteBuffer;
import java.io.PrintStream;
import com.sleepycat.je.log.*;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../../DatabaseImpl.ump"
public class DatabaseImpl implements LogWritable,LogReadable,Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DatabaseImpl Attributes
  private Tree tree;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseImpl(Tree aTree)
  {
    tree = aTree;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTree(Tree aTree)
  {
    boolean wasSet = false;
    tree = aTree;
    wasSet = true;
    return wasSet;
  }

  public Tree getTree()
  {
    return tree;
  }

  public void delete()
  {}


  /**
   * 
   * Create a database object for a new database.
   */
  // line 95 "../../../../DatabaseImpl.ump"
   public  DatabaseImpl(String dbName, DatabaseId id, EnvironmentImpl envImpl, DatabaseConfig dbConfig) throws DatabaseException{
    this.id = id;
	this.envImpl = envImpl;
	this.btreeComparator = dbConfig.getBtreeComparator();
	this.duplicateComparator = dbConfig.getDuplicateComparator();
	duplicatesAllowed = dbConfig.getSortedDuplicates();
	transactional = dbConfig.getTransactional();
	maxMainTreeEntriesPerNode = dbConfig.getNodeMaxEntries();
	maxDupTreeEntriesPerNode = dbConfig.getNodeMaxDupTreeEntries();
	initDefaultSettings();
	this.hook288();
	tree = new Tree(this);
	referringHandles = Collections.synchronizedSet(new HashSet());
	eofNodeId = Node.getNextNodeId();
	debugDatabaseName = dbName;
  }


  /**
   * 
   * Create an empty database object for initialization from the log. Note that the rest of the initialization comes from readFromLog(), except for the debugDatabaseName, which is set by the caller.
   */
  // line 115 "../../../../DatabaseImpl.ump"
   public  DatabaseImpl() throws DatabaseException{
    id = new DatabaseId();
	envImpl = null;
	this.hook289();
	tree = new Tree();
	referringHandles = Collections.synchronizedSet(new HashSet());
	eofNodeId = Node.getNextNodeId();
  }

  // line 124 "../../../../DatabaseImpl.ump"
   public void setDebugDatabaseName(String debugName){
    debugDatabaseName = debugName;
  }

  // line 128 "../../../../DatabaseImpl.ump"
   public String getDebugName(){
    return debugDatabaseName;
  }

  // line 132 "../../../../DatabaseImpl.ump"
   public void setPendingDeletedHook(TestHook hook){
    pendingDeletedHook = hook;
  }


  /**
   * 
   * Initialize configuration settings when creating a new instance or after reading an instance from the log. The envImpl field must be set before calling this method.
   */
  // line 139 "../../../../DatabaseImpl.ump"
   private void initDefaultSettings() throws DatabaseException{
    DbConfigManager configMgr = envImpl.getConfigManager();
	binDeltaPercent = configMgr.getInt(EnvironmentParams.BIN_DELTA_PERCENT);
	binMaxDeltas = configMgr.getInt(EnvironmentParams.BIN_MAX_DELTAS);
	if (maxMainTreeEntriesPerNode == 0) {
	    maxMainTreeEntriesPerNode = configMgr.getInt(EnvironmentParams.NODE_MAX);
	}
	if (maxDupTreeEntriesPerNode == 0) {
	    maxDupTreeEntriesPerNode = configMgr.getInt(EnvironmentParams.NODE_MAX_DUPTREE);
	}
  }


  /**
   * 
   * Clone. For now just pass off to the super class for a field-by-field copy.
   */
  // line 154 "../../../../DatabaseImpl.ump"
   public Object clone() throws CloneNotSupportedException{
    return super.clone();
  }


  /**
   * 
   * @return the database id.
   */
  // line 172 "../../../../DatabaseImpl.ump"
   public DatabaseId getId(){
    return id;
  }

  // line 176 "../../../../DatabaseImpl.ump"
  public void setId(DatabaseId id){
    this.id = id;
  }

  // line 180 "../../../../DatabaseImpl.ump"
   public long getEofNodeId(){
    return eofNodeId;
  }


  /**
   * 
   * @return true if this database is transactional.
   */
  // line 187 "../../../../DatabaseImpl.ump"
   public boolean isTransactional(){
    return transactional;
  }


  /**
   * 
   * Sets the transactional property for the first opened handle.
   */
  // line 194 "../../../../DatabaseImpl.ump"
   public void setTransactional(boolean transactional){
    this.transactional = transactional;
  }


  /**
   * 
   * @return true if duplicates are allowed in this database.
   */
  // line 201 "../../../../DatabaseImpl.ump"
   public boolean getSortedDuplicates(){
    return duplicatesAllowed;
  }

  // line 205 "../../../../DatabaseImpl.ump"
   public int getNodeMaxEntries(){
    return maxMainTreeEntriesPerNode;
  }

  // line 209 "../../../../DatabaseImpl.ump"
   public int getNodeMaxDupTreeEntries(){
    return maxDupTreeEntriesPerNode;
  }


  /**
   * 
   * Set the duplicate comparison function for this database.
   * @param duplicateComparator -The Duplicate Comparison function.
   */
  // line 217 "../../../../DatabaseImpl.ump"
   public void setDuplicateComparator(Comparator duplicateComparator){
    this.duplicateComparator = duplicateComparator;
  }


  /**
   * 
   * Set the btree comparison function for this database.
   * @param btreeComparator -The btree Comparison function.
   */
  // line 225 "../../../../DatabaseImpl.ump"
   public void setBtreeComparator(Comparator btreeComparator){
    this.btreeComparator = btreeComparator;
  }


  /**
   * 
   * @return the btree Comparator object.
   */
  // line 232 "../../../../DatabaseImpl.ump"
   public Comparator getBtreeComparator(){
    return btreeComparator;
  }


  /**
   * 
   * @return the duplicate Comparator object.
   */
  // line 239 "../../../../DatabaseImpl.ump"
   public Comparator getDuplicateComparator(){
    return duplicateComparator;
  }


  /**
   * 
   * Set the db environment during recovery, after instantiating the database from the log
   */
  // line 246 "../../../../DatabaseImpl.ump"
   public void setEnvironmentImpl(EnvironmentImpl envImpl) throws DatabaseException{
    this.envImpl = envImpl;
	initDefaultSettings();
	tree.setDatabase(this);
  }


  /**
   * 
   * @return the database environment.
   */
  // line 255 "../../../../DatabaseImpl.ump"
   public EnvironmentImpl getDbEnvironment(){
    return envImpl;
  }


  /**
   * 
   * Returns whether one or more handles are open.
   */
  // line 262 "../../../../DatabaseImpl.ump"
   public boolean hasOpenHandles(){
    return referringHandles.size() > 0;
  }


  /**
   * 
   * Add a referring handle
   */
  // line 269 "../../../../DatabaseImpl.ump"
   public void addReferringHandle(Database db){
    referringHandles.add(db);
  }


  /**
   * 
   * Decrement the reference count.
   */
  // line 276 "../../../../DatabaseImpl.ump"
   public void removeReferringHandle(Database db){
    referringHandles.remove(db);
  }


  /**
   * 
   * For this secondary database return the primary that it is associated with, or null if not associated with any primary. Note that not all handles need be associated with a primary.
   */
  // line 290 "../../../../DatabaseImpl.ump"
   public Database findPrimaryDatabase() throws DatabaseException{
    for (Iterator i = referringHandles.iterator(); i.hasNext();) {
	    Object obj = i.next();
	    if (obj instanceof SecondaryDatabase) {
		return ((SecondaryDatabase) obj).getPrimaryDatabase();
	    }
	}
	return null;
  }

  // line 300 "../../../../DatabaseImpl.ump"
   public String getName() throws DatabaseException{
    return envImpl.getDbMapTree().getDbName(id);
  }


  /**
   * 
   * Return the count of nodes in the database. Used for truncate, perhaps should be made available through other means? Database should be quiescent.
   */
  // line 307 "../../../../DatabaseImpl.ump"
  public long countRecords() throws DatabaseException{
    LNCounter lnCounter = new LNCounter();
	SortedLSNTreeWalker walker = new SortedLSNTreeWalker(this, false, false, tree.getRootLsn(), lnCounter);
	walker.walk();
	return lnCounter.getCount();
  }

  // line 315 "../../../../DatabaseImpl.ump"
   private boolean walkDatabaseTree(TreeWalkerStatsAccumulator statsAcc, PrintStream out, boolean verbose) throws DatabaseException{
    boolean ok = true;
	Locker locker = new ThreadLocker(envImpl);
	Cursor cursor = null;
	CursorImpl impl = null;
	try {
	    EnvironmentImpl.incThreadLocalReferenceCount();
	    cursor = DbInternal.newCursor(this, locker, null);
	    impl = DbInternal.getCursorImpl(cursor);
	    tree.setTreeStatsAccumulator(statsAcc);
	    impl.setTreeStatsAccumulator(statsAcc);
	    DatabaseEntry foundData = new DatabaseEntry();
	    DatabaseEntry key = new DatabaseEntry();
	    OperationStatus status = DbInternal.position(cursor, key, foundData, LockMode.READ_UNCOMMITTED, true);
	    while (status == OperationStatus.SUCCESS) {
		try {
		    status = DbInternal.retrieveNext(cursor, key, foundData, LockMode.READ_UNCOMMITTED, GetMode.NEXT);
		} catch (DatabaseException DBE) {
		    ok = false;
		    if (DbInternal.advanceCursor(cursor, key, foundData)) {
			if (verbose) {
			    out.println("Error encountered (continuing):");
			    out.println(DBE);
			    printErrorRecord(out, key, foundData);
			}
		    } else {
			throw DBE;
		    }
		}
	    }
	} finally {
	    if (impl != null) {
		impl.setTreeStatsAccumulator(null);
	    }
	    tree.setTreeStatsAccumulator(null);
	    EnvironmentImpl.decThreadLocalReferenceCount();
	    if (cursor != null) {
		cursor.close();
	    }
	}
	return ok;
  }


  /**
   * 
   * Prints the key and data, if available, for a BIN entry that could not be read/verified. Uses the same format as DbDump and prints both the hex and printable versions of the entries.
   */
  // line 361 "../../../../DatabaseImpl.ump"
   private void printErrorRecord(PrintStream out, DatabaseEntry key, DatabaseEntry data){
    byte[] bytes = key.getData();
	StringBuffer sb = new StringBuffer("Error Key ");
	if (bytes == null) {
	    sb.append("UNKNOWN");
	} else {
	    CmdUtil.formatEntry(sb, bytes, false);
	    sb.append(' ');
	    CmdUtil.formatEntry(sb, bytes, true);
	}
	out.println(sb);
	bytes = data.getData();
	sb = new StringBuffer("Error Data ");
	if (bytes == null) {
	    sb.append("UNKNOWN");
	} else {
	    CmdUtil.formatEntry(sb, bytes, false);
	    sb.append(' ');
	    CmdUtil.formatEntry(sb, bytes, true);
	}
	out.println(sb);
  }


  /**
   * 
   * Preload the cache, using up to maxBytes bytes or maxMillsecs msec.
   */
  // line 387 "../../../../DatabaseImpl.ump"
   public PreloadStats preload(PreloadConfig config) throws DatabaseException{
    return new DatabaseImpl_preload(this, config).execute();
  }

  // line 391 "../../../../DatabaseImpl.ump"
   public String dumpString(int nSpaces){
    StringBuffer sb = new StringBuffer();
	sb.append(TreeUtils.indent(nSpaces));
	sb.append("<database id=\"");
	sb.append(id.toString());
	sb.append("\"");
	if (btreeComparator != null) {
	    sb.append(" btc=\"");
	    sb.append(serializeComparator(btreeComparator));
	    sb.append("\"");
	}
	if (duplicateComparator != null) {
	    sb.append(" dupc=\"");
	    sb.append(serializeComparator(duplicateComparator));
	    sb.append("\"");
	}
	sb.append("/>");
	return sb.toString();
  }


  /**
   * 
   * @see LogWritable#getLogSize
   */
  // line 414 "../../../../DatabaseImpl.ump"
   public int getLogSize(){
    return id.getLogSize() + tree.getLogSize() + LogUtils.getBooleanLogSize()
		+ LogUtils.getStringLogSize(serializeComparator(btreeComparator))
		+ LogUtils.getStringLogSize(serializeComparator(duplicateComparator)) + (LogUtils.getIntLogSize() * 2);
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 423 "../../../../DatabaseImpl.ump"
   public void writeToLog(ByteBuffer logBuffer){
    id.writeToLog(logBuffer);
	tree.writeToLog(logBuffer);
	LogUtils.writeBoolean(logBuffer, duplicatesAllowed);
	LogUtils.writeString(logBuffer, serializeComparator(btreeComparator));
	LogUtils.writeString(logBuffer, serializeComparator(duplicateComparator));
	LogUtils.writeInt(logBuffer, maxMainTreeEntriesPerNode);
	LogUtils.writeInt(logBuffer, maxDupTreeEntriesPerNode);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 436 "../../../../DatabaseImpl.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    id.readFromLog(itemBuffer, entryTypeVersion);
	tree.readFromLog(itemBuffer, entryTypeVersion);
	duplicatesAllowed = LogUtils.readBoolean(itemBuffer);
	btreeComparatorName = LogUtils.readString(itemBuffer);
	duplicateComparatorName = LogUtils.readString(itemBuffer);
	try {
	    if (!EnvironmentImpl.getNoComparators()) {
		if (btreeComparatorName.length() != 0) {
		    Class btreeComparatorClass = Class.forName(btreeComparatorName);
		    btreeComparator = instantiateComparator(btreeComparatorClass, "Btree");
		}
		if (duplicateComparatorName.length() != 0) {
		    Class duplicateComparatorClass = Class.forName(duplicateComparatorName);
		    duplicateComparator = instantiateComparator(duplicateComparatorClass, "Duplicate");
		}
	    }
	} catch (ClassNotFoundException CNFE) {
	    throw new LogException("couldn't instantiate class comparator", CNFE);
	}
	if (entryTypeVersion >= 1) {
	    maxMainTreeEntriesPerNode = LogUtils.readInt(itemBuffer);
	    maxDupTreeEntriesPerNode = LogUtils.readInt(itemBuffer);
	}
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 465 "../../../../DatabaseImpl.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<database>");
	id.dumpLog(sb, verbose);
	tree.dumpLog(sb, verbose);
	sb.append("<dupsort v=\"").append(duplicatesAllowed);
	sb.append("\"/>");
	sb.append("<btcf name=\"");
	sb.append(btreeComparatorName);
	sb.append("\"/>");
	sb.append("<dupcf name=\"");
	sb.append(duplicateComparatorName);
	sb.append("\"/>");
	sb.append("</database>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 483 "../../../../DatabaseImpl.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 490 "../../../../DatabaseImpl.ump"
   public long getTransactionId(){
    return 0;
  }


  /**
   * 
   * Used both to write to the log and to validate a comparator when set in DatabaseConfig.
   */
  // line 497 "../../../../DatabaseImpl.ump"
   public static  String serializeComparator(Comparator comparator){
    if (comparator != null) {
	    return comparator.getClass().getName();
	} else {
	    return "";
	}
  }


  /**
   * 
   * Used both to read from the log and to validate a comparator when set in DatabaseConfig.
   */
  // line 508 "../../../../DatabaseImpl.ump"
   public static  Comparator instantiateComparator(Class comparator, String comparatorType) throws LogException{
    if (comparator == null) {
	    return null;
	}
	try {
	    return (Comparator) comparator.newInstance();
	} catch (InstantiationException IE) {
	    throw new LogException("Exception while trying to load " + comparatorType + " Comparator class: " + IE);
	} catch (IllegalAccessException IAE) {
	    throw new LogException("Exception while trying to load " + comparatorType + " Comparator class: " + IAE);
	}
  }

  // line 521 "../../../../DatabaseImpl.ump"
   public int getBinDeltaPercent(){
    return binDeltaPercent;
  }

  // line 525 "../../../../DatabaseImpl.ump"
   public int getBinMaxDeltas(){
    return binMaxDeltas;
  }

  // line 529 "../../../../DatabaseImpl.ump"
   protected void hook288() throws DatabaseException{
    
  }

  // line 532 "../../../../DatabaseImpl.ump"
   protected void hook289() throws DatabaseException{
    
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tree" + "=" + (getTree() != null ? !getTree().equals(this)  ? getTree().toString().replaceAll("  ","    ") : "this" : "null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 47 "../../../../DatabaseImpl.ump"
  private DatabaseId id ;
// line 51 "../../../../DatabaseImpl.ump"
  private EnvironmentImpl envImpl ;
// line 53 "../../../../DatabaseImpl.ump"
  private boolean duplicatesAllowed ;
// line 55 "../../../../DatabaseImpl.ump"
  private boolean transactional ;
// line 57 "../../../../DatabaseImpl.ump"
  private Set referringHandles ;
// line 59 "../../../../DatabaseImpl.ump"
  private long eofNodeId ;
// line 61 "../../../../DatabaseImpl.ump"
  private Comparator btreeComparator = null ;
// line 63 "../../../../DatabaseImpl.ump"
  private Comparator duplicateComparator = null ;
// line 65 "../../../../DatabaseImpl.ump"
  private String btreeComparatorName = "" ;
// line 67 "../../../../DatabaseImpl.ump"
  private String duplicateComparatorName = "" ;
// line 69 "../../../../DatabaseImpl.ump"
  private int binDeltaPercent ;
// line 71 "../../../../DatabaseImpl.ump"
  private int binMaxDeltas ;
// line 73 "../../../../DatabaseImpl.ump"
  private int maxMainTreeEntriesPerNode ;
// line 75 "../../../../DatabaseImpl.ump"
  private int maxDupTreeEntriesPerNode ;
// line 77 "../../../../DatabaseImpl.ump"
  private String debugDatabaseName ;
// line 79 "../../../../DatabaseImpl.ump"
  private TestHook pendingDeletedHook ;
// line 84 "../../../../DatabaseImpl.ump"
  static final HaltPreloadException timeExceededPreloadException = new HaltPreloadException(
	    PreloadStatus.EXCEEDED_TIME) ;
// line 87 "../../../../DatabaseImpl.ump"
  static final HaltPreloadException memoryExceededPreloadException = new HaltPreloadException(
	    PreloadStatus.FILLED_CACHE) ;

// line 282 "../../../../DatabaseImpl.ump"
  synchronized int getReferringHandleCount () 
  {
    return referringHandles.size();
  }

  
}