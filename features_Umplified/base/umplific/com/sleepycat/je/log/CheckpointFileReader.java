/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import java.io.IOException;

// line 3 "../../../../CheckpointFileReader.ump"
public class CheckpointFileReader extends FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CheckpointFileReader()
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
  // line 22 "../../../../CheckpointFileReader.ump"
   public  CheckpointFileReader(EnvironmentImpl env, int readBufferSize, boolean forward, long startLsn, long finishLsn, long endOfFileLsn) throws IOException,DatabaseException{
    super(env, readBufferSize, forward, startLsn, null, endOfFileLsn, finishLsn);
  }


  /**
   * 
   * @return true if this is a targetted entry.
   */
  // line 29 "../../../../CheckpointFileReader.ump"
   protected boolean isTargetEntry(byte logEntryTypeNumber, byte logEntryTypeVersion){
    boolean isTarget = false;
	isRoot = false;
	isCheckpointEnd = false;
	isCheckpointStart = false;
	if (LogEntryType.LOG_CKPT_END.equalsType(logEntryTypeNumber, logEntryTypeVersion)) {
	    isTarget = true;
	    isCheckpointEnd = true;
	} else if (LogEntryType.LOG_CKPT_START.equalsType(logEntryTypeNumber, logEntryTypeVersion)) {
	    isTarget = true;
	    isCheckpointStart = true;
	} else if (LogEntryType.LOG_ROOT.equalsType(logEntryTypeNumber, logEntryTypeVersion)) {
	    isTarget = true;
	    isRoot = true;
	}
	return isTarget;
  }


  /**
   * 
   * This reader instantiate the first object of a given log entry
   */
  // line 50 "../../../../CheckpointFileReader.ump"
   protected boolean processEntry(ByteBuffer entryBuffer) throws DatabaseException{
    return true;
  }


  /**
   * 
   * @return true if last entry was a root entry.
   */
  // line 57 "../../../../CheckpointFileReader.ump"
   public boolean isRoot(){
    return isRoot;
  }


  /**
   * 
   * @return true if last entry was a checkpoint end entry.
   */
  // line 64 "../../../../CheckpointFileReader.ump"
   public boolean isCheckpointEnd(){
    return isCheckpointEnd;
  }


  /**
   * 
   * @return true if last entry was a checkpoint start entry.
   */
  // line 71 "../../../../CheckpointFileReader.ump"
   public boolean isCheckpointStart(){
    return isCheckpointStart;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../CheckpointFileReader.ump"
  private boolean isRoot ;
// line 13 "../../../../CheckpointFileReader.ump"
  private boolean isCheckpointEnd ;
// line 15 "../../../../CheckpointFileReader.ump"
  private boolean isCheckpointStart ;

  
}