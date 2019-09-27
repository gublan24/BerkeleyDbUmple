/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../TreeUtils.ump"
public class TreeUtils
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeUtils()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * For tree dumper.
   */
  // line 13 "../../../../TreeUtils.ump"
   public static  String indent(int nSpaces){
    return SPACES.substring(0, nSpaces);
  }

  // line 17 "../../../../TreeUtils.ump"
   public static  String dumpByteArray(byte [] b){
    StringBuffer sb = new StringBuffer();
	if (b != null) {
	    if (Key.DUMP_BINARY) {
		for (int i = 0; i < b.length; i++) {
		    sb.append(b[i] & 0xFF);
		    sb.append(" ");
		}
	    } else {
		sb.append(new String(b));
	    }
	} else {
	    sb.append("null");
	}
	return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../TreeUtils.ump"
  static private final String SPACES = "                                " + "                                "
	    + "                                " + "                                " ;

  
}