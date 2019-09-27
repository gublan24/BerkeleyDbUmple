/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../DbCacheSize.ump"
// line 3 "../../../../DbCacheSize_inner.ump"
public class DbCacheSize
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbCacheSize()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../DbCacheSize.ump"
   private static  void printStats(PrintStream out, Environment env, String msg) throws DatabaseException{
    out.println();
	out.println(msg + ':');
	EnvironmentStats stats = env.getStats(null);
	out.println("CacheSize=" + INT_FORMAT.format(stats.getCacheTotalBytes()) + " BtreeSize="
		+ INT_FORMAT.format(stats.getCacheDataBytes()));
	if (stats.getNNodesScanned() > 0) {
	    out.println("*** All records did not fit in the cache ***");
	}
  }

  // line 18 "../../../../DbCacheSize.ump"
   protected static  void hook831(PrintStream out, Environment env) throws DatabaseException{
    printStats(out, env, "Stats for internal nodes only (after preload)");
	original(out, env);
  }

  // line 23 "../../../../DbCacheSize.ump"
   protected static  void hook832(PrintStream out, Environment env) throws DatabaseException{
    printStats(out, env, "Stats for internal and leaf nodes (after insert)");
	original(out, env);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.util;
  
  @MethodObject
  // line 4 "../../../../DbCacheSize_inner.ump"
  public static class DbCacheSize_insertRecords
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbCacheSize_insertRecords()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DbCacheSize_inner.ump"
     protected void hook833() throws DatabaseException{
      stats=env.getStats(null);
          if (stats.getNNodesScanned() > 0) {
            out.println("*** Ran out of cache memory at record " + i + " -- try increasing the Java heap size ***");
            throw new ReturnVoid();
          }
          original();
    }
  
  }
}