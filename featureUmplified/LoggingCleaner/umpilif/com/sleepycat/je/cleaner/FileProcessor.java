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

  // line 7 "../../../../FileProcessor.ump"
   protected void hook124(long logLsn, LN ln, boolean obsolete, boolean migrated, boolean completed) throws DatabaseException{
    cleaner.trace(cleaner.detailedTraceLevel, Cleaner.CLEAN_LN, ln, logLsn, completed, obsolete, migrated);
	original(logLsn, ln, obsolete, migrated, completed);
  }

  // line 13 "../../../../FileProcessor.ump"
   protected void hook125(IN inClone, DatabaseImpl db, long lsn, boolean obsolete, boolean dirtied, boolean completed) throws DatabaseException{
    try {
	    original(inClone, db, lsn, obsolete, dirtied, completed);
	} finally {
	    cleaner.trace(cleaner.detailedTraceLevel, Cleaner.CLEAN_IN, inClone, lsn, completed, obsolete, dirtied);
	}
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
     protected void hook126() throws DatabaseException{
      if (processedHere) {
            _this.cleaner.trace(_this.cleaner.detailedTraceLevel,Cleaner.CLEAN_LN,ln,logLsn,completed,obsolete,false);
          }
          original();
    }
  
  }
}