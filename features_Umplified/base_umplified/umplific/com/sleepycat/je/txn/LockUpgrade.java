/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../LockUpgrade.ump"
public class LockUpgrade
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockUpgrade()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * No upgrade types can be defined outside this class.
   */
  // line 28 "../../../../LockUpgrade.ump"
   private  LockUpgrade(LockType upgrade, boolean promotion, boolean illegal){
    this.upgrade = upgrade;
	this.promotion = promotion;
	this.illegal = illegal;
  }


  /**
   * 
   * This method is called to determine whether the upgrade is illegal. If true is returned, an internal error has occurred.  This should never happen since RANGE_INSERT should never be requested along with other locks by the same locker; a separate locker is used for RANGE_INSERT locks.
   */
  // line 37 "../../../../LockUpgrade.ump"
  public boolean getIllegal(){
    return illegal;
  }


  /**
   * 
   * This method is called first to determine whether an upgrade to a new lock type is needed, and what the new lock type should be.  If null is returned, the existing lock should be unchanged and no upgrade is needed.  If non-null is returned, an upgrade to the returned type should be performed; in this case, call getPromotion to determine how to do the upgrade.
   */
  // line 44 "../../../../LockUpgrade.ump"
  public LockType getUpgrade(){
    return upgrade;
  }


  /**
   * 
   * This method is called when getUpgrade returns non-null to determine whether the upgrade is a true promotion or can be granted immediately. A true promotion is a change from read to write locking, and may require waiting if the write lock conflicts with a lock held by another locker. An upgrade that is not a promotion is just a type change, and never causes a lock conflict.
   */
  // line 51 "../../../../LockUpgrade.ump"
  public boolean getPromotion(){
    return promotion;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../LockUpgrade.ump"
  static final LockUpgrade ILLEGAL = new LockUpgrade(null, false, true) ;
// line 8 "../../../../LockUpgrade.ump"
  static final LockUpgrade EXISTING = new LockUpgrade(null, false, false) ;
// line 10 "../../../../LockUpgrade.ump"
  static final LockUpgrade WRITE_PROMOTE = new LockUpgrade(LockType.WRITE, true, false) ;
// line 12 "../../../../LockUpgrade.ump"
  static final LockUpgrade RANGE_READ_IMMED = new LockUpgrade(LockType.RANGE_READ, false, false) ;
// line 14 "../../../../LockUpgrade.ump"
  static final LockUpgrade RANGE_WRITE_IMMED = new LockUpgrade(LockType.RANGE_WRITE, false, false) ;
// line 16 "../../../../LockUpgrade.ump"
  static final LockUpgrade RANGE_WRITE_PROMOTE = new LockUpgrade(LockType.RANGE_WRITE, true, false) ;
// line 18 "../../../../LockUpgrade.ump"
  private LockType upgrade ;
// line 20 "../../../../LockUpgrade.ump"
  private boolean promotion ;
// line 22 "../../../../LockUpgrade.ump"
  private boolean illegal ;

  
}