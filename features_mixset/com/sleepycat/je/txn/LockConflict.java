/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../LockConflict.ump"
public class LockConflict
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockConflict()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * No conflict types can be defined outside this class.
   */
  // line 20 "../../../../LockConflict.ump"
   private  LockConflict(boolean allowed, boolean restart){
    this.allowed = allowed;
	this.restart = restart;
  }


  /**
   * 
   * This method is called first to determine whether the locks is allowed. If true, there is no conflict.  If false, there is a conflict and the requester must wait for or be denied the lock, or (if getRestart returns true) an exception should be thrown to cause the requester's operation to be restarted.
   */
  // line 28 "../../../../LockConflict.ump"
  public boolean getAllowed(){
    return allowed;
  }


  /**
   * 
   * This method is called when getAllowed returns false to determine whether an exception should be thrown to cause the requester's operation to be restarted.  If getAllowed returns false and this method returns false, the requester should wait for or be denied the lock, depending on the request mode.  If getAllowed returns true, this method will always return false.
   */
  // line 35 "../../../../LockConflict.ump"
  public boolean getRestart(){
    return restart;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../LockConflict.ump"
  static final LockConflict ALLOW = new LockConflict(true, false) ;
// line 8 "../../../../LockConflict.ump"
  static final LockConflict BLOCK = new LockConflict(false, false) ;
// line 10 "../../../../LockConflict.ump"
  static final LockConflict RESTART = new LockConflict(false, true) ;
// line 12 "../../../../LockConflict.ump"
  private boolean allowed ;
// line 14 "../../../../LockConflict.ump"
  private boolean restart ;

  
}