/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../LockStats.ump"
public class LockStats
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockStats()
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
  // line 44 "../../../LockStats.ump"
   public int getNOwners(){
    return nOwners;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 51 "../../../LockStats.ump"
   public int getNReadLocks(){
    return nReadLocks;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 58 "../../../LockStats.ump"
   public int getNTotalLocks(){
    return nTotalLocks;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 65 "../../../LockStats.ump"
   public int getNWaiters(){
    return nWaiters;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 72 "../../../LockStats.ump"
   public int getNWriteLocks(){
    return nWriteLocks;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 79 "../../../LockStats.ump"
   public long getNRequests(){
    return nRequests;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 86 "../../../LockStats.ump"
   public long getNWaits(){
    return nWaits;
  }


  /**
   * 
   * Internal use only.
   */
  // line 93 "../../../LockStats.ump"
   public void setNOwners(int val){
    nOwners = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 100 "../../../LockStats.ump"
   public void setNReadLocks(int val){
    nReadLocks = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 107 "../../../LockStats.ump"
   public void accumulateNTotalLocks(int val){
    nTotalLocks += val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 114 "../../../LockStats.ump"
   public void setNWaiters(int val){
    nWaiters = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 121 "../../../LockStats.ump"
   public void setNWriteLocks(int val){
    nWriteLocks = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 128 "../../../LockStats.ump"
   public void setNRequests(long requests){
    this.nRequests = requests;
  }


  /**
   * 
   * Internal use only.
   */
  // line 135 "../../../LockStats.ump"
   public void setNWaits(long waits){
    this.nWaits = waits;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 142 "../../../LockStats.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("nTotalLocks=").append(nTotalLocks).append('\n');
	sb.append("nReadLocks=").append(nReadLocks).append('\n');
	sb.append("nWriteLocks=").append(nWriteLocks).append('\n');
	sb.append("nWaiters=").append(nWaiters).append('\n');
	sb.append("nOwners=").append(nOwners).append('\n');
	sb.append("nRequests=").append(nRequests).append('\n');
	sb.append("nWaits=").append(nWaits).append('\n');
	this.hook64(sb);
	return sb.toString();
  }

  // line 155 "../../../LockStats.ump"
   protected void hook64(StringBuffer sb){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../LockStats.ump"
  private int nTotalLocks ;
// line 13 "../../../LockStats.ump"
  private int nReadLocks ;
// line 18 "../../../LockStats.ump"
  private int nWriteLocks ;
// line 23 "../../../LockStats.ump"
  private int nWaiters ;
// line 28 "../../../LockStats.ump"
  private int nOwners ;
// line 33 "../../../LockStats.ump"
  private long nRequests ;
// line 38 "../../../LockStats.ump"
  private long nWaits ;

  
}