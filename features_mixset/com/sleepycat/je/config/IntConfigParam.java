/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../IntConfigParam.ump"
public class IntConfigParam extends ConfigParam
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public IntConfigParam()
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

  // line 15 "../../../../IntConfigParam.ump"
  public  IntConfigParam(String configName, Integer minVal, Integer maxVal, Integer defaultValue, boolean mutable, String description){
    super(configName, defaultValue.toString(), mutable, description);
	min = minVal;
	max = maxVal;
  }

  // line 21 "../../../../IntConfigParam.ump"
   private void validate(Integer value) throws IllegalArgumentException{
    if (value != null) {
	    if (min != null) {
		if (value.compareTo(min) < 0) {
		    throw new IllegalArgumentException(DEBUG_NAME + ":" + " param " + name + " doesn't validate, "
			    + value + " is less than min of " + min);
		}
	    }
	    if (max != null) {
		if (value.compareTo(max) > 0) {
		    throw new IllegalArgumentException(DEBUG_NAME + ":" + " param " + name + " doesn't validate, "
			    + value + " is greater than max of " + max);
		}
	    }
	}
  }

  // line 38 "../../../../IntConfigParam.ump"
   public void validateValue(String value) throws IllegalArgumentException{
    try {
	    validate(new Integer(value));
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException(DEBUG_NAME + ": " + value + " not valid value for " + name);
	}
  }

  // line 46 "../../../../IntConfigParam.ump"
   public String getExtraDescription(){
    StringBuffer minMaxDesc = new StringBuffer();
	if (min != null) {
	    minMaxDesc.append("# minimum = ").append(min);
	}
	if (max != null) {
	    if (min != null) {
		minMaxDesc.append("\n");
	    }
	    minMaxDesc.append("# maximum = ").append(max);
	}
	return minMaxDesc.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../IntConfigParam.ump"
  private static final String DEBUG_NAME = IntConfigParam.class.getName() ;
// line 9 "../../../../IntConfigParam.ump"
  private Integer min ;
// line 11 "../../../../IntConfigParam.ump"
  private Integer max ;

  
}