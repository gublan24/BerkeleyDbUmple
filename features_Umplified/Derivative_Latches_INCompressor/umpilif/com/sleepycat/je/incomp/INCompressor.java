/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.incomp;
import com.sleepycat.je.latch.LatchSupport;

// line 3 "../../../../INCompressor.ump"
public class INCompressor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INCompressor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../INCompressor.ump"
   protected void hook393() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 12 "../../../../INCompressor.ump"
   protected void hook394(BIN foundBin) throws DatabaseException{
    foundBin.releaseLatch();
	original(foundBin);
  }

  // line 17 "../../../../INCompressor.ump"
   protected void hook395() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 23 "../../../../INCompressor.ump"
   protected void hook396(BIN bin, BINReference binRef, boolean empty, boolean requeued, byte [] dupKey, boolean isDBIN) throws DatabaseException{
    try {
	    original(bin, binRef, empty, requeued, dupKey, isDBIN);
	} finally {
	    bin.releaseLatch();
	}
  }

  // line 32 "../../../../INCompressor.ump"
   protected void hook397(byte [] mainKey, byte [] dupKey, Tree tree, DIN duplicateRoot, DBIN duplicateBin, BIN bin) throws DatabaseException{
    try {
	    original(mainKey, dupKey, tree, duplicateRoot, duplicateBin, bin);
	} catch (DatabaseException DBE) {
	    if (bin != null) {
		bin.releaseLatchIfOwner();
	    }
	    if (duplicateRoot != null) {
		duplicateRoot.releaseLatchIfOwner();
	    }
	    if (duplicateBin != null) {
		duplicateBin.releaseLatchIfOwner();
	    }
	    throw DBE;
	}
  }

  // line 49 "../../../../INCompressor.ump"
   protected void hook398(IN in) throws DatabaseException{
    assert in.isLatchOwner();
	original(in);
  }

  // line 54 "../../../../INCompressor.ump"
   protected void hook399(BINSearch binSearch) throws DatabaseException{
    if (binSearch.bin != null) {
	    binSearch.bin.releaseLatch();
	}
	original(binSearch);
  }

  // line 61 "../../../../INCompressor.ump"
   protected void hook400(BIN bin) throws DatabaseException{
    bin.releaseLatch();
	original(bin);
  }

  // line 66 "../../../../INCompressor.ump"
   protected void hook401(DIN duplicateRoot, BIN bin) throws DatabaseException{
    duplicateRoot.latch();
	bin.releaseLatch();
	original(duplicateRoot, bin);
  }

  // line 72 "../../../../INCompressor.ump"
   protected void hook402(BIN bin) throws DatabaseException{
    bin.releaseLatch();
	original(bin);
  }

}