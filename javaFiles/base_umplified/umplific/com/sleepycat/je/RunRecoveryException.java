/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;

// line 3 "../../../RunRecoveryException.ump"
public class RunRecoveryException extends DatabaseException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RunRecoveryException()
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

  // line 11 "../../../RunRecoveryException.ump"
   public  RunRecoveryException(EnvironmentImpl env){
    super();
	invalidate(env);
  }

  // line 16 "../../../RunRecoveryException.ump"
   public  RunRecoveryException(EnvironmentImpl env, Throwable t){
    super(t);
	invalidate(env);
  }

  // line 21 "../../../RunRecoveryException.ump"
   public  RunRecoveryException(EnvironmentImpl env, String message){
    super(message);
	invalidate(env);
  }

  // line 26 "../../../RunRecoveryException.ump"
   public  RunRecoveryException(EnvironmentImpl env, String message, Throwable t){
    super(message, t);
	invalidate(env);
  }

  // line 31 "../../../RunRecoveryException.ump"
   private void invalidate(EnvironmentImpl env){
    if (env != null) {
	    env.invalidate(this);
	}
  }

  // line 37 "../../../RunRecoveryException.ump"
   public void setAlreadyThrown(){
    alreadyThrown = true;
  }

  // line 41 "../../../RunRecoveryException.ump"
   public String toString(){
    if (alreadyThrown) {
	    return "Environment invalid because of previous exception: " + super.toString();
	} else {
	    return super.toString();
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../RunRecoveryException.ump"
  private boolean alreadyThrown = false ;

  
}