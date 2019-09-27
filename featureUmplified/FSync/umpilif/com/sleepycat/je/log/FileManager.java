/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileManager.ump"
public class FileManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Flush a file using the group sync mechanism, trying to amortize off other syncs.
   */
  // line 11 "../../../../FileManager.ump"
  public void groupSync() throws DatabaseException{
    syncManager.fsync();
  }

  // line 15 "../../../../FileManager.ump"
   protected void hook452(EnvironmentImpl envImpl) throws DatabaseException{
    syncManager = new FSyncManager(envImpl);
	original(envImpl);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../FileManager.ump"
  private FSyncManager syncManager ;

  
}