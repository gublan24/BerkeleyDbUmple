/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.util.DbDump;
import com.sleepycat.je.tree.NameLN;
import com.sleepycat.je.tree.MapLN;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.log.ScavengerFileReader;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.log.LastFileReader;
import com.sleepycat.je.log.FileManager;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.Environment;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Date;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import com.sleepycat.je.util.*;

// line 3 "../../../../DbScavenger.ump"
public class DbScavenger extends DbDump
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbScavenger()
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

  // line 56 "../../../../DbScavenger.ump"
   public  DbScavenger(Environment env, PrintStream outputFile, String outputDirectory, boolean formatUsingPrintable, boolean doAggressiveScavengerRun, boolean verbose){
    super(env, null, outputFile, outputDirectory, formatUsingPrintable);
	this.doAggressiveScavengerRun = doAggressiveScavengerRun;
	this.dbIdToName = new HashMap();
	this.dbIdToDupSort = new HashMap();
	this.dbIdToOutputStream = new HashMap();
	this.verbose = verbose;
  }


  /**
   * 
   * Set to true if corrupted boundaries should be dumped out.
   */
  // line 68 "../../../../DbScavenger.ump"
   public void setDumpCorruptedBounds(boolean dumpCorruptedBounds){
    this.dumpCorruptedBounds = dumpCorruptedBounds;
  }

  // line 72 "../../../../DbScavenger.ump"
   public void dump() throws IOException,DatabaseException{
    openEnv(false);
	envImpl = DbInternal.envGetEnvironmentImpl(env);
	DbConfigManager cm = envImpl.getConfigManager();
	try {
	    readBufferSize = cm.getInt(EnvironmentParams.LOG_ITERATOR_READ_SIZE);
	} catch (DatabaseException DBE) {
	    readBufferSize = 8192;
	}
	LastFileReader reader = new LastFileReader(envImpl, readBufferSize);
	while (reader.readNextEntry()) {
	}
	long lastUsedLsn = reader.getLastValidLsn();
	long nextAvailableLsn = reader.getEndOfLog();
	envImpl.getFileManager().setLastPosition(nextAvailableLsn, lastUsedLsn, reader.getPrevOffset());
	try {
	    if (verbose) {
		System.out.println("Pass 1: " + new Date());
	    }
	    scavengeDbTree(lastUsedLsn, nextAvailableLsn);
	    if (verbose) {
		System.out.println("Pass 2: " + new Date());
	    }
	    scavenge(lastUsedLsn, nextAvailableLsn);
	    if (verbose) {
		System.out.println("End: " + new Date());
	    }
	} finally {
	    closeOutputStreams();
	}
  }

  // line 104 "../../../../DbScavenger.ump"
   private void scavengeDbTree(long lastUsedLsn, long nextAvailableLsn) throws IOException,DatabaseException{
    committedTxnIdsSeen = new BitMap();
	nodeIdsSeen = new BitMap();
	final ScavengerFileReader scavengerReader = new ScavengerFileReader(envImpl, readBufferSize, lastUsedLsn,
		DbLsn.NULL_LSN, nextAvailableLsn) {
	    protected void processEntryCallback(LogEntry entry, LogEntryType entryType) throws DatabaseException {
		processDbTreeEntry(entry, entryType);
	    }
	};
	scavengerReader.setTargetType(LogEntryType.LOG_MAPLN_TRANSACTIONAL);
	scavengerReader.setTargetType(LogEntryType.LOG_MAPLN);
	scavengerReader.setTargetType(LogEntryType.LOG_NAMELN_TRANSACTIONAL);
	scavengerReader.setTargetType(LogEntryType.LOG_NAMELN);
	scavengerReader.setTargetType(LogEntryType.LOG_TXN_COMMIT);
	scavengerReader.setTargetType(LogEntryType.LOG_TXN_ABORT);
	lastTime = System.currentTimeMillis();
	long fileNum = -1;
	while (scavengerReader.readNextEntry()) {
	    fileNum = reportProgress(fileNum, scavengerReader.getLastLsn());
	}
  }

  // line 126 "../../../../DbScavenger.ump"
   private long reportProgress(long fileNum, long lastLsn){
    long currentFile = DbLsn.getFileNumber(lastLsn);
	if (verbose) {
	    if (currentFile != fileNum) {
		long now = System.currentTimeMillis();
		System.out.println(
			"processing file " + FileManager.getFileName(currentFile, ".jdb  ") + (now - lastTime) + " ms");
		lastTime = now;
	    }
	}
	return currentFile;
  }

  // line 139 "../../../../DbScavenger.ump"
   private boolean checkProcessEntry(LogEntry entry, LogEntryType entryType, boolean pass2){
    boolean isTransactional = entry.isTransactional();
	if (isTransactional) {
	    long txnId = entry.getTransactionId();
	    if (entryType.equals(LogEntryType.LOG_TXN_COMMIT)) {
		committedTxnIdsSeen.set(txnId);
		return false;
	    }
	    if (entryType.equals(LogEntryType.LOG_TXN_ABORT)) {
		return false;
	    }
	    if (!committedTxnIdsSeen.get(txnId)) {
		return false;
	    }
	}
	if (entry instanceof LNLogEntry) {
	    LNLogEntry lnEntry = (LNLogEntry) entry;
	    LN ln = lnEntry.getLN();
	    long nodeId = ln.getNodeId();
	    boolean isDelDupLN = entryType.equals(LogEntryType.LOG_DEL_DUPLN_TRANSACTIONAL)
		    || entryType.equals(LogEntryType.LOG_DEL_DUPLN);
	    if (pass2 && doAggressiveScavengerRun) {
		return !isDelDupLN;
	    }
	    if (nodeIdsSeen.get(nodeId)) {
		return false;
	    } else {
		nodeIdsSeen.set(nodeId);
		if (isDelDupLN) {
		    return false;
		} else {
		    return true;
		}
	    }
	}
	return false;
  }

  // line 177 "../../../../DbScavenger.ump"
   private void processDbTreeEntry(LogEntry entry, LogEntryType entryType) throws DatabaseException{
    boolean processThisEntry = checkProcessEntry(entry, entryType, false);
	if (processThisEntry && (entry instanceof LNLogEntry)) {
	    LNLogEntry lnEntry = (LNLogEntry) entry;
	    LN ln = lnEntry.getLN();
	    if (ln instanceof NameLN) {
		String name = new String(lnEntry.getKey());
		Integer dbId = new Integer(((NameLN) ln).getId().getId());
		if (dbIdToName.containsKey(dbId) && !((String) dbIdToName.get(dbId)).equals(name)) {
		    throw new DatabaseException("Already name mapped for dbId: " + dbId + " changed from "
			    + (String) dbIdToName.get(dbId) + " to " + name);
		} else {
		    dbIdToName.put(dbId, name);
		}
	    }
	    if (ln instanceof MapLN) {
		DatabaseImpl db = ((MapLN) ln).getDatabase();
		Integer dbId = new Integer(db.getId().getId());
		Boolean dupSort = Boolean.valueOf(db.getSortedDuplicates());
		if (dbIdToDupSort.containsKey(dbId)) {
		    throw new DatabaseException("Already saw dupSort entry for dbId: " + dbId);
		} else {
		    dbIdToDupSort.put(dbId, dupSort);
		}
	    }
	}
  }

  // line 205 "../../../../DbScavenger.ump"
   private void scavenge(long lastUsedLsn, long nextAvailableLsn) throws IOException,DatabaseException{
    final ScavengerFileReader scavengerReader = new ScavengerFileReader(envImpl, readBufferSize, lastUsedLsn,
		DbLsn.NULL_LSN, nextAvailableLsn) {
	    protected void processEntryCallback(LogEntry entry, LogEntryType entryType) throws DatabaseException {
		processRegularEntry(entry, entryType);
	    }
	};
	scavengerReader.setTargetType(LogEntryType.LOG_LN_TRANSACTIONAL);
	scavengerReader.setTargetType(LogEntryType.LOG_LN);
	scavengerReader.setTargetType(LogEntryType.LOG_DEL_DUPLN_TRANSACTIONAL);
	scavengerReader.setTargetType(LogEntryType.LOG_DEL_DUPLN);
	scavengerReader.setDumpCorruptedBounds(dumpCorruptedBounds);
	long progressFileNum = -1;
	while (scavengerReader.readNextEntry()) {
	    progressFileNum = reportProgress(progressFileNum, scavengerReader.getLastLsn());
	}
  }

  // line 223 "../../../../DbScavenger.ump"
   private void processRegularEntry(LogEntry entry, LogEntryType entryType) throws DatabaseException{
    boolean processThisEntry = checkProcessEntry(entry, entryType, true);
	if (processThisEntry) {
	    LNLogEntry lnEntry = (LNLogEntry) entry;
	    Integer dbId = new Integer(lnEntry.getDbId().getId());
	    PrintStream out = getOutputStream(dbId);
	    LN ln = lnEntry.getLN();
	    byte[] keyData = lnEntry.getKey();
	    byte[] data = ln.getData();
	    if (data != null) {
		dumpOne(out, keyData, formatUsingPrintable);
		dumpOne(out, data, formatUsingPrintable);
		if ((++flushCounter % FLUSH_INTERVAL) == 0) {
		    out.flush();
		    flushCounter = 0;
		}
	    }
	}
  }

  // line 243 "../../../../DbScavenger.ump"
   private PrintStream getOutputStream(Integer dbId) throws DatabaseException{
    try {
	    PrintStream ret = (PrintStream) dbIdToOutputStream.get(dbId);
	    if (ret != null) {
		return ret;
	    }
	    String name = (String) dbIdToName.get(dbId);
	    if (name == null) {
		name = "db" + dbId;
	    }
	    File file = new File(outputDirectory, name + ".dump");
	    ret = new PrintStream(new FileOutputStream(file), false);
	    dbIdToOutputStream.put(dbId, ret);
	    Boolean dupSort = (Boolean) dbIdToDupSort.get(dbId);
	    if (dupSort == null) {
		dupSort = Boolean.valueOf(false);
	    }
	    printHeader(ret, dupSort.booleanValue(), formatUsingPrintable);
	    return ret;
	} catch (IOException IOE) {
	    throw new DatabaseException(IOE);
	}
  }

  // line 267 "../../../../DbScavenger.ump"
   private void closeOutputStreams(){
    Iterator iter = dbIdToOutputStream.values().iterator();
	while (iter.hasNext()) {
	    PrintStream s = (PrintStream) iter.next();
	    s.println("DATA=END");
	    s.close();
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 32 "../../../../DbScavenger.ump"
  private static final int FLUSH_INTERVAL = 100 ;
// line 34 "../../../../DbScavenger.ump"
  private int readBufferSize ;
// line 36 "../../../../DbScavenger.ump"
  private EnvironmentImpl envImpl ;
// line 38 "../../../../DbScavenger.ump"
  private BitMap committedTxnIdsSeen ;
// line 40 "../../../../DbScavenger.ump"
  private BitMap nodeIdsSeen ;
// line 42 "../../../../DbScavenger.ump"
  private Map dbIdToName ;
// line 44 "../../../../DbScavenger.ump"
  private Map dbIdToDupSort ;
// line 46 "../../../../DbScavenger.ump"
  private Map dbIdToOutputStream ;
// line 48 "../../../../DbScavenger.ump"
  private boolean dumpCorruptedBounds = false ;
// line 50 "../../../../DbScavenger.ump"
  private int flushCounter = 0 ;
// line 52 "../../../../DbScavenger.ump"
  private long lastTime ;

  
}