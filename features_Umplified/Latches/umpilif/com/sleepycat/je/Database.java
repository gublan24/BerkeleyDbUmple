/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Database.ump"
// line 3 "../../../Database_inner.ump"
public class Database
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Database()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Releases a lock acquired by calling acquireTriggerListReadLock().
   */
  // line 9 "../../../Database.ump"
   private void releaseTriggerListReadLock() throws DatabaseException{
    EnvironmentImpl env = envHandle.getEnvironmentImpl();
	env.getTriggerLatch().release();
  }

  // line 14 "../../../Database.ump"
   protected void hook53(List list) throws DatabaseException{
    try {
	    original(list);
	} finally {
	    releaseTriggerListReadLock();
	}
  }

  // line 23 "../../../Database.ump"
   protected void hook54(Locker locker, DatabaseEntry priKey, DatabaseEntry oldData, DatabaseEntry newData) throws DatabaseException{
    try {
	    original(locker, priKey, oldData, newData);
	} finally {
	    releaseTriggerListReadLock();
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 4 "../../../Database_inner.ump"
  public static class Database_acquireTriggerListReadLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_acquireTriggerListReadLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../Database_inner.ump"
    public void execute() throws DatabaseException{
      env=_this.envHandle.getEnvironmentImpl();
          env.getTriggerLatch().acquireShared();
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 11 "../../../Database_inner.ump"
  public static class Database_releaseTriggerListWriteLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_releaseTriggerListWriteLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 13 "../../../Database_inner.ump"
    public void execute() throws DatabaseException{
      original();
          env=_this.envHandle.getEnvironmentImpl();
          env.getTriggerLatch().release();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 18 "../../../Database_inner.ump"
  public static class Database_acquireTriggerListWriteLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_acquireTriggerListWriteLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 20 "../../../Database_inner.ump"
    public void execute() throws DatabaseException{
      env=_this.envHandle.getEnvironmentImpl();
          env.getTriggerLatch().acquireExclusive();
          original();
    }
  
  }
}