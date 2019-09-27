/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../CursorImpl.ump"
// line 3 "../../../../CursorImpl_inner.ump"
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

  // line 8 "../../../../CursorImpl.ump"
   private void verifyCursor(BIN bin) throws DatabaseException{
    if (!bin.getCursorSet().contains(this)) {
	    throw new DatabaseException("BIN cursorSet is inconsistent.");
	}
  }

  // line 14 "../../../../CursorImpl.ump"
   protected void hook276() throws DatabaseException{
    if (DEBUG) {
	    verifyCursor(bin);
	}
	original();
  }

  // line 21 "../../../../CursorImpl.ump"
   protected void hook277() throws DatabaseException{
    if (DEBUG) {
	    verifyCursor(dupBin);
	}
	original();
  }

  // line 28 "../../../../CursorImpl.ump"
   protected void hook278() throws DatabaseException{
    if (DEBUG) {
	    if (bin != null) {
		verifyCursor(bin);
	    }
	    if (dupBin != null) {
		verifyCursor(dupBin);
	    }
	}
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_getNextDuplicate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_getNextDuplicate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../CursorImpl_inner.ump"
     protected void hook279() throws DatabaseException{
      if (_this.DEBUG) {
            _this.verifyCursor(_this.dupBin);
          }
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../CursorImpl.ump"
  private static final boolean DEBUG = false ;

  
}