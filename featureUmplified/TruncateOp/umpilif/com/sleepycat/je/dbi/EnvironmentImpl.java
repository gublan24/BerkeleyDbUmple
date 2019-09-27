/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../EnvironmentImpl.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Truncate a database. Return a new DatabaseImpl object which represents the new truncated database. The old database is marked as deleted.
   * @deprecated This supports Database.truncate(), which is deprecated.
   */
  // line 10 "../../../../EnvironmentImpl.ump"
   public TruncateResult truncate(Locker locker, DatabaseImpl database) throws DatabaseException{
    return dbMapTree.truncate(locker, database, true);
  }


  /**
   * 
   * Truncate a database.
   */
  // line 17 "../../../../EnvironmentImpl.ump"
   public long truncate(Locker locker, String databaseName, boolean returnCount) throws DatabaseException{
    return dbMapTree.truncate(locker, databaseName, returnCount);
  }

}