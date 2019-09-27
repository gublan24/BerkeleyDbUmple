/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../Cleaner.ump"
public class Cleaner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Returns whether the given BIN entry may be stripped by the evictor. True is always returned if the BIN is not dirty. False is returned if the BIN is dirty and the entry will be migrated soon.
   */
  // line 9 "../../../../Cleaner.ump"
   public boolean isEvictable(BIN bin, int index){
    if (bin.getDirty()) {
	    if (bin.getMigrate(index)) {
		return false;
	    }
	    boolean isResident = (bin.getTarget(index) != null);
	    Long fileNum = new Long(DbLsn.getFileNumber(bin.getLsn(index)));
	    if ((PROACTIVE_MIGRATION || isResident) && mustBeCleanedFiles.contains(fileNum)) {
		return false;
	    }
	    if ((clusterAll || (clusterResident && isResident)) && lowUtilizationFiles.contains(fileNum)) {
		return false;
	    }
	}
	return true;
  }

}