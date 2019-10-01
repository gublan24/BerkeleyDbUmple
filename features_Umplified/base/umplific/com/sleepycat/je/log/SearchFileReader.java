/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.entry.LogEntry;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../SearchFileReader.ump"
public class SearchFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SearchFileReader()
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
  // line 22 "../../../../SearchFileReader.ump"
   public  SearchFileReader(EnvironmentImpl env, int readBufferSize, boolean forward, long startLsn, long endOfFileLsn, LogEntryType targetType) throws IOException,DatabaseException{
    super(env, readBufferSize, forward, startLsn, null, endOfFileLsn, DbLsn.NULL_LSN);
	this.targetType = targetType;
	logEntry = targetType.getNewLogEntry();
  }


  /**
   * 
   * @return true if this is a targetted entry.
   */
  // line 31 "../../../../SearchFileReader.ump"
   protected boolean isTargetEntry(byte logEntryTypeNumber, byte logEntryTypeVersion){
    return (targetType.equalsType(logEntryTypeNumber, logEntryTypeVersion));
  }


  /**
   * 
   * This reader instantiate the first object of a given log entry.
   */
  // line 38 "../../../../SearchFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    logEntry.readEntry(entryBuffer, currentEntrySize, currentEntryTypeVersion, true);
	return true;
  }


  /**
   * 
   * @return the last object read.
   */
  // line 46 "../../../../SearchFileReader.ump"
   public Object getLastObject(){
    return logEntry.getMainItem();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../SearchFileReader.ump"
  private LogEntryType targetType ;
// line 15 "../../../../SearchFileReader.ump"
  private LogEntry logEntry ;

  
}