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
	this.hook346(in);
  }


  /**
   * 
   * An IN is getting swept or is displaced by recovery.
   */
  // line 68 "../../../../INList.ump"
   public void remove(IN in) throws DatabaseException{
    removeLatchAlreadyHeld(in);
  }

  // line 72 "../../../../INList.ump"
   public SortedSet tailSet(IN in) throws DatabaseException{
    return ins.tailSet(in);
  }

  // line 76 "../../../../INList.ump"
   public IN first() throws DatabaseException{
    return (IN) ins.first();
  }


  /**
   * 
   * Return an iterator over the main 'ins' set.  Returned iterator will not show the elements in addedINs. The major latch should be held before entering.  The caller is responsible for releasing the major latch when they're finished with the iterator.
   * @return an iterator over the main 'ins' set.
   */
  // line 84 "../../../../INList.ump"
   public Iterator iterator(){
    return ins.iterator();
  }


  /**
   * 
   * Clear the entire list during recovery and at shutdown.
   */
  // line 91 "../../../../INList.ump"
   public void clear() throws DatabaseException{
    ins.clear();
	this.hook342();
  }

  // line 96 "../../../../INList.ump"
   public void dump(){
    System.out.println("size=" + getSize());
	Iterator iter = ins.iterator();
	while (iter.hasNext()) {
	    IN theIN = (IN) iter.next();
	    System.out.println(
		    "db=" + theIN.getDatabase().getId() + " nid=: " + theIN.getNodeId() + "/" + theIN.getLevel());
	}
  }

  // line 106 "../../../../INList.ump"
   protected void hook338(EnvironmentImpl envImpl){
    
  }

  // line 109 "../../../../INList.ump"
   protected void hook339(EnvironmentImpl envImpl) throws DatabaseException{
    
  }

  // line 112 "../../../../INList.ump"
   protected void hook340() throws DatabaseException{
    
  }

  // line 115 "../../../../INList.ump"
   protected boolean hook341(IN in, boolean removeDone) throws DatabaseException{
    return removeDone;
  }

  // line 119 "../../../../INList.ump"
   protected void hook342() throws DatabaseException{
    
  }

  // line 122 "../../../../INList.ump"
   protected void hook346(IN in) throws DatabaseException{
    
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