/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../ByteBinding.ump"
public class ByteBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ByteBinding()
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

  // line 11 "../../../../ByteBinding.ump"
   public Object entryToObject(TupleInput input){
    return new Byte(input.readByte());
  }

  // line 15 "../../../../ByteBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeByte(((Number) object).byteValue());
  }

  // line 19 "../../../../ByteBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>byte</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../ByteBinding.ump"
   public static  byte entryToByte(DatabaseEntry entry){
    return entryToInput(entry).readByte();
  }


  /**
   * 
   * Converts a simple <code>byte</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../ByteBinding.ump"
   public static  void byteToEntry(byte val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeByte(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../ByteBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[BYTE_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../ByteBinding.ump"
  private static final int BYTE_SIZE = 1 ;

  
}