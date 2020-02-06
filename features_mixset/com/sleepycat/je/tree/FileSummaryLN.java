/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.cleaner.TrackedFileSummary;
import com.sleepycat.je.cleaner.PackedOffsets;
import com.sleepycat.je.cleaner.FileSummary;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import java.io.UnsupportedEncodingException;

// line 3 "../../../../FileSummaryLN.ump"
public class FileSummaryLN extends LN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FileSummaryLN Attributes
  private FileSummary baseSummary;
  private PackedOffsets obsoleteOffsets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileSummaryLN(long aNodeId)
  {
    super(aNodeId);
    baseSummary = new FileSummary();
    obsoleteOffsets = new PackedOffsets();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBaseSummary(FileSummary aBaseSummary)
  {
    boolean wasSet = false;
    baseSummary = aBaseSummary;
    wasSet = true;
    return wasSet;
  }

  public boolean setObsoleteOffsets(PackedOffsets aObsoleteOffsets)
  {
    boolean wasSet = false;
    obsoleteOffsets = aObsoleteOffsets;
    wasSet = true;
    return wasSet;
  }

  public FileSummary getBaseSummary()
  {
    return baseSummary;
  }

  public PackedOffsets getObsoleteOffsets()
  {
    return obsoleteOffsets;
  }

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * Creates a new LN with a given base summary.
   */
  // line 36 "../../../../FileSummaryLN.ump"
   public  FileSummaryLN(FileSummary baseSummary){
    super(new byte[0]);
	assert baseSummary != null;
	this.baseSummary = baseSummary;
	obsoleteOffsets = new PackedOffsets();
	logVersion = -1;
  }


  /**
   * 
   * Creates an empty LN to be filled in from the log.
   * public FileSummaryLN() throws DatabaseException {
   * baseSummary = new FileSummary();
   * obsoleteOffsets = new PackedOffsets();
   * }
   * 
   * Sets the live summary object that will be added to the base summary at the time the LN is logged.
   */
  // line 55 "../../../../FileSummaryLN.ump"
   public void setTrackedSummary(TrackedFileSummary trackedSummary){
    this.trackedSummary = trackedSummary;
	needOffsets = true;
  }


  /**
   * 
   * Returns the tracked summary, or null if setTrackedSummary was not called.
   */
  // line 63 "../../../../FileSummaryLN.ump"
   public TrackedFileSummary getTrackedSummary(){
    return trackedSummary;
  }


  /**
   * 
   * Returns true if the given key for this LN is a String file number key. For the old version of the LN there will be a single record per file. If this is a version 0 log entry, the key is a string.  However, such an LN may be migrated by the cleaner, in which case the version will be 1 or greater [#13061].  In the latter case, we can distinguish a string key by: 1) If the key is not 8 bytes long, it has to be a string key. 2) If the key is 8 bytes long, but bytes[4] is ascii "0" to "9", then it must be a string key.  bytes[4] to bytes[7] are a sequence number that is the number of log entries counted.  For this number to be greater than 0x30000000, the binary value of 4 digits starting with ascii "0", over 400 million log entries would have to occur in a single file; this should never happen. Note that having to rely on method (2) is unlikely.  A string key will only be 8 bytes if the file number reach 8 decimal digits (10,000,000 to 99,999,999).  This is a very large file number and unlikely to have occurred using JE 1.7.1 or earlier. In summary, the only time the algorithm here could fail is if there were more than 400 million log entries per file, and more than 10 million were written with JE 1.7.1 or earlier.
   */
  // line 82 "../../../../FileSummaryLN.ump"
   public boolean hasStringKey(byte [] bytes){
    if (logVersion == 0 || bytes.length != 8) {
	    return true;
	} else {
	    return (bytes[4] >= '0' && bytes[4] <= '9');
	}
  }


  /**
   * 
   * Convert a FileSummaryLN key from a byte array to a long.  The file number is the first 4 bytes of the key.
   */
  // line 93 "../../../../FileSummaryLN.ump"
   public long getFileNumber(byte [] bytes){
    if (hasStringKey(bytes)) {
	    try {
		return Long.valueOf(new String(bytes, "UTF-8")).longValue();
	    } catch (UnsupportedEncodingException shouldNeverHappen) {
		assert false : shouldNeverHappen;
		return 0;
	    }
	} else {
	    ByteBuffer buf = ByteBuffer.wrap(bytes);
	    return LogUtils.readIntMSB(buf) & 0xFFFFFFFFL;
	}
  }


  /**
   * 
   * Returns the first 4 bytes of the key for the given file number.  This can be used to do a range search to find the first LN for the file.
   */
  // line 110 "../../../../FileSummaryLN.ump"
   public static  byte[] makePartialKey(long fileNum){
    byte[] bytes = new byte[4];
	ByteBuffer buf = ByteBuffer.wrap(bytes);
	LogUtils.writeIntMSB(buf, (int) fileNum);
	return bytes;
  }


  /**
   * 
   * Returns the full two-part key for a given file number and unique sequence.  This can be used to insert a new LN.
   * @param sequence is a unique identifier for the LN for the given file,and must be greater than the last sequence.
   */
  // line 121 "../../../../FileSummaryLN.ump"
   public static  byte[] makeFullKey(long fileNum, int sequence){
    assert sequence >= 0;
	byte[] bytes = new byte[8];
	ByteBuffer buf = ByteBuffer.wrap(bytes);
	LogUtils.writeIntMSB(buf, (int) fileNum);
	LogUtils.writeIntMSB(buf, Integer.MAX_VALUE - sequence);
	return bytes;
  }


  /**
   * 
   * Initialize a node that has been faulted in from the log.  If this FSLN contains version 1 offsets that can be incorrect when RMW was used, and if je.cleaner.rmwFix is enabled, discard the offsets.  [#13158]
   */
  // line 133 "../../../../FileSummaryLN.ump"
   public void postFetchInit(DatabaseImpl db, long sourceLsn) throws DatabaseException{
    super.postFetchInit(db, sourceLsn);
	if (logVersion == 1 && db.getDbEnvironment().getUtilizationProfile().isRMWFixEnabled()) {
	    obsoleteOffsets = new PackedOffsets();
	}
  }

  // line 140 "../../../../FileSummaryLN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 144 "../../../../FileSummaryLN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 148 "../../../../FileSummaryLN.ump"
   public String endTag(){
    return END_TAG;
  }

  // line 152 "../../../../FileSummaryLN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	sb.append(super.dumpString(nSpaces, dumpTags));
	sb.append('\n');
	if (!isDeleted()) {
	    sb.append(baseSummary.toString());
	    sb.append(obsoleteOffsets.toString());
	}
	return sb.toString();
  }


  /**
   * 
   * Dump additional fields. Done this way so the additional info can be within the XML tags defining the dumped log entry.
   */
  // line 166 "../../../../FileSummaryLN.ump"
   protected void dumpLogAdditional(StringBuffer sb, boolean verbose){
    if (!isDeleted()) {
	    baseSummary.dumpLog(sb, true);
	    if (verbose) {
		obsoleteOffsets.dumpLog(sb, true);
	    }
	}
  }


  /**
   * 
   * Log type for transactional entries.
   */
  // line 178 "../../../../FileSummaryLN.ump"
   protected LogEntryType getTransactionalLogType(){
    assert false : "Txnl access to UP db not allowed";
	return LogEntryType.LOG_FILESUMMARYLN;
  }


  /**
   * 
   * @see LN#getLogType
   */
  // line 186 "../../../../FileSummaryLN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_FILESUMMARYLN;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchFileSummaryLNs must be marshalled within the log write latch, becausethat critical section is used to guarantee that all previous log entries are reflected in the summary.
   */
  // line 193 "../../../../FileSummaryLN.ump"
   public boolean marshallOutsideWriteLatch(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 200 "../../../../FileSummaryLN.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LN#getLogSize
   */
  // line 207 "../../../../FileSummaryLN.ump"
   public int getLogSize(){
    int size = super.getLogSize();
	if (!isDeleted()) {
	    size += baseSummary.getLogSize();
	    getOffsets();
	    size += obsoleteOffsets.getLogSize();
	}
	return size;
  }


  /**
   * 
   * @see LN#writeToLog
   */
  // line 220 "../../../../FileSummaryLN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    if (trackedSummary != null) {
	    baseSummary.add(trackedSummary);
	    if (!isDeleted()) {
		getOffsets();
	    }
	    trackedSummary.reset();
	}
	super.writeToLog(logBuffer);
	if (!isDeleted()) {
	    baseSummary.writeToLog(logBuffer);
	    obsoleteOffsets.writeToLog(logBuffer);
	}
  }


  /**
   * 
   * @see LN#readFromLog
   */
  // line 238 "../../../../FileSummaryLN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	logVersion = entryTypeVersion;
	if (!isDeleted()) {
	    baseSummary.readFromLog(itemBuffer, entryTypeVersion);
	    if (entryTypeVersion > 0) {
		obsoleteOffsets.readFromLog(itemBuffer, entryTypeVersion);
	    }
	}
  }


  /**
   * 
   * If tracked offsets may be present, get them so they are ready to be written to the log.
   */
  // line 252 "../../../../FileSummaryLN.ump"
   private void getOffsets(){
    if (needOffsets) {
	    long[] offsets = trackedSummary.getObsoleteOffsets();
	    if (offsets != null) {
		obsoleteOffsets.pack(offsets);
	    }
	    needOffsets = false;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 18 "../../../../FileSummaryLN.ump"
  private static final String BEGIN_TAG = "<fileSummaryLN>" ;
// line 20 "../../../../FileSummaryLN.ump"
  private static final String END_TAG = "</fileSummaryLN>" ;
// line 24 "../../../../FileSummaryLN.ump"
  private TrackedFileSummary trackedSummary ;
// line 28 "../../../../FileSummaryLN.ump"
  private boolean needOffsets ;
// line 30 "../../../../FileSummaryLN.ump"
  private byte logVersion ;

  
}