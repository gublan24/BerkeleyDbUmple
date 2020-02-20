/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.util.Date;
import java.io.Serializable;
import com.sleepycat.bind.serial.*;

// line 3 "../../../Statistics_TransactionStats.ump"
// line 3 "../../../Statistics_TransactionStats_inner.ump"
public class TransactionStats implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TransactionStats()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Internal use only.
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 69 "../../../Statistics_TransactionStats.ump"
   public Active[] getActiveTxns(){
    return activeTxns;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 76 "../../../Statistics_TransactionStats.ump"
   public long getLastCheckpointTime(){
    return lastCheckpointTime;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 83 "../../../Statistics_TransactionStats.ump"
   public long getLastTxnId(){
    return lastTxnId;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 90 "../../../Statistics_TransactionStats.ump"
   public int getNAborts(){
    return nAborts;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 97 "../../../Statistics_TransactionStats.ump"
   public int getNXAAborts(){
    return nXAAborts;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 104 "../../../Statistics_TransactionStats.ump"
   public int getNXAPrepares(){
    return nXAPrepares;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 111 "../../../Statistics_TransactionStats.ump"
   public int getNActive(){
    return nActive;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 118 "../../../Statistics_TransactionStats.ump"
   public int getNBegins(){
    return nBegins;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 125 "../../../Statistics_TransactionStats.ump"
   public int getNCommits(){
    return nCommits;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 132 "../../../Statistics_TransactionStats.ump"
   public int getNXACommits(){
    return nXACommits;
  }


  /**
   * 
   * Internal use only.
   */
  // line 139 "../../../Statistics_TransactionStats.ump"
   public void setActiveTxns(Active [] actives){
    activeTxns = actives;
  }


  /**
   * 
   * Internal use only.
   */
  // line 146 "../../../Statistics_TransactionStats.ump"
   public void setLastCheckpointTime(long l){
    lastCheckpointTime = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 153 "../../../Statistics_TransactionStats.ump"
   public void setLastTxnId(long val){
    lastTxnId = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 160 "../../../Statistics_TransactionStats.ump"
   public void setNAborts(int val){
    nAborts = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 167 "../../../Statistics_TransactionStats.ump"
   public void setNXAAborts(int val){
    nXAAborts = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 174 "../../../Statistics_TransactionStats.ump"
   public void setNActive(int val){
    nActive = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 181 "../../../Statistics_TransactionStats.ump"
   public void setNBegins(int val){
    nBegins = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 188 "../../../Statistics_TransactionStats.ump"
   public void setNCommits(int val){
    nCommits = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 195 "../../../Statistics_TransactionStats.ump"
   public void setNXACommits(int val){
    nXACommits = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 202 "../../../Statistics_TransactionStats.ump"
   public void setNXAPrepares(int val){
    nXAPrepares = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 209 "../../../Statistics_TransactionStats.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
        sb.append("nBegins=").append(nBegins).append('\n');
        sb.append("nAborts=").append(nAborts).append('\n');
        sb.append("nCommits=").append(nCommits).append('\n');
        sb.append("nXAPrepares=").append(nXAPrepares).append('\n');
        sb.append("nXAAborts=").append(nXAAborts).append('\n');
        sb.append("nXACommits=").append(nXACommits).append('\n');
        sb.append("nActive=").append(nActive).append('\n');
        sb.append("activeTxns=[");
        if (activeTxns != null) {
            for (int i = 0; i < activeTxns.length; i += 1) {
                sb.append("  ").append(activeTxns[i]).append('\n');
            }
        }
        sb.append("]\n");
        sb.append("lastTxnId=").append(lastTxnId).append('\n');
        sb.append("lastCheckpointTime=").append(new Date(lastCheckpointTime)).append('\n');
        return sb.toString();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../Statistics_TransactionStats_inner.ump"
  public static class Active implements Serializable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Active()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
  
    /**
     * 
     * Javadoc for this public method is generated via
     * the doc templates in the doc_src directory.
     */
    // line 26 "../../../Statistics_TransactionStats_inner.ump"
     public long getId(){
      return txnId;
    }
  
  
    /**
     * 
     * Javadoc for this public method is generated via
     * the doc templates in the doc_src directory.
     */
    // line 33 "../../../Statistics_TransactionStats_inner.ump"
     public long getParentId(){
      return parentId;
    }
  
  
    /**
     * 
     * Javadoc for this public method is generated via
     * the doc templates in the doc_src directory.
     */
    // line 40 "../../../Statistics_TransactionStats_inner.ump"
     public String getName(){
      return name;
    }
  
  
    /**
     * 
     * Internal use only.
     */
    // line 46 "../../../Statistics_TransactionStats_inner.ump"
     public  Active(String name, long txnId, long parentId){
      this.name=name;
          this.txnId=txnId;
          this.parentId=parentId;
    }
  
    // line 51 "../../../Statistics_TransactionStats_inner.ump"
     public String toString(){
      return "txnId = " + txnId + " txnName = "+ name;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 12 "../../../Statistics_TransactionStats_inner.ump"
    private long txnId ;
  // line 16 "../../../Statistics_TransactionStats_inner.ump"
    private long parentId ;
  // line 20 "../../../Statistics_TransactionStats_inner.ump"
    private String name ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../Statistics_TransactionStats.ump"
  private long lastCheckpointTime ;
// line 18 "../../../Statistics_TransactionStats.ump"
  private long lastTxnId ;
// line 23 "../../../Statistics_TransactionStats.ump"
  private int nActive ;
// line 28 "../../../Statistics_TransactionStats.ump"
  private int nBegins ;
// line 33 "../../../Statistics_TransactionStats.ump"
  private int nAborts ;
// line 38 "../../../Statistics_TransactionStats.ump"
  private int nCommits ;
// line 43 "../../../Statistics_TransactionStats.ump"
  private int nXAAborts ;
// line 48 "../../../Statistics_TransactionStats.ump"
  private int nXAPrepares ;
// line 53 "../../../Statistics_TransactionStats.ump"
  private int nXACommits ;
// line 58 "../../../Statistics_TransactionStats.ump"
  private Active activeTxns[] ;

  
}