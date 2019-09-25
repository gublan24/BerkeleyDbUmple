/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
import java.util.Comparator;

// line 3 "../../../../Key.ump"
public class Key
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Key()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Construct a new key from a byte array.
   */
  // line 21 "../../../../Key.ump"
   public  Key(byte [] key){
    if (key == null) {
	    this.key = null;
	} else {
	    this.key = new byte[key.length];
	    System.arraycopy(key, 0, this.key, 0, key.length);
	}
  }

  // line 30 "../../../../Key.ump"
   public static  byte[] makeKey(DatabaseEntry dbt){
    byte[] entryKey = dbt.getData();
	if (entryKey == null) {
	    return EMPTY_KEY;
	} else {
	    byte[] newKey = new byte[dbt.getSize()];
	    System.arraycopy(entryKey, dbt.getOffset(), newKey, 0, dbt.getSize());
	    return newKey;
	}
  }


  /**
   * 
   * Get the byte array for the key.
   */
  // line 44 "../../../../Key.ump"
   public byte[] getKey(){
    return key;
  }


  /**
   * 
   * Compare two keys.  Standard compareTo function and returns. Note that any configured user comparison function is not used, and therefore this method should not be used for comparison of keys during Btree operations.
   */
  // line 51 "../../../../Key.ump"
   public int compareTo(Object o){
    if (o == null) {
	    throw new NullPointerException();
	}
	Key argKey = (Key) o;
	return compareUnsignedBytes(this.key, argKey.key);
  }


  /**
   * 
   * Support Set of Key in BINReference.
   */
  // line 62 "../../../../Key.ump"
   public boolean equals(Object o){
    return (o instanceof Key) && (compareTo(o) == 0);
  }


  /**
   * 
   * Support HashSet of Key in BINReference.
   */
  // line 69 "../../../../Key.ump"
   public int hashCode(){
    int code = 0;
	for (int i = 0; i < key.length; i += 1) {
	    code += key[i];
	}
	return code;
  }


  /**
   * 
   * Compare keys with an optional comparator.
   */
  // line 80 "../../../../Key.ump"
   public static  int compareKeys(byte [] key1, byte [] key2, Comparator comparator){
    if (comparator != null) {
	    return comparator.compare(key1, key2);
	} else {
	    return compareUnsignedBytes(key1, key2);
	}
  }


  /**
   * 
   * Compare using a default unsigned byte comparison.
   */
  // line 91 "../../../../Key.ump"
   private static  int compareUnsignedBytes(byte [] key1, byte [] key2){
    int a1Len = key1.length;
	int a2Len = key2.length;
	int limit = Math.min(a1Len, a2Len);
	for (int i = 0; i < limit; i++) {
	    byte b1 = key1[i];
	    byte b2 = key2[i];
	    if (b1 == b2) {
		continue;
	    } else {
		return (b1 & 0xff) - (b2 & 0xff);
	    }
	}
	return (a1Len - a2Len);
  }

  // line 107 "../../../../Key.ump"
   public static  String dumpString(byte [] key, int nspaces){
    StringBuffer sb = new StringBuffer();
	sb.append(TreeUtils.indent(nspaces));
	sb.append("<key v=\"");
	if (DUMP_BINARY) {
	    if (key == null) {
		sb.append("<null>");
	    } else {
		sb.append(TreeUtils.dumpByteArray(key));
	    }
	} else if (DUMP_INT_BINDING) {
	    if (key == null) {
		sb.append("<null>");
	    } else {
		DatabaseEntry e = new DatabaseEntry(key);
	    }
	} else {
	    sb.append(key == null ? "" : new String(key));
	}
	sb.append("\"/>");
	return sb.toString();
  }


  /**
   * 
   * Print the string w/out XML format.
   */
  // line 133 "../../../../Key.ump"
   public static  String getNoFormatString(byte [] key){
    return "key=" + dumpString(key, 0);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../Key.ump"
  public static boolean DUMP_BINARY = true ;
// line 11 "../../../../Key.ump"
  public static boolean DUMP_INT_BINDING = false ;
// line 13 "../../../../Key.ump"
  public static final byte[] EMPTY_KEY = new byte[0] ;
// line 15 "../../../../Key.ump"
  private byte[] key ;

  
}