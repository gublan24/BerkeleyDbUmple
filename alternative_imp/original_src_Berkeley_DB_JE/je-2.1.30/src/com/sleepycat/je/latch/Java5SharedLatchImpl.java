/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: Java5SharedLatchImpl.java,v 1.3 2006/01/03 21:55:47 bostic Exp $
 */

package com.sleepycat.je.latch;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.dbi.EnvironmentImpl;

/**
 * Java5SharedLatchImpl provides an implementation of the SharedLatch
 * interface.  By using a wrapper class we can avoid link errors when we run in
 * Java 1.4 JVMs.  LatchSupport will only reference this class if it knows that
 * the ReentrantReadWriteLock class is available at runtime through
 * Class.forName().  LatchSupport only references this class through the
 * SharedLatch interface and only constructs this using
 *
 *    Class.forName("Java5LatchImpl").newInstance();
 */
class Java5SharedLatchImpl
    extends ReentrantReadWriteLock
    implements SharedLatch {

    private String name;
    private boolean noteLatch;

    Java5SharedLatchImpl() {
	super(EnvironmentImpl.getFairLatches());
    }

    /**
     * Set the latch name, used for latches in objects instantiated from the
     * log.
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * If noteLatch is true, then track latch usage in the latchTable.
     */
    public void setNoteLatch(boolean noteLatch) {
	this.noteLatch = noteLatch;
    }

    /**
     * Acquire a latch for exclusive/write access.
     *
     * Wait for the latch if some other thread is holding it.  If there are
     * threads waiting for access, they will be granted the latch on a FIFO
     * basis if fair latches are set. When the method returns, the latch is
     * held for exclusive access.
     *
     * @throws LatchException if the latch is already held by the current
     * thread for exclusive access.
     */
    public void acquireExclusive()
	throws DatabaseException {

        try {
	    if (isWriteLockedByCurrentThread()) {
		throw new LatchException(name + " already held");
	    }

	    writeLock().lock();

            assert (noteLatch ? noteLatch() : true);// intentional side effect;
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
    }

    public boolean acquireExclusiveNoWait()
	throws DatabaseException {

        try {
	    if (isWriteLockedByCurrentThread()) {
		throw new LatchException(name + " already held");
	    }

	    boolean ret = writeLock().tryLock();

            assert (noteLatch ? noteLatch() : true);// intentional side effect;
	    return ret;
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
    }

    /**
     * Acquire a latch for shared/read access.
     */
    public void acquireShared()
        throws DatabaseException {

        try {
	    readLock().lock();

            assert (noteLatch ?  noteLatch() : true);// intentional side effect
	} finally {
	    assert EnvironmentImpl.maybeForceYield();
	}
    }

    /**
     * Release an exclusive or shared latch.  If there are other thread(s)
     * waiting for the latch, they are woken up and granted the latch.
     */
    public void release()
	throws LatchNotHeldException {

	try {
	    if (isWriteLockedByCurrentThread()) {
		writeLock().unlock();
		return;
	    }

	    /*
	     * There's no way to tell if a readlock is held by the current
	     * thread so just try unlocking it.
	     */
	    readLock().unlock();
	} catch (IllegalMonitorStateException IMSE) {
	    return;
	} finally {
	    /* Intentional side effect. */
	    assert (noteLatch ? unNoteLatch() : true);
	}
    }

    /**
     * Only call under the assert system. This records latching by thread.
     */
    private boolean noteLatch()
	throws LatchException {

        return LatchSupport.latchTable.noteLatch(this);
    }

    /**
     * Only call under the assert system. This records latching by thread.
     */
    private boolean unNoteLatch() {
        
	return LatchSupport.latchTable.unNoteLatch(this, name);
    }
}
