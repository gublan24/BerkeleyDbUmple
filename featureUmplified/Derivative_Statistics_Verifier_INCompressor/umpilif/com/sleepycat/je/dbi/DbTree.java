/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../DbTree.ump"
public class DbTree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbTree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../DbTree.ump"
   protected boolean hook292(VerifyConfig config, PrintStream out, boolean ret) throws DatabaseException{
    synchronized (envImpl.getINCompressor()) {
	    ret = original(config, out, ret);
	}
	return ret;
  }

}