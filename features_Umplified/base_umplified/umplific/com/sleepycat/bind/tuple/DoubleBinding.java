/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../DoubleBinding.ump"
public class DoubleBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DoubleBinding()
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

  // line 11 "../../../../DoubleBinding.ump"
   public Object entryToObject(TupleInput input){
    return new Double(input.readDouble());
  }

  // line 15 "../../../../DoubleBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeDouble(((Number) object).doubleValue());
  }

  // line 19 "../../../../DoubleBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>double</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../DoubleBinding.ump"
   public static  double entryToDouble(DatabaseEntry entry){
    return entryToInput(entry).readDouble();
  }


  /**
   * 
   * Converts a simple <code>double</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../DoubleBinding.ump"
   public static  void doubleToEntry(double val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeDouble(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../DoubleBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[DOUBLE_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../DoubleBinding.ump"
  private static final int DOUBLE_SIZE = 8 ;

  
}