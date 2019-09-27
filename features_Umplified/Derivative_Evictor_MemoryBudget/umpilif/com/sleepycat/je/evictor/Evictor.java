/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;

// line 3 "../../../../Evictor.ump"
// line 3 "../../../../Evictor_inner.ump"
public class Evictor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../Evictor.ump"
   protected long hook389(long evictBytes, IN renewedChild) throws DatabaseException{
    evictBytes = renewedChild.getInMemorySize();
	return original(evictBytes, renewedChild);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.evictor;
  
  @MethodObject
  // line 4 "../../../../Evictor_inner.ump"
  public static class Evictor_isRunnable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_isRunnable()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
    public boolean execute() throws DatabaseException{
      boolean result=original();
          result=doRun;
          return result;
    }
  
    // line 11 "../../../../Evictor_inner.ump"
     protected void hook388() throws DatabaseException{
      currentUsage=mb.getCacheMemoryUsage();
          maxMem=mb.getCacheBudget();
          doRun=((currentUsage - maxMem) > 0);
          if (doRun) {
            _this.currentRequiredEvictBytes=_this.evictBytesSetting;
            _this.currentRequiredEvictBytes+=(currentUsage - maxMem);
            if (_this.DEBUG) {
              if (source == _this.SOURCE_CRITICAL) {
                System.out.println("executed: critical runnable");
              }
            }
          }
          if (_this.runnableHook != null) {
            doRun=((Boolean)_this.runnableHook.getHookValue()).booleanValue();
            _this.currentRequiredEvictBytes=maxMem;
          }
          original();
    }
  
  }
}