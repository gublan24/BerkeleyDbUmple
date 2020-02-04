/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.DatabaseException;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

// line 3 "../../../../INList.ump"
// line 3 "../../../../INList_static.ump"
public class INList
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INList()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 20 "../../../../INList.ump"
  public  INList(EnvironmentImpl envImpl){
    this.envImpl = envImpl;
	ins = new TreeSet();
	Label338: ;//this.hook338(envImpl);
  }


  /**
   * 
   * Used only by tree verifier when validating INList. Must be called with orig.majorLatch acquired.
   */
  // line 29 "../../../../INList.ump"
   public  INList(INList orig, EnvironmentImpl envImpl) throws DatabaseException{
    ins = new TreeSet(orig.getINs());
	Lanbel340: //this.hook340();
	this.envImpl = envImpl;
	Label339: //this.hook339(envImpl);
  }

  // line 36 "../../../../INList.ump"
   public SortedSet getINs(){
    return ins;
  }

  // line 40 "../../../../INList.ump"
   public int getSize(){
    return ins.size();
  }


  /**
   * 
   * An IN has just come into memory, add it to the list.
   */
  // line 47 "../../../../INList.ump"
   public void add(IN in) throws DatabaseException{
    new INList_add(this, in).execute();
  }

  // line 51 "../../../../INList.ump"
   private void addAndSetMemory(Set set, IN in){
    new INList_addAndSetMemory(this, set, in).execute();
  }


  /**
   * 
   * An IN is getting evicted or is displaced by recovery.  Caller is responsible for acquiring the major latch before calling this and releasing it when they're done.
   */
  // line 58 "../../../../INList.ump"
   public void removeLatchAlreadyHeld(IN in) throws DatabaseException{
    boolean removeDone = ins.remove(in);
		Label341: //removeDone = this.hook341(in, removeDone);
		assert removeDone;
		//this.hook346(in);
    Label346:
  }


  /**
   * 
   * An IN is getting swept or is displaced by recovery.
   */
  // line 69 "../../../../INList.ump"
   public void remove(IN in) throws DatabaseException{
    try {
						 	removeLatchAlreadyHeld(in); //original(in);
					} finally {
							LabelRemove_1://releaseMajorLatch();
					}
  }

  // line 77 "../../../../INList.ump"
   public SortedSet tailSet(IN in) throws DatabaseException{
    return ins.tailSet(in);
  }

  // line 81 "../../../../INList.ump"
   public IN first() throws DatabaseException{
    return (IN) ins.first();
  }


  /**
   * 
   * Return an iterator over the main 'ins' set.  Returned iterator will not show the elements in addedINs. The major latch should be held before entering.  The caller is responsible for releasing the major latch when they're finished with the iterator.
   * @return an iterator over the main 'ins' set.
   */
  // line 89 "../../../../INList.ump"
   public Iterator iterator(){
    return ins.iterator();
  }


  /**
   * 
   * Clear the entire list during recovery and at shutdown.
   */
  // line 96 "../../../../INList.ump"
   public void clear() throws DatabaseException{
    ins.clear();
	//this.hook342();
  }

  // line 101 "../../../../INList.ump"
   public void dump(){
    System.out.println("size=" + getSize());
	Iterator iter = ins.iterator();
	while (iter.hasNext()) {
	    IN theIN = (IN) iter.next();
	    System.out.println(
		    "db=" + theIN.getDatabase().getId() + " nid=: " + theIN.getNodeId() + "/" + theIN.getLevel());
	}
  }


  /**
   * protected void hook338(EnvironmentImpl envImpl) {
   * }
   * protected void hook339(EnvironmentImpl envImpl) throws DatabaseException {
   * }
   * protected void hook340() throws DatabaseException {
   * }
   * protected boolean hook341(IN in, boolean removeDone) throws DatabaseException {
   * return removeDone;
   * }
   * protected void hook342() throws DatabaseException {
   * }
   */
  // line 127 "../../../../INList.ump"
   protected void hook346(IN in) throws DatabaseException{
    
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../INList_static.ump"
  public static class INList_add
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public INList_add()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../INList_static.ump"
    public  INList_add(INList _this, IN in){
      this._this=_this;
          this.in=in;
    }
  
    // line 10 "../../../../INList_static.ump"
    public void execute() throws DatabaseException{
      Label344: //this.hook344();
          addToMajor=true;
  try{
          Label343: //this.hook343();
    			Label345: //this.hook345();
          _this.addAndSetMemory(_this.ins,in);
  Label345_1:
  }
  finally{
           Label343_1:  ;//
  }
  				//End of hook345
  				//End of hook343
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 24 "../../../../INList_static.ump"
    protected INList _this ;
  // line 25 "../../../../INList_static.ump"
    protected IN in ;
  // line 26 "../../../../INList_static.ump"
    protected boolean enteredWithLatchHeld ;
  // line 27 "../../../../INList_static.ump"
    protected boolean addToMajor ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 36 "../../../../INList_static.ump"
  public static class INList_addAndSetMemory
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public INList_addAndSetMemory()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 38 "../../../../INList_static.ump"
    public  INList_addAndSetMemory(INList _this, Set set, IN in){
      this._this=_this;
          this.set=set;
          this.in=in;
    }
  
    // line 43 "../../../../INList_static.ump"
    public void execute(){
      addOk=set.add(in);
          assert addOk : "failed adding in " + in.getNodeId();
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 46 "../../../../INList_static.ump"
    protected INList _this ;
  // line 47 "../../../../INList_static.ump"
    protected Set set ;
  // line 48 "../../../../INList_static.ump"
    protected IN in ;
  // line 49 "../../../../INList_static.ump"
    protected boolean addOk ;
  // line 50 "../../../../INList_static.ump"
    protected MemoryBudget mb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../INList.ump"
  private static final String DEBUG_NAME = INList.class.getName() ;
// line 15 "../../../../INList.ump"
  private SortedSet ins = null ;
// line 17 "../../../../INList.ump"
  private EnvironmentImpl envImpl ;

  
}