/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../CharacterBinding.ump"
public class CharacterBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CharacterBinding()
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

  // line 11 "../../../../CharacterBinding.ump"
   public Object entryToObject(TupleInput input){
    return new Character(input.readChar());
  }

  // line 15 "../../../../CharacterBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeChar(((Character) object).charValue());
  }

  // line 19 "../../../../CharacterBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput();
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>char</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 28 "../../../../CharacterBinding.ump"
   public static  char entryToChar(DatabaseEntry entry){
    return entryToInput(entry).readChar();
  }


  /**
   * 
   * Converts a simple <code>char</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 37 "../../../../CharacterBinding.ump"
   public static  void charToEntry(char val, DatabaseEntry entry){
    outputToEntry(sizedOutput().writeChar(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 44 "../../../../CharacterBinding.ump"
   private static  TupleOutput sizedOutput(){
    return new TupleOutput(new byte[CHAR_SIZE]);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../CharacterBinding.ump"
  private static final int CHAR_SIZE = 2 ;

  
}