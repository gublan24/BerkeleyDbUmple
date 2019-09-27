/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import java.io.ObjectStreamClass;
// line 3 "../../../../ClassCatalog.ump"
public interface ClassCatalog
{
  
  public void close() throws DatabaseException ;

  public byte[] getClassID(ObjectStreamClass classDesc) throws DatabaseException, ClassNotFoundException ;

  public ObjectStreamClass getClassFormat(byte[] classID) throws DatabaseException, ClassNotFoundException ;

}