/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.util;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../ExceptionUnwrapper.ump"
public class ExceptionUnwrapper
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ExceptionUnwrapper()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Unwraps an Exception and returns the underlying Exception, or throws an Error if the underlying Throwable is an Error.
   * @param e is the Exception to unwrap.
   * @return the underlying Exception.
   * @throws Error if the underlying Throwable is an Error.
   * @throws IllegalArgumentException if the underlying Throwable is not anException or an Error.
   */
  // line 14 "../../../ExceptionUnwrapper.ump"
   public static  Exception unwrap(Exception e){
    Throwable t = unwrapAny(e);
	if (t instanceof Exception) {
	    return (Exception) t;
	} else if (t instanceof Error) {
	    throw (Error) t;
	} else {
	    throw new IllegalArgumentException("Not Exception or Error: " + t);
	}
  }


  /**
   * 
   * Unwraps an Exception and returns the underlying Throwable.
   * @param e is the Exception to unwrap.
   * @return the underlying Throwable.
   */
  // line 30 "../../../ExceptionUnwrapper.ump"
   public static  Throwable unwrapAny(Throwable e){
    while (true) {
	    if (e instanceof ExceptionWrapper) {
		Throwable e2 = ((ExceptionWrapper) e).getCause();
		if (e2 == null) {
		    return e;
		} else {
		    e = e2;
		}
	    } else {
		return e;
	    }
	}
  }

}