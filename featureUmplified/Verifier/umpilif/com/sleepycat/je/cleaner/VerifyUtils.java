/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.SortedLSNTreeWalker.TreeNodeProcessor;
import com.sleepycat.je.dbi.SortedLSNTreeWalker;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Database;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

// line 3 "../../../../VerifyUtils.ump"
// line 3 "../../../../VerifyUtils_inner.ump"
public class VerifyUtils
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public VerifyUtils()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Compare the lsns referenced by a given Database to the lsns held in the utilization profile. Assumes that the database and environment is quiescent, and that there is no current cleaner activity.
   */
  // line 23 "../../../../VerifyUtils.ump"
   public static  void checkLsns(Database db) throws DatabaseException{
    DatabaseImpl dbImpl = DbInternal.dbGetDatabaseImpl(db);
	GatherLSNs gatherLsns = new GatherLSNs();
	long rootLsn = dbImpl.getTree().getRootLsn();
	SortedLSNTreeWalker walker = new SortedLSNTreeWalker(dbImpl, false, false, rootLsn, gatherLsns);
	walker.walk();
	Set lsnsInTree = gatherLsns.getLsns();
	lsnsInTree.add(new Long(rootLsn));
	Iterator iter = lsnsInTree.iterator();
	Set fileNums = new HashSet();
	while (iter.hasNext()) {
	    long lsn = ((Long) iter.next()).longValue();
	    fileNums.add(new Long(DbLsn.getFileNumber(lsn)));
	}
	iter = fileNums.iterator();
	Set obsoleteLsns = new HashSet();
	UtilizationProfile profile = dbImpl.getDbEnvironment().getUtilizationProfile();
	while (iter.hasNext()) {
	    Long fileNum = (Long) iter.next();
	    PackedOffsets obsoleteOffsets = new PackedOffsets();
	    TrackedFileSummary tfs = profile.getObsoleteDetail(fileNum, obsoleteOffsets, false);
	    PackedOffsets.Iterator obsoleteIter = obsoleteOffsets.iterator();
	    while (obsoleteIter.hasNext()) {
		long offset = obsoleteIter.next();
		Long oneLsn = new Long(DbLsn.makeLsn(fileNum.longValue(), offset));
		obsoleteLsns.add(oneLsn);
		if (DEBUG) {
		    System.out.println("Adding 0x" + Long.toHexString(oneLsn.longValue()));
		}
	    }
	}
	boolean error = false;
	iter = lsnsInTree.iterator();
	while (iter.hasNext()) {
	    Long lsn = (Long) iter.next();
	    if (obsoleteLsns.contains(lsn)) {
		System.err.println("Obsolete lsns contains valid lsn " + DbLsn.getNoFormatString(lsn.longValue()));
		error = true;
	    }
	}
	iter = obsoleteLsns.iterator();
	while (iter.hasNext()) {
	    Long lsn = (Long) iter.next();
	    if (lsnsInTree.contains(lsn)) {
		System.err.println("Tree contains obsolete lsn " + DbLsn.getNoFormatString(lsn.longValue()));
		error = true;
	    }
	}
	if (error) {
	    throw new DatabaseException("Lsn mismatch");
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  // line 4 "../../../../VerifyUtils_inner.ump"
  public static class GatherLSNs
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public GatherLSNs()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 9 "../../../../VerifyUtils_inner.ump"
     public void processLSN(long childLSN, LogEntryType childType){
      lsns.add(new Long(childLSN));
    }
  
    // line 12 "../../../../VerifyUtils_inner.ump"
     public Set getLsns(){
      return lsns;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 7 "../../../../VerifyUtils_inner.ump"
    private Set lsns=new HashSet() ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../../../VerifyUtils.ump"
  private static final boolean DEBUG = false ;

  
}