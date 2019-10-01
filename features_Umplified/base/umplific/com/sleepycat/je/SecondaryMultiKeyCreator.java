/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.util.Set;
// line 3 "../../../SecondaryMultiKeyCreator.ump"
public interface SecondaryMultiKeyCreator
{
  
  public void createSecondaryKeys(SecondaryDatabase secondary, DatabaseEntry key, DatabaseEntry data, Set results)
	    throws DatabaseException ;

}