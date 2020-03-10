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
import com.sleepycat.je.log.*;

// line 2 "../../../../Tracer.ump"
// line 3 "../../../../loggingBase_Tracer.ump"
// line 3 "../../../../LoggingDbLogHandler_Tracer.ump"
public class Tracer implements LoggableObject,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  // Default constructor has been disabled.  


  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a new debug record.
   */
  // line 31 "../../../../Tracer.ump"
   public  Tracer(String msg){
    this.time = getCurrentTimestamp();
	this.msg = msg;
  }


  /**
   * 
   * Logger method for recording a general message.
   */
  // line 39 "../../../../Tracer.ump"
   public static  void trace(Level logLevel, EnvironmentImpl envImpl, String msg){
    // line 20 "../../../../loggingBase_Tracer.ump"
    envImpl.getLogger().log(logLevel, msg);
    // END OF UMPLE BEFORE INJECTION
    
  }


  /**
   * 
   * Parse a logging level config parameter, and return a more explanatory error message if it doesn't parse.
   */
  // line 45 "../../../../Tracer.ump"
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
  // line 60 "../../../../Tracer.ump"
   private Timestamp getCurrentTimestamp(){
    Calendar cal = Calendar.getInstance();
	return new Timestamp(cal.getTime().getTime());
  }


  /**
   * 
   * @return the stacktrace for an exception
   */
  // line 68 "../../../../Tracer.ump"
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


  /**
   * 
   * @see LoggableObject#getLogType
   */
  // line 10 "../../../../LoggingDbLogHandler_Tracer.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_TRACE;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 17 "../../../../LoggingDbLogHandler_Tracer.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 24 "../../../../LoggingDbLogHandler_Tracer.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 31 "../../../../LoggingDbLogHandler_Tracer.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize()
   */
  // line 37 "../../../../LoggingDbLogHandler_Tracer.ump"
   public int getLogSize(){
    return (LogUtils.getTimestampLogSize() + LogUtils.getStringLogSize(msg));
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 44 "../../../../LoggingDbLogHandler_Tracer.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeTimestamp(logBuffer, time);
	LogUtils.writeString(logBuffer, msg);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 52 "../../../../LoggingDbLogHandler_Tracer.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion){
    time = LogUtils.readTimestamp(itemBuffer);
	msg = LogUtils.readString(itemBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 60 "../../../../LoggingDbLogHandler_Tracer.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<Dbg time=\"");
      sb.append(time);
      sb.append("\">");
      sb.append("<msg val=\"");
	    sb.append(msg);
	    sb.append("\"/>");
	    sb.append("</Dbg>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 73 "../../../../LoggingDbLogHandler_Tracer.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 80 "../../../../LoggingDbLogHandler_Tracer.ump"
   public long getTransactionId(){
    return 0;
  }

  // line 84 "../../../../LoggingDbLogHandler_Tracer.ump"
   public String toString(){
    return (time + "/" + msg);
  }


  /**
   * 
   * Just in case it's ever used as a hash key.
   */
  // line 91 "../../../../LoggingDbLogHandler_Tracer.ump"
   public int hashCode(){
    return toString().hashCode();
  }


  /**
   * 
   * Override Object.equals
   */
  // line 98 "../../../../LoggingDbLogHandler_Tracer.ump"
   public boolean equals(Object obj){
    if (this == obj) {
	    return true;
	}
	if (!(obj instanceof Tracer)) {
	    return false;
	}
	return (toString().equals(obj.toString()));
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 21 "../../../../Tracer.ump"
  public static final String INFO_FILES = "je.info" ;
// line 23 "../../../../Tracer.ump"
  private Timestamp time ;
// line 25 "../../../../Tracer.ump"
  private String msg ;

  
}