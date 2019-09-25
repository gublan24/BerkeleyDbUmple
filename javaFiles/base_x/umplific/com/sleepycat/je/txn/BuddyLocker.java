/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../BuddyLocker.ump"
public class BuddyLocker extends BasicLocker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BuddyLocker()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }


  /**
   * 
   * Creates a BuddyLocker.
   */
  // line 15 "../../../../BuddyLocker.ump"
   public  BuddyLocker(EnvironmentImpl env, Locker buddy) throws DatabaseException{
    super(env);
	this.buddy = buddy;
  }


  /**
   * 
   * Returns the buddy locker.
   */
  // line 23 "../../../../BuddyLocker.ump"
  public Locker getBuddy(){
    return buddy;
  }


  /**
   * 
   * Forwards this call to the buddy locker.
   */
  // line 30 "../../../../BuddyLocker.ump"
   public Txn getTxnLocker(){
    return buddy.getTxnLocker();
  }


  /**
   * 
   * Creates a new instance of this txn for the same environment.  No transactional locks are held by this object, so no locks are retained. newNonTxnLocker is also called for the BuddyLocker.
   */
  // line 37 "../../../../BuddyLocker.ump"
   public Locker newNonTxnLocker() throws DatabaseException{
    return new BuddyLocker(envImpl, buddy.newNonTxnLocker());
  }


  /**
   * 
   * Forwards this call to the base class and to the buddy locker.
   */
  // line 44 "../../../../BuddyLocker.ump"
   public void releaseNonTxnLocks() throws DatabaseException{
    super.releaseNonTxnLocks();
	buddy.releaseNonTxnLocks();
  }


  /**
   * 
   * Returns whether this locker can share locks with the given locker.
   */
  // line 52 "../../../../BuddyLocker.ump"
   public boolean sharesLocksWith(Locker other){
    if (super.sharesLocksWith(other)) {
	    return true;
	} else {
	    return buddy == other;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../BuddyLocker.ump"
  private Locker buddy ;

  
}