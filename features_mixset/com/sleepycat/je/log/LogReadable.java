/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import java.nio.ByteBuffer;
// line 3 "../../../../LogReadable.ump"
public interface LogReadable
{
  
  public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException ;

  public void dumpLog(StringBuffer sb, boolean verbose) ;

  public boolean logEntryIsTransactional() ;

  public long getTransactionId() ;

}