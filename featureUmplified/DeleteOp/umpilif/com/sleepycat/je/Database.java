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
   protected void hook55() throws DatabaseException{
    databaseImpl.checkIsDeleted("preload");
	original();
  }

  // line 11 "../../../Database.ump"
   protected void hook56() throws DatabaseException{
    databaseImpl.checkIsDeleted("preload");
	original();
  }

  // line 16 "../../../Database.ump"
   protected void hook57() throws DatabaseException{
    databaseImpl.checkIsDeleted("preload");
	original();
  }

}