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
   protected void hook173() throws DatabaseException{
    env.getEvictor().doCriticalEviction();
	original();
  }

  // line 11 "../../../../UtilizationProfile.ump"
   protected void hook174() throws DatabaseException{
    env.getEvictor().doCriticalEviction();
	original();
  }

  // line 16 "../../../../UtilizationProfile.ump"
   protected void hook175() throws DatabaseException{
    env.getEvictor().doCriticalEviction();
	original();
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
     protected void hook176() throws DatabaseException{
      _this.env.getEvictor().doCriticalEviction();
          original();
    }
  
  }
}