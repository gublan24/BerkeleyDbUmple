/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.bind.EntityBinding;
import com.sleepycat.bind.*;

// line 3 "../../../../TupleTupleBinding.ump"
public abstract class TupleTupleBinding extends TupleBase implements EntityBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleTupleBinding()
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


  /**
   * 
   * Creates a tuple-tuple entity binding.
   */
  // line 14 "../../../../TupleTupleBinding.ump"
   public  TupleTupleBinding(){
    
  }

  // line 17 "../../../../TupleTupleBinding.ump"
   public Object entryToObject(DatabaseEntry key, DatabaseEntry data){
    return entryToObject(TupleBinding.entryToInput(key), TupleBinding.entryToInput(data));
  }

  // line 21 "../../../../TupleTupleBinding.ump"
   public void objectToKey(Object object, DatabaseEntry key){
    TupleOutput output = getTupleOutput(object);
	objectToKey(object, output);
	outputToEntry(output, key);
  }

  // line 27 "../../../../TupleTupleBinding.ump"
   public void objectToData(Object object, DatabaseEntry data){
    TupleOutput output = getTupleOutput(object);
	objectToData(object, output);
	outputToEntry(output, data);
  }


  /**
   * 
   * Constructs an entity object from  {@link TupleInput} key and data entries.
   * @param keyInputis the {@link TupleInput} key entry object.
   * @param dataInputis the {@link TupleInput} data entry object.
   * @return the entity object constructed from the key and data.
   */
   public abstract Object entryToObject(TupleInput keyInput, TupleInput dataInput);


  /**
   * 
   * Extracts a key tuple from an entity object.
   * @param objectis the entity object.
   * @param outputis the {@link TupleOutput} to which the key should be written.
   */
   public abstract void objectToKey(Object object, TupleOutput output);


  /**
   * 
   * Extracts a key tuple from an entity object.
   * @param objectis the entity object.
   * @param outputis the {@link TupleOutput} to which the data should bewritten.
   */
   public abstract void objectToData(Object object, TupleOutput output);

}