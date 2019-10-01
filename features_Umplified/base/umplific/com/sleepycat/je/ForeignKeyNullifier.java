/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
// line 3 "../../../ForeignKeyNullifier.ump"
public interface ForeignKeyNullifier
{
  
  public boolean nullifyForeignKey(SecondaryDatabase secondary, DatabaseEntry data) throws DatabaseException ;

}