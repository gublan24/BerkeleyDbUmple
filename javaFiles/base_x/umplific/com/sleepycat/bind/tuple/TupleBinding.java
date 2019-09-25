/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.bind.EntryBinding;
import java.util.Map;
import java.util.HashMap;
import com.sleepycat.bind.*;

// line 3 "../../../../TupleBinding.ump"
public abstract class TupleBinding extends TupleBase implements EntryBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleBinding()
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

  // line 15 "../../../../TupleBinding.ump"
   private static  void addPrimitive(Class cls1, Class cls2, TupleBinding binding){
    primitives.put(cls1, binding);
	primitives.put(cls2, binding);
  }


  /**
   * 
   * Creates a tuple binding.
   */
  // line 23 "../../../../TupleBinding.ump"
   public  TupleBinding(){
    
  }

  // line 26 "../../../../TupleBinding.ump"
   public Object entryToObject(DatabaseEntry entry){
    return entryToObject(entryToInput(entry));
  }

  // line 30 "../../../../TupleBinding.ump"
   public void objectToEntry(Object object, DatabaseEntry entry){
    TupleOutput output = getTupleOutput(object);
	objectToEntry(object, output);
	outputToEntry(output, entry);
  }


  /**
   * 
   * Constructs a key or data object from a  {@link TupleInput} entry.
   * @param inputis the tuple key or data entry.
   * @return the key or data object constructed from the entry.
   */
   public abstract Object entryToObject(TupleInput input);


  /**
   * 
   * Converts a key or data object to a tuple entry.
   * @param objectis the key or data object.
   * @param outputis the tuple entry to which the key or data should be written.
   */
   public abstract void objectToEntry(Object object, TupleOutput output);


  /**
   * 
   * Creates a tuple binding for a primitive Java class. The following Java classes are supported. <ul> <li><code>String</code></li> <li><code>Character</code></li> <li><code>Boolean</code></li> <li><code>Byte</code></li> <li><code>Short</code></li> <li><code>Integer</code></li> <li><code>Long</code></li> <li><code>Float</code></li> <li><code>Double</code></li> </ul>
   * @param clsis the primitive Java class.
   * @return a new binding for the primitive class or null if the clsparameter is not one of the supported classes.
   */
  // line 55 "../../../../TupleBinding.ump"
   public static  TupleBinding getPrimitiveBinding(Class cls){
    return (TupleBinding) primitives.get(cls);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../TupleBinding.ump"
  private static final Map primitives = new HashMap() ;

  
}