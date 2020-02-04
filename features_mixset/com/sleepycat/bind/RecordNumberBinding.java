/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.compat.DbCompat;

// line 3 "../../../RecordNumberBinding.ump"
public class RecordNumberBinding implements EntryBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecordNumberBinding()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../RecordNumberBinding.ump"
   public Object entryToObject(DatabaseEntry entry){
    return new Long(entryToRecordNumber(entry));
  }

  // line 16 "../../../RecordNumberBinding.ump"
   public void objectToEntry(Object object, DatabaseEntry entry){
    recordNumberToEntry(((Number) object).longValue(), entry);
  }


  /**
   * 
   * Utility method for use by bindings to translate a entry buffer to an record number integer.
   * @param entrythe entry buffer.
   * @return the record number.
   */
  // line 25 "../../../RecordNumberBinding.ump"
   public static  long entryToRecordNumber(DatabaseEntry entry){
    return DbCompat.getRecordNumber(entry) & 0xFFFFFFFFL;
  }


  /**
   * 
   * Utility method for use by bindings to translate a record number integer to a entry buffer.
   * @param recordNumberthe record number.
   * @param entrythe entry buffer to hold the record number.
   */
  // line 34 "../../../RecordNumberBinding.ump"
   public static  void recordNumberToEntry(long recordNumber, DatabaseEntry entry){
    entry.setData(new byte[4], 0, 4);
	DbCompat.setRecordNumber(entry, (int) recordNumber);
  }

}