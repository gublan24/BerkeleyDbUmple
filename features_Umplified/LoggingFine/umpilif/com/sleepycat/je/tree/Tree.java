/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

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


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 10 "../../../../Tree.ump"
   private void traceSplitRoot(Level level, String splitType, IN newRoot, long newRootLsn, IN oldRoot, long oldRootLsn){
    new Tree_traceSplitRoot(this, level, splitType, newRoot, newRootLsn, oldRoot, oldRootLsn).execute();
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 18 "../../../../Tree.ump"
   private void traceMutate(Level level, BIN theBin, LN existingLn, LN newLn, long newLsn, DupCountLN dupCountLN, long dupRootLsn, DIN dupRoot, long ddinLsn, DBIN dupBin, long dbinLsn){
    new Tree_traceMutate(this, level, theBin, existingLn, newLn, newLsn, dupCountLN, dupRootLsn, dupRoot, ddinLsn,
		dupBin, dbinLsn).execute();
  }

  // line 23 "../../../../Tree.ump"
   protected void hook661() throws DatabaseException,NodeNotEmptyException,CursorsExistException{
    RecoveryManager.traceRootDeletion(Level.FINE, database);
	original();
  }

  // line 28 "../../../../Tree.ump"
   protected void hook662(IN curRoot, long curRootLsn, long logLsn, IN newRoot) throws DatabaseException{
    traceSplitRoot(Level.FINE, TRACE_ROOT_SPLIT, newRoot, logLsn, curRoot, curRootLsn);
	original(curRoot, curRootLsn, logLsn, newRoot);
  }

  // line 33 "../../../../Tree.ump"
   protected void hook663(DIN curRoot, DIN newRoot, long curRootLsn, long logLsn) throws DatabaseException{
    traceSplitRoot(Level.FINE, TRACE_DUP_ROOT_SPLIT, newRoot, logLsn, curRoot, curRootLsn);
	original(curRoot, newRoot, curRootLsn, logLsn);
  }

  // line 39 "../../../../Tree.ump"
   protected void hook664(LN newLN, DIN dupRoot, DBIN dupBin, BIN bin, LN existingLN, DupCountLN dupCountLN, long dbinLsn, long dinLsn, long dupCountLsn, long newLsn) throws DatabaseException{
    traceMutate(Level.FINE, bin, existingLN, newLN, newLsn, dupCountLN, dupCountLsn, dupRoot, dinLsn, dupBin,
		dbinLsn);
	original(newLN, dupRoot, dupBin, bin, existingLN, dupCountLN, dbinLsn, dinLsn, dupCountLsn, newLsn);
  }

  // line 45 "../../../../Tree.ump"
   protected void hook665(IN subtreeRoot) throws DatabaseException{
    Tracer.trace(Level.FINE, database.getDbEnvironment(),
		"SubtreeRemoval: subtreeRoot = " + subtreeRoot.getNodeId());
	original(subtreeRoot);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../Tree_inner.ump"
  public static class Tree_traceMutate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceMutate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Tree_inner.ump"
    public  Tree_traceMutate(Tree _this, Level level, BIN theBin, LN existingLn, LN newLn, long newLsn, DupCountLN dupCountLN, long dupRootLsn, DIN dupRoot, long ddinLsn, DBIN dupBin, long dbinLsn){
      this._this=_this;
          this.level=level;
          this.theBin=theBin;
          this.existingLn=existingLn;
          this.newLn=newLn;
          this.newLsn=newLsn;
          this.dupCountLN=dupCountLN;
          this.dupRootLsn=dupRootLsn;
          this.dupRoot=dupRoot;
          this.ddinLsn=ddinLsn;
          this.dupBin=dupBin;
          this.dbinLsn=dbinLsn;
    }
  
    // line 20 "../../../../Tree_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 21 "../../../../Tree_inner.ump"
    protected Tree _this ;
  // line 22 "../../../../Tree_inner.ump"
    protected Level level ;
  // line 23 "../../../../Tree_inner.ump"
    protected BIN theBin ;
  // line 24 "../../../../Tree_inner.ump"
    protected LN existingLn ;
  // line 25 "../../../../Tree_inner.ump"
    protected LN newLn ;
  // line 26 "../../../../Tree_inner.ump"
    protected long newLsn ;
  // line 27 "../../../../Tree_inner.ump"
    protected DupCountLN dupCountLN ;
  // line 28 "../../../../Tree_inner.ump"
    protected long dupRootLsn ;
  // line 29 "../../../../Tree_inner.ump"
    protected DIN dupRoot ;
  // line 30 "../../../../Tree_inner.ump"
    protected long ddinLsn ;
  // line 31 "../../../../Tree_inner.ump"
    protected DBIN dupBin ;
  // line 32 "../../../../Tree_inner.ump"
    protected long dbinLsn ;
  // line 33 "../../../../Tree_inner.ump"
    protected Logger logger ;
  // line 34 "../../../../Tree_inner.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 36 "../../../../Tree_inner.ump"
  public static class Tree_traceSplitRoot
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceSplitRoot()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 38 "../../../../Tree_inner.ump"
    public  Tree_traceSplitRoot(Tree _this, Level level, String splitType, IN newRoot, long newRootLsn, IN oldRoot, long oldRootLsn){
      this._this=_this;
          this.level=level;
          this.splitType=splitType;
          this.newRoot=newRoot;
          this.newRootLsn=newRootLsn;
          this.oldRoot=oldRoot;
          this.oldRootLsn=oldRootLsn;
    }
  
    // line 47 "../../../../Tree_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 48 "../../../../Tree_inner.ump"
    protected Tree _this ;
  // line 49 "../../../../Tree_inner.ump"
    protected Level level ;
  // line 50 "../../../../Tree_inner.ump"
    protected String splitType ;
  // line 51 "../../../../Tree_inner.ump"
    protected IN newRoot ;
  // line 52 "../../../../Tree_inner.ump"
    protected long newRootLsn ;
  // line 53 "../../../../Tree_inner.ump"
    protected IN oldRoot ;
  // line 54 "../../../../Tree_inner.ump"
    protected long oldRootLsn ;
  // line 55 "../../../../Tree_inner.ump"
    protected Logger logger ;
  // line 56 "../../../../Tree_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}