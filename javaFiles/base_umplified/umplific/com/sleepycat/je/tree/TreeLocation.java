/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;

// line 3 "../../../../TreeLocation.ump"
public class TreeLocation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeLocation()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 16 "../../../../TreeLocation.ump"
   public void reset(){
    bin = null;
	index = -1;
	lnKey = null;
	childLsn = DbLsn.NULL_LSN;
  }

  // line 23 "../../../../TreeLocation.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer("<TreeLocation bin=\"");
	if (bin == null) {
	    sb.append("null");
	} else {
	    sb.append(bin.getNodeId());
	}
	sb.append("\" index=\"");
	sb.append(index);
	sb.append("\" lnKey=\"");
	sb.append(Key.dumpString(lnKey, 0));
	sb.append("\" childLsn=\"");
	sb.append(DbLsn.toString(childLsn));
	sb.append("\">");
	return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../TreeLocation.ump"
  public BIN bin ;
// line 9 "../../../../TreeLocation.ump"
  public int index ;
// line 11 "../../../../TreeLocation.ump"
  public byte[] lnKey ;
// line 13 "../../../../TreeLocation.ump"
  public long childLsn = DbLsn.NULL_LSN ;

  
}