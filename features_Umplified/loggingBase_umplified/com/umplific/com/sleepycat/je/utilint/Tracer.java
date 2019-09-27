/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;

// line 3 "../../../../Tracer.ump"
public class Tracer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tracer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create trace record that will be filled in from the log.
   */
  // line 9 "../../../../Tracer.ump"
  public  Tracer(){
    
  }

  // line 12 "../../../../Tracer.ump"
   public String getMessage(){
    return msg;
  }


  /**
   * 
   * Logger method for recording a general message.
   */
  // line 19 "../../../../Tracer.ump"
   public static  void trace(Level logLevel, EnvironmentImpl envImpl, String msg){
    envImpl.getLogger().log(logLevel, msg);
	original(logLevel, envImpl, msg);
  }

}