/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../BooleanConfigParam.ump"
public class BooleanConfigParam extends ConfigParam
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BooleanConfigParam()
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
   * Set a boolean parameter w/default.
   * @param configName
   * @param defaultValue
   */
  // line 15 "../../../../BooleanConfigParam.ump"
  public  BooleanConfigParam(String configName, boolean defaultValue, boolean mutable, String description){
    super(configName, Boolean.valueOf(defaultValue).toString(), mutable, description);
  }


  /**
   * 
   * Make sure that value is a valid string for booleans.
   */
  // line 22 "../../../../BooleanConfigParam.ump"
   public void validateValue(String value) throws IllegalArgumentException{
    if (!value.trim().equalsIgnoreCase(Boolean.FALSE.toString())
		&& !value.trim().equalsIgnoreCase(Boolean.TRUE.toString())) {
	    throw new IllegalArgumentException(DEBUG_NAME + ": " + value + " not valid boolean " + name);
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../BooleanConfigParam.ump"
  private static final String DEBUG_NAME = BooleanConfigParam.class.getName() ;

  
}