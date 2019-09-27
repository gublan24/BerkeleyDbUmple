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
   * Flip the log to a new file, forcing an fsync. Return the LSN of the trace record in the new file.
   */
  // line 9 "../../../../EnvironmentImpl.ump"
   public long forceLogFileFlip() throws DatabaseException{
    Tracer newRec = new Tracer("File Flip");
	return logManager.logForceFlip(newRec);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
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
    public Logger execute() throws DatabaseException{
      Logger result=original();
          fileHandler=null;
          try {
            if (_this.configManager.getBoolean(EnvironmentParams.JE_LOGGING_FILE)) {
              limit=_this.configManager.getInt(EnvironmentParams.JE_LOGGING_FILE_LIMIT);
              count=_this.configManager.getInt(EnvironmentParams.JE_LOGGING_FILE_COUNT);
              logFilePattern=envHome + "/" + Tracer.INFO_FILES;
              fileHandler=new FileHandler(logFilePattern,limit,count,true);
              fileHandler.setFormatter(new SimpleFormatter());
              fileHandler.setLevel(level);
              logger.addHandler(fileHandler);
            }
          }
     catch (      IOException e) {
            throw new DatabaseException(e.getMessage());
          }
          return result;
    }
  
  }
}