/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RecoveryManager_static.ump"
// line 3 "../../../../RecoveryManager.ump"
// line 3 "../../../../RecoveryManager_inner.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../RecoveryManager_static.ump"
  public static class TxnNodeId
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //TxnNodeId Attributes
    private long nodeId;
    private long txnId;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public TxnNodeId(long aNodeId, long aTxnId)
    {
      nodeId = aNodeId;
      txnId = aTxnId;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setNodeId(long aNodeId)
    {
      boolean wasSet = false;
      nodeId = aNodeId;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setTxnId(long aTxnId)
    {
      boolean wasSet = false;
      txnId = aTxnId;
      wasSet = true;
      return wasSet;
    }
  
    public long getNodeId()
    {
      return nodeId;
    }
  
    public long getTxnId()
    {
      return txnId;
    }
  
    public void delete()
    {}
  
    // line 8 "../../../../RecoveryManager_static.ump"
    public  TxnNodeId(long nodeId, long txnId){
      this.nodeId=nodeId;
          this.txnId=txnId;
    }
  
  
    /**
     * 
     * Compare two TxnNodeId objects
     */
    // line 15 "../../../../RecoveryManager_static.ump"
     public boolean equals(Object obj){
      if (this == obj) {
            return true;
          }
          if (!(obj instanceof TxnNodeId)) {
            return false;
          }
          return ((((TxnNodeId)obj).txnId == txnId) && (((TxnNodeId)obj).nodeId == nodeId));
    }
  
    // line 24 "../../../../RecoveryManager_static.ump"
     public int hashCode(){
      return (int)(txnId + nodeId);
    }
  
    // line 27 "../../../../RecoveryManager_static.ump"
     public String toString(){
      return "txnId=" + txnId + "/nodeId="+ nodeId;
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  import com.sleepycat.je.tree.*;
  
  // line 30 "../../../../RecoveryManager_static.ump"
  public static class RootDeleter implements WithRootLatched
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //RootDeleter Attributes
    private Tree tree;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RootDeleter(Tree aTree)
    {
      tree = aTree;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setTree(Tree aTree)
    {
      boolean wasSet = false;
      tree = aTree;
      wasSet = true;
      return wasSet;
    }
  
    public Tree getTree()
    {
      return tree;
    }
  
    public void delete()
    {}
  
    // line 34 "../../../../RecoveryManager_static.ump"
    public  RootDeleter(Tree tree){
      this.tree=tree;
    }
  
  
    /**
     * 
     * @return true if the in-memory root was replaced.
     */
    // line 40 "../../../../RecoveryManager_static.ump"
     public IN doWork(ChildReference root) throws DatabaseException{
      tree.setRoot(null,false);
          return null;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "tree" + "=" + (getTree() != null ? !getTree().equals(this)  ? getTree().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 44 "../../../../RecoveryManager_static.ump"
  // line 4 "../../../../RecoveryManager_inner.ump"
  public static class RecoveryManager_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RecoveryManager_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 46 "../../../../RecoveryManager_static.ump"
    public  RecoveryManager_trace(Level level, DatabaseImpl database, String debugType, boolean success, Node node, long logLsn, IN parent, boolean found, boolean replaced, boolean inserted, long replacedLsn, long abortLsn, int index){
      this.level=level;
          this.database=database;
          this.debugType=debugType;
          this.success=success;
          this.node=node;
          this.logLsn=logLsn;
          this.parent=parent;
          this.found=found;
          this.replaced=replaced;
          this.inserted=inserted;
          this.replacedLsn=replacedLsn;
          this.abortLsn=abortLsn;
          this.index=index;
    }
  
    // line 61 "../../../../RecoveryManager_static.ump"
    public void execute(){
      // line 6 "../../../../RecoveryManager_inner.ump"
      logger=database.getDbEnvironment().getLogger();
              useLevel=level;
              if (!success) {
                useLevel=Level.SEVERE;
              }
              if (logger.isLoggable(useLevel)) {
                sb=new StringBuffer();
                sb.append(debugType);
                sb.append(" success=").append(success);
                sb.append(" node=");
                sb.append(node.getNodeId());
                sb.append(" lsn=");
                sb.append(DbLsn.getNoFormatString(logLsn));
                if (parent != null) {
                  sb.append(" parent=").append(parent.getNodeId());
                }
                sb.append(" found=");
                sb.append(found);
                sb.append(" replaced=");
                sb.append(replaced);
                sb.append(" inserted=");
                sb.append(inserted);
                if (replacedLsn != DbLsn.NULL_LSN) {
                  sb.append(" replacedLsn=");
                  sb.append(DbLsn.getNoFormatString(replacedLsn));
                }
                if (abortLsn != DbLsn.NULL_LSN) {
                  sb.append(" abortLsn=");
                  sb.append(DbLsn.getNoFormatString(abortLsn));
                }
                sb.append(" index=").append(index);
                logger.log(useLevel,sb.toString());
              }
              //original();
      // END OF UMPLE BEFORE INJECTION
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 62 "../../../../RecoveryManager_static.ump"
    protected Level level ;
  // line 63 "../../../../RecoveryManager_static.ump"
    protected DatabaseImpl database ;
  // line 64 "../../../../RecoveryManager_static.ump"
    protected String debugType ;
  // line 65 "../../../../RecoveryManager_static.ump"
    protected boolean success ;
  // line 66 "../../../../RecoveryManager_static.ump"
    protected Node node ;
  // line 67 "../../../../RecoveryManager_static.ump"
    protected long logLsn ;
  // line 68 "../../../../RecoveryManager_static.ump"
    protected IN parent ;
  // line 69 "../../../../RecoveryManager_static.ump"
    protected boolean found ;
  // line 70 "../../../../RecoveryManager_static.ump"
    protected boolean replaced ;
  // line 71 "../../../../RecoveryManager_static.ump"
    protected boolean inserted ;
  // line 72 "../../../../RecoveryManager_static.ump"
    protected long replacedLsn ;
  // line 73 "../../../../RecoveryManager_static.ump"
    protected long abortLsn ;
  // line 74 "../../../../RecoveryManager_static.ump"
    protected int index ;
  // line 75 "../../../../RecoveryManager_static.ump"
    protected Logger logger ;
  // line 76 "../../../../RecoveryManager_static.ump"
    protected Level useLevel ;
  // line 77 "../../../../RecoveryManager_static.ump"
    protected StringBuffer sb ;
  
    
  }
}