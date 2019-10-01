/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.UtfOps;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../StringBinding.ump"
public class StringBinding extends TupleBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StringBinding()
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

  // line 10 "../../../../StringBinding.ump"
   public Object entryToObject(TupleInput input){
    return input.readString();
  }

  // line 14 "../../../../StringBinding.ump"
   public void objectToEntry(Object object, TupleOutput output){
    output.writeString((String) object);
  }

  // line 18 "../../../../StringBinding.ump"
   protected TupleOutput getTupleOutput(Object object){
    return sizedOutput((String) object);
  }


  /**
   * 
   * Converts an entry buffer into a simple <code>String</code> value.
   * @param entryis the source entry buffer.
   * @return the resulting value.
   */
  // line 27 "../../../../StringBinding.ump"
   public static  String entryToString(DatabaseEntry entry){
    return entryToInput(entry).readString();
  }


  /**
   * 
   * Converts a simple <code>String</code> value into an entry buffer.
   * @param valis the source value.
   * @param entryis the destination entry buffer.
   */
  // line 36 "../../../../StringBinding.ump"
   public static  void stringToEntry(String val, DatabaseEntry entry){
    outputToEntry(sizedOutput(val).writeString(val), entry);
  }


  /**
   * 
   * Returns a tuple output object of the exact size needed, to avoid wasting space when a single primitive is output.
   */
  // line 43 "../../../../StringBinding.ump"
   private static  TupleOutput sizedOutput(String val){
    int stringLength = (val == null) ? 1 : UtfOps.getByteLength(val.toCharArray());
	stringLength++;
	return new TupleOutput(new byte[stringLength]);
  }

}