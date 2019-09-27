/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.LatchNotHeldException;

// line 3 "../../../../CursorImpl.ump"
// line 3 "../../../../CursorImpl_inner.ump"
public class CursorImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CursorImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../CursorImpl.ump"
   public void releaseBIN() throws LatchNotHeldException{
    if (bin != null) {
	    bin.releaseLatchIfOwner();
	}
  }

  // line 14 "../../../../CursorImpl.ump"
   public void latchBINs() throws DatabaseException{
    latchBIN();
	latchDBIN();
  }

  // line 19 "../../../../CursorImpl.ump"
   public void releaseBINs() throws LatchNotHeldException{
    releaseBIN();
	releaseDBIN();
  }

  // line 24 "../../../../CursorImpl.ump"
   public void releaseDBIN() throws LatchNotHeldException{
    if (dupBin != null) {
	    dupBin.releaseLatchIfOwner();
	}
  }

  // line 30 "../../../../CursorImpl.ump"
   private boolean checkAlreadyLatched(boolean alreadyLatched){
    if (alreadyLatched) {
	    if (dupBin != null) {
		return dupBin.isLatchOwner();
	    } else if (bin != null) {
		return bin.isLatchOwner();
	    }
	}
	return true;
  }

  // line 41 "../../../../CursorImpl.ump"
   protected void hook206() throws DatabaseException,CloneNotSupportedException{
    latchBINs();
	original();
  }

  // line 46 "../../../../CursorImpl.ump"
   protected void hook207() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 51 "../../../../CursorImpl.ump"
   protected void hook208(BIN bin){
    assert bin.isLatchOwner();
	original(bin);
  }

  // line 56 "../../../../CursorImpl.ump"
   protected void hook209(BIN abin) throws DatabaseException{
    abin.releaseLatch();
	original(abin);
  }

  // line 61 "../../../../CursorImpl.ump"
   protected void hook210(DBIN abin) throws DatabaseException{
    abin.releaseLatch();
	original(abin);
  }

  // line 66 "../../../../CursorImpl.ump"
   protected void hook211() throws DatabaseException{
    dupBin.releaseLatch();
	original();
  }

  // line 71 "../../../../CursorImpl.ump"
   protected void hook212(LockType lockType) throws DatabaseException{
    latchBIN();
	try {
	    original(lockType);
	} finally {
	    releaseBIN();
	}
  }

  // line 81 "../../../../CursorImpl.ump"
   protected void hook213(boolean isDup, LN ln, LockResult lockResult, LockResult dclLockResult, DIN dupRoot) throws DatabaseException{
    try {
	    original(isDup, ln, lockResult, dclLockResult, dupRoot);
	} finally {
	    if (dupRoot != null) {
		dupRoot.releaseLatchIfOwner();
	    }
	}
  }

  // line 91 "../../../../CursorImpl.ump"
   protected void hook214() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 96 "../../../../CursorImpl.ump"
   protected void hook215() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 101 "../../../../CursorImpl.ump"
   protected void hook216() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 106 "../../../../CursorImpl.ump"
   protected void hook217() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 112 "../../../../CursorImpl.ump"
   protected void hook218(DatabaseEntry data, DatabaseEntry foundKey, DatabaseEntry foundData, boolean isDup) throws DatabaseException{
    try {
	    original(data, foundKey, foundData, isDup);
	} finally {
	    releaseBINs();
	}
  }

  // line 120 "../../../../CursorImpl.ump"
   protected void hook219() throws DatabaseException{
    latchBINs();
	original();
  }

  // line 125 "../../../../CursorImpl.ump"
   protected void hook220() throws DatabaseException{
    if (dupBin == null) {
	    latchBIN();
	} else {
	    latchDBIN();
	}
	original();
  }

  // line 135 "../../../../CursorImpl.ump"
   protected void hook221(DatabaseEntry foundKey, DatabaseEntry foundData, LockType lockType, boolean first) throws DatabaseException{
    assert checkAlreadyLatched(true) : dumpToString(true);
	try {
	    original(foundKey, foundData, lockType, first);
	} finally {
	    releaseBINs();
	}
  }

  // line 144 "../../../../CursorImpl.ump"
   protected void hook222() throws DatabaseException{
    latchBIN();
	original();
  }

  // line 149 "../../../../CursorImpl.ump"
   protected void hook223(LockType lockType) throws DatabaseException{
    try {
	    original(lockType);
	} finally {
	    releaseBINs();
	}
  }

  // line 157 "../../../../CursorImpl.ump"
   protected void hook224(boolean alreadyLatched) throws DatabaseException{
    assert checkAlreadyLatched(alreadyLatched) : dumpToString(true);
	original(alreadyLatched);
  }

  // line 162 "../../../../CursorImpl.ump"
   protected boolean hook225(boolean alreadyLatched) throws DatabaseException{
    assert checkAlreadyLatched(alreadyLatched) : dumpToString(true);
	if (!alreadyLatched) {
	    latchBIN();
	} else {
	    alreadyLatched = false;
	}
	return original(alreadyLatched);
  }

  // line 172 "../../../../CursorImpl.ump"
   protected boolean hook226(boolean alreadyLatched) throws DatabaseException{
    alreadyLatched = false;
	return original(alreadyLatched);
  }

  // line 177 "../../../../CursorImpl.ump"
   protected void hook227() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0;
	original();
  }

  // line 182 "../../../../CursorImpl.ump"
   protected void hook228() throws DatabaseException{
    latchBIN();
	original();
  }

  // line 187 "../../../../CursorImpl.ump"
   protected void hook229() throws DatabaseException{
    releaseBIN();
	original();
  }

  // line 192 "../../../../CursorImpl.ump"
   protected void hook230(boolean alreadyLatched) throws DatabaseException{
    alreadyLatched = true;
	original(alreadyLatched);
  }

  // line 197 "../../../../CursorImpl.ump"
   protected void hook231() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 0 : LatchSupport.latchesHeldToString();
	original();
  }

  // line 202 "../../../../CursorImpl.ump"
   private void flushBINToBeRemoved() throws DatabaseException{
    binToBeRemoved.latch();
	original();
  }

  // line 207 "../../../../CursorImpl.ump"
   protected void hook232() throws DatabaseException{
    binToBeRemoved.releaseLatch();
	original();
  }

  // line 212 "../../../../CursorImpl.ump"
   private void flushDBINToBeRemoved() throws DatabaseException{
    dupBinToBeRemoved.latch();
	original();
  }

  // line 217 "../../../../CursorImpl.ump"
   protected void hook233() throws DatabaseException{
    dupBinToBeRemoved.releaseLatch();
	original();
  }

  // line 222 "../../../../CursorImpl.ump"
   protected void hook234(boolean first, DIN duplicateRoot, IN in, boolean found) throws DatabaseException{
    try {
	    original(first, duplicateRoot, in, found);
	} catch (DatabaseException e) {
	    if (in != null) {
		in.releaseLatch();
	    }
	    throw e;
	}
  }

  // line 235 "../../../../CursorImpl.ump"
   protected void hook235(DatabaseEntry matchKey, DatabaseEntry matchData, SearchMode searchMode, LockType lockType, boolean foundSomething, boolean foundExactKey, boolean foundExactData, boolean foundLast, boolean exactSearch, BINBoundary binBoundary) throws DatabaseException{
    try {
	    original(matchKey, matchData, searchMode, lockType, foundSomething, foundExactKey, foundExactData,
		    foundLast, exactSearch, binBoundary);
	} catch (DatabaseException e) {
	    releaseBIN();
	    throw e;
	}
  }

  // line 245 "../../../../CursorImpl.ump"
   protected void hook236(DIN duplicateRoot) throws DatabaseException{
    duplicateRoot.latch();
	releaseBIN();
	original(duplicateRoot);
  }

  // line 251 "../../../../CursorImpl.ump"
   protected void hook237() throws DatabaseException{
    latchBINs();
	original();
  }

  // line 256 "../../../../CursorImpl.ump"
   protected void hook238() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 261 "../../../../CursorImpl.ump"
   protected void hook239(DIN dupRoot) throws DatabaseException{
    dupRoot.latch();
	latchDBIN();
	original(dupRoot);
  }

  // line 267 "../../../../CursorImpl.ump"
   protected void hook240() throws DatabaseException{
    latchBIN();
	original();
  }

  // line 272 "../../../../CursorImpl.ump"
   protected void hook241(DIN dupRoot) throws DatabaseException{
    dupRoot.releaseLatch();
	releaseBINs();
	original(dupRoot);
  }

  // line 278 "../../../../CursorImpl.ump"
   protected void hook242(boolean isDBINLatched, DIN dupRoot) throws DatabaseException{
    if (isDBINLatched) {
	    if (!dupRoot.latchNoWait()) {
		releaseDBIN();
		dupRoot.latch();
		latchDBIN();
	    }
	} else {
	    dupRoot.latch();
	}
	original(isDBINLatched, dupRoot);
  }

  // line 291 "../../../../CursorImpl.ump"
   protected void hook243() throws DatabaseException{
    assert bin.isLatchOwner();
	original();
  }

  // line 296 "../../../../CursorImpl.ump"
   protected void hook264(DIN dupRoot) throws DatabaseException{
    dupRoot.releaseLatch();
	original(dupRoot);
  }

  // line 301 "../../../../CursorImpl.ump"
   protected void hook265(DIN dupRoot) throws DatabaseException{
    dupRoot.latch();
	releaseBIN();
	original(dupRoot);
  }

  // line 307 "../../../../CursorImpl.ump"
   protected void hook266() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 312 "../../../../CursorImpl.ump"
   protected void hook267() throws DatabaseException{
    releaseBIN();
	original();
  }

  // line 317 "../../../../CursorImpl.ump"
   protected void hook268(DIN dupRoot) throws DatabaseException{
    dupRoot.releaseLatch();
	original(dupRoot);
  }

  // line 322 "../../../../CursorImpl.ump"
   protected void hook269() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 327 "../../../../CursorImpl.ump"
   protected void hook270() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 332 "../../../../CursorImpl.ump"
   protected void hook271() throws DatabaseException{
    releaseBINs();
	original();
  }

  // line 337 "../../../../CursorImpl.ump"
   protected void hook272() throws DatabaseException{
    assert checkAlreadyLatched(true) : dumpToString(true);
	original();
  }

  // line 342 "../../../../CursorImpl.ump"
   protected void hook273() throws DatabaseException{
    releaseBIN();
	original();
  }

  // line 347 "../../../../CursorImpl.ump"
   protected void hook274(IN in, DIN dupRoot) throws DatabaseException{
    dupRoot.latch();
	in.releaseLatch();
	original(in, dupRoot);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_latchBIN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_latchBIN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../CursorImpl_inner.ump"
     protected void hook244() throws DatabaseException{
      while (_this.bin != null) {
            original();
          }
          throw new ReturnObject(null);
    }
  
    // line 12 "../../../../CursorImpl_inner.ump"
     protected void hook245() throws DatabaseException{
      waitingOn=_this.bin;
          waitingOn.latch();
          if (_this.bin == waitingOn) {
            original();
          }
          waitingOn.releaseLatch();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 20 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_getNextDuplicate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_getNextDuplicate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 22 "../../../../CursorImpl_inner.ump"
     protected void hook250() throws DatabaseException{
      assert _this.checkAlreadyLatched(alreadyLatched) : _this.dumpToString(true);
          original();
    }
  
    // line 26 "../../../../CursorImpl_inner.ump"
     protected void hook251() throws DatabaseException{
      if (!alreadyLatched) {
            _this.latchDBIN();
          }
     else {
            alreadyLatched=false;
          }
          original();
    }
  
    // line 35 "../../../../CursorImpl_inner.ump"
     protected void hook252() throws DatabaseException{
      _this.releaseDBIN();
          original();
    }
  
    // line 39 "../../../../CursorImpl_inner.ump"
     protected void hook253() throws DatabaseException{
      assert LatchSupport.countLatchesHeld() == 0;
          original();
    }
  
    // line 43 "../../../../CursorImpl_inner.ump"
     protected void hook254() throws DatabaseException{
      assert (LatchSupport.countLatchesHeld() == 0);
          _this.dupBinToBeRemoved.latch();
          original();
    }
  
    // line 48 "../../../../CursorImpl_inner.ump"
     protected void hook255() throws DatabaseException{
      _this.dupBinToBeRemoved.releaseLatch();
          original();
    }
  
    // line 52 "../../../../CursorImpl_inner.ump"
     protected void hook256() throws DatabaseException{
      alreadyLatched=true;
          original();
    }
  
    // line 56 "../../../../CursorImpl_inner.ump"
     protected void hook257() throws DatabaseException{
      assert LatchSupport.countLatchesHeld() == 0;
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 60 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_lockNextKeyForInsert
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_lockNextKeyForInsert()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 62 "../../../../CursorImpl_inner.ump"
     protected void hook248() throws DatabaseException{
      latched=true;
          try {
            original();
          }
      finally {
            if (latched) {
              _this.releaseBINs();
            }
          }
    }
  
    // line 73 "../../../../CursorImpl_inner.ump"
     protected void hook249() throws DatabaseException{
      latched=false;
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 77 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_latchDBIN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_latchDBIN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 79 "../../../../CursorImpl_inner.ump"
     protected void hook246() throws DatabaseException{
      while (_this.dupBin != null) {
            original();
          }
          throw new ReturnObject(null);
    }
  
    // line 85 "../../../../CursorImpl_inner.ump"
     protected void hook247() throws DatabaseException{
      waitingOn=_this.dupBin;
          waitingOn.latch();
          if (_this.dupBin == waitingOn) {
            original();
          }
          waitingOn.releaseLatch();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 93 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_fetchCurrent
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_fetchCurrent()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 95 "../../../../CursorImpl_inner.ump"
     protected void hook258() throws DatabaseException{
      try {
            original();
          }
      finally {
            _this.releaseBINs();
          }
    }
  
    // line 103 "../../../../CursorImpl_inner.ump"
     protected void hook259() throws DatabaseException{
      assert _this.targetBin.isLatchOwner();
          original();
    }
  
    // line 107 "../../../../CursorImpl_inner.ump"
     protected void hook260() throws DatabaseException{
      try {
            original();
          }
     catch (      DatabaseException DE) {
            _this.targetBin.releaseLatchIfOwner();
            throw DE;
          }
    }
  
    // line 116 "../../../../CursorImpl_inner.ump"
     protected void hook261() throws DatabaseException{
      _this.targetBin.releaseLatchIfOwner();
          original();
    }
  
    // line 120 "../../../../CursorImpl_inner.ump"
     protected void hook262() throws DatabaseException{
      duplicateRoot.latch();
          _this.targetBin.releaseLatch();
          original();
    }
  
    // line 125 "../../../../CursorImpl_inner.ump"
     protected void hook263() throws DatabaseException{
      try {
            original();
          }
     catch (      DatabaseException DE) {
            _this.releaseBINs();
            throw DE;
          }
    }
  
  }
}