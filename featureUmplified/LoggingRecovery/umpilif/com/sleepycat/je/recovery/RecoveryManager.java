/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RecoveryManager.ump"
// line 3 "../../../../RecoveryManager_inner.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 10 "../../../../RecoveryManager.ump"
   private void traceINDeleteReplay(long nodeId, long logLsn, boolean found, boolean deleted, int index, boolean isDuplicate){
    new RecoveryManager_traceINDeleteReplay(this, nodeId, logLsn, found, deleted, index, isDuplicate).execute();
  }

  // line 15 "../../../../RecoveryManager.ump"
   protected static  void hook555(Level traceLevel, DatabaseImpl db, TreeLocation location, LN lnFromLog, long logLsn, long abortLsn, boolean found, boolean replaced, boolean success) throws DatabaseException{
    
  }

  // line 18 "../../../../RecoveryManager.ump"
   protected void hook578(EnvironmentImpl env) throws DatabaseException{
    detailedTraceLevel = Tracer.parseLevel(env, EnvironmentParams.JE_LOGGING_LEVEL_RECOVERY);
	original(env);
  }

  // line 24 "../../../../RecoveryManager.ump"
   protected void hook579(long nodeId, boolean containsDuplicates, long logLsn, boolean found, boolean deleted, SearchResult result) throws DatabaseException{
    traceINDeleteReplay(nodeId, logLsn, found, deleted, result.index, containsDuplicates);
	original(nodeId, containsDuplicates, logLsn, found, deleted, result);
  }

  // line 30 "../../../../RecoveryManager.ump"
   protected void hook580(DatabaseImpl db, IN inFromLog, long lsn, boolean success, RootUpdater rootUpdater) throws DatabaseException{
    trace(detailedTraceLevel, db, TRACE_ROOT_REPLACE, success, inFromLog, lsn, null, true,
		rootUpdater.getReplaced(), rootUpdater.getInserted(), rootUpdater.getOriginalLsn(), DbLsn.NULL_LSN, -1);
	original(db, inFromLog, lsn, success, rootUpdater);
  }

  // line 37 "../../../../RecoveryManager.ump"
   protected void hook581(DatabaseImpl db, DIN inFromLog, long lsn, boolean found, boolean inserted, boolean replaced, long origLsn, IN parent, int index, boolean success) throws DatabaseException{
    trace(detailedTraceLevel, db, TRACE_DUP_ROOT_REPLACE, success, inFromLog, lsn, parent, found, replaced,
		inserted, origLsn, DbLsn.NULL_LSN, index);
	original(db, inFromLog, lsn, found, inserted, replaced, origLsn, parent, index, success);
  }

  // line 44 "../../../../RecoveryManager.ump"
   protected void hook582(DatabaseImpl db, IN inFromLog, long logLsn, boolean inserted, boolean replaced, long origLsn, boolean success, SearchResult result) throws DatabaseException{
    trace(detailedTraceLevel, db, TRACE_IN_REPLACE, success, inFromLog, logLsn, result.parent,
		result.exactParentFound, replaced, inserted, origLsn, DbLsn.NULL_LSN, result.index);
	original(db, inFromLog, logLsn, inserted, replaced, origLsn, success, result);
  }

  // line 51 "../../../../RecoveryManager.ump"
   protected void hook583(DatabaseImpl db, TreeLocation location, LN lnFromLog, long logLsn, boolean found, boolean replaced, boolean inserted, boolean success) throws DatabaseException{
    trace(detailedTraceLevel, db, TRACE_LN_REDO, success, lnFromLog, logLsn, location.bin, found, replaced,
		inserted, location.childLsn, DbLsn.NULL_LSN, location.index);
	original(db, location, lnFromLog, logLsn, found, replaced, inserted, success);
  }

  // line 59 "../../../../RecoveryManager.ump"
   protected static  void hook584(Level traceLevel, DatabaseImpl db, TreeLocation location, LN lnFromLog, byte [] mainKey, byte [] dupKey, long logLsn, long abortLsn, boolean abortKnownDeleted, RecoveryInfo info, boolean splitsAllowed, boolean found, boolean replaced, boolean success) throws DatabaseException{
    try {
	    original(traceLevel, db, location, lnFromLog, mainKey, dupKey, logLsn, abortLsn, abortKnownDeleted, info,
		    splitsAllowed, found, replaced, success);
	} finally {
	    hook555(traceLevel, db, location, lnFromLog, logLsn, abortLsn, found, replaced, success);
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 4 "../../../../RecoveryManager_inner.ump"
  public static class RecoveryManager_traceINDeleteReplay
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RecoveryManager_traceINDeleteReplay()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../RecoveryManager_inner.ump"
    public  RecoveryManager_traceINDeleteReplay(RecoveryManager _this, long nodeId, long logLsn, boolean found, boolean deleted, int index, boolean isDuplicate){
      this._this=_this;
          this.nodeId=nodeId;
          this.logLsn=logLsn;
          this.found=found;
          this.deleted=deleted;
          this.index=index;
          this.isDuplicate=isDuplicate;
    }
  
    // line 15 "../../../../RecoveryManager_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 16 "../../../../RecoveryManager_inner.ump"
    protected RecoveryManager _this ;
  // line 17 "../../../../RecoveryManager_inner.ump"
    protected long nodeId ;
  // line 18 "../../../../RecoveryManager_inner.ump"
    protected long logLsn ;
  // line 19 "../../../../RecoveryManager_inner.ump"
    protected boolean found ;
  // line 20 "../../../../RecoveryManager_inner.ump"
    protected boolean deleted ;
  // line 21 "../../../../RecoveryManager_inner.ump"
    protected int index ;
  // line 22 "../../../../RecoveryManager_inner.ump"
    protected boolean isDuplicate ;
  // line 23 "../../../../RecoveryManager_inner.ump"
    protected Logger logger ;
  // line 24 "../../../../RecoveryManager_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}