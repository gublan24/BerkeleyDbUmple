/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../ThreadLocker.ump"
public class ThreadLocker extends BasicLocker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ThreadLocker()
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
   * Creates a ThreadLocker.
   */
  // line 13 "../../../../ThreadLocker.ump"
   public  ThreadLocker(EnvironmentImpl env) throws DatabaseException{
    super(env);
  }


  /**
   * 
   * Check that this txn is not used in the wrong thread.
   */
  // line 20 "../../../../ThreadLocker.ump"
   protected void checkState(boolean ignoreCalledByAbort) throws DatabaseException{
    if (thread != Thread.currentThread()) {
	    throw new DatabaseException("A per-thread transaction was" + " created in " + thread + " but used in "
		    + Thread.currentThread());
	}
  }


  /**
   * 
   * Creates a new instance of this txn for the same environment.  No transactional locks are held by this object, so no locks are retained.
   */
  // line 30 "../../../../ThreadLocker.ump"
   public Locker newNonTxnLocker() throws DatabaseException{
    checkState(false);
	return new ThreadLocker(envImpl);
  }


  /**
   * 
   * Returns whether this locker can share locks with the given locker. Locks are shared when both are txns are ThreadLocker instances for the same thread.
   */
  // line 38 "../../../../ThreadLocker.ump"
   public boolean sharesLocksWith(Locker other){
    if (super.sharesLocksWith(other)) {
	    return true;
	} else if (other instanceof ThreadLocker) {
	    return thread == ((ThreadLocker) other).thread;
	} else {
	    return false;
	}
  }

}