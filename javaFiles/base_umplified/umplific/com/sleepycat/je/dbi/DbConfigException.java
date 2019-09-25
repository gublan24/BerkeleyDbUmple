/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.*;

// line 3 "../../../../DbConfigException.ump"
public class DbConfigException extends DatabaseException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbConfigException()
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

  // line 9 "../../../../DbConfigException.ump"
   public  DbConfigException(Throwable t){
    super(t);
  }

  // line 13 "../../../../DbConfigException.ump"
   public  DbConfigException(String message){
    super(message);
  }

  // line 17 "../../../../DbConfigException.ump"
   public  DbConfigException(String message, Throwable t){
    super(message, t);
  }

}