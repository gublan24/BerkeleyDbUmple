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

  // line 6 "../../../../CursorImpl.ump"
   protected long hook283(LN ln, long newLNSize) throws DatabaseException{
    newLNSize = ln.getMemorySizeIncludedByParent();
	return original(ln, newLNSize);
  }

  // line 11 "../../../../CursorImpl.ump"
   protected long hook284(LN ln, long oldLNSize) throws DatabaseException{
    oldLNSize = ln.getMemorySizeIncludedByParent();
	return original(ln, oldLNSize);
  }

  // line 16 "../../../../CursorImpl.ump"
   protected long hook285(LN ln, long newLNSize) throws DatabaseException{
    newLNSize = ln.getMemorySizeIncludedByParent();
	return original(ln, newLNSize);
  }

  // line 21 "../../../../CursorImpl.ump"
   protected long hook286(LN ln, long oldLNSize) throws DatabaseException{
    oldLNSize = ln.getMemorySizeIncludedByParent();
	return original(ln, oldLNSize);
  }

}