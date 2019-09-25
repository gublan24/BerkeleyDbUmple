/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.util;
import de.ovgu.cide.jakutil.*;
import java.io.IOException;

// line 3 "../../../IOExceptionWrapper.ump"
public class IOExceptionWrapper implements ExceptionWrapper
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public IOExceptionWrapper()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../IOExceptionWrapper.ump"
   public  IOExceptionWrapper(Throwable e){
    super(e.getMessage());
	this.e = e;
  }


  /**
   * 
   * @deprecated replaced by {@link #getCause}.
   */
  // line 20 "../../../IOExceptionWrapper.ump"
   public Throwable getDetail(){
    return e;
  }

  // line 24 "../../../IOExceptionWrapper.ump"
   public Throwable getCause(){
    return e;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../IOExceptionWrapper.ump"
  private Throwable e ;

  
}