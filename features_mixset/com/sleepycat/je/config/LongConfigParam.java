/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../LongConfigParam.ump"
public class LongConfigParam extends ConfigParam
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LongConfigParam()
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

  // line 15 "../../../../LongConfigParam.ump"
  public  LongConfigParam(String configName, Long minVal, Long maxVal, Long defaultValue, boolean mutable, String description){
    super(configName, defaultValue.toString(), mutable, description);
	min = minVal;
	max = maxVal;
  }

  // line 21 "../../../../LongConfigParam.ump"
   private void validate(Long value) throws IllegalArgumentException{
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
			    + value + " is greater than max " + " of " + max);
		}
	    }
	}
  }

  // line 38 "../../../../LongConfigParam.ump"
   public void validateValue(String value) throws IllegalArgumentException{
    try {
	    validate(new Long(value));
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException(DEBUG_NAME + ": " + value + " not valid value for " + name);
	}
  }

  // line 46 "../../../../LongConfigParam.ump"
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
  
  // line 7 "../../../../LongConfigParam.ump"
  private static final String DEBUG_NAME = LongConfigParam.class.getName() ;
// line 9 "../../../../LongConfigParam.ump"
  private Long min ;
// line 11 "../../../../LongConfigParam.ump"
  private Long max ;

  
}