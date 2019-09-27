/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../Cleaner.ump"
// line 3 "../../../../Cleaner_inner.ump"
public class Cleaner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cleaner Attributes
  private Level detailedTraceLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner(Level aDetailedTraceLevel)
  {
    detailedTraceLevel = aDetailedTraceLevel;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDetailedTraceLevel(Level aDetailedTraceLevel)
  {
    boolean wasSet = false;
    detailedTraceLevel = aDetailedTraceLevel;
    wasSet = true;
    return wasSet;
  }

  /**
   * adding inner elements ;
   */
  public Level getDetailedTraceLevel()
  {
    return detailedTraceLevel;
  }

  public void delete()
  {}


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 12 "../../../../Cleaner.ump"
  public void trace(Level level, String action, Node node, long logLsn, boolean completed, boolean obsolete, boolean dirtiedMigrated){
    new Cleaner_trace(this, level, action, node, logLsn, completed, obsolete, dirtiedMigrated).execute();
  }

  // line 16 "../../../../Cleaner.ump"
   protected void hook90() throws DatabaseException{
    detailedTraceLevel = Tracer.parseLevel(env, EnvironmentParams.JE_LOGGING_LEVEL_CLEANER);
	original();
  }

  // line 21 "../../../../Cleaner.ump"
   protected void hook91(LN ln, boolean obsolete, boolean completed) throws DatabaseException{
    trace(detailedTraceLevel, CLEAN_PENDING_LN, ln, DbLsn.NULL_LSN, completed, obsolete, false);
	original(ln, obsolete, completed);
  }

  // line 27 "../../../../Cleaner.ump"
   protected void hook92(long lsn, String cleanAction, boolean obsolete, boolean migrated, boolean completed, LN ln) throws DatabaseException{
    trace(detailedTraceLevel, cleanAction, ln, lsn, completed, obsolete, migrated);
	original(lsn, cleanAction, obsolete, migrated, completed, ln);
  }

  // line 33 "../../../../Cleaner.ump"
   protected void hook93(long lsn, String cleanAction, boolean obsolete, boolean migrated, boolean completed, LN ln) throws DatabaseException{
    trace(detailedTraceLevel, cleanAction, ln, lsn, completed, obsolete, migrated);
	original(lsn, cleanAction, obsolete, migrated, completed, ln);
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "detailedTraceLevel" + "=" + (getDetailedTraceLevel() != null ? !getDetailedTraceLevel().equals(this)  ? getDetailedTraceLevel().toString().replaceAll("  ","    ") : "this" : "null");
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../Cleaner_inner.ump"
  public static class Cleaner_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Cleaner_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Cleaner_inner.ump"
    public  Cleaner_trace(Cleaner _this, Level level, String action, Node node, long logLsn, boolean completed, boolean obsolete, boolean dirtiedMigrated){
      this._this=_this;
          this.level=level;
          this.action=action;
          this.node=node;
          this.logLsn=logLsn;
          this.completed=completed;
          this.obsolete=obsolete;
          this.dirtiedMigrated=dirtiedMigrated;
    }
  
    // line 16 "../../../../Cleaner_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 17 "../../../../Cleaner_inner.ump"
    protected Cleaner _this ;
  // line 18 "../../../../Cleaner_inner.ump"
    protected Level level ;
  // line 19 "../../../../Cleaner_inner.ump"
    protected String action ;
  // line 20 "../../../../Cleaner_inner.ump"
    protected Node node ;
  // line 21 "../../../../Cleaner_inner.ump"
    protected long logLsn ;
  // line 22 "../../../../Cleaner_inner.ump"
    protected boolean completed ;
  // line 23 "../../../../Cleaner_inner.ump"
    protected boolean obsolete ;
  // line 24 "../../../../Cleaner_inner.ump"
    protected boolean dirtiedMigrated ;
  // line 25 "../../../../Cleaner_inner.ump"
    protected Logger logger ;
  // line 26 "../../../../Cleaner_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}