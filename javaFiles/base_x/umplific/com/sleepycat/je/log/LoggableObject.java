/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
// line 3 "../../../../LoggableObject.ump"
public interface LoggableObject extends LogWritable
{
  
  public LogEntryType getLogType() ;

  public void postLogWork(long justLoggedLsn) throws DatabaseException ;

  public boolean marshallOutsideWriteLatch() ;

  public boolean countAsObsoleteWhenLogged() ;

}