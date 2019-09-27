/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../LNInfo.ump"
public class LNInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LNInfo()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../LNInfo.ump"
  public int getMemorySize(){
    int size = MemoryBudget.LN_INFO_OVERHEAD;
	if (ln != null) {
	    size += ln.getMemorySizeIncludedByParent();
	}
	if (key != null) {
	    size += MemoryBudget.byteArraySize(key.length);
	}
	if (dupKey != null) {
	    size += MemoryBudget.byteArraySize(dupKey.length);
	}
	return size;
  }

}