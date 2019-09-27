/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.io.PrintStream;

// line 3 "../../../StatsConfig.ump"
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
   */
  // line 21 "../../../StatsConfig.ump"
   public  StatsConfig(){
    
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 27 "../../../StatsConfig.ump"
   public void setFast(boolean fast){
    this.fast = fast;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 34 "../../../StatsConfig.ump"
   public boolean getFast(){
    return fast;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 41 "../../../StatsConfig.ump"
   public void setClear(boolean clear){
    this.clear = clear;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 48 "../../../StatsConfig.ump"
   public boolean getClear(){
    return clear;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 55 "../../../StatsConfig.ump"
   public void setShowProgressStream(PrintStream showProgressStream){
    this.showProgressStream = showProgressStream;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 62 "../../../StatsConfig.ump"
   public PrintStream getShowProgressStream(){
    return showProgressStream;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 69 "../../../StatsConfig.ump"
   public void setShowProgressInterval(int showProgressInterval){
    this.showProgressInterval = showProgressInterval;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 76 "../../../StatsConfig.ump"
   public int getShowProgressInterval(){
    return showProgressInterval;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../StatsConfig.ump"
  static StatsConfig DEFAULT = new StatsConfig() ;
// line 9 "../../../StatsConfig.ump"
  private boolean fast = false ;
// line 11 "../../../StatsConfig.ump"
  private boolean clear = false ;
// line 13 "../../../StatsConfig.ump"
  private PrintStream showProgressStream = null ;
// line 15 "../../../StatsConfig.ump"
  private int showProgressInterval = 0 ;

  
}