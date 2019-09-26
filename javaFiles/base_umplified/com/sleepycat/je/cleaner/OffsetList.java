// Original file location:  /home/abdulaziz/Desktop/BerkeleyDb/javaFiles/base_x/com/sleepycat/je/cleaner/OffsetList.java
package com.sleepycat.je.cleaner;
import com.sleepycat.je.utilint.Tracer;
import de.ovgu.cide.jakutil.*;
/** 
 * List of LSN offsets as a linked list of segments.  The reasons for using a
 * list of this type and not a java.util.List are:
 * <ul>
 * <li>Segements reduce memory overhead by storing long primitives rather than
 * Long objects.  Many longs per segment reduce link overhead.</li>
 * <li>Memory is only allocated for new segments, reducing the number of calls
 * to update the memory budget.</li>
 * <li>This is an append-only list that supports a single appender thread and
 * multiple unsynchronized reader threads.  The caller is responsible for
 * synchronizing such that only one thread calls add() at one time.  The reader
 * threads see data as it is changing but do not see inconsistent data (corrupt
 * longs) and do not require synchronization for thread safety.</li>
 * </ul>
 * <p>The algorithms here use traversal of the list segments rather than
 * recursion to avoid using a lot of stack space.</p>
 */
public class OffsetList {
  static final int SEGMENT_CAPACITY=100;
  private Segment head;
  private Segment tail;
  private int size;
  public OffsetList(){
    head=new Segment();
    tail=head;
  }
  /** 
 * Adds the given value and returns whether a new segment was allocated.
 */
  public boolean add(  long value,  boolean checkDupOffsets){
    if (checkDupOffsets) {
      assert (!contains(value)) : Tracer.getStackTrace(new Exception("Dup Offset " + Long.toHexString(value)));
    }
    Segment oldTail=tail;
    tail=tail.add(value);
    size+=1;
    return tail != oldTail;
  }
  public int size(){
    return size;
  }
  /** 
 * Merges the given list and returns whether a segment was freed.
 */
  boolean merge(  OffsetList other){
    boolean oneSegFreed=true;
    Segment seg=other.head;
    while (true) {
      Segment next=seg.next();
      if (next != null) {
        seg.setNext(head);
        head=seg;
        seg=next;
      }
 else {
        for (int i=0; i < seg.size(); i+=1) {
          if (add(seg.get(i),false)) {
            assert oneSegFreed;
            oneSegFreed=false;
          }
        }
        break;
      }
    }
    return oneSegFreed;
  }
  /** 
 * Returns an array of all values as longs.  If a writer thread is
 * appending to the list while this method is excuting, some values may be
 * missing from the returned array, but the operation is safe.
 */
  public long[] toArray(){
    long[] a=new long[size];
    int next=0;
    segments:     for (Segment seg=head; seg != null; seg=seg.next()) {
      for (int i=0; i < seg.size(); i+=1) {
        if (next >= a.length) {
          break segments;
        }
        a[next]=seg.get(i);
        next+=1;
      }
    }
    return a;
  }
  /** 
 * Returns whether this list contains the given offset.
 */
  boolean contains(  long offset){
    for (Segment seg=head; seg != null; seg=seg.next()) {
      for (int i=0; i < seg.size(); i+=1) {
        if (seg.get(i) == offset) {
          return true;
        }
      }
    }
    return false;
  }
  /** 
 * One segment of a OffsetList containing at most SEGMENT_CAPACITY values.
 * public for Sizeof.
 */
// START_OF_STATIC_CLASS 
// static class Segment {
//     private int index;
//     private Segment next;
//     private int[] values;
//     public Segment(){
//       values=new int[SEGMENT_CAPACITY];
//     }
//     /** 
//  * Call this method on the tail.  The new tail is returned, if
//  * allocating a new tail is necessary.
//  */
//     Segment add(    long value){
//       if (index < values.length) {
//         values[index]=(int)value;
//         index+=1;
//         return this;
//       }
//  else {
//         Segment seg=new Segment();
//         seg.values[0]=(int)value;
//         seg.index=1;
//         next=seg;
//         return seg;
//       }
//     }
//     /** 
//  * Returns the value at the given index from this segment only.
//  */
//     long get(    int i){
//       return ((long)values[i]) & 0xFFFFFFFF;
//     }
//     /** 
//  * Returns the next segment or null if this is the tail segment.
//  */
//     Segment next(){
//       return next;
//     }
//     /** 
//  * Sets the next pointer during a merge.
//  */
//     void setNext(    Segment next){
//       this.next=next;
//     }
//     /** 
//  * Returns the number of values in this segment.
//  */
//     int size(){
//       return index;
//     }
//   }
// END_OF_STATIC_CLASS 
}
