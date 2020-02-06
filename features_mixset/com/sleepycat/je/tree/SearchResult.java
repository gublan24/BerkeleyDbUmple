/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../SearchResult.ump"
public class SearchResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SearchResult Attributes
  private int index;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SearchResult()
  {
    index = -1;
    // line 13 "../../../../SearchResult.ump"
    exactParentFound = false;
    	keepSearching = true;
    	parent = null;
    //	index = -1;
    	childNotResident = false;
    // END OF UMPLE AFTER INJECTION
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIndex(int aIndex)
  {
    boolean wasSet = false;
    index = aIndex;
    wasSet = true;
    return wasSet;
  }

  public int getIndex()
  {
    return index;
  }

  public void delete()
  {}

  // line 20 "../../../../SearchResult.ump"
   public String toString(){
    return "exactParentFound=" + exactParentFound + " keepSearching=" + keepSearching + " parent="
		+ ((parent == null) ? "null" : Long.toString(parent.getNodeId())) + " index=" + index
		+ " childNotResident=" + childNotResident;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../SearchResult.ump"
  public boolean exactParentFound ;
// line 7 "../../../../SearchResult.ump"
  public boolean keepSearching ;
// line 8 "../../../../SearchResult.ump"
  public boolean childNotResident ;
// line 9 "../../../../SearchResult.ump"
  public IN parent ;

  
}