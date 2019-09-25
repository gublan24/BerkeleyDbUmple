/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.Node;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.PreloadConfig;
import com.sleepycat.je.DatabaseException;
import java.util.Map;
import java.util.HashMap;

// line 3 "../../../../PreloadLSNTreeWalker.ump"
public class PreloadLSNTreeWalker extends SortedLSNTreeWalker
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadLSNTreeWalker()
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

  // line 18 "../../../../PreloadLSNTreeWalker.ump"
  public  PreloadLSNTreeWalker(DatabaseImpl db, TreeNodeProcessor callback, PreloadConfig conf) throws DatabaseException{
    super(db, false, false, db.tree.getRootLsn(), callback);
	accumulateLNs = conf.getLoadLNs();
  }

  // line 23 "../../../../PreloadLSNTreeWalker.ump"
   public void walk() throws DatabaseException{
    WithRootLatched preloadWRL = new PreloadWithRootLatched();
	dbImpl.getTree().withRootLatchedExclusive(preloadWRL);
  }

  // line 28 "../../../../PreloadLSNTreeWalker.ump"
   protected IN getRootIN(long rootLsn) throws DatabaseException{
    return dbImpl.getTree().getRootIN(false);
  }

  // line 32 "../../../../PreloadLSNTreeWalker.ump"
   protected void addToLsnINMap(Long lsn, IN in, int index){
    assert in.getDatabase() != null;
	lsnINMap.put(lsn, new INEntry(in, index));
  }

  // line 37 "../../../../PreloadLSNTreeWalker.ump"
   protected Node fetchLSN(long lsn) throws DatabaseException{
    try {
	    INEntry inEntry = (INEntry) lsnINMap.remove(new Long(lsn));
	    assert (inEntry != null);
	    IN in = inEntry.in;
	    this.hook352(lsn, inEntry, in);
	    throw ReturnHack.returnObject;
	} catch (ReturnObject r) {
	    return (Node) r.value;
	}
  }

  // line 49 "../../../../PreloadLSNTreeWalker.ump"
   protected void hook352(long lsn, INEntry inEntry, IN in) throws DatabaseException{
    int index = inEntry.index;
	if (in.isEntryKnownDeleted(index) || in.getLsn(index) != lsn) {
	    throw new ReturnObject(null);
	}
	throw new ReturnObject(in.fetchTarget(index));
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../PreloadLSNTreeWalker.ump"
  private Map lsnINMap = new HashMap() ;

  
}