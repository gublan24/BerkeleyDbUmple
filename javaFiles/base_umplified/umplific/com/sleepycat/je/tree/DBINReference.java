/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.DatabaseId;

// line 3 "../../../../DBINReference.ump"
public class DBINReference extends BINReference
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DBINReference()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 11 "../../../../DBINReference.ump"
  public  DBINReference(long nodeId, DatabaseId databaseId, byte [] idKey, byte [] dupKey){
    super(nodeId, databaseId, idKey);
	this.dupKey = dupKey;
  }

  // line 16 "../../../../DBINReference.ump"
   public byte[] getKey(){
    return dupKey;
  }

  // line 20 "../../../../DBINReference.ump"
   public byte[] getData(){
    return idKey;
  }

  // line 24 "../../../../DBINReference.ump"
   public String toString(){
    return super.toString() + " dupKey=" + Key.dumpString(dupKey, 0);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../DBINReference.ump"
  private byte[] dupKey ;

  
}