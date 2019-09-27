/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../DbTree.ump"
public class DbTree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbTree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../DbTree.ump"
   protected void hook306(boolean allowEviction, CursorImpl idCursor) throws DatabaseException,UnsupportedEncodingException{
    idCursor.setAllowEviction(allowEviction);
	original(allowEviction, idCursor);
  }

  // line 13 "../../../../DbTree.ump"
   protected void hook307(boolean allowEviction, CursorImpl nameCursor) throws DatabaseException,UnsupportedEncodingException{
    nameCursor.setAllowEviction(allowEviction);
	original(allowEviction, nameCursor);
  }

  // line 19 "../../../../DbTree.ump"
   protected void hook308(boolean allowEviction, CursorImpl nameCursor) throws DatabaseException,UnsupportedEncodingException{
    nameCursor.setAllowEviction(allowEviction);
	original(allowEviction, nameCursor);
  }

  // line 24 "../../../../DbTree.ump"
   protected void hook309(boolean allowEviction, CursorImpl idCursor) throws DatabaseException{
    idCursor.setAllowEviction(allowEviction);
	original(allowEviction, idCursor);
  }

  // line 29 "../../../../DbTree.ump"
   protected void hook310(boolean allowEviction, CursorImpl idCursor) throws DatabaseException{
    idCursor.setAllowEviction(allowEviction);
	original(allowEviction, idCursor);
  }

}