/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.RunRecoveryException;
import com.sleepycat.je.*;

// line 3 "../../../../RecoveryException.ump"
public class RecoveryException extends RunRecoveryException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryException()
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

  // line 10 "../../../../RecoveryException.ump"
   public  RecoveryException(EnvironmentImpl env, String message, Throwable t){
    super(env, message, t);
  }

  // line 14 "../../../../RecoveryException.ump"
   public  RecoveryException(EnvironmentImpl env, String message){
    super(env, message);
  }

}