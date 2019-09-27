/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../DbTree.ump"
public class DbTree
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
   * To truncate, remove the database named by databaseName and create a new database in its place.
   * @param returnCountif true, must return the count of records in the database,which can be an expensive option.
   */
  // line 10 "../../../../DbTree.ump"
  public long truncate(Locker locker, String databaseName, boolean returnCount) throws DatabaseException{
    CursorImpl nameCursor = null;
	Locker autoTxn = null;
	try {
	    NameLockResult result = lockNameLN(locker, databaseName, "truncate");
	    nameCursor = result.nameCursor;
	    if (nameCursor == null) {
		return 0;
	    } else {
		DatabaseId newId = new DatabaseId(getNextDbId());
		DatabaseImpl newDb = (DatabaseImpl) result.dbImpl.clone();
		newDb.setId(newId);
		newDb.setTree(new Tree(newDb));
		CursorImpl idCursor = null;
		boolean operationOk = false;
		try {
		    autoTxn = createLocker(envImpl);
		    idCursor = new CursorImpl(idDatabase, autoTxn);
		    idCursor.putLN(newId.getBytes(), new MapLN(newDb), false);
		    operationOk = true;
		} finally {
		    if (idCursor != null) {
			idCursor.close();
		    }
		    if (autoTxn != null) {
			autoTxn.operationEnd(operationOk);
		    }
		}
		result.nameLN.setId(newDb.getId());
		long recordCount = 0;
		if (returnCount) {
		    recordCount = result.dbImpl.countRecords();
		}
		DatabaseEntry dataDbt = new DatabaseEntry(new byte[0]);
		nameCursor.putCurrent(dataDbt, null, null);
		this.hook296(locker, result, newDb);
		return recordCount;
	    }
	} catch (CloneNotSupportedException CNSE) {
	    throw new DatabaseException(CNSE);
	} finally {
	    if (nameCursor != null) {
		this.hook294(nameCursor);
		nameCursor.close();
	    }
	}
  }


  /**
   * 
   * Truncate a database named by databaseName. Return the new DatabaseImpl object that represents the truncated database. The old one is marked as deleted.
   * @deprecated This method used by Database.truncate()
   */
  // line 62 "../../../../DbTree.ump"
  public TruncateResult truncate(Locker locker, DatabaseImpl oldDatabase, boolean returnCount) throws DatabaseException{
    CursorImpl nameCursor = new CursorImpl(nameDatabase, locker);
	try {
	    String databaseName = getDbName(oldDatabase.getId());
	    DatabaseEntry keyDbt = new DatabaseEntry(databaseName.getBytes("UTF-8"));
	    boolean found = (nameCursor.searchAndPosition(keyDbt, null, SearchMode.SET, LockType.WRITE)
		    & CursorImpl.FOUND) != 0;
	    if (!found) {
		throw new DatabaseException("Database " + databaseName + " not found in map tree");
	    }
	    NameLN nameLN = (NameLN) nameCursor.getCurrentLNAlreadyLatched(LockType.WRITE);
	    assert nameLN != null;
	    int handleCount = oldDatabase.getReferringHandleCount();
	    if (handleCount > 1) {
		throw new DatabaseException(
			"Can't truncate database " + databaseName + "," + handleCount + " open databases exist");
	    }
	    DatabaseImpl newDb;
	    DatabaseId newId = new DatabaseId(getNextDbId());
	    newDb = (DatabaseImpl) oldDatabase.clone();
	    newDb.setId(newId);
	    newDb.setTree(new Tree(newDb));
	    CursorImpl idCursor = null;
	    boolean operationOk = false;
	    Locker autoTxn = null;
	    try {
		autoTxn = createLocker(envImpl);
		idCursor = new CursorImpl(idDatabase, autoTxn);
		idCursor.putLN(newId.getBytes(), new MapLN(newDb), false);
		operationOk = true;
	    } finally {
		if (idCursor != null) {
		    idCursor.close();
		}
		if (autoTxn != null) {
		    autoTxn.operationEnd(operationOk);
		}
	    }
	    nameLN.setId(newDb.getId());
	    long count = 0;
	    if (returnCount) {
		count = oldDatabase.countRecords();
	    }
	    this.hook297(locker, oldDatabase);
	    DatabaseEntry dataDbt = new DatabaseEntry(new byte[0]);
	    nameCursor.putCurrent(dataDbt, null, null);
	    return new TruncateResult(newDb, (int) count);
	} catch (CloneNotSupportedException CNSE) {
	    throw new DatabaseException(CNSE);
	} catch (UnsupportedEncodingException UEE) {
	    throw new DatabaseException(UEE);
	} finally {
	    this.hook295(nameCursor);
	    nameCursor.close();
	}
  }

  // line 119 "../../../../DbTree.ump"
   protected void hook294(CursorImpl nameCursor) throws DatabaseException{
    
  }

  // line 122 "../../../../DbTree.ump"
   protected void hook295(CursorImpl nameCursor) throws DatabaseException{
    
  }

  // line 126 "../../../../DbTree.ump"
   protected void hook296(Locker locker, NameLockResult result, DatabaseImpl newDb) throws DatabaseException,CloneNotSupportedException{
    
  }

  // line 130 "../../../../DbTree.ump"
   protected void hook297(Locker locker, DatabaseImpl oldDatabase) throws DatabaseException,CloneNotSupportedException,UnsupportedEncodingException{
    
  }

}