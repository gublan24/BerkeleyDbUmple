/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;

// line 3 "../../../../OffsetList.ump"
// line 3 "../../../../OffsetList_static.ump"
public class OffsetList
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OffsetList Attributes
  private Segment head;
  private Segment tail;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OffsetList()
  {
    head = new Segment();
    tail = head;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHead(Segment aHead)
  {
    boolean wasSet = false;
    head = aHead;
    wasSet = true;
    return wasSet;
  }

  public boolean setTail(Segment aTail)
  {
    boolean wasSet = false;
    tail = aTail;
    wasSet = true;
    return wasSet;
  }

  public Segment getHead()
  {
    return head;
  }

  public Segment getTail()
  {
    return tail;
  }

  public void delete()
  {}


  /**
   * public OffsetList() {
   * head = new Segment();
   * tail = head;
   * }
   * 
   * Adds the given value and returns whether a new segment was allocated.
   */
  // line 24 "../../../../OffsetList.ump"
   public boolean add(long value, boolean checkDupOffsets){
    if (checkDupOffsets) {
	    assert (!contains(value)) : Tracer.getStackTrace(new Exception("Dup Offset " + Long.toHexString(value)));
	}
	Segment oldTail = tail;
	tail = tail.add(value);
	size += 1;
	return tail != oldTail;
  }

  // line 34 "../../../../OffsetList.ump"
   public int size(){
    return size;
  }


  /**
   * 
   * Merges the given list and returns whether a segment was freed.
   */
  // line 41 "../../../../OffsetList.ump"
  public boolean merge(OffsetList other){
    boolean oneSegFreed = true;
	Segment seg = other.head;
	while (true) {
	    Segment next = seg.next();
	    if (next != null) {
		seg.setNext(head);
		head = seg;
		seg = next;
	    } else {
		for (int i = 0; i < seg.size(); i += 1) {
		    if (add(seg.get(i), false)) {
			assert oneSegFreed;
			oneSegFreed = false;
		    }
		}
		break;
	    }
	}
	return oneSegFreed;
  }


  /**
   * 
   * Returns an array of all values as longs.  If a writer thread is appending to the list while this method is excuting, some values may be missing from the returned array, but the operation is safe.
   */
  // line 66 "../../../../OffsetList.ump"
   public long[] toArray(){
    long[] a = new long[size];
	int next = 0;
	segments: for (Segment seg = head; seg != null; seg = seg.next()) {
	    for (int i = 0; i < seg.size(); i += 1) {
		if (next >= a.length) {
		    break segments;
		}
		a[next] = seg.get(i);
		next += 1;
	    }
	}
	return a;
  }


  /**
   * 
   * Returns whether this list contains the given offset.
   */
  // line 84 "../../../../OffsetList.ump"
  public boolean contains(long offset){
    for (Segment seg = head; seg != null; seg = seg.next()) {
	    for (int i = 0; i < seg.size(); i += 1) {
		if (seg.get(i) == offset) {
		    return true;
		}
	    }
	}
	return false;
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "head" + "=" + (getHead() != null ? !getHead().equals(this)  ? getHead().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tail" + "=" + (getTail() != null ? !getTail().equals(this)  ? getTail().toString().replaceAll("  ","    ") : "this" : "null");
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../OffsetList_static.ump"
  public static class Segment
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Segment()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
  
    /**
     * public Segment(){
     * values=new int[SEGMENT_CAPACITY];
     * }
     * 
     * Call this method on the tail.  The new tail is returned, if
     * allocating a new tail is necessary.
     */
    // line 17 "../../../../OffsetList_static.ump"
    public Segment add(long value){
      if (index < values.length) {
            values[index]=(int)value;
            index+=1;
            return this;
          }
     else {
            Segment seg=new Segment();
            seg.values[0]=(int)value;
            seg.index=1;
            next=seg;
            return seg;
          }
    }
  
  
    /**
     * 
     * Returns the value at the given index from this segment only.
     */
    // line 34 "../../../../OffsetList_static.ump"
    public long get(int i){
      return ((long)values[i]) & 0xFFFFFFFF;
    }
  
  
    /**
     * 
     * Returns the next segment or null if this is the tail segment.
     */
    // line 40 "../../../../OffsetList_static.ump"
    public Segment next(){
      return next;
    }
  
  
    /**
     * 
     * Sets the next pointer during a merge.
     */
    // line 46 "../../../../OffsetList_static.ump"
    public void setNext(Segment next){
      this.next=next;
    }
  
  
    /**
     * 
     * Returns the number of values in this segment.
     */
    // line 52 "../../../../OffsetList_static.ump"
    public int size(){
      return index;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../OffsetList_static.ump"
    private int index ;
  // line 6 "../../../../OffsetList_static.ump"
    private Segment next ;
  // line 7 "../../../../OffsetList_static.ump"
    private int[] values = new int[SEGMENT_CAPACITY] ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../OffsetList.ump"
  static final int SEGMENT_CAPACITY = 100 ;
// line 13 "../../../../OffsetList.ump"
  private int size ;

  
}