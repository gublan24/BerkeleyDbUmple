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


  /**
   * 
   * Creates a database but does not open or fully initialize it. Is protected for use in compat package.
   */
  // line 11 "../../../Database.ump"
  public  Database(Environment env){
    logger = envHandle.getEnvironmentImpl().getLogger();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../Database.ump"
  private Logger logger ;

  
}