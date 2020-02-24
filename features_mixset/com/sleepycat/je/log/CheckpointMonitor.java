/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump"
public class CheckpointMonitor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CheckpointMonitor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 16 "../../../../Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump"
  public  CheckpointMonitor(EnvironmentImpl envImpl) throws DatabaseException{
    bytesWritten = 0;
	//periodInBytes = envImpl.getConfigManager().getLong(EnvironmentParams.CHECKPOINTER_BYTES_INTERVAL);
	periodInBytes /= 10;
	this.envImpl = envImpl;
  }


  /**
   * 
   * Update checkpoint driving information. Call from within the log write latch.
   * @return true if a checkpoint is needed.
   */
  // line 27 "../../../../Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump"
  public boolean recordLogWrite(int entrySize, LoggableObject item){
    bytesWritten += entrySize;
	return (bytesWritten >= periodInBytes);
  }


  /**
   * 
   * Wake up the checkpointer. Note that the check on bytesWritten is actually within a latched period.
   */
  // line 35 "../../../../Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump"
  public void activate(){
   // envImpl.getCheckpointer().wakeup();
	bytesWritten = 0;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump"
  private int bytesWritten ;
// line 11 "../../../../Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump"
  private long periodInBytes ;
// line 13 "../../../../Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump"
  private EnvironmentImpl envImpl ;

  
}