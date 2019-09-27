/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.MemoryBudget;
import java.util.TreeMap;
import java.util.SortedMap;

// line 3 "../../../../LookAheadCache.ump"
public class LookAheadCache
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LookAheadCache()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 16 "../../../../LookAheadCache.ump"
  public  LookAheadCache(int lookAheadCacheSize){
    map = new TreeMap();
	maxMem = lookAheadCacheSize;
  }

  // line 21 "../../../../LookAheadCache.ump"
  public boolean isEmpty(){
    return map.isEmpty();
  }

  // line 25 "../../../../LookAheadCache.ump"
  public boolean isFull(){
    return usedMem >= maxMem;
  }

  // line 29 "../../../../LookAheadCache.ump"
  public Long nextOffset(){
    return (Long) map.firstKey();
  }

  // line 33 "../../../../LookAheadCache.ump"
  public void add(Long lsnOffset, LNInfo info){
    map.put(lsnOffset, info);
	usedMem++;
	this.hook166(info);
  }

  // line 39 "../../../../LookAheadCache.ump"
  public LNInfo remove(Long offset){
    LNInfo info = (LNInfo) map.remove(offset);
	if (info != null) {
	    this.hook167(info);
	}
	return info;
  }

  // line 47 "../../../../LookAheadCache.ump"
   protected void hook166(LNInfo info){
    
  }

  // line 50 "../../../../LookAheadCache.ump"
   protected void hook167(LNInfo info){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../LookAheadCache.ump"
  private SortedMap map ;
// line 11 "../../../../LookAheadCache.ump"
  private int maxMem ;
// line 13 "../../../../LookAheadCache.ump"
  private int usedMem ;

  
}