/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../ByteArrayBinding.ump"
public class ByteArrayBinding implements EntryBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ByteArrayBinding()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates a byte array binding.
   */
  // line 14 "../../../ByteArrayBinding.ump"
   public  ByteArrayBinding(){
    
  }

  // line 17 "../../../ByteArrayBinding.ump"
   public Object entryToObject(DatabaseEntry entry){
    int len = entry.getSize();
	if (len == 0) {
	    return ZERO_LENGTH_BYTE_ARRAY;
	} else {
	    byte[] bytes = new byte[len];
	    System.arraycopy(entry.getData(), entry.getOffset(), bytes, 0, bytes.length);
	    return bytes;
	}
  }

  // line 28 "../../../ByteArrayBinding.ump"
   public void objectToEntry(Object object, DatabaseEntry entry){
    byte[] bytes = (byte[]) object;
	entry.setData(bytes, 0, bytes.length);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../ByteArrayBinding.ump"
  private static byte[] ZERO_LENGTH_BYTE_ARRAY = new byte[0] ;

  
}