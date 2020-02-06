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

// line 3 "../../../../MemoryBudget.ump"
// line 3 "../../../../MemoryBudget_static.ump"
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
      Label351:   ;
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
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../MemoryBudget_static.ump"
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
      Label348:   ; //this.hook348();
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
  
  
  
  // line 15 "../../../../MemoryBudget_static.ump"
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
  
    // line 17 "../../../../MemoryBudget_static.ump"
    public  MemoryBudget_reset(MemoryBudget _this, DbConfigManager configManager){
      this._this=_this;
          this.configManager=configManager;
    }
  
    // line 21 "../../../../MemoryBudget_static.ump"
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
  
          Label350:   ;        //this.hook350();
          newTrackerBudget=(newMaxMemory * _this.envImpl.getConfigManager().getInt(EnvironmentParams.CLEANER_DETAIL_MAX_MEMORY_PERCENTAGE)) / 100;
          _this.maxMemory=newMaxMemory;
          Label349:   ; //this.hook349();
          _this.logBufferBudget=newLogBufferBudget;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 63 "../../../../MemoryBudget_static.ump"
    protected MemoryBudget _this ;
  // line 64 "../../../../MemoryBudget_static.ump"
    protected DbConfigManager configManager ;
  // line 65 "../../../../MemoryBudget_static.ump"
    protected long newMaxMemory ;
  // line 66 "../../../../MemoryBudget_static.ump"
    protected long jvmMemory ;
  // line 67 "../../../../MemoryBudget_static.ump"
    protected int maxMemoryPercent ;
  // line 68 "../../../../MemoryBudget_static.ump"
    protected long newLogBufferBudget ;
  // line 69 "../../../../MemoryBudget_static.ump"
    protected int numBuffers ;
  // line 70 "../../../../MemoryBudget_static.ump"
    protected long startingBufferSize ;
  // line 71 "../../../../MemoryBudget_static.ump"
    protected int logBufferSize ;
  // line 72 "../../../../MemoryBudget_static.ump"
    protected long newCriticalThreshold ;
  // line 73 "../../../../MemoryBudget_static.ump"
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

  
}