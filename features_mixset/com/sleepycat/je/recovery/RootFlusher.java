/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.log.LogManager;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.tree.*;

// line 3 "../../../../RootFlusher.ump"
// line 3 "../../../../Latches_RootFlusher.ump"
public class RootFlusher implements WithRootLatched
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RootFlusher()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 24 "../../../../RootFlusher.ump"
  public  RootFlusher(DatabaseImpl db, LogManager logManager, long targetNodeId){
    this.db = db;
	flushed = false;
	this.logManager = logManager;
	this.targetNodeId = targetNodeId;
	stillRoot = false;
  }


  /**
   * 
   * Flush the rootIN if dirty.
   */
  // line 35 "../../../../RootFlusher.ump"
   public IN doWork(ChildReference root) throws DatabaseException{
    if (root == null) {
					return null;
			}
			IN rootIN = (IN) root.fetchTarget(db, null);
			Label599:
rootIN.latch(false);
 //this.hook599(root, rootIN);
			if (rootIN.getNodeId() == targetNodeId) {
							stillRoot = true;
							if (rootIN.getDirty()) {
						long newLsn = rootIN.log(logManager);
						root.setLsn(newLsn);
						flushed = true;
							}
					}
		//hook599
    Label599_1:
//			try {original(root, rootIN);} finally {
					rootIN.releaseLatch();
	//		}

		return null;
  }

  // line 54 "../../../../RootFlusher.ump"
  public boolean getFlushed(){
    return flushed;
  }

  // line 58 "../../../../RootFlusher.ump"
  public boolean stillRoot(){
    return stillRoot;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../RootFlusher.ump"
  private DatabaseImpl db ;
// line 15 "../../../../RootFlusher.ump"
  private boolean flushed ;
// line 17 "../../../../RootFlusher.ump"
  private boolean stillRoot ;
// line 19 "../../../../RootFlusher.ump"
  private LogManager logManager ;
// line 21 "../../../../RootFlusher.ump"
  private long targetNodeId ;

  
}