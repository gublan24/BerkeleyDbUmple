/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../FileProcessor.ump"
// line 3 "../../../../FileProcessor_inner.ump"
public class FileProcessor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileProcessor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../FileProcessor_inner.ump"
  public static class FileProcessor_processLN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processLN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 8 "../../../../FileProcessor_inner.ump"
     protected void hook117() throws DatabaseException{
      
    }
  
    // line 10 "../../../../FileProcessor_inner.ump"
    public void execute() throws DatabaseException{
      lookAheadCache=(LookAheadCache)lookAheadCachep;
          original();
    }
  
    // line 14 "../../../../FileProcessor_inner.ump"
     protected void hook132() throws DatabaseException{
      offset=lookAheadCache.nextOffset();
          info=lookAheadCache.remove(offset);
          original();
    }
  
    // line 19 "../../../../FileProcessor_inner.ump"
     protected void hook133() throws DatabaseException{
      if (!isDupCountLN) {
            for (int i=0; i < bin.getNEntries(); i+=1) {
              lsn=bin.getLsn(i);
              if (i != index && !bin.isEntryKnownDeleted(i) && !bin.isEntryPendingDeleted(i) && DbLsn.getFileNumber(lsn) == fileNum.longValue()) {
                myOffset=new Long(DbLsn.getFileOffset(lsn));
                myInfo=lookAheadCache.remove(myOffset);
                if (myInfo != null) {
                  this.hook117();
                  _this.processFoundLN(myInfo,lsn,lsn,bin,i,null);
                }
              }
            }
          }
          original();
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../FileProcessor_inner.ump"
    protected LookAheadCache lookAheadCache ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 35 "../../../../FileProcessor_inner.ump"
  public static class FileProcessor_processFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 37 "../../../../FileProcessor_inner.ump"
     protected void hook116() throws DatabaseException,IOException{
      
    }
  
    // line 39 "../../../../FileProcessor_inner.ump"
     protected void hook127() throws DatabaseException,IOException{
      lookAheadCache=new LookAheadCache(lookAheadCacheSize);
          original();
    }
  
    // line 43 "../../../../FileProcessor_inner.ump"
     protected void hook128() throws DatabaseException,IOException{
      lookAheadCacheSize=_this.cleaner.lookAheadCacheSize;
          original();
    }
  
    // line 47 "../../../../FileProcessor_inner.ump"
     protected void hook129() throws DatabaseException,IOException{
      while (!lookAheadCache.isEmpty()) {
            this.hook116();
            _this.processLN(fileNum,location,null,null,lookAheadCache,dbCache);
          }
          original();
    }
  
    // line 54 "../../../../FileProcessor_inner.ump"
     protected void hook130() throws DatabaseException,IOException{
      lookAheadCache.add(aLsn,aLninfo);
          if (lookAheadCache.isFull()) {
            original();
          }
    }
  
    // line 60 "../../../../FileProcessor_inner.ump"
     protected void hook131() throws DatabaseException,IOException{
      p=lookAheadCache;
          original();
    }
  
  }
}