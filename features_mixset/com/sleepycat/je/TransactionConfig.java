/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../TransactionConfig.ump"
public class TransactionConfig implements Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TransactionConfig()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * public TransactionConfig() {
   * }
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 36 "../../../TransactionConfig.ump"
   public void setSync(boolean sync){
    this.sync = sync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 43 "../../../TransactionConfig.ump"
   public boolean getSync(){
    return sync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 50 "../../../TransactionConfig.ump"
   public void setNoSync(boolean noSync){
    this.noSync = noSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 57 "../../../TransactionConfig.ump"
   public boolean getNoSync(){
    return noSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 64 "../../../TransactionConfig.ump"
   public void setWriteNoSync(boolean writeNoSync){
    this.writeNoSync = writeNoSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 71 "../../../TransactionConfig.ump"
   public boolean getWriteNoSync(){
    return writeNoSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 78 "../../../TransactionConfig.ump"
   public void setNoWait(boolean noWait){
    this.noWait = noWait;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 85 "../../../TransactionConfig.ump"
   public boolean getNoWait(){
    return noWait;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 92 "../../../TransactionConfig.ump"
   public void setReadUncommitted(boolean readUncommitted){
    this.readUncommitted = readUncommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 99 "../../../TransactionConfig.ump"
   public boolean getReadUncommitted(){
    return readUncommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * @deprecated
   */
  // line 107 "../../../TransactionConfig.ump"
   public void setDirtyRead(boolean dirtyRead){
    setReadUncommitted(dirtyRead);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * @deprecated
   */
  // line 115 "../../../TransactionConfig.ump"
   public boolean getDirtyRead(){
    return getReadUncommitted();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 122 "../../../TransactionConfig.ump"
   public void setReadCommitted(boolean readCommitted){
    this.readCommitted = readCommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 129 "../../../TransactionConfig.ump"
   public boolean getReadCommitted(){
    return readCommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 136 "../../../TransactionConfig.ump"
   public void setSerializableIsolation(boolean serializableIsolation){
    this.serializableIsolation = serializableIsolation;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 143 "../../../TransactionConfig.ump"
   public boolean getSerializableIsolation(){
    return serializableIsolation;
  }


  /**
   * 
   * Used by Environment to create a copy of the application supplied configuration. Done this way to provide non-public cloning.
   */
  // line 150 "../../../TransactionConfig.ump"
  public TransactionConfig cloneConfig(){
    try {
	    return (TransactionConfig) super.clone();
	} catch (CloneNotSupportedException willNeverOccur) {
	    return null;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../TransactionConfig.ump"
  public static final TransactionConfig DEFAULT = new TransactionConfig() ;
// line 12 "../../../TransactionConfig.ump"
  private boolean sync = false ;
// line 14 "../../../TransactionConfig.ump"
  private boolean noSync = false ;
// line 16 "../../../TransactionConfig.ump"
  private boolean writeNoSync = false ;
// line 18 "../../../TransactionConfig.ump"
  private boolean noWait = false ;
// line 20 "../../../TransactionConfig.ump"
  private boolean readUncommitted = false ;
// line 22 "../../../TransactionConfig.ump"
  private boolean readCommitted = false ;
// line 24 "../../../TransactionConfig.ump"
  private boolean serializableIsolation = false ;

  
}