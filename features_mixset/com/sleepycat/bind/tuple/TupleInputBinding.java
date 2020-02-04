/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.*;

// line 3 "../../../../TupleInputBinding.ump"
public class TupleInputBinding implements EntryBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleInputBinding()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates a tuple input binding.
   * public TupleInputBinding() {
   * }
   */
  // line 16 "../../../../TupleInputBinding.ump"
   public Object entryToObject(DatabaseEntry entry){
    return TupleBinding.entryToInput(entry);
  }

  // line 20 "../../../../TupleInputBinding.ump"
   public void objectToEntry(Object object, DatabaseEntry entry){
    TupleBinding.inputToEntry((TupleInput) object, entry);
  }

}