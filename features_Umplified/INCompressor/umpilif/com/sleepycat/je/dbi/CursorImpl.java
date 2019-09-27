/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../CursorImpl.ump"
// line 3 "../../../../CursorImpl_inner.ump"
public class CursorImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CursorImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../CursorImpl.ump"
   protected void hook281(byte [] lnKey) throws DatabaseException{
    locker.addDeleteInfo(dupBin, new Key(lnKey));
	original(lnKey);
  }

  // line 11 "../../../../CursorImpl.ump"
   protected void hook282(byte [] lnKey) throws DatabaseException{
    locker.addDeleteInfo(bin, new Key(lnKey));
	original(lnKey);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.dbi;
  
  @MethodObject
  // line 4 "../../../../CursorImpl_inner.ump"
  public static class CursorImpl_fetchCurrent
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public CursorImpl_fetchCurrent()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../CursorImpl_inner.ump"
     protected void hook280() throws DatabaseException{
      envImpl=_this.database.getDbEnvironment();
          envImpl.addToCompressorQueue(_this.targetBin,new Key(_this.targetBin.getKey(_this.targetIndex)),false);
          original();
    }
  
  }
}