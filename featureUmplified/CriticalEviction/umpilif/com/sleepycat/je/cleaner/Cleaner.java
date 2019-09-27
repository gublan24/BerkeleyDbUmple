/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../Cleaner.ump"
// line 3 "../../../../Cleaner_inner.ump"
public class Cleaner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cleaner()
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
  // line 4 "../../../../Cleaner_inner.ump"
  public static class Cleaner_processPending
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Cleaner_processPending()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Cleaner_inner.ump"
     protected void hook86() throws DatabaseException{
      
    }
  
    // line 8 "../../../../Cleaner_inner.ump"
     protected void hook114() throws DatabaseException{
      if (_this.DO_CRITICAL_EVICTION) {
            this.hook86();
          }
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../Cleaner.ump"
  static final boolean DO_CRITICAL_EVICTION = true ;

  
}