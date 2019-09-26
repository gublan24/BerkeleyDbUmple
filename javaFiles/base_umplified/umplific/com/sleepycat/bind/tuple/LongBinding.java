/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../LongBinding.ump"
public class LongBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LongBinding()
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

  // line 11 "../../../../LongBinding.ump"
   public Object entryToObject(TupleInput input){
    return new Long(input.readLong());
  }

  // line 15 "../../../../LongBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeLong(((Number) object).longValue());
  }

  // line 19 "../../../../LongBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>long</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../LongBinding.ump"
   public static  long entryToLong(DatabaseEntry entry){
    return entryToInput(entry).readLong();
  }


  /**
   * 
   * Converts a simple <code>long</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../LongBinding.ump"
   public static  void longToEntry(long val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeLong(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../LongBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[LONG_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../LongBinding.ump"
  private static final int LONG_SIZE = 8 ;

  
}