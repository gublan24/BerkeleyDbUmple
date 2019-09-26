/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Cursor.ump"
public class Cursor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cursor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../Cursor.ump"
   protected void hook36(DatabaseImpl dbImpl) throws DatabaseException{
    this.logger = dbImpl.getDbEnvironment().getLogger();
	original(dbImpl);
  }


  /**
   * 
   * Copy constructor.
   */
  // line 16 "../../../Cursor.ump"
  public  Cursor(Cursor cursor, boolean samePosition) throws DatabaseException{
    logger = dbImpl.getDbEnvironment().getLogger();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../Cursor.ump"
  private Logger logger ;

  
}