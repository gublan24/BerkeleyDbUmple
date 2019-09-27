/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.incomp.INCompressor;

// line 3 "../../../../EnvironmentImpl.ump"
// line 3 "../../../../EnvironmentImpl_inner.ump"
public class EnvironmentImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Return the incompressor. In general, don't use this directly because it's easy to forget that the incompressor can be null at times (i.e during the shutdown procedure. Instead, wrap the functionality within this class, like lazyCompress.
   */
  // line 12 "../../../../EnvironmentImpl.ump"
   public INCompressor getINCompressor(){
    return inCompressor;
  }


  /**
   * 
   * Tells the asynchronous IN compressor thread about a BIN with a deleted entry.
   */
  // line 19 "../../../../EnvironmentImpl.ump"
   public void addToCompressorQueue(BIN bin, Key deletedKey, boolean doWakeup) throws DatabaseException{
    if (inCompressor != null) {
	    inCompressor.addBinKeyToQueue(bin, deletedKey, doWakeup);
	}
  }


  /**
   * 
   * Tells the asynchronous IN compressor thread about a BINReference with a deleted entry.
   */
  // line 28 "../../../../EnvironmentImpl.ump"
   public void addToCompressorQueue(BINReference binRef, boolean doWakeup) throws DatabaseException{
    if (inCompressor != null) {
	    inCompressor.addBinRefToQueue(binRef, doWakeup);
	}
  }


  /**
   * 
   * Tells the asynchronous IN compressor thread about a collections of BINReferences with deleted entries.
   */
  // line 37 "../../../../EnvironmentImpl.ump"
   public void addToCompressorQueue(Collection binRefs, boolean doWakeup) throws DatabaseException{
    if (inCompressor != null) {
	    inCompressor.addMultipleBinRefsToQueue(binRefs, doWakeup);
	}
  }


  /**
   * 
   * Do lazy compression at opportune moments.
   */
  // line 46 "../../../../EnvironmentImpl.ump"
   public void lazyCompress(IN in) throws DatabaseException{
    if (inCompressor != null) {
	    inCompressor.lazyCompress(in);
	}
  }


  /**
   * 
   * Invoke a compress programatically. Note that only one compress may run at a time.
   */
  // line 55 "../../../../EnvironmentImpl.ump"
   public boolean invokeCompressor() throws DatabaseException{
    if (inCompressor != null) {
	    inCompressor.doCompress();
	    return true;
	} else {
	    return false;
	}
  }


  /**
   * 
   * Available for the unit tests.
   */
  // line 67 "../../../../EnvironmentImpl.ump"
   public void shutdownINCompressor() throws InterruptedException{
    if (inCompressor != null) {
	    inCompressor.shutdown();
	    inCompressor.clearEnv();
	    inCompressor = null;
	}
	return;
  }

  // line 76 "../../../../EnvironmentImpl.ump"
   public int getINCompressorQueueSize() throws DatabaseException{
    return inCompressor.getBinRefQueueSize();
  }

  // line 80 "../../../../EnvironmentImpl.ump"
   protected void hook330(DbConfigManager mgr) throws DatabaseException{
    inCompressor.runOrPause(mgr.getBoolean(EnvironmentParams.ENV_RUN_INCOMPRESSOR));
	original(mgr);
  }

  // line 85 "../../../../EnvironmentImpl.ump"
   protected void hook331(){
    if (inCompressor != null) {
	    inCompressor.requestShutdown();
	}
	original();
  }


  /**
   * 
   * Ask all daemon threads to shut down.
   */
  // line 95 "../../../../EnvironmentImpl.ump"
   private void shutdownDaemons() throws InterruptedException{
    shutdownINCompressor();
	original();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../EnvironmentImpl_inner.ump"
  public static class EnvironmentImpl_createDaemons
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public EnvironmentImpl_createDaemons()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../EnvironmentImpl_inner.ump"
     protected void hook332() throws DatabaseException{
      compressorWakeupInterval=PropUtil.microsToMillis(_this.configManager.getLong(EnvironmentParams.COMPRESSOR_WAKEUP_INTERVAL));
          _this.inCompressor=new INCompressor(_this,compressorWakeupInterval,"INCompressor");
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../EnvironmentImpl.ump"
  private INCompressor inCompressor ;

  
}