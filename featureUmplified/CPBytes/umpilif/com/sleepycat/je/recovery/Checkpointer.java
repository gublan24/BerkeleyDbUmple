/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../Checkpointer.ump"
// line 3 "../../../../Checkpointer_inner.ump"
public class Checkpointer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Checkpointer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../Checkpointer.ump"
   protected void hook539(EnvironmentImpl envImpl) throws DatabaseException{
    logSizeBytesInterval = envImpl.getConfigManager().getLong(EnvironmentParams.CHECKPOINTER_BYTES_INTERVAL);
	original(envImpl);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 4 "../../../../Checkpointer_inner.ump"
  public static class Checkpointer_getWakeupPeriod
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_getWakeupPeriod()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Checkpointer_inner.ump"
     protected void hook540() throws IllegalArgumentException,DatabaseException{
      if (bytePeriod == 0) {
            original();
          }
    }
  
    // line 11 "../../../../Checkpointer_inner.ump"
     protected void hook541() throws IllegalArgumentException,DatabaseException{
      bytePeriod=configManager.getLong(EnvironmentParams.CHECKPOINTER_BYTES_INTERVAL);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 15 "../../../../Checkpointer_inner.ump"
  public static class Checkpointer_isRunnable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_isRunnable()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 17 "../../../../Checkpointer_inner.ump"
     protected void hook542() throws DatabaseException{
      if (useBytesInterval != 0) {
            nextLsn=_this.envImpl.getFileManager().getNextLsn();
            if (DbLsn.getNoCleaningDistance(nextLsn,_this.lastCheckpointEnd,_this.logFileMax) >= useBytesInterval) {
              throw new ReturnBoolean(true);
            }
     else {
              throw new ReturnBoolean(false);
            }
          }
     else {
            original();
          }
    }
  
    // line 31 "../../../../Checkpointer_inner.ump"
     protected void hook543() throws DatabaseException{
      if (config.getKBytes() != 0) {
            useBytesInterval=config.getKBytes() << 10;
          }
     else {
            original();
          }
    }
  
    // line 39 "../../../../Checkpointer_inner.ump"
     protected void hook544() throws DatabaseException{
      if (_this.logSizeBytesInterval != 0) {
            useBytesInterval=_this.logSizeBytesInterval;
          }
     else {
            original();
          }
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Checkpointer.ump"
  private long logSizeBytesInterval ;

  
}