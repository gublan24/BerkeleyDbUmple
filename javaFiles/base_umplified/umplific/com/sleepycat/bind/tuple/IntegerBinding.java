/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../IntegerBinding.ump"
public class IntegerBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public IntegerBinding()
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

  // line 11 "../../../../IntegerBinding.ump"
   public Object entryToObject(TupleInput input){
    return new Integer(input.readInt());
  }

  // line 15 "../../../../IntegerBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeInt(((Number) object).intValue());
  }

  // line 19 "../../../../IntegerBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>int</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../IntegerBinding.ump"
   public static  int entryToInt(DatabaseEntry entry){
    return entryToInput(entry).readInt();
  }


  /**
   * 
   * Converts a simple <code>int</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../IntegerBinding.ump"
   public static  void intToEntry(int val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeInt(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../IntegerBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[INT_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../IntegerBinding.ump"
  private static final int INT_SIZE = 4 ;

  
}