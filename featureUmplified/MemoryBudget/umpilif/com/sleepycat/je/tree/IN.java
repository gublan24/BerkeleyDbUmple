/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../IN.ump"
// line 3 "../../../../IN_inner.ump"
public class IN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public IN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Initialize the per-node memory count by computing its memory usage.
   */
  // line 13 "../../../../IN.ump"
   protected void initMemorySize(){
    inMemorySize = computeMemorySize();
  }

  // line 17 "../../../../IN.ump"
   public boolean verifyMemorySize(){
    long calcMemorySize = computeMemorySize();
	if (calcMemorySize != inMemorySize) {
	    String msg = "-Warning: Out of sync. " + "Should be " + calcMemorySize + " / actual: " + inMemorySize
		    + " node: " + getNodeId();
	    this.hook615(msg);
	    System.out.println(msg);
	    return false;
	} else {
	    return true;
	}
  }


  /**
   * 
   * Return the number of bytes used by this IN.  Latching is up to the caller.
   */
  // line 33 "../../../../IN.ump"
   public long getInMemorySize(){
    return inMemorySize;
  }

  // line 37 "../../../../IN.ump"
   private long getEntryInMemorySize(int idx){
    return getEntryInMemorySize(entryKeyVals[idx], entryTargets[idx]);
  }

  // line 41 "../../../../IN.ump"
   protected long getEntryInMemorySize(byte [] key, Node target){
    long ret = 0;
	if (key != null) {
	    ret += MemoryBudget.byteArraySize(key.length);
	}
	if (target != null) {
	    ret += target.getMemorySizeIncludedByParent();
	}
	return ret;
  }


  /**
   * 
   * Count up the memory usage attributable to this node alone. LNs children are counted by their BIN/DIN parents, but INs are not counted by their parents because they are resident on the IN list.
   */
  // line 55 "../../../../IN.ump"
   protected long computeMemorySize(){
    MemoryBudget mb = databaseImpl.getDbEnvironment().getMemoryBudget();
	long calcMemorySize = getMemoryOverhead(mb);
	calcMemorySize += computeLsnOverhead();
	for (int i = 0; i < nEntries; i++) {
	    calcMemorySize += getEntryInMemorySize(i);
	}
	if (provisionalObsolete != null) {
	    calcMemorySize += provisionalObsolete.size() * MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
	}
	return calcMemorySize;
  }

  // line 68 "../../../../IN.ump"
   public static  long computeOverhead(DbConfigManager configManager) throws DatabaseException{
    return MemoryBudget.IN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }

  // line 72 "../../../../IN.ump"
   private int computeLsnOverhead(){
    if (entryLsnLongArray == null) {
	    return MemoryBudget.byteArraySize(entryLsnByteArray.length);
	} else {
	    return MemoryBudget.BYTE_ARRAY_OVERHEAD + entryLsnLongArray.length * MemoryBudget.LONG_OVERHEAD;
	}
  }

  // line 80 "../../../../IN.ump"
   protected static  long computeArraysOverhead(DbConfigManager configManager) throws DatabaseException{
    int capacity = configManager.getInt(EnvironmentParams.NODE_MAX);
	return MemoryBudget.byteArraySize(capacity) + (capacity * (2 * MemoryBudget.ARRAY_ITEM_OVERHEAD));
  }

  // line 85 "../../../../IN.ump"
   protected long getMemoryOverhead(MemoryBudget mb){
    return mb.getINOverhead();
  }

  // line 89 "../../../../IN.ump"
   protected void updateMemorySize(ChildReference oldRef, ChildReference newRef){
    long delta = 0;
	if (newRef != null) {
	    delta = getEntryInMemorySize(newRef.getKey(), newRef.getTarget());
	}
	if (oldRef != null) {
	    delta -= getEntryInMemorySize(oldRef.getKey(), oldRef.getTarget());
	}
	changeMemorySize(delta);
  }

  // line 100 "../../../../IN.ump"
   protected void updateMemorySize(long oldSize, long newSize){
    long delta = newSize - oldSize;
	changeMemorySize(delta);
  }

  // line 105 "../../../../IN.ump"
  public void updateMemorySize(Node oldNode, Node newNode){
    long delta = 0;
	if (newNode != null) {
	    delta = newNode.getMemorySizeIncludedByParent();
	}
	if (oldNode != null) {
	    delta -= oldNode.getMemorySizeIncludedByParent();
	}
	changeMemorySize(delta);
  }

  // line 116 "../../../../IN.ump"
   private void changeMemorySize(long delta){
    inMemorySize += delta;
	if (inListResident) {
	    MemoryBudget mb = databaseImpl.getDbEnvironment().getMemoryBudget();
	    accumulatedDelta += delta;
	    if (accumulatedDelta > ACCUMULATED_LIMIT || accumulatedDelta < -ACCUMULATED_LIMIT) {
		mb.updateTreeMemoryUsage(accumulatedDelta);
		accumulatedDelta = 0;
	    }
	}
  }

  // line 128 "../../../../IN.ump"
   public int getAccumulatedDelta(){
    return accumulatedDelta;
  }

  // line 132 "../../../../IN.ump"
   public void setInListResident(boolean resident){
    inListResident = resident;
  }

  // line 136 "../../../../IN.ump"
   protected void hook615(String msg){
    
  }


  /**
   * 
   * Create a new IN.
   */
  // line 142 "../../../../IN.ump"
  public  IN(DatabaseImpl db, byte [] identifierKey, int capacity, int level){
    initMemorySize();
  }


  /**
   * 
   * Initialize IN object.
   */
  // line 149 "../../../../IN.ump"
   protected void init(DatabaseImpl db, byte [] identifierKey, int initialCapacity, int level){
    original(db, identifierKey, initialCapacity, level);
	inListResident = false;
  }

  // line 154 "../../../../IN.ump"
   protected void hook637() throws DatabaseException{
    initMemorySize();
	original();
  }


  /**
   * 
   * Initialize a node read in during recovery.
   */
  // line 162 "../../../../IN.ump"
   public void postRecoveryInit(DatabaseImpl db, long sourceLsn){
    original(db, sourceLsn);
	initMemorySize();
  }

  // line 167 "../../../../IN.ump"
   protected void hook638(Node node) throws DatabaseException,LogFileNotFoundException,Exception{
    updateMemorySize(null, node);
	original(node);
  }


  /**
   * 
   * Update the idx'th entry of this node. This flavor is used when the target LN is being modified, by an operation like a delete or update. We don't have to check whether the LSN has been nulled or not, because we know an LSN existed before. Also, the modification of the target is done in the caller, so instead of passing in the old and new nodes, we pass in the old and new node sizes.
   */
  // line 175 "../../../../IN.ump"
   public void updateEntry(int idx, long lsn, long oldLNSize, long newLNSize){
    updateMemorySize(oldLNSize, newLNSize);
	original(idx, lsn, oldLNSize, newLNSize);
  }


  /**
   * 
   * Add self and children to this in-memory IN list. Called by recovery, can run with no latching.
   */
  // line 183 "../../../../IN.ump"
  public void rebuildINList(INList inList) throws DatabaseException{
    initMemorySize();
	original(inList);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../IN_inner.ump"
  public static class IN_splitInternal
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_splitInternal()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../IN_inner.ump"
     protected void hook650() throws DatabaseException{
      newSize=_this.computeMemorySize();
          _this.updateMemorySize(oldMemorySize,newSize);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 11 "../../../../IN_inner.ump"
  public static class IN_deleteEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_deleteEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 13 "../../../../IN_inner.ump"
     protected void hook648() throws DatabaseException{
      _this.updateMemorySize(oldLSNArraySize,_this.computeLsnOverhead());
          original();
    }
  
    // line 17 "../../../../IN_inner.ump"
     protected void hook649() throws DatabaseException{
      _this.updateMemorySize(_this.getEntryInMemorySize(index),0);
          oldLSNArraySize=_this.computeLsnOverhead();
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 22 "../../../../IN_inner.ump"
  public static class IN_trackProvisionalObsolete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_trackProvisionalObsolete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 24 "../../../../IN_inner.ump"
    public void execute(){
      original();
          if (memDelta != 0) {
            _this.changeMemorySize(memDelta);
          }
    }
  
    // line 30 "../../../../IN_inner.ump"
     protected void hook651(){
      child.changeMemorySize(0 - childMemDelta);
          memDelta+=childMemDelta;
          original();
    }
  
    // line 35 "../../../../IN_inner.ump"
     protected void hook652(){
      childMemDelta=child.provisionalObsolete.size() * MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
          original();
    }
  
    // line 39 "../../../../IN_inner.ump"
     protected void hook653(){
      memDelta+=MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
          original();
    }
  
    // line 43 "../../../../IN_inner.ump"
     protected void hook654(){
      memDelta+=MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 47 "../../../../IN_inner.ump"
  public static class IN_insertEntry1
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_insertEntry1()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 49 "../../../../IN_inner.ump"
     protected void hook645() throws DatabaseException{
      _this.updateMemorySize(0,_this.getEntryInMemorySize(index));
          original();
    }
  
    // line 53 "../../../../IN_inner.ump"
     protected void hook646() throws DatabaseException{
      _this.changeMemorySize(_this.computeLsnOverhead() - oldSize);
          original();
    }
  
    // line 57 "../../../../IN_inner.ump"
     protected void hook647() throws DatabaseException{
      oldSize=_this.computeLsnOverhead();
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 61 "../../../../IN_inner.ump"
  public static class IN_updateEntryCompareKey
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntryCompareKey()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 63 "../../../../IN_inner.ump"
    public void execute(){
      oldSize=_this.getEntryInMemorySize(idx);
          original();
    }
  
    // line 67 "../../../../IN_inner.ump"
     protected void hook644(){
      newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 72 "../../../../IN_inner.ump"
  public static class IN_updateEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 74 "../../../../IN_inner.ump"
    public void execute(){
      oldSize=_this.getEntryInMemorySize(idx);
          original();
          newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 80 "../../../../IN_inner.ump"
  public static class IN_setLsn
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_setLsn()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 82 "../../../../IN_inner.ump"
    public void execute(){
      oldSize=_this.computeLsnOverhead();
          original();
    }
  
    // line 86 "../../../../IN_inner.ump"
     protected void hook639(){
      _this.changeMemorySize(_this.computeLsnOverhead() - oldSize);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 90 "../../../../IN_inner.ump"
  public static class IN_updateEntry2
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntry2()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 92 "../../../../IN_inner.ump"
    public void execute(){
      oldSize=_this.getEntryInMemorySize(idx);
          original();
    }
  
    // line 96 "../../../../IN_inner.ump"
     protected void hook642(){
      newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 101 "../../../../IN_inner.ump"
  public static class IN_flushProvisionalObsolete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_flushProvisionalObsolete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 103 "../../../../IN_inner.ump"
     protected void hook655() throws DatabaseException{
      _this.changeMemorySize(0 - memDelta);
          original();
    }
  
    // line 107 "../../../../IN_inner.ump"
     protected void hook656() throws DatabaseException{
      memDelta=_this.provisionalObsolete.size() * MemoryBudget.LONG_LIST_PER_ITEM_OVERHEAD;
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 111 "../../../../IN_inner.ump"
  public static class IN_updateEntry3
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_updateEntry3()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 113 "../../../../IN_inner.ump"
    public void execute(){
      oldSize=_this.getEntryInMemorySize(idx);
          original();
    }
  
    // line 117 "../../../../IN_inner.ump"
     protected void hook643(){
      newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 122 "../../../../IN_inner.ump"
  public static class IN_setEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_setEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 124 "../../../../IN_inner.ump"
    public void execute(){
      oldSize=_this.getEntryInMemorySize(idx);
          original();
    }
  
    // line 128 "../../../../IN_inner.ump"
     protected void hook640(){
      newSize=_this.getEntryInMemorySize(idx);
          _this.updateMemorySize(oldSize,newSize);
          original();
    }
  
    // line 133 "../../../../IN_inner.ump"
     protected void hook641(){
      oldSize=0;
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../IN.ump"
  private boolean inListResident ;
// line 7 "../../../../IN.ump"
  private int accumulatedDelta = 0 ;

  
}