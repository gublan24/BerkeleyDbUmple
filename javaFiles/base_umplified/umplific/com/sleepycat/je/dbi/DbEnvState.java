/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../DbEnvState.ump"
public class DbEnvState
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbEnvState()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 26 "../../../../DbEnvState.ump"
  public  DbEnvState(String name){
    this.name = name;
  }

  // line 30 "../../../../DbEnvState.ump"
   public String toString(){
    return name;
  }

  // line 34 "../../../../DbEnvState.ump"
  public void checkState(DbEnvState [] validPrevStates, DbEnvState newState) throws DatabaseException{
    if (DEBUG) {
	    System.out.println("newState = " + newState + " currentState = " + name);
	}
	boolean transitionOk = false;
	for (int i = 0; i < validPrevStates.length; i++) {
	    if (this == validPrevStates[i]) {
		transitionOk = true;
		break;
	    }
	}
	if (!transitionOk) {
	    throw new DatabaseException("Can't go from environment state " + toString() + " to " + newState.toString());
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../DbEnvState.ump"
  private static final boolean DEBUG = false ;
// line 9 "../../../../DbEnvState.ump"
  private String name ;
// line 11 "../../../../DbEnvState.ump"
  public static final DbEnvState INIT = new DbEnvState("initialized") ;
// line 13 "../../../../DbEnvState.ump"
  public static final DbEnvState OPEN = new DbEnvState("open") ;
// line 15 "../../../../DbEnvState.ump"
  public static final DbEnvState CLOSED = new DbEnvState("closed") ;
// line 17 "../../../../DbEnvState.ump"
  public static final DbEnvState INVALID = new DbEnvState("invalid") ;
// line 19 "../../../../DbEnvState.ump"
  public static final DbEnvState[] VALID_FOR_OPEN = {INIT, CLOSED};
// line 21 "../../../../DbEnvState.ump"
  public static final DbEnvState[] VALID_FOR_CLOSE = {INIT, OPEN, INVALID};
// line 23 "../../../../DbEnvState.ump"
  public static final DbEnvState[] VALID_FOR_REMOVE = {INIT, CLOSED};

  
}