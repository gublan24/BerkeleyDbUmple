/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../CursorImpl.ump"
// line 3 "../../../../CursorImpl_inner.ump"
public class CursorImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CursorImpl()
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
  // line 9 "../../../../CursorImpl.ump"
   private void trace(Level level, String changeType, BIN theBin, LN ln, int lnIndex, long oldLsn, long newLsn){
    new CursorImpl_trace(this, level, changeType, theBin, ln, lnIndex, oldLsn, newLsn).execute();
  }

  // line 13 "../../../../CursorImpl.ump"
   protected void hook204(LN ln, long oldLsn, long newLsn) throws DatabaseException{
    trace(Level.FINER, TRACE_DELETE, targetBin, ln, targetIndex, oldLsn, newLsn);
	original(ln, oldLsn, newLsn);
  }

  // line 18 "../../../../CursorImpl.ump"
   protected void hook205(LN ln, long oldLsn, long newLsn) throws DatabaseException{
    trace(Level.FINER, TRACE_MOD, targetBin, ln, targetIndex, oldLsn, newLsn);
	original(ln, oldLsn, newLsn);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../CursorImpl_inner.ump"
    public  CursorImpl_trace(CursorImpl _this, Level level, String changeType, BIN theBin, LN ln, int lnIndex, long oldLsn, long newLsn){
      this._this=_this;
          this.level=level;
          this.changeType=changeType;
          this.theBin=theBin;
          this.ln=ln;
          this.lnIndex=lnIndex;
          this.oldLsn=oldLsn;
          this.newLsn=newLsn;
    }
  
    // line 16 "../../../../CursorImpl_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 17 "../../../../CursorImpl_inner.ump"
    protected CursorImpl _this ;
  // line 18 "../../../../CursorImpl_inner.ump"
    protected Level level ;
  // line 19 "../../../../CursorImpl_inner.ump"
    protected String changeType ;
  // line 20 "../../../../CursorImpl_inner.ump"
    protected BIN theBin ;
  // line 21 "../../../../CursorImpl_inner.ump"
    protected LN ln ;
  // line 22 "../../../../CursorImpl_inner.ump"
    protected int lnIndex ;
  // line 23 "../../../../CursorImpl_inner.ump"
    protected long oldLsn ;
  // line 24 "../../../../CursorImpl_inner.ump"
    protected long newLsn ;
  // line 25 "../../../../CursorImpl_inner.ump"
    protected Logger logger ;
  // line 26 "../../../../CursorImpl_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}