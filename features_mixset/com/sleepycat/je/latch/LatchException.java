/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.*;

// line 3 "../../../../Latches_LatchException.ump"
public class LatchException extends DatabaseException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LatchException()
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

  // line 12 "../../../../Latches_LatchException.ump"
   public  LatchException(String message){
    super(message);
  }

}