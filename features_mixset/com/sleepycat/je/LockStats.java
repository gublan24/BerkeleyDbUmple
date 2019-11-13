/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.io.Serializable;
import com.sleepycat.bind.serial.*;

// line 3 "../../../LockStats.ump"
// line 3 "../../../Statistics_LockStats.ump"
public class LockStats implements Serializable
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
  // line 44 "../../../Statistics_LockStats.ump"
   public int getNOwners(){
    return nOwners;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 51 "../../../Statistics_LockStats.ump"
   public int getNReadLocks(){
    return nReadLocks;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 58 "../../../Statistics_LockStats.ump"
   public int getNTotalLocks(){
    return nTotalLocks;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 65 "../../../Statistics_LockStats.ump"
   public int getNWaiters(){
    return nWaiters;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 72 "../../../Statistics_LockStats.ump"
   public int getNWriteLocks(){
    return nWriteLocks;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 79 "../../../Statistics_LockStats.ump"
   public long getNRequests(){
    return nRequests;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 86 "../../../Statistics_LockStats.ump"
   public long getNWaits(){
    return nWaits;
  }


  /**
   * 
   * Internal use only.
   */
  // line 93 "../../../Statistics_LockStats.ump"
   public void setNOwners(int val){
    nOwners = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 100 "../../../Statistics_LockStats.ump"
   public void setNReadLocks(int val){
    nReadLocks = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 107 "../../../Statistics_LockStats.ump"
   public void accumulateNTotalLocks(int val){
    nTotalLocks += val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 114 "../../../Statistics_LockStats.ump"
   public void setNWaiters(int val){
    nWaiters = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 121 "../../../Statistics_LockStats.ump"
   public void setNWriteLocks(int val){
    nWriteLocks = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 128 "../../../Statistics_LockStats.ump"
   public void setNRequests(long requests){
    this.nRequests = requests;
  }


  /**
   * 
   * Internal use only.
   */
  // line 135 "../../../Statistics_LockStats.ump"
   public void setNWaits(long waits){
    this.nWaits = waits;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 142 "../../../Statistics_LockStats.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
			sb.append("nTotalLocks=").append(nTotalLocks).append('\n');
			sb.append("nReadLocks=").append(nReadLocks).append('\n');
			sb.append("nWriteLocks=").append(nWriteLocks).append('\n');
			sb.append("nWaiters=").append(nWaiters).append('\n');
			sb.append("nOwners=").append(nOwners).append('\n');
			sb.append("nRequests=").append(nRequests).append('\n');
			sb.append("nWaits=").append(nWaits).append('\n');
			Label64: //this.hook64(sb);
			return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../Statistics_LockStats.ump"
  private int nTotalLocks ;
// line 13 "../../../Statistics_LockStats.ump"
  private int nReadLocks ;
// line 18 "../../../Statistics_LockStats.ump"
  private int nWriteLocks ;
// line 23 "../../../Statistics_LockStats.ump"
  private int nWaiters ;
// line 28 "../../../Statistics_LockStats.ump"
  private int nOwners ;
// line 33 "../../../Statistics_LockStats.ump"
  private long nRequests ;
// line 38 "../../../Statistics_LockStats.ump"
  private long nWaits ;

  
}