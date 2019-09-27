/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.util.Date;
import java.io.Serializable;

// line 3 "../../../TransactionStats.ump"
// line 3 "../../../TransactionStats_inner.ump"
public class TransactionStats
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
   */
  // line 63 "../../../TransactionStats.ump"
   public  TransactionStats(){
    
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 69 "../../../TransactionStats.ump"
   public Active[] getActiveTxns(){
    return activeTxns;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 76 "../../../TransactionStats.ump"
   public long getLastCheckpointTime(){
    return lastCheckpointTime;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 83 "../../../TransactionStats.ump"
   public long getLastTxnId(){
    return lastTxnId;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 90 "../../../TransactionStats.ump"
   public int getNAborts(){
    return nAborts;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 97 "../../../TransactionStats.ump"
   public int getNXAAborts(){
    return nXAAborts;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 104 "../../../TransactionStats.ump"
   public int getNXAPrepares(){
    return nXAPrepares;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 111 "../../../TransactionStats.ump"
   public int getNActive(){
    return nActive;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 118 "../../../TransactionStats.ump"
   public int getNBegins(){
    return nBegins;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 125 "../../../TransactionStats.ump"
   public int getNCommits(){
    return nCommits;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 132 "../../../TransactionStats.ump"
   public int getNXACommits(){
    return nXACommits;
  }


  /**
   * 
   * Internal use only.
   */
  // line 139 "../../../TransactionStats.ump"
   public void setActiveTxns(Active [] actives){
    activeTxns = actives;
  }


  /**
   * 
   * Internal use only.
   */
  // line 146 "../../../TransactionStats.ump"
   public void setLastCheckpointTime(long l){
    lastCheckpointTime = l;
  }


  /**
   * 
   * Internal use only.
   */
  // line 153 "../../../TransactionStats.ump"
   public void setLastTxnId(long val){
    lastTxnId = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 160 "../../../TransactionStats.ump"
   public void setNAborts(int val){
    nAborts = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 167 "../../../TransactionStats.ump"
   public void setNXAAborts(int val){
    nXAAborts = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 174 "../../../TransactionStats.ump"
   public void setNActive(int val){
    nActive = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 181 "../../../TransactionStats.ump"
   public void setNBegins(int val){
    nBegins = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 188 "../../../TransactionStats.ump"
   public void setNCommits(int val){
    nCommits = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 195 "../../../TransactionStats.ump"
   public void setNXACommits(int val){
    nXACommits = val;
  }


  /**
   * 
   * Internal use only.
   */
  // line 202 "../../../TransactionStats.ump"
   public void setNXAPrepares(int val){
    nXAPrepares = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 209 "../../../TransactionStats.ump"
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
  
  package com.sleepycat.je;
  
  // line 4 "../../../TransactionStats_inner.ump"
  public static class Active
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
    // line 26 "../../../TransactionStats_inner.ump"
     public long getId(){
      return txnId;
    }
  
  
    /**
     * 
     * Javadoc for this public method is generated via
     * the doc templates in the doc_src directory.
     */
    // line 33 "../../../TransactionStats_inner.ump"
     public long getParentId(){
      return parentId;
    }
  
  
    /**
     * 
     * Javadoc for this public method is generated via
     * the doc templates in the doc_src directory.
     */
    // line 40 "../../../TransactionStats_inner.ump"
     public String getName(){
      return name;
    }
  
  
    /**
     * 
     * Internal use only.
     */
    // line 46 "../../../TransactionStats_inner.ump"
     public  Active(String name, long txnId, long parentId){
      this.name=name;
          this.txnId=txnId;
          this.parentId=parentId;
    }
  
    // line 51 "../../../TransactionStats_inner.ump"
     public String toString(){
      return "txnId = " + txnId + " txnName = "+ name;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 12 "../../../TransactionStats_inner.ump"
    private long txnId ;
  // line 16 "../../../TransactionStats_inner.ump"
    private long parentId ;
  // line 20 "../../../TransactionStats_inner.ump"
    private String name ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../TransactionStats.ump"
  private long lastCheckpointTime ;
// line 17 "../../../TransactionStats.ump"
  private long lastTxnId ;
// line 22 "../../../TransactionStats.ump"
  private int nActive ;
// line 27 "../../../TransactionStats.ump"
  private int nBegins ;
// line 32 "../../../TransactionStats.ump"
  private int nAborts ;
// line 37 "../../../TransactionStats.ump"
  private int nCommits ;
// line 42 "../../../TransactionStats.ump"
  private int nXAAborts ;
// line 47 "../../../TransactionStats.ump"
  private int nXAPrepares ;
// line 52 "../../../TransactionStats.ump"
  private int nXACommits ;
// line 57 "../../../TransactionStats.ump"
  private Active activeTxns[] ;

  
}