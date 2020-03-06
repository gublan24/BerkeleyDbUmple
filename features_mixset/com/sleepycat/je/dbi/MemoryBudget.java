/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.DBIN;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.DatabaseException;
import java.util.Iterator;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.latch.LatchSupport;

// line 3 "../../../../MemoryBudget.ump"
// line 3 "../../../../Statistics_MemoryBudget.ump"
// line 3 "../../../../MemoryBudget_MemoryBudget.ump"
// line 3 "../../../../Evictor_MemoryBudget.ump"
// line 4 "../../../../Evictor_MemoryBudget_inner.ump"
// line 3 "../../../../Latches_MemoryBudget.ump"
// line 3 "../../../../Derivative_Latches_MemoryBudget_MemoryBudget.ump"
// line 3 "../../../../Derivative_Latches_MemoryBudget_MemoryBudget_inner.ump"
// line 3 "../../../../Derivative_Evictor_MemoryBudget_MemoryBudget.ump"
// line 3 "../../../../Derivative_Evictor_MemoryBudget_MemoryBudget_inner.ump"
public class MemoryBudget implements EnvConfigObserver
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MemoryBudget()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 28 "../../../../MemoryBudget.ump"
   private static  void sinit(){
    
  }

  // line 31 "../../../../MemoryBudget.ump"
  public  MemoryBudget(EnvironmentImpl envImpl, DbConfigManager configManager) throws DatabaseException{
    this.envImpl = envImpl;
			envImpl.addConfigObserver(this);
			reset(configManager);
			//this.hook351(configManager);
      Label351:
inOverhead = IN.computeOverhead(configManager);
			binOverhead = BIN.computeOverhead(configManager);
			dinOverhead = DIN.computeOverhead(configManager);
			dbinOverhead = DBIN.computeOverhead(configManager);
   ;
  }


  /**
   * 
   * Respond to config updates.
   */
  // line 42 "../../../../MemoryBudget.ump"
   public void envConfigUpdate(DbConfigManager configManager) throws DatabaseException{
    long oldLogBufferBudget = logBufferBudget;
	reset(configManager);
	if (oldLogBufferBudget != logBufferBudget) {
	    envImpl.getLogManager().resetPool(configManager);
	}
  }


  /**
   * 
   * Initialize at construction time and when the cache is resized.
   */
  // line 53 "../../../../MemoryBudget.ump"
   private void reset(DbConfigManager configManager) throws DatabaseException{
    /* 
         * Calculate the total memory allotted to JE.
         * 1. If je.maxMemory is specified, use that. Check that it's
         * not more than the jvm memory.
         * 2. Otherwise, take je.maxMemoryPercent * JVM max memory.
         */
        long newMaxMemory =
            configManager.getLong(EnvironmentParams.MAX_MEMORY);
        long jvmMemory = getRuntimeMaxMemory();

        if (newMaxMemory != 0) {
            /* Application specified a cache size number, validate it. */
            if (jvmMemory < newMaxMemory) {
                throw new IllegalArgumentException
                    (EnvironmentParams.MAX_MEMORY.getName() +
                     " has a value of " + newMaxMemory +
                     " but the JVM is only configured for " +
                     jvmMemory +
                     ". Consider using je.maxMemoryPercent.");
            }
            if (newMaxMemory < MIN_MAX_MEMORY_SIZE) {
                throw new IllegalArgumentException
                    (EnvironmentParams.MAX_MEMORY.getName() +
                     " is " + newMaxMemory +
                     " which is less than the minimum: " +
                     MIN_MAX_MEMORY_SIZE);
            }
        } else {

            /*
             * When no explicit cache size is specified and the JVM memory size
             * is unknown, assume a default sized (64 MB) heap.  This produces
             * a reasonable cache size when no heap size is known.
             */
            if (jvmMemory == Long.MAX_VALUE) {
                jvmMemory = N_64MB;
            }

            /* Use the configured percentage of the JVM memory size. */
            int maxMemoryPercent =
                configManager.getInt(EnvironmentParams.MAX_MEMORY_PERCENT);
            newMaxMemory = (maxMemoryPercent * jvmMemory) / 100;
        }

        /*
	 * Calculate the memory budget for log buffering.  If the LOG_MEM_SIZE
	 * parameter is not set, start by using 7% (1/16th) of the cache
	 * size. If it is set, use that explicit setting.
	 * 
	 * No point in having more log buffers than the maximum size. If
	 * this starting point results in overly large log buffers,
	 * reduce the log buffer budget again.
         */
        long newLogBufferBudget =
            configManager.getLong(EnvironmentParams.LOG_MEM_SIZE);	    
        if (newLogBufferBudget == 0) {
	    newLogBufferBudget = newMaxMemory >> 4;
	} else if (newLogBufferBudget > newMaxMemory / 2) {
            newLogBufferBudget = newMaxMemory / 2;
        }

        /* 
         * We have a first pass at the log buffer budget. See what
         * size log buffers result. Don't let them be too big, it would
         * be a waste.
         */
        int numBuffers =
	    configManager.getInt(EnvironmentParams.NUM_LOG_BUFFERS);
        long startingBufferSize = newLogBufferBudget / numBuffers; 
        int logBufferSize =
            configManager.getInt(EnvironmentParams.LOG_BUFFER_MAX_SIZE);
        if (startingBufferSize > logBufferSize) {
            startingBufferSize = logBufferSize;
            newLogBufferBudget = numBuffers * startingBufferSize;
        } else if (startingBufferSize <
		   EnvironmentParams.MIN_LOG_BUFFER_SIZE) {
            startingBufferSize = EnvironmentParams.MIN_LOG_BUFFER_SIZE;
            newLogBufferBudget = numBuffers * startingBufferSize;
	}

        Label350:
;
						long newCriticalThreshold =
            (newMaxMemory * 
             envImpl.getConfigManager().getInt
                (EnvironmentParams.EVICTOR_CRITICAL_PERCENTAGE))/100;
 ;


        long newTrackerBudget =
            (newMaxMemory * 
             envImpl.getConfigManager().getInt
                (EnvironmentParams.CLEANER_DETAIL_MAX_MEMORY_PERCENTAGE))/100;

        /* 
         * If all has gone well, update the budget fields.  Once the log buffer
         * budget is determined, the remainder of the memory is left for tree
         * nodes.
         */
        maxMemory = newMaxMemory;
        Label349:
criticalThreshold = newCriticalThreshold;
 ;

        logBufferBudget = newLogBufferBudget;
    // line 490 "../../../../MemoryBudget_MemoryBudget.ump"
    trackerBudget = true?newTrackerBudget:newMaxMemory;
            cacheBudget = newMaxMemory - newLogBufferBudget;
    	nLockTables = 
                configManager.getInt(EnvironmentParams.N_LOCK_TABLES);
    	lockMemoryUsage = new long[nLockTables];
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Returns Runtime.maxMemory(), accounting for a MacOS bug. May return Long.MAX_VALUE if there is no inherent limit. Used by unit tests as well as by this class.
   */
  // line 158 "../../../../MemoryBudget.ump"
   public static  long getRuntimeMaxMemory(){
    if ("Mac OS X".equals(System.getProperty("os.name"))) {
	    String jvmVersion = System.getProperty("java.version");
	    if (jvmVersion != null && jvmVersion.startsWith("1.4.2")) {
		return Long.MAX_VALUE;
	    }
	}
	return Runtime.getRuntime().maxMemory();
  }

  // line 168 "../../../../MemoryBudget.ump"
   public long getLogBufferBudget(){
    return logBufferBudget;
  }

  // line 172 "../../../../MemoryBudget.ump"
   public long getMaxMemory(){
    return maxMemory;
  }


  /**
   * 
   * Public for testing.
   */
  // line 298 "../../../../MemoryBudget_MemoryBudget.ump"
   public long calcTreeCacheUsage() throws DatabaseException{
    long totalSize = 0;
			INList inList = envImpl.getInMemoryINs();
			Label347:
inList.latchMajor();
			
try {
			Iterator iter = inList.iterator();
			while (iter.hasNext()) {
					IN in = (IN) iter.next();
					long size = in.getInMemorySize();
					totalSize += size;
			}
	} 

finally {
Label347_1:
inList.releaseMajorLatch();
 ;
	}

		return totalSize;
  }


  /**
   * 
   * Initialize the starting environment memory state
   */
  // line 334 "../../../../MemoryBudget_MemoryBudget.ump"
  public void initCacheMemoryUsage() throws DatabaseException{
    synchronized (memoryUsageSynchronizer) {
	    treeMemoryUsage = calcTreeCacheUsage();
	}
    // line 10 "../../../../Derivative_Latches_MemoryBudget_MemoryBudget.ump"
    assert LatchSupport.countLatchesHeld() == 0;
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Update the environment wide tree memory count, wake up the evictor if
   * necessary.
   * @param increment note that increment may be negative.
   */
  // line 369 "../../../../MemoryBudget_MemoryBudget.ump"
   public void updateTreeMemoryUsage(long increment){
    synchronized (memoryUsageSynchronizer) {
	    treeMemoryUsage += increment;
	}
    // line 16 "../../../../Derivative_Evictor_MemoryBudget_MemoryBudget.ump"
    //original(increment);
    				if (getCacheMemoryUsage() > cacheBudget) {
    						envImpl.alertEvictor();
    				}
    // END OF UMPLE AFTER INJECTION
  }


  /**
   * 
   * Update the environment wide misc memory count, wake up the evictor if
   * necessary.
   * @param increment note that increment may be negative.
   */
  // line 381 "../../../../MemoryBudget_MemoryBudget.ump"
   public void updateMiscMemoryUsage(long increment){
    synchronized (memoryUsageSynchronizer) {
	    miscMemoryUsage += increment;
	}
    // line 27 "../../../../Derivative_Evictor_MemoryBudget_MemoryBudget.ump"
    //original(increment);
    			if (getCacheMemoryUsage() > cacheBudget) {
    					envImpl.alertEvictor();
    			}
    // END OF UMPLE AFTER INJECTION
  }

  // line 388 "../../../../MemoryBudget_MemoryBudget.ump"
   public void updateLockMemoryUsage(long increment, int lockTableIndex){
    lockMemoryUsage[lockTableIndex] += increment;
    // line 34 "../../../../Derivative_Evictor_MemoryBudget_MemoryBudget.ump"
    //original(increment, lockTableIndex);
    			if (getCacheMemoryUsage() > cacheBudget) {
    					envImpl.alertEvictor();
    			}
    // END OF UMPLE AFTER INJECTION
  }

  // line 393 "../../../../MemoryBudget_MemoryBudget.ump"
   public long accumulateNewUsage(IN in, long newSize){
    return in.getInMemorySize() + newSize;
  }

  // line 397 "../../../../MemoryBudget_MemoryBudget.ump"
   public void refreshTreeMemoryUsage(long newSize){
    synchronized (memoryUsageSynchronizer) {
	    treeMemoryUsage = newSize;
	}
  }

  // line 403 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getCacheMemoryUsage(){
    long accLockMemoryUsage = 0;
	if (nLockTables == 1) {
	    accLockMemoryUsage = lockMemoryUsage[0];
	} else {
	    for (int i = 0; i < nLockTables; i++) {
		accLockMemoryUsage += lockMemoryUsage[i];
	    }
	}
	return treeMemoryUsage + miscMemoryUsage + accLockMemoryUsage;
  }


  /**
   * 
   * Used for unit testing.
   */
  // line 418 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getTreeMemoryUsage(){
    return treeMemoryUsage;
  }


  /**
   * 
   * Returns the memory size occupied by a byte array of a given length.
   */
  // line 430 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getTrackerBudget(){
    return trackerBudget;
  }

  // line 435 "../../../../MemoryBudget_MemoryBudget.ump"
   public static  int byteArraySize(int arrayLen){
    /*
         * BYTE_ARRAY_OVERHEAD accounts for 4 bytes of data.  Data larger than
         * 4 bytes is allocated in 8 byte increments.
         */
        int size = BYTE_ARRAY_OVERHEAD;
        if (arrayLen > 4) {
            size += ((arrayLen - 4 + 7) / 8) * 8;
        }

        return size;
  }

  // line 448 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getCacheBudget(){
    return cacheBudget;
  }

  // line 452 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getINOverhead(){
    return inOverhead;
  }

  // line 456 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getBINOverhead(){
    return binOverhead;
  }

  // line 460 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getDINOverhead(){
    return dinOverhead;
  }

  // line 464 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getDBINOverhead(){
    return dbinOverhead;
  }

  // line 8 "../../../../Derivative_Evictor_MemoryBudget_MemoryBudget.ump"
   public long getCriticalThreshold(){
    return criticalThreshold;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../MemoryBudget.ump"
  public final static long MIN_MAX_MEMORY_SIZE = 96 * 1024 ;
// line 16 "../../../../MemoryBudget.ump"
  public final static String MIN_MAX_MEMORY_SIZE_STRING = Long.toString(MIN_MAX_MEMORY_SIZE) ;
// line 18 "../../../../MemoryBudget.ump"
  private final static long N_64MB = (1 << 26) ;
// line 20 "../../../../MemoryBudget.ump"
  private long maxMemory ;
// line 22 "../../../../MemoryBudget.ump"
  private long logBufferBudget ;
// line 24 "../../../../MemoryBudget.ump"
  private EnvironmentImpl envImpl ;
// line 5 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LONG_OVERHEAD_32 = 16 ;
// line 6 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LONG_OVERHEAD_64 = 24 ;
// line 9 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BYTE_ARRAY_OVERHEAD_32 = 16 ;
// line 10 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BYTE_ARRAY_OVERHEAD_64 = 24 ;
// line 13 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int OBJECT_OVERHEAD_32 = 8 ;
// line 14 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int OBJECT_OVERHEAD_64 = 16 ;
// line 17 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int ARRAY_ITEM_OVERHEAD_32 = 4 ;
// line 18 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int ARRAY_ITEM_OVERHEAD_64 = 8 ;
// line 21 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_OVERHEAD_32 = 120 ;
// line 22 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_OVERHEAD_64 = 216 ;
// line 25 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_ENTRY_OVERHEAD_32 = 24 ;
// line 26 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_ENTRY_OVERHEAD_64 = 48 ;
// line 29 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_OVERHEAD_32 = 136 ;
// line 30 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_OVERHEAD_64 = 240 ;
// line 33 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_ENTRY_OVERHEAD_32 = 24 ;
// line 34 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_ENTRY_OVERHEAD_64 = 48 ;
// line 37 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TWOHASHMAPS_OVERHEAD_32 = 240 ;
// line 38 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TWOHASHMAPS_OVERHEAD_64 = 432 ;
// line 41 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_OVERHEAD_32 = 40 ;
// line 42 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_OVERHEAD_64 = 64 ;
// line 45 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_ENTRY_OVERHEAD_32 = 32 ;
// line 46 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_ENTRY_OVERHEAD_64 = 53 ;
// line 49 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_OVERHEAD_32 = 24 ;
// line 50 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_OVERHEAD_64 = 32 ;
// line 53 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DUPCOUNTLN_OVERHEAD_32 = 24 ;
// line 54 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DUPCOUNTLN_OVERHEAD_64 = 40 ;
// line 57 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_32_14 = 344 ;
// line 58 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_32_15 = 360 ;
// line 59 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_64_15 = 528 ;
// line 62 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_32_14 = 352 ;
// line 63 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_32_15 = 360 ;
// line 64 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_64_15 = 536 ;
// line 67 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_32_14 = 352 ;
// line 68 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_32_15 = 368 ;
// line 69 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_64_15 = 544 ;
// line 72 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_32_14 = 312 ;
// line 73 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_32_15 = 320 ;
// line 74 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_64_15 = 472 ;
// line 77 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int KEY_OVERHEAD_32 = 16 ;
// line 78 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int KEY_OVERHEAD_64 = 24 ;
// line 81 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCK_OVERHEAD_32 = 32 ;
// line 82 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCK_OVERHEAD_64 = 56 ;
// line 85 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCKINFO_OVERHEAD_32 = 16 ;
// line 86 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCKINFO_OVERHEAD_64 = 32 ;
// line 93 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TXN_OVERHEAD_32_14 = 167 ;
// line 94 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TXN_OVERHEAD_32_15 = 175 ;
// line 95 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TXN_OVERHEAD_64_15 = 293 ;
// line 98 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_32_14 = 32 +
        HASHSET_ENTRY_OVERHEAD_32 ;
// line 100 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_32_15 = 40 +
        HASHSET_ENTRY_OVERHEAD_32 ;
// line 102 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_64_15 = 56 +
        HASHSET_ENTRY_OVERHEAD_64 ;
// line 107 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int UTILIZATION_PROFILE_ENTRY_32 = 88 ;
// line 108 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int UTILIZATION_PROFILE_ENTRY_64 = 136 ;
// line 112 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_INITIAL_OVERHEAD_32 = 464 ;
// line 113 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_INITIAL_OVERHEAD_64 = 504 ;
// line 116 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_SEGMENT_OVERHEAD_32 = 440 ;
// line 117 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_SEGMENT_OVERHEAD_64 = 464 ;
// line 120 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_INFO_OVERHEAD_32 = 24 ;
// line 121 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_INFO_OVERHEAD_64 = 48 ;
// line 125 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LONG_LIST_PER_ITEM_OVERHEAD_32 = 20 ;
// line 126 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LONG_LIST_PER_ITEM_OVERHEAD_64 = 32 ;
// line 128 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int LONG_OVERHEAD ;
// line 129 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int BYTE_ARRAY_OVERHEAD ;
// line 130 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int OBJECT_OVERHEAD ;
// line 131 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int ARRAY_ITEM_OVERHEAD ;
// line 132 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int HASHMAP_OVERHEAD ;
// line 133 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int HASHMAP_ENTRY_OVERHEAD ;
// line 134 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int HASHSET_OVERHEAD ;
// line 135 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int HASHSET_ENTRY_OVERHEAD ;
// line 136 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int TWOHASHMAPS_OVERHEAD ;
// line 137 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int TREEMAP_OVERHEAD ;
// line 138 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int TREEMAP_ENTRY_OVERHEAD ;
// line 139 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int LN_OVERHEAD ;
// line 140 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int DUPCOUNTLN_OVERHEAD ;
// line 141 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int BIN_FIXED_OVERHEAD ;
// line 142 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int DIN_FIXED_OVERHEAD ;
// line 143 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int DBIN_FIXED_OVERHEAD ;
// line 144 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int IN_FIXED_OVERHEAD ;
// line 145 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int KEY_OVERHEAD ;
// line 146 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int LOCK_OVERHEAD ;
// line 147 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int LOCKINFO_OVERHEAD ;
// line 148 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int TXN_OVERHEAD ;
// line 149 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int CHECKPOINT_REFERENCE_SIZE ;
// line 150 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int UTILIZATION_PROFILE_ENTRY ;
// line 151 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int TFS_LIST_INITIAL_OVERHEAD ;
// line 152 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int TFS_LIST_SEGMENT_OVERHEAD ;
// line 153 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int LN_INFO_OVERHEAD ;
// line 154 "../../../../MemoryBudget_MemoryBudget.ump"
  public final static int LONG_LIST_PER_ITEM_OVERHEAD ;
// line 156 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static String JVM_ARCH_PROPERTY = "sun.arch.data.model" ;
// line 157 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static String FORCE_JVM_ARCH = "je.forceJVMArch" ;

// line 159 "../../../../MemoryBudget_MemoryBudget.ump"
  static 
  {
    boolean is64 = false;
	boolean isJVM14 = (LatchSupport.getJava5LatchClass() == null);//TODO default value necessary
	String overrideArch = System.getProperty(FORCE_JVM_ARCH);
	try {
	    if (overrideArch == null) {
		String arch = System.getProperty(JVM_ARCH_PROPERTY);
		if (arch != null) {
		    is64 = Integer.parseInt(arch) == 64;
		}
	    } else {
		is64 = Integer.parseInt(overrideArch) == 64;
	    }
	} catch (NumberFormatException NFE) {
	    NFE.printStackTrace(System.err);
	}

	if (is64) {
	    if (isJVM14) {
		RuntimeException RE = new RuntimeException
		    ("1.4 based 64 bit JVM not supported");
		RE.printStackTrace(System.err);
		throw RE;
	    }
	    LONG_OVERHEAD = LONG_OVERHEAD_64;
	    BYTE_ARRAY_OVERHEAD = BYTE_ARRAY_OVERHEAD_64;
	    OBJECT_OVERHEAD = OBJECT_OVERHEAD_64;
	    ARRAY_ITEM_OVERHEAD = ARRAY_ITEM_OVERHEAD_64;
	    HASHMAP_OVERHEAD = HASHMAP_OVERHEAD_64;
	    HASHMAP_ENTRY_OVERHEAD = HASHMAP_ENTRY_OVERHEAD_64;
	    HASHSET_OVERHEAD = HASHSET_OVERHEAD_64;
	    HASHSET_ENTRY_OVERHEAD = HASHSET_ENTRY_OVERHEAD_64;
	    TWOHASHMAPS_OVERHEAD = TWOHASHMAPS_OVERHEAD_64;
	    TREEMAP_OVERHEAD = TREEMAP_OVERHEAD_64;
	    TREEMAP_ENTRY_OVERHEAD = TREEMAP_ENTRY_OVERHEAD_64;
	    LN_OVERHEAD = LN_OVERHEAD_64;
	    DUPCOUNTLN_OVERHEAD = DUPCOUNTLN_OVERHEAD_64;
	    BIN_FIXED_OVERHEAD = BIN_FIXED_OVERHEAD_64_15;
	    DIN_FIXED_OVERHEAD = DIN_FIXED_OVERHEAD_64_15;
	    DBIN_FIXED_OVERHEAD = DBIN_FIXED_OVERHEAD_64_15;
	    IN_FIXED_OVERHEAD = IN_FIXED_OVERHEAD_64_15;
	    TXN_OVERHEAD = TXN_OVERHEAD_64_15;
	    CHECKPOINT_REFERENCE_SIZE = CHECKPOINT_REFERENCE_SIZE_64_15;
	    KEY_OVERHEAD = KEY_OVERHEAD_64;
	    LOCK_OVERHEAD = LOCK_OVERHEAD_64;
	    LOCKINFO_OVERHEAD = LOCKINFO_OVERHEAD_64;
	    UTILIZATION_PROFILE_ENTRY = UTILIZATION_PROFILE_ENTRY_64;
	    TFS_LIST_INITIAL_OVERHEAD = TFS_LIST_INITIAL_OVERHEAD_64;
	    TFS_LIST_SEGMENT_OVERHEAD = TFS_LIST_SEGMENT_OVERHEAD_64;
	    LN_INFO_OVERHEAD = LN_INFO_OVERHEAD_64;
	    LONG_LIST_PER_ITEM_OVERHEAD = LONG_LIST_PER_ITEM_OVERHEAD_64;
	} else {
	    LONG_OVERHEAD = LONG_OVERHEAD_32;
	    BYTE_ARRAY_OVERHEAD = BYTE_ARRAY_OVERHEAD_32;
	    OBJECT_OVERHEAD = OBJECT_OVERHEAD_32;
	    ARRAY_ITEM_OVERHEAD = ARRAY_ITEM_OVERHEAD_32;
	    HASHMAP_OVERHEAD = HASHMAP_OVERHEAD_32;
	    HASHMAP_ENTRY_OVERHEAD = HASHMAP_ENTRY_OVERHEAD_32;
	    HASHSET_OVERHEAD = HASHSET_OVERHEAD_32;
	    HASHSET_ENTRY_OVERHEAD = HASHSET_ENTRY_OVERHEAD_32;
	    TWOHASHMAPS_OVERHEAD = TWOHASHMAPS_OVERHEAD_32;
	    TREEMAP_OVERHEAD = TREEMAP_OVERHEAD_32;
	    TREEMAP_ENTRY_OVERHEAD = TREEMAP_ENTRY_OVERHEAD_32;
	    LN_OVERHEAD = LN_OVERHEAD_32;
	    DUPCOUNTLN_OVERHEAD = DUPCOUNTLN_OVERHEAD_32;
	    if (isJVM14) {
		BIN_FIXED_OVERHEAD = BIN_FIXED_OVERHEAD_32_14;
		DIN_FIXED_OVERHEAD = DIN_FIXED_OVERHEAD_32_14;
		DBIN_FIXED_OVERHEAD = DBIN_FIXED_OVERHEAD_32_14;
		IN_FIXED_OVERHEAD = IN_FIXED_OVERHEAD_32_14;
		TXN_OVERHEAD = TXN_OVERHEAD_32_14;
		CHECKPOINT_REFERENCE_SIZE = CHECKPOINT_REFERENCE_SIZE_32_14;
	    } else {
		BIN_FIXED_OVERHEAD = BIN_FIXED_OVERHEAD_32_15;
		DIN_FIXED_OVERHEAD = DIN_FIXED_OVERHEAD_32_15;
		DBIN_FIXED_OVERHEAD = DBIN_FIXED_OVERHEAD_32_15;
		IN_FIXED_OVERHEAD = IN_FIXED_OVERHEAD_32_15;
		TXN_OVERHEAD = TXN_OVERHEAD_32_15;
		CHECKPOINT_REFERENCE_SIZE = CHECKPOINT_REFERENCE_SIZE_32_15;
	    }
	    KEY_OVERHEAD = KEY_OVERHEAD_32;
	    LOCK_OVERHEAD = LOCK_OVERHEAD_32;
	    LOCKINFO_OVERHEAD = LOCKINFO_OVERHEAD_32;
	    UTILIZATION_PROFILE_ENTRY = UTILIZATION_PROFILE_ENTRY_32;
	    TFS_LIST_INITIAL_OVERHEAD = TFS_LIST_INITIAL_OVERHEAD_32;
	    TFS_LIST_SEGMENT_OVERHEAD = TFS_LIST_SEGMENT_OVERHEAD_32;
	    LN_INFO_OVERHEAD = LN_INFO_OVERHEAD_32;
	    LONG_LIST_PER_ITEM_OVERHEAD = LONG_LIST_PER_ITEM_OVERHEAD_32;
	}
  }
// line 251 "../../../../MemoryBudget_MemoryBudget.ump"
  private long treeMemoryUsage ;
// line 256 "../../../../MemoryBudget_MemoryBudget.ump"
  private long miscMemoryUsage ;
// line 261 "../../../../MemoryBudget_MemoryBudget.ump"
  private Object memoryUsageSynchronizer = new Object() ;
// line 266 "../../../../MemoryBudget_MemoryBudget.ump"
  private int nLockTables ;
// line 273 "../../../../MemoryBudget_MemoryBudget.ump"
  private long[] lockMemoryUsage ;
// line 277 "../../../../MemoryBudget_MemoryBudget.ump"
  private long trackerBudget ;
// line 283 "../../../../MemoryBudget_MemoryBudget.ump"
  private long cacheBudget ;
// line 288 "../../../../MemoryBudget_MemoryBudget.ump"
  private long inOverhead ;
// line 289 "../../../../MemoryBudget_MemoryBudget.ump"
  private long binOverhead ;
// line 290 "../../../../MemoryBudget_MemoryBudget.ump"
  private long dinOverhead ;
// line 291 "../../../../MemoryBudget_MemoryBudget.ump"
  private long dbinOverhead ;
// line 5 "../../../../Derivative_Evictor_MemoryBudget_MemoryBudget.ump"
  private long criticalThreshold ;

  
}