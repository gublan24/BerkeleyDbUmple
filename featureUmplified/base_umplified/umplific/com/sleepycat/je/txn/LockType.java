/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../LockType.ump"
public class LockType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockType()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * No lock types can be defined outside this class.
   */
  // line 69 "../../../../LockType.ump"
   private  LockType(int index, boolean write, String name){
    this.index = index;
	this.write = write;
	this.name = name;
  }


  /**
   * 
   * Specifies that when this type is requested it can result in LockGrantType.RESTART.
   */
  // line 85 "../../../../LockType.ump"
   private void setCausesRestart(){
    causesRestart = true;
  }


  /**
   * 
   * Returns the LockConfict that results when this lock type is held and the given lock type is requested by another locker.
   */
  // line 99 "../../../../LockType.ump"
  public LockConflict getConflict(LockType requestedType){
    return conflictMatrix[index][requestedType.index];
  }


  /**
   * 
   * Returns the LockUpgrade that results when this lock type is held and the given lock type is requested by the same locker. <p>For the returned LockUpgrade object, getIllegal will never return true because this method fires an assertion if getIllegal returns true.
   */
  // line 106 "../../../../LockType.ump"
  public LockUpgrade getUpgrade(LockType requestedType){
    LockUpgrade upgrade = upgradeMatrix[index][requestedType.index];
	assert !upgrade.getIllegal() : toString() + " to " + requestedType;
	return upgrade;
  }

  // line 112 "../../../../LockType.ump"
   public String toString(){
    return name;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../LockType.ump"
  public static final LockType READ = new LockType(0, false, "READ") ;
// line 11 "../../../../LockType.ump"
  public static final LockType WRITE = new LockType(1, true, "WRITE") ;
// line 13 "../../../../LockType.ump"
  public static final LockType RANGE_READ = new LockType(2, false, "RANGE_READ") ;
// line 15 "../../../../LockType.ump"
  public static final LockType RANGE_WRITE = new LockType(3, true, "RANGE_WRITE") ;
// line 17 "../../../../LockType.ump"
  public static final LockType RANGE_INSERT = new LockType(4, false, "RANGE_INSERT") ;
// line 22 "../../../../LockType.ump"
  public static final LockType NONE = new LockType(5, false, "NONE") ;
// line 27 "../../../../LockType.ump"
  public static final LockType RESTART = new LockType(6, false, "RESTART") ;
// line 33 "../../../../LockType.ump"
  private static LockConflict[][] conflictMatrix = {{ LockConflict.ALLOW, LockConflict.BLOCK, LockConflict.ALLOW, LockConflict.BLOCK, LockConflict.ALLOW },
	    { LockConflict.BLOCK, LockConflict.BLOCK, LockConflict.BLOCK, LockConflict.BLOCK, LockConflict.ALLOW },
	    { LockConflict.ALLOW, LockConflict.BLOCK, LockConflict.ALLOW, LockConflict.BLOCK, LockConflict.BLOCK },
	    { LockConflict.BLOCK, LockConflict.BLOCK, LockConflict.BLOCK, LockConflict.BLOCK, LockConflict.BLOCK },
	    { LockConflict.ALLOW, LockConflict.ALLOW, LockConflict.RESTART, LockConflict.RESTART,
		    LockConflict.ALLOW }};
// line 45 "../../../../LockType.ump"
  private static LockUpgrade[][] upgradeMatrix = {{ LockUpgrade.EXISTING, LockUpgrade.WRITE_PROMOTE, LockUpgrade.RANGE_READ_IMMED,
		    LockUpgrade.RANGE_WRITE_PROMOTE, LockUpgrade.ILLEGAL },
	    { LockUpgrade.EXISTING, LockUpgrade.EXISTING, LockUpgrade.RANGE_WRITE_IMMED, LockUpgrade.RANGE_WRITE_IMMED,
		    LockUpgrade.ILLEGAL },
	    { LockUpgrade.EXISTING, LockUpgrade.RANGE_WRITE_PROMOTE, LockUpgrade.EXISTING,
		    LockUpgrade.RANGE_WRITE_PROMOTE, LockUpgrade.ILLEGAL },
	    { LockUpgrade.EXISTING, LockUpgrade.EXISTING, LockUpgrade.EXISTING, LockUpgrade.EXISTING,
		    LockUpgrade.ILLEGAL },
	    { LockUpgrade.ILLEGAL, LockUpgrade.ILLEGAL, LockUpgrade.ILLEGAL, LockUpgrade.ILLEGAL,
		    LockUpgrade.EXISTING }};
// line 57 "../../../../LockType.ump"
  private int index ;
// line 59 "../../../../LockType.ump"
  private boolean write ;
// line 61 "../../../../LockType.ump"
  private String name ;
// line 63 "../../../../LockType.ump"
  private boolean causesRestart ;

// line 77 "../../../../LockType.ump"
  public final boolean isWriteLock () 
  {
    return write;
  }

// line 91 "../../../../LockType.ump"
  final boolean getCausesRestart () 
  {
    return causesRestart;
  }

  
}