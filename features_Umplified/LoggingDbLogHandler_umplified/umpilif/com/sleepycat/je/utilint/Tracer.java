/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;

// line 3 "../../../../Tracer.ump"
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
   * @see LoggableObject#getLogType
   */
  // line 10 "../../../../Tracer.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_TRACE;
  }


  /**
   * 
   * @see LoggableObject#marshallOutsideWriteLatchCan be marshalled outside the log write latch.
   */
  // line 17 "../../../../Tracer.ump"
   public boolean marshallOutsideWriteLatch(){
    return true;
  }


  /**
   * 
   * @see LoggableObject#countAsObsoleteWhenLogged
   */
  // line 24 "../../../../Tracer.ump"
   public boolean countAsObsoleteWhenLogged(){
    return false;
  }


  /**
   * 
   * @see LoggableObject#postLogWork
   */
  // line 31 "../../../../Tracer.ump"
   public void postLogWork(long justLoggedLsn){
    
  }


  /**
   * 
   * @see LoggableObject#getLogSize()
   */
  // line 37 "../../../../Tracer.ump"
   public int getLogSize(){
    return (LogUtils.getTimestampLogSize() + LogUtils.getStringLogSize(msg));
  }


  /**
   * 
   * @see LoggableObject#writeToLog
   */
  // line 44 "../../../../Tracer.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeTimestamp(logBuffer, time);
	LogUtils.writeString(logBuffer, msg);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 52 "../../../../Tracer.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion){
    time = LogUtils.readTimestamp(itemBuffer);
	msg = LogUtils.readString(itemBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 60 "../../../../Tracer.ump"
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
  // line 73 "../../../../Tracer.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 80 "../../../../Tracer.ump"
   public long getTransactionId(){
    return 0;
  }

  // line 84 "../../../../Tracer.ump"
   public String toString(){
    return (time + "/" + msg);
  }


  /**
   * 
   * Just in case it's ever used as a hash key.
   */
  // line 91 "../../../../Tracer.ump"
   public int hashCode(){
    return toString().hashCode();
  }


  /**
   * 
   * Override Object.equals
   */
  // line 98 "../../../../Tracer.ump"
   public boolean equals(Object obj){
    if (this == obj) {
	    return true;
	}
	if (!(obj instanceof Tracer)) {
	    return false;
	}
	return (toString().equals(obj.toString()));
  }

}