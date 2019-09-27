/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Database.ump"
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

  // line 6 "../../../Database.ump"
   public DatabaseStats getStats(StatsConfig config) throws DatabaseException{
    checkEnv();
	checkRequiredDbState(OPEN, "Can't call Database.stat");
	StatsConfig useConfig = (config == null) ? StatsConfig.DEFAULT : config;
	if (databaseImpl != null) {
	    this.hook38();
	    return databaseImpl.stat(useConfig);
	}
	return null;
  }

  // line 17 "../../../Database.ump"
   protected void hook38() throws DatabaseException{
    
  }

}