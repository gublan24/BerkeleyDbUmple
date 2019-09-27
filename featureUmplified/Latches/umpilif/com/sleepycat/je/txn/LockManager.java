/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../LockManager.ump"
public class LockManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../LockManager.ump"
   private boolean checkNoLatchesHeld(boolean nonBlockingRequest){
    if (nonBlockingRequest) {
	    return true;
	} else {
	    return (LatchSupport.countLatchesHeld() == 0);
	}
  }

  // line 18 "../../../../LockManager.ump"
   protected void hook770() throws DatabaseException{
    lockTableLatches = new Latch[nLockTables];
	original();
  }

  // line 23 "../../../../LockManager.ump"
   protected void hook771(EnvironmentImpl envImpl, int i) throws DatabaseException{
    lockTableLatches[i] = LatchSupport.makeLatch("Lock Table " + i, envImpl);
	original(envImpl, i);
  }

  // line 28 "../../../../LockManager.ump"
   protected void hook772(boolean nonBlockingRequest) throws DeadlockException,DatabaseException{
    assert checkNoLatchesHeld(nonBlockingRequest) : LatchSupport.countLatchesHeld()
		+ " latches held while trying to lock, lock table =" + LatchSupport.latchesHeldToString();
	original(nonBlockingRequest);
  }

  // line 34 "../../../../LockManager.ump"
   protected void hook773(StringBuffer sb, int i) throws DatabaseException{
    lockTableLatches[i].acquire();
	try {
	    original(sb, i);
	} finally {
	    lockTableLatches[i].release();
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../LockManager.ump"
  protected Latch[] lockTableLatches ;

  
}