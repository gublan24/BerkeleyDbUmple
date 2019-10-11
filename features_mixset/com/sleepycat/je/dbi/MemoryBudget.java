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

// line 3 "../../../../MemoryBudget.ump"
// line 3 "../../../../MemoryBudget_static.ump"
// line 3 "../../../../MemoryBudget_MemoryBudget.ump"
// line 3 "../../../../MemoryBudget_inner_inner.ump"
// line 3 "../../../../Evictor_MemoryBudget.ump"
// line 3 "../../../../Evictor_MemoryBudget_inner.ump"
// line 3 "../../../../Statistics_MemoryBudget.ump"
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

  // line 27 "../../../../MemoryBudget.ump"
   private static  void sinit(){
    new MemoryBudget_sinit().execute();
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
			//original(configManager);

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
    new MemoryBudget_reset(this, configManager).execute();
  }


  /**
   * 
   * Returns Runtime.maxMemory(), accounting for a MacOS bug. May return Long.MAX_VALUE if there is no inherent limit. Used by unit tests as well as by this class.
   */
  // line 60 "../../../../MemoryBudget.ump"
   public static  long getRuntimeMaxMemory(){
    if ("Mac OS X".equals(System.getProperty("os.name"))) {
	    String jvmVersion = System.getProperty("java.version");
	    if (jvmVersion != null && jvmVersion.startsWith("1.4.2")) {
		return Long.MAX_VALUE;
	    }
	}
	return Runtime.getRuntime().maxMemory();
  }

  // line 70 "../../../../MemoryBudget.ump"
   public long getLogBufferBudget(){
    return logBufferBudget;
  }

  // line 74 "../../../../MemoryBudget.ump"
   public long getMaxMemory(){
    return maxMemory;
  }


  /**
   * 
   * Initialize the starting environment memory state
   */
  // line 209 "../../../../MemoryBudget_MemoryBudget.ump"
  public void initCacheMemoryUsage() throws DatabaseException{
    synchronized (memoryUsageSynchronizer) {
					treeMemoryUsage = calcTreeCacheUsage();
			}
  }


  /**
   * 
   * Public for testing.
   */
  // line 218 "../../../../MemoryBudget_MemoryBudget.ump"
   public long calcTreeCacheUsage() throws DatabaseException{
    long totalSize = 0;
			INList inList = envImpl.getInMemoryINs();
			//	totalSize = this.hook347(totalSize, inList);
			Label347:
Iterator iter = inList.iterator();
		while (iter.hasNext()) {
			  IN in = (IN) iter.next();
			  long size = in.getInMemorySize();
			  totalSize += size;
		}

		return totalSize;
  }


  /**
   * 
   * Update the environment wide tree memory count, wake up the evictor if necessary.
   * @param incrementnote that increment may be negative.
   */
  // line 230 "../../../../MemoryBudget_MemoryBudget.ump"
   public void updateTreeMemoryUsage(long increment){
    synchronized (memoryUsageSynchronizer) {
					treeMemoryUsage += increment;
			}
  }


  /**
   * 
   * Update the environment wide misc memory count, wake up the evictor if necessary.
   * @param incrementnote that increment may be negative.
   */
  // line 240 "../../../../MemoryBudget_MemoryBudget.ump"
   public void updateMiscMemoryUsage(long increment){
    synchronized (memoryUsageSynchronizer) {
					miscMemoryUsage += increment;
			}
  }

  // line 246 "../../../../MemoryBudget_MemoryBudget.ump"
   public void updateLockMemoryUsage(long increment, int lockTableIndex){
    lockMemoryUsage[lockTableIndex] += increment;
  }

  // line 250 "../../../../MemoryBudget_MemoryBudget.ump"
   public long accumulateNewUsage(IN in, long newSize){
    return in.getInMemorySize() + newSize;
  }

  // line 254 "../../../../MemoryBudget_MemoryBudget.ump"
   public void refreshTreeMemoryUsage(long newSize){
    synchronized (memoryUsageSynchronizer) {
					treeMemoryUsage = newSize;
			}
  }

  // line 260 "../../../../MemoryBudget_MemoryBudget.ump"
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
  // line 275 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getTreeMemoryUsage(){
    return treeMemoryUsage;
  }

  // line 279 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getTrackerBudget(){
    return trackerBudget;
  }

  // line 283 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getCacheBudget(){
    return cacheBudget;
  }

  // line 287 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getINOverhead(){
    return inOverhead;
  }

  // line 291 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getBINOverhead(){
    return binOverhead;
  }

  // line 295 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getDINOverhead(){
    return dinOverhead;
  }

  // line 299 "../../../../MemoryBudget_MemoryBudget.ump"
   public long getDBINOverhead(){
    return dbinOverhead;
  }


  /**
   * 
   * Returns the memory size occupied by a byte array of a given length.
   */
  // line 306 "../../../../MemoryBudget_MemoryBudget.ump"
   public static  int byteArraySize(int arrayLen){
    int size = BYTE_ARRAY_OVERHEAD;
		if (arrayLen > 4) {
			  size += ((arrayLen - 4 + 7) / 8) * 8;
		}
		return size;
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 4 "../../../../MemoryBudget_static.ump"
  // line 4 "../../../../MemoryBudget_inner_inner.ump"
  public static class MemoryBudget_sinit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public MemoryBudget_sinit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../MemoryBudget_static.ump"
    public void execute(){
      // line 7 "../../../../MemoryBudget_inner_inner.ump"
      is64=false;
              isJVM14=true;
      // END OF UMPLE BEFORE INJECTION
      this.hook348();
      // line 12 "../../../../MemoryBudget_inner_inner.ump"
      //  original();
              overrideArch=System.getProperty(FORCE_JVM_ARCH);
              try {
                if (overrideArch == null) {
                  arch=System.getProperty(JVM_ARCH_PROPERTY);
                  if (arch != null) {
                    is64=Integer.parseInt(arch) == 64;
                  }
                }
         else {
                  is64=Integer.parseInt(overrideArch) == 64;
                }
              }
         catch (      NumberFormatException NFE) {
                NFE.printStackTrace(System.err);
              }
              if (is64) {
                if (isJVM14) {
                  RE=new RuntimeException("1.4 based 64 bit JVM not supported");
                  RE.printStackTrace(System.err);
                  throw RE;
                }
                LONG_OVERHEAD=LONG_OVERHEAD_64;
                BYTE_ARRAY_OVERHEAD=BYTE_ARRAY_OVERHEAD_64;
                OBJECT_OVERHEAD=OBJECT_OVERHEAD_64;
                ARRAY_ITEM_OVERHEAD=ARRAY_ITEM_OVERHEAD_64;
                HASHMAP_OVERHEAD=HASHMAP_OVERHEAD_64;
                HASHMAP_ENTRY_OVERHEAD=HASHMAP_ENTRY_OVERHEAD_64;
                HASHSET_OVERHEAD=HASHSET_OVERHEAD_64;
                HASHSET_ENTRY_OVERHEAD=HASHSET_ENTRY_OVERHEAD_64;
                TWOHASHMAPS_OVERHEAD=TWOHASHMAPS_OVERHEAD_64;
                TREEMAP_OVERHEAD=TREEMAP_OVERHEAD_64;
                TREEMAP_ENTRY_OVERHEAD=TREEMAP_ENTRY_OVERHEAD_64;
                LN_OVERHEAD=LN_OVERHEAD_64;
                DUPCOUNTLN_OVERHEAD=DUPCOUNTLN_OVERHEAD_64;
                BIN_FIXED_OVERHEAD=BIN_FIXED_OVERHEAD_64_15;
                DIN_FIXED_OVERHEAD=DIN_FIXED_OVERHEAD_64_15;
                DBIN_FIXED_OVERHEAD=DBIN_FIXED_OVERHEAD_64_15;
                IN_FIXED_OVERHEAD=IN_FIXED_OVERHEAD_64_15;
                TXN_OVERHEAD=TXN_OVERHEAD_64_15;
                CHECKPOINT_REFERENCE_SIZE=CHECKPOINT_REFERENCE_SIZE_64_15;
                KEY_OVERHEAD=KEY_OVERHEAD_64;
                LOCK_OVERHEAD=LOCK_OVERHEAD_64;
                LOCKINFO_OVERHEAD=LOCKINFO_OVERHEAD_64;
                UTILIZATION_PROFILE_ENTRY=UTILIZATION_PROFILE_ENTRY_64;
                TFS_LIST_INITIAL_OVERHEAD=TFS_LIST_INITIAL_OVERHEAD_64;
                TFS_LIST_SEGMENT_OVERHEAD=TFS_LIST_SEGMENT_OVERHEAD_64;
                LN_INFO_OVERHEAD=LN_INFO_OVERHEAD_64;
                LONG_LIST_PER_ITEM_OVERHEAD=LONG_LIST_PER_ITEM_OVERHEAD_64;
              }
         else {
                LONG_OVERHEAD=LONG_OVERHEAD_32;
                BYTE_ARRAY_OVERHEAD=BYTE_ARRAY_OVERHEAD_32;
                OBJECT_OVERHEAD=OBJECT_OVERHEAD_32;
                ARRAY_ITEM_OVERHEAD=ARRAY_ITEM_OVERHEAD_32;
                HASHMAP_OVERHEAD=HASHMAP_OVERHEAD_32;
                HASHMAP_ENTRY_OVERHEAD=HASHMAP_ENTRY_OVERHEAD_32;
                HASHSET_OVERHEAD=HASHSET_OVERHEAD_32;
                HASHSET_ENTRY_OVERHEAD=HASHSET_ENTRY_OVERHEAD_32;
                TWOHASHMAPS_OVERHEAD=TWOHASHMAPS_OVERHEAD_32;
                TREEMAP_OVERHEAD=TREEMAP_OVERHEAD_32;
                TREEMAP_ENTRY_OVERHEAD=TREEMAP_ENTRY_OVERHEAD_32;
                LN_OVERHEAD=LN_OVERHEAD_32;
                DUPCOUNTLN_OVERHEAD=DUPCOUNTLN_OVERHEAD_32;
                if (isJVM14) {
                  BIN_FIXED_OVERHEAD=BIN_FIXED_OVERHEAD_32_14;
                  DIN_FIXED_OVERHEAD=DIN_FIXED_OVERHEAD_32_14;
                  DBIN_FIXED_OVERHEAD=DBIN_FIXED_OVERHEAD_32_14;
                  IN_FIXED_OVERHEAD=IN_FIXED_OVERHEAD_32_14;
                  TXN_OVERHEAD=TXN_OVERHEAD_32_14;
                  CHECKPOINT_REFERENCE_SIZE=CHECKPOINT_REFERENCE_SIZE_32_14;
                }
         else {
                  BIN_FIXED_OVERHEAD=BIN_FIXED_OVERHEAD_32_15;
                  DIN_FIXED_OVERHEAD=DIN_FIXED_OVERHEAD_32_15;
                  DBIN_FIXED_OVERHEAD=DBIN_FIXED_OVERHEAD_32_15;
                  IN_FIXED_OVERHEAD=IN_FIXED_OVERHEAD_32_15;
                  TXN_OVERHEAD=TXN_OVERHEAD_32_15;
                  CHECKPOINT_REFERENCE_SIZE=CHECKPOINT_REFERENCE_SIZE_32_15;
                }
                KEY_OVERHEAD=KEY_OVERHEAD_32;
                LOCK_OVERHEAD=LOCK_OVERHEAD_32;
                LOCKINFO_OVERHEAD=LOCKINFO_OVERHEAD_32;
                UTILIZATION_PROFILE_ENTRY=UTILIZATION_PROFILE_ENTRY_32;
                TFS_LIST_INITIAL_OVERHEAD=TFS_LIST_INITIAL_OVERHEAD_32;
                TFS_LIST_SEGMENT_OVERHEAD=TFS_LIST_SEGMENT_OVERHEAD_32;
                LN_INFO_OVERHEAD=LN_INFO_OVERHEAD_32;
                LONG_LIST_PER_ITEM_OVERHEAD=LONG_LIST_PER_ITEM_OVERHEAD_32;
              }
      // END OF UMPLE AFTER INJECTION
    }
  
    // line 14 "../../../../MemoryBudget_static.ump"
     protected void hook348(){
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 8 "../../../../MemoryBudget_static.ump"
    protected boolean is64 ;
  // line 9 "../../../../MemoryBudget_static.ump"
    protected boolean isJVM14 ;
  // line 10 "../../../../MemoryBudget_static.ump"
    protected String overrideArch ;
  // line 11 "../../../../MemoryBudget_static.ump"
    protected String arch ;
  // line 12 "../../../../MemoryBudget_static.ump"
    protected RuntimeException RE ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
    @MethodObject
  // line 16 "../../../../MemoryBudget_static.ump"
  // line 103 "../../../../MemoryBudget_inner_inner.ump"
  // line 4 "../../../../Evictor_MemoryBudget_inner.ump"
  public static class MemoryBudget_reset
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public MemoryBudget_reset()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 18 "../../../../MemoryBudget_static.ump"
    public  MemoryBudget_reset(MemoryBudget _this, DbConfigManager configManager){
      this._this=_this;
          this.configManager=configManager;
    }
  
    // line 22 "../../../../MemoryBudget_static.ump"
    public void execute() throws DatabaseException{
      newMaxMemory=configManager.getLong(EnvironmentParams.MAX_MEMORY);
          jvmMemory=_this.getRuntimeMaxMemory();
          if (newMaxMemory != 0) {
            if (jvmMemory < newMaxMemory) {
              throw new IllegalArgumentException(EnvironmentParams.MAX_MEMORY.getName() + " has a value of " + newMaxMemory+ " but the JVM is only configured for "+ jvmMemory+ ". Consider using je.maxMemoryPercent.");
            }
            if (newMaxMemory < _this.MIN_MAX_MEMORY_SIZE) {
              throw new IllegalArgumentException(EnvironmentParams.MAX_MEMORY.getName() + " is " + newMaxMemory+ " which is less than the minimum: "+ _this.MIN_MAX_MEMORY_SIZE);
            }
          }
     else {
            if (jvmMemory == Long.MAX_VALUE) {
              jvmMemory=_this.N_64MB;
            }
            maxMemoryPercent=configManager.getInt(EnvironmentParams.MAX_MEMORY_PERCENT);
            newMaxMemory=(maxMemoryPercent * jvmMemory) / 100;
          }
          newLogBufferBudget=configManager.getLong(EnvironmentParams.LOG_MEM_SIZE);
          if (newLogBufferBudget == 0) {
            newLogBufferBudget=newMaxMemory >> 4;
          }
     else       if (newLogBufferBudget > newMaxMemory / 2) {
            newLogBufferBudget=newMaxMemory / 2;
          }
          numBuffers=configManager.getInt(EnvironmentParams.NUM_LOG_BUFFERS);
          startingBufferSize=newLogBufferBudget / numBuffers;
          logBufferSize=configManager.getInt(EnvironmentParams.LOG_BUFFER_MAX_SIZE);
          if (startingBufferSize > logBufferSize) {
            startingBufferSize=logBufferSize;
            newLogBufferBudget=numBuffers * startingBufferSize;
          }
     else       if (startingBufferSize < EnvironmentParams.MIN_LOG_BUFFER_SIZE) {
            startingBufferSize=EnvironmentParams.MIN_LOG_BUFFER_SIZE;
            newLogBufferBudget=numBuffers * startingBufferSize;
          }
          //this.hook350();
          Label350:
  newCriticalThreshold=(newMaxMemory * _this.envImpl.getConfigManager().getInt(EnvironmentParams.EVICTOR_CRITICAL_PERCENTAGE)) / 100;
          //original();
  
          newTrackerBudget=(newMaxMemory * _this.envImpl.getConfigManager().getInt(EnvironmentParams.CLEANER_DETAIL_MAX_MEMORY_PERCENTAGE)) / 100;
          _this.maxMemory=newMaxMemory;
          this.hook349();
          _this.logBufferBudget=newLogBufferBudget;
      // line 105 "../../../../MemoryBudget_inner_inner.ump"
      //original();
              _this.trackerBudget=true ? newTrackerBudget : newMaxMemory;
              _this.cacheBudget=newMaxMemory - newLogBufferBudget;
              _this.nLockTables=configManager.getInt(EnvironmentParams.N_LOCK_TABLES);
              _this.lockMemoryUsage=new long[_this.nLockTables];
      // END OF UMPLE AFTER INJECTION
    }
  
    // line 76 "../../../../MemoryBudget_static.ump"
     protected void hook349() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 64 "../../../../MemoryBudget_static.ump"
    protected MemoryBudget _this ;
  // line 65 "../../../../MemoryBudget_static.ump"
    protected DbConfigManager configManager ;
  // line 66 "../../../../MemoryBudget_static.ump"
    protected long newMaxMemory ;
  // line 67 "../../../../MemoryBudget_static.ump"
    protected long jvmMemory ;
  // line 68 "../../../../MemoryBudget_static.ump"
    protected int maxMemoryPercent ;
  // line 69 "../../../../MemoryBudget_static.ump"
    protected long newLogBufferBudget ;
  // line 70 "../../../../MemoryBudget_static.ump"
    protected int numBuffers ;
  // line 71 "../../../../MemoryBudget_static.ump"
    protected long startingBufferSize ;
  // line 72 "../../../../MemoryBudget_static.ump"
    protected int logBufferSize ;
  // line 73 "../../../../MemoryBudget_static.ump"
    protected long newCriticalThreshold ;
  // line 74 "../../../../MemoryBudget_static.ump"
    protected long newTrackerBudget ;
  
    
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
// line 7 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LONG_OVERHEAD_64 = 24 ;
// line 9 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BYTE_ARRAY_OVERHEAD_32 = 16 ;
// line 11 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BYTE_ARRAY_OVERHEAD_64 = 24 ;
// line 13 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int OBJECT_OVERHEAD_32 = 8 ;
// line 15 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int OBJECT_OVERHEAD_64 = 16 ;
// line 17 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int ARRAY_ITEM_OVERHEAD_32 = 4 ;
// line 19 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int ARRAY_ITEM_OVERHEAD_64 = 8 ;
// line 21 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_OVERHEAD_32 = 120 ;
// line 23 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_OVERHEAD_64 = 216 ;
// line 25 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_ENTRY_OVERHEAD_32 = 24 ;
// line 27 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHMAP_ENTRY_OVERHEAD_64 = 48 ;
// line 29 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_OVERHEAD_32 = 136 ;
// line 31 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_OVERHEAD_64 = 240 ;
// line 33 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_ENTRY_OVERHEAD_32 = 24 ;
// line 35 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int HASHSET_ENTRY_OVERHEAD_64 = 48 ;
// line 37 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TWOHASHMAPS_OVERHEAD_32 = 240 ;
// line 39 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TWOHASHMAPS_OVERHEAD_64 = 432 ;
// line 41 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_OVERHEAD_32 = 40 ;
// line 43 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_OVERHEAD_64 = 64 ;
// line 45 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_ENTRY_OVERHEAD_32 = 32 ;
// line 47 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TREEMAP_ENTRY_OVERHEAD_64 = 53 ;
// line 49 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_OVERHEAD_32 = 24 ;
// line 51 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_OVERHEAD_64 = 32 ;
// line 53 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DUPCOUNTLN_OVERHEAD_32 = 24 ;
// line 55 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DUPCOUNTLN_OVERHEAD_64 = 40 ;
// line 57 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_32_14 = 344 ;
// line 59 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_32_15 = 360 ;
// line 61 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_64_15 = 528 ;
// line 63 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_32_14 = 352 ;
// line 65 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_32_15 = 360 ;
// line 67 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_64_15 = 536 ;
// line 69 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_32_14 = 352 ;
// line 71 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_32_15 = 368 ;
// line 73 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_64_15 = 544 ;
// line 75 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_32_14 = 312 ;
// line 77 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_32_15 = 320 ;
// line 79 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_64_15 = 472 ;
// line 81 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int KEY_OVERHEAD_32 = 16 ;
// line 83 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int KEY_OVERHEAD_64 = 24 ;
// line 85 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCK_OVERHEAD_32 = 32 ;
// line 87 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCK_OVERHEAD_64 = 56 ;
// line 89 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCKINFO_OVERHEAD_32 = 16 ;
// line 91 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LOCKINFO_OVERHEAD_64 = 32 ;
// line 93 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TXN_OVERHEAD_32_14 = 167 ;
// line 95 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TXN_OVERHEAD_32_15 = 175 ;
// line 97 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TXN_OVERHEAD_64_15 = 293 ;
// line 99 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_32_14 = 32 + HASHSET_ENTRY_OVERHEAD_32 ;
// line 101 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_32_15 = 40 + HASHSET_ENTRY_OVERHEAD_32 ;
// line 103 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_64_15 = 56 + HASHSET_ENTRY_OVERHEAD_64 ;
// line 105 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int UTILIZATION_PROFILE_ENTRY_32 = 88 ;
// line 107 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int UTILIZATION_PROFILE_ENTRY_64 = 136 ;
// line 109 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_INITIAL_OVERHEAD_32 = 464 ;
// line 111 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_INITIAL_OVERHEAD_64 = 504 ;
// line 113 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_SEGMENT_OVERHEAD_32 = 440 ;
// line 115 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int TFS_LIST_SEGMENT_OVERHEAD_64 = 464 ;
// line 117 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_INFO_OVERHEAD_32 = 24 ;
// line 119 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LN_INFO_OVERHEAD_64 = 48 ;
// line 121 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LONG_LIST_PER_ITEM_OVERHEAD_32 = 20 ;
// line 123 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static int LONG_LIST_PER_ITEM_OVERHEAD_64 = 32 ;
// line 125 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int LONG_OVERHEAD ;
// line 127 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int BYTE_ARRAY_OVERHEAD ;
// line 129 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int OBJECT_OVERHEAD ;
// line 131 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int ARRAY_ITEM_OVERHEAD ;
// line 133 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int HASHMAP_OVERHEAD ;
// line 135 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int HASHMAP_ENTRY_OVERHEAD ;
// line 137 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int HASHSET_OVERHEAD ;
// line 139 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int HASHSET_ENTRY_OVERHEAD ;
// line 141 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int TWOHASHMAPS_OVERHEAD ;
// line 143 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int TREEMAP_OVERHEAD ;
// line 145 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int TREEMAP_ENTRY_OVERHEAD ;
// line 147 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int LN_OVERHEAD ;
// line 149 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int DUPCOUNTLN_OVERHEAD ;
// line 151 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int BIN_FIXED_OVERHEAD ;
// line 153 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int DIN_FIXED_OVERHEAD ;
// line 155 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int DBIN_FIXED_OVERHEAD ;
// line 157 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int IN_FIXED_OVERHEAD ;
// line 159 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int KEY_OVERHEAD ;
// line 161 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int LOCK_OVERHEAD ;
// line 163 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int LOCKINFO_OVERHEAD ;
// line 165 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int TXN_OVERHEAD ;
// line 167 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int CHECKPOINT_REFERENCE_SIZE ;
// line 169 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int UTILIZATION_PROFILE_ENTRY ;
// line 171 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int TFS_LIST_INITIAL_OVERHEAD ;
// line 173 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int TFS_LIST_SEGMENT_OVERHEAD ;
// line 175 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int LN_INFO_OVERHEAD ;
// line 177 "../../../../MemoryBudget_MemoryBudget.ump"
  public static int LONG_LIST_PER_ITEM_OVERHEAD ;
// line 179 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static String JVM_ARCH_PROPERTY = "sun.arch.data.model" ;
// line 181 "../../../../MemoryBudget_MemoryBudget.ump"
  private final static String FORCE_JVM_ARCH = "je.forceJVMArch" ;
// line 183 "../../../../MemoryBudget_MemoryBudget.ump"
  private long treeMemoryUsage ;
// line 185 "../../../../MemoryBudget_MemoryBudget.ump"
  private long miscMemoryUsage ;
// line 187 "../../../../MemoryBudget_MemoryBudget.ump"
  private Object memoryUsageSynchronizer = new Object() ;
// line 189 "../../../../MemoryBudget_MemoryBudget.ump"
  private int nLockTables ;
// line 191 "../../../../MemoryBudget_MemoryBudget.ump"
  private long[] lockMemoryUsage ;
// line 193 "../../../../MemoryBudget_MemoryBudget.ump"
  private long trackerBudget ;
// line 195 "../../../../MemoryBudget_MemoryBudget.ump"
  private long cacheBudget ;
// line 197 "../../../../MemoryBudget_MemoryBudget.ump"
  private long inOverhead ;
// line 199 "../../../../MemoryBudget_MemoryBudget.ump"
  private long binOverhead ;
// line 201 "../../../../MemoryBudget_MemoryBudget.ump"
  private long dinOverhead ;
// line 203 "../../../../MemoryBudget_MemoryBudget.ump"
  private long dbinOverhead ;

  
}