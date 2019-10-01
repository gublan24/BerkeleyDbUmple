/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Txn_static.ump"
// line 3 "../../../../Txn.ump"
// line 3 "../../../../Txn_inner.ump"
public class Txn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Txn()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../Txn_static.ump"
  public static class DatabaseCleanupInfo
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //DatabaseCleanupInfo Attributes
    private DatabaseImpl dbImpl;
    private boolean deleteAtCommit;
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DatabaseCleanupInfo(DatabaseImpl aDbImpl, boolean aDeleteAtCommit)
    {
      dbImpl = aDbImpl;
      deleteAtCommit = aDeleteAtCommit;
    }
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public boolean setDbImpl(DatabaseImpl aDbImpl)
    {
      boolean wasSet = false;
      dbImpl = aDbImpl;
      wasSet = true;
      return wasSet;
    }
  
    public boolean setDeleteAtCommit(boolean aDeleteAtCommit)
    {
      boolean wasSet = false;
      deleteAtCommit = aDeleteAtCommit;
      wasSet = true;
      return wasSet;
    }
  
    public DatabaseImpl getDbImpl()
    {
      return dbImpl;
    }
  
    public boolean getDeleteAtCommit()
    {
      return deleteAtCommit;
    }
  
    public void delete()
    {}
  
    // line 8 "../../../../Txn_static.ump"
    public  DatabaseCleanupInfo(DatabaseImpl dbImpl, boolean deleteAtCommit){
      this.dbImpl=dbImpl;
          this.deleteAtCommit=deleteAtCommit;
    }
  
  
    public String toString()
    {
      return super.toString() + "["+
              "deleteAtCommit" + ":" + getDeleteAtCommit()+ "]" + System.getProperties().getProperty("line.separator") +
              "  " + "dbImpl" + "=" + (getDbImpl() != null ? !getDbImpl().equals(this)  ? getDbImpl().toString().replaceAll("  ","    ") : "this" : "null");
    }
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 12 "../../../../Txn_static.ump"
  public static class Txn_addLock
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Txn_addLock()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 14 "../../../../Txn_static.ump"
    public  Txn_addLock(Txn _this, Long nodeId, Lock lock, LockType type, LockGrantType grantStatus){
      this._this=_this;
          this.nodeId=nodeId;
          this.lock=lock;
          this.type=type;
          this.grantStatus=grantStatus;
    }
  
    // line 21 "../../../../Txn_static.ump"
    public void execute() throws DatabaseException{
      synchronized (_this) {
            this.hook815();
            if (type.isWriteLock()) {
              if (_this.writeInfo == null) {
                _this.writeInfo=new HashMap();
                _this.undoDatabases=new HashMap();
                this.hook818();
              }
              _this.writeInfo.put(nodeId,new WriteLockInfo(lock));
              this.hook817();
              if ((grantStatus == LockGrantType.PROMOTION) || (grantStatus == LockGrantType.WAIT_PROMOTION)) {
                _this.readLocks.remove(lock);
                this.hook819();
              }
              this.hook816();
            }
     else {
              _this.addReadLock(lock);
            }
          }
    }
  
    // line 49 "../../../../Txn_static.ump"
     protected void hook815() throws DatabaseException{
      
    }
  
    // line 51 "../../../../Txn_static.ump"
     protected void hook816() throws DatabaseException{
      
    }
  
    // line 53 "../../../../Txn_static.ump"
     protected void hook817() throws DatabaseException{
      
    }
  
    // line 55 "../../../../Txn_static.ump"
     protected void hook818() throws DatabaseException{
      
    }
  
    // line 57 "../../../../Txn_static.ump"
     protected void hook819() throws DatabaseException{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 42 "../../../../Txn_static.ump"
    protected Txn _this ;
  // line 43 "../../../../Txn_static.ump"
    protected Long nodeId ;
  // line 44 "../../../../Txn_static.ump"
    protected Lock lock ;
  // line 45 "../../../../Txn_static.ump"
    protected LockType type ;
  // line 46 "../../../../Txn_static.ump"
    protected LockGrantType grantStatus ;
  // line 47 "../../../../Txn_static.ump"
    protected int delta ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 59 "../../../../Txn_static.ump"
  // line 4 "../../../../Txn_inner.ump"
  public static class Txn_traceCommit
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Txn_traceCommit()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 61 "../../../../Txn_static.ump"
    public  Txn_traceCommit(Txn _this, int numWriteLocks, int numReadLocks){
      this._this=_this;
          this.numWriteLocks=numWriteLocks;
          this.numReadLocks=numReadLocks;
    }
  
    // line 66 "../../../../Txn_static.ump"
    public void execute(){
      // line 6 "../../../../Txn_inner.ump"
      logger=envImpl.getLogger();
              //original(); //@Abdulaziz should be updated .
              //
      // END OF UMPLE BEFORE INJECTION
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 67 "../../../../Txn_static.ump"
    protected Txn _this ;
  // line 68 "../../../../Txn_static.ump"
    protected int numWriteLocks ;
  // line 69 "../../../../Txn_static.ump"
    protected int numReadLocks ;
  // line 70 "../../../../Txn_static.ump"
    protected Logger logger ;
  // line 71 "../../../../Txn_static.ump"
    protected StringBuffer sb ;
  
    
  }
}