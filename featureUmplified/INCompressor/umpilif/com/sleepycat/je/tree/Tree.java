/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../Tree.ump"
public class Tree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../Tree.ump"
   protected void hook754(BIN bin) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    if (bin.getNEntries() == 0) {
	    database.getDbEnvironment().addToCompressorQueue(bin, null, false);
	}
	original(bin);
  }

}