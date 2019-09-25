/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.DBIN;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.cleaner.OffsetList;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Arrays;

// line 3 "../../../../SortedLSNTreeWalker.ump"
public class SortedLSNTreeWalker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SortedLSNTreeWalker()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 45 "../../../../SortedLSNTreeWalker.ump"
   public  SortedLSNTreeWalker(DatabaseImpl dbImpl, boolean removeINsFromINList, boolean setDbState, long rootLsn, TreeNodeProcessor callback) throws DatabaseException{
    this.dbImpl = dbImpl;
	this.envImpl = dbImpl.getDbEnvironment();
	if (envImpl == null) {
	    throw new DatabaseException("environmentImpl is null for target db " + dbImpl.getDebugName());
	}
	this.dups = dbImpl.getSortedDuplicates();
	this.removeINsFromINList = removeINsFromINList;
	this.setDbState = setDbState;
	this.rootLsn = rootLsn;
	this.callback = callback;
	currentLSNs = new long[0];
	currentLSNIdx = 0;
  }

  // line 60 "../../../../SortedLSNTreeWalker.ump"
   private boolean extractINsForDb(INList inList) throws DatabaseException{
    return new SortedLSNTreeWalker_extractINsForDb(this, inList).execute();
  }


  /**
   * 
   * Find all non-resident nodes, and execute the callback. The root IN's LSN is not returned to the callback.
   */
  // line 67 "../../../../SortedLSNTreeWalker.ump"
   public void walk() throws DatabaseException{
    walkInternal();
  }

  // line 71 "../../../../SortedLSNTreeWalker.ump"
   protected void walkInternal() throws DatabaseException{
    INList inList = envImpl.getInMemoryINs();
	IN root = null;
	if (!extractINsForDb(inList)) {
	    if (rootLsn == DbLsn.NULL_LSN) {
		return;
	    }
	    root = getRootIN(rootLsn);
	    accumulateLSNs(root);
	    releaseRootIN(root);
	}
	this.hook359();
	while (true) {
	    maybeGetMoreINs();
	    if (currentLSNs != null && currentLSNIdx < currentLSNs.length) {
		fetchAndProcessLSN(currentLSNs[currentLSNIdx++]);
	    } else {
		break;
	    }
	}
  }

  // line 93 "../../../../SortedLSNTreeWalker.ump"
   private void maybeGetMoreINs(){
    if ((currentLSNs != null && currentLSNIdx >= currentLSNs.length)) {
	    if (accumulatedLSNFileNumbers == null || accumulatedLSNFileNumbers.size() == 0) {
		currentLSNs = null;
		currentLSNIdx = Integer.MAX_VALUE;
		return;
	    }
	    long[] tempFileNumbers = accumulatedLSNFileNumbers.toArray();
	    long[] tempFileOffsets = accumulatedLSNFileOffsets.toArray();
	    int nLSNs = tempFileNumbers.length;
	    currentLSNIdx = 0;
	    currentLSNs = new long[nLSNs];
	    for (int i = 0; i < nLSNs; i++) {
		currentLSNs[i] = DbLsn.makeLsn(tempFileNumbers[i], tempFileOffsets[i]);
	    }
	    Arrays.sort(currentLSNs);
	    accumulatedLSNFileNumbers = null;
	    accumulatedLSNFileOffsets = null;
	}
  }

  // line 114 "../../../../SortedLSNTreeWalker.ump"
   private void accumulateLSNs(IN in) throws DatabaseException{
    boolean accumulate = true;
	if (!accumulateLNs) {
	    if ((!dups && (in instanceof BIN)) || (in instanceof DBIN)) {
		accumulate = false;
	    }
	}
	for (int i = 0; i < in.getNEntries(); i++) {
	    if (in.isEntryPendingDeleted(i) || in.isEntryKnownDeleted(i)) {
		continue;
	    }
	    long lsn = in.getLsn(i);
	    Node node = in.getTarget(i);
	    if (accumulate && (node == null)) {
		if (accumulatedLSNFileNumbers == null) {
		    accumulatedLSNFileNumbers = new OffsetList();
		    accumulatedLSNFileOffsets = new OffsetList();
		}
		accumulatedLSNFileNumbers.add(DbLsn.getFileNumber(lsn), false);
		accumulatedLSNFileOffsets.add(DbLsn.getFileOffset(lsn), false);
		addToLsnINMap(new Long(lsn), in, i);
	    } else {
		callback.processLSN(lsn, (node == null) ? LogEntryType.LOG_LN : node.getLogType());
	    }
	}
	if (in instanceof DIN) {
	    if (in.isRoot()) {
		DIN din = (DIN) in;
		callback.processLSN(din.getDupCountLNRef().getLsn(), LogEntryType.LOG_DUPCOUNTLN);
	    }
	}
  }

  // line 147 "../../../../SortedLSNTreeWalker.ump"
   private void fetchAndProcessLSN(long lsn) throws DatabaseException{
    Node node = fetchLSN(lsn);
	if (node != null) {
	    callback.processLSN(lsn, node.getLogType());
	    if (node instanceof IN) {
		accumulateLSNs((IN) node);
	    }
	}
  }


  /**
   * 
   * The default behavior fetches the rootIN from the log, but classes extending this may fetch the root from the tree.
   */
  // line 160 "../../../../SortedLSNTreeWalker.ump"
   protected IN getRootIN(long rootLsn) throws DatabaseException{
    return (IN) envImpl.getLogManager().get(rootLsn);
  }

  // line 164 "../../../../SortedLSNTreeWalker.ump"
   protected void releaseRootIN(IN ignore) throws DatabaseException{
    
  }

  // line 167 "../../../../SortedLSNTreeWalker.ump"
   protected void addToLsnINMap(Long lsn, IN in, int index){
    
  }

  // line 170 "../../../../SortedLSNTreeWalker.ump"
   protected Node fetchLSN(long lsn) throws DatabaseException{
    return (Node) envImpl.getLogManager().get(lsn);
  }

  // line 174 "../../../../SortedLSNTreeWalker.ump"
   protected void hook359() throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 19 "../../../../SortedLSNTreeWalker.ump"
  protected DatabaseImpl dbImpl ;
// line 21 "../../../../SortedLSNTreeWalker.ump"
  private EnvironmentImpl envImpl ;
// line 23 "../../../../SortedLSNTreeWalker.ump"
  private long rootLsn ;
// line 25 "../../../../SortedLSNTreeWalker.ump"
  private boolean dups ;
// line 27 "../../../../SortedLSNTreeWalker.ump"
  private boolean removeINsFromINList ;
// line 29 "../../../../SortedLSNTreeWalker.ump"
  private boolean setDbState ;
// line 31 "../../../../SortedLSNTreeWalker.ump"
  private long[] currentLSNs ;
// line 33 "../../../../SortedLSNTreeWalker.ump"
  private int currentLSNIdx = 0 ;
// line 35 "../../../../SortedLSNTreeWalker.ump"
  private OffsetList accumulatedLSNFileNumbers ;
// line 37 "../../../../SortedLSNTreeWalker.ump"
  private OffsetList accumulatedLSNFileOffsets ;
// line 39 "../../../../SortedLSNTreeWalker.ump"
  private TreeNodeProcessor callback ;
// line 41 "../../../../SortedLSNTreeWalker.ump"
  protected boolean accumulateLNs = false ;

  
}