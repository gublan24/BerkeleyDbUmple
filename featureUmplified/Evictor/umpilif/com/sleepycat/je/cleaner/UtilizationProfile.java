/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../UtilizationProfile.ump"
// line 3 "../../../../UtilizationProfile_inner.ump"
public class UtilizationProfile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UtilizationProfile()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../UtilizationProfile.ump"
   protected void hook187(CursorImpl cursor) throws DatabaseException{
    cursor.evict();
	original(cursor);
  }

  // line 11 "../../../../UtilizationProfile.ump"
   protected void hook188(CursorImpl cursor) throws DatabaseException{
    cursor.evict();
	original(cursor);
  }

  // line 16 "../../../../UtilizationProfile.ump"
   protected void hook189(CursorImpl cursor) throws DatabaseException{
    cursor.setAllowEviction(false);
	original(cursor);
  }

  // line 21 "../../../../UtilizationProfile.ump"
   protected void hook190(CursorImpl cursor) throws DatabaseException{
    cursor.evict();
	original(cursor);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../UtilizationProfile_inner.ump"
  public static class UtilizationProfile_populateCache
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_populateCache()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../UtilizationProfile_inner.ump"
     protected void hook191() throws DatabaseException{
      cursor.evict();
          original();
    }
  
  }
}