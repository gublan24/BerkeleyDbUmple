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
  // line 10 "../../../../IN.ump"
   private void traceSplit(Level level, IN parent, IN newSibling, long parentLsn, long myNewLsn, long newSiblingLsn, int splitIndex, int idKeyIndex, int childIndex){
    new IN_traceSplit(this, level, parent, newSibling, parentLsn, myNewLsn, newSiblingLsn, splitIndex, idKeyIndex,
		childIndex).execute();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../IN_inner.ump"
  public static class IN_traceSplit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public IN_traceSplit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../IN_inner.ump"
    public  IN_traceSplit(IN _this, Level level, IN parent, IN newSibling, long parentLsn, long myNewLsn, long newSiblingLsn, int splitIndex, int idKeyIndex, int childIndex){
      this._this=_this;
          this.level=level;
          this.parent=parent;
          this.newSibling=newSibling;
          this.parentLsn=parentLsn;
          this.myNewLsn=myNewLsn;
          this.newSiblingLsn=newSiblingLsn;
          this.splitIndex=splitIndex;
          this.idKeyIndex=idKeyIndex;
          this.childIndex=childIndex;
    }
  
    // line 18 "../../../../IN_inner.ump"
    public void execute(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 19 "../../../../IN_inner.ump"
    protected IN _this ;
  // line 20 "../../../../IN_inner.ump"
    protected Level level ;
  // line 21 "../../../../IN_inner.ump"
    protected IN parent ;
  // line 22 "../../../../IN_inner.ump"
    protected IN newSibling ;
  // line 23 "../../../../IN_inner.ump"
    protected long parentLsn ;
  // line 24 "../../../../IN_inner.ump"
    protected long myNewLsn ;
  // line 25 "../../../../IN_inner.ump"
    protected long newSiblingLsn ;
  // line 26 "../../../../IN_inner.ump"
    protected int splitIndex ;
  // line 27 "../../../../IN_inner.ump"
    protected int idKeyIndex ;
  // line 28 "../../../../IN_inner.ump"
    protected int childIndex ;
  // line 29 "../../../../IN_inner.ump"
    protected Logger logger ;
  // line 30 "../../../../IN_inner.ump"
    protected StringBuffer sb ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 32 "../../../../IN_inner.ump"
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
  
    // line 34 "../../../../IN_inner.ump"
     protected void hook617() throws DatabaseException{
      _this.traceSplit(Level.FINE,parent,newSibling,parentLsn,myNewLsn,newSiblingLsn,splitIndex,idKeyIndex,childIndex);
          original();
    }
  
  }
}