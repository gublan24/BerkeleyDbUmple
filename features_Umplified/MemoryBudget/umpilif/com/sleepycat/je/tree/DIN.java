/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../DIN.ump"
// line 3 "../../../../DIN_inner.ump"
public class DIN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DIN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Count up the memory usage attributable to this node alone. LNs children are counted by their BIN/DIN parents, but INs are not counted by  their parents because they are resident on the IN list.
   */
  // line 9 "../../../../DIN.ump"
   protected long computeMemorySize(){
    long size = super.computeMemorySize();
	if (dupCountLNRef != null) {
	    size += getEntryInMemorySize(dupCountLNRef.getKey(), dupCountLNRef.getTarget());
	}
	return size;
  }

  // line 17 "../../../../DIN.ump"
   public static  long computeOverhead(DbConfigManager configManager) throws DatabaseException{
    return MemoryBudget.DIN_FIXED_OVERHEAD + IN.computeArraysOverhead(configManager);
  }

  // line 21 "../../../../DIN.ump"
   protected long getMemoryOverhead(MemoryBudget mb){
    return mb.getDINOverhead();
  }


  /**
   * 
   * Create a new DIN.
   */
  // line 28 "../../../../DIN.ump"
  public  DIN(DatabaseImpl db, byte [] identifierKey, int capacity, byte [] dupKey, ChildReference dupCountLNRef, int level){
    initMemorySize();
  }


  /**
   * 
   * Assign the Dup Count LN.
   */
  // line 35 "../../../../DIN.ump"
  public void setDupCountLN(ChildReference dupCountLNRef){
    updateMemorySize(this.dupCountLNRef, dupCountLNRef);
	original(dupCountLNRef);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../DIN_inner.ump"
  public static class DIN_updateDupCountLNRefAndNullTarget
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DIN_updateDupCountLNRefAndNullTarget()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DIN_inner.ump"
    public void execute(){
      original();
          newSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
          _this.updateMemorySize(oldSize,newSize);
    }
  
    // line 11 "../../../../DIN_inner.ump"
     protected void hook614(){
      oldSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 15 "../../../../DIN_inner.ump"
  public static class DIN_updateDupCountLN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DIN_updateDupCountLN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 17 "../../../../DIN_inner.ump"
    public void execute(){
      oldSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
          original();
          newSize=_this.getEntryInMemorySize(_this.dupCountLNRef.getKey(),_this.dupCountLNRef.getTarget());
          _this.updateMemorySize(oldSize,newSize);
    }
  
  }
}