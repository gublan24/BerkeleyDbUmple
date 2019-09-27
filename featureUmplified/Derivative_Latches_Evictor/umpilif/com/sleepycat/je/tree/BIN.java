/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../BIN.ump"
public class BIN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BIN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Reduce memory consumption by evicting all LN targets. Note that the targets are not persistent, so this doesn't affect node dirtiness. The BIN should be latched by the caller.
   * @return number of evicted bytes
   */
  // line 10 "../../../../BIN.ump"
   public long evictLNs() throws DatabaseException{
    assert isLatchOwner() : "BIN must be latched before evicting LNs";
	return original();
  }

}