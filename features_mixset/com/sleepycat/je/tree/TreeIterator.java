/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import java.util.NoSuchElementException;
import java.util.Iterator;
import com.sleepycat.je.latch.LatchNotHeldException;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../TreeIterator.ump"
// line 3 "../../../../Latches_TreeIterator.ump"
public class TreeIterator implements Iterator
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

  // line 17 "../../../../TreeIterator.ump"
   public  TreeIterator(Tree tree) throws DatabaseException{
    nextBin = (BIN) tree.getFirstNode();
	Label755:
if (nextBin != null) {
	    nextBin.releaseLatch();
	}
	//original();
           ;  //this.hook755();
	index = -1;
	this.tree = tree;
  }

  // line 24 "../../../../TreeIterator.ump"
   public boolean hasNext(){
    boolean ret = false;
	try {
	    Label756:
if (nextBin != null) {
	    nextBin.latch();
	}
	//original();
           ;  //this.hook756();
	    advance();
	    ret = (nextBin != null) && (index < nextBin.getNEntries());
	} catch (DatabaseException e) {
	} finally {
	    Label757:
try {
	    if (nextBin != null) {
		nextBin.releaseLatch();
	    }
	} catch (LatchNotHeldException e) {
	}
	//original();
           ;  //this.hook757();
	}
	return ret;
  }

  // line 37 "../../../../TreeIterator.ump"
   public Object next(){
    Object ret = null;
    try {
      if (nextBin == null) {
        throw new NoSuchElementException();
      }
      Label758:
nextBin.latch();
	//original();
 ; // this.hook758();
      ret = nextBin.getKey(index);
    } 
    catch (DatabaseException e) {

    } 
    finally {
      Label759:
try {
	    if (nextBin != null) {
		nextBin.releaseLatch();
	    }
	} catch (LatchNotHeldException e) {
	}
	//original();
 ; // this.hook759();
    }
    return ret;
  }

  // line 55 "../../../../TreeIterator.ump"
   public void remove(){
    throw new UnsupportedOperationException();
  }

  // line 59 "../../../../TreeIterator.ump"
   private void advance() throws DatabaseException{
    while (nextBin != null) {
	    if (++index < nextBin.getNEntries()) {
		return;
	    }
	    nextBin = tree.getNextBin(nextBin, false);
	    index = -1;
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../TreeIterator.ump"
  private Tree tree ;
// line 12 "../../../../TreeIterator.ump"
  private BIN nextBin ;
// line 14 "../../../../TreeIterator.ump"
  private int index ;

  
}