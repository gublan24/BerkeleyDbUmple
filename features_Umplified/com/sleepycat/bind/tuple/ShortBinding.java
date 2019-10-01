/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../ShortBinding.ump"
public class ShortBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ShortBinding()
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

  // line 11 "../../../../ShortBinding.ump"
   public Object entryToObject(TupleInput input){
    return new Short(input.readShort());
  }

  // line 15 "../../../../ShortBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeShort(((Number) object).shortValue());
  }

  // line 19 "../../../../ShortBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>short</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../ShortBinding.ump"
   public static  short entryToShort(DatabaseEntry entry){
    return entryToInput(entry).readShort();
  }


  /**
   * 
   * Converts a simple <code>short</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../ShortBinding.ump"
   public static  void shortToEntry(short val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeShort(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../ShortBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[SHORT_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../ShortBinding.ump"
  private static final int SHORT_SIZE = 2 ;

  
}