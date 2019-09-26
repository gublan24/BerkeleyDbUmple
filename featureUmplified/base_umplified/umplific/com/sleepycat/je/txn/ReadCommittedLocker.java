/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.Key;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.CursorImpl;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../ReadCommittedLocker.ump"
public class ReadCommittedLocker extends BuddyLocker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ReadCommittedLocker()
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
   * Creates a ReadCommittedLocker.
   * @param buddy is a transactional locker that will be used for acquiringwrite locks.
   */
  // line 18 "../../../../ReadCommittedLocker.ump"
   public  ReadCommittedLocker(EnvironmentImpl env, Locker buddy) throws DatabaseException{
    super(env, (buddy instanceof ReadCommittedLocker) ? ((ReadCommittedLocker) buddy).getBuddy() : buddy);
	assert getBuddy().isTransactional();
  }


  /**
   * 
   * Creates a new instance of this txn for the same environment.  No transactional locks are held by this object, so no locks are retained. newNonTxnLocker is also called for the BuddyLocker.
   */
  // line 26 "../../../../ReadCommittedLocker.ump"
   public Locker newNonTxnLocker() throws DatabaseException{
    return new ReadCommittedLocker(envImpl, getBuddy().newNonTxnLocker());
  }


  /**
   * 
   * Forwards write locks to the buddy locker (the transaction locker).
   * @see Locker#lockInternal
   * @Override
   */
  // line 36 "../../../../ReadCommittedLocker.ump"
  public LockResult lockInternal(long nodeId, LockType lockType, boolean noWait, DatabaseImpl database) throws DatabaseException{
    if (lockType.isWriteLock()) {
	    return getBuddy().lockInternal(nodeId, lockType, noWait, database);
	} else {
	    return super.lockInternal(nodeId, lockType, noWait, database);
	}
  }


  /**
   * 
   * Releases the lock from this locker, or if not owned by this locker then releases it from the buddy locker.
   */
  // line 47 "../../../../ReadCommittedLocker.ump"
   public void releaseLock(long nodeId) throws DatabaseException{
    if (!lockManager.release(nodeId, this)) {
	    lockManager.release(nodeId, getBuddy());
	}
  }


  /**
   * 
   * Forwards this method to the transactional buddy.  Since the buddy handles write locks, it knows whether this transaction created the node.
   */
  // line 56 "../../../../ReadCommittedLocker.ump"
   public boolean createdNode(long nodeId) throws DatabaseException{
    return getBuddy().createdNode(nodeId);
  }


  /**
   * 
   * Forwards this method to the transactional buddy.  The buddy handles write locks and therefore handles abort information.
   */
  // line 63 "../../../../ReadCommittedLocker.ump"
   public long getAbortLsn(long nodeId) throws DatabaseException{
    return getBuddy().getAbortLsn(nodeId);
  }


  /**
   * 
   * @return the WriteLockInfo for this node.
   */
  // line 70 "../../../../ReadCommittedLocker.ump"
   public WriteLockInfo getWriteLockInfo(long nodeId) throws DatabaseException{
    return getBuddy().getWriteLockInfo(nodeId);
  }


  /**
   * 
   * Forwards this method to the transactional buddy.  The buddy Txn tracks cursors.
   */
  // line 77 "../../../../ReadCommittedLocker.ump"
   public void registerCursor(CursorImpl cursor) throws DatabaseException{
    getBuddy().registerCursor(cursor);
  }


  /**
   * 
   * Forwards this method to the transactional buddy.  The buddy Txn tracks cursors.
   */
  // line 84 "../../../../ReadCommittedLocker.ump"
   public void unRegisterCursor(CursorImpl cursor) throws DatabaseException{
    getBuddy().unRegisterCursor(cursor);
  }


  /**
   * 
   * Is always transactional because the buddy locker is transactional.
   */
  // line 91 "../../../../ReadCommittedLocker.ump"
   public boolean isTransactional(){
    return true;
  }


  /**
   * 
   * Is always read-committed isolation.
   */
  // line 98 "../../../../ReadCommittedLocker.ump"
   public boolean isReadCommittedIsolation(){
    return true;
  }

}