/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Database.ump"
// line 3 "../../../Database_inner.ump"
public class Database
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Database()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je;
  
  @MethodObject
  // line 4 "../../../Database_inner.ump"
  public static class Database_truncate
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Database_truncate()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../Database_inner.ump"
     protected void hook39() throws DatabaseException{
      Tracer.trace(Level.FINEST,_this.envHandle.getEnvironmentImpl(),"Database.truncate" + ": txnId=" + ((txn == null) ? "null" : Long.toString(txn.getId())));
          original();
    }
  
  }
}