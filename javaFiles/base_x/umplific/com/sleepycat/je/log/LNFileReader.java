/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.TxnPrepare;
import com.sleepycat.je.txn.TxnCommit;
import com.sleepycat.je.txn.TxnAbort;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.log.entry.LNLogEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import javax.transaction.xa.Xid;
import java.util.Map;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../LNFileReader.ump"
public class LNFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LNFileReader()
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
   * Create this reader to start at a given LSN.
   * @param env The relevant EnvironmentImpl
   * @param readBufferSize buffer size in bytes for reading in log
   * @param startLsn where to start in the log
   * @param redo If false, we're going to go forward fromthe start LSN to the end of the log. If true, we're going backwards from the end of the log to the start LSN. 
   * @param endOfFileLsn the virtual LSN that marks the end of the log. (Theone off the end of the log). Only used if we're reading backwards. Different from the startLsn because the startLsn tells us where the beginning of the start entry is, but not the length/end of the start entry. May be null if we're going foward.
   * @param finishLsn the last LSN to read in the log. May be null if wewant to read to the end of the log.
   */
  // line 36 "../../../../LNFileReader.ump"
   public  LNFileReader(EnvironmentImpl env, int readBufferSize, long startLsn, boolean redo, long endOfFileLsn, long finishLsn, Long singleFileNum) throws IOException,DatabaseException{
    super(env, readBufferSize, redo, startLsn, singleFileNum, endOfFileLsn, finishLsn);
	targetEntryMap = new HashMap();
  }

  // line 41 "../../../../LNFileReader.ump"
   public void addTargetType(LogEntryType entryType) throws DatabaseException{
    targetEntryMap.put(entryType, entryType.getNewLogEntry());
  }


  /**
   * 
   * @return true if this is a transactional LN or Locker Commit entry.
   */
  // line 48 "../../../../LNFileReader.ump"
   protected boolean isTargetEntry(byte entryTypeNum, byte entryTypeVersion){
    if (LogEntryType.isProvisional(entryTypeVersion)) {
	    targetLogEntry = null;
	} else {
	    LogEntryType fromLogType = new LogEntryType(entryTypeNum, entryTypeVersion);
	    targetLogEntry = (LogEntry) targetEntryMap.get(fromLogType);
	}
	return (targetLogEntry != null);
  }


  /**
   * 
   * This reader instantiates an LN and key for every LN entry.
   */
  // line 61 "../../../../LNFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    targetLogEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
	return true;
  }


  /**
   * 
   * @return true if the last entry was an LN.
   */
  // line 69 "../../../../LNFileReader.ump"
   public boolean isLN(){
    return (targetLogEntry instanceof LNLogEntry);
  }


  /**
   * 
   * Get the last LN seen by the reader.
   */
  // line 76 "../../../../LNFileReader.ump"
   public LN getLN(){
    return ((LNLogEntry) targetLogEntry).getLN();
  }


  /**
   * 
   * Get the last databaseId seen by the reader.
   */
  // line 83 "../../../../LNFileReader.ump"
   public DatabaseId getDatabaseId(){
    return ((LNLogEntry) targetLogEntry).getDbId();
  }


  /**
   * 
   * Get the last key seen by the reader.
   */
  // line 90 "../../../../LNFileReader.ump"
   public byte[] getKey(){
    return ((LNLogEntry) targetLogEntry).getKey();
  }


  /**
   * 
   * Get the last key seen by the reader.
   */
  // line 97 "../../../../LNFileReader.ump"
   public byte[] getDupTreeKey(){
    return ((LNLogEntry) targetLogEntry).getDupKey();
  }


  /**
   * 
   * @return the transaction id of the current entry.
   */
  // line 104 "../../../../LNFileReader.ump"
   public Long getTxnId(){
    return ((LNLogEntry) targetLogEntry).getTxnId();
  }

  // line 108 "../../../../LNFileReader.ump"
   public boolean isPrepare(){
    return (targetLogEntry.getMainItem() instanceof TxnPrepare);
  }


  /**
   * 
   * Get the last txn prepare id seen by the reader.
   */
  // line 115 "../../../../LNFileReader.ump"
   public long getTxnPrepareId(){
    return ((TxnPrepare) targetLogEntry.getMainItem()).getId();
  }


  /**
   * 
   * Get the last txn prepare Xid seen by the reader.
   */
  // line 122 "../../../../LNFileReader.ump"
   public Xid getTxnPrepareXid(){
    return ((TxnPrepare) targetLogEntry.getMainItem()).getXid();
  }

  // line 126 "../../../../LNFileReader.ump"
   public boolean isAbort(){
    return (targetLogEntry.getMainItem() instanceof TxnAbort);
  }


  /**
   * 
   * Get the last txn abort id seen by the reader.
   */
  // line 133 "../../../../LNFileReader.ump"
   public long getTxnAbortId(){
    return ((TxnAbort) targetLogEntry.getMainItem()).getId();
  }


  /**
   * 
   * Get the last txn commit id seen by the reader.
   */
  // line 140 "../../../../LNFileReader.ump"
   public long getTxnCommitId(){
    return ((TxnCommit) targetLogEntry.getMainItem()).getId();
  }


  /**
   * 
   * Get node id of current LN.
   */
  // line 147 "../../../../LNFileReader.ump"
   public long getNodeId(){
    return ((LNLogEntry) targetLogEntry).getLN().getNodeId();
  }


  /**
   * 
   * Get last abort LSN seen by the reader (may be null).
   */
  // line 154 "../../../../LNFileReader.ump"
   public long getAbortLsn(){
    return ((LNLogEntry) targetLogEntry).getAbortLsn();
  }


  /**
   * 
   * Get last abort known deleted seen by the reader.
   */
  // line 161 "../../../../LNFileReader.ump"
   public boolean getAbortKnownDeleted(){
    return ((LNLogEntry) targetLogEntry).getAbortKnownDeleted();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 21 "../../../../LNFileReader.ump"
  protected Map targetEntryMap ;
// line 23 "../../../../LNFileReader.ump"
  protected LogEntry targetLogEntry ;

  
}