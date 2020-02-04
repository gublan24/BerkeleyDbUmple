/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../SequenceConfig.ump"
public class SequenceConfig
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SequenceConfig()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * public SequenceConfig() {
   * }
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 36 "../../../SequenceConfig.ump"
   public void setAllowCreate(boolean allowCreate){
    this.allowCreate = allowCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 43 "../../../SequenceConfig.ump"
   public boolean getAllowCreate(){
    return allowCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 50 "../../../SequenceConfig.ump"
   public void setCacheSize(int cacheSize){
    this.cacheSize = cacheSize;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 57 "../../../SequenceConfig.ump"
   public int getCacheSize(){
    return cacheSize;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 64 "../../../SequenceConfig.ump"
   public void setDecrement(boolean decrement){
    this.decrement = decrement;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 71 "../../../SequenceConfig.ump"
   public boolean getDecrement(){
    return decrement;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 78 "../../../SequenceConfig.ump"
   public void setExclusiveCreate(boolean exclusiveCreate){
    this.exclusiveCreate = exclusiveCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 85 "../../../SequenceConfig.ump"
   public boolean getExclusiveCreate(){
    return exclusiveCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 92 "../../../SequenceConfig.ump"
   public void setInitialValue(long initialValue){
    this.initialValue = initialValue;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 99 "../../../SequenceConfig.ump"
   public long getInitialValue(){
    return initialValue;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 106 "../../../SequenceConfig.ump"
   public void setAutoCommitNoSync(boolean autoCommitNoSync){
    this.autoCommitNoSync = autoCommitNoSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 113 "../../../SequenceConfig.ump"
   public boolean getAutoCommitNoSync(){
    return autoCommitNoSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 120 "../../../SequenceConfig.ump"
   public void setRange(long min, long max){
    this.rangeMin = min;
	this.rangeMax = max;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 128 "../../../SequenceConfig.ump"
   public long getRangeMin(){
    return rangeMin;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 135 "../../../SequenceConfig.ump"
   public long getRangeMax(){
    return rangeMax;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 142 "../../../SequenceConfig.ump"
   public void setWrap(boolean wrap){
    this.wrap = wrap;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 149 "../../../SequenceConfig.ump"
   public boolean getWrap(){
    return wrap;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../SequenceConfig.ump"
  public static final SequenceConfig DEFAULT = new SequenceConfig() ;
// line 8 "../../../SequenceConfig.ump"
  private int cacheSize = 0 ;
// line 10 "../../../SequenceConfig.ump"
  private long rangeMin = Long.MIN_VALUE ;
// line 12 "../../../SequenceConfig.ump"
  private long rangeMax = Long.MAX_VALUE ;
// line 14 "../../../SequenceConfig.ump"
  private long initialValue = 0L ;
// line 16 "../../../SequenceConfig.ump"
  private boolean allowCreate ;
// line 18 "../../../SequenceConfig.ump"
  private boolean decrement ;
// line 20 "../../../SequenceConfig.ump"
  private boolean exclusiveCreate ;
// line 22 "../../../SequenceConfig.ump"
  private boolean autoCommitNoSync ;
// line 24 "../../../SequenceConfig.ump"
  private boolean wrap ;

  
}