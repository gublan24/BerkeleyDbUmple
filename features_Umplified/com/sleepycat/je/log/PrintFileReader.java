/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../PrintFileReader.ump"
public class PrintFileReader extends DumpFileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PrintFileReader()
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
  // line 17 "../../../../PrintFileReader.ump"
   public  PrintFileReader(EnvironmentImpl env, int readBufferSize, long startLsn, long finishLsn, String entryTypes, String txnIds, boolean verbose) throws IOException,DatabaseException{
    super(env, readBufferSize, startLsn, finishLsn, entryTypes, txnIds, verbose);
  }


  /**
   * 
   * This reader prints the log entry item.
   */
  // line 24 "../../../../PrintFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    LogEntryType lastEntryType = LogEntryType.findType(currentEntryTypeNum, currentEntryTypeVersion);
	StringBuffer sb = new StringBuffer();
	sb.append("<entry lsn=\"0x").append(Long.toHexString(readBufferFileNum));
	sb.append("/0x").append(Long.toHexString(currentEntryOffset));
	sb.append("\" type=\"").append(lastEntryType);
	if (LogEntryType.isProvisional(currentEntryTypeVersion)) {
	    sb.append("\" isProvisional=\"true");
	}
	sb.append("\" prev=\"0x");
	sb.append(Long.toHexString(currentEntryPrevOffset));
	if (verbose) {
	    sb.append("\" size=\"").append(currentEntrySize);
	    sb.append("\" cksum=\"").append(currentEntryChecksum);
	}
	sb.append("\">");
	LogEntry entry = lastEntryType.getSharedLogEntry();
	entry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
	boolean dumpIt = true;
	if (targetTxnIds.size() > 0) {
	    if (entry.isTransactional()) {
		if (!targetTxnIds.contains(new Long(entry.getTransactionId()))) {
		    dumpIt = false;
		}
	    } else {
		dumpIt = false;
	    }
	}
	if (dumpIt) {
	    entry.dumpEntry(sb, verbose);
	    sb.append("</entry>");
	    System.out.println(sb.toString());
	}
	return true;
  }

}