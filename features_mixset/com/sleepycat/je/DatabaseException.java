/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.bind.serial.*;

// line 3 "../../../DatabaseException.ump"
public class DatabaseException extends Exception
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseException()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * //    public DatabaseException() {
   * super();
   * }
   */
  // line 13 "../../../DatabaseException.ump"
   public  DatabaseException(Throwable t){
    super(t);
  }

  // line 17 "../../../DatabaseException.ump"
   public  DatabaseException(String message){
    super(message);
  }

  // line 21 "../../../DatabaseException.ump"
   public  DatabaseException(String message, Throwable t){
    super(message, t);
  }

}