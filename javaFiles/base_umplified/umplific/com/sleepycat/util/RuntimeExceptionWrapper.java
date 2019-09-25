/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.util;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../RuntimeExceptionWrapper.ump"
public class RuntimeExceptionWrapper implements ExceptionWrapper
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RuntimeExceptionWrapper()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 11 "../../../RuntimeExceptionWrapper.ump"
   public  RuntimeExceptionWrapper(Throwable e){
    super(e.getMessage());
	this.e = e;
  }


  /**
   * 
   * @deprecated replaced by {@link #getCause}.
   */
  // line 19 "../../../RuntimeExceptionWrapper.ump"
   public Throwable getDetail(){
    return e;
  }

  // line 23 "../../../RuntimeExceptionWrapper.ump"
   public Throwable getCause(){
    return e;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../RuntimeExceptionWrapper.ump"
  private Throwable e ;

  
}