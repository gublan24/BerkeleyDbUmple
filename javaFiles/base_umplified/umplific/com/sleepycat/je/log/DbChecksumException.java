/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.*;

// line 3 "../../../../DbChecksumException.ump"
public class DbChecksumException extends RunRecoveryException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbChecksumException()
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

  // line 10 "../../../../DbChecksumException.ump"
   public  DbChecksumException(EnvironmentImpl env, String message){
    super(env, message);
  }

  // line 14 "../../../../DbChecksumException.ump"
   public  DbChecksumException(EnvironmentImpl env, String message, Throwable t){
    super(env, message, t);
  }

}