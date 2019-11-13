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
	    Label532: //this.hook352(lsn, inEntry, in);
			int index = inEntry.index;
			if (in.isEntryKnownDeleted(inEntry.index) || in.getLsn(inEntry.index) != lsn) {
					throw new ReturnObject(null);
			}
			throw new ReturnObject(in.fetchTarget(inEntry.index));
			}
	    //throw ReturnHack.returnObject;
  }

  // line 50 "../../../../PreloadLSNTreeWalker.ump"
  public void catch(ReturnObject r){
    Label532_1: 
	    return (Node) r.value;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../PreloadLSNTreeWalker_static.ump"
  public static class INEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //INEntry Attributes
    private IN in;
    private int index;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public INEntry(IN aIn, int aIndex)
    {
      in = aIn;
      index = aIndex;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setIn(IN aIn)
    {
      boolean wasSet = false;
      in = aIn;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setIndex(int aIndex)
    {
      boolean wasSet = false;
      index = aIndex;
      wasSet = true;
      return wasSet;
    }
  
    public IN getIn()
    {
      return in;
    }
  
    public int getIndex()
    {
      return index;
    }
  
    public void delete()
    {}
  
    // line 6 "../../../../PreloadLSNTreeWalker_static.ump"
    public  INEntry(IN in, int index){
      this.in=in;
          this.index=index;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+
              "index" + ":" + getIndex()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "in" + "=" + (getIn() != null ? !getIn().equals(this)  ? getIn().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  import com.sleepycat.je.tree.*;
  
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