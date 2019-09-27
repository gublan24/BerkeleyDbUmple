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
     protected void hook194() throws DatabaseException{
      oldMemorySize=_this.fileSummaryMap.size() * MemoryBudget.UTILIZATION_PROFILE_ENTRY;
          original();
    }
  
    // line 10 "../../../../UtilizationProfile_inner.ump"
     protected void hook195() throws DatabaseException{
      newMemorySize=_this.fileSummaryMap.size() * MemoryBudget.UTILIZATION_PROFILE_ENTRY;
          mb=_this.env.getMemoryBudget();
          mb.updateMiscMemoryUsage(newMemorySize - oldMemorySize);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 16 "../../../../UtilizationProfile_inner.ump"
  public static class UtilizationProfile_removeFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_removeFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 18 "../../../../UtilizationProfile_inner.ump"
     protected void hook192() throws DatabaseException{
      mb=_this.env.getMemoryBudget();
          mb.updateMiscMemoryUsage(0 - MemoryBudget.UTILIZATION_PROFILE_ENTRY);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 23 "../../../../UtilizationProfile_inner.ump"
  public static class UtilizationProfile_putFileSummary
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_putFileSummary()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 25 "../../../../UtilizationProfile_inner.ump"
     protected void hook193() throws DatabaseException{
      mb=_this.env.getMemoryBudget();
          mb.updateMiscMemoryUsage(MemoryBudget.UTILIZATION_PROFILE_ENTRY);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 30 "../../../../UtilizationProfile_inner.ump"
  public static class UtilizationProfile_clearCache
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_clearCache()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 32 "../../../../UtilizationProfile_inner.ump"
    public void execute(){
      memorySize=_this.fileSummaryMap.size() * MemoryBudget.UTILIZATION_PROFILE_ENTRY;
          mb=_this.env.getMemoryBudget();
          mb.updateMiscMemoryUsage(0 - memorySize);
          original();
    }
  
  }
}