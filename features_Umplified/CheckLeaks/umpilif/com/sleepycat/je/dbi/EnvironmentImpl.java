/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../EnvironmentImpl.ump"
// line 3 "../../../../EnvironmentImpl_inner.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Debugging support. Check for leaked locks and transactions.
   */
  // line 9 "../../../../EnvironmentImpl.ump"
   private void checkLeaks() throws DatabaseException{
    new EnvironmentImpl_checkLeaks(this).execute();
  }

  // line 13 "../../../../EnvironmentImpl.ump"
   protected void hook311() throws DatabaseException{
    
  }

  // line 16 "../../../../EnvironmentImpl.ump"
   protected void hook325(StringBuffer errors) throws DatabaseException{
    try {
	    checkLeaks();
	    this.hook311();
	} catch (DatabaseException DBE) {
	    errors.append("\nException performing validity checks: ");
	    errors.append(DBE.toString()).append("\n");
	}
	original(errors);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../EnvironmentImpl_inner.ump"
  public static class EnvironmentImpl_checkLeaks
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImpl_checkLeaks()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../EnvironmentImpl_inner.ump"
    public  EnvironmentImpl_checkLeaks(EnvironmentImpl _this){
      this._this=_this;
    }
  
    // line 9 "../../../../EnvironmentImpl_inner.ump"
    public void execute() throws DatabaseException{
      if (!_this.configManager.getBoolean(EnvironmentParams.ENV_CHECK_LEAKS)) {
            return;
          }
          clean=true;
          this.hook313();
          this.hook312();
          assert clean : "Lock, transaction, or latch left behind at environment close";
    }
  
    // line 24 "../../../../EnvironmentImpl_inner.ump"
     protected void hook312() throws DatabaseException{
      
    }
  
    // line 26 "../../../../EnvironmentImpl_inner.ump"
     protected void hook313() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 17 "../../../../EnvironmentImpl_inner.ump"
    protected EnvironmentImpl _this ;
  // line 18 "../../../../EnvironmentImpl_inner.ump"
    protected boolean clean ;
  // line 19 "../../../../EnvironmentImpl_inner.ump"
    protected StatsConfig statsConfig ;
  // line 20 "../../../../EnvironmentImpl_inner.ump"
    protected LockStats lockStat ;
  // line 21 "../../../../EnvironmentImpl_inner.ump"
    protected TransactionStats txnStat ;
  // line 22 "../../../../EnvironmentImpl_inner.ump"
    protected TransactionStats.Active[] active ;
  
    
  }
}