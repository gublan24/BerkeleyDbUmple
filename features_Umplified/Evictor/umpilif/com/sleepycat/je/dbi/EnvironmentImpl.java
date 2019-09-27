/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import com.sleepycat.je.evictor.Evictor;

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

  // line 9 "../../../../EnvironmentImpl.ump"
   public void invokeEvictor() throws DatabaseException{
    if (evictor != null) {
	    evictor.doEvict(Evictor.SOURCE_MANUAL);
	}
  }

  // line 15 "../../../../EnvironmentImpl.ump"
   public void shutdownEvictor() throws InterruptedException{
    if (evictor != null) {
	    evictor.shutdown();
	    evictor.clearEnv();
	    evictor = null;
	}
	return;
  }

  // line 24 "../../../../EnvironmentImpl.ump"
   public Evictor getEvictor(){
    return evictor;
  }

  // line 28 "../../../../EnvironmentImpl.ump"
  public void alertEvictor(){
    if (evictor != null) {
	    evictor.alert();
	}
  }

  // line 34 "../../../../EnvironmentImpl.ump"
   protected void hook334(){
    if (evictor != null) {
	    evictor.requestShutdown();
	}
	original();
  }


  /**
   * 
   * Ask all daemon threads to shut down.
   */
  // line 44 "../../../../EnvironmentImpl.ump"
   private void shutdownDaemons() throws InterruptedException{
    original();
	shutdownEvictor();
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
    public void execute() throws DatabaseException{
      _this.evictor=new Evictor(_this,"Evictor");
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../EnvironmentImpl.ump"
  private Evictor evictor ;

  
}