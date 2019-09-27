/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../SortedLSNTreeWalker.ump"
public class SortedLSNTreeWalker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SortedLSNTreeWalker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../SortedLSNTreeWalker.ump"
   protected void hook359() throws DatabaseException{
    if (setDbState) {
	    dbImpl.finishedINListHarvest();
	}
	original();
  }

}