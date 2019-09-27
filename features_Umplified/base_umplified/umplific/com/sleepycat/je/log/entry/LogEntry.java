/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.log.entry;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
// line 3 "../../../../../LogEntry.ump"
public interface LogEntry extends Cloneable
{
  
  void readEntry(ByteBuffer entryBuffer, int entrySize, byte entryTypeVersion, boolean readFullItem)
	    throws DatabaseException ;

  public Object clone() throws CloneNotSupportedException ;

  public boolean isTransactional() ;

  public long getTransactionId() ;

  // ABSTRACT METHODS 

 public StringBuffer dumpEntry(StringBuffer sb, boolean verbose);
 public Object getMainItem();
}