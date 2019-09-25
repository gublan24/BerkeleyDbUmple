/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../LockGrantType.ump"
public class LockGrantType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockGrantType()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 25 "../../../../LockGrantType.ump"
   private  LockGrantType(String name){
    this.name = name;
  }

  // line 29 "../../../../LockGrantType.ump"
   public String toString(){
    return name;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../LockGrantType.ump"
  private String name ;
// line 8 "../../../../LockGrantType.ump"
  public static final LockGrantType NEW = new LockGrantType("NEW") ;
// line 10 "../../../../LockGrantType.ump"
  public static final LockGrantType WAIT_NEW = new LockGrantType("WAIT_NEW") ;
// line 12 "../../../../LockGrantType.ump"
  public static final LockGrantType PROMOTION = new LockGrantType("PROMOTION") ;
// line 14 "../../../../LockGrantType.ump"
  public static final LockGrantType WAIT_PROMOTION = new LockGrantType("WAIT_PROMOTION") ;
// line 16 "../../../../LockGrantType.ump"
  public static final LockGrantType EXISTING = new LockGrantType("EXISTING") ;
// line 18 "../../../../LockGrantType.ump"
  public static final LockGrantType DENIED = new LockGrantType("DENIED") ;
// line 20 "../../../../LockGrantType.ump"
  public static final LockGrantType WAIT_RESTART = new LockGrantType("WAIT_RESTART") ;
// line 22 "../../../../LockGrantType.ump"
  public static final LockGrantType NONE_NEEDED = new LockGrantType("NONE_NEEDED") ;

  
}