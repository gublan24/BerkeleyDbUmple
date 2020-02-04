/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.util.List;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../BINDelta.ump"
public class BINDelta implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BINDelta()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Read a BIN and create the deltas.
   */
  // line 32 "../../../../BINDelta.ump"
   public  BINDelta(BIN bin){
    lastFullLsn = bin.getLastFullVersion();
	dbId = bin.getDatabaseId();
	deltas = new ArrayList();
	logEntryType = bin.getBINDeltaType();
	for (int i = 0; i < bin.getNEntries(); i++) {
	    if (bin.isDirty(i)) {
		deltas.add(new DeltaInfo(bin.getKey(i), bin.getLsn(i), bin.getState(i)));
	    }
	}
  }


  /**
   * 
   * For instantiating from the log.
   */
  // line 47 "../../../../BINDelta.ump"
   public  BINDelta(){
    dbId = new DatabaseId();
	lastFullLsn = DbLsn.NULL_LSN;
	deltas = new ArrayList();
  }


  /**
   * 
   * @return a count of deltas for this BIN.
   */
  // line 56 "../../../../BINDelta.ump"
  public int getNumDeltas(){
    return deltas.size();
  }


  /**
   * 
   * @return the dbId for this BIN.
   */
  // line 63 "../../../../BINDelta.ump"
   public DatabaseId getDbId(){
    return dbId;
  }


  /**
   * 
   * @return the last full version of this BIN
   */
  // line 70 "../../../../BINDelta.ump"
   public long getLastFullLsn(){
    return lastFullLsn;
  }


  /**
   * 
   * Create a BIN by starting with the full version and applying the deltas.
   */
  // line 77 "../../../../BINDelta.ump"
   public BIN reconstituteBIN(EnvironmentImpl env) throws DatabaseException{
    BIN fullBIN = (BIN) env.getLogManager().get(lastFullLsn);
	DatabaseImpl db = env.getDbMapTree().getDb(dbId);
	fullBIN.setDatabase(db);
	fullBIN.setLastFullLsn(lastFullLsn);
	Label612: //this.hook612(fullBIN);
	for (int i = 0; i < deltas.size(); i++) {
	    DeltaInfo info = (DeltaInfo) deltas.get(i);
	    int foundIndex = fullBIN.findEntry(info.getKey(), true, false);
	    if (foundIndex >= 0 && (foundIndex & IN.EXACT_MATCH) != 0) {
		foundIndex &= ~IN.EXACT_MATCH;
		if (info.isKnownDeleted()) {
		    fullBIN.setKnownDeleted(foundIndex);
		} else {
		    fullBIN.updateEntry(foundIndex, info.getLsn(), info.getState());
		}
	    } else {
		if (!info.isKnownDeleted()) {
		    ChildReference entry = new ChildReference(null, info.getKey(), info.getLsn(), info.getState());
		    boolean insertOk = fullBIN.insertEntry(entry);
		    assert insertOk;
		}
	    }
	}
	fullBIN.setGeneration(0);
	Label611: //this.hook611(fullBIN);
	return fullBIN;
  }

  // line 106 "../../../../BINDelta.ump"
   public LogEntryType getLogType(){
    return logEntryType;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 113 "../../../../BINDelta.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 120 "../../../../BINDelta.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }

  // line 124 "../../../../BINDelta.ump"
   public void postLogWork(long justLoggedLsn){
    
  }

  // line 127 "../../../../BINDelta.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    dbId.readFromLog(itemBuffer, entryTypeVersion);
	lastFullLsn = LogUtils.readLong(itemBuffer);
	int numDeltas = LogUtils.readInt(itemBuffer);
	for (int i = 0; i < numDeltas; i++) {
	    DeltaInfo info = new DeltaInfo();
	    info.readFromLog(itemBuffer, entryTypeVersion);
	    deltas.add(info);
	}
  }

  // line 138 "../../../../BINDelta.ump"
   public int getLogSize(){
    int size = dbId.getLogSize() + LogUtils.LONG_BYTES + LogUtils.INT_BYTES;
	for (int i = 0; i < deltas.size(); i++) {
	    DeltaInfo info = (DeltaInfo) deltas.get(i);
	    size += info.getLogSize();
	}
	return size;
  }

  // line 147 "../../../../BINDelta.ump"
   public void writeToLog(ByteBuffer logBuffer){
    dbId.writeToLog(logBuffer);
	LogUtils.writeLong(logBuffer, lastFullLsn);
	LogUtils.writeInt(logBuffer, deltas.size());
	for (int i = 0; i < deltas.size(); i++) {
	    DeltaInfo info = (DeltaInfo) deltas.get(i);
	    info.writeToLog(logBuffer);
	}
  }

  // line 157 "../../../../BINDelta.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    dbId.dumpLog(sb, verbose);
	sb.append("<lastFullLsn>");
	sb.append(DbLsn.toString(lastFullLsn));
	sb.append("</lastFullLsn>");
	sb.append("<deltas size=\"").append(deltas.size()).append("\"/>");
	for (int i = 0; i < deltas.size(); i++) {
	    DeltaInfo info = (DeltaInfo) deltas.get(i);
	    info.dumpLog(sb, verbose);
	}
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 172 "../../../../BINDelta.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 179 "../../../../BINDelta.ump"
   public long getTransactionId(){
    return 0;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 20 "../../../../BINDelta.ump"
  private DatabaseId dbId ;
// line 22 "../../../../BINDelta.ump"
  private long lastFullLsn ;
// line 24 "../../../../BINDelta.ump"
  private List deltas ;
// line 26 "../../../../BINDelta.ump"
  private LogEntryType logEntryType ;

  
}