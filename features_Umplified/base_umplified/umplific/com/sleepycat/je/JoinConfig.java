/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../JoinConfig.ump"
public class JoinConfig implements Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JoinConfig()
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
  // line 15 "../../../JoinConfig.ump"
   public  JoinConfig(){
    
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 21 "../../../JoinConfig.ump"
   public void setNoSort(boolean noSort){
    this.noSort = noSort;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 28 "../../../JoinConfig.ump"
   public boolean getNoSort(){
    return noSort;
  }


  /**
   * 
   * Used by SecondaryDatabase to create a copy of the application supplied configuration. Done this way to provide non-public cloning.
   */
  // line 35 "../../../JoinConfig.ump"
  public JoinConfig cloneConfig(){
    try {
	    return (JoinConfig) super.clone();
	} catch (CloneNotSupportedException willNeverOccur) {
	    return null;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../JoinConfig.ump"
  static JoinConfig DEFAULT = new JoinConfig() ;
// line 9 "../../../JoinConfig.ump"
  private boolean noSort ;

  
}