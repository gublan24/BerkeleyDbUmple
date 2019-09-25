/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../PutMode.ump"
public class PutMode
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PutMode()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../PutMode.ump"
  public static final PutMode NODUP = new PutMode() ;
// line 8 "../../../../PutMode.ump"
  public static final PutMode CURRENT = new PutMode() ;
// line 10 "../../../../PutMode.ump"
  public static final PutMode OVERWRITE = new PutMode() ;
// line 12 "../../../../PutMode.ump"
  public static final PutMode NOOVERWRITE = new PutMode() ;

  
}