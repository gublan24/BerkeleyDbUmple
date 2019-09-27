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

  // line 6 "../../../../DbTree.ump"
   protected void hook299(CursorImpl cursor) throws DatabaseException{
    cursor.releaseBINs();
	original(cursor);
  }

  // line 11 "../../../../DbTree.ump"
   protected void hook300(NameLockResult result) throws DatabaseException,UnsupportedEncodingException{
    result.nameCursor.releaseBIN();
	original(result);
  }

  // line 16 "../../../../DbTree.ump"
   protected void hook301(NameLockResult result) throws DatabaseException{
    result.nameCursor.releaseBIN();
	original(result);
  }

  // line 21 "../../../../DbTree.ump"
   protected void hook302(NameLockResult result) throws DatabaseException{
    result.nameCursor.releaseBIN();
	original(result);
  }

  // line 26 "../../../../DbTree.ump"
   protected void hook303(CursorImpl nameCursor) throws DatabaseException,UnsupportedEncodingException{
    nameCursor.releaseBIN();
	original(nameCursor);
  }

  // line 31 "../../../../DbTree.ump"
   protected void hook304(CursorImpl idCursor) throws DatabaseException{
    idCursor.releaseBIN();
	original(idCursor);
  }

  // line 36 "../../../../DbTree.ump"
   protected void hook305(CursorImpl cursor) throws DatabaseException{
    cursor.releaseBINs();
	original(cursor);
  }

}