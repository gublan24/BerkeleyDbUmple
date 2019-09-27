/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../CheckpointConfig.ump"
public class CheckpointConfig
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CheckpointConfig()
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
  // line 16 "../../../CheckpointConfig.ump"
   public  CheckpointConfig(){
    
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 22 "../../../CheckpointConfig.ump"
   public void setForce(boolean force){
    this.force = force;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 29 "../../../CheckpointConfig.ump"
   public boolean getForce(){
    return force;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 36 "../../../CheckpointConfig.ump"
   public void setMinimizeRecoveryTime(boolean minimizeRecoveryTime){
    this.minimizeRecoveryTime = minimizeRecoveryTime;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 43 "../../../CheckpointConfig.ump"
   public boolean getMinimizeRecoveryTime(){
    return minimizeRecoveryTime;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../CheckpointConfig.ump"
  public final static CheckpointConfig DEFAULT = new CheckpointConfig() ;
// line 8 "../../../CheckpointConfig.ump"
  private boolean force = false ;
// line 10 "../../../CheckpointConfig.ump"
  private boolean minimizeRecoveryTime = false ;

  
}