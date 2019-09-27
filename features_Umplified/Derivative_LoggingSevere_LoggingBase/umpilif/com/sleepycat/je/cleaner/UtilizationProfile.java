/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../UtilizationProfile.ump"
public class UtilizationProfile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UtilizationProfile()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../UtilizationProfile.ump"
   protected void hook177(long fileNum, int sequence, OperationStatus status) throws DatabaseException{
    if (status == OperationStatus.KEYEXIST) {
	    env.getLogger().log(Level.SEVERE, "Cleaner duplicate key sequence file=0x" + Long.toHexString(fileNum)
		    + " sequence=0x" + Long.toHexString(sequence));
	}
	original(fileNum, sequence, status);
  }

}