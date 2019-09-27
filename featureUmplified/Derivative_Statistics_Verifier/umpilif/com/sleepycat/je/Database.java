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
   public DatabaseStats verify(VerifyConfig config) throws DatabaseException{
    checkEnv();
	checkRequiredDbState(OPEN, "Can't call Database.verify");
	this.hook37();
	VerifyConfig useConfig = (config == null) ? VerifyConfig.DEFAULT : config;
	DatabaseStats stats = databaseImpl.getEmptyStats();
	databaseImpl.verify(useConfig, stats);
	return stats;
  }

  // line 16 "../../../Database.ump"
   protected void hook37() throws DatabaseException{
    
  }

}