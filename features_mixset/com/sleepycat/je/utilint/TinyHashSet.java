/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.HashSet;

// line 3 "../../../../TinyHashSet.ump"
// line 3 "../../../../TinyHashSet_static.ump"
public class TinyHashSet
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TinyHashSet()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 15 "../../../../TinyHashSet.ump"
   public int size(){
    if (single != null) {
	    return 1;
	} else if (set != null) {
	    return set.size();
	} else {
	    return 0;
	}
  }

  // line 25 "../../../../TinyHashSet.ump"
   public boolean remove(Object o){
    assert (single == null) || (set == null);
	if (single != null) {
	    if (single == o || single.equals(o)) {
		single = null;
		return true;
	    } else {
		return false;
	    }
	} else if (set != null) {
	    return set.remove(o);
	} else {
	    return false;
	}
  }

  // line 41 "../../../../TinyHashSet.ump"
   public boolean add(Object o){
    assert (single == null) || (set == null);
	if (set != null) {
	    return set.add(o);
	} else if (single == null) {
	    single = o;
	    return true;
	} else {
	    set = new HashSet();
	    set.add(single);
	    single = null;
	    return set.add(o);
	}
  }

  // line 56 "../../../../TinyHashSet.ump"
   public boolean contains(Object o){
    if (set != null) {
        return set.contains(o);
    }
    return false;
  }

  // line 63 "../../../../TinyHashSet.ump"
   public Set copy(){
    assert (single == null) || (set == null);
	if (set != null) {
	    return new HashSet(set);
	} else {
	    Set ret = new HashSet();
	    if (single != null) {
		ret.add(single);
	    }
	    return ret;
	}
  }

  // line 76 "../../../../TinyHashSet.ump"
   public Iterator iterator(){
    assert (single == null) || (set == null);
	if (set != null) {
	    return set.iterator();
	} else {
	    return new SingleElementIterator(single, this);
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../TinyHashSet_static.ump"
  public static class SingleElementIterator implements Iterator
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //SingleElementIterator Attributes
    private TinyHashSet theSet;
    private boolean returnedTheObject;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public SingleElementIterator(TinyHashSet aTheSet)
    {
      theSet = aTheSet;
      returnedTheObject = false;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setTheSet(TinyHashSet aTheSet)
    {
      boolean wasSet = false;
      theSet = aTheSet;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setReturnedTheObject(boolean aReturnedTheObject)
    {
      boolean wasSet = false;
      returnedTheObject = aReturnedTheObject;
      wasSet = true;
      return wasSet;
    }
  
    public TinyHashSet getTheSet()
    {
      return theSet;
    }
  
    public boolean getReturnedTheObject()
    {
      return returnedTheObject;
    }
  
    public void delete()
    {}
  
    // line 10 "../../../../TinyHashSet_static.ump"
    public  SingleElementIterator(Object o, TinyHashSet theSet){
      theObject=o;
          this.theSet=theSet;
          returnedTheObject=(o == null);
    }
  
    // line 15 "../../../../TinyHashSet_static.ump"
     public boolean hasNext(){
      return !returnedTheObject;
    }
  
    // line 18 "../../../../TinyHashSet_static.ump"
     public Object next(){
      if (returnedTheObject) {
            throw new NoSuchElementException();
          }
          returnedTheObject=true;
          return theObject;
    }
  
    // line 25 "../../../../TinyHashSet_static.ump"
     public void remove(){
      if (theObject == null || !returnedTheObject) {
            throw new IllegalStateException();
          }
          theSet.remove(theObject);
    }
  
  
    public String toString()
    {
      return super.toString() + "["+
              "returnedTheObject" + ":" + getReturnedTheObject()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "theSet" + "=" + (getTheSet() != null ? !getTheSet().equals(this)  ? getTheSet().toString().replaceAll("  ","    ") : "this" : "null");
    }  
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 6 "../../../../TinyHashSet_static.ump"
    public Object theObject ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../TinyHashSet.ump"
  private Set set ;
// line 12 "../../../../TinyHashSet.ump"
  private Object single ;

  
}