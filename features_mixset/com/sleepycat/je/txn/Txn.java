/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;

// line 3 "../../../../Txn_static.ump"
// line 3 "../../../../Txn.ump"
// line 3 "../../../../Txn_inner.ump"
// line 3 "../../../../MemoryBudget_Txn.ump"
// line 3 "../../../../MemoryBudget_Txn_inner.ump"
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

  // line 12 "../../../../MemoryBudget_Txn.ump"
   private void updateMemoryUsage(int delta){
    inMemorySize += delta;
			accumulatedDelta += delta;
			if (accumulatedDelta > ACCUMULATED_LIMIT || accumulatedDelta < -ACCUMULATED_LIMIT) {
					envImpl.getMemoryBudget().updateMiscMemoryUsage(accumulatedDelta);
					accumulatedDelta = 0;
			}
  }

  // line 21 "../../../../MemoryBudget_Txn.ump"
  public int getAccumulatedDelta(){
    return accumulatedDelta;
  }
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
    @MethodObject
  // line 12 "../../../../Txn_static.ump"
  // line 4 "../../../../MemoryBudget_Txn_inner.ump"
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
            //this.hook815();
            Label815:
  delta=0;
          //original();
  
            if (type.isWriteLock()) {
              if (_this.writeInfo == null) {
                _this.writeInfo=new HashMap();
                _this.undoDatabases=new HashMap();
               // this.hook818();
                Label818:
  delta+=MemoryBudget.TWOHASHMAPS_OVERHEAD;
          //original();
  
              }
              _this.writeInfo.put(nodeId,new WriteLockInfo(lock));
              //this.hook817();
              Label817:
  delta+=_this.WRITE_LOCK_OVERHEAD;
          //original();
  
              if ((grantStatus == LockGrantType.PROMOTION) || (grantStatus == LockGrantType.WAIT_PROMOTION)) {
                _this.readLocks.remove(lock);
                //this.hook819();
                Label819:
  delta-=_this.READ_LOCK_OVERHEAD;
         // original();
  
              }
              //this.hook816();
              Label816:
  _this.updateMemoryUsage(delta);
          //original();
  
            }
     else {
              _this.addReadLock(lock);
            }
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 47 "../../../../Txn_static.ump"
    protected Txn _this ;
  // line 48 "../../../../Txn_static.ump"
    protected Long nodeId ;
  // line 49 "../../../../Txn_static.ump"
    protected Lock lock ;
  // line 50 "../../../../Txn_static.ump"
    protected LockType type ;
  // line 51 "../../../../Txn_static.ump"
    protected LockGrantType grantStatus ;
  // line 52 "../../../../Txn_static.ump"
    protected int delta ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
  // line 66 "../../../../Txn_static.ump"
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
  
    // line 68 "../../../../Txn_static.ump"
    public  Txn_traceCommit(Txn _this, int numWriteLocks, int numReadLocks){
      this._this=_this;
          this.numWriteLocks=numWriteLocks;
          this.numReadLocks=numReadLocks;
    }
  
    // line 73 "../../../../Txn_static.ump"
    public void execute(){
      // line 6 "../../../../Txn_inner.ump"
      logger=envImpl.getLogger();
      // END OF UMPLE BEFORE INJECTION
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 74 "../../../../Txn_static.ump"
    protected Txn _this ;
  // line 75 "../../../../Txn_static.ump"
    protected int numWriteLocks ;
  // line 76 "../../../../Txn_static.ump"
    protected int numReadLocks ;
  // line 77 "../../../../Txn_static.ump"
    protected Logger logger ;
  // line 78 "../../../../Txn_static.ump"
    protected StringBuffer sb ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../MemoryBudget_Txn.ump"
  private final int READ_LOCK_OVERHEAD = MemoryBudget.HASHSET_ENTRY_OVERHEAD ;
// line 7 "../../../../MemoryBudget_Txn.ump"
  private final int WRITE_LOCK_OVERHEAD = MemoryBudget.HASHMAP_ENTRY_OVERHEAD + MemoryBudget.LONG_OVERHEAD ;
// line 9 "../../../../MemoryBudget_Txn.ump"
  private int accumulatedDelta = 0 ;

  
}