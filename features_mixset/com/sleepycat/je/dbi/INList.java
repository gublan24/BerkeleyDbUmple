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
// line 3 "../../../../MemoryBudget_INList.ump"
// line 3 "../../../../MemoryBudget_INList_inner.ump"
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
	this.hook338(envImpl);
  }


  /**
   * 
   * Used only by tree verifier when validating INList. Must be called with orig.majorLatch acquired.
   */
  // line 29 "../../../../INList.ump"
   public  INList(INList orig, EnvironmentImpl envImpl) throws DatabaseException{
    ins = new TreeSet(orig.getINs());
	this.hook340();
	this.envImpl = envImpl;
	this.hook339(envImpl);
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
		removeDone = this.hook341(in, removeDone);
		assert removeDone;
		//this.hook346(in);
    Label346:
if (updateMemoryUsage) {
			  envImpl.getMemoryBudget().updateTreeMemoryUsage(in.getAccumulatedDelta() - in.getInMemorySize());
			  in.setInListResident(false);
		}
//		original(in);

  }


  /**
   * 
   * An IN is getting swept or is displaced by recovery.
   */
  // line 69 "../../../../INList.ump"
   public void remove(IN in) throws DatabaseException{
    removeLatchAlreadyHeld(in);
  }

  // line 73 "../../../../INList.ump"
   public SortedSet tailSet(IN in) throws DatabaseException{
    return ins.tailSet(in);
  }

  // line 77 "../../../../INList.ump"
   public IN first() throws DatabaseException{
    return (IN) ins.first();
  }


  /**
   * 
   * Return an iterator over the main 'ins' set.  Returned iterator will not show the elements in addedINs. The major latch should be held before entering.  The caller is responsible for releasing the major latch when they're finished with the iterator.
   * @return an iterator over the main 'ins' set.
   */
  // line 85 "../../../../INList.ump"
   public Iterator iterator(){
    return ins.iterator();
  }


  /**
   * 
   * Clear the entire list during recovery and at shutdown.
   */
  // line 92 "../../../../INList.ump"
   public void clear() throws DatabaseException{
    ins.clear();
	this.hook342();
    // line 30 "../../../../MemoryBudget_INList.ump"
    //		original();
    		if (updateMemoryUsage) {
    			  envImpl.getMemoryBudget().refreshTreeMemoryUsage(0);
    			}
    // END OF UMPLE AFTER INJECTION
  }

  // line 97 "../../../../INList.ump"
   public void dump(){
    System.out.println("size=" + getSize());
	Iterator iter = ins.iterator();
	while (iter.hasNext()) {
	    IN theIN = (IN) iter.next();
	    System.out.println(
		    "db=" + theIN.getDatabase().getId() + " nid=: " + theIN.getNodeId() + "/" + theIN.getLevel());
	}
  }

  // line 107 "../../../../INList.ump"
   protected void hook338(EnvironmentImpl envImpl){
    
  }

  // line 110 "../../../../INList.ump"
   protected void hook339(EnvironmentImpl envImpl) throws DatabaseException{
    
  }

  // line 113 "../../../../INList.ump"
   protected void hook340() throws DatabaseException{
    
  }

  // line 116 "../../../../INList.ump"
   protected boolean hook341(IN in, boolean removeDone) throws DatabaseException{
    return removeDone;
  }

  // line 120 "../../../../INList.ump"
   protected void hook342() throws DatabaseException{
    
  }

  // line 123 "../../../../INList.ump"
   protected void hook346(IN in) throws DatabaseException{
    
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
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
      this.hook344();
          addToMajor=true;
          this.hook343();
    }
  
    // line 19 "../../../../INList_static.ump"
     protected void hook343() throws DatabaseException{
      this.hook345();
    }
  
    // line 22 "../../../../INList_static.ump"
     protected void hook344() throws DatabaseException{
      
    }
  
    // line 24 "../../../../INList_static.ump"
     protected void hook345() throws DatabaseException{
      _this.addAndSetMemory(_this.ins,in);
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 14 "../../../../INList_static.ump"
    protected INList _this ;
  // line 15 "../../../../INList_static.ump"
    protected IN in ;
  // line 16 "../../../../INList_static.ump"
    protected boolean enteredWithLatchHeld ;
  // line 17 "../../../../INList_static.ump"
    protected boolean addToMajor ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 27 "../../../../INList_static.ump"
  // line 4 "../../../../MemoryBudget_INList_inner.ump"
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
  
    // line 29 "../../../../INList_static.ump"
    public  INList_addAndSetMemory(INList _this, Set set, IN in){
      this._this=_this;
          this.set=set;
          this.in=in;
    }
  
    // line 34 "../../../../INList_static.ump"
    public void execute(){
      addOk=set.add(in);
          assert addOk : "failed adding in " + in.getNodeId();
      // line 6 "../../../../MemoryBudget_INList_inner.ump"
      //original();
              if (_this.updateMemoryUsage) {
                mb=_this.envImpl.getMemoryBudget();
                mb.updateTreeMemoryUsage(in.getInMemorySize());
                in.setInListResident(true);
              }
      // END OF UMPLE AFTER INJECTION
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 37 "../../../../INList_static.ump"
    protected INList _this ;
  // line 38 "../../../../INList_static.ump"
    protected Set set ;
  // line 39 "../../../../INList_static.ump"
    protected IN in ;
  // line 40 "../../../../INList_static.ump"
    protected boolean addOk ;
  // line 41 "../../../../INList_static.ump"
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
// line 5 "../../../../MemoryBudget_INList.ump"
  private boolean updateMemoryUsage ;

  
}