/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.DatabaseException;
import java.util.TreeMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Comparator;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../StatsFileReader.ump"
public class StatsFileReader extends DumpFileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StatsFileReader()
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
   */
  // line 36 "../../../../StatsFileReader.ump"
   public  StatsFileReader(EnvironmentImpl env, int readBufferSize, long startLsn, long finishLsn, String entryTypes, String txnIds, boolean verbose) throws IOException,DatabaseException{
    super(env, readBufferSize, startLsn, finishLsn, entryTypes, txnIds, verbose);
	entryInfoMap = new TreeMap(new LogEntryTypeComparator());
	totalLogBytes = 0;
	totalCount = 0;
	ckptCounter = new CheckpointCounter();
	ckptList = new ArrayList();
	if (verbose) {
	    ckptList.add(ckptCounter);
	}
  }


  /**
   * 
   * This reader collects stats about the log entry.
   */
  // line 51 "../../../../StatsFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    LogEntryType lastEntryType = LogEntryType.findType(currentEntryTypeNum, currentEntryTypeVersion);
	entryBuffer.position(entryBuffer.position() + currentEntrySize);
	EntryInfo info = (EntryInfo) entryInfoMap.get(lastEntryType);
	if (info == null) {
	    info = new EntryInfo();
	    entryInfoMap.put(lastEntryType, info);
	}
	info.count++;
	totalCount++;
	if (LogEntryType.isProvisional(currentEntryTypeVersion)) {
	    info.provisionalCount++;
	}
	int size = currentEntrySize + LogManager.HEADER_BYTES;
	info.totalBytes += size;
	totalLogBytes += size;
	if ((info.minBytes == 0) || (info.minBytes > size)) {
	    info.minBytes = size;
	}
	if (info.maxBytes < size) {
	    info.maxBytes = size;
	}
	if (verbose) {
	    if (firstLsnRead == DbLsn.NULL_LSN) {
		firstLsnRead = getLastLsn();
	    }
	    if (currentEntryTypeNum == LogEntryType.LOG_CKPT_END.getTypeNum()) {
		ckptCounter.endCkptLsn = getLastLsn();
		ckptCounter = new CheckpointCounter();
		ckptList.add(ckptCounter);
	    } else {
		ckptCounter.increment(this, currentEntryTypeNum);
	    }
	}
	return true;
  }

  // line 88 "../../../../StatsFileReader.ump"
   public void summarize(){
    System.out.println("Log statistics:");
	Iterator iter = entryInfoMap.entrySet().iterator();
	NumberFormat form = NumberFormat.getIntegerInstance();
	NumberFormat percentForm = NumberFormat.getInstance();
	percentForm.setMaximumFractionDigits(1);
	System.out.println(pad("type") + pad("total") + pad("provisional") + pad("total") + pad("min") + pad("max")
		+ pad("avg") + pad("entries"));
	System.out.println(pad("") + pad("count") + pad("count") + pad("bytes") + pad("bytes") + pad("bytes")
		+ pad("bytes") + pad("as % of log"));
	long realTotalBytes = 0;
	while (iter.hasNext()) {
	    Map.Entry m = (Map.Entry) iter.next();
	    EntryInfo info = (EntryInfo) m.getValue();
	    StringBuffer sb = new StringBuffer();
	    LogEntryType entryType = (LogEntryType) m.getKey();
	    sb.append(pad(entryType.toString()));
	    sb.append(pad(form.format(info.count)));
	    sb.append(pad(form.format(info.provisionalCount)));
	    sb.append(pad(form.format(info.totalBytes)));
	    sb.append(pad(form.format(info.minBytes)));
	    sb.append(pad(form.format(info.maxBytes)));
	    sb.append(pad(form.format((long) (info.totalBytes / info.count))));
	    double entryPercent = ((double) (info.totalBytes * 100) / totalLogBytes);
	    sb.append(pad(percentForm.format(entryPercent)));
	    System.out.println(sb.toString());
	    if (entryType == LogEntryType.LOG_LN_TRANSACTIONAL) {
		int overhead = LogManager.HEADER_BYTES + 46;
		realTotalBytes += info.totalBytes - (info.count * overhead);
	    }
	    if (entryType == LogEntryType.LOG_LN) {
		int overhead = LogManager.HEADER_BYTES + 21;
		realTotalBytes += info.totalBytes - (info.count * overhead);
	    }
	}
	StringBuffer sb = new StringBuffer();
	sb.append(pad("key/data"));
	sb.append(pad(""));
	sb.append(pad(""));
	sb.append(pad(form.format(realTotalBytes)));
	sb.append(pad(""));
	sb.append(pad(""));
	sb.append(pad(""));
	String realSize = "(" + percentForm.format((double) (realTotalBytes * 100) / totalLogBytes) + ")";
	sb.append(pad(realSize));
	System.out.println(sb.toString());
	System.out.println("\nTotal bytes in portion of log read: " + form.format(totalLogBytes));
	System.out.println("Total number of entries: " + form.format(totalCount));
	if (verbose) {
	    summarizeCheckpointInfo();
	}
  }

  // line 141 "../../../../StatsFileReader.ump"
   private String pad(String result){
    int spaces = 15 - result.length();
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < spaces; i++) {
	    sb.append(" ");
	}
	sb.append(result);
	return sb.toString();
  }

  // line 151 "../../../../StatsFileReader.ump"
   private void summarizeCheckpointInfo(){
    System.out.println("\nPer checkpoint interval info:");
	System.out.println(pad("lnTxn") + pad("ln") + pad("mapLNTxn") + pad("mapLN") + pad("end-end") + pad("end-start")
		+ pad("start-end") + pad("maxLNReplay") + pad("ckptEnd"));
	long logFileMax;
	try {
	    logFileMax = env.getConfigManager().getLong(EnvironmentParams.LOG_FILE_MAX);
	} catch (DatabaseException e) {
	    e.printStackTrace();
	    return;
	}
	Iterator iter = ckptList.iterator();
	CheckpointCounter prevCounter = null;
	NumberFormat form = NumberFormat.getInstance();
	while (iter.hasNext()) {
	    CheckpointCounter c = (CheckpointCounter) iter.next();
	    StringBuffer sb = new StringBuffer();
	    int maxTxnLNs = c.preStartLNTxnCount + c.postStartLNTxnCount;
	    sb.append(pad(form.format(maxTxnLNs)));
	    int maxLNs = c.preStartLNCount + c.postStartLNCount;
	    sb.append(pad(form.format(maxLNs)));
	    sb.append(pad(form.format(c.preStartMapLNTxnCount + c.postStartMapLNTxnCount)));
	    sb.append(pad(form.format(c.preStartMapLNCount + c.postStartMapLNCount)));
	    long end = (c.endCkptLsn == DbLsn.NULL_LSN) ? getLastLsn() : c.endCkptLsn;
	    long endToEndDistance = 0;
	    FileManager fileManager = env.getFileManager();
	    if (prevCounter == null) {
		endToEndDistance = DbLsn.getWithCleaningDistance(end, fileManager, firstLsnRead, logFileMax);
	    } else {
		endToEndDistance = DbLsn.getWithCleaningDistance(end, fileManager, prevCounter.endCkptLsn, logFileMax);
	    }
	    sb.append(pad(form.format(endToEndDistance)));
	    long start = (c.startCkptLsn == DbLsn.NULL_LSN) ? getLastLsn() : c.startCkptLsn;
	    long endToStartDistance = 0;
	    if (prevCounter == null) {
		endToStartDistance = DbLsn.getWithCleaningDistance(start, fileManager, firstLsnRead, logFileMax);
	    } else {
		endToStartDistance = DbLsn.getWithCleaningDistance(start, fileManager, prevCounter.endCkptLsn,
			logFileMax);
	    }
	    sb.append(pad(form.format(endToStartDistance)));
	    long startToEndDistance = 0;
	    if ((c.startCkptLsn != DbLsn.NULL_LSN) && (c.endCkptLsn != DbLsn.NULL_LSN)) {
		startToEndDistance = DbLsn.getWithCleaningDistance(c.endCkptLsn, fileManager, c.startCkptLsn,
			logFileMax);
	    }
	    sb.append(pad(form.format(startToEndDistance)));
	    int maxReplay = maxLNs + maxTxnLNs;
	    if (prevCounter != null) {
		maxReplay += prevCounter.postStartLNTxnCount;
		maxReplay += prevCounter.postStartLNCount;
	    }
	    sb.append(pad(form.format(maxReplay)));
	    if (c.endCkptLsn == DbLsn.NULL_LSN) {
		sb.append("   ").append(DbLsn.getNoFormatString(getLastLsn()));
	    } else {
		sb.append("   ").append(DbLsn.getNoFormatString(c.endCkptLsn));
	    }
	    System.out.println(sb.toString());
	    prevCounter = c;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 19 "../../../../StatsFileReader.ump"
  private Map entryInfoMap ;
// line 21 "../../../../StatsFileReader.ump"
  private long totalLogBytes ;
// line 23 "../../../../StatsFileReader.ump"
  private long totalCount ;
// line 25 "../../../../StatsFileReader.ump"
  private ArrayList ckptList ;
// line 27 "../../../../StatsFileReader.ump"
  private CheckpointCounter ckptCounter ;
// line 29 "../../../../StatsFileReader.ump"
  private long firstLsnRead ;

  
}