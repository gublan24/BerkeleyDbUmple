/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../CursorImpl.ump"
public class CursorImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CursorImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Disables or enables eviction during cursor operations for an internal cursor. For example, a cursor used to implement eviction should not itself perform eviction. Eviction is enabled by default.
   */
  // line 11 "../../../../CursorImpl.ump"
   public void setAllowEviction(boolean allowed){
    allowEviction = allowed;
  }


  /**
   * 
   * Evict the LN node at the cursor position. This is used for internal databases only.
   */
  // line 18 "../../../../CursorImpl.ump"
   public void evict() throws DatabaseException{
    this.hook202();
	setTargetBin();
	targetBin.evictLN(targetIndex);
  }

  // line 24 "../../../../CursorImpl.ump"
   protected void hook202() throws DatabaseException{
    
  }

  // line 27 "../../../../CursorImpl.ump"
   protected void hook203() throws DatabaseException{
    
  }


  /**
   * 
   * Shallow copy. addCursor() is optionally called. Allows inheriting the BIN position from some other cursor.
   */
  // line 33 "../../../../CursorImpl.ump"
   public CursorImpl cloneCursor(boolean addCursor, CursorImpl usePosition) throws DatabaseException{
    CursorImpl result = original(addCursor, usePosition);
	if (allowEviction) {
	    this.hook203();
	}
	return result;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../CursorImpl.ump"
  private boolean allowEviction = true ;

  
}