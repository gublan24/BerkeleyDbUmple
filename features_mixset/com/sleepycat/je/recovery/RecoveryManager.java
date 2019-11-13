/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.txn.Txn;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.TreeLocation;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.TrackingInfo;
import com.sleepycat.je.tree.SearchResult;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.Key;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.log.LastFileReader;
import com.sleepycat.je.log.LNFileReader;
import com.sleepycat.je.log.INFileReader;
import com.sleepycat.je.log.FileManager;
import com.sleepycat.je.log.CheckpointFileReader;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.cleaner.UtilizationTracker;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.CheckpointConfig;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;

// line 3 "../../../../RecoveryManager.ump"
// line 3 "../../../../RecoveryManager_static.ump"
// line 3 "../../../../loggingBase_RecoveryManager.ump"
// line 3 "../../../../loggingBase_RecoveryManager_inner.ump"
// line 3 "../../../../Evictor_RecoveryManager.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Make a recovery manager
   */
  // line 91 "../../../../RecoveryManager.ump"
   public  RecoveryManager(EnvironmentImpl env) throws DatabaseException{
    this.env = env;
			DbConfigManager cm = env.getConfigManager();
			readBufferSize = cm.getInt(EnvironmentParams.LOG_ITERATOR_READ_SIZE);
			committedTxnIds = new HashSet();
			abortedTxnIds = new HashSet();
			preparedTxns = new HashMap();
			inListRebuildDbIds = new HashSet();
			fileSummaryLsns = new HashMap();
			Label578:           ;  //this.hook578(env);
  }


  /**
   * 
   * Look for an existing log and use it to create an in memory structure for accessing existing databases. The file manager and logging system are only available after recovery.
   * @return RecoveryInfo statistics about the recovery process.
   */
  // line 107 "../../../../RecoveryManager.ump"
   public RecoveryInfo recover(boolean readOnly) throws DatabaseException{
    info = new RecoveryInfo();
			try {
					FileManager fileManager = env.getFileManager();
					DbConfigManager configManager = env.getConfigManager();
					boolean forceCheckpoint = configManager.getBoolean(EnvironmentParams.ENV_RECOVERY_FORCE_CHECKPOINT);
					if (fileManager.filesExist()) {
				findEndOfLog(readOnly);
				Label559:           ;  //this.hook559();
				findLastCheckpoint();
				env.getLogManager().setLastLsnAtRecovery(fileManager.getLastUsedLsn());
				Label558:           ;  //this.hook558();
				env.readMapTreeFromLog(info.useRootLsn);
				buildTree();
					} else {
				Label556:           ;  //this.hook556();
				Label560:           ;  //this.hook560();
				env.logMapTreeRoot();
				forceCheckpoint = true;
					}
					if (preparedTxns.size() > 0) {
				Label573:           ;  //this.hook573();
				preparedTxns = null;
					}
					if (DbInternal.getCreateUP(env.getConfigManager().getEnvironmentConfig())) {
				env.getUtilizationProfile().populateCache();
					}
					if (!readOnly && (env.getLogManager().getLastLsnAtRecovery() != info.checkpointEndLsn || forceCheckpoint)) {
				CheckpointConfig config = new CheckpointConfig();
				config.setForce(true);
				config.setMinimizeRecoveryTime(true);
				env.invokeCheckpoint(config, false, "recovery");
					}
			} catch (IOException e) {
					Label575:           ;  //this.hook575(e);
					throw new RecoveryException(env, "Couldn't recover: " + e.getMessage(), e);
			} finally {
					Tracer.trace(Level.CONFIG, env, "Recovery finished: " + info);
			}
			return info;
  }


  /**
   * 
   * Find the end of the log, initialize the FileManager. While we're perusing the log, return the last checkpoint LSN if we happen to see it.
   */
  // line 152 "../../../../RecoveryManager.ump"
   private void findEndOfLog(boolean readOnly) throws IOException,DatabaseException{
    LastFileReader reader = new LastFileReader(env, readBufferSize);
			while (reader.readNextEntry()) {
					LogEntryType type = reader.getEntryType();
					if (LogEntryType.LOG_CKPT_END.equals(type)) {
				info.checkpointEndLsn = reader.getLastLsn();
				info.partialCheckpointStartLsn = DbLsn.NULL_LSN;
					} else if (LogEntryType.LOG_CKPT_START.equals(type)) {
				if (info.partialCheckpointStartLsn == DbLsn.NULL_LSN) {
						info.partialCheckpointStartLsn = reader.getLastLsn();
				}
					}
			}
			assert (reader.getLastValidLsn() != reader.getEndOfLog()) : "lastUsed="
				+ DbLsn.getNoFormatString(reader.getLastValidLsn()) + " end="
				+ DbLsn.getNoFormatString(reader.getEndOfLog());
			if (!readOnly) {
					reader.setEndOfFile();
			}
			info.lastUsedLsn = reader.getLastValidLsn();
			info.nextAvailableLsn = reader.getEndOfLog();
			info.nRepeatIteratorReads += reader.getNRepeatIteratorReads();
			env.getFileManager().setLastPosition(info.nextAvailableLsn, info.lastUsedLsn, reader.getPrevOffset());
  }


  /**
   * 
   * Find the last checkpoint and establish the firstActiveLsn point, checkpoint start, and checkpoint end.
   */
  // line 180 "../../../../RecoveryManager.ump"
   private void findLastCheckpoint() throws IOException,DatabaseException{
    if (info.checkpointEndLsn == DbLsn.NULL_LSN) {
	    CheckpointFileReader searcher = new CheckpointFileReader(env, readBufferSize, false, info.lastUsedLsn,
		    DbLsn.NULL_LSN, info.nextAvailableLsn);
	    while (searcher.readNextEntry()) {
		if (searcher.isCheckpointEnd()) {
		    info.checkpointEndLsn = searcher.getLastLsn();
		    break;
		} else if (searcher.isCheckpointStart()) {
		    info.partialCheckpointStartLsn = searcher.getLastLsn();
		} else if (searcher.isRoot()) {
		    if (info.useRootLsn == DbLsn.NULL_LSN) {
			info.useRootLsn = searcher.getLastLsn();
		    }
		}
	    }
	    info.nRepeatIteratorReads += searcher.getNRepeatIteratorReads();
	}
	if (info.checkpointEndLsn == DbLsn.NULL_LSN) {
	    info.checkpointStartLsn = DbLsn.NULL_LSN;
	    info.firstActiveLsn = DbLsn.NULL_LSN;
	} else {
	    CheckpointEnd checkpointEnd = (CheckpointEnd) (env.getLogManager().get(info.checkpointEndLsn));
	    info.checkpointEnd = checkpointEnd;
	    info.checkpointStartLsn = checkpointEnd.getCheckpointStartLsn();
	    info.firstActiveLsn = checkpointEnd.getFirstActiveLsn();
	    if (checkpointEnd.getRootLsn() != DbLsn.NULL_LSN) {
		info.useRootLsn = checkpointEnd.getRootLsn();
	    }
	    env.getCheckpointer().setCheckpointId(checkpointEnd.getId());
	    env.getCheckpointer().setFirstActiveLsn(checkpointEnd.getFirstActiveLsn());
	}
	if (info.useRootLsn == DbLsn.NULL_LSN) {
	    throw new RecoveryException(env, "This environment's log file has no root. Since the root "
		    + "is the first entry written into a log at environment "
		    + "creation, this should only happen if the initial creation "
		    + "of the environment was never checkpointed or synced. "
		    + "Please move aside the existing log files to allow the " + "creation of a new environment");
	}
  }


  /**
   * 
   * Use the log to recreate an in memory tree.
   */
  // line 224 "../../../../RecoveryManager.ump"
   private void buildTree() throws IOException,DatabaseException{
    inListClearCounter = 0;
	Label572:           ;  //this.hook572();
	long start = System.currentTimeMillis();
	readINsAndTrackIds(info.checkpointStartLsn);
	long end = System.currentTimeMillis();
	Label571:           ;  //this.hook571(start, end);
	start = System.currentTimeMillis();
	info.numOtherINs += readINs(info.checkpointStartLsn, true, LogEntryType.LOG_BIN_DELTA, null, null, true);
	end = System.currentTimeMillis();
	Label570:           ;  //this.hook570(start, end);
	start = System.currentTimeMillis();
	Set mapLNSet = new HashSet();
	mapLNSet.add(LogEntryType.LOG_MAPLN_TRANSACTIONAL);
	mapLNSet.add(LogEntryType.LOG_TXN_COMMIT);
	mapLNSet.add(LogEntryType.LOG_TXN_ABORT);
	mapLNSet.add(LogEntryType.LOG_TXN_PREPARE);
	undoLNs(info, mapLNSet);
	end = System.currentTimeMillis();
	Label569:           ;  //this.hook569(start, end);
	start = System.currentTimeMillis();
	mapLNSet.add(LogEntryType.LOG_MAPLN);
	redoLNs(info, mapLNSet);
	end = System.currentTimeMillis();
	Label568:           ;  //this.hook568(start, end);
	start = System.currentTimeMillis();
	info.numOtherINs += readINs(info.checkpointStartLsn, false, LogEntryType.LOG_IN, LogEntryType.LOG_BIN,
		LogEntryType.LOG_IN_DELETE_INFO, false);
	end = System.currentTimeMillis();
	Label567:           ;  //this.hook567(start, end);
	start = System.currentTimeMillis();
	info.numBinDeltas = readINs(info.checkpointStartLsn, false, LogEntryType.LOG_BIN_DELTA, null, null, true);
	end = System.currentTimeMillis();
	Label566:           ;  //this.hook566(start, end);
	start = System.currentTimeMillis();
	info.numDuplicateINs += readINs(info.checkpointStartLsn, false, LogEntryType.LOG_DIN, LogEntryType.LOG_DBIN,
		LogEntryType.LOG_IN_DUPDELETE_INFO, true);
	end = System.currentTimeMillis();
	Label565:           ;  //this.hook565(start, end);
	start = System.currentTimeMillis();
	info.numBinDeltas += readINs(info.checkpointStartLsn, false, LogEntryType.LOG_DUP_BIN_DELTA, null, null, true);
	end = System.currentTimeMillis();
	Label564:           ;  //this.hook564(start, end);
	rebuildINList();
//	Label:           ;  //this.hook596();
  Label596:
env.invokeEvictor();
			//original();

	Label563:           ;  //this.hook563();
	start = System.currentTimeMillis();
	Set lnSet = new HashSet();
	lnSet.add(LogEntryType.LOG_LN_TRANSACTIONAL);
	lnSet.add(LogEntryType.LOG_NAMELN_TRANSACTIONAL);
	lnSet.add(LogEntryType.LOG_DEL_DUPLN_TRANSACTIONAL);
	lnSet.add(LogEntryType.LOG_DUPCOUNTLN_TRANSACTIONAL);
	undoLNs(info, lnSet);
	end = System.currentTimeMillis();
	Label562:           ;  //this.hook562(start, end);
	start = System.currentTimeMillis();
	lnSet.add(LogEntryType.LOG_LN);
	lnSet.add(LogEntryType.LOG_NAMELN);
	lnSet.add(LogEntryType.LOG_DEL_DUPLN);
	lnSet.add(LogEntryType.LOG_DUPCOUNTLN);
	lnSet.add(LogEntryType.LOG_FILESUMMARYLN);
	redoLNs(info, lnSet);
	end = System.currentTimeMillis();
	Label561:           ;  //this.hook561(start, end);
  }

  // line 291 "../../../../RecoveryManager.ump"
   private void readINsAndTrackIds(long rollForwardLsn) throws IOException,DatabaseException{
    INFileReader reader = new INFileReader(env, readBufferSize, rollForwardLsn, info.nextAvailableLsn, true, false,
		info.partialCheckpointStartLsn, fileSummaryLsns);
	reader.addTargetType(LogEntryType.LOG_IN);
	reader.addTargetType(LogEntryType.LOG_BIN);
	reader.addTargetType(LogEntryType.LOG_IN_DELETE_INFO);
	Label593://Label:           ;  //this.hook593(reader);
	try {
	    info.numMapINs = 0;
	    DbTree dbMapTree = env.getDbMapTree();
	    while (reader.readNextEntry()) {
		DatabaseId dbId = reader.getDatabaseId();
		if (dbId.equals(DbTree.ID_DB_ID)) {
		    DatabaseImpl db = dbMapTree.getDb(dbId);
		    replayOneIN(reader, db, false);
		    info.numMapINs++;
		}
	    }
	    info.useMaxNodeId = reader.getMaxNodeId();
	    info.useMaxDbId = reader.getMaxDbId();
	    info.useMaxTxnId = reader.getMaxTxnId();
	    if (info.checkpointEnd != null) {
		if (info.useMaxNodeId < info.checkpointEnd.getLastNodeId()) {
		    info.useMaxNodeId = info.checkpointEnd.getLastNodeId();
		}
		if (info.useMaxDbId < info.checkpointEnd.getLastDbId()) {
		    info.useMaxDbId = info.checkpointEnd.getLastDbId();
		}
		if (info.useMaxTxnId < info.checkpointEnd.getLastTxnId()) {
		    info.useMaxTxnId = info.checkpointEnd.getLastTxnId();
		}
	    }
	    Node.setLastNodeId(info.useMaxNodeId);
	    env.getDbMapTree().setLastDbId(info.useMaxDbId);
	    env.getTxnManager().setLastTxnId(info.useMaxTxnId);
	    info.nRepeatIteratorReads += reader.getNRepeatIteratorReads();
	} catch (Exception e) {
	    traceAndThrowException(reader.getLastLsn(), "readMapIns", e);
	}
  }


  /**
   * 
   * Read INs and process.
   */
  // line 336 "../../../../RecoveryManager.ump"
   private int readINs(long rollForwardLsn, boolean mapDbOnly, LogEntryType inType1, LogEntryType inType2, LogEntryType inType3, boolean requireExactMatch) throws IOException,DatabaseException{
    INFileReader reader = new INFileReader(env, readBufferSize, rollForwardLsn, info.nextAvailableLsn, false,
		mapDbOnly, info.partialCheckpointStartLsn, fileSummaryLsns);
	if (inType1 != null) {
	    reader.addTargetType(inType1);
	}
	if (inType2 != null) {
	    reader.addTargetType(inType2);
	}
	if (inType3 != null) {
	    reader.addTargetType(inType3);
	}
	int numINsSeen = 0;
	try {
	    DbTree dbMapTree = env.getDbMapTree();
	    while (reader.readNextEntry()) {
		DatabaseId dbId = reader.getDatabaseId();
		boolean isMapDb = dbId.equals(DbTree.ID_DB_ID);
		boolean isTarget = false;
		if (mapDbOnly && isMapDb) {
		    isTarget = true;
		} else if (!mapDbOnly && !isMapDb) {
		    isTarget = true;
		}
		if (isTarget) {
		    DatabaseImpl db = dbMapTree.getDb(dbId);
		    if (db == null) {
		    } else {
			replayOneIN(reader, db, requireExactMatch);
			numINsSeen++;
			inListRebuildDbIds.add(dbId);
		    }
		}
	    }
	    info.nRepeatIteratorReads += reader.getNRepeatIteratorReads();
	    return numINsSeen;
	} catch (Exception e) {
	    traceAndThrowException(reader.getLastLsn(), "readNonMapIns", e);
	    return 0;
	}
  }


  /**
   * 
   * Get an IN from the reader, set its database, and fit into tree.
   */
  // line 381 "../../../../RecoveryManager.ump"
   private void replayOneIN(INFileReader reader, DatabaseImpl db, boolean requireExactMatch) throws DatabaseException{
    if (reader.isDeleteInfo()) {
	    replayINDelete(db, reader.getDeletedNodeId(), false, reader.getDeletedIdKey(), null, reader.getLastLsn());
	} else if (reader.isDupDeleteInfo()) {
	    replayINDelete(db, reader.getDupDeletedNodeId(), true, reader.getDupDeletedMainKey(),
		    reader.getDupDeletedDupKey(), reader.getLastLsn());
	} else {
	    IN in = reader.getIN();
	    long inLsn = reader.getLsnOfIN();
	    in.postRecoveryInit(db, inLsn);
	    Label585: ;  //this.hook585(in);
	    replaceOrInsert(db, in, reader.getLastLsn(), inLsn, requireExactMatch);
	}
	if ((++inListClearCounter % CLEAR_INCREMENT) == 0) {
	    env.getInMemoryINs().clear();
	}
  }


  /**
   * 
   * Undo all aborted LNs. To do so, walk the log backwards, keeping a collection of committed txns. If we see a log entry that doesn't have a committed txn, undo it.
   */
  // line 402 "../../../../RecoveryManager.ump"
   private void undoLNs(RecoveryInfo info, Set lnTypes) throws IOException,DatabaseException{
    long firstActiveLsn = info.firstActiveLsn;
	long lastUsedLsn = info.lastUsedLsn;
	long endOfFileLsn = info.nextAvailableLsn;

	LNFileReader reader = new LNFileReader(env, readBufferSize, lastUsedLsn, false, endOfFileLsn, firstActiveLsn,
		null);
	Iterator iter = lnTypes.iterator();
	while (iter.hasNext()) {
	    LogEntryType lnType = (LogEntryType) iter.next();
	    reader.addTargetType(lnType);
	}
	Map countedFileSummaries = new HashMap();
	Set countedAbortLsnNodes = new HashSet();
	DbTree dbMapTree = env.getDbMapTree();
	TreeLocation location = new TreeLocation();
	try {
	    while (reader.readNextEntry()) {
		if (reader.isLN()) {
		    Long txnId = reader.getTxnId();
		    if (txnId != null && !committedTxnIds.contains(txnId)) {
		  //this.hook597();
      Label597:
env.invokeEvictor();
      			//original();

			LN ln = reader.getLN();
			long logLsn = reader.getLastLsn();
			long abortLsn = reader.getAbortLsn();
			boolean abortKnownDeleted = reader.getAbortKnownDeleted();
			DatabaseId dbId = reader.getDatabaseId();
			DatabaseImpl db = dbMapTree.getDb(dbId);
			if (db != null) {
			    ln.postFetchInit(db, logLsn);
			    Label586:           ;  //this.hook586(info, reader, location, ln, logLsn, abortLsn, abortKnownDeleted, db);
					undo(detailedTraceLevel, db, location, ln, reader.getKey(), reader.getDupTreeKey(), logLsn, abortLsn,		abortKnownDeleted, info, true);
					Label586_1:
			    TxnNodeId txnNodeId = new TxnNodeId(reader.getNodeId(), txnId.longValue());
			    undoUtilizationInfo(ln, logLsn, abortLsn, abortKnownDeleted, txnNodeId,
				    countedFileSummaries, countedAbortLsnNodes);
			    inListRebuildDbIds.add(dbId);
			}
		    }
		} else if (reader.isPrepare()) {
		    long prepareId = reader.getTxnPrepareId();
		    Long prepareIdL = new Long(prepareId);
		    if (!committedTxnIds.contains(prepareIdL) && !abortedTxnIds.contains(prepareIdL)) {
			TransactionConfig txnConf = new TransactionConfig();
			Txn preparedTxn = new Txn(env, txnConf, prepareId);
			preparedTxn.setLockTimeout(0);
			preparedTxns.put(prepareIdL, preparedTxn);
			env.getTxnManager().registerXATxn(reader.getTxnPrepareXid(), preparedTxn, true);
			Label574:           ;  //this.hook574(reader);
		    }
		} else if (reader.isAbort()) {
		    abortedTxnIds.add(new Long(reader.getTxnAbortId()));
		} else {
		    committedTxnIds.add(new Long(reader.getTxnCommitId()));
		}
	    }
	    info.nRepeatIteratorReads += reader.getNRepeatIteratorReads();
	} catch (Exception e) {
	    traceAndThrowException(reader.getLastLsn(), "undoLNs", e);
	}
  }


  /**
   * 
   * Apply all committed LNs.
   * @param rollForwardLsnstart redoing from this point
   * @param lnType1targetted LN
   * @param lnType2targetted LN
   */
  // line 471 "../../../../RecoveryManager.ump"
   private void redoLNs(RecoveryInfo info, Set lnTypes) throws IOException,DatabaseException{
    long endOfFileLsn = info.nextAvailableLsn;
	long rollForwardLsn = info.checkpointStartLsn;
	LNFileReader reader = new LNFileReader(env, readBufferSize, rollForwardLsn, true, DbLsn.NULL_LSN, endOfFileLsn,
		null);
	Iterator iter = lnTypes.iterator();
	while (iter.hasNext()) {
	    LogEntryType lnType = (LogEntryType) iter.next();
	    reader.addTargetType(lnType);
	}
	Set countedAbortLsnNodes = new HashSet();
	DbTree dbMapTree = env.getDbMapTree();
	TreeLocation location = new TreeLocation();
	try {
	    while (reader.readNextEntry()) {
		if (reader.isLN()) {
		    Long txnId = reader.getTxnId();
		    boolean processThisLN = false;
		    boolean lnIsCommitted = false;
		    boolean lnIsPrepared = false;
		    Txn preparedTxn = null;
		    if (txnId == null) {
			processThisLN = true;
		    } else {
			lnIsCommitted = committedTxnIds.contains(txnId);
			if (!lnIsCommitted) {
			    preparedTxn = (Txn) preparedTxns.get(txnId);
			    lnIsPrepared = preparedTxn != null;
			}
			if (lnIsCommitted || lnIsPrepared) {
			    processThisLN = true;
			}
		    }
		    if (processThisLN) {
			//Label:           ;  //this.hook598();
      Label598:
env.invokeEvictor();

			LN ln = reader.getLN();
			DatabaseId dbId = reader.getDatabaseId();
			DatabaseImpl db = dbMapTree.getDb(dbId);
			long logLsn = reader.getLastLsn();
			long treeLsn = DbLsn.NULL_LSN;
			if (db != null) {
			    ln.postFetchInit(db, logLsn);
			    if (preparedTxn != null) {
				preparedTxn.addLogInfo(logLsn);
				preparedTxn.lock(ln.getNodeId(), LockType.WRITE, false, db);
				preparedTxn.setPrepared(true);
			    }
			    treeLsn = redo(db, location, ln, reader.getKey(), reader.getDupTreeKey(), logLsn, info);
			    inListRebuildDbIds.add(dbId);
			}
			TxnNodeId txnNodeId = null;
			if (txnId != null) {
			    txnNodeId = new TxnNodeId(reader.getNodeId(), txnId.longValue());
			}
			redoUtilizationInfo(logLsn, treeLsn, reader.getAbortLsn(), reader.getAbortKnownDeleted(), ln,
				txnNodeId, countedAbortLsnNodes);
		    }
		}
	    }
	    info.nRepeatIteratorReads += reader.getNRepeatIteratorReads();
	} catch (Exception e) {
	    traceAndThrowException(reader.getLastLsn(), "redoLns", e);
	}
  }


  /**
   * 
   * Rebuild the in memory inList with INs that have been made resident by the recovery process.
   */
  // line 540 "../../../../RecoveryManager.ump"
   private void rebuildINList() throws DatabaseException{
    env.getInMemoryINs().clear();
	env.getDbMapTree().rebuildINListMapDb();
	Iterator iter = inListRebuildDbIds.iterator();
	while (iter.hasNext()) {
	    DatabaseId dbId = (DatabaseId) iter.next();
	    if (!dbId.equals(DbTree.ID_DB_ID)) {
		DatabaseImpl db = env.getDbMapTree().getDb(dbId);
		if (db != null) {
		    db.getTree().rebuildINList();
		}
	    }
	}
  }


  /**
   * 
   * Recover an internal node. If inFromLog is: - not found, insert it in the appropriate location. - if found and there is a physical match (LSNs are the same) do nothing. - if found and there is a logical match (LSNs are different, another version of this IN is in place, replace the found node with the node read from the log only if the log version's LSN is greater. InFromLog should be latched upon entering this method and it will not be latched upon exiting.
   * @param inFromLog -the new node to put in the tree. The identifier key and node id are used to find the existing version of the node.
   * @param logLsn -the location of log entry in in the log.
   * @param inLsnLSN of this in -- may not be the same as the log LSN if thecurrent entry is a BINDelta
   * @param requireExactMatch -true if we won't place this node in the tree unless we find exactly that parent. Used for BINDeltas, where we want to only apply the BINDelta to that exact node.
   */
  // line 563 "../../../../RecoveryManager.ump"
   private void replaceOrInsert(DatabaseImpl db, IN inFromLog, long logLsn, long inLsn, boolean requireExactMatch) throws DatabaseException{
    List trackingList = null;
	try {
	    if (inFromLog.isRoot()) {
		if (inFromLog.containsDuplicates()) {
		    replaceOrInsertDuplicateRoot(db, (DIN) inFromLog, logLsn);
		} else {
		    replaceOrInsertRoot(db, inFromLog, logLsn);
		}
	    } else {
		trackingList = new ArrayList();
		replaceOrInsertChild(db, inFromLog, logLsn, inLsn, trackingList, requireExactMatch);
	    }
	} catch (Exception e) {
	    String trace = printTrackList(trackingList);
	    Label576:           ;  //this.hook576(db, logLsn, e, trace);
	    throw new DatabaseException("lsnFromLog=" + DbLsn.getNoFormatString(logLsn), e);
	} finally {
	    Label587:           ;  //this.hook587(inFromLog, logLsn);
	}
  }


  /**
   * 
   * Dump a tracking list into a string.
   */
  // line 588 "../../../../RecoveryManager.ump"
   private String printTrackList(List trackingList){
    if (trackingList != null) {
	    StringBuffer sb = new StringBuffer();
	    Iterator iter = trackingList.iterator();
	    sb.append("Trace list:");
	    sb.append('\n');
	    while (iter.hasNext()) {
		sb.append((TrackingInfo) iter.next());
		sb.append('\n');
	    }
	    return sb.toString();
	} else {
	    return null;
	}
  }


  /**
   * 
   * Replay an IN delete. Remove an entry from an IN to reflect a reverse split.
   */
  // line 608 "../../../../RecoveryManager.ump"
   private void replayINDelete(DatabaseImpl db, long nodeId, boolean containsDuplicates, byte [] mainKey, byte [] dupKey, long logLsn) throws DatabaseException{
    boolean found = false;
	boolean deleted = false;
	Tree tree = db.getTree();
	SearchResult result = new SearchResult();
	try {
	    result = db.getTree().getParentINForChildIN(nodeId, containsDuplicates, false, mainKey, dupKey, false,
		    false, -1, null, true);
	    if (result.parent == null) {
		tree.withRootLatchedExclusive(new RootDeleter(tree));
		DbTree dbTree = db.getDbEnvironment().getDbMapTree();
		dbTree.modifyDbRoot(db);
		Label557:           ;  //this.hook557(db);
		deleted = true;
	    } else if (result.exactParentFound) {
		found = true;
		deleted = result.parent.deleteEntry(result.index, false);
	    }
	} finally {
	    Label588:           ;  //this.hook588(result);
	    Label579:           ;  //this.hook579(nodeId, containsDuplicates, logLsn, found, deleted, result);
	}
  }


  /**
   * 
   * If the root of this tree is null, use this IN from the log as a root. Note that we should really also check the LSN of the mapLN, because perhaps the root is null because it's been deleted. However, the replay of all the LNs will end up adjusting the tree correctly. If there is a root, check if this IN is a different LSN and if so, replace it.
   */
  // line 635 "../../../../RecoveryManager.ump"
   private void replaceOrInsertRoot(DatabaseImpl db, IN inFromLog, long lsn) throws DatabaseException{
    boolean success = true;
	Tree tree = db.getTree();
	RootUpdater rootUpdater = new RootUpdater(tree, inFromLog, lsn);
	try {
	    tree.withRootLatchedExclusive(rootUpdater);
	    if (rootUpdater.updateDone()) {
		EnvironmentImpl env = db.getDbEnvironment();
		env.getDbMapTree().modifyDbRoot(db);
	    }
	} catch (Exception e) {
	    success = false;
	    throw new DatabaseException("lsnFromLog=" + DbLsn.getNoFormatString(lsn), e);
	} finally {
	    Label580:           ;  //this.hook580(db, inFromLog, lsn, success, rootUpdater);
	}
  }


  /**
   * 
   * Recover this root of a duplicate tree.
   */
  // line 656 "../../../../RecoveryManager.ump"
   private void replaceOrInsertDuplicateRoot(DatabaseImpl db, DIN inFromLog, long lsn) throws DatabaseException{
    boolean found = true;
	boolean inserted = false;
	boolean replaced = false;
	long origLsn = DbLsn.NULL_LSN;
	byte[] mainTreeKey = inFromLog.getMainTreeKey();
	IN parent = null;
	int index = -1;
	boolean success = false;
	try {
	    parent = db.getTree().searchSplitsAllowed(mainTreeKey, -1, true);
	    assert parent instanceof BIN;
	    ChildReference newRef = new ChildReference(inFromLog, mainTreeKey, lsn);
	    index = parent.insertEntry1(newRef);
	    if ((index >= 0 && (index & IN.EXACT_MATCH) != 0)) {
		index &= ~IN.EXACT_MATCH;
		if (parent.isEntryKnownDeleted(index)) {
		    parent.setEntry(index, inFromLog, mainTreeKey, lsn, (byte) 0);
		    replaced = true;
		} else {
		    origLsn = parent.getLsn(index);
		    if (DbLsn.compareTo(origLsn, lsn) < 0) {
			parent.setEntry(index, inFromLog, mainTreeKey, lsn, parent.getState(index));
			replaced = true;
		    }
		}
	    } else {
		found = false;
	    }
	    success = true;
	} finally {
	    Label589:           ;  //this.hook589(parent);
	    Label581:           ;  //this.hook581(db, inFromLog, lsn, found, inserted, replaced, origLsn, parent, index, success);
	}
  }

  // line 693 "../../../../RecoveryManager.ump"
   private void replaceOrInsertChild(DatabaseImpl db, IN inFromLog, long logLsn, long inLsn, List trackingList, boolean requireExactMatch) throws DatabaseException{
    boolean inserted = false;
	boolean replaced = false;
	long origLsn = DbLsn.NULL_LSN;
	boolean success = false;
	SearchResult result = new SearchResult();
	try {
	    result = db.getTree().getParentINForChildIN(inFromLog, requireExactMatch, false, -1, trackingList);
	    if (result.parent == null) {
		return;
	    }
	    if (result.index >= 0) {
		if (result.parent.getLsn(result.index) == logLsn) {
		} else {
		    if (result.exactParentFound) {
			origLsn = result.parent.getLsn(result.index);
			if (DbLsn.compareTo(origLsn, logLsn) < 0) {
			    result.parent.updateEntry(result.index, inFromLog, inLsn);
			    replaced = true;
			}
		    }
		}
	    }
	    success = true;
	} finally {
	    Label590:           ;  //this.hook590(result);
	    Label582:           ;  //this.hook582(db, inFromLog, logLsn, inserted, replaced, origLsn, success, result);
	}
  }


  /**
   * 
   * Redo a committed LN for recovery. <pre> log LN found  | logLSN &gt; LSN | LN is deleted | action in tree     | in tree      |               | --------------+--------------+---------------+------------------------ Y         |    N         |    n/a        | no action --------------+--------------+---------------+------------------------ Y         |    Y         |     N         | replace w/log LSN --------------+--------------+---------------+------------------------ Y         |    Y         |     Y         | replace w/log LSN, put |              |               | on compressor queue --------------+--------------+---------------+------------------------ N         |    n/a       |     N         | insert into tree --------------+--------------+---------------+------------------------ N         |    n/a       |     Y         | no action --------------+--------------+---------------+------------------------ </pre>
   * @param locationholds state about the search in the tree. Passed in from therecovery manager to reduce objection creation overhead.
   * @param lnFromLog -the new node to put in the tree.
   * @param mainKeyis the key that navigates us through the main tree
   * @param dupTreeKeyis the key that navigates us through the duplicate tree
   * @param logLsnis the LSN from the just-read log entry
   * @param infois a recovery stats object.
   * @return the LSN found in the tree, or null if not found.
   */
  // line 734 "../../../../RecoveryManager.ump"
   private long redo(DatabaseImpl db, TreeLocation location, LN lnFromLog, byte [] mainKey, byte [] dupKey, long logLsn, RecoveryInfo info) throws DatabaseException{
    boolean found = false;
	boolean replaced = false;
	boolean inserted = false;
	boolean success = false;
	try {
	    location.reset();
	    found = db.getTree().getParentBINForChildLN(location, mainKey, dupKey, lnFromLog, true, false, true, true);
	    if (!found && (location.bin == null)) {
		success = true;
		return DbLsn.NULL_LSN;
	    }
	    if (lnFromLog.containsDuplicates()) {
		if (found) {
		    DIN duplicateRoot = (DIN) location.bin.fetchTarget(location.index);
		    if (DbLsn.compareTo(logLsn, location.childLsn) >= 0) {
			duplicateRoot.updateDupCountLNRefAndNullTarget(logLsn);
		    }
		}
	    } else {
		if (found) {
		    info.lnFound++;
		    if (DbLsn.compareTo(logLsn, location.childLsn) > 0) {
			info.lnReplaced++;
			replaced = true;
			location.bin.updateEntry(location.index, null, logLsn);
		    }
		    if (DbLsn.compareTo(logLsn, location.childLsn) >= 0 && lnFromLog.isDeleted()) {
			location.bin.setKnownDeletedLeaveTarget(location.index);
			byte[] deletedKey = location.bin.containsDuplicates() ? dupKey : mainKey;
			Label594: //Label:           ;  //this.hook594(db, location, deletedKey);
		    }
		} else {
		    info.lnNotFound++;
		    if (!lnFromLog.isDeleted()) {
			info.lnInserted++;
			inserted = true;
			boolean insertOk = insertRecovery(db, location, logLsn);
			assert insertOk;
		    }
		}
	    }
	    success = true;
	    return found ? location.childLsn : DbLsn.NULL_LSN;
	} finally {
	    Label591:           ;  //this.hook591(location);
	    Label583:           ;  //this.hook583(db, location, lnFromLog, logLsn, found, replaced, inserted, success);
	}
  }


  /**
   * 
   * Undo the changes to this node. Here are the rules that govern the action taken. <pre> found LN in  | abortLsn is | logLsn ==       | action taken tree      | null        | LSN in tree     | by undo -------------+-------------+---------------------------------------- Y       |     N       |      Y          | replace w/abort LSN ------------ +-------------+-----------------+----------------------- Y       |     Y       |      Y          | remove from tree ------------ +-------------+-----------------+----------------------- Y       |     N/A     |      N          | no action ------------ +-------------+-----------------+----------------------- N       |     N/A     |    N/A          | no action (*) (*) If this key is not present in the tree, this record doesn't reflect the IN state of the tree and this log entry is not applicable. </pre>
   * @param locationholds state about the search in the tree. Passed in from therecovery manager to reduce objection creation overhead.
   * @param lnFromLog -the new node to put in the tree.
   * @param mainKeyis the key that navigates us through the main tree
   * @param dupTreeKeyis the key that navigates us through the duplicate tree
   * @param logLsnis the LSN from the just-read log entry
   * @param abortLsngives us the location of the original version of the node
   * @param infois a recovery stats object.
   */
  // line 796 "../../../../RecoveryManager.ump"
   public static  void undo(Level traceLevel, DatabaseImpl db, TreeLocation location, LN lnFromLog, byte [] mainKey, byte [] dupKey, long logLsn, long abortLsn, boolean abortKnownDeleted, RecoveryInfo info, boolean splitsAllowed) throws DatabaseException{
    boolean found = false;
	boolean replaced = false;
	boolean success = false;
	Label584: //hook584(traceLevel, db, location, lnFromLog, mainKey, dupKey, logLsn, abortLsn, abortKnownDeleted, info, splitsAllowed, found, replaced, success);
	location.reset();
		found = db.getTree().getParentBINForChildLN(location, mainKey, dupKey, lnFromLog, splitsAllowed, true, false,
			true);
		if (lnFromLog.containsDuplicates()) {
			  if (found) {
			DIN duplicateRoot = (DIN) location.bin.fetchTarget(location.index);
			Label592: //replaced = hook592(location, logLsn, abortLsn, replaced, duplicateRoot);
			if (DbLsn.compareTo(logLsn, location.childLsn) == 0) {
							duplicateRoot.updateDupCountLNRefAndNullTarget(abortLsn);
							replaced = true;
					}
			Label592_1:
			//end hook592
			  }
		} else {
			  if (found) {
			if (info != null) {
				  info.lnFound++;
			}
			boolean updateEntry = DbLsn.compareTo(logLsn, location.childLsn) == 0;
			if (updateEntry) {
				  if (abortLsn == DbLsn.NULL_LSN) {
				location.bin.setKnownDeletedLeaveTarget(location.index);
				byte[] deletedKey = location.bin.containsDuplicates() ? dupKey : mainKey;
				Label595: //hook595(db, location, deletedKey);
				  } else {
				if (info != null) {
					  info.lnReplaced++;
				}
				replaced = true;
				location.bin.updateEntry(location.index, null, abortLsn);
				if (abortKnownDeleted) {
					  location.bin.setKnownDeleted(location.index);
				} else {
					  location.bin.clearKnownDeleted(location.index);
				}
				  }
				  location.bin.clearPendingDeleted(location.index);
			}
			  } else {
			if (info != null) {
				  info.lnNotFound++;
			}
			  }
		}
		success = true;
  

// End hook584
  }


  /**
   * 
   * Inserts a LN into the tree for recovery redo processing. In this case, we know we don't have to lock when checking child LNs for deleted status (there can be no other thread running on this tree) and we don't have to log the new entry. (it's in the log already)
   * @param db
   * @param locationthis embodies the parent bin, the index, the key thatrepresents this entry in the bin.
   * @param logLsnLSN of this current ln
   * @param keyto use when creating a new ChildReference object.
   * @return true if LN was inserted, false if it was a duplicate duplicate orif an attempt was made to insert a duplicate when allowDuplicates was false.
   */
  // line 861 "../../../../RecoveryManager.ump"
   private static  boolean insertRecovery(DatabaseImpl db, TreeLocation location, long logLsn) throws DatabaseException{
    ChildReference newLNRef = new ChildReference(null, location.lnKey, logLsn);
	BIN parentBIN = location.bin;
	int entryIndex = parentBIN.insertEntry1(newLNRef);
	if ((entryIndex & IN.INSERT_SUCCESS) == 0) {
	    entryIndex &= ~IN.EXACT_MATCH;
	    boolean canOverwrite = false;
	    if (parentBIN.isEntryKnownDeleted(entryIndex)) {
		canOverwrite = true;
	    } else {
		LN currentLN = (LN) parentBIN.fetchTarget(entryIndex);
		if (currentLN == null || currentLN.isDeleted()) {
		    canOverwrite = true;
		}
		parentBIN.updateEntry(entryIndex, null);
	    }
	    if (canOverwrite) {
		parentBIN.updateEntry(entryIndex, null, logLsn, location.lnKey);
		parentBIN.clearKnownDeleted(entryIndex);
		location.index = entryIndex;
		return true;
	    } else {
		return false;
	    }
	}
	location.index = entryIndex & ~IN.INSERT_SUCCESS;
	return true;
  }


  /**
   * 
   * Update file utilization info during redo.
   */
  // line 894 "../../../../RecoveryManager.ump"
   private void redoUtilizationInfo(long logLsn, long treeLsn, long abortLsn, boolean abortKnownDeleted, LN ln, TxnNodeId txnNodeId, Set countedAbortLsnNodes){
    UtilizationTracker tracker = env.getUtilizationTracker();
	if (ln.isDeleted()) {
	    Long logFileNum = new Long(DbLsn.getFileNumber(logLsn));
	    long fileSummaryLsn = DbLsn.longToLsn((Long) fileSummaryLsns.get(logFileNum));
	    int cmpFsLsnToLogLsn = (fileSummaryLsn != DbLsn.NULL_LSN) ? DbLsn.compareTo(fileSummaryLsn, logLsn) : -1;
	    if (cmpFsLsnToLogLsn < 0) {
		tracker.countObsoleteNode(logLsn, null);
	    }
	}
	if (treeLsn != DbLsn.NULL_LSN) {
	    int cmpLogLsnToTreeLsn = DbLsn.compareTo(logLsn, treeLsn);
	    if (cmpLogLsnToTreeLsn != 0) {
		long newLsn = (cmpLogLsnToTreeLsn < 0) ? treeLsn : logLsn;
		long oldLsn = (cmpLogLsnToTreeLsn > 0) ? treeLsn : logLsn;
		Long oldLsnFile = new Long(DbLsn.getFileNumber(oldLsn));
		long oldFsLsn = DbLsn.longToLsn((Long) fileSummaryLsns.get(oldLsnFile));
		int cmpOldFsLsnToNewLsn = (oldFsLsn != DbLsn.NULL_LSN) ? DbLsn.compareTo(oldFsLsn, newLsn) : -1;
		if (cmpOldFsLsnToNewLsn < 0) {
		    tracker.countObsoleteNode(oldLsn, null);
		}
	    }
	    if (cmpLogLsnToTreeLsn <= 0 && abortLsn != DbLsn.NULL_LSN && !abortKnownDeleted
		    && !countedAbortLsnNodes.contains(txnNodeId)) {
		Long abortFileNum = new Long(DbLsn.getFileNumber(abortLsn));
		long abortFsLsn = DbLsn.longToLsn((Long) fileSummaryLsns.get(abortFileNum));
		int cmpAbortFsLsnToLogLsn = (abortFsLsn != DbLsn.NULL_LSN) ? DbLsn.compareTo(abortFsLsn, logLsn) : -1;
		if (cmpAbortFsLsnToLogLsn < 0) {
		    tracker.countObsoleteNodeInexact(abortLsn, null);
		    countedAbortLsnNodes.add(txnNodeId);
		}
	    }
	}
  }


  /**
   * 
   * Update file utilization info during recovery undo (not abort undo).
   */
  // line 933 "../../../../RecoveryManager.ump"
   private void undoUtilizationInfo(LN ln, long logLsn, long abortLsn, boolean abortKnownDeleted, TxnNodeId txnNodeId, Map countedFileSummaries, Set countedAbortLsnNodes){
    UtilizationTracker tracker = env.getUtilizationTracker();
	Long logFileNum = new Long(DbLsn.getFileNumber(logLsn));
	long fileSummaryLsn = DbLsn.longToLsn((Long) fileSummaryLsns.get(logFileNum));
	int cmpFsLsnToLogLsn = (fileSummaryLsn != DbLsn.NULL_LSN) ? DbLsn.compareTo(fileSummaryLsn, logLsn) : -1;
	if (cmpFsLsnToLogLsn < 0) {
	    tracker.countObsoleteNode(logLsn, null);
	}
	if (cmpFsLsnToLogLsn > 0) {
	    Long countedFile = (Long) countedFileSummaries.get(txnNodeId);
	    if (countedFile == null || countedFile.longValue() > logFileNum.longValue()) {
		if (!ln.isDeleted()) {
		    tracker.countObsoleteNode(logLsn, null);
		}
		countedFileSummaries.put(txnNodeId, logFileNum);
	    }
	}
  }


  /**
   * 
   * Concoct a header for the recovery pass trace info.
   */
  // line 955 "../../../../RecoveryManager.ump"
   private String passStartHeader(int passNum){
    return "Recovery Pass " + passNum + " start: ";
  }


  /**
   * 
   * Concoct a header for the recovery pass trace info.
   */
  // line 962 "../../../../RecoveryManager.ump"
   private String passEndHeader(int passNum, long start, long end){
    return "Recovery Pass " + passNum + " end (" + (end - start) + "): ";
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled. This is used to construct verbose trace messages for individual log entry processing.
   */
  // line 971 "../../../../RecoveryManager.ump"
   private static  void trace(Level level, DatabaseImpl database, String debugType, boolean success, Node node, long logLsn, IN parent, boolean found, boolean replaced, boolean inserted, long replacedLsn, long abortLsn, int index){
    new RecoveryManager_trace(level, database, debugType, success, node, logLsn, parent, found, replaced, inserted,
		replacedLsn, abortLsn, index).execute();
  }

  // line 976 "../../../../RecoveryManager.ump"
   private void traceAndThrowException(long badLsn, String method, Exception origException) throws DatabaseException{
    String badLsnString = DbLsn.getNoFormatString(badLsn);
	Label577:           ;  //this.hook577(method, origException, badLsnString);
	throw new DatabaseException("last LSN=" + badLsnString, origException);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../RecoveryManager_static.ump"
  public static class TxnNodeId
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //TxnNodeId Attributes
    private long nodeId;
    private long txnId;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public TxnNodeId(long aNodeId, long aTxnId)
    {
      nodeId = aNodeId;
      txnId = aTxnId;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setNodeId(long aNodeId)
    {
      boolean wasSet = false;
      nodeId = aNodeId;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setTxnId(long aTxnId)
    {
      boolean wasSet = false;
      txnId = aTxnId;
      wasSet = true;
      return wasSet;
    }
  
    public long getNodeId()
    {
      return nodeId;
    }
  
    public long getTxnId()
    {
      return txnId;
    }
  
    public void delete()
    {}
  
    // line 8 "../../../../RecoveryManager_static.ump"
    public  TxnNodeId(long nodeId, long txnId){
      this.nodeId=nodeId;
          this.txnId=txnId;
    }
  
  
    /**
     * 
     * Compare two TxnNodeId objects
     */
    // line 15 "../../../../RecoveryManager_static.ump"
     public boolean equals(Object obj){
      if (this == obj) {
            return true;
          }
          if (!(obj instanceof TxnNodeId)) {
            return false;
          }
          return ((((TxnNodeId)obj).txnId == txnId) && (((TxnNodeId)obj).nodeId == nodeId));
    }
  
    // line 24 "../../../../RecoveryManager_static.ump"
     public int hashCode(){
      return (int)(txnId + nodeId);
    }
  
    // line 27 "../../../../RecoveryManager_static.ump"
     public String toString(){
      return "txnId=" + txnId + "/nodeId="+ nodeId;
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  import com.sleepycat.je.tree.*;
  
  // line 30 "../../../../RecoveryManager_static.ump"
  public static class RootDeleter implements WithRootLatched
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //RootDeleter Attributes
    private Tree tree;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RootDeleter(Tree aTree)
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
  
    // line 34 "../../../../RecoveryManager_static.ump"
    public  RootDeleter(Tree tree){
      this.tree=tree;
    }
  
  
    /**
     * 
     * @return true if the in-memory root was replaced.
     */
    // line 40 "../../../../RecoveryManager_static.ump"
     public IN doWork(ChildReference root) throws DatabaseException{
      tree.setRoot(null,false);
          return null;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "tree" + "=" + (getTree() != null ? !getTree().equals(this)  ? getTree().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 44 "../../../../RecoveryManager_static.ump"
  // line 4 "../../../../loggingBase_RecoveryManager_inner.ump"
  public static class RecoveryManager_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RecoveryManager_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 46 "../../../../RecoveryManager_static.ump"
    public  RecoveryManager_trace(Level level, DatabaseImpl database, String debugType, boolean success, Node node, long logLsn, IN parent, boolean found, boolean replaced, boolean inserted, long replacedLsn, long abortLsn, int index){
      this.level=level;
          this.database=database;
          this.debugType=debugType;
          this.success=success;
          this.node=node;
          this.logLsn=logLsn;
          this.parent=parent;
          this.found=found;
          this.replaced=replaced;
          this.inserted=inserted;
          this.replacedLsn=replacedLsn;
          this.abortLsn=abortLsn;
          this.index=index;
    }
  
    // line 61 "../../../../RecoveryManager_static.ump"
    public void execute(){
      // line 6 "../../../../loggingBase_RecoveryManager_inner.ump"
      logger=database.getDbEnvironment().getLogger();
              useLevel=level;
              if (!success) {
                useLevel=Level.SEVERE;
              }
              if (logger.isLoggable(useLevel)) {
                sb=new StringBuffer();
                sb.append(debugType);
                sb.append(" success=").append(success);
                sb.append(" node=");
                sb.append(node.getNodeId());
                sb.append(" lsn=");
                sb.append(DbLsn.getNoFormatString(logLsn));
                if (parent != null) {
                  sb.append(" parent=").append(parent.getNodeId());
                }
                sb.append(" found=");
                sb.append(found);
                sb.append(" replaced=");
                sb.append(replaced);
                sb.append(" inserted=");
                sb.append(inserted);
                if (replacedLsn != DbLsn.NULL_LSN) {
                  sb.append(" replacedLsn=");
                  sb.append(DbLsn.getNoFormatString(replacedLsn));
                }
                if (abortLsn != DbLsn.NULL_LSN) {
                  sb.append(" abortLsn=");
                  sb.append(DbLsn.getNoFormatString(abortLsn));
                }
                sb.append(" index=").append(index);
                logger.log(useLevel,sb.toString());
              }
              //original();
      // END OF UMPLE BEFORE INJECTION
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 62 "../../../../RecoveryManager_static.ump"
    protected Level level ;
  // line 63 "../../../../RecoveryManager_static.ump"
    protected DatabaseImpl database ;
  // line 64 "../../../../RecoveryManager_static.ump"
    protected String debugType ;
  // line 65 "../../../../RecoveryManager_static.ump"
    protected boolean success ;
  // line 66 "../../../../RecoveryManager_static.ump"
    protected Node node ;
  // line 67 "../../../../RecoveryManager_static.ump"
    protected long logLsn ;
  // line 68 "../../../../RecoveryManager_static.ump"
    protected IN parent ;
  // line 69 "../../../../RecoveryManager_static.ump"
    protected boolean found ;
  // line 70 "../../../../RecoveryManager_static.ump"
    protected boolean replaced ;
  // line 71 "../../../../RecoveryManager_static.ump"
    protected boolean inserted ;
  // line 72 "../../../../RecoveryManager_static.ump"
    protected long replacedLsn ;
  // line 73 "../../../../RecoveryManager_static.ump"
    protected long abortLsn ;
  // line 74 "../../../../RecoveryManager_static.ump"
    protected int index ;
  // line 75 "../../../../RecoveryManager_static.ump"
    protected Logger logger ;
  // line 76 "../../../../RecoveryManager_static.ump"
    protected Level useLevel ;
  // line 77 "../../../../RecoveryManager_static.ump"
    protected StringBuffer sb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 49 "../../../../RecoveryManager.ump"
  private static final String TRACE_DUP_ROOT_REPLACE = "DupRootRecover:" ;
// line 51 "../../../../RecoveryManager.ump"
  private static final String TRACE_LN_REDO = "LNRedo:" ;
// line 53 "../../../../RecoveryManager.ump"
  private static final String TRACE_LN_UNDO = "LNUndo" ;
// line 55 "../../../../RecoveryManager.ump"
  private static final String TRACE_IN_REPLACE = "INRecover:" ;
// line 57 "../../../../RecoveryManager.ump"
  private static final String TRACE_ROOT_REPLACE = "RootRecover:" ;
// line 59 "../../../../RecoveryManager.ump"
  private static final String TRACE_IN_DEL_REPLAY = "INDelReplay:" ;
// line 61 "../../../../RecoveryManager.ump"
  private static final String TRACE_IN_DUPDEL_REPLAY = "INDupDelReplay:" ;
// line 63 "../../../../RecoveryManager.ump"
  private static final String TRACE_ROOT_DELETE = "RootDelete:" ;
// line 65 "../../../../RecoveryManager.ump"
  private static final int CLEAR_INCREMENT = 50 ;
// line 67 "../../../../RecoveryManager.ump"
  private EnvironmentImpl env ;
// line 69 "../../../../RecoveryManager.ump"
  private int readBufferSize ;
// line 71 "../../../../RecoveryManager.ump"
  private RecoveryInfo info ;
// line 73 "../../../../RecoveryManager.ump"
  private Set committedTxnIds ;
// line 75 "../../../../RecoveryManager.ump"
  private Set abortedTxnIds ;
// line 77 "../../../../RecoveryManager.ump"
  private Map preparedTxns ;
// line 79 "../../../../RecoveryManager.ump"
  private Set inListRebuildDbIds ;
// line 81 "../../../../RecoveryManager.ump"
  private Level detailedTraceLevel ;
// line 83 "../../../../RecoveryManager.ump"
  private Map fileSummaryLsns ;
// line 85 "../../../../RecoveryManager.ump"
  private int inListClearCounter ;

  
}