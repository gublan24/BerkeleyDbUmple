/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../DatabaseNotFoundException.ump"
public class DatabaseNotFoundException extends DatabaseException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseNotFoundException()
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
   * public DatabaseNotFoundException() {
   * super();
   * }
   */
  // line 13 "../../../DatabaseNotFoundException.ump"
   public  DatabaseNotFoundException(Throwable t){
    super(t);
  }

  // line 17 "../../../DatabaseNotFoundException.ump"
   public  DatabaseNotFoundException(String message){
    super(message);
  }

  // line 21 "../../../DatabaseNotFoundException.ump"
   public  DatabaseNotFoundException(String message, Throwable t){
    super(message, t);
  }

}