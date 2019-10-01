/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.SecondaryKeyCreator;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.ForeignKeyNullifier;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.*;

// line 3 "../../../../SerialSerialKeyCreator.ump"
public abstract class SerialSerialKeyCreator implements SecondaryKeyCreator,ForeignKeyNullifier
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SerialSerialKeyCreator()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates a serial-serial key creator.
   * @param classCatalogis the catalog to hold shared class information and for adatabase should be a  {@link StoredClassCatalog}.
   * @param primaryKeyClassis the primary key base class.
   * @param dataClassis the data base class.
   * @param indexKeyClassis the index key base class.
   */
  // line 27 "../../../../SerialSerialKeyCreator.ump"
   public  SerialSerialKeyCreator(ClassCatalog classCatalog, Class primaryKeyClass, Class dataClass, Class indexKeyClass){
    this(new SerialBinding(classCatalog, primaryKeyClass), new SerialBinding(classCatalog, dataClass),
		new SerialBinding(classCatalog, indexKeyClass));
  }


  /**
   * 
   * Creates a serial-serial entity binding.
   * @param primaryKeyBindingis the primary key binding.
   * @param dataBindingis the data binding.
   * @param indexKeyBindingis the index key binding.
   */
  // line 39 "../../../../SerialSerialKeyCreator.ump"
   public  SerialSerialKeyCreator(SerialBinding primaryKeyBinding, SerialBinding dataBinding, SerialBinding indexKeyBinding){
    this.primaryKeyBinding = primaryKeyBinding;
	this.dataBinding = dataBinding;
	this.indexKeyBinding = indexKeyBinding;
  }

  // line 46 "../../../../SerialSerialKeyCreator.ump"
   public boolean createSecondaryKey(SecondaryDatabase db, DatabaseEntry primaryKeyEntry, DatabaseEntry dataEntry, DatabaseEntry indexKeyEntry) throws DatabaseException{
    Object primaryKeyInput = primaryKeyBinding.entryToObject(primaryKeyEntry);
	Object dataInput = dataBinding.entryToObject(dataEntry);
	Object indexKey = createSecondaryKey(primaryKeyInput, dataInput);
	if (indexKey != null) {
	    indexKeyBinding.objectToEntry(indexKey, indexKeyEntry);
	    return true;
	} else {
	    return false;
	}
  }

  // line 58 "../../../../SerialSerialKeyCreator.ump"
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
   * Creates the index key object from primary key and entry objects.
   * @param primaryKeyis the deserialized source primary key entry, or null if noprimary key entry is used to construct the index key.
   * @param datais the deserialized source data entry, or null if no dataentry is used to construct the index key.
   * @return the destination index key object, or null to indicate that thekey is not present.
   */
   public abstract Object createSecondaryKey(Object primaryKey, Object data);


  /**
   * 
   * Clears the index key in a data object. <p> On entry the data parameter contains the index key to be cleared. It should be changed by this method such that  {@link #createSecondaryKey}will return false. Other fields in the data object should remain unchanged. </p>
   * @param datais the source and destination data object.
   * @return the destination data object, or null to indicate that the key isnot present and no change is necessary. The data returned may be the same object passed as the data parameter or a newly created object.
   */
  // line 82 "../../../../SerialSerialKeyCreator.ump"
   public Object nullifyForeignKey(Object data){
    return null;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../SerialSerialKeyCreator.ump"
  protected SerialBinding primaryKeyBinding ;
// line 14 "../../../../SerialSerialKeyCreator.ump"
  protected SerialBinding dataBinding ;
// line 16 "../../../../SerialSerialKeyCreator.ump"
  protected SerialBinding indexKeyBinding ;

  
}