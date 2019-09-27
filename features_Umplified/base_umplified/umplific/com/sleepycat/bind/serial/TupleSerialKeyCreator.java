/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.SecondaryKeyCreator;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.ForeignKeyNullifier;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleBase;
import com.sleepycat.je.*;
import com.sleepycat.bind.tuple.*;

// line 3 "../../../../TupleSerialKeyCreator.ump"
public abstract class TupleSerialKeyCreator extends TupleBase implements SecondaryKeyCreator,ForeignKeyNullifier
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleSerialKeyCreator()
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
   * Creates a tuple-serial key creator.
   * @param classCatalogis the catalog to hold shared class information and for adatabase should be a  {@link StoredClassCatalog}.
   * @param dataClassis the data base class.
   */
  // line 24 "../../../../TupleSerialKeyCreator.ump"
   public  TupleSerialKeyCreator(ClassCatalog classCatalog, Class dataClass){
    this(new SerialBinding(classCatalog, dataClass));
  }


  /**
   * 
   * Creates a tuple-serial key creator.
   * @param dataBindingis the data binding.
   */
  // line 32 "../../../../TupleSerialKeyCreator.ump"
   public  TupleSerialKeyCreator(SerialBinding dataBinding){
    this.dataBinding = dataBinding;
  }

  // line 37 "../../../../TupleSerialKeyCreator.ump"
   public boolean createSecondaryKey(SecondaryDatabase db, DatabaseEntry primaryKeyEntry, DatabaseEntry dataEntry, DatabaseEntry indexKeyEntry) throws DatabaseException{
    TupleOutput output = getTupleOutput(null);
	TupleInput primaryKeyInput = entryToInput(primaryKeyEntry);
	Object dataInput = dataBinding.entryToObject(dataEntry);
	if (createSecondaryKey(primaryKeyInput, dataInput, output)) {
	    outputToEntry(output, indexKeyEntry);
	    return true;
	} else {
	    return false;
	}
  }

  // line 49 "../../../../TupleSerialKeyCreator.ump"
   public boolean nullifyForeignKey(SecondaryDatabase db, DatabaseEntry dataEntry) throws DatabaseException{
    Object data = dataBinding.entryToObject(dataEntry);
	data = nullifyForeignKey(data);
	if (data != null) {
	    dataBinding.objectToEntry(data, dataEntry);
	    return true;
	} else {
	    return false;
	}
  }


  /**
   * 
   * Creates the index key entry from primary key tuple entry and deserialized data entry.
   * @param primaryKeyInputis the {@link TupleInput} for the primary key entry, or nullif no primary key entry is used to construct the index key.
   * @param dataInputis the deserialized data entry, or null if no data entry isused to construct the index key.
   * @param indexKeyOutputis the destination index key tuple. For index keys which areoptionally present, no tuple entry should be output to indicate that the key is not present or null.
   * @return true if a key was created, or false to indicate that the key isnot present.
   */
   public abstract boolean createSecondaryKey(TupleInput primaryKeyInput, Object dataInput, TupleOutput indexKeyOutput);


  /**
   * 
   * Clears the index key in the deserialized data entry. <p> On entry the data parameter contains the index key to be cleared. It should be changed by this method such that  {@link #createSecondaryKey}will return false. Other fields in the data object should remain unchanged. </p>
   * @param datais the source and destination deserialized data entry.
   * @return the destination data object, or null to indicate that the key isnot present and no change is necessary. The data returned may be the same object passed as the data parameter or a newly created object.
   */
  // line 75 "../../../../TupleSerialKeyCreator.ump"
   public Object nullifyForeignKey(Object data){
    return null;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 16 "../../../../TupleSerialKeyCreator.ump"
  protected SerialBinding dataBinding ;

  
}