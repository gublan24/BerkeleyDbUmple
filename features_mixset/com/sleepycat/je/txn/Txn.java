/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.tree.TreeLocation;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.recovery.RecoveryManager;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.dbi.CursorImpl;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.LockStats;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Database;
import javax.transaction.xa.Xid;
import javax.transaction.xa.XAResource;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../Txn.ump"
// line 3 "../../../../Txn_static.ump"
public class Txn extends Locker implements LogWritable,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Txn()
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
   * Create a transaction from Environment.txnBegin.
   */
  // line 90 "../../../../Txn.ump"
   public  Txn(EnvironmentImpl envImpl, TransactionConfig config) throws DatabaseException{
    super(envImpl, config.getReadUncommitted(), config.getNoWait());
	init(envImpl, config);
  }

  // line 95 "../../../../Txn.ump"
   public  Txn(EnvironmentImpl envImpl, TransactionConfig config, long id) throws DatabaseException{
    super(envImpl, config.getReadUncommitted(), config.getNoWait());
	init(envImpl, config);
	this.id = id;
  }

  // line 101 "../../../../Txn.ump"
   private void init(EnvironmentImpl envImpl, TransactionConfig config) throws DatabaseException{
    serializableIsolation = config.getSerializableIsolation();
	readCommittedIsolation = config.getReadCommitted();
	if (config.getSync()) {
	    defaultFlushSyncBehavior = TXN_SYNC;
	} else if (config.getWriteNoSync()) {
	    defaultFlushSyncBehavior = TXN_WRITE_NOSYNC;
	} else if (config.getNoSync()) {
	    defaultFlushSyncBehavior = TXN_NOSYNC;
	} else {
	    defaultFlushSyncBehavior = TXN_SYNC;
	}
	lastLoggedLsn = DbLsn.NULL_LSN;
	firstLoggedLsn = DbLsn.NULL_LSN;
	txnState = USABLE;
	//this.hook809();
  Label809:
	this.envImpl.getTxnManager().registerTxn(this);
  }


  /**
   * 
   * Constructor for reading from log.
   */
  // line 124 "../../../../Txn.ump"
   public  Txn(){
    lastLoggedLsn = DbLsn.NULL_LSN;
  }


  /**
   * 
   * UserTxns get a new unique id for each instance.
   */
  // line 131 "../../../../Txn.ump"
   protected long generateId(TxnManager txnManager){
    return txnManager.incTxnId();
  }


  /**
   * 
   * Access to last LSN.
   */
  // line 138 "../../../../Txn.ump"
  public long getLastLsn(){
    return lastLoggedLsn;
  }

  // line 142 "../../../../Txn.ump"
   public void setPrepared(boolean prepared){
    if (prepared) {
	    txnState |= IS_PREPARED;
	} else {
	    txnState &= ~IS_PREPARED;
	}
  }

  // line 150 "../../../../Txn.ump"
   public void setSuspended(boolean suspended){
    if (suspended) {
	    txnState |= XA_SUSPENDED;
	} else {
	    txnState &= ~XA_SUSPENDED;
	}
  }

  // line 158 "../../../../Txn.ump"
   public boolean isSuspended(){
    return (txnState & XA_SUSPENDED) != 0;
  }


  /**
   * 
   * Gets a lock on this nodeId and, if it is a write lock, saves an abort LSN.  Caller will set the abortLsn later, after the write lock has been obtained.
   * @see Locker#lockInternal
   * @Override
   */
  // line 168 "../../../../Txn.ump"
  public LockResult lockInternal(long nodeId, LockType lockType, boolean noWait, DatabaseImpl database) throws DatabaseException{
    long timeout = 0;
	boolean useNoWait = noWait || defaultNoWait;
	synchronized (this) {
	    checkState(false);
	    if (!useNoWait) {
		timeout = lockTimeOutMillis;
	    }
	}
	LockGrantType grant = lockManager.lock(nodeId, this, lockType, timeout, useNoWait, database);
	WriteLockInfo info = null;
	if (writeInfo != null) {
	    if (grant != LockGrantType.DENIED && lockType.isWriteLock()) {
		synchronized (this) {
		    info = (WriteLockInfo) writeInfo.get(new Long(nodeId));
		    undoDatabases.put(database.getId(), database);
		}
	    }
	}
	return new LockResult(grant, info);
  }

  // line 190 "../../../../Txn.ump"
   public int prepare(Xid xid) throws DatabaseException{
    if ((txnState & IS_PREPARED) != 0) {
	    throw new DatabaseException("prepare() has already been called for Transaction " + id + ".");
	}
	synchronized (this) {
	    checkState(false);
	    if (checkCursorsForClose()) {
		throw new DatabaseException("Transaction " + id + " prepare failed because there were open cursors.");
	    }
	    TxnPrepare prepareRecord = new TxnPrepare(id, xid);
	    LogManager logManager = envImpl.getLogManager();
	    logManager.logForceFlush(prepareRecord, true);
	}
	setPrepared(true);
	return XAResource.XA_OK;
  }

  // line 207 "../../../../Txn.ump"
   public void commit(Xid xid) throws DatabaseException{
    commit(TXN_SYNC);
	envImpl.getTxnManager().unRegisterXATxn(xid, true);
	return;
  }

  // line 213 "../../../../Txn.ump"
   public void abort(Xid xid) throws DatabaseException{
    abort(true);
	envImpl.getTxnManager().unRegisterXATxn(xid, false);
	return;
  }


  /**
   * 
   * Call commit() with the default sync configuration property.
   */
  // line 222 "../../../../Txn.ump"
   public long commit() throws DatabaseException{
    return commit(defaultFlushSyncBehavior);
  }


  /**
   * 
   * Commit this transaction 1. Releases read locks 2. Writes a txn commit record into the log 3. Flushes the log to disk. 4. Add deleted LN info to IN compressor queue 5. Release all write locks  If any step of this fails, we must convert this transaction to an abort.
   */
  // line 229 "../../../../Txn.ump"
   public long commit(byte flushSyncBehavior) throws DatabaseException{
    try {
					long commitLsn = DbLsn.NULL_LSN;
					synchronized (this) {
				checkState(false);
				if (checkCursorsForClose()) {
						throw new DatabaseException(
							"Transaction " + id + " commit failed because there were open cursors.");
				}
				if (handleLockToHandleMap != null) {
						Iterator handleLockIter = handleLockToHandleMap.entrySet().iterator();
						while (handleLockIter.hasNext()) {
					Map.Entry entry = (Map.Entry) handleLockIter.next();
					transferHandleLockToHandleSet((Long) entry.getKey(), (Set) entry.getValue());
						}
				}
				LogManager logManager = envImpl.getLogManager();
				int numReadLocks = clearReadLocks();
				int numWriteLocks = 0;
				if (writeInfo != null) {
						numWriteLocks = writeInfo.size();
						TxnCommit commitRecord = new TxnCommit(id, lastLoggedLsn);
						if (flushSyncBehavior == TXN_SYNC) {
					commitLsn = logManager.logForceFlush(commitRecord, true);
						} else if (flushSyncBehavior == TXN_WRITE_NOSYNC) {
					commitLsn = logManager.logForceFlush(commitRecord, false);
						} else {
					commitLsn = logManager.log(commitRecord);
						}
						Label806: //this.hook806();
						Set alreadyCountedLsnSet = new HashSet();
						Iterator iter = writeInfo.values().iterator();
						while (iter.hasNext()) {
					WriteLockInfo info = (WriteLockInfo) iter.next();
					lockManager.release(info.lock, this);
					if (info.abortLsn != DbLsn.NULL_LSN && !info.abortKnownDeleted) {
							Long longLsn = new Long(info.abortLsn);
							if (!alreadyCountedLsnSet.contains(longLsn)) {
						logManager.countObsoleteNode(info.abortLsn, null);
						alreadyCountedLsnSet.add(longLsn);
							}
					}
						}
						writeInfo = null;
						Label803: //this.hook803();
				}
				traceCommit(numWriteLocks, numReadLocks);
					}
					Label805: //this.hook805();
					close(true);
					return commitLsn;
			} catch (RunRecoveryException e) {
					throw e;
			} catch (Throwable t) {
					try {
				abortInternal(flushSyncBehavior == TXN_SYNC, !(t instanceof DatabaseException));
				Label800: //this.hook800(t);
					} catch (Throwable abortT2) {
				throw new DatabaseException("Failed while attempting to commit transaction " + id
					+ ". The attempt to abort and clean up also failed. "
					+ "The original exception seen from commit = " + t.getMessage()
					+ " The exception from the cleanup = " + abortT2.getMessage(), t);
					}
					throw new DatabaseException("Failed while attempting to commit transaction " + id
						+ ", aborted instead. Original exception = " + t.getMessage(), t);
			}
  }


  /**
   * 
   * Abort this transaction. Steps are: 1. Release LN read locks. 2. Write a txn abort entry to the log. This is only for log file cleaning optimization and there's no need to guarantee a flush to disk.   3. Find the last LN log entry written for this txn, and use that to traverse the log looking for nodes to undo. For each node, use the same undo logic as recovery to rollback the transaction. Note that we walk the log in order to undo in reverse order of the actual operations. For example, suppose the txn did this: delete K1/D1 (in LN 10) create K1/D1 (in LN 20) If we process LN10 before LN 20, we'd inadvertently create a  duplicate tree of "K1", which would be fatal for the mapping tree. 4. Release the write lock for this LN.
   */
  // line 300 "../../../../Txn.ump"
   public long abort(boolean forceFlush) throws DatabaseException{
    return abortInternal(forceFlush, true);
  }

  // line 304 "../../../../Txn.ump"
   private long abortInternal(boolean forceFlush, boolean writeAbortRecord) throws DatabaseException{
    try {
	    int numReadLocks;
	    int numWriteLocks;
	    long abortLsn;
	    synchronized (this) {
		checkState(true);
		TxnAbort abortRecord = new TxnAbort(id, lastLoggedLsn);
		abortLsn = DbLsn.NULL_LSN;
		if (writeInfo != null) {
		    if (writeAbortRecord) {
			if (forceFlush) {
			    abortLsn = envImpl.getLogManager().logForceFlush(abortRecord, true);
			} else {
			    abortLsn = envImpl.getLogManager().log(abortRecord);
			}
		    }
		}
		undo();
		numReadLocks = (readLocks == null) ? 0 : clearReadLocks();
		Label808: //this.hook808();
		numWriteLocks = (writeInfo == null) ? 0 : clearWriteLocks();
		Label804: //this.hook804();
	    }
	    Label807: //this.hook807();
	    synchronized (this) {
		boolean openCursors = checkCursorsForClose();
		Label799: //this.hook799(numReadLocks, numWriteLocks, openCursors);
		if (openCursors) {
		    throw new DatabaseException("Transaction " + id + " detected open cursors while aborting");
		}
		if (handleToHandleLockMap != null) {
		    Iterator handleIter = handleToHandleLockMap.keySet().iterator();
		    while (handleIter.hasNext()) {
			Database handle = (Database) handleIter.next();
			DbInternal.dbInvalidate(handle);
		    }
		}
		return abortLsn;
	    }
	} finally {
	    close(false);
	}
  }


  /**
   * 
   * Rollback the changes to this txn's write locked nodes.
   */
  // line 352 "../../../../Txn.ump"
   private void undo() throws DatabaseException{
    Long nodeId = null;
	long undoLsn = lastLoggedLsn;
	LogManager logManager = envImpl.getLogManager();
	try {
	    Set alreadyUndone = new HashSet();
	    TreeLocation location = new TreeLocation();
	    while (undoLsn != DbLsn.NULL_LSN) {
		LNLogEntry undoEntry = (LNLogEntry) logManager.getLogEntry(undoLsn);
		LN undoLN = undoEntry.getLN();
		nodeId = new Long(undoLN.getNodeId());
		if (!alreadyUndone.contains(nodeId)) {
		    alreadyUndone.add(nodeId);
		    DatabaseId dbId = undoEntry.getDbId();
		    DatabaseImpl db = (DatabaseImpl) undoDatabases.get(dbId);
		    undoLN.postFetchInit(db, undoLsn);
		    long abortLsn = undoEntry.getAbortLsn();
		    boolean abortKnownDeleted = undoEntry.getAbortKnownDeleted();
		    Label802: //this.hook802(undoLsn, location, undoEntry, undoLN, db, abortLsn, abortKnownDeleted);
				RecoveryManager.undo(Level.FINER, db, location, undoLN, undoEntry.getKey(), undoEntry.getDupKey(), undoLsn, abortLsn, abortKnownDeleted, null, false);
		    Label802_1: //end of hook802
		    if (!undoLN.isDeleted()) {
			logManager.countObsoleteNode(undoLsn, null);
		    }
		}
		undoLsn = undoEntry.getUserTxn().getLastLsn();
	    }
	} catch (RuntimeException e) {
	    throw new DatabaseException("Txn undo for node=" + nodeId + " LSN=" + DbLsn.getNoFormatString(undoLsn), e);
	} catch (DatabaseException e) {
	    Label801: //this.hook801(nodeId, undoLsn, e);
	    throw e;
	}
  }

  // line 387 "../../../../Txn.ump"
   private int clearWriteLocks() throws DatabaseException{
    int numWriteLocks = writeInfo.size();
	Iterator iter = writeInfo.values().iterator();
	while (iter.hasNext()) {
	    WriteLockInfo info = (WriteLockInfo) iter.next();
	    lockManager.release(info.lock, this);
	}
	writeInfo = null;
	return numWriteLocks;
  }

  // line 398 "../../../../Txn.ump"
   private int clearReadLocks() throws DatabaseException{
    int numReadLocks = 0;
	if (readLocks != null) {
	    numReadLocks = readLocks.size();
	    Iterator iter = readLocks.iterator();
	    while (iter.hasNext()) {
		Lock rLock = (Lock) iter.next();
		lockManager.release(rLock, this);
	    }
	    readLocks = null;
	}
	return numReadLocks;
  }


  /**
   * 
   * Called by the recovery manager when logging a transaction aware object. This method is synchronized by the caller, by being called within the log latch. Record the last LSN for this transaction, to create the transaction chain, and also record the LSN in the write info for abort logic.
   */
  // line 415 "../../../../Txn.ump"
   public void addLogInfo(long lastLsn) throws DatabaseException{
    lastLoggedLsn = lastLsn;
	synchronized (this) {
	    if (firstLoggedLsn == DbLsn.NULL_LSN) {
		firstLoggedLsn = lastLsn;
	    }
	}
  }


  /**
   * 
   * @return first logged LSN, to aid recovery rollback.
   */
  // line 427 "../../../../Txn.ump"
  public long getFirstActiveLsn() throws DatabaseException{
    synchronized (this) {
	    return firstLoggedLsn;
	}
  }


  /**
   * 
   * Add lock to the appropriate queue.
   */
  // line 436 "../../../../Txn.ump"
  public void addLock(Long nodeId, Lock lock, LockType type, LockGrantType grantStatus) throws DatabaseException{
    new Txn_addLock(this, nodeId, lock, type, grantStatus).execute();
  }

  // line 440 "../../../../Txn.ump"
   private void addReadLock(Lock lock){
    int delta = 0;
			if (readLocks == null) {
					readLocks = new HashSet();
			//		delta = this.hook811(delta);
          Label811:
			}
			readLocks.add(lock);
			//this.hook810(delta);
      Label810:
  }


  /**
   * 
   * Remove the lock from the set owned by this transaction. If specified to LockManager.release, the lock manager will call this when its releasing a lock. Usually done because the transaction doesn't need to really keep the lock, i.e for a deleted record.
   */
  // line 455 "../../../../Txn.ump"
  public void removeLock(long nodeId, Lock lock) throws DatabaseException{
    synchronized (this) {
					if ((readLocks != null) && readLocks.remove(lock)) {
				//this.hook812();
          Label812:
					} else if ((writeInfo != null) && (writeInfo.remove(new Long(nodeId)) != null)) {
				//this.hook813();
         label813: 
					}
			}
  }


  /**
   * 
   * A lock is being demoted. Move it from the write collection into the read collection.
   */
  // line 470 "../../../../Txn.ump"
  public void moveWriteToReadLock(long nodeId, Lock lock){
    boolean found = false;
	synchronized (this) {
	    if ((writeInfo != null) && (writeInfo.remove(new Long(nodeId)) != null)) {
		found = true;
		//this.hook814();
    Label814:
	    }
	    assert found : "Couldn't find lock for Node " + nodeId + " in writeInfo Map.";
	    addReadLock(lock);
	}
  }


  /**
   * 
   * @return true if this transaction created this node. We know that thisis true if the node is write locked and has a null abort LSN.
   */
  // line 486 "../../../../Txn.ump"
   public boolean createdNode(long nodeId) throws DatabaseException{
    boolean created = false;
	synchronized (this) {
	    if (writeInfo != null) {
		WriteLockInfo info = (WriteLockInfo) writeInfo.get(new Long(nodeId));
		if (info != null) {
		    created = info.createdThisTxn;
		}
	    }
	}
	return created;
  }


  /**
   * 
   * @return the abortLsn for this node.
   */
  // line 502 "../../../../Txn.ump"
   public long getAbortLsn(long nodeId) throws DatabaseException{
    WriteLockInfo info = null;
	synchronized (this) {
	    if (writeInfo != null) {
		info = (WriteLockInfo) writeInfo.get(new Long(nodeId));
	    }
	}
	if (info == null) {
	    return DbLsn.NULL_LSN;
	} else {
	    return info.abortLsn;
	}
  }


  /**
   * 
   * @return the WriteLockInfo for this node.
   */
  // line 519 "../../../../Txn.ump"
   public WriteLockInfo getWriteLockInfo(long nodeId) throws DatabaseException{
    WriteLockInfo info = WriteLockInfo.basicWriteLockInfo;
	synchronized (this) {
	    if (writeInfo != null) {
		info = (WriteLockInfo) writeInfo.get(new Long(nodeId));
	    }
	}
	return info;
  }


  /**
   * 
   * Is always transactional.
   */
  // line 532 "../../../../Txn.ump"
   public boolean isTransactional(){
    return true;
  }


  /**
   * 
   * Is serializable isolation if so configured.
   */
  // line 539 "../../../../Txn.ump"
   public boolean isSerializableIsolation(){
    return serializableIsolation;
  }


  /**
   * 
   * Is read-committed isolation if so configured.
   */
  // line 546 "../../../../Txn.ump"
   public boolean isReadCommittedIsolation(){
    return readCommittedIsolation;
  }


  /**
   * 
   * This is a transactional locker.
   */
  // line 553 "../../../../Txn.ump"
   public Txn getTxnLocker(){
    return this;
  }


  /**
   * 
   * Returns 'this', since this locker holds no non-transactional locks.
   */
  // line 560 "../../../../Txn.ump"
   public Locker newNonTxnLocker() throws DatabaseException{
    return this;
  }


  /**
   * 
   * This locker holds no non-transactional locks.
   */
  // line 567 "../../../../Txn.ump"
   public void releaseNonTxnLocks() throws DatabaseException{
    
  }


  /**
   * 
   * Created transactions do nothing at the end of the operation.
   */
  // line 573 "../../../../Txn.ump"
   public void operationEnd() throws DatabaseException{
    
  }


  /**
   * 
   * Created transactions do nothing at the end of the operation.
   */
  // line 579 "../../../../Txn.ump"
   public void operationEnd(boolean operationOK) throws DatabaseException{
    
  }


  /**
   * 
   * Created transactions don't transfer locks until commit.
   */
  // line 585 "../../../../Txn.ump"
   public void setHandleLockOwner(boolean ignore, Database dbHandle, boolean dbIsClosing) throws DatabaseException{
    if (dbIsClosing) {
	    Long handleLockId = (Long) handleToHandleLockMap.get(dbHandle);
	    if (handleLockId != null) {
		Set dbHandleSet = (Set) handleLockToHandleMap.get(handleLockId);
		boolean removed = dbHandleSet.remove(dbHandle);
		assert removed : "Can't find " + dbHandle + " from dbHandleSet";
		if (dbHandleSet.size() == 0) {
		    Object foo = handleLockToHandleMap.remove(handleLockId);
		    assert (foo != null) : "Can't find " + handleLockId + " from handleLockIdtoHandleMap.";
		}
	    }
	    unregisterHandle(dbHandle);
	} else {
	    if (dbHandle != null) {
		DbInternal.dbSetHandleLocker(dbHandle, this);
	    }
	}
  }


  /**
   * 
   * Cursors operating under this transaction are added to the collection.
   */
  // line 608 "../../../../Txn.ump"
   public void registerCursor(CursorImpl cursor) throws DatabaseException{
    synchronized (this) {
	    cursor.setLockerNext(cursorSet);
	    if (cursorSet != null) {
		cursorSet.setLockerPrev(cursor);
	    }
	    cursorSet = cursor;
	}
  }


  /**
   * 
   * Remove a cursor from the collection.
   */
  // line 621 "../../../../Txn.ump"
   public void unRegisterCursor(CursorImpl cursor) throws DatabaseException{
    synchronized (this) {
	    CursorImpl prev = cursor.getLockerPrev();
	    CursorImpl next = cursor.getLockerNext();
	    if (prev == null) {
		cursorSet = next;
	    } else {
		prev.setLockerNext(next);
	    }
	    if (next != null) {
		next.setLockerPrev(prev);
	    }
	    cursor.setLockerPrev(null);
	    cursor.setLockerNext(null);
	}
  }


  /**
   * 
   * @return true if this txn is willing to give up the handle lock toanother txn before this txn ends.
   */
  // line 641 "../../../../Txn.ump"
   public boolean isHandleLockTransferrable(){
    return false;
  }


  /**
   * 
   * Check if all cursors associated with the txn are closed. If not, those open cursors will be forcibly closed.
   * @return true if open cursors exist
   */
  // line 649 "../../../../Txn.ump"
   private boolean checkCursorsForClose() throws DatabaseException{
    CursorImpl c = cursorSet;
	while (c != null) {
	    if (!c.isClosed()) {
		return true;
	    }
	    c = c.getLockerNext();
	}
	return false;
  }


  /**
   * 
   * Set the state of a transaction to ONLY_ABORTABLE.
   */
  // line 663 "../../../../Txn.ump"
   public void setOnlyAbortable(){
    txnState &= ~STATE_BITS;
	txnState |= ONLY_ABORTABLE;
  }


  /**
   * 
   * Get the state of a transaction's ONLY_ABORTABLE.
   */
  // line 671 "../../../../Txn.ump"
   public boolean getOnlyAbortable(){
    return (txnState & ONLY_ABORTABLE) != 0;
  }


  /**
   * 
   * Throw an exception if the transaction is not open. If calledByAbort is true, it means we're being called from abort(). Caller must invoke with "this" synchronized.
   */
  // line 678 "../../../../Txn.ump"
   protected void checkState(boolean calledByAbort) throws DatabaseException{
    boolean ok = false;
	boolean onlyAbortable = false;
	byte state = (byte) (txnState & STATE_BITS);
	ok = (state == USABLE);
	onlyAbortable = (state == ONLY_ABORTABLE);
	if (!calledByAbort && onlyAbortable) {
	    throw new DatabaseException("Transaction " + id + " must be aborted.");
	}
	if (ok || (calledByAbort && onlyAbortable)) {
	    return;
	}
	throw new DatabaseException("Transaction " + id + " has been closed.");
  }


  /**
   * 
   */
  // line 695 "../../../../Txn.ump"
   private void close(boolean isCommit) throws DatabaseException{
    synchronized (this) {
	    txnState &= ~STATE_BITS;
	    txnState |= CLOSED;
	}
	envImpl.getTxnManager().unRegisterTxn(this, isCommit);
  }


  /**
   * 
   * @see LogWritable#getLogSize
   */
  // line 706 "../../../../Txn.ump"
   public int getLogSize(){
    return LogUtils.LONG_BYTES + LogUtils.LONG_BYTES;
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 713 "../../../../Txn.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeLong(logBuffer, id);
	LogUtils.writeLong(logBuffer, lastLoggedLsn);
  }


  /**
   * 
   * @see LogReadable#readFromLogIt's ok for FindBugs to whine about id not being synchronized.
   */
  // line 721 "../../../../Txn.ump"
   public void readFromLog(ByteBuffer logBuffer, byte entryTypeVersion){
    id = LogUtils.readLong(logBuffer);
	lastLoggedLsn = LogUtils.readLong(logBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 729 "../../../../Txn.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<txn id=\"");
	sb.append(super.toString());
	sb.append("\">");
	sb.append(DbLsn.toString(lastLoggedLsn));
	sb.append("</txn>");
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 740 "../../../../Txn.ump"
   public long getTransactionId(){
    return getId();
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 747 "../../../../Txn.ump"
   public boolean logEntryIsTransactional(){
    return true;
  }


  /**
   * 
   * Transfer a single handle lock to the set of corresponding handles at commit time.
   */
  // line 754 "../../../../Txn.ump"
   private void transferHandleLockToHandleSet(Long handleLockId, Set dbHandleSet) throws DatabaseException{
    int numHandles = dbHandleSet.size();
	Database[] dbHandles = new Database[numHandles];
	dbHandles = (Database[]) dbHandleSet.toArray(dbHandles);
	Locker[] destTxns = new Locker[numHandles];
	for (int i = 0; i < numHandles; i++) {
	    destTxns[i] = new BasicLocker(envImpl);
	}
	long nodeId = handleLockId.longValue();
	lockManager.transferMultiple(nodeId, this, destTxns);
	for (int i = 0; i < numHandles; i++) {
	    destTxns[i].addToHandleMaps(handleLockId, dbHandles[i]);
	    DbInternal.dbSetHandleLocker(dbHandles[i], destTxns[i]);
	}
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.  The string construction can be numerous enough to show up on a performance profile.
   */
  // line 773 "../../../../Txn.ump"
   private void traceCommit(int numWriteLocks, int numReadLocks){
    new Txn_traceCommit(this, numWriteLocks, numReadLocks).execute();
  }

  // line 777 "../../../../Txn.ump"
  public int getInMemorySize(){
    return inMemorySize;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Txn_static.ump"
  public static class DatabaseCleanupInfo
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //DatabaseCleanupInfo Attributes
    private DatabaseImpl dbImpl;
    private boolean deleteAtCommit;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DatabaseCleanupInfo(DatabaseImpl aDbImpl, boolean aDeleteAtCommit)
    {
      dbImpl = aDbImpl;
      deleteAtCommit = aDeleteAtCommit;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setDbImpl(DatabaseImpl aDbImpl)
    {
      boolean wasSet = false;
      dbImpl = aDbImpl;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setDeleteAtCommit(boolean aDeleteAtCommit)
    {
      boolean wasSet = false;
      deleteAtCommit = aDeleteAtCommit;
      wasSet = true;
      return wasSet;
    }
  
    public DatabaseImpl getDbImpl()
    {
      return dbImpl;
    }
  
    public boolean getDeleteAtCommit()
    {
      return deleteAtCommit;
    }
  
    public void delete()
    {}
  
    // line 8 "../../../../Txn_static.ump"
    public  DatabaseCleanupInfo(DatabaseImpl dbImpl, boolean deleteAtCommit){
      this.dbImpl=dbImpl;
          this.deleteAtCommit=deleteAtCommit;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+
              "deleteAtCommit" + ":" + getDeleteAtCommit()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "dbImpl" + "=" + (getDbImpl() != null ? !getDbImpl().equals(this)  ? getDbImpl().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 12 "../../../../Txn_static.ump"
  public static class Txn_addLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Txn_addLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 14 "../../../../Txn_static.ump"
    public  Txn_addLock(Txn _this, Long nodeId, Lock lock, LockType type, LockGrantType grantStatus){
      this._this=_this;
          this.nodeId=nodeId;
          this.lock=lock;
          this.type=type;
          this.grantStatus=grantStatus;
    }
  
    // line 21 "../../../../Txn_static.ump"
    public void execute() throws DatabaseException{
      synchronized (_this) {
            //this.hook815();
            Label815:
            if (type.isWriteLock()) {
              if (_this.writeInfo == null) {
                _this.writeInfo=new HashMap();
                _this.undoDatabases=new HashMap();
               // this.hook818();
                Label818:
              }
              _this.writeInfo.put(nodeId,new WriteLockInfo(lock));
              //this.hook817();
              Label817:
              if ((grantStatus == LockGrantType.PROMOTION) || (grantStatus == LockGrantType.WAIT_PROMOTION)) {
                _this.readLocks.remove(lock);
                //this.hook819();
                Label819:
              }
              //this.hook816();
              Label816:
            }
     else {
              _this.addReadLock(lock);
            }
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 47 "../../../../Txn_static.ump"
    protected Txn _this ;
  // line 48 "../../../../Txn_static.ump"
    protected Long nodeId ;
  // line 49 "../../../../Txn_static.ump"
    protected Lock lock ;
  // line 50 "../../../../Txn_static.ump"
    protected LockType type ;
  // line 51 "../../../../Txn_static.ump"
    protected LockGrantType grantStatus ;
  // line 52 "../../../../Txn_static.ump"
    protected int delta ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 66 "../../../../Txn_static.ump"
  public static class Txn_traceCommit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Txn_traceCommit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 68 "../../../../Txn_static.ump"
    public  Txn_traceCommit(Txn _this, int numWriteLocks, int numReadLocks){
      this._this=_this;
          this.numWriteLocks=numWriteLocks;
          this.numReadLocks=numReadLocks;
    }
  
    // line 73 "../../../../Txn_static.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 74 "../../../../Txn_static.ump"
    protected Txn _this ;
  // line 75 "../../../../Txn_static.ump"
    protected int numWriteLocks ;
  // line 76 "../../../../Txn_static.ump"
    protected int numReadLocks ;
  // line 77 "../../../../Txn_static.ump"
    protected Logger logger ;
  // line 78 "../../../../Txn_static.ump"
    protected StringBuffer sb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 42 "../../../../Txn.ump"
  public static final byte TXN_NOSYNC = 0 ;
// line 44 "../../../../Txn.ump"
  public static final byte TXN_WRITE_NOSYNC = 1 ;
// line 46 "../../../../Txn.ump"
  public static final byte TXN_SYNC = 2 ;
// line 48 "../../../../Txn.ump"
  private static final String DEBUG_NAME = Txn.class.getName() ;
// line 50 "../../../../Txn.ump"
  private byte txnState ;
// line 52 "../../../../Txn.ump"
  private CursorImpl cursorSet ;
// line 54 "../../../../Txn.ump"
  private static final byte USABLE = 0 ;
// line 56 "../../../../Txn.ump"
  private static final byte CLOSED = 1 ;
// line 58 "../../../../Txn.ump"
  private static final byte ONLY_ABORTABLE = 2 ;
// line 60 "../../../../Txn.ump"
  private static final byte STATE_BITS = 3 ;
// line 62 "../../../../Txn.ump"
  private static final byte IS_PREPARED = 4 ;
// line 64 "../../../../Txn.ump"
  private static final byte XA_SUSPENDED = 8 ;
// line 66 "../../../../Txn.ump"
  private Set readLocks ;
// line 68 "../../../../Txn.ump"
  private Map writeInfo ;
// line 70 "../../../../Txn.ump"
  private Map undoDatabases ;
// line 72 "../../../../Txn.ump"
  private long lastLoggedLsn = DbLsn.NULL_LSN ;
// line 74 "../../../../Txn.ump"
  private long firstLoggedLsn = DbLsn.NULL_LSN ;
// line 76 "../../../../Txn.ump"
  private byte defaultFlushSyncBehavior ;
// line 78 "../../../../Txn.ump"
  private boolean serializableIsolation ;
// line 80 "../../../../Txn.ump"
  private boolean readCommittedIsolation ;
// line 82 "../../../../Txn.ump"
  private int inMemorySize ;
// line 84 "../../../../Txn.ump"
  public static int ACCUMULATED_LIMIT = 10000 ;

  
}