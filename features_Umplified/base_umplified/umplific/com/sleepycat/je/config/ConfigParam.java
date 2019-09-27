/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../ConfigParam.ump"
public class ConfigParam
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ConfigParam Attributes
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ConfigParam(String aName)
  {
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  /**
   * @Abdulaziz   public static final String CONFIG_DELIM = " ";
   */
  public String getName()
  {
    return name;
  }

  public void delete()
  {}

  // line 18 "../../../../ConfigParam.ump"
  public  ConfigParam(String configName, String configDefault, boolean mutable, String description) throws IllegalArgumentException{
    name = configName;
	defaultValue = configDefault;
	this.mutable = mutable;
	this.description = description;
	validateName(configName);
	validateValue(configDefault);
	EnvironmentParams.addSupportedParam(this);
  }

  // line 32 "../../../../ConfigParam.ump"
   public String getDescription(){
    return description;
  }

  // line 36 "../../../../ConfigParam.ump"
   public String getExtraDescription(){
    return null;
  }

  // line 40 "../../../../ConfigParam.ump"
   public String getDefault(){
    return defaultValue;
  }

  // line 44 "../../../../ConfigParam.ump"
   public boolean isMutable(){
    return mutable;
  }


  /**
   * 
   * Validate yourself.
   */
  // line 51 "../../../../ConfigParam.ump"
   public void validate() throws IllegalArgumentException{
    validateName(name);
	validateValue(defaultValue);
  }

  // line 56 "../../../../ConfigParam.ump"
   private void validateName(String name) throws IllegalArgumentException{
    if ((name == null) || (name.length() < 1)) {
	    throw new IllegalArgumentException(" A configuration parameter" + " name can't be null or 0" + " length");
	}
  }

  // line 62 "../../../../ConfigParam.ump"
   public void validateValue(String value) throws IllegalArgumentException{
    
  }

  // line 65 "../../../../ConfigParam.ump"
   public String toString(){
    return name;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../ConfigParam.ump"
  private String defaultValue ;
// line 12 "../../../../ConfigParam.ump"
  private String description ;
// line 14 "../../../../ConfigParam.ump"
  private boolean mutable ;

  
}