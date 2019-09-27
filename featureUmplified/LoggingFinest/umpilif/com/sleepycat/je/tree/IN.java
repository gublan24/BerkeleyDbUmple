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
   * Send trace messages to the java.util.logger. Don't rely on the logger alone to conditionalize whether we send this message, we don't even want to construct the message if the level is not enabled.
   */
  // line 9 "../../../../IN.ump"
   private void traceDelete(Level level, int index){
    new IN_traceDelete(this, level, index).execute();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../IN_inner.ump"
  public static class IN_traceDelete
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_traceDelete()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../IN_inner.ump"
    public  IN_traceDelete(IN _this, Level level, int index){
      this._this=_this;
          this.level=level;
          this.index=index;
    }
  
    // line 11 "../../../../IN_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 12 "../../../../IN_inner.ump"
    protected IN _this ;
  // line 13 "../../../../IN_inner.ump"
    protected Level level ;
  // line 14 "../../../../IN_inner.ump"
    protected int index ;
  // line 15 "../../../../IN_inner.ump"
    protected Logger logger ;
  // line 16 "../../../../IN_inner.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 18 "../../../../IN_inner.ump"
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
  
    // line 20 "../../../../IN_inner.ump"
     protected void hook616() throws DatabaseException{
      _this.traceDelete(Level.FINEST,index);
          original();
    }
  
  }
}