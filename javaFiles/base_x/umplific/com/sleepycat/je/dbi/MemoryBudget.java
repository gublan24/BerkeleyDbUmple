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
	this.hook351(configManager);
  }


  /**
   * 
   * Respond to config updates.
   */
  // line 41 "../../../../MemoryBudget.ump"
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
  // line 52 "../../../../MemoryBudget.ump"
   private void reset(DbConfigManager configManager) throws DatabaseException{
    new MemoryBudget_reset(this, configManager).execute();
  }


  /**
   * 
   * Returns Runtime.maxMemory(), accounting for a MacOS bug. May return Long.MAX_VALUE if there is no inherent limit. Used by unit tests as well as by this class.
   */
  // line 59 "../../../../MemoryBudget.ump"
   public static  long getRuntimeMaxMemory(){
    if ("Mac OS X".equals(System.getProperty("os.name"))) {
	    String jvmVersion = System.getProperty("java.version");
	    if (jvmVersion != null && jvmVersion.startsWith("1.4.2")) {
		return Long.MAX_VALUE;
	    }
	}
	return Runtime.getRuntime().maxMemory();
  }

  // line 69 "../../../../MemoryBudget.ump"
   public long getLogBufferBudget(){
    return logBufferBudget;
  }

  // line 73 "../../../../MemoryBudget.ump"
   public long getMaxMemory(){
    return maxMemory;
  }

  // line 77 "../../../../MemoryBudget.ump"
   protected void hook351(DbConfigManager configManager) throws DatabaseException{
    
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