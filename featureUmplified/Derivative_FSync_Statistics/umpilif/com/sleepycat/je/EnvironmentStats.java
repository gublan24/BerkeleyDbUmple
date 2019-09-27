/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../EnvironmentStats.ump"
public class EnvironmentStats
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentStats()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 15 "../../../EnvironmentStats.ump"
   public long getNFSyncs(){
    return nFSyncs;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 22 "../../../EnvironmentStats.ump"
   public long getNFSyncRequests(){
    return nFSyncRequests;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 29 "../../../EnvironmentStats.ump"
   public long getNFSyncTimeouts(){
    return nFSyncTimeouts;
  }


  /**
   * 
   * Internal use only.
   */
  // line 36 "../../../EnvironmentStats.ump"
   public void setNFSyncs(long val){
    nFSyncs = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 43 "../../../EnvironmentStats.ump"
   public void setNFSyncRequests(long val){
    nFSyncRequests = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 50 "../../../EnvironmentStats.ump"
   public void setNFSyncTimeouts(long val){
    nFSyncTimeouts = val;
  }

  // line 54 "../../../EnvironmentStats.ump"
   protected void hook60(){
    nFSyncs = 0;
	nFSyncRequests = 0;
	nFSyncTimeouts = 0;
	original();
  }

  // line 61 "../../../EnvironmentStats.ump"
   protected void hook61(StringBuffer sb){
    sb.append("nFSyncs=").append(nFSyncs).append('\n');
	sb.append("nFSyncRequests=").append(nFSyncRequests).append('\n');
	sb.append("nFSyncTimeouts=").append(nFSyncTimeouts).append('\n');
	original(sb);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../EnvironmentStats.ump"
  private long nFSyncs ;
// line 7 "../../../EnvironmentStats.ump"
  private long nFSyncRequests ;
// line 9 "../../../EnvironmentStats.ump"
  private long nFSyncTimeouts ;

  
}