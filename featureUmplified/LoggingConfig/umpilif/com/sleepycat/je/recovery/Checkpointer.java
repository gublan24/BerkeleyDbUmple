/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../Checkpointer.ump"
// line 3 "../../../../Checkpointer_inner.ump"
public class Checkpointer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Checkpointer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../Checkpointer.ump"
   private void trace(EnvironmentImpl envImpl, String invokingSource, boolean success){
    StringBuffer sb = new StringBuffer();
	sb.append("Checkpoint ").append(checkpointId);
	sb.append(": source=").append(invokingSource);
	sb.append(" success=").append(success);
	this.hook516(sb);
	Tracer.trace(Level.CONFIG, envImpl, sb.toString());
  }

  // line 15 "../../../../Checkpointer.ump"
   protected void hook516(StringBuffer sb){
    
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.recovery;
  
  @MethodObject
  // line 4 "../../../../Checkpointer_inner.ump"
  public static class Checkpointer_doCheckpoint
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Checkpointer_doCheckpoint()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Checkpointer_inner.ump"
     protected void hook522() throws DatabaseException{
      traced=false;
          original();
    }
  
    // line 10 "../../../../Checkpointer_inner.ump"
     protected void hook523() throws DatabaseException{
      _this.trace(_this.envImpl,invokingSource,true);
          traced=true;
          original();
    }
  
    // line 15 "../../../../Checkpointer_inner.ump"
     protected void hook524() throws DatabaseException{
      if (!traced) {
            _this.trace(_this.envImpl,invokingSource,success);
          }
          original();
    }
  
  }
}