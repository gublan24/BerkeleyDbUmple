/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../JEVersion.ump"
public class JEVersion
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JEVersion()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 20 "../../../JEVersion.ump"
   private  JEVersion(int majorNum, int minorNum, int patchNum, String name){
    this.majorNum = majorNum;
	this.minorNum = minorNum;
	this.patchNum = patchNum;
	this.name = name;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 30 "../../../JEVersion.ump"
   public String toString(){
    return getVersionString();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 37 "../../../JEVersion.ump"
   public int getMajor(){
    return majorNum;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 44 "../../../JEVersion.ump"
   public int getMinor(){
    return minorNum;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 51 "../../../JEVersion.ump"
   public int getPatch(){
    return patchNum;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 58 "../../../JEVersion.ump"
   public String getNumericVersionString(){
    StringBuffer version = new StringBuffer();
	version.append(majorNum).append(".");
	version.append(minorNum).append(".");
	version.append(patchNum);
	return version.toString();
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 69 "../../../JEVersion.ump"
   public String getVersionString(){
    StringBuffer version = new StringBuffer();
	version.append(majorNum).append(".");
	version.append(minorNum).append(".");
	version.append(patchNum);
	if (name != null) {
	    version.append(" (");
	    version.append(name).append(")");
	}
	return version.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../JEVersion.ump"
  public static final JEVersion CURRENT_VERSION = new JEVersion(2, 1, 30, null) ;
// line 11 "../../../JEVersion.ump"
  private int majorNum ;
// line 13 "../../../JEVersion.ump"
  private int minorNum ;
// line 15 "../../../JEVersion.ump"
  private int patchNum ;
// line 17 "../../../JEVersion.ump"
  private String name ;

  
}