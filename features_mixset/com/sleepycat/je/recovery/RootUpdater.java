/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.tree.*;

// line 3 "../../../../RootUpdater.ump"
// line 3 "../../../../Latches_RootUpdater.ump"
public class RootUpdater implements WithRootLatched
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RootUpdater()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 26 "../../../../RootUpdater.ump"
  public  RootUpdater(Tree tree, IN inFromLog, long lsn){
    this.tree = tree;
	this.inFromLog = inFromLog;
	this.lsn = lsn;
  }

  // line 32 "../../../../RootUpdater.ump"
   public IN doWork(ChildReference root) throws DatabaseException{
    ChildReference newRoot = tree.makeRootChildReference(inFromLog, new byte[0], lsn);
	this.hook600();
	if (root == null) {
	    tree.setRoot(newRoot, false);
	    inserted = true;
	} else {
	    origLsn = root.getLsn();
	    if (DbLsn.compareTo(origLsn, lsn) < 0) {
		tree.setRoot(newRoot, false);
		replaced = true;
	    }
	}
	return null;
  }

  // line 48 "../../../../RootUpdater.ump"
  public boolean updateDone(){
    return inserted || replaced;
  }

  // line 52 "../../../../RootUpdater.ump"
  public boolean getInserted(){
    return inserted;
  }

  // line 56 "../../../../RootUpdater.ump"
  public boolean getReplaced(){
    return replaced;
  }

  // line 60 "../../../../RootUpdater.ump"
  public long getOriginalLsn(){
    return origLsn;
  }

  // line 64 "../../../../RootUpdater.ump"
   protected void hook600() throws DatabaseException{
    inFromLog.releaseLatch();
	original();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../RootUpdater.ump"
  private Tree tree ;
// line 15 "../../../../RootUpdater.ump"
  private IN inFromLog ;
// line 17 "../../../../RootUpdater.ump"
  private long lsn = DbLsn.NULL_LSN ;
// line 19 "../../../../RootUpdater.ump"
  private boolean inserted = false ;
// line 21 "../../../../RootUpdater.ump"
  private boolean replaced = false ;
// line 23 "../../../../RootUpdater.ump"
  private long origLsn = DbLsn.NULL_LSN ;

  
}