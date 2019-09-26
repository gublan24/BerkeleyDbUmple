/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.log.entry;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
// line 3 "../../../../../INContainingEntry.ump"
public interface INContainingEntry
{
  
  public IN getIN(EnvironmentImpl env) throws DatabaseException ;

  public DatabaseId getDbId() ;

  public long getLsnOfIN(long lastReadLsn) ;

}