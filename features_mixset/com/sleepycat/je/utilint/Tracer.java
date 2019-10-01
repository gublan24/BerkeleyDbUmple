/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LoggableObject;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.config.ConfigParam;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Level;
import java.util.Calendar;
import java.sql.Timestamp;
import java.nio.ByteBuffer;
import java.io.StringWriter;
import java.io.PrintWriter;

// line 2 "../../../../Tracer.ump"
// line 3 "../../../../loggingBase_Tracer.ump"
public class Tracer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tracer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a new debug record.
   */
  // line 28 "../../../../Tracer.ump"
   public  Tracer(String msg){
    this.time = getCurrentTimestamp();
	this.msg = msg;
  }


  /**
   * 
   * Logger method for recording a general message.
   */
  // line 36 "../../../../Tracer.ump"
   public static  void trace(Level logLevel, EnvironmentImpl envImpl, String msg){
    // line 20 "../../../../loggingBase_Tracer.ump"
    envImpl.getLogger().log(logLevel, msg);
    // END OF UMPLE BEFORE INJECTION
    
  }


  /**
   * 
   * Parse a logging level config parameter, and return a more explanatory error message if it doesn't parse.
   */
  // line 42 "../../../../Tracer.ump"
   public static  Level parseLevel(EnvironmentImpl envImpl, ConfigParam configParam) throws DatabaseException{
    Level level = null;
	try {
	    String levelVal = envImpl.getConfigManager().get(configParam);
	    level = Level.parse(levelVal);
	} catch (IllegalArgumentException e) {
	    throw new DatabaseException("Problem parsing parameter " + configParam.getName() + ": " + e.getMessage(),
		    e);
	}
	return level;
  }


  /**
   * 
   * @return a timestamp for "now"
   */
  // line 57 "../../../../Tracer.ump"
   private Timestamp getCurrentTimestamp(){
    Calendar cal = Calendar.getInstance();
	return new Timestamp(cal.getTime().getTime());
  }


  /**
   * 
   * @return the stacktrace for an exception
   */
  // line 65 "../../../../Tracer.ump"
   public static  String getStackTrace(Throwable t){
    StringWriter s = new StringWriter();
	t.printStackTrace(new PrintWriter(s));
	String stackTrace = s.toString();
	stackTrace = stackTrace.replaceAll("<", "&lt;");
	stackTrace = stackTrace.replaceAll(">", "&gt;");
	return stackTrace;
  }


  /**
   * 
   * Create trace record that will be filled in from the log.
   */
  // line 9 "../../../../loggingBase_Tracer.ump"
  public  Tracer(){
    
  }

  // line 12 "../../../../loggingBase_Tracer.ump"
   public String getMessage(){
    return msg;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 18 "../../../../Tracer.ump"
  public static final String INFO_FILES = "je.info" ;
// line 20 "../../../../Tracer.ump"
  private Timestamp time ;
// line 22 "../../../../Tracer.ump"
  private String msg ;

  
}