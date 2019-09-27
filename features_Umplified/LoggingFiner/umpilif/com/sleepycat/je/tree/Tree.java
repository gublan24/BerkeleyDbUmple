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
  // line 9 "../../../../Tree.ump"
   private void traceInsert(Level level, EnvironmentImpl env, BIN insertingBin, LN ln, long lnLsn, int index){
    new Tree_traceInsert(this, level, env, insertingBin, ln, lnLsn, index).execute();
  }


  /**
   * 
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 17 "../../../../Tree.ump"
   private void traceInsertDuplicate(Level level, EnvironmentImpl env, BIN insertingDBin, LN ln, long lnLsn, long binNid){
    new Tree_traceInsertDuplicate(this, level, env, insertingDBin, ln, lnLsn, binNid).execute();
  }

  // line 21 "../../../../Tree.ump"
   protected void hook657(LN ln, EnvironmentImpl env, BIN bin, int index, long newLsn) throws DatabaseException{
    traceInsert(Level.FINER, env, bin, ln, newLsn, index);
	original(ln, env, bin, index, newLsn);
  }

  // line 26 "../../../../Tree.ump"
   protected void hook658(LN ln, EnvironmentImpl env, BIN bin, int index, long newLsn) throws DatabaseException{
    traceInsert(Level.FINER, env, bin, ln, newLsn, index);
	original(ln, env, bin, index, newLsn);
  }

  // line 31 "../../../../Tree.ump"
   protected void hook659(LN newLN, long binNid, DBIN dupBin, long newLsn) throws DatabaseException{
    traceInsertDuplicate(Level.FINER, database.getDbEnvironment(), dupBin, newLN, newLsn, binNid);
	original(newLN, binNid, dupBin, newLsn);
  }

  // line 36 "../../../../Tree.ump"
   protected void hook660(LN newLN, long binNid, DBIN dupBin, long newLsn) throws DatabaseException{
    traceInsertDuplicate(Level.FINER, database.getDbEnvironment(), dupBin, newLN, newLsn, binNid);
	original(newLN, binNid, dupBin, newLsn);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../Tree_inner.ump"
  public static class Tree_traceInsertDuplicate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceInsertDuplicate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Tree_inner.ump"
    public  Tree_traceInsertDuplicate(Tree _this, Level level, EnvironmentImpl env, BIN insertingDBin, LN ln, long lnLsn, long binNid){
      this._this=_this;
          this.level=level;
          this.env=env;
          this.insertingDBin=insertingDBin;
          this.ln=ln;
          this.lnLsn=lnLsn;
          this.binNid=binNid;
    }
  
    // line 15 "../../../../Tree_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 16 "../../../../Tree_inner.ump"
    protected Tree _this ;
  // line 17 "../../../../Tree_inner.ump"
    protected Level level ;
  // line 18 "../../../../Tree_inner.ump"
    protected EnvironmentImpl env ;
  // line 19 "../../../../Tree_inner.ump"
    protected BIN insertingDBin ;
  // line 20 "../../../../Tree_inner.ump"
    protected LN ln ;
  // line 21 "../../../../Tree_inner.ump"
    protected long lnLsn ;
  // line 22 "../../../../Tree_inner.ump"
    protected long binNid ;
  // line 23 "../../../../Tree_inner.ump"
    protected Logger logger ;
  // line 24 "../../../../Tree_inner.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 26 "../../../../Tree_inner.ump"
  public static class Tree_traceInsert
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceInsert()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 28 "../../../../Tree_inner.ump"
    public  Tree_traceInsert(Tree _this, Level level, EnvironmentImpl env, BIN insertingBin, LN ln, long lnLsn, int index){
      this._this=_this;
          this.level=level;
          this.env=env;
          this.insertingBin=insertingBin;
          this.ln=ln;
          this.lnLsn=lnLsn;
          this.index=index;
    }
  
    // line 37 "../../../../Tree_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 38 "../../../../Tree_inner.ump"
    protected Tree _this ;
  // line 39 "../../../../Tree_inner.ump"
    protected Level level ;
  // line 40 "../../../../Tree_inner.ump"
    protected EnvironmentImpl env ;
  // line 41 "../../../../Tree_inner.ump"
    protected BIN insertingBin ;
  // line 42 "../../../../Tree_inner.ump"
    protected LN ln ;
  // line 43 "../../../../Tree_inner.ump"
    protected long lnLsn ;
  // line 44 "../../../../Tree_inner.ump"
    protected int index ;
  // line 45 "../../../../Tree_inner.ump"
    protected Logger logger ;
  // line 46 "../../../../Tree_inner.ump"
    protected StringBuffer sb ;
  
    
  }
}