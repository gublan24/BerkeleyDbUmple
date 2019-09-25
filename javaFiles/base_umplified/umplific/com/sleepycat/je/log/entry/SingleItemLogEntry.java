/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log.entry;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;

// line 3 "../../../../../SingleItemLogEntry.ump"
public class SingleItemLogEntry implements LogEntry
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SingleItemLogEntry Attributes
  private LogReadable item;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SingleItemLogEntry(LogReadable aItem)
  {
    item = aItem;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setItem(LogReadable aItem)
  {
    boolean wasSet = false;
    item = aItem;
    wasSet = true;
    return wasSet;
  }

  public LogReadable getItem()
  {
    return item;
  }

  public void delete()
  {}

  // line 15 "../../../../../SingleItemLogEntry.ump"
   public  SingleItemLogEntry(Class logClass){
    this.logClass = logClass;
  }


  /**
   * 
   * @see LogEntry#readEntry
   */
  // line 23 "../../../../../SingleItemLogEntry.ump"
   public void readEntry(ByteBuffer entryBuffer, int entrySize, byte entryTypeVersion, boolean readFullItem) throws DatabaseException{
    try {
	    item = (LogReadable) logClass.newInstance();
	    item.readFromLog(entryBuffer, entryTypeVersion);
	} catch (IllegalAccessException e) {
	    throw new DatabaseException(e);
	} catch (InstantiationException e) {
	    throw new DatabaseException(e);
	}
  }


  /**
   * 
   * @see LogEntry#dumpEntry
   */
  // line 37 "../../../../../SingleItemLogEntry.ump"
   public StringBuffer dumpEntry(StringBuffer sb, boolean verbose){
    item.dumpLog(sb, verbose);
	return sb;
  }


  /**
   * 
   * @see LogEntry#getMainItem
   */
  // line 45 "../../../../../SingleItemLogEntry.ump"
   public Object getMainItem(){
    return item;
  }


  /**
   * 
   * @see LogEntry#clone
   */
  // line 52 "../../../../../SingleItemLogEntry.ump"
   public Object clone() throws CloneNotSupportedException{
    return super.clone();
  }


  /**
   * 
   * @see LogEntry#isTransactional
   */
  // line 59 "../../../../../SingleItemLogEntry.ump"
   public boolean isTransactional(){
    return item.logEntryIsTransactional();
  }


  /**
   * 
   * @see LogEntry#getTransactionId
   */
  // line 66 "../../../../../SingleItemLogEntry.ump"
   public long getTransactionId(){
    return item.getTransactionId();
  }


  /**
   * 
   * @return a new instance
   */
  // line 73 "../../../../../SingleItemLogEntry.ump"
   public LogEntry getNewInstance() throws DatabaseException{
    try {
	    return (LogEntry) logClass.newInstance();
	} catch (InstantiationException e) {
	    throw new DatabaseException(e);
	} catch (IllegalAccessException e) {
	    throw new DatabaseException(e);
	}
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "item" + "=" + (getItem() != null ? !getItem().equals(this)  ? getItem().toString().replaceAll("  ","    ") : "this" : "null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../../SingleItemLogEntry.ump"
  private Class logClass ;

  
}