/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;

// line 3 "../../../../Evictor.ump"
// line 3 "../../../../Evictor_inner.ump"
public class Evictor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Do a check on whether synchronous eviction is needed.
   */
  // line 9 "../../../../Evictor.ump"
   public void doCriticalEviction() throws DatabaseException{
    new Evictor_doCriticalEviction(this).execute();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.evictor;
  
  @MethodObject
  // line 4 "../../../../Evictor_inner.ump"
  public static class Evictor_doCriticalEviction
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_doCriticalEviction()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
    public  Evictor_doCriticalEviction(Evictor _this){
      this._this=_this;
    }
  
    // line 9 "../../../../Evictor_inner.ump"
    public void execute() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 10 "../../../../Evictor_inner.ump"
    protected Evictor _this ;
  // line 11 "../../../../Evictor_inner.ump"
    protected MemoryBudget mb ;
  // line 12 "../../../../Evictor_inner.ump"
    protected long currentUsage ;
  // line 13 "../../../../Evictor_inner.ump"
    protected long maxMem ;
  // line 14 "../../../../Evictor_inner.ump"
    protected long over ;
  
    
  }
}