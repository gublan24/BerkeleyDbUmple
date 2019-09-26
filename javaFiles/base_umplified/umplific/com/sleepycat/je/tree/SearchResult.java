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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SearchResult()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 17 "../../../../SearchResult.ump"
   public  SearchResult(){
    exactParentFound = false;
	keepSearching = true;
	parent = null;
	index = -1;
	childNotResident = false;
  }

  // line 25 "../../../../SearchResult.ump"
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
// line 8 "../../../../SearchResult.ump"
  public boolean keepSearching ;
// line 10 "../../../../SearchResult.ump"
  public boolean childNotResident ;
// line 12 "../../../../SearchResult.ump"
  public IN parent ;
// line 14 "../../../../SearchResult.ump"
  public int index ;

  
}