/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import java.util.NoSuchElementException;
import java.util.Iterator;
import com.sleepycat.je.cleaner.*;

// line 3 "../../../../TreeIterator.ump"
public class TreeIterator extends Iterator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeIterator()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 17 "../../../../TreeIterator.ump"
   public  TreeIterator(Tree tree) throws DatabaseException{
    nextBin = (BIN) tree.getFirstNode();
	Label755:           ;  //this.hook755();
	index = -1;
	this.tree = tree;
  }

  // line 24 "../../../../TreeIterator.ump"
   public boolean hasNext(){
    boolean ret = false;
	try {
	    Label756:           ;  //this.hook756();
	    advance();
	    ret = (nextBin != null) && (index < nextBin.getNEntries());
	} catch (DatabaseException e) {
	} finally {
	    Label757:           ;  //this.hook757();
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
	    Label758:           ;  //this.hook758();
	    ret = nextBin.getKey(index);
	} catch (DatabaseException e) {
	} finally {
	    Label759:           ;  //this.hook759();
	}
	return ret;
  }

  // line 52 "../../../../TreeIterator.ump"
   public void remove(){
    throw new UnsupportedOperationException();
  }

  // line 56 "../../../../TreeIterator.ump"
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