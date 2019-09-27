/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import com.sleepycat.je.latch.LatchNotHeldException;

// line 3 "../../../../TreeIterator.ump"
public class TreeIterator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeIterator()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../TreeIterator.ump"
   protected void hook755() throws DatabaseException{
    if (nextBin != null) {
	    nextBin.releaseLatch();
	}
	original();
  }

  // line 14 "../../../../TreeIterator.ump"
   protected void hook756() throws DatabaseException{
    if (nextBin != null) {
	    nextBin.latch();
	}
	original();
  }

  // line 21 "../../../../TreeIterator.ump"
   protected void hook757(){
    try {
	    if (nextBin != null) {
		nextBin.releaseLatch();
	    }
	} catch (LatchNotHeldException e) {
	}
	original();
  }

  // line 31 "../../../../TreeIterator.ump"
   protected void hook758() throws DatabaseException{
    nextBin.latch();
	original();
  }

  // line 36 "../../../../TreeIterator.ump"
   protected void hook759(){
    try {
	    if (nextBin != null) {
		nextBin.releaseLatch();
	    }
	} catch (LatchNotHeldException e) {
	}
	original();
  }

}