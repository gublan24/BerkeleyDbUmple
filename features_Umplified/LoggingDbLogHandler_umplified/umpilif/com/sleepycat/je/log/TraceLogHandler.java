/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.util.logging.LogRecord;
import java.util.logging.Handler;

// line 3 "../../../../TraceLogHandler.ump"
public class TraceLogHandler
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TraceLogHandler()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 19 "../../../../TraceLogHandler.ump"
   public  TraceLogHandler(EnvironmentImpl env){
    this.env = env;
  }

  // line 23 "../../../../TraceLogHandler.ump"
   public void close(){
    
  }

  // line 26 "../../../../TraceLogHandler.ump"
   public void flush(){
    
  }

  // line 29 "../../../../TraceLogHandler.ump"
   public void publish(LogRecord l){
    if (!env.isReadOnly() && !env.mayNotWrite()) {
	    try {
		Tracer newRec = new Tracer(l.getMessage());
		env.getLogManager().log(newRec);
	    } catch (DatabaseException e) {
		System.err.println("Problem seen while tracing into " + "the database log:");
		e.printStackTrace();
	    }
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 16 "../../../../TraceLogHandler.ump"
  private EnvironmentImpl env ;

  
}