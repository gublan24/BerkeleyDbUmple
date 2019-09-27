/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../MemoryBudget.ump"
// line 3 "../../../../MemoryBudget_inner.ump"
public class MemoryBudget
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


  /**
   * 
   * Initialize the starting environment memory state
   */
  // line 209 "../../../../MemoryBudget.ump"
  public void initCacheMemoryUsage() throws DatabaseException{
    synchronized (memoryUsageSynchronizer) {
	    treeMemoryUsage = calcTreeCacheUsage();
	}
  }


  /**
   * 
   * Public for testing.
   */
  // line 218 "../../../../MemoryBudget.ump"
   public long calcTreeCacheUsage() throws DatabaseException{
    long totalSize = 0;
	INList inList = envImpl.getInMemoryINs();
	totalSize = this.hook347(totalSize, inList);
	return totalSize;
  }


  /**
   * 
   * Update the environment wide tree memory count, wake up the evictor if necessary.
   * @param incrementnote that increment may be negative.
   */
  // line 229 "../../../../MemoryBudget.ump"
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
  // line 239 "../../../../MemoryBudget.ump"
   public void updateMiscMemoryUsage(long increment){
    synchronized (memoryUsageSynchronizer) {
	    miscMemoryUsage += increment;
	}
  }

  // line 245 "../../../../MemoryBudget.ump"
   public void updateLockMemoryUsage(long increment, int lockTableIndex){
    lockMemoryUsage[lockTableIndex] += increment;
  }

  // line 249 "../../../../MemoryBudget.ump"
   public long accumulateNewUsage(IN in, long newSize){
    return in.getInMemorySize() + newSize;
  }

  // line 253 "../../../../MemoryBudget.ump"
   public void refreshTreeMemoryUsage(long newSize){
    synchronized (memoryUsageSynchronizer) {
	    treeMemoryUsage = newSize;
	}
  }

  // line 259 "../../../../MemoryBudget.ump"
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
  // line 274 "../../../../MemoryBudget.ump"
   public long getTreeMemoryUsage(){
    return treeMemoryUsage;
  }

  // line 278 "../../../../MemoryBudget.ump"
   public long getTrackerBudget(){
    return trackerBudget;
  }

  // line 282 "../../../../MemoryBudget.ump"
   public long getCacheBudget(){
    return cacheBudget;
  }

  // line 286 "../../../../MemoryBudget.ump"
   public long getINOverhead(){
    return inOverhead;
  }

  // line 290 "../../../../MemoryBudget.ump"
   public long getBINOverhead(){
    return binOverhead;
  }

  // line 294 "../../../../MemoryBudget.ump"
   public long getDINOverhead(){
    return dinOverhead;
  }

  // line 298 "../../../../MemoryBudget.ump"
   public long getDBINOverhead(){
    return dbinOverhead;
  }


  /**
   * 
   * Returns the memory size occupied by a byte array of a given length.
   */
  // line 305 "../../../../MemoryBudget.ump"
   public static  int byteArraySize(int arrayLen){
    int size = BYTE_ARRAY_OVERHEAD;
	if (arrayLen > 4) {
	    size += ((arrayLen - 4 + 7) / 8) * 8;
	}
	return size;
  }

  // line 313 "../../../../MemoryBudget.ump"
   protected long hook347(long totalSize, INList inList) throws DatabaseException{
    Iterator iter = inList.iterator();
	while (iter.hasNext()) {
	    IN in = (IN) iter.next();
	    long size = in.getInMemorySize();
	    totalSize += size;
	}
	return totalSize;
  }

  // line 323 "../../../../MemoryBudget.ump"
   protected void hook351(DbConfigManager configManager) throws DatabaseException{
    inOverhead = IN.computeOverhead(configManager);
	binOverhead = BIN.computeOverhead(configManager);
	dinOverhead = DIN.computeOverhead(configManager);
	dbinOverhead = DBIN.computeOverhead(configManager);
	original(configManager);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../MemoryBudget_inner.ump"
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
  
    // line 6 "../../../../MemoryBudget_inner.ump"
    public void execute(){
      is64=false;
          isJVM14=true;
          original();
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
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 99 "../../../../MemoryBudget_inner.ump"
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
  
    // line 101 "../../../../MemoryBudget_inner.ump"
    public void execute() throws DatabaseException{
      original();
          _this.trackerBudget=true ? newTrackerBudget : newMaxMemory;
          _this.cacheBudget=newMaxMemory - newLogBufferBudget;
          _this.nLockTables=configManager.getInt(EnvironmentParams.N_LOCK_TABLES);
          _this.lockMemoryUsage=new long[_this.nLockTables];
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../MemoryBudget.ump"
  private final static int LONG_OVERHEAD_32 = 16 ;
// line 7 "../../../../MemoryBudget.ump"
  private final static int LONG_OVERHEAD_64 = 24 ;
// line 9 "../../../../MemoryBudget.ump"
  private final static int BYTE_ARRAY_OVERHEAD_32 = 16 ;
// line 11 "../../../../MemoryBudget.ump"
  private final static int BYTE_ARRAY_OVERHEAD_64 = 24 ;
// line 13 "../../../../MemoryBudget.ump"
  private final static int OBJECT_OVERHEAD_32 = 8 ;
// line 15 "../../../../MemoryBudget.ump"
  private final static int OBJECT_OVERHEAD_64 = 16 ;
// line 17 "../../../../MemoryBudget.ump"
  private final static int ARRAY_ITEM_OVERHEAD_32 = 4 ;
// line 19 "../../../../MemoryBudget.ump"
  private final static int ARRAY_ITEM_OVERHEAD_64 = 8 ;
// line 21 "../../../../MemoryBudget.ump"
  private final static int HASHMAP_OVERHEAD_32 = 120 ;
// line 23 "../../../../MemoryBudget.ump"
  private final static int HASHMAP_OVERHEAD_64 = 216 ;
// line 25 "../../../../MemoryBudget.ump"
  private final static int HASHMAP_ENTRY_OVERHEAD_32 = 24 ;
// line 27 "../../../../MemoryBudget.ump"
  private final static int HASHMAP_ENTRY_OVERHEAD_64 = 48 ;
// line 29 "../../../../MemoryBudget.ump"
  private final static int HASHSET_OVERHEAD_32 = 136 ;
// line 31 "../../../../MemoryBudget.ump"
  private final static int HASHSET_OVERHEAD_64 = 240 ;
// line 33 "../../../../MemoryBudget.ump"
  private final static int HASHSET_ENTRY_OVERHEAD_32 = 24 ;
// line 35 "../../../../MemoryBudget.ump"
  private final static int HASHSET_ENTRY_OVERHEAD_64 = 48 ;
// line 37 "../../../../MemoryBudget.ump"
  private final static int TWOHASHMAPS_OVERHEAD_32 = 240 ;
// line 39 "../../../../MemoryBudget.ump"
  private final static int TWOHASHMAPS_OVERHEAD_64 = 432 ;
// line 41 "../../../../MemoryBudget.ump"
  private final static int TREEMAP_OVERHEAD_32 = 40 ;
// line 43 "../../../../MemoryBudget.ump"
  private final static int TREEMAP_OVERHEAD_64 = 64 ;
// line 45 "../../../../MemoryBudget.ump"
  private final static int TREEMAP_ENTRY_OVERHEAD_32 = 32 ;
// line 47 "../../../../MemoryBudget.ump"
  private final static int TREEMAP_ENTRY_OVERHEAD_64 = 53 ;
// line 49 "../../../../MemoryBudget.ump"
  private final static int LN_OVERHEAD_32 = 24 ;
// line 51 "../../../../MemoryBudget.ump"
  private final static int LN_OVERHEAD_64 = 32 ;
// line 53 "../../../../MemoryBudget.ump"
  private final static int DUPCOUNTLN_OVERHEAD_32 = 24 ;
// line 55 "../../../../MemoryBudget.ump"
  private final static int DUPCOUNTLN_OVERHEAD_64 = 40 ;
// line 57 "../../../../MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_32_14 = 344 ;
// line 59 "../../../../MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_32_15 = 360 ;
// line 61 "../../../../MemoryBudget.ump"
  private final static int BIN_FIXED_OVERHEAD_64_15 = 528 ;
// line 63 "../../../../MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_32_14 = 352 ;
// line 65 "../../../../MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_32_15 = 360 ;
// line 67 "../../../../MemoryBudget.ump"
  private final static int DIN_FIXED_OVERHEAD_64_15 = 536 ;
// line 69 "../../../../MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_32_14 = 352 ;
// line 71 "../../../../MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_32_15 = 368 ;
// line 73 "../../../../MemoryBudget.ump"
  private final static int DBIN_FIXED_OVERHEAD_64_15 = 544 ;
// line 75 "../../../../MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_32_14 = 312 ;
// line 77 "../../../../MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_32_15 = 320 ;
// line 79 "../../../../MemoryBudget.ump"
  private final static int IN_FIXED_OVERHEAD_64_15 = 472 ;
// line 81 "../../../../MemoryBudget.ump"
  private final static int KEY_OVERHEAD_32 = 16 ;
// line 83 "../../../../MemoryBudget.ump"
  private final static int KEY_OVERHEAD_64 = 24 ;
// line 85 "../../../../MemoryBudget.ump"
  private final static int LOCK_OVERHEAD_32 = 32 ;
// line 87 "../../../../MemoryBudget.ump"
  private final static int LOCK_OVERHEAD_64 = 56 ;
// line 89 "../../../../MemoryBudget.ump"
  private final static int LOCKINFO_OVERHEAD_32 = 16 ;
// line 91 "../../../../MemoryBudget.ump"
  private final static int LOCKINFO_OVERHEAD_64 = 32 ;
// line 93 "../../../../MemoryBudget.ump"
  private final static int TXN_OVERHEAD_32_14 = 167 ;
// line 95 "../../../../MemoryBudget.ump"
  private final static int TXN_OVERHEAD_32_15 = 175 ;
// line 97 "../../../../MemoryBudget.ump"
  private final static int TXN_OVERHEAD_64_15 = 293 ;
// line 99 "../../../../MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_32_14 = 32 + HASHSET_ENTRY_OVERHEAD_32 ;
// line 101 "../../../../MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_32_15 = 40 + HASHSET_ENTRY_OVERHEAD_32 ;
// line 103 "../../../../MemoryBudget.ump"
  private final static int CHECKPOINT_REFERENCE_SIZE_64_15 = 56 + HASHSET_ENTRY_OVERHEAD_64 ;
// line 105 "../../../../MemoryBudget.ump"
  private final static int UTILIZATION_PROFILE_ENTRY_32 = 88 ;
// line 107 "../../../../MemoryBudget.ump"
  private final static int UTILIZATION_PROFILE_ENTRY_64 = 136 ;
// line 109 "../../../../MemoryBudget.ump"
  private final static int TFS_LIST_INITIAL_OVERHEAD_32 = 464 ;
// line 111 "../../../../MemoryBudget.ump"
  private final static int TFS_LIST_INITIAL_OVERHEAD_64 = 504 ;
// line 113 "../../../../MemoryBudget.ump"
  private final static int TFS_LIST_SEGMENT_OVERHEAD_32 = 440 ;
// line 115 "../../../../MemoryBudget.ump"
  private final static int TFS_LIST_SEGMENT_OVERHEAD_64 = 464 ;
// line 117 "../../../../MemoryBudget.ump"
  private final static int LN_INFO_OVERHEAD_32 = 24 ;
// line 119 "../../../../MemoryBudget.ump"
  private final static int LN_INFO_OVERHEAD_64 = 48 ;
// line 121 "../../../../MemoryBudget.ump"
  private final static int LONG_LIST_PER_ITEM_OVERHEAD_32 = 20 ;
// line 123 "../../../../MemoryBudget.ump"
  private final static int LONG_LIST_PER_ITEM_OVERHEAD_64 = 32 ;
// line 125 "../../../../MemoryBudget.ump"
  public static int LONG_OVERHEAD ;
// line 127 "../../../../MemoryBudget.ump"
  public static int BYTE_ARRAY_OVERHEAD ;
// line 129 "../../../../MemoryBudget.ump"
  public static int OBJECT_OVERHEAD ;
// line 131 "../../../../MemoryBudget.ump"
  public static int ARRAY_ITEM_OVERHEAD ;
// line 133 "../../../../MemoryBudget.ump"
  public static int HASHMAP_OVERHEAD ;
// line 135 "../../../../MemoryBudget.ump"
  public static int HASHMAP_ENTRY_OVERHEAD ;
// line 137 "../../../../MemoryBudget.ump"
  public static int HASHSET_OVERHEAD ;
// line 139 "../../../../MemoryBudget.ump"
  public static int HASHSET_ENTRY_OVERHEAD ;
// line 141 "../../../../MemoryBudget.ump"
  public static int TWOHASHMAPS_OVERHEAD ;
// line 143 "../../../../MemoryBudget.ump"
  public static int TREEMAP_OVERHEAD ;
// line 145 "../../../../MemoryBudget.ump"
  public static int TREEMAP_ENTRY_OVERHEAD ;
// line 147 "../../../../MemoryBudget.ump"
  public static int LN_OVERHEAD ;
// line 149 "../../../../MemoryBudget.ump"
  public static int DUPCOUNTLN_OVERHEAD ;
// line 151 "../../../../MemoryBudget.ump"
  public static int BIN_FIXED_OVERHEAD ;
// line 153 "../../../../MemoryBudget.ump"
  public static int DIN_FIXED_OVERHEAD ;
// line 155 "../../../../MemoryBudget.ump"
  public static int DBIN_FIXED_OVERHEAD ;
// line 157 "../../../../MemoryBudget.ump"
  public static int IN_FIXED_OVERHEAD ;
// line 159 "../../../../MemoryBudget.ump"
  public static int KEY_OVERHEAD ;
// line 161 "../../../../MemoryBudget.ump"
  public static int LOCK_OVERHEAD ;
// line 163 "../../../../MemoryBudget.ump"
  public static int LOCKINFO_OVERHEAD ;
// line 165 "../../../../MemoryBudget.ump"
  public static int TXN_OVERHEAD ;
// line 167 "../../../../MemoryBudget.ump"
  public static int CHECKPOINT_REFERENCE_SIZE ;
// line 169 "../../../../MemoryBudget.ump"
  public static int UTILIZATION_PROFILE_ENTRY ;
// line 171 "../../../../MemoryBudget.ump"
  public static int TFS_LIST_INITIAL_OVERHEAD ;
// line 173 "../../../../MemoryBudget.ump"
  public static int TFS_LIST_SEGMENT_OVERHEAD ;
// line 175 "../../../../MemoryBudget.ump"
  public static int LN_INFO_OVERHEAD ;
// line 177 "../../../../MemoryBudget.ump"
  public static int LONG_LIST_PER_ITEM_OVERHEAD ;
// line 179 "../../../../MemoryBudget.ump"
  private final static String JVM_ARCH_PROPERTY = "sun.arch.data.model" ;
// line 181 "../../../../MemoryBudget.ump"
  private final static String FORCE_JVM_ARCH = "je.forceJVMArch" ;
// line 183 "../../../../MemoryBudget.ump"
  private long treeMemoryUsage ;
// line 185 "../../../../MemoryBudget.ump"
  private long miscMemoryUsage ;
// line 187 "../../../../MemoryBudget.ump"
  private Object memoryUsageSynchronizer = new Object() ;
// line 189 "../../../../MemoryBudget.ump"
  private int nLockTables ;
// line 191 "../../../../MemoryBudget.ump"
  private long[] lockMemoryUsage ;
// line 193 "../../../../MemoryBudget.ump"
  private long trackerBudget ;
// line 195 "../../../../MemoryBudget.ump"
  private long cacheBudget ;
// line 197 "../../../../MemoryBudget.ump"
  private long inOverhead ;
// line 199 "../../../../MemoryBudget.ump"
  private long binOverhead ;
// line 201 "../../../../MemoryBudget.ump"
  private long dinOverhead ;
// line 203 "../../../../MemoryBudget.ump"
  private long dbinOverhead ;

  
}