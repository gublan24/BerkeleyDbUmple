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

  // line 6 "../../../../Cleaner.ump"
   protected void hook95(BIN bin, DIN parentDIN) throws DatabaseException{
    if (parentDIN != null) {
	    parentDIN.releaseLatchIfOwner();
	}
	if (bin != null) {
	    bin.releaseLatchIfOwner();
	}
	original(bin, parentDIN);
  }

}