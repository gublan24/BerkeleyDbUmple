/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.config.ConfigParam;
import java.util.Properties;
import java.util.Iterator;
import java.util.Enumeration;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../EnvironmentMutableConfig.ump"
public class EnvironmentMutableConfig implements Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EnvironmentMutableConfig Attributes
  private Properties props;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentMutableConfig()
  {
    props = new Properties();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setProps(Properties aProps)
  {
    boolean wasSet = false;
    props = aProps;
    wasSet = true;
    return wasSet;
  }

  /**
   * 
   * Note that in the implementation we choose not to extend Properties  in order to keep the configuration type safe.
   */
  public Properties getProps()
  {
    return props;
  }

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   * public EnvironmentMutableConfig() {
   * props = new Properties();
   * }
   * 
   * Used by EnvironmentConfig to construct from properties.
   */
  // line 45 "../../../EnvironmentMutableConfig.ump"
  public  EnvironmentMutableConfig(Properties properties) throws IllegalArgumentException{
    validateProperties(properties);
	props = new Properties();
	props.putAll(properties);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 54 "../../../EnvironmentMutableConfig.ump"
   public void setTxnNoSync(boolean noSync){
    txnNoSync = noSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 61 "../../../EnvironmentMutableConfig.ump"
   public boolean getTxnNoSync(){
    return txnNoSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 68 "../../../EnvironmentMutableConfig.ump"
   public void setTxnWriteNoSync(boolean writeNoSync){
    txnWriteNoSync = writeNoSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 75 "../../../EnvironmentMutableConfig.ump"
   public boolean getTxnWriteNoSync(){
    return txnWriteNoSync;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 82 "../../../EnvironmentMutableConfig.ump"
   public void setCacheSize(long totalBytes) throws IllegalArgumentException{
    setVal(EnvironmentParams.MAX_MEMORY, Long.toString(totalBytes));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 89 "../../../EnvironmentMutableConfig.ump"
   public long getCacheSize(){
    return cacheSize;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 96 "../../../EnvironmentMutableConfig.ump"
   public void setCachePercent(int percent) throws IllegalArgumentException{
    setVal(EnvironmentParams.MAX_MEMORY_PERCENT, Integer.toString(percent));
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 103 "../../../EnvironmentMutableConfig.ump"
   public int getCachePercent(){
    String val = getVal(EnvironmentParams.MAX_MEMORY_PERCENT);
	try {
	    return Integer.parseInt(val);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Cache percent is not a valid integer: " + e.getMessage());
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 115 "../../../EnvironmentMutableConfig.ump"
   public void setConfigParam(String paramName, String value) throws IllegalArgumentException{
    ConfigParam param = (ConfigParam) EnvironmentParams.SUPPORTED_PARAMS.get(paramName);
	if (param == null) {
	    throw new IllegalArgumentException(paramName + " is not a valid BDBJE environment configuration");
	}
	if (!param.isMutable()) {
	    throw new IllegalArgumentException(paramName + " is not a mutable BDBJE environment configuration");
	}
	setVal(param, value);
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 129 "../../../EnvironmentMutableConfig.ump"
   public String getConfigParam(String paramName) throws IllegalArgumentException{
    ConfigParam param = (ConfigParam) EnvironmentParams.SUPPORTED_PARAMS.get(paramName);
	if (param == null) {
	    throw new IllegalArgumentException(paramName + " is not a valid BDBJE environment configuration");
	}
	return getVal(param);
  }


  /**
   * 
   * Gets either the value stored in this configuration or the default value for this param.
   */
  // line 140 "../../../EnvironmentMutableConfig.ump"
  public String getVal(ConfigParam param){
    String val = props.getProperty(param.getName());
	if (val == null) {
	    val = param.getDefault();
	}
	return val;
  }


  /**
   * 
   * Sets and validates the specified parameter.
   */
  // line 151 "../../../EnvironmentMutableConfig.ump"
  public void setVal(ConfigParam param, String val) throws IllegalArgumentException{
    if (validateParams) {
	    param.validateValue(val);
	}
	props.setProperty(param.getName(), val);
  }

  // line 158 "../../../EnvironmentMutableConfig.ump"
  public void setValidateParams(boolean validateParams){
    this.validateParams = validateParams;
  }


  /**
   * 
   * Validate a property bag passed in a construction time.
   */
  // line 165 "../../../EnvironmentMutableConfig.ump"
  public void validateProperties(Properties props) throws IllegalArgumentException{
    Enumeration propNames = props.propertyNames();
	while (propNames.hasMoreElements()) {
	    String name = (String) propNames.nextElement();
	    ConfigParam param = (ConfigParam) EnvironmentParams.SUPPORTED_PARAMS.get(name);
	    if (param == null) {
		throw new IllegalArgumentException(name + " is not a valid BDBJE environment configuration");
	    }
	    param.validateValue(props.getProperty(name));
	}
  }


  /**
   * 
   * Check that the immutable values in the environment config used to open an environment match those in the config object saved by the underlying shared EnvironmentImpl.
   */
  // line 180 "../../../EnvironmentMutableConfig.ump"
  public void checkImmutablePropsForEquality(EnvironmentMutableConfig passedConfig) throws IllegalArgumentException{
    Properties passedProps = passedConfig.props;
	Iterator iter = EnvironmentParams.SUPPORTED_PARAMS.keySet().iterator();
	while (iter.hasNext()) {
	    String paramName = (String) iter.next();
	    ConfigParam param = (ConfigParam) EnvironmentParams.SUPPORTED_PARAMS.get(paramName);
	    assert param != null;
	    if (!param.isMutable()) {
		String paramVal = props.getProperty(paramName);
		String useParamVal = passedProps.getProperty(paramName);
		if ((paramVal != null) ? (!paramVal.equals(useParamVal)) : (useParamVal != null)) {
		    throw new IllegalArgumentException(paramName + " is set to " + useParamVal
			    + " in the config parameter" + " which is incompatible" + " with the value of " + paramVal
			    + " in the" + " underlying environment");
		}
	    }
	}
  }


  /**
   * 
   * Overrides Object.clone() to clone all properties, used by this class and EnvironmentConfig.
   */
  // line 202 "../../../EnvironmentMutableConfig.ump"
   protected Object clone() throws CloneNotSupportedException{
    EnvironmentMutableConfig copy = (EnvironmentMutableConfig) super.clone();
	copy.props = (Properties) props.clone();
	return copy;
  }


  /**
   * 
   * Used by Environment to create a copy of the application supplied configuration. Done this way to provide non-public cloning.
   */
  // line 211 "../../../EnvironmentMutableConfig.ump"
  public EnvironmentMutableConfig cloneMutableConfig(){
    try {
	    EnvironmentMutableConfig copy = (EnvironmentMutableConfig) clone();
	    copy.clearImmutableProps();
	    return copy;
	} catch (CloneNotSupportedException willNeverOccur) {
	    return null;
	}
  }


  /**
   * 
   * Copies the per-handle properties of this object to the given config object.
   */
  // line 224 "../../../EnvironmentMutableConfig.ump"
  public void copyHandlePropsTo(EnvironmentMutableConfig other){
    other.txnNoSync = txnNoSync;
	other.txnWriteNoSync = txnWriteNoSync;
  }


  /**
   * 
   * Copies all mutable props to the given config object.
   */
  // line 232 "../../../EnvironmentMutableConfig.ump"
  public void copyMutablePropsTo(EnvironmentMutableConfig toConfig){
    Properties toProps = toConfig.props;
	Enumeration propNames = props.propertyNames();
	while (propNames.hasMoreElements()) {
	    String paramName = (String) propNames.nextElement();
	    ConfigParam param = (ConfigParam) EnvironmentParams.SUPPORTED_PARAMS.get(paramName);
	    assert param != null;
	    if (param.isMutable()) {
		String newVal = props.getProperty(paramName);
		toProps.setProperty(paramName, newVal);
	    }
	}
  }


  /**
   * 
   * Fill in the properties calculated by the environment to the given config object.
   */
  // line 249 "../../../EnvironmentMutableConfig.ump"
  public void fillInEnvironmentGeneratedProps(EnvironmentImpl envImpl){
    cacheSize = envImpl.getMemoryBudget().getMaxMemory();
  }


  /**
   * 
   * Removes all immutable props.
   */
  // line 256 "../../../EnvironmentMutableConfig.ump"
   private void clearImmutableProps(){
    Enumeration propNames = props.propertyNames();
	while (propNames.hasMoreElements()) {
	    String paramName = (String) propNames.nextElement();
	    ConfigParam param = (ConfigParam) EnvironmentParams.SUPPORTED_PARAMS.get(paramName);
	    assert param != null;
	    if (!param.isMutable()) {
		props.remove(paramName);
	    }
	}
  }


  /**
   * 
   * For unit testing, to prevent loading of je.properties.
   */
  // line 271 "../../../EnvironmentMutableConfig.ump"
  public void setLoadPropertyFile(boolean loadPropertyFile){
    this.loadPropertyFile = loadPropertyFile;
  }


  /**
   * 
   * For unit testing, to prevent loading of je.properties.
   */
  // line 278 "../../../EnvironmentMutableConfig.ump"
  public boolean getLoadPropertyFile(){
    return loadPropertyFile;
  }


  /**
   * 
   * Testing support
   */
  // line 285 "../../../EnvironmentMutableConfig.ump"
  public int getNumExplicitlySetParams(){
    return props.size();
  }

  // line 289 "../../../EnvironmentMutableConfig.ump"
   public String toString(){
    return props.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../EnvironmentMutableConfig.ump"
  private boolean txnNoSync = false ;
// line 15 "../../../EnvironmentMutableConfig.ump"
  private boolean txnWriteNoSync = false ;
// line 17 "../../../EnvironmentMutableConfig.ump"
  protected long cacheSize ;
// line 27 "../../../EnvironmentMutableConfig.ump"
  private boolean loadPropertyFile = true ;
// line 32 "../../../EnvironmentMutableConfig.ump"
  private boolean validateParams = true ;

  
}