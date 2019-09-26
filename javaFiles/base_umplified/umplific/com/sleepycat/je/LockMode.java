/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../LockMode.ump"
public class LockMode
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockMode()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 35 "../../../LockMode.ump"
   private  LockMode(String lockModeName){
    this.lockModeName = lockModeName;
  }

  // line 39 "../../../LockMode.ump"
   public String toString(){
    return "LockMode." + lockModeName;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../LockMode.ump"
  private String lockModeName ;
// line 11 "../../../LockMode.ump"
  public static final LockMode DEFAULT = new LockMode("DEFAULT") ;
// line 16 "../../../LockMode.ump"
  public static final LockMode READ_UNCOMMITTED = new LockMode("READ_UNCOMMITTED") ;
// line 22 "../../../LockMode.ump"
  public static final LockMode DIRTY_READ = READ_UNCOMMITTED ;
// line 27 "../../../LockMode.ump"
  public static final LockMode READ_COMMITTED = new LockMode("READ_COMMITTED") ;
// line 32 "../../../LockMode.ump"
  public static final LockMode RMW = new LockMode("RMW") ;

  
}