/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;

// line 3 "../../../../DbRunAction.ump"
// line 3 "../../../../DbRunAction_inner.ump"
public class DbRunAction
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbRunAction()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../DbRunAction.ump"
   private static  void doEvict(Environment env) throws DatabaseException{
    new DbRunAction_doEvict(env).execute();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.util;
  
  @MethodObject
  // line 4 "../../../../DbRunAction_inner.ump"
  public static class DbRunAction_doEvict
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbRunAction_doEvict()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DbRunAction_inner.ump"
    public  DbRunAction_doEvict(Environment env){
      this.env=env;
    }
  
    // line 9 "../../../../DbRunAction_inner.ump"
    public void execute() throws DatabaseException{
      envImpl=DbInternal.envGetEnvironmentImpl(env);
          this.hook837();
          c=new EnvironmentMutableConfig();
          this.hook836();
          env.setMutableConfig(c);
          start=System.currentTimeMillis();
          env.evictMemory();
          end=System.currentTimeMillis();
          f=new DecimalFormat();
          f.setMaximumFractionDigits(2);
          System.out.println("evict time=" + f.format(end - start));
    }
  
    // line 29 "../../../../DbRunAction_inner.ump"
     protected void hook836() throws DatabaseException{
      
    }
  
    // line 31 "../../../../DbRunAction_inner.ump"
     protected void hook837() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 21 "../../../../DbRunAction_inner.ump"
    protected Environment env ;
  // line 22 "../../../../DbRunAction_inner.ump"
    protected EnvironmentImpl envImpl ;
  // line 23 "../../../../DbRunAction_inner.ump"
    protected long cacheUsage ;
  // line 24 "../../../../DbRunAction_inner.ump"
    protected EnvironmentMutableConfig c ;
  // line 25 "../../../../DbRunAction_inner.ump"
    protected long start ;
  // line 26 "../../../../DbRunAction_inner.ump"
    protected long end ;
  // line 27 "../../../../DbRunAction_inner.ump"
    protected DecimalFormat f ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.util;
  
  @MethodObject
  // line 33 "../../../../DbRunAction_inner.ump"
  public static class DbRunAction_main
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbRunAction_main()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 35 "../../../../DbRunAction_inner.ump"
     protected void hook844() throws Exception{
      if (doAction == EVICT) {
            preload(env,dbName);
          }
          original();
    }
  
    // line 41 "../../../../DbRunAction_inner.ump"
     protected void hook845() throws Exception{
      if (doAction == EVICT) {
            envConfig.setConfigParam(EnvironmentParams.ENV_RUN_EVICTOR.getName(),"false");
            envConfig.setConfigParam(EnvironmentParams.EVICTOR_CRITICAL_PERCENTAGE.getName(),"1000");
          }
          original();
    }
  
    // line 48 "../../../../DbRunAction_inner.ump"
     protected void hook846() throws Exception{
      if (action.equalsIgnoreCase("evict")) {
            doAction=EVICT;
          }
     else {
            original();
          }
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../DbRunAction.ump"
  private static final int EVICT = 3 ;

  
}