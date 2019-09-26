/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.*;

// line 3 "../../../../LogException.ump"
public class LogException extends DatabaseException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogException()
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

  // line 9 "../../../../LogException.ump"
   public  LogException(String message){
    super(message);
  }

  // line 13 "../../../../LogException.ump"
   public  LogException(String message, Exception e){
    super(message, e);
  }

}