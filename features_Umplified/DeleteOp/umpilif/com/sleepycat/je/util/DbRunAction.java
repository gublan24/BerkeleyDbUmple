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
   private static  void removeAndClean(Environment env, String name) throws DatabaseException{
    long a, b, c, d, e, f;
	Transaction txn = env.beginTransaction(null, null);
	CheckpointConfig force = new CheckpointConfig();
	force.setForce(true);
	a = System.currentTimeMillis();
	env.removeDatabase(txn, name);
	b = System.currentTimeMillis();
	txn.commit();
	c = System.currentTimeMillis();
	int cleanedCount = 0;
	while (env.cleanLog() > 0) {
	    cleanedCount++;
	}
	d = System.currentTimeMillis();
	System.out.println("cleanedCount=" + cleanedCount);
	e = 0;
	f = 0;
	if (cleanedCount > 0) {
	    e = System.currentTimeMillis();
	    env.checkpoint(force);
	    f = System.currentTimeMillis();
	}
	System.out.println("Remove of " + name + " remove: " + getSecs(a, b) + " commit: " + getSecs(b, c) + " clean: "
		+ getSecs(c, d) + " checkpoint: " + getSecs(e, f));
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.util;
  
  @MethodObject
  // line 4 "../../../../DbRunAction_inner.ump"
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
  
    // line 6 "../../../../DbRunAction_inner.ump"
     protected void hook842() throws Exception{
      if (doAction == REMOVEDB) {
            removeAndClean(env,dbName);
          }
          original();
    }
  
    // line 12 "../../../../DbRunAction_inner.ump"
     protected void hook843() throws Exception{
      if (action.equalsIgnoreCase("removedb")) {
            doAction=REMOVEDB;
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
  private static final int REMOVEDB = 5 ;

  
}