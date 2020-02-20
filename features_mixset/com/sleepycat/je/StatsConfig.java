/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.io.PrintStream;

// line 3 "../../../Statistics_StatsConfig.ump"
public class StatsConfig
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StatsConfig()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 26 "../../../Statistics_StatsConfig.ump"
   public void setFast(boolean fast){
    this.fast = fast;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 33 "../../../Statistics_StatsConfig.ump"
   public boolean getFast(){
    return fast;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 40 "../../../Statistics_StatsConfig.ump"
   public void setClear(boolean clear){
    this.clear = clear;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 47 "../../../Statistics_StatsConfig.ump"
   public boolean getClear(){
    return clear;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 54 "../../../Statistics_StatsConfig.ump"
   public void setShowProgressStream(PrintStream showProgressStream){
    this.showProgressStream = showProgressStream;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 61 "../../../Statistics_StatsConfig.ump"
   public PrintStream getShowProgressStream(){
    return showProgressStream;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 68 "../../../Statistics_StatsConfig.ump"
   public void setShowProgressInterval(int showProgressInterval){
    this.showProgressInterval = showProgressInterval;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 75 "../../../Statistics_StatsConfig.ump"
   public int getShowProgressInterval(){
    return showProgressInterval;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../Statistics_StatsConfig.ump"
  static StatsConfig DEFAULT = new StatsConfig() ;
// line 8 "../../../Statistics_StatsConfig.ump"
  private boolean fast = false ;
// line 10 "../../../Statistics_StatsConfig.ump"
  private boolean clear = false ;
// line 12 "../../../Statistics_StatsConfig.ump"
  private PrintStream showProgressStream = null ;
// line 14 "../../../Statistics_StatsConfig.ump"
  private int showProgressInterval = 0 ;

  
}