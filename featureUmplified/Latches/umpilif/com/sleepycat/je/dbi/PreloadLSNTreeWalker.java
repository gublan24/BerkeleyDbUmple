/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../PreloadLSNTreeWalker.ump"
public class PreloadLSNTreeWalker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadLSNTreeWalker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../PreloadLSNTreeWalker.ump"
   protected void releaseRootIN(IN root) throws DatabaseException{
    root.releaseLatch();
  }

  // line 10 "../../../../PreloadLSNTreeWalker.ump"
   protected void hook352(long lsn, INEntry inEntry, IN in) throws DatabaseException{
    in.latch();
	try {
	    original(lsn, inEntry, in);
	} finally {
	    in.releaseLatch();
	}
  }

}