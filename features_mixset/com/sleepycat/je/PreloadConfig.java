/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../PreloadConfig.ump"
public class PreloadConfig implements Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadConfig()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * public PreloadConfig() {
   * }
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 25 "../../../PreloadConfig.ump"
   public void setMaxBytes(long maxBytes){
    this.maxBytes = maxBytes;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 32 "../../../PreloadConfig.ump"
   public long getMaxBytes(){
    return maxBytes;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 39 "../../../PreloadConfig.ump"
   public void setMaxMillisecs(long maxMillisecs){
    this.maxMillisecs = maxMillisecs;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 46 "../../../PreloadConfig.ump"
   public long getMaxMillisecs(){
    return maxMillisecs;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 53 "../../../PreloadConfig.ump"
   public void setLoadLNs(boolean loadLNs){
    this.loadLNs = loadLNs;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 60 "../../../PreloadConfig.ump"
   public boolean getLoadLNs(){
    return loadLNs;
  }


  /**
   * 
   * Used by Database to create a copy of the application supplied configuration. Done this way to provide non-public cloning.
   */
  // line 67 "../../../PreloadConfig.ump"
  public DatabaseConfig cloneConfig(){
    try {
	    return (DatabaseConfig) super.clone();
	} catch (CloneNotSupportedException willNeverOccur) {
	    return null;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../PreloadConfig.ump"
  public static final PreloadConfig DEFAULT = new PreloadConfig() ;
// line 9 "../../../PreloadConfig.ump"
  private long maxBytes ;
// line 11 "../../../PreloadConfig.ump"
  private long maxMillisecs ;
// line 13 "../../../PreloadConfig.ump"
  private boolean loadLNs ;

  
}