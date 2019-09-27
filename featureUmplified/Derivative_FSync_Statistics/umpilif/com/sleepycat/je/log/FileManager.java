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

  // line 6 "../../../../FileManager.ump"
   public long getNFSyncs(){
    return syncManager.getNFSyncs();
  }

  // line 10 "../../../../FileManager.ump"
   public long getNFSyncRequests(){
    return syncManager.getNFSyncRequests();
  }

  // line 14 "../../../../FileManager.ump"
   public long getNFSyncTimeouts(){
    return syncManager.getNTimeouts();
  }

  // line 18 "../../../../FileManager.ump"
  public void loadStats(StatsConfig config, EnvironmentStats stats) throws DatabaseException{
    syncManager.loadStats(config, stats);
  }

}