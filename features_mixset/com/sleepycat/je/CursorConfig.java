/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../CursorConfig.ump"
public class CursorConfig implements Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CursorConfig()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * public CursorConfig() {
   * }
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 42 "../../../CursorConfig.ump"
   public void setReadUncommitted(boolean readUncommitted){
    this.readUncommitted = readUncommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 49 "../../../CursorConfig.ump"
   public boolean getReadUncommitted(){
    return readUncommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * @deprecated
   */
  // line 57 "../../../CursorConfig.ump"
   public void setDirtyRead(boolean dirtyRead){
    setReadUncommitted(dirtyRead);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * @deprecated
   */
  // line 65 "../../../CursorConfig.ump"
   public boolean getDirtyRead(){
    return getReadUncommitted();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 72 "../../../CursorConfig.ump"
   public void setReadCommitted(boolean readCommitted){
    this.readCommitted = readCommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 79 "../../../CursorConfig.ump"
   public boolean getReadCommitted(){
    return readCommitted;
  }


  /**
   * 
   * Internal method used by Cursor to create a copy of the application supplied configuration. Done this way to provide non-public cloning.
   */
  // line 86 "../../../CursorConfig.ump"
  public CursorConfig cloneConfig(){
    try {
	    return (CursorConfig) super.clone();
	} catch (CloneNotSupportedException willNeverOccur) {
	    return null;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../CursorConfig.ump"
  public final static CursorConfig DEFAULT = new CursorConfig() ;
// line 15 "../../../CursorConfig.ump"
  public final static CursorConfig READ_UNCOMMITTED = new CursorConfig() ;
// line 21 "../../../CursorConfig.ump"
  public final static CursorConfig DIRTY_READ = READ_UNCOMMITTED ;
// line 26 "../../../CursorConfig.ump"
  public final static CursorConfig READ_COMMITTED = new CursorConfig() ;
// line 28 "../../../CursorConfig.ump"
  private boolean readUncommitted = false ;
// line 30 "../../../CursorConfig.ump"
  private boolean readCommitted = false ;

  
}