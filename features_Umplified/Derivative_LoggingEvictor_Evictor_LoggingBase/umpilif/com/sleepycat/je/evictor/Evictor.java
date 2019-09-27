/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.evictor;

// line 3 "../../../../Evictor.ump"
// line 3 "../../../../Evictor_inner.ump"
public class Evictor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evictor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.evictor;
  
  @MethodObject
  // line 4 "../../../../Evictor_inner.ump"
  public static class Evictor_evictBatch
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_evictBatch()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_inner.ump"
     protected void hook368() throws DatabaseException{
      
    }
  
    // line 8 "../../../../Evictor_inner.ump"
     protected void hook369() throws DatabaseException{
      
    }
  
    // line 10 "../../../../Evictor_inner.ump"
     protected void hook371() throws DatabaseException{
      logger=_this.envImpl.getLogger();
          if (logger.isLoggable(_this.detailedTraceLevel)) {
            msg="Evictor: ";
            this.hook369();
            msg+=" finished=" + finished + " source="+ source+ " requiredEvictBytes="+ _this.formatter.format(requiredEvictBytes)+ " evictBytes="+ _this.formatter.format(evictBytes)+ " inListSize="+ inListStartSize+ " nNodesScanned="+ _this.nNodesScannedThisRun;
            this.hook368();
            msg+=" nBatchSets=" + nBatchSets;
            Tracer.trace(_this.detailedTraceLevel,_this.envImpl,msg);
          }
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.evictor;
  
  @MethodObject
  // line 22 "../../../../Evictor_inner.ump"
  public static class Evictor_isRunnable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Evictor_isRunnable()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 24 "../../../../Evictor_inner.ump"
     protected void hook370() throws DatabaseException{
      
    }
  
    // line 26 "../../../../Evictor_inner.ump"
     protected void hook372() throws DatabaseException{
      logger=_this.envImpl.getLogger();
          if (logger.isLoggable(_this.detailedTraceLevel)) {
            r=Runtime.getRuntime();
            totalBytes=r.totalMemory();
            freeBytes=r.freeMemory();
            usedBytes=r.totalMemory() - r.freeMemory();
            sb=new StringBuffer();
            sb.append(" source=").append(source);
            this.hook370();
            sb.append(" requiredEvict=").append(_this.formatter.format(_this.currentRequiredEvictBytes));
            sb.append(" JVMtotalBytes= ").append(_this.formatter.format(totalBytes));
            sb.append(" JVMfreeBytes= ").append(_this.formatter.format(freeBytes));
            sb.append(" JVMusedBytes= ").append(_this.formatter.format(usedBytes));
            logger.log(_this.detailedTraceLevel,sb.toString());
          }
          original();
    }
  
  }
}