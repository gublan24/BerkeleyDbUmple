/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../DatabaseUtil.ump"
public class DatabaseUtil
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseUtil()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Throw an exception if the parameter is null.
   */
  // line 10 "../../../DatabaseUtil.ump"
   static  void checkForNullParam(Object param, String name){
    if (param == null) {
	    throw new NullPointerException(name + " cannot be null");
	}
  }


  /**
   * 
   * Throw an exception if the dbt is null or the data field is not set.
   */
  // line 19 "../../../DatabaseUtil.ump"
   static  void checkForNullDbt(DatabaseEntry dbt, String name, boolean checkData){
    if (dbt == null) {
	    throw new NullPointerException("DatabaseEntry " + name + " cannot be null");
	}
	if (checkData) {
	    if (dbt.getData() == null) {
		throw new NullPointerException("Data field for DatabaseEntry " + name + " cannot be null");
	    }
	}
  }


  /**
   * 
   * Throw an exception if the key dbt has the partial flag set.  This method should be called for all put() operations.
   */
  // line 33 "../../../DatabaseUtil.ump"
   static  void checkForPartialKey(DatabaseEntry dbt){
    if (dbt.getPartial()) {
	    throw new IllegalArgumentException("A partial key DatabaseEntry is not allowed");
	}
  }

}