/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.LatchNotHeldException;
import com.sleepycat.je.latch.Latch;

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
   * Latch this node and set the generation.
   */
  // line 14 "../../../../IN.ump"
   public void latch() throws DatabaseException{
    latch(true);
  }


  /**
   * 
   * Latch this node if it is not latched by another thread, and set the generation if the latch succeeds.
   */
  // line 21 "../../../../IN.ump"
   public boolean latchNoWait() throws DatabaseException{
    return latchNoWait(true);
  }


  /**
   * 
   * Release the latch on this node.
   */
  // line 28 "../../../../IN.ump"
   public void releaseLatch() throws LatchNotHeldException{
    latch.release();
  }


  /**
   * 
   * Release the latch on this node.
   */
  // line 35 "../../../../IN.ump"
   public void releaseLatchIfOwner() throws LatchNotHeldException{
    latch.releaseIfOwner();
  }


  /**
   * 
   * @return true if this thread holds the IN's latch
   */
  // line 42 "../../../../IN.ump"
   public boolean isLatchOwner(){
    return latch.isOwner();
  }

  // line 46 "../../../../IN.ump"
   protected void hook618(EnvironmentImpl env){
    latch = LatchSupport.makeLatch(shortClassName() + getNodeId(), env);
	original(env);
  }


  /**
   * 
   * Latch this node, optionally setting the generation.
   */
  // line 54 "../../../../IN.ump"
   public void latch(boolean updateGeneration) throws DatabaseException{
    original(updateGeneration);
	latch.acquire();
  }

  // line 59 "../../../../IN.ump"
   protected void hook619(boolean updateGeneration) throws DatabaseException{
    if (latch.acquireNoWait()) {
	    original(updateGeneration);
	} else {
	    throw new ReturnBoolean(false);
	}
  }

  // line 80 "../../../../IN.ump"
   protected void hook620() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 85 "../../../../IN.ump"
   protected void hook621() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 90 "../../../../IN.ump"
   protected void hook622() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 95 "../../../../IN.ump"
   protected void hook623() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 100 "../../../../IN.ump"
   protected void hook624() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 105 "../../../../IN.ump"
   protected void hook625(Node child) throws DatabaseException{
    ((IN) child).releaseLatch();
	original(child);
  }

  // line 110 "../../../../IN.ump"
   protected void hook626() throws DatabaseException{
    releaseLatch();
	original();
  }

  // line 115 "../../../../IN.ump"
   protected void hook627() throws DatabaseException{
    releaseLatch();
	original();
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 123 "../../../../IN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    original(itemBuffer, entryTypeVersion);
	latch.setName(shortClassName() + getNodeId());
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
     protected void hook630() throws DatabaseException{
      try {
            original();
          }
      finally {
            newSibling.releaseLatch();
          }
    }
  
    // line 14 "../../../../IN_inner.ump"
     protected void hook631() throws DatabaseException{
      newSibling.latch();
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 18 "../../../../IN_inner.ump"
  public static class IN_isValidForDelete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_isValidForDelete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 20 "../../../../IN_inner.ump"
     protected void hook634() throws DatabaseException{
      needToLatch=!_this.isLatchOwner();
          try {
            original();
          }
      finally {
            if (needToLatch) {
              _this.releaseLatchIfOwner();
            }
          }
    }
  
    // line 31 "../../../../IN_inner.ump"
     protected void hook635() throws DatabaseException{
      if (needToLatch) {
            _this.latch();
          }
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 37 "../../../../IN_inner.ump"
  public static class IN_validateSubtreeBeforeDelete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_validateSubtreeBeforeDelete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 39 "../../../../IN_inner.ump"
     protected void hook628() throws DatabaseException{
      needToLatch=!_this.isLatchOwner();
          try {
            original();
          }
      finally {
            if (needToLatch) {
              _this.releaseLatchIfOwner();
            }
          }
    }
  
    // line 50 "../../../../IN_inner.ump"
     protected void hook629() throws DatabaseException{
      if (needToLatch) {
            _this.latch();
          }
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 56 "../../../../IN_inner.ump"
  public static class IN_verify
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_verify()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 58 "../../../../IN_inner.ump"
    public void execute() throws DatabaseException{
      unlatchThis=false;
          original();
    }
  
    // line 62 "../../../../IN_inner.ump"
     protected void hook632() throws DatabaseException{
      if (!_this.isLatchOwner()) {
            _this.latch();
            unlatchThis=true;
          }
          original();
    }
  
    // line 69 "../../../../IN_inner.ump"
     protected void hook633() throws DatabaseException{
      if (unlatchThis) {
            _this.releaseLatch();
          }
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../IN.ump"
  private Latch latch ;

// line 70 "../../../../IN.ump"
  void findParent (Tree.SearchType searchType, long targetNodeId, boolean targetContainsDuplicates,
	    boolean targetIsRoot, byte[] targetMainTreeKey, byte[] targetDupTreeKey, SearchResult result,
	    boolean requireExactMatch, boolean updateGeneration, int targetLevel, List trackingList, boolean doFetch)
	    throws DatabaseException 
  {
    assert isLatchOwner();
	original(searchType, targetNodeId, targetContainsDuplicates, targetIsRoot, targetMainTreeKey, targetDupTreeKey,
		result, requireExactMatch, updateGeneration, targetLevel, trackingList, doFetch);
  }

  
}