/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import com.sleepycat.je.latch.LatchSupport;

// line 3 "../../../Cursor.ump"
public class Cursor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cursor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../Cursor.ump"
   protected void hook19(CursorImpl dup) throws DatabaseException{
    dup.latchBINs();
	original(dup);
  }

  // line 12 "../../../Cursor.ump"
   protected void hook20(CursorImpl origCursor, CursorImpl dup) throws DatabaseException{
    if (origCursor != null) {
	    origCursor.releaseBINs();
	}
	if (dup != null) {
	    dup.releaseBINs();
	}
	original(origCursor, dup);
  }

  // line 22 "../../../Cursor.ump"
   protected void hook21(CursorImpl origCursor) throws DatabaseException{
    if (origCursor != null) {
	    origCursor.releaseBINs();
	}
	original(origCursor);
  }

  // line 29 "../../../Cursor.ump"
   protected void hook22() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0 : LatchSupport.latchesHeldToString();
	original();
  }

  // line 34 "../../../Cursor.ump"
   protected void hook23() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 1 : LatchSupport.latchesHeldToString();
	original();
  }

  // line 39 "../../../Cursor.ump"
   protected void hook24() throws DatabaseException{
    cursorImpl.releaseBINs();
	original();
  }

  // line 46 "../../../Cursor.ump"
   protected void hook25(CursorImpl dup, DatabaseEntry key, DatabaseEntry data, LockType searchLockType, LockType advanceLockType, SearchMode searchMode, boolean advanceAfterRangeSearch, OperationStatus status, boolean keyChange) throws DatabaseException{
    try {
	    original(dup, key, data, searchLockType, advanceLockType, searchMode, advanceAfterRangeSearch, status,
		    keyChange);
	} finally {
	    cursorImpl.releaseBINs();
	    if (status != OperationStatus.SUCCESS && dup != cursorImpl) {
		dup.releaseBINs();
	    }
	}
  }

  // line 58 "../../../Cursor.ump"
   protected void hook26() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 63 "../../../Cursor.ump"
   protected void hook27() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 68 "../../../Cursor.ump"
   protected void hook28() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 73 "../../../Cursor.ump"
   protected void hook29() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 78 "../../../Cursor.ump"
   protected void hook30() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 83 "../../../Cursor.ump"
   protected void hook31() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 88 "../../../Cursor.ump"
   protected void hook32(CursorImpl origCursor) throws DatabaseException{
    origCursor.releaseBINs();
	original(origCursor);
  }

  // line 93 "../../../Cursor.ump"
   protected void hook33(CursorImpl origCursor) throws DatabaseException{
    origCursor.latchBINs();
	original(origCursor);
  }

  // line 98 "../../../Cursor.ump"
   protected void hook34(CursorImpl origCursor) throws DatabaseException{
    origCursor.releaseBINs();
	original(origCursor);
  }

  // line 103 "../../../Cursor.ump"
   protected void hook35(CursorImpl origCursor) throws DatabaseException{
    origCursor.latchBINs();
	original(origCursor);
  }

}