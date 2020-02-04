/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.config.ConfigParam;
import java.util.Properties;

// line 3 "../../../EnvironmentConfig.ump"
public class EnvironmentConfig extends EnvironmentMutableConfig
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentConfig()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * public EnvironmentConfig() {
   * super();
   * }
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 40 "../../../EnvironmentConfig.ump"
   public  EnvironmentConfig(Properties properties) throws IllegalArgumentException{
    super(properties);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 47 "../../../EnvironmentConfig.ump"
   public void setAllowCreate(boolean allowCreate){
    this.allowCreate = allowCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 54 "../../../EnvironmentConfig.ump"
   public boolean getAllowCreate(){
    return allowCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 61 "../../../EnvironmentConfig.ump"
   public void setLockTimeout(long timeout) throws IllegalArgumentException{
    setVal(EnvironmentParams.LOCK_TIMEOUT, Long.toString(timeout));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 68 "../../../EnvironmentConfig.ump"
   public long getLockTimeout(){
    String val = getVal(EnvironmentParams.LOCK_TIMEOUT);
	long timeout = 0;
	try {
	    timeout = Long.parseLong(val);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Bad value for timeout:" + e.getMessage());
	}
	return timeout;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 82 "../../../EnvironmentConfig.ump"
   public void setReadOnly(boolean readOnly){
    setVal(EnvironmentParams.ENV_RDONLY, Boolean.toString(readOnly));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 89 "../../../EnvironmentConfig.ump"
   public boolean getReadOnly(){
    String val = getVal(EnvironmentParams.ENV_RDONLY);
	return (Boolean.valueOf(val)).booleanValue();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 97 "../../../EnvironmentConfig.ump"
   public void setTransactional(boolean transactional){
    setVal(EnvironmentParams.ENV_INIT_TXN, Boolean.toString(transactional));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 104 "../../../EnvironmentConfig.ump"
   public boolean getTransactional(){
    String val = getVal(EnvironmentParams.ENV_INIT_TXN);
	return (Boolean.valueOf(val)).booleanValue();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 112 "../../../EnvironmentConfig.ump"
   public void setLocking(boolean locking){
    setVal(EnvironmentParams.ENV_INIT_LOCKING, Boolean.toString(locking));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 119 "../../../EnvironmentConfig.ump"
   public boolean getLocking(){
    String val = getVal(EnvironmentParams.ENV_INIT_LOCKING);
	return (Boolean.valueOf(val)).booleanValue();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 127 "../../../EnvironmentConfig.ump"
   public void setTxnTimeout(long timeout) throws IllegalArgumentException{
    setVal(EnvironmentParams.TXN_TIMEOUT, Long.toString(timeout));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 134 "../../../EnvironmentConfig.ump"
   public long getTxnTimeout(){
    String val = getVal(EnvironmentParams.TXN_TIMEOUT);
	long timeout = 0;
	try {
	    timeout = Long.parseLong(val);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Bad value for timeout:" + e.getMessage());
	}
	return timeout;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 148 "../../../EnvironmentConfig.ump"
   public void setTxnSerializableIsolation(boolean txnSerializableIsolation){
    setVal(EnvironmentParams.TXN_SERIALIZABLE_ISOLATION, Boolean.toString(txnSerializableIsolation));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 155 "../../../EnvironmentConfig.ump"
   public boolean getTxnSerializableIsolation(){
    String val = getVal(EnvironmentParams.TXN_SERIALIZABLE_ISOLATION);
	return (Boolean.valueOf(val)).booleanValue();
  }


  /**
   * 
   * For unit testing, to set readCommitted as the default.
   */
  // line 163 "../../../EnvironmentConfig.ump"
  public void setTxnReadCommitted(boolean txnReadCommitted){
    this.txnReadCommitted = txnReadCommitted;
  }


  /**
   * 
   * For unit testing, to set readCommitted as the default.
   */
  // line 170 "../../../EnvironmentConfig.ump"
  public boolean getTxnReadCommitted(){
    return txnReadCommitted;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 177 "../../../EnvironmentConfig.ump"
   public void setConfigParam(String paramName, String value) throws IllegalArgumentException{
    ConfigParam param = (ConfigParam) EnvironmentParams.SUPPORTED_PARAMS.get(paramName);
	if (param == null) {
	    throw new IllegalArgumentException(paramName + " is not a valid BDBJE environment configuration");
	}
	setVal(param, value);
  }


  /**
   * 
   * For unit testing, to prevent creating the utilization profile DB.
   */
  // line 188 "../../../EnvironmentConfig.ump"
  public void setCreateUP(boolean createUP){
    this.createUP = createUP;
  }


  /**
   * 
   * For unit testing, to prevent creating the utilization profile DB.
   */
  // line 195 "../../../EnvironmentConfig.ump"
  public boolean getCreateUP(){
    return createUP;
  }


  /**
   * 
   * For unit testing, to prevent writing utilization data during checkpoint.
   */
  // line 202 "../../../EnvironmentConfig.ump"
  public void setCheckpointUP(boolean checkpointUP){
    this.checkpointUP = checkpointUP;
  }


  /**
   * 
   * For unit testing, to prevent writing utilization data during checkpoint.
   */
  // line 209 "../../../EnvironmentConfig.ump"
  public boolean getCheckpointUP(){
    return checkpointUP;
  }


  /**
   * 
   * Used by Environment to create a copy of the application supplied configuration.
   */
  // line 216 "../../../EnvironmentConfig.ump"
  public EnvironmentConfig cloneConfig(){
    try {
	    return (EnvironmentConfig) clone();
	} catch (CloneNotSupportedException willNeverOccur) {
	    return null;
	}
  }

  // line 224 "../../../EnvironmentConfig.ump"
   public String toString(){
    return ("allowCreate=" + allowCreate + "\n" + super.toString());
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../EnvironmentConfig.ump"
  static EnvironmentConfig DEFAULT = new EnvironmentConfig() ;
// line 15 "../../../EnvironmentConfig.ump"
  private boolean createUP = true ;
// line 20 "../../../EnvironmentConfig.ump"
  private boolean checkpointUP = true ;
// line 22 "../../../EnvironmentConfig.ump"
  private boolean allowCreate = false ;
// line 27 "../../../EnvironmentConfig.ump"
  private boolean txnReadCommitted = false ;

  
}