/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

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

  // line 6 "../../../../LookAheadCache.ump"
  public  LookAheadCache(int lookAheadCacheSize){
    usedMem = MemoryBudget.TREEMAP_OVERHEAD;
  }

  // line 10 "../../../../LookAheadCache.ump"
   protected void hook166(LNInfo info){
    usedMem += info.getMemorySize();
	usedMem += MemoryBudget.TREEMAP_ENTRY_OVERHEAD - 1;
	original(info);
  }

  // line 16 "../../../../LookAheadCache.ump"
   protected void hook167(LNInfo info){
    usedMem--;
	usedMem -= info.getMemorySize();
	usedMem -= MemoryBudget.TREEMAP_ENTRY_OVERHEAD + 1;
	original(info);
  }

}