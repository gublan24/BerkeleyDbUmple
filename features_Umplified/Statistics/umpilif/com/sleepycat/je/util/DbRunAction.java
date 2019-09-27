/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;
import com.sleepycat.je.StatsConfig;

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
     protected void hook838() throws Exception{
      if (doAction == DBSTATS) {
            dbConfig=new DatabaseConfig();
            dbConfig.setReadOnly(true);
            DbInternal.setUseExistingConfig(dbConfig,true);
            db=env.openDatabase(null,dbName,dbConfig);
            try {
              System.out.println(db.getStats(new StatsConfig()));
            }
      finally {
              db.close();
            }
          }
          original();
    }
  
    // line 21 "../../../../DbRunAction_inner.ump"
     protected void hook839() throws Exception{
      if (action.equalsIgnoreCase("dbstats")) {
            doAction=DBSTATS;
          }
     else {
            original();
          }
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../DbRunAction.ump"
  private static final int DBSTATS = 6 ;

  
}