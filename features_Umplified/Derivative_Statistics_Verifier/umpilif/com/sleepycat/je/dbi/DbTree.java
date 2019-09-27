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

  // line 6 "../../../../DbTree.ump"
   public boolean verify(VerifyConfig config, PrintStream out) throws DatabaseException{
    boolean ret = true;
	try {
	    boolean ok = idDatabase.verify(config, idDatabase.getEmptyStats());
	    if (!ok) {
		ret = false;
	    }
	    ok = nameDatabase.verify(config, nameDatabase.getEmptyStats());
	    if (!ok) {
		ret = false;
	    }
	} catch (DatabaseException DE) {
	    ret = false;
	}
	ret = this.hook292(config, out, ret);
	return ret;
  }

  // line 24 "../../../../DbTree.ump"
   protected void hook291(CursorImpl cursor) throws DatabaseException{
    
  }

  // line 27 "../../../../DbTree.ump"
   protected boolean hook292(VerifyConfig config, PrintStream out, boolean ret) throws DatabaseException{
    Locker locker = null;
	CursorImpl cursor = null;
	LockType lockType = LockType.NONE;
	try {
	    locker = new BasicLocker(envImpl);
	    cursor = new CursorImpl(idDatabase, locker);
	    if (cursor.positionFirstOrLast(true, null)) {
		MapLN mapLN = (MapLN) cursor.getCurrentLNAlreadyLatched(lockType);
		DatabaseEntry keyDbt = new DatabaseEntry();
		DatabaseEntry dataDbt = new DatabaseEntry();
		while (true) {
		    if (mapLN != null && !mapLN.isDeleted()) {
			DatabaseImpl dbImpl = mapLN.getDatabase();
			boolean ok = dbImpl.verify(config, dbImpl.getEmptyStats());
			if (!ok) {
			    ret = false;
			}
		    }
		    OperationStatus status = cursor.getNext(keyDbt, dataDbt, lockType, true, false);
		    if (status != OperationStatus.SUCCESS) {
			break;
		    }
		    mapLN = (MapLN) cursor.getCurrentLN(lockType);
		}
	    }
	} catch (DatabaseException e) {
	    e.printStackTrace(out);
	    ret = false;
	} finally {
	    if (cursor != null) {
		this.hook291(cursor);
		cursor.close();
	    }
	    if (locker != null) {
		locker.operationEnd();
	    }
	}
	return ret;
  }

}