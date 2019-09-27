/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../Tree.ump"
// line 3 "../../../../Tree_inner.ump"
public class Tree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 4 "../../../../Tree_inner.ump"
  public static class Tree_traceInsertDuplicate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceInsertDuplicate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Tree_inner.ump"
    public void execute(){
      logger=env.getLogger();
          if (logger.isLoggable(level)) {
            sb=new StringBuffer();
            sb.append(_this.TRACE_INSERT_DUPLICATE);
            sb.append(" dbin=");
            sb.append(insertingDBin.getNodeId());
            sb.append(" bin=");
            sb.append(binNid);
            sb.append(" ln=");
            sb.append(ln.getNodeId());
            sb.append(" lnLsn=");
            sb.append(DbLsn.getNoFormatString(lnLsn));
            logger.log(level,sb.toString());
          }
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 23 "../../../../Tree_inner.ump"
  public static class Tree_traceInsert
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceInsert()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 25 "../../../../Tree_inner.ump"
    public void execute(){
      logger=env.getLogger();
          if (logger.isLoggable(level)) {
            sb=new StringBuffer();
            sb.append(_this.TRACE_INSERT);
            sb.append(" bin=");
            sb.append(insertingBin.getNodeId());
            sb.append(" ln=");
            sb.append(ln.getNodeId());
            sb.append(" lnLsn=");
            sb.append(DbLsn.getNoFormatString(lnLsn));
            sb.append(" index=");
            sb.append(index);
            logger.log(level,sb.toString());
          }
          original();
    }
  
  }
}