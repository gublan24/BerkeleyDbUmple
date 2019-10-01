/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
// line 3 "../../../ForeignMultiKeyNullifier.ump"
public interface ForeignMultiKeyNullifier
{
  
  public boolean nullifyForeignKey(SecondaryDatabase secondary, DatabaseEntry key, DatabaseEntry data,
	    DatabaseEntry secKey) throws DatabaseException ;

}