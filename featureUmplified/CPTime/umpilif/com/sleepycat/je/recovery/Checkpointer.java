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
   protected void hook545(long waitTime) throws DatabaseException{
    timeInterval = waitTime;
	original(waitTime);
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
    public long execute() throws IllegalArgumentException,DatabaseException{
      wakeupPeriod=PropUtil.microsToMillis(configManager.getLong(EnvironmentParams.CHECKPOINTER_WAKEUP_INTERVAL));
          return original();
    }
  
    // line 10 "../../../../Checkpointer_inner.ump"
     protected void hook540() throws IllegalArgumentException,DatabaseException{
      result+=wakeupPeriod;
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 14 "../../../../Checkpointer_inner.ump"
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
  
    // line 16 "../../../../Checkpointer_inner.ump"
     protected void hook542() throws DatabaseException{
      if (useTimeInterval != 0) {
            lastUsedLsn=_this.envImpl.getFileManager().getLastUsedLsn();
            if (((System.currentTimeMillis() - _this.lastCheckpointMillis) >= useTimeInterval) && (DbLsn.compareTo(lastUsedLsn,_this.lastCheckpointEnd) != 0)) {
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
  
    // line 30 "../../../../Checkpointer_inner.ump"
     protected void hook543() throws DatabaseException{
      if (config.getMinutes() != 0) {
            useTimeInterval=config.getMinutes() * 60 * 1000;
          }
     else {
            original();
          }
    }
  
    // line 38 "../../../../Checkpointer_inner.ump"
     protected void hook544() throws DatabaseException{
      useTimeInterval=_this.timeInterval;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../Checkpointer.ump"
  private long timeInterval ;

  
}