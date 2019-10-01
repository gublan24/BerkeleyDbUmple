/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.Properties;
import java.util.Enumeration;

// line 3 "../../../../PropUtil.ump"
public class PropUtil
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PropUtil()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * @return true if the property is set to "true".
   */
  // line 14 "../../../../PropUtil.ump"
   public static  boolean getBoolean(Properties props, String propName){
    String value = props.getProperty(propName);
	if ((value != null) && (value.equalsIgnoreCase("true"))) {
	    return true;
	} else {
	    return false;
	}
  }


  /**
   * 
   * Validate properties in the property bag. If null was passed, return an empty property object, else return the original property object.
   * @throws DatabaseException if the property bag containsa property not specified in the set of allowed properties.
   */
  // line 28 "../../../../PropUtil.ump"
   public static  Properties validateProps(Properties props, Set allowedProps, String apiMethod) throws DatabaseException{
    if (props == null) {
	    return new Properties();
	} else {
	    if (props.size() > 0) {
		Enumeration e = props.propertyNames();
		while (e.hasMoreElements()) {
		    String propName = (String) e.nextElement();
		    validateProp(propName, allowedProps, apiMethod);
		}
	    }
	    return props;
	}
  }


  /**
   * 
   * @throws DatabaseException if the property is not valid.
   */
  // line 46 "../../../../PropUtil.ump"
   public static  void validateProp(String propName, Set allowedProps, String apiMethod) throws DatabaseException{
    if (!allowedProps.contains(propName)) {
	    throw new DatabaseException(propName + " is not a valid property for " + apiMethod);
	}
  }


  /**
   * 
   * Convert microseconds to milliseconds, ensuring that any microsecond value greater than zero converts to at least one millisecond to avoid a zero millisecond result since Object.wait(0) waits forever.
   */
  // line 55 "../../../../PropUtil.ump"
   public static  long microsToMillis(long micros){
    return (micros + 999) / 1000;
  }

}