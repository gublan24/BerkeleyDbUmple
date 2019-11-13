/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.config.ShortConfigParam;
import com.sleepycat.je.config.LongConfigParam;
import com.sleepycat.je.config.IntConfigParam;
import com.sleepycat.je.config.ConfigParam;
import com.sleepycat.je.config.BooleanConfigParam;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../DbConfigManager.ump"
public class DbConfigManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbConfigManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Todo: should this even be a separate class?
   */
  // line 19 "../../../../DbConfigManager.ump"
   public  DbConfigManager(EnvironmentConfig config) throws DbConfigException{
    environmentConfig = config;
  }

  // line 23 "../../../../DbConfigManager.ump"
   public EnvironmentConfig getEnvironmentConfig(){
    return environmentConfig;
  }


  /**
   * 
   * Get this parameter from the environment wide configuration settings.
   * @param configParam
   * @return default for param if param wasn't explicitly set
   */
  // line 32 "../../../../DbConfigManager.ump"
   public synchronized  String get(ConfigParam configParam) throws IllegalArgumentException{
    return environmentConfig.getConfigParam(configParam.getName());
  }


  /**
   * 
   * Get this parameter from the environment wide configuration settings.
   * @param configParam
   * @return default for param if param wasn't explicitly set
   */
  // line 41 "../../../../DbConfigManager.ump"
   public synchronized  String get(String configParamName) throws IllegalArgumentException{
    return environmentConfig.getConfigParam(configParamName);
  }


  /**
   * 
   * Get this parameter from the environment wide configuration settings.
   * @param configParam
   * @return default for param if it wasn't explicitly set.
   */
  // line 50 "../../../../DbConfigManager.ump"
   public boolean getBoolean(BooleanConfigParam configParam) throws DatabaseException{
    String val = get(configParam);
	return Boolean.valueOf(val).booleanValue();
  }


  /**
   * 
   * Get this parameter from the environment wide configuration settings.
   * @param configParam
   * @return default for param if it wasn't explicitly set.
   */
  // line 60 "../../../../DbConfigManager.ump"
   public short getShort(ShortConfigParam configParam) throws DatabaseException{
    String val = get(configParam);
	short shortValue = 0;
	try {
	    shortValue = Short.parseShort(val);
	} catch (NumberFormatException e) {
	    assert false : e.getMessage();
	}
	return shortValue;
  }


  /**
   * 
   * Get this parameter from the environment wide configuration settings.
   * @param configParam
   * @return default for param if it wasn't explicitly set.
   */
  // line 76 "../../../../DbConfigManager.ump"
   public int getInt(IntConfigParam configParam) throws DatabaseException{
    String val = get(configParam);
	int intValue = 0;
	if (val != null) {
	    try {
		intValue = Integer.parseInt(val);
	    } catch (NumberFormatException e) {
		assert false : e.getMessage();
	    }
	}
	return intValue;
  }


  /**
   * 
   * Get this parameter from the environment wide configuration settings.
   * @param configParam
   * @return default for param if it wasn't explicitly set
   */
  // line 94 "../../../../DbConfigManager.ump"
   public long getLong(LongConfigParam configParam) throws DatabaseException{
    String val = get(configParam);
	long longValue = 0;
	if (val != null) {
	    try {
		longValue = Long.parseLong(val);
	    } catch (NumberFormatException e) {
		assert false : e.getMessage();
	    }
	}
	return longValue;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../DbConfigManager.ump"
  private EnvironmentConfig environmentConfig ;

  
}