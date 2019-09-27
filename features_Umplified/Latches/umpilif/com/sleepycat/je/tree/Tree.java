/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import com.sleepycat.je.latch.SharedLatch;
import com.sleepycat.je.latch.LatchSupport;

// line 3 "../../../../Tree.ump"
// line 3 "../../../../Tree_inner.ump"
public class Tree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../Tree.ump"
   private void releaseNodeLadderLatches(ArrayList nodeLadder) throws DatabaseException{
    ListIterator iter = nodeLadder.listIterator(nodeLadder.size());
	while (iter.hasPrevious()) {
	    SplitInfo info3 = (SplitInfo) iter.previous();
	    info3.child.releaseLatch();
	}
  }


  /**
   * 
   * constructor helper
   */
  // line 21 "../../../../Tree.ump"
   private void init(DatabaseImpl database){
    rootLatch = LatchSupport.makeSharedLatch("RootLatch", (database != null) ? database.getDbEnvironment() : null);
	original(database);
  }


  /**
   * 
   * Set the root for the tree. Should only be called within the root latch.
   */
  // line 29 "../../../../Tree.ump"
   public void setRoot(ChildReference newRoot, boolean notLatched){
    assert (notLatched || rootLatch.isWriteLockedByCurrentThread());
	original(newRoot, notLatched);
  }

  // line 34 "../../../../Tree.ump"
   protected void hook670(WithRootLatched wrl) throws DatabaseException{
    try {
	    original(wrl);
	} finally {
	    rootLatch.release();
	}
  }

  // line 42 "../../../../Tree.ump"
   protected void hook671(WithRootLatched wrl) throws DatabaseException{
    try {
	    original(wrl);
	} finally {
	    rootLatch.release();
	}
  }

  // line 51 "../../../../Tree.ump"
   protected void hook672(byte [] idKey, UtilizationTracker tracker, IN subtreeRootIN, ArrayList nodeLadder, IN rootIN, boolean rootNeedsUpdating) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    rootLatch.acquireExclusive();
	try {
	    original(idKey, tracker, subtreeRootIN, nodeLadder, rootIN, rootNeedsUpdating);
	} finally {
	    releaseNodeLadderLatches(nodeLadder);
	    if (rootIN != null) {
		rootIN.releaseLatch();
	    }
	    rootLatch.release();
	}
  }


  /**
   * 
   * This entire tree is empty, clear the root and log a new MapLN
   * @return the rootIN that has been detached, or null if there hasn't beenany removal.
   */
  // line 68 "../../../../Tree.ump"
   private IN logTreeRemoval(IN rootIN, UtilizationTracker tracker) throws DatabaseException{
    assert rootLatch.isWriteLockedByCurrentThread();
	return original(rootIN, tracker);
  }

  // line 73 "../../../../Tree.ump"
   protected void hook673() throws DatabaseException{
    assert rootLatch.isWriteLockedByCurrentThread();
	original();
  }

  // line 79 "../../../../Tree.ump"
   protected IN hook674(byte [] idKey, byte [] mainKey, IN in, IN deletedSubtreeRoot) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    try {
	    deletedSubtreeRoot = original(idKey, mainKey, in, deletedSubtreeRoot);
	} finally {
	    in.releaseLatch();
	}
	return deletedSubtreeRoot;
  }

  // line 88 "../../../../Tree.ump"
   protected void hook675(DIN duplicateRoot) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    if (duplicateRoot != null) {
	    duplicateRoot.releaseLatch();
	}
	original(duplicateRoot);
  }

  // line 96 "../../../../Tree.ump"
   protected void hook676(ArrayList nodeLadder) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    releaseNodeLadderLatches(nodeLadder);
	original(nodeLadder);
  }

  // line 101 "../../../../Tree.ump"
   protected void hook677(DIN dupRoot) throws DatabaseException{
    assert dupRoot.isLatchOwner();
	original(dupRoot);
  }

  // line 106 "../../../../Tree.ump"
   protected void hook678(DIN dupRoot) throws DatabaseException{
    assert dupRoot.isLatchOwner();
	original(dupRoot);
  }

  // line 111 "../../../../Tree.ump"
   protected void hook679(IN child) throws DatabaseException{
    child.releaseLatch();
	original(child);
  }

  // line 116 "../../../../Tree.ump"
   protected void hook680(IN child) throws DatabaseException{
    assert child.isLatchOwner();
	original(child);
  }

  // line 121 "../../../../Tree.ump"
   protected void hook681(IN potentialParent) throws DatabaseException{
    potentialParent.releaseLatchIfOwner();
	original(potentialParent);
  }

  // line 126 "../../../../Tree.ump"
   protected void hook682(IN searchResult) throws DatabaseException{
    searchResult.releaseLatchIfOwner();
	original(searchResult);
  }

  // line 133 "../../../../Tree.ump"
   protected void hook683(TreeLocation location, byte [] mainKey, byte [] dupKey, LN ln, boolean splitsAllowed, boolean findDeletedEntries, boolean searchDupTree, boolean updateGeneration, boolean exactSearch, boolean indicateIfExact, Node childNode) throws DatabaseException{
    try {
	    original(location, mainKey, dupKey, ln, splitsAllowed, findDeletedEntries, searchDupTree, updateGeneration,
		    exactSearch, indicateIfExact, childNode);
	} catch (DatabaseException e) {
	    location.bin.releaseLatchIfOwner();
	    throw e;
	}
  }

  // line 143 "../../../../Tree.ump"
   protected void hook684(BIN oldBIN) throws DatabaseException{
    if (oldBIN != null) {
	    oldBIN.releaseLatch();
	}
	original(oldBIN);
  }

  // line 152 "../../../../Tree.ump"
   protected void hook685(TreeLocation location, byte [] dupKey, DIN dupRoot, LN ln, boolean findDeletedEntries, boolean indicateIfExact, boolean exactSearch, boolean splitsAllowed, boolean updateGeneration) throws DatabaseException{
    dupRoot.latch();
	try {
	    original(location, dupKey, dupRoot, ln, findDeletedEntries, indicateIfExact, exactSearch, splitsAllowed,
		    updateGeneration);
	} catch (DatabaseException e) {
	    dupRoot.releaseLatchIfOwner();
	    throw e;
	}
  }

  // line 164 "../../../../Tree.ump"
   protected void hook686(boolean traverseWithinDupTree, boolean forward, byte [] idKey, IN next, IN parent, IN nextIN) throws DatabaseException{
    try {
	    original(traverseWithinDupTree, forward, idKey, next, parent, nextIN);
	} catch (DatabaseException e) {
	    next.releaseLatchIfOwner();
	    if (parent != null) {
		parent.releaseLatchIfOwner();
	    }
	    if (nextIN != null) {
		nextIN.releaseLatchIfOwner();
	    }
	    throw e;
	}
  }

  // line 179 "../../../../Tree.ump"
   protected void hook687() throws DatabaseException{
    assert LatchSupport.countLatchesHeld() == 1 : LatchSupport.latchesHeldToString();
	original();
  }

  // line 185 "../../../../Tree.ump"
   protected void hook688(LogManager logManager, INList inMemoryINs, IN curRoot, long curRootLsn, long logLsn, IN newRoot) throws DatabaseException{
    try {
	    original(logManager, inMemoryINs, curRoot, curRootLsn, logLsn, newRoot);
	} finally {
	    curRoot.releaseLatch();
	}
  }

  // line 193 "../../../../Tree.ump"
   protected void hook689(IN curRoot) throws DatabaseException{
    curRoot.latch();
	original(curRoot);
  }

  // line 198 "../../../../Tree.ump"
   protected void hook690(IN parent) throws DatabaseException{
    assert parent.isLatchOwner();
	original(parent);
  }

  // line 203 "../../../../Tree.ump"
   protected void hook691(IN parent) throws DatabaseException{
    parent.releaseLatch();
	original(parent);
  }

  // line 208 "../../../../Tree.ump"
   protected void hook692(IN parent) throws DatabaseException,Throwable{
    parent.releaseLatch();
	original(parent);
  }

  // line 213 "../../../../Tree.ump"
   protected void hook693(IN child) throws DatabaseException,Throwable{
    child.releaseLatch();
	original(child);
  }

  // line 218 "../../../../Tree.ump"
   protected void hook694(IN parent, IN child) throws DatabaseException{
    if (child != null) {
	    child.releaseLatchIfOwner();
	}
	parent.releaseLatchIfOwner();
	original(parent, child);
  }

  // line 226 "../../../../Tree.ump"
   protected void hook695(IN parent) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    assert parent.isLatchOwner();
	original(parent);
  }

  // line 231 "../../../../Tree.ump"
   protected void hook696(SplitInfo info5) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    info5.child.releaseLatch();
	original(info5);
  }

  // line 237 "../../../../Tree.ump"
   protected void hook697(ArrayList nodeLadder) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    releaseNodeLadderLatches(nodeLadder);
	original(nodeLadder);
  }

  // line 243 "../../../../Tree.ump"
   protected void hook698(IN parent, byte [] key, long nid, boolean updateGeneration, int index, IN child) throws DatabaseException,SplitRequiredException{
    try {
	    original(parent, key, nid, updateGeneration, index, child);
	} catch (DatabaseException e) {
	    if (child != null) {
		child.releaseLatchIfOwner();
	    }
	    parent.releaseLatchIfOwner();
	    throw e;
	}
  }

  // line 255 "../../../../Tree.ump"
   protected void hook699(IN parent) throws DatabaseException,SplitRequiredException{
    assert parent.isLatchOwner();
	original(parent);
  }

  // line 260 "../../../../Tree.ump"
   protected void hook700(IN parent) throws DatabaseException,SplitRequiredException{
    parent.releaseLatch();
	original(parent);
  }

  // line 265 "../../../../Tree.ump"
   protected void hook701(boolean updateGeneration, IN rootIN) throws DatabaseException{
    try {
	    original(updateGeneration, rootIN);
	} finally {
	    rootLatch.release();
	}
  }

  // line 273 "../../../../Tree.ump"
   protected void hook702() throws DatabaseException{
    rootLatch.acquireShared();
	original();
  }

  // line 279 "../../../../Tree.ump"
   protected void hook703(LN ln, byte [] key, boolean allowDuplicates, CursorImpl cursor, LockResult lnLock, EnvironmentImpl env, LogManager logManager, INList inMemoryINs, BIN bin) throws DatabaseException{
    try {
	    original(ln, key, allowDuplicates, cursor, lnLock, env, logManager, inMemoryINs, bin);
	} finally {
	    cursor.releaseBIN();
	}
  }

  // line 289 "../../../../Tree.ump"
   protected void hook704(byte [] key, BIN bin, LN newLN, CursorImpl cursor, LockResult lnLock, boolean allowDuplicates, EnvironmentImpl env, int index, boolean successfulInsert, DIN dupRoot, Node n, long binNid, DBIN dupBin) throws DatabaseException{
    try {
	    original(key, bin, newLN, cursor, lnLock, allowDuplicates, env, index, successfulInsert, dupRoot, n, binNid,
		    dupBin);
	} finally {
	    if (dupBin != null) {
		dupBin.releaseLatchIfOwner();
	    }
	    if (dupRoot != null) {
		dupRoot.releaseLatchIfOwner();
	    }
	}
  }

  // line 303 "../../../../Tree.ump"
   protected void hook705(DIN dupRoot) throws DatabaseException{
    dupRoot.releaseLatch();
	original(dupRoot);
  }

  // line 309 "../../../../Tree.ump"
   protected void hook706(BIN bin, int index, DIN curRoot, LogManager logManager, INList inMemoryINs, byte [] rootIdKey, DIN newRoot, long curRootLsn, long logLsn) throws DatabaseException{
    try {
	    original(bin, index, curRoot, logManager, inMemoryINs, rootIdKey, newRoot, curRootLsn, logLsn);
	} finally {
	    curRoot.releaseLatch();
	}
  }

  // line 317 "../../../../Tree.ump"
   protected void hook707(DIN newRoot) throws DatabaseException{
    newRoot.latch();
	original(newRoot);
  }

  // line 324 "../../../../Tree.ump"
   protected void hook708(byte [] key, LogManager logManager, INList inMemoryINs, LN newLN, CursorImpl cursor, EnvironmentImpl env, DIN dupRoot, DBIN dupBin, BIN bin, int index, LN existingLN, byte [] newLNKey, Locker locker, DupCountLN dupCountLN, long firstDupCountLNLsn) throws DatabaseException{
    try {
	    original(key, logManager, inMemoryINs, newLN, cursor, env, dupRoot, dupBin, bin, index, existingLN,
		    newLNKey, locker, dupCountLN, firstDupCountLNLsn);
	} catch (DatabaseException e) {
	    dupBin.releaseLatchIfOwner();
	    dupRoot.releaseLatchIfOwner();
	    throw e;
	}
  }

  // line 335 "../../../../Tree.ump"
   protected void hook709(DBIN dupBin) throws DatabaseException{
    dupBin.latch();
	original(dupBin);
  }

  // line 340 "../../../../Tree.ump"
   protected void hook710(DIN dupRoot) throws DatabaseException{
    dupRoot.latch();
	original(dupRoot);
  }

  // line 346 "../../../../Tree.ump"
   protected BIN hook711(byte [] key, LogManager logManager, INList inMemoryINs, BIN bin, boolean rootLatchIsHeld) throws DatabaseException{
    try {
	    bin = original(key, logManager, inMemoryINs, bin, rootLatchIsHeld);
	} finally {
	    if (rootLatchIsHeld) {
		rootLatch.release();
	    }
	}
	return bin;
  }

  // line 357 "../../../../Tree.ump"
   protected void hook712(BIN bin) throws DatabaseException{
    bin.releaseLatch();
	original(bin);
  }

  // line 362 "../../../../Tree.ump"
   protected void hook713(INList inList, IN subtreeRoot, UtilizationTracker tracker) throws DatabaseException{
    inList.latchMajor();
	try {
	    original(inList, subtreeRoot, tracker);
	} finally {
	    inList.releaseMajorLatch();
	}
  }

  // line 371 "../../../../Tree.ump"
   protected void hook714(INList inMemoryList) throws DatabaseException{
    rootLatch.acquireShared();
	try {
	    original(inMemoryList);
	} finally {
	    rootLatch.release();
	}
  }

  // line 380 "../../../../Tree.ump"
   protected void hook715(int index) throws DatabaseException{
    rootLatch.acquireShared();
	try {
	    original(index);
	} finally {
	    rootLatch.release();
	}
  }

  // line 389 "../../../../Tree.ump"
   protected void hook728() throws DatabaseException{
    rootLatch.acquireExclusive();
	original();
  }

  // line 394 "../../../../Tree.ump"
   protected void hook729() throws DatabaseException{
    rootLatch.acquireShared();
	original();
  }

  // line 399 "../../../../Tree.ump"
   protected void hook730(IN in) throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    assert in.isLatchOwner();
	original(in);
  }

  // line 404 "../../../../Tree.ump"
   protected void hook731(TreeLocation location) throws DatabaseException{
    location.bin.releaseLatch();
	original(location);
  }

  // line 409 "../../../../Tree.ump"
   protected void hook732() throws DatabaseException{
    assert (LatchSupport.countLatchesHeld() == 1) : LatchSupport.latchesHeldToString();
	original();
  }

  // line 414 "../../../../Tree.ump"
   protected void hook733() throws DatabaseException{
    assert (LatchSupport.countLatchesHeld() == 0) : LatchSupport.latchesHeldToString();
	original();
  }

  // line 419 "../../../../Tree.ump"
   protected void hook734(IN next) throws DatabaseException{
    next.releaseLatch();
	original(next);
  }

  // line 424 "../../../../Tree.ump"
   protected void hook735(IN nextIN) throws DatabaseException{
    nextIN.latch();
	assert (LatchSupport.countLatchesHeld() == 2) : LatchSupport.latchesHeldToString();
	original(nextIN);
  }

  // line 430 "../../../../Tree.ump"
   protected void hook736(IN parent) throws DatabaseException{
    parent.releaseLatch();
	original(parent);
  }

  // line 435 "../../../../Tree.ump"
   protected void hook737(IN parent) throws DatabaseException{
    parent.releaseLatch();
	assert LatchSupport.countLatchesHeld() == 1 : LatchSupport.latchesHeldToString();
	original(parent);
  }

  // line 441 "../../../../Tree.ump"
   protected void hook738(IN parent) throws DatabaseException,SplitRequiredException{
    parent.releaseLatch();
	original(parent);
  }

  // line 446 "../../../../Tree.ump"
   protected void hook739(IN parent, IN child) throws DatabaseException,SplitRequiredException{
    child.releaseLatch();
	parent.releaseLatch();
	original(parent, child);
  }

  // line 452 "../../../../Tree.ump"
   protected void hook740(IN child) throws DatabaseException,SplitRequiredException{
    child.releaseLatch();
	original(child);
  }

  // line 457 "../../../../Tree.ump"
   protected void hook741(BIN bin) throws DatabaseException{
    assert bin.isLatchOwner();
	original(bin);
  }

  // line 462 "../../../../Tree.ump"
   protected void hook742(DBIN dupBin) throws DatabaseException{
    dupBin.releaseLatch();
	original(dupBin);
  }

  // line 467 "../../../../Tree.ump"
   protected void hook743(CursorImpl cursor) throws DatabaseException{
    cursor.releaseBIN();
	original(cursor);
  }

  // line 472 "../../../../Tree.ump"
   protected void hook744(DIN dupRoot) throws DatabaseException{
    dupRoot.latch();
	original(dupRoot);
  }

  // line 477 "../../../../Tree.ump"
   protected void hook745(CursorImpl cursor) throws DatabaseException{
    cursor.releaseBIN();
	original(cursor);
  }

  // line 482 "../../../../Tree.ump"
   protected void hook746(CursorImpl cursor) throws DatabaseException{
    cursor.latchBIN();
	original(cursor);
  }

  // line 487 "../../../../Tree.ump"
   protected void hook747(DBIN dupBin) throws DatabaseException{
    dupBin.releaseLatch();
	original(dupBin);
  }

  // line 492 "../../../../Tree.ump"
   protected void hook748() throws DatabaseException{
    rootLatch.acquireShared();
	original();
  }

  // line 497 "../../../../Tree.ump"
   protected void hook749() throws DatabaseException{
    rootLatch.release();
	original();
  }

  // line 502 "../../../../Tree.ump"
   protected void hook750(BIN bin) throws DatabaseException{
    bin.latch();
	original(bin);
  }

  // line 507 "../../../../Tree.ump"
   protected void hook751() throws DatabaseException{
    rootLatch.release();
	rootLatch.acquireExclusive();
	original();
  }

  // line 513 "../../../../Tree.ump"
   protected void hook752() throws DatabaseException{
    rootLatch.release();
	original();
  }

  // line 518 "../../../../Tree.ump"
   protected void hook753() throws DatabaseException{
    rootLatch.release();
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../Tree_inner.ump"
  public static class Tree_forceSplit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_forceSplit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Tree_inner.ump"
     protected void hook722() throws DatabaseException,SplitRequiredException{
      isRootLatched=false;
          original();
    }
  
    // line 10 "../../../../Tree_inner.ump"
     protected void hook723() throws DatabaseException,SplitRequiredException{
      if (origParent.isDbRoot()) {
            _this.rootLatch.acquireExclusive();
            isRootLatched=true;
          }
          origParent.latch();
          original();
    }
  
    // line 18 "../../../../Tree_inner.ump"
     protected void hook724() throws DatabaseException,SplitRequiredException{
      child.latch();
          original();
    }
  
    // line 22 "../../../../Tree_inner.ump"
     protected void hook725() throws DatabaseException,SplitRequiredException{
      child.releaseLatch();
          original();
    }
  
    // line 26 "../../../../Tree_inner.ump"
     protected void hook726() throws DatabaseException,SplitRequiredException{
      assert isRootLatched;
          original();
    }
  
    // line 30 "../../../../Tree_inner.ump"
     protected void hook727() throws DatabaseException,SplitRequiredException{
      if (!success) {
            if (child != null) {
              child.releaseLatchIfOwner();
            }
            origParent.releaseLatchIfOwner();
          }
          if (nodeLadder.size() > 0) {
            iter=nodeLadder.listIterator(nodeLadder.size());
            while (iter.hasPrevious()) {
              info2=(SplitInfo)iter.previous();
              info2.child.releaseLatchIfOwner();
            }
          }
          if (isRootLatched) {
            _this.rootLatch.release();
          }
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 49 "../../../../Tree_inner.ump"
  public static class Tree_searchSplitsAllowed
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_searchSplitsAllowed()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 51 "../../../../Tree_inner.ump"
     protected void hook716() throws DatabaseException{
      try {
            original();
          }
      finally {
            if (rootLatched) {
              _this.rootLatch.release();
            }
          }
    }
  
    // line 61 "../../../../Tree_inner.ump"
     protected void hook717() throws DatabaseException{
      _this.rootLatch.acquireShared();
          rootLatched=true;
          rootLatchedExclusive=false;
          original();
    }
  
    // line 67 "../../../../Tree_inner.ump"
     protected void hook718() throws DatabaseException{
      rootIN.latch();
          original();
    }
  
    // line 71 "../../../../Tree_inner.ump"
     protected void hook719() throws DatabaseException{
      rootLatched=true;
          _this.rootLatch.acquireExclusive();
          original();
    }
  
    // line 76 "../../../../Tree_inner.ump"
     protected void hook720() throws DatabaseException{
      _this.splitRoot();
          _this.rootLatch.release();
          rootLatched=false;
          original();
    }
  
    // line 82 "../../../../Tree_inner.ump"
     protected void hook721() throws DatabaseException{
      b=!rootLatchedExclusive;
          if (b) {
            rootIN=null;
            _this.rootLatch.release();
            _this.rootLatch.acquireExclusive();
            rootLatchedExclusive=true;
          }
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  // line 92 "../../../../Tree_inner.ump"
  public class RootChildReference
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RootChildReference()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 94 "../../../../Tree_inner.ump"
     protected void hook666() throws DatabaseException{
      if (getTarget() == null && !rootLatch.isWriteLockedByCurrentThread()) {
            rootLatch.release();
            rootLatch.acquireExclusive();
          }
          original();
    }
  
    // line 101 "../../../../Tree_inner.ump"
     protected void hook667(){
      assert rootLatch.isWriteLockedByCurrentThread();
          original();
    }
  
    // line 105 "../../../../Tree_inner.ump"
     protected void hook668(){
      assert rootLatch.isWriteLockedByCurrentThread();
          original();
    }
  
    // line 109 "../../../../Tree_inner.ump"
     protected void hook669(){
      assert rootLatch.isWriteLockedByCurrentThread();
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../Tree.ump"
  private SharedLatch rootLatch ;

  
}