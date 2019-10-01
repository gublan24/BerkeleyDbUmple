/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.BitSet;

// line 3 "../../../../BitMap.ump"
public class BitMap
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BitMap()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 17 "../../../../BitMap.ump"
   public  BitMap(){
    bitSegments = new HashMap();
  }

  // line 21 "../../../../BitMap.ump"
   public void set(long index) throws IndexOutOfBoundsException{
    if (index < 0) {
	    throw new IndexOutOfBoundsException(index + " is negative.");
	}
	BitSet bitset = getBitSet(index, true);
	if (bitset == null) {
	    throw new IllegalArgumentException(index + " is out of bounds");
	}
	int useIndex = getIntIndex(index);
	bitset.set(useIndex);
  }

  // line 33 "../../../../BitMap.ump"
   public boolean get(long index) throws IndexOutOfBoundsException{
    if (index < 0) {
	    throw new IndexOutOfBoundsException(index + " is negative.");
	}
	BitSet bitset = getBitSet(index, false);
	if (bitset == null) {
	    return false;
	}
	int useIndex = getIntIndex(index);
	return bitset.get(useIndex);
  }

  // line 45 "../../../../BitMap.ump"
   private BitSet getBitSet(long index, boolean allowCreate){
    Long segmentId = new Long(index >> SEGMENT_SIZE);
	BitSet bitset = (BitSet) bitSegments.get(segmentId);
	if (allowCreate) {
	    if (bitset == null) {
		bitset = new BitSet();
		bitSegments.put(segmentId, bitset);
	    }
	}
	return bitset;
  }

  // line 57 "../../../../BitMap.ump"
   private int getIntIndex(long index){
    return (int) (index & SEGMENT_MASK);
  }

  // line 61 "../../../../BitMap.ump"
  public int getNumSegments(){
    return bitSegments.size();
  }

  // line 65 "../../../../BitMap.ump"
  public int cardinality(){
    int count = 0;
	Iterator iter = bitSegments.values().iterator();
	while (iter.hasNext()) {
	    BitSet b = (BitSet) iter.next();
	    count += b.cardinality();
	}
	return count;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../BitMap.ump"
  private static final int SEGMENT_SIZE = 16 ;
// line 12 "../../../../BitMap.ump"
  private static final int SEGMENT_MASK = 0xffff ;
// line 14 "../../../../BitMap.ump"
  private Map bitSegments ;

  
}