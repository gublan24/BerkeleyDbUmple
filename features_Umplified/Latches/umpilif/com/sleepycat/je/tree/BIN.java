/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../BIN.ump"
public class BIN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BIN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../BIN.ump"
   protected void hook603(Node child) throws DatabaseException{
    ((IN) child).releaseLatch();
	original(child);
  }

  // line 11 "../../../../BIN.ump"
   protected void hook604() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 16 "../../../../BIN.ump"
   protected void hook605() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 21 "../../../../BIN.ump"
   protected void hook606() throws DatabaseException{
    releaseLatch();
	original();
  }


  /**
   * 
   * Register a cursor with this bin. Caller has this bin already latched.
   * @param cursorCursor to register.
   */
  // line 30 "../../../../BIN.ump"
   public void addCursor(CursorImpl cursor){
    assert isLatchOwner();
	original(cursor);
  }


  /**
   * 
   * Unregister a cursor with this bin. Caller has this bin already latched.
   * @param cursorCursor to unregister.
   */
  // line 39 "../../../../BIN.ump"
   public void removeCursor(CursorImpl cursor){
    assert isLatchOwner();
	original(cursor);
  }


  /**
   * 
   * Adjust any cursors that are referring to this BIN. This method is called during a split operation. "this" is the BIN being split. newSibling is the new BIN into which the entries from "this" between newSiblingLow and newSiblingHigh have been copied.
   * @param newSibling -the newSibling into which "this" has been split.
   * @param newSiblingLow,newSiblingHigh - the low and high entry of "this" that were moved into newSibling.
   */
  // line 49 "../../../../BIN.ump"
  public void adjustCursors(IN newSibling, int newSiblingLow, int newSiblingHigh){
    assert newSibling.isLatchOwner();
	assert this.isLatchOwner();
	original(newSibling, newSiblingLow, newSiblingHigh);
  }


  /**
   * 
   * Adjust cursors referring to this BIN following an insert.
   * @param insertIndex -The index of the new entry.
   */
  // line 59 "../../../../BIN.ump"
  public void adjustCursorsForInsert(int insertIndex){
    assert this.isLatchOwner();
	original(insertIndex);
  }


  /**
   * 
   * Adjust cursors referring to the given binIndex in this BIN following a mutation of the entry from an LN to a DIN. The entry was moved from a BIN to a newly created DBIN so each cursor must be added to the new DBIN.
   * @param binIndex -The index of the DIN (previously LN) entry in the BIN.
   * @param dupBin -The DBIN into which the LN entry was moved.
   * @param dupBinIndex -The index of the moved LN entry in the DBIN.
   * @param excludeCursor -The cursor being used for insertion and that should not be updated.
   */
  // line 71 "../../../../BIN.ump"
  public void adjustCursorsForMutation(int binIndex, DBIN dupBin, int dupBinIndex, CursorImpl excludeCursor){
    assert this.isLatchOwner();
	original(binIndex, dupBin, dupBinIndex, excludeCursor);
  }

  // line 76 "../../../../BIN.ump"
   protected void hook607(int validIndex, int numValidEntries, boolean needToLatch) throws DatabaseException{
    needToLatch = !isLatchOwner();
	try {
	    original(validIndex, numValidEntries, needToLatch);
	} finally {
	    if (needToLatch && isLatchOwner()) {
		releaseLatch();
	    }
	}
  }

  // line 87 "../../../../BIN.ump"
   protected void hook608(boolean needToLatch) throws DatabaseException{
    if (needToLatch) {
	    latch();
	}
	original(needToLatch);
  }

}