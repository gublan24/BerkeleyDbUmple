/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.DatabaseId;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

// line 3 "../../../../BINReference.ump"
public class BINReference
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BINReference()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 19 "../../../../BINReference.ump"
  public  BINReference(long nodeId, DatabaseId databaseId, byte [] idKey){
    this.nodeId = nodeId;
	this.databaseId = databaseId;
	this.idKey = idKey;
  }

  // line 25 "../../../../BINReference.ump"
   public long getNodeId(){
    return nodeId;
  }

  // line 29 "../../../../BINReference.ump"
   public DatabaseId getDatabaseId(){
    return databaseId;
  }

  // line 33 "../../../../BINReference.ump"
   public byte[] getKey(){
    return idKey;
  }

  // line 37 "../../../../BINReference.ump"
   public byte[] getData(){
    return null;
  }

  // line 41 "../../../../BINReference.ump"
   public void addDeletedKey(Key key){
    if (deletedKeys == null) {
	    deletedKeys = new HashSet();
	}
	deletedKeys.add(key);
  }

  // line 48 "../../../../BINReference.ump"
   public void addDeletedKeys(BINReference other){
    if (deletedKeys == null) {
	    deletedKeys = new HashSet();
	}
	if (other.deletedKeys != null) {
	    deletedKeys.addAll(other.deletedKeys);
	}
  }

  // line 57 "../../../../BINReference.ump"
   public void removeDeletedKey(Key key){
    if (deletedKeys != null) {
	    deletedKeys.remove(key);
	    if (deletedKeys.size() == 0) {
		deletedKeys = null;
	    }
	}
  }

  // line 66 "../../../../BINReference.ump"
   public boolean hasDeletedKey(Key key){
    return (deletedKeys != null) && deletedKeys.contains(key);
  }

  // line 70 "../../../../BINReference.ump"
   public boolean deletedKeysExist(){
    return ((deletedKeys != null) && (deletedKeys.size() > 0));
  }

  // line 74 "../../../../BINReference.ump"
   public Iterator getDeletedKeyIterator(){
    if (deletedKeys != null) {
	    return deletedKeys.iterator();
	} else {
	    return null;
	}
  }


  /**
   * 
   * Compare two BINReferences.
   */
  // line 85 "../../../../BINReference.ump"
   public boolean equals(Object obj){
    if (this == obj) {
	    return true;
	}
	if (!(obj instanceof BINReference)) {
	    return false;
	}
	return ((BINReference) obj).nodeId == nodeId;
  }

  // line 95 "../../../../BINReference.ump"
   public int hashCode(){
    return (int) nodeId;
  }

  // line 99 "../../../../BINReference.ump"
   public String toString(){
    return "idKey=" + Key.getNoFormatString(idKey) + " nodeId = " + nodeId + " db=" + databaseId + " deletedKeys="
		+ deletedKeys;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../BINReference.ump"
  protected byte[] idKey ;
// line 12 "../../../../BINReference.ump"
  private long nodeId ;
// line 14 "../../../../BINReference.ump"
  private DatabaseId databaseId ;
// line 16 "../../../../BINReference.ump"
  private Set deletedKeys ;

  
}