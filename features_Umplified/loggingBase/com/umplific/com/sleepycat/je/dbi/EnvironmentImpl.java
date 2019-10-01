/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../EnvironmentImpl.ump"
// line 3 "../../../../EnvironmentImpl_inner.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Initialize the debugging logging system. Note that publishing to the database log is not permitted until we've initialized the file manager in recovery. We can't log safely before that.
   */
  // line 11 "../../../../EnvironmentImpl.ump"
   private Logger initLogger(File envHome) throws DatabaseException{
    return new EnvironmentImpl_initLogger(this, envHome).execute();
  }


  /**
   * 
   * Close down the logger.
   */
  // line 18 "../../../../EnvironmentImpl.ump"
   public void closeLogger(){
    Handler[] handlers = envLogger.getHandlers();
	for (int i = 0; i < handlers.length; i++) {
	    handlers[i].close();
	}
  }


  /**
   * 
   * @return environment Logger, for use in debugging output.
   */
  // line 28 "../../../../EnvironmentImpl.ump"
   public Logger getLogger(){
    return envLogger;
  }

  // line 32 "../../../../EnvironmentImpl.ump"
   protected void hook336(File envHome) throws DatabaseException{
    envLogger = initLogger(envHome);
	original(envHome);
  }

  // line 37 "../../../../EnvironmentImpl.ump"
   protected void hook337() throws DatabaseException{
    closeLogger();
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 4 "../../../../EnvironmentImpl_inner.ump"
  public static class EnvironmentImpl_initLogger
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImpl_initLogger()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../EnvironmentImpl_inner.ump"
    public  EnvironmentImpl_initLogger(EnvironmentImpl _this, File envHome){
      this._this=_this;
          this.envHome=envHome;
    }
  
    // line 10 "../../../../EnvironmentImpl_inner.ump"
    public Logger execute() throws DatabaseException{
      logger=Logger.getAnonymousLogger();
          logger.setUseParentHandlers(false);
          level=Tracer.parseLevel(_this,EnvironmentParams.JE_LOGGING_LEVEL);
          logger.setLevel(level);
          return logger;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 16 "../../../../EnvironmentImpl_inner.ump"
    protected EnvironmentImpl _this ;
  // line 17 "../../../../EnvironmentImpl_inner.ump"
    protected File envHome ;
  // line 18 "../../../../EnvironmentImpl_inner.ump"
    protected Logger logger ;
  // line 19 "../../../../EnvironmentImpl_inner.ump"
    protected Level level ;
  // line 20 "../../../../EnvironmentImpl_inner.ump"
    protected Handler consoleHandler ;
  // line 21 "../../../../EnvironmentImpl_inner.ump"
    protected Handler fileHandler ;
  // line 22 "../../../../EnvironmentImpl_inner.ump"
    protected int limit ;
  // line 23 "../../../../EnvironmentImpl_inner.ump"
    protected int count ;
  // line 24 "../../../../EnvironmentImpl_inner.ump"
    protected String logFilePattern ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../EnvironmentImpl.ump"
  private Logger envLogger ;

  
}