/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;

// line 3 "../../../../Lock.ump"
public class Lock
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Lock()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Create a Lock.
   */
  // line 31 "../../../../Lock.ump"
  public  Lock(Long nodeId){
    this.nodeId = nodeId;
  }

  // line 35 "../../../../Lock.ump"
   public  Lock(){
    
  }

  // line 38 "../../../../Lock.ump"
  public Long getNodeId(){
    return nodeId;
  }


  /**
   * 
   * The first waiter goes into the firstWaiter member variable.  Once the waiterList is made, all appended waiters go into waiterList, even after the firstWaiter goes away and leaves that field null, so as to leave the list ordered.
   */
  // line 45 "../../../../Lock.ump"
   private void addWaiterToEndOfList(LockInfo waiter, MemoryBudget mb, int lockTableIndex){
    if (waiterList == null) {
	    if (firstWaiter == null) {
		firstWaiter = waiter;
	    } else {
		waiterList = new ArrayList();
		waiterList.add(waiter);
	    }
	} else {
	    waiterList.add(waiter);
	}
	this.hook760(mb, lockTableIndex);
  }


  /**
   * 
   * Add this waiter to the front of the list.
   */
  // line 62 "../../../../Lock.ump"
   private void addWaiterToHeadOfList(LockInfo waiter, MemoryBudget mb, int lockTableIndex){
    if (firstWaiter != null) {
	    if (waiterList == null) {
		waiterList = new ArrayList();
	    }
	    waiterList.add(0, firstWaiter);
	}
	firstWaiter = waiter;
	this.hook761(mb, lockTableIndex);
  }


  /**
   * 
   * Get a list of waiters for debugging and error messages.
   */
  // line 76 "../../../../Lock.ump"
  public List getWaitersListClone(){
    List dumpWaiters = new ArrayList();
	if (firstWaiter != null) {
	    dumpWaiters.add(firstWaiter);
	}
	if (waiterList != null) {
	    dumpWaiters.addAll(waiterList);
	}
	return dumpWaiters;
  }


  /**
   * 
   * Remove this locker from the waiter list.
   */
  // line 90 "../../../../Lock.ump"
  public void flushWaiter(Locker locker, MemoryBudget mb, int lockTableIndex){
    if ((firstWaiter != null) && (firstWaiter.getLocker() == locker)) {
	    firstWaiter = null;
	    this.hook762(mb, lockTableIndex);
	} else if (waiterList != null) {
	    Iterator iter = waiterList.iterator();
	    while (iter.hasNext()) {
		LockInfo info = (LockInfo) iter.next();
		if (info.getLocker() == locker) {
		    iter.remove();
		    this.hook763(mb, lockTableIndex);
		    return;
		}
	    }
	}
  }

  // line 107 "../../../../Lock.ump"
   private void addOwner(LockInfo newLock, MemoryBudget mb, int lockTableIndex){
    if (firstOwner == null) {
	    firstOwner = newLock;
	} else {
	    if (ownerSet == null) {
		ownerSet = new HashSet();
	    }
	    ownerSet.add(newLock);
	}
	this.hook764(mb, lockTableIndex);
  }


  /**
   * 
   * Get a new Set of the owners.
   */
  // line 122 "../../../../Lock.ump"
  public Set getOwnersClone(){
    Set owners;
	if (ownerSet != null) {
	    owners = new HashSet(ownerSet);
	} else {
	    owners = new HashSet();
	}
	if (firstOwner != null) {
	    owners.add(firstOwner);
	}
	return owners;
  }


  /**
   * 
   * Remove this LockInfo from the owner set.
   */
  // line 138 "../../../../Lock.ump"
   private boolean flushOwner(LockInfo oldOwner, MemoryBudget mb, int lockTableIndex){
    boolean removed = false;
	if (oldOwner != null) {
	    if (firstOwner == oldOwner) {
		firstOwner = null;
		return true;
	    }
	    if (ownerSet != null) {
		removed = ownerSet.remove(oldOwner);
	    }
	}
	this.hook765(mb, lockTableIndex, removed);
	return removed;
  }


  /**
   * 
   * Remove this locker from the owner set.
   */
  // line 156 "../../../../Lock.ump"
   private LockInfo flushOwner(Locker locker, MemoryBudget mb, int lockTableIndex){
    LockInfo flushedInfo = null;
	if ((firstOwner != null) && (firstOwner.getLocker() == locker)) {
	    flushedInfo = firstOwner;
	    firstOwner = null;
	} else if (ownerSet != null) {
	    Iterator iter = ownerSet.iterator();
	    while (iter.hasNext()) {
		LockInfo o = (LockInfo) iter.next();
		if (o.getLocker() == locker) {
		    iter.remove();
		    flushedInfo = o;
		}
	    }
	}
	this.hook766(mb, lockTableIndex, flushedInfo);
	return flushedInfo;
  }


  /**
   * 
   * Returns the owner LockInfo for a locker, or null if locker is not an owner.
   */
  // line 178 "../../../../Lock.ump"
   private LockInfo getOwnerLockInfo(Locker locker){
    if ((firstOwner != null) && (firstOwner.getLocker() == locker)) {
	    return firstOwner;
	}
	if (ownerSet != null) {
	    Iterator iter = ownerSet.iterator();
	    while (iter.hasNext()) {
		LockInfo o = (LockInfo) iter.next();
		if (o.getLocker() == locker) {
		    return o;
		}
	    }
	}
	return null;
  }


  /**
   * 
   * Return true if locker is an owner of this Lock for lockType, false otherwise. This method is only used by unit tests.
   */
  // line 197 "../../../../Lock.ump"
  public boolean isOwner(Locker locker, LockType lockType){
    LockInfo o = getOwnerLockInfo(locker);
	if (o != null) {
	    LockType ownedLockType = o.getLockType();
	    if (lockType == ownedLockType) {
		return true;
	    }
	    LockUpgrade upgrade = ownedLockType.getUpgrade(lockType);
	    if (!upgrade.getPromotion()) {
		return true;
	    }
	}
	return false;
  }


  /**
   * 
   * Return true if locker is an owner of this Lock and this is a write lock.
   */
  // line 215 "../../../../Lock.ump"
  public boolean isOwnedWriteLock(Locker locker){
    LockInfo o = getOwnerLockInfo(locker);
	return o != null && o.getLockType().isWriteLock();
  }


  /**
   * 
   * Return true if locker is a waiter on this Lock. This method is only used by unit tests.
   */
  // line 223 "../../../../Lock.ump"
  public boolean isWaiter(Locker locker){
    if (firstWaiter != null) {
	    if (firstWaiter.getLocker() == locker) {
		return true;
	    }
	}
	if (waiterList != null) {
	    Iterator iter = waiterList.iterator();
	    while (iter.hasNext()) {
		LockInfo info = (LockInfo) iter.next();
		if (info.getLocker() == locker) {
		    return true;
		}
	    }
	}
	return false;
  }

  // line 241 "../../../../Lock.ump"
  public int nWaiters(){
    int count = 0;
	if (firstWaiter != null) {
	    count++;
	}
	if (waiterList != null) {
	    count += waiterList.size();
	}
	return count;
  }

  // line 252 "../../../../Lock.ump"
  public int nOwners(){
    int count = 0;
	if (firstOwner != null) {
	    count++;
	}
	if (ownerSet != null) {
	    count += ownerSet.size();
	}
	return count;
  }


  /**
   * 
   * Attempts to acquire the lock and returns the LockGrantType. Assumes we hold the lockTableLatch when entering this method.
   */
  // line 267 "../../../../Lock.ump"
  public LockGrantType lock(LockType requestType, Locker locker, boolean nonBlockingRequest, MemoryBudget mb, int lockTableIndex){
    assert validateRequest(locker);
	LockInfo newLock = new LockInfo(locker, requestType);
	LockGrantType grant = tryLock(newLock, nWaiters() == 0, mb, lockTableIndex);
	if (grant == LockGrantType.WAIT_NEW || grant == LockGrantType.WAIT_PROMOTION
		|| grant == LockGrantType.WAIT_RESTART) {
	    if (requestType.getCausesRestart() && grant != LockGrantType.WAIT_RESTART) {
		LockInfo waiter = null;
		Iterator iter = null;
		if (waiterList != null) {
		    iter = waiterList.iterator();
		}
		if (firstWaiter != null) {
		    waiter = firstWaiter;
		} else if ((iter != null) && (iter.hasNext())) {
		    waiter = (LockInfo) iter.next();
		}
		while (waiter != null) {
		    Locker waiterLocker = waiter.getLocker();
		    LockType waiterType = waiter.getLockType();
		    if (waiterType != LockType.RESTART && locker != waiterLocker
			    && !locker.sharesLocksWith(waiterLocker)) {
			LockConflict conflict = waiterType.getConflict(requestType);
			if (conflict.getRestart()) {
			    grant = LockGrantType.WAIT_RESTART;
			    break;
			}
		    }
		    if ((iter != null) && (iter.hasNext())) {
			waiter = (LockInfo) iter.next();
		    } else {
			waiter = null;
		    }
		}
	    }
	    if (nonBlockingRequest) {
		grant = LockGrantType.DENIED;
	    } else {
		if (grant == LockGrantType.WAIT_PROMOTION) {
		    addWaiterToHeadOfList(newLock, mb, lockTableIndex);
		} else {
		    assert grant == LockGrantType.WAIT_NEW || grant == LockGrantType.WAIT_RESTART;
		    if (grant == LockGrantType.WAIT_RESTART) {
			newLock.setLockType(LockType.RESTART);
		    }
		    addWaiterToEndOfList(newLock, mb, lockTableIndex);
		}
	    }
	}
	return grant;
  }


  /**
   * 
   * Releases a lock and moves the next waiter(s) to the owners.
   * @returnnull if we were not the owner,a non-empty set if owners should be notified after releasing, an empty set if no notification is required.
   */
  // line 323 "../../../../Lock.ump"
  public Set release(Locker locker, MemoryBudget mb, int lockTableIndex){
    LockInfo removedLock = flushOwner(locker, mb, lockTableIndex);
	if (removedLock == null) {
	    return null;
	}
	Set lockersToNotify = Collections.EMPTY_SET;
	if (nWaiters() == 0) {
	    return lockersToNotify;
	}
	LockInfo waiter = null;
	Iterator iter = null;
	boolean isFirstWaiter = false;
	if (waiterList != null) {
	    iter = waiterList.iterator();
	}
	if (firstWaiter != null) {
	    waiter = firstWaiter;
	    isFirstWaiter = true;
	} else if ((iter != null) && (iter.hasNext())) {
	    waiter = (LockInfo) iter.next();
	}
	while (waiter != null) {
	    LockType waiterType = waiter.getLockType();
	    Locker waiterLocker = waiter.getLocker();
	    LockGrantType grant;
	    if (waiterType == LockType.RESTART) {
		grant = rangeInsertConflict(waiterLocker) ? LockGrantType.WAIT_NEW : LockGrantType.NEW;
	    } else {
		grant = tryLock(waiter, true, mb, lockTableIndex);
	    }
	    if (grant == LockGrantType.NEW || grant == LockGrantType.EXISTING || grant == LockGrantType.PROMOTION) {
		if (isFirstWaiter) {
		    firstWaiter = null;
		} else {
		    iter.remove();
		}
		if (lockersToNotify == Collections.EMPTY_SET) {
		    lockersToNotify = new HashSet();
		}
		lockersToNotify.add(waiterLocker);
		this.hook767(mb, lockTableIndex);
	    } else {
		assert grant == LockGrantType.WAIT_NEW || grant == LockGrantType.WAIT_PROMOTION
			|| grant == LockGrantType.WAIT_RESTART;
		break;
	    }
	    if ((iter != null) && (iter.hasNext())) {
		waiter = (LockInfo) iter.next();
		isFirstWaiter = false;
	    } else {
		waiter = null;
	    }
	}
	return lockersToNotify;
  }


  /**
   * 
   * Called from lock() to try locking a new request, and from release() to try locking a waiting request.
   * @param newLock is the lock that is requested.
   * @param firstWaiterInLine determines whether to grant the lock when aNEW lock can be granted, but other non-conflicting owners exist; for example, when a new READ lock is requested but READ locks are held by other owners.  This parameter should be true if the requestor is the first waiter in line (or if there are no waiters), and false otherwise.
   * @param mb is the current memory budget.
   * @return LockGrantType.EXISTING, NEW, PROMOTION, WAIT_RESTART, WAIT_NEWor WAIT_PROMOTION.
   */
  // line 386 "../../../../Lock.ump"
   private LockGrantType tryLock(LockInfo newLock, boolean firstWaiterInLine, MemoryBudget mb, int lockTableIndex){
    if (nOwners() == 0) {
	    addOwner(newLock, mb, lockTableIndex);
	    return LockGrantType.NEW;
	}
	Locker locker = newLock.getLocker();
	LockType requestType = newLock.getLockType();
	LockUpgrade upgrade = null;
	LockInfo lockToUpgrade = null;
	boolean ownerExists = false;
	boolean ownerConflicts = false;
	LockInfo owner = null;
	Iterator iter = null;
	if (ownerSet != null) {
	    iter = ownerSet.iterator();
	}
	if (firstOwner != null) {
	    owner = firstOwner;
	} else if ((iter != null) && (iter.hasNext())) {
	    owner = (LockInfo) iter.next();
	}
	while (owner != null) {
	    Locker ownerLocker = owner.getLocker();
	    LockType ownerType = owner.getLockType();
	    if (locker == ownerLocker) {
		assert (upgrade == null);
		upgrade = ownerType.getUpgrade(requestType);
		if (upgrade.getUpgrade() == null) {
		    return LockGrantType.EXISTING;
		} else {
		    lockToUpgrade = owner;
		}
	    } else {
		if (!locker.sharesLocksWith(ownerLocker)) {
		    LockConflict conflict = ownerType.getConflict(requestType);
		    if (conflict.getRestart()) {
			return LockGrantType.WAIT_RESTART;
		    } else {
			if (!conflict.getAllowed()) {
			    ownerConflicts = true;
			}
			ownerExists = true;
		    }
		}
	    }
	    if ((iter != null) && (iter.hasNext())) {
		owner = (LockInfo) iter.next();
	    } else {
		owner = null;
	    }
	}
	if (upgrade != null) {
	    LockType upgradeType = upgrade.getUpgrade();
	    assert upgradeType != null;
	    if (!ownerConflicts) {
		lockToUpgrade.setLockType(upgradeType);
		return upgrade.getPromotion() ? LockGrantType.PROMOTION : LockGrantType.EXISTING;
	    } else {
		return LockGrantType.WAIT_PROMOTION;
	    }
	} else {
	    if (!ownerConflicts && (!ownerExists || firstWaiterInLine)) {
		addOwner(newLock, mb, lockTableIndex);
		return LockGrantType.NEW;
	    } else {
		return LockGrantType.WAIT_NEW;
	    }
	}
  }


  /**
   * 
   * Called from release() when a RESTART request is waiting to determine if any RANGE_INSERT owners exist.  We can't call tryLock for a RESTART lock because it must never be granted.
   */
  // line 459 "../../../../Lock.ump"
   private boolean rangeInsertConflict(Locker waiterLocker){
    LockInfo owner = null;
	Iterator iter = null;
	if (ownerSet != null) {
	    iter = ownerSet.iterator();
	}
	if (firstOwner != null) {
	    owner = firstOwner;
	} else if ((iter != null) && (iter.hasNext())) {
	    owner = (LockInfo) iter.next();
	}
	while (owner != null) {
	    Locker ownerLocker = owner.getLocker();
	    if (ownerLocker != waiterLocker && !ownerLocker.sharesLocksWith(waiterLocker)
		    && owner.getLockType() == LockType.RANGE_INSERT) {
		return true;
	    }
	    if ((iter != null) && (iter.hasNext())) {
		owner = (LockInfo) iter.next();
	    } else {
		owner = null;
	    }
	}
	return false;
  }


  /**
   * 
   * Downgrade a write lock to a read lock.
   */
  // line 488 "../../../../Lock.ump"
  public void demote(Locker locker){
    LockInfo owner = getOwnerLockInfo(locker);
	if (owner != null) {
	    LockType type = owner.getLockType();
	    if (type.isWriteLock()) {
		owner.setLockType((type == LockType.RANGE_WRITE) ? LockType.RANGE_READ : LockType.READ);
	    }
	}
  }


  /**
   * 
   * Transfer a lock from one transaction to another. Make sure that this destination locker is only present as a single reader or writer.
   */
  // line 502 "../../../../Lock.ump"
  public LockType transfer(Locker currentLocker, Locker destLocker, MemoryBudget mb, int lockTableIndex) throws DatabaseException{
    LockType lockType = null;
	int numRemovedLockInfos = 0;
	if (firstOwner != null) {
	    if (firstOwner.getLocker() == destLocker) {
		firstOwner = null;
		numRemovedLockInfos++;
	    } else if (firstOwner.getLocker() == currentLocker) {
		lockType = setNewLocker(firstOwner, destLocker);
	    }
	}
	if (ownerSet != null) {
	    Iterator iter = ownerSet.iterator();
	    while (iter.hasNext()) {
		LockInfo owner = (LockInfo) iter.next();
		if (owner.getLocker() == destLocker) {
		    iter.remove();
		    numRemovedLockInfos++;
		} else if (owner.getLocker() == currentLocker) {
		    lockType = setNewLocker(owner, destLocker);
		}
	    }
	}
	if ((firstWaiter != null) && (firstWaiter.getLocker() == destLocker)) {
	    firstWaiter = null;
	    numRemovedLockInfos++;
	}
	if (waiterList != null) {
	    Iterator iter = waiterList.iterator();
	    while (iter.hasNext()) {
		LockInfo info = (LockInfo) iter.next();
		if (info.getLocker() == destLocker) {
		    iter.remove();
		    numRemovedLockInfos++;
		}
	    }
	}
	this.hook768(mb, lockTableIndex, numRemovedLockInfos);
	return lockType;
  }

  // line 543 "../../../../Lock.ump"
   private LockType setNewLocker(LockInfo owner, Locker destLocker) throws DatabaseException{
    owner.setLocker(destLocker);
	destLocker.addLock(nodeId, this, owner.getLockType(), LockGrantType.NEW);
	return owner.getLockType();
  }


  /**
   * 
   * Transfer a lock from one transaction to many others. Only really needed for case where a write handle lock is being transferred to multiple read handles.
   */
  // line 553 "../../../../Lock.ump"
  public LockType transferMultiple(Locker currentLocker, Locker [] destLockers, MemoryBudget mb, int lockTableIndex) throws DatabaseException{
    LockType lockType = null;
	LockInfo oldOwner = null;
	if (destLockers.length == 1) {
	    return transfer(currentLocker, destLockers[0], mb, lockTableIndex);
	} else {
	    if (firstOwner != null) {
		for (int i = 0; i < destLockers.length; i++) {
		    if (firstOwner.getLocker() == destLockers[i]) {
			firstOwner = null;
			break;
		    }
		}
	    }
	    if (ownerSet != null) {
		Iterator ownersIter = ownerSet.iterator();
		while (ownersIter.hasNext()) {
		    LockInfo o = (LockInfo) ownersIter.next();
		    for (int i = 0; i < destLockers.length; i++) {
			if (o.getLocker() == destLockers[i]) {
			    ownersIter.remove();
			    break;
			}
		    }
		}
	    }
	    if (firstOwner != null) {
		oldOwner = cloneLockInfo(firstOwner, currentLocker, destLockers, mb, lockTableIndex);
	    }
	    if ((ownerSet != null) && (oldOwner == null)) {
		Iterator ownersIter = ownerSet.iterator();
		while (ownersIter.hasNext()) {
		    LockInfo o = (LockInfo) ownersIter.next();
		    oldOwner = cloneLockInfo(o, currentLocker, destLockers, mb, lockTableIndex);
		    if (oldOwner != null) {
			break;
		    }
		}
	    }
	    if (firstWaiter != null) {
		for (int i = 0; i < destLockers.length; i++) {
		    if (firstWaiter.getLocker() == destLockers[i]) {
			firstWaiter = null;
			break;
		    }
		}
	    }
	    if (waiterList != null) {
		Iterator iter = waiterList.iterator();
		while (iter.hasNext()) {
		    LockInfo o = (LockInfo) iter.next();
		    for (int i = 0; i < destLockers.length; i++) {
			if (o.getLocker() == destLockers[i]) {
			    iter.remove();
			    break;
			}
		    }
		}
	    }
	}
	boolean removed = flushOwner(oldOwner, mb, lockTableIndex);
	assert removed;
	return lockType;
  }


  /**
   * 
   * If oldOwner is the current owner, clone it and transform it into a dest locker.
   */
  // line 622 "../../../../Lock.ump"
   private LockInfo cloneLockInfo(LockInfo oldOwner, Locker currentLocker, Locker [] destLockers, MemoryBudget mb, int lockTableIndex) throws DatabaseException{
    if (oldOwner.getLocker() == currentLocker) {
	    try {
		LockType lockType = oldOwner.getLockType();
		for (int i = 0; i < destLockers.length; i++) {
		    LockInfo clonedLockInfo = (LockInfo) oldOwner.clone();
		    clonedLockInfo.setLocker(destLockers[i]);
		    destLockers[i].addLock(nodeId, this, lockType, LockGrantType.NEW);
		    addOwner(clonedLockInfo, mb, lockTableIndex);
		}
		return oldOwner;
	    } catch (CloneNotSupportedException e) {
		throw new DatabaseException(e);
	    }
	} else {
	    return null;
	}
  }


  /**
   * 
   * Return the locker that has a write ownership on this lock. If no write owner exists, return null.
   */
  // line 644 "../../../../Lock.ump"
  public Locker getWriteOwnerLocker(){
    LockInfo owner = null;
	Iterator iter = null;
	if (ownerSet != null) {
	    iter = ownerSet.iterator();
	}
	if (firstOwner != null) {
	    owner = firstOwner;
	} else if ((iter != null) && (iter.hasNext())) {
	    owner = (LockInfo) iter.next();
	}
	while (owner != null) {
	    if (owner.getLockType().isWriteLock()) {
		return owner.getLocker();
	    }
	    if ((iter != null) && (iter.hasNext())) {
		owner = (LockInfo) iter.next();
	    } else {
		owner = null;
	    }
	}
	return null;
  }


  /**
   * 
   * Debugging aid, validation before a lock request.
   */
  // line 671 "../../../../Lock.ump"
   private boolean validateRequest(Locker locker){
    if (firstWaiter != null) {
	    if (firstWaiter.getLocker() == locker) {
		assert false : "locker " + locker + " is already on waiters list.";
	    }
	}
	if (waiterList != null) {
	    Iterator iter = waiterList.iterator();
	    while (iter.hasNext()) {
		LockInfo o = (LockInfo) iter.next();
		if (o.getLocker() == locker) {
		    assert false : "locker " + locker + " is already on waiters list.";
		}
	    }
	}
	return true;
  }


  /**
   * 
   * Debug dumper.
   */
  // line 692 "../../../../Lock.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append(" NodeId:").append(nodeId);
	sb.append(" Owners:");
	if (nOwners() == 0) {
	    sb.append(" (none)");
	} else {
	    if (firstOwner != null) {
		sb.append(firstOwner);
	    }
	    if (ownerSet != null) {
		Iterator iter = ownerSet.iterator();
		while (iter.hasNext()) {
		    LockInfo info = (LockInfo) iter.next();
		    sb.append(info);
		}
	    }
	}
	sb.append(" Waiters:");
	if (nWaiters() == 0) {
	    sb.append(" (none)");
	} else {
	    sb.append(getWaitersListClone());
	}
	return sb.toString();
  }

  // line 719 "../../../../Lock.ump"
   protected void hook760(MemoryBudget mb, int lockTableIndex){
    
  }

  // line 722 "../../../../Lock.ump"
   protected void hook761(MemoryBudget mb, int lockTableIndex){
    
  }

  // line 725 "../../../../Lock.ump"
   protected void hook762(MemoryBudget mb, int lockTableIndex){
    
  }

  // line 728 "../../../../Lock.ump"
   protected void hook763(MemoryBudget mb, int lockTableIndex){
    
  }

  // line 731 "../../../../Lock.ump"
   protected void hook764(MemoryBudget mb, int lockTableIndex){
    
  }

  // line 734 "../../../../Lock.ump"
   protected void hook765(MemoryBudget mb, int lockTableIndex, boolean removed){
    
  }

  // line 737 "../../../../Lock.ump"
   protected void hook766(MemoryBudget mb, int lockTableIndex, LockInfo flushedInfo){
    
  }

  // line 740 "../../../../Lock.ump"
   protected void hook767(MemoryBudget mb, int lockTableIndex){
    
  }

  // line 743 "../../../../Lock.ump"
   protected void hook768(MemoryBudget mb, int lockTableIndex, int numRemovedLockInfos) throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../../../Lock.ump"
  private LockInfo firstOwner ;
// line 19 "../../../../Lock.ump"
  private Set ownerSet ;
// line 21 "../../../../Lock.ump"
  private LockInfo firstWaiter ;
// line 23 "../../../../Lock.ump"
  private List waiterList ;
// line 25 "../../../../Lock.ump"
  private Long nodeId ;

  
}