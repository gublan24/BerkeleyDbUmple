/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;

// line 3 "../../../../Evictor.ump"
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

  // line 6 "../../../../Evictor.ump"
   protected boolean hook386(DatabaseImpl db, boolean b2) throws DatabaseException{
    b2 = db.isDeleted();
	return original(db, b2);
  }

  // line 11 "../../../../Evictor.ump"
   protected boolean hook387(DatabaseImpl db, boolean b) throws DatabaseException{
    b |= db.isDeleteFinished();
	return original(db, b);
  }

}