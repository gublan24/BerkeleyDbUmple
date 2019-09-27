/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../PreloadProcessor.ump"
public class PreloadProcessor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadProcessor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../PreloadProcessor.ump"
   protected void hook355(){
    if (envImpl.getMemoryBudget().getCacheMemoryUsage() > maxBytes) {
	    throw DatabaseImpl.memoryExceededPreloadException;
	}
	original();
  }

}