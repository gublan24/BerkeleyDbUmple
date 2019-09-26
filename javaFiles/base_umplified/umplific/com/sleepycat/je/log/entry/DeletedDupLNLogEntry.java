/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log.entry;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Txn;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.Key;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;

// line 3 "../../../../../DeletedDupLNLogEntry.ump"
public class DeletedDupLNLogEntry extends LNLogEntry
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DeletedDupLNLogEntry()
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
   * Constructor to read an entry.
   */
  // line 21 "../../../../../DeletedDupLNLogEntry.ump"
   public  DeletedDupLNLogEntry(boolean isTransactional){
    super(com.sleepycat.je.tree.LN.class, isTransactional);
  }


  /**
   * 
   * Constructor to make an object that can write this entry.
   */
  // line 29 "../../../../../DeletedDupLNLogEntry.ump"
   public  DeletedDupLNLogEntry(LogEntryType entryType, LN ln, DatabaseId dbId, byte [] key, byte [] dataAsKey, long abortLsn, boolean abortKnownDeleted, Txn txn){
    super(entryType, ln, dbId, key, abortLsn, abortKnownDeleted, txn);
	this.dataAsKey = dataAsKey;
  }


  /**
   * 
   * Extends its super class to read in the extra dup key.
   * @see LNLogEntry#readEntry
   */
  // line 39 "../../../../../DeletedDupLNLogEntry.ump"
   public void readEntry(ByteBuffer entryBuffer, int entrySize, byte entryTypeVersion, boolean readFullItem) throws DatabaseException{
    super.readEntry(entryBuffer, entrySize, entryTypeVersion, readFullItem);
	if (readFullItem) {
	    dataAsKey = LogUtils.readByteArray(entryBuffer);
	} else {
	    dataAsKey = null;
	}
  }


  /**
   * 
   * Extends super class to dump out extra key.
   * @see LNLogEntry#dumpEntry
   */
  // line 52 "../../../../../DeletedDupLNLogEntry.ump"
   public StringBuffer dumpEntry(StringBuffer sb, boolean verbose){
    super.dumpEntry(sb, verbose);
	sb.append(Key.dumpString(dataAsKey, 0));
	return sb;
  }


  /**
   * 
   * Extend super class to add in extra key.
   * @see LNLogEntry#getLogSize
   */
  // line 62 "../../../../../DeletedDupLNLogEntry.ump"
   public int getLogSize(){
    return super.getLogSize() + LogUtils.getByteArrayLogSize(dataAsKey);
  }


  /**
   * 
   * @see LNLogEntry#writeToLog
   */
  // line 69 "../../../../../DeletedDupLNLogEntry.ump"
   public void writeToLog(ByteBuffer destBuffer){
    super.writeToLog(destBuffer);
	LogUtils.writeByteArray(destBuffer, dataAsKey);
  }


  /**
   * 
   * Get the data-as-key out of the entry.
   */
  // line 77 "../../../../../DeletedDupLNLogEntry.ump"
   public byte[] getDupKey(){
    return dataAsKey;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../../DeletedDupLNLogEntry.ump"
  private byte[] dataAsKey ;

  
}