/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.incomp;

// line 3 "../../../../INCompressor.ump"
public class INCompressor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INCompressor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../INCompressor.ump"
   protected boolean hook415(BINSearch binSearch, boolean close) throws DatabaseException{
    close |= binSearch.db.isDeleted();
	return original(binSearch, close);
  }

}