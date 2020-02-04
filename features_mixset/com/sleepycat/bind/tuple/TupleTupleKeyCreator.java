/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.SecondaryKeyCreator;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.ForeignKeyNullifier;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.*;

// line 3 "../../../../TupleTupleKeyCreator.ump"
public abstract class TupleTupleKeyCreator extends TupleBase implements SecondaryKeyCreator,ForeignKeyNullifier
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleTupleKeyCreator()
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
   * Creates a tuple-tuple key creator.
   * public TupleTupleKeyCreator() {
   * }
   */
  // line 21 "../../../../TupleTupleKeyCreator.ump"
   public boolean createSecondaryKey(SecondaryDatabase db, DatabaseEntry primaryKeyEntry, DatabaseEntry dataEntry, DatabaseEntry indexKeyEntry) throws DatabaseException{
    TupleOutput output = getTupleOutput(null);
	TupleInput primaryKeyInput = entryToInput(primaryKeyEntry);
	TupleInput dataInput = entryToInput(dataEntry);
	if (createSecondaryKey(primaryKeyInput, dataInput, output)) {
	    outputToEntry(output, indexKeyEntry);
	    return true;
	} else {
	    return false;
	}
  }

  // line 33 "../../../../TupleTupleKeyCreator.ump"
   public boolean nullifyForeignKey(SecondaryDatabase db, DatabaseEntry dataEntry) throws DatabaseException{
    TupleOutput output = getTupleOutput(null);
	if (nullifyForeignKey(entryToInput(dataEntry), output)) {
	    outputToEntry(output, dataEntry);
	    return true;
	} else {
	    return false;
	}
  }


  /**
   * 
   * Creates the index key from primary key tuple and data tuple.
   * @param primaryKeyInputis the {@link TupleInput} for the primary key entry.
   * @param dataInputis the {@link TupleInput} for the data entry.
   * @param indexKeyOutputis the destination index key tuple.
   * @return true if a key was created, or false to indicate that the key isnot present.
   */
   public abstract boolean createSecondaryKey(TupleInput primaryKeyInput, TupleInput dataInput, TupleOutput indexKeyOutput);


  /**
   * 
   * Clears the index key in the tuple data entry. The dataInput should be read and then written to the dataOutput, clearing the index key in the process. <p> The secondary key should be output or removed by this method such that {@link #createSecondaryKey} will return false. Other fields in the dataobject should remain unchanged. </p>
   * @param dataInputis the {@link TupleInput} for the data entry.
   * @param dataOutputis the destination {@link TupleOutput}.
   * @return true if the key was cleared, or false to indicate that the key isnot present and no change is necessary.
   */
  // line 59 "../../../../TupleTupleKeyCreator.ump"
   public boolean nullifyForeignKey(TupleInput dataInput, TupleOutput dataOutput){
    return false;
  }

}