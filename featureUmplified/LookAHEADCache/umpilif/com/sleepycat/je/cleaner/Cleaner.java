/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../Cleaner.ump"
public class Cleaner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cleaner Attributes
  private int lookAheadCacheSize;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner(int aLookAheadCacheSize)
  {
    lookAheadCacheSize = aLookAheadCacheSize;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLookAheadCacheSize(int aLookAheadCacheSize)
  {
    boolean wasSet = false;
    lookAheadCacheSize = aLookAheadCacheSize;
    wasSet = true;
    return wasSet;
  }

  public int getLookAheadCacheSize()
  {
    return lookAheadCacheSize;
  }

  public void delete()
  {}

  // line 8 "../../../../Cleaner.ump"
   protected void hook94(DbConfigManager cm) throws DatabaseException{
    lookAheadCacheSize = cm.getInt(EnvironmentParams.CLEANER_LOOK_AHEAD_CACHE_SIZE);
	original(cm);
  }


  public String toString()
  {
    return super.toString() + "["+
            "lookAheadCacheSize" + ":" + getLookAheadCacheSize()+ "]";
  }
}