/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;

// line 3 "../../../../DumpFileReader.ump"
public abstract class DumpFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DumpFileReader()
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
  // line 25 "../../../../DumpFileReader.ump"
   public  DumpFileReader(EnvironmentImpl env, int readBufferSize, long startLsn, long finishLsn, String entryTypes, String txnIds, boolean verbose) throws IOException,DatabaseException{
    super(env, readBufferSize, true, startLsn, null, DbLsn.NULL_LSN, finishLsn);
	targetEntryTypes = new HashSet();
	if (entryTypes != null) {
	    StringTokenizer tokenizer = new StringTokenizer(entryTypes, ",");
	    while (tokenizer.hasMoreTokens()) {
		String typeString = (String) tokenizer.nextToken();
		targetEntryTypes.add(new Byte(typeString.trim()));
	    }
	}
	targetTxnIds = new HashSet();
	if (txnIds != null) {
	    StringTokenizer tokenizer = new StringTokenizer(txnIds, ",");
	    while (tokenizer.hasMoreTokens()) {
		String txnIdString = (String) tokenizer.nextToken();
		targetTxnIds.add(new Long(txnIdString.trim()));
	    }
	}
	this.verbose = verbose;
  }


  /**
   * 
   * @return true if this reader should process this entry, or justskip over it.
   */
  // line 49 "../../../../DumpFileReader.ump"
   protected boolean isTargetEntry(byte logEntryTypeNumber, byte logEntryTypeVersion){
    if (targetEntryTypes.size() == 0) {
	    return true;
	} else {
	    return targetEntryTypes.contains(new Byte(logEntryTypeNumber));
	}
  }

  // line 57 "../../../../DumpFileReader.ump"
   public void summarize(){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../DumpFileReader.ump"
  private Set targetEntryTypes ;
// line 16 "../../../../DumpFileReader.ump"
  protected Set targetTxnIds ;
// line 18 "../../../../DumpFileReader.ump"
  protected boolean verbose ;

  
}