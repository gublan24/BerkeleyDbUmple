/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../FloatBinding.ump"
public class FloatBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FloatBinding()
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

  // line 11 "../../../../FloatBinding.ump"
   public Object entryToObject(TupleInput input){
    return new Float(input.readFloat());
  }

  // line 15 "../../../../FloatBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeFloat(((Number) object).floatValue());
  }

  // line 19 "../../../../FloatBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>float</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../FloatBinding.ump"
   public static  float entryToFloat(DatabaseEntry entry){
    return entryToInput(entry).readFloat();
  }


  /**
   * 
   * Converts a simple <code>float</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../FloatBinding.ump"
   public static  void floatToEntry(float val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeFloat(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../FloatBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[FLOAT_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../FloatBinding.ump"
  private static final int FLOAT_SIZE = 4 ;

  
}