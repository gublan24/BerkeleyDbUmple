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

  // line 6 "../../../../FileProcessor.ump"
   protected boolean hook159(DatabaseImpl db, boolean b) throws DatabaseException{
    b |= db.isDeleted();
	return original(db, b);
  }

  // line 11 "../../../../FileProcessor.ump"
   protected void hook160(DatabaseImpl db) throws DatabaseException{
    cleaner.addPendingDB(db);
	original(db);
  }
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
  
    // line 6 "../../../../FileProcessor_inner.ump"
     protected void hook157() throws DatabaseException{
      b|=db.isDeleted();
          original();
    }
  
    // line 10 "../../../../FileProcessor_inner.ump"
     protected void hook158() throws DatabaseException{
      _this.cleaner.addPendingDB(db);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 14 "../../../../FileProcessor_inner.ump"
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
  
    // line 16 "../../../../FileProcessor_inner.ump"
     protected void hook154() throws DatabaseException,IOException{
      checkPendingDbSet=new HashSet();
          original();
    }
  
    // line 20 "../../../../FileProcessor_inner.ump"
     protected void hook155() throws DatabaseException,IOException{
      for (Iterator i=checkPendingDbSet.iterator(); i.hasNext(); ) {
            dbId=(DatabaseId)i.next();
            db=dbMapTree.getDb(dbId,_this.cleaner.lockTimeout,dbCache);
            _this.cleaner.addPendingDB(db);
          }
          original();
    }
  
    // line 28 "../../../../FileProcessor_inner.ump"
     protected void hook156() throws DatabaseException,IOException{
      dbId1=reader.getDatabaseId();
          if (dbId1 != null) {
            checkPendingDbSet.add(dbId1);
          }
          original();
    }
  
  }
}