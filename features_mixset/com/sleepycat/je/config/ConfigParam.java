/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../ConfigParam.ump"
public class ConfigParam
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  /**
   * @Abdulaziz  CONFIG_DELIM has a semicolon.
   */
  public static final String CONFIG_DELIM = ";";

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

  public String getName()
  {
    return name;
  }

  public void delete()
  {}

  // line 19 "../../../../ConfigParam.ump"
  public  ConfigParam(String configName, String configDefault, boolean mutable, String description) throws IllegalArgumentException{
    name = configName;
	defaultValue = configDefault;
	this.mutable = mutable;
	this.description = description;
	validateName(configName);
	validateValue(configDefault);
	EnvironmentParams.addSupportedParam(this);
  }

  // line 33 "../../../../ConfigParam.ump"
   public String getDescription(){
    return description;
  }

  // line 37 "../../../../ConfigParam.ump"
   public String getExtraDescription(){
    return null;
  }

  // line 41 "../../../../ConfigParam.ump"
   public String getDefault(){
    return defaultValue;
  }

  // line 45 "../../../../ConfigParam.ump"
   public boolean isMutable(){
    return mutable;
  }


  /**
   * 
   * Validate yourself.
   */
  // line 52 "../../../../ConfigParam.ump"
   public void validate() throws IllegalArgumentException{
    validateName(name);
	validateValue(defaultValue);
  }

  // line 57 "../../../../ConfigParam.ump"
   private void validateName(String name) throws IllegalArgumentException{
    if ((name == null) || (name.length() < 1)) {
	    throw new IllegalArgumentException(" A configuration parameter" + " name can't be null or 0" + " length");
	}
  }

  // line 63 "../../../../ConfigParam.ump"
   public void validateValue(String value) throws IllegalArgumentException{
    
  }

  // line 66 "../../../../ConfigParam.ump"
   public String toString(){
    return name;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../ConfigParam.ump"
  private String defaultValue ;
// line 13 "../../../../ConfigParam.ump"
  private String description ;
// line 15 "../../../../ConfigParam.ump"
  private boolean mutable ;

  
}