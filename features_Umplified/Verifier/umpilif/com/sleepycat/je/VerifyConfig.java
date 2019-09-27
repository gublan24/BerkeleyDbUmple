/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.io.PrintStream;

// line 3 "../../../VerifyConfig.ump"
public class VerifyConfig
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public VerifyConfig()
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
  // line 23 "../../../VerifyConfig.ump"
   public  VerifyConfig(){
    
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 29 "../../../VerifyConfig.ump"
   public void setPropagateExceptions(boolean propagate){
    propagateExceptions = propagate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 36 "../../../VerifyConfig.ump"
   public boolean getPropagateExceptions(){
    return propagateExceptions;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 43 "../../../VerifyConfig.ump"
   public void setAggressive(boolean aggressive){
    this.aggressive = aggressive;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 50 "../../../VerifyConfig.ump"
   public boolean getAggressive(){
    return aggressive;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 57 "../../../VerifyConfig.ump"
   public void setPrintInfo(boolean printInfo){
    this.printInfo = printInfo;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 64 "../../../VerifyConfig.ump"
   public boolean getPrintInfo(){
    return printInfo;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 71 "../../../VerifyConfig.ump"
   public void setShowProgressStream(PrintStream showProgressStream){
    this.showProgressStream = showProgressStream;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 78 "../../../VerifyConfig.ump"
   public PrintStream getShowProgressStream(){
    return showProgressStream;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 85 "../../../VerifyConfig.ump"
   public void setShowProgressInterval(int showProgressInterval){
    this.showProgressInterval = showProgressInterval;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 92 "../../../VerifyConfig.ump"
   public int getShowProgressInterval(){
    return showProgressInterval;
  }


  /**
   * 
   * Returns the values for each configuration attribute.
   * @return the values for each configuration attribute.
   */
  // line 100 "../../../VerifyConfig.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("propagateExceptions=").append(propagateExceptions);
	return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../VerifyConfig.ump"
  static VerifyConfig DEFAULT = new VerifyConfig() ;
// line 9 "../../../VerifyConfig.ump"
  private boolean propagateExceptions = false ;
// line 11 "../../../VerifyConfig.ump"
  private boolean aggressive = false ;
// line 13 "../../../VerifyConfig.ump"
  private boolean printInfo = false ;
// line 15 "../../../VerifyConfig.ump"
  private PrintStream showProgressStream = null ;
// line 17 "../../../VerifyConfig.ump"
  private int showProgressInterval = 0 ;

  
}