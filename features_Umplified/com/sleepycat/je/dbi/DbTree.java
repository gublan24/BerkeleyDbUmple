/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.txn.BasicLocker;
import com.sleepycat.je.txn.AutoTxn;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.TreeUtils;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.NameLN;
import com.sleepycat.je.tree.MapLN;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.CursorImpl.SearchMode;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.DeadlockException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Database;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.io.UnsupportedEncodingException;
import java.io.PrintStream;
import com.sleepycat.je.log.*;

// line 3 "../../../../DbTree.ump"
// line 3 "../../../../DbTree_static.ump"
public class DbTree implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbTree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a dbTree from the log.
   */
  // line 63 "../../../../DbTree.ump"
   public  DbTree() throws DatabaseException{
    this.envImpl = null;
	idDatabase = new DatabaseImpl();
	idDatabase.setDebugDatabaseName(ID_DB_NAME);
	nameDatabase = new DatabaseImpl();
	nameDatabase.setDebugDatabaseName(NAME_DB_NAME);
  }


  /**
   * 
   * Create a new dbTree for a new environment.
   */
  // line 74 "../../../../DbTree.ump"
   public  DbTree(EnvironmentImpl env) throws DatabaseException{
    this.envImpl = env;
	idDatabase = new DatabaseImpl(ID_DB_NAME, new DatabaseId(0), env, new DatabaseConfig());
	nameDatabase = new DatabaseImpl(NAME_DB_NAME, new DatabaseId(1), env, new DatabaseConfig());
	lastAllocatedDbId = 1;
  }

  // line 102 "../../../../DbTree.ump"
   private Locker createLocker(EnvironmentImpl envImpl) throws DatabaseException{
    if (envImpl.isNoLocking()) {
	    return new BasicLocker(envImpl);
	} else {
	    return new AutoTxn(envImpl, new TransactionConfig());
	}
  }


  /**
   * 
   * Set the db environment during recovery, after instantiating the tree from the log.
   */
  // line 113 "../../../../DbTree.ump"
  public void setEnvironmentImpl(EnvironmentImpl envImpl) throws DatabaseException{
    this.envImpl = envImpl;
	idDatabase.setEnvironmentImpl(envImpl);
	nameDatabase.setEnvironmentImpl(envImpl);
  }


  /**
   * 
   * Called by the Tree to propagate a root change. If the tree is a data database, we will write the MapLn that represents this db to the log. If the tree is one of the mapping dbs, we'll write the dbtree to the log.
   * @param dbthe target db
   */
  // line 175 "../../../../DbTree.ump"
   public void modifyDbRoot(DatabaseImpl db) throws DatabaseException{
    if (db.getId().equals(ID_DB_ID) || db.getId().equals(NAME_DB_ID)) {
	    envImpl.logMapTreeRoot();
	} else {
	    Locker locker = createLocker(envImpl);
	    CursorImpl cursor = new CursorImpl(idDatabase, locker);
	    boolean operationOk = false;
	    try {
		DatabaseEntry keyDbt = new DatabaseEntry(db.getId().getBytes());
		MapLN mapLN = null;
		while (true) {
		    try {
			boolean searchOk = (cursor.searchAndPosition(keyDbt, new DatabaseEntry(), SearchMode.SET,
				LockType.WRITE) & CursorImpl.FOUND) != 0;
			if (!searchOk) {
			    throw new DatabaseException("can't find database " + db.getId());
			}
			mapLN = (MapLN) cursor.getCurrentLNAlreadyLatched(LockType.WRITE);
			assert mapLN != null;
		    } catch (DeadlockException DE) {
			cursor.close();
			locker.operationEnd(false);
			locker = createLocker(envImpl);
			cursor = new CursorImpl(idDatabase, locker);
			continue;
		    } finally {
			this.hook299(cursor);
		    }
		    break;
		}
		RewriteMapLN writeMapLN = new RewriteMapLN(cursor);
		mapLN.getDatabase().getTree().withRootLatchedExclusive(writeMapLN);
		operationOk = true;
	    } finally {
		if (cursor != null) {
		    cursor.close();
		}
		locker.operationEnd(operationOk);
	    }
	}
  }

  // line 217 "../../../../DbTree.ump"
   private NameLockResult lockNameLN(Locker locker, String databaseName, String action) throws DatabaseException{
    NameLockResult result = new NameLockResult();
	result.dbImpl = getDb(locker, databaseName, null);
	if (result.dbImpl == null) {
	    throw new DatabaseNotFoundException("Attempted to " + action + " non-existent database " + databaseName);
	}
	result.nameCursor = new CursorImpl(nameDatabase, locker);
	try {
	    DatabaseEntry key = new DatabaseEntry(databaseName.getBytes("UTF-8"));
	    boolean found = (result.nameCursor.searchAndPosition(key, null, SearchMode.SET, LockType.WRITE)
		    & CursorImpl.FOUND) != 0;
	    if (!found) {
		this.hook300(result);
		result.nameCursor.close();
		result.nameCursor = null;
		return result;
	    }
	    result.nameLN = (NameLN) result.nameCursor.getCurrentLNAlreadyLatched(LockType.WRITE);
	    assert result.nameLN != null;
	    int handleCount = result.dbImpl.getReferringHandleCount();
	    if (handleCount > 0) {
		throw new DatabaseException(
			"Can't " + action + " database " + databaseName + "," + handleCount + " open Dbs exist");
	    }
	} catch (UnsupportedEncodingException UEE) {
	    this.hook301(result);
	    result.nameCursor.close();
	    throw new DatabaseException(UEE);
	} catch (DatabaseException e) {
	    this.hook302(result);
	    result.nameCursor.close();
	    throw e;
	}
	return result;
  }

  // line 253 "../../../../DbTree.ump"
  public void deleteMapLN(DatabaseId id) throws DatabaseException{
    Locker autoTxn = null;
	boolean operationOk = false;
	CursorImpl idCursor = null;
	try {
	    autoTxn = createLocker(envImpl);
	    idCursor = new CursorImpl(idDatabase, autoTxn);
	    boolean found = (idCursor.searchAndPosition(new DatabaseEntry(id.getBytes()), null, SearchMode.SET,
		    LockType.WRITE) & CursorImpl.FOUND) != 0;
	    if (found) {
		idCursor.delete();
	    }
	    operationOk = true;
	} finally {
	    if (idCursor != null) {
		idCursor.close();
	    }
	    if (autoTxn != null) {
		autoTxn.operationEnd(operationOk);
	    }
	}
  }


  /**
   * 
   * Get a database object given a database name.
   */
  // line 280 "../../../../DbTree.ump"
   public DatabaseImpl getDb(Locker nameLocker, String databaseName, Database databaseHandle) throws DatabaseException{
    return getDb(nameLocker, databaseName, databaseHandle, true);
  }


  /**
   * 
   * Get a database object given a database name.
   * @param nameLockeris used to access the NameLN. As always, a NullTxn is used toaccess the MapLN.
   * @param databaseNametarget database
   * @return null if database doesn't exist
   * @param allowEvictionis whether eviction is allowed during cursor operations.
   */
  // line 292 "../../../../DbTree.ump"
   public DatabaseImpl getDb(Locker nameLocker, String databaseName, Database databaseHandle, boolean allowEviction) throws DatabaseException{
    try {
	    CursorImpl nameCursor = null;
	    DatabaseId id = null;
	    try {
		nameCursor = new CursorImpl(nameDatabase, nameLocker);
		this.hook308(allowEviction, nameCursor);
		DatabaseEntry keyDbt = new DatabaseEntry(databaseName.getBytes("UTF-8"));
		boolean found = (nameCursor.searchAndPosition(keyDbt, null, SearchMode.SET, LockType.READ)
			& CursorImpl.FOUND) != 0;
		if (found) {
		    NameLN nameLN = (NameLN) nameCursor.getCurrentLNAlreadyLatched(LockType.READ);
		    assert nameLN != null;
		    id = nameLN.getId();
		    if (databaseHandle != null) {
			nameLocker.addToHandleMaps(new Long(nameLN.getNodeId()), databaseHandle);
		    }
		}
	    } finally {
		if (nameCursor != null) {
		    this.hook303(nameCursor);
		    nameCursor.close();
		}
	    }
	    if (id == null) {
		return null;
	    } else {
		return getDb(id, -1, allowEviction, databaseName);
	    }
	} catch (UnsupportedEncodingException UEE) {
	    throw new DatabaseException(UEE);
	}
  }


  /**
   * 
   * Get a database object based on an id only. Used by recovery, cleaning and other clients who have an id in hand, and don't have a resident node, to find the matching database for a given log entry.
   */
  // line 329 "../../../../DbTree.ump"
   public DatabaseImpl getDb(DatabaseId dbId) throws DatabaseException{
    return getDb(dbId, -1);
  }


  /**
   * 
   * Get a database object based on an id only. Specify the lock timeout to use, or -1 to use the default timeout. A timeout should normally only be specified by daemons with their own timeout configuration. public for unit tests.
   */
  // line 336 "../../../../DbTree.ump"
   public DatabaseImpl getDb(DatabaseId dbId, long lockTimeout) throws DatabaseException{
    return getDb(dbId, lockTimeout, true, null);
  }


  /**
   * 
   * Get a database object based on an id only, caching the id-db mapping in the given map.
   */
  // line 343 "../../../../DbTree.ump"
   public DatabaseImpl getDb(DatabaseId dbId, long lockTimeout, Map dbCache) throws DatabaseException{
    if (dbCache.containsKey(dbId)) {
	    return (DatabaseImpl) dbCache.get(dbId);
	} else {
	    DatabaseImpl db = getDb(dbId, lockTimeout, true, null);
	    dbCache.put(dbId, db);
	    return db;
	}
  }


  /**
   * 
   * Get a database object based on an id only. Specify the lock timeout to use, or -1 to use the default timeout. A timeout should normally only be specified by daemons with their own timeout configuration. public for unit tests.
   * @param allowEvictionis whether eviction is allowed during cursor operations.
   */
  // line 358 "../../../../DbTree.ump"
   public DatabaseImpl getDb(DatabaseId dbId, long lockTimeout, boolean allowEviction, String dbNameIfAvailable) throws DatabaseException{
    if (dbId.equals(idDatabase.getId())) {
	    return idDatabase;
	} else if (dbId.equals(nameDatabase.getId())) {
	    return nameDatabase;
	} else {
	    Locker locker = new BasicLocker(envImpl);
	    if (lockTimeout != -1) {
		locker.setLockTimeout(lockTimeout);
	    }
	    CursorImpl idCursor = null;
	    DatabaseImpl foundDbImpl = null;
	    while (true) {
		idCursor = new CursorImpl(idDatabase, locker);
		this.hook309(allowEviction, idCursor);
		try {
		    DatabaseEntry keyDbt = new DatabaseEntry(dbId.getBytes());
		    boolean found = (idCursor.searchAndPosition(keyDbt, new DatabaseEntry(), SearchMode.SET,
			    LockType.READ) & CursorImpl.FOUND) != 0;
		    if (found) {
			MapLN mapLN = (MapLN) idCursor.getCurrentLNAlreadyLatched(LockType.READ);
			assert mapLN != null;
			foundDbImpl = mapLN.getDatabase();
		    }
		    break;
		} catch (DeadlockException DE) {
		    idCursor.close();
		    locker.operationEnd(false);
		    locker = new BasicLocker(envImpl);
		    if (lockTimeout != -1) {
			locker.setLockTimeout(lockTimeout);
		    }
		    idCursor = new CursorImpl(idDatabase, locker);
		    this.hook310(allowEviction, idCursor);
		    continue;
		} finally {
		    this.hook304(idCursor);
		    idCursor.close();
		    locker.operationEnd(true);
		}
	    }
	    if (envImpl.isOpen()) {
		setDebugNameForDatabaseImpl(foundDbImpl, dbNameIfAvailable);
	    }
	    return foundDbImpl;
	}
  }

  // line 406 "../../../../DbTree.ump"
   private void setDebugNameForDatabaseImpl(DatabaseImpl dbImpl, String dbName) throws DatabaseException{
    if (dbImpl != null) {
	    if (dbName != null) {
		dbImpl.setDebugDatabaseName(dbName);
	    } else if (dbImpl.getDebugName() == null) {
		dbImpl.setDebugDatabaseName(getDbName(dbImpl.getId()));
	    }
	}
  }


  /**
   * 
   * Rebuild the IN list after recovery.
   */
  // line 419 "../../../../DbTree.ump"
   public void rebuildINListMapDb() throws DatabaseException{
    idDatabase.getTree().rebuildINList();
  }


  /**
   * 
   * Return the database name for a given db. Slow, must traverse. Used by truncate and for debugging.
   */
  // line 426 "../../../../DbTree.ump"
   public String getDbName(DatabaseId id) throws DatabaseException{
    if (id.equals(ID_DB_ID)) {
	    return ID_DB_NAME;
	} else if (id.equals(NAME_DB_ID)) {
	    return NAME_DB_NAME;
	}
	Locker locker = null;
	CursorImpl cursor = null;
	try {
	    locker = new BasicLocker(envImpl);
	    cursor = new CursorImpl(nameDatabase, locker);
	    DatabaseEntry keyDbt = new DatabaseEntry();
	    DatabaseEntry dataDbt = new DatabaseEntry();
	    String name = null;
	    if (cursor.positionFirstOrLast(true, null)) {
		OperationStatus status = cursor.getCurrentAlreadyLatched(keyDbt, dataDbt, LockType.NONE, true);
		do {
		    if (status == OperationStatus.SUCCESS) {
			NameLN nameLN = (NameLN) cursor.getCurrentLN(LockType.NONE);
			if (nameLN != null && nameLN.getId().equals(id)) {
			    name = new String(keyDbt.getData(), "UTF-8");
			    break;
			}
		    }
		    status = cursor.getNext(keyDbt, dataDbt, LockType.NONE, true, false);
		} while (status == OperationStatus.SUCCESS);
	    }
	    return name;
	} catch (UnsupportedEncodingException UEE) {
	    throw new DatabaseException(UEE);
	} finally {
	    if (cursor != null) {
		this.hook305(cursor);
		cursor.close();
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	}
  }


  /**
   * 
   * @return a list of database names held in the environment, as strings.
   */
  // line 470 "../../../../DbTree.ump"
   public List getDbNames() throws DatabaseException{
    List nameList = new ArrayList();
	Locker locker = null;
	CursorImpl cursor = null;
	try {
	    locker = new BasicLocker(envImpl);
	    cursor = new CursorImpl(nameDatabase, locker);
	    DatabaseEntry keyDbt = new DatabaseEntry();
	    DatabaseEntry dataDbt = new DatabaseEntry();
	    if (cursor.positionFirstOrLast(true, null)) {
		OperationStatus status = cursor.getCurrentAlreadyLatched(keyDbt, dataDbt, LockType.READ, true);
		do {
		    if (status == OperationStatus.SUCCESS) {
			String name = new String(keyDbt.getData(), "UTF-8");
			if (!isReservedDbName(name)) {
			    nameList.add(name);
			}
		    }
		    status = cursor.getNext(keyDbt, dataDbt, LockType.READ, true, false);
		} while (status == OperationStatus.SUCCESS);
	    }
	    return nameList;
	} catch (UnsupportedEncodingException UEE) {
	    throw new DatabaseException(UEE);
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	}
  }


  /**
   * 
   * Returns true if the name is a reserved JE database name.
   */
  // line 507 "../../../../DbTree.ump"
   public boolean isReservedDbName(String name){
    for (int i = 0; i < RESERVED_DB_NAMES.length; i += 1) {
	    if (RESERVED_DB_NAMES[i].equals(name)) {
		return true;
	    }
	}
	return false;
  }


  /**
   * 
   * @return the higest level node in the environment.
   */
  // line 519 "../../../../DbTree.ump"
   public int getHighestLevel() throws DatabaseException{
    RootLevel getLevel = new RootLevel(idDatabase);
	idDatabase.getTree().withRootLatchedShared(getLevel);
	int idHighLevel = getLevel.getRootLevel();
	getLevel = new RootLevel(nameDatabase);
	nameDatabase.getTree().withRootLatchedShared(getLevel);
	int nameHighLevel = getLevel.getRootLevel();
	return (nameHighLevel > idHighLevel) ? nameHighLevel : idHighLevel;
  }


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 532 "../../../../DbTree.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_ROOT;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatch Can be marshalled outsidethe log write latch.
   */
  // line 539 "../../../../DbTree.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 546 "../../../../DbTree.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#getLogSize
   */
  // line 553 "../../../../DbTree.ump"
   public int getLogSize(){
    return LogUtils.getIntLogSize() + idDatabase.getLogSize() + nameDatabase.getLogSize();
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 560 "../../../../DbTree.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeInt(logBuffer, lastAllocatedDbId);
	idDatabase.writeToLog(logBuffer);
	nameDatabase.writeToLog(logBuffer);
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 569 "../../../../DbTree.ump"
   public void postLogWork(long justLoggedLsn) throws DatabaseException{
    
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 575 "../../../../DbTree.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    lastAllocatedDbId = LogUtils.readInt(itemBuffer);
	idDatabase.readFromLog(itemBuffer, entryTypeVersion);
	nameDatabase.readFromLog(itemBuffer, entryTypeVersion);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 584 "../../../../DbTree.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<dbtree lastId = \"");
	sb.append(lastAllocatedDbId);
	sb.append("\">");
	sb.append("<idDb>");
	idDatabase.dumpLog(sb, verbose);
	sb.append("</idDb><nameDb>");
	nameDatabase.dumpLog(sb, verbose);
	sb.append("</nameDb>");
	sb.append("</dbtree>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional.
   */
  // line 599 "../../../../DbTree.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 606 "../../../../DbTree.ump"
   public long getTransactionId(){
    return 0;
  }

  // line 610 "../../../../DbTree.ump"
  public String dumpString(int nSpaces){
    StringBuffer self = new StringBuffer();
	self.append(TreeUtils.indent(nSpaces));
	self.append("<dbTree lastDbId =\"");
	self.append(lastAllocatedDbId);
	self.append("\">");
	self.append('\n');
	self.append(idDatabase.dumpString(nSpaces + 1));
	self.append('\n');
	self.append(nameDatabase.dumpString(nSpaces + 1));
	self.append('\n');
	self.append("</dbtree>");
	return self.toString();
  }

  // line 625 "../../../../DbTree.ump"
   public String toString(){
    return dumpString(0);
  }


  /**
   * 
   * For debugging.
   */
  // line 632 "../../../../DbTree.ump"
   public void dump() throws DatabaseException{
    idDatabase.getTree().dump();
	nameDatabase.getTree().dump();
  }

  // line 637 "../../../../DbTree.ump"
   protected void hook299(CursorImpl cursor) throws DatabaseException{
    
  }

  // line 640 "../../../../DbTree.ump"
   protected void hook300(NameLockResult result) throws DatabaseException,UnsupportedEncodingException{
    
  }

  // line 643 "../../../../DbTree.ump"
   protected void hook301(NameLockResult result) throws DatabaseException{
    
  }

  // line 646 "../../../../DbTree.ump"
   protected void hook302(NameLockResult result) throws DatabaseException{
    
  }

  // line 649 "../../../../DbTree.ump"
   protected void hook303(CursorImpl nameCursor) throws DatabaseException,UnsupportedEncodingException{
    
  }

  // line 652 "../../../../DbTree.ump"
   protected void hook304(CursorImpl idCursor) throws DatabaseException{
    
  }

  // line 655 "../../../../DbTree.ump"
   protected void hook305(CursorImpl cursor) throws DatabaseException{
    
  }

  // line 659 "../../../../DbTree.ump"
   protected void hook306(boolean allowEviction, CursorImpl idCursor) throws DatabaseException,UnsupportedEncodingException{
    
  }

  // line 663 "../../../../DbTree.ump"
   protected void hook307(boolean allowEviction, CursorImpl nameCursor) throws DatabaseException,UnsupportedEncodingException{
    
  }

  // line 667 "../../../../DbTree.ump"
   protected void hook308(boolean allowEviction, CursorImpl nameCursor) throws DatabaseException,UnsupportedEncodingException{
    
  }

  // line 670 "../../../../DbTree.ump"
   protected void hook309(boolean allowEviction, CursorImpl idCursor) throws DatabaseException{
    
  }

  // line 673 "../../../../DbTree.ump"
   protected void hook310(boolean allowEviction, CursorImpl idCursor) throws DatabaseException{
    
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  import com.sleepycat.je.tree.*;
  
  // line 4 "../../../../DbTree_static.ump"
  public static class RewriteMapLN implements WithRootLatched
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RewriteMapLN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 8 "../../../../DbTree_static.ump"
    public  RewriteMapLN(CursorImpl cursor){
      this.cursor=cursor;
    }
  
    // line 11 "../../../../DbTree_static.ump"
     public IN doWork(ChildReference root) throws DatabaseException{
      DatabaseEntry dataDbt=new DatabaseEntry(new byte[0]);
          cursor.putCurrent(dataDbt,null,null);
          return null;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 6 "../../../../DbTree_static.ump"
    private CursorImpl cursor ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 16 "../../../../DbTree_static.ump"
  public static class NameLockResult
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //NameLockResult Attributes
    private CursorImpl nameCursor;
    private DatabaseImpl dbImpl;
    private NameLN nameLN;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public NameLockResult(CursorImpl aNameCursor, DatabaseImpl aDbImpl, NameLN aNameLN)
    {
      nameCursor = aNameCursor;
      dbImpl = aDbImpl;
      nameLN = aNameLN;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setNameCursor(CursorImpl aNameCursor)
    {
      boolean wasSet = false;
      nameCursor = aNameCursor;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setDbImpl(DatabaseImpl aDbImpl)
    {
      boolean wasSet = false;
      dbImpl = aDbImpl;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setNameLN(NameLN aNameLN)
    {
      boolean wasSet = false;
      nameLN = aNameLN;
      wasSet = true;
      return wasSet;
    }
  
    public CursorImpl getNameCursor()
    {
      return nameCursor;
    }
  
    public DatabaseImpl getDbImpl()
    {
      return dbImpl;
    }
  
    public NameLN getNameLN()
    {
      return nameLN;
    }
  
    public void delete()
    {}
  
  
    public String toString()
    {
      return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "nameCursor" + "=" + (getNameCursor() != null ? !getNameCursor().equals(this)  ? getNameCursor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
              "  " + "dbImpl" + "=" + (getDbImpl() != null ? !getDbImpl().equals(this)  ? getDbImpl().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
              "  " + "nameLN" + "=" + (getNameLN() != null ? !getNameLN().equals(this)  ? getNameLN().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  import com.sleepycat.je.tree.*;
  
  // line 21 "../../../../DbTree_static.ump"
  public static class RootLevel implements WithRootLatched
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RootLevel()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 26 "../../../../DbTree_static.ump"
    public  RootLevel(DatabaseImpl db){
      this.db=db;
          rootLevel=0;
    }
  
    // line 30 "../../../../DbTree_static.ump"
     public IN doWork(ChildReference root) throws DatabaseException{
      IN rootIN=(IN)root.fetchTarget(db,null);
          rootLevel=rootIN.getLevel();
          return null;
    }
  
    // line 35 "../../../../DbTree_static.ump"
    public int getRootLevel(){
      return rootLevel;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 23 "../../../../DbTree_static.ump"
    private DatabaseImpl db ;
  // line 24 "../../../../DbTree_static.ump"
    private int rootLevel ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 39 "../../../../DbTree.ump"
  public static final DatabaseId ID_DB_ID = new DatabaseId(0) ;
// line 41 "../../../../DbTree.ump"
  public static final DatabaseId NAME_DB_ID = new DatabaseId(1) ;
// line 43 "../../../../DbTree.ump"
  public static final String ID_DB_NAME = "_jeIdMap" ;
// line 45 "../../../../DbTree.ump"
  public static final String NAME_DB_NAME = "_jeNameMap" ;
// line 47 "../../../../DbTree.ump"
  public static final String UTILIZATION_DB_NAME = "_jeUtilization" ;
// line 49 "../../../../DbTree.ump"
  private static final String[] RESERVED_DB_NAMES = {ID_DB_NAME, NAME_DB_NAME, UTILIZATION_DB_NAME};
// line 51 "../../../../DbTree.ump"
  private int lastAllocatedDbId ;
// line 53 "../../../../DbTree.ump"
  private DatabaseImpl idDatabase ;
// line 55 "../../../../DbTree.ump"
  private DatabaseImpl nameDatabase ;
// line 57 "../../../../DbTree.ump"
  private EnvironmentImpl envImpl ;

// line 83 "../../../../DbTree.ump"
  public synchronized int getLastDbId () 
  {
    return lastAllocatedDbId;
  }

// line 90 "../../../../DbTree.ump"
  private synchronized int getNextDbId () 
  {
    return ++lastAllocatedDbId;
  }

// line 97 "../../../../DbTree.ump"
  public synchronized void setLastDbId (int maxDbId) 
  {
    lastAllocatedDbId = maxDbId;
  }

// line 121 "../../../../DbTree.ump"
  public synchronized DatabaseImpl createDb (Locker locker, String databaseName, DatabaseConfig dbConfig,
	    Database databaseHandle) throws DatabaseException 
  {
    return createDb(locker, databaseName, dbConfig, databaseHandle, true);
  }

// line 133 "../../../../DbTree.ump"
  public synchronized DatabaseImpl createDb (Locker locker, String databaseName, DatabaseConfig dbConfig,
	    Database databaseHandle, boolean allowEviction) throws DatabaseException 
  {
    DatabaseId newId = new DatabaseId(getNextDbId());
	DatabaseImpl newDb = new DatabaseImpl(databaseName, newId, envImpl, dbConfig);
	CursorImpl idCursor = null;
	CursorImpl nameCursor = null;
	boolean operationOk = false;
	Locker autoTxn = null;
	try {
	    nameCursor = new CursorImpl(nameDatabase, locker);
	    this.hook307(allowEviction, nameCursor);
	    LN nameLN = new NameLN(newId);
	    nameCursor.putLN(databaseName.getBytes("UTF-8"), nameLN, false);
	    if (databaseHandle != null) {
		locker.addToHandleMaps(new Long(nameLN.getNodeId()), databaseHandle);
	    }
	    autoTxn = createLocker(envImpl);
	    idCursor = new CursorImpl(idDatabase, autoTxn);
	    this.hook306(allowEviction, idCursor);
	    idCursor.putLN(newId.getBytes(), new MapLN(newDb), false);
	    operationOk = true;
	} catch (UnsupportedEncodingException UEE) {
	    throw new DatabaseException(UEE);
	} finally {
	    if (idCursor != null) {
		idCursor.close();
	    }
	    if (nameCursor != null) {
		nameCursor.close();
	    }
	    if (autoTxn != null) {
		autoTxn.operationEnd(operationOk);
	    }
	}
	return newDb;
  }

  
}