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
// line 3 "../../../../PreloadLSNTreeWalker_static.ump"
// line 3 "../../../../Latches_PreloadLSNTreeWalker.ump"
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
    //try {
	    INEntry inEntry = (INEntry) lsnINMap.remove(new Long(lsn));
	    assert (inEntry != null);
	    IN in = inEntry.in;
	    Label352:
in.latch();
 ; //this.hook352(lsn, inEntry, in);
			int index = inEntry.index;
			if (in.isEntryKnownDeleted(inEntry.index) || in.getLsn(inEntry.index) != lsn) {
					return null; //throw new ReturnObject(null);
			}	    
      Label352_1:
in.releaseLatch();
 ;// 
			return in.fetchTarget(inEntry.index);
  }

  // line 7 "../../../../Latches_PreloadLSNTreeWalker.ump"
   protected void releaseRootIN(IN root) throws DatabaseException{
    root.releaseLatch();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../PreloadLSNTreeWalker_static.ump"
  public static class INEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public INEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../PreloadLSNTreeWalker_static.ump"
    public  INEntry(IN in, int index){
      this.in=in;
          this.index=index;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 9 "../../../../PreloadLSNTreeWalker_static.ump"
    protected IN in ;
  // line 10 "../../../../PreloadLSNTreeWalker_static.ump"
    protected int index ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 12 "../../../../PreloadLSNTreeWalker_static.ump"
  public class PreloadWithRootLatched implements WithRootLatched
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public PreloadWithRootLatched()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 15 "../../../../PreloadLSNTreeWalker_static.ump"
     public IN doWork(ChildReference root) throws DatabaseException{
      walkInternal();
          return null;
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../PreloadLSNTreeWalker.ump"
  private Map lsnINMap = new HashMap() ;

  
}