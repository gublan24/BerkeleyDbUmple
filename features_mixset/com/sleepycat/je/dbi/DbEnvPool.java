/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.DatabaseException;
import java.util.Map;
import java.util.Hashtable;
import java.io.IOException;
import java.io.File;

// line 3 "../../../../DbEnvPool.ump"
// line 3 "../../../../DbEnvPool_static.ump"
public class DbEnvPool
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbEnvPool()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Enforce singleton behavior.
   */
  // line 20 "../../../../DbEnvPool.ump"
   private  DbEnvPool(){
    envs = new Hashtable();
  }


  /**
   * 
   * Access the singleton instance.
   */
  // line 27 "../../../../DbEnvPool.ump"
   public static  DbEnvPool getInstance(){
    return pool;
  }


  /**
   * 
   * If the environment is not open, open it.
   */
  // line 34 "../../../../DbEnvPool.ump"
   public EnvironmentImplInfo getEnvironment(File envHome, EnvironmentConfig config) throws DatabaseException{
    return getEnvironment(envHome, config, true);
  }

  // line 38 "../../../../DbEnvPool.ump"
   public EnvironmentImplInfo getExistingEnvironment(File envHome) throws DatabaseException{
    return getEnvironment(envHome, null, false);
  }


  /**
   * 
   * Find a single environment, used by Environment handles and by command line utilities.
   */
  // line 46 "../../../../DbEnvPool.ump"
   private synchronized  EnvironmentImplInfo getEnvironment(File envHome, EnvironmentConfig config, boolean openIfNeeded) throws DatabaseException{
    boolean found;
	boolean firstHandle = false;
	EnvironmentImpl environmentImpl = null;
	String environmentKey = getEnvironmentMapKey(envHome);
	if (envs.containsKey(environmentKey)) {
	    environmentImpl = (EnvironmentImpl) envs.get(environmentKey);
	    if (!environmentImpl.isOpen()) {
		if (openIfNeeded) {
		    environmentImpl.open();
		    found = true;
		} else {
		    found = false;
		}
	    } else {
		found = true;
	    }
	} else {
	    if (openIfNeeded) {
		environmentImpl = new EnvironmentImpl(envHome, config);
		envs.put(environmentKey, environmentImpl);
		firstHandle = true;
		found = true;
	    } else {
		found = false;
	    }
	}
	if (found) {
	    return new EnvironmentImplInfo(environmentImpl, firstHandle);
	} else {
	    return new EnvironmentImplInfo(null, false);
	}
  }


  /**
   * 
   * Remove a EnvironmentImpl from the pool because it's been closed.
   */
  // line 83 "../../../../DbEnvPool.ump"
  public void remove(File envHome) throws DatabaseException{
    envs.remove(getEnvironmentMapKey(envHome));
  }

  // line 87 "../../../../DbEnvPool.ump"
   public void clear(){
    envs.clear();
  }

  // line 91 "../../../../DbEnvPool.ump"
   private String getEnvironmentMapKey(File file) throws DatabaseException{
    try {
	    return file.getCanonicalPath();
	} catch (IOException e) {
	    throw new DatabaseException(e);
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../DbEnvPool_static.ump"
  public static class EnvironmentImplInfo
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImplInfo()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 8 "../../../../DbEnvPool_static.ump"
    public  EnvironmentImplInfo(EnvironmentImpl envImpl, boolean firstHandle){
      this.envImpl=envImpl;
          this.firstHandle=firstHandle;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../DbEnvPool_static.ump"
    public EnvironmentImpl envImpl ;
  // line 6 "../../../../DbEnvPool_static.ump"
    public boolean firstHandle=false ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../DbEnvPool.ump"
  private static DbEnvPool pool = new DbEnvPool() ;
// line 14 "../../../../DbEnvPool.ump"
  private Map envs ;

  
}