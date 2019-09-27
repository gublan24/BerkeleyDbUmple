/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../IN.ump"
public class IN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public IN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Returns whether this node can itself be evicted.  This is faster than (getEvictionType() == MAY_EVICT_NODE) and is used by the evictor after a node has been selected, to check that it is still evictable.
   */
  // line 15 "../../../../IN.ump"
   public boolean isEvictable(){
    if (isEvictionProhibited()) {
	    return false;
	}
	if (hasNonLNChildren()) {
	    return false;
	}
	return true;
  }


  /**
   * 
   * Returns the eviction type for this IN, for use by the evictor.  Uses the internal isEvictionProhibited and getChildEvictionType methods that may be overridden by subclasses.
   * @return MAY_EVICT_LNS if evictable LNs may be stripped; otherwise,MAY_EVICT_NODE if the node itself may be evicted; otherwise, MAY_NOT_EVICT.
   */
  // line 29 "../../../../IN.ump"
   public int getEvictionType(){
    if (isEvictionProhibited()) {
	    return MAY_NOT_EVICT;
	} else {
	    return getChildEvictionType();
	}
  }


  /**
   * 
   * Returns whether the node is not evictable, irrespective of the status of the children nodes.
   */
  // line 40 "../../../../IN.ump"
  public boolean isEvictionProhibited(){
    return isDbRoot();
  }


  /**
   * 
   * Returns the eviction type based on the status of child nodes, irrespective of isEvictionProhibited.
   */
  // line 47 "../../../../IN.ump"
  public int getChildEvictionType(){
    return hasResidentChildren() ? MAY_NOT_EVICT : MAY_EVICT_NODE;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../IN.ump"
  public static final int MAY_NOT_EVICT = 0 ;
// line 7 "../../../../IN.ump"
  public static final int MAY_EVICT_LNS = 1 ;
// line 9 "../../../../IN.ump"
  public static final int MAY_EVICT_NODE = 2 ;

  
}