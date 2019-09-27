/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../ChildReference.ump"
public class ChildReference
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ChildReference()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../ChildReference.ump"
   protected void hook613(IN in) throws DatabaseException,LogFileNotFoundException,Exception{
    if (in != null) {
	    in.updateMemorySize(null, target);
	}
	original(in);
  }

}