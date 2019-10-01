/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../BooleanBinding.ump"
public class BooleanBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BooleanBinding()
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

  // line 11 "../../../../BooleanBinding.ump"
   public Object entryToObject(TupleInput input){
    return input.readBoolean() ? Boolean.TRUE : Boolean.FALSE;
  }

  // line 15 "../../../../BooleanBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeBoolean(((Boolean) object).booleanValue());
  }

  // line 19 "../../../../BooleanBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>boolean</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../BooleanBinding.ump"
   public static  boolean entryToBoolean(DatabaseEntry entry){
    return entryToInput(entry).readBoolean();
  }


  /**
   * 
   * Converts a simple <code>boolean</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../BooleanBinding.ump"
   public static  void booleanToEntry(boolean val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeBoolean(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../BooleanBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[BOOLEAN_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../BooleanBinding.ump"
  private static final int BOOLEAN_SIZE = 1 ;

  
}