/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.MemoryBudget;
import java.util.TreeMap;
import java.util.SortedMap;

// line 3 "../../../../LookAHEADCache_LookAheadCache.ump"
// line 3 "../../../../Derivative_LookAHEADCache_MemoryBudget_LookAheadCache.ump"
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

  // line 16 "../../../../LookAHEADCache_LookAheadCache.ump"
  public  LookAheadCache(int lookAheadCacheSize){
    map = new TreeMap();
	maxMem = lookAheadCacheSize;
  }

  // line 21 "../../../../LookAHEADCache_LookAheadCache.ump"
  public boolean isEmpty(){
    return map.isEmpty();
  }

  // line 25 "../../../../LookAHEADCache_LookAheadCache.ump"
  public boolean isFull(){
    return usedMem >= maxMem;
  }

  // line 29 "../../../../LookAHEADCache_LookAheadCache.ump"
  public Long nextOffset(){
    return (Long) map.firstKey();
  }

  // line 33 "../../../../LookAHEADCache_LookAheadCache.ump"
  public void add(Long lsnOffset, LNInfo info){
    map.put(lsnOffset, info);
	usedMem++;
	Label166:
usedMem += info.getMemorySize();
	usedMem += MemoryBudget.TREEMAP_ENTRY_OVERHEAD - 1;
	//original(info);
 ;//this.hook166(info);
  }

  // line 39 "../../../../LookAHEADCache_LookAheadCache.ump"
  public LNInfo remove(Long offset){
    LNInfo info = (LNInfo) map.remove(offset);
	if (info != null) {
	    Label167:
usedMem--;
	usedMem -= info.getMemorySize();
	usedMem -= MemoryBudget.TREEMAP_ENTRY_OVERHEAD + 1;
	//original(info);
 ;//this.hook167(info);
	}
	return info;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../LookAHEADCache_LookAheadCache.ump"
  private SortedMap map ;
// line 11 "../../../../LookAHEADCache_LookAheadCache.ump"
  private int maxMem ;
// line 13 "../../../../LookAHEADCache_LookAheadCache.ump"
  private int usedMem ;

  
}