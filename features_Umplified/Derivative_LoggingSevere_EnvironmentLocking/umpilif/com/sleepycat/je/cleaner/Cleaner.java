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
   protected void hook87(Set safeFiles) throws DatabaseException{
    Tracer.trace(Level.SEVERE, env,
		"Cleaner has " + safeFiles.size() + " files not deleted because of read-only processes.");
	original(safeFiles);
  }

}