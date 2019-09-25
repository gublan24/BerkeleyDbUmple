/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.bind;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
// line 3 "../../../EntityBinding.ump"
public interface EntityBinding
{
  
  // ABSTRACT METHODS 

 public Object entryToObject(DatabaseEntry key, DatabaseEntry data);
 public void objectToKey(Object object, DatabaseEntry key);
 public void objectToData(Object object, DatabaseEntry data);
}