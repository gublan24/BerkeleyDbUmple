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

  // line 7 "../../../../RecoveryManager.ump"
   protected static  void hook555(Level traceLevel, DatabaseImpl db, TreeLocation location, LN lnFromLog, long logLsn, long abortLsn, boolean found, boolean replaced, boolean success) throws DatabaseException{
    trace(traceLevel, db, TRACE_LN_UNDO, success, lnFromLog, logLsn, location.bin, found, replaced, false,
		location.childLsn, abortLsn, location.index);
	original(traceLevel, db, location, lnFromLog, logLsn, abortLsn, found, replaced, success);
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
    public void execute(){
      logger=_this.env.getLogger();
          if (logger.isLoggable(_this.detailedTraceLevel)) {
            sb=new StringBuffer();
            sb.append((isDuplicate) ? _this.TRACE_IN_DUPDEL_REPLAY : _this.TRACE_IN_DEL_REPLAY);
            sb.append(" node=").append(nodeId);
            sb.append(" lsn=").append(DbLsn.getNoFormatString(logLsn));
            sb.append(" found=").append(found);
            sb.append(" deleted=").append(deleted);
            sb.append(" index=").append(index);
            logger.log(_this.detailedTraceLevel,sb.toString());
          }
          original();
    }
  
  }
}