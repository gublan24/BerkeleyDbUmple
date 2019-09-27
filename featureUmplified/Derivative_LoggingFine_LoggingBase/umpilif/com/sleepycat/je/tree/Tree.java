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
  public static class Tree_traceMutate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceMutate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Tree_inner.ump"
    public void execute(){
      logger=_this.database.getDbEnvironment().getLogger();
          if (logger.isLoggable(level)) {
            sb=new StringBuffer();
            sb.append(_this.TRACE_MUTATE);
            sb.append(" existingLn=");
            sb.append(existingLn.getNodeId());
            sb.append(" newLn=");
            sb.append(newLn.getNodeId());
            sb.append(" newLnLsn=");
            sb.append(DbLsn.getNoFormatString(newLsn));
            sb.append(" dupCountLN=");
            sb.append(dupCountLN.getNodeId());
            sb.append(" dupRootLsn=");
            sb.append(DbLsn.getNoFormatString(dupRootLsn));
            sb.append(" rootdin=");
            sb.append(dupRoot.getNodeId());
            sb.append(" ddinLsn=");
            sb.append(DbLsn.getNoFormatString(ddinLsn));
            sb.append(" dbin=");
            sb.append(dupBin.getNodeId());
            sb.append(" dbinLsn=");
            sb.append(DbLsn.getNoFormatString(dbinLsn));
            sb.append(" bin=");
            sb.append(theBin.getNodeId());
            logger.log(level,sb.toString());
          }
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.tree;
  
  @MethodObject
  // line 35 "../../../../Tree_inner.ump"
  public static class Tree_traceSplitRoot
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Tree_traceSplitRoot()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 37 "../../../../Tree_inner.ump"
    public void execute(){
      logger=_this.database.getDbEnvironment().getLogger();
          if (logger.isLoggable(level)) {
            sb=new StringBuffer();
            sb.append(splitType);
            sb.append(" newRoot=").append(newRoot.getNodeId());
            sb.append(" newRootLsn=").append(DbLsn.getNoFormatString(newRootLsn));
            sb.append(" oldRoot=").append(oldRoot.getNodeId());
            sb.append(" oldRootLsn=").append(DbLsn.getNoFormatString(oldRootLsn));
            logger.log(level,sb.toString());
          }
          original();
    }
  
  }
}